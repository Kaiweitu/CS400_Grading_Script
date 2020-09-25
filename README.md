# CS400 Grading Script
This is Kaiwei's implementation of CS400 grading script.

## Overview

Folder structure:
```text
# This is the testing code directory for the AVLTreeUnitTest written in Junit 5
AVLTreeUnitTest
 |- scr
 |  |- main/java    # This is where the student code will be placed
 |  |- test
 |      |- java     # This is where the all the test files stay
 |          |- TestSuite1.java  # This is a template test code file
 |      |- resources    # This is where all the input files stay
```


```text
# This is the directory where we put all the submission files
# Unzip the large zip files downloading from the zybook and put all those zip files 
# for each student into this directory
submission
 |- Kaiwei_Tu_ktu5wisc.edu_2020-09-21_18-22-46.zip # A sample submission zip file
result
 |- placeholder.log # A sample log file

# This is a python script which will automatically grade all the student submission
# in the <submission_dir> directory.
#
# Usage: python3 grade_script -i <submission_dir> -o <logs_dir> -g <csv_path> -t <tests_dir>
# 
# <submission_dir> is where all the submission zip file stays.
# <logs_dir> is where all the grading log will be written to.
# <csv_path> is a csv generated by the script which contains all the student's grade
# <tests_dir> is where the test is 
#
# Sample: python3 grade_script.py -i submission -o result -t AVLTreeUnitTest -g grade.csv
grade_script.py

# A script will run the following command:
# python3 grade_script.py -i submission -o result -t AVLTreeUnitTest -g grade.csv
runtest.sh
```

```text
# A generated result csv with following columns:
# Name, Email, Submission Timestamp, Passed, Failed, Total
grade_result_2020-09-22_19-51-51.csv
```

## Usage
Clone the repo and run the tests.
```bash
git clone git@github.com:Kaiweitu/CS400_Grading_Script.git
cd CS400_Grading_Script

# Copy all the submission zip files into the submission directory

# Copy all the testing files into the corresponding directory if applicable
cp <tests>/* AVLTreeUnitTest/src/test/java/

# Copy all the input files into the corresponding directory if applicable
cp <inputs>/* AVLTreeUnitTest/src/test/resources/

./runtest.sh
```