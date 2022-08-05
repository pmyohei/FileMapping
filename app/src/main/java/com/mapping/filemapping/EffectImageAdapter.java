package com.mapping.filemapping;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*
 * エフェクトイメージ用アダプタ
 */
public class EffectImageAdapter extends RecyclerView.Adapter<EffectImageAdapter.ViewHolder> {

    private final EffectManager mEffectManager;
    private final TypedArray mEffectImageResourceName;

    /*
     * ViewHolder：リスト内の各アイテムのレイアウトを含む View のラッパー
     * (固有のためインナークラスで定義)
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_effect;

        /*
         * コンストラクタ
         */
        public ViewHolder(View itemView) {
            super(itemView);
            iv_effect = itemView.findViewById(R.id.iv_effect);
        }

        /*
         * ビューの設定
         *   @para1：エフェクトイメージ
         *   @para2：エフェクトイメージリソースのパス文字列（res/drawable-xxhdpi-v4/effect_heart_float_red.png）
         */
        public void setView( Drawable effectImage, String resPathName ){
            //エフェクトイメージを設定
            iv_effect.setImageDrawable( effectImage );
            //エフェクト種別を取得
            int effectKind = mEffectManager.getEffectKindOnImagePath( resPathName );

            //エフェクトイメージリスナー
            iv_effect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //マップ上にエフェクトを適用
                    mEffectManager.startEffect( effectKind );
                }
            });
        }

    }

    /*
     * コンストラクタ
     */
    public EffectImageAdapter(ViewGroup toEffectView ) {
        //エフェクトイメージリストを保持
        Resources res = toEffectView.getContext().getResources();
        mEffectImageResourceName = res.obtainTypedArray(R.array.effectImageResource);

        //エフェクトマネージャの生成
        mEffectManager = new EffectManager( toEffectView );
    }

    /*
     * ここの戻り値が、onCreateViewHolder()の第２引数になる
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /*
     *　ViewHolderの生成
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //ビューを生成
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_effect, viewGroup, false);
        return new ViewHolder(view);
    }

    /*
     * ViewHolderの設定
     *   表示内容等の設定を行う
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        //ビューの設定
//        viewHolder.setView( mData[i] );
        viewHolder.setView( mEffectImageResourceName.getDrawable(i), mEffectImageResourceName.getString(i) );
    }

    /*
     * データ数取得
     */
    @Override
    public int getItemCount() {
        //表示データ数を返す
        //return mData.length;
        return mEffectImageResourceName.length();
    }

}
