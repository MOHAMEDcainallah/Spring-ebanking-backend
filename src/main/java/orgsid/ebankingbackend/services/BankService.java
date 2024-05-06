package orgsid.ebankingbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orgsid.ebankingbackend.entities.BankAccount;
import orgsid.ebankingbackend.entities.CurrentAccount;
import orgsid.ebankingbackend.entities.SavingAccount;
import orgsid.ebankingbackend.repositories.BankAccountRepository;

//@Service
//@Transactional
//public class BankService {
//    @Autowired
//    private BankAccountRepository bankAccountRepository;
//    public void consulter(){  //cette methode et transactionnel
//        BankAccount bankAccount1=
//                bankAccountRepository.findById("0c0ec18d-9bd3-48dc-a651-5f8dc702f429").orElse(null);
//        System.out.println("**************************");
//        if (bankAccount1!=null) {
//            System.out.println(bankAccount1.getId());
//            System.out.println(bankAccount1.getBalance());
//            System.out.println(bankAccount1.getStatus());
//            System.out.println(bankAccount1.getCreatedAt());
//            System.out.println(bankAccount1.getCustomer().getName());
//            System.out.println(bankAccount1.getClass().getSimpleName());
//            if (bankAccount1 instanceof CurrentAccount) {
//                System.out.println("Over Draft=>" + ((CurrentAccount) bankAccount1).getOverDraft());
//            } else if (bankAccount1 instanceof SavingAccount) {
//                System.out.println("Interest Rate=>" + ((SavingAccount) bankAccount1).getInterestRate());
//            }
//            bankAccount1.getAccountOperations().forEach(accountOperation -> {
//                System.out.println(accountOperation.getType() +
//                        "\t" + accountOperation.getOperationDate() +
//                        "\t" + accountOperation.getAmount());
//
//            });
//        }
//    }
//}
        //c le code que nous peut l ajputer avec une methode consulter a la place du code dans commande line runner