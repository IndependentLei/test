package com.shebao.test.test.factory;

import cn.hutool.core.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LoginFactory {

    private static Map<String,Login> loginMap = new HashMap<>();

    @Autowired
    private void initMap(List<Login> loginList){
        loginMap = loginList.stream().collect(Collectors.toMap(login->AnnotationUtils.findAnnotation(login.getClass(), Component.class).value(),v->v,(x,y)->x));
        System.out.println(loginMap);
    }

    public Login getLoginType(String type){
        Login login = loginMap.get(type);
        Assert.notNull(login);
        return login;
    }


}
