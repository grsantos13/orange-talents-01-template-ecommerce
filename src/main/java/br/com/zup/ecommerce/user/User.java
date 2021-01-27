package br.com.zup.ecommerce.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "{user.login.blank}")
	@Email(message = "{user.login.emailFormat}")
	@Column(nullable = false)
	private String login;
	
	@NotBlank(message = "{user.password.blank}")
	@Size(min = 6, message = "{user.password.size}")
	@Column(nullable = false)
	private String password;
	
	@NotNull(message = "{user.creationTime.null}")
	@PastOrPresent(message = "{user.creationTime.future}")
	@Column(nullable = false, updatable = false)
	private LocalDateTime creationTime;
	
	public User(@NotBlank @Email String login,
			@NotBlank @Size String password) {
		this.login = login;
		this.password = password;
		this.creationTime = LocalDateTime.now();
	}
}
