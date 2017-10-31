package su.ias.utils.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import su.ias.utils.example.fragments.Fragment1;
import su.ias.utils.fragment.FragmentBuilder;
import su.ias.utils.fragment.FragmentUtils;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new FragmentBuilder(R.id.container, new Fragment1())
                .addToBackStack(false)
                .replace(getSupportFragmentManager());
    }

    @Override
    public void onBackPressed() {
        if (!FragmentUtils.onBackPressed(getSupportFragmentManager())){
            super.onBackPressed();
        }
    }
}
