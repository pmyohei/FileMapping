package com.mapping.filemapping;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * DB非同期処理
 *   ノードcreate用
 */
public class AsyncCreateNode extends AsyncShowProgress {

    private final AppDatabase      mDB;
    private final NodeTable        mNode;
    private       int              mPid;
    private final OnFinishListener mOnFinishListener;

    /*
     * コンストラクタ
     */
    public AsyncCreateNode(Context context, NodeTable newNode, OnFinishListener listener) {
        super(context);

        mDB               = AppDatabaseManager.getInstance(context);
        mOnFinishListener = listener;
        mNode             = newNode;
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
            insertDB();

            //後処理
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onPostExecute();
                }
            });
        }

        /*
         * DB処理
         */
        private void insertDB(){
            //NodeDao
            NodeTableDao nodeDao = mDB.daoNodeTable();

            //ノードを挿入し、レコードに割り当てられたpidを取得
            mPid = (int)nodeDao.insert( mNode );
        }
    }

    /*
     * バックグラウンド前処理
     */
    @Override
    void onPreExecute() {
        super.onPreExecute();
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
    @Override
    void onPostExecute() {
        super.onPostExecute();

        //読み取り完了
        mOnFinishListener.onFinish( mPid );
    }

    /*
     * データ作成完了リスナー
     */
    public interface OnFinishListener {
        /*
         * ノード生成完了時、コールされる
         */
        void onFinish( int pid );
    }


}
