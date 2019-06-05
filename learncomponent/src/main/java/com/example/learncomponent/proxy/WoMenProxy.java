package com.example.learncomponent.proxy;

import com.infoholdcity.basearchitecture.self_extends.Klog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class WoMenProxy  {
    Object target;


    public WoMenProxy(Object o){
        this.target = o;
    }
    public Object getProxyObject(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Klog.Companion.e("YYYY",proxy.getClass().getName());
                Klog.Companion.e("YYYY","调用之前");
                System.out.println("开始代理"); //增强实现
                Object result = method.invoke(target, args);
                System.out.println("代理结束"); //增强实现
                Klog.Companion.e("YYYY","调用之后");
                return result;
            }
        });


    }

}
