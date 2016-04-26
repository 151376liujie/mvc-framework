package org.framework.aop;

import java.lang.annotation.*;

/**
 * 切面类注解，该注解只能标注在类上
 * Created by liujie on 2016/4/26.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();

}
