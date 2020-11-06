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
    @DisplayName("5")
    void insert() {
        HashTable<Integer, String> table = new HashTable<>();
        try {
            table.insert(5, "five");
            assertEquals("five", table.get(5));
            table.insert(6, "six");
            assertEquals("six", table.get(6));

        } catch (DuplicateKeyException e) {
            fail("Unknown Exception");
        } catch (KeyNotFoundException e) {
            fail("Unknown Exception");
        } catch (NullKeyException e) {
            fail("Unknown Exception");
        } catch (Exception e) {
            fail("Unknown Exception");
        }

    }

    @Test
    @DisplayName("5")
    void resize() {
        HashTable<Integer, String> table = new HashTable<>(4, 0.75);
        assertEquals(4, table.getCapacity());
        try {
            table.insert(5, "five");
            assertEquals("five", table.get(5));
            assertEquals(0.25f, table.getLoadFactor());

            table.insert(6, "six");
            assertEquals(0.5f, table.getLoadFactor());

            table.insert(7, "seven");
            assertEquals(0.33, (double) Math.round(table.getLoadFactor() * 100d) / 100d);


            assertEquals(3, table.numKeys());

        } catch (DuplicateKeyException e) {
            fail("Unknown Exception");
        } catch (KeyNotFoundException e) {
            fail("Unknown Exception");
        } catch (NullKeyException e) {
            fail("Unknown Exception");
        } catch (Exception e) {
            fail("Unknown Exception");
        }

    }

    @Test
    @DisplayName("15")
    void remove() {
        HashTable<Integer, String> table = new HashTable<>(4, 0.75);
        assertEquals(4, table.getCapacity());
        try {
            table.insert(5, "five");
            table.insert(6, "six");
            table.insert(7, "seven");
            table.insert(8, "eight");
            table.insert(14, "fourteen");
            table.insert(10, "ten");
            assertEquals("five", table.get(5));
            assertEquals("six", table.get(6));
            assertEquals("seven", table.get(7));
            assertEquals("eight", table.get(8));
            assertEquals("fourteen", table.get(14));
            assertEquals("ten", table.get(10));

            assertEquals(6, table.numKeys());
            assertEquals(false, table.remove(11));
            assertEquals(true, table.remove(14));
            assertEquals(5, table.numKeys());
            assertEquals(true, table.remove(10));
            assertEquals(4, table.numKeys());


        } catch (DuplicateKeyException e) {
            fail("Unknown Exception");
        } catch (KeyNotFoundException e) {
            fail("Unknown Exception");
        } catch (NullKeyException e) {
            fail("Unknown Exception");
        } catch (Exception e) {
            fail("Unknown Exception");
        }

    }

    @Test
    @DisplayName("10")
    void insertException() {
        HashTable<Integer, String> table = new HashTable<>(4, 0.75);
        try {
            table.insert(5, "five");
            assertThrows(
                    DuplicateKeyException.class,
                    () -> table.insert(5, "five"),
                    "Expected insert to throw DuplicateKeyException but it didn't"
            );

            assertThrows(
                    NullKeyException.class,
                    () -> table.insert(null, "five"),
                    "Expected insert to throw NullKeyException but it didn't"
            );



        } catch (DuplicateKeyException e) {
            fail("Unknown Exception");
        } catch (NullKeyException e) {
            fail("Unknown Exception");
        } catch (Exception e) {
            fail("Unknown Exception");
        }
    }

    @Test
    @DisplayName("5")
    void removeException() {
        HashTable<Integer, String> table = new HashTable<>(4, 0.75);

        assertThrows(
                NullKeyException.class,
                () -> table.remove(null),
                "Expected insert to throw NullKeyException but it didn't"
        );


    }

    @Test
    @DisplayName("10")
    void getException() {
        HashTable<Integer, String> table = new HashTable<>(4, 0.75);

        assertThrows(
                NullKeyException.class,
                () -> table.get(null),
                "Expected insert to throw NullKeyException but it didn't"
        );

        assertThrows(
                KeyNotFoundException.class,
                () -> table.get(10),
                "Expected insert to throw KeyNotFoundException but it didn't"
        );
    }

}