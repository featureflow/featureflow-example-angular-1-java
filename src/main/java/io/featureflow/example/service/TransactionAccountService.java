package io.featureflow.example.service;

import io.featureflow.example.service.dto.TransactionAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TransactionAccount.
 */
public interface TransactionAccountService {

    /**
     * Save a transactionAccount.
     *
     * @param transactionAccountDTO the entity to save
     * @return the persisted entity
     */
    TransactionAccountDTO save(TransactionAccountDTO transactionAccountDTO);

    /**
     *  Get all the transactionAccounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TransactionAccountDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" transactionAccount.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TransactionAccountDTO findOne(Long id);

    /**
     *  Delete the "id" transactionAccount.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
