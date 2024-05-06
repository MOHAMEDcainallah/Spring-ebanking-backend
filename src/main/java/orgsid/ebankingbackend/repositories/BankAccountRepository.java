package orgsid.ebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import orgsid.ebankingbackend.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
