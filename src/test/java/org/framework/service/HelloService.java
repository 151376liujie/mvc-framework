package org.framework.service;

import org.framework.annotation.Inject;
import org.framework.annotation.Service;

/**
 * Created by LiuJie on 2016/5/4 10:08.
 */

@Service
public class HelloService {

    @Inject
    private GreetService greetService;

    public void sayHi(String name) {
        greetService.sayHi(name);
    }

}
