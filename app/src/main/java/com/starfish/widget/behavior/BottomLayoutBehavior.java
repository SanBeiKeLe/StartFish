package com.starfish.widget.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.starfish.utils.Logger;


public class BottomLayoutBehavior extends CoordinatorLayout.Behavior {

    private ObjectAnimator mObjectAnimator;


    public BottomLayoutBehavior(Context context){

    }


    public BottomLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {


        if(dy > 0 ){ // 手指上滑
            Logger.d("上滑 ");
            if(!getAnimator(child).isRunning() && child.getY() == coordinatorLayout.getHeight() - child.getHeight()){
                Logger.d("上滑 开始");
                mObjectAnimator.start();
            }
        }else if(dy < 0){ // 下滑
            Logger.d("下滑");
            if(!getAnimator(child).isRunning() &&  child.getY() == coordinatorLayout.getHeight()){
                Logger.d("下滑 reverse");
                mObjectAnimator.reverse();
            }
        }
    }

    private ObjectAnimator getAnimator(View child){
        if(mObjectAnimator == null){
            mObjectAnimator = ObjectAnimator.ofFloat(child, "Y",child.getY(),child.getY() + child.getHeight());
            mObjectAnimator.setDuration(320);

            mObjectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    Logger.d("动画暂停");
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                    Logger.d("动画 repeat");
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    Logger.d("动画 开始");
                }

                @Override
                public void onAnimationPause(Animator animation) {
                    super.onAnimationPause(animation);
                    Logger.d("动画 pause");
                }

                @Override
                public void onAnimationResume(Animator animation) {
                    super.onAnimationResume(animation);
                    Logger.d("动画 resume");
                }
            });
        }

        return mObjectAnimator;
    }
}
