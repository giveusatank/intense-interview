package com.jd;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 *  Thread的状态定义在Thread.STATE枚举类中：
 *  （1）NEW: 新建，未start
 *  （2）RUNNABLE: 就绪 + 运行
 *  （3）BLOCKED: 阻塞（等待锁对象）
 *  （4）WAITING: 无限等待
 *  （5）TIMED_WAITING: 有限等待
 *  （6）TERMINATED: 终止
 *
 */
public class ThreadState {

    @Test
    public void testThreadNew() throws InterruptedException {
        //NEW
        Thread t1 = new Thread();
        System.out.println(t1.getState());

        TimeUnit.SECONDS.sleep(1);

        //RUNNABLE
        t1.start();
        System.out.println(t1.getState());
    }

    @Test
    public void testThreadBlocked(){
        Object locker = new Object();
        Thread t2 = new Thread(() -> {
            synchronized (locker) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AAA");

        t2.start();

        Thread t3 = new Thread(() -> {
            synchronized (locker) {
                System.out.println(Thread.currentThread().getName());
            }
        }, "BBB");

        t3.start();
        System.out.println(t3.getState());
    }

    @Test
    public void testThreadWaiting() throws InterruptedException {
        Object locker = new Object();

        Thread t1 = new Thread(()->{
            synchronized (locker){
                try {
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(10);
                    locker.wait();
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println(t1.getState()); //TIMED_WAITING

        TimeUnit.SECONDS.sleep(10);
        System.out.println(t1.getState()); //WAITING

        synchronized (locker){
            locker.notifyAll();
        }

        System.out.println(t1.getState()); //BLOCKED
        TimeUnit.SECONDS.sleep(1);
        System.out.println(t1.getState()); //RUNNABLE
    }
}
