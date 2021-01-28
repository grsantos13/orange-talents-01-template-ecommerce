package br.com.zup.ecommerce.category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
	
	@PersistenceContext
	private EntityManager manager;

	@PostMapping("/categories")
	@Transactional
	public ResponseEntity<?> create(@RequestBody @Valid NewCategoryRequest request){
		Category category = request.toModel(manager);
		manager.persist(category);
		CategoryResponse response = new CategoryResponse(category);
		return ResponseEntity.ok(response);
	}

}
