package sn.isi.l3gl.money.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.isi.l3gl.money.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByOperationType(Transaction.Type type);
}
