package com.shebao.test.service;

import com.shebao.test.model.po.FilePo;
import com.shebao.test.model.po.MergePo;

public interface BigFileDealWithService {

    void dealWith(FilePo filePo);

    void merge(MergePo mergePo);
}
