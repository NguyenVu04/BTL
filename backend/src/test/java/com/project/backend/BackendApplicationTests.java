package com.project.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.project.backend.Course.Course;
import com.project.backend.Student.Student;
import com.project.backend.Teacher.Certificate;
import com.project.backend.Teacher.Teacher;
import com.project.backend.email.EmailService;
import com.project.backend.exceptionhandler.ExceptionLog;
import com.project.backend.repository.BackendStorage;
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
	@Autowired
	private ExceptionLog exceptionLog;

	@Test
	void testSaveDocument() {
		AuthenticationDetails details = new AuthenticationDetails("admin2", "admin2", "admin", UserRole.STUDENT);
		boolean t = repo.updateDocumentById(details);
		assertTrue(t);
	}

	@Test
	void testStudent() {
		List<String> CourseID = new ArrayList<String>();
		LocalDateTime convertDate = LocalDateTime.of(2004, 1, 21, 12, 20, 10);
		Date date = Date.from(convertDate.atZone(ZoneId.systemDefault()).toInstant());
		Timestamp timestamp = Timestamp.of(date);

		Student student = new Student("Viet", timestamp, "daivietvonin1@gmail.com", CourseID, true);
		repo.saveDocument(student, "2213955");
	}

	@Test
	void testUpdateDOB() {
		LocalDateTime date = LocalDateTime.of(2004, 12, 8, 0, 0, 0);
		Date dates = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
		Timestamp timestamp = Timestamp.of(dates);
		Map<String, Object> obj = repo.getDocumentById(Student.class, "2213955").getData();
		obj.put("dob", timestamp);
		repo.updateDocumentById(Student.class, "2213955", obj);
	}

	@Test
	void testTeacher() {
		List<String> CourseID = new ArrayList<String>();
		Certificate certificate = new Certificate("CSE", "Professor", "HCMUT");
		LocalDateTime date = LocalDateTime.now();
		Date dates = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
		Timestamp timestamp = Timestamp.of(dates);
		Teacher student = new Teacher("Viet", "ledinhthuan@hcmut.edu.vn", timestamp, "0937584842", CourseID,
				certificate);
		repo.saveDocument(student, "1121");
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
		List<DocumentSnapshot> list = repo.getDocumentsByField(AuthenticationDetails.class, "email", "aaa", "username",
				2);
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

	@Test
	void testDateToLocalDateTime() {
		/**
		 * Tests the conversion between `LocalDateTime`, `Date`, and `Timestamp`
		 * objects.
		 * 
		 * This test verifies that a `LocalDateTime` can be converted to a `Date` and
		 * back to a `LocalDateTime` without losing any precision.
		 * 
		 * @param convertDate the `LocalDateTime` to be converted
		 * @param date        the `Date` object created from the `LocalDateTime`
		 * @param timestamp   the `Timestamp` object created from the `Date`
		 * @param newDate     the `Date` object created from the `Timestamp`
		 * @param dateTime    the `LocalDateTime` object created from the `Date`
		 */
		LocalDateTime convertDate = LocalDateTime.of(2024, 1, 21, 12, 20, 10);
		Date date = Date.from(convertDate.atZone(ZoneId.systemDefault()).toInstant());
		Timestamp timestamp = Timestamp.of(date);
		Date newDate = timestamp.toDate();
		assertEquals(date, newDate);
		LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		assertEquals(convertDate.getDayOfMonth(), dateTime.getDayOfMonth());
	}

	@Autowired
	private BackendStorage storage;

	@Autowired
	private EmailService mail;

	@Test
	public void testEmail() {
		Map.Entry<String, Resource> resource = storage.getFile(Arrays.asList("Hello, World.txt"));
		assertEquals("Hello, World.txt", resource.getKey());
		assertTrue(mail.sendEmail("backend",
				"MAIL TEST",
				"This is a test",
				"nguyenvu04.work@gmail.com",
				Arrays.asList(resource)));
		assertTrue(mail.sendEmail("backend",
				"MAIL TEST",
				"This is a test",
				Arrays.asList("nguyenvu04.work@gmail.com", "daivietvonin1@gmail.com"),
				Arrays.asList(resource)));
	}
}
