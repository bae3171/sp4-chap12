package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import spring.RegisterRequest;

/**
 * Validator 인터페이스 구현
 * 
 * @author assu
 * @date 2016. 7. 16.
 */
public class RegisterRequestValidator implements Validator {

	private static final String emailRegExp =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private Pattern pattern;
	
	/**
	 * 정규식을 매개변수로 Pattern 클래스의 static메서드인 
	 * Pattern compile(String regex)을 호출하여 Pattern 인스턴스 얻음
	 */
	public RegisterRequestValidator() {
		pattern = Pattern.compile(emailRegExp);
	}
	
	/* 
	 * 파라미터로 전달받은 clazz 타입의 객체가 RegisterRequest 클래스로 타입 변환이 가능한지 확인
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterRequest.class.isAssignableFrom(clazz);
	}

	/* 
	 * target 객체는 검사 대상 객체이고,
	 * errors 객체는 검사 결과 에러 코드를 설정하기 위한 객체
	 * 
	 * 검사 대상 객체의 특정 프로퍼티나 상태가 올바른지 검사하고,
	 * 올바르지 않다면 Errors의 rejectValue() or reject() 메서드를 이용해서 에러 코드 저장함.
	 */
	@Override
	public void validate(Object target, Errors errors) {
		RegisterRequest regReq = (RegisterRequest) target;	// target을 실제 타입으로 변환
		
		// "email" 프로퍼티의 값이 유효한지 검사
		if (regReq.getEmail() == null || regReq.getEmail().trim().isEmpty()) {
			errors.rejectValue("email", "required");
		} else {
			// 정규식으로 비교할 대상을 매개변수로 Pattern클래스의 Matcher matcher를 호출해서 Matcher 인스턴스얻음.
			Matcher matcher = pattern.matcher(regReq.getEmail());
			// Matcher인스턴스의 boolean matches()를 호출해서 정규식에 부합하는지 확인
			if (!matcher.matches()) {
				errors.rejectValue("email", "bad");
			}
		}
		
		// @RequestMapping 적용 메서드에 Errors타입 파라미터를 전달받고,
		// 이 Errors객체를 Validator의 validator()메서드에 두번째 파라미터로 전달하기 때문에
		// 대상 객체인 target을 ValidationUtils 에 파라미터로 전달하지 않아도 target 객체의 "name" 프로퍼티값 검사 가능
		
		// "name"프로퍼티가 null이거나 공백문자로만 되어있는 경우
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmpty(errors, "password", "required");
		ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required");
		
		if (!regReq.isPasswordEqualToConfirmPassword()) {
			errors.rejectValue("confirmPassword", "nomatch");
		}
	}

}
