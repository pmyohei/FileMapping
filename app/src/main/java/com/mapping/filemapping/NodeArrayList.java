package com.mapping.filemapping;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * ArrayList：ノード用
 *   任意のマップに所属するノードを保持する目的で使用する
 */
public class NodeArrayList<E> extends ArrayList<NodeTable> implements Serializable {

    /* 定数 */
    public static final int NO_DATA = -1;   //データなし

    /*
     * コンストラクタ
     */
    public NodeArrayList() {
        super();
    }

    /*
     *　ラストIndex取得
     */
    public int getLastIdx() {

        int size = size();

        if (size == 0) {
            return NO_DATA;
        }

        return size - 1;
    }

    /*
     *　指定PIDのノードを取得
     */
    public NodeTable getNode(int pid) {

        int size = size();
        for (int i = 0; i < size; i++) {

            if (pid == get(i).getPid()) {
                //指定親ノードのPIDと一致するノードを返す
                return get(i);
            }
        }

        return null;
    }

    /*
     *　指定親Pidの子ノードをリストとして取得
     */
    public NodeArrayList<NodeTable> getChildNodes(int parentPid) {

        //検索結果
        NodeArrayList<NodeTable> result = new NodeArrayList<>();

        //ノード数分ループ
        for (NodeTable node : this) {
            //親ノード検索
            if (parentPid == node.getPidParentNode()) {
                //リストに追加
                result.add(node);
            }
        }

        return result;
    }

    /*
     *　同一名のノード保持判定
     *   指定ノードの子ノード（直下）の中に、指定ノード名と同じ名前のノードがあるかをチェックする
     */
    public boolean hasSameNodeNameAtParent(int parentPid, String nodeName) {

        //指定ノードの子ノードリスト
        NodeArrayList<NodeTable> nodes = getChildNodes(parentPid);

        //実検索
        return doHasSameNodeNameAtParent( nodes, nodeName );
    }

    /*
     *　同一名のノード保持判定
     *   指定ノードの子ノード（直下）の中に、指定ノード名と同じ名前のノードがあるかをチェックする。
     *   ただし、para2で指定したノードは検索対象外とする。
     */
    public boolean hasSameNodeNameAtParent(int parentPid, int exclusionPid, String nodeName) {

        //指定ノードの子ノードリスト
        NodeArrayList<NodeTable> nodes = getChildNodes(parentPid);

        //検索対象外ノードを、検索対象ノードから除外
        nodes.deleteNode( exclusionPid );

        //実検索
        return doHasSameNodeNameAtParent( nodes, nodeName );
    }

    /*
     *　同一名のノードを既に持っているか（実処理）
     */
    private boolean doHasSameNodeNameAtParent(NodeArrayList<NodeTable> nodes, String nodeName) {

        //ノード数分ループ
        for (NodeTable node : nodes) {

            //ノード名が同じ場合
            if ( (node.getKind() != NodeTable.NODE_KIND_PICTURE) && (node.getNodeName().equals(nodeName)) ) {
                return true;
            }
        }

        //ノード未保持
        return false;
    }

    /*
     *　指定ノードの直下のノード取得（孫ノードは対象外）
     */
    public NodeArrayList<NodeTable> getDirectlyBelow(int pid) {

        NodeArrayList<NodeTable> nodes = new NodeArrayList<>();

        //ノード数分ループ
        for (NodeTable node : this) {
            //指定ノードを親ノードとするノード
            if (pid == node.getPidParentNode()) {
                //このノードの配下ノードを取得
                nodes.add( node );
            }
        }

        return nodes;
    }

    /*
     *　指定ノード配下のノード取得（指定ノード含む）
     *   param：指定ノードもリストに含めるかどうか
     *          true ：含める
     *          false：含めない
     */
    public NodeArrayList<NodeTable> getUnderNodes(int pid, boolean isMyself) {

        NodeArrayList<NodeTable> nodes = new NodeArrayList<>();

        //ノード数分ループ
        for (NodeTable node : this) {

            //指定ノードもリストに追加
            if( isMyself && (pid == node.getPid()) ){
                nodes.add(node);
            }

            //指定ノードを親ノードとするノード
            if (pid == node.getPidParentNode()) {
                //このノードの配下ノードを取得
                NodeArrayList<NodeTable> tmp = getUnderNodes(node.getPid(), isMyself);
                nodes.addAll(tmp);
            }
        }

        return nodes;
    }




    /*
     *　指定ノードをリストから削除
     */
    public void deleteNode(int pid) {

        //リストループ
        for( NodeTable node: this ){

            //指定ノードと同じノード発見
            if( node.getPid() == pid ){
                //リストから削除
                remove( node );
                return;
            }
        }
    }

    /*
     *　指定ノードをリストから削除
     */
    public void deleteNodes( NodeArrayList<NodeTable> nodes ) {
        //指定ノード分ループ
        for( NodeTable node: nodes ){
            //削除
            deleteNode(node.getPid() );
        }
    }

    /*
     *　階層化されたリストを取得
     *   例）ルートノード
     *     　ノードＡ
     *       ノードa1（親ノードＡ）
     *       ノードa2（親ノードＡ）
     *     　ノードＢ
     *       ノードb1（親ノードＢ）
     *     　ノードＣ
     *     　ノードＤ
     */
    public NodeArrayList<NodeTable> getHierarchyList() {

        //階層化リスト
        NodeArrayList<NodeTable> hierarchyList = new NodeArrayList<>();
        //ルートノード
        NodeTable root = getRootNode();

        //階層化リストを生成
        createHierarchyList( hierarchyList, root );

        return hierarchyList;
    }

    /*
     *　階層化リストを生成
     */
    public void createHierarchyList(NodeArrayList<NodeTable> addList, NodeTable node ) {

        //指定ノードをリストに追加
        addList.add( node );

        //指定ノードの直下ノードを取得
        NodeArrayList<NodeTable> directlyBelowNodes = getDirectlyBelow( node.getPid() );

        //直下ノード分ループ
        for( NodeTable childNode: directlyBelowNodes ){
            createHierarchyList( addList, childNode );
        }
    }
    
    /*
     *　ノードの階層を取得
     *   ＜階層レベル参考＞
     *     　・ルートノード　：１
     *       　・ノードA　　：２
     *       　　・ノードa　：３
     *       　　・ノードa　：３
     *       　・ノードB　　：２
     *       　　・ノードb　：３
     */
    public int getHierarchyLevel( NodeTable targetNode ) {

        //ルートノード
        if( targetNode.getKind() == NodeTable.NODE_KIND_ROOT ){
            //1階層を返す
            return 1;
        }

        //階層レベル
        int level = 1;

        //親の階層レベルを取得
        NodeTable parentNode = getNode( targetNode.getPidParentNode() );
        level += getHierarchyLevel( parentNode, level );

        //このルートは通らない想定（ルートノードは必ず存在するため）
        return level;
    }

    /*
     *　ノードの階層を取得
     */
    private int getHierarchyLevel( NodeTable targetNode, int level ) {

        //ルートノード
        if( targetNode.getKind() == NodeTable.NODE_KIND_ROOT ){
            //1階層を返す
            return 1;
        }

        //親ノード
        NodeTable parentNode = getNode( targetNode.getPidParentNode() );
        level += getHierarchyLevel( parentNode, level );

        //このルートは通らない想定（ルートノードは必ず存在するため）
        return level;
    }


    /*
     *　本リストからルートノードを取得
     */
    public NodeTable getRootNode() {
        //指定ノード分ループ
        for( NodeTable node: this ){
            if( node.getKind() == NodeTable.NODE_KIND_ROOT ){
                return node;
            }
        }

        //このルートは通らない想定（ルートノードは必ず存在するため）
        return null;
    }

}
