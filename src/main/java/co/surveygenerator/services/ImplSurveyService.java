package co.surveygenerator.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.surveygenerator.entities.Survey;
import co.surveygenerator.repositories.ISurveyRepository;

@Service
public class ImplSurveyService implements ISurveyService {

	@Autowired
	private ISurveyRepository surveyRepository;
	
	@Override
	@Transactional
	public Survey create(Survey survey) {
		return surveyRepository.save(survey);
	}

	
	@Override
	public List<Survey> findAllSurveyByUserId(Integer userId) {
		return surveyRepository.findAllSurveyByUserId(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Survey> findAll() {
		// TODO Auto-generated method stub
		return surveyRepository.findAll();
	}

}
