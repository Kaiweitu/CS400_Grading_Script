import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;


@Timeout(10)
class UnitTestRunner {

    private static SocialNetwork networkMd;
    private static SocialNetwork networkLg;
    private static SocialNetwork networkEmpty;


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

    @BeforeAll
    public static void setUpClass() throws Exception {
        networkMd = new SocialNetwork("src/main/java/social-network-md.json");
        networkLg = new SocialNetwork("src/main/java/social-network-lg.json");
        networkEmpty = new SocialNetwork("src/main/java/social-network-empty.json");
    }

    @Test
    @DisplayName("4")
    public final void test00_averageFriendsPerPersonMd() {
        assertEquals(3, networkMd.averageFriendsPerPerson());
    }

    @Test
    @DisplayName("4")
    public final void test01_averageFriendsPerPersonLg() {
        assertEquals(3, networkLg.averageFriendsPerPerson());

    }

    @Test
    @DisplayName("4")
    public final void test02_averageFriendsPerPersonEdge() {
        assertEquals(0, networkEmpty.averageFriendsPerPerson());
    }

    @Test
    @DisplayName("4")
    public final void test03_mutualFriendsMd() {
        String expectedArr[] = {"Alex", "Bailey"};
        Set<String> expectedSet = new HashSet<>(Arrays.asList(expectedArr));
        assertEquals(expectedSet, networkMd.mutualFriends("Edward", "Addison"));
    }

    @Test
    @DisplayName("4")
    public final void test04_mutualFriendsLg() {
        String expectedArr[] = {"Cole", "Gabby"};
        Set<String> expectedSet = new HashSet<>(Arrays.asList(expectedArr));
        assertEquals(expectedSet, networkLg.mutualFriends("Ben", "Avery"));
    }

    @Test
    @DisplayName("4")
    public final void test05_mutualFriendsEdge() {
        assertEquals(new HashSet<>(), networkLg.mutualFriends("SomePerson", "SomeOtherPerson"));
    }

    @Test
    @DisplayName("4")
    public final void test06_socialButterflyMd() {
        assertEquals("Addison", networkMd.socialButterfly());
    }

    @Test
    @DisplayName("4")
    public final void test07_socialButterflyLg() {
        assertEquals("Alisha", networkLg.socialButterfly());
    }

    @Test
    @DisplayName("4")
    public final void test08_socialButterflyEdge() {
        assertEquals("", networkEmpty.socialButterfly());
    }

    @Test
    @DisplayName("4")
    public final void test09_influencerMd() {
        assertEquals("Alex", networkMd.influencer());
    }

    @Test
    @DisplayName("4")
    public final void test10_influencerLg() {
        assertEquals("Margot", networkLg.influencer());
    }

    @Test
    @DisplayName("4")
    public final void test11_influencerEdge() {
        assertEquals("", networkEmpty.influencer());
    }

    @Test
    @DisplayName("4")
    public final void test12_haveSeenMemeMd() {
        String expectedArr[] = {"Alex", "Addison", "Bailey", "Daniel", "Edward", "Jess", "Riley"};
        Set<String> expectedSet = new HashSet<>(Arrays.asList(expectedArr));
        assertEquals(expectedSet, networkMd.haveSeenMeme("Jess", 3));
    }

    @Test
    @DisplayName("4")
    public final void test13_haveSeenMemeLg() {
        String expectedArr[] = {"Alexis", "Alisha", "Avery", "Ben", "Carly", "Cole", "Cory", "Dana", "Drew",
                "Esther", "Gabby", "Jennifer", "Kerry", "Larry", "Lillian", "Margot", "Mark", "Megan", "Nate",
                "Samuel", "Will", "Xavier"};
        Set<String> expectedSet = new HashSet<>(Arrays.asList(expectedArr));
        assertEquals(expectedSet, networkLg.haveSeenMeme("Margot", 3));
    }

    @Test
    @DisplayName("4")
    public final void test14_haveSeenMemeEdge() {
        assertEquals(new HashSet<String>(), networkEmpty.haveSeenMeme("Margot", -3));
    }

    @Test
    @DisplayName("4")
    public final void test15_youMayKnowMd() {
        String expectedArr[] = {"Mel", "Riley"};
        Set<String> expectedSet = new HashSet<>(Arrays.asList(expectedArr));
        assertEquals(expectedSet, networkMd.youMayKnow("Alex"));
    }

    @Test
    @DisplayName("4")
    public final void test16_youMayKnowLg() {
        String expectedArr[] = {"Avery", "Cole", "Gabby", "Margot", "Will"};
        Set<String> expectedSet = new HashSet<>(Arrays.asList(expectedArr));
        assertEquals(expectedSet, networkLg.youMayKnow("Lillian"));
    }

    @Test
    @DisplayName("4")
    public final void test17_youMayKnowEdge() {
        assertEquals(new HashSet<String>(), networkEmpty.youMayKnow("SomePerson"));
    }

    @Test
    @DisplayName("4")
    public final void test18_isFriendGroupMd() {
        String arr[] = {"Addison", "Alex", "Bailey", "Jess"};
        Set<String> set = new HashSet<>(Arrays.asList(arr));
        assertEquals(false, networkMd.isFriendGroup(set));
    }

    @Test
    @DisplayName("4")
    public final void test19_isFriendGroupLg() {
        String arr[] = {"Alexis", "Carly", "Cory", "Drew", "Megan"};
        Set<String> set = new HashSet<>(Arrays.asList(arr));
        assertEquals(true, networkLg.isFriendGroup(set));

    }

    @Test
    @DisplayName("4")
    public final void test20_isFriendGroupEdge() {
            String arr[] = {"Cory"};
            Set<String> set = new HashSet<>(Arrays.asList(arr));
            assertEquals(false, networkLg.isFriendGroup(set));
    }

    @Test
    @DisplayName("4")
    public final void test21_sixDegreesOfSeparationMd() {
            assertEquals(true, networkMd.sixDegreesOfSeparation());
    }

    @Test
    @DisplayName("4")
    public final void test22_sixDegreesOfSeparationLg() {
            assertEquals(false, networkLg.sixDegreesOfSeparation());

    }

    @Test
    @DisplayName("4")
    public final void test23_sixDegreesOfSeparationEdge() {
            assertEquals(true, networkEmpty.sixDegreesOfSeparation());

    }

    @Test
    @DisplayName("4")
    public final void test24_socialLadderMd() {
        String expectedArr[] = {"Edward", "Alex", "Jess", "Riley"};
        List<String> expectedList = new ArrayList<String>(Arrays.asList(expectedArr));
        assertEquals(expectedList, networkMd.socialLadder("Edward", "Riley"));

    }

    @Test
    @DisplayName("4")
    public final void test25_socialLadderLg() {
        String expectedArr[] = {"Kerry", "Alisha", "Margot", "Ben"};
        List<String> expectedList = new ArrayList<String>(Arrays.asList(expectedArr));
        assertEquals(expectedList, networkLg.socialLadder("Kerry", "Ben"));
    }

    @Test
    @DisplayName("4")
    public final void test26_socialLadderEdge() {
        assertEquals(new ArrayList<String>(), networkLg.socialLadder(null, null));
    }

    @Test
    @DisplayName("4")
    public final void test27_glueMd() {
        String arr[] = {"Alex", "Bailey", "Jess", "Riley"};
        Set<String> set = new HashSet<>(Arrays.asList(arr));
        assertEquals("Jess", networkMd.glue(set));

    }

    @Test
    @DisplayName("4")
    public final void test28_glueLg() {
        String arr[] = {"Alisha", "Ashley", "Esther", "Gabe", "Jennifer", "Larry", "Margot", "Mason"};
        Set<String> set = new HashSet<>(Arrays.asList(arr));
        assertEquals("Alisha", networkLg.glue(set));
    }

    @Test
    @DisplayName("4")
    public final void test29_glueEdge() {
        String arr[] = {"Avery", "Cole", "Gabby", "Jill"};
        Set<String> set = new HashSet<>(Arrays.asList(arr));
        assertEquals("", networkLg.glue(set));
    }


}