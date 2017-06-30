package io.featureflow.example.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.featureflow.example.service.TransactionAccountService;
import io.featureflow.example.web.rest.util.HeaderUtil;
import io.featureflow.example.web.rest.util.PaginationUtil;
import io.featureflow.example.service.dto.TransactionAccountDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TransactionAccount.
 */
@RestController
@RequestMapping("/api")
public class TransactionAccountResource {

    private final Logger log = LoggerFactory.getLogger(TransactionAccountResource.class);

    private static final String ENTITY_NAME = "transactionAccount";

    private final TransactionAccountService transactionAccountService;

    public TransactionAccountResource(TransactionAccountService transactionAccountService) {
        this.transactionAccountService = transactionAccountService;
    }

    /**
     * POST  /transaction-accounts : Create a new transactionAccount.
     *
     * @param transactionAccountDTO the transactionAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transactionAccountDTO, or with status 400 (Bad Request) if the transactionAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transaction-accounts")
    @Timed
    public ResponseEntity<TransactionAccountDTO> createTransactionAccount(@RequestBody TransactionAccountDTO transactionAccountDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionAccount : {}", transactionAccountDTO);
        if (transactionAccountDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new transactionAccount cannot already have an ID")).body(null);
        }
        TransactionAccountDTO result = transactionAccountService.save(transactionAccountDTO);
        return ResponseEntity.created(new URI("/api/transaction-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transaction-accounts : Updates an existing transactionAccount.
     *
     * @param transactionAccountDTO the transactionAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transactionAccountDTO,
     * or with status 400 (Bad Request) if the transactionAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the transactionAccountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transaction-accounts")
    @Timed
    public ResponseEntity<TransactionAccountDTO> updateTransactionAccount(@RequestBody TransactionAccountDTO transactionAccountDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionAccount : {}", transactionAccountDTO);
        if (transactionAccountDTO.getId() == null) {
            return createTransactionAccount(transactionAccountDTO);
        }
        TransactionAccountDTO result = transactionAccountService.save(transactionAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transactionAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transaction-accounts : get all the transactionAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of transactionAccounts in body
     */
    @GetMapping("/transaction-accounts")
    @Timed
    public ResponseEntity<List<TransactionAccountDTO>> getAllTransactionAccounts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TransactionAccounts");
        Page<TransactionAccountDTO> page = transactionAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/transaction-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /transaction-accounts/:id : get the "id" transactionAccount.
     *
     * @param id the id of the transactionAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transactionAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/transaction-accounts/{id}")
    @Timed
    public ResponseEntity<TransactionAccountDTO> getTransactionAccount(@PathVariable Long id) {
        log.debug("REST request to get TransactionAccount : {}", id);
        TransactionAccountDTO transactionAccountDTO = transactionAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transactionAccountDTO));
    }

    /**
     * DELETE  /transaction-accounts/:id : delete the "id" transactionAccount.
     *
     * @param id the id of the transactionAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transaction-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransactionAccount(@PathVariable Long id) {
        log.debug("REST request to delete TransactionAccount : {}", id);
        transactionAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
