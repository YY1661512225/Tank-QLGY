package com.qlgy.tank;
import com.qlgy.game.Bullet;
import com.qlgy.game.Explode;
import  com.qlgy.game.GameFrame;
import com.qlgy.map.MapTile;
import com.qlgy.util.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/*
坦克类
 */
  public abstract  class Tank{
    //四个方向
    public static final int DIR_UP=0;
    public static final int DIR_DOWN=1;
    public static final int DIR_LEFT=2;
    public static final int DIR_RIGHT=3;
    //半径
    public static final int RADIUS=20;
    //默认速度,每帧30ms
    public static final int DEFAULT_SPEED=4;
    //坦克的状态
    public static final int STATE_STAND=0;
    public static final int STATE_MOVE=1;
    public static final int STATE_DIE=2;
    //坦克的初始生命
    public static final int DEFAULT_HP=100;
    public  int maxHp = DEFAULT_HP;

    private int x,y;
    private int hp=DEFAULT_HP;
    private int atk;
    public  static  final  int ATK_MAX = 25;
    public  static  final  int ATK_MIN = 15;
    private int speed=DEFAULT_SPEED;
    private int dir;
    private int state= STATE_STAND;
    private Color color;
    private  boolean isEnemy=false;

    private  BloodBar bar = new BloodBar();

    private String name;

    //子弹是否可见

    //TODO 炮弹
    private List<Bullet> bullets=new ArrayList();
    //使用容器来保存当前坦克上的所有的爆炸效果
      private  List<Explode> explodes= new ArrayList<>();

    public Tank(int x,int y,int dir){
        this.x=x;
        this.y=y;
        this.dir=dir;
        initTank();
    }

    public  Tank(){
        initTank();
    }

    private  void initTank(){
        color= MyUtil.getRandomColor();
        name=MyUtil.getRandomName();
        atk = MyUtil.getRandomNumber(ATK_MIN,ATK_MAX);
    }

    /*
    绘制坦克
     */
    public void draw(Graphics g){

        logic();
        drawImgTank(g);
        drawBullets(g);
        drawName(g);
        bar.draw(g);
    }

    private  void drawName(Graphics g){
        g.setColor(color);
        g.setFont(Constant.SMALL_FRONT);
        g.drawString(name,x-RADIUS,y-38);
    }

    /*
    使用图片的方式绘制坦克
     */
    public abstract void  drawImgTank(Graphics g);


    /*
    使用系统的方式定义坦克
     */
    private  void drawTank(Graphics g){
        g.setColor(color);

        //绘制坦克的画像
        g.fillOval(x-RADIUS,y-RADIUS,RADIUS<<1,RADIUS<<1);
        //g.drawImage(tankImg[dir],x-RADIUS,y-RADIUS,null);
        //绘制坦克的圆
        int endX=x;
        int endY=y;
        switch (dir){
            case DIR_UP:
                endY=y-2*RADIUS;
                g.drawLine(x-1,y,endX-1,endY);
                g.drawLine(x+1,y,endX+1,endY);
                break;
            case DIR_DOWN:
                endY=y+2*RADIUS;
                g.drawLine(x-1,y,endX-1,endY);
                g.drawLine(x+1,y,endX+1,endY);
                break;
            case DIR_LEFT:
                endX=x-2*RADIUS;
                g.drawLine(x,y-1,endX,endY-1);
                g.drawLine(x,y+1,endX,endY+1);
                break;
            case DIR_RIGHT:
                endX=x+2*RADIUS;
                g.drawLine(x,y-1,endX,endY-1);
                g.drawLine(x,y+1,endX,endY+1);
                break;
        }
        g.drawLine(x,y,endX,endY);
    }

    //坦克的逻辑处理
    private void logic(){
        switch (state){
            case STATE_STAND:
                break;
            case STATE_MOVE:
                move();
                break;
            case STATE_DIE:
                break;
        }

    }

    //坦克移动的功能
      private  int oldX=-1,oldY=-1;
    private void  move(){
        //发生碰撞，返回上一步位置
        oldX = x;
        oldY = y;
        switch (dir){
            case DIR_UP:
                y-=speed;
                if(y < RADIUS+GameFrame.titleBarH) {
                    y = RADIUS + GameFrame.titleBarH;
                }
                break;
            case DIR_DOWN:
                y+=speed;
                if (y > Constant.FRAME_HEIGHT-RADIUS){
                    y= Constant.FRAME_HEIGHT-RADIUS;
                }
                break;
            case DIR_LEFT:
                x-=speed;
                if (x < RADIUS){
                    x = RADIUS;
                }
                break;
            case  DIR_RIGHT:
                x+=speed;
                if (x > Constant.FRAME_WIDTH-RADIUS){
                    x = Constant.FRAME_WIDTH-RADIUS;
                }

                break;
        }
    }

    public static int getDirUp() {
        return DIR_UP;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List getBullets() {
        return bullets;
    }

    public void setBullets(List bullets) {
        this.bullets = bullets;
    }

    public  boolean isEnemy(){
        return  isEnemy;
    }

    public  void  setEnemy(boolean enemy){
        isEnemy=enemy;
    }

      public String getName() {
          return name;
      }

      public void setName(String name) {
          this.name = name;
      }

      //上一次开火的时间
      private long fireTime;
    //子弹发射的最小的间隔1s
      public static  final  int FIRE_INTERVAL = 200;

      //坦克开火的方法
    //创建了一个子弹对象，子弹对象的属性信息通过坦克的信息获得
    //然后将创建的子弹添加到坦克管理的容器中
    public  void  fire(){
        if (System.currentTimeMillis() - fireTime > FIRE_INTERVAL) {
            int bulletx = x;
            int bullety = y;
            switch (dir) {
                case DIR_UP:
                    bullety -= RADIUS;
                    break;
                case DIR_DOWN:
                    bullety += RADIUS;
                    break;
                case DIR_LEFT:
                    bulletx -= RADIUS;
                    break;
                case DIR_RIGHT:
                    bulletx += RADIUS;
                    break;
            }
            //从对象池里拿
            Bullet bullet = BulletPool.get();
            bullet.setX(bulletx);
            bullet.setY(bullety);
            bullet.setDir(dir);
            bullet.setAtk(atk);
            bullet.setColor(color);
            bullet.setVisible(true);
            //  Bullet bullet= new Bullet(bulletx,bullety,dir,atk,color);
            bullets.add(bullet);
            //发射子弹之后，记录本次发射的时间
            fireTime = System.currentTimeMillis();
            MusicUtil.playBomb();
        }
    }

    //将坦克发射的子弹全部绘制出来
    private  void  drawBullets(Graphics g){
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.draw(g);
        }
        //遍历所有的子弹，将不可见的子弹移除，还原成对象池
        for (int i=0;i < bullets.size();i++){
            Bullet bullet=bullets.get(i);
            if (!bullet.isVisible()){
                Bullet remove=bullets.remove(i);
                i--;
                BulletPool.theReturn(remove);
            }
        }
    }

    //坦克销毁的时候，处理坦克所有的子弹
    public  void  bulletsRetern(){
        //for (Bullet bullet : bullets)
        for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                BulletPool.theReturn(bullet);
        }
        bullets.clear();
    }

    //坦克和敌人子弹碰撞的方法
      public   void  collideBullets(List<Bullet> bullets){
        //遍历所有的子弹，和当前的坦克进行碰撞检测
          //for (Bullet bullet : bullets)
          for (int i = 0; i < bullets.size(); i++) {
              Bullet bullet = bullets.get(i);
              //子弹和坦克碰撞
              int bulletX = bullet.getX();
              int bulletY = bullet.getY();
              if (MyUtil.isCollide(x,y,RADIUS,bulletX,bulletY)){
                    //子弹消失
                  bullet.setVisible(false);
                  //坦克受到伤害
                  hurt(bullet);
                 // final int stk = bullet.getAtk();
                  //hp- = atk;
                 addExplode(x-RADIUS,y-RADIUS);
              }

          }
      }

      private  void  addExplode(int x,int y){
          //添加爆炸效果,当前被击中的坦克的坐标为参考
          Explode explode = ExplodesPool.get();
          explode.setX(x);
          explode.setY(y);
          explode.setVisible(true);
          explode.setIndex(0);
          //  new Explode(x-RADIUS,y-RADIUS)
          explodes.add(explode);
      }

      //坦克收到的伤害
      private  void hurt(Bullet bullet){
            int stk = bullet.getAtk();
            hp -=atk;
            if (hp<0){
                hp = 0;
                die();
            }
      }
      private void die(){
        //敌人
                if (isEnemy){
                    //敌人坦克被消灭
                    //归还对象池
                    GameFrame.killEnemyCount++;
                    EnemyTankPool.theReturn(this);
                    //本关是否结束
                    if (GameFrame.isCrossLevel()){
                        //判断游戏是否通关
                        if (GameFrame.isLastLevel()){
                            GameFrame.setGameState(Constant.STATE_WIN);
                        }else {
                            //进入下一关
                            GameFrame.startCrossLevel();
                        }
                    }

                }else{
                 //gameover
                    delaySecondsToOver(2000);
                //GameFrame.setGameState(Constant.STATE_OVER);
                }
      }

      //判断当前的坦克是否死亡
      public  boolean idDie(){
        return hp <= 0;
      }

      //绘制当前坦克上的所有的爆炸的效果
            public  void  drawExplodes(Graphics g) {
               // for (Explode explode : explodes)
                for (int i = 0; i < explodes.size(); i++) {
                    Explode explode = explodes.get(i);
                    explode.draw(g);
                }
                //将不可的见爆炸效果删除，还回对象池
                for (int i = 0; i < explodes.size(); i++) {
                     Explode explode = explodes.get(i);
                     if (!explode.isVisible()){
                         Explode remove = explodes.remove(i);
                         ExplodesPool.theReturn(remove);
                         i--;
                     }
                }
            }

            //内部类来表示坦克的血条
            class BloodBar{
              public static  final  int BAR_LENGTH = 42;
              public  static  final  int BAR_HEIGHT = 4;

              public  void  draw(Graphics g){
                  //填充底色
                  g.setColor(Color.YELLOW);
                  g.fillRect(x-RADIUS,y-RADIUS-BAR_HEIGHT*2,BAR_LENGTH,BAR_HEIGHT);
                    //红色的当前的血量
                  g.setColor(Color.RED);
                  g.fillRect(x-RADIUS,y-RADIUS-BAR_HEIGHT*2,hp*BAR_LENGTH/maxHp,BAR_HEIGHT);
                  //蓝色的边框
                  g.setColor(Color.WHITE);
                  g.drawRect(x-RADIUS,y-RADIUS-BAR_HEIGHT*2,BAR_LENGTH,BAR_HEIGHT);
              }
            }

            //坦克的子弹和地图所有块的碰撞
            public  void bulletsAndTankCollideMapTiles(List<MapTile> tiles){
                //foreach 增强的for循环，在遍历的过程中，只能使用迭代器的方式删除元素
                //使用基本的for循环
               // for (MapTile tile : tiles)
                    for (int i = 0; i < tiles.size(); i++) {
                        MapTile tile = tiles.get(i);
                        if(tile.isCollideBullet(bullets)){
                        //添加爆炸效果
                        addExplode(tile.getX()-MapTile.radius/2,tile.getY());
                        //设置地图块，销毁
                        //地图水泥块没有击毁的处理
                        if (tile.getType() == MapTile.TYPE_HARD)
                            continue;
                        tile.setVisible(false);
                        //归还对象池
                        MapTilePool.theReturn(tile);
                        //当老巢被击毁，一秒钟切换到游戏结束的画面
                        if (tile.isHouse()){
                           delaySecondsToOver(2000);
                        }
                    }
                }

            }

            //延迟若干毫秒，切换游戏结束
            private  void delaySecondsToOver(int millisSecond){
            new Thread(){
                public void run() {
                    try {
                        Thread.sleep(millisSecond);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    GameFrame.setGameState(Constant.STATE_LOST);
                }
            }.start();
        }
            //一个地图块和当前坦克的碰撞方法
      //从地图中提取八个点，来判断八个点是否有任何一个点和当前的坦克有碰撞
      //点的顺序，从左上角，顺时针遍历
      public  boolean isCollodeTile(List<MapTile> tiles){
        final int len=2;
          //for (MapTile tile : tiles)
          for (int i = 0; i < tiles.size(); i++) {
              MapTile tile = tiles.get(i);
              //如果块不可见，或者是逻辑块不进行碰撞检测
              if (!tile.isVisible()  || tile.getType() == MapTile.TYPE_COVER)
                  continue;
              int tileX = tile.getX();
              int tileY = tile.getY();
              boolean collide = MyUtil.isCollide(x,y,RADIUS,tileX,tileY);
              //如果碰撞。直接返回，否则，继续判断下一个点
              if(collide){
                  return  true;
              }
              //点二，中上点
              tileX+=MapTile.radius;
              collide = MyUtil.isCollide(x,y,RADIUS,tileX,tileY);
              //如果碰撞。直接返回，否则，继续判断下一个点
              if(collide){
                  return  true;
              }

              //点三，右上点
              tileX+= MapTile.radius;
              collide = MyUtil.isCollide(x,y,RADIUS,tileX,tileY);
              //如果碰撞。直接返回，否则，继续判断下一个点
              if(collide){
                  return  true;
              }

              //点四，中中点
              tileY+= MapTile.radius;
              collide = MyUtil.isCollide(x,y,RADIUS,tileX,tileY);
              //如果碰撞。直接返回，否则，继续判断下一个点
              if(collide){
                  return  true;
              }

              //点五，右下点
              tileY+= MapTile.radius;
              collide = MyUtil.isCollide(x,y,RADIUS,tileX,tileY);
              //如果碰撞。直接返回，否则，继续判断下一个点
              if(collide){
                  return  true;
              }

              //点六，下中点
              tileX-= MapTile.radius;
              collide = MyUtil.isCollide(x,y,RADIUS,tileX,tileY);
              //如果碰撞。直接返回，否则，继续判断下一个点
              if(collide){
                  return  true;
              }

              //点七，左下点
              tileX-= MapTile.radius;
              collide = MyUtil.isCollide(x,y,RADIUS,tileX,tileY);
              //如果碰撞。直接返回，否则，继续判断下一个点
              if(collide){
                  return  true;
              }

              //点八，左中点
              tileY-= MapTile.radius;
              collide = MyUtil.isCollide(x,y,RADIUS,tileX,tileY);
              //如果碰撞。直接返回，否则，继续判断下一个点
              if(collide){
                  return  true;
              }


          }

          return  false;
    }

    //坦克回退的方法
      public void back(){
        /*switch (dir){
            case DIR_UP:
                y+=speed;
                break;
            case  DIR_DOWN:
                y-=speed;
                break;
            case  DIR_LEFT:
                x+=speed;
                break;
            case  DIR_RIGHT:
                x-=speed;
                break;
        }*/
        x = oldX;
        y = oldY;
      }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
}
