package com.shebao.test.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeEnum {
    USER(1,"用户"),
    ADMIN(2,"管理员"),
    NULL(0,"");

    private  Integer code;
    private  String name;

    TypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    private static final Map<Integer, TypeEnum> codeMap = new HashMap<>((int) (values().length / .75f) + 1);

    static {
        for(TypeEnum item : values()) {
            codeMap.put(item.code, item);
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TypeEnum fromCode(Integer code) {
        TypeEnum item = codeMap.get(code);
        return item == null ? NULL : item;
    }

}
