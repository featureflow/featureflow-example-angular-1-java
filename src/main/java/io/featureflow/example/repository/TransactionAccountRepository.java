package io.featureflow.example.repository;

import io.featureflow.example.domain.TransactionAccount;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TransactionAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionAccountRepository extends JpaRepository<TransactionAccount,Long> {
    
}
