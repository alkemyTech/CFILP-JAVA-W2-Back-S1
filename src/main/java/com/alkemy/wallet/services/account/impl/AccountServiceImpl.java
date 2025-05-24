package com.alkemy.wallet.services.account.impl;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.mapper.AccountMapper;
import com.alkemy.wallet.models.account.Account;
import com.alkemy.wallet.models.account.AccountType;
import com.alkemy.wallet.models.financer_product.FinancerProduct;
import com.alkemy.wallet.models.transaction.Transaction;
import com.alkemy.wallet.models.user.User;
import com.alkemy.wallet.repository.account.AccountRepository;
import com.alkemy.wallet.repository.account.AccountTypeRepository;
import com.alkemy.wallet.repository.financer_product.FinancerProductRepository;
import com.alkemy.wallet.repository.transaction.TransactionRepository;
import com.alkemy.wallet.repository.user.UserRepository;
import com.alkemy.wallet.services.account.AccountService;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private FinancerProductRepository financerProductRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional
    public AccountDTO getAccountById(int id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id " + id));

        return accountMapper.toDTO(account);
    }

    @Override
    public Account editAccount(int id, Account newAccountData) {
        return accountRepository.findById(id).map(account -> {
            if (newAccountData.getCbu() != null) {
                account.setCbu(newAccountData.getCbu());
            }
            if (newAccountData.getAlias() != null) {
                account.setAlias(newAccountData.getAlias());
            }
            if (newAccountData.getCurrency() != null) {
                account.setCurrency(newAccountData.getCurrency());
            }
            if (newAccountData.getAccountType() != null) {
                account.setAccountType(newAccountData.getAccountType());
            }
            return accountRepository.save(account);
        }).orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id " + id));
    }

    @Override
    public List<AccountDTO> getAllAccountByUserId(int userId) {
        try {
            List<Account> accounts = accountRepository.findByUserId(userId);
            if (accounts.isEmpty()) {
                throw new RuntimeException("No se encontraron cuentas para el usuario con ID " + userId);
            }
            List<AccountDTO> accountDTOs = accountMapper.toDTOList(accounts);
       
            return accountDTOs;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteAccountById(int id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Cuenta no encontrada con id " + id);
        }
        accountRepository.deleteById(id);
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        if (accounts.isEmpty()) {
            throw new RuntimeException("No se encontraron cuentas");
        }
        return accounts;

    }

    @Override
    public Account createAccount(AccountDTO accountDTO) {

        int userId = accountDTO.getUserId();
        // Verificar si el usuario existe por el ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID " + userId));

        //Obtenemos el nombre del tipo de cuenta desde el DTO
        String accountTypeName = accountDTO.getAccountType();

        // Verificar si el tipo de cuenta existe por el nombre
        AccountType accountType = accountTypeRepository.findByAccountType(accountTypeName);
        if (accountType == null) {
            throw new RuntimeException("Tipo de cuenta no encontrada con el nombre " + accountTypeName);
        }
        
        //Mapea el DTO a la entidad
        Account account = accountMapper.toEntity(accountDTO);
        account.setUser(user); // Asignar el usuario a la cuenta
        account.setAccountType(accountType); // Asignar el tipo de cuenta a la cuenta
        account.setBalance(0);
        return accountRepository.save(account);
    }

    @Override
    public List<Transaction> getAccountTransactions(int accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
        if (transactions.isEmpty()) {
            throw new RuntimeException("No se encontraron transacciones para la cuenta con ID " + accountId);
        }
        return transactions;
    }

    @Override
    public List<FinancerProduct> getAccountFinancerProducts(int accountId) {
        List<FinancerProduct> products = financerProductRepository.findByAccountId(accountId);
        if (products.isEmpty()) {
            throw new RuntimeException("No se encontraron productos financieros para la cuenta con ID " + accountId);
        }
        return products;
    }

}
