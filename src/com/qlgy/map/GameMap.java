package com.qlgy.map;

import com.qlgy.game.GameFrame;
import com.qlgy.game.LevelInof;
import com.qlgy.tank.Tank;
import com.qlgy.util.MapTilePool;
import com.qlgy.util.MyUtil;
import com.qlgy.util.Constant;

import java.awt.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

//游戏地图类
public class GameMap {
    public static final int MAP_X = Tank.RADIUS;
    public static final  int MAP_Y = Tank.RADIUS*3 + GameFrame.titleBarH;
    public  static  final  int MAP_WIDTH = Constant.FRAME_WIDTH-Tank.RADIUS*6;
    public  static  final  int MAP_HEIGHT = Constant.FRAME_HEIGHT-Tank.RADIUS*8-GameFrame.titleBarH;
    //游戏地图元素块的容器
    private List<MapTile> tiles = new ArrayList<>();

    //大本营
    private TankHouse house;
    public GameMap() {

    }

    //初始化地图元素块
    public  void initMap(int level){
        tiles.clear();
        try {
            loadLevel(level);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
      /*  //随机的得到一个地图的元素块，添加到容器中
        final  int COUNT = 30;
        for (int i = 0; i < COUNT; i++) {
            MapTile title = MapTilePool.get();
            int x = MyUtil.getRandomNumber(MAP_X, MAP_X + MAP_WIDTH - MapTile.tileW);
            int y = MyUtil.getRandomNumber(MAP_Y, MAP_Y + MAP_HEIGHT - MapTile.tileW);
            //新生成的随机块和已经存在块有重叠的部分，那么重新生成
            if (isCollide(tiles,x,y)){
                i--;
                continue;
            }
            title.setX(x);
            title.setY(y);
            tiles.add(title);
        }*/
        //三行的一个地图
       // addRow(MAP_X,MAP_Y,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,0);
        //addRow(MAP_X,MAP_Y+MapTile.tileW*2,MAP_X+MAP_WIDTH,MapTile.TYPE_COVER,0);
        //addRow(MAP_X,MAP_Y+MapTile.tileW*4,MAP_X+MAP_WIDTH,MapTile.TYPE_HARD,MapTile.tileW);

        //初始化大本营
        house = new TankHouse();
        addHouse();
    }

    private  void  loadLevel(int level) throws  Exception{
        //获得关卡信息类的唯一实例对象
        LevelInof levelInof =LevelInof.getInstance();
        levelInof.setLevel(level);

        Properties prop = new Properties();
        prop.load(new FileInputStream("level/lv_"+level));
        //将所有的地图信息加载进来
        int enemyCount = Integer.parseInt(prop.getProperty("enemyCount"));
        //读取敌人类型,0,1,对敌人类型解析
        String[] enemyType = prop.getProperty("enemyType").split(",");
        //设置敌人数量
        levelInof.setEnemyCount(enemyCount);
        int[] type = new int[enemyType.length];
        for (int i = 0; i <type.length ; i++) {
            type[i] = Integer.parseInt(enemyType[i]);
        }
        //设置敌人类型
        levelInof.setEnemyType(type);
        //关卡难度
        //如果没有设计游戏难度，那么就当1处理
        String levelType = prop.getProperty("levelType");
        levelInof.setLevel(Integer.parseInt(levelType == null ? "1" : levelType));

        String methodName =prop.getProperty("method");
        int invokeCount = Integer.parseInt(prop.getProperty("invokeCount"));
        //把实参读取到数组中
        String[] params = new String[invokeCount];
        for (int i =1; i <= invokeCount ; i++) {
            params[i-1] = prop.getProperty("param"+i);
        }
        //将读取到参数调用对应的方法
        invokeMethod(methodName,params);
    }

    //根据方法的名字和参数调用对应的方法
    private void invokeMethod(String name,String [] params){
        for (String param : params) {
            //获得每一行方法的参数，解析
            String[] split = param.split(",");
            //使用一个int数组保存解析后的内容
            int[] arr =new int[split.length];
            int i;
            for ( i = 0; i < split.length-1; i++) {
                arr[i] = Integer.parseInt(split[i]);
            }
            //块之间的间隔是地图块之间的倍数
            final  int DIS = MapTile.tileW;
            int dis = (int)(Double.parseDouble(split[i])*DIS);
            switch (name){
                case "addRow":
                    addRow(MAP_X+arr[0]*DIS,MAP_Y+arr[1]*DIS,MAP_X+MAP_WIDTH-arr[2]*DIS,arr[3],dis);
                    break;
                case "addCol":
                    addCol(MAP_X+arr[0]*DIS,MAP_Y+arr[1]*DIS,MAP_Y+MAP_HEIGHT-arr[2]*DIS,arr[3],dis);
                    break;
                case  "addRect":
                    addRect(MAP_X+arr[0]*DIS,MAP_Y+arr[1]*DIS,MAP_X+MAP_WIDTH-arr[2]*DIS,MAP_Y+MAP_HEIGHT-arr[3]*DIS,arr[4],dis);
                    break;
            }
        }
    }
    //将老巢的所有元素块添加到地图容器中
    private   void addHouse(){
        tiles.addAll(house.getTiles());
    }

    //某一个点是否和title集合中的所有的块有重叠的部分
    //有重叠，返回true，否则返回false
    private  boolean isCollide(List<MapTile> tiles,int x,int y)
    {
        for (MapTile tile : tiles) {
            int tileX = tile.getX();
            int tileY = tile.getY();
            if (Math.abs(tileX-x) < MapTile.tileW && Math.abs(tileY-y) < MapTile.tileW+8){
                return  true;
            }
        }
        return  false;
    }

    //只对没有遮挡效果的块进行绘制
    public  void  drawBk(Graphics g) {
        for (MapTile tile : tiles) {
            if (tile.getType() != MapTile.TYPE_COVER)
                tile.draw(g);
        }
    }
       // house.draw(g);
        //只绘制有遮挡效果的块
        public  void  drawCover(Graphics g){
            for (MapTile tile : tiles) {
                if(tile.getType() == MapTile.TYPE_COVER)
                    tile.draw(g);
            }

    }
    public List<MapTile> getTiles(){
        return tiles;
    }

    //将所有的不可见的地图块
    public  void clearDestroyTile(){
        for (int i = 0; i < tiles.size(); i++) {
            MapTile tile = tiles.get(i);
            if(!tile.isVisible()){
                tiles.remove(i);
            }
        }
    }

    //往地图块容器中添加一行指定类型的地图块
    //type 类型；isLinked，true：地图块是连续的；false：地图块存在间隔
    //DIS：地图块的间隔
    //statrtX,添加地图块的起始的x坐标，start Y，添加地图块的起始的y坐标，endX，添加地图块的结束的x坐标
    //如果是块宽，连续，如果大于块宽，不连续
    public  void  addRow(int startX,int startY,int endX, int type,final  int DIS){
        int count = 0;

            //如果是连续的，计算一行有多少的地图块
            count = (endX - startX)/(MapTile.tileW+DIS);
            for (int i =0;i<count;i++) {
                MapTile tile = MapTilePool.get();
                tile.setType(type);
                tile.setX(startX + i * (MapTile.tileW+DIS));
                tile.setY(startY);
                tiles.add(tile);
        }
    }

   //往地图块容器中添加一列元素
    public  void addCol(int startX,int startY,int endY, int type,final  int DIS){
        int count = 0;

        //如果是连续的，计算一行有多少的地图块
        count = (endY - startY)/(MapTile.tileW+DIS);
        for (int i =0;i<count;i++) {
            MapTile tile = MapTilePool.get();
            tile.setType(type);
            tile.setX(startX );
            tile.setY(startY + i * (MapTile.tileW+DIS));
            tiles.add(tile);
        }
    }

    //对指定的矩形区域添加元素块
    public void  addRect(int startX,int startY,int endx,int endY,int type,final  int DIS){
        int rows = (endY - startY)/(MapTile.tileW+DIS);
        for (int i = 0; i < rows; i++) {
            addRow(startX,startY+i*(MapTile.tileW+DIS),endx,type,DIS);
        }
      //  int cols = (endx - startX)/(MapTile.tileW+DIS);
    }

}
