package cn.tedu.submarine;

import javax.swing.JFrame;
import javax.swing.JPanel; //1.
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/** 整个游戏世界 */
public class World extends JPanel { //2.
    public static final int WIDTH = 641;  //窗口的宽
    public static final int HEIGHT = 479; //窗口的高

    //如下一堆为窗口中所显示的对象
    private Battleship ship = new Battleship(); //战舰对象
    private SeaObject[] submarines = {}; //潜艇(侦察潜艇、鱼雷潜艇、水雷潜艇)数组
    private Mine[] mines = {}; //水雷数组
    private Bomb[] bombs = {}; //炸弹数组

    /** 生成潜艇(侦察潜艇、鱼雷潜艇、水雷潜艇)对象 */
    public SeaObject nextSubmarine(){
        Random rand = new Random(); //随机数对象
        int type = rand.nextInt(20); //0到19之间的随机数
        if(type<10){ //0到9时，返回侦察潜艇对象
            return new ObserveSubmarine();
        }else if(type<15){ //10到14时，返回鱼雷潜艇对象
            return new TorpedoSubmarine();
        }else{ //15到19时，返回水雷潜艇对象
            return new MineSubmarine();
        }
    }

    private int subEnterIndex = 0; //潜艇入场计数
    /** 潜艇(侦察潜艇、鱼雷潜艇、水雷潜艇)入场 */
    public void submarineEnterAction() { //每10毫秒走一次
        subEnterIndex++; //每10毫秒增1
        if (subEnterIndex % 40 == 0) { //每400(40*10)毫秒走一次
            SeaObject obj = nextSubmarine(); //获取潜艇对象
            submarines = Arrays.copyOf(submarines, submarines.length + 1); //扩容
            submarines[submarines.length - 1] = obj; //将obj添加到最后一个元素上
        }
    }

    private int mineEnterIndex = 0; //水雷入场计数
    /** 水雷入场 */
    public void mineEnterAction(){ //每10毫秒走一次
        mineEnterIndex++; //每10毫秒增1
        if(mineEnterIndex%100==0){ //每1000(100*10)毫秒走一次
            for(int i=0;i<submarines.length;i++){ //遍历所有潜艇
                if(submarines[i] instanceof MineSubmarine){ //若为水雷潜艇类型
                    MineSubmarine ms = (MineSubmarine)submarines[i]; //将潜艇强转为水雷潜艇类型
                    Mine obj = ms.shootMine(); //获取水雷对象
                    mines = Arrays.copyOf(mines,mines.length+1); //扩容
                    mines[mines.length-1] = obj; //将obj添加到mines最后一个元素上
                }
            }
        }
    }

    /** 海洋对象移动 */
    public void moveAction(){ //每10毫秒走一次
        for(int i=0;i<submarines.length;i++){ //遍历所有潜艇
            submarines[i].move(); //潜艇移动
        }
        for(int i=0;i<mines.length;i++){ //遍历所有水雷
            mines[i].move(); //水雷移动
        }
        for(int i=0;i<bombs.length;i++){ //遍历所有炸弹
            bombs[i].move();
        }
    }

    /** 删除越界的海洋对象 */
    public void outOfBoundsAction(){ //每10毫秒走一次
        for(int i=0;i<submarines.length;i++){ //遍历所有潜艇
            if(submarines[i].isOutOfBounds() || submarines[i].isDead()){ //若越界了，或者，死了的
                submarines[i] = submarines[submarines.length-1]; //将越界元素替换为最后一个元素
                submarines = Arrays.copyOf(submarines,submarines.length-1); //缩容
            }
        }

        for(int i=0;i<mines.length;i++){ //遍历所有水雷
            if(mines[i].isOutOfBounds() || mines[i].isDead()){ //若越界了，或者，死了的
                mines[i] = mines[mines.length-1]; //将越界元素替换为最后一个元素
                mines = Arrays.copyOf(mines,mines.length-1); //缩容
            }
        }

        for(int i=0;i<bombs.length;i++){ //遍历所有炸弹
            if(bombs[i].isOutOfBounds() || bombs[i].isDead()){ //若越界了，或者，死了的
                bombs[i] = bombs[bombs.length-1]; //将越界元素替换为最后一个元素
                bombs = Arrays.copyOf(bombs,bombs.length-1); //缩容
            }
        }
    }

    private int score = 0; //玩家得分
    /** 炸弹与潜艇的碰撞 */
    public void bombBangAction(){ //每10毫秒走一次
        for(int i=0;i<bombs.length;i++){ //遍历所有炸弹
            Bomb b = bombs[i]; //获取每一个炸弹
            for(int j=0;j<submarines.length;j++){ //遍历所有潜艇
                SeaObject s = submarines[j]; //获取每一个潜艇
                if(b.isLive() && s.isLive() && b.isHit(s)){ //若都活着并且还撞上了
                    s.goDead(); //潜艇去死
                    b.goDead(); //炸弹去死
                    if(s instanceof EnemyScore){ //若被撞潜艇能得分
                        EnemyScore es = (EnemyScore)s; //将被撞潜艇强转为得分接口
                        score += es.getScore(); //玩家得分
                    }
                    if(s instanceof EnemyLife){ //若被撞潜艇能得命
                        EnemyLife el = (EnemyLife)s; //将被撞潜艇强转为得命接口
                        int num = el.getLife(); //获取命数
                        ship.addLife(num); //战舰增命
                    }
                }
            }
        }
    }

    /** 启动程序的执行 */
    public void action(){
        //键盘侦听器---不要求掌握
        KeyAdapter k = new KeyAdapter() {
            /** 重写keyReleased()按键抬起事件 keyPressed()按键按下事件 */
            public void keyReleased(KeyEvent e) { //按键抬起时会自动触发---不要求掌握
                if(e.getKeyCode()==KeyEvent.VK_SPACE){ //抬起空格键---不要求掌握
                    Bomb obj = ship.shootBomb(); //获取炸弹对象
                    bombs = Arrays.copyOf(bombs,bombs.length+1); //扩容
                    bombs[bombs.length-1] = obj; //将obj添加到bombs最后一个元素上
                }
                if(e.getKeyCode()==KeyEvent.VK_LEFT){ //抬起左键头---不要求掌握
                    ship.moveLeft(); //战舰左移
                }
                if(e.getKeyCode()==KeyEvent.VK_RIGHT){ //抬起右键头---不要求掌握
                    ship.moveRight(); //战舰右移
                }
            }
        };
        this.addKeyListener(k); //添加键盘侦听器---不要求掌握

        Timer timer = new Timer(); //定时器对象
        int interval = 10; //定时间隔(以毫秒为单位)
        timer.schedule(new TimerTask() {
            public void run() { //定时干的事--每10毫秒自动执行run()
                submarineEnterAction(); //潜艇(侦察潜艇、鱼雷潜艇、水雷潜艇)入场
                mineEnterAction();      //水雷入场
                moveAction();           //海洋对象移动
                outOfBoundsAction();    //删除越界的海洋对象
                bombBangAction();       //炸弹与潜艇的碰撞
                repaint(); //重新调用paint()方法---不要求掌握
            }
        }, interval, interval); //定时计划表
    }

    /** 重写JPanel中的paint()画  g:画笔(系统自带的) */
    public void paint(Graphics g){ //每10毫秒走一次
        Images.sea.paintIcon(null,g,0,0); //画海洋图---不要求掌握
        ship.paintImage(g); //画战舰
        for(int i=0;i<submarines.length;i++){ //遍历所有潜艇
            submarines[i].paintImage(g); //画潜艇
        }
        for(int i=0;i<mines.length;i++){ //遍历所有水雷
            mines[i].paintImage(g); //画水雷
        }
        for(int i=0;i<bombs.length;i++){ //遍历所有炸弹
            bombs[i].paintImage(g); //画炸弹
        }

        g.drawString("SCORE: "+score,200,50); //画分--不要求掌握
        g.drawString("LIFE: "+ship.getLife(),400,50); //画命--不要求掌握

    }

    public static void main(String[] args){
        JFrame frame = new JFrame(); //3.
        World world = new World(); //分配窗口中的那一堆对象
        world.setFocusable(true);
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH+16, HEIGHT+39);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); //自动调用paint()方法

        world.action(); //启动程序的执行
    }
}



















