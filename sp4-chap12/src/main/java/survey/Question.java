package survey;

import java.util.Collections;
import java.util.List;


/**
 * 개별 설문항목데이터 관련 커맨드 객체
 * 
 * @author assu
 * @date 2016. 7. 16.
 */
public class Question {
	private String title;
	private List<String> options;
	
	public Question(String title, List<String> options) {
		this.title = title;
		this.options = options;
	}
	
	/**
	 * 주관식일 경우 답변 옵션이 없는 생성자 사용
	 * @param title
	 */
	public Question(String title) {
		this(title, Collections.<String>emptyList());
	}

	public String getTitle() {
		return title;
	}

	public List<String> getOptions() {
		return options;
	}

	public boolean isChoice() {
		return options != null && !options.isEmpty();
	}
}
