package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.domain.Role;
import ra.model.domain.RoleName;
import ra.repository.RoleRepository;
import ra.service.IRoleService;
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(()->new RuntimeException("Role Not Found"));
    }
}