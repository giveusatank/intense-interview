package com.jd;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程场景问题
 * （1）死锁：
 * （2）生产者、消费者：
 */
public class ThreadCase {

    @Test
    public void testDeadLock() throws InterruptedException {

        Object locker1 = new Object();
        Object locker2 = new Object();

        Thread t1 = new Thread(() -> {
            try {
                synchronized (locker1) {
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (locker2) {

                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                synchronized (locker2) {
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (locker1) {

                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(8);
        System.out.println(t1.getState());
        System.out.println(t2.getState());
    }

    @Test
    public void testConsumerAndProvider() throws InterruptedException {

        BookFactory carFactory = new BookFactory();

        Thread[] producers = new Thread[100];
        Thread[] consumers = new Thread[100];

        for (int i = 0; i <= 99; i++) {
            producers[i] = new Thread(() -> {
                carFactory.provideBooks();
            });
        }
        for (int j = 0; j <= 99; j++) {
            consumers[j] = new Thread(() -> {
                carFactory.consumerBooks();
            });
        }

        for (int i = 0; i <= 99; i++) {
            producers[i].start();
            consumers[i].start();
        }
        TimeUnit.SECONDS.sleep(100);
    }
}

class CarFactory {
    private volatile static int number = 0;
    public synchronized void produceCar() {
        while (number >= 1) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("生产者：当前:" + (number++) + "，之后" + number);
        this.notifyAll();
    }

    public synchronized void consumerCar() {
        while (number <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("消费者：当前:" + (number--) + "，之后" + number);
        this.notifyAll();
    }
}

class BookFactory{
    private static volatile int number = 0;
    ReentrantLock locker = new ReentrantLock();
    Condition consumer = locker.newCondition();
    Condition provider = locker.newCondition();

    public void provideBooks(){
        locker.lock();
        if (number >= 1) {
            try {
                provider.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("生产者：当前:" + (number++) + "，之后" + number);
        consumer.signal();
        locker.unlock();
    }

    public void consumerBooks(){
        locker.lock();
        if (number <= 0) {
            try {
                consumer.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("消费者：当前:" + (number--) + "，之后" + number);
        provider.signal();
        locker.unlock();
    }

}
