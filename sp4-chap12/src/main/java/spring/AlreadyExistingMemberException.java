package spring;

/**
 * 회원 가입 처리 관련 클래스
 * 
 * @author assu
 * @date 2016.05.22
 */
public class AlreadyExistingMemberException extends RuntimeException {
	/**	 */
	private static final long serialVersionUID = 8541713429299719920L;

	public AlreadyExistingMemberException(String message) {
		super(message);
	}
}
