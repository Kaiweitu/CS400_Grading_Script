import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;


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
    void insertRotateRightHidden() {
        // CASE 1
        AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
        try {
            tree.insert(10, "");
            tree.insert(11, "");
            tree.insert(5, "");
            tree.insert(2, "");
            tree.insert(7, "");

            String output = removeWhitespace(tree.preOrderTraversal());

            assertEquals("1052711", output);

            // Rotate right
            tree.delete(11);
            assertEquals("75210", removeWhitespace(tree.preOrderTraversal()));

        } catch (DuplicateKeyException e) {
            fail("failed: unexpected DuplicateKeyException\n");
        }
        catch (IllegalKeyException e) {
            fail("failed: unexpected IllegalKeyException\n");
        }
    }


    @Test
    @DisplayName("10")
    void insertRotateLeftRightHidden() {
        // CASE 1
        AVLTree<Integer, String> tree = new AVLTree<Integer, String>();

        try {
            tree.insert(10, "");
            tree.insert(11, "");
            tree.insert(5, "");
            tree.insert(7, "");

            String output = removeWhitespace(tree.preOrderTraversal());

            assertEquals("105711", output);

            // Rotate left right
            tree.delete(11);
            assertEquals("7510", removeWhitespace(tree.preOrderTraversal()));


        } catch (DuplicateKeyException e) {
            fail("failed: unexpected DuplicateKeyException\n");
        }
        catch (IllegalKeyException e) {
            fail("failed: unexpected IllegalKeyException\n");
        }
    }

    @Test
    @DisplayName("5")
    void insertRotateLeftHidden() {
        // CASE 1
        AVLTree<Integer, String> tree = new AVLTree<Integer, String>();

        try {
            tree.insert(10, "");
            tree.insert(15, "");
            tree.insert(5, "");
            tree.insert(11, "");
            tree.insert(16, "");

            assertEquals("105151116", removeWhitespace(tree.preOrderTraversal()));

            // Rotate left
            tree.delete(5);
            assertEquals("11101516", removeWhitespace(tree.preOrderTraversal()));


        } catch (DuplicateKeyException e) {
            fail("failed: unexpected DuplicateKeyException\n");
        }
        catch (IllegalKeyException e) {
            fail("failed: unexpected IllegalKeyException\n");
        }
    }


    @Test
    @DisplayName("10")
    void insertRotateRightLeftHidden() {
        // CASE 1
        AVLTree<Integer, String> tree = new AVLTree<Integer, String>();

        try {
            tree.insert(10, "");
            tree.insert(15, "");
            tree.insert(5, "");
            tree.insert(11, "");

            assertEquals("1051511", removeWhitespace(tree.preOrderTraversal()));

            // Rotate left
            tree.delete(5);
            assertEquals("111015", removeWhitespace(tree.preOrderTraversal()));


        } catch (DuplicateKeyException e) {
            fail("failed: unexpected DuplicateKeyException\n");
        }
        catch (IllegalKeyException e) {
            fail("failed: unexpected IllegalKeyException\n");
        }
    }


}