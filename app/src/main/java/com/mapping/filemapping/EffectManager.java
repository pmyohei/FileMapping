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

    //------------------------------------
    // エフェクト形状
    //------------------------------------
    public static final int EFFECT_SHAPE_HEART_NORMAL = 0x00;           //ハート
    public static final int EFFECT_SHAPE_HEART_THIN = 0x01;
    public static final int EFFECT_SHAPE_HEART_INFLATED = 0x02;
    public static final int EFFECT_SHAPE_TRIANGLE = 0x10;               //尖鋭
    public static final int EFFECT_SHAPE_DIA = 0x11;
    public static final int EFFECT_SHAPE_STAR = 0x20;                   //星
    public static final int EFFECT_SHAPE_MOON = 0x21;
    public static final int EFFECT_SHAPE_SPARKLE_4_DIRECTION = 0x30;    //スパークル
    public static final int EFFECT_SHAPE_SPARKLE_8_DIRECTION = 0x31;
    public static final int EFFECT_SHAPE_SPARKLE_SHORT = 0x32;
    public static final int EFFECT_SHAPE_SPARKLE_SHIN = 0x33;
    public static final int EFFECT_SHAPE_SPARKLE_LONG = 0x34;
    public static final int EFFECT_SHAPE_SPARKLE_RANDOM = 0x35;
    public static final int EFFECT_SHAPE_FLOWER = 0x40;                 //花
    public static final int EFFECT_SHAPE_SAKURA = 0x41;
    public static final int EFFECT_SHAPE_CIRCLE = 0x51;                 //円
    public static final int EFFECT_SHAPE_OVAL = 0x53;

    //------------------------------------
    // エフェクトアニメーション
    //------------------------------------
    final int EFFECT_ANIM_NONE = -1;                         //なし
    final int EFFECT_ANIM_BLINK = 0x00;                      //明滅
    final int EFFECT_ANIM_BLINK_MOVE = 0x01;                 //明滅（移動あり）
    final int EFFECT_ANIM_SPIN = 0x10;                       //回転
    final int EFFECT_ANIM_SPIN_MOVE = 0x11;                  //回転（移動あり）
    final int EFFECT_ANIM_SLOW_MOVE = 0x20;                  //ゆっくり移動
    final int EFFECT_ANIM_SLOW_FLOAT = 0x30;                 //ゆっくり浮き上がる
    final int EFFECT_ANIM_SLOW_FALL = 0x40;                  //ゆっくり落ちる
    final int EFFECT_ANIM_STROKE_GRADATION_ROTATE = 0x50;    //枠線のグラデーションの回転
    final int EFFECT_ANIM_SCALE_UP = 0x60;                   //拡大と縮小


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
    public void startEffect(int effect) {

        switch (effect) {
            case MapTable.EFFECT_HEART_FLOAT_RED:
                setAttrHeartFloatRed();
                break;

            case MapTable.EFFECT_HEART_FLOAT_BLACK:
                setAttrHeartFloatBlack();
                break;

            case MapTable.EFFECT_HEART_FLOAT_WHITE:
                setAttrHeartFloatWhite();
                break;

            case MapTable.EFFECT_HEART_SCALE_COLORFUL:
                setAttrHeartScaleColorful();
                break;

            case MapTable.EFFECT_INFLATED_HEART_FLOAT_3COLOR:
                setAttrInflatedHeartFloat3Color();
                break;

            case MapTable.EFFECT_THIN_HEART_FLOAT_COLORFUL:
                setAttrThinHeartFloatColorful();
                break;

            case MapTable.EFFECT_STAR_MOON_YELLOW:
                setAttrStarMoonYellow();
                break;

            case MapTable.EFFECT_STAR_MOON_COLORFUL:
                setAttrStarMoonColorful();
                break;

            case MapTable.EFFECT_CIRCLE_STAR:
                setAttrCircleStar();
                break;

            case MapTable.EFFECT_SNOW:
                setAttrSnow();
                break;

            case MapTable.EFFECT_8SPARKLE_WHITE:
                setAttr8sparkleWhite();
                break;

            case MapTable.EFFECT_8SPARKLE_YELLOW:
                setAttr8sparkleYellow();
                break;

            case MapTable.EFFECT_4SPARKLE_WHITE:
                setAttr4sparkleWhite();
                break;

            case MapTable.EFFECT_4SPARKLE_RED_BLUE:
                setAttr4sparkleRedBlue();
                break;

            case MapTable.EFFECT_4_8SPARKLE_WHITE:
                setAttr4_8sparkleWhite();
                break;

            case MapTable.EFFECT_4_8SPARKLE_COLORFUL:
                setAttr4_8sparkleColorful();
                break;

            case MapTable.EFFECT_POLKADOTS_COLORFUL:
                setAttrPolkadotsColorful();
                break;

            case MapTable.EFFECT_POLKADOTS_WHITE:
                setAttrPolkadotsWhite();
                break;

            case MapTable.EFFECT_SAKURA_PINK:
                setAttrSakuraPink();
                break;

            case MapTable.EFFECT_FLOWER_WHITE:
                setAttrFlowerWhite();
                break;

            case MapTable.EFFECT_FLOWER_2COLOR:
                setAttrFlower2Color();
                break;

            case MapTable.EFFECT_LITTLE_FLOWER_WHITE:
                setAttrLittleFlowerWhite();
                break;

            default:
                break;
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
     * エフェクト情報の設定
     *
     */
    private void setAttrHeartFloatRed(){
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_heart_red );

        //ハート
        setEffectAttr( EFFECT_SHAPE_HEART_NORMAL, Paint.Style.FILL, EFFECT_ANIM_SLOW_FLOAT, false);
        setEffectVolume( 40 );
        setEffectSize( 20, 50 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0xAA );
        restartEffect();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrHeartFloatBlack(){
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_heart_black );

        //ハート
        setEffectAttr( EFFECT_SHAPE_HEART_NORMAL, Paint.Style.FILL, EFFECT_ANIM_SLOW_FLOAT, false);
        setEffectVolume( 40 );
        setEffectSize( 20, 50 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0xAA );
        restartEffect();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrHeartFloatWhite(){
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_heart_white );

        //ハート
        setEffectAttr( EFFECT_SHAPE_HEART_NORMAL, Paint.Style.FILL, EFFECT_ANIM_SLOW_FLOAT, false);
        setEffectVolume( 40 );
        setEffectSize( 20, 50 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0xAA );
        restartEffect();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrHeartScaleColorful(){
        //ハート
        setEffectAttr( EFFECT_SHAPE_HEART_NORMAL, Paint.Style.FILL, EFFECT_ANIM_SCALE_UP, true);
        setEffectVolume( 40 );
        setEffectSize( 200, 300 );
        setEffectColorPtn( COLOR_PTN_LIGHT_RANDOM);
        setEffectAlpha( 0x55, 0xdd );
        setGradation( false );
        restartEffect();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrInflatedHeartFloat3Color(){
        int colorBlue = mAddDistView.getContext().getResources().getColor( R.color.effect_inflated_heart_blue);
        int colorCream = mAddDistView.getContext().getResources().getColor( R.color.effect_inflated_heart_cream);
        int colorPink = mAddDistView.getContext().getResources().getColor( R.color.effect_inflated_heart_pink);

        //共通設定
        setEffectAttr( EFFECT_SHAPE_HEART_INFLATED, Paint.Style.FILL, EFFECT_ANIM_BLINK_MOVE, false);
        setEffectSize( 100, 100 );
        setEffectVolume( 15 );
        setGradation( false );
        setEffectAlpha( 0xDD, 0xFF );

        //ハート、青系
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorBlue );
        restartEffect();

        //ハート、クリーム色系
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorCream );
        createEffects();

        //ハート、ピンク系
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorPink );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrThinHeartFloatColorful(){
        //ハート
        setEffectAttr( EFFECT_SHAPE_HEART_THIN, Paint.Style.FILL, EFFECT_ANIM_SLOW_FLOAT, true);
        setEffectVolume( 60 );
        setEffectSize( 100, 300 );
        setEffectColorPtn( COLOR_PTN_LIGHT_RANDOM);
        setEffectAlpha( 0x88 );
        restartEffect();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrStarMoonYellow(){
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_star );

        //星
        setEffectAttr( EFFECT_SHAPE_STAR, Paint.Style.FILL, EFFECT_ANIM_SPIN);
        setEffectVolume( 30 );
        setEffectSize( 100, 40 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0x99, 0xCC );
        restartEffect();

        //月
        setEffectAttr( EFFECT_SHAPE_MOON, Paint.Style.FILL, EFFECT_ANIM_NONE, true);
        setEffectVolume( 5 );
        setEffectSize( 100, 100 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0x99 );
        createEffects();

        //円
        setEffectAttr( EFFECT_SHAPE_CIRCLE, Paint.Style.FILL, EFFECT_ANIM_NONE);
        setEffectVolume( 100 );
        setEffectSize( 10, 10 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0x44, 0xDD );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrStarMoonColorful(){
        //星
        setEffectAttr( EFFECT_SHAPE_STAR, Paint.Style.FILL, EFFECT_ANIM_SPIN);
        setEffectVolume( 30 );
        setEffectSize( 100, 40 );
        setEffectColorPtn( COLOR_PTN_LIGHT_RANDOM);
        setEffectAlpha( 0x99, 0xCC );
        restartEffect();

        //月
        setEffectAttr( EFFECT_SHAPE_MOON, Paint.Style.FILL, EFFECT_ANIM_NONE, true);
        setEffectVolume( 5 );
        setEffectSize( 100, 100 );
        setEffectColorPtn( COLOR_PTN_LIGHT_RANDOM);
        setEffectAlpha( 0x99 );
        createEffects();

        //円
        setEffectAttr( EFFECT_SHAPE_CIRCLE, Paint.Style.FILL, EFFECT_ANIM_NONE);
        setEffectVolume( 100 );
        setEffectSize( 10, 10 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, Color.WHITE );
        setEffectAlpha( 0x44, 0xDD );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrCircleStar(){
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_sparkle_white );

        //円、アニメーションあり
        setEffectAttr( EFFECT_SHAPE_CIRCLE, Paint.Style.FILL, EFFECT_ANIM_BLINK_MOVE);
        setEffectVolume( 100 );
        setEffectSize( 10, 20 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0x44, 0xDD );
        restartEffect();

        //円、アニメーションなし
        setEffectAttr( EFFECT_SHAPE_CIRCLE, Paint.Style.FILL, EFFECT_ANIM_NONE);
        setEffectVolume( 50 );
        setEffectSize( 10, 10 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0x44, 0xDD );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrSnow(){
        int colorSnow = mAddDistView.getContext().getResources().getColor( R.color.effect_snow );

        //円（雪）
        setEffectAttr( EFFECT_SHAPE_CIRCLE, Paint.Style.FILL, EFFECT_ANIM_SLOW_FALL);
        setEffectVolume( 160 );
        setEffectSize( 40, 10 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorSnow );
        setEffectAlpha( 0x44, 0xCC );
        setGradation( true );
        restartEffect();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttr8sparkleWhite(){
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_sparkle_white );

        //8方向スパークル
        setEffectAttr( EFFECT_SHAPE_SPARKLE_8_DIRECTION, Paint.Style.FILL, EFFECT_ANIM_BLINK_MOVE);
        setEffectVolume( 30 );
        setEffectSize( 100, 100 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0xDD, 0xEE );
        restartEffect();

        //円
        setEffectAttr( EFFECT_SHAPE_CIRCLE, Paint.Style.FILL, EFFECT_ANIM_NONE);
        setEffectVolume( 100 );
        setEffectSize( 10, 10 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0x44, 0xDD );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttr8sparkleYellow(){
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_sparkle_yellow );

        //8方向スパークル
        setEffectAttr( EFFECT_SHAPE_SPARKLE_8_DIRECTION, Paint.Style.FILL, EFFECT_ANIM_BLINK_MOVE);
        setEffectVolume( 30 );
        setEffectSize( 100, 100 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color);
        setEffectAlpha( 0xDD, 0xEE );
        restartEffect();

        //円
        setEffectAttr( EFFECT_SHAPE_CIRCLE, Paint.Style.FILL, EFFECT_ANIM_NONE);
        setEffectVolume( 100 );
        setEffectSize( 10, 10 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0x44, 0xDD );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttr4sparkleWhite(){
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_sparkle_white );

        //4方向スパークル
        setEffectAttr( EFFECT_SHAPE_SPARKLE_4_DIRECTION, Paint.Style.FILL, EFFECT_ANIM_BLINK_MOVE, false);
        setEffectVolume( 30 );
        setEffectSize( 200, 100 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0xDD, 0xEE );
        restartEffect();

        //ダイア
        setEffectAttr( EFFECT_SHAPE_DIA, Paint.Style.FILL, EFFECT_ANIM_NONE);
        setEffectVolume( 100 );
        setEffectSize( 10, 10 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );
        setEffectAlpha( 0x44, 0xDD );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttr4sparkleRedBlue(){
        int colorRed = mAddDistView.getContext().getResources().getColor( R.color.effect_sparkle_red );
        int colorBlue = mAddDistView.getContext().getResources().getColor( R.color.effect_sparkle_blue );
        int colorWhite = mAddDistView.getContext().getResources().getColor( R.color.effect_sparkle_white );

        //-----------------------
        // 4方向スパークル
        //-----------------------
        //共通設定
        setEffectAttr( EFFECT_SHAPE_SPARKLE_4_DIRECTION, Paint.Style.FILL, EFFECT_ANIM_BLINK_MOVE, false);
        setEffectVolume( 15 );
        setEffectSize( 200, 100 );
        setEffectAlpha( 0xDD, 0xEE );

        //4方向スパークル、赤系
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorRed);
        restartEffect();

        //4方向スパークル、青系
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorBlue);
        createEffects();

        //-----------------------
        // 円
        //-----------------------
        setEffectAttr( EFFECT_SHAPE_CIRCLE, Paint.Style.FILL, EFFECT_ANIM_NONE);
        setEffectVolume( 100 );
        setEffectSize( 10, 10 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorWhite );
        setEffectAlpha( 0x44, 0xDD );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttr4_8sparkleWhite(){
        int colorWhite = mAddDistView.getContext().getResources().getColor( R.color.effect_sparkle_white );

        //-----------------------
        // 8方向スパークル
        //-----------------------
        //共通設定
        setEffectAttr( EFFECT_SHAPE_SPARKLE_8_DIRECTION, Paint.Style.FILL, EFFECT_ANIM_BLINK_MOVE, true);
        setEffectSize( 100, 100 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorWhite);
        setEffectAlpha( 0xDD, 0xEE );

        //8方向スパークル、アニメーションあり
        setAnimation( EFFECT_ANIM_BLINK_MOVE);
        setEffectVolume( 15 );
        restartEffect();

        //8方向スパークル、アニメーションなし
        setAnimation( EFFECT_ANIM_NONE);
        setEffectVolume( 5 );
        createEffects();

        //-----------------------
        // 4方向スパークル
        //-----------------------
        //共通設定
        setEffectAttr( EFFECT_SHAPE_SPARKLE_4_DIRECTION, Paint.Style.FILL, EFFECT_ANIM_BLINK_MOVE, true);
        setEffectSize( 200, 100 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorWhite);
        setEffectAlpha( 0xDD, 0xEE );

        //4方向スパークル、アニメーションあり
        setAnimation( EFFECT_ANIM_BLINK_MOVE);
        setEffectVolume( 15 );
        createEffects();

        //4方向スパークル、アニメーションなし
        setAnimation( EFFECT_ANIM_NONE);
        setEffectVolume( 5 );
        createEffects();

        //-----------------------
        // 円
        //-----------------------
        setEffectAttr( EFFECT_SHAPE_CIRCLE, Paint.Style.FILL, EFFECT_ANIM_NONE);
        setEffectVolume( 100 );
        setEffectSize( 10, 10 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorWhite );
        setEffectAlpha( 0x44, 0xDD );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttr4_8sparkleColorful(){
        //-----------------------
        // 8方向スパークル
        //-----------------------
        //共通設定
        setEffectAttr( EFFECT_SHAPE_SPARKLE_8_DIRECTION, Paint.Style.FILL, EFFECT_ANIM_BLINK_MOVE, true);
        setEffectColorPtn( COLOR_PTN_LIGHT_RANDOM);
        setEffectAlpha( 0xDD, 0xEE );
        setEffectSize( 100, 100 );

        //8方向スパークル、アニメーションあり
        setAnimation( EFFECT_ANIM_BLINK_MOVE);
        setEffectVolume( 15 );
        restartEffect();

        //8方向スパークル、アニメーションなし
        setAnimation( EFFECT_ANIM_NONE);
        setEffectVolume( 5 );
        createEffects();

        //-----------------------
        // 4方向スパークル
        //-----------------------
        //共通設定
        setEffectAttr( EFFECT_SHAPE_SPARKLE_4_DIRECTION, Paint.Style.FILL, EFFECT_ANIM_BLINK_MOVE, true);
        setEffectSize( 200, 100 );
        setEffectColorPtn( COLOR_PTN_MIDDLE_RANDOM);
        setEffectAlpha( 0xDD, 0xEE );

        //4方向スパークル、アニメーションあり
        setAnimation( EFFECT_ANIM_BLINK_MOVE);
        setEffectVolume( 15 );
        createEffects();

        //4方向スパークル、アニメーションなし
        setAnimation( EFFECT_ANIM_NONE);
        setEffectVolume( 5 );
        createEffects();

        //-----------------------
        // 円
        //-----------------------
        setEffectAttr( EFFECT_SHAPE_CIRCLE, Paint.Style.FILL, EFFECT_ANIM_NONE);
        setEffectVolume( 100 );
        setEffectSize( 10, 10 );
        setEffectColorPtn( COLOR_PTN_LIGHT_RANDOM);
        setEffectAlpha( 0x44, 0xDD );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrPolkadotsColorful(){

        //共通設定：楕円
        setEffectVolume( 20 );
        setEffectColorPtn( COLOR_PTN_LIGHT_RANDOM);
        setEffectAttr( EFFECT_SHAPE_OVAL, Paint.Style.FILL, EFFECT_ANIM_NONE, false);

        //楕円、アニメーションなし
        setAnimation( EFFECT_ANIM_NONE);
        setEffectSize( 100, 200 );
        setEffectAlpha( 0x11, 0x22 );
        restartEffect();

        //楕円、アニメーションあり、透明度高め
        setAnimation( EFFECT_ANIM_BLINK_MOVE);
        setEffectSize( 100, 200 );
        setEffectAlpha( 0x22 );
        createEffects();

        //楕円、アニメーションあり、透明度ランダム
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_polkadots_white );
        setAnimation( EFFECT_ANIM_BLINK_MOVE);
        setEffectSize( 200, 400 );
        setEffectAlpha( 0x11, 0x55 );
        createEffects();

        //小さい円、アニメーションなし
        setEffectAttr( EFFECT_SHAPE_CIRCLE, Paint.Style.FILL, EFFECT_ANIM_NONE);
        setEffectVolume( 100 );
        setEffectSize( 10, 10 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color);
        setEffectAlpha( 0x44, 0xDD );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrPolkadotsWhite(){
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_polkadots_white );

        //楕円、アニメーションなし
        setEffectAttr( EFFECT_SHAPE_OVAL, Paint.Style.FILL, EFFECT_ANIM_NONE, false);
        setEffectVolume( 20 );
        setEffectSize( 100, 200 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color);
        setEffectAlpha( 0x11, 0x22 );
        restartEffect();

        //楕円、アニメーションあり、透明度高め
        setEffectAttr( EFFECT_SHAPE_OVAL, Paint.Style.FILL, EFFECT_ANIM_BLINK_MOVE, false);
        setEffectVolume( 20 );
        setEffectSize( 100, 200 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color);
        setEffectAlpha( 0x22 );
        createEffects();

        //楕円、アニメーションあり、透明度ランダム
        setEffectAttr( EFFECT_SHAPE_OVAL, Paint.Style.FILL, EFFECT_ANIM_BLINK_MOVE, false);
        setEffectVolume( 10 );
        setEffectSize( 200, 400 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color);
        setEffectAlpha( 0x11, 0x55 );
        createEffects();

        //小さい円、アニメーションなし
        setEffectAttr( EFFECT_SHAPE_CIRCLE, Paint.Style.FILL, EFFECT_ANIM_NONE);
        setEffectVolume( 100 );
        setEffectSize( 10, 10 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, color);
        setEffectAlpha( 0x44, 0xDD );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrSakuraPink(){
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_sakura );

        //共通設定
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );

        //サクラ
        setEffectAttr( EFFECT_SHAPE_SAKURA, Paint.Style.FILL, EFFECT_ANIM_SPIN, false);
        setEffectVolume( 20 );
        setEffectSize( 100, 200 );
        setEffectAlpha( 0x99, 0xDD );
        restartEffect();

        //ダイヤ
        setEffectAttr( EFFECT_SHAPE_DIA, Paint.Style.FILL, EFFECT_ANIM_NONE, true);
        setEffectVolume( 100 );
        setEffectSize( 30, 20 );
        setEffectAlpha( 0x33, 0xEE );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrFlowerWhite(){
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_flower_white );

        //共通設定
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );

        //花びら
        setEffectAttr( EFFECT_SHAPE_FLOWER, Paint.Style.FILL, EFFECT_ANIM_SPIN, false);
        setEffectVolume( 15 );
        setEffectSize( 100, 200 );
        setEffectAlpha( 0x99, 0xDD );
        restartEffect();

        //ダイヤ
        setEffectAttr( EFFECT_SHAPE_DIA, Paint.Style.FILL, EFFECT_ANIM_NONE, true);
        setEffectVolume( 100 );
        setEffectSize( 30, 20 );
        setEffectAlpha( 0x66, 0xDD );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrFlower2Color(){
        int colorWhite = mAddDistView.getContext().getResources().getColor( R.color.effect_flower_white );
        int colorOrange = mAddDistView.getContext().getResources().getColor( R.color.effect_flower_orange);
        int colorNavy = mAddDistView.getContext().getResources().getColor( R.color.effect_flower_navy);

        //ダイヤ
        setEffectAttr( EFFECT_SHAPE_DIA, Paint.Style.FILL, EFFECT_ANIM_NONE, true);
        setEffectVolume( 100 );
        setEffectSize( 20, 20 );
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorWhite );
        setEffectAlpha( 0x33, 0xDD );
        restartEffect();

        //-----------------------
        // 花びら
        //-----------------------
        //共通設定
        setEffectAttr( EFFECT_SHAPE_FLOWER, Paint.Style.FILL, EFFECT_ANIM_SPIN, false);
        setEffectVolume( 8 );
        setEffectSize( 100, 200 );
        setEffectAlpha( 0x99, 0xDD );

        //花びら、オレンジ系
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorOrange );
        createEffects();

        //花びら、ネイビー系
        setEffectColorPtn( COLOR_PTN_SPECIFY, colorNavy );
        createEffects();
    }

    /*
     * エフェクト情報の設定
     *   aa
     */
    private void setAttrLittleFlowerWhite(){
        int color = mAddDistView.getContext().getResources().getColor( R.color.effect_flower_white );

        //共通設定
        setEffectColorPtn( COLOR_PTN_SPECIFY, color );

        //花びら（小さめ）
        setEffectAttr( EFFECT_SHAPE_FLOWER, Paint.Style.FILL, EFFECT_ANIM_NONE, true);
        setEffectVolume( 60 );
        setEffectSize( 60, 60 );
        setEffectAlpha( 0x22, 0xDD );
        restartEffect();

        //ダイヤ
        setEffectAttr( EFFECT_SHAPE_DIA, Paint.Style.FILL, EFFECT_ANIM_NONE);
        setEffectVolume( 100 );
        setEffectSize( 10, 10 );
        setEffectAlpha( 0x66, 0xDD );
        createEffects();
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
            case EFFECT_ANIM_BLINK:
                applyBlinkEffectAnimation(animationTarget);
                break;

            //------------------------
            // 場所を変えながら明滅
            //------------------------
            case EFFECT_ANIM_BLINK_MOVE:
                applyBlinkMoveEffectAnimation(animationTarget);
                break;

            //------------------------
            // 回転
            //------------------------
            case EFFECT_ANIM_SPIN:
                applySpinEffectAnimation(animationTarget);
                break;

            //------------------------
            // 場所を変えながら回転
            //------------------------
            case EFFECT_ANIM_SPIN_MOVE:
                applySpinMoveEffectAnimation(animationTarget);
                break;

            //------------------------
            // ゆっくりな移動
            //------------------------
            case EFFECT_ANIM_SLOW_MOVE:

                break;

            //------------------------
            // ゆっくりと浮き上がる
            //------------------------
            case EFFECT_ANIM_SLOW_FLOAT:
                applyFloatEffectAnimation(animationTarget);
                break;

            //------------------------
            // ゆっくり落ちる
            //------------------------
            case EFFECT_ANIM_SLOW_FALL:
                applyFallEffectAnimation(animationTarget);
                break;

            //------------------------
            // 枠線のグラデーションの回転
            //------------------------
            case EFFECT_ANIM_STROKE_GRADATION_ROTATE:
                applyStrokeGradationRotateEffectAnimation(animationTarget);
                break;

            //------------------------
            // 拡大と縮小
            //------------------------
            case EFFECT_ANIM_SCALE_UP:
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
