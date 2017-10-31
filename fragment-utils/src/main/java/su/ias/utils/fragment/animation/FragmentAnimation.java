package su.ias.utils.fragment.animation;

import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;

/**
 * Created on 10/27/17.
 * create your animation use this class
 */

public class FragmentAnimation {

    private final @AnimatorRes
    @AnimRes
    int enter;

    @AnimatorRes
    private final @AnimRes
    int exit;

    @AnimatorRes
    private final @AnimRes
    int popEnter;

    @AnimatorRes
    private final @AnimRes
    int popExit;

    public FragmentAnimation(int enterAnimation, int exitAnimation) {
        this.enter = enterAnimation;
        this.exit = exitAnimation;
        popEnter = 0;
        popExit = 0;
    }

    public FragmentAnimation(int enterAnimation,
                             int exitAnimation,
                             int exitCurrentAnimation,
                             int enterCurrentAnimation) {
        this.enter = enterAnimation;
        this.exit = exitAnimation;
        this.popEnter = exitCurrentAnimation;
        this.popExit = enterCurrentAnimation;
    }

    public int getEnter() {
        return enter;
    }

    public int getExit() {
        return exit;
    }

    public int getPopEnter() {
        return popEnter;
    }

    public int getPopExit() {
        return popExit;
    }
}
