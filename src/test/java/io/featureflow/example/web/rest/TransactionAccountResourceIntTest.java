package io.featureflow.example.web.rest;

import io.featureflow.example.FeatureflowExampleAngularOneJavaApp;

import io.featureflow.example.domain.TransactionAccount;
import io.featureflow.example.repository.TransactionAccountRepository;
import io.featureflow.example.service.TransactionAccountService;
import io.featureflow.example.service.dto.TransactionAccountDTO;
import io.featureflow.example.service.mapper.TransactionAccountMapper;
import io.featureflow.example.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.featureflow.example.domain.enumeration.AccountType;
/**
 * Test class for the TransactionAccountResource REST controller.
 *
 * @see TransactionAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeatureflowExampleAngularOneJavaApp.class)
public class TransactionAccountResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_BALANCE = 1L;
    private static final Long UPDATED_BALANCE = 2L;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final AccountType DEFAULT_ACCOUNT_TYPE = AccountType.SAVINGS;
    private static final AccountType UPDATED_ACCOUNT_TYPE = AccountType.CREDIT;

    @Autowired
    private TransactionAccountRepository transactionAccountRepository;

    @Autowired
    private TransactionAccountMapper transactionAccountMapper;

    @Autowired
    private TransactionAccountService transactionAccountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransactionAccountMockMvc;

    private TransactionAccount transactionAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransactionAccountResource transactionAccountResource = new TransactionAccountResource(transactionAccountService);
        this.restTransactionAccountMockMvc = MockMvcBuilders.standaloneSetup(transactionAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionAccount createEntity(EntityManager em) {
        TransactionAccount transactionAccount = new TransactionAccount()
            .name(DEFAULT_NAME)
            .balance(DEFAULT_BALANCE)
            .type(DEFAULT_TYPE)
            .accountType(DEFAULT_ACCOUNT_TYPE);
        return transactionAccount;
    }

    @Before
    public void initTest() {
        transactionAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionAccount() throws Exception {
        int databaseSizeBeforeCreate = transactionAccountRepository.findAll().size();

        // Create the TransactionAccount
        TransactionAccountDTO transactionAccountDTO = transactionAccountMapper.toDto(transactionAccount);
        restTransactionAccountMockMvc.perform(post("/api/transaction-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionAccount in the database
        List<TransactionAccount> transactionAccountList = transactionAccountRepository.findAll();
        assertThat(transactionAccountList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionAccount testTransactionAccount = transactionAccountList.get(transactionAccountList.size() - 1);
        assertThat(testTransactionAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTransactionAccount.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testTransactionAccount.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTransactionAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void createTransactionAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionAccountRepository.findAll().size();

        // Create the TransactionAccount with an existing ID
        transactionAccount.setId(1L);
        TransactionAccountDTO transactionAccountDTO = transactionAccountMapper.toDto(transactionAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionAccountMockMvc.perform(post("/api/transaction-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TransactionAccount> transactionAccountList = transactionAccountRepository.findAll();
        assertThat(transactionAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTransactionAccounts() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList
        restTransactionAccountMockMvc.perform(get("/api/transaction-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getTransactionAccount() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get the transactionAccount
        restTransactionAccountMockMvc.perform(get("/api/transaction-accounts/{id}", transactionAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionAccount() throws Exception {
        // Get the transactionAccount
        restTransactionAccountMockMvc.perform(get("/api/transaction-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionAccount() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);
        int databaseSizeBeforeUpdate = transactionAccountRepository.findAll().size();

        // Update the transactionAccount
        TransactionAccount updatedTransactionAccount = transactionAccountRepository.findOne(transactionAccount.getId());
        updatedTransactionAccount
            .name(UPDATED_NAME)
            .balance(UPDATED_BALANCE)
            .type(UPDATED_TYPE)
            .accountType(UPDATED_ACCOUNT_TYPE);
        TransactionAccountDTO transactionAccountDTO = transactionAccountMapper.toDto(updatedTransactionAccount);

        restTransactionAccountMockMvc.perform(put("/api/transaction-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionAccount in the database
        List<TransactionAccount> transactionAccountList = transactionAccountRepository.findAll();
        assertThat(transactionAccountList).hasSize(databaseSizeBeforeUpdate);
        TransactionAccount testTransactionAccount = transactionAccountList.get(transactionAccountList.size() - 1);
        assertThat(testTransactionAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTransactionAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testTransactionAccount.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTransactionAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionAccount() throws Exception {
        int databaseSizeBeforeUpdate = transactionAccountRepository.findAll().size();

        // Create the TransactionAccount
        TransactionAccountDTO transactionAccountDTO = transactionAccountMapper.toDto(transactionAccount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransactionAccountMockMvc.perform(put("/api/transaction-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionAccount in the database
        List<TransactionAccount> transactionAccountList = transactionAccountRepository.findAll();
        assertThat(transactionAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransactionAccount() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);
        int databaseSizeBeforeDelete = transactionAccountRepository.findAll().size();

        // Get the transactionAccount
        restTransactionAccountMockMvc.perform(delete("/api/transaction-accounts/{id}", transactionAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TransactionAccount> transactionAccountList = transactionAccountRepository.findAll();
        assertThat(transactionAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionAccount.class);
        TransactionAccount transactionAccount1 = new TransactionAccount();
        transactionAccount1.setId(1L);
        TransactionAccount transactionAccount2 = new TransactionAccount();
        transactionAccount2.setId(transactionAccount1.getId());
        assertThat(transactionAccount1).isEqualTo(transactionAccount2);
        transactionAccount2.setId(2L);
        assertThat(transactionAccount1).isNotEqualTo(transactionAccount2);
        transactionAccount1.setId(null);
        assertThat(transactionAccount1).isNotEqualTo(transactionAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionAccountDTO.class);
        TransactionAccountDTO transactionAccountDTO1 = new TransactionAccountDTO();
        transactionAccountDTO1.setId(1L);
        TransactionAccountDTO transactionAccountDTO2 = new TransactionAccountDTO();
        assertThat(transactionAccountDTO1).isNotEqualTo(transactionAccountDTO2);
        transactionAccountDTO2.setId(transactionAccountDTO1.getId());
        assertThat(transactionAccountDTO1).isEqualTo(transactionAccountDTO2);
        transactionAccountDTO2.setId(2L);
        assertThat(transactionAccountDTO1).isNotEqualTo(transactionAccountDTO2);
        transactionAccountDTO1.setId(null);
        assertThat(transactionAccountDTO1).isNotEqualTo(transactionAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionAccountMapper.fromId(null)).isNull();
    }
}
