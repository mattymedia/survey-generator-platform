package co.surveygenerator.services;

import java.util.List;

import co.surveygenerator.entities.Survey;

public interface ISurveyService {
	
	public List<Survey> findAll();

	public List<Survey> findAllSurveyByUserId(Integer integer);
	
	public Survey create(Survey survey);
	
	
}
