package com.mapping.filemapping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NodeInformationActivity extends AppCompatActivity {

    /*-- 定数 --*/
    /* 画面遷移-レスポンスコード */
    //ノード生成
    public static final int RES_NODE_POSITIVE = 100;
    public static final int RES_NODE_CANCEL   = 101;

    /* 画面遷移-キー */
    public static String INTENT_CREATED_NODE  = "CreatedNode";
    public static String INTENT_UPDATED_NODE  = "UpdatedNode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_information);

        //遷移元からの情報
        Intent intent = getIntent();
        boolean isCreate = intent.getBooleanExtra( MapActivity.INTENT_KIND_CREATE, false );

        //ポジティブボタン
        Button bt_positive = findViewById(R.id.bt_positive);

        //新規生成の場合
        if( isCreate ){
            //遷移元からの情報
            int mapPid          = intent.getIntExtra(MapActivity.INTENT_MAP_PID, 0);
            int selectedNodePid = intent.getIntExtra(MapActivity.INTENT_NODE_PID, 0);

            //ノード生成
            bt_positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ノード名空チェック
                    String nodeName = ((EditText)findViewById(R.id.et_nodeName)).getText().toString();
                    if( nodeName.isEmpty() ){
                        //空なら、メッセージ出力して終了
                        //★

                        return;
                    }

                    //マップ共通データ
                    MapCommonData mapCommonData = (MapCommonData)getApplication();
                    NodeArrayList<NodeTable> nodes = mapCommonData.getNodes();

                    //ノード名重複チェック
                    if( nodes.hasSameNodeNameAtParent(selectedNodePid, nodeName) ){
                        //既に同じノード名があるなら、メッセージ出力して終了
                        //★

                        return;
                    }

                    //ノード初期位置を親ノードの中心位置から一定の距離離した位置にする
                    final int POS_OFFSET = 100;

                    NodeTable parentNode = nodes.getNode( selectedNodePid );
                    int posX = (int)parentNode.getCenterPosX() + POS_OFFSET;
                    int posY = (int)parentNode.getCenterPosY();

                    //ノードを生成
                    //★
                    //※本ノード自体のpidはこの時点では確定していないため、DB処理完了後に設定
                    NodeTable newNode = new NodeTable();
                    newNode.setNodeName(nodeName);
                    newNode.setPidMap(mapPid);
                    newNode.setPidParentNode( selectedNodePid );
                    newNode.setKind(NodeTable.NODE_KIND_NODE);
                    newNode.setPos( posX, posY );
                    //newNode.setNodeColor();
                    //newNode.setTextColor();

                    //DB保存処理
                    AsyncCreateNodeOperaion db = new AsyncCreateNodeOperaion(view.getContext(), newNode, new AsyncCreateNodeOperaion.OnCreateListener() {

                        @Override
                        public void onCreate(int pid) {
                            //データ挿入されたため、レコードに割り当てられたpidをノードに設定
                            newNode.setPid( pid );

                            //resultコード設定
                            intent.putExtra( INTENT_CREATED_NODE, newNode );
                            setResult(RES_NODE_POSITIVE, intent );

                            //元の画面へ戻る
                            finish();
                        }
                    });

                    //非同期処理開始
                    db.execute();
                }
            });

        } else {

            //編集対象ノード
            NodeTable node = (NodeTable) intent.getSerializableExtra(MapActivity.INTENT_NODE);

            //親ノードのPid
            int parentPid = node.getPidParentNode();

            //ノード名
            ((EditText)findViewById(R.id.et_nodeName)).setText( node.getNodeName() );

            //★ノード情報を設定
            //

            //ノード生成
            bt_positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ノード名空チェック
                    String nodeName = ((EditText)findViewById(R.id.et_nodeName)).getText().toString();
                    if( nodeName.isEmpty() ){
                        //空なら、メッセージ出力して終了
                        //★

                        return;
                    }

                    //マップ共通データ
                    MapCommonData mapCommonData = (MapCommonData)getApplication();
                    NodeArrayList<NodeTable> nodes = mapCommonData.getNodes();

                    //ノード名重複チェック
                    //★自分の名前はチェック対象外にする必要あり
                    if( nodes.hasSameNodeNameAtParent(parentPid, nodeName) ){
                        //既に同じノード名があるなら、メッセージ出力して終了
                        //★

                        return;
                    }

                    //DB保存処理
                    AsyncUpdateNode db = new AsyncUpdateNode(view.getContext(), node, new AsyncUpdateNode.OnFinishListener() {

                        @Override
                        public void onFinish() {

                            //resultコード設定
                            intent.putExtra( INTENT_UPDATED_NODE, node );
                            setResult( RES_NODE_POSITIVE, intent );

                            //元の画面へ戻る
                            finish();
                        }
                    });

                    //非同期処理開始
                    db.execute();
                }
            });
        }

        //キャンセル
        findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //resultコード設定
                setResult(RES_NODE_CANCEL);

                //元の画面へ戻る
                finish();
            }
        });


    }








}