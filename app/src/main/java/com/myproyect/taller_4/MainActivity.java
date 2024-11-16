package com.myproyect.taller_4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView greetingTextView = findViewById(R.id.greetingTextView);
        Button mainScreenButton = findViewById(R.id.mainScreenButton);

        setGreetingMessage(greetingTextView);

        mainScreenButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainScreenActivity.class);
            startActivity(intent);
        });
    }

    private void setGreetingMessage(TextView textView) {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting;

        if (hour >= 6 && hour < 12) {
            greeting = "Buenos días";
        } else if (hour >= 12 && hour < 18) {
            greeting = "Buenas tardes";
        } else {
            greeting = "Buenas noches";
        }

        textView.setText(greeting);
    }
}
