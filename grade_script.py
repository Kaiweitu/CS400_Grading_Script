from os import listdir, mkdir, remove
from os.path import join, exists
from zipfile import ZipFile
import sys, shutil, datetime, time, subprocess
import getopt

class Error(Exception):
    """Base class for exceptions in this module."""
    pass

class CompileError(Error):
    """Exception raised for errors in the compilation.

    Attributes:
        profile -- student profile that failed the compilation
        message -- explanation of the error
    """

    def __init__(self, profile, message):
        self._profile = profile
        self._message = message


class StudentProfile:
    def __init__(self, name, email, timestamp, unzip_dir):
        self._name = name
        self._email = email
        self._timestamp = timestamp
        self._sub_dir = join(unzip_dir, "{}_{}_{}".format(name, email, timestamp))
        self._passed = -1
        self._failed = -1


    def get_subdir(self):
        return self._sub_dir

    def get_name(self):
        return self._name
    
    def get_email(self):
        return self._email
    
    def get_timestamp(self):
        return self._timestamp

    def get_passed(self):
        return self._passed
    
    def get_failed(self):
        return self._failed

    def start_grade(self, grade_dir, result_dir):
        log_path = join(result_dir, "{}_{}_{}.log".format(self._name, self._email, self._timestamp))
        if exists(log_path):
            remove(log_path)

        print("Start grading for the student {}".format(self._name))
        print("\tCopy the student's code into the testing code...")
        file_copied = []
        for f in listdir(self._sub_dir):
            dest = join(grade_dir, 'src', 'main', 'java', f)
            shutil.copy(join(self._sub_dir, f), dest)
            file_copied.append(dest)

        with open(log_path, "w+") as log:
            rnt = subprocess.run(["./gradlew", "clean", "assemble", "testClasses"], cwd=grade_dir, stdout=log, stderr=log)

        print("\tCompile student's source code...")
        if rnt.returncode != 0:
            raise CompileError(self, "Unable to compile student {}'s source code".format(self._name))
        
        print("\tStart running the Unit Test...")
        child = subprocess.Popen(
                                    ["java", "-jar", "junit-platform-console-standalone-1.7.0-all.jar", "-cp", "build/classes/java/test/:build/classes/java/main/", "--scan-classpath"], \
                                    cwd = grade_dir,
                                    stdout=subprocess.PIPE
                                )
        output = child.communicate()[0]
        lines = output.splitlines()
        failed = int(lines[-2].split()[1])
        success = int(lines[-3].split()[1])
        aborted = int(lines[-4].split()[1])
        started = int(lines[-5].split()[1])

        assert failed + success + aborted == started
        # print(failed, success, aborted, started)
        self._passed = success
        self._failed = failed + aborted

        
        with open(log_path, "ab+") as log:
            log.write(output)
        print("\tWrite out the grading log file...")

        for f in file_copied:
            remove(f)
        print("\tClean up the testing environment\n")



def unzip_sub(sub_folder):
    unzip_directory_name = "/tmp/{}_unzip".format(sub_folder) 
    if exists(unzip_directory_name):
        shutil.rmtree(unzip_directory_name)
    mkdir(unzip_directory_name)
    student_list = []
    for f in listdir(sub_folder):
        sufix = f.split('.')[-1]
        if sufix != "zip":
            print("\tFile {} is not a zip file. Skipped".format(f))
            continue
        tokens = f.split('.')[:-1]
        name_email = tokens[0].split('_') 
        name = '_'.join(name_email[:-1])
        email = "{}@wisc.edu".format(name_email[-1][:-4])
        timestamp = tokens[1][4:]
        student = StudentProfile(name, email, timestamp, unzip_directory_name)

        unzip_directory_path = student.get_subdir()
        print("\t{}; Email: {}; Timestamp: {}".format(name, email, timestamp))
        mkdir(unzip_directory_path)
        with ZipFile(join(sub_folder, f), 'r') as zip_ref:
            zip_ref.extractall(unzip_directory_path)
        student_list.append(student)
    return student_list


def main():
    help_description = "python3 grade_script -i <submission_dir> -o <logs_dir> -g <csv_path> -t <tests_dir>"
    try:
        opts, args = getopt.getopt(sys.argv[1:], "i:o:t:g:h", [])
    except getopt.GetoptError:
        print(help_description)
        sys.exit(1)
    
    
    for opt, arg in opts:
        if opt == '-h':
            print(help_description)
            sys.exit(1)
        elif opt in ("-i"):
            sub_dir = arg
        elif opt in ("-o"):
            result_dir = arg
        elif opt in ("-t"):
            test_dir = arg
        elif opt in ("-g"):
            grade_csv = arg
        else:
            print(help_description)
            sys.exit(1) 

    if not exists(result_dir):
        mkdir(result_dir)
    print("===================================")
    print("= Welcome! CS 400 Testing Script  =")
    print("===================================\n")
    print("Submission directory will be graded: {}".format(sub_dir))
    print("Extracting all the submission zip files...")
    students = unzip_sub(sub_dir)
    print("Extract successfully.")
    print("Total {} submissions waits to be graded".format(len(students)))
    print("\n")

    print("Prepare Grading Environment")

    timestamp = datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d_%H-%M-%S')
    temp_dir = join("/", "tmp", "cs400_grading_tmp_dir_{}".format(timestamp))
    mkdir(temp_dir)
    print("Created temperoray grading directory at {}".format(temp_dir))

    shutil.copytree(test_dir, join(temp_dir, test_dir))
    print("Copy unit test directory to the temporary directory...")

    print("Start Grading...")

    compilation_failed_list = []
    for st in students:
        try:
            st.start_grade(join(temp_dir, test_dir), result_dir)
        except CompileError:
            compilation_failed_list.append(st)
    # shutil.rmtree(temp_dir)

    print("\n===================================")
    print("=             Summary             =")
    print("===================================\n")

    print("Successfully graded {} students\n".format(len(students) - len(compilation_failed_list)))
    print("{} students compile failed, and please check manually.\n".format(len(compilation_failed_list)))
    for st in compilation_failed_list:
        print("...[Compilation Failed] {} ; {} ; {}".format(st.get_name(), st.get_email(), st.get_timestamp()))

    for st in students:
        if st not in compilation_failed_list:
            with open("grade_result_{}.csv".format(timestamp), "a+") as f:
                f.write("{},{},{},{},{},{}\n".format(st.get_name(), st.get_email(), st.get_timestamp(), st.get_passed(), st.get_failed(), st.get_passed() + st.get_failed()))


if __name__ == "__main__":
    main()
