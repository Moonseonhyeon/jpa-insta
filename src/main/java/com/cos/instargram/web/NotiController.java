package com.cos.instargram.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.instargram.domain.noti.NotiRepository;
import com.cos.instargram.service.NotiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class NotiController {

	private final NotiService notiService; 
	
	@GetMapping("/noti/{loginUserId}")
	public String noti(@PathVariable int loginUserId, Model model) {
		model.addAttribute("notis", notiService.알람리스트(loginUserId));
		return "noti/noti";
	}
	
}
