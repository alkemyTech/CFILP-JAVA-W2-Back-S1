package com.alkemy.wallet.services.user.impl;

import com.alkemy.wallet.models.user.Role;
import com.alkemy.wallet.repository.user.RoleRepository;
import com.alkemy.wallet.services.user.RoleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleById(int id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + id));
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByRoleName(name)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado: " + name));
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role deleteRoleById(int id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar. Rol no encontrado con id " + id);
        }
        Role role = roleRepository.findById(id).orElseThrow();
        roleRepository.deleteById(id);
        return role;
    }

    @Override
    public Role editRole(int id, Role role) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se puede editar. Rol no encontrado con id " + id));
        existingRole.setRoleName(role.getRoleName());
        return roleRepository.save(existingRole);
    }

    @Override
    public List<Role> getAllRoles() {
        return   roleRepository.findAll();
    }
}
