package com.shebao.test.test;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.shebao.test.config.TaskThreadPool;
import com.shebao.test.model.entity.Person;
import com.shebao.test.model.entity.Person1;
import com.shebao.test.model.entity.TestPerson;
import com.shebao.test.model.enums.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.time.DateUtils;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class test {
//    public static void main(String[] args) {
//        Person person = Person.builder()
//                        .name("jdl")
//                        .age("2")
//                        .build();
//        System.out.println(person.toString());
//    }

    @Test
    public void test2(){
        List<String> strings = Collections.singletonList("222");
        System.out.println(strings);
    }

    @Test
    public void test3(){
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
    public void test5(){
        List<Person> personList = Arrays.asList(new Person(1L,"小王", "2")
                , new Person(2L,"小李", "2")
                , new Person(3L,"小王", "2")
                , new Person(4L,"小春", "2"));
        Map<String, List<Person>> collect = personList.stream().collect(Collectors.groupingBy(Person::getName));
        System.out.println(collect);
        System.out.println(collect.get("小王"));
    }

    @Test
    public void test6(){
        String str = "1,2,3,4";
        List<String> strList = Splitter.on(",").splitToList(str);
        System.out.println(strList);
    }

    public static void main(String[] args) {
        String str = "1,2,3,4";
        List<String> strList = Splitter.on(",").splitToList(str);
        System.out.println(strList);
    }

    @Test
    public void test7(){
        List<Person> personList = Arrays.asList(new Person(1L,"小王", "2")
                , new Person(2L,"小李", "2")
                , new Person(3L,"小王", "2")
                , new Person(4L,"小春", "2"));
        Map<String, String> collect = personList.stream().collect(Collectors.toMap(Person::getName, Person::getAge, (x, y) -> y));
        System.out.println(collect);

    }

    @Test
    public void test8(){
        List<Integer> typeList = Arrays.asList(1, 1, 2, 1);
        List<String> collect = typeList.stream().map(type -> TypeEnum.fromCode(type).getName()).collect(Collectors.toList());
        String join = Joiner.on(",").join(collect);
        System.out.println(join);
    }

    @Test
    public void test9(){
        List<Long> ids = Lists.newArrayList();
        System.out.println(ids);
        if(CollectionUtils.isNotEmpty(ids)){
            System.out.println(1);
        }

        HashMap<String, String> A = Maps.newHashMap();
        HashMap<String, String> B = Maps.newHashMap();

        Map<String, Object> collect = A.entrySet()//获取集合
                .stream()//获取流
                .filter(a->B.keySet().contains(a))//peek支持在每个元素上执行一个操作并且返回新的stream
                // ，我们就利用这个方法转换数据
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));//collect方法用来将流转到集合对象
    }

    @Test
    public void test10(){
        Map<String,String> one = Maps.newHashMap();
        one.put("1","2");
        one.put("2","3");
        one.put("3","4");
        Map<String,String> two = Maps.newHashMap();
        two.put("3","4");
        two.put("4","5");
        two.put("6","7");

        Map<String, String> collectMap = one.entrySet().stream().filter(o -> two.containsKey(o.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println(collectMap);

    }


    @Test
    public void test11(){
        List<Person> personList = Arrays.asList(new Person(1L,"小王", "2")
                , new Person(2L,"小李", "2")
                , new Person(3L,"小王", "2")
                , new Person(4L,"小春", "2"));

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

        BeanUtils.copyProperties(testPerson,testPerson1);
        System.out.println(testPerson1);
    }

    @Test
    public void test12(){
        long dayToSecond = TimeUnit.DAYS.toSeconds(1);
        System.out.println(dayToSecond);
        long ss = 24 * 60 * 60;
        System.out.println(ss);
    }

    @Test
    public void test13(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 1、普通的时间转换
        String string = new SimpleDateFormat("yyyyMM").format(new Date());
        System.out.println(string);
    }

    @Test
    public void test14(){
        BigDecimal bigDecimal = new BigDecimal(5000);
        BigDecimal multiply = bigDecimal.multiply(new BigDecimal("0.12"));
        System.out.println(multiply);
    }

    @Test
    public void test15(){
        BigDecimal bigDecimal = new BigDecimal(5000);
        System.out.println(bigDecimal.multiply(BigDecimal.valueOf(0.12)));
    }

    @Test
    public void test16(){
        List<Callable<String>> response = Lists.newArrayList();
        List<Person> personList = Arrays.asList(new Person(1L,"小王", "2")
                , new Person(2L,"小李", "2")
                , new Person(3L,"小王", "4")
                , new Person(4L,"小春", "2")
                , new Person(4L,"小春", "4")
                , new Person(4L,"小春", "2")
                , new Person(4L,"小春", "7")
                , new Person(4L,"小春", "9")
                , new Person(4L,"小春", "2")
                , new Person(4L,"小春", "2"));
        Map<String, List<Person>> collect = personList.stream().collect(Collectors.groupingBy(Person::getAge));
        // 使用Callable去执行多线程
        collect.forEach((age,persons) -> response.add(()->testCallable(persons)));
        List<String> strings = TaskThreadPool.addTask(response);
        System.out.println(strings);
    }
    private String testCallable(List<Person> personList){
        StringBuilder allAge = new StringBuilder();
        for(Person person : personList){
            allAge.append(person.getAge());
        }
        return allAge.toString();
    }

    @Test
    public void test17(){
        try {
            File file = new File("E:\\123.pdf");
            FileInputStream fis = new FileInputStream(file);
            File file1 = new File("E:\\2321.pdf");
            FileOutputStream fos = new FileOutputStream(file1);
            byte[] collect = new byte[1024];
            int len;
            while ((len = fis.read(collect))!= -1){
                fos.write(collect,0,len);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test18() {
        System.out.println(getMonthSpace("202204","202205"));
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

            int i = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
            int month = 0;
            if (i<0)
            {
                month = -i*12;
            }else if(i>0)
            {
                month =  i*12;
            }
            result = (c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH)) + month;

            return Math.abs(result)+1;
        }catch (ParseException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Test
    public void test20(){
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
        Map<String,List<String >> menuMap = new HashMap<>();
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
        menuMap.put("肉",肉);
        menuMap.put("菜",菜);
        menuMap.put("汤",汤);
        menuMap.put("不辣",不辣);
        menuMap.put("辣",辣);


    }

    @Test
    public void test(){
        //
    }

    @Test
    public void test21(){
        class Cat {
            public Cat(String name) {
                this.name = name;
            }
            private String name;
            public String getName() {
                return name;
            }
            public void setName(String name) {
                this.name = name;    }
        }
        Cat c1 = new Cat("王磊");Cat c2 = new Cat("王磊");System.out.println(c1.equals(c2));
    }

    @Test
    public void test22(){
        String userHome = System.getProperty("user.home");
        System.out.println(userHome);
    }

    @Test
    public void test23(){

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject3 = new JSONObject();
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);

        jsonObject.put("id",1);
        jsonObject1.put("id",11);
        jsonObject2.put("id",10);
        jsonObject3.put("id",5);
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
    class  Clone1{
        public Integer id;
    }

    @Data
    class CloneTest implements Cloneable{
        public Integer id;
        public Clone1 clone1;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
    @Test
    public void test24() throws CloneNotSupportedException {
        CloneTest cloneTest = new CloneTest();
        cloneTest.clone1 = new Clone1(1);
        cloneTest.setId(1);
        CloneTest clone = (CloneTest)cloneTest.clone();

        clone.setClone1(new Clone1(2));

        System.out.println(cloneTest.getClone1() == clone.getClone1());
        System.out.println(clone);
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
        pressMouse(robot,InputEvent.BUTTON1_MASK,500);
    }

    private static void pressMouse(Robot r,int m,int delay){
        r.mousePress(m);
        r.delay(10);
        r.mouseRelease(m);
        r.delay(delay);
    }

    @Test
    public void test26(){
        Map<String,String> map = Maps.newHashMap();
        map.put("","");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry);
        }

        String str = "{\"method\":\"post\",\"sourceUrlCode\":\"ythzf\",\"interfaceName\":\"zhlsxQueryCompanyOperator\",\"parameter\":\"{\\\"tyshxydm\\\":\\\"91360125MA39U3Y5XR\\\",\\\"jbrlb\\\":\\\"2\\\",\\\"withSx\\\":\\\"false\\\",\\\"status\\\":\\\"1\\\"}\"}";
    }

    @Test
    public void test27(){
            String str = "{\"interfaceName\":\"sbDoYwsl\",\"parameter\":\"{\\\"channelCode\\\":\\\"2002\\\",\\\"interfaceCode\\\":\\\"TYSL0106\\\",\\\"tzBusiHeader\\\":{\\\"aaa027\\\":\\\"360106\\\",\\\"aaz010\\\":\\\"3000000001237470\\\",\\\"aaa028\\\":\\\"2\\\",\\\"bae813\\\":\\\"360106\\\",\\\"bae814\\\":\\\"南昌市红谷滩新区\\\",\\\"userid\\\":\\\"91360125MA39U3Y5XR\\\",\\\"username\\\":\\\"江西人力云企业服务有限公司\\\",\\\"projid\\\":\\\"29746ba68d984e0eafb9981355021f62\\\"},\\\"data\\\":{\\\"searchText\\\":\\\"362201199601140618\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aab998\\\":\\\"91360125MA39U3Y5XR\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aac999\\\":\\\"500001049402\\\",\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac058\\\":\\\"01\\\",\\\"aac050\\\":\\\"22\\\",\\\"DIC_QYZS_AAE160\\\":\\\"2102080101\\\",\\\"aae035\\\":\\\"2022-10-16T16:00:00.000Z\\\",\\\"aaa121\\\":\\\"F210208\\\",\\\"ac05List\\\":[{\\\"aaz159\\\":\\\"3620000006928774\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"410\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1413601000000200\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0,\\\"aaa042\\\":0.002,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":26,\\\"_uid\\\":26},{\\\"aaz159\\\":\\\"3620000006928775\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"210\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1210360100000100\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0.005,\\\"aaa042\\\":0.005,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":27,\\\"_uid\\\":27},{\\\"aaz159\\\":\\\"3620000006928776\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"110\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1110360100000100\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0.08,\\\"aaa042\\\":0.16,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":28,\\\"_uid\\\":28}],\\\"aae160\\\":\\\"2102080101\\\"}}\",\"projid\":\"29746ba68d984e0eafb9981355021f62\"}";
            String str2 = "{\"interfaceName\":\"qgtcAddBjxxApasInfo\",\"parameter\":\"{\\\"projid\\\":\\\"29746ba68d984e0eafb9981355021f62\\\",\\\"dataBody\\\":{\\\"formData\\\":{\\\"form\\\":{\\\"searchText\\\":\\\"362201199601140618\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aab998\\\":\\\"91360125MA39U3Y5XR\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aac999\\\":\\\"500001049402\\\",\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac058\\\":\\\"01\\\",\\\"aac050\\\":\\\"22\\\",\\\"DIC_QYZS_AAE160\\\":\\\"2102080101\\\",\\\"aae035\\\":\\\"2022-10-17\\\"}},\\\"gridData\\\":{\\\"datagrid266\\\":[{\\\"searchText\\\":null,\\\"aab001\\\":null,\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aac999\\\":\\\"500001049402\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac003\\\":\\\"冯青云\\\",\\\"aac004\\\":\\\"1\\\",\\\"aac005\\\":\\\"01\\\",\\\"aac006\\\":\\\"19960114\\\",\\\"aac007\\\":\\\"20220801\\\",\\\"aac058\\\":\\\"01\\\",\\\"bae535\\\":null,\\\"bac238\\\":null,\\\"aac009\\\":null,\\\"bab001\\\":null,\\\"aac010\\\":null,\\\"aae006\\\":null,\\\"aac012\\\":null,\\\"aae005\\\":null,\\\"bac243\\\":null,\\\"aae159\\\":null,\\\"aae007\\\":null,\\\"bae226\\\":null,\\\"aac161\\\":\\\"CHN\\\",\\\"aac225\\\":null,\\\"aac226\\\":null,\\\"aac227\\\":null,\\\"aac228\\\":null,\\\"aac229\\\":null,\\\"aac230\\\":null,\\\"aac231\\\":null,\\\"aac232\\\":null,\\\"aac233\\\":null,\\\"aac234\\\":null,\\\"aac235\\\":null,\\\"aac084\\\":\\\"0\\\",\\\"aac081\\\":null,\\\"aac060\\\":\\\"1\\\",\\\"aac031\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aac222\\\":null,\\\"_id\\\":25,\\\"_uid\\\":25}],\\\"datagrid\\\":[{\\\"aaz159\\\":\\\"3620000006928774\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"410\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1413601000000200\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0,\\\"aaa042\\\":0.002,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":26,\\\"_uid\\\":26},{\\\"aaz159\\\":\\\"3620000006928775\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"210\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1210360100000100\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0.005,\\\"aaa042\\\":0.005,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":27,\\\"_uid\\\":27},{\\\"aaz159\\\":\\\"3620000006928776\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"110\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1110360100000100\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0.08,\\\"aaa042\\\":0.16,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":28,\\\"_uid\\\":28}],\\\"companyGrid\\\":[],\\\"companyCbGrid\\\":[]},\\\"gridSelData\\\":{\\\"datagrid266\\\":[{\\\"searchText\\\":null,\\\"aab001\\\":null,\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aac999\\\":\\\"500001049402\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac003\\\":\\\"冯青云\\\",\\\"aac004\\\":\\\"1\\\",\\\"aac005\\\":\\\"01\\\",\\\"aac006\\\":\\\"19960114\\\",\\\"aac007\\\":\\\"20220801\\\",\\\"aac058\\\":\\\"01\\\",\\\"bae535\\\":null,\\\"bac238\\\":null,\\\"aac009\\\":null,\\\"bab001\\\":null,\\\"aac010\\\":null,\\\"aae006\\\":null,\\\"aac012\\\":null,\\\"aae005\\\":null,\\\"bac243\\\":null,\\\"aae159\\\":null,\\\"aae007\\\":null,\\\"bae226\\\":null,\\\"aac161\\\":\\\"CHN\\\",\\\"aac225\\\":null,\\\"aac226\\\":null,\\\"aac227\\\":null,\\\"aac228\\\":null,\\\"aac229\\\":null,\\\"aac230\\\":null,\\\"aac231\\\":null,\\\"aac232\\\":null,\\\"aac233\\\":null,\\\"aac234\\\":null,\\\"aac235\\\":null,\\\"aac084\\\":\\\"0\\\",\\\"aac081\\\":null,\\\"aac060\\\":\\\"1\\\",\\\"aac031\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aac222\\\":null,\\\"_id\\\":25,\\\"_uid\\\":25}],\\\"datagrid\\\":[{\\\"aaz159\\\":\\\"3620000006928774\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"410\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1413601000000200\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0,\\\"aaa042\\\":0.002,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":26,\\\"_uid\\\":26},{\\\"aaz159\\\":\\\"3620000006928775\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"210\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1210360100000100\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0.005,\\\"aaa042\\\":0.005,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":27,\\\"_uid\\\":27},{\\\"aaz159\\\":\\\"3620000006928776\\\",\\\"aac001\\\":\\\"3000000001237470\\\",\\\"aab001\\\":\\\"1000000005323692\\\",\\\"aae140\\\":\\\"110\\\",\\\"aac008\\\":\\\"1\\\",\\\"aac049\\\":202208,\\\"aac030\\\":\\\"20220901\\\",\\\"aae868\\\":null,\\\"aae206\\\":202209,\\\"aab033\\\":null,\\\"aac013\\\":null,\\\"aac031\\\":\\\"1\\\",\\\"aac066\\\":\\\"101\\\",\\\"aac313\\\":\\\"1101\\\",\\\"aac314\\\":\\\"企业一般人员\\\",\\\"aae041\\\":202209,\\\"aae042\\\":null,\\\"aae030\\\":null,\\\"aae031\\\":null,\\\"aae100\\\":\\\"1\\\",\\\"aac335\\\":null,\\\"aac336\\\":null,\\\"aac337\\\":null,\\\"aae180\\\":3528,\\\"aac040\\\":null,\\\"aaz003\\\":null,\\\"aaz113\\\":null,\\\"aaz165\\\":null,\\\"aaz289\\\":\\\"1110360100000100\\\",\\\"aaz434\\\":null,\\\"aaz136\\\":null,\\\"aae823\\\":null,\\\"aae013\\\":null,\\\"aaz649\\\":null,\\\"aae860\\\":null,\\\"aae859\\\":null,\\\"aae011\\\":null,\\\"aae036\\\":null,\\\"aaz262\\\":null,\\\"aab034\\\":null,\\\"aab360\\\":null,\\\"aab359\\\":null,\\\"aaf018\\\":null,\\\"aaa431\\\":null,\\\"aaz673\\\":null,\\\"aaa027\\\":\\\"360106\\\",\\\"aaa508\\\":null,\\\"bac245\\\":null,\\\"includeHistory\\\":null,\\\"aae140Like\\\":null,\\\"aae017\\\":null,\\\"ym\\\":null,\\\"ajc050_start\\\":null,\\\"ajc050_end\\\":null,\\\"aac030Begin\\\":null,\\\"aac030End\\\":null,\\\"aac999\\\":\\\"500001049402\\\",\\\"aab004\\\":\\\"江西人力云企业服务有限公司\\\",\\\"aab999\\\":\\\"100000400005\\\",\\\"aaa041\\\":0.08,\\\"aaa042\\\":0.16,\\\"aac031s\\\":null,\\\"aae140s\\\":null,\\\"aaz692\\\":null,\\\"aaa350\\\":null,\\\"aae870\\\":null,\\\"aae878\\\":null,\\\"aaa121\\\":null,\\\"aac008s\\\":null,\\\"aac003\\\":\\\"冯青云\\\",\\\"aac002\\\":\\\"362201199601140618\\\",\\\"aac006\\\":null,\\\"aae140Sub\\\":null,\\\"baa102\\\":null,\\\"bab138\\\":null,\\\"aab019\\\":\\\"100\\\",\\\"_id\\\":28,\\\"_uid\\\":28}],\\\"companyGrid\\\":[],\\\"companyCbGrid\\\":[]},\\\"tzBusiHeader\\\":{\\\"aaa027\\\":\\\"360106\\\",\\\"aaz010\\\":\\\"3000000001237470\\\",\\\"aaa028\\\":\\\"2\\\",\\\"bae813\\\":\\\"360106\\\",\\\"bae814\\\":\\\"南昌市红谷滩新区\\\",\\\"userid\\\":\\\"91360125MA39U3Y5XR\\\",\\\"username\\\":\\\"江西人力云企业服务有限公司\\\",\\\"projid\\\":\\\"29746ba68d984e0eafb9981355021f62\\\"},\\\"channelCode\\\":\\\"2002\\\"},\\\"dataType\\\":\\\"JSON\\\"}\",\"projid\":\"29746ba68d984e0eafb9981355021f62\"}";
            JSONObject jsonObject = JSON.parseObject(str);
            JSONObject jsonObject2 = JSON.parseObject(str2);
            System.out.println(jsonObject);
            System.out.println(jsonObject2);
            System.out.println(new Date());
    }

    @Test
    public void tset28(){
//        System.out.printf("%s月无费用%n", DateUtil.offsetMonth(new Date(),2).month());
        System.out.println(DateUtil.offsetMonth(new Date(),1).month());
    }
}
