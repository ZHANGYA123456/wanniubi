package cn.tedu.submarine;

import javax.swing.*;

/** 炸弹 */
public class Bomb extends SeaObject {
    /** 构造方法 */
    public Bomb(int x,int y){
        super(9,12,x,y,3);
    }

    /** 重写move()移动 */
    public void move(){
        y += speed; //y+(向下)
    }

    /** 重写getImage()获取图片 */
    public ImageIcon getImage(){
        return Images.bomb; //返回炸弹图片
    }

    /** 重写isOutOfBounds()检测炸弹是否越界 */
    public boolean isOutOfBounds(){
        return this.y>=World.HEIGHT; //炸弹的y>=窗口的高，即为越界了
    }
}


















