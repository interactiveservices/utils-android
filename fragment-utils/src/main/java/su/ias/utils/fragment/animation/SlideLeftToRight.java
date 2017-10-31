package su.ias.utils.fragment.animation;

import su.ias.utils.fragment.R;

/**
 * Created on 10/27/17.
 */

public class SlideLeftToRight extends FragmentAnimation {

    public SlideLeftToRight() {
        super(R.anim.fragment_slide_in_right,
              R.anim.fragment_slide_out_left,
              R.anim.fragment_slide_in_left,
              R.anim.fragment_slide_out_right);

    }
}
