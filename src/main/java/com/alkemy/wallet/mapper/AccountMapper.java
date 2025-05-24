package com.alkemy.wallet.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.models.account.Account;
import com.alkemy.wallet.models.account.AccountType;
import com.alkemy.wallet.models.user.User;
import com.alkemy.wallet.repository.account.AccountTypeRepository;
import com.alkemy.wallet.repository.user.UserRepository;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {

    @Autowired
    protected AccountTypeRepository accountTypeRepository;

    @Autowired
    protected UserRepository userRepository;

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "accountType.accountType", target = "accountType")
    @Mapping(source = "currency", target = "currency")
    public abstract AccountDTO toDTO(Account account);

    public abstract List<AccountDTO> toDTOList(List<Account> accounts);

    @Mapping(target = "accountType", expression = "java(accountTypeFromString(accountDTO.getAccountType()))")
    @Mapping(target = "user", expression = "java(userFromId(accountDTO.getUserId()))")
    public abstract Account toEntity(AccountDTO accountDTO);
   
    // Método auxiliar usando el repositorio
    protected AccountType accountTypeFromString(String name) {
        if (name == null) return null;
        return accountTypeRepository.findByAccountType(name);
    }

    protected User userFromId(int userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
