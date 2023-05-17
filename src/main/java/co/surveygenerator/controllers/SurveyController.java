package co.surveygenerator.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.surveygenerator.dto.Message;
import co.surveygenerator.dto.SurveyDto;
import co.surveygenerator.entities.Category;
import co.surveygenerator.entities.Option;
import co.surveygenerator.entities.Question;
import co.surveygenerator.entities.Survey;
import co.surveygenerator.repositories.ICategoryRepository;
import co.surveygenerator.repositories.IOptionRepository;
import co.surveygenerator.repositories.IQuestionRepository;
import co.surveygenerator.services.ISurveyService;
import co.surveygenerator.services.IUserDataService;

@RestController
@RequestMapping("/surveygenerator/surveys")
@CrossOrigin(origins = "http://localhost:4200")
public class SurveyController {

	@Autowired
	private IUserDataService userDataService;

	@Autowired
	private ISurveyService surveyService;

	@Autowired
	private IQuestionRepository questionRepository;

	@Autowired
	private IOptionRepository optionRepository;

	@Autowired
	private ICategoryRepository categoryRepository;

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/findall")
	public List<Survey> findAllByIdUser() {
		return surveyService.findAllSurveyByUserId(userDataService.getCurrentUserId());
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/findbyid/{id}")
	public Survey findById(@PathVariable Integer id) {
			return surveyService.findById(id);
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/create")
	public ResponseEntity<Message> create(@RequestBody SurveyDto surveyDto) {
		Survey newSurvey = new Survey();
		newSurvey.setTitle(surveyDto.getTitle());
		// newSurvey.setSubTitle(surveyDto.getSubTitle());
		newSurvey.setDescription(surveyDto.getDescription());
		newSurvey.setUserData(userDataService.findById(userDataService.getCurrentUserId()));

		surveyService.create(newSurvey);

		return new ResponseEntity<Message>(new Message("Survey Created."), HttpStatus.OK);

	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/create/surveyform/{id}")
	public void createSurveyForm(@RequestBody List<Question> questions, @PathVariable Integer id) {
		List<Question> questionList = new ArrayList<>();
		List<Option> optionList = new ArrayList<>();

		Survey survey = surveyService.findById(id);
		
		if(survey != null) {
			for (Question question : questions) {
				Question newQuestion = new Question();
				newQuestion.setDescription(question.getDescription());
				newQuestion.setSurvey(survey);
				questionList.add(newQuestion);
	
				for (Option option : question.getOptions()) {
					Option newOption = new Option();
					newOption.setDescription(option.getDescription());
					newOption.setQuestion(newQuestion);
					optionList.add(newOption);
				}
			}
		}

		questionRepository.saveAll(questionList);
		optionRepository.saveAll(optionList);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteVyId(@PathVariable Integer id) {
		surveyService.delete(id);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/categories")
	public List<Category> findAllCategories() {
		return categoryRepository.findAll();
	}
}
