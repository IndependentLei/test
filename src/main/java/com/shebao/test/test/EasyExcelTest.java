package com.shebao.test.test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Slf4j
public class EasyExcelTest {
    // C:\Users\EDY\Desktop\test.xlsx
    public static void main(String[] args) {
//        simpleWriteExcel();
        complexFillExcel();
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
}
