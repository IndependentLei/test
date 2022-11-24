package com.shebao.test.test.factory.template;

import com.shebao.test.test.factory.Login;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Component(value = "wLogin")
public class WLogin implements Login {
    @Override
    public boolean support(String type) {
        return AnnotationUtils.findAnnotation(this.getClass(),Component.class).value().equals(type);
    }

    @Override
    public String execute(String username, String password) {
        return AnnotationUtils.findAnnotation(this.getClass(),Component.class).value();
    }
}
