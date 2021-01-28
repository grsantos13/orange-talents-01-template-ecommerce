package br.com.zup.ecommerce.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

/**
 * 
 * It represents a clean password in the system
 * @author Gustavo.Santos
 *
 */
public class CleanPassword {
	
	@NotBlank
	@Size(min = 6)
	private String password;

	public CleanPassword( @NotBlank @Size(min = 6) String password) {
		Assert.hasLength(password, "Password must not be blank");
		Assert.isTrue(password.length() >= 6, "Password must contain at least 6 characters");

		this.password = password;
	}

	public String hash() {
		return new BCryptPasswordEncoder().encode(password);
	}
	
	

}
