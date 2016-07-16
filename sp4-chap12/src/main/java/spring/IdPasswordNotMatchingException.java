package spring;

/**
 * 회원 데이터 관련 클래스
 * 
 * RuntimeException를 상속받은 이유 : 트랜잭션 롤백 때문.
 * 
 * @author assu
 * @date 2016.05.22
 */
public class IdPasswordNotMatchingException extends RuntimeException {

	/**	 */
	private static final long serialVersionUID = 209094386738831609L;

}
