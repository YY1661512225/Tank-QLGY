package com.qlgy.game;

import com.qlgy.util.MyUtil;

/*
用来管理当前关卡的信息：单例类
 单例设计模式：如果一个类只需要该类具有唯一的实现，那么可以使用单例设计模式来设计该类。
 */
public class LevelInof {
    //构造方法要私有化
    private  LevelInof(){

    }

    //定义一个静态的本类型变量，来指向唯一的实例
    private  static  LevelInof instance;

    //懒汉模式的单例，第一次使用该实例的时候，创建唯一的实例
    //所有的访问唯一实例都是通过该方法
    //该方法具有安全隐患，多线程的情况下，可能会创建多个实例
    public  static  LevelInof getInstance(){
        if (instance == null){
            //创建了唯一的实例
           instance = new LevelInof();
        }
        return instance;
    }

    //关卡的编号
    private int level;
    //关卡的敌人的数量
    private  int enemyCount;
    //通关要求的时长，-：不限时
    private  int crossTime=-1;
    //敌人的类型信息
    private  int[] enemyType;
    //关卡的难度
    private  int levelType;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public void setEnemyCount(int enemyCount) {
        this.enemyCount = enemyCount;
    }

    public int getCrossTime() {
        return crossTime;
    }

    public void setCrossTime(int crossTime) {
        this.crossTime = crossTime;
    }

    public int[] getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(int[] enemyType) {
        this.enemyType = enemyType;
    }

    public int getLevelType() {
        return levelType <=0 ? 1: levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    //获得敌人类型数组中随机的一个类型
    public  int getRandomEnemytype(){
        int index = MyUtil.getRandomNumber(0,enemyType.length);
        return enemyType[index];
    }
}
