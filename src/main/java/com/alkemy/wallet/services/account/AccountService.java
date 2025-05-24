package com.alkemy.wallet.services.account;


import java.util.List;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.models.account.Account;
import com.alkemy.wallet.models.financer_product.FinancerProduct;
import com.alkemy.wallet.models.transaction.Transaction;



public interface AccountService {

    public Account createAccount(AccountDTO account);

    public void deleteAccountById(int id);

    public Account editAccount(int id, Account newAccountData);

    public AccountDTO getAccountById(int id);

    public List<AccountDTO> getAllAccountByUserId(int userId);

    public List<Transaction> getAccountTransactions(int accountId);

    public List<Account> getAllAccounts();

    public List<FinancerProduct> getAccountFinancerProducts(int accountId);
}
