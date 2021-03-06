package com.sxt.thread;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: DeadLock.java
 * @time: 2019/11/8 14:16
 * @desc: 解决死锁
 */

public class DeadLock2 {
    public static void main(String[] args) {
        Makeup2 g1 = new Makeup2(1, "丰光");
        Makeup2 g2 = new Makeup2(2, "师兄");
        g1.start();
        g2.start();
    }
}


// 化妆
class Makeup2 extends Thread {
    static Lipstick lip = new Lipstick();
    static Mirror mir = new Mirror();
    // 选择
    int choice;
    // 名字
    String girlname;

    public Makeup2(int choice, String girlname) {
        this.choice = choice;
        this.girlname = girlname;
    }

    @Override
    public void run() {
        // 化妆
        makeup();
    }

    private void makeup() {
        // 相互持有对方的对象锁，这样才有可能造成死锁
        if (choice == 1) {
            // 获得口红的锁
            synchronized (lip) {
                System.out.println(this.girlname + "-->涂口红");
                // 1秒后想拥有镜子的锁
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (mir) {
                System.out.println(this.girlname + "-->照镜子");
            }
        } else {
            synchronized (mir) {
                System.out.println(this.girlname + "-->照镜子");
                // 2秒后想拥有口红的锁
                try {
                    Thread.sleep(1100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (lip) {
                System.out.println(this.girlname + "-->涂口红");
            }
        }
    }
}