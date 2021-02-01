package br.com.zup.ecommerce.user;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(name = "user_login_uk", columnNames = { "login" }) })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Email
	@Column(nullable = false)
	private String login;

	@NotBlank
	@Size(min = 6)
	@Column(nullable = false)
	private String password;

	@NotNull
	@PastOrPresent
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Profile> profiles = new LinkedHashSet<>();

	@Deprecated
	public User() {
	}

	public User(@NotBlank @Email String login, @NotNull @Valid CleanPassword cleanPassword) {
		Assert.hasLength(login, "Field login must not be blank");
		Assert.notNull(cleanPassword, "The object cleanPassword must not be null");
		
		this.login = login;
		this.password = cleanPassword.hash();
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public Set<Profile> getProfiles() {
		return profiles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return login.equals(user.login);
	}

	@Override
	public int hashCode() {
		return Objects.hash(login);
	}
}
