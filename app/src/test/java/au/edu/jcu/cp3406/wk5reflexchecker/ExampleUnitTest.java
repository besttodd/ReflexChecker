package au.edu.jcu.cp3406.wk5reflexchecker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testChecking() {
        boolean correct = false;
        List<String> items = new ArrayList<String>();
        boolean[][] userInput = {
                {false, false, false},
                {false, false, false},
                {false, true, false},
                {false, false, false},
                {false, true, false}
        };

        items.add("drinks");
        items.add("fruits");
        items.add("drinks");
        items.add("fruits");
        items.add("fruits");

        String task1 = "Select one";
        String task2 = "Select one";
        int dCount = 0;
        int fCount = 0;

        outerLoop:
        for (int i = 0; i < 5; i++) {
             if (items.get(i).equals("drinks")) {
                switch (task1) {
                    case "Select all":
                        for (int j = 0; j < 3; j++) {
                            if (!userInput[i][j]) {
                                correct = false;
                                break outerLoop;
                            }
                            correct = true;
                        }
                        break;
                    case "Select none":
                        for (int j = 0; j < 3; j++) {
                            if (userInput[i][j]) {
                                correct = false;
                                break;
                            }
                            correct = true;
                        }
                        break;
                    case "Select one":
                        for (int j = 0; j < 3; j++) {
                        if (userInput[i][j]) {
                            dCount++;
                        }
                    }
                    if (dCount > 1) {
                        correct = false;
                        break outerLoop;
                    } else {
                        correct = true;
                    }
                    break;
                }
            } else {
                switch (task2) {
                    case "Select all":
                        for (int j = 0; j < 3; j++) {
                            if (!userInput[i][j]) {
                                correct = false;
                                break outerLoop;
                            }
                            correct = true;
                        }
                        break;
                    case "Select none":
                        for (int j = 0; j < 3; j++) {
                            if (userInput[i][j]) {
                                correct = false;
                                break outerLoop;
                            }
                            correct = true;
                        }
                        break;
                    case "Select one":
                        for (int j = 0; j < 3; j++) {
                            if (userInput[i][j]) {
                                fCount++;
                            }
                        }
                        if (fCount > 1) {
                            correct = false;
                            break outerLoop;
                        } else {
                            correct = true;
                        }
                        break;
                }
            }
        }

        if (task1.equals("Select one")) {
            if (dCount == 0) { correct = false; }
        }
        if (task2.equals("Select one")) {
            if (fCount == 0) { correct = false; }
        }
        if (correct) {
            System.out.println("The task is complete!");
        } else {
            System.out.println("The task is IN-complete!");
        }
    }

    @Test
    public void test2dLists() {
    }
}