package com.qlgy.util;

//敌人坦克对象池
import com.qlgy.tank.EnemyTank;
import com.qlgy.tank.Tank;

import java.util.ArrayList;
import java.util.List;

public class EnemyTankPool {
    public  static  final  int DEFAULT_POOL_SIZE=20;
    public  static  final  int POOL_MAXSIZE=20;

    private static List<Tank> pool = new ArrayList<>();
    static {
        for (int i=0;i < DEFAULT_POOL_SIZE;i++){
            pool.add(new EnemyTank());
        }
    }


    public  static Tank get(){
        Tank tank =null;

        if (pool.size()==0) {
            tank = new EnemyTank();
        }else {

            tank=pool.remove(0);
        }
        return tank;
    }

    public  static  void  theReturn(Tank tank){

        if (pool.size()==POOL_MAXSIZE){
            return;
        }
        pool.add(tank) ;
    }
}

