package com.shebao.test.controller.dataDesensitization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataDesensitization {
    @Desensitization(type = DesensitizationTypeEnum.CHINESE_NAME)
    private String name;
    @Desensitization(type = DesensitizationTypeEnum.ID_CARD,startInclude = 6,endExclude = 4)
    private String idNo;
    @Desensitization(type = DesensitizationTypeEnum.PASSWORD)
    private String password;
    @Desensitization(type = DesensitizationTypeEnum.MOBILE_PHONE)
    private String mobilePhone;
    @Desensitization(type = DesensitizationTypeEnum.BANK_CARD)
    private String bankCard;
}
