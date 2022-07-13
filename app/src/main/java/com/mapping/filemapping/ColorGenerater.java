package com.mapping.filemapping;

import android.graphics.Color;
import android.util.Log;

import java.util.Random;

/*
 * 自動カラー生成を行う
 */
public class ColorGenerater {

    //適合色角度リスト
    //static float[] matchAngles = {45, 90, 105, 120, 135, 180};
    static float[] matchAngles = {45, 90, 105, 120, 135, 180, 210, 225, 240, 270, 315};

    public ColorGenerater(){}

    /*
     * ランダムなHSVを取得
     */
    static public float[] createRandomHSV() {
        //範囲上限・最小値
        final int RANGE_H = 3601;   //0.0 - 360.0
        final int RANGE_S = 61;     //0.00 - 0.60
        final int RANGE_V = 31;     //0.70 - 1.00  ※最小値加算した場合
        final float MIN_V = 0.7f;

        //色相・彩度・明度
        float[] hsv = new float[3];

        Random random = new Random();
        hsv[0] = random.nextInt(RANGE_H) / 10f;
        hsv[1] = random.nextInt(RANGE_S) / 100f;
        hsv[2] = (random.nextInt(RANGE_V) / 100f) + MIN_V;

        //Log.i("createRandomHSV", "色相=" + hsv[0] + " 彩度=" + hsv[1] + " 明度=" + hsv[2]);

        return hsv;
    }

    /*
     * ベース色に適合する色をランダムで取得
     */
    static public float[] createMatchingHSV( float[] baseHSV ) {
        //色相・彩度・明度
        float[] pairHsv = new float[3];

        //------------------------
        // 彩度・明度
        //------------------------
        //範囲上限・最小値
        final int RANGE_S = 61;     //0.00 - 0.60
        final int RANGE_V = 21;     //0.70 - 1.00  ※最小値加算した場合
        final float MIN_V = 0.8f;
        //彩度／明度はランダム
        Random random = new Random();
        pairHsv[1] = (random.nextInt(RANGE_S) / 100f);
        pairHsv[2] = (random.nextInt(RANGE_V) / 100f) + MIN_V;

        //------------------------
        // 色相
        //------------------------
        //適合色の角度
        final float matchingAngle = getMatchingAngle();

        //指定カラーの指定角度分回転させた色を取得
        pairHsv[0] = baseHSV[0] + matchingAngle;
        if( pairHsv[0] > 360.0f ){
            pairHsv[0] -= 360.0f;
        }

        //Log.i("補色", "色相=" + pairHsv[0] + " 彩度=" + pairHsv[1] + " 明度=" + pairHsv[2]);
        return pairHsv;
    }

    /*
     * 適した色の角度を取得
     *   算出する角度は、完全に適した角度に対してある程度の角度の幅を反映した値をする
     */
    private static float getMatchingAngle(){
        Random random = new Random();

        //基準の適した角度を取得
        int angleNum = matchAngles.length;
        int anglePos = random.nextInt(angleNum);
        float angle = matchAngles[anglePos];

        //基準角度に対する幅を適用
        //※基準角度に対して、「-1.0 ～ 1.0」の角度をずらす
        float angleRange = (random.nextInt(20) - 10) / 10f;
        angle += angleRange;

        //Log.i("色の自動生成チェック", "角度=" + angle);

        return angle;
    }

    /*
     * 基準の色に対して、指定角度回転された位置にある色を取得
     *   para1:基準色
     *   para2:取得する角度
     *   para3:回転方向
     */
    private static float getAnglePositionColor(float baseColor, float angle, int direction){
        float positionColor;
        if( direction == 0 ){
            positionColor = ( (baseColor + angle) > 360f ? baseColor - angle : baseColor + angle );
        } else {
            positionColor = ( (baseColor - angle) < 0f ? baseColor + angle : baseColor - angle );
        }

        return positionColor;
    }

}
