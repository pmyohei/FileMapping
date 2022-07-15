package com.mapping.filemapping;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import java.util.Random;

public class EffectManager {

    //エフェクトビュー追加先レイアウト
    final private ViewGroup mAddDistView;
    //エフェクトの形状
    private int mEffectShape;
    //エフェクトアニメーション
    private int mEffectAnimation;
    //エフェクトのPaintStyle
    private Paint.Style mPaintStyle;
    //エフェクト量
    private int mEffectVolume;

    /*
     * コンストラクタ
     */
    public EffectManager(ViewGroup parentView) {
        mAddDistView = parentView;
    }

    /*
     * コンストラクタ
     *   para1：エフェクトビュー追加先のレイアウト
     *   para2：エフェクトの形状
     *   para3：エフェクトのアニメーション
     */
    public EffectManager(ViewGroup parentView, int shape, Paint.Style paintStyle, int animation) {
        mAddDistView = parentView;

        //エフェクト情報の設定
        setEffectAttr(shape, paintStyle, animation);
    }

    /*
     * エフェクト開始
     */
    public void startEffect() {
        //エフェクトを生成
        createEffects();
    }

    /*
     * エフェクト停止
     */
    public void stopEffect() {
        //エフェクトを削除
        removeEffects();
    }

    /*
     * エフェクト再開始
     *   表示中のエフェクトを削除し、現在設定中のエフェクト設定値で再開始
     */
    public void restartEffect() {

        //------------------------
        // 描画中のエフェクトを削除
        //------------------------
        removeEffects();

        //------------------------
        // 新しいエフェクトを生成
        //------------------------
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   para1：エフェクト形状
     *   para2：エフェクトアニメーション
     */
    public void setEffectAttr(int shape, Paint.Style paintStyle, int animation) {
        mEffectShape = shape;
        mPaintStyle = paintStyle;
        mEffectAnimation = animation;

        //初期値
        mEffectVolume = 20;
    }

    /*
     * エフェクト情報の設定
     *   para1：エフェクト形状
     *   para2：エフェクトアニメーション
     */
    public void setEffectVolume(int volume) {
        mEffectVolume = volume;
    }

    /*
     * エフェクト生成
     */
    public void createEffects() {

        //中央座標を取得
        int centerX = mAddDistView.getWidth() / 2;
        int centerY = mAddDistView.getHeight() / 2;

        int rangeX = (int)(mAddDistView.getWidth() * 0.4f);
        int rangeY = (int)(mAddDistView.getHeight() * 0.4f);
        int absRangeX = rangeX / 2;
        int absRangeY = rangeY / 2;

        //---------------------------------
        //エフェクトビューの生成
        //---------------------------------
        for (int i = 0; i < mEffectVolume; i++) {

            //---------------------------------
            // エフェクトビュー生成・レイアウトへ追加
            //---------------------------------
            EffectView effectView = new EffectView(mAddDistView.getContext(), mEffectShape, mPaintStyle);
            mAddDistView.addView(effectView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //---------------------------------
            // 生成座標の設定
            //---------------------------------
            //座標offsetをランダムに設定
            Random random = new Random();
            int offsetX = random.nextInt( rangeX ) - absRangeX;
            int offsetY = random.nextInt( rangeY ) - absRangeY;

            //位置設定
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) effectView.getLayoutParams();
            mlp.setMargins(centerX + offsetX, centerY + offsetY, mlp.rightMargin, mlp.bottomMargin);

            //---------------------------------
            //　アニメーションの適用
            //---------------------------------
            applyEffectAnimation( effectView );
        }
    }

    /*
     * エフェクト削除
     */
    private void removeEffects() {
        //子ビュー数
        int childNum = mAddDistView.getChildCount();

        //------------------------------------
        // 最後尾から削除チェック
        //------------------------------------
        for( int i = childNum - 1; i >= 0; i--){
            //エフェクトビューならレイアウトから除外
            View view = mAddDistView.getChildAt(i);
            if( view instanceof EffectView ){
                mAddDistView.removeView( view );
            }
        }
    }

    /*
     * エフェクト有効／無効
     */
    private void visibilityEffects(int visible) {

        //--------------------------------
        // 全てのエフェクト可視状態を変更する
        //--------------------------------

    }

    /*
     * エフェクトアニメーションのエフェクトビューへの適用
     */
    private void applyEffectAnimation(View animationTarget) {

        //--------------------------------
        // 指定アニメーションに応じて適用
        //--------------------------------
        switch (mEffectAnimation) {
            //------------------------
            // 明滅
            //------------------------
            case MapTable.BLINK:
                applyBlinkEffectAnimation(animationTarget);
                break;

            //------------------------
            // 場所を変えながら明滅
            //------------------------
            case MapTable.BLINK_MOVE:
                applyBlinkMoveEffectAnimation(animationTarget);
                break;

            //------------------------
            // 回転
            //------------------------
            case MapTable.SPIN:
                applySpinEffectAnimation(animationTarget);
                break;

            //------------------------
            // ゆっくりな移動
            //------------------------
            case MapTable.SLOW_MOVE:

                break;

            //------------------------
            // ゆっくりと浮き上がる
            //------------------------
            case MapTable.SLOW_FLOAT:

                break;

            //------------------------
            // 枠線のグラデーションの回転
            //------------------------
            case MapTable.STROKE_GRADATION_ROTATE:
                applyStrokeGradationRotateEffectAnimation(animationTarget);
                break;

            //------------------------
            // 拡大と縮小
            //------------------------
            case MapTable.SCALE_UP:
                applyScaleupEffectAnimation(animationTarget);
                break;

            //------------------------
            // 処理なし
            //------------------------
            default:
                break;
        }
    }

    /*
     * エフェクトアニメーション
     * 　　明滅
     */
    private void applyBlinkEffectAnimation( View animationTarget ) {

        //------------------------
        // 乱数値
        //------------------------
        Random random = new Random();
        int duration = random.nextInt( 2501 ) + 1000;
        int delay    = random.nextInt( 1001 );

        //------------------------
        // アニメーションの適用
        //------------------------
        AnimatorSet tmpSet = (AnimatorSet) AnimatorInflater.loadAnimator(animationTarget.getContext(), R.animator.effect_blink);
        tmpSet.setTarget(animationTarget);
        tmpSet.setDuration( duration );
        //tmpSet.setStartDelay( delay );
        tmpSet.start();
    }

    /*
     * エフェクトアニメーション
     * 　　明滅：移動あり
     */
    private void applyBlinkMoveEffectAnimation( View animationTarget ) {

        //------------------------
        // 乱数値
        //------------------------
        Random random = new Random();
        int duration = random.nextInt( 2501 ) + 1000;

        //
        int centerX = mAddDistView.getWidth() / 2;
        int centerY = mAddDistView.getHeight() / 2;
        int rangeX = (int)(mAddDistView.getWidth() * 0.4f);
        int rangeY = (int)(mAddDistView.getHeight() * 0.4f);
        int absRangeX = rangeX / 2;
        int absRangeY = rangeY / 2;

        //------------------------
        // アニメーションの適用
        //------------------------
/*        ValueAnimator blinkAnim = ObjectAnimator.ofFloat(animationTarget, "alpha", 0f, 1f);
        blinkAnim.setDuration( 6000 );
        blinkAnim.setRepeatMode( ValueAnimator.REVERSE );
        blinkAnim.setRepeatCount( ValueAnimator.INFINITE );
        blinkAnim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationRepeat(Animator animation) {
                //座標offsetをランダムに設定
                Random random = new Random();
                int offsetX = random.nextInt( rangeX ) - absRangeX;
                int offsetY = random.nextInt( rangeY ) - absRangeY;

                //位置設定
                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) animationTarget.getLayoutParams();
                //mlp.setMargins(centerX + offsetX, centerY + offsetY, mlp.rightMargin, mlp.bottomMargin);

                animationTarget.setTranslationX( offsetX );
                animationTarget.setTranslationY( offsetY );

                Log.i("アニメーション開始", "コールチェック　リピート offsetX" + offsetX);
            }}
        );
        blinkAnim.start();*/
        ValueAnimator blinkAnim = ObjectAnimator.ofFloat(animationTarget, "alpha", 0f, 1f);
        blinkAnim.setDuration( duration );
        blinkAnim.setRepeatMode( ValueAnimator.REVERSE );
        blinkAnim.setRepeatCount( ValueAnimator.INFINITE );
        blinkAnim.addListener( new TrancelationListener( animationTarget ) );
        blinkAnim.start();

/*        ValueAnimator blinkOnAnim = ObjectAnimator.ofFloat(animationTarget, "alpha", 0f, 1f);
        blinkOnAnim.setDuration( 2000 );
        ValueAnimator blinkOffAnim = ObjectAnimator.ofFloat(animationTarget, "alpha", 1f, 0f);
        blinkOffAnim.setDuration( 2000 );

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(blinkOffAnim).before(blinkOnAnim);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                Log.i("アニメーション開始", "コールチェック　開始");
            }
            public void onAnimationRepeat(Animator animation) {
                //座標offsetをランダムに設定
                Random random = new Random();
                int offsetX = random.nextInt( rangeX ) - absRangeX;
                int offsetY = random.nextInt( rangeY ) - absRangeY;

                //位置設定
                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) animationTarget.getLayoutParams();
                //mlp.setMargins(centerX + offsetX, centerY + offsetY, mlp.rightMargin, mlp.bottomMargin);

                animationTarget.setTranslationX( offsetX );
                animationTarget.setTranslationY( offsetY );

                Log.i("アニメーション開始", "コールチェック　リピート offsetX" + offsetX);
            }}
        );

        animatorSet.start();*/
    }

    /*
     * エフェクトアニメーション
     * 　　回転
     */
    private void applySpinEffectAnimation( View animationTarget ) {

        //------------------------
        // 乱数値
        //------------------------
        Random random = new Random();
        int duration = random.nextInt( 8001 ) + 8000;
        int delay    = random.nextInt( 1001 );

        //------------------------
        // アニメーションの適用
        //------------------------
        AnimatorSet tmpSet = (AnimatorSet) AnimatorInflater.loadAnimator(animationTarget.getContext(), R.animator.effect_spin);
        tmpSet.setTarget(animationTarget);
        tmpSet.setDuration( duration );
        //tmpSet.setStartDelay( delay );
        tmpSet.start();
    }


    /*
     * エフェクトアニメーション
     * 　　枠線のグラデーション回転
     */
    private void applyStrokeGradationRotateEffectAnimation( View animationTarget ) {

        //------------------------
        // 乱数値
        //------------------------
        Random random = new Random();
        int duration = random.nextInt( 8001 ) + 4000;
        int delay    = random.nextInt( 1001 );

        //------------------------
        // アニメーションの適用
        //------------------------
        EffectAnimation animation = new EffectAnimation((EffectView)animationTarget);
        animation.setDuration(duration);
        //animation.setStartOffset(delay);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new LinearInterpolator());    //※本設定をしないと2週目でapplyTransformationのinterpolatedTimeが1を超えた値をとる
        animation.setFillEnabled(true);                         //※同上
        animation.setFillAfter(true);                           //※同上
        animationTarget.startAnimation(animation);
    }

    /*
     * エフェクトアニメーション
     * 　　拡大と縮小を繰り返す
     */
    private void applyScaleupEffectAnimation( View animationTarget ) {

        //------------------------
        // 乱数値
        //------------------------
        Random random = new Random();
        int duration = random.nextInt( 4001 ) + 4000;
        int delay    = random.nextInt( 1001 );

        //------------------------
        // アニメーションの適用
        //------------------------
        ValueAnimator blinkAnim = ObjectAnimator.ofFloat(animationTarget, "scaleAlpha", 0f, 1f);
        blinkAnim.setDuration( duration );
        blinkAnim.setRepeatMode( ValueAnimator.REVERSE );
        blinkAnim.setRepeatCount( ValueAnimator.INFINITE );
        blinkAnim.addListener( new TrancelationListener( animationTarget ) );
        blinkAnim.start();
    }



    /*
     * リピートアニメーションリスナー
     */
    private class TrancelationListener extends AnimatorListenerAdapter {

        private final View mAnimationView;
        private boolean mTrancelateStart;

        public TrancelationListener( View animationView ){
            mTrancelateStart = false;
            mAnimationView = animationView;
        }

        /*
         * アニメーションリピートリスナー
         *
         *   前提
         *     setRepeatMode()にて、REVERSEが設定されていること。
         *     アニメーションが往復して完全に開始の状態まで戻った時に、
         *     本処理が実行される形となっている。
         */
        public void onAnimationRepeat(Animator animation) {
            //--------------------------------
            // リピート処理は交互に行う
            //--------------------------------
            if( !mTrancelateStart) {
                mTrancelateStart = true;
                return;
            }
            mTrancelateStart = false;

            //--------------------------------
            // 移動量をランダム生成し、配置を移動
            //--------------------------------
            Random random = new Random();
            final int RANGE = 800;
            final int ABS_RANGE = RANGE / 2;
            int offsetX = random.nextInt(RANGE) - ABS_RANGE;
            int offsetY = random.nextInt(RANGE) - ABS_RANGE;

            mAnimationView.setTranslationX( offsetX );
            mAnimationView.setTranslationY( offsetY );
            //Log.i("アニメーション開始", "コールチェック　リピート offsetX" + offsetX);
        }
    }



}
