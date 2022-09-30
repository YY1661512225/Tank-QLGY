package com.qlgy.tank;

import com.qlgy.game.GameFrame;
import com.qlgy.game.LevelInof;
import com.qlgy.util.EnemyTankPool;
import com.qlgy.util.MyUtil;
import com.qlgy.util.Constant;

import java.awt.*;

public class EnemyTank extends  Tank {
    //坦克的图片数组
    private  static  final  int TYPE_GREEN=0;
    private  static  final  int TYPE_YELLOW=1;
    private int type=TYPE_GREEN;

    private static Image[] greenImg;
    private  static  Image[] yellowImg;

    //记录5秒开始的时间
    private  long aiTime;

    static {
        greenImg = new Image[4];
        greenImg[0] =  MyUtil.createImage("res/ul.png");
        greenImg[1] =  MyUtil.createImage("res/dl.png");
        greenImg[2] =  MyUtil.createImage("res/ll.png");
        greenImg[3] =  MyUtil.createImage("res/rl.png");
        yellowImg =new Image[4];
        yellowImg[0] =  MyUtil.createImage("res/u.png");
        yellowImg[1] =  MyUtil.createImage("res/d.png");
        yellowImg[2] =  MyUtil.createImage("res/l.png");
        yellowImg[3] =  MyUtil.createImage("res/r.png");
    }

    private EnemyTank(int x, int y, int dir) {
        super(x, y, dir);
        //敌人一旦创建,计时
        aiTime =System.currentTimeMillis();
        type = MyUtil.getRandomNumber(0,TYPE_YELLOW+1);
    }

    public EnemyTank(){
        aiTime =System.currentTimeMillis();
        type = MyUtil.getRandomNumber(0,TYPE_YELLOW+1);
    }

    //用于创建敌人的坦克
    public  static Tank creatEnemy(){
        int x = MyUtil.getRandomNumber(0,2) == 0 ?RADIUS :
                Constant.FRAME_WIDTH-RADIUS;
        int y= GameFrame.titleBarH+RADIUS;
        int dir=DIR_DOWN;
        //Tank enemy= new EnemyTank(x,y,dir);
        EnemyTank enemy = (EnemyTank)EnemyTankPool.get();
        enemy.setX(x);
        enemy.setY(y);
        enemy.setDir(dir);

        enemy.setEnemy(true);
        enemy.setState(STATE_MOVE);
        //根据游戏的难度设置敌人的血量
        int maxHp = Tank.DEFAULT_HP*LevelInof.getInstance().getLevelType();
        enemy.setHp(maxHp);
        enemy.setMaxHp(maxHp);
        //通过关卡信息中的敌人类型，来设置当前出生的敌人类型
       int enemyType = LevelInof.getInstance().getRandomEnemytype();
        enemy.setType(enemyType);
        return  enemy;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void drawImgTank(Graphics g){
        ai();
        g.drawImage(type == TYPE_GREEN ? greenImg[getDir()] : yellowImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);

    }

      private  void  ai(){
        if (System.currentTimeMillis()-aiTime> Constant.ENEMY_AI_INTERVAL){
            //间隔五秒，切换随机一个状态
            setDir(MyUtil.getRandomNumber(DIR_UP,DIR_RIGHT+1));
            setState(MyUtil.getRandomNumber(0,2)==0 ? STATE_STAND : STATE_MOVE);
            aiTime = System.currentTimeMillis();


        }
        //比较小的概率去开火
        if (Math.random() < Constant.ENEMY_FIRE_PERCENT){
            fire();
        }

      }
}
