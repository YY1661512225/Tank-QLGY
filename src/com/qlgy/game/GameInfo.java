package com.qlgy.game;

import java.util.Properties;

/*
和游戏相关的信息类
 */
public class GameInfo {
    //从配置文件中读取
    //关卡数量
    private  static int levelCount;

    static{
        try {
            Properties prop = new Properties();
            levelCount = Integer.parseInt(prop.getProperty("levelCount"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getLevelCount() {
        return levelCount;
    }
}
