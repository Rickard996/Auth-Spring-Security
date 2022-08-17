package cl.RicardoC.configSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	//Inyectando UserDetailsService
	private UserDetailsService userDetailsService;
	
    public WebSecurityConfig(UserDetailsService userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}

	// Agregar Bcyrpt bean
	
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	protected void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/registration").permitAll()
		
		.antMatchers("/admin/**").access("hasRole('ADMIN')")   //NEW. 
		//Cualquier URL que empiece con "/admin" requiere que el usuario tenga un ROLE_ADMIN
		.anyRequest().authenticated().and()
		.formLogin().loginPage("/login").permitAll().and()
		.logout().permitAll();

	}
	
	//configureGlobal(AuthenticationManagerBuilder): Este método configura Spring Security 
	//para usar nuestra implementación personalizada de UserDetailsService con Bcrypt.
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(this.userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
}
