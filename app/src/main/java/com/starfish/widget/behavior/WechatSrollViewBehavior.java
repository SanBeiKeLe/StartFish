package com.starfish.widget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.starfish.utils.Logger;


public class WechatSrollViewBehavior extends CoordinatorLayout.Behavior {


    private  int mHeaderHeight;

//    private HeaderView mHeaderView;

    public WechatSrollViewBehavior() {
    }

    public WechatSrollViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        /*if(dependency instanceof HeaderView){
            mHeaderView = (HeaderView) dependency;
            return true;
        }*/
        return false;

    }


    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {

        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        mHeaderHeight = dependency.getHeight();
        int top = (int) dependency.getY();
        int left = (int) dependency.getX();
        child.setX(left);
        child.setY(top + dependency.getHeight());
        return true;
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Logger.d("onNestedPreScroll dy = %s",dy);



       // int moveTo = (int) (mHeaderView.getY() - dy);


        if(dy > 0 ){ // 向上滑动


        }else{

        }


    }



}
