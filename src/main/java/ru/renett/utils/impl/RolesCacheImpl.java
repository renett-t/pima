package ru.renett.utils.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.renett.exceptions.ServiceException;
import ru.renett.models.Role;
import ru.renett.repository.RolesRepository;
import ru.renett.utils.RolesCache;

import java.util.*;

@Component
public class RolesCacheImpl implements RolesCache {
    private final RolesRepository rolesRepository;

    private final List<Role> roles = new ArrayList<>();

    @Autowired
    public RolesCacheImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
        roles.addAll(rolesRepository.findAll());
    }

    @Override
    public Role getRoleByName(String name) {
        for (Role item : roles) {
            if (item.getName().name().toLowerCase(Locale.ROOT).equals(name.toLowerCase())) {
                return item;
            }
        }
        throw new ServiceException("Roles not initialized or not found needed;");
    }

    @Override
    public Role getRoleByName(Role.ROLE role) {
        for (Role item : roles) {
            if (item.getName().equals(role)) {
                return item;
            }
        }
        throw new ServiceException("Roles not initialized or not found needed;");
    }

    @Override
    public Role getRoleById(Long id) {
        for (Role item : roles) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        throw new ServiceException("Roles not initialized or not found needed;");
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }
}
