package au.edu.jcu.cp3406.wk5reflexchecker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

    public static int SETTINGS_REQUEST = 111;
    private Random random = new Random();
    private static final int[] drawables = {
            R.drawable.baseline_bug_report_black_48,
            R.drawable.baseline_android_black_48,
            R.drawable.baseline_extension_black_48,
            R.drawable.baseline_thumb_up_black_48
    };

    Handler handler = new Handler();
    Runnable runnable;
    final Stopwatch timer = new Stopwatch();

    List<String> drinksOrFruits = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setupDescription(R.id.task1, R.array.task1_descriptions);
        setupDescription(R.id.task2, R.array.task2_descriptions);

        for (int i = 0; i < 5; i++) {
            addImage();
            if (random.nextBoolean()) {
                addCheckboxes(R.array.drinks);
                drinksOrFruits.add("drinks");
            }
            else {
                addCheckboxes(R.array.fruits);
                drinksOrFruits.add("fruits");
            }
        }
        addButton();
        startTimer();
    }

    private void setupDescription(int taskID, int arrayID) {
        TextView task = findViewById(taskID);
        String[] descriptions = getResources().getStringArray(arrayID);

        int i = random.nextInt(descriptions.length);
        task.setText(descriptions[i]);
    }

    private void addImage() {
        ViewGroup gameRows = findViewById(R.id.game_rows);
        getLayoutInflater().inflate(R.layout.image, gameRows);

        View lastChild = gameRows.getChildAt(gameRows.getChildCount() - 1);
        ImageView image = lastChild.findViewById(R.id.image);

        int index = random.nextInt(drawables.length);
        image.setImageDrawable(getResources().getDrawable(drawables[index]));
    }

    private void addCheckboxes(int arrayID) {
        String[] newText = getResources().getStringArray(arrayID);

        ViewGroup gameRows = findViewById(R.id.game_rows);
        getLayoutInflater().inflate(R.layout.checkboxes, gameRows);

        View lastChild = gameRows.getChildAt(gameRows.getChildCount() - 1);
        TableRow checkboxes = lastChild.findViewById(R.id.checkboxes);

        int count = checkboxes.getChildCount();
        Set<Integer> randNums = new HashSet<>();

        while (randNums.size() != count) {
            randNums.add(random.nextInt(newText.length));
        }
        Iterator<Integer> itr = randNums.iterator();

        for (int i = 0; i < count; i++) {
            View singleBox = checkboxes.getChildAt(i);
            CheckBox box = singleBox.findViewById(singleBox.getId());
            box.setText(newText[itr.next()]);
            box.setChecked(random.nextBoolean());
        }
    }

    private void addButton() {
        ViewGroup gameRows = findViewById(R.id.game_rows);
        getLayoutInflater().inflate(R.layout.button, gameRows);
    }

    private void startTimer() {
        final TextView timerView = findViewById(R.id.timer);
        timer.start();

        handler.post(runnable = new Runnable() {
            @Override
            public void run() {
                if (timer.isRunning()) {
                    timer.tick();
                    timerView.setText(timer.toString());
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void checkAnswers(View view) {
        boolean[][] usersChoice;
        Context context = getApplicationContext();
        String notDonemsg = "That's not correct!!\nKeep going";

        handler.removeCallbacks(runnable);
        usersChoice = getInput();
        if (checkTask(usersChoice)) {
            timer.stop();
            System.out.println("The task is complete!");
            Intent intent = new Intent(this, results.class);
            intent.putExtra("time", timer.toString());
            startActivityForResult(intent, results.SETTINGS_REQUEST);
        } else {
            System.out.println("The task is IN-complete!");
            Toast notDone = Toast.makeText(context, notDonemsg, Toast.LENGTH_LONG);
            notDone.setGravity(Gravity.CENTER, 0, 0);
            notDone.show();
        }
    }

    boolean[][] getInput() {
        ViewGroup gameRows = findViewById(R.id.game_rows);
        int numRows = gameRows.getChildCount();
        boolean[][] selected = new boolean[5][3];
        int index = 0;


        for (int i = 0; i < numRows; i++) {
            View boxesRow = gameRows.getChildAt(i);
            if (boxesRow instanceof TableRow) {
                TableRow checkboxes = boxesRow.findViewById(R.id.checkboxes);
                System.out.println("Row" + i + "-------------------------------------------------------");
                for (int j = 0; j < checkboxes.getChildCount(); j++) {
                    View singleBox = checkboxes.getChildAt(j);
                    CheckBox box = singleBox.findViewById(singleBox.getId());
                    selected[index][j] = box.isChecked();
                }
                index++;
            }
        }

        for (int i=0; i<5; i++) {
            System.out.println("Row" + i + "-------------------------------------------------------");
            for (int j=0; j<3; j++) {
                System.out.println("==" + selected[i][j] + "-------------------------------------------------------");
            }
        }

        return selected;
    }

    boolean checkTask(boolean[][] userInput) {
        TextView task1View = findViewById(R.id.task1);
        String task1 = task1View.getText().toString();
        TextView task2View = findViewById(R.id.task2);
        String task2 = task2View.getText().toString();
        boolean correct = false;
        int dCount = 0;
        int fCount = 0;

        outerLoop:
        for (int i = 0; i < 5; i++) {
            if (drinksOrFruits.get(i).equals("drinks")) {
                switch (task1) {
                    case "Select all drinks":
                        for (int j = 0; j < 3; j++) {
                            if (!userInput[i][j]) {
                                correct = false;
                                break outerLoop;
                            }
                            correct = true;
                        }
                        break;
                    case "Deselect all drinks":
                        for (int j = 0; j < 3; j++) {
                            if (userInput[i][j]) {
                                correct = false;
                                break;
                            }
                            correct = true;
                        }
                        break;
                    case "Select one drink":
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
                    case "Select all fruits":
                        for (int j = 0; j < 3; j++) {
                            if (!userInput[i][j]) {
                                correct = false;
                                break outerLoop;
                            }
                            correct = true;
                        }
                        break;
                    case "Deselect all fruits":
                        for (int j = 0; j < 3; j++) {
                            if (userInput[i][j]) {
                                correct = false;
                                break outerLoop;
                            }
                            correct = true;
                        }
                        break;
                    case "Select one fruit":
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

        return correct;
    }
}
