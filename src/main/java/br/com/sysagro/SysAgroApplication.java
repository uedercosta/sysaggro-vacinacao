package br.com.sysagro;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.sysagro.models.Perfil;
import br.com.sysagro.models.Usuario;
import br.com.sysagro.repositories.PerfilRepository;
import br.com.sysagro.repositories.UsuarioRepository;

@SpringBootApplication
public class SysAgroApplication implements CommandLineRunner{
	
	@Autowired
	private PerfilRepository perfilRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	

	public static void main(String[] args) {
		SpringApplication.run(SysAgroApplication.class, args);
	}
	

	@Override
	public void run(String... args) throws Exception {		
		if (!perfilRepository.findByDescricao("ADMINISTRATOR").isPresent()) {
			Perfil perfil = new Perfil();
			perfil.setDescricao("ADMINISTRATOR");
			perfilRepository.save(perfil);
		}

		if (!perfilRepository.findByDescricao("USUARIOS DO SISTEMA").isPresent()) {
			Perfil perfil = new Perfil();
			perfil.setDescricao("USUARIOS DO SISTEMA");
			perfilRepository.save(perfil);
		}
		
		if (usuarioRepository.findByLogin("ADMINISTRATOR") == null) {
			Usuario usuario = new Usuario();
			usuario.setAtivo(true);
			usuario.setLogin("ADMINISTRATOR");
			usuario.setNome("ADMINISTRADOR DO SISTEMA");
			usuario.setSenha(encoder.encode("1234"));
			Optional<Perfil> perfil = perfilRepository.findByDescricao("ADMINISTRATOR");
			usuario.setPerfis(Arrays.asList(perfil.get()));
			usuarioRepository.save(usuario);
		}		
		
		if (usuarioRepository.findByLogin("USER") == null) {
			Usuario usuario = new Usuario();
			usuario.setAtivo(true);
			usuario.setLogin("USER");
			usuario.setNome("USUARIO DO SISTEMA");
			usuario.setSenha(encoder.encode("123456"));
			Optional<Perfil> perfil = perfilRepository.findByDescricao("USUARIOS DO SISTEMA");
			usuario.setPerfis(Arrays.asList(perfil.get()));
			usuarioRepository.save(usuario);
		}			
	}

}
