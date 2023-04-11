package co.surveygenerator.controllers;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.surveygenerator.dto.Message;
import co.surveygenerator.dto.UserDataDto;
import co.surveygenerator.entities.UserData;
import co.surveygenerator.security.entities.User;
import co.surveygenerator.security.services.UserService;
import co.surveygenerator.services.UserDataService;

@RestController
@RequestMapping("/userdata")
public class UserDataController {
	
	@Autowired
	private UserDataService userDataService;
	
	@Autowired
	private UserService userService;
		
	@GetMapping("/list")
	public List<UserData> findAll(){
		return userDataService.findAll();
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/current")
	public ResponseEntity<Message> currentUser() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 String username = authentication.getName();
		 return new ResponseEntity<Message>(new Message("username: " + username), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/save")
	public ResponseEntity<Message> save(@Valid @RequestBody UserDataDto userData, 
			BindingResult result) {
		
		if(result.hasErrors())
			return new ResponseEntity<Message>(new Message("The fields are empty or a valid email was not provided."), HttpStatus.BAD_REQUEST);
		
		User user = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
	            .orElseThrow(() -> new RuntimeException("User not found"));
		
		UserData newUserData = new UserData(user, userData.getName(), userData.getSurname(), userData.getEmail());
		userDataService.save(newUserData);
		
		return new ResponseEntity<Message>(new Message("the user information has been saved."), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String admin() {
		return "este es el perfil admin";
	}

}
