package com.nhnacademy.mvc;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ControllerFactory  {
    private final ConcurrentMap<String, Object> beanMap = new ConcurrentHashMap<>();

    public void init(Set<Class<?>> c){
        //todo beanMap에 key = method + servletPath, value = Controller instance
        for (Class<?> clazz : c) {
            // Controller 인스턴스 생성
            Object controllerInstance;
            try {
                controllerInstance = clazz.newInstance();

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                continue; // 인스턴스 생성에 실패하면 다음 클래스로 넘어감
            }

            // Controller 클래스의 메서드들을 순회하면서 servletPath에 대응되는 메서드를 beanMap에 등록
            RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                String key = requestMapping.method() + requestMapping.value();
                beanMap.put(key, controllerInstance);
            }
        }
    }

    public Object getBean(String method, String path){
        //todo beanMap 에서 method+servletPath을 key로 이용하여 Controller instance를 반환합니다.
        return beanMap.get(method + path);
    }
}