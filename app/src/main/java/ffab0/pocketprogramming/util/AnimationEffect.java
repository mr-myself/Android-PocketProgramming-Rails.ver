package ffab0.pocketprogramming.util;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ffab0.pocketprogramming.R;

public class AnimationEffect {

    static public void fadeIn(Activity activity, LinearLayout linearLayout) {
        final Animation pageAnimationFadeIn = AnimationUtils.loadAnimation(activity, R.anim.fadein);
        linearLayout.startAnimation(pageAnimationFadeIn);
    }

    static public void animatePropertyValuesHolderSample(ImageView target) {

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1.1f, 1.0f, 1.05f, 1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1.1f, 1.0f, 1.05f, 1.0f);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(target, scaleX, scaleY);
        objectAnimator.setDuration(1500);
        objectAnimator.start();
    }

    static public void clickAnimation(Button target) {
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.5f, 1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.5f, 1.0f);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(target, scaleX, scaleY);
        objectAnimator.setDuration(1500);
        objectAnimator.start();
    }
}
