package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
	 * @RequestMapping 적용 메서드의 커맨드 객체 파라미터 뒤에 Errors 타입의 파라미터가 위치하면,
	 * 스프링 MVC는 해당 메서드 호출할 때 커맨드 객체와 연결된 Errors 객체를 생성해서 파라미터로 전달함.
	 * 이 Errors 객체는 getFieldValue() 메서드를 제공하고 있으며,
	 * 이 메서드를 사용하면 커맨드 객체의 특정 프로퍼티값을 가져올 수 있다.
	 * 따라서 ValidationUtils.rejectIfEmptyOrWhitespace() (RegisterRequestValidator클래스 참고) 메서드는
	 * 커맨드 객체를 전달받지 않아도 Errors 객체를 이용해서 지정한 값을 구할 수 있다.
	 * 
	 * @RequestMapping 적용 메서드에 Errors 타입의 파라미터를 추가할 때의 주의할 점은
	 * Errors 타입의 파라미터는 반드시 커맨드 객체를 위한 파라미터 다음에 위치해야 함.
	 * 
	 * 커맨드 객체의 특정 프로퍼티가 아닌 커맨드 객체 자체가 잘돗된 경우에는
	 * rejectValue()대신 reject() 사용. (보안을 위해 아이디와 비밀번호가 일치하지 않는다고 보여주는 경우)
	 * 특정 프로퍼티에 에러를 추가하는게 아니라 커맨드 객체 자체에 에러를 추가함.
	 * 
	 * @param regReq
	 * @return
	 */
	@RequestMapping(value="/register/step3", method=RequestMethod.POST)
	public String handleStep3(RegisterRequest regReq, Errors errors) {
		// validate() 메서드를 실행하는 과정에서 유효하지 않은 값이 존재하면 Errors의 rejectValue()메서드가 실행되는데,
		// 이 메서드가 한번이라도 불리면 Errors의 hasErrors()메서드는 true 리턴함.
		new RegisterRequestValidator().validate(regReq, errors);
		
		if (errors.hasErrors()) {
			return "register/step2";
		}
		
		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
		} catch (AlreadyExistingMemberException e) {
			errors.rejectValue("email", "duplicate");
			return "register/step2";
		}
	}
	
	/*@RequestMapping(value="/register/step3", method=RequestMethod.POST)
	public String handleStep3(RegisterRequest regReq) {
		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
		} catch (AlreadyExistingMemberException e) {
			return "register/step2";
		}
	}*/
}
