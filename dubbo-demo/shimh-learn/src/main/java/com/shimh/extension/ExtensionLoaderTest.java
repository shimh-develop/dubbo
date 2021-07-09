package com.shimh.extension;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.AdaptiveClassCodeGenerator;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author: shiminghui
 * @create: 2021年03月
 **/
public class ExtensionLoaderTest {

    @Test
    public void test1() {
        // 获取普通的扩展类com.shimh.extension.AImpl
        A a = ExtensionLoader.getExtensionLoader(A.class).getExtension("impl");
    }

    @Test
    public void test2() {
        // 获取自适应扩展类
        A a = ExtensionLoader.getExtensionLoader(A.class).getAdaptiveExtension();

        // 获取自适应扩展类时 用到的构建类内容字符串
        AdaptiveClassCodeGenerator generator = new AdaptiveClassCodeGenerator(A.class, "impl");
        String classContent = generator.generate();
        System.out.println(classContent);
        /**
         * package com.shimh.extension;
         * import org.apache.dubbo.common.extension.ExtensionLoader;
         * public class A$Adaptive implements com.shimh.extension.A {
         * public void testAdaptive(org.apache.dubbo.common.URL arg0)  {
         * if (arg0 == null) throw new IllegalArgumentException("url == null");
         * org.apache.dubbo.common.URL url = arg0;
         * String extName = url.getParameter("a", "impl");
         * if(extName == null) throw new IllegalStateException("Failed to get extension (com.shimh.extension.A) name from url (" + url.toString() + ") use keys([a])");
         * com.shimh.extension.A extension = (com.shimh.extension.A)ExtensionLoader.getExtensionLoader(com.shimh.extension.A.class).getExtension(extName);
         * extension.testAdaptive(arg0);
         * }
         * }
         */
    }

    @Test
    public void test3() {
        // 获取所有自动激活扩展类
        URL url = null;
        String key = null;
        String group = null;
        // 如果URL的参数中传入了-default,则所有的默认@Activate都不
        // 会被激活，只有URL参数中指定的扩展点会被激活。如果传入了-符号开头的扩展点名， 则该扩展点也不会被自动激活。例如：-xxxx,表示名字为xxxx的扩展点不会被激活
        List<A> aList = ExtensionLoader.getExtensionLoader(A.class).getActivateExtension(url, key, group);

        String[] values = null;
        // 最底层的方法
        ExtensionLoader.getExtensionLoader(A.class).getActivateExtension(url, values, group);
    }


    @Test
    public void simple() throws Exception{
        A.class.getConstructor();
        System.out.println(A.class.getSimpleName());
        System.out.println(AImpl.class.getSimpleName());
        for (Method m: AImpl.class.getMethods()) {
            System.out.println(m.getParameters()[0]);
        }

    }


}
