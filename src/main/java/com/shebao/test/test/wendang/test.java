package com.shebao.test.test.wendang;

import org.junit.Test;

public class test {

    @Test
    public void test(){
        /**
         * spring的问题 https://blog.csdn.net/qq_41701956/article/details/116354268
         */
    }
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

    @Test
    public void test4(){
        /**
         * @Autowird注解默认通过byType方式注入
         * @Resource默认通过byName注入，如果没有匹配则通过byType注入
         */
    }

    @Test
    public void test5(){
        /**
         * Mybatis的一级、二级缓存:
         * 答：
         * 1）一级缓存：基于PerpetualCache的HashMap本地缓存，其存储作用域为Session，当Session flush或close之后，该 Session中的所有Cache就将清空，默认打开一级缓存。
         *
         * 2）二级缓存与一级缓存其机制相同，默认也是采用PerpetualCache，HashMap存储，不同在于其存储作用域为Mapper(Namespace)，并且可自定义存储源，如Ehcache。默认不打开二级缓存，要开启二级缓存，使用二级缓存属性类需要实现Serializable序列化接口(可用来保存对象的状态),可在它的映射文件中配置 ；
         *
         * 3）对于缓存数据更新机制，当某一个作用域(一级缓存Session/二级缓存Namespaces)的进行了C/U/D操作后，默认该作用域下所有select中的缓存将被clear

         */
    }

    @Test
    public void test6(){
        /**
         * bean 标签有两个重要的属性（init-method和destroy-method）。用它们你可以自己定制初始化和注销方法。它们也有相应的注解（@PostConstruct和@PreDestroy）。
         */
    }
}
