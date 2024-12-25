package cn.tedu.submarine;
import javax.swing.ImageIcon;
/** 图片类 */
//点mysubamrine项目，右键new一个Directory，起名img，将8张图片复制进去
public class Images {
//  公开的  静态的  图片数据类型   变量名
    public static ImageIcon battleship; //战舰图片
    public static ImageIcon obsersubm;  //侦察潜艇图片
    public static ImageIcon torpesubm;  //鱼雷潜艇图片
    public static ImageIcon minesubm;   //水雷潜艇图片
    public static ImageIcon mine;       //水雷图片
    public static ImageIcon bomb;       //炸弹图片
    public static ImageIcon sea;        //海洋图片
    public static ImageIcon gameover;   //游戏结束图片

    static{
        //将img下的battleship.png图片--读取到battleship图片中
        battleship = new ImageIcon("img/battleship.png");
        obsersubm = new ImageIcon("img/obsersubm.png");
        torpesubm = new ImageIcon("img/torpesubm.png");
        minesubm = new ImageIcon("img/minesubm.png");
        mine = new ImageIcon("img/mine.png");
        bomb = new ImageIcon("img/bomb.png");
        sea = new ImageIcon("img/sea.png");
        gameover = new ImageIcon("img/gameover.png");
    }

    public static void main(String[] args) { //测试图片
        //返回8表示读取成功，返回其余数字均表示读取失败
        System.out.println(battleship.getImageLoadStatus());
        System.out.println(obsersubm.getImageLoadStatus());
        System.out.println(torpesubm.getImageLoadStatus());
        System.out.println(minesubm.getImageLoadStatus());
        System.out.println(mine.getImageLoadStatus());
        System.out.println(bomb.getImageLoadStatus());
        System.out.println(sea.getImageLoadStatus());
        System.out.println(gameover.getImageLoadStatus());
    }
}

















