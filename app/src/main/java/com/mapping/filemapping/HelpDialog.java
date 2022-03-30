package com.mapping.filemapping;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/*
 * ダイアログ：ヘルプ
 */
public class HelpDialog extends DialogFragment {

    //ヘルプ種別
    public static final int HELP_KIND_ICON = 0;
    public static final int HELP_KIND_MAP = 1;
    //Bundle保存キー
    private static final String KEY_LAYOUT_ID = "layoutId";
    private static final String KEY_HELP_KIND = "helpKind";
    private static final String KEY_NODE_KIND = "nodeKind";

    //空のコンストラクタ
    //※必須（画面回転等の画面再生成時にコールされる）
    public HelpDialog(){
        //do nothing
    }

    public static HelpDialog newInstance(int helpKind){
        Bundle args = new Bundle();
        args.putInt(KEY_LAYOUT_ID, R.layout.help_map_dialog);
        args.putInt(KEY_HELP_KIND, helpKind);
        HelpDialog helpDialog = new HelpDialog();
        helpDialog.setArguments(args);

        return helpDialog;
    }

    public static HelpDialog newInstance(int helpKind, int nodeKind){
        Bundle args = new Bundle();
        args.putInt(KEY_LAYOUT_ID, R.layout.help_icon_dialog);
        args.putInt(KEY_HELP_KIND, helpKind);
        args.putInt(KEY_NODE_KIND, nodeKind);
        HelpDialog helpDialog = new HelpDialog();
        helpDialog.setArguments(args);

        return helpDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ダイアログにレイアウトを設定
        int id = getArguments().getInt( KEY_LAYOUT_ID );
        return inflater.inflate(id, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //ダイアログ取得
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //背景を透明にする(デフォルトテーマに付いている影などを消す) ※これをしないと、画面横サイズまで拡張されない
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //ダイアログを返す
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        //ダイアログ取得
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }

        //サイズ設定
        setupDialogSize(dialog);

        //レイアウト
        int helpKind = getArguments().getInt(KEY_HELP_KIND);
        if (helpKind == HELP_KIND_ICON) {
            //ツールアイコン
            setupHelpIcon();
        } else {
            //マップ画面
            setupHelpMap();
        }
    }

    /*
     * ダイアログサイズ設定
     */
    private void setupDialogSize(Dialog dialog) {

        //縦画面時の横割合
        final float PORTRAIT_RATIO = 0.8f;
        //横画面時の横割合
        final float LANDSCAPE_RATIO = 0.5f;

        //画面向きを取得
        int orientation = getResources().getConfiguration().orientation;
        float widthRatio = ( (orientation == Configuration.ORIENTATION_PORTRAIT) ? PORTRAIT_RATIO : LANDSCAPE_RATIO );

        //画面サイズの取得
        int screeenWidth = ResourceManager.getScreenWidth( getContext() );
        //レイアウトパラメータ
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        //lp.height = metrics.heightPixels / 2;
        lp.width = (int) (screeenWidth * widthRatio);
        //サイズ設定
        window.setAttributes(lp);
    }

    /*
     * マップ画面のヘルプを設定
     */
    private void setupHelpMap() {

        //ヘルプページリスト
        List<Integer> layoutIdList = new ArrayList<>();
        //用語
        layoutIdList.add(R.layout.help_map_word);
        //基本操作
        layoutIdList.add(R.layout.help_map_basic_dialog);
        //マップ画面上部のアイコンについて
        layoutIdList.add(R.layout.help_map_toolbar_dialog);
        //写真の扱いについて
        layoutIdList.add(R.layout.help_map_about_photo_dialog);
        //制限事項
        layoutIdList.add(R.layout.help_map_limitations_dialog);

        //ViewPager2にアダプタを割り当て
        Dialog dialog = getDialog();
        ViewPager2 vp2_help = dialog.findViewById(R.id.vp2_help);
        vp2_help.setAdapter( new MapHelpPageAdapter(layoutIdList) );

        //インジケータの設定
        TabLayout tabLayout = dialog.findViewById(R.id.tab_help);
        new TabLayoutMediator(tabLayout, vp2_help,
                (tab, position) -> tab.setText("")
        ).attach();
    }

    /*
     * ヘルプアイコンのヘルプを設定
     */
    private void setupHelpIcon() {

        int nodeKind = getArguments().getInt(KEY_NODE_KIND);
        switch ( nodeKind ){
            case NodeTable.NODE_KIND_ROOT:
                setupHelpRootNode();
                break;
            case NodeTable.NODE_KIND_NODE:
                setupHelpNode();
                break;
            case NodeTable.NODE_KIND_PICTURE:
                setupHelpPictureNode();
                break;
        }
    }

    /*
     * 無関係の説明を非表示にする：ルートノード
     */
    private void setupHelpRootNode() {

        Dialog dialog = getDialog();

        //関係のないアイコンを非表示にする
        View view = dialog.findViewById( R.id.ll_delete );
        view.setVisibility( View.GONE );

        /* -- 現状デフォルトで非表示
        view = dialog.findViewById( R.id.ll_newMap );
        view.setVisibility( View.GONE );
        */

        view = dialog.findViewById( R.id.ll_change_parent );
        view.setVisibility( View.GONE );

        view = dialog.findViewById( R.id.ll_add_photo_library );
        view.setVisibility( View.GONE );
    }

    /*
     * 無関係の説明を非表示にする：ノード
     */
    private void setupHelpNode() {

        Dialog dialog = getDialog();

        //関係のないアイコンを非表示にする
        View view = dialog.findViewById( R.id.ll_add_photo_library );
        view.setVisibility( View.GONE );

        /* -- 現状デフォルトで非表示
        view = dialog.findViewById( R.id.ll_newMap );
        view.setVisibility( View.GONE );
        */
    }

    /*
     * 無関係の説明を非表示にする：写真ノード
     */
    private void setupHelpPictureNode() {

        Dialog dialog = getDialog();

        //関係のないアイコンを非表示にする
        View view = dialog.findViewById( R.id.ll_add );
        view.setVisibility( View.GONE );

        view = dialog.findViewById( R.id.ll_add_picture_node);
        view.setVisibility( View.GONE );

        /* -- 現状デフォルトで非表示
        view = dialog.findViewById( R.id.ll_newMap );
        view.setVisibility( View.GONE );
        */
    }

    /*
     * マップ画面のヘルプページ用アダプタ
     */
    public class MapHelpPageAdapter extends RecyclerView.Adapter<MapHelpPageAdapter.ViewHolder> {

        //ページレイアウト
        private final List<Integer>   mData;

        /*
         * ViewHolder：リスト内の各アイテムのレイアウトを含む View のラッパー
         */
        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView ) {
                super(itemView);
            }
        }

        /*
         * コンストラクタ
         */
        public MapHelpPageAdapter(List<Integer> layoutIdList) {
            mData = layoutIdList;
        }

        /*
         * ここで返した値が、onCreateViewHolder()の第２引数になる
         */
        @Override
        public int getItemViewType(int position) {
            //レイアウトIDを返す
            return position;
        }

        /*
         *　ViewHolderの生成
         */
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

            //レイアウトを生成
            LayoutInflater inflater = LayoutInflater.from( viewGroup.getContext() );
            View view = inflater.inflate(mData.get(position), viewGroup, false);

            return new ViewHolder(view);
        }

        /*
         * ViewHolderの設定
         */
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
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
}
