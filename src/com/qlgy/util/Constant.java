package com.qlgy.util;

import java.awt.*;

/*游戏常量类
 */
public class Constant {
    /*
    游戏窗口
     */
    public static final String GAME_TITLE="坦克大战";

    public static final int FRAME_WIDTH=960;
    public  static  final  int FRAME_HEIGHT=660;

    //动态的获得系统屏幕的宽高
    public  static  final  int SCAREEN_W = Toolkit.getDefaultToolkit().getScreenSize().width;
    public  static  final  int SCREEN_H =  Toolkit.getDefaultToolkit().getScreenSize().height;

    public static  final  int FRAME_x = SCAREEN_W-FRAME_WIDTH>>1;
    public  static  final  int FRAME_y = SCREEN_H-FRAME_HEIGHT>>1;



    /*
    游戏菜单
     */
    public  static  final  int  STATE_MENU=0;
    public  static  final  int  STATE_HELP=1;
    public  static  final  int  STATE_ABOUT=2;
    public  static  final  int  STATE_RUN=3;
    public  static  final  int  STATE_LOST=4;
    public  static  final int   STATE_WIN=5;
    public  static  final  int STATE_CROSS=6;

    public static final String[] MENUS={
            "开始游戏",
            "继续游戏",
            "游戏帮助",
            "游戏关于",
            "退出游戏"

    };

    public static final  String OVER_STR0 = "ESC键退出游戏";
    public static  final  String OVER_STR1 = "ENTER键回到主菜单";
    /*
    字体
     */
    public static final  Font FRONT=new Font("宋体",Font.BOLD,24);
    public static final  Font SMALL_FRONT=new Font("宋体",Font.BOLD,9);

    public  static  final  int REPAINT_INTERVAL=30;
        //最大敌人数量
    public static  final  int ENEMY_MAX_COUNT=10;

    public  static  final  int ENEMY_BORN_INTERVAL=5000;

    public  static  final  int ENEMY_AI_INTERVAL=5000;
    public  static  final  double ENEMY_FIRE_PERCENT=0.03;
    //public  static  final String HOUSE_STR ="1W1";
}
