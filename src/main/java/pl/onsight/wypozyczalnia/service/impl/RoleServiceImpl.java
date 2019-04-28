package pl.onsight.wypozyczalnia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.onsight.wypozyczalnia.model.entity.UserRoleEntity;
import pl.onsight.wypozyczalnia.repository.UserRoleRepository;
import pl.onsight.wypozyczalnia.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {


    private UserRoleRepository userRoleRepository;

    @Autowired
    public RoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public void saveRole(UserRoleEntity role) {
        userRoleRepository.save(role);
    }
}
