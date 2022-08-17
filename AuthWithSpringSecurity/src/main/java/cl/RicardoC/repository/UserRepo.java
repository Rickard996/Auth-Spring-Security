package cl.RicardoC.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.RicardoC.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long>{

	User findByUsername(String username);
	
}
