package com.cos.instargram.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.PARAMETER) // 이 어노테이션 사용할 위치
@Retention(RetentionPolicy.RUNTIME) //스프링실행할 때 부터 이 어노테이션 활성화
public @interface LoginUserAnnotation {

}
