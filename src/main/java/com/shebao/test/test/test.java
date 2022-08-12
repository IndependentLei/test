package com.shebao.test.test;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.RandomUtil;
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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
}
