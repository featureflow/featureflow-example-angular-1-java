package io.featureflow.example.service.mapper;

import io.featureflow.example.domain.*;
import io.featureflow.example.service.dto.ClientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Client and its DTO ClientDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientMapper extends EntityMapper <ClientDTO, Client> {
    
    
    default Client fromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
