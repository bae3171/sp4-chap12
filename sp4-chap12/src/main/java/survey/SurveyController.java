package survey;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 중첩,콜렉션 프로퍼티의 커맨드객체를 사용하는 컨트롤러
 * 
 * @author assu
 * @date 2016. 7. 16.
 */
@Controller
@RequestMapping("/survey")
public class SurveyController {

	/**
	 * 전송방식만을 설정했으므로 이 메서드가 처리하는 경로는 GET방식으로 요청된 /survey
	 * @return
	 */
	/*@RequestMapping(method=RequestMethod.GET)
	public String form() {
		return "survey/surveyFrom";
	}*/
	
	@RequestMapping(method = RequestMethod.GET)
	public String form(Model model) {
		List<Question> questions = createQuestions();
		model.addAttribute("questions", questions);
		return "survey/surveyFrom";
	}
	
	private List<Question> createQuestions() {
		// Arrays.asList : 배열 -> 배열리스트
		Question q1 = new Question("당신의 역할은 무엇입니까?", Arrays.asList("서버", "프론트", "풀스택"));
		Question q2 = new Question("많이 사용하는 개발도구는 무엇입니까?", Arrays.asList("이클립스", "인텔리J", "서브라임"));
		Question q3 = new Question("하고 싶은 말을 적어주세요.");
		
		return Arrays.asList(q1, q2, q3); 
	}
	
	/**
	 * 전송방식만을 설정했으므로 이 메서드가 처리하는 경로는 POST방식으로 요청된 /survey
	 * @param data
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public String submit(@ModelAttribute("ansData") AnsweredData data) {
		return "survey/submitted";
	}
}
