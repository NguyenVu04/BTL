package com.project.backend;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.backend.repository.FirestoreRepository;
import com.project.backend.security.AuthenticationDetails;
import com.project.backend.security.UserRole;

@SpringBootTest
class BackendApplicationTests {
	@Autowired
	private FirestoreRepository repo;
	@Test
	void contextLoads() {
		AuthenticationDetails details = new AuthenticationDetails("aaa", "bbb", Arrays.asList(UserRole.STUDENT), "scw594P1nMrDvIglNSJm");
		repo.saveDocument(details);
	}

}
