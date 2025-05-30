package com.alkemy.wallet.controllers;

import java.util.List;

import com.alkemy.wallet.services.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alkemy.wallet.dto.UserDTO;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UserController {

    @Autowired
    private UserService userService;
    

    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers(); // Si no hay usuarios, se lanzará una excepción que será manejada por el GlobalExceptionHandler
        return ResponseEntity.ok(users); //si hay usuarios, se devuelve un 200 OK
    }


    @Operation(summary = "Obtener un usuario por ID")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        UserDTO user = userService.getUserById(id); // Si el usuario no existe, se lanzará una excepción que será manejada por el GlobalExceptionHandler
        return ResponseEntity.ok(user); //si el usuario existe, se devuelve un 200 OK
    }

    @Operation(summary = "Actualizar un usuario por ID")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserDTO userData) {
        UserDTO updated = userService.editUserById(id, userData); // Si el usuario no existe EntityNotFoundException manejada por el GlobalExceptionHandler
        return ResponseEntity.ok(updated); // Devuelve un 200 OK
    }

    @Operation(summary = "Eliminar un usuario por ID")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id); //Si el usuario no existe se lanzará EntityNotFoundException
        return ResponseEntity.noContent().build(); // Devuelve un 204 No Content
    }

}
