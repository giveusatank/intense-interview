package com.jd;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 *   1. yield：静态方法、释放CPU，不释放锁对象
 *   2. sleep：静态方法、释放CPU，不释放锁对象
 *   3. join：底层就是wait方法、释放CPU、锁对象
 *   4. wait：释放CPU、锁对象
 *   5. notify/notifyAll：不释放CPU、不释放锁对象
 *
 *   6.interrupt：给当前线程打一个中断标记
 *   7.isInterrupted：返回当前线程中断标记，并清除标记
 *   8.interrupted：静态方法、返回当前线程中断标记，并清除标记
 *   9.currentThread：静态方法、返回当前线程对象
 *
 */
public class ThreadCommonMethod {
    @Test
    public void testJoinMethod() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(8);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t1.join();
        System.out.println(Thread.currentThread().getName());
    }

    @Test
    public void testYieldMethod(){
        Thread t1 = new Thread(()->{});
        t1.start();
        //放弃CPU重新竞争
        Thread.yield();
    }

    @Test
    public void testSleepMethod() throws InterruptedException {
        Object locker = new Object();
        Thread t1 = new Thread(()->{
            synchronized (locker){
                try {
                    TimeUnit.SECONDS.sleep(15);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AAA");

        Thread t2 = new Thread(()->{
            synchronized (locker){
                System.out.println(Thread.currentThread().getName());
            }
        },"BBB");

        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println(t2.getState());
        TimeUnit.SECONDS.sleep(100);
    }

    @Test
    public void testInterruptMethod() throws InterruptedException {

        Thread t1 = new Thread(()->{
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);

                } catch (InterruptedException e) {
                    //响应中断进入catch
                    Thread.currentThread().isInterrupted();
                    Thread.interrupted();
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t1.interrupt();
    }
}
