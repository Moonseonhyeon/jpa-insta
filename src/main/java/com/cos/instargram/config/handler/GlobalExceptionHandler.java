package com.cos.instargram.config.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.instargram.config.handler.ex.MyUsernameNotFoundException;
import com.cos.instargram.util.Script;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = MyUsernameNotFoundException.class)
	public String myUsernameNotFoundException(Exception e) {
	return Script.alert("아이디 혹은 패스워드가 잘못되었습니다.");
	}
	

}
