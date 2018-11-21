package pl.sda.javapoz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.javapoz.model.NavbarLink;

@Repository
public interface NavbarLinkRepository extends CrudRepository<NavbarLink, Long> {
    public NavbarLink findByName(String name);
}
