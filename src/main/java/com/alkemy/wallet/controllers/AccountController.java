package com.alkemy.wallet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.alkemy.wallet.services.account.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.models.account.Account;
import com.alkemy.wallet.models.financer_product.FinancerProduct;
import com.alkemy.wallet.models.transaction.Transaction;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Cuentas", description = "Operaciones relacionadas con cuentas")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Operation(summary = "Crear nueva cuenta")
    @ApiResponse(responseCode = "201", description = "Cuenta creada")
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountDTO account) {
        Account created = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Listar todas las cuentas")
    @ApiResponse(responseCode = "200", description = "Lista de cuentas")
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @Operation(summary = "Obtener cuenta por ID")
    @ApiResponse(responseCode = "200", description = "Cuenta encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable int id) {
        AccountDTO account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @Operation(summary = "Listar cuentas por usuario")
    @ApiResponse(responseCode = "200", description = "Lista de cuentas por usuario")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDTO>> getAccountsByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(accountService.getAllAccountByUserId(userId));
    }

    @Operation(summary = "Actualizar cuenta por ID")
    @ApiResponse(responseCode = "200", description = "Cuenta actualizada")
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable int id, @RequestBody Account newAccountData) {
        Account updatedAccount = accountService.editAccount(id, newAccountData);
        return ResponseEntity.ok(updatedAccount);
    }

    @Operation(summary = "Eliminar cuenta por ID")
    @ApiResponse(responseCode = "204", description = "Cuenta eliminada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener transacciones de una cuenta")
    @ApiResponse(responseCode = "200", description = "Lista de transacciones")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable int accountId) {
        return ResponseEntity.ok(accountService.getAccountTransactions(accountId));
    }

    @Operation(summary = "Obtener productos financieros de una cuenta")
    @ApiResponse(responseCode = "200", description = "Lista de productos financieros")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    @GetMapping("/{accountId}/financer-products")
    public ResponseEntity<List<FinancerProduct>> getFinancerProducts(@PathVariable int accountId) {
        return ResponseEntity.ok(accountService.getAccountFinancerProducts(accountId));
    }

}
