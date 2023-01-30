package com.shebao.test.test.mianshi;

import org.junit.Test;

public class test {
    @Test
    public void test1(){
        // ACID原则
        /**
         * 1。原子性
         * 2.一致性
         * 3.隔离性
         * 4.持久性
         */
    }

    @Test
    public void test2(){
        // BeanFactory 和 ApplicationContext的区别
        /**
         相同：
         ·Spring提供了两种不同的IOC容器，一个是BeanFactory,另外一个是ApplicationContext,它们都是Java的interface,ApplicationContext继承于BeanFactory(ApplicationContext继承ListableBeanFactory。
         ·它们都可以用来配置XML属性，也支持属性的自动注入。
         。而ListableBeanFactory继承BeanFactory),BeanFactory和ApplicationContext都提供了一种方式，使用getBean("bean name")获取bean。

         不同：
         ·当你调用getBean()方法时，BeanFactory仅实例化bean,而ApplicationContext在启动容器的时候实例化单例bean,不会等待调用getBean()方法时再实例化。
         ·BeanFactory不支持国际化，即i18n,但ApplicationContext提供了对它的支持.
         ·BeanFactory-与ApplicationContext之间的另一个区别是能够将事件发布到注册为监听器的bean.
         ·BeanFactory的一个核心实现是XMLBeanFactory而ApplicationContext的一个核心实现是ClassPathXmlApplicationContext,Web容器的环境我们使用WebApplicationContext并且增加了getServletContext方法。
         ·如果使用自动注入并使用BeanFactory,则需要使用API注册AutoWiredBeanPostProcessor,如果使用ApplicationContext,则可以使用XML进行配置。
         ·简而言之，BeanFactory提供基本的IOC和DI功能，而ApplicationContext提供高级功能，BeanFactory可用于测试和非生产使用，但ApplicationContext,是功能更丰富的容器实现，应该优于BeanFactory
         */
    }

    @Test
    public void test3(){
        // HashMap 和 HashTable的区别
        /**
         * 1.HashTable线程同步，HashMap非线程同步。
         * 2.HashTable:不允许<键，值>有空值，HashMap允许<键，值>有空值。
         * 3.HashTable使用Enumeration,HashMap使用Iterator。
         * 4.HashTable中hash数组的默认大小是11，增加方式的old*2+1,HashMap中hash数组的默认大小是16，增长方式是2的指数倍。
         * 5.hashtable继承与Dictionary类，hashmap:继承自AbstractMap类
         */
    }
}
