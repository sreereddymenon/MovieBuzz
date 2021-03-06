package com.sreesha.android.moviebuzz.Animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Sreesha on 28-01-2016.
 */
public class CustomAnimator {
    private static int counter = 0;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void createCircularRevealEffect(View view
            , int width
            , int height
            , int initialRadius
            , long duration) {
        int cx = width / 2;
        int cy = height / 2;


        int finalRadius = Math.max(width
                , height);
        android.animation.Animator anim =
                ViewAnimationUtils.createCircularReveal(
                        view
                        , cx
                        , cy
                        , initialRadius
                        , finalRadius
                ).setDuration(duration);

        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    public static void slideAnimRecyclerView(RecyclerView.ViewHolder viewHolder, boolean direction) {

        View holderItemView = viewHolder.itemView;
        holderItemView.setPivotX(viewHolder.itemView.getHeight());
        holderItemView.setPivotY(0);

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translateY = ObjectAnimator.ofFloat(holderItemView, "translationY", 600, 0);
        animatorSet.playTogether(translateY);
        animatorSet
                .setDuration(1500)
                .setInterpolator(new DecelerateInterpolator(1.1f));
        //animatorSet.start();
    }

    public static void playFadeAnimation(View view, boolean goingUP) {
        ObjectAnimator mAnimatorAlpha;
        ObjectAnimator mAnimatorTranslateY;
        ObjectAnimator mAnimatorTranslateX;
        AnimatorSet mAnimSet = new AnimatorSet();
        if (goingUP) {
            mAnimatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
            mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 1, 1); ;

        } else {
            mAnimatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
            mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 1, 1);

        }
        mAnimatorTranslateX
        = ObjectAnimator.ofFloat(view, "translationX"
                , -200, 0);
        mAnimatorTranslateX.setDuration(100);
        mAnimSet.playTogether(mAnimatorAlpha, mAnimatorTranslateY,mAnimatorTranslateX);
        mAnimSet.setDuration(700);
        mAnimSet.start();
    }
}
