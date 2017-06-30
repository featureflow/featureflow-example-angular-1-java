package io.featureflow.example.service.mapper;

import io.featureflow.example.domain.*;
import io.featureflow.example.service.dto.TransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Transaction and its DTO TransactionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionMapper extends EntityMapper <TransactionDTO, Transaction> {
    
    
    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
