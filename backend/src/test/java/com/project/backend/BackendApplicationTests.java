package com.project.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.cloud.firestore.DocumentSnapshot;
import com.project.backend.repository.FirestoreRepository;
import com.project.backend.security.AuthenticationDetails;
import com.project.backend.security.UserRole;
@SuppressWarnings("null")
@SpringBootTest
class BackendApplicationTests {
	@Autowired
	private FirestoreRepository repo;
	@Test
	void testSaveDocument() {
		AuthenticationDetails details = new AuthenticationDetails("d", "aaa", "baabb4477", Arrays.asList(UserRole.STUDENT), "scw594P1nMrDvIglNSJm");
		repo.saveDocument(details);
	}
	@Test
	void testGetDocumentsByField() {
		DocumentSnapshot obj = repo.getDocumentById(AuthenticationDetails.class, "ioq7bGpKnXVKLcWAPamF");
		List<DocumentSnapshot> list = repo.getDocumentsByField(AuthenticationDetails.class, "email", "aaa");
		assertEquals("c", list.get(1).getData().get("username").toString());
	}
	@Test
	void testGetDocumentById() {
		DocumentSnapshot obj = repo.getDocumentById(AuthenticationDetails.class, "ioq7bGpKnXVKLcWAPamF");
		assertEquals("b", obj.getData().get("username"));
	}
	@Test
	void testDeleteDocumentById() {
		repo.deleteDocumentById(AuthenticationDetails.class, "aaaa");
		DocumentSnapshot obj = repo.getDocumentById(AuthenticationDetails.class, "aaaa");
		assertEquals(null, obj);
	}
	@Test
	void testUpdateDocumentById() {
		DocumentSnapshot obj = repo.getDocumentById(AuthenticationDetails.class, "w00KJz3nTgB5Rs4hrPdz");
		AuthenticationDetails d = obj.toObject(AuthenticationDetails.class);
		d.setEmail("bbbbbb");
		assertEquals(true, repo.updateDocumentById(d));
	}
	@Test
	void testUpdateDocumentById2() {
		DocumentSnapshot obj = repo.getDocumentById(AuthenticationDetails.class, "w00KJz3nTgB5Rs4hrPdz");
		Map<String, Object> o = obj.getData();	
		o.put("email", "44444444");
		assertEquals(true, repo.updateDocumentById(AuthenticationDetails.class, "w00KJz3nTgB5Rs4hrPdz", o));
	}
}
