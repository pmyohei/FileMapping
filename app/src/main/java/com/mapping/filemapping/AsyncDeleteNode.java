package com.mapping.filemapping;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * DB非同期処理
 *   ノードdelete用
 */
public class AsyncDeleteNode {

    private final AppDatabase               mDB;
    private final NodeArrayList<NodeTable>  mNodes;
    private final OnFinishListener          mOnFinishListener;

    /*
     * コンストラクタ
     */
    public AsyncDeleteNode(Context context, NodeArrayList<NodeTable> node, OnFinishListener listener) {
        mDB               = AppDatabaseManager.getInstance(context);
        mOnFinishListener = listener;
        mNodes = node;
    }

    /*
     * 非同期処理
     */
    private class AsyncRunnable implements Runnable {

        Handler handler = new Handler(Looper.getMainLooper());

        /*
         * バックグラウンド処理
         */
        @Override
        public void run() {

            //メイン処理
            dbOperation();

            //後処理
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onPostExecute();
                }
            });
        }

        /*
         * ノードを更新
         */
        private void dbOperation(){

            //NodeDao
            NodeTableDao nodeDao = mDB.daoNodeTable();

            //ノードを削除
            for( NodeTable node: mNodes ){
                nodeDao.delete( node );
            }
        }
    }

    /*
     * バックグラウンド前処理
     */
    void onPreExecute() {
        //
    }

    /*
     * 実行
     */
    void execute() {
        //バックグランド前処理
        onPreExecute();

        //シングルスレッド（キューなし）で動作するexecutorを作成
        ExecutorService executorService  = Executors.newSingleThreadExecutor();

        //非同期処理を送信
        executorService.submit(new AsyncRunnable());
    }

    /*
     * バックグランド処理終了後の処理
     */
    void onPostExecute() {

        //削除したビューをレイアウトから削除
        //レイアウトからノードとラインを削除
/*        for( NodeTable node: mNodes ){
            NodeView self = node.getNodeView();
            ((ViewGroup)self.getParent()).removeView( self.getLineView() );
            ((ViewGroup)self.getParent()).removeView( self );
        }*/

        //読み取り完了
        mOnFinishListener.onFinish();
    }

    /*
     * データ作成完了リスナー
     */
    public interface OnFinishListener {
        /*
         * ノード生成完了時、コールされる
         */
        void onFinish();
    }


}
