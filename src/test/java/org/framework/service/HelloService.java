package org.framework.service;

import org.framework.annotation.Service;

/**
 * Created by LiuJie on 2016/5/4 10:08.
 */

@Service
public class HelloService {


    public void sayHi(String name) {
        System.out.println("hello," + name);
    }


}
