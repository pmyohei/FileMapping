package com.mapping.filemapping;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class EffectAnimation extends Animation {

    //アニメーション適用対象エフェクトビュー
    final private EffectView mEffectView;

    /*
     * コンストラクタ
     */
    public EffectAnimation(EffectView effectView) {
        this.mEffectView = effectView;
    }

    /*
     * 本メソッド
     *   アニメーションデータとして、指定されたエフェクトビューの枠線をグラデーションとして設定する
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        //枠線グラデーションの描画
        mEffectView.drawStrokeGradationEffect( interpolatedTime );

        //Log.i("グラデーション枠アニメ（apply）", "interpolatedTime=" + interpolatedTime);
    }
}
