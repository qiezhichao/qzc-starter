package com.qzc.ag.test;

import com.qzc.ag.annotation.ServiceMapping;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @ServiceMapping("get.user")
    public User getUserById(String id){
        User user = new User("1",12,"zhangsan");
        return user;
    }

}
