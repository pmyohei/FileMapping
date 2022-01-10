package com.mapping.filemapping;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaredrummler.android.colorpicker.ColorPickerView;

import java.util.List;

public class NodeDesignAdapter extends RecyclerView.Adapter<NodeDesignAdapter.GuideViewHolder> {

    //フィールド変数
    private final List<Integer>   mData;
    //設定対象ノードビュー
    private final BaseNode        mv_node;
    //FragmentManager
    private final FragmentManager mFragmentManager;

    /*
     * ViewHolder：リスト内の各アイテムのレイアウトを含む View のラッパー
     */
    static class GuideViewHolder extends RecyclerView.ViewHolder {

        //カラー指定
        private final int NODE_BACKGROUNG_COLOR = 0;
        private final int NODE_TEXT_COLOR = 1;
        private final int LINE_COLOR = 2;

        //設定対象ノードビュー
        private final BaseNode        mv_node;
        //FragmentManager
        private final FragmentManager mFragmentManager;

        //ノード名
        private EditText et_nodeName;

        //ノードデザイン
        private TextView tv_bgColorCode;
        private TextView tv_bgColorGraphic;
        private TextView tv_txColorCode;
        private TextView tv_txColorGraphic;

        //ラインデザイン
        private TextView tv_lineColorCode;
        private TextView tv_lineColorGraphic;
        private RadioGroup rg_lineSize;


        /*
         * コンストラクタ
         */
        public GuideViewHolder(View itemView, int position, BaseNode node, FragmentManager fragmentManager) {
            super(itemView);

            mv_node = node;
            mFragmentManager = fragmentManager;

            if (position == 0) {
                et_nodeName = itemView.findViewById(R.id.et_nodeName);

            } else if (position == 1) {
                //背景色
                tv_bgColorCode    = itemView.findViewById(R.id.tv_bgColorCode);
                tv_bgColorGraphic = itemView.findViewById(R.id.tv_bgColorGraphic);
                //テキスト色
                tv_txColorCode    = itemView.findViewById(R.id.tv_txColorCode);
                tv_txColorGraphic = itemView.findViewById(R.id.tv_txColorGraphic);

            } else if (position == 2) {
                //色
                tv_lineColorCode    = itemView.findViewById(R.id.tv_lineColorCode);
                tv_lineColorGraphic = itemView.findViewById(R.id.tv_lineColorGraphic);

                rg_lineSize = itemView.findViewById(R.id.rg_lineSize);

            }
        }

        /*
         * 各種ページ設定
         */
        public void setPage(int position) {

            if (position == 0) {
                setPage0();

            } else if (position == 1) {
                setPage1();

            } else if (position == 2) {
                setPage2();
            }

        }

        /*
         * ページ設定（０）
         */
        public void setPage0() {

        }

        /*
         * ページ設定（１）
         */
        public void setPage1() {

            //背景色-カラーコード
            tv_bgColorCode.setOnClickListener(new ClickColorCode(NODE_BACKGROUNG_COLOR) );
            //背景色-カラーピッカー
            tv_bgColorGraphic.setOnClickListener( new ClickColorPicker(NODE_BACKGROUNG_COLOR) );

            //テキスト色-カラーコード
            tv_txColorCode.setOnClickListener(new ClickColorCode(NODE_TEXT_COLOR) );
            //テキスト色-カラーピッカー
            tv_txColorGraphic.setOnClickListener( new ClickColorPicker(NODE_TEXT_COLOR) );
        }

        /*
         * ページ設定（２）
         */
        public void setPage2() {

            //ラインカラー-カラーコード
            tv_lineColorCode.setOnClickListener(new ClickColorCode(LINE_COLOR) );
            //ラインカラー-カラーピッカー
            tv_lineColorGraphic.setOnClickListener( new ClickColorPicker(LINE_COLOR) );

            //ラインサイズ
            rg_lineSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    //選択されたindexを取得
                    RadioButton rb = radioGroup.findViewById( checkedId );
                    int idx = radioGroup.indexOfChild( rb );

                    //ラインサイズ設定
                    Log.i("NodeDesign", "ラインサイズ設定値=" + (idx + 1));
                    ((ChildNode)mv_node).setLineSize( idx + 1 );
                }
            });

        }

        /*
         *
         * カラーコード表示リスナー
         *
         */
        private class ClickColorCode implements View.OnClickListener {

            private final int mColorKind;

            /*
             * コンストラクタ
             */
            public ClickColorCode( int kind ){
                mColorKind = kind;
            }

            @Override
            public void onClick(View view) {

                //ダイアログを生成
                ColorCodeDialog dialog = new ColorCodeDialog();

                //OKボタンリスナー
                dialog.setOnPositiveClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.i("Design", "カラーコード=" + ((EditText)view).getText());

                        //カラーコード文字列
                        String code = "#" + ((EditText)view).getText().toString();

                        if( mColorKind == NODE_BACKGROUNG_COLOR ){
                            //ノード背景色
                            mv_node.setNodeBackgroundColor( code );
                        } else if ( mColorKind == NODE_TEXT_COLOR ){
                            //ノードテキストカラー
                            mv_node.setNodeTextColor( code );
                        } else {
                            //ラインカラー
                            ((ChildNode)mv_node).setLineColor( code );
                        }

                        dialog.dismiss();
                    }
                });

                dialog.show(mFragmentManager, "ColorCode");
            }
        }

        /*
         *
         * カラーピッカー表示リスナー
         *
         */
        private class ClickColorPicker implements View.OnClickListener {

            private final int mColorKind;

            /*
             * コンストラクタ
             */
            public ClickColorPicker( int kind ){
                mColorKind = kind;
            }

            @Override
            public void onClick(View view) {

                //ダイアログを生成
                ColorPickerDialog dialog = new ColorPickerDialog();

                //OKボタンリスナー
                dialog.setOnPositiveClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //カラーコード文字列
                        String code = "#" + Integer.toHexString( ((ColorPickerView)view).getColor() );

                        if( mColorKind == NODE_BACKGROUNG_COLOR ){
                            //背景色
                            mv_node.setNodeBackgroundColor( code );
                        } else if ( mColorKind == NODE_TEXT_COLOR ){
                            //テキストカラー
                            mv_node.setNodeTextColor( code );
                        } else {
                            //ラインカラー
                            ((ChildNode)mv_node).setLineColor( code );
                        }

                        dialog.dismiss();
                    }
                });

                dialog.show(mFragmentManager, "ColorGraphic");
            }
        }
    }

    /*
     * コンストラクタ
     */
    public NodeDesignAdapter(List<Integer> layoutIdList, BaseNode v_node, FragmentManager fragmentManager) {
        mData            = layoutIdList;
        mv_node          = v_node;
        mFragmentManager = fragmentManager;
    }

    /*
     * ここで返した値が、onCreateViewHolder()の第２引数になる
     */
    @Override
    public int getItemViewType(int position) {
        //レイアウトIDを返す
        return position;
        //return mData.get(position);
    }

    /*
     *　ViewHolderの生成
     */
    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        //レイアウトを生成
        LayoutInflater inflater = LayoutInflater.from( viewGroup.getContext() );
        View view = inflater.inflate(mData.get(position), viewGroup, false);

        return new GuideViewHolder(view, position, mv_node, mFragmentManager);
    }

    /*
     * ViewHolderの設定
     */
    @Override
    public void onBindViewHolder(@NonNull GuideViewHolder viewHolder, final int i) {

        //ページ設定
        viewHolder.setPage( i );

    }

    /*
     * 表示データ数の取得
     */
    @Override
    public int getItemCount() {
        //ページ数
        return mData.size();
    }




}
