package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	};
	
	public void saveAndFlush(User user) {
		userRepository.saveAndFlush(user);
	}
	
	public Optional<User> findById(long id) {
		return userRepository.findById(id);
	}

	public void deleteById(long id) {
		userRepository.deleteById(id);
	}
}