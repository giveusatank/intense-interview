package com.jd;

import org.junit.jupiter.api.Test;

/**
 * 单例：
 * （1）懒汉式：可以延迟加载
 * （2）饿汉式：
 * （3）静态内部类：可以延迟加载
 * （4）枚举：
 */
public class ThreadSingleton {

    @Test
    public void testSingleton(){

        LazySingleton lazySingleton1 = LazySingleton.getInstance();
        LazySingleton lazySingleton2 = LazySingleton.getInstance();

        HungrySingleton hungrySingleton1 = HungrySingleton.getInstance();
        HungrySingleton hungrySingleton2 = HungrySingleton.getInstance();

        StaticInnerClassSingleton innerClassSingleton1 = StaticInnerClassSingleton.StaticInnerClass.getInstance();
        StaticInnerClassSingleton innerClassSingleton2 = StaticInnerClassSingleton.StaticInnerClass.getInstance();

        EnumSingleton enumSingleton1 = EnumSingleton.getInstance();
        EnumSingleton enumSingleton2 = EnumSingleton.getInstance();

        System.out.println(lazySingleton1 == lazySingleton2);
        System.out.println(hungrySingleton1 == hungrySingleton2);
        System.out.println(innerClassSingleton1 == innerClassSingleton2);
        System.out.println(enumSingleton1 == enumSingleton2);

    }

}

//懒汉式
class LazySingleton{
    private static volatile LazySingleton INSTANCE;
    private LazySingleton(){}
    public static LazySingleton getInstance(){
        //Double Check Lock：双检查锁
        synchronized (LazySingleton.class){
            if(INSTANCE == null){
                INSTANCE = new LazySingleton();
            }
        }
        return INSTANCE;
    }
}

//饿汉式
class HungrySingleton{
    private static final HungrySingleton INSTANCE = new HungrySingleton();
    private HungrySingleton(){}
    public static HungrySingleton getInstance(){
        return INSTANCE;
    }
}

//静态内部类
class StaticInnerClassSingleton{
    static class StaticInnerClass{
        private static StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
        private StaticInnerClass(){}
        public static StaticInnerClassSingleton getInstance(){
            return INSTANCE;
        }
    }
}

//枚举类
enum EnumSingleton{
    INSTANCE;
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}


