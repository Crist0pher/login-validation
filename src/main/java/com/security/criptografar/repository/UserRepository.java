package com.security.criptografar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.criptografar.models.*;

public interface UserRepository extends JpaRepository<UserModel, Integer>{
	
	public Optional <UserModel> findByLogin(String login);

}
