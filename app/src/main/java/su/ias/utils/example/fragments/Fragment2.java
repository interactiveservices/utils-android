package su.ias.utils.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import su.ias.utils.example.R;
import su.ias.utils.fragment.FragmentBuilder;

/**
 * Created on 10/30/17.
 */

public class Fragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            TextView centerText = (TextView) view.findViewById(R.id.tv_center);
            centerText.setText(getArguments().getString("title", this.getClass().getSimpleName()));
        }

        view.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FragmentBuilder(R.id.container, new Fragment4()).addToBackStack(true)
                        .replace(getActivity().getSupportFragmentManager());
            }
        });
    }
}
