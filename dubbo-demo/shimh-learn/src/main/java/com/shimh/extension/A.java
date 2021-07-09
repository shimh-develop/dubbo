package com.shimh.extension;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * @author: shiminghui
 * @create: 2021年03月
 **/
@SPI
public interface A {

    @Adaptive
    public void testAdaptive(URL url);

}
