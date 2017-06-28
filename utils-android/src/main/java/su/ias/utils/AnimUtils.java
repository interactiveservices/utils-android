package su.ias.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;

import java.io.Serializable;

/**
 * класс с анимациями
 */

public final class AnimUtils {

    public AnimUtils() {
        throw new AssertionError();
    }

    /**
     * Анимация плавного перемещения view слева направо
     *
     * @param context  контекст
     * @param view     перемещаемое view
     * @param duration длительность анимации в миллисекундах
     */
    public static void leftToRight(final Context context, final View view, final long duration) {
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left,
                                       int top,
                                       int right,
                                       int bottom,
                                       int oldLeft,
                                       int oldTop,
                                       int oldRight,
                                       int oldBottom) {
                v.removeOnLayoutChangeListener(this);

                Animation anim = android.view.animation.AnimationUtils.loadAnimation(context,
                                                                                     R.anim.slide_in_left);
                anim.setDuration(duration);
                v.startAnimation(anim);

            }
        });
    }

    /**
     * Анимация плавного перемещения view справа налево
     *
     * @param context  контекст
     * @param view     перемещаемое view
     * @param duration длительность анимации в миллисекундах
     */
    public static void rightToLeft(final Context context,
                                   final View view,
                                   final Dismissible.OnDismissedListener listener,
                                   final long duration) {
        Animation anim =
                android.view.animation.AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                listener.onDismissed();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    /**
     * Анимация "кругового" появления view
     *
     * @param context        контекст
     * @param view           появляемая view
     * @param revealSettings настройки анимации
     * @param startColor     начальный цвет фона view
     * @param endColor       конечный цвет фона view
     */
    public static void circularReveal(final Context context,
                                      final View view,
                                      final RevealAnimationSetting revealSettings,
                                      final int startColor,
                                      final int endColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v,
                                           int left,
                                           int top,
                                           int right,
                                           int bottom,
                                           int oldLeft,
                                           int oldTop,
                                           int oldRight,
                                           int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    int cx = revealSettings.getCenterX();
                    int cy = revealSettings.getCenterY();
                    int width = revealSettings.getWidth();
                    int height = revealSettings.getHeight();
                    int duration = context.getResources()
                            .getInteger(android.R.integer.config_mediumAnimTime);

                    //Simply use the diagonal of the view
                    float finalRadius = (float) Math.sqrt(width * width + height * height);
                    Animator anim =
                            ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius)
                                    .setDuration(duration);
                    anim.setInterpolator(new FastOutSlowInInterpolator());
                    anim.start();
                    recolorBackground(view, startColor, endColor, duration);
                }
            });
        }
    }

    public static void hideCircularReveal(final Context context,
                                          final View view,
                                          final RevealAnimationSetting revealSettings,
                                          final int startColor,
                                          final int endColor,
                                          final Dismissible.OnDismissedListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = revealSettings.getCenterX();
            int cy = revealSettings.getCenterY();
            int width = revealSettings.getWidth();
            int height = revealSettings.getHeight();
            int duration =
                    context.getResources().getInteger(android.R.integer.config_mediumAnimTime);

            float initRadius = (float) Math.sqrt(width * width + height * height);
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initRadius, 0);
            anim.setDuration(duration);
            anim.setInterpolator(new FastOutSlowInInterpolator());
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    listener.onDismissed();
                }
            });
            anim.start();
            recolorBackground(view, startColor, endColor, duration);
        } else {
            listener.onDismissed();
        }
    }

    public static void recolorBackground(final View view,
                                         final int startColor,
                                         final int endColor,
                                         final int duration) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(startColor, endColor);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });
        anim.setDuration(duration);
        anim.start();
    }

    /**
     * развернуть элемент
     *
     * @param v - тот кого мы будем разворачивать
     */
    public static void expand(final View v) {
        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1 ?
                        WindowManager.LayoutParams.WRAP_CONTENT :
                        (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext()
                .getResources()
                .getDisplayMetrics().density));
        v.startAnimation(a);
    }

    /**
     * свернуть элемент
     *
     * @param v - элемент который будем сворачивать
     */
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height =
                            initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext()
                .getResources()
                .getDisplayMetrics().density));
        v.startAnimation(a);
    }

    /**
     * анимация вращения
     *
     * @param v      - что вертим
     * @param start  - начиная с какого градуса
     * @param finish - заканчивая каким градусом
     */
    public static void rotate(final View v,
                              final float start,
                              final float finish,
                              final long duration) {
        RotateAnimation rotateAnimation = new RotateAnimation(start,
                                                              finish,
                                                              RotateAnimation.RELATIVE_TO_SELF,
                                                              0.5f,
                                                              RotateAnimation.RELATIVE_TO_SELF,
                                                              0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(duration);
        v.startAnimation(rotateAnimation);
    }

    /**
     * анимация прозрачнности
     */
    public static void alpha(final View v, float fromAlpha, float toAlpha, long duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    /**
     * Анимация прозрачного перехода между двумя view
     *
     * @param inView   view, которая будет показана после завершения анимации
     * @param outView  view, которая будет исчезать
     * @param duration длительность анимации
     */
    public static void crossfade(final View inView, final View outView, final long duration) {
        inView.setAlpha(0f);
        inView.setVisibility(View.VISIBLE);

        inView.animate().alpha(1f).setDuration(duration).setListener(null);

        outView.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        outView.setVisibility(View.GONE);
                    }
                });
    }

    public interface Dismissible {
        void dismiss(OnDismissedListener listener);

        interface OnDismissedListener {
            void onDismissed();
        }
    }

    public static class RevealAnimationSetting implements Serializable {

        private int centerX;
        private int centerY;
        private int width;
        private int height;

        public RevealAnimationSetting(int centerX, int centerY, int width, int height) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.width = width;
            this.height = height;
        }

        public int getCenterX() {
            return centerX;
        }

        public int getCenterY() {
            return centerY;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

    }

}
