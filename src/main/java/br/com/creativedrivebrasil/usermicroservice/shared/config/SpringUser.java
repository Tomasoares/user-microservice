package br.com.creativedrivebrasil.usermicroservice.shared.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.creativedrivebrasil.usermicroservice.shared.UserDTO;

public class SpringUser implements UserDetails {

	private static final long serialVersionUID = 1513169320324251786L;
	
	private UserDTO user = new UserDTO();
	
	public SpringUser(final UserDTO user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String roleName = this.user.getType().name();
		SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + roleName);
		return Arrays.asList(role);
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
