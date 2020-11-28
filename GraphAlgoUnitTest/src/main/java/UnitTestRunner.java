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
import java.util.Arrays;
import java.util.List;
import java.util.Set;


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
    @DisplayName("25")
    void empty() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-empty.json");
        System.out.println(s.averageFriendsPerPerson());
        System.out.println(s.socialButterfly());
        System.out.println(s.influencer());
        System.out.println(s.sixDegreesOfSeparation());

        System.out.println(s.mutualFriends("Alisha", "Nate"));
        System.out.println(s.haveSeenMeme("Carol", 2));
        System.out.println(s.youMayKnow("Carol"));
        System.out.println(s.socialLadder("Alisha", "Stanley"));
        //System.out.println(s.glue());


        Set<String> expectedMutual = Set.of();
        Set<String> expectedSeen = Set.of();
        Set<String> expectedKnow = Set.of();
        List<String> expectedLadder = Arrays.asList();

        // System.out.println(s.glue(expectedKnow));
        assertTrue(expectedSeen.equals(s.haveSeenMeme("Carol", 2)));
        assertTrue(expectedKnow.equals(s.youMayKnow("Carol")));
        assertTrue(expectedMutual.equals(s.mutualFriends("Alisha", "Nate")));
        assertTrue(expectedLadder.equals(s.socialLadder("Alisha", "Stanley")));
        assertEquals("", s.glue(expectedKnow));

        assertEquals(0, s.averageFriendsPerPerson());
        assertEquals("", s.socialButterfly());
        assertEquals("", s.influencer());
        assertTrue(s.sixDegreesOfSeparation());


    }

    @Test
    @DisplayName("5")
    void lg_mutual() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-lg.json");


        Set<String> expectedMutual = Set.of("Larry");

        assertTrue(expectedMutual.equals(s.mutualFriends("Alisha", "Nate")));
    }

    @Test
    @DisplayName("5")
    void lg_ladder() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-lg.json");


        List<String> expectedLadder = Arrays.asList("Alisha", "Larry", "Nate", "Aria", "Stanley");

        assertTrue(expectedLadder.equals(s.socialLadder("Alisha", "Stanley")));
    }

    @Test
    @DisplayName("5")
    void lg_glue() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-lg.json");

        Set<String> expectedKnow = Set.of("Thomas", "Alisha", "Samuel", "Kendra", "Aria", "Cameron");

        assertEquals("Nate", s.glue(expectedKnow));
    }

    @Test
    @DisplayName("5")
    void lg_average() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-lg.json");

        assertEquals(3, s.averageFriendsPerPerson());
    }

    @Test
    @DisplayName("5")
    void lg_butterfly() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-lg.json");

        assertEquals("Alisha", s.socialButterfly());
    }

    @Test
    @DisplayName("5")
    void lg_influencer() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-lg.json");

        assertEquals("Margot", s.influencer());
    }

    @Test
    @DisplayName("5")
    void lg_sixDegrees() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-lg.json");

        assertFalse(s.sixDegreesOfSeparation());
    }

    @Test
    @DisplayName("5")
    void lg_haveSeenMeme() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-lg.json");

        Set<String> expectedSeen = Set.of("Nate", "Larry", "Carol");

        assertTrue(expectedSeen.equals(s.haveSeenMeme("Carol", 2)));
    }

    @Test
    @DisplayName("5")
    void lg_youMayKnow() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-lg.json");


        Set<String> expectedKnow = Set.of("Thomas", "Alisha", "Samuel", "Kendra", "Aria", "Cameron");

        assertTrue(expectedKnow.equals(s.youMayKnow("Carol")));
    }


    @Test
    @DisplayName("5")
    void md_youMayKnow() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-md.json");


        Set<String> expectedKnow = Set.of("Mel", "Riley");

        assertTrue(expectedKnow.equals(s.youMayKnow("Bailey")));

    }
    @Test
    @DisplayName("5")
    void md_Ladder() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-md.json");

        List<String> expectedLadder = Arrays.asList("Edward", "Alex", "Jess", "Riley", "Daniel");

        assertTrue(expectedLadder.equals(s.socialLadder("Edward", "Daniel")));

    }
    @Test
    @DisplayName("5")
    void md_glue() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-md.json");


        Set<String> expectedSeen = Set.of("Alex", "Addison", "Jess", "Edward", "Bailey");

        assertEquals("", s.glue(expectedSeen));

    }

    @Test
    @DisplayName("5")
    void md_average() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-md.json");

        assertEquals(3, s.averageFriendsPerPerson());
    }

    @Test
    @DisplayName("5")
    void md_Bufferfly() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-md.json");


        assertEquals("Addison", s.socialButterfly());
    }
    @Test
    @DisplayName("5")
    void md_influencer() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-md.json");

        assertEquals("Alex", s.influencer());
    }
    @Test
    @DisplayName("5")
    void md_sixDegree() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-md.json");


        assertTrue(s.sixDegreesOfSeparation());
    }

    @Test
    @DisplayName("5")
    void md_mutualFriends() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-md.json");

        Set<String> expectedMutual = Set.of("Alex", "Bailey");

        assertTrue(expectedMutual.equals(s.mutualFriends("Edward", "Jess")));
    }

    @Test
    @DisplayName("5")
    void md_haveSeenMeme() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-md.json");

        Set<String> expectedSeen = Set.of("Alex", "Addison", "Jess", "Edward", "Bailey");

        assertTrue(expectedSeen.equals(s.haveSeenMeme("Bailey", 2)));
    }


    @Test
    @DisplayName("5")
    void sm_youMayKnow() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-sm.json");

        Set<String> expectedKnow = Set.of("Aaron");

        assertTrue(expectedKnow.equals(s.youMayKnow("Scott")));

    }

    @Test
    @DisplayName("5")
    void sm_socialLadder() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-sm.json");
        Set<String> expectedKnow = Set.of("Aaron");

        assertTrue(expectedKnow.equals(s.youMayKnow("Scott")));

    }

    @Test
    @DisplayName("5")
    void sm_glue() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-sm.json");

        Set<String> expectedSeen = Set.of("Malika", "Lilly", "Scott");

        assertEquals("Scott", s.glue(expectedSeen));

    }

    @Test
    @DisplayName("5")
    void sm_average() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-sm.json");

        assertEquals(2, s.averageFriendsPerPerson());
    }

    @Test
    @DisplayName("5")
    void sm_bufferfly() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-sm.json");

        assertEquals("Lilly", s.socialButterfly());
    }

    @Test
    @DisplayName("5")
    void sm_influencer() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-sm.json");

        assertEquals("Lilly", s.influencer());
    }
    @Test
    @DisplayName("5")
    void sm_sixDegrees() {
        SocialNetwork s = new SocialNetwork("src/main/java/social-network-sm.json");

        assertTrue(s.sixDegreesOfSeparation());
    }
}