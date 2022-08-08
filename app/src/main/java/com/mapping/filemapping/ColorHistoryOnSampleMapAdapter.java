package com.mapping.filemapping;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ColorHistoryOnSampleMapAdapter extends RecyclerView.Adapter<ColorHistoryOnSampleMapAdapter.ViewHolder> {

    //カラーパターン
    public static final int COLOR_2 = 2;

    private final List<String[]> mData;
    private final ColorSampleMapView mfl_sampleMap;
    private final ViewGroup mll_colorParent;
    private final int mPattern;

    /*
     * ViewHolder：リスト内の各アイテムのレイアウトを含む View のラッパー
     * (固有のためインナークラスで定義)
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private final ColorSampleMapView mfl_sampleMap;
        private final ViewGroup mll_colorParent;
        private final LinearLayout ll_colorItem;

        /*
         * コンストラクタ
         */
        public ViewHolder(View itemView, View view, ViewGroup colorParent) {
            super(itemView);

            mfl_sampleMap = (ColorSampleMapView)view;
            mll_colorParent = colorParent;
            ll_colorItem = itemView.findViewById(R.id.ll_colorItem);
        }

        /*
         * ビューの設定
         */
        public void setView( String[] colorPettern ){

            Context context = mfl_sampleMap.getContext();

            int count = 0;
            for( String color: colorPettern ){
                //カラーを設定するビューID
                String idStr = "v_color" + Integer.toString( count );
                int v_id = context.getResources().getIdentifier( idStr, "id", context.getPackageName() );

                //色を整数値に変換
                int colorValue = Color.parseColor( color );

                //カラーを設定
                MaterialCardView v = ll_colorItem.findViewById( v_id );
                ColorStateList colorState = new ColorStateList(
                        new int[][] {
                                new int[]{ android.R.attr.state_checked},
                                new int[]{ -android.R.attr.state_checked},
                        },
                        new int[] {
                                colorValue,
                                colorValue,
                        }
                );
                v.setCardForegroundColor( colorState );

                count++;
            }

            //色履歴クリックリスナー
            ll_colorItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //履歴をサンプルマップに反映
                    mfl_sampleMap.setColorPattern( colorPettern );
                    //選択中の色を履歴に追加
                    String[] colors = getSelecteColors();
                    //履歴にない色であれば、色履歴アダプタのリストへ追加
                    boolean hasColors = hasColorsOnHistory( mData, colors );
                    if( !hasColors ) {
                        //本アダプタへ追加を通知
                        mData.add( colors );
                        int addIndex = mData.size() - 1;
                        notifyItemInserted( addIndex );
                    }

                    //履歴を選択中色に反映
                    MaterialCardView mcv_color0 = mll_colorParent.findViewById(R.id.mcv_color0);
                    MaterialCardView mcv_color1 = mll_colorParent.findViewById(R.id.mcv_color1);
                    mcv_color0.setCardBackgroundColor( Color.parseColor(colorPettern[0]) );
                    mcv_color1.setCardBackgroundColor( Color.parseColor(colorPettern[1]) );
                }
            });
        }

        /*
         * 生成色をサンプルマップへ適用
         */
        private String[] getSelecteColors(){
            //現在生成中の色リストを作成
            String[] colorsStr = new String[2];
            MaterialCardView mcv_color0 = mll_colorParent.findViewById(R.id.mcv_color0);
            MaterialCardView mcv_color1 = mll_colorParent.findViewById(R.id.mcv_color1);
            ColorStateList colorDrawable0 = mcv_color0.getCardBackgroundColor();
            ColorStateList colorDrawable1 = mcv_color1.getCardBackgroundColor();
            int color0 = colorDrawable0.getDefaultColor();
            int color1 = colorDrawable1.getDefaultColor();
            String code0 = "#" + Integer.toHexString( color0 );
            String code1 = "#" + Integer.toHexString( color1 );
            colorsStr[0] = code0;
            colorsStr[1] = code1;

            return colorsStr;
        }
    }

    /*
     * コンストラクタ
     */
    public ColorHistoryOnSampleMapAdapter(List<String[]> data, View sampleMap, ViewGroup colorParent, int pattern ) {
        mData = data;
        mfl_sampleMap = (ColorSampleMapView) sampleMap;
        mll_colorParent = colorParent;
        mPattern = pattern;
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

        int layout = ( (mPattern == COLOR_2) ? R.layout.item_color_pattern2: R.layout.item_color_pattern3 );

        //ビューを生成
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(layout, viewGroup, false);

        return new ViewHolder(view, mfl_sampleMap, mll_colorParent);
    }

    /*
     * ViewHolderの設定
     *   表示内容等の設定を行う
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        //ビューの設定
        viewHolder.setView( mData.get(i) );

        //アニメーション付きでカラーを表示
        Animation animation = AnimationUtils.loadAnimation(mfl_sampleMap.getContext(), R.anim.appear_color_history);
        viewHolder.itemView.startAnimation(animation);
    }

    /*
     * データ数取得
     */
    @Override
    public int getItemCount() {
        //表示データ数を返す
        return mData.size();
    }


    /*
     * 指定色パターンを保持しているか判定
     *   @retrun true ：あり
     *           false：なし
     */
    public static boolean hasColorsOnHistory(List<String[]> historyColors, String[] checkColors ) {

        for( String[] colors: historyColors ){
            //一致している場合（順不同）
            if( (colors[0].equals(checkColors[0]) && colors[1].equals(checkColors[1])) ||
                (colors[0].equals(checkColors[1]) && colors[1].equals(checkColors[0])) ){
                return true;
            }
        }
        return false;
    }

}
