package su.ias.utils.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import su.ias.utils.fragment.animation.FragmentAnimation;

/**
 * Created on 10/27/17.
 */

public class FragmentBuilder {

    private Fragment fragment;
    private Bundle parameters;
    private boolean addToBackStack;
    private @IdRes
    int containerId;
    private List<View> sharedElement = new ArrayList<>();
    private FragmentAnimation animation;


    public FragmentBuilder(@IdRes int containerId, @NonNull Fragment fragment) {
        this.fragment = fragment;
        this.containerId = containerId;
    }

    private void checkBundle() {
        if (parameters == null) {
            parameters = new Bundle();
        }
    }

    public FragmentBuilder addToBackStack(boolean addToBackStack) {
        this.addToBackStack = addToBackStack;
        return this;
    }

    public FragmentBuilder addSharedElement(View view) {
        sharedElement.add(view);
        return this;
    }

    public FragmentBuilder addIntArgument(String key, int value) {
        checkBundle();
        parameters.putInt(key, value);
        return this;
    }

    public FragmentBuilder addStringArgument(String key, String value) {
        checkBundle();
        parameters.putString(key, value);
        return this;
    }

    public FragmentBuilder addDoubleArgument(String key, Double value) {
        checkBundle();
        parameters.putDouble(key, value);
        return this;
    }

    public FragmentBuilder addLongArgument(String key, Long value) {
        checkBundle();
        parameters.putLong(key, value);
        return this;
    }

    public FragmentBuilder addParcelableArgument(String key, Parcelable value) {
        checkBundle();
        parameters.putParcelable(key, value);
        return this;
    }

    public FragmentBuilder setArguments(Bundle args) {
        parameters = args;
        return this;
    }

    private FragmentTransaction createTransaction(@NonNull FragmentManager fragmentManager) {
        if (parameters != null && !parameters.isEmpty()) {
            fragment.setArguments(parameters);
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }

        if (!sharedElement.isEmpty() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            for (View element : sharedElement) {
                transaction.addSharedElement(element, element.getTransitionName());
            }

        }

        if (animation != null) {
            transaction.setCustomAnimations(animation.getEnter(),
                                            animation.getExit(),
                                            animation.getPopEnter(),
                                            animation.getPopExit());
        }

        return transaction;
    }

    public FragmentBuilder setAnimation(FragmentAnimation animation) {
        this.animation = animation;
        return this;
    }

    public void replace(@NonNull FragmentManager fragmentManager) {
        createTransaction(fragmentManager).replace(containerId,
                                                   fragment,
                                                   fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }

    public void add(@NonNull FragmentManager fragmentManager) {
        createTransaction(fragmentManager).add(containerId,
                                               fragment,
                                               fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }

}
