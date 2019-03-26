package com.developer.UInvFISI.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().usersByUsernameQuery("select email, password, '1' as enabled from tbl_usuarios where email= ? and habilitado = true")
			.authoritiesByUsernameQuery("select u.email, r.nombre from tbl_usuarios u inner join tbl_usuario_roles ur on(u.usuario_id=ur.usuario_id) inner join tbl_roles r on(ur.rol_id=r.rol_id) where u.email=?")
			.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
		
		

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
					.antMatchers("/").permitAll()
					.antMatchers("/register").permitAll()
					.antMatchers("/login").permitAll()
					.antMatchers("/inicio").hasAnyAuthority("SUPER_USER", "ADMIN_USER")
					.anyRequest().authenticated()
					.and()
					.csrf().disable()
					.formLogin()
					.loginPage("/login")
					.failureUrl("/login?error=true")
					.defaultSuccessUrl("/inicio")
					.usernameParameter("email")
					.passwordParameter("password")
					.and()
					.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/login")
					.and()
					.exceptionHandling().accessDeniedPage("/404");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/assets/**");
	}

}
