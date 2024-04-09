package com.project.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.project.backend.Course.Course;
import com.project.backend.Student.Student;
import com.project.backend.exceptionhandler.ExceptionLog;
import com.project.backend.repository.FirestoreRepository;
import com.project.backend.security.AuthenticationDetails;
import com.project.backend.security.JwtUtils;
import com.project.backend.security.UserRole;

import io.jsonwebtoken.Jwts;
@SuppressWarnings("null")
@SpringBootTest
class BackendApplicationTests {
	@Autowired
	private FirestoreRepository repo;
	@Test
	void testSaveDocument() {
		AuthenticationDetails details = new AuthenticationDetails("123465", "aaa101", "baabb", UserRole.TEACHER);
		repo.saveDocument(details);
	}
	@Test
	void testStudent() {
		Student student = new Student("Viet", Timestamp.now(), "daivietvonin1@gmail.com", null, true);
		repo.saveDocument(student);
	}
	@Test
	void testGetStudent() {
		DocumentSnapshot obj = repo.getDocumentById(Student.class, "fcq518PECoQ78ITUnszO");
		Student student = obj.toObject(Student.class);
		assertEquals("Viet", student.getName());
	}
	@Test
	void testStudentOrderBy() {
		
		List<DocumentSnapshot> list = repo.getDocumentsByField(Student.class, "name", "Viet", "dob", 1);
		assertEquals("Viet", list.get(0).get("name"));
	}
	@Test
	void testGetDocumentsByField1() {
		List<DocumentSnapshot> list = repo.getAllDocumentsByField(Course.class, "price", 100);
			assertEquals(2, list.size());
	}
	@SuppressWarnings("unused")
	@Test
	void testGetDocumentsByField() {
		DocumentSnapshot obj = repo.getDocumentById(AuthenticationDetails.class, "Djo2ohyahjN8gGofkPW5");
		List<DocumentSnapshot> list = repo.getDocumentsByField(AuthenticationDetails.class, "email", "aaa", "username", 2);
		assertEquals(2, list.size());
	}
	@Test
	void testGetDocumentById() {
		DocumentSnapshot obj = repo.getDocumentById(AuthenticationDetails.class, "lSKOq6mNbM9hW3aoOoSg");
		assertEquals("c", obj.getData().get("username"));
	}
	@Test
	void testDeleteDocumentById() {
		repo.deleteDocumentById(AuthenticationDetails.class, "aaaa");
		DocumentSnapshot obj = repo.getDocumentById(AuthenticationDetails.class, "aaaa");
		assertEquals(null, obj);
	}
	@Test
	void testUpdateDocumentById() {
		DocumentSnapshot obj = repo.getDocumentById(AuthenticationDetails.class, "TXda3LgHmXVotDhfwloI");
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
	@Autowired
	private ExceptionLog log;
	@Test
	void testEncodeJwt() {
		SecretKey s = Jwts.KEY.A256GCMKW.key().build();
		byte[] k = s.getEncoded();
		try (PrintStream out = new PrintStream(new FileOutputStream("SecretKey", false))) {
			out.write(k);
		} catch (Exception e) {
			log.log(e);
		}
		try (InputStream stream = new FileInputStream("SecretKey")) {
			byte[] bytes = stream.readAllBytes();
			SecretKey key = new SecretKeySpec(bytes, "AES");
			assertTrue(key.equals(s));
		} catch (Exception e) {
			log.log(e);
		}
	}
	@Autowired
	private JwtUtils utils;
	@Test
	void testJwtUtils() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", "aaaaaaaa");
		map.put("userId", "123456");
		assertEquals(map.get("user"), utils.decodeToken(utils.encodeObject(map)).get("user"));
	}
	@Test
	void testJwtUtils2() {
		String token = "eyJhbGciOiJkaXIiLCJlbmMiOiJBMjU2R0NNIn0..8uCUEqdolq3OJDat.qwMaiIMwFaETEqoWotAVeNUpUJLlCWRO30zEmJzp1QP9zE72aCsp4OYdHO247jySlBzzV7DBrFUVETTsFfmlC1SYTmwFAtF1uUNYSMACRDZqw55zp_L4YgWlSmKpRj7OLVkev9j_xoEPPy0M1wInAUjCF4REER1IG7rWSXyKWcP2lv6E56OuXw.KlelRFys5mV8Qpd0tk6Yug";
		assertEquals("aaa1", utils.decodeToken(token).get("email"));
	}
}
