package com.cos.instargram.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instargram.domain.noti.Noti;
import com.cos.instargram.domain.noti.NotiRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotiService {	
	private final NotiRepository notiRepository;
	
	@Transactional(readOnly = true)
	public List<Noti> 알람리스트(int loginUserId){
		return notiRepository.findByToUserId(loginUserId);
	}
	
	

}
