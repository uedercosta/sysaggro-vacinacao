package br.com.sysagro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sysagro.models.Perfil;
import br.com.sysagro.models.Usuario;
import br.com.sysagro.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{

	@Autowired
	private UsuarioRepository repository;
	
	@Transactional(readOnly = true)
	public List<Usuario> findByNome(String value) {
		return value.isEmpty() ? repository.findAll(Sort.by("login").ascending())
				               : repository.findByNome(value.toUpperCase());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByLogin(username.toUpperCase());
		return new User(
				usuario.getLogin(), 
				usuario.getSenha(), 
				usuario.isAtivo(), 
				usuario.isAtivo(),
				usuario.isAtivo(), 
				usuario.isAtivo(), 
				AuthorityUtils.createAuthorityList(getAuthorities(usuario.getPerfis()))
			);
	}

	private String[] getAuthorities(List<Perfil> perfis) {
		String[] authorities = new String[perfis.size()];
		for (int i = 0; i < perfis.size(); i++) {
			authorities[i] = perfis.get(i).getDescricao();
		}
		return authorities;
	}


}
