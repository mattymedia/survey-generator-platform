package co.surveygenerator.controllers;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.surveygenerator.dto.Message;
import co.surveygenerator.dto.UserDataDto;
import co.surveygenerator.entities.UserData;
import co.surveygenerator.services.IUserDataService;

@RestController
@RequestMapping("/surveygenerator/userdata")
public class UserDataController {
	
	@Autowired
	private IUserDataService userDataService;
			
	@GetMapping("/list")
	public List<UserData> findAll(){
		return userDataService.findAll();
	}
		
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/edit")
	public ResponseEntity<Message> edit(@Valid @RequestBody UserDataDto userForm, BindingResult result) {
			       
		if(result.hasErrors())
			return new ResponseEntity<Message>(new Message("The fields are empty or a valid email was not provided."), HttpStatus.BAD_REQUEST);
		
		UserData userProfile = userDataService.findById(userDataService.getCurrentUser());
		userProfile.setName(userForm.getName());
		userProfile.setSurname(userForm.getSurname());
		userProfile.setEmail(userForm.getEmail());
		userDataService.save(userProfile);
		
		return new ResponseEntity<Message>(new Message("the user information has been saved."), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String admin() {
		return "este es el perfil admin";
	}

}
