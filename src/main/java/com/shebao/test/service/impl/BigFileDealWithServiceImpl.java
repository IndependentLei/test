package com.shebao.test.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.UUID;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.shebao.test.model.dto.FileIndexDto;
import com.shebao.test.model.po.FilePo;
import com.shebao.test.model.po.MergePo;
import com.shebao.test.service.BigFileDealWithService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.*;

@Service
@Slf4j
public class BigFileDealWithServiceImpl implements BigFileDealWithService {

    // 同时只能有十个线程在工作,其他阻塞
    private static final Semaphore semaphore = new Semaphore(50);

    private static final String PATH = "D:\\test\\%s";

    private static final String SAVE_PATH = "D:\\test\\merge\\%s.%s";

    private static final long size = 50;

    private static final ConcurrentMap<String,ArrayDeque<FileIndexDto>> map = new ConcurrentHashMap<>();

    /**
     * 分片处理
     */
    @Override
    public void dealWith(FilePo filePo) {
        boolean flag = false;
        String uuid = null;
        try {
            semaphore.acquire();
            String outPath = String.format(PATH,filePo.getBiz());
            File outFile = new File(outPath);
            if(!outFile.exists()){
                outFile.mkdir();
            }
            uuid = UUID.fastUUID().toString();
            String curFilePath = String.format("%s%s%s",outPath,File.separator,uuid);
            filePo.getFile().transferTo(new File(curFilePath));
            flag = true;
        }catch (Exception e){
            log.error("dealWith error {} {}",e,e.getMessage());
        }finally {
            semaphore.release();
            if(flag){
                String finalUuid = uuid;
                map.computeIfAbsent(uuid, k->{
                    FileIndexDto fileIndexDto = FileIndexDto.builder()
                            .uuid(finalUuid)
                            .index(filePo.getIndex()*size)
                            .build();
                    ArrayDeque<FileIndexDto> deque = new ArrayDeque<>();
                    deque.add(fileIndexDto);
                    return deque;
                });
            }
        }
    }

    /**
     * 合并
     */
    @Override
    public void merge(MergePo mergePo) {
        RandomAccessFile out = null;
        try {
            semaphore.acquire();
            String uuid = UUID.fastUUID().toString();
            String saveUrl = String.format(SAVE_PATH, uuid, mergePo.getType());
            out = new RandomAccessFile(saveUrl,"rw");
            ArrayDeque<FileIndexDto> deque = map.get(mergePo.getBiz());
            if(CollectionUtils.isEmpty(deque)){
                throw new RuntimeException("需要合并的文件为空");
            }
            RandomAccessFile finalOut = out;
            // 判断是否合并成功
            boolean b = deque.stream().map(
                    item -> CompletableFuture.supplyAsync(() -> {
                        try {
                            finalOut.seek(item.getIndex());
                            String fPUrl = String.format(PATH,item.getUuid());
                            byte[] byteArray = IOUtils.toByteArray(Files.newInputStream(Paths.get(fPUrl)));
                            finalOut.write(byteArray);
                        } catch (Exception e) {
                            log.error("merge error {} {}", e, e.getMessage());
                        }
                        return true;
                    })
            ).map(item->{
                try {
                    return item.get();
                } catch (Exception e) {
                    log.error("merge error {} {}",e,e.getMessage());
                }
                return false;
            }).allMatch(BooleanUtils::isTrue);
        }catch (Exception e){
            log.error("merge error {} {}",e,e.getMessage());
        }finally {
            semaphore.release();
            if( null != out ){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("merge error {} {}",e,e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        String outPath = String.format(PATH,"11111");
        File outFile = new File(outPath);
        outFile.mkdir();
        System.out.println(outFile.exists());
    }
}
