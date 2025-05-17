package com.alkemy.wallet.controllers;

import java.util.List;

import com.alkemy.wallet.services.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alkemy.wallet.models.user.User;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UserController {

    @Autowired
    private UserService userService;


    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers(); // Si no hay usuarios, se lanzará una excepción que será manejada por el GlobalExceptionHandler
        return ResponseEntity.ok(users); //si hay usuarios, se devuelve un 200 OK
    }


    @Operation(summary = "Obtener un usuario por ID")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id); // Si el usuario no existe, se lanzará una excepción que será manejada por el GlobalExceptionHandler
        return ResponseEntity.ok(user); //si el usuario existe, se devuelve un 200 OK
    }

@Operation(summary = "Crear nuevo usuario")
    @ApiResponse(responseCode = "201", description = "Usuario creado")
    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user); //Si hay algún error de validación, lo manejará el GlobalExceptionHandler
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); // Si todo va bien devuelve un 201 Created
    }

    @Operation(summary = "Actualizar un usuario por ID")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userData) {
        User updated = userService.editUserById(id, userData); // Si el usuario no existe EntityNotFoundException manejada por el GlobalExceptionHandler
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
