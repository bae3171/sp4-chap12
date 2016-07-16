package spring;

/**
 * 이메일과 비번이 일치하는지 확인 후 일치하면 AuthInfo 객체 생성
 * 
 * @author assu
 * @date 2016. 7. 16.
 */
public class AuthService {
	private MemberDao memberDao;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public AuthInfo authenticate(String email, String password) {
		Member member = memberDao.selectByEmail(email);
		if (member == null) {
			throw new IdPasswordNotMatchingException();
		}
		if (!member.matchPassword(password)) {
			throw new IdPasswordNotMatchingException();
		}
		return new AuthInfo(member.getId(), member.getEmail(), member.getName());
	}
}
