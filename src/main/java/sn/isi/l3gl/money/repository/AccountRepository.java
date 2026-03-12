package sn.isi.l3gl.money.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.isi.l3gl.money.model.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByOwnerAndPhoneNumber(String owner, String phoneNumber);
}
