package co.surveygenerator.services;

import java.util.List;

import co.surveygenerator.entities.UserData;

public interface UserDataService {

	public List<UserData> findAll();
	
	public UserData save(UserData userData);
	
}
