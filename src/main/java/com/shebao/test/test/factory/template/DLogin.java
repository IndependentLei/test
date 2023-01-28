package com.shebao.test.test.factory.template;

import com.shebao.test.test.factory.Login;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component(value = "dLogin")
public class DLogin implements Login {
    @Override
    public boolean support(String type) {
       return Objects.requireNonNull(AnnotationUtils.findAnnotation(this.getClass(), Component.class)).value().equals(type);
    }

    @Override
    public String execute(String username, String password) {
        return Objects.requireNonNull(AnnotationUtils.findAnnotation(this.getClass(), Component.class)).value();
    }
}
