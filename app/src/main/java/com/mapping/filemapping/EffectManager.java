package com.mapping.filemapping;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import java.util.Random;

public class EffectManager {

    //透明度
    public static final int DEFAULT_ALPHA = 0x55;
    public static final int DISABLE_ALPHA = -1;

    //色パターン
    public static final int COLOR_PTN_SPECIFY = 1;          //色指定の場合、指定された色を本体色と影色のどちらにも設定
    public static final int COLOR_PTN_LIGHT_RANDOM = 2;
    public static final int COLOR_PTN_MIDDLE_RANDOM = 3;


    //エフェクトビュー追加先レイアウト
    final private ViewGroup mAddDistView;
    //エフェクトの形状
    private int mEffectShape;
    //エフェクトアニメーション
    private int mEffectAnimation;
    //エフェクトのPaintStyle
    private Paint.Style mPaintStyle;
    //エフェクトサイズ範囲
    private int mEffectRangeSize;
    //エフェクト最小サイズ
    private int mEffectMinSize;
    //エフェクト色
    private int mEffectColor;
    private int mEffectShadowColor;
    //エフェクト色パターン（指定、ランダム）
    private int mEffectColorPtn;
    //エフェクト透明度
    private int mEffectMinAlpha;
    private int mEffectMaxAlpha;
    private boolean mIsEffectRandomAlpha;
    //傾けの有無
    private boolean mIsTilt;
    //グラデーションの有無
    private boolean mIsGradation;
    //エフェクト量
    private int mEffectVolume;

    //ランダム色リスト
    private TypedArray mRandomLightColors;
    private TypedArray mRandomMiddleColors;
    private TypedArray mRandomDarkColors;



    /*
     * コンストラクタ
     */
    public EffectManager(ViewGroup parentView) {
        mAddDistView = parentView;

        //ランダムカラー
        Resources res = parentView.getContext().getResources();
        mRandomLightColors = res.obtainTypedArray(R.array.effectRandomLightColors);
        mRandomMiddleColors = res.obtainTypedArray(R.array.effectRandomMiddleColors);
        mRandomDarkColors = res.obtainTypedArray(R.array.effectRandomDarkColors);
    }


    /*
     * エフェクト開始
     */
    public void startEffect() {
        //エフェクトを生成
        createEffects();
    }

    /*
     * エフェクト開始
     */
    public void startEffect( int effectResID ) {

        String test = "aaa";
        switch ( test ){
            case "aaa":


            case "bbb":




        }

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
     *   para2：Paintスタイル
     *   para3：エフェクトアニメーション
     */
    public void setEffectAttr(int shape, Paint.Style paintStyle, int animation) {
        setEffectAttr(shape, paintStyle, animation, false);
    }

    /*
     * エフェクト情報の設定
     *   para1：エフェクト形状
     *   para2：Paintスタイル
     *   para3：エフェクトアニメーション
     *   para4：傾けの有無
     */
    public void setEffectAttr(int shape, Paint.Style paintStyle, int animation, boolean isTilt) {
        mEffectShape = shape;
        mPaintStyle = paintStyle;
        mEffectAnimation = animation;
        mIsTilt = isTilt;

        //初期値
        mEffectVolume = 20;
        mEffectMinAlpha = DEFAULT_ALPHA;
        mEffectMaxAlpha = DEFAULT_ALPHA;
        mIsEffectRandomAlpha = false;
        mIsGradation = false;
    }

    /*
     * エフェクト量の設定
     *   para1：生成するエフェクト数
     */
    public void setEffectVolume(int volume) {
        mEffectVolume = volume;
    }

    /*
     * エフェクトサイズの設定
     *   para1：エフェクトのサイズ幅
     *   para2：エフェクトの最小サイズ
     */
    public void setEffectSize(int rangeSize, int minSize) {
        mEffectRangeSize = rangeSize;
        mEffectMinSize = minSize;
    }

    /*
     * エフェクト色パターンの設定
     *   para1：エフェクト色指定パターン
     */
    public void setEffectColorPtn(int pattern) {
        //本メソッドはランダム指定のみの想定
        mEffectColorPtn = pattern;

    }
    public void setEffectColorPtn(int pattern, int color) {
        mEffectColorPtn = pattern;

        if( pattern == COLOR_PTN_SPECIFY ){
            //本体色も影色も指定色を設定
            mEffectColor = color;
            mEffectShadowColor = color;
        }
    }

    /*
     * エフェクトカラーをランダムに設定
     */
    private void setEffectRandomColor() {
        //ランダム値
        final int RANDOM_COLOR_NUM = 6;
        Random random = new Random();
        int i = random.nextInt(RANDOM_COLOR_NUM);

        //ランダム色リストから色を取得
        if( mEffectColorPtn == COLOR_PTN_LIGHT_RANDOM ){
            mEffectColor = mRandomLightColors.getColor( i, Color.WHITE );
        } else {
            mEffectColor = mRandomMiddleColors.getColor( i, Color.WHITE );
        }

        mEffectShadowColor = mRandomDarkColors.getColor( i, Color.WHITE );
    }

    /*
     * Paintにグラデーションを付与するかどうか
     */
    public void setGradation(boolean isGradation) {
        mIsGradation = isGradation;
    }

    /*
     * アニメーションの設定
     */
    public void setAnimation(int animation) {
        mEffectAnimation = animation;
    }

    /*
     * 傾きの有無
     */
    public void setTilt(boolean isTilt) {
        mIsTilt = isTilt;
    }

    /*
     * エフェクト透明度の設定
     *   para1：透明度
     */
    public void setEffectAlpha(int alpha) {
        mEffectMinAlpha = alpha;
        mEffectMaxAlpha = EffectView.UNSPECIFIED;
    }
    public void setEffectAlpha(int minAlpha, int maxAlpha) {
        mEffectMinAlpha = minAlpha;
        mEffectMaxAlpha = maxAlpha;
    }

    /*
     * エフェクト生成
     */
    public void createEffects() {

        //中央座標を取得
        int centerX = mAddDistView.getWidth() / 2;
        int centerY = mAddDistView.getHeight() / 2;

        int rangeX = (int) (mAddDistView.getWidth() * 0.3f);
        int rangeY = (int) (mAddDistView.getHeight() * 0.3f);
        int absRangeX = rangeX / 2;
        int absRangeY = rangeY / 2;

        //---------------------------------
        //エフェクトビューの生成
        //---------------------------------
        for (int i = 0; i < mEffectVolume; i++) {

            if( mEffectColorPtn != COLOR_PTN_SPECIFY ){
                setEffectRandomColor();
            }

            //---------------------------------
            // エフェクトビュー生成・レイアウトへ追加
            //---------------------------------
            EffectView effectView = new EffectView(mAddDistView.getContext());
            effectView.setEffectShape( mEffectShape, mPaintStyle );
            effectView.setEffectSize( mEffectRangeSize, mEffectMinSize );
            effectView.setEffectColor( mEffectColor, mEffectShadowColor );
            effectView.setEffectTilt( mIsTilt );
            effectView.setEffectAlpha( mEffectMinAlpha, mEffectMaxAlpha );
            effectView.setGradation( mIsGradation );
            mAddDistView.addView(effectView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //---------------------------------
            // 生成座標の設定
            //---------------------------------
            //座標offsetをランダムに設定
            Random random = new Random();
            int offsetX = random.nextInt(rangeX) - absRangeX;
            int offsetY = random.nextInt(rangeY) - absRangeY;

            //位置設定
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) effectView.getLayoutParams();
            mlp.setMargins(centerX + offsetX, centerY + offsetY, mlp.rightMargin, mlp.bottomMargin);

            //---------------------------------
            //　アニメーションの適用
            //---------------------------------
            applyEffectAnimation(effectView);
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
        for (int i = childNum - 1; i >= 0; i--) {
            //エフェクトビューならレイアウトから除外
            View view = mAddDistView.getChildAt(i);
            if (view instanceof EffectView) {
                mAddDistView.removeView(view);
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
            case MapTable.EFFECT_ANIM_BLINK:
                applyBlinkEffectAnimation(animationTarget);
                break;

            //------------------------
            // 場所を変えながら明滅
            //------------------------
            case MapTable.EFFECT_ANIM_BLINK_MOVE:
                applyBlinkMoveEffectAnimation(animationTarget);
                break;

            //------------------------
            // 回転
            //------------------------
            case MapTable.EFFECT_ANIM_SPIN:
                applySpinEffectAnimation(animationTarget);
                break;

            //------------------------
            // 場所を変えながら回転
            //------------------------
            case MapTable.EFFECT_ANIM_SPIN_MOVE:
                applySpinMoveEffectAnimation(animationTarget);
                break;

            //------------------------
            // ゆっくりな移動
            //------------------------
            case MapTable.EFFECT_ANIM_SLOW_MOVE:

                break;

            //------------------------
            // ゆっくりと浮き上がる
            //------------------------
            case MapTable.EFFECT_ANIM_SLOW_FLOAT:
                applyFloatEffectAnimation(animationTarget);
                break;

            //------------------------
            // ゆっくり落ちる
            //------------------------
            case MapTable.EFFECT_ANIM_SLOW_FALL:
                applyFallEffectAnimation(animationTarget);
                break;

            //------------------------
            // 枠線のグラデーションの回転
            //------------------------
            case MapTable.EFFECT_ANIM_STROKE_GRADATION_ROTATE:
                applyStrokeGradationRotateEffectAnimation(animationTarget);
                break;

            //------------------------
            // 拡大と縮小
            //------------------------
            case MapTable.EFFECT_ANIM_SCALE_UP:
                applyScaleUpEffectAnimation(animationTarget);
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
     * 　　明滅。場所は固定
     */
    private void applyBlinkEffectAnimation(View animationTarget) {

        //------------------------
        // 乱数値
        //------------------------
        Random random = new Random();
        int duration = random.nextInt(2501) + 1000;
        int delay = random.nextInt(1001);

        //------------------------
        // アニメーションの適用
        //------------------------
        AnimatorSet tmpSet = (AnimatorSet) AnimatorInflater.loadAnimator(animationTarget.getContext(), R.animator.effect_blink);
        tmpSet.setTarget(animationTarget);
        tmpSet.setDuration(duration);
        //tmpSet.setStartDelay( delay );
        tmpSet.start();
    }

    /*
     * エフェクトアニメーション
     * 　　明滅、消滅後は別の座標に移動して明滅を再開
     */
    private void applyBlinkMoveEffectAnimation(View animationTarget) {

        //------------------------
        // 乱数値
        //------------------------
        Random random = new Random();
        int duration = random.nextInt(2501) + 1000;

        //------------------------
        // アニメーションの適用
        //------------------------
        ValueAnimator blinkAnim = ObjectAnimator.ofFloat(animationTarget, "alpha", 0f, 1f);
        blinkAnim.setDuration(duration);
        blinkAnim.setRepeatMode(ValueAnimator.REVERSE);
        blinkAnim.setRepeatCount(ValueAnimator.INFINITE);
        blinkAnim.addListener(new TrancelationReverseListener(animationTarget));
        blinkAnim.start();
    }

    /*
     * エフェクトアニメーション
     * 　　回転
     */
    private void applySpinEffectAnimation(View animationTarget) {

        //------------------------
        // 乱数値
        //------------------------
        Random random = new Random();
        int duration = random.nextInt(10001) + 20000;
        int delay = random.nextInt(1001);

        //------------------------
        // アニメーションの適用
        //------------------------
        AnimatorSet tmpSet = (AnimatorSet) AnimatorInflater.loadAnimator(animationTarget.getContext(), R.animator.effect_spin);
        tmpSet.setTarget(animationTarget);
        tmpSet.setDuration(duration);
        //tmpSet.setStartDelay( delay );
        tmpSet.start();
    }

    /*
     * エフェクトアニメーション
     * 　　回転。回転後、移動。
     */
    private void applySpinMoveEffectAnimation(View animationTarget) {

    }



    /*
     * エフェクトアニメーション
     * 　　浮き上がり
     */
    private void applyFloatEffectAnimation( View animationTarget ) {
        //------------------------
        // 乱数値
        //------------------------
        Random random = new Random();
        int duration = random.nextInt( 4001 ) + 5000;
        int delay    = random.nextInt( 1001 );

        //------------------------
        // アニメーションの適用
        //------------------------
        ValueAnimator floatAlphaAnim = ObjectAnimator.ofFloat(animationTarget, "floatAlpha", 0f, 1f);
        floatAlphaAnim.setDuration( duration );
        floatAlphaAnim.setRepeatMode( ValueAnimator.RESTART );
        floatAlphaAnim.setRepeatCount( ValueAnimator.INFINITE );
        floatAlphaAnim.addListener( new TrancelationRestartListener( animationTarget ) );
        floatAlphaAnim.start();
    }

    /*
     * エフェクトアニメーション
     * 　　ゆっくり落ちる
     */
    private void applyFallEffectAnimation( View animationTarget ) {
        //------------------------
        // 乱数値
        //------------------------
        Random random = new Random();
        int duration = random.nextInt( 5001 ) + 10000;
        int delay    = random.nextInt( 1001 );

        //------------------------
        // アニメーションの適用
        //------------------------
        ValueAnimator floatAlphaAnim = ObjectAnimator.ofFloat(animationTarget, "fallAlpha", 0f, 1f);
        floatAlphaAnim.setDuration( duration );
        floatAlphaAnim.setRepeatMode( ValueAnimator.RESTART );
        floatAlphaAnim.setRepeatCount( ValueAnimator.INFINITE );
        floatAlphaAnim.addListener( new TrancelationRestartListener( animationTarget ) );
        floatAlphaAnim.start();
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
    private void applyScaleUpEffectAnimation(View animationTarget ) {

        //------------------------
        // 乱数値
        //------------------------
        Random random = new Random();
        int duration = random.nextInt( 2001 ) + 2000;
        int delay    = random.nextInt( 1001 );

        //------------------------
        // アニメーションの適用
        //------------------------
        ValueAnimator scaleAlphaAnim = ObjectAnimator.ofFloat(animationTarget, "scaleAlpha", 0f, 1f);
        scaleAlphaAnim.setDuration( duration );
        scaleAlphaAnim.setRepeatMode( ValueAnimator.REVERSE );
        scaleAlphaAnim.setRepeatCount( ValueAnimator.INFINITE );
        scaleAlphaAnim.addListener( new TrancelationReverseListener( animationTarget ) );
        scaleAlphaAnim.start();
    }

    /*
     * リピートアニメーションリスナー（REVERSE）
     */
    private class TrancelationReverseListener extends AnimatorListenerAdapter {

        private final View mAnimationView;
        private boolean mTrancelateStart;

        public TrancelationReverseListener(View animationView ){
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

    /*
     * リピートアニメーションリスナー（RESTART）
     */
    private class TrancelationRestartListener extends AnimatorListenerAdapter {

        private final View mAnimationView;

        public TrancelationRestartListener(View animationView ){
            mAnimationView = animationView;
        }

        /*
         * アニメーションリピートリスナー
         *
         *   前提
         *     setRepeatMode()にて、RESTARTが設定されていること。
         */
        public void onAnimationRepeat(Animator animation) {

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
