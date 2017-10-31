package su.ias.utils.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.Collections;
import java.util.List;

/**
 * Created on 10/27/17.
 */

public final class FragmentUtils {

    public FragmentUtils() {
        throw new UnsupportedOperationException("oh no... instant ");
    }

    /**
     * return Fragment list from FragmentManager
     *
     * @param fm fragment manager
     * @return fragment list
     */
    public static List<Fragment> getFragments(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.isEmpty()) {
            return Collections.emptyList();
        }
        return fragments;
    }

    /**
     * go back on backStack
     *
     * @param fm FragmentManager from SupportLibrary
     */
    public static void back(@NonNull final FragmentManager fm) {
        back(fm, true);
    }

    public static void back(@NonNull final FragmentManager fm, boolean immediately) {
        if (immediately) {
            fm.popBackStackImmediate();
        } else {
            fm.popBackStack();
        }
    }

    /**
     * return Fragment or null
     *
     * @param fm      fragment Manager
     * @param findClz fragment class
     * @return found fragment or null
     */
    @Nullable
    public static Fragment findFragment(@NonNull final FragmentManager fm,
                                        final Class<? extends Fragment> findClz) {
        return findFragment(fm, findClz.getSimpleName());
    }

    @Nullable
    public static Fragment findFragment(@NonNull final FragmentManager fm, @NonNull String tag) {
        return fm.findFragmentByTag(tag);
    }

    /**
     * check exist Fragment
     *
     * @param fm      FragmentManager
     * @param findClz fragment class
     * @return true if exist and not hidden
     */
    public static boolean existFragment(@NonNull final FragmentManager fm,
                                        final Class<? extends Fragment> findClz) {
        Fragment fragment = findFragment(fm, findClz);
        return fragment != null && !fragment.isHidden();
    }

    public static void hide(FragmentManager fm, Class<? extends Fragment> hideClz) {
        fm.beginTransaction().hide(findFragment(fm, hideClz)).commitAllowingStateLoss();
    }

    public static void show(FragmentManager fm, Class<? extends Fragment> showClz) {
        fm.beginTransaction().show(findFragment(fm, showClz)).commitAllowingStateLoss();
    }

    public static void remove(FragmentManager fm, Class<? extends Fragment> simpleName) {
        fm.beginTransaction().remove(findFragment(fm, simpleName)).commitAllowingStateLoss();
    }

    /**
     * clear back stack go to first stack element
     *
     * @param fm FragmentManager
     */
    public static void clearBackStack(@NonNull final FragmentManager fm) {
        while (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
        }
    }

    public static boolean onBackPressed(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        if (fragments == null || fragments.isEmpty()) {
            return false;
        }
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            //@formatter:off
            if (fragment != null &&
                    fragment.isResumed() &&
                    fragment.isVisible() &&
                    fragment.getUserVisibleHint() &&
                    fragment instanceof OnBackClickListener &&
                    ((OnBackClickListener) fragment)
                    .onBackClick())
            {
                return true;
            }
            //@formatter:on
        }
        return false;
    }
}
