package com.qlgy.tank;


import com.qlgy.util.MyUtil;

import java.awt.*;

public class MyTank extends Tank {
    //坦克的图片数组
    private static  Image[] tankImg;
    //静态代码块中进行初始化
    static {
        tankImg=new Image[4];
        tankImg[0]= MyUtil.createImage("res/u.png");
        tankImg[1]= MyUtil.createImage("res/d.png");
        tankImg[2]= MyUtil.createImage("res/l.png");
        tankImg[3]= MyUtil.createImage("res/r.png");

    }


    public MyTank(int x, int y, int dir) {
        super(x, y, dir);
    }

    @Override
    public void drawImgTank(Graphics g){
        g.drawImage(tankImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);

    }
}
