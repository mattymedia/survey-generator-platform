package co.surveygenerator.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "questions")
public class Question implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String description;
	
	@ManyToOne
    @JoinColumn(name = "survey_id")
	@JsonIgnore
	private Survey survey;
	
	@Column(name = "num_question")
	private Integer numQuestion;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private List<Option> options;
	
	public Question() {}

	public Question(String description, Survey survey, Integer numQuestion,List<Option> options) {
		this.description = description;
		this.survey = survey;
		this.options = options;
		this.numQuestion = numQuestion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	
	public Integer getNumQuestion() {
		return numQuestion;
	}

	public void setNumQuestion(Integer numQuestion) {
		this.numQuestion = numQuestion;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}	
}
