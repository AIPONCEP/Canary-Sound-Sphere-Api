package com.api.canarysoundsphereapi.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.api.canarysoundsphereapi.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	/*
	 * Colección de roles (autoridades) asignados al usuario:
	 * GrantedAuthority es una interfaz que representa una autoridad o un rol
	 * concedido a un usuario. Spring Security utiliza objetos GrantedAuthority para
	 * realizar controles de acceso basados en roles.
	 */
	private Collection<? extends GrantedAuthority> authorities;

	// Mapeo de los roles del usuario a objetos GrantedAuthority
	public UserDetailsImpl(String id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	// Método estático para construir UserDetailsImpl a partir de un objeto User
	public static UserDetailsImpl build(User user) {
		// Mapeo de los roles del usuario a objetos GrantedAuthority
		List<GrantedAuthority> authorities = user.getRoles().stream() // .stream(): Convierte la colección de roles en
																		// un flujo (stream), que permite operaciones de
																		// transformación y filtrado.
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
		// Creación de un nuevo UserDetailsImpl con los detalles del usuario y sus roles
		return new UserDetailsImpl(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				authorities);
	}

	// Métodos de la interfaz UserDetails

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // La cuenta nunca expira
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // La cuenta nunca se bloquea
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // Las credenciales nunca expiran
	}

	@Override
	public boolean isEnabled() {
		return true; // La cuenta siempre está habilitada
	}

	// Comparación de objetos UserDetailsImpl por su identificador único (id)
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}

/*
 * 
 * Esta clase es esencial para proporcionar detalles del usuario al sistema de
 * seguridad de Spring. Contiene información como nombre de usuario, contraseña
 * (anotada con @JsonIgnore para ocultarla en la serialización/deserialización),
 * roles asignados al usuario y métodos para determinar si la cuenta está
 * activa, las credenciales están vigentes, etc. La implementación de estos
 * métodos permite a Spring Security realizar comprobaciones de autenticación y
 * autorización. Además, el método build facilita la creación de instancias de
 * UserDetailsImpl a partir de objetos User.
 */