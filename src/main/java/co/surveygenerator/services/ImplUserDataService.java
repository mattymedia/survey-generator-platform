package co.surveygenerator.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.surveygenerator.entities.UserData;
import co.surveygenerator.repositories.UserDataRepository;

@Service
public class ImplUserDataService implements UserDataService{

	@Autowired
	private UserDataRepository userDataRepository;
	
	@Override
	public List<UserData> findAll() {
		return userDataRepository.findAll();
	}

	@Override
	public UserData save(UserData userData) {
		return userDataRepository.save(userData);
	}

}
