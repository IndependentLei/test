package com.shebao.test.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
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
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.shebao.test.config.TaskThreadPool;
import com.shebao.test.model.entity.Person;
import com.shebao.test.model.entity.Person1;
import com.shebao.test.model.entity.TestPerson;
import com.shebao.test.model.enums.TypeEnum;
import com.shebao.test.test.mapStruct.PersonMapStruct;
import io.netty.util.concurrent.CompleteFuture;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StopWatch;

import javax.persistence.criteria.CriteriaBuilder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
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
    public void test165(){
        System.out.println(data().stream()
                .map(DemoData::getDoubleData)
                .reduce(Double::sum).orElse(null));
        System.out.println(data().stream()
                .map(DemoData::getDoubleData)
                .reduce(1D,Double::sum));
        System.out.println(data().stream()
                .map(DemoData::getDoubleData)
                .reduce(1D,Double::sum, Double::sum));
    }

    @Test
    public void test175(){
        Double dd = 10.01;
        Double dd1 = 0D;
        System.out.println( dd / dd1 );

        Integer integer = 10;
        Integer ii = 0;
        System.out.println( integer / ii );
    }

    @Test
    public void test176(){
        List<DemoData> collect = data().stream().peek(item -> item.setString("1111")).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test177(){
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
        System.out.println((eTime-sTime)/1000);

        System.out.println(bigDecimal);
    }



    public long getRandom(){
        return RandomUtil.randomLong(1,5);
    }

    public BigDecimal getPrice(){
        return RandomUtil.randomBigDecimal(BigDecimal.valueOf(50),BigDecimal.valueOf(5000));
    }

    public BigDecimal getA(String item){
        try {
            long random = getRandom();
            BigDecimal price = getPrice();
            log.info("{}-{}-{}",item,random,price);
            TimeUnit.SECONDS.sleep(random);
            return price;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getB(String item){
        try {
            long random = getRandom();
            BigDecimal price = getPrice();
            log.info("{}-{}-{}",item,random,price);
            TimeUnit.SECONDS.sleep(random);
            return price;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getC(String item){
        try {
            long random = getRandom();
            BigDecimal price = getPrice();
            log.info("{}-{}-{}",item,random,price);
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
    public class FData{
        private Boolean isSucc;
        private String fStr;
    }
    private List<FData> fDataTest(){
        List<FData> fDataList = Lists.newArrayList();
        fDataList.add(new FData(true,"1"));
        fDataList.add(new FData(false,"1"));
        return fDataList;
    }
    @Test
    public void test180(){
        boolean b = fDataTest().stream()
                .allMatch(FData::getIsSucc);
        System.out.println(b);
    }

    @Test
    public void test181(){
        Double d = 1.0;
        Double a = Math.ceil(d/1000);
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
}
