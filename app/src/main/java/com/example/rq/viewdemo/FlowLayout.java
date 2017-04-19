package com.example.rq.viewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();

        int lineHeight = 0 ;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;

        for (int i = 0; i < getChildCount() ;i++) {
            View child = getChildAt(i);

            child.measure(getChildMeasureSpec(widthMeasureSpec,getPaddingLeft() + getPaddingRight(),child.getLayoutParams().width)
            ,getChildMeasureSpec(heightMeasureSpec,getPaddingTop() + getPaddingBottom(),child.getLayoutParams().height));

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            lineHeight = Math.max(lineHeight,childHeight);
            //判断是否换行
            if (childWidth + childLeft + getPaddingRight() > width) {
                //换行后左边距重新初始化
                childLeft = getPaddingLeft();
                //子View高度换行后要累加
                childTop  += lineHeight;
                //行高初始化为换行后的子View高度
                lineHeight = childHeight;
            }
            //累加每个子View的宽度,用于判断换行
            childLeft+=childWidth;

        }
        height = childTop + lineHeight +getPaddingBottom();
        setMeasuredDimension(width,resolveSize(height,heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();

        int lineHeight = 0 ;

        for (int i = 0; i < getChildCount() ;i++) {
            View child = getChildAt(i);

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            lineHeight = Math.max(lineHeight,childHeight);

            if (childWidth + childLeft + getPaddingRight() > getWidth()) {
                childLeft = getPaddingLeft();
                childTop  += lineHeight;
                lineHeight = childHeight;
            }
            child.layout(childLeft,childTop,childLeft+childWidth,childTop + childHeight);
            childLeft+=childWidth;
        }
    }


}
