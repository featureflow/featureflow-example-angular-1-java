package io.featureflow.example.service.dto;


import java.io.Serializable;
import java.util.Objects;
import io.featureflow.example.domain.enumeration.AccountType;

/**
 * A DTO for the TransactionAccount entity.
 */
public class TransactionAccountDTO implements Serializable {

    private Long id;

    private String name;

    private Long balance;

    private String type;

    private AccountType accountType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionAccountDTO transactionAccountDTO = (TransactionAccountDTO) o;
        if(transactionAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionAccountDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", balance='" + getBalance() + "'" +
            ", type='" + getType() + "'" +
            ", accountType='" + getAccountType() + "'" +
            "}";
    }
}
