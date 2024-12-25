package cn.tedu.submarine;

import javax.swing.ImageIcon;
import java.util.Random;
import java.awt.Graphics;

/** 海洋对象 */
public abstract class SeaObject {
    public static final int LIVE = 0; //活着的
    public static final int DEAD = 1; //死了的
    protected int state = LIVE; //当前状态(默认为活着的)

    protected int width;  //宽     //成员变量一般都要private的
    protected int height; //高     //此处设计为protected:
    protected int x;      //x坐标  //是因为咱们还没有讲到getter/setter
    protected int y;      //y坐标
    protected int speed;  //移动速度

    /** 专门给侦察潜艇、鱼雷潜艇、水雷潜艇提供的 */
    //三种潜艇的width/height是不一样的，所以数据不能写死，需传参写活
    //三种潜艇的x/y/speed是一样的，所以数据可以写死，不需要传参
    public SeaObject(int width,int height){
        this.width = width;
        this.height = height;
        x = -width; //负的潜艇的宽
        Random rand = new Random(); //随机数对象
        y = rand.nextInt(World.HEIGHT-height-150+1)+150; //150到460之间的随机数
        speed = rand.nextInt(3)+1; //1到3之间的随机数
    }

    /** 专门给战舰、水雷、炸弹提供的 */
    //三种对象的width/height/x/y/speed都是不一样的，所以数据不能写死，需传参写活
    public SeaObject(int width,int height,int x,int y,int speed){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    /** 海洋对象移动 */
    public abstract void move();

    /** 获取对象的图片 */
    public abstract ImageIcon getImage();

    /** 判断对象是否是活着的 */
    public boolean isLive(){
        return state==LIVE; //若对象当前状态为LIVE，表示为活着的，返回true，否则返回false
    }

    /** 判断对象是否是死了的 */
    public boolean isDead(){
        return state==DEAD; //若对象当前状态为DEAD，表示为死了的，返回true，否则返回false
    }

    /** 画图片 g:画笔 */
    public void paintImage(Graphics g){
        if(this.isLive()){ //若活着的
            this.getImage().paintIcon(null,g,this.x,this.y); //---不要求掌握
        }
    }

    /** 检测潜艇是否越界 */
    public boolean isOutOfBounds(){
        return this.x>=World.WIDTH; //潜艇的x>=窗口的宽，即为越界了
    }

    /** 检测碰撞   this:一个对象 other:另一个对象 */
    public boolean isHit(SeaObject other){
        //假设: this为潜艇，other为炸弹
        int x1 = this.x-other.width;  //x1:潜艇的x-炸弹的宽
        int x2 = this.x+this.width;   //x2:潜艇的x+潜艇的宽
        int y1 = this.y-other.height; //y1:潜艇的y-炸弹的高
        int y2 = this.y+this.height;  //y2:潜艇的y+潜艇的高
        int x = other.x; //x:炸弹的x
        int y = other.y; //y:炸弹的y

        //x在x1与x2之间，并且，y在y1与y2之间，即为撞上了
        return x>=x1 && x<=x2 && y>=y1 && y<=y2;
    }

    /** 海洋对象去死 */
    public void goDead(){
        state = DEAD; //当前状态修改为DEAD死了的
    }

}















