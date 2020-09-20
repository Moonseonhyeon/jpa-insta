package com.cos.instargram.config.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.instargram.config.handler.ex.MyImageIdNotFoundException;
import com.cos.instargram.config.handler.ex.MyUserIdNotFoundException;
import com.cos.instargram.config.handler.ex.MyUsernameNotFoundException;
import com.cos.instargram.util.Script;

@ControllerAdvice //모든 Exceptioin 낚아챌 수 있어요!
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = MyUserIdNotFoundException.class)
	public String myUserIdNotFoundException(Exception e) {
		return Script.back(e.getMessage()); //웹이라서 이렇게 반환함. 안드랑 리액트랑 할 때는 다름.
	}
	
	@ExceptionHandler(value = MyUsernameNotFoundException.class)
	public String myUsernameNotFoundException(Exception e) {
	return Script.alert(e.getMessage());
	}
	
	@ExceptionHandler(value=MyImageIdNotFoundException.class)
	public String myImageIdNotFoundException(Exception e) {
		return Script.alert(e.getMessage());
	}
	
	
}
