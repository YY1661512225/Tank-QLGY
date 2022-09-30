package com.qlgy.map;


import com.qlgy.game.Bullet;
import com.qlgy.util.BulletPool;
import com.qlgy.util.Constant;
import com.qlgy.util.MyUtil;

import java.awt.*;
import java.util.List;

/*
地图元素块
 */
public class MapTile {

    public static  final  int TYPE_NORMAL = 0;
    public static  final  int TYPE_HOUSE = 1;
    public  static final int  TYPE_COVER = 2;
    public static  final  int  TYPE_HARD = 3;

    //private  static Image titleImg;
    public  static  int tileW=40;
    public  static  int radius=tileW>>1;
    private  int type = TYPE_NORMAL;


    private  static  Image[] tileImg;
    static{
        tileImg = new Image[4];
        tileImg[TYPE_NORMAL] = MyUtil.createImage("res/title.png");
        tileImg[TYPE_HOUSE] = MyUtil.createImage("res/house.png");
        tileImg[TYPE_COVER] = MyUtil.createImage("res/cover.png");
        tileImg[TYPE_HARD] = MyUtil.createImage("res/hard.png");
        if(tileW<=0){
            tileW = tileImg[TYPE_NORMAL].getWidth(null);
        }
    }

    //图片资源的左上角
    private  int x,y;

    private  boolean visible = true;

    public MapTile() {
    }

    public MapTile(int x, int y) {
        this.x = x;
        this.y = y;
        if(tileW<=0){
            tileW = tileImg[TYPE_NORMAL].getWidth(null);
        }
    }

    public  void  draw(Graphics g){
        if (!isVisible())
            return;
        if(tileW<=0){
            tileW = tileImg[TYPE_NORMAL].getWidth(null);
        }
        g.drawImage(tileImg[type],x,y,null);

      /*  //绘制块上的名字
        if (name != null){
            g.setColor(Color.RED);
            g.drawString(name,x,y+radius*3/2);
        }*/
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    /*public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }*/

    //地图和若干个子弹是否有碰撞
    public  boolean isCollideBullet(List<Bullet> bullets){
        if (!isVisible()||type == TYPE_COVER)
            return false;
        //for (Bullet bullet : bullets)
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            int bulletX = bullet.getX();
            int bulletY = bullet.getY();
            boolean collide =  MyUtil.isCollide(x+radius ,y+radius ,radius,bulletX,bulletY);
            if (collide){
                //子弹销毁
                bullet.setVisible(false);
                BulletPool.theReturn(bullet);
                return collide;}
        }
        return false;
    }

    //判断当前的地图块是否是老巢
    public boolean isHouse(){
        return type==TYPE_HOUSE;
    }

}
