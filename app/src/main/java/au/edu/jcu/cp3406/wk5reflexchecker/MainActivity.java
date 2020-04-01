package au.edu.jcu.cp3406.wk5reflexchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startTest(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        //intent.putExtra();
        startActivityForResult(intent, GameActivity.SETTINGS_REQUEST);
    }
}
