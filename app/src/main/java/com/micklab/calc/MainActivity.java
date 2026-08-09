package com.micklab.calc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView display;
    Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Uncaught exception handler at the beginning of onCreate
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            runOnUiThread(() -> {
                if (display != null) {
                    display.setText(e.toString());
                }
            });
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.result_text_view);
        buttons = new Button[10]; // 0 to 9

        for (int i = 0; i < buttons.length; i++) {
            String id = "button_" + i;
            int resourceId = getResources().getIdentifier(id, "id", getPackageName());
            buttons[i] = findViewById(resourceId);
        }

        // Set click listeners for all buttons
        for (Button button : buttons) {
            button.setOnClickListener(v -> onClickButton(v));
        }

        // Explicitly set click listeners for btnClear and btnEquals
        findViewById(R.id.btnClear).setOnClickListener(v -> onClickButton(v));
        findViewById(R.id.btnEquals).setOnClickListener(v -> onClickButton(v));
    }

    private void onClickButton(View v) {
        String input = "";
        int result;

        for (int i = 0; i < buttons.length; i++) {
            int btnId = v.getId();
            if (btnId == R.id.btnClear) {
                input = "";
                display.setText("");
                break;
            } else if (btnId == R.id.btnEquals) {
                try {
                    result = evaluate(input);
                    display.setText("Result: " + result);
                } catch (Exception e) {
                    display.setText("Error");
                }
                break;
            }
        }
    }

    private int evaluate(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
