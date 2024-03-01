package com.shebao.test.test;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

@Slf4j
public class EasyExcelTest {
    // C:\Users\EDY\Desktop\test.xlsx
    public static void main(String[] args) {
//        simpleWriteExcel();
//        complexFillExcel();
//        TJTBQingFeng tjtbQingFeng = new EasyExcelTest.TJTBQingFeng();
//        tjtbQingFeng.setGrzh("1111");
//        tjtbQingFeng.setDwjcbl("5");
//        tjtbQingFeng.setGrjcbl("5");
//        tjtbQingFeng.setXm("5555");
//        tjtbQingFeng.setZjhm("320804199810261910");
//        tjtbQingFeng.setJcjs("5000");
//        EasyExcelTest.fillData(Collections.singletonList(tjtbQingFeng));

        FillData fillData = new FillData();
//        fillData.setBlxz("111111");
//        fillData.setBz("11111");
//        fillData.setName("111111");
//        fillData.setXh("11111");
//        fillData.setHjdz("111111");
//        fillData.setLzyy("111111111");
        EasyExcelTest.fill2Data(Collections.singletonList(fillData));
    }

    /**
     * 复杂写入
     */
    public static void complexFillExcel(){
        List<FillPerson> fillPeopleList = Arrays.asList(new FillPerson("嵇道磊", "24", "男", "java")
                , new FillPerson("嵇道磊", "25", "男", "java")
                , new FillPerson("嵇道磊", "26", "男", "java")
                , new FillPerson("嵇道磊", "27", "男", "java"));
        ExcelWriter excelWriter = null;
        try {
            ClassPathResource resource = new ClassPathResource("templates/test2.xlsx");
//            File tempFile = File.createTempFile("C:\\Users\\EDY\\Desktop\\EasyExcel", ".xlsx");
            File tempFile = new File("C:\\Users\\EDY\\Desktop\\EasyExcel.xlsx");
            excelWriter = EasyExcel.write(tempFile).withTemplate(resource.getStream()).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();  // 自动换行
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(fillPeopleList,fillConfig,writeSheet);

            // 填充
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("cese","随便测一测");
            paramMap.put("cese2","再次随便测一测");
            excelWriter.fill(paramMap,writeSheet);

            FileUtil.writeFromStream(new FileInputStream(tempFile),tempFile);
        }catch (Exception e){
            log.warn("====>{}",e.getMessage());
        }finally {
            if(excelWriter != null){
                excelWriter.finish();
            }
        }


    }

    @Data
    static class  TJTBQingFeng{
        private String grzh;
        private String xm;
        private String zjhm;
        private String jcjs;
        private String dwjcbl;
        private String grjcbl;
    }

    // 填充数据
    private static void fillData(List<TJTBQingFeng> fillList){
        log.info("fillExecl start {}", JSON.toJSONString(fillList));
        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("templates/调基调比启封【2022-08-26】.xlsx");
        File tempFile = new File("C:\\Users\\EDY\\Desktop\\EasyExcel-"+System.currentTimeMillis()+".xlsx");
        ExcelWriter excelWriter = EasyExcel.write(tempFile).withTemplate(systemResourceAsStream).build();
        FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();  // 自动换行
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        excelWriter.fill(fillList,fillConfig,writeSheet);
        try {
            FileUtil.writeFromStream(new FileInputStream(tempFile),tempFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        excelWriter.finish();
    }

    private static void fill2Data(List<FillData> fillList){
        try {
            org.springframework.core.io.ClassPathResource classPathResource = new org.springframework.core.io.ClassPathResource("templates/南昌社保增减.xlsx");
            File tempFile = new File(System.getProperty("user.home")+File.separator+String.format("南昌社保增减【%s】.xlsx", DateUtil.format(new Date(), DatePattern.CHINESE_DATE_PATTERN)));
            ExcelWriter excelWriter = EasyExcel.write(tempFile).withTemplate(classPathResource.getInputStream()).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();  // 自动换行
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(fillList,fillConfig,writeSheet);

            // 标题
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("dabiaoti","111111111111111111111111");
            paramMap.put("xiaobiaoti","111111111111111111111111");
            excelWriter.fill(paramMap,writeSheet);
            try {
                FileUtil.writeFromStream(new FileInputStream(tempFile),tempFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            excelWriter.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 简单写入
     */
    public static void simpleWriteExcel(){
        List<Question> questions = Arrays.asList(new Question("1", "1", "1")
                , new Question("1", "1", "1")
                , new Question("1", "1", "1"));
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write("C:\\Users\\EDY\\Desktop\\test1.xlsx", Question.class).build();
            WriteSheet sheet = EasyExcel.writerSheet("自定义工作表").build();
            excelWriter.write(questions,sheet);
        }catch (Exception e){
            log.info("捕获异常：{}",e.getMessage());
        }finally {
            if(excelWriter != null) {
                excelWriter.finish();
            }
        }

    }
    /**
     * 简单读excel
     */
    public static void simpleReadExcel(){
        List<Question> questionList = new ArrayList<>();
        EasyExcel.read("C:\\Users\\EDY\\Desktop\\test.xlsx",Question.class,new AnalysisEventListener<Question>(){

            @Override
            public void invoke(Question question, AnalysisContext analysisContext) {
                questionList.add(question);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).doReadAll();

        questionList.forEach(question -> {
            log.info("question ---> {}",question.toString());
        });
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class FillData {
        /**
         * 序号
         */
        private String xh;
        /**
         * 办理险种
         */
        private String blxz;
        /**
         * 姓名
         */
        private String name;
        /**
         * 身份号码
         */
        private String idNo;
        /**
         * 开始参保时间
         */
        private String startMonth;
        /**
         * 缴费工资
         */
        private String yangLaoBase;
        /**
         * 户籍地址
         */
        private String hjdz;
        /**
         * 离职原因
         */
        private String lzyy;
        /**
         * 备注
         */
        private String bz;
    }
}

