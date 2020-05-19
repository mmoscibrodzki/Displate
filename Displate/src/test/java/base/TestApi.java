package base;

public class TestApi {

    static String name;

    public static void newScenario(String scenarioName) {
        System.out.println("*************" + scenarioName + " started.");
        name = scenarioName;
    }

    public static void endScenario() {
        System.out.println(name + " ended.*************");
    }

}
