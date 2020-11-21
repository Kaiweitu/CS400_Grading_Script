import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

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
    @DisplayName("10")
    void addVertex() {
        Graph<Integer> graph = new Graph<Integer>();
        graph.addVertex(2);
        assertTrue(graph.getAllVertices().contains(2));
        graph.addVertex(10);
        assertTrue(graph.getAllVertices().contains(10));
        graph.addVertex(20);
        assertTrue(graph.getAllVertices().contains(20));
        graph.addVertex(30);
        assertTrue(graph.getAllVertices().contains(30));
    }

    @Test
    @DisplayName("10")
    void addEdge() {
        Graph<Integer> graph = new Graph<Integer>();
        graph.addVertex(2);
        assertTrue(graph.getAllVertices().contains(2));
        graph.addVertex(10);
        assertTrue(graph.getAllVertices().contains(10));
        graph.addVertex(20);
        assertTrue(graph.getAllVertices().contains(20));
        graph.addVertex(30);
        assertTrue(graph.getAllVertices().contains(30));

        graph.addEdge(2, 10);
        assertTrue(graph.getAdjacentVerticesOf(2).contains(10));
        assertTrue(graph.getAdjacentVerticesOf(10).contains(2));
        graph.addEdge(10, 20);
        assertTrue(graph.getAdjacentVerticesOf(10).contains(20));
        assertTrue(graph.getAdjacentVerticesOf(20).contains(10));

    }

    @Test
    @DisplayName("10")
    void removeVertex() {
        Graph<Integer> graph = new Graph<Integer>();
        graph.addVertex(2);
        assertTrue(graph.getAllVertices().contains(2));
        graph.addVertex(10);
        assertTrue(graph.getAllVertices().contains(10));
        graph.addVertex(20);
        assertTrue(graph.getAllVertices().contains(20));
        graph.addVertex(30);
        assertTrue(graph.getAllVertices().contains(30));

        graph.removeVertex(10);
        assertFalse(graph.getAllVertices().contains(10));
    }

    @Test
    @DisplayName("10")
    void removeEdge() {
        Graph<Integer> graph = new Graph<Integer>();
        graph.addVertex(2);
        assertTrue(graph.getAllVertices().contains(2));
        graph.addVertex(10);
        assertTrue(graph.getAllVertices().contains(10));
        graph.addVertex(20);
        assertTrue(graph.getAllVertices().contains(20));
        graph.addVertex(30);
        assertTrue(graph.getAllVertices().contains(30));

        graph.addEdge(2, 10);
        assertTrue(graph.getAdjacentVerticesOf(2).contains(10));
        assertTrue(graph.getAdjacentVerticesOf(10).contains(2));
        graph.addEdge(10, 20);
        assertTrue(graph.getAdjacentVerticesOf(10).contains(20));
        assertTrue(graph.getAdjacentVerticesOf(20).contains(10));


        graph.removeEdge(10, 20);
        assertFalse(graph.getAdjacentVerticesOf(10).contains(20));
        assertFalse(graph.getAdjacentVerticesOf(20).contains(10));

    }

    @Test
    @DisplayName("20")
    void size() {
        Graph<Integer> graph = new Graph<Integer>();
        graph.addVertex(2);
        assertTrue(graph.getAllVertices().contains(2));
        graph.addVertex(10);
        assertTrue(graph.getAllVertices().contains(10));
        graph.addVertex(20);
        assertTrue(graph.getAllVertices().contains(20));
        graph.addVertex(30);
        assertTrue(graph.getAllVertices().contains(30));

        assertEquals(0, graph.size());
        graph.addEdge(2, 10);
        assertEquals(1, graph.size());
        graph.removeEdge(2, 10);
        assertEquals(0, graph.size());
    }


    @Test
    @DisplayName("20")
    void empty() {
        Graph<Integer> graph = new Graph<Integer>();
        graph.addVertex(2);
        assertTrue(graph.getAllVertices().contains(2));
        graph.addVertex(10);
        assertTrue(graph.getAllVertices().contains(10));

        graph.addEdge(2, 10);
        assertTrue(graph.getAdjacentVerticesOf(2).contains(10));
        assertTrue(graph.getAdjacentVerticesOf(10).contains(2));



        // No Exception
        graph.addVertex(2);
        graph.addEdge(2, 10);
        graph.removeVertex(100);
        graph.removeEdge(2, 11);
    }

    @Test
    @DisplayName("20")
    void errorHandle() {
        Graph<Integer> graph = new Graph<>();
        assertThrows(
                IllegalArgumentException.class,
                () -> graph.addVertex(null),
                "Expected insert to throw NullKeyException but it didn't"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge(null, null),
                "Expected insert to throw NullKeyException but it didn't"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.removeEdge(null, null),
                "Expected insert to throw NullKeyException but it didn't"
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> graph.removeVertex(null),
                "Expected insert to throw NullKeyException but it didn't"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.getAdjacentVerticesOf(null),
                "Expected insert to throw NullKeyException but it didn't"
        );
    }

}