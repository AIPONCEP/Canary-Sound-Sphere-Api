package com.api.canarysoundsphereapi.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.canarysoundsphereapi.repositories.UserRepository;
import com.api.canarysoundsphereapi.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	// Método para cargar los detalles de usuario mediante su nombre de usuario
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Buscar al usuario en el repositorio por su nombre de usuario
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		// Construir UserDetailsImpl a partir del usuario encontrado
		return UserDetailsImpl.build(user);
	}
}