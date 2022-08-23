package com.mapping.filemapping;

import static android.content.Context.WINDOW_SERVICE;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Insets;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import androidx.core.content.res.ResourcesCompat;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/*
 * 共通リソース管理
 */
public class ResourceManager {

    //URI読み込み有効
    //※URIからの読み込みを有効にする（端末内の全てのリソースにアクセス可能にする）
    //※リリースでは、必ず「false」にすること
    public static final boolean READ_URI = true;

    //URIパス
    public static final String URI_PATH = "content://com.android.providers.media.documents/document/image%";

    //URI識別子分割用の文字列
    //URI 例)content://com.android.providers.media.documents/document/image%3A27
    public static final String URI_SPLIT = "image%";
    //URI 例)content://com.android.providers.downloads.documents/document/12

    //SharedPreferences
    public static final String SHARED_DATA_NAME = "UIData";         //SharedPreferences保存名
    public static final String SHARED_KEY_HELP_ON_MAP = "showHelpOnMap";
    public static final String SHARED_KEY_HELP_ON_MAPLIST = "showHelpOnMapList";
    public static final String SHARED_KEY_HELP_ON_MAPENTRY = "showHelpOnMapEntry";
    public static final String SHARED_KEY_HELP_ON_GALLERY = "showHelpOnMapGallery";

    //ヘルプ表示の有無（初期値 or 取得エラー時)
    //※「今後表示しない」が選択された時、falseに設定
    public static final boolean INVALID_SHOW_HELP = true;

    /* 画面遷移-キー */
    public static final String KEY_NEW_MAP = "IsNewMap";
    public static final String KEY_CREATED_NODE = "CreatedNode";
    public static final String KEY_THUMBNAIL = "thumbnail";
    public static final String KEY_NEW_THUMBNAIL = "new_thumbnail";
    public static final String KEY_NEW_SHAPE = "new_shape";

    //serialVersionUID
    public static final long SERIAL_VERSION_UID_NODE_TABLE = 1L;
    public static final long SERIAL_VERSION_UID_NODE_VIEW = 2L;

    /* ノード共通情報 */
    //ノード初期生成位置（親ノードからのオフセット）
    public static final int POS_NODE_INIT_OFFSET = 120;
    //ノード形状；四角形の場合の角丸サイズ（四角形の辺に対する割合）
    public static final float SQUARE_CORNER_RATIO = 0.1f;

    //ノード生成ダイアログの表示領域の割合
    public static final float NODE_DESIGN_DIALOG_RATIO = 0.5f;

    //ノード色（フェールセーフ用）
    public static final String NODE_INVALID_COLOR = "#000000";


    /*
     * 本アプリケーション内のフォントリスト(英語)
     */
    public static List<Typeface> getAlphabetFonts(Context context) {

        List<Typeface> fonts = new ArrayList<>();
        fonts.add(ResourcesCompat.getFont(context, R.font.luxurious_roman_regular));
        fonts.add(ResourcesCompat.getFont(context, R.font.roboto_regular));
        fonts.add(ResourcesCompat.getFont(context, R.font.the_nautigal_bold));
        fonts.add(ResourcesCompat.getFont(context, R.font.dancing_script_medium));
        fonts.add(ResourcesCompat.getFont(context, R.font.oswald_variable_font_wght));
        fonts.add(ResourcesCompat.getFont(context, R.font.caveat_medium));
        fonts.add(ResourcesCompat.getFont(context, R.font.moon_dance_regular));
        fonts.add(ResourcesCompat.getFont(context, R.font.josefin_sans_semibold));

        return fonts;
    }

    /*
     * 本アプリケーション内のフォントリスト(日本語)
     */
    public static List<Typeface> getJapaneseFonts(Context context) {

        List<Typeface> fonts = new ArrayList<>();
        fonts.add(ResourcesCompat.getFont(context, R.font.ipaexm));
        fonts.add(ResourcesCompat.getFont(context, R.font.ipaexg));
        fonts.add(ResourcesCompat.getFont(context, R.font.hannari_mincho_regular));
        fonts.add(ResourcesCompat.getFont(context, R.font.senobi_gothic_medium));
        fonts.add(ResourcesCompat.getFont(context, R.font.jk_maru_gothic_m));
        fonts.add(ResourcesCompat.getFont(context, R.font.pixel_mplus10_regular));

        return fonts;
    }

    /*
     * 本アプリケーション内のフォントリスト(英語)
     */
    public static List<String> getAlphabetFonts() {

        List<String> fonts = new ArrayList<>();
        fonts.add("luxurious_roman_regular");
        fonts.add("roboto_regular");
        fonts.add("the_nautigal_bold");
        fonts.add("dancing_script_medium");
        fonts.add("oswald_variable_font_wght");
        fonts.add("caveat_medium");
        fonts.add("moon_dance_regular");
        fonts.add("josefin_sans_semibold");

        return fonts;
    }

    /*
     * 本アプリケーション内のフォントリスト(日本語)
     */
    public static List<String> getJapaneseFonts() {

        List<String> fonts = new ArrayList<>();
        fonts.add("ipaexm");
        fonts.add("ipaexg");
        fonts.add("hannari_mincho_regular");
        fonts.add("senobi_gothic_medium");
        fonts.add("jk_maru_gothic_m");
        fonts.add("pixel_mplus10_regular");

        return fonts;
    }

    /*
     *　コンテンツURIから絶対パスを取得（/~~/~~/xxx.png）
     */
    public static String getPathFromUri(Context context, Uri uri) {

        //Log.i("URI変換", "対象のuri=" + uri);

        ContentResolver contentResolver = context.getContentResolver();

        //String mimeType = contentResolver.getType(uri);
        //Log.i("URI変換", "mimeType=" + mimeType);

        //対象フィールド
        String[] columns = {
                MediaStore.Images.Media.DATA
        };

        //DocumentId 文字列を取得
        //例）「image:26」（画像フォルダ配下の場合）
        String wholeId = DocumentsContract.getDocumentId(uri);
        String[] wholeIdSplit = wholeId.split(":");
        if( wholeIdSplit.length <= 1 ){
            //「:」がなければMediaではないURIとみなす
            //Log.i("URI変換", "wholeId=" + wholeId);
            return null;
        }

        //検索条件
        String where = MediaStore.Images.Media._ID + "=?";
        //idを取得
        //例）「image:26」から「26」を取得
        String id = wholeIdSplit[1];

        //クエリ発行
        Cursor cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, where, new String[]{id}, null);

        //Log.i("URI変換", "dump cursor:" + DatabaseUtils.dumpCursorToString(cursor));

        String path = null;
        if (cursor.moveToFirst()) {
            //絶対パスを取得
            path = cursor.getString(cursor.getColumnIndexOrThrow(columns[0]));

            File file = new File( path );
            if( !file.isFile() ){
                //念のためファイルとして生成できるパスか確認
                //File認定されなければ、変換エラーとする
                path = null;
            }
        }

        cursor.close();

        //-- URI読み込み有効化
        if( ResourceManager.READ_URI ){
            if( path == null ){
                path = uri.toString();
            }
        }
        //--

        return path;
    }


    /*
     *　スクリーン横幅を取得
     */
    public static int getScreenWidth( Context context ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
            WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();

            return windowMetrics.getBounds().width();
            //Log.d("screenWidth=>>>", screenWidth + "");
            //Log.d("screenHeight=>>", screenHeight + "");

        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }
    }

    /*
     *　スクリーン縦幅を取得
     */
    public static int getScreenHeight( Context context ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
            WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();

            return windowMetrics.getBounds().height();

        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);

            return displayMetrics.heightPixels;
        }
    }


    /*
     *　スクリーン横幅を取得（ナビゲーションバーのサイズを除外。横画面時の横幅を知りたい時用）
     */
/*    public static int getScreenWidthWithoutNavibar( Context context ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
            WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();

            Insets insets = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());

            return windowMetrics.getBounds().width() - insets.bottom;

        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }
    }*/

    /*
     *　スクリーン縦幅を取得（ナビゲーションバーのサイズを除外。縦画面時の縦幅を知りたい時用）
     */
/*    public static int getScreenHeightWithoutNavibar( Context context ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
            WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();

            Insets insets = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return windowMetrics.getBounds().height() - insets.bottom;

        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);

            return displayMetrics.heightPixels;
        }
    }*/
}
