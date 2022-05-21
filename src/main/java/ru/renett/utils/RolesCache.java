package ru.renett.utils;

import ru.renett.exceptions.ServiceException;
import ru.renett.models.Role;

import java.util.List;

public interface RolesCache {

    Role getRoleByName(String name) throws ServiceException;
    Role getRoleByName(Role.ROLE role) throws ServiceException;
    Role getRoleById(Long id) throws ServiceException;
    List<Role> getRoles() throws ServiceException;
}
