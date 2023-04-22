package co.surveygenerator.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.surveygenerator.dto.Message;
import co.surveygenerator.dto.SurveyDto;
import co.surveygenerator.entities.Option;
import co.surveygenerator.entities.Question;
import co.surveygenerator.entities.Survey;
import co.surveygenerator.repositories.IOptionRepository;
import co.surveygenerator.repositories.IQuestionRepository;
import co.surveygenerator.services.ISurveyService;
import co.surveygenerator.services.IUserDataService;

@RestController
@RequestMapping("/surveygenerator/surveys")
public class SurveyController {
		
	@Autowired
	private IUserDataService userDataService; 
	
	@Autowired
	private ISurveyService surveyService;
		
	@Autowired
	private IQuestionRepository questionRepository;
	
	@Autowired
	private IOptionRepository optionRepository;
	
	@PreAuthorize("hasRole('USER')")	
	@GetMapping("/user-surveys")
	public List<Survey> findAllByIdUser(){
		return surveyService.findAllSurveyByUserId(userDataService.getCurrentUserId());	
	}
	
	@PreAuthorize("hasRole('USER')")	
	@PostMapping("/create")
	public ResponseEntity<Message> create(@RequestBody SurveyDto surveyDto){      
		Survey newSurvey = new Survey();
		newSurvey.setTitle(surveyDto.getTitle());
		newSurvey.setSubTitle(surveyDto.getSubTitle());
		newSurvey.setDescription(surveyDto.getDescription());
		newSurvey.setUserData(userDataService.findById(userDataService.getCurrentUserId()));
				
		surveyService.create(newSurvey);
		
		return new ResponseEntity<Message>(new Message("Survey Created."), HttpStatus.OK);

	}
	
	@PreAuthorize("hasRole('USER')")	
	@GetMapping("/questions/{id}")
	public Optional<Question> findQuestion(@PathVariable Integer id){
		return questionRepository.findById(id);
	}
	
	@PreAuthorize("hasRole('USER')")	
	@GetMapping("/questions/options/{id}")
	public Optional<Option> findOption(@PathVariable Integer id){
		return optionRepository.findById(id);
	}
}
