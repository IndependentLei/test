package com.shebao.test.test;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BitSetBloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.antherd.smcrypto.sm2.SignatureOptions;
import com.antherd.smcrypto.sm2.Sm2;
import com.antherd.smcrypto.sm4.Sm4;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.shebao.test.config.TaskThreadPool;
import com.shebao.test.model.dto.A;
import com.shebao.test.model.dto.B;
import com.shebao.test.model.entity.Person;
import com.shebao.test.model.entity.Person1;
import com.shebao.test.model.entity.TestPerson;
import com.shebao.test.model.enums.TypeEnum;
import com.shebao.test.test.mapStruct.PersonMapStruct;
import com.shebao.test.test.threadPool.ThreadPoolTest1;
import com.shebao.test.util.SM4;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.testng.internal.thread.DefaultThreadPoolExecutorFactory;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class test {
//    public static void main(String[] args) {
//        Person person = Person.builder()
//                        .name("jdl")
//                        .age("2")
//                        .build();
//        System.out.println(person.toString());
//    }

    @Test
    public void test2() {
        List<String> strings = Collections.singletonList("222");
        System.out.println(strings);
    }

    @Test
    public void test3() {
        Integer integer = 128;
        int i = 128;
        boolean equals = Objects.equals(integer, i);
        System.out.println(equals);
    }

//    @Test
//    public void test4() throws IOException {
//        String filePath = TestFileUtil.getPath()+"excel"+File.separator+"template.xlsx";
//        String newFileName = TestFileUtil.getPath()+UUID.randomUUID() +".xlsx";
//        com.shebao.test.model.entity.Test test = new com.shebao.test.model.entity.Test();
//        test.setNumber(22L);
//        EasyExcel.write(newFileName).withTemplate(filePath).sheet().doFill(test);
//    }

    @Test
    public void test5() {
        List<Person> personList = Arrays.asList(new Person(1L, "小王", "2")
                , new Person(2L, "小李", "2")
                , new Person(3L, "小王", "2")
                , new Person(4L, "小春", "2"));
        Map<String, List<Person>> collect = personList.stream().collect(Collectors.groupingBy(Person::getName));
        System.out.println(collect);
        System.out.println(collect.get("小王"));
    }

    @Test
    public void test6() {
        String str = "1,2,3,4";
        List<String> strList = Splitter.on(",").splitToList(str);
        System.out.println(strList);
    }

    @Test
    public void test7() {
        List<Person> personList = Arrays.asList(new Person(1L, "小王", "2")
                , new Person(2L, "小李", "2")
                , new Person(3L, "小王", "2")
                , new Person(4L, "小春", "2"));
        Map<String, String> collect = personList.stream().collect(Collectors.toMap(Person::getName, Person::getAge, (x, y) -> y));
        System.out.println(collect);

    }

    @Test
    public void test8() {
        List<Integer> typeList = Arrays.asList(1, 1, 2, 1);
        List<String> collect = typeList.stream().map(type -> TypeEnum.fromCode(type).getName()).collect(Collectors.toList());
        String join = Joiner.on(",").join(collect);
        System.out.println(join);
    }

    @Test
    public void test9() {
        List<Long> ids = Lists.newArrayList();
        System.out.println(ids);
        if (CollectionUtils.isNotEmpty(ids)) {
            System.out.println(1);
        }

        HashMap<String, String> A = Maps.newHashMap();
        HashMap<String, String> B = Maps.newHashMap();

        Map<String, Object> collect = A.entrySet()//获取集合
                .stream()//获取流
                .filter(a -> B.keySet().contains(a))//peek支持在每个元素上执行一个操作并且返回新的stream
                // ，我们就利用这个方法转换数据
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));//collect方法用来将流转到集合对象
    }

    @Test
    public void test10() {
        Map<String, String> one = Maps.newHashMap();
        one.put("1", "2");
        one.put("2", "3");
        one.put("3", "4");
        Map<String, String> two = Maps.newHashMap();
        two.put("3", "4");
        two.put("4", "5");
        two.put("6", "7");

        Map<String, String> collectMap = one.entrySet().stream().filter(o -> two.containsKey(o.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println(collectMap);

    }


    @Test
    public void test11() {
        List<Person> personList = Arrays.asList(new Person(1L, "小王", "2")
                , new Person(2L, "小李", "2")
                , new Person(3L, "小王", "2")
                , new Person(4L, "小春", "2"));

        TestPerson testPerson = new TestPerson();
        TestPerson testPerson1 = new TestPerson();
        Person1 person1 = new Person1();
        person1.setPersonList(personList);
        Person1 person11 = new Person1();
        person11.setPersonList(personList);
        List<Person1> person1List = Lists.newArrayList();
        person1List.add(person1);
        person1List.add(person11);
        testPerson.setPersonList(person1List);
        testPerson.setName("111");

        BeanUtils.copyProperties(testPerson, testPerson1);
        System.out.println(testPerson1);
    }

    @Test
    public void test12() {
        long dayToSecond = TimeUnit.DAYS.toSeconds(1);
        System.out.println(dayToSecond);
        long ss = 24 * 60 * 60;
        System.out.println(ss);
    }

    @Test
    public void test13() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 1、普通的时间转换
        String string = new SimpleDateFormat("yyyyMM").format(new Date());
        System.out.println(string);
    }

    @Test
    public void test14() {
        BigDecimal bigDecimal = new BigDecimal(5000);
        BigDecimal multiply = bigDecimal.multiply(new BigDecimal("0.12"));
        System.out.println(multiply);
    }

    @Test
    public void test15() {
        BigDecimal bigDecimal = new BigDecimal(5000);
        System.out.println(bigDecimal.multiply(BigDecimal.valueOf(0.12)));
    }

    @Test
    public void test16() {
        List<Callable<String>> response = Lists.newArrayList();
        List<Person> personList = Arrays.asList(new Person(1L, "小王", "2")
                , new Person(2L, "小李", "2")
                , new Person(3L, "小王", "4")
                , new Person(4L, "小春", "2")
                , new Person(4L, "小春", "4")
                , new Person(4L, "小春", "2")
                , new Person(4L, "小春", "7")
                , new Person(4L, "小春", "9")
                , new Person(4L, "小春", "2")
                , new Person(4L, "小春", "2"));
        Map<String, List<Person>> collect = personList.stream().collect(Collectors.groupingBy(Person::getAge));
        // 使用Callable去执行多线程
        collect.forEach((age, persons) -> response.add(() -> testCallable(persons)));
        List<String> strings = TaskThreadPool.addTask(response);
        System.out.println(strings);
    }

    private String testCallable(List<Person> personList) {
        StringBuilder allAge = new StringBuilder();
        for (Person person : personList) {
            allAge.append(person.getAge());
        }
        return allAge.toString();
    }

    @Test
    public void test17() {
        try {
            File file = new File("E:\\123.pdf");
            FileInputStream fis = new FileInputStream(file);
            File file1 = new File("E:\\2321.pdf");
            FileOutputStream fos = new FileOutputStream(file1);
            byte[] collect = new byte[1024];
            int len;
            while ((len = fis.read(collect)) != -1) {
                fos.write(collect, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test18() {
        System.out.println(getMonthSpace("202204", "202205"));
    }

    /**
     * 获取两个日期相差多少个月
     */
    public static int getMonthSpace(String date1, String date2) {
        try {
            int result = 0;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();

            c1.setTime(sdf.parse(date1));
            c2.setTime(sdf.parse(date2));

            int i = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
            int month = 0;
            if (i < 0) {
                month = -i * 12;
            } else if (i > 0) {
                month = i * 12;
            }
            result = (c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH)) + month;

            return Math.abs(result) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Test
    public void test20() {
        String s = RandomUtil.randomString(16);
        String s1 = RandomUtil.randomNumbers(16);

        String provinceByIdCard = IdcardUtil.getProvinceByIdCard("320804199810261910");
        IdcardUtil.Idcard idcard = new IdcardUtil.Idcard("320804199810261910");
        System.out.println(idcard.getAge());
        System.out.println(idcard.getBirthDate());
        System.out.println(idcard.getCityCode());
        System.out.println(idcard.getGender());
        System.out.println(idcard.getProvince());
        System.out.println(idcard.getProvinceCode());
//        System.out.println(provinceByIdCard);
//        System.out.println(s);
//        System.out.println(s1);

    }


    static {
        Map<String, List<String>> menuMap = new HashMap<>();
        List<String> 肉 = new ArrayList<>();
        肉.add("干锅虾");
        肉.add("干锅鸡杂");
        肉.add("回锅肉");
        肉.add("小葱跑蛋");
        肉.add("青椒跑蛋");
        List<String> 菜 = new ArrayList<>();

        菜.add("酸辣土豆丝");
        菜.add("干锅豆腐");
        菜.add("酸辣白菜");
        菜.add("红烧茄子");
        菜.add("毛豆炒肉");
        菜.add("番茄炒鸡蛋");
        List<String> 汤 = new ArrayList<>();

        List<String> 不辣 = new ArrayList<>();
        不辣.add("干锅豆腐");
        不辣.add("干锅虾");
        不辣.add("大煮干丝");
        不辣.add("小葱跑蛋");
        不辣.add("红烧茄子");
        不辣.add("毛豆炒肉");
        不辣.add("番茄炒鸡蛋");

        List<String> 辣 = new ArrayList<>();
        辣.add("酸辣土豆丝");
        辣.add("干锅鸡杂");
        辣.add("酸辣白菜");
        辣.add("青椒跑蛋");
        辣.add("回锅肉");


        汤.add("大煮干丝");
        menuMap.put("肉", 肉);
        menuMap.put("菜", 菜);
        menuMap.put("汤", 汤);
        menuMap.put("不辣", 不辣);
        menuMap.put("辣", 辣);


    }

    @Test
    public void test() {
        //
    }

    @Test
    public void test21() {
        class Cat {
            public Cat(String name) {
                this.name = name;
            }

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
        Cat c1 = new Cat("王磊");
        Cat c2 = new Cat("王磊");
        System.out.println(c1.equals(c2));
    }

    @Test
    public void test22() {
        String userHome = System.getProperty("user.home");
        System.out.println(userHome);
    }

    @Test
    public void test23() {

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject3 = new JSONObject();
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);

        jsonObject.put("id", 1);
        jsonObject1.put("id", 11);
        jsonObject2.put("id", 10);
        jsonObject3.put("id", 5);
        List<JSONObject> jsonObjects = jsonArray.toJavaList(JSONObject.class);
        JSONObject jsonObject4 = jsonObjects.stream().max(Comparator.comparingInt(a -> a.getInteger("id"))).get();
        System.out.println(jsonObject4);

        List<JSONObject> ids = jsonObjects.stream().sorted(Comparator.comparingInt(obj -> obj.getInteger("id"))).collect(Collectors.toList());
        System.out.println(ids);

        Stream<int[]> stream = Stream.of(new int[]{1, 2, 3});

        jsonObjects.stream().map(jsonObject5 -> jsonObject5.getString("id").split("  ")).collect(Collectors.toList());

    }

    @AllArgsConstructor
    @Data
    class Clone1 {
        public Integer id;
    }

    @Data
    class CloneTest implements Cloneable {
        public Integer id;
        public Clone1 clone1;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    @Test
    public void test25() throws AWTException {
        Robot robot = new Robot();
        robot.mouseMove(0, 0);
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = defaultToolkit.getScreenSize();
        double height = screenSize.getHeight();
        double width = screenSize.getWidth();
        Dimension size = screenSize.getSize();
        System.out.println(size);
        System.out.println(height);
        System.out.println(width);

        robot.mouseMove((int) (10), (int) (height));
        Point point = MouseInfo.getPointerInfo().getLocation();
//        System.out.println(point);
//        robot.mousePress(InputEvent.BUTTON1_MASK);
//        robot.
//        robot.mouseRelease(InputEvent.BUTTON1_MASK);
////        robot.keyPress(KeyEvent.VK_Q);
////        robot.keyRelease(KeyEvent.VK_Q);
//        int[] ks = new int[]{KeyEvent.VK_C,KeyEvent.VK_L,KeyEvent.VK_S, KeyEvent.VK_ENTER};
        pressMouse(robot, InputEvent.BUTTON1_MASK, 500);
    }

    private static void pressMouse(Robot r, int m, int delay) {
        r.mousePress(m);
        r.delay(10);
        r.mouseRelease(m);
        r.delay(delay);
    }

    @Test
    public void test26() {
        Map<String, String> map = Maps.newHashMap();
        map.put("", "");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry);
        }

        String str = "{\"method\":\"post\",\"sourceUrlCode\":\"ythzf\",\"interfaceName\":\"zhlsxQueryCompanyOperator\",\"parameter\":\"{\\\"tyshxydm\\\":\\\"91360125MA39U3Y5XR\\\",\\\"jbrlb\\\":\\\"2\\\",\\\"withSx\\\":\\\"false\\\",\\\"status\\\":\\\"1\\\"}\"}";
    }

    @Test
    public void test27() {
        String str = "{\"interfaceName\":\"sbDoYwsl\",\"parameter\":\"{\\\"channelCode\\\":\\\"2002\\\",\\\"interfaceCode\\\":\\\"TYSL0106\\\",\\\"tzBusiHeader\\\":{\\\"aaa027\\\":\\\"360106\\\",\\\"aaz010\\\":\\\"3000000001237470\\\",\\\"aaa028\\\":\\\"2\\\",\\\"bae813\\\":\\\"360106\\\",\\\"bae814\\\":\\\"南昌市红谷滩新区\\\",\\\"userid\\\":\\\"91360125MA39U3Y5XR\\\",\\\"username\\\":\\\"江西人力云企业服务有限公司\\\",\\\"projid\\\":\\\"29746ba68d984e0eafb9981355021f62\\\"},\\\"data\\\":{\\\"searchText\\\":\\\"362201199601140618\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aab998\\\":\\\"91360125MA39U3Y5XR\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aac999\\\":\\\"500001049402\\\",\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac058\\\":\\\"01\\\",\\\"aac050\\\":\\\"22\\\",\\\"DIC_QYZS_AAE160\\\":\\\"2102080101\\\",\\\"aae035\\\":\\\"2022-10-16T16:00:00.000Z\\\",\\\"aaa121\\\":\\\"F210208\\\",\\\"ac05List\\\":[{\\\"aaz159\\\":\\\"3620000006928774\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"410\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1413601000000200\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0,\\\"aaa042\\\":0.002,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":26,\\\"_uid\\\":26},{\\\"aaz159\\\":\\\"3620000006928775\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"210\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1210360100000100\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0.005,\\\"aaa042\\\":0.005,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":27,\\\"_uid\\\":27},{\\\"aaz159\\\":\\\"3620000006928776\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"110\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1110360100000100\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0.08,\\\"aaa042\\\":0.16,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":28,\\\"_uid\\\":28}],\\\"aae160\\\":\\\"2102080101\\\"}}\",\"projid\":\"29746ba68d984e0eafb9981355021f62\"}";
        String str2 = "{\"interfaceName\":\"qgtcAddBjxxApasInfo\",\"parameter\":\"{\\\"projid\\\":\\\"29746ba68d984e0eafb9981355021f62\\\",\\\"dataBody\\\":{\\\"formData\\\":{\\\"form\\\":{\\\"searchText\\\":\\\"362201199601140618\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aab998\\\":\\\"91360125MA39U3Y5XR\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aac999\\\":\\\"500001049402\\\",\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac058\\\":\\\"01\\\",\\\"aac050\\\":\\\"22\\\",\\\"DIC_QYZS_AAE160\\\":\\\"2102080101\\\",\\\"aae035\\\":\\\"2022-10-17\\\"}},\\\"gridData\\\":{\\\"datagrid266\\\":[{\\\"searchText\\\":null,\\\"aab001\\\":null,\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aac999\\\":\\\"500001049402\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac003\\\":\\\"冯青云\\\",\\\"aac004\\\":\\\"1\\\",\\\"aac005\\\":\\\"01\\\",\\\"aac006\\\":\\\"19960114\\\",\\\"aac007\\\":\\\"20220801\\\",\\\"aac058\\\":\\\"01\\\",\\\"bae535\\\":null,\\\"bac238\\\":null,\\\"aac009\\\":null,\\\"bab001\\\":null,\\\"aac010\\\":null,\\\"aae006\\\":null,\\\"aac012\\\":null,\\\"aae005\\\":null,\\\"bac243\\\":null,\\\"aae159\\\":null,\\\"aae007\\\":null,\\\"bae226\\\":null,\\\"aac161\\\":\\\"CHN\\\",\\\"aac225\\\":null,\\\"aac226\\\":null,\\\"aac227\\\":null,\\\"aac228\\\":null,\\\"aac229\\\":null,\\\"aac230\\\":null,\\\"aac231\\\":null,\\\"aac232\\\":null,\\\"aac233\\\":null,\\\"aac234\\\":null,\\\"aac235\\\":null,\\\"aac084\\\":\\\"0\\\",\\\"aac081\\\":null,\\\"aac060\\\":\\\"1\\\",\\\"aac031\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aac222\\\":null,\\\"_id\\\":25,\\\"_uid\\\":25}],\\\"datagrid\\\":[{\\\"aaz159\\\":\\\"3620000006928774\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"410\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1413601000000200\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0,\\\"aaa042\\\":0.002,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":26,\\\"_uid\\\":26},{\\\"aaz159\\\":\\\"3620000006928775\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"210\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1210360100000100\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0.005,\\\"aaa042\\\":0.005,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":27,\\\"_uid\\\":27},{\\\"aaz159\\\":\\\"3620000006928776\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"110\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1110360100000100\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0.08,\\\"aaa042\\\":0.16,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":28,\\\"_uid\\\":28}],\\\"companyGrid\\\":[],\\\"companyCbGrid\\\":[]},\\\"gridSelData\\\":{\\\"datagrid266\\\":[{\\\"searchText\\\":null,\\\"aab001\\\":null,\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aac999\\\":\\\"500001049402\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac003\\\":\\\"冯青云\\\",\\\"aac004\\\":\\\"1\\\",\\\"aac005\\\":\\\"01\\\",\\\"aac006\\\":\\\"19960114\\\",\\\"aac007\\\":\\\"20220801\\\",\\\"aac058\\\":\\\"01\\\",\\\"bae535\\\":null,\\\"bac238\\\":null,\\\"aac009\\\":null,\\\"bab001\\\":null,\\\"aac010\\\":null,\\\"aae006\\\":null,\\\"aac012\\\":null,\\\"aae005\\\":null,\\\"bac243\\\":null,\\\"aae159\\\":null,\\\"aae007\\\":null,\\\"bae226\\\":null,\\\"aac161\\\":\\\"CHN\\\",\\\"aac225\\\":null,\\\"aac226\\\":null,\\\"aac227\\\":null,\\\"aac228\\\":null,\\\"aac229\\\":null,\\\"aac230\\\":null,\\\"aac231\\\":null,\\\"aac232\\\":null,\\\"aac233\\\":null,\\\"aac234\\\":null,\\\"aac235\\\":null,\\\"aac084\\\":\\\"0\\\",\\\"aac081\\\":null,\\\"aac060\\\":\\\"1\\\",\\\"aac031\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aac222\\\":null,\\\"_id\\\":25,\\\"_uid\\\":25}],\\\"datagrid\\\":[{\\\"aaz159\\\":\\\"3620000006928774\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"410\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1413601000000200\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0,\\\"aaa042\\\":0.002,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":26,\\\"_uid\\\":26},{\\\"aaz159\\\":\\\"3620000006928775\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"210\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1210360100000100\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0.005,\\\"aaa042\\\":0.005,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":27,\\\"_uid\\\":27},{\\\"aaz159\\\":\\\"3620000006928776\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"110\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1110360100000100\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0.08,\\\"aaa042\\\":0.16,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":28,\\\"_uid\\\":28}],\\\"companyGrid\\\":[],\\\"companyCbGrid\\\":[]},\\\"tzBusiHeader\\\":{\\\"aaa027\\\":\\\"360106\\\",\\\"aaz010\\\":\\\"3000000001237470\\\",\\\"aaa028\\\":\\\"2\\\",\\\"bae813\\\":\\\"360106\\\",\\\"bae814\\\":\\\"南昌市红谷滩新区\\\",\\\"userid\\\":\\\"91360125MA39U3Y5XR\\\",\\\"username\\\":\\\"江西人力云企业服务有限公司\\\",\\\"projid\\\":\\\"29746ba68d984e0eafb9981355021f62\\\"},\\\"channelCode\\\":\\\"2002\\\"},\\\"dataType\\\":\\\"JSON\\\"}\",\"projid\":\"29746ba68d984e0eafb9981355021f62\"}";
        JSONObject jsonObject = JSON.parseObject(str);
        JSONObject jsonObject2 = JSON.parseObject(str2);
        System.out.println(jsonObject);
        System.out.println(jsonObject2);
        System.out.println(new Date());
    }

    @Test
    public void tset28() {
//        System.out.printf("%s月无费用%n", DateUtil.offsetMonth(new Date(),2).month());
        System.out.println(DateUtil.offsetMonth(new Date(), 6).month());
    }

    @Test
    public void test29() {
        System.out.println(DateUtil.beginOfYear(new Date()).getTime());
        // 1640966400000
        // 1640966400000
    }

    @Test
    public void test30() {
        try {
            List<String> list = new ArrayList<>(0);
            Field elementData = ArrayList.class.getDeclaredField("elementData");
            elementData.setAccessible(true);
            Object o = elementData.get(list);

            list.add("1");
            System.out.println(list.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test31() {
        System.out.println(IdcardUtil.getGenderByIdCard("130530198711061549"));
    }

    @Test
    public void test32() {
        Map<String, String> map = Maps.newHashMap();
        map.put("", "");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    @Test
    public void test33() {
        System.out.println(StringUtils.contains("1,2,3", "1"));
    }

    @Test
    public void test34() {
        StopWatch sw = new StopWatch("测试");
        sw.start("task1");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000000; i++) {
            sb.append(i);
        }
        sw.stop();

        sw.start("task2");
        for (int i = 0; i < 1000000; i++) {
            sb.append(i);
        }
        sw.stop();

        for (StopWatch.TaskInfo taskInfo : sw.getTaskInfo()) {
            System.out.println(taskInfo.getTimeMillis());
        }

    }


    // map中一些一些方法的使用
    @Test
    public void test111() {
        Map<String, Integer> map = new HashMap<>();

        map.put("apple", 1);
        map.put("pink", 1);
        map.put("dog", 1);
        map.put("cat", 1);

        // 如果当前key存在，就什么操作也不进行，如果不存在，将key存入，第二个参数是存入value的操作（key）->{....}
        map.computeIfAbsent("apple1", (key) -> 3);

        // 如果存在，就进行计算，如果不存在，就忽略
        map.computeIfPresent("pink1", (key, val) -> {
            System.out.println(val);
            return val;
        });

        // 不存在就添加进去，存在什么操作都不进行
        map.putIfAbsent("pink1", 4);

        // 不关系存不存在，存在就更新value，不存在就put进去，返回值为新的value
        map.compute("11", (key, val) -> 1);

        // key不存在就直接存入value，存在就进行第三个参数的操作
        map.merge("11", 2, (key, val) -> 3);

// {pink1=4, apple=1, pink=1, apple1=3, cat=1, dog=1}

        System.out.println(map);
    }

    @Test
    public void test222() {
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (Objects.equals(3, next)) {
                iterator.remove();
            }
        }
        System.out.println(Arrays.toString(integers.toArray()));
    }

    @Test
    public void test35() {
        System.out.println(DateUtil.format(DateUtil.parse("202212", DatePattern.SIMPLE_MONTH_PATTERN).offset(DateField.HOUR_OF_DAY, -8), DatePattern.UTC_MS_PATTERN));
    }

    @Test
    public void test36() {
        Person person = new Person(1L, "11", "11");

        Person person1 = new Person();
        person1.setId(2L);

        String[] beanField = getBeanField(person1);
        BeanUtil.copyProperties(person1, person, beanField);

        System.out.println(person);
    }

    public String[] getBeanField(Object obj) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        List<String> list = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            try {
                declaredField.setAccessible(true);
                Object o = declaredField.get(obj);
                if (Objects.isNull(o)) {
                    list.add(declaredField.getName());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return list.toArray(new String[0]);
    }

    @Test
    public void test37() {
        // Optional 用法
        Person person = new Person();
        person.setName("ab111");
        String s = Optional.ofNullable(person)
                .map(Person::getName)
                .map(String::toUpperCase)
                .orElse("");
        System.out.println(s);
    }

    @Test
    public void test38() {
        System.out.println(DateUtil.offset(new Date(), DateField.MONTH, 1).monthBaseOne());
    }

    @Test
    public void test39() {
        Person p = new Person(1L, "1", "1");
        Person1 person1 = PersonMapStruct.INSTANT.pTop1(p);
        System.out.println(person1);
    }

//    public static void main(String[] args) {
//        // jvm 栈针的使用
//        method1();
//    }

//    public static void method1(){
//        int i = method2(3, 2);
////        method1();
//    }

    public static int method2(int a, int b) {
        int c = Math.max(a, b);
        return c;
    }

    @Test
    public void test40() {
        int i = 0;
        try {
            System.out.println("try");
            i = 5;
            throw new Exception();
        } catch (Exception e) {
            System.out.println("catch");
            i = 20;
            return;
        } finally {
            System.out.println("finally");
            i = 10;
        }

//        System.out.println(i);
    }

    @Test
    public void test41() {
        int i = test42();
        System.out.println(i);
//        System.out.println(i);
    }

    public static int test42() {
        try {
            return 10;
        } finally {
            return 20;
        }
    }

    @Test
    public void test43() {
        /**
         *     public ThreadPoolExecutor(int corePoolSize,  核心线程数
         *                               int maximumPoolSize, 最大线程数
         *                               long keepAliveTime, 存活时间
         *                               TimeUnit unit, 时间单位
         *                               BlockingQueue<Runnable> workQueue, 阻塞队列
         *                               ThreadFactory threadFactory, 线程工程
         *                               RejectedExecutionHandler handler 拒绝策略){}
         */
        ExecutorService executorService = new ThreadPoolExecutor(2,
                4,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                new DefaultThreadFactory("工厂1"),
                new ThreadPoolExecutor.CallerRunsPolicy());
        /**
         * 拒绝策略
         * new ThreadPoolExecutor.AbortPolicy()  中止策略。默认的拒绝策略，直接抛出 RejectedExecutionException。调用者可以捕获这个异常，然后根据需求编写自己的处理代码。
         * new ThreadPoolExecutor.DiscardPolicy()  抛弃策略。什么都不做，直接抛弃被拒绝的任务。
         * new ThreadPoolExecutor.DiscardOldestPolicy()  抛弃最老策略。抛弃阻塞队列中最老的任务，相当于就是队列中下一个将要被执行的任务，然后重新提交被拒绝的任务。如果阻塞队列是一个优先队列，那么“抛弃最旧的”策略将导致抛弃优先级最高的任务，因此最好不要将该策略和优先级队列放在一起使用。
         * new ThreadPoolExecutor.CallerRunsPolicy()  调用者运行策略。在调用者线程中执行该任务。该策略实现了一种调节机制，该策略既不会抛弃任务，也不会抛出异常，而是将任务回退到调用者（调用线程池执行任务的主线程），由于执行任务需要一定时间，因此主线程至少在一段时间内不能提交任务，从而使得线程池有时间来处理完正在执行的任务。
         *
         */

        executorService.submit(() -> {
            log.info("1111");
        });
    }

    @Test
    public void test44() {
        String key = "111";
        int p;
        System.out.println((p = key.hashCode()) ^ (p >>> 16));
    }

    @Test
    public void test45() {
        System.out.println(Math.round(-1.5));
        System.out.println(Math.round(-0.5));
    }

    abstract class AA {

    }

    @Test
    public void test46() {
        /**
         * volatile和synchronized的区别
         * volatile本质是在告诉jvm当前变量在寄存器（工作内存）中的值是不确定的，需要从主存中读取； synchronized则是锁定当前变量，只有当前线程可以访问该变量，其他线程被阻塞住。
         * volatile仅能使用在变量级别；synchronized则可以使用在变量、方法、和类级别的
         * volatile仅能实现变量的修改可见性，不能保证原子性；而synchronized则可以保证变量的修改可见性和原子性
         * volatile不会造成线程的阻塞；synchronized可能会造成线程的阻塞。
         * volatile标记的变量不会被编译器优化；synchronized标记的变量可以被编译器优化
         */
    }

    @Test
    public void test47() {
        /**
         * 自动装配原理
         * 1）通过注解@SpringBootApplication=>@EnableAutoConfiguration=>@Import({AutoConfigurationImportSelector.class})实现自动装配
         * 2）AutoConfigurationImportSelector类中重写了ImportSelector中selectImports方法，批量返回需要装配的配置类
         * 3）通过Spring提供的SpringFactoriesLoader机制，扫描classpath下的META-INF/spring.factories文件，读取需要自动装配的配置类
         * 4）依据条件筛选的方式，把不符合的配置类移除掉，最终完成自动装配
         */
    }

    @Test
    public void test48() {
        /**
         * 说一下 mybatis 的一级缓存和二级缓存？
         * 一级缓存是session级别的缓存，默认开启，当查询一次数据库时，对查询结果进行缓存，如果之后的查询在一级缓存中存在，则无需再访问数据库；
         * 二级缓存是sessionFactory级别的缓存，需要配置才会开启。当进行sql语句查询时，先查看一级缓存，如果不存在，访问二级缓存，降低数据库访问压力。
         */
    }

    @Test
    public void test49() {
        /**
         * 事务(Transaction)是并发控制的基本单位。所谓事务，它是一个操作序列，这些操作要么都执行，要么都不执行，它是一个不可分割的工作单位。例如，银行转帐工作：从一个帐号扣款并使另一个帐号增款，这两个操作要么都执行，要么都不执行。
         * 1、数据库事务必须具备ACID特性，ACID是Atomic（原子性）、Consistency（一致性）、Isolation（隔离性）和Durability（持久性）的英文缩写。
         * 1）原子性（Atomicity）
         * 一个事务(transaction)中的所有操作，要么全部完成，要么全部不完成，不会结束在中间某个环节。事务在执行过程中发生错误，会被回滚（Rollback）到事务开始前的状态，就像这个事务从来没有执行过一样。（oracle通过redo和undo日志保证）
         * 2）一致性（Consistency）
         * 事务的一致性指的是在一个事务执行之前和执行之后数据库都必须处于一致性状态。如果事务成功地完成，那么系统中所有变化将正确地应用，系统处于有效状态。如果在事务中出现错误，那么系统中的所有变化将自动地回滚，系统返回到原始状态。
         * 3）隔离性（Isolation）
         * 指的是在并发环境中，当不同的事务同时操纵相同的数据时，每个事务都有各自的完整数据空间。由并发事务所做的修改必须与任何其他并发事务所做的修改隔离。事务查看数据更新时，数据所处的状态要么是另一事务修改它之前的状态，要么是另一事务修改它之后的状态，事务不会查看到中间状态的数据。
         * 4）持久性（Durability）
         * 指的是只要事务成功结束，它对数据库所做的更新就必须永久保存下来。即使发生系统崩溃，重新启动数据库系统后，数据库还能恢复到事务成功结束时的状态。
         * 2、事务的（ACID）特性是由关系数据库管理系统（RDBMS，数据库系统）来实现的。数据库管理系统采用日志来保证事务的原子性、一致性和持久性。日志记录了事务对数据库所做的更新，如果某个事务在执行过程中发生错误，就可以根据日志，撤销事务对数据库已做的更新，使数据库退回到执行事务前的初始状态。
         * 数据库管理系统采用锁机制来实现事务的隔离性。当多个事务同时更新数据库中相同的数据时，只允许持有锁的事务能更新该数据，其他事务必须等待，直到前一个事务释放了锁，其他事务才有机会更新该数据。
         * 3、在关系型数据库中，事务的隔离性分为四个隔离级别，在解读这四个级别前先介绍几个关于读数据的概念。
         * 1）脏读（Dirty Reads）：所谓脏读就是对脏数据（Drity Data）的读取，而脏数据所指的就是未提交的数据。也就是说，一个事务正在对一条记录做修改，在这个事务完成并提交之前，这条数据是处于待定状态的（可能提交也可能回滚），这时，第二个事务来读取这条没有提交的数据，并据此做进一步的处理，就会产生未提交的数据依赖关系。这种现象被称为脏读。（oracle通过undo日志保证，读未提交的数据时，放回undo后的数据）
         * 2）不可重复读（Non-Repeatable Reads）：一个事务先后读取同一条记录，但两次读取的数据不同，我们称之为不可重复读。也就是说，这个事务在两次读取之间该数据被其它事务所修改。
         * 3）幻读（Phantom Reads）：一个事务按相同的查询条件重新读取以前检索过的数据，却发现其他事务插入了满足其查询条件的新数据，这种现象就称为幻读。
         * 4、事务四个隔离级别对比：
         * 1）未提交读（Read Uncommitted）：SELECT语句以非锁定方式被执行，所以有可能读到脏数据，隔离级别最低。（读不锁）
         * 2）提交读（Read Committed）：只能读取到已经提交的数据。即解决了脏读，但未解决不可重复读。（读锁，等写完）
         * 3）可重复读（Repeated Read）：在同一个事务内的查询都是事务开始时刻一致的，InnoDB的默认级别。在SQL标准中，该隔离级别消除了不可重复读，但是还存在幻读。(写锁，等读完)
         * 4）串行读（Serializable）：完全的串行化读，所有SELECT语句都被隐式的转换成SELECT ... LOCK IN SHARE MODE，即读取使用表级共享锁，读写相互都会阻塞。隔离级别最高。(以上3个均为行锁/记录锁，当前为表锁)
         */
    }

//    public static void main(String[] args) {
//        String str = "23.2,14,55,-87.98,45";
//        char[] chars = str.toCharArray();
//        int szie = 0;
//        for (int i = 0; i < chars.length; i++) {
//            if(chars[i] == ','){
//                szie++;
//            }
//        }
//        if(chars[chars.length-1] != ','){
//            szie++;
//        }else {
//
//        }
//        float[] floats = new float[szie];
//        int k = 0;
//        int j = 0;
//        for (int i = 0; i < chars.length; i++) {
//            if((chars[i] == ',' || i == chars.length-1)){
//                String substring = i == chars.length-1 ? str.substring(k, i+1) : str.substring(k, i);
//                boolean flag = false;
//                if(substring.contains("-")){
//                    substring = substring.substring(1);
//                    flag = true;
//                }
//                float v = toFloat(substring);
//                if(flag){
//                    v = -v;
//                }
//                floats[j++] = v;
//                k = i+1;
//            }
//        }
//
//        System.out.println(Arrays.toString(floats));
//    }

    public static float toFloat(String str) {
        float sum = 0;
        float h = 1f;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                h = 0.1f;
            } else {
                int i1 = Integer.parseInt(str.substring(i, i + 1));
                if (h >= 1) {
                    sum = sum * h + i1;
                    h = h * 10;
                } else {
                    sum = sum + i1 * h;
                    h = h / 10;
                }
            }
        }
        return sum;
    }

    @Test
    public void tt() {
        System.out.println(containsDuplicate(new int[]{1, 2, 3, 1}));
    }


    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }
        return nums.length == set.size();
    }


    public int maxSum(int[] sz) {
        int pr = sz[0];
        int sum = 0;
        for (int i : sz) {
            if (sum > 0) {
                sum += i;
            } else {
                sum = i;
            }
            pr = Math.max(sum, pr);
        }
        return pr;
    }

    @Test
    public void test444() {
        System.out.println(maxSum(new int[]{-2, 3, -1, 1, -3}));
    }


    class MovingAverage {
        private Integer size;
        private ArrayDeque<Integer> arrayDeque;

        private double sum;

        public MovingAverage(int size) {
            this.size = size;
            arrayDeque = new ArrayDeque<>(size);
            this.sum = 0;
        }

        public double next(int val) {
            if (this.size == arrayDeque.size()) {
                sum -= arrayDeque.poll();
            }

            arrayDeque.offer(val);
            sum += val;
            return sum / arrayDeque.size();
        }
    }

    @Test
    public void test4441() {
        // inputs = [[3], [1], [10], [3], [5]]
        MovingAverage ma = new MovingAverage(3);
        double next = ma.next(3);
        System.out.println(next);
        double next1 = ma.next(1);
        System.out.println(next1);
        double next2 = ma.next(10);
        System.out.println(next2);
        double next3 = ma.next(3);
        System.out.println(next3);
        double next4 = ma.next(5);
        System.out.println(next4);
    }


    private int zhongLei(int[] arr) {
        Set<Integer> set = new HashSet<>(arr.length);
        for (int i : arr) {
            set.add(i);
        }
        return Math.min(set.size(), arr.length / 2);
    }

    @Test
    public void test4442() {
        System.out.println(zhongLei(new int[]{1, 1, 2, 3}));
    }

    @Test
    public void test4443() {
        System.out.println(toRom(1994));
    }

    public String toRom(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] rom = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (values[i] <= num) {
                sb.append(rom[i]);
                num -= values[i];
            }
        }
        return sb.toString();
    }

    @Test
    public void test4444() {
        Table<String, String, Integer> table = HashBasedTable.create();
//存放元素
        table.put("Hydra", "Jan", 20);
        table.put("Hydra", "Feb", 28);

        table.put("Trunks", "Jan", 28);
        table.put("Trunks", "Feb", 16);

//取出元素
        Integer dayCount = table.get("Hydra", "Feb");
    }

    @Test
    public void test123() {
//        merge(new int[]{1,2,3,0,0,0},3,new int[]{2,5,6},3);
        merge1(new int[]{2, 0}, 1, new int[]{1}, 1);
        /**
         * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
         * 输出：[2,2]
         */
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (m == 0) {
            for (int i = 0; i < nums2.length; i++) {
                nums1[i] = nums2[i];
            }
            return;
        }
        if (n == 0) {
            return;
        }
        int j = 0;
        for (int i = 0; i < m + n; i++) {
            if (nums1[i] > nums2[j]) {
                for (int k = m + n - 1; k > i; k--) {
                    nums1[k] = nums1[k - 1];
                }
                nums1[i] = nums2[j];
                if (++j >= n) {
                    break;
                }
            } else {
                if (nums1[i] == 0) {
                    nums1[i] = nums2[j++];
                }
            }
        }
    }

    public void merge1(int[] nums1, int m, int[] nums2, int n) {
        int m1 = m - 1;
        int n1 = n - 1;
        int wz = m + n - 1;
        int cur = 0;
        while (m1 >= 0 || n1 >= 0) {
            if (m1 == -1) {
                cur = nums2[n1--];
            } else if (n1 == -1) {
                cur = nums1[m1--];
            } else if (nums1[m1] > nums2[n1]) {
                cur = nums1[m1--];
            } else {
                cur = nums2[n1--];
            }
            nums1[wz--] = cur;
        }
    }


    @Test
    public void test124() {
        System.out.println(Arrays.toString(intersect(new int[]{1, 2, 2, 1}, new int[]{2, 2})));
    }

    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return intersect(nums2, nums1);
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums1) {
            if (map.computeIfPresent(i, (k, v) -> v += 1) == null) {
                map.put(i, 1);
            }
        }

        int[] ints = new int[nums1.length];
        int index = 0;
        for (int i : nums2) {
            Integer count = map.getOrDefault(i, 0);
            if (count > 0) {
                ints[index++] = i;
                --count;
                if (count > 0) {
                    map.put(i, count);
                } else {
                    map.remove(i);
                }
            }
        }
        return Arrays.copyOfRange(ints, 0, index);
    }

    @Test
    public void test125() {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
    }

    public int maxProfit(int[] prices) {
        if (prices.length == 0) return 0;
        int ans = 0;
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            sum += (prices[i] - prices[i - 1]);
            ans = Math.max(ans, sum);
            if (sum < 0) sum = 0;
        }
        return ans;
    }


    // [[1,2],[3,4]], r = 1, c = 4

    @Test
    public void test126() {
        System.out.println(matrixReshape(new int[][]{{1, 2}, {3, 4}}, 2, 2));
    }

    public int[][] matrixReshape(int[][] nums, int r, int c) {
        int m = nums.length;
        int n = nums[0].length;
        if (m * n != r * c) {
            return nums;
        }

        int[][] ans = new int[r][c];
        for (int x = 0; x < m * n; ++x) {
            ans[x / c][x % c] = nums[x / n][x % n];
        }
        return ans;
    }

    @Test
    public void test127() {
        System.out.println(generate(6));
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> lists = new ArrayList<>(numRows);
        Integer[][] numRowsArr = new Integer[numRows][];
        for (int i = 0; i < numRows; i++) {
            Integer[] nums = new Integer[i + 1];
            for (int j = 0; j <= i; j++) {
                if (j == 0 || i == j) {
                    nums[j] = 1;
                }
            }
            numRowsArr[i] = nums;
        }

        for (int i = 2; i < numRows; i++) {
            for (int j = 1; j < i; j++) {
                numRowsArr[i][j] = numRowsArr[i - 1][j] + numRowsArr[i - 1][j - 1];
            }
        }
        for (Integer[] integers : numRowsArr) {
            lists.add(Arrays.asList(integers));
        }
        return lists;
    }

    @Test
    public void test128() {
        to("11");
    }

    public void to(String str) {
        String str2 = str;


        str2 = "222";
        System.out.println(str2 == str);
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public class DemoData {
        @ExcelProperty("字符串标题")
        private String string;
        @ExcelProperty("日期标题")
        private Date date;
        @ExcelProperty("数字标题")
        private Double doubleData;
        /**
         * 忽略这个字段
         */
        @ExcelIgnore
        private String ignore;
    }

    @Test
    public void simpleWrite() {
        // 注意 simpleWrite在数据量不大的情况下可以使用（5000以内，具体也要看实际情况），数据量大参照 重复多次写入

        // 写法1 JDK8+
        // since: 3.0.0-beta1
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        // 如果这里想使用03 则 传入excelType参数即可
//        // 分页查询数据
//        EasyExcel.write(fileName, DemoData.class)
//                .sheet("模板")
//                .doWrite(this::data);
//
//        // 写法2
//        fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        // 如果这里想使用03 则 传入excelType参数即可
//        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());

        // 写法3
        fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, DemoData.class).build();
//            for (int i = 0; i < 3; i++) {
            WriteSheet writeSheet = EasyExcel.writerSheet("模板" + 1).build();
            excelWriter.write(data(), writeSheet);
//            }
        } finally {
            if (null != excelWriter) {
                excelWriter.close();
            }
        }
    }

    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
//            list.add(data);
        }
        return list;
    }

    @Test
    public void test165() {
        System.out.println(data().stream()
                .map(DemoData::getDoubleData)
                .reduce(Double::sum).orElse(null));
        System.out.println(data().stream()
                .map(DemoData::getDoubleData)
                .reduce(1D, Double::sum));
        System.out.println(data().stream()
                .map(DemoData::getDoubleData)
                .reduce(1D, Double::sum, Double::sum));
    }

    @Test
    public void test175() {
        Double dd = 10.01;
        Double dd1 = 0D;
        System.out.println(dd / dd1);

        Integer integer = 10;
        Integer ii = 0;
        System.out.println(integer / ii);
    }

    @Test
    public void test176() {
        List<DemoData> collect = data().stream().peek(item -> item.setString("1111")).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test177() {
        long sTime = System.currentTimeMillis();
        CompletableFuture<BigDecimal> A = CompletableFuture.supplyAsync(
                () -> getA("A")
        );
        CompletableFuture<BigDecimal> B = CompletableFuture.supplyAsync(
                () -> getB("B")
        );
        CompletableFuture<BigDecimal> C = CompletableFuture.supplyAsync(
                () -> getC("C")
        );

        BigDecimal bigDecimal = Stream.of(A, B, C)
                .map(CompletableFuture::join)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        long eTime = System.currentTimeMillis();
        System.out.println((eTime - sTime) / 1000);

        System.out.println(bigDecimal);
    }


    public long getRandom() {
        return RandomUtil.randomLong(1, 5);
    }

    public BigDecimal getPrice() {
        return RandomUtil.randomBigDecimal(BigDecimal.valueOf(50), BigDecimal.valueOf(5000));
    }

    public BigDecimal getA(String item) {
        try {
            long random = getRandom();
            BigDecimal price = getPrice();
            log.info("{}-{}-{}", item, random, price);
            TimeUnit.SECONDS.sleep(random);
            return price;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getB(String item) {
        try {
            long random = getRandom();
            BigDecimal price = getPrice();
            log.info("{}-{}-{}", item, random, price);
            TimeUnit.SECONDS.sleep(random);
            return price;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getC(String item) {
        try {
            long random = getRandom();
            BigDecimal price = getPrice();
            log.info("{}-{}-{}", item, random, price);
            TimeUnit.SECONDS.sleep(random);
            return price;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test179() throws ExecutionException, InterruptedException {
        // 没有返回结果
        CompletableFuture<Void> voidGatA = CompletableFuture.runAsync(() -> getA(""));
        System.out.println(voidGatA.get());
        CompletableFuture<BigDecimal> getA = CompletableFuture.supplyAsync(() -> getA(""));
        System.out.println(getA.get());
    }

    @Data
    @AllArgsConstructor
    public class FData {
        private Boolean isSucc;
        private String fStr;
    }
//    private List<FData> fDataTest(){
//        List<FData> fDataList = Lists.newArrayList();
//        fDataList.add(new FData(true,"1"));
//        fDataList.add(new FData(false,"1"));
//        return fDataList;
//    }
//    @Test
//    public void test180(){
//        boolean b = fDataTest().stream()
//                .allMatch(FData::getIsSucc);
//        System.out.println(b);
//    }

    @Test
    public void test181() {
        Double d = 1.0;
        Double a = Math.ceil(d / 1000);
        System.out.println(a);
        System.out.println(Objects.equals(d, a));
    }

    @Test
    public void test182() {
        BloomFilter<Integer> bloomFilter = BloomFilter.create(
                Funnels.integerFunnel(),
                100000,
                0.001
        );
        bloomFilter.put(1);
        bloomFilter.put(2);
        bloomFilter.put(3);
        bloomFilter.put(4);
        bloomFilter.put(5);
        bloomFilter.put(6);

        boolean b = bloomFilter.mightContain(7);
        System.out.println(b);

        boolean b1 = bloomFilter.mightContain(2);
        System.out.println(b1);

    }


//    public List<R> sendAsyncBatch(List<P> list, Executor executor, TaskLoader<R,P> loader) {
//
//        List<R> resultList = Collections.synchronizedList(Lists.newArrayList());
//        if (CollectionUtils.isNotEmpty(list)) {
//            Executor finalExecutor = executor;
//            // 将任务拆分分成每50个为一个任务
//            CollUtil.split(list, 50)
//                    .forEach(tempList -> {
//                        CompletableFuture[] completableFutures = tempList.stream()
//                                .map(p -> CompletableFuture.supplyAsync(() -> {
//                                                    try {
//                                                        return loader.load(p);
//                                                    } catch (InterruptedException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                    return null;
//                                                }, finalExecutor)
//                                                .handle((result, throwable) -> {
//                                                    if (Objects.nonNull(throwable)) {
//                                                        //log.error("async error:{}", throwable.getMessage());
//                                                    } else if (Objects.nonNull(result)) {
//                                                        //log.info("async success:{}", result);
//                                                    } else {
//                                                        //log.error("async result is null");
//                                                    }
//                                                    return result;
//                                                }).whenComplete((r, ex) -> {
//                                                    if (Objects.nonNull(r)) {
//                                                        resultList.add((R) r);
//                                                    }
//                                                })
//                                ).toArray(CompletableFuture[]::new);
//                        CompletableFuture.allOf(completableFutures).join()
//                        System.out.println(resultList.size());
//                    });
//        }
//        return resultList;
//    }

    @Test
    public void test183() throws IOException {
//        BufferedImage bufferedImage = QrCodeUtil.generate("qrcode_test",300,300);
//        File file = new File(System.getProperty("user.home") + File.separator + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN));
//        System.out.println(file.getAbsolutePath());
//        ImageIO.write(bufferedImage, "jpg", file);
    }

    @Test
    public void test184() {
        String pat = "\\d{4}-\\d{2}-\\d{2}";
        System.out.println(Pattern.matches(pat, "2023-04-19"));
        System.out.println(Pattern.matches(pat, "202204"));
    }

    @Test
    public void test185() {
        int i = Stream.of(1, 2, 3, 4, 5, 6, 7, 18, 9, 10)
                .reduce((x, y) -> {
                    System.out.println("x = " + x + "   " + "y =" + y);
                    return x + y;
                }).get();
        System.out.println("i = " + i);        // i = 65
    }

    @Data
    @AllArgsConstructor
    public class Book implements Comparable<Book> {

        private String bookName;

        private String author;

        private Integer age;

        private Integer price;

        @Override
        public int compareTo(Book o) {
            return age - o.age;
        }
    }

//    @Test
//    public void test186(){
//        List<Book> books = Stream.of(
//                new Book("剑来", "烽火", 38, 100),
//                new Book("斗破", "土豆", 34, 60),
//                new Book("完美", "辰东", 37, 70)
//        ).collect(Collectors.toList());
//
//        Integer i = books.parallelStream().reduce(0, (integer, book) -> {
//            System.out.println("线程 " + Thread.currentThread().getId() + " ===> " + "integer = " + integer);
//            System.out.println("线程 " + Thread.currentThread().getId() + " ===> " + "bookPrice = " + book.getPrice());
//            return integer + book.getPrice();
//        }, (integer1, integer2) -> {
//            System.out.println("线程 " + Thread.currentThread().getId() + " ===> " + "integer1 = " + integer1);
//            System.out.println("线程 " + Thread.currentThread().getId() + " ===> " + "integer2 = " + integer2);
//            return integer1 + integer2;
//        });
//
//        System.out.println("i = " + i);
//    }


    @Test
    public void test187() {
        List<String[]> collect = Stream.of(
                new String[]{"a,b,c", "1"},
                new String[]{"d,e,f"},
                new String[]{"g,h,j"}
        ).collect(Collectors.toList());

        collect.stream()
                .flatMap(Arrays::stream)
                .peek(System.out::println)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    @Test
    public void test188() {
        Map<String, String> map = Maps.newHashMap();
        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");
        map.put("4", "1");
        map.put("5", "1");

        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            if (stringStringEntry.getKey().equals("3")) {
                map.remove(stringStringEntry.getKey());
            } else {
                System.out.println(stringStringEntry);
            }
        }
    }

    @Test
    public void test189() {
        Map<String, List<com.alibaba.fastjson2.JSONObject>> opMap = Maps.newHashMap();

//        opMap.computeIfAbsent("1",key-> {
//            List<com.alibaba.fastjson2.JSONObject> list = opMap.get(key);
//            if( null == list ){
//                list = com.google.common.collect.Lists.newArrayList();
//            }
//            com.alibaba.fastjson2.JSONObject js = new com.alibaba.fastjson2.JSONObject();
//            js.put("1","2");
//            list.add(js);
//            return list;
//        });
//
//        opMap.computeIfAbsent("1",key-> {
//            List<com.alibaba.fastjson2.JSONObject> list = opMap.get(key);
//            if( null == list ){
//                list = com.google.common.collect.Lists.newArrayList();
//            }
//            com.alibaba.fastjson2.JSONObject js = new com.alibaba.fastjson2.JSONObject();
//            js.put("1","2");
//            list.add(js);
//            return list;
//        });

        List<com.alibaba.fastjson2.JSONObject> jsonObjects = opMap.computeIfPresent("1", (key, value) -> {
            List<com.alibaba.fastjson2.JSONObject> list = opMap.get(key);
            com.alibaba.fastjson2.JSONObject js = new com.alibaba.fastjson2.JSONObject();
            js.put("1", "2");
            list.add(js);
            return list;
        });
        if (null == jsonObjects) {
            List<com.alibaba.fastjson2.JSONObject> jsonObjectList = Lists.newArrayList();
            com.alibaba.fastjson2.JSONObject js = new com.alibaba.fastjson2.JSONObject();
            js.put("1", "2");
            jsonObjectList.add(js);
            opMap.put("1", jsonObjectList);
        }
        List<com.alibaba.fastjson2.JSONObject> jsonObject1 = opMap.computeIfPresent("1", (key, value) -> {
            List<com.alibaba.fastjson2.JSONObject> list = opMap.get(key);
            com.alibaba.fastjson2.JSONObject js = new com.alibaba.fastjson2.JSONObject();
            js.put("1", "2");
            list.add(js);
            return list;
        });
    }


    @Test
    public void test190() {
        Integer a = new Integer(1);
        Integer b = new Integer(1);


        Integer c = 1;
        Integer d = Integer.valueOf(1);

        System.out.println(a == b); // false

        System.out.println(c == d); // true

        System.out.println(a == c); // false

        System.out.println(b == d); // false
    }


    @Test
    public void test191() {
        String a = "1";

        String b = "2";

        String ab = "12";

        String c = a + b;

        System.out.println(ab == c);
    }

    @Test
    public void test192() {
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("2");

        set.add("1");

        set.forEach(item -> {

        });
    }

    @Test
    public void test193() {
        try {
            File file = new File("D:\\test\\test.txt");
            RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
//            accessFile.writeBytes("1111");
            for (int i = 0; i < 5; i++) {
                int index = i;
                CompletableFuture.runAsync(() -> {
                    try {
                        accessFile.writeBytes(index + "--->" + index);
                        System.out.println(accessFile.getFilePointer());
                    } catch (Exception e) {
                        log.info("error {}", e.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("111");
        }
    }

    @Test
    public void test194() throws IOException {
        String mergeName = "D:\\test\\merge.txt";
        File file = new File(mergeName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        try (RandomAccessFile mergeFile = new RandomAccessFile(mergeName, "rw");) {
            String baseUrl = "D:\\test\\test%s.txt";
            for (int i = 0; i < 3; i++) {
                int index = i;
                mergeFile(String.format(baseUrl, index), mergeFile);
            }
        } catch (Exception e) {

        }
    }

    private void mergeFile(String fileName, RandomAccessFile mergeFile) {

        File file = new File(fileName);
        try (RandomAccessFile in = new RandomAccessFile(file, "rw");) {
            String count = in.readLine();
            while (count != null) {
                mergeFile.writeChars(count);
                count = in.readLine();
            }
        } catch (IOException e) {
        }
    }

    @Test
    public void test195() {
        Semaphore semaphore = new Semaphore(1);

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "---->正在开始");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "---->正在结束");
                } catch (Exception e) {
                    System.out.println(111);
                } finally {
                    semaphore.release();
                }
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {

        }

    }

    @Test
    public void deque() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.push("1");
        deque.push("2");
        deque.push("3");

        log.info("deque {}", JSON.toJSONString(deque));

        for (String item : deque) {
            log.info("deque item {}", JSON.toJSONString(item));
        }

        deque.addLast("1");

        log.info("deque {}", JSON.toJSONString(deque));

        deque.remove();

        log.info("deque {}", JSON.toJSONString(deque));
    }


    @Test
    public void test196() {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(5);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            new Thread(() -> {
                try {
                    blockingQueue.put(String.valueOf(index));
                    log.info("{}---->添加成功", index);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {

        }

        for (int i = 0; i < 10; i++) {
            final int index = i;
            new Thread(() -> {
                blockingQueue.remove(String.valueOf(index));
                log.info("{}---->删除成功", index);
            }).start();
        }
    }

    @Test
    public void test197() {
        int index = 1;
        do {
            try {
                if (index % 2 == 0) {
                    continue;
                }
                if (index == 5) {
                    throw new RuntimeException("error");
                }
            } catch (Exception e) {
                log.error("~~~~ {}", e.getMessage());
            } finally {
                log.info("~~~~~ {}", index);
                index++;
            }
        } while (index < 10);
    }


    @Test
    public void test198() {
        List<String> stringList = Arrays.asList("1", "2", "3", "4", "5");
        String str = stringList.stream()
                .filter(item -> Objects.equals("6", item))
                .findFirst()
                .orElse("null");
        System.out.println(str);
    }

    @AllArgsConstructor
    @Data
    static class Test199 {
        private String name;
        private String phone;
    }


//    @Test
//    public void test199(){
//        List<Test199> test199List = Lists.newArrayList();
//        test199List.add(new Test199("1","11111"));
//        test199List.add(new Test199("2","22222"));
//        test199List.add(new Test199("3","33333"));
//        test199List.add(new Test199("4","11111"));
//
//        List<Test199> newList = Lists.newArrayList();
//        List<Test199> noList = Lists.newArrayList();
//        test199List.stream()
//                .collect(Collectors.groupingBy(Test199::getPhone))
//                .forEach((k,v)->{
//                    if(1 < CollectionUtils.size(v)){
//                        noList.addAll(v);
//                    }else {
//                        newList.addAll(v);
//                    }
//                });
//        System.out.println(newList);
//        System.out.println(noList);
//    }

    @Test
    public void test200() {
        /**
         * ArrayBlockingQueue：一个基于数组的有界阻塞队列。
         * LinkedBlockingQueue：一个基于链表的可选有界阻塞队列。
         * PriorityBlockingQueue：一个支持优先级排序的无界阻塞队列。
         * DelayQueue：一个支持延迟获取元素的无界阻塞队列。
         * SynchronousQueue：一个不存储元素的阻塞队列，用于线程间的直接传输。
         */


    }

    @Test
    public void test201() {
        BigDecimal bigDecimal = BigDecimal.valueOf(1700.0);
        String json = "{\"js\":1700}";
        JSONObject jsonObject = JSON.parseObject(json);
        if (bigDecimal.compareTo(jsonObject.getBigDecimal("js")) == 0) {
            System.out.println("111");
        } else {
            System.out.println("222");
        }
    }


    @Test
    public void test202() {
        try {
            CompletableFuture<String> demo = new CompletableFuture<>();
            demo.complete("success");
            System.out.println(demo.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test203() {
        CountDownLatch count = new CountDownLatch(1);
        CompletableFuture.runAsync(() -> {
            try {
                int index = 0;
                do {
                    index++;
                    if (index == 10) {
//                        System.out.println(index);
                        return;
                    }
                } while (index < 20);

                System.out.println(111);
            } finally {
                count.countDown();
            }

        });
        try {
            count.await();
            System.out.println("main");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void test204() {
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < 50; i++) {
            list.add(i + "");
        }
        List<List<String>> partition = com.google.common.collect.Lists.partition(list, 20);
        System.out.println(partition);
        for (List<String> strings : partition) {
            strings.removeIf(item -> {
                if (Integer.parseInt(item) % 2 == 0) {
                    return true;
                } else {
                    return false;
                }
            });
            System.out.println(partition);
        }
    }

    @Test
    public void test205() {
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < 50; i++) {
            list.add(i + "");
        }
        list.forEach(item -> {
            if (item.equals("20")) {
                return;
            } else {
                System.out.println(item);
            }
        });
    }

    @Test
    public void test206() {
        int HASH_INCREMENT = 0x61c88647;
        ggget(HASH_INCREMENT);
    }

    public void ggget(int a) {
        System.out.println(a);
    }

//    @Test
//    public void test207() {
//        /**
//            // 泛型方法接受 Number 及其子类的参数
//            void myGenericMethod (List < ? extends Number > list){
//                // 方法体
//            }
//
//            // 泛型方法接受 Integer 及其父类的参数
//            void myGenericMethod (List < ? super Integer > list){
//                // 方法体
//            }
//         **/
//
//        Test209 test209 = new Test209();
//
//        List<String> stringList = Lists.newArrayList();
//        stringList.add("1");
//
//        List<Integer> test1 = Lists.newArrayList();
//        test1.add(1);
//
//        List<Number> numberList = Lists.newArrayList();
//        numberList.add(1);
//
//        // 泛型方法接受 Number 及其子类的参数
//        test209.myGenericMethod1(test1);
//        test209.myGenericMethod1(numberList);
//        // 报错
//        // test209.myGenericMethod1(stringList);
//
//
//
//        // 泛型方法接受 Integer 及其父类的参数
//        test209.myGenericMethod(test1);
//        test209.myGenericMethod(numberList);
//        // 报错
//        // test209.myGenericMethod(stringList);
//
//    }

    interface Test208 {
        // 泛型方法接受 Number 及其子类的参数
        void myGenericMethod1(List<? extends Number> list);

        // 泛型方法接受 Integer 及其父类的参数
        void myGenericMethod(List<? super Integer> list);
    }

//    class Test209 implements Test208{
//
//        @Override
//        public void myGenericMethod1(List<? extends Number> list) {
//            System.out.println("--->"+list.toString());
//        }
//
//        @Override
//        public void myGenericMethod(List<? super Integer> list) {
//            System.out.println("--->"+list.toString());
//        }
//    }


//    static class Father {
//        public Object name;
//
//        public void sout(){
//            System.out.println(this.name.toString());
//        }
//    }
//
//
//    static class Son1 extends Father{
//        public Son1(){
//
//        }
//
//        {
//            super.name = new Object();
//        }
//    }
//
//
//    static class Son2 extends Father{
//        {
//            super.name = new Object();
//        }
//    }
//
//    public static void main(String[] args) {
//        Son1 son1 = new Son1();
//        Son2 son2 = new Son2();
//
//        son1.sout();
//        son2.sout();
//    }

    private static final ThreadLocal<String> arTl = new ThreadLocal<>();

    @Test
    public void test210() {
//        arTl.set("1111");
        System.out.println(arTl.get());
        arTl.remove();
        System.out.println(arTl.get());

        arTl.set("222");
        System.out.println(arTl.get());
    }

    @Test
    public void test211() {
        System.out.println(DateUtil.format(DateUtil.offset(DateUtil.beginOfMonth(new Date()), DateField.DAY_OF_MONTH, 14), DatePattern.NORM_DATE_PATTERN));
    }

    @Test
    public void test212() {
        String msg = "1111";

        String msg1 = "";

        String msg2 = msg + msg1;

        System.out.println();
    }

    @Test
    public void test213() throws InterruptedException {

        ExecutorService executorService = new ThreadPoolExecutor(2,
                5,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                new DefaultThreadFactory("工厂1"),
                new ThreadPoolExecutor.CallerRunsPolicy());

        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<>(2);

        executorService.execute(() -> {
            for (int i = 0; i < 20; i++) {
                if (!linkedBlockingDeque.offer(String.valueOf(i))) {
                    log.info("第" + i + "个添加不进去");
                } else {
                    log.info("第" + i + "个添加已经进去");
                }
            }
        });
        for (int i = 0; i < 20; i++) {
            linkedBlockingDeque.take();
        }
    }


    @Resource
    A a;

    @Resource
    B b;

    @Test
    public void test214() {
        System.out.println(a.InstantiationTracingBeanPostProcessor);
        System.out.println(b.InstantiationTracingBeanPostProcessor);
    }

    @Test
    public void test215() {
        LinkedHashMap<String, Object> linkedHashMap = Maps.newLinkedHashMap();
        linkedHashMap.put("1", "1");
        linkedHashMap.put("2", "2");
        linkedHashMap.put("3", "3");
        linkedHashMap.put("4", "4");

        JSONObject jsonObject = new JSONObject(linkedHashMap);

        System.out.println(JSON.toJSON(jsonObject));

    }

    @Data
    public class tet111 {
        private List<String> list;
    }

    @Test
    public void test216() {
        tet111 tet = new tet111();
        tet.setList(Arrays.asList("1", "2", "3"));
        List<String> collect = tet.getList().stream().filter(item -> Objects.equals("2", item)).collect(Collectors.toList());
        tet.setList(collect);

        tet.getList().add("6");
        System.out.println(JSON.toJSONString(tet.getList()));
    }

    @Test
    public void test217(){
        List<String> list = Lists.newArrayList();
        list.add("202405");
        list.add("202205");
        list.add("202301");

        DateTime dateTime = list.stream()
                .map(item -> DateUtil.parse(item, DatePattern.SIMPLE_MONTH_PATTERN))
                .max(Comparator.naturalOrder()).orElse(null);
        if(null!=dateTime){
            String parse = DateUtil.format(dateTime, DatePattern.SIMPLE_MONTH_PATTERN);
            System.out.println(parse);
        }
    }

    @Test
    public void test218(){
        int times = 3;
        boolean flag = false;
        while (!flag && times-- > 0) {
            System.out.println(1111);
        }
    }


    @Test
    public void test219(){
        int times = 3;
        boolean flag = false;
        while (!flag && times-- > 0) {
            System.out.println(1111);
        }
    }

    @Test
    public void test220(){
        List<String> list = new ArrayList<>(4);
        list.add("2");
        list.add("1");
        list.add("4");
        list.add("5");

        for (int i = 0; i <list.size(); i++) {
            if("2".equals(list.get(i))){
                list.remove(i);
            }
        }

    }

    @Test
    public void test221(){
        List<String> list = new ArrayList<>(4);
        list.add("2");
        list.add("1");
        list.add("4");
        list.add("5");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if("2".equals(next)){
                iterator.remove();
            }else {
                System.out.println(next);
            }
        }
    }

    @Data
    @AllArgsConstructor
    static class Test223{
        private String idNo;
        private String name;
        private String age;
        private String ossKey;
    }

    @Test
    public void test223(){
        List<Test223> test223List = Lists.newArrayList();
        Map<String,String> map = Maps.newHashMap();
        for (int i = 0; i <100; i++) {
            String s = RandomUtil.randomNumbers(18);
            Test223 test223 = new Test223(s,String.valueOf(i),String.valueOf(i),"");
            test223List.add(test223);
            map.put(s,JSON.toJSONString(test223));
        }
        List<Callable<Boolean>> callableList = Lists.newArrayList();
        List<List<Test223>> partition = com.google.common.collect.Lists.partition(test223List, 20);

        partition.forEach(item->{
            callableList.add(()->{
                TimeUnit.SECONDS.sleep(5);
                String ossKey = RandomUtil.randomString(6);
                item.forEach(kk->{
                    kk.setOssKey(ossKey);
                });
                return true;
            });
        });
        TaskThreadPool.addTask(callableList);
        System.out.println(JSON.toJSON(partition));
    }

    public int[] StringToInt(String[] arr){
        int[] array = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
                array[i] = Integer.parseInt(arr[i]);
        }
        return array;
    }

    public static byte[] intToBytes(int a){
        byte[] ans=new byte[4];
        for(int i=0;i<4;i++)
            ans[i]=(byte)(a>>(i*8));//截断 int 的低 8 位为一个字节 byte，并存储起来
        return ans;
    }

    @Test
    public void test224(){
        SignatureOptions signatureOptions4 = new SignatureOptions();
        signatureOptions4.setHash(true);
        String sigValueHex4 = Sm2.doSignature("appId=460000010&data={\"appcode\":\"460000099\",\"hnCode\":\"111\"}&reqNo=3cd81124-9888-4e85-910a-6d1305278295&serviceName=ylzms.hnocs.userstruct.special.smscode&timestamp=1694497809260&key=hjikY0WSw6r6rHPS", "0088eacdcb541553ea1bac38cee836ba4d652de6a8202ee0505505fba48af06e39",signatureOptions4);
        System.out.println(sigValueHex4);
//        String s = Sm2.doEncrypt("appId=460000010&data={\"appcode\":\"460000099\",\"hnCode\":\"4601994269996\"}&reqNo=680f3cda-cf1c-4a36-a67d-12d9f4daa6aa&serviceName=ylzms.hnocs.userstruct.special.smscode&timestamp=1694165408305&key=hjikY0WSw6r6rHPS", "0088eacdcb541553ea1bac38cee836ba4d652de6a8202ee0505505fba48af06e39");
//        System.out.println(s);
//         4553018d4f6bc142cf92de0dbdb036dba0f2246eb92b7d899db6a47d646174d7ea978f0895319a6ef2f8dde5a5469a67384dbd9357f99cb7f3369aac048dcb89
        // 2fc094a0458d1920289496a517d0f765b4b755170aaf33ac8ac7a36730546eab4d2bd93ad01395d6992916c943a56b82304f693d388b53b9dbf42c33e3744ec5
//        int[] eee = new int[]{52, 54, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 48, 48, 48};
//        byte[] bytes = SM4.sm4Main("hjikY0WSw6r6rHPS".getBytes(StandardCharsets.UTF_8), eee, 1);
//        System.out.println(bytes);
//        String hjikY0WSw6r6rHPS = Sm4.encrypt("hjikY0WSw6r6rHPS", Arrays.toString(Sm4.utf8ToArray("4600000100000000"))).toUpperCase();
//        System.out.println(hjikY0WSw6r6rHPS);
//        String encryptData1 = Sm4.encrypt("{\"appcode\":\"460000099\",\"hnCode\":\"111\",\"smsCode\":\"222\"}",hjikY0WSw6r6rHPS);
//        // 9C670DFA72D9759BFDFC1FF575AFD66394D19F1D7024E4DCAFD0551974B4788150707E82BAA1155A42D361DC3C6C534E23699DF01CF2CC83E6604BEC7FDD8DC5
//        System.out.println(encryptData1);
    }

    @Test
    public void test225(){
        System.out.println(Arrays.toString(Sm4.utf8ToArray("4600000100000000")));
    }

    @Test
    public void test226(){
        List<String> list = com.google.common.collect.Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }
        List<List<String>> partition = com.google.common.collect.Lists.partition(list,30);
        List<String> reduce = partition.stream()
                .reduce(com.google.common.collect.Lists.newArrayList(), (ylist, item) -> {
                    ylist.addAll(item);
                    return ylist;
                });
        System.out.println(reduce);
    }

    @Test
    public void test227(){
        String str = "11111111";
        String ss = str.intern();
        System.out.println(str == ss);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    static class Com implements Comparable<Com>{
        private Integer mode;
        private Integer mode1;
        private String name;

        @Override
        public int compareTo(Com o) {
            if(this.getMode().equals(4) &&this.getMode().equals(o.getMode())){
                return this.getMode1().compareTo(o.getMode1());
            } else if(this.getMode().equals(o.getMode())){
                return 0;
            }else if(this.getMode() < o.getMode() ){
                if(this.getMode() ==1 && o.getMode() ==2){
                    return 1;
                }else {
                    return -1;
                }
            }else {
                if(o.getMode() ==1 && this.getMode() ==2){
                    return -1;
                }else {
                    return 1;
                }
            }
        }
    }

    @Test
    public void test228(){
        List<Com> comList = Lists.newArrayList();
        for (int i = 0; i < 3 ; i++) {
            Com com = new Com(RandomUtil.randomInt(1, 4),RandomUtil.randomInt(1, 5),String.valueOf(i));
            comList.add(com);
        }
        Com com = comList.stream().max(Com::compareTo).orElse(null);
        System.out.println(com);
    }

    @Test
    public void test229(){
        String format = String.format("单位%s+个人%s", "5%", "5%");
        System.out.println(format);
    }

    @Test
    public void test230(){
        System.out.println(RandomUtil.randomNumbers(15));
    }

    @Test
    public void test231(){
//        BitMapBloomFilter bitMap = BloomFilterUtil.createBitMap(1000);
        BitSetBloomFilter bitMap = BloomFilterUtil.createBitSet(2000,1000,3);
        bitMap.add("968");
        for (int i = 0; i <100; i++) {
            bitMap.add(RandomUtil.randomNumbers(1000));
        }
        if(bitMap.contains("968")){
            System.out.println(bitMap);
        }
    }

    @Test
    public void test234() {
        ReentrantLock lock = new ReentrantLock();
        new Thread(() -> {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        }, "thread-1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
        }
        new Thread(() -> {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        }, "thread-2").start();



        CountDownLatch countDownLatch = new CountDownLatch(2);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (Exception e) {

                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test236() throws InterruptedException {
        LinkedBlockingDeque<String> str = new LinkedBlockingDeque<>();
        String poll = str.poll(5,TimeUnit.SECONDS);
        String take = str.take();
        System.out.println(111);
    }

    @Test
    public void test235() {
        /**
         *  ctl: 一个int类型是4个字节，32位，高三位 表示 线程池的状态，其他29为表示线程池的 个数
         *  线程池的状态 ：
         *      RUNNING     : -1  可以正常的接受正常的任务
         *      SHUTDOWN    : 0   调用 SHUTDOWN 方法  无法添加新的任务，但是可以继续处理队列里面的任务，并且工作线程清零
         *      STOP        : 1   调用 SHUTDOWNNOW方法
         *      TIDYING     : 2
         *      TERMINATED  : 3
         *  线程池的状态流转
         *                   --- shutdown() ------- SHUTDOWN-----  (任务执行完并且工作线程清零)
         *      RUNNING ----|                                   |-------- TIDYING -----terminated()---- TERMINATED
         *                  ---- shutdownNow() ---- STOP -------- 工作线程清零
         */
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(0,
                1,
                0L,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>(1),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        executorService.shutdownNow();
        executorService.shutdown();
        executorService.execute(() -> {
            try {
                TimeUnit.HOURS.sleep(1);
                System.out.println(1);
            }catch (Exception e){

            }
        });

        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(2);
            }catch (Exception e){

            }
        });
        // //检查线程池 是否是 SHUTDOWN 状态
        // ,如果 “是SHUTDOWN 状态” && “不是SHUTDOWN or  firstTask不是null or  workQueue不是空” 就不能添加

//        executorService.shutdown();
//      1100000000000000000000000000000
//      11100000000000000000000000000000
//      11111111111111111111111111111
//
    }

    @Test
    public void test237() {
        int i = 0;
        retry:
        while (true) {
            if (i == 10) {
                break;
            }
            while (true) {
                i++;
                System.out.println(i);
                if (i == 10) {
                    continue retry;
                }
            }
        }
    }

    @Test
    public void test238(){
        List<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.forEach(p -> {
            new Thread(() -> {
                System.out.println(System.identityHashCode(list));
            }).start();
        });
    }

    @Test
    public void test239(){
        new MyRunnable(new Thread(),()->{
            System.out.println(111);
        }).thread.start();
    }


    static class MyRunnable implements Runnable{

        final Thread thread;

        Runnable firstTask;

        public MyRunnable(Thread thread,Runnable firstTask) {
            this.thread = thread;
            this.firstTask = firstTask;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    @Test
    public void test240(){
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }
        try {
            TimeUnit.HOURS.sleep(10);
        }catch (Exception e){

        }
    }


    @Test
    public void test250(){
        /**
         * 类加载过程
         * 1.加载：根据全类名，去找class文件，解析成二进制，转成方法区的运行时数据结构
         * 2、验证：类的字节码是否符合规范
         * 3、准备：被static修饰的属性分配内存空间并初始化，并给默认值，如果还被final你直接被赋值
         * 3、解析：常量池内的符号引用转换为直接引用。主要解析的是 类或接口、字段、类方法、接口方法、方法类型、方法句柄等符号引用
         * 4、初始化：执行类构造器 <clinit>()（静态代码块） 方法，初始化类变量，执行静态代码块
         */
    }


    @Test
    public void  test251(){
        HashMap<String,String> map = new HashMap<>();
        map.put("1","1");

        String s = map.get(null);


        ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("1","1");
        String s1 = concurrentHashMap.get("1");
    }

    @Test
    public void test252() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        readLock.lock();
        readLock.unlock();

        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        writeLock.unlock();
    }

    @Test
    public void test253(){
        List<String> list1 = null;
        List<String> list2 = Arrays.asList("2");
        Collection<String> union = CollectionUtils.union(list1, list2);
        System.out.println(union);
    }

    @Test
    public void test254(){
        CopyOnWriteArrayList<String> copy = new CopyOnWriteArrayList<>();
        copy.add("1");
        copy.get(0);

        CopyOnWriteArraySet<String> copySet = new CopyOnWriteArraySet<>();
        copySet.add("s");
        copySet.remove("S");
    }

    @Test
    public void test255(){
        HashMap<String,String> map = new HashMap<>(17);
        map.put("null",null);
        map.put("null",null);

        ConcurrentHashMap<String,String> map1 = new ConcurrentHashMap<>();
        map1.put("1","1");

        LinkedList<String> strings = new LinkedList<>();
        strings.add("1");

        Hashtable hashtable = new Hashtable();
        hashtable.put("1","");
    }

    @Test
    public void test256() {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(2,
                20,
                0L,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>(2000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        System.out.println();
        executorService.execute(() -> System.out.println(Thread.currentThread().getName()));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {

        }
        executorService.execute(() -> System.out.println(Thread.currentThread().getName()));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {

        }
        executorService.execute(() -> System.out.println(Thread.currentThread().getName()));

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("", "");
    }

    @Test
    public void test257() {
        /**
         * 写：独占锁
         * 读：共享锁
         * 锁的降级，升级
         * 降级：写锁变成读锁 （允许）
         * 升级：读锁变成写锁 （不允许）
         */
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();

        writeLock.lock();
        writeLock.unlock();

        readLock.lock();


        StampedLock lock = new StampedLock();
        lock.readLock();

        lock.unlockRead(1);

        lock.writeLock();

        lock.unlockWrite(1);


    }

    @Test
    public void test258() {
//        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println("完成了"));

        for (int i = 0; i < 5; i++) {
            final int finalI = i + 1;
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(finalI);
                    cyclicBarrier.await();
//                    cyclicBarrier.reset();
                    System.out.println(Thread.currentThread().getName() + "正在执行");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {

        }
    }

    @Test
    public void test259() {
        ReentrantLock lock = new ReentrantLock();
        Condition A = lock.newCondition();
        Condition B = lock.newCondition();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    A.await();
                    System.out.println(111);
                } catch (Exception e) {
                    log.error("异常", e);
                } finally {
                    lock.unlock();
                }
            }).start();
        }


        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }
        new Thread(() -> {
            lock.lock();
            try {
                A.signalAll();
                System.out.println("signalAll");
            } catch (Exception e) {
                log.error("异常", e);
            } finally {
                System.out.println("unlock");
                lock.unlock();
            }
        }).start();

        try {
            Thread.sleep(10000000);
        }catch (Exception e){

        }
    }

    @Test
    public void test260(){
        List<Long> list = new ArrayList<>();
        list.add(1L);
        List<Long> list1 = new ArrayList<>();
        list1.add(1L);

        boolean b1 = Objects.deepEquals(list.toArray(), list1.toArray());
        System.out.println(b1);
    }

    @Test
    public void test261(){
        // 项目启动 初始化 SqlSessionFactory 工厂
        // 执行：
        //      1、通过 MapperProxy获取 mapper代理对象
        //      2、反射调用方法
        //      3、mapperMethod 调用 execute方法 选择执行方式（INSERT | UPDATE | DELETE |SELECT）
        //      4、判断是否分页，通过 sqlSession 调用查询方法，（在通过 sqlSessionProxy 调用查询方法）
        //      5、根据（类名+方法名）获取 MappedStatement，获取二级缓存的key，如果查询到，返回，没有查询到，执行查询
        //      然后再去一级缓存中查询，查询到返回，没有查询到执行查询
        //      6、根据 configuration 获取 StatementHandler，准备 Statement，查询，设置结果集 ResultHandler
        //


        /**
         * 首先读取配置文件，然后加载映射文件，由SqlSessionFactory工厂对象去创建核心对象SqlSession，SqlSession对象会通过Executor执行器对象执行sql。
         * 然后Executor执行器对象会调用StatementHandler对象去真正的访问数据库执行sql语句。
         * 在执行sql语句前MapperStatement会先对映射信息进行封装，然后StatementHandler调用ParameterHandler去设置编译参数【#{}，${}】，编译在StatementHandler中进行。
         * 然后StatementHandler调用JBDC原生API进行处理，获取执行结果，这个执行结果交给ResultSetHandler 来进行结果集封装，然后将结果返回给StatementHandler。
         * 注意： 这里MapperStatement是对映射信息的封装，用于存储要映射的SQL语句的id、参数等信息。TypeHandler进行数据库类型和JavaBean类型映射处理。
         */

        /**
         * 1、执行器Executor，执行器负责整个SQL执行过程的总体控制。
         * 2、语句处理器StatementHandler，语句处理器负责和JDBC层具体交互，包括prepare语句，执行语句，以及调用ParameterHandler.parameterize ()设置参数。
         * 3、参数处理器ParameterHandler，参数处理器负责PreparedStatement入参的具体设置。
         * 4、结果集处理器ResultSetHandler，结果处理器负责将JDBC查询结果映射到java对象。
         */
    }

    @Test
    public void test262(){
        /**
         * @Transactional，作用是定义代理植入点。【aop实现原理分析】中，
         * 分析知道代理对象创建的通过BeanPostProcessor的实现类AnnotationAwareAspectJAutoProxyCreator的postProcessAfterInstantiation方法来实现个，
         * 如果需要进行代理，那么在这个方法就会返回一个代理对象给容器，同时判断植入点也是在这个方法中。
         */
    }

    @Test
    public void test263() {
        List<Person> list = Lists.newArrayList();
        Person person = new Person(1L, "name", "age");
        list.add(person);
        System.out.println(list);
        list.stream()
                .filter(item -> Objects.equals("name", item.getName()))
                .findFirst()
                .ifPresent(item -> item.setName("jack"));
        System.out.println(list);
    }

    @Test
    public void test264(){
        InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
        ThreadLocal<Integer> tl = new ThreadLocal<>();

        threadLocal.set(5);
        try{
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){

        }
        // 创建子线程的时候，会将 父线程的 inheritableThreadLocals变量复制到子线程的inheritableThreadLocals变量中
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            threadLocal.set(1);
            tl.set(5);
        });
        thread.start();

        try{
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){

        }
        System.out.println(Thread.currentThread().getName());
        Integer i = threadLocal.get();
        Integer i1 = tl.get();
        System.out.println(i);
        System.out.println(i1);
    }

    @Test
    public void test265(){
        InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

        threadLocal.set(1);

        new Thread(()->{
            Integer i = threadLocal.get();
            System.out.println(i);
        }).start();
    }

    @Test
    public void test266(){
        int i = 5;
        int j = 5;
        int result = i++;
        int result1 = ++j;
        System.out.println(result);
        System.out.println(result1);

        System.out.println("--------------");

        System.out.println(i);
        System.out.println(j);
    }

    @Test
    public void test267(){
        // 协变指的是子类型对象可以赋值给父类型引用的情况。在泛型中，协变表示如果 B 是 A 的子类
        // ，那么 List<B> 就是 List<A> 的子类。这意味着你可以将 List<B> 赋值给 List<A>，但只能读取 List<A> 中的元素，不能向其中添加任何元素。
        List<? extends Number> numbers = Arrays.asList(1);
//        numbers.add(Integer.valueOf(1));
        Number number = numbers.get(0);
        System.out.println(number);


        // 逆变指的是父类型对象可以赋值给子类型引用的情况。在泛型中，逆变表示如果 B 是 A 的子类
        // ，那么 Consumer<A> 就是 Consumer<B> 的子类。这意味着你可以将 Consumer<A> 赋值给 Consumer<B>，并且可以向其中添加 B 类型的元素，但不能读取其中的元素。
        Consumer<? super Integer> consumer = System.out::println;
        consumer.accept(1);

        List<? super Integer> list = new ArrayList<>();
        list.add(6);
        Object o = list.get(0);
        System.out.println(o);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Fruit{
        private String name;
        private String color;
    }

    static class Apple extends Fruit{

    }

    static class GoodApple extends Apple{

    }

    @Test
    public void test268(){
        // https://blog.csdn.net/m0_37796683/article/details/108584499
        Apple apple = new Apple();
        Fruit fruit = new Fruit();
        GoodApple goodApple = new GoodApple();

        // 协变   null - Fruit
        List<? extends Fruit> fruit1 = new ArrayList<Apple>();
//        fruit1.add(apple);  不知道是Fruit的那个一个子类，不允许添加
        Fruit fruit4 = fruit1.get(0);

        // 逆变   Apple - Object
        List<? super Apple> fruit2 = new ArrayList<Fruit>();
        fruit2.add(apple); // Apple的子类已经本身可以添加，
        fruit2.add(goodApple);
//        fruit2.add(fruit); // Fruit 是 Apple 的超类，不允许添加
        Object o = fruit2.get(0);


        // 不变
        List<Apple> fruit3 = new ArrayList<Apple>();
    }

    @Test
    public void test269(){
        /**
         * Spring MVC 执行流程
         * 根据url匹配（HandlerMethod）  获取处理器执行链（HandlerExecutionChain） 加入 HandlerInterceptor 拦截器
         * 执行拦截器的前置拦截功能，根据 HandlerMethod 选择 HandlerAdapter调用 handle方法，然后调用 invokeHandlerMethod 方法
         * 选择数据绑定工厂  选择Model工厂，根据 HandlerMethod创建 ServletInvocableHandlerMethod
         * 设置参数解析器、返回值解析器，创建 ModelAndViewContainer，调用 方法执行 ~
         * 根据返回值选择对应的返回值处理器，返回值ModelAndView ，执行拦截器的后置拦截功能
         */

        /**
         * protected ModelAndView invokeHandlerMethod(HttpServletRequest request,
         * 			HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
         *
         * 		ServletWebRequest webRequest = new ServletWebRequest(request, response);
         * 		try {
         * 			WebDataBinderFactory binderFactory = getDataBinderFactory(handlerMethod);
         * 			ModelFactory modelFactory = getModelFactory(handlerMethod, binderFactory);
         *
         * 			ServletInvocableHandlerMethod invocableMethod = createInvocableHandlerMethod(handlerMethod);
         * 			if (this.argumentResolvers != null) {
         * 				invocableMethod.setHandlerMethodArgumentResolvers(this.argumentResolvers);
         *                        }
         * 			if (this.returnValueHandlers != null) {
         * 				invocableMethod.setHandlerMethodReturnValueHandlers(this.returnValueHandlers);
         *            }
         * 			invocableMethod.setDataBinderFactory(binderFactory);
         * 			invocableMethod.setParameterNameDiscoverer(this.parameterNameDiscoverer);
         *
         * 			ModelAndViewContainer mavContainer = new ModelAndViewContainer();
         * 			mavContainer.addAllAttributes(RequestContextUtils.getInputFlashMap(request));
         * 			modelFactory.initModel(webRequest, mavContainer, invocableMethod);
         * 			mavContainer.setIgnoreDefaultModelOnRedirect(this.ignoreDefaultModelOnRedirect);
         *
         * 			AsyncWebRequest asyncWebRequest = WebAsyncUtils.createAsyncWebRequest(request, response);
         * 			asyncWebRequest.setTimeout(this.asyncRequestTimeout);
         *
         * 			WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
         * 			asyncManager.setTaskExecutor(this.taskExecutor);
         * 			asyncManager.setAsyncWebRequest(asyncWebRequest);
         * 			asyncManager.registerCallableInterceptors(this.callableInterceptors);
         * 			asyncManager.registerDeferredResultInterceptors(this.deferredResultInterceptors);
         *
         * 			if (asyncManager.hasConcurrentResult()) {
         * 				Object result = asyncManager.getConcurrentResult();
         * 				mavContainer = (ModelAndViewContainer) asyncManager.getConcurrentResultContext()[0];
         * 				asyncManager.clearConcurrentResult();
         * 				LogFormatUtils.traceDebug(logger, traceOn -> {
         * 					String formatted = LogFormatUtils.formatValue(result, !traceOn);
         * 					return "Resume with async result [" + formatted + "]";
         *                });
         * 				invocableMethod = invocableMethod.wrapConcurrentResult(result);
         *            }
         *
         * 			invocableMethod.invokeAndHandle(webRequest, mavContainer);
         * 			if (asyncManager.isConcurrentHandlingStarted()) {
         * 				return null;
         *            }
         *
         * 			return getModelAndView(mavContainer, modelFactory, webRequest);* 		}
         * 		finally {
         * 			webRequest.requestCompleted();
         * 		}
         * 	}
         */
    }

    @Test
    public void test270(){
        /**
         * sizeCtl
         *      为 0 代表数据没有初始化，数组的初始化容量为 16
         *      为 正数 如果数组未初始化，那么其记录的数组初始化容量，如果数据已经初始化，那么记录的是数组扩容的阈值
         *      为 -1 表示数组正在初始化
         *      小于 0 并且不是 -1 表示数组正在扩容，-（1+n）表示此时有n个线程正在共同完成数组的扩容操作
         */
        ConcurrentHashMap<String,String> hashMap = new ConcurrentHashMap<>();
        hashMap.put("1","1");
        String s = hashMap.get("1");
    }

    @Test
    public void test271(){
        /**
         * InnoDB的行锁
         * InnoDB行锁通过对索引数据页上的记录加锁实现的，主要实现的算法有 3 种 : Record Lock ，Gap Lock 和 Next-key Lock。
         *      Record Lock锁：锁定单个行记录的锁（记录锁，RC，RR隔离级别都支持）
         *      GapLock锁：间隙锁 锁定索引记录间隙，确保索引记录的间隙不变。（范围锁，RR隔离级别支持）
         *      Next-key Lock锁：记录锁和间隙锁组合，同时锁住数据，并且锁住数据前后范围。（记录锁 + 范围锁，RR隔离级别支持）
         *  在RR隔离级别，InnoDB对于记录加锁行为都是先采用Next-key Lock,但是当SQL操作含有唯一索引时，InnoDB会对Next-key Lock进行优化，降级为RecordLock，仅锁住索引本身而非范围
         *      select  ... from ... mvcc 实现非阻塞读，innoDB不加锁
         *      select ... from lock in share mode 追加共享锁，InnoDB会使用 Next-key Lock 锁进行处理，如果扫描发现唯一索引，可以降级为RecordLock锁
         *      select ... from for update ，追加排他锁，InnoDB会使用 Next-key Lock 锁进行处理，如果扫描发现唯一索引，可以降级为RecordLock锁
         *      update ... where ... ，InnoDB会使用 Next-key Lock 锁进行处理，如果扫描发现唯一索引，可以降级为RecordLock锁
         *      delete ... where ... ，InnoDB会使用 Next-key Lock 锁进行处理，如果扫描发现唯一索引，可以降级为RecordLock锁
         *      inset ... ，InnoDB会在将要插入的那一行设置一个排他的RecordLock锁
         */
    }

    @Test
    public void test272(){
        ConcurrentHashMap<String,String> hashMap = new ConcurrentHashMap<>();
        hashMap.put("1","1");
        hashMap.size();
    }

    @Test
    public void test273() {
        // CMS  垃圾回收器  并发 标记 清除
        /**
         * 1、初始标记：stw（stop the world） ：主要是标记 GC Root 开始的下级（注：仅下一级）对象，这个过程会 STW，但是跟 GC Root 直接关联的下级对象不会很多，因此这个过程其实很快。
         * 2、并发标记：gc线程和用户线程并发执行 ：根据上一步的结果，继续向下标识所有关联的对象，直到这条链上的最尽头。这个过程是多线程的，虽然耗时理论上会比较长，但是其它工作线程并不会阻塞，没有 STW。
         * 3、重新标记：stw（防止并发标记的时候用户线程产生垃圾，再次标记） ：顾名思义，就是要再标记一次。为啥还要再标记一次？因为第 2 步并没有阻塞其它工作线程，其它线程在标识过程中，很有可能会产生新的垃圾。
         * 4、并发清除
         */

        // G1 垃圾回收器
        /**
         * 1、初始标记：stw 仅使用一条初始标记线程对所有与 GC Roots 直接关联的对象进行标记。
         * 2、并发标记：使用一条标记线程与用户线程并发执行。此过程进行可达性分析，速度很慢。
         * 3、最终标记：stw，使用多条标记线程并发执行。
         * 4、筛选回收：回收废弃对象，此时也要 Stop The World，并使用多条筛选回收线程并发执行。（还会更新Region的统计数据，对各个Region的回收价值和成本进行排序）
         */
    }

    @Test
    public void test274(){
        // 1、实例化bean 2、属性注入 3、BeanNameAware#setBeanName
        // 4、BeanFactoryAware#setBeanFactory 5、BeanPostProcesser预处理方法
        // 6、InitBean#afterPropertiesSet 7、Init-Method方法
        // 7、BeanPostProcesser后处理方法 8、bean存入缓存，9、实现Depost#detory方法 10、destory-mehtod方法
    }

    @Test
    public void test275(){
        // Producer -- channel -->  Broker ( Exchange - Qyeye )--- channel--> Consumer
        /**
         * Producer：消息生产者。负责产生和发送消息到 Broker
         * Broker：消息处理中心。负责消息存储、确认、重试等，一般其中会包含多个 queue
         * Consumer：消息消费者。负责从 Broker 中获取消息，并进行相应处理
         */
    }

    @Test
    public void test276(){
        /**
         * CAP理论：
         * Consistency（一致性）
         * Availability（可用性）
         * Partition tolerance（分区容忍性）
         * 这三个性质对应了分布式系统的三个指标：
         * 而CAP理论说的就是：一个分布式系统，不可能同时做到这三点
         */
    }

    @Test
    public void test277(){
        // ACID
        /**
         * A:原子性
         * C：一致性
         * I：隔离性
         * D：持久性
         */
    }

    @Test
    public void test278() {
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
//            return "111";
//        }).thenApply(item->{
//            System.out.println(item);
//            return item;
//        });
//        try {
//            String s = future.get();
//        }catch (Exception e){
//
//        }

        CompletableFuture<String> handle = CompletableFuture.supplyAsync(() -> {
            System.out.println("1111");
            return "1111";
        }).handle((x, e) -> {
            System.out.println("handle" + x);
            return x;
        });

        String join = handle.join();
        System.out.println("join" + join);
    }

    @Test
    public void test279() {
        System.out.println(LocalDateTime.now().minusDays(30));
    }

    @Test
    public void test280(){
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1000);
        System.out.println(integers.contains(1000L));
    }

    @Test
    public void test281() {
        List<Person> personList = Lists.newArrayList();
        personList.add(new Person(1L, "小李", "3"));
        personList.add(new Person(2L, "小王", "3"));
        personList.add(new Person(3L, "小孙", "3"));
    }

    @Test
    public void test282() {
        int daysInMonth = LocalDate.now().lengthOfMonth();
        System.out.println(daysInMonth);
    }
}
