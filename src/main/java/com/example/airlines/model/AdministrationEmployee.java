package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
public class AdministrationEmployee extends Employee {
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE})
    @Getter private Account account;

    public void setAccount(Account account) throws WrongAccountOwnerType {
        if(this.account == account) {
            return;
        }
        else if(account != null) {
            this.account = account;
            account.setAdministrationEmployeeOwner(this);
        }
        else {
            Account tmpAccount = this.account;
            this.account = null;
            tmpAccount.resetOwner();
        }
    }
}