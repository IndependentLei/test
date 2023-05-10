package com.shebao.test.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilePo {
    /**
     * 文件
     */
    private MultipartFile file;
    /**
     * 标记
     */
    private String biz;

    private Long index;
}
