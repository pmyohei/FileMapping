package com.mapping.filemapping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Locale;

public class DesignMapPageAdapter extends RecyclerView.Adapter<DesignMapPageAdapter.PageViewHolder> {

    //フィールド変数
    private final List<Integer> mData;
    //マップ
    private final View mv_map;

    /*
     * ViewHolder：リスト内の各アイテムのレイアウトを含む View のラッパー
     */
    static class PageViewHolder extends RecyclerView.ViewHolder {

        //マップ
        private final View mv_map;

        //マップデザイン
        private ColorSelectionView csv_map;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private MaterialCardView mcv_gradationClear;
        private ColorSelectionView csv_mapGradation;
        private MaterialCardView iv_TlBr;
        private MaterialCardView iv_TopBottom;
        private MaterialCardView iv_TrBl;
        private MaterialCardView iv_LeftRight;
        private MaterialCardView iv_RightLeft;
        private MaterialCardView iv_BlTr;
        private MaterialCardView iv_BottomTop;
        private MaterialCardView iv_BrTl;
        //エフェクト
        private RecyclerView rv_effects;
        private TextView tv_small_heart_red;
        private TextView tv_small_heart_black;
        private TextView tv_small_heart_white;
        private TextView tv_middle_heart_colorful;
        private TextView tv_stroke_heart_blue;
        private TextView tv_stroke_fill_heart_red;
        private TextView tv_scale_heart_colorful;
        private TextView tv_blink_heart_purple;
        private TextView tv_snowFall;
        private TextView tv_PolkaDotsColorful;
        private TextView tv_PolkaDotsYellow;
        private TextView tv_starMoonYellow;
        private TextView tv_starMoonColorful;
        private TextView tv_circleStarWhite;
        private TextView tv_sparkle8White;
        private TextView tv_sparkle8Yellow;
        private TextView tv_sparkle4White;
        private TextView tv_sparkle4RedBlue;
        private TextView tv_sparkle4_8White;
        private TextView tv_sparkle4_8Colorful;

        private TextView tv_star;
        private TextView tv_starSmall;
        private TextView tv_flower;
        private TextView tv_sakura;
        private TextView tv_spakcle;
        private TextView tv_spakcleVeryLong;
        private TextView tv_dia;
        private TextView tv_dot;
        private TextView tv_circle;
        private TextView tv_yellowLight;
        private TextView tv_cruch_heart;
        private TextView tv_normal_heart;
        private TextView tv_normal_float_heart;
        private TextView tv_small_float_heart;
        private TextView tv_snow;
        private TextView tv_moon;
        //ノードデザイン
        private ColorSelectionView csv_background;
        private ColorSelectionView csv_text;
        private RecyclerView rv_fontAlphabet;
        private RecyclerView rv_fontjapanese;
        private TextView tv_fontjapanese;
        private ImageView iv_circle;
        private ImageView iv_circleLittle;
        private ImageView iv_squareRounded;
        private ImageView iv_square;
        private ImageView iv_octagon;
        private ImageView iv_octagonRounded;
        private ImageView iv_dia;
        private ImageView iv_diaSemi;
        private ColorSelectionView csv_border;
        private SeekbarView sbv_borderSize;
        private SeekbarView sbv_nodeSize;
        private TextView tv_titel_nodeSize;
        private ColorSelectionView csv_shadow;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private Switch sw_shadow;
        //ラインデザイン
        private ColorSelectionView csv_line;
        private SeekbarView  sbv_lineSize;


        /*
         * コンストラクタ
         */
        public PageViewHolder(View itemView, int position, View v_map) {
            super(itemView);
            mv_map = v_map;

            switch (position) {
                case 0:
                    //マップ
                    csv_map = itemView.findViewById(R.id.csv_mapMonoColor);
                    csv_mapGradation = itemView.findViewById(R.id.csv_mapGradationColor);
                    mcv_gradationClear = itemView.findViewById(R.id.mcv_gradationClear);
                    iv_TlBr = itemView.findViewById(R.id.iv_TlBr);
                    iv_TopBottom = itemView.findViewById(R.id.iv_TopBottom);
                    iv_TrBl = itemView.findViewById(R.id.iv_TrBl);
                    iv_LeftRight = itemView.findViewById(R.id.iv_LeftRight);
                    iv_RightLeft = itemView.findViewById(R.id.iv_RightLeft);
                    iv_BlTr = itemView.findViewById(R.id.iv_BlTr);
                    iv_BottomTop = itemView.findViewById(R.id.iv_BottomTop);
                    iv_BrTl = itemView.findViewById(R.id.iv_BrTl);
                    break;

                case 1:
                    rv_effects = itemView.findViewById(R.id.rv_effects);

                    tv_small_heart_red = itemView.findViewById(R.id.tv_small_heart_red);
                    tv_small_heart_black = itemView.findViewById(R.id.tv_small_heart_black);
                    tv_small_heart_white = itemView.findViewById(R.id.tv_small_heart_white);
                    tv_middle_heart_colorful = itemView.findViewById(R.id.tv_middle_heart_colorful);
                    tv_stroke_heart_blue = itemView.findViewById(R.id.tv_stroke_heart_blue);
                    tv_stroke_fill_heart_red = itemView.findViewById(R.id.tv_stroke_fill_heart_red);
                    tv_scale_heart_colorful = itemView.findViewById(R.id.tv_scale_heart_colorful);
                    tv_blink_heart_purple = itemView.findViewById(R.id.tv_blink_heart_purple);
                    tv_snowFall = itemView.findViewById(R.id.tv_snowFall);
                    tv_PolkaDotsColorful = itemView.findViewById(R.id.tv_PolkaDotsColorful);
                    tv_PolkaDotsYellow = itemView.findViewById(R.id.tv_PolkaDotsYellow);
                    tv_starMoonYellow = itemView.findViewById(R.id.tv_starMoonYellow);
                    tv_starMoonColorful = itemView.findViewById(R.id.tv_starMoonColorful);
                    tv_circleStarWhite = itemView.findViewById(R.id.tv_circleStarWhite);
                    tv_sparkle8White = itemView.findViewById(R.id.tv_sparkle8White);
                    tv_sparkle8Yellow = itemView.findViewById(R.id.tv_sparkle8Yellow);
                    tv_sparkle4White = itemView.findViewById(R.id.tv_sparkle4White);
                    tv_sparkle4RedBlue = itemView.findViewById(R.id.tv_sparkle4RedBlue);
                    tv_sparkle4_8White = itemView.findViewById(R.id.tv_sparkle4_8White);
                    tv_sparkle4_8Colorful = itemView.findViewById(R.id.tv_sparkle4_8Colorful);

                    //tmp
                    tv_star = itemView.findViewById(R.id.tv_star);
                    tv_starSmall = itemView.findViewById(R.id.tv_starSmall);
                    tv_flower = itemView.findViewById(R.id.tv_flower);
                    tv_sakura = itemView.findViewById(R.id.tv_sakura);
                    tv_spakcle = itemView.findViewById(R.id.tv_spakcle);
                    tv_spakcleVeryLong = itemView.findViewById(R.id.tv_spakcleVeryLong);
                    tv_dia = itemView.findViewById(R.id.tv_dia);
                    tv_dot = itemView.findViewById(R.id.tv_dot);
                    tv_circle = itemView.findViewById(R.id.tv_circle);
                    tv_yellowLight = itemView.findViewById(R.id.tv_yellowLight);
                    tv_cruch_heart = itemView.findViewById(R.id.tv_cruch_heart);
                    tv_normal_heart = itemView.findViewById(R.id.tv_normal_heart);
                    tv_normal_float_heart = itemView.findViewById(R.id.tv_normal_float_heart);
                    tv_small_float_heart = itemView.findViewById(R.id.tv_small_float_heart);
                    tv_snow = itemView.findViewById(R.id.tv_snow);
                    tv_moon = itemView.findViewById(R.id.tv_moon);
                    break;

                case 2:
                    //フォント
                    rv_fontAlphabet = itemView.findViewById(R.id.rv_fontAlphabet);
                    rv_fontjapanese = itemView.findViewById(R.id.rv_fontJapanese);
                    tv_fontjapanese = itemView.findViewById(R.id.tv_fontJapanese);
                    break;

                case 3:
                    //ノードサイズ
                    tv_titel_nodeSize = itemView.findViewById(R.id.tv_titel_nodeSize);
                    sbv_nodeSize = itemView.findViewById(R.id.sbv_nodeSize);
                    //ラインサイズ
                    sbv_lineSize = itemView.findViewById(R.id.sbv_lineSize);
                    //枠線サイズ
                    sbv_borderSize = itemView.findViewById(R.id.sbv_borderSize);
                    break;

                case 4:
                    //ノード形
                    iv_circle = itemView.findViewById(R.id.iv_TlBr);
                    iv_circleLittle = itemView.findViewById(R.id.iv_TopBottom);
                    iv_squareRounded = itemView.findViewById(R.id.iv_TrBl);
                    iv_square = itemView.findViewById(R.id.iv_BlTr);
                    iv_octagon = itemView.findViewById(R.id.iv_BottomTop);
                    iv_octagonRounded = itemView.findViewById(R.id.iv_BrTl);
                    iv_dia = itemView.findViewById(R.id.iv_LeftRight);
                    iv_diaSemi = itemView.findViewById(R.id.iv_RightLeft);
                    break;

                case 5:
                    //テキスト色
                    csv_text = itemView.findViewById(R.id.csv_text);
                    break;

                case 6:
                    //背景色
                    csv_background = itemView.findViewById(R.id.csv_background);
                    break;

                case 7:
                    //枠線色
                    csv_border = itemView.findViewById(R.id.csv_border);
                    break;

                case 8:
                    //影
                    sw_shadow = itemView.findViewById(R.id.sw_shadow);
                    csv_shadow = itemView.findViewById(R.id.csv_shadow);
                    break;

                case 9:
                    //ラインの色
                    csv_line = itemView.findViewById(R.id.csv_line);
                    break;
            }
        }

        /*
         * 各種ページ設定
         */
        public void setPage(int position) {

            switch (position){
                case 0:
                    setMapColorPage();
                    break;
                case 1:
                    setMapEffectPage();
                    break;
                case 2:
                    setFontPage();
                    break;
                case 3:
                    setLinePage();
                    break;
                case 4:
                    setNodeShapePage();
                    break;
                case 5:
                    setTextPage();
                    break;
                case 6:
                    setBackgroundPage();
                    break;
                case 7:
                    setBorderPage();
                    break;
                case 8:
                    setShadowPage();
                    break;
                case 9:
                    setLineColorPage();
                    break;
            }
        }

        /*
         * ページ設定：マップ色
         */
        public void setMapColorPage() {
            //--------------------------------------
            // マップ色
            //--------------------------------------
            csv_map.setOnColorListener( ColorSelectionView.MAP, ColorSelectionView.COLOR_MAP, mv_map );
            csv_mapGradation.setOnColorListener( ColorSelectionView.MAP, ColorSelectionView.COLOR_MAP_GRADATION, mv_map );

            //--------------------------------------
            // グラデーションOnOff
            //--------------------------------------
            mcv_gradationClear.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         //※設定色は適当な値（本値は設定されない）
                         ((MapActivity)mv_map.getContext()).setMapColor( MapActivity.MAP_COLOR_PTN_GRADATION_OFF, "#000000", MapTable.GRNDIR_KEEPING );
                     }
                 }
            );

            //--------------------------------------
            // グラデーション方向
            //--------------------------------------
            iv_TlBr.setOnClickListener( new ClickGradationDirectionImage( MapTable.GRNDIR_TL_BR ));
            iv_TopBottom.setOnClickListener( new ClickGradationDirectionImage( MapTable.GRNDIR_TOP_BOTTOM ));
            iv_TrBl.setOnClickListener( new ClickGradationDirectionImage( MapTable.GRNDIR_TR_BL ));
            iv_LeftRight.setOnClickListener( new ClickGradationDirectionImage( MapTable.GRNDIR_LEFT_RIGHT ));
            iv_RightLeft.setOnClickListener( new ClickGradationDirectionImage( MapTable.GRNDIR_RIGHT_LEFT ));
            iv_BlTr.setOnClickListener( new ClickGradationDirectionImage( MapTable.GRNDIR_BL_TR ));
            iv_BottomTop.setOnClickListener( new ClickGradationDirectionImage( MapTable.GRNDIR_BOTTOM_TOP ));
            iv_BrTl.setOnClickListener( new ClickGradationDirectionImage( MapTable.GRNDIR_BR_TL ));
        }

        /*
         * ページ設定：マップエフェクト
         */
        public void setMapEffectPage() {
            //エフェクト追加先のマップビューを取得
            FrameLayout fl_map = mv_map.findViewById(R.id.fl_map);

            fl_map.post(() -> {

                Context context = mv_map.getContext();

                //レイアウトマネージャの生成・設定（横スクロール）
                LinearLayoutManager ll_manager = new LinearLayoutManager(context);
                ll_manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                rv_effects.setLayoutManager(ll_manager);

//                String[] effectResourceNames = ResourceManager.getAlphabetFonts();
                //RecyclerViewにアダプタを設定
                rv_effects.setAdapter( new EffectImageAdapter( null, fl_map ) );
                //スクロール抑制リスナー（ViewPager2のタブ切り替えを制御）
                ViewPager2 vp2_design = mv_map.getRootView().findViewById(R.id.vp2_design);
                rv_effects.addOnItemTouchListener( new Vp2ScrollControlListener( vp2_design ) );




                final EffectManager effectManager = new EffectManager( (ViewGroup)fl_map );

                Resources resources = fl_map.getContext().getResources();

                tv_small_heart_red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int color = resources.getColor( R.color.effect_heart_red );

                        effectManager.setEffectAttr( MapTable.HEART_NORMAL, Paint.Style.FILL, MapTable.SLOW_FLOAT, false);
                        effectManager.setEffectVolume( 40 );
                        effectManager.setEffectSize( 20, 50 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0xAA );
                        effectManager.restartEffect();
                    }
                });

                tv_small_heart_black.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int color = resources.getColor( R.color.effect_heart_black );

                        effectManager.setEffectAttr( MapTable.HEART_NORMAL, Paint.Style.FILL, MapTable.SLOW_FLOAT, false);
                        effectManager.setEffectVolume( 40 );
                        effectManager.setEffectSize( 20, 50 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0xAA );
                        effectManager.restartEffect();
                    }
                });

                tv_small_heart_white.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int color = resources.getColor( R.color.effect_heart_white );

                        effectManager.setEffectAttr( MapTable.HEART_NORMAL, Paint.Style.FILL, MapTable.SLOW_FLOAT, false);
                        effectManager.setEffectVolume( 40 );
                        effectManager.setEffectSize( 20, 50 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0xAA );
                        effectManager.restartEffect();
                    }
                });

                tv_middle_heart_colorful.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //int color = resources.getColor( R.color.effect_heart_white );

                        //effectManager.setEffectAttr( MapTable.HEART_THIN, Paint.Style.FILL, MapTable.SLOW_FLOAT, true);
                        effectManager.setEffectAttr( MapTable.HEART_THIN, Paint.Style.FILL, MapTable.NO_ANIM, true);
                        effectManager.setEffectVolume( 80 );
                        effectManager.setEffectSize( 400, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.setEffectAlpha( 0x88 );
                        effectManager.restartEffect();
                    }
                });

                tv_stroke_heart_blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int color = resources.getColor( R.color.effect_heart_gradation_blue);

                        effectManager.setEffectAttr( MapTable.HEART_INFLATED, Paint.Style.STROKE, MapTable.SLOW_FLOAT, false);
                        effectManager.setEffectVolume( 20 );
                        effectManager.setEffectSize( 100, 300 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0xDD );
                        effectManager.restartEffect();
                    }
                });

                tv_stroke_fill_heart_red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int fillColor = resources.getColor( R.color.effect_heart_red);
                        int strokeColor = resources.getColor( R.color.effect_heart_pink);

                        effectManager.setEffectAttr( MapTable.HEART_NORMAL, Paint.Style.FILL, MapTable.SLOW_FLOAT, false);
                        effectManager.setEffectVolume( 20 );
                        effectManager.setEffectSize( 100, 300 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, fillColor );
                        effectManager.setEffectAlpha( 0xDD );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.HEART_NORMAL, Paint.Style.STROKE, MapTable.SLOW_FLOAT, false);
                        effectManager.setEffectVolume( 15 );
                        effectManager.setEffectSize( 100, 200 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, strokeColor );
                        effectManager.setEffectAlpha( 0xDD );
                        effectManager.createEffects();
                    }
                });

                tv_scale_heart_colorful.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        effectManager.setEffectAttr( MapTable.HEART_NORMAL, Paint.Style.FILL, MapTable.SCALE_UP, true);
                        effectManager.setEffectAttr( MapTable.HEART_NORMAL, Paint.Style.FILL, MapTable.NO_ANIM, true);
                        effectManager.setEffectVolume( 40 );
                        effectManager.setEffectSize( 400, 300 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.setEffectAlpha( 0x55, 0xdd );
                        effectManager.setGradation( false );
                        effectManager.restartEffect();
                    }
                });

                tv_blink_heart_purple.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int color = view.getContext().getResources().getColor( R.color.effect_heart_purple);

                        effectManager.setEffectAttr( MapTable.HEART_INFLATED, Paint.Style.FILL, MapTable.BLINK_MOVE, false);
                        effectManager.setEffectVolume( 20 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0x88, 0xDD );
                        effectManager.setGradation( false );
                        effectManager.restartEffect();
                    }
                });

                tv_snowFall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int colorSnow = view.getContext().getResources().getColor( R.color.effect_snow );

//                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.SLOW_FALL);
                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM);
//                        effectManager.setEffectVolume( 160 );
                        effectManager.setEffectVolume( 260 );
//                        effectManager.setEffectSize( 40, 10 );
                        effectManager.setEffectSize( 40, 40 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, colorSnow );
                        effectManager.setEffectAlpha( 0x44, 0xCC );
                        effectManager.setGradation( true );
                        effectManager.restartEffect();
                    }
                });

                tv_PolkaDotsColorful.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int colorSnow = view.getContext().getResources().getColor( R.color.effect_snow );

                        effectManager.setEffectAttr( MapTable.OVAL, Paint.Style.FILL, MapTable.NO_ANIM, false);
                        effectManager.setEffectVolume( 20 );
                        effectManager.setEffectSize( 100, 200 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.setEffectAlpha( 0x11, 0x22 );
                        //effectManager.setEffectAlpha( 0x33, 0x44 );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.OVAL, Paint.Style.FILL, MapTable.BLINK_MOVE, false);
                        effectManager.setEffectVolume( 20 );
                        effectManager.setEffectSize( 100, 200 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.setEffectAlpha( 0x11 );
                        //effectManager.setEffectAlpha( 0x33 );
                        effectManager.createEffects();

                        effectManager.setEffectAttr( MapTable.OVAL, Paint.Style.FILL, MapTable.BLINK_MOVE, false);
                        effectManager.setEffectVolume( 20 );
                        effectManager.setEffectSize( 200, 400 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.setEffectAlpha( 0x11, 0x22 );
                        effectManager.createEffects();

                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, Color.WHITE );
                        effectManager.setEffectAlpha( 0x44, 0xDD );
                        effectManager.createEffects();
                    }
                });

                tv_PolkaDotsYellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });


                tv_starMoonYellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int color = view.getContext().getResources().getColor( R.color.effect_star );

                        effectManager.setEffectAttr( MapTable.STAR, Paint.Style.FILL, MapTable.SPIN);
                        effectManager.setEffectVolume( 30 );
                        effectManager.setEffectSize( 100, 40 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0x99, 0xCC );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.MOON, Paint.Style.FILL, MapTable.NO_ANIM, true);
                        effectManager.setEffectVolume( 5 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0x99 );
                        effectManager.createEffects();

                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0x44, 0xDD );
                        effectManager.createEffects();
                    }
                });

                tv_starMoonColorful.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        effectManager.setEffectAttr( MapTable.STAR, Paint.Style.FILL, MapTable.SPIN);
//                        effectManager.setEffectVolume( 30 );
                        effectManager.setEffectVolume( 50 );
                        effectManager.setEffectSize( 100, 40 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.setEffectAlpha( 0x99, 0xCC );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.MOON, Paint.Style.FILL, MapTable.NO_ANIM, true);
//                        effectManager.setEffectVolume( 5 );
                        effectManager.setEffectVolume( 10 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.setEffectAlpha( 0x99 );
                        effectManager.createEffects();

                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM);
//                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectVolume( 200 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, Color.WHITE );
                        effectManager.setEffectAlpha( 0x44, 0xDD );
                        effectManager.createEffects();
                    }
                });

                tv_circleStarWhite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int color = view.getContext().getResources().getColor( R.color.effect_sparkle_white );

//                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.BLINK_MOVE);
                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM);
//                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectVolume( 200 );
//                        effectManager.setEffectSize( 10, 20 );
                        effectManager.setEffectSize( 30, 40 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
//                        effectManager.setEffectAlpha( 0x44, 0xDD );
                        effectManager.setEffectAlpha( 0x44, 0xFF );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM);
                        effectManager.setEffectVolume( 50 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0x44, 0xDD );
                        effectManager.createEffects();
                    }
                });

                //スパークル：８方向、白
                tv_sparkle8White.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int color = view.getContext().getResources().getColor( R.color.effect_sparkle_white );

                        effectManager.setEffectAttr( MapTable.SPARCLE_CENTRAL_CIRCLE, Paint.Style.FILL, MapTable.BLINK_MOVE);
                        effectManager.setEffectVolume( 30 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0xDD, 0xEE );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0x44, 0xDD );
                        effectManager.createEffects();
                    }
                });

                //スパークル：８方向、白
                tv_sparkle8White.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int color = view.getContext().getResources().getColor( R.color.effect_sparkle_white );

                        effectManager.setEffectAttr( MapTable.SPARCLE_CENTRAL_CIRCLE, Paint.Style.FILL, MapTable.BLINK_MOVE);
                        effectManager.setEffectVolume( 30 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0xDD, 0xEE );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0x44, 0xDD );
                        effectManager.createEffects();
                    }
                });

                //スパークル：８方向、黄色
                tv_sparkle8Yellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int color = view.getContext().getResources().getColor( R.color.effect_sparkle_yellow );

                        effectManager.setEffectAttr( MapTable.SPARCLE_CENTRAL_CIRCLE, Paint.Style.FILL, MapTable.BLINK_MOVE);
                        effectManager.setEffectVolume( 30 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color);
                        effectManager.setEffectAlpha( 0xDD, 0xEE );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0x44, 0xDD );
                        effectManager.createEffects();
                    }
                });

                //スパークル：４方向、白
                tv_sparkle4White.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int color = view.getContext().getResources().getColor( R.color.effect_sparkle_white );

                        effectManager.setEffectAttr( MapTable.SPARKLE_VERY_LONG, Paint.Style.FILL, MapTable.BLINK_MOVE, false);
                        effectManager.setEffectVolume( 30 );
                        effectManager.setEffectSize( 200, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0xDD, 0xEE );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.DIA, Paint.Style.FILL, MapTable.NO_ANIM);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0x44, 0xDD );
                        effectManager.createEffects();
                    }
                });

                //スパークル：４方向、赤・青
                tv_sparkle4RedBlue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int colorRed = view.getContext().getResources().getColor( R.color.effect_sparkle_red );
                        int colorBlue = view.getContext().getResources().getColor( R.color.effect_sparkle_blue );
                        int colorWhite = view.getContext().getResources().getColor( R.color.effect_sparkle_white );

                        effectManager.setEffectAttr( MapTable.SPARKLE_VERY_LONG, Paint.Style.FILL, MapTable.BLINK_MOVE, false);
                        effectManager.setEffectVolume( 15 );
                        effectManager.setEffectSize( 200, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, colorRed);
                        effectManager.setEffectAlpha( 0xDD, 0xEE );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.SPARKLE_VERY_LONG, Paint.Style.FILL, MapTable.BLINK_MOVE, false);
                        effectManager.setEffectVolume( 15 );
                        effectManager.setEffectSize( 200, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, colorBlue);
                        effectManager.setEffectAlpha( 0xDD, 0xEE );
                        effectManager.createEffects();

                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, colorWhite );
                        effectManager.setEffectAlpha( 0x44, 0xDD );
                        effectManager.createEffects();
                    }
                });

                //スパークル：４・８方向、白
                tv_sparkle4_8White.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int colorWhite = view.getContext().getResources().getColor( R.color.effect_sparkle_white );

                        effectManager.setEffectAttr( MapTable.SPARCLE_CENTRAL_CIRCLE, Paint.Style.FILL, MapTable.BLINK_MOVE, true);
                        effectManager.setEffectVolume( 15 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, colorWhite);
                        effectManager.setEffectAlpha( 0xDD, 0xEE );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.SPARCLE_CENTRAL_CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM, true);
                        effectManager.setEffectVolume( 5 );
                        effectManager.createEffects();

                        effectManager.setEffectAttr( MapTable.SPARKLE_VERY_LONG, Paint.Style.FILL, MapTable.BLINK_MOVE, true);
                        effectManager.setEffectVolume( 15 );
                        effectManager.setEffectSize( 200, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, colorWhite);
                        effectManager.setEffectAlpha( 0xDD, 0xEE );
                        effectManager.createEffects();

                        effectManager.setEffectAttr( MapTable.SPARKLE_VERY_LONG, Paint.Style.FILL, MapTable.NO_ANIM, true);
                        effectManager.setEffectVolume( 5 );
                        effectManager.createEffects();

                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, colorWhite );
                        effectManager.setEffectAlpha( 0x44, 0xDD );
                        effectManager.createEffects();
                    }
                });

                //スパークル：４・８方向、カラフル
                tv_sparkle4_8Colorful.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int colorWhite = view.getContext().getResources().getColor( R.color.effect_sparkle_white );

                        effectManager.setEffectAttr( MapTable.SPARCLE_CENTRAL_CIRCLE, Paint.Style.FILL, MapTable.BLINK_MOVE, true);
                        effectManager.setEffectVolume( 15 );
                        effectManager.setEffectSize( 100, 100 );
                        //effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, colorWhite);
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.setEffectAlpha( 0xDD, 0xEE );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.SPARCLE_CENTRAL_CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM, true);
                        effectManager.setEffectVolume( 5 );
                        effectManager.createEffects();

                        effectManager.setEffectAttr( MapTable.SPARKLE_VERY_LONG, Paint.Style.FILL, MapTable.BLINK_MOVE, true);
                        effectManager.setEffectVolume( 15 );
                        effectManager.setEffectSize( 200, 100 );
                        //effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, colorWhite);
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.setEffectAlpha( 0xDD, 0xEE );
                        effectManager.createEffects();

                        effectManager.setEffectAttr( MapTable.SPARKLE_VERY_LONG, Paint.Style.FILL, MapTable.NO_ANIM, true);
                        effectManager.setEffectVolume( 5 );
                        effectManager.createEffects();

                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.NO_ANIM);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 10, 10 );
                        //effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, colorWhite);
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.setEffectAlpha( 0x44, 0xDD );
                        effectManager.createEffects();
                    }
                });







                // サンプル ----------------------------------------------------------------

                tv_star.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.STAR, Paint.Style.FILL, MapTable.SPIN);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.DOT, Paint.Style.FILL, MapTable.BLINK);
                        effectManager.setEffectVolume( 300 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_DEFAULT );
                        effectManager.createEffects();
                    }
                });

                tv_starSmall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.STAR, Paint.Style.FILL, MapTable.SPIN);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 40, 80 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.setEffectAlpha(0xDD );
                        effectManager.setGradation(true);
                        effectManager.restartEffect();
                    }
                });

                tv_flower.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.FLOWER, Paint.Style.FILL, MapTable.SPIN);
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_DEFAULT );
                        effectManager.restartEffect();
                    }
                });

                tv_sakura.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.SAKURA, Paint.Style.FILL, MapTable.BLINK_MOVE);
                        effectManager.setEffectVolume( 20 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_DEFAULT );
                        effectManager.restartEffect();
                    }
                });

                tv_spakcle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.SPARCLE_CENTRAL_CIRCLE, Paint.Style.FILL, MapTable.BLINK);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_DEFAULT );
                        effectManager.restartEffect();
                    }
                });

                tv_spakcleVeryLong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.SPARKLE_VERY_LONG, Paint.Style.FILL, MapTable.NO_ANIM, true);
                        effectManager.setEffectVolume( 10 );
                        effectManager.setEffectSize( 100, 400 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.restartEffect();
                    }
                });

                tv_dia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.DIA, Paint.Style.FILL, MapTable.BLINK);
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_DEFAULT );
                        effectManager.restartEffect();
                    }
                });

                tv_dot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.DOT, Paint.Style.FILL, MapTable.BLINK);
                        effectManager.setEffectVolume( 300 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_DEFAULT );
                        effectManager.restartEffect();
                    }
                });

                tv_circle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.CIRCLE, Paint.Style.FILL, MapTable.BLINK);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 100, 20 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_DEFAULT );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.DOT, Paint.Style.FILL, MapTable.BLINK);
                        effectManager.setEffectVolume( 300 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_DEFAULT );
                        effectManager.createEffects();
                    }
                });

                tv_yellowLight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int color = view.getContext().getResources().getColor( R.color.effect_right_yellow );

                        effectManager.setEffectAttr( MapTable.DOT, Paint.Style.FILL, MapTable.BLINK);
                        effectManager.setEffectVolume( 100 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0xAA );
                        effectManager.restartEffect();

                        effectManager.setEffectAttr( MapTable.DOT, Paint.Style.FILL, MapTable.BLINK);
                        effectManager.setEffectVolume( 20 );
                        effectManager.setEffectSize( 20, 20 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0x88 );
                        effectManager.createEffects();
                    }
                });

                tv_cruch_heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.HEART_INFLATED, Paint.Style.STROKE, MapTable.STROKE_GRADATION_ROTATE);
                        effectManager.setEffectVolume( 20 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_DEFAULT );
                        effectManager.restartEffect();
                    }
                });

                tv_normal_heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.HEART_NORMAL, Paint.Style.FILL, MapTable.SCALE_UP);
                        effectManager.setEffectVolume( 40 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_DEFAULT );
                        effectManager.restartEffect();
                    }
                });

                tv_normal_float_heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.HEART_NORMAL, Paint.Style.FILL, MapTable.SLOW_FLOAT, true);
                        effectManager.setEffectVolume( 80 );
                        effectManager.setEffectSize( 100, 100 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_RANDOM );
                        effectManager.restartEffect();
                    }
                });

                tv_small_float_heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.HEART_NORMAL, Paint.Style.FILL, MapTable.SLOW_FLOAT, true);
                        effectManager.setEffectVolume( 80 );
                        effectManager.setEffectSize( 20, 40 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, Color.RED );
                        effectManager.restartEffect();
                    }
                });

                tv_snow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.DOT, Paint.Style.FILL, MapTable.BLINK);
                        effectManager.setEffectVolume( 200 );
                        effectManager.setEffectSize( 10, 10 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, Color.WHITE );
                        effectManager.setEffectAlpha( 0xCC );
                        effectManager.setGradation( true );
                        effectManager.restartEffect();

                        int color = view.getContext().getResources().getColor( R.color.effect_right_blue );

                        effectManager.setEffectAttr( MapTable.SNOW, Paint.Style.FILL, MapTable.SLOW_FALL);
                        effectManager.setEffectVolume( 60 );
                        effectManager.setEffectSize( 40, 40 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_SPECIFY, color );
                        effectManager.setEffectAlpha( 0xAA );
                        effectManager.setGradation( true );
                        effectManager.createEffects();
                    }
                });

                tv_moon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectManager.setEffectAttr( MapTable.MOON, Paint.Style.FILL, MapTable.NO_ANIM);
                        effectManager.setEffectVolume( 10 );
                        effectManager.setEffectSize( 100, 400 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_DEFAULT );
                        effectManager.setEffectAlpha( 0xFF );
                        effectManager.restartEffect();

/*                        effectManager.setEffectAttr( MapTable.SNOW, Paint.Style.FILL, MapTable.SLOW_FALL);
                        effectManager.setEffectVolume( 120 );
                        effectManager.setEffectSize( 100, 200 );
                        effectManager.setEffectColorPtn( EffectView.COLOR_PTN_DEFAULT );
                        effectManager.setEffectAlpha( 0x22 );
                        effectManager.createEffects();*/
                    }
                });

            });
        }

        /*
         * ページ設定：フォント
         */
        public void setFontPage() {

            Context context = mv_map.getContext();

            //フォントアダプタ
            //レイアウトマネージャの生成・設定（横スクロール）
            LinearLayoutManager ll_manager = new LinearLayoutManager(context);
            ll_manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_fontAlphabet.setLayoutManager(ll_manager);

            //フォントリソースリストを取得
            //List<Typeface> alphaFonts = ResourceManager.getAlphabetFonts( context );
            List<String> alphaFonts = ResourceManager.getAlphabetFonts();
            //RecyclerViewにアダプタを設定
            rv_fontAlphabet.setAdapter( new FontAdapter( alphaFonts, null, mv_map, FontAdapter.ALPHABET ) );
            //スクロールリスナー（ViewPager2のタブ切り替えを制御）
            ViewPager2 vp2_design = mv_map.getRootView().findViewById(R.id.vp2_design);
            rv_fontAlphabet.addOnItemTouchListener( new Vp2ScrollControlListener( vp2_design ) );

            //日本語設定の場合のみ、日本語フォントも設定
            Locale locale = Locale.getDefault();
            if(locale.equals(Locale.JAPAN)||locale.equals(Locale.JAPANESE)){
                LinearLayoutManager ll_manager2 = new LinearLayoutManager(context);
                ll_manager2.setOrientation(LinearLayoutManager.HORIZONTAL);
                rv_fontjapanese.setLayoutManager(ll_manager2);

                //フォントリソースリストを取得
                //List<Typeface> jpFonts = ResourceManager.getJapaneseFonts( context );
                List<String> jpFonts = ResourceManager.getJapaneseFonts();
                //RecyclerViewにアダプタを設定
                rv_fontjapanese.setAdapter( new FontAdapter( jpFonts, null, mv_map, FontAdapter.JAPANESE ) );
                //スクロールリスナー（ViewPager2のタブ切り替えを制御）
                rv_fontjapanese.addOnItemTouchListener( new Vp2ScrollControlListener( vp2_design ) );

            } else {
                //日本語以外なら、非表示
                tv_fontjapanese.setVisibility( View.GONE );
                rv_fontjapanese.setVisibility( View.GONE );
            }
        }

        /*
         * ページ設定：ラインサイズ
         */
        public void setLinePage() {
            //ラインサイズ
            sbv_lineSize.setLineSizeSeekbar( null );
            //枠サイズのシークバー
            sbv_borderSize.setBorderSizeSeekbar( null );

            //ノードサイズは設定対象外のため、非表示
            tv_titel_nodeSize.setVisibility( View.GONE );
            sbv_nodeSize.setVisibility( View.GONE );
        }

        /*
         * ページ設定：ノードの形状
         */
        private void setNodeShapePage() {
            //ノード形
            iv_circle.setOnClickListener( new ClickShapeImage(NodeTable.CIRCLE) );
            iv_circleLittle.setOnClickListener( new ClickShapeImage(NodeTable.CIRCLE_LITTLE) );
            iv_squareRounded.setOnClickListener( new ClickShapeImage(NodeTable.SQUARE_ROUNDED) );
            iv_square.setOnClickListener( new ClickShapeImage(NodeTable.SQUARE) );
            iv_octagon.setOnClickListener( new ClickShapeImage(NodeTable.OCTAGON) );
            iv_octagonRounded.setOnClickListener( new ClickShapeImage(NodeTable.OCTAGON_ROUNDED) );
            iv_dia.setOnClickListener( new ClickShapeImage(NodeTable.DIA) );
            iv_diaSemi.setOnClickListener( new ClickShapeImage(NodeTable.DIA_SEMI) );
        }

        /*
         * ページ設定：テキスト色
         */
        private void setTextPage() {
            //テキスト色
            csv_text.setOnColorListener( ColorSelectionView.MAP, ColorSelectionView.COLOR_TEXT, mv_map );
        }

        /*
         * ページ設定：ノード色
         */
        private void setBackgroundPage() {
            //背景色
            csv_background.setOnColorListener( ColorSelectionView.MAP, ColorSelectionView.COLOR_NODE_BACKGROUNG, mv_map );
        }

        /*
         * ページ設定：ノード境界線
         */
        private void setBorderPage() {
            //枠色
            csv_border.setOnColorListener( ColorSelectionView.MAP, ColorSelectionView.COLOR_BORDER, mv_map );
        }

        /*
         * ページ設定：ノードの影
         */
        private void setShadowPage() {
            //影色
            csv_shadow.setOnColorListener( ColorSelectionView.MAP, ColorSelectionView.COLOR_SHADOW, mv_map );

            //マップ共通データ
            MapCommonData commonData = (MapCommonData)((Activity)mv_map.getContext()).getApplication();
            MapTable map = commonData.getMap();

            //影のon/off
            sw_shadow.setChecked( map.isShadow() );
            sw_shadow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    //全ノードの影の状態を反転
                    commonData.getNodes().setAllNodeShadow( b );
                    //状態反転
                    map.setShadow( b );
                }
            });
        }

        /*
         * ページ設定：ライン色
         */
        private void setLineColorPage() {
            //ラインカラー
            csv_line.setOnColorListener( ColorSelectionView.MAP, ColorSelectionView.COLOR_LINE, mv_map );
        }

        /*
         * ノード形状イメージリスナー
         */
        private class ClickShapeImage implements View.OnClickListener {

            private final int mShapeKind;

            /*
             * コンストラクタ
             */
            public ClickShapeImage(int kind ){
                mShapeKind = kind;
            }

            @Override
            public void onClick(View view) {
                //マップ共通データ
                MapCommonData commonData = (MapCommonData)((Activity)mv_map.getContext()).getApplication();
                NodeArrayList<NodeTable> nodes = commonData.getNodes();
                //全ノードに形状を設定
                nodes.setAllNodeShape( mShapeKind );
            }
        }


        /*
         * グラデーション方向クリックリスナー
         */
        private class ClickGradationDirectionImage implements View.OnClickListener {
            //グラデーション方向
            private final int mDirection;

            public ClickGradationDirectionImage(int direction ){
                mDirection = direction;
            }

            @Override
            public void onClick(View view) {
                //※設定色は適当な値（本色は設定されない）
                ((MapActivity)mv_map.getContext()).setMapColor( MapActivity.MAP_COLOR_PTN_GRADATION_DIR, "#000000", mDirection );
            }
        }


    }

    /*
     * コンストラクタ
     */
    public DesignMapPageAdapter(List<Integer> layoutIdList, View v_map) {
        mData = layoutIdList;
        mv_map = v_map;
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
    public PageViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        //レイアウトを生成
        LayoutInflater inflater = LayoutInflater.from( viewGroup.getContext() );
        View view = inflater.inflate(mData.get(position), viewGroup, false);

        return new PageViewHolder(view, position, mv_map);
    }

    /*
     * ViewHolderの設定
     */
    @Override
    public void onBindViewHolder(@NonNull PageViewHolder viewHolder, final int i) {
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
