package cl.RicardoC.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cl.RicardoC.models.User;
import cl.RicardoC.repository.RoleRepo;
import cl.RicardoC.repository.UserRepo;

@Service
public class UserService {

	private RoleRepo roleRepo;
	private UserRepo userRepo;
	private BCryptPasswordEncoder bcryptPassEncoder;
	
	//podemos inyectarlo con un Autowired O con un Constructor. Ambas validas
	
	public UserService(RoleRepo roleRepo, UserRepo userRepo, BCryptPasswordEncoder bcryptPassEncoder) {
		super();
		this.roleRepo = roleRepo;
		this.userRepo = userRepo;
		this.bcryptPassEncoder = bcryptPassEncoder;
	}
	
	
	public void saveWithUserRole(User user) {
		
		user.setPassword(this.bcryptPassEncoder.encode(user.getPassword()));
		user.setRoles(this.roleRepo.findByName("ROLE_USER"));
		this.userRepo.save(user);
	}
	
	
    public void saveUserWithAdminRole(User user) {
        user.setPassword(this.bcryptPassEncoder.encode(user.getPassword()));
        user.setRoles( this.roleRepo.findByName("ROLE_ADMIN"));
        this.userRepo.save(user);
    }    
	
	public User findByUsername(String username) {
		return this.userRepo.findByUsername(username);
	}
	
	
	
	
	
	
	
}
