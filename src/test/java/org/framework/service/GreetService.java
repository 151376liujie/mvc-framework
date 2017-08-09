package org.framework.service;

import org.framework.annotation.Service;

/**
 * Author: jonny
 * Time: 2017-08-09 21:34.
 */
@Service
public class GreetService {

    public void sayHi(String name) {
        System.out.println("hello " + name);
    }

}
