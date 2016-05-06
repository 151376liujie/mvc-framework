package org.framework;

import org.framework.utils.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by liujie on 2016/5/7 1:14.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestAopUtils.class,
        TestClassUtils.class,
//        TestDatabaseUtils.class,
        TestIocUtils.class,
        TestPropertiesUtils.class,
        TestReflectionUtils.class
})
public class TestSuit {

}
