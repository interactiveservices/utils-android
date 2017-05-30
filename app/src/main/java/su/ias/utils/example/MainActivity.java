package su.ias.utils.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import su.ias.utils.AnimUtils;
import su.ias.utils.GrammarUtils;
import su.ias.utils.IntentUtils;
import su.ias.utils.MoneyUtils;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://be-interactive.ru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtDescription = (TextView) findViewById(R.id.txt_description);
        AnimUtils.alpha(txtDescription, 0f, 1f, 2000);

        final Button btnWeb = (Button) findViewById(R.id.btn_web);
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.openWebPage(MainActivity.this, URL);
            }
        });

        final Button btnDial = (Button) findViewById(R.id.btn_dial);
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.dial(MainActivity.this, "+7 (495) 744 5500");
            }
        });

        final TextView txtCost = (TextView) findViewById(R.id.txt_cost);
        MoneyUtils.inflateTextViewWithCost(txtCost, 1500000d);
        AnimUtils.rotate(txtCost, 0, 360, 2000);

        final TextView txtCases = (TextView) findViewById(R.id.txt_cases);
        txtCases.setText(GrammarUtils.getMonthCaseByNumber(this, 27));
        AnimUtils.leftToRight(this, txtCases, 2000);
    }
}