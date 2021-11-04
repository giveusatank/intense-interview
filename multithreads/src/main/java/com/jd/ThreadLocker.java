package com.jd;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  synchronized 和 ReentrantLock：
 *
 *  （1）相同、差异点：
 *
 *  （2）
 */
public class ThreadLocker {

    @Test
    public void testSynchronized() throws InterruptedException {
        Object locker = new Object();
        Thread t1 = new Thread(() -> {
            try {
                synchronized (locker) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName());
                    synchronized (locker) {
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println(Thread.currentThread().getName());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
    }

    @Test
    public void testReentrantLock() throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread(()->{
            try {
                lock.lock();
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(String.format("%s释放锁",Thread.currentThread().getName()));
                lock.unlock();
            }
        },"AAA");

        Thread t2 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                //尝试加锁3S
                //boolean res = lock.tryLock(8,TimeUnit.SECONDS);
                //尝试加锁一次
                //boolean res = lock.tryLock();
                //可打断加锁
                //lock.lockInterruptibly();
                System.out.println(lock.isLocked());
                System.out.println(lock.isFair());
                System.out.println(lock.isHeldByCurrentThread());

                /*String mark = res==false?"失败":"成功";
                System.out.println(
                        String.format("%s获取锁%s",
                        Thread.currentThread().getName(),
                        mark));*/

            } catch (Exception e) {
                System.out.println("响应中断");
                e.printStackTrace();
            }
        },"BBB");

        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(20);
    }

}
