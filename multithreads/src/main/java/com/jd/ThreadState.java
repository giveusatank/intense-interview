package com.jd;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class ThreadState {

    @Test
    public void testThreadNew() throws InterruptedException {

        Object locker = new Object();

        /*//NEW
        Thread t1 = new Thread();
        System.out.println(t1.getState());

        TimeUnit.SECONDS.sleep(1);

        //RUNNABLE
        t1.start();
        System.out.println(t1.getState());*/

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

    public static void main(String[] args) throws InterruptedException {
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

        TimeUnit.SECONDS.sleep(1);
        System.out.println(t3.getState());

        TimeUnit.SECONDS.sleep(5);
        System.out.println(t2.getState());
        System.out.println(t3.getState());
    }
}
