package com.developer.UInvFISI;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.developer.UInvFISI.entity.Usuario;
import com.developer.UInvFISI.security.services.UsuarioService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UInvFisiApplicationTests {

	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Before
	public void initDB() {
		{
			Usuario usuario = new Usuario("Alexis Manuel", "Gutierrez Fuentes", "alexisgutierrezf.1997@gmail.com", "alexisgf");
			usuarioService.saveAdminUser(usuario);
		}
		
	}
	
	@Test
	public void testUser() {
		
		Usuario superUsuario = usuarioService.findByEmail("alexisgutierrezf.1997@gmail.com");
		assertNotNull(superUsuario);
		
	}

}
