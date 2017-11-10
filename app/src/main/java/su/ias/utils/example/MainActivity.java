package su.ias.utils.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import su.ias.utils.AnimUtils;
import su.ias.utils.GrammarUtils;
import su.ias.utils.IntentUtils;
import su.ias.utils.MoneyUtils;
import su.ias.utils.UiUtils;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://be-interactive.ru/mobile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UiUtils.setSoftInputMode(this, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        final TextView txtDescription = findViewById(R.id.txt_description);
        AnimUtils.alpha(txtDescription, 0f, 1f, 2000);

        final Button btnWeb = findViewById(R.id.btn_web);
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.openUrl(MainActivity.this, URL);
            }
        });

        final Button btnDial = findViewById(R.id.btn_dial);
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.dial(MainActivity.this, "+7 (495) 744 55 00");
            }
        });

        final Button btnSettings = findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.openAppDetailsActivity(MainActivity.this);
            }
        });

        final TextView txtCost = findViewById(R.id.txt_cost);
        MoneyUtils.inflateTextViewWithCost(txtCost, -1500000d);
        AnimUtils.rotate(txtCost, 0, 360, 2000);

        final TextView txtCases = findViewById(R.id.txt_cases);
        TextViewCompat.setTextAppearance(txtCases, UiUtils.fetchTextAppearance(this));
        txtCases.setTextColor(UiUtils.fetchPrimaryColor(this));
        txtCases.setText(GrammarUtils.getMonthCaseByNumber(this, 27));
        AnimUtils.leftToRight(this, txtCases, 2000);

        final Button shake = findViewById(R.id.shake);
        shake.setBackgroundColor(UiUtils.fetchAccentColor(this));
        shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.shakeAnimate(MainActivity.this, shake);
            }
        });

        final EditText editTextFocus = findViewById(R.id.edit_text_focus);
        UiUtils.showKeyboard(this, editTextFocus);
    }

    public void openFragmentUtils(View view) {
        startActivity(new Intent(this, FragmentActivity.class));
    }
}