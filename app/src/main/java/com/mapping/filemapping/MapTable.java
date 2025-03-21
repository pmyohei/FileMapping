package com.mapping.filemapping;

import android.graphics.drawable.GradientDrawable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

/*
 * マップテーブル
 */
@Entity(tableName = "map")
public class MapTable implements Serializable {

    //主キー
    @PrimaryKey(autoGenerate = true)
    private int pid;

    //マップ名
    @ColumnInfo(name = "map_name")
    private String mapName;

    //メモ
    @ColumnInfo(name = "memo")
    private String memo;

    //作成日
    @ColumnInfo(name = "created_date")
    private String createdDate;

    //更新日
    @ColumnInfo(name = "update_date")
    private String updateDate;

    //デフォルトカラー１
    @ColumnInfo(name = "first_color")
    private String firstColor;

    //デフォルトカラー２
    @ColumnInfo(name = "second_color")
    private String secondColor;

    //デフォルトカラー３
    @ColumnInfo(name = "third_color")
    private String thirdColor;

    //マップカラー
    @ColumnInfo(name = "map_color")
    private String mapColor;

    //マップカラー（グラデーション）
    @ColumnInfo(name = "map_gradation_color")
    private String mapGradationColor;

    //グラデーションonoff
    @ColumnInfo(name = "is_gradation", defaultValue="false")
    private boolean isGradation;

    //グラデーション方向
    @ColumnInfo(name = "gradation_direction", defaultValue="0")
    private int gradationDirection;

    //デフォルト影onoff
    @ColumnInfo(name = "is_shadow")
    private boolean isShadow;

    //エフェクト
    @ColumnInfo(name = "effect", defaultValue="-1")
    private int effect;

    //------------------------------------
    // エフェクト
    //------------------------------------
    static public final int EFFECT_NONE = -1;
    static public final int EFFECT_HEART_FLOAT_RED = 0x00;
    static public final int EFFECT_HEART_FLOAT_BLACK = 0x01;
    static public final int EFFECT_HEART_FLOAT_WHITE = 0x02;
    static public final int EFFECT_HEART_SCALE_COLORFUL = 0x03;
    static public final int EFFECT_INFLATED_HEART_FLOAT_3COLOR = 0x04;
    static public final int EFFECT_THIN_HEART_FLOAT_COLORFUL = 0x05;
    static public final int EFFECT_STAR_MOON_YELLOW = 0x10;
    static public final int EFFECT_STAR_MOON_COLORFUL = 0x11;
    static public final int EFFECT_CIRCLE_STAR = 0x12;
    static public final int EFFECT_SNOW = 0x20;
    static public final int EFFECT_8SPARKLE_WHITE = 0x30;
    static public final int EFFECT_8SPARKLE_YELLOW = 0x31;
    static public final int EFFECT_4SPARKLE_WHITE = 0x32;
    static public final int EFFECT_4SPARKLE_RED_BLUE = 0x33;
    static public final int EFFECT_4_8SPARKLE_WHITE = 0x34;
    static public final int EFFECT_4_8SPARKLE_COLORFUL = 0x35;
    static public final int EFFECT_POLKADOTS_COLORFUL = 0x40;
    static public final int EFFECT_POLKADOTS_WHITE = 0x41;
    static public final int EFFECT_SAKURA_PINK = 0x50;
    static public final int EFFECT_FLOWER_WHITE = 0x51;
    static public final int EFFECT_FLOWER_2COLOR = 0x52;
    static public final int EFFECT_LITTLE_FLOWER_WHITE = 0x53;

    //------------------------------------
    // グラデーション方向
    //------------------------------------
    static public final int GRNDIR_KEEPING = -1;            //※現状維持指定用
    static public final int GRNDIR_TL_BR = 0;
    static public final int GRNDIR_TOP_BOTTOM = 1;
    static public final int GRNDIR_TR_BL = 2;
    static public final int GRNDIR_LEFT_RIGHT = 3;
    static public final int GRNDIR_RIGHT_LEFT = 4;
    static public final int GRNDIR_BL_TR = 5;
    static public final int GRNDIR_BOTTOM_TOP = 6;
    static public final int GRNDIR_BR_TL = 7;

    /*---  getter/setter  ---*/
    public int getPid() {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getMapName() {
        return mapName;
    }
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdateDate() { return updateDate; }
    public void setUpdateDate(String updateDate) { this.updateDate = updateDate; }

    public String getFirstColor() {
        return firstColor;
    }
    public void setFirstColor(String firstColor) {
        this.firstColor = firstColor;
    }

    public String getSecondColor() {
        return secondColor;
    }
    public void setSecondColor(String secondColor) {
        this.secondColor = secondColor;
    }

    public String getThirdColor() {
        return thirdColor;
    }
    public void setThirdColor(String thirdColor) {
        this.thirdColor = thirdColor;
    }

    public String getMapColor() { return mapColor; }
    public void setMapColor(String mapColor) { this.mapColor = mapColor; }

    public String getMapGradationColor() {
        //空ならマップ色（メイン）を返す
        if( mapGradationColor == null || mapGradationColor.isEmpty() ){
            return mapColor;
        }
        return mapGradationColor;
    }
    public void setMapGradationColor(String mapGradationColor) {
        this.mapGradationColor = mapGradationColor;
    }

    public boolean isGradation() {
        return isGradation;
    }
    public void setGradation(boolean gradation) {
        isGradation = gradation;
    }

    public int getGradationDirection() {
        return gradationDirection;
    }
    public void setGradationDirection(int gradationDirection) {
        this.gradationDirection = gradationDirection;
    }

    public boolean isShadow() { return isShadow; }
    public void setShadow(boolean isShadow) { this.isShadow = isShadow;}

    public int getEffect() {
        return effect;
    }
    public void setEffect(int effect) {
        this.effect = effect;
    }

    /*
     * マップカラーの設定（主カラー、副カラー）
     */
    public void setMapColors(int primaryColor, int subColor) {
        String primary = "#" + Integer.toHexString( primaryColor );
        String secondary = "#" + Integer.toHexString( subColor );
        this.mapColor = primary;
        this.mapGradationColor = secondary;
    }

    /*
     * デフォルトカラーを取得
     */
    public String[] getDefaultColors() {
        String[] colors = new String[3];
        colors[0] = firstColor;
        colors[1] = secondColor;
        colors[2] = thirdColor;

        return colors;
    }

    /*
     * GradientDrawableクラスのグラデーション方向の取得
     */
    public static GradientDrawable.Orientation getGradationOrientation( int direction ) {

        switch ( direction ){
            case GRNDIR_TL_BR:
                return GradientDrawable.Orientation.TL_BR;
            case GRNDIR_TOP_BOTTOM:
                return GradientDrawable.Orientation.TOP_BOTTOM;
            case GRNDIR_TR_BL:
                return GradientDrawable.Orientation.TR_BL;
            case GRNDIR_LEFT_RIGHT:
                return GradientDrawable.Orientation.LEFT_RIGHT;
            case GRNDIR_RIGHT_LEFT:
                return GradientDrawable.Orientation.RIGHT_LEFT;
            case GRNDIR_BL_TR:
                return GradientDrawable.Orientation.BL_TR;
            case GRNDIR_BOTTOM_TOP:
                return GradientDrawable.Orientation.BOTTOM_TOP;
            case GRNDIR_BR_TL:
                return GradientDrawable.Orientation.BR_TL;
            default:
                return GradientDrawable.Orientation.TOP_BOTTOM;
        }
    }
}
