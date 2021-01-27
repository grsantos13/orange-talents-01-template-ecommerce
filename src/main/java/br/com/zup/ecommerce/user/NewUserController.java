package br.com.zup.ecommerce.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewUserController {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@PostMapping("/users")
	@Transactional
	public ResponseEntity<?> create(@RequestBody @Valid NewUserRequest request){
		String passwordEncoded = encoder.encode(request.getPassword());
		request.setPassword(passwordEncoded);
		User user = request.toModel();
		manager.persist(user);
		return ResponseEntity.ok().build();
	}
}
