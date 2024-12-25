package cn.tedu.submarine;

import javax.swing.ImageIcon;

/** 战舰:是海洋对象 */
public class Battleship extends SeaObject {
    private int life;   //命     //成员变量一般都是private私有的
    /** 构造方法 */
    public Battleship(){
        super(66,26,270,124,20);
        life = 5;
        System.out.println("git");
        System.out.println("git2");

    }

    /** 重写move()移动 */
    public void move(){
    }

    /** 重写getImage()获取图片 */
    public ImageIcon getImage(){
        return Images.battleship; //返回战舰图片
    }

    /** 发射炸弹--生成炸弹对象 */
    public Bomb shootBomb(){
        //this指的是战舰，炸弹的坐标就是战舰的坐标
        return new Bomb(this.x,this.y);
    }

    /** 战舰左移 */
    public void moveLeft(){
        x -= speed; //x-(向左)
    }
    /** 战舰右移 */
    public void moveRight(){
        x += speed; //x+(向右)
    }

    /** 增命 */
    public void addLife(int num){
        life += num; //命数增num
    }

    /** 获取命数 */
    public int getLife(){
        return life; //返回命数
    }
}



















