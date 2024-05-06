package orgsid.ebankingbackend.services;

import orgsid.ebankingbackend.dtos.*;
import orgsid.ebankingbackend.entities.BankAccount;
import orgsid.ebankingbackend.entities.CurrentAccount;
import orgsid.ebankingbackend.entities.Customer;
import orgsid.ebankingbackend.entities.SavingAccount;
import orgsid.ebankingbackend.exceptions.BalanceNotSufficientException;
import orgsid.ebankingbackend.exceptions.BankAccountNotFoundException;
import orgsid.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId,double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    // c est une methode qui affiche tous les customers pour l ajouter a l interface @Override
    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountsId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
}


