package cn.tedu.submarine;

import javax.swing.*;

/** 水雷潜艇 */
public class MineSubmarine extends SeaObject implements EnemyLife {
    /** 构造方法 */
    public MineSubmarine(){
        super(63,19);
    }

    /** 重写move()移动 */
    public void move(){
        x += speed; //x+(向右)
    }

    /** 重写getImage()获取图片 */
    public ImageIcon getImage(){
        return Images.minesubm; //返回水雷潜艇图片
    }

    /** 发射水雷---生成水雷对象 */
    public Mine shootMine(){
        //水雷的x:水雷潜艇的x+水雷潜艇的width
        //水雷的y:水雷潜艇的y-11(水雷的高)
        return new Mine(this.x+this.width,this.y-11);
    }

    /** 重写getLife()得命 */
    public int getLife(){
        return 1; //打掉水雷潜艇，战舰得1条命
    }
}















