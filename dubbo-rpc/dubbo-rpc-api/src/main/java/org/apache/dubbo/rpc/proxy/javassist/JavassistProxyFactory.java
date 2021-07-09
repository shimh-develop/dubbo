/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.rpc.proxy.javassist;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.bytecode.Proxy;
import org.apache.dubbo.common.bytecode.Wrapper;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.proxy.AbstractProxyFactory;
import org.apache.dubbo.rpc.proxy.AbstractProxyInvoker;
import org.apache.dubbo.rpc.proxy.InvokerInvocationHandler;

/**
 * JavassistRpcProxyFactory
 */
public class JavassistProxyFactory extends AbstractProxyFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
        /**
         * 消费端生成代理
         *
         * package org.apache.dubbo.common.bytecode;
         *
         * public class proxy0 implements org.apache.dubbo.demo.DemoService {
         *
         *     public static java.lang.reflect.Method[] methods;
         *
         *     private java.lang.reflect.InvocationHandler handler;
         *
         *     public proxy0() {
         *     }
         *
         *     public proxy0(java.lang.reflect.InvocationHandler arg0) {
         *         handler = $1;
         *     }
         *
         *     public java.lang.String sayHello(java.lang.String arg0) {
         *         Object[] args = new Object[1];
         *         args[0] = ($w) $1;
         *         Object ret = handler.invoke(this, methods[0], args);
         *         return (java.lang.String) ret;
         *     }
         * }
         *
         * 实际就是调用
         * @see InvokerInvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
         * 包装好参数就调用invoker
         */
        return (T) Proxy.getProxy(interfaces).newInstance(new InvokerInvocationHandler(invoker));
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) {
        //s 服务端生成代理
        // TODO Wrapper cannot handle this scenario correctly: the classname contains '$'
        //s 通过 Javassist 生成 Class 对象，最后再通过反射创建 Wrapper 实例
        final Wrapper wrapper = Wrapper.getWrapper(proxy.getClass().getName().indexOf('$') < 0 ? proxy.getClass() : type);
        return new AbstractProxyInvoker<T>(proxy, type, url) {
            @Override
            protected Object doInvoke(T proxy, String methodName,
                                      Class<?>[] parameterTypes,
                                      Object[] arguments) throws Throwable {
                return wrapper.invokeMethod(proxy, methodName, parameterTypes, arguments);
            }
        };
    }

}
