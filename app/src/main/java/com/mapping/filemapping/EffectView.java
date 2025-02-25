package com.mapping.filemapping;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Build;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class EffectView extends View {

    /*
     * グラデーション座標
     */
    class GradationCoordinate {

        //--------------------------------
        // 座標最大値・進行度
        //--------------------------------
        int width;
        int height;

        //--------------------------------
        // グラデーション移動方向変更の閾値
        //--------------------------------
        final float STEP_CHANGE_INTERVAL = 0.25f;
        final float SHIFT_RIGHT = STEP_CHANGE_INTERVAL;
        final float SHIFT_DOWN = SHIFT_RIGHT + STEP_CHANGE_INTERVAL;
        final float SHIFT_LEFT = SHIFT_DOWN + STEP_CHANGE_INTERVAL;
        final float SHIFT_UP = SHIFT_LEFT + STEP_CHANGE_INTERVAL;

        //--------------------------------
        // グラデーション開始終了座標
        //--------------------------------
        float startX;
        float startY;
        float endX;
        float endY;

        /*
         * コンストラクタ
         */
        public GradationCoordinate(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /*
         * 進行度に応じた座標設定
         */
        public void setGradationCoordinate(float process) {

            if (process <= SHIFT_RIGHT) {
                //-- 開始座標 --//
                //移動方向：右
                startX = width * (process / STEP_CHANGE_INTERVAL);
                startY = 0;
                //-- 終了座標 --//
                //移動方向：左
                endX = width * (1 - (process / STEP_CHANGE_INTERVAL));
                endY = height;

            } else if (process <= SHIFT_DOWN) {
                //-- 開始座標 --//
                //移動方向：下
                startX = width;
                startY = height * ((process - SHIFT_RIGHT) / STEP_CHANGE_INTERVAL);

                //-- 終了座標 --//
                //移動方向：上
                endX = 0;
                endY = height * (1 - ((process - SHIFT_RIGHT) / STEP_CHANGE_INTERVAL));

            } else if (process <= SHIFT_LEFT) {
                //-- 開始座標 --//
                //移動方向：左
                startX = width * (1 - (process - SHIFT_DOWN) / STEP_CHANGE_INTERVAL);
                startY = height;
                //-- 終了座標 --//
                //移動方向：右
                endX = width * ((process - SHIFT_DOWN) / STEP_CHANGE_INTERVAL);
                endY = 0;

            } else {
                //-- 開始座標 --//
                //移動方向：上
                startX = 0;
                startY = height * (1 - ((process - SHIFT_LEFT) / STEP_CHANGE_INTERVAL));
                //-- 終了座標 --//
                //移動方向：下
                endX = width;
                endY = height * ((process - SHIFT_LEFT) / STEP_CHANGE_INTERVAL);
            }
        }
    }

    //未設定
    final static int UNSPECIFIED = -1;

    //エフェクト形状
    private int mEffectShape;
    //エフェクトのPaintStyle
    private Paint.Style mPaintStyle;
    //エフェクトサイズ範囲、最小サイズ
    private int mEffectRangeSize;
    private int mEffectMinSize;
    //ビュー関連
    private int mSize = 0;
    private int mAlpha;
    private int mColor = 0;
    private int mShadowColor;// = getResources().getColor(R.color.yellow);
    private boolean mIsGradation;
    private ArrayList<Path> mPath = new ArrayList<>();
    private ArrayList<Paint> mPaint = new ArrayList<>();
    //グラデーション座標
    GradationCoordinate mGradationCoordinate = null;

    /*
     *　コードから生成
     */
    public EffectView(Context context, int minAlpha, int maxAlpha) {
        super(context);
        mIsGradation = false;
        setEffectAlpha( minAlpha, maxAlpha );
    }

    /*
     * エフェクトの形状設定
     */
    public void setEffectShape(int effectShape, Paint.Style paintStyle) {
        //エフェクト情報
        mEffectShape = effectShape;
        mPaintStyle = paintStyle;
        //描画情報クリア
        mPath.clear();
        mPaint.clear();
    }

    /*
     * エフェクトサイズの設定
     *   para1：エフェクトのサイズ幅
     *   para2：エフェクトの最小サイズ
     */
    public void setEffectSize(int rangeSize, int minSize) {
        mEffectRangeSize = rangeSize;
        mEffectMinSize = minSize;

        //サイズをランダムに設定
        setRandomSize(mEffectRangeSize, mEffectMinSize);
    }

    /*
     * エフェクト透明度の設定
     *   para1：透明度
     */
/*
    private void setEffectAlpha(int alpha) {
        mAlpha = alpha;
    }
*/

    /*
     * エフェクト透明度の設定
     *   para1：最小透明度（0x00～）
     *   para2：最大透明度（～0xFF）
     *
     *   最小～最大透明度の範囲でランダムに透明度を算出し、本エフェクトの透明度とする。
     */
    private void setEffectAlpha(int minAlpha, int maxAlpha) {

        //-------------------------------------
        // 最大値未指定なら、最小透明度を固定で設定
        //-------------------------------------
        if( maxAlpha == UNSPECIFIED ){
            //setEffectAlpha( minAlpha );
            mAlpha = minAlpha;
            return;
        }

        //-------------------------------------
        // 最大値指定ありなら、透明度をランダムで算出
        //-------------------------------------
        //ランダムで算出する範囲
        int range = (maxAlpha - minAlpha) + 1;

        //ランダムに透明度を算出し、最小透明度に上乗せ
        Random random = new Random();
        int alpha = minAlpha + random.nextInt(range);

//        setEffectAlpha( alpha );
        mAlpha = alpha;
    }

    /*
     * エフェクトの傾き
     */
    public void setEffectTilt(boolean isTilt) {
        if (isTilt) {
            setRandomAngle();
        }
    }

    /*
     * Paintにグラデーションを付与するかどうか
     */
    public void setGradation(boolean isGradation) {
        mIsGradation = isGradation;
    }

    /*
     * サイズのランダム設定
     */
    private void setRandomSize(int rangeSize, int minSize) {
        //サイズ範囲
        final int MAX_RANGE = rangeSize + 1;

        //ランダムな値をサイズに適用
        Random random = new Random();
        int offset = random.nextInt(MAX_RANGE);
        mSize = minSize + offset;
    }

    /*
     * エフェクト色の設定
     */
    public void setEffectColor(int color, int shadowColor) {
        mColor = color;

        //API31以降の場合、setShadowLayer()に対しては、透過度を設定色に反映させる形とする
        //※ViewへのsetAlpha()だとShadowLayerに適用されないため
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            int alpha = mAlpha << 24;
            shadowColor = (shadowColor & 0x00FFFFFF) | alpha;
        }

        mShadowColor = shadowColor;
    }


    /*
     * 角度のランダム設定
     */
    private void setRandomAngle() {
        //傾け最大角度
        final int MAX_TILT = 31;

        //ランダムに角度をずらす
        Random random = new Random();
        int tiltAngle = random.nextInt(MAX_TILT) - 15;
        setRotation(tiltAngle);
    }

    /*
     * Paint色設定
     *   ※拡張用として実装しておく
     */
    public void setPaintColor(Paint paint) {

        int colorType = 0;

        switch (colorType) {
            case 0:

                //単色
                paint.setColor(mColor);
                break;

            case 1:
                //グラデーション：LinearGradient：直線
                Shader gradient = new LinearGradient(
                        //グラデーション開始座標
                        0, 0,
                        //グラデーション終了座標
                        0, mSize,
                        mColor, Color.WHITE, Shader.TileMode.MIRROR);
                paint.setShader(gradient);

                break;

            case 2:
                //グラデーション：RadialGradient：放射状
                float halfSize = mSize / 2f;

                gradient = new RadialGradient(
                        halfSize, halfSize,     //中心座標
                        halfSize,               //半径
                        Color.WHITE, getResources().getColor(R.color.transparent_50_white),
                        Shader.TileMode.CLAMP);
                paint.setShader(gradient);

                break;
        }
    }


    /*
     * エフェクトのPathを生成
     */
    private void createEffectPath() {

        //Pathが設定済みなら何もしない
        if (mPath.size() > 0) {
            return;
        }

        //--------------------------------
        // エフェクト形状に応じたPath設定
        //--------------------------------
        switch (mEffectShape) {
            case EffectManager.EFFECT_SHAPE_HEART_NORMAL:
                mPath = createHeartNormalPath();
                break;

            case EffectManager.EFFECT_SHAPE_HEART_THIN:
                mPath = createThinHeartPath();
                break;

            case EffectManager.EFFECT_SHAPE_HEART_INFLATED:
                mPath = createInflatedHeartPath();
                break;

            case EffectManager.EFFECT_SHAPE_TRIANGLE:
                mPath = createTrianglePath();
                break;

            case EffectManager.EFFECT_SHAPE_DIA:
                mPath = createDiaPath();
                break;

            case EffectManager.EFFECT_SHAPE_STAR:
                mPath = createStarPath();
                break;

            case EffectManager.EFFECT_SHAPE_MOON:
                mPath = createMoonPath();
                break;

            case EffectManager.EFFECT_SHAPE_SPARKLE_SHORT:
                mPath = createSparkleShortPath();
                break;

            case EffectManager.EFFECT_SHAPE_SPARKLE_SHIN:
                mPath = createSparkleShinPath();
                break;

            case EffectManager.EFFECT_SHAPE_SPARKLE_LONG:
                mPath = createSparkleLongPath();
                break;

            case EffectManager.EFFECT_SHAPE_SPARKLE_4_DIRECTION:
                mPath = createSparkle4DirectionPath();
                break;

            case EffectManager.EFFECT_SHAPE_SPARKLE_8_DIRECTION:
                mPath = createSparkle8DirectionPath();
                break;

            case EffectManager.EFFECT_SHAPE_SPARKLE_RANDOM:
                mPath = createSparkleRandomPath();
                break;

            case EffectManager.EFFECT_SHAPE_FLOWER:
                mPath = createFlowerPath();
                break;

            case EffectManager.EFFECT_SHAPE_SAKURA:
                mPath = createSakuraPath();
                break;

            case EffectManager.EFFECT_SHAPE_CIRCLE:
                mPath = createCirclePath();
                break;

            case EffectManager.EFFECT_SHAPE_OVAL:
                mPath = createOvalPath();
                break;

            default:
                mPath = createCirclePath();
                break;
        }

        //API28以下は、影の見切れを防ぐために線だけのPathを描画しておく
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            //Shadowレイヤによる影描画エリアの確保
            secureShadowAreaPath();
        }
    }

    /*
     * 影描画エリアの確保：Path
     */
    private void secureShadowAreaPath() {
        Path path = mPath.get(0);

        //描画中心
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        //横線
        path.moveTo(centerX - mSize, centerY);
        path.lineTo(centerX + mSize, centerY);
        path.close();
        //縦線
        path.moveTo(centerX, centerY - mSize);
        path.lineTo(centerX, centerY + mSize);
        path.close();
    }


    /*
     * エフェクトのPaintを生成
     */
    private void createEffectPaint() {

        //Paint設定済みなら何もしない
        if (mPaint.size() > 0) {
            return;
        }

        //--------------------------------
        // エフェクト形状に応じたPath設定
        //--------------------------------
        switch (mEffectShape) {
            case EffectManager.EFFECT_SHAPE_HEART_NORMAL:
            case EffectManager.EFFECT_SHAPE_HEART_THIN:
            case EffectManager.EFFECT_SHAPE_HEART_INFLATED:
            case EffectManager.EFFECT_SHAPE_TRIANGLE:
            case EffectManager.EFFECT_SHAPE_DIA:
            case EffectManager.EFFECT_SHAPE_STAR:
            case EffectManager.EFFECT_SHAPE_SPARKLE_SHORT:
            case EffectManager.EFFECT_SHAPE_SPARKLE_SHIN:
            case EffectManager.EFFECT_SHAPE_SPARKLE_LONG:
            case EffectManager.EFFECT_SHAPE_SPARKLE_RANDOM:
            case EffectManager.EFFECT_SHAPE_FLOWER:
            case EffectManager.EFFECT_SHAPE_SAKURA:
            case EffectManager.EFFECT_SHAPE_CIRCLE:
                //Paint単体生成
                mPaint = createCommonPaint();
                break;

            case EffectManager.EFFECT_SHAPE_SPARKLE_4_DIRECTION:
                //スパークル(4方向)Paint
                mPaint = createSparkle4DirectionPaint();
                break;

            case EffectManager.EFFECT_SHAPE_SPARKLE_8_DIRECTION:
                //スパークル(4方向)Paint
                mPaint = createSparkle8DirectionPaint();
                break;

            default:
                //Paint単体生成
                mPaint = createCommonPaint();
                break;
        }
    }


    /*
     * Path生成
     *  ハート（ノーマル）
     */
    private ArrayList<Path> createHeartNormalPath() {
        //ビューサイズの半分の値
        final float halfSize = mSize / 2f;
        final float quarterSize = halfSize / 2f;

        //指定座標
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;
        float x_m50 = cx - mSize;
        float x_25 = cx - quarterSize;
        float x_50 = cx;
        float x_75 = cx + quarterSize;
        float x_100 = cx + halfSize;
        float y_0 = cy - halfSize;
        float y_30 = cy - (halfSize / 3f);
        float y_50 = cy;
        float y_100 = cy + halfSize;

        //Path生成
        Path path = new Path();
        path.moveTo(x_50, y_100);
        path.cubicTo(x_m50, y_50, x_25, y_0, x_50, y_30);
        path.cubicTo(x_75, y_0, x_100 + halfSize, y_50, x_50, y_100);
        path.close();

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }

    /*
     * Path生成
     *  ハート（細め）
     */
    private ArrayList<Path> createThinHeartPath() {
        //ビューサイズの半分の値
        float halfSize = mSize / 2f;
        float quaterSize = mSize / 4f;

        Path path = new Path();
        path.moveTo(halfSize, mSize);
        path.cubicTo(0, halfSize, quaterSize, 0, halfSize, halfSize);
        path.cubicTo(halfSize + quaterSize, 0, mSize, halfSize, halfSize, mSize);
        path.close();

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }

    /*
     * Path生成
     *  ハート（膨らんだ形状）
     */
    private ArrayList<Path> createInflatedHeartPath() {

        //---------------------
        // 座標
        //---------------------
        float halfSize = mSize / 2f;
        final float center = getWidth() / 2f;
        final float org = center - halfSize;
        final float ctrl_01 = org + (mSize * 0.10f);
        final float ctrl_04 = org + (mSize * 0.4f);
        final float ctrl_06 = org + (mSize * 0.6f);
        final float ctrl_09 = org + (mSize * 0.9f);
        final float top = org + mSize;

        //---------------------
        // ハートを生成
        //---------------------
        Path path = new Path();
        path.moveTo(center, top);
        path.cubicTo(ctrl_04, center, ctrl_01, top, org, center);
        path.cubicTo(org, org, center, org, center, center);
        path.cubicTo(center, org, top, org, top, center);
        path.cubicTo(ctrl_09, top, ctrl_06, center, center, top);
        path.close();

        //---------------------
        // Pathリストに設定
        //---------------------
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }

    /*
     * Path生成
     *  三角形
     */
    private ArrayList<Path> createTrianglePath() {
        //ビューサイズの半分の値
        float halfSize = mSize / 2f;

        Path path = new Path();
        path.moveTo(halfSize, 0);
        path.lineTo(0, mSize);
        path.lineTo(mSize, mSize);
        path.lineTo(halfSize, 0);
        path.close();

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }

    /*
     * Path生成
     *  ダイヤ
     */
    private ArrayList<Path> createDiaPath() {
        //ビューサイズの半分の値
        float halfSize = mSize / 2f;
        //描画中心
        float center = getWidth() / 2f;
        final float org = center - halfSize;
        final float top = center + halfSize;

        //Path生成
        Path path = new Path();
        path.moveTo(center, org);
        path.lineTo(org, center);
        path.lineTo(center, top);
        path.lineTo(top, center);
        path.lineTo(center, org);
        path.close();

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }


    /*
     * Path生成
     *  星
     */
    private ArrayList<Path> createStarPath() {
        //ビューサイズの半分の値
        float halfSize = mSize / 2f;

        //描画情報
        int points = 5;                    //頂点数（普通の星のため、5つ設定）
        int startAngle = -90;              //90度の位置から頂点を描画
        float viewRadius = halfSize / 2f;  //基本半径
        float outerRadius = halfSize / 2f; //基本半径から頂点までの長さ
        float innerRadius = 0;             //基本半径から内側までの長さ

        //指定頂点を辿るPathを生成
        Path path = getSparklePath(points, startAngle, (viewRadius + outerRadius), (viewRadius - innerRadius));

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }

    /*
     * Path生成
     *  月
     */
    private ArrayList<Path> createMoonPath() {
        //ビューサイズの半分の値
        float halfSize = mSize / 2f;
        float qSize = halfSize / 2f;

        //描画中心
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        //各軸の位置（割合）
        float x_25 = centerX - qSize;
        float x_60 = centerX + halfSize * 0.2f;
        float x_80 = centerX + halfSize * 0.8f;
        float y_10 = centerY - halfSize * 0.9f;
        float y_25 = centerY - qSize;
        float y_75 = centerY + qSize;
        float y_90 = centerY + halfSize * 0.9f;

        //Pathの生成
        Path path = new Path();
        path.moveTo(x_25, y_25);
        path.cubicTo(x_60, y_25, x_60, y_75, x_25, y_75);
        path.cubicTo(x_80, y_90, x_80, y_10, x_25, y_25);
        path.close();

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }


    /*
     * Path生成
     *  スパークル：短め
     */
    private ArrayList<Path> createSparkleShortPath() {
        //ビューサイズの半分の値
        float halfSize = mSize / 2f;

        int points = 4;                                   //外側の頂点数（普通の星の場合は5つ）
        int startAngle = -90;                             //90度の位置から頂点を描画
        float viewRadius = halfSize / 4f;                 //基本半径
        float outerRadius = halfSize - (halfSize / 4f);   //基本半径に加算
        float innerRadius = 0;                            //基本半径から減算

        //指定頂点を辿るPathを生成
        Path path = getSparklePath(points, startAngle, (viewRadius + outerRadius), (viewRadius - innerRadius));

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }

    /*
     * Path生成
     *  スパークル：細め
     */
    private ArrayList<Path> createSparkleShinPath() {
        //ビューサイズの半分の値
        float halfSize = mSize / 2f;

        int startAngle = -90;                           //90度の位置から頂点を描画
        float viewRadius = halfSize / 8f;               //基本半径
        float outerRadius = halfSize - (halfSize / 8f); //基本半径に加算
        float innerRadius = 0;                          //基本半径から減算

        //頂点の描画
        Path path = getCrossShinSparkle(startAngle, (viewRadius + outerRadius), (viewRadius - innerRadius));

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }

    /*
     * Path生成
     *  スパークル：不規則
     */
    private ArrayList<Path> createSparkleRandomPath() {

        int points = 9;                           //外側の頂点数（普通の星の場合は5つ）
        int startAngle = -90;                     //90度の位置から頂点を描画
        float viewRadius = mSize / 8f;            //基本半径
        float innerRadius = viewRadius * 0.6f;    //基本半径から減算

        //頂点の描画
        Path path = setVertexRandomLen(points, startAngle, viewRadius, (viewRadius - innerRadius));

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }

    /*
     * Path生成
     *  スパークル：長め
     */
    private ArrayList<Path> createSparkleLongPath() {
        //ビューサイズの半分の値
        float halfSize = mSize / 2f;
        final int CENTER_CIRCLE_RADIUS = 4;
        final int POS_DIFF = CENTER_CIRCLE_RADIUS / 2;

        Path path = new Path();
        //中央円
        path.addCircle(halfSize, halfSize, CENTER_CIRCLE_RADIUS, Path.Direction.CW);
        //上
        path.moveTo(halfSize - POS_DIFF, halfSize);
        path.lineTo(halfSize, 0);
        path.lineTo(halfSize + POS_DIFF, halfSize);
        //下
        path.lineTo(halfSize, mSize);
        path.lineTo(halfSize - POS_DIFF, halfSize);
        path.close();
        //左
        path.moveTo(halfSize, halfSize + POS_DIFF);
        path.lineTo(0, halfSize);
        path.lineTo(halfSize, halfSize - POS_DIFF);
        //右
        path.lineTo(mSize, halfSize);
        path.lineTo(halfSize, halfSize + POS_DIFF);
        path.close();

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }

    /*
     * Path生成
     *  スパークル：かなり長め
     */
    private ArrayList<Path> createSparkle4DirectionPath() {

        //--------------------------------------------
        // サイズ関連
        //--------------------------------------------
        //円半径
        final int CENTER_CIRCLE_RADIUS = mSize / 20;
        //十字の根元の幅
        final int POS_DIFF = (int) (CENTER_CIRCLE_RADIUS / 4f);
        //中心座標
        final float centerX = getWidth() / 2f;
        final float centerY = getHeight() / 2f;

        //--------------------------------------------
        // 中央円
        //--------------------------------------------
        Path circlePath = new Path();
        circlePath.addCircle(centerX, centerY, CENTER_CIRCLE_RADIUS, Path.Direction.CW);

        //--------------------------------------------
        // 十字
        //--------------------------------------------
        //頂点座標
        final float sparcleLen = getWidth() / 2f;
        final float x_0 = centerX - sparcleLen;
        final float x_100 = centerX + sparcleLen;
        final float y_0 = centerY - sparcleLen;
        final float y_100 = centerY + sparcleLen;

        Path sparkleCrossPath = new Path();
        //上
        sparkleCrossPath.moveTo(centerX - POS_DIFF, centerY);
        sparkleCrossPath.lineTo(centerX, y_0);
        sparkleCrossPath.lineTo(centerX + POS_DIFF, centerY);
        //下
        sparkleCrossPath.lineTo(centerX, y_100);
        sparkleCrossPath.lineTo(centerX - POS_DIFF, centerY);
        sparkleCrossPath.close();
        //左
        sparkleCrossPath.moveTo(centerX, centerY + POS_DIFF);
        sparkleCrossPath.lineTo(x_0, centerY);
        sparkleCrossPath.lineTo(centerX, centerY - POS_DIFF);
        //右
        sparkleCrossPath.lineTo(x_100, centerY);
        sparkleCrossPath.lineTo(centerX, centerY + POS_DIFF);
        sparkleCrossPath.close();

        //--------------------------------------------
        // Pathリストに追加
        //--------------------------------------------
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(circlePath);
        pathes.add(sparkleCrossPath);

        return pathes;
    }

    /*
     * Path生成
     *  スパークル：中央に円。十字／斜め十字。
     */
    private ArrayList<Path> createSparkle8DirectionPath() {
        //ビューサイズの半分の値
        float halfSize = mSize / 2f;

        //--------------------------------------------
        // オブジェクトのPathを生成
        //--------------------------------------------
        //中心円の生成
        Path circlePath = new Path();
        circlePath.addCircle(halfSize, halfSize, halfSize / 3, Path.Direction.CW);
        //十字（細め）
        Path sparcleCrossPath = getCrossShinSparkle(-90, halfSize, halfSize * 0.06f);
        //斜め十字（細め）
        Path sparcleDiagonalPath = getCrossShinSparkle(-45, halfSize / 2, halfSize * 0.06f);

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(circlePath);
        pathes.add(sparcleCrossPath);
        pathes.add(sparcleDiagonalPath);

        return pathes;
    }

    /*
     * Path生成
     *   花びら
     */
    private ArrayList<Path> createFlowerPath() {
        //花びら１片の長さ
        float petalLen = mSize / 2f;
        float qSize = petalLen / 2f;
        //描画中心
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        //各軸の位置（割合）
        float x_0 = centerX - petalLen;
        float x_25 = centerX - qSize;
        float x_75 = centerX + qSize;
        float x_100 = centerX + petalLen;
        float y_0 = centerY - petalLen;
        float y_25 = centerY - qSize;
        float y_75 = centerY + qSize;
        float y_100 = centerY + petalLen;

        Path path = new Path();
        path.moveTo(centerX, centerY);
        //上の花びら
        path.quadTo(x_25, y_25, centerX, y_0);       //左
        path.quadTo(x_75, y_25, centerX, centerY);    //右
        //左の花びら
        path.quadTo(x_25, y_25, x_0, centerY);       //上
        path.quadTo(x_25, y_75, centerX, centerY);    //下
        //下の花びら
        path.quadTo(x_25, y_75, centerX, y_100);      //左
        path.quadTo(x_75, y_75, centerX, centerY);    //右
        //右の花びら
        path.quadTo(x_75, y_75, x_100, centerY);      //下
        path.quadTo(x_75, y_25, centerX, centerY);    //上

        path.close();

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }

    /*
     * Path生成
     *   桜の花びら
     */
    private ArrayList<Path> createSakuraPath() {
        //ビューサイズの半分の値
        float halfSize = mSize / 2f;

        //角度差分：基準の角度に対して、各頂点の角度差分
        final int LEFT_CTRL_ANGLE = -40;
        final int RIGHT_CTRL_ANGLE = 40;
        final int CENTER_ANGLE = 0;
        final int LEFT_TIP_ANGLE = -10;
        final int RIGHT_TIP_ANGLE = 10;

        //花びらの幅
        float petalWidth = mSize / 3f;
        //花びらの幅の半分
        float halfPetalWidth = petalWidth / 2f;

        //描画中心座標
        final float cx = getWidth() / 2f;
        final float cy = getHeight() / 2f;

        //各点までの長さ
        final float petalHeight = halfSize;
        final float innerHeight = halfSize - halfPetalWidth;
        final float ctrlHeight = halfSize / 2f;

        //ビューの中心へ移動
        Path path = new Path();
        path.moveTo(cx, cy);

        //円の半径
        float halfPI = (float) Math.PI / 180f;

        //描画開始情報
        int startAngle = -90;     //90度の位置から頂点を描画
        int points = 5;           //花びらの数

        //各頂点間の角度
        float oneAngle = 360f / points;
        for (int i = 0; i < points; i++) {
            //描画する花びらの角度
            float angle = startAngle + (oneAngle * i);

            //--------------------------------------------
            // 各頂点・制御点のラジアン
            //--------------------------------------------
            //制御点（左・右）
            float leftCtrlRadians = ((angle + LEFT_CTRL_ANGLE) * halfPI);
            float rightCtrlRadians = ((angle + RIGHT_CTRL_ANGLE) * halfPI);
            //中央の切れ込み
            float centerRadians = ((angle + CENTER_ANGLE) * halfPI);
            //花びら先端（左・右）
            float leftTipRadians = ((angle + LEFT_TIP_ANGLE) * halfPI);
            float rightTipRadians = ((angle + RIGHT_TIP_ANGLE) * halfPI);

            //--------------------------------------------
            // 各点の高さに対する、指定角度を反映した値を算出
            //--------------------------------------------
            //制御点（左）
            float tranceLeftCtrlX = (float) (ctrlHeight * Math.cos(leftCtrlRadians));
            float tranceLeftCtrlY = (float) (ctrlHeight * Math.sin(leftCtrlRadians));
            //制御点（右）
            float tranceRightCtrlX = (float) (ctrlHeight * Math.cos(rightCtrlRadians));
            float tranceRightCtrlY = (float) (ctrlHeight * Math.sin(rightCtrlRadians));
            //中央の切れ込み点
            float tranceInnerX = (float) (innerHeight * Math.cos(centerRadians));
            float tranceInnerY = (float) (innerHeight * Math.sin(centerRadians));
            //花びら先端（左）
            float tranceLeftTipX = (float) (petalHeight * Math.cos(leftTipRadians));
            float tranceLeftTipY = (float) (petalHeight * Math.sin(leftTipRadians));
            //花びら先端（右）
            float tranceRightTipX = (float) (petalHeight * Math.cos(rightTipRadians));
            float tranceRightTipY = (float) (petalHeight * Math.sin(rightTipRadians));

            //--------------------------------------------
            // 中心点を基準にした座標を計算
            //--------------------------------------------
            //制御点（左）の座標
            float leftCtrlPosX = cx + tranceLeftCtrlX;
            float leftCtrlPosY = cy + tranceLeftCtrlY;
            //制御点（右）の座標
            float rightCtrlPosX = cx + tranceRightCtrlX;
            float rightCtrlPosY = cy + tranceRightCtrlY;
            //中央の切れ込み点の座標
            float innerPosX = cx + tranceInnerX;
            float innerPosY = cy + tranceInnerY;
            //花びら先端（左）の座標
            float leftTipPosX = cx + tranceLeftTipX;
            float leftTipPosY = cy + tranceLeftTipY;
            //花びら先端（右）の座標
            float rightTipPosX = cx + tranceRightTipX;
            float rightTipPosY = cy + tranceRightTipY;

            //--------------------------------------------
            // 花びらを描画
            //--------------------------------------------
            //花びらの左側：下の先端　→　先端（左）
            path.quadTo(leftCtrlPosX, leftCtrlPosY, leftTipPosX, leftTipPosY);
            //内側の切れ込み（左）：先端（左）　→　中央の切れ込み
            path.lineTo(innerPosX, innerPosY);
            //内側の切れ込み（右）：中央の切れ込み　→　先端（右）
            path.lineTo(rightTipPosX, rightTipPosY);
            //花びらの右側：先端（右）　→　下の先端
            path.quadTo(rightCtrlPosX, rightCtrlPosY, cx, cy);
        }
        path.close();

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }

    /*
     * Path生成
     *   円
     */
    private ArrayList<Path> createCirclePath() {
        //ビューサイズの半分の値
        float halfSize = mSize / 2f;

        //円形
        Path path = new Path();
        path.addCircle(getWidth() / 2f, getHeight() / 2f, halfSize, Path.Direction.CW);

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }

    /*
     * Path生成
     *   楕円形
     */
    private ArrayList<Path> createOvalPath() {
        //ビューサイズの半分の値
        float halfSize = mSize / 2f;

        //座標
        final float centerX = getWidth() / 2f;
        final float centerY = getHeight() / 2f;
        final float left = centerX - halfSize * 0.5f;
        final float top = centerY - halfSize;
        final float right = centerX + halfSize * 1.5f;
        final float bottom = centerY + halfSize * 1.4f;

        //楕円形
        Path path = new Path();
        path.addOval( left, top, right, bottom, Path.Direction.CW );

        //Pathリストに設定
        ArrayList<Path> pathes = new ArrayList<>();
        pathes.add(path);

        return pathes;
    }


    /*
     * スパークルのPath取得
     *   para1：頂点の数
     *   para2：頂点描画開始角度（右水平方向0度・時計回り）
     *   para3：外側の頂点までの長さ
     *   para4：内側の頂点までの長さ
     */
    private Path getSparklePath(int points, float startAngle, float outerDist, float innerDist) {

        //ビューサイズの半分の値
        //中心座標
        final float centerX = getWidth() / 2f;
        final float centerY = getHeight() / 2f;
        //頂点の角度間隔
        final float oneAngle = 360f / points;
        //円の半径
        final float halfPI = (float) Math.PI / 180f;

        //パス
        Path path = new Path();

        //頂点の数だけ処理
        for (int i = 0; i < points; i++) {
            //--------------------
            // 角度情報
            //--------------------
            float angle = startAngle + (oneAngle * i);
            float radians = (float) (angle * halfPI);
            float halfRadians = (float) ((angle + (oneAngle / 2f)) * halfPI);

            //--------------------
            // 外側の頂点
            //--------------------
            //「頂点までの距離」に角度を反映
            float offsetX = (float) (outerDist * Math.cos(radians));
            float offsetY = (float) (outerDist * Math.sin(radians));
            //中心点を基準に頂点の座標を算出
            float x = centerX + offsetX;
            float y = centerY + offsetY;

            //--------------------
            // Pathに外側頂点を反映
            //--------------------
            if (i == 0) {
                //移動
                path.moveTo(x, y);
            } else {
                //ライン描画：内側の頂点　→　外側の頂点
                path.lineTo(x, y);
            }

            //--------------------
            // 内側の頂点
            //--------------------
            //「頂点までの距離」に角度を反映
            offsetX = (float) (innerDist * Math.cos(halfRadians));
            offsetY = (float) (innerDist * Math.sin(halfRadians));
            //中心点を基準に頂点の座標を算出
            x = centerX + offsetX;
            y = centerY + offsetY;

            //ライン描画：外側の頂点　→　内側の頂点
            path.lineTo(x, y);
        }

        return path;
    }

    /*
     * スパークルのPath取得
     * 　十字で細さの指定が可能なスパークルPathを生成する。
     *   para1：頂点描画開始角度（右水平方向0度・時計回り）
     *   para2：外側の頂点までの長さ
     *   para3：内側の頂点までの長さ ※本値が小さい程、細いスパークルとなる
     */
    private Path getCrossShinSparkle(float startAngle, float outerDist, float innerDist) {
        //ビューサイズの半分の値
        final float halfSize = mSize / 2f;
        //頂点数
        final int VERTEX_NUM = 4;
        //頂点数
        final int INNER_VERTEX_ANGLE_OFFSET = 45;
        //中心座標
        final float centerX = halfSize;
        final float centerY = halfSize;
        //頂点間の角度間隔
        final float oneAngle = 360f / VERTEX_NUM;
        //円の半径
        final float halfPI = (float) Math.PI / 180f;

        //パス
        Path path = new Path();

        //頂点の数だけ処理
        for (int i = 0; i < VERTEX_NUM; i++) {
            //--------------------
            // 角度情報
            //--------------------
            float angle = startAngle + (oneAngle * i);
            float radians = angle * halfPI;

            //--------------------
            // 外側の頂点
            //--------------------
            //外側の頂点の座標を算出
            float offsetX = (float) (outerDist * Math.cos(radians));
            float offsetY = (float) (outerDist * Math.sin(radians));
            float x = centerX + offsetX;
            float y = centerY + offsetY;

            //--------------------
            // Pathに外側頂点を反映
            //--------------------
            if (i == 0) {
                //初めは移動
                path.moveTo(x, y);
            } else {
                //ライン描画
                path.lineTo(x, y);
            }

            //--------------------
            // 内側の頂点
            //--------------------
            //内側の頂点は、外側の頂点から45度ずらした位置とする
            float angleIn = angle + INNER_VERTEX_ANGLE_OFFSET;
            float radiansIn = angleIn * halfPI;
            //内側頂点の座標を算出
            offsetX = (float) (innerDist * Math.cos(radiansIn));
            offsetY = (float) (innerDist * Math.sin(radiansIn));
            x = centerX + offsetX;
            y = centerY + offsetY;

            //ライン描画
            path.lineTo(x, y);
        }

        return path;
    }

    /*
     * スパークルのPath取得
     *   スパークルの長さはランダムとする
     *   para1：頂点の数
     *   para2：頂点描画開始角度（右水平方向0度・時計回り）
     *   para3：外側の頂点までの長さ
     *   para4：内側の頂点までの長さ
     */
    private Path setVertexRandomLen(int points, float startAngle, float viewRadius, float innerDist) {
        //ビューサイズの半分の値
        final float halfSize = mSize / 2f;
        //中心座標
        final float centerX = halfSize;
        final float centerY = halfSize;
        //頂点の角度間隔
        final float oneAngle = 360f / points;
        //円の半径
        final float halfPI = (float) Math.PI / 180f;

        //光の長さの範囲
        int range = (int) halfSize * 100;
        Random random = new Random();

        //Path
        Path path = new Path();

        //--------------------
        // 頂点分Path情報を設定
        //--------------------
        for (int i = 0; i < points; i++) {

            //--------------------
            // 角度情報
            //--------------------
            float angle = startAngle + (oneAngle * i);
            float radians = angle * halfPI;
            float halfRadians = (angle + (oneAngle / 2f)) * halfPI;

            //光の長さをランダムに生成
            float outerRadius = random.nextInt(range + 1) / 100f;

            //--------------------
            // 外側の頂点
            //--------------------
            float offsetX = (float) ((viewRadius + outerRadius) * Math.cos(radians));
            float offsetY = (float) ((viewRadius + outerRadius) * Math.sin(radians));
            float x = centerX + offsetX;
            float y = centerY + offsetY;

            //--------------------
            // Pathに外側頂点を反映
            //--------------------
            if (i == 0) {
                //初めは移動
                path.moveTo(x, y);
            } else {
                //ライン描画
                path.lineTo(x, y);
            }

            //--------------------
            // 内側の頂点
            //--------------------
            offsetX = (float) (innerDist * Math.cos(halfRadians));
            offsetY = (float) (innerDist * Math.sin(halfRadians));
            x = centerX + offsetX;
            y = centerY + offsetY;

            //ライン描画
            path.lineTo(x, y);
        }
        path.close();

        return path;
    }

    /*
     * Paint生成
     *   共通のPaint情報設定。設定するPaintは1つのみ。
     */
    private ArrayList<Paint> createCommonPaint() {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShadowLayer(mSize / 8f, 0, 0, mShadowColor);

        //Paintの色情報設定
        setPaintColor(paint);
        paint.setAlpha(mAlpha);   //※色設定後に行う必要がある

        //----------------------------------------
        // Paint.Styleの適用
        //----------------------------------------
        if (mPaintStyle == Paint.Style.FILL) {
            paint.setStyle(Paint.Style.FILL);
        } else {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
        }

        //グラデーションの付与
        if (mIsGradation) {
            addGradation(paint);
        }

        ArrayList<Paint> paints = new ArrayList<>();
        paints.add(paint);
        return paints;
    }

    /*
     * Paintにグラデーションを付与
     */
    private void addGradation( Paint paint ) {
        //ビューサイズの半分の値
        final float halfSize = mSize / 2f;
        //描画中心
        float centerX  = getWidth() / 2f;
        float centerY  = getHeight() / 2f;

        int[] colors = new int[] {
                mColor,
                Color.TRANSPARENT,
        };

        float[] stops = new float[] {
                0.0f,
                0.7f,
                1.0f,
        };

        //中心円のグラデーション
        Shader circleGradient = new RadialGradient(
                centerX, centerY,
                halfSize,
                colors,
                //stops,
                null,
                Shader.TileMode.CLAMP);

        paint.setShader(circleGradient);
    }


    /*
     * Paint生成
     *  　スパークル(かなり長め)Paint生成
     */
    private ArrayList<Paint> createSparkle4DirectionPaint() {

        //描画中心
        float centerX  = getWidth() / 2f;
        float centerY  = getHeight() / 2f;
        //円半径
        final int CENTER_CIRCLE_RADIUS = mSize / 20;

        //グラデーション色：中心円
        int[] colors = new int[] {
                Color.WHITE,
                Color.TRANSPARENT,
        };
        float[] stops = new float[] {
                0.5f,
                1.0f,
        };

        //グラデーション色：十字
        int[] spklColors = new int[] {
                Color.WHITE,
                mColor,
                Color.TRANSPARENT,
        };
        float[] spklStops = new float[] {
                0.0f,
                0.5f,
                0.9f,
        };

        //--------------------------------------------
        // グラデーションの生成
        //--------------------------------------------
        //中心円のグラデーション
        Shader circleGradient = new RadialGradient(
                centerX, centerY,         //中心座標
                CENTER_CIRCLE_RADIUS,
                colors,
                stops,
                Shader.TileMode.CLAMP);

        //十字のグラデーション
        Shader sparkleGradient = new RadialGradient(
                centerX, centerY,         //中心座標
                getWidth() / 2f,                   //半径
                spklColors,
                spklStops,
                Shader.TileMode.CLAMP);

        //--------------------------------------------
        // Paint情報生成
        //--------------------------------------------
        //中心円
        Paint CirclePaint = new Paint();
        CirclePaint.setAntiAlias(true);
        CirclePaint.setAlpha(0xFF);
        CirclePaint.setStyle(Paint.Style.FILL);
        CirclePaint.setShadowLayer(mSize / 8f, 0, 0, Color.WHITE);
        CirclePaint.setShader(circleGradient);

        //十字
        Paint sparklePaint = new Paint();
        sparklePaint.setAntiAlias(true);
        sparklePaint.setAlpha(0xFF);
        sparklePaint.setStyle(Paint.Style.FILL);
        sparklePaint.setShadowLayer(mSize / 8f, 0, 0, Color.WHITE);
        sparklePaint.setShader(sparkleGradient);

        //--------------------------------------------
        // Paintリストに追加
        //--------------------------------------------
        ArrayList<Paint> paints = new ArrayList<>();
        paints.add(CirclePaint);         //中心円
        paints.add(sparklePaint);        //十字

        return paints;
    }

    /*
     * Paint生成
     *  　十字スパークルPaint生成
     */
    private ArrayList<Paint> createSparkle8DirectionPaint() {

        //ビューサイズの半分の値
        final float halfSize = mSize / 2f;

        //--------------------------------------------
        // グラデーションの生成
        //--------------------------------------------
        //中心円のグラデーション
        Shader circleGradient = new RadialGradient(
                halfSize, halfSize,         //中心座標
                halfSize / 3,         //半径
                //getResources().getColor(R.color.transparent_50_white), Color.TRANSPARENT,
                mColor, Color.TRANSPARENT,
                Shader.TileMode.CLAMP);

        //十字のグラデーション
        Shader sparkleGradient = new RadialGradient(
                halfSize, halfSize,         //中心座標
                halfSize,                   //半径
                mColor, Color.TRANSPARENT,
                Shader.TileMode.CLAMP);

        //--------------------------------------------
        // Paint情報生成
        //--------------------------------------------
        //中心円
        Paint CirclePaint = new Paint();
        CirclePaint.setAntiAlias(true);
        CirclePaint.setAlpha(0xFF);
        CirclePaint.setStyle(Paint.Style.FILL);
        CirclePaint.setShadowLayer(mSize / 8f, 0, 0, mShadowColor);
        CirclePaint.setShader(circleGradient);

        //十字／斜め十字
        Paint sparklePaint = new Paint();
        sparklePaint.setAntiAlias(true);
        sparklePaint.setAlpha(0xFF);
        sparklePaint.setStyle(Paint.Style.FILL);
        sparklePaint.setShadowLayer(mSize / 8f, 0, 0, mShadowColor);
        sparklePaint.setShader(sparkleGradient);

        //--------------------------------------------
        // Paintリストに追加
        //--------------------------------------------
        ArrayList<Paint> paints = new ArrayList<>();
        paints.add(CirclePaint);         //中心円
        paints.add(sparklePaint);        //十字
        paints.add(sparklePaint);        //斜め十字

        return paints;
    }

    /*
     * エフェクトオブジェクトの描画
     */
    private void drawEffectPath(Canvas canvas) {

        int pathNum = mPath.size();

        //--------------------
        // Pathなし
        //--------------------
        //フェールセーフとして何もしない
        if (pathNum == 0) {
            return;
        }

        //--------------------
        // Pathが1つのみ
        //--------------------
        if (pathNum == 1) {
            canvas.drawPath(mPath.get(0), mPaint.get(0));
            return;
        }

        //--------------------
        // Pathが複数あり
        //--------------------
        //Pathに対応するPaintがなければ何もしない（フェールセーフ）
        if (pathNum != mPaint.size()) {
            return;
        }

        //Pathの数だけ保存
        for (int i = 0; i < pathNum; i++) {
            //描画
            Path path = mPath.get(i);
            Paint paint = mPaint.get(i);
            canvas.drawPath(path, paint);
            //Path毎に保存
            canvas.save();
        }
        //保存したPathを全て復元
        canvas.restore();
    }


    /*
     * エフェクトアニメーションプロパティ
     * 　回転　android:propertyName="spinEffect"
     */
    public void setSpinEffect(float rotate) {
        //角度に反映
        setRotation(rotate);
    }

    /*
     * エフェクトアニメーションプロパティ
     * 　拡大　android:propertyName="scale"
     */
    public void setScale(float value) {
        setScaleX( value );
        setScaleY( value );
    }

    /*
     * エフェクトアニメーションプロパティ
     * 　拡大と透過　android:propertyName="scaleAlpha"
     */
    public void setScaleAlpha(float value) {
        this.setScale( value );

        //表示開始値
        final float START_ALPHA = 0.6f;

        //透明度設定値の算出
        float alpha = 0.0f;
        if( value >= START_ALPHA ){
            //----------------------------------
            // 例）表示開始値：0.2f
            //     value - 0.2f
            //   ーーーーーーーーーーー * 1.0 + 0.0f
            //   0.8f(＝1.0f - 0.2f)
            //----------------------------------
            alpha = (value - START_ALPHA) / (1.0f - START_ALPHA);
        }
        setAlpha( alpha );
    }

    /*
     * エフェクトアニメーションプロパティ
     * 　上昇と透過　android:propertyName="floatAlpha"
     *   透明の制御は、引数の値と「表示折り返し値」を元に行う
     */
    public void setFloatAlpha(float value) {
        //上昇（負の値：上へ移動）
        this.setTranslationY( (value * 400) * -1 );

        //---------------------------
        // 透明制御
        //---------------------------
        final float WRAP_ALPHA = 0.5f;
        final float MAX_ALPHA = 1.0f;
        float alpha = getWrapValue( value, WRAP_ALPHA, MAX_ALPHA );
        setAlpha( alpha );
    }

    /*
     * エフェクトアニメーションプロパティ
     * 　下降と透過　android:propertyName="fallAlpha"
     *   透明の制御は、引数の値と「表示折り返し値」を元に行う
     */
    public void setFallAlpha(float value) {
        //下降（正の値：下へ移動）
        this.setTranslationY( value * 500 );

        //---------------------------
        // 透明制御
        //---------------------------
        final float WRAP_ALPHA = 0.5f;
        final float MAX_ALPHA = 1.0f;
        float alpha = getWrapValue( value, WRAP_ALPHA, MAX_ALPHA );
        setAlpha( alpha );
    }

    /*
     * 折り返し込みの値を取得
     *   para1：変換値
     *   para2：折り返し値
     *   para3：変換後最大値
     */
    private float getWrapValue( final float org, final float wrapValue, final float max ){

        float converted;
        if( org <= wrapValue ){
            //0.0f ～ 「折り返し値」→ 0.0f ～ max
            converted = org / (max - wrapValue);
        } else {
            //「折り返し値」～ 0.0f → 1.0f ～ max
            float sub = (org - wrapValue) / wrapValue;
            converted = max - sub;
        }

        return converted;
    }


    /*
     * エフェクトアニメーション
     * 　枠線グラデーションを回っているように見せるアニメーション
     *   para1：アニメーション進行度（0.0～1.0）
     *
     *   ！特記事項！
     * 　　　本メソッドはAnimationクラスの「applyTransformation」からコールされる
     */
    public void drawStrokeGradationEffect(float process) {

        //----------------------------------------
        // 進行度に応じたグラデーション座標を計算
        //----------------------------------------
        if( mGradationCoordinate == null ){
            //未生成なら生成
            mGradationCoordinate = new GradationCoordinate(mSize, mSize);
        }
        mGradationCoordinate.setGradationCoordinate( process );

        //----------------------------------------
        // グラデーション生成
        //----------------------------------------
        Shader gradient = new LinearGradient(
                mGradationCoordinate.startX, mGradationCoordinate.startY,             //グラデーション開始座標
                mGradationCoordinate.endX, mGradationCoordinate.endY,                 //グラデーション終了座標
                Color.RED,
                Color.WHITE,
                Shader.TileMode.MIRROR);

        //----------------------------------------
        // グラデーションをPaintに適用
        //----------------------------------------
        if( mPaint.size() == 0 ){
            //Paintが生成されていないタイミングであれば、何もしない
            return;
        }
        Paint paint = mPaint.get(0);
        paint.setShader(gradient);

        invalidate();
    }


    /*
     * onDraw
     *   エフェクトの描画
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //エフェクト形状の設定
        createEffectPath();
        //エフェクト描画Paintの設定
        createEffectPaint();
        //エフェクトPathの描画
        drawEffectPath( canvas );
    }

    /*
     * onMeasure
     *   本ビューへのサイズ反映を目的にオーバーライド
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //設定サイズを保持していれば、反映する
        if (mSize > 0) {
            //シャドウレイヤの描画範囲を確保するため、canvasにて描画する形状よりも大きいビューサイズとする
            final int SECURE_SHADOW_AREA_SCALE = 2;
            int secureShadowAreaSize = mSize * SECURE_SHADOW_AREA_SCALE;
            setMeasuredDimension(secureShadowAreaSize, secureShadowAreaSize );
        }
    }


}
