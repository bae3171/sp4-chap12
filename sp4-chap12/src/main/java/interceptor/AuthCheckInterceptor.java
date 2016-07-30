package interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 로그인 여부 인터셉터
 * 
 * request.getSession(), request.getSession(true) - HttpSession이 존재하면 현재의 HttpSession 반환, 
 *                                                  존재하지 않으면 새로운 HttpSession 생성.
 * request.getSession(false)                      - 존재하지 않으면 null 반환.
 * 
 * @author assu
 * @date 2016. 7. 30.
 */
public class AuthCheckInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object authInfo = session.getAttribute("authInfo");
			if (authInfo != null) {
				return true;
			}
		}
		response.sendRedirect(request.getContextPath() + "/login");
		return false;
	}
}
