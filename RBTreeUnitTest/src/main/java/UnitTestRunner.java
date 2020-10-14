import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

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
    void insertRootOnly()  {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();

        try {
            tree.insert("bucky", 1);
        } catch (IllegalKeyException e){
            fail("unexpected IllegalKeyException occurred when calling insert()");
        }

        assertEquals(0, tree.getHeight());
        assertEquals(1, tree.getSize());

        try {
            assertEquals(1, tree.getValue("bucky"));
        }
        catch (IllegalKeyException e){
            fail("unexpected IllegalKeyException occurred when calling getValue()");
        }
    }

    @Test
    @DisplayName("5")
    void getEmptyTree()  {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();

        assertEquals(-1, tree.getHeight());
        assertEquals(0, tree.getSize());

        try {
            assertEquals(null, tree.getValue("bucky"));
        }
        catch (IllegalKeyException e){
            fail("unexpected IllegalKeyException occurred when calling getValue()");
        }
    }

    @Test
    @DisplayName("10")
    void insertSingleRotateLeft()   {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();

        try {
            tree.insert("a", 1);
            tree.insert("b", 1);
            tree.insert("c", 1);
        } catch (IllegalKeyException e){
            fail("unexpected IllegalKeyException occurred when calling insert()");
        }

        assertEquals(1, tree.getHeight());
        assertEquals(3, tree.getSize());
        assertEquals("bac", removeWhitespace(tree.preOrderTraversal().trim()));
    }

    @Test
    @DisplayName("10")
    void insertSingleRotateRight()   {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();

        try {
            tree.insert("a", 1);
            tree.insert("b", 1);
            tree.insert("c", 1);
        } catch (IllegalKeyException e){
            fail("unexpected IllegalKeyException occurred when calling insert()");
        }

        assertEquals(1, tree.getHeight());
        assertEquals(3, tree.getSize());
        assertEquals("bac", removeWhitespace(tree.preOrderTraversal().trim()));
    }

    @Test
    @DisplayName("10")
    void insertSingleRecoloring()   {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();

        try {
            tree.insert("c", 1);
            tree.insert("b", 1);
            tree.insert("d", 1);
            tree.insert("e", 1);
            tree.insert("a", 1);
        } catch (IllegalKeyException e){
            fail("unexpected IllegalKeyException occurred when calling insert()");
        }

        assertEquals(2, tree.getHeight());
        assertEquals(5, tree.getSize());
        assertEquals("cbade", removeWhitespace(tree.preOrderTraversal().trim()));
    }

    @Test
    @DisplayName("25")
    void insertMultiRecoloring()  {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();

        try {
            tree.insert("h", 1);
            tree.insert("c", 1);
            tree.insert("l", 1);
            tree.insert("j", 1);
            tree.insert("p", 1);
            tree.insert("m", 1);
            tree.insert("r", 1);
            tree.insert("a", 1);
            tree.insert("e", 1);
            tree.insert("d", 1);

            assertEquals(3, tree.getHeight());
            assertEquals(10, tree.getSize());
            assertEquals("hcaedljpmr", removeWhitespace(tree.preOrderTraversal().trim()));

            tree.insert("z", 1);

            assertEquals("hcaedljpmrz", removeWhitespace(tree.preOrderTraversal().trim()));

        } catch (IllegalKeyException e){
            fail("unexpected IllegalKeyException occurred when calling insert()");
        }

    }

    @Test
    @DisplayName("15")
    void insertRotateLeftRight()   {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();

        try {
            tree.insert("y", 1);
            tree.insert("c", 1);
            tree.insert("h", 1);

            assertEquals(1, tree.getHeight());
            assertEquals(3, tree.getSize());
            assertEquals("hcy", removeWhitespace(tree.preOrderTraversal().trim()));


            tree.insert("e", 1);
            tree.insert("d", 1);

            assertEquals(2, tree.getHeight());
            assertEquals(5, tree.getSize());
            assertEquals("hdcey", removeWhitespace(tree.preOrderTraversal().trim()));

        } catch (IllegalKeyException e){
            fail("unexpected IllegalKeyException occurred when calling insert()");
        }
    }

    @Test
    @DisplayName("15")
    void insertRotateRightLeft() {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();

        try {
            tree.insert("h", 1);
            tree.insert("n", 1);
            tree.insert("p", 1);

            assertEquals(1, tree.getHeight());
            assertEquals(3, tree.getSize());
            assertEquals("nhp", removeWhitespace(tree.preOrderTraversal().trim()));


            tree.insert("t", 1);
            tree.insert("s", 1);

            assertEquals(2, tree.getHeight());
            assertEquals(5, tree.getSize());
            assertEquals("nhspt", removeWhitespace(tree.preOrderTraversal().trim()));

        }
        catch (IllegalKeyException e) {
            fail("unexpected IllegalKeyException occurred when calling insert()");
        }
    }

    @Test
    @DisplayName("5")
    void insertUpdate() {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();

        try {
            tree.insert("h", 1);
            tree.insert("h", 2);

            assertEquals(0, tree.getHeight());
            assertEquals(1, tree.getSize());
            assertEquals(2, tree.getValue("h"));
        }
        catch (IllegalKeyException e) {
            fail("unexpected IllegalKeyException occurred when calling insert()");
        }

    }

}