package com.security.criptografar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.security.criptografar.models.UserModel;
import com.security.criptografar.repository.UserRepository;

@RestController
@RequestMapping("/")
public class Controller {
	
		@Autowired
		UserRepository repository;
		@Autowired 
		PasswordEncoder encoder;
	
		@GetMapping
		public ResponseEntity <List <UserModel>> getAll(){
			return ResponseEntity.ok(repository.findAll());
		}
		
		@PostMapping("/save")
		public ResponseEntity<UserModel> save(@RequestBody UserModel user){
			user.setPassword(encoder.encode(user.getPassword()));
		return ResponseEntity.ok(repository.save(user));
		}
		
		@PostMapping("/valid")
		public ResponseEntity <Boolean> validPassword(@RequestBody UserModel user){
			
			
			Optional <UserModel> userModel = repository.findByLogin(user.getLogin());
			
			if(userModel.isEmpty()) {
				 	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
			}
			boolean valid = encoder.matches(user.getPassword(), userModel.get().getPassword());
			HttpStatus status = (!valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
			
		 	return ResponseEntity.status(status).body(valid);
			
		}
}
