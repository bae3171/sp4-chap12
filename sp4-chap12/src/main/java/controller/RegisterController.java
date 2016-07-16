package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import spring.AlreadyExistingMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;


/**
 * @RequestMapping을 이용한 경로 매핑
 * 
 * @author assu
 * @date 2016. 6. 12.
 */
@Controller
public class RegisterController {
	
	private MemberRegisterService memberRegisterService;
	
	public void setMemberRegisterService(MemberRegisterService memberRegisterService) {
		this.memberRegisterService = memberRegisterService;
	}
	
	/**
	 * 약관 동의 화면
	 * 
	 * @return
	 */
	@RequestMapping("/register/step1")
	public String handleStep1() {
		return "register/step1";
	}
	
	/**
	 * 동의안하면 다시 약관동의화면, 동의하면 회원정보입력화면으로 이동
	 * 
	 * @param agree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/register/step2", method=RequestMethod.POST)
	public String handleStep2(@RequestParam(value="agree", defaultValue="false") Boolean agree, Model model) {
		if (!agree) {
			return "register/step1";
		}
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";
	}
	
	
	/**
	 * GET방식으로 step2 접근 시 step1으로 리다이렉트
	 * 
	 * @return
	 */
	@RequestMapping(value="/register/step2", method=RequestMethod.GET)
	public String handleStep2Get() {
		return "redirect:step1";
	}
	
	/**
	 * 이미 등록한 메일이면 전단계, 아니면 완료화면으로 이동
	 * 
	 * @param regReq
	 * @return
	 */
	@RequestMapping(value="/register/step3", method=RequestMethod.POST)
	public String handleStep3(RegisterRequest regReq) {
		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
		} catch (AlreadyExistingMemberException e) {
			return "register/step2";
		}
	}
}
