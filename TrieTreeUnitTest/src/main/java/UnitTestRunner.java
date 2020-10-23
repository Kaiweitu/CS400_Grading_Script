import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;


@Timeout(10)
class UnitTestRunner {
    public static void main(String[] argv) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
        .selectors(selectClass(UnitTestRunner.class))
        .build();
    
        Launcher launcher = LauncherFactory.create();
        
        // Register a listener of your choice
        TestResultListener listener = new TestResultListener();
        SummaryGeneratingListener slistener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.registerTestExecutionListeners(slistener);
        
        launcher.execute(request);
        TestExecutionSummary summary = slistener.getSummary();
        summary.printFailuresTo(new PrintWriter(System.out));
        // summary.printTo(new PrintWriter((System.out)));
        listener.printReport();

    }



    /**
     * Remove whitespace from the print output
     * @param String
     * @return String
     */
    private String removeWhitespace(String str) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                strBuilder.append(str.charAt(i));
            }
        }

        String finalString = strBuilder.toString();
        return finalString;
    }


    @Test
    @DisplayName("5")
    void insertTwice() {
        // CASE 1
        TrieTree tree = new TrieTree();

        tree.insert("dog");
        tree.insert("dog");
//        tree.printTrie();

        assertEquals(2, tree.getFrequency("dog"));

        assertEquals(1, tree.getSize());
    }

    @Test
    @DisplayName("10")
    void insertMultiple() {
        // CASE 1
        TrieTree tree = new TrieTree();

        tree.insert("dog");
        tree.insert("dogge");
        tree.insert("buggy");
        tree.insert("dog");
//        tree.printTrie();

        assertEquals(2, tree.getFrequency("dog"));
        assertEquals(1, tree.getFrequency("buggy"));
        assertEquals(1, tree.getFrequency("dogge"));

        assertEquals(3, tree.getSize());
    }

    @Test
    @DisplayName("5")
    void getSizeEmpty() {
        // CASE 1
        TrieTree tree = new TrieTree();

        assertEquals(0, tree.getSize());

    }

    @Test
    @DisplayName("5")
    void getFrequency() {
        // CASE 1
        TrieTree tree = new TrieTree();

        assertEquals(0, tree.getFrequency("dog"));

    }


    @Test
    @DisplayName("20")
    void getWordsWithPrefix() {
        TrieTree tree = new TrieTree();

        assertEquals(0, tree.getWordsWithPrefix("1").size());
        tree.insert("dog");
        tree.insert("din");
        tree.insert("cat");
        tree.insert("train");
        tree.insert("can");
        tree.insert("candle");
        tree.insert("doggie");
        tree.insert("doggie");
        tree.insert("doggie");
        tree.insert("doggi");
        tree.insert("doggi");
        tree.insert("doggi");
        tree.insert("doggieee");
        tree.insert("doggieeee");
        tree.insert("doggieeeee");
        tree.insert("dog");
        tree.insert("dog");
        tree.insert("doggieeee");
        tree.insert("dog");
        tree.insert("coat");
        tree.insert("cat");

        ArrayList<String> expected = new ArrayList(Arrays.asList(
                "can", "candle", "cat", "coat", "din", "dog", "doggi", "doggie", "doggieee", "doggieeee", "doggieeeee", "train"
        ));

        System.out.println(tree.getWordsWithPrefix(""));

        ArrayList<String> ans = tree.getWordsWithPrefix("");

        for (int i = 0 ; i < ans.size(); i++) {
            if (expected.contains(ans.get(i))) {
                expected.remove(ans.get(i));
            } else{
                fail("getWordsWithPrefix return an non-existed string");
            }
        }

        assertEquals(0, expected.size());

    }

    @Test
    @DisplayName("30")
    void StressTest() {
        TrieTree tree = new TrieTree();
        ArrayList<String> expected = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String words = "a";
            int charNum = (int)'a';
            for (int j = 0; j < i; j++) {
                words += (char)(charNum + (i * 20 + j) % 20);
            }
            expected.add(words);
            tree.insert(words);
        }
        assertEquals(1000, tree.getSize());
        ArrayList<String> ans = tree.getWordsWithPrefix("");
        for (int i = 0 ; i < ans.size(); i++) {
            if (expected.contains(ans.get(i))) {
                expected.remove(ans.get(i));
            } else{
                fail("getWordsWithPrefix return an non-existed string");
            }
        }

        assertEquals(0, expected.size());
    }


    @Test
    @DisplayName("25")
    void errorHandle() {
        TrieTree tree = new TrieTree();
        assertThrows(
                IllegalArgumentException.class,
                () -> tree.insert(""),
                "Expected insert to throw IllegalArgumentException but it didn't"
        );


        assertThrows(
                IllegalArgumentException.class,
                () -> tree.insert(null),
                "Expected insert to throw IllegalArgumentException but it didn't"
        );


        assertThrows(
                IllegalArgumentException.class,
                () -> tree.getFrequency(null),
                "Expected insert to throw IllegalArgumentException but it didn't"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> tree.getFrequency(""),
                "Expected insert to throw IllegalArgumentException but it didn't"
        );


        assertThrows(
                IllegalArgumentException.class,
                () -> tree.getWordsWithPrefix(null),
                "Expected insert to throw IllegalArgumentException but it didn't"
        );




    }





}