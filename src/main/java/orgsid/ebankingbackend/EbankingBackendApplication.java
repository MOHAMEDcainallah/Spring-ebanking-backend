package orgsid.ebankingbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import orgsid.ebankingbackend.dtos.BankAccountDTO;
import orgsid.ebankingbackend.dtos.CurrentBankAccountDTO;
import orgsid.ebankingbackend.dtos.CustomerDTO;
import orgsid.ebankingbackend.dtos.SavingBankAccountDTO;
import orgsid.ebankingbackend.entities.*;
import orgsid.ebankingbackend.enums.AccountStatus;
import orgsid.ebankingbackend.enums.OperationType;
import orgsid.ebankingbackend.exceptions.BalanceNotSufficientException;
import orgsid.ebankingbackend.exceptions.BankAccountNotFoundException;
import orgsid.ebankingbackend.exceptions.CustomerNotFoundException;
import orgsid.ebankingbackend.repositories.AccountOperationRepository;
import orgsid.ebankingbackend.repositories.BankAccountRepository;
import orgsid.ebankingbackend.repositories.CustomerRepository;
import orgsid.ebankingbackend.services.BankAccountService;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}
//    @Bean
//	CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository){
//		return args ->{
//			BankAccount bankAccount1=
//					bankAccountRepository.findById("0c0ec18d-9bd3-48dc-a651-5f8dc702f429").orElse(null);
//			System.out.println("**************************");
//			if (bankAccount1!=null) {
//				System.out.println(bankAccount1.getId());
//				System.out.println(bankAccount1.getBalance());
//				System.out.println(bankAccount1.getStatus());
//				System.out.println(bankAccount1.getCreatedAt());
//				System.out.println(bankAccount1.getCustomer().getName());
//				System.out.println(bankAccount1.getClass().getSimpleName());
//				if (bankAccount1 instanceof CurrentAccount) {
//					System.out.println("Over Draft=>" + ((CurrentAccount) bankAccount1).getOverDraft());
//				} else if (bankAccount1 instanceof SavingAccount) {
//					System.out.println("Interest Rate=>" + ((SavingAccount) bankAccount1).getInterestRate());
//				}
//				bankAccount1.getAccountOperations().forEach(accountOperation -> {
//					System.out.println(accountOperation.getType() +
//							"\t" + accountOperation.getOperationDate() +
//							"\t" + accountOperation.getAmount());
//
//				});
//			}
//		};
//	}

	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
		return args ->{
			Stream.of("Simo","Ziko","Mjid").forEach(name->{
				CustomerDTO customer=new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				bankAccountService.saveCustomer(customer);
			});
			bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5, customer.getId());

				} catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
			List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
			for (BankAccountDTO bankAccount:bankAccounts){
				for (int i = 0; i < 10; i++) {
					String accountId;
					if (bankAccount instanceof SavingBankAccountDTO){
						accountId=((SavingBankAccountDTO) bankAccount).getId();
					} else {
						accountId=((CurrentBankAccountDTO) bankAccount).getId();
					}
					bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
					bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");

				}

			}
		};
	}

    //@Bean
	 CommandLineRunner start(CustomerRepository customerRepository
			, AccountOperationRepository accountOperationRepository
			, BankAccountRepository bankAccountRepository){
		return args -> {
			Stream.of("HASSAN","IKRAM","ACHRAF").forEach(name->{
				Customer customer=new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerRepository.save(customer);
			});
			//nous avons creer des comptes courrants pour les utilisateurs
			customerRepository.findAll().forEach(customer ->{
				CurrentAccount currentAccount=new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()+90000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(customer);
				currentAccount.setOverDraft(9000);
				bankAccountRepository.save(currentAccount);

               // nous avons creer des comptes d epargne pour les utilisateurs
				SavingAccount savingAccount=new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()+90000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(customer);
				savingAccount.setInterestRate(5.5);
				bankAccountRepository.save(savingAccount);

			});
           bankAccountRepository.findAll().forEach(bankAccount -> {
			   for (int i = 0; i < 5; i++) {
				   AccountOperation accountOperation=new AccountOperation();
				   accountOperation.setOperationDate(new Date());
				   accountOperation.setAmount(Math.random()*12000);
				   accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
				   accountOperation.setBankAccount(bankAccount);
				   accountOperationRepository.save(accountOperation);
			   }

		   });
		};
	}

}
