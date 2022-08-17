package cl.RicardoC.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.RicardoC.models.Role;
import cl.RicardoC.models.User;
import cl.RicardoC.repository.UserRepo;

//Nuestra clase será utilizada para cargar el usuario de acuerdo al username que es enviado desde el formulario.

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

	private UserRepo userRepo;
	
	public UserDetailServiceImplementation(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}

	//Encuentra al usuario por su username. Si encuentra al usuario, lo devuelve con las autorizaciones correspondientes. 
	//Si el username no existe, el método lanza un error.
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = this.userRepo.findByUsername(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
	}
	
	//Devuelve una lista de las autorizaciones/permisos para un usuario específico. 
	//Por ejemplo, nuestros clientes pueden ser 'user', 'admin', o ambos. 
	//Para que Spring Security implemente la autorización, debemos obtener el nombre de los posibles 
	//roles del usuario actual de nuestra base de datos y crear un nuevo objeto SimpleGrantedAuthority con esos roles.
	
	public List<GrantedAuthority> getAuthorities(User user){
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(Role role: user.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
			authorities.add(grantedAuthority);
		}
		
		return authorities;
	}
	
	
	
	
	
	
	

}
