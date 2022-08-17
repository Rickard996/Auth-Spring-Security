package cl.RicardoC.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.RicardoC.models.Role;

@Repository
public interface RoleRepo extends CrudRepository<Role, Long>{
	
    List<Role> findAll();
    
    List<Role> findByName(String name);
	
}
