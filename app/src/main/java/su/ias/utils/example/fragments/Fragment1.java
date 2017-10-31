package su.ias.utils.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import su.ias.utils.example.R;
import su.ias.utils.fragment.FragmentUtils;
import su.ias.utils.fragment.FragmentBuilder;
import su.ias.utils.fragment.OnBackClickListener;
import su.ias.utils.fragment.animation.SlideFromTop;
import su.ias.utils.fragment.animation.SlideLeftToRight;
import su.ias.utils.fragment.animation.SlideToTop;

public class Fragment1 extends Fragment implements OnBackClickListener {

    public Fragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_slide).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new FragmentBuilder(R.id.container,
                                    new Fragment2()).setAnimation(new SlideLeftToRight())
                        .addToBackStack(true)
                        .replace(getActivity().getSupportFragmentManager());
            }
        });

        view.findViewById(R.id.btn_to_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FragmentUtils.existFragment(getActivity().getSupportFragmentManager(),
                                                Fragment3.class)) {

                    removeTopFragment();
                } else {

                    new FragmentBuilder(R.id.container,
                                        new Fragment3()).setAnimation(new SlideFromTop())
                            .addToBackStack(false)
                            .add(getActivity().getSupportFragmentManager());
                }
            }
        });

        view.findViewById(R.id.btn_with_params).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FragmentBuilder(R.id.container,
                                    new Fragment2()).setAnimation(new SlideToTop())
                        .addStringArgument("title", "Param Title")
                        .addToBackStack(true)
                        .replace(getActivity().getSupportFragmentManager());
            }
        });
    }

    private void removeTopFragment(){
        FragmentUtils.remove(getActivity().getSupportFragmentManager(),
                             Fragment3.class);
    }

    @Override
    public boolean onBackClick() {
        if (FragmentUtils.existFragment(getActivity().getSupportFragmentManager(), Fragment3.class)){
            removeTopFragment();
            return true;
        }
        return false;
    }
}
