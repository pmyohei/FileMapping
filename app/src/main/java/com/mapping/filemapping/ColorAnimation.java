package com.mapping.filemapping;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

/*
 * 色遷移アニメーション用メソッドを実装
 */
public class ColorAnimation {

    /*
     * アニメーション付きの色変更
     *   開始色から終了色に遷移するアニメーションを、指定されたビューに対して適用する。
     * 　 para1：Context
     * 　 para2：色変更対象ビュー
     * 　 para3：プロパティ文字列
     * 　 para4：開始色
     * 　 para5：終了色
     */
    static public void startTranceColorAnimation(Context context, Object object, String property, int srcColor, int dstColor ) {

        //アニメーション時間
        int duration = context.getResources().getInteger(R.integer.color_trance_animation_duration);

        //---------------------------------------
        // アニメーション付きで背景色を変更
        //---------------------------------------
        ValueAnimator tranceAnimator = ObjectAnimator.ofArgb(object, property, srcColor, dstColor);
        tranceAnimator.setDuration( duration );

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(tranceAnimator);
        animatorSet.start();
    }

    /*
     * アニメーション付きの色変更
     *   開始色から終了色に遷移するアニメーションを、指定されたビューに対して適用する。
     * 　 para1：Context
     * 　 para2：色変更対象ビュー
     * 　 para3：プロパティ文字列
     * 　 para4：開始色
     * 　 para5：終了色
     */
    static public void startTranceShadowColorAnimation( final Context context, final Object object, final float radius,
                                                        final int startColor, final int endColor) {
        //アニメーション時間
        int duration = context.getResources().getInteger(R.integer.color_trance_animation_duration);

        //---------------------------------------
        // アニメーション付きで背景色を変更
        //---------------------------------------
        ArgbEvaluator argb = new ArgbEvaluator();

        ValueAnimator animator = TimeAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //現在の進行度
                float fraction = valueAnimator.getAnimatedFraction();
                //設定色（変化中の色）
                int color = (int)argb.evaluate( fraction, startColor, endColor );
                //影レイヤーに適用
                ((Paint)object).setShadowLayer(radius, 0, 0, color);
            }
        });
        animator.start();
    }

    /*
     * アニメーション付きの色変更
     *   開始色から終了色に遷移するアニメーションを、指定されたビューに対して適用する。
     * 　 para1：Context
     * 　 para2：色変更対象ビュー
     * 　 para3：プロパティ文字列
     * 　 para4：開始色
     * 　 para5：終了色
     * 　 para6：グラデーション方向
     */
    static public void startTranceGradationColorAnimation( Context context, Object object,
                                                           int startColor1, int endColor1, int startColor2, int endColor2,
                                                           GradientDrawable.Orientation orientation) {
        //アニメーション時間
        int duration = context.getResources().getInteger(R.integer.color_trance_animation_duration);

        //---------------------------------------
        // アニメーション付きで背景色を変更
        //---------------------------------------
        ArgbEvaluator argb = new ArgbEvaluator();

        GradientDrawable bgDraw = new GradientDrawable();
        bgDraw.setOrientation( orientation );

        ValueAnimator animator = TimeAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //現在の進行度
                float fraction = valueAnimator.getAnimatedFraction();
                //設定するグラデーション
                int gradationColor1 = (int)argb.evaluate( fraction, startColor1, endColor1 );
                int gradationColor2 = (int)argb.evaluate( fraction, startColor2, endColor2 );
                bgDraw.setColors( new int[]{gradationColor1, gradationColor2} );
                //背景色に適用
                ((View)object).setBackground(bgDraw);
            }
        });
        animator.start();
    }
}
