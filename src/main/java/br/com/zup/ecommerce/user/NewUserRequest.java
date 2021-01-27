package br.com.zup.ecommerce.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewUserRequest {

	@NotBlank(message = "{user.login.blank}")
	@Email(message = "{user.login.emailformat}")
	private String login;
	
	@NotBlank(message = "{user.password.blank}")
	@Size(min = 6, message = "{user.password.size}")
	private String password;

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public User toModel() {
		return new User(this.login, this.password);
	}
	

}
