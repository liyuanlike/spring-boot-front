package com.github.service;

import com.github.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Cacheable(value="objectCache", key="#root.targetClass.getSimpleName().concat(':').concat(#root.methodName).concat(':').concat(#uid)")
	public User getUser(Integer uid) {
		return new User();
	}

}

