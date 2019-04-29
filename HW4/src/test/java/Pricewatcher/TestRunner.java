package src.test.java.Pricewatcher;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/* Automate tests */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AppTest.class);

        for(Failure failure: result.getFailures()){
            System.out.println("Error: " + failure.toString());
        }
        System.out.println("Success: " + result.wasSuccessful());
    }

}
