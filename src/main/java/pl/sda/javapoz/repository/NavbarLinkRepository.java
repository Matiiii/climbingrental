package pl.sda.javapoz.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.javapoz.model.NavbarLink;

public interface NavbarLinkRepository extends CrudRepository<NavbarLink,Long>{

    public NavbarLink findByName(String name);

}
