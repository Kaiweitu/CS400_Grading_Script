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
        listener.printReport();

    }



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
    void insertRootSplit() {
        BTree btree = new BTree(3);
        btree.insert(1);
        btree.insert(2);
        btree.insert(3);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(1);
        expected.add(3);
        assertArrayEquals(expected.toArray(), btree.preOrderTraversal().toArray());
    }

    @Test
    @DisplayName("10")
    void insertChildSplit() {
        BTree btree = new BTree(3);
        btree.insert(1);
        btree.insert(2);
        btree.insert(3);
        btree.insert(4);
        btree.insert(5);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(4);
        expected.add(1);
        expected.add(3);
        expected.add(5);
        assertArrayEquals(expected.toArray(), btree.preOrderTraversal().toArray());
    }

    @Test
    @DisplayName("15")
    void insertMultipleSplit() {
        BTree btree = new BTree(3);
        btree.insert(1);
        btree.insert(2);
        btree.insert(3);
        btree.insert(4);
        btree.insert(5);
        btree.insert(6);
        btree.insert(7);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(4);
        expected.add(2);
        expected.add(1);
        expected.add(3);
        expected.add(6);
        expected.add(5);
        expected.add(7);
        assertArrayEquals(expected.toArray(), btree.preOrderTraversal().toArray());
    }

    @Test
    @DisplayName("25")
    void removeMultipleMerge() {
        BTree btree = new BTree(3);
        btree.insert(1);
        btree.insert(2);
        btree.insert(3);
        btree.insert(4);
        btree.insert(5);
        btree.insert(6);
        btree.insert(7);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(4);
        expected.add(2);
        expected.add(1);
        expected.add(3);
        expected.add(6);
        expected.add(5);
        expected.add(7);
        // Sanity check whether it's the tree we expected.
        assertArrayEquals(expected.toArray(), btree.preOrderTraversal().toArray());

        btree.delete(7);
        expected.clear();
        expected.add(2);
        expected.add(4);
        expected.add(1);
        expected.add(3);
        expected.add(5);
        expected.add(6);
        assertArrayEquals(expected.toArray(), btree.preOrderTraversal().toArray());

        btree.insert(7);
        // assertArrayEquals(expected.toArray(), btree.preOrderTraversal().toArray());
        btree.delete(5);
        expected.clear();
        expected.add(2);
        expected.add(4);
        expected.add(1);
        expected.add(3);
        expected.add(6);
        expected.add(7);
        assertArrayEquals(expected.toArray(), btree.preOrderTraversal().toArray());
    }


    @Test
    @DisplayName("15")
    void insertMerge() {
        BTree btree = new BTree(3);
        btree.insert(1);
        btree.insert(2);
        btree.insert(3);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(1);
        expected.add(3);
        assertArrayEquals(expected.toArray(), btree.preOrderTraversal().toArray());


        btree.delete(3);
        expected.clear();
        expected.add(1);
        expected.add(2);
        assertArrayEquals(expected.toArray(), btree.preOrderTraversal().toArray());
    }


    @Test
    @DisplayName("15")
    void contains() {
        BTree btree = new BTree(5);

        int a1 = 0;
        int a2 = 1;

        btree.insert(a1);
        btree.insert(a2);

        for (int i = 0; i < 40; i++) {
            btree.insert(a1 + a2);
            int temp = a1;
            a1 = a2;
            a2 += temp;
        }

        System.out.println( btree.contains(28657));
        assertEquals(true, btree.contains(28657));
        assertEquals(false, btree.contains(102334156));
    }

    @Test
    @DisplayName("15")
    void errorhandle() {
        BTree btree = new BTree(5);
        btree.insert(1);

        assertDoesNotThrow(
                () -> btree.delete(2)
        );

        assertDoesNotThrow(
                () -> btree.delete(null)
        );

        assertDoesNotThrow(
                () -> btree.contains(null)
        );

        assertDoesNotThrow(
                () -> btree.contains(2)
        );

    }







}