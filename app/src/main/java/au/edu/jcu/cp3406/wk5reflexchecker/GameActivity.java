package au.edu.jcu.cp3406.wk5reflexchecker;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setupDescription(R.id.task1, R.array.task1_descriptions);
        setupDescription(R.id.task2, R.array.task2_descriptions);

        for (int i = 0; i < 5; i++) {
            addImage();
            if (random.nextBoolean()) { addCheckboxes(R.array.drinks); }
            else { addCheckboxes(R.array.fruits); }
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

        for (int i = 0; i < count; i++) {
            View singleBox = checkboxes.getChildAt(i);
            CheckBox box = singleBox.findViewById(singleBox.getId());
            box.setText(newText[random.nextInt(newText.length)]);
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

    public void checkTasks(View view) {
        timer.stop();
        handler.removeCallbacks(runnable);
    }


}
