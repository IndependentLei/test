package com.shebao.test.test.factory;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginTest {

    @Autowired
    LoginFactory loginFactory;

    public String login(JSONObject loginObj){
        try {
            String type = loginObj.getString("type");
            String username = loginObj.getString("username");
            String password = loginObj.getString("password");
            Login login = loginFactory.getLoginType(type);
            if(login.support(type)){
                return login.execute(username, password);
            }
        } catch (Exception e) {
        }
        return "登录有误";
    }
}
