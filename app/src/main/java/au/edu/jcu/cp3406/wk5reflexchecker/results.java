package au.edu.jcu.cp3406.wk5reflexchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class results extends AppCompatActivity {
    public static int SETTINGS_REQUEST = 222;
    String time = "00:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView timeDisplay = findViewById(R.id.finalTime);
        time = Objects.requireNonNull(getIntent().getExtras()).getString("time");
        timeDisplay.setText(time);
    }

    public void restart(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
