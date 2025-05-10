package com.alkemy.wallet.services.impl;

import com.alkemy.wallet.models.Account;
import com.alkemy.wallet.models.AccountType;
import com.alkemy.wallet.models.User;
import com.alkemy.wallet.repository.AccountRepository;
import com.alkemy.wallet.repository.AccountTypeRepository;
import com.alkemy.wallet.repository.UserRepository;
import com.alkemy.wallet.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AccountServiceImpl implements AccountService {

    
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account getAccountById(int id) {
        return accountRepository.findById(id).orElse(null);
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
    public void  deleteAccountById(int id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Cuenta no encontrada con id " + id);
        }
        accountRepository.deleteById(id);
    }


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Override
    public Account createAccount(Account account) {
        int userId = account.getUser().getId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID " + userId));

        int accountTypeId = account.getAccountType().getId();
        AccountType accountType = accountTypeRepository.findById(accountTypeId)
            .orElseThrow(() -> new RuntimeException("Tipo de cuenta no encontrada con ID " + accountTypeId));
        account.setUser(user);
        account.setAccountType(accountType);
        account.setBalance(0);
        return accountRepository.save(account);
    }
}
