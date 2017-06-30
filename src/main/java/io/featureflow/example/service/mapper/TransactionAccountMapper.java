package io.featureflow.example.service.mapper;

import io.featureflow.example.domain.*;
import io.featureflow.example.service.dto.TransactionAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TransactionAccount and its DTO TransactionAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionAccountMapper extends EntityMapper <TransactionAccountDTO, TransactionAccount> {
    
    
    default TransactionAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionAccount transactionAccount = new TransactionAccount();
        transactionAccount.setId(id);
        return transactionAccount;
    }
}
