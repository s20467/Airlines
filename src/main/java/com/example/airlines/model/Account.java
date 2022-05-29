package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter protected Integer id;

    @Getter @Setter private String password;

    @Getter @Setter private AccountOwnerType ownerType = AccountOwnerType.NONE;

    @OneToOne(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Client clientOwner;
    @OneToOne(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private AdministrationEmployee administrationEmployeeOwner;
    @OneToOne(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private PartnerCompany companyOwner;

    public Client getClientOwner() throws WrongAccountOwnerType {
        if(ownerType == AccountOwnerType.CLIENT)
            return clientOwner;
        else
            throw new WrongAccountOwnerType("Account is not owned by client");
    }

    public AdministrationEmployee getAdministrationEmployeeOwner() throws WrongAccountOwnerType {
        if(ownerType == AccountOwnerType.ADMINISTRATION_EMPLOYEE)
            return administrationEmployeeOwner;
        else
            throw new WrongAccountOwnerType("Account is not owned by AdministrationEmployee");
    }

    public PartnerCompany getCompanyOwner() throws WrongAccountOwnerType {
        if(ownerType == AccountOwnerType.COMPANY)
            return companyOwner;
        else
            throw new WrongAccountOwnerType("Account is not owned by Company");
    }

    public void setClientOwner(Client clientOwner) throws WrongAccountOwnerType {
        if(ownerType == AccountOwnerType.NONE || ownerType == AccountOwnerType.CLIENT) {
            ownerType = AccountOwnerType.CLIENT;
            this.clientOwner = clientOwner;
            clientOwner.setAccount(this);
        }
        else {
            throw new WrongAccountOwnerType("Account is already owned by " + (ownerType == AccountOwnerType.ADMINISTRATION_EMPLOYEE ? "AdministrationEmployee" : "Company"));
        }
    }

    public void setAdministrationEmployeeOwner(AdministrationEmployee administrationEmployeeOwner) throws WrongAccountOwnerType {
        if(ownerType == AccountOwnerType.NONE || ownerType == AccountOwnerType.ADMINISTRATION_EMPLOYEE) {
            ownerType = AccountOwnerType.ADMINISTRATION_EMPLOYEE;
            this.administrationEmployeeOwner = administrationEmployeeOwner;
            administrationEmployeeOwner.setAccount(this);
        }
        else {
            throw new WrongAccountOwnerType("Account is already owned by " + (ownerType == AccountOwnerType.CLIENT ? "Client" : "Company"));
        }
    }

    public void setCompanyOwner(PartnerCompany companyOwner) throws WrongAccountOwnerType {
        if(ownerType == AccountOwnerType.NONE || ownerType == AccountOwnerType.COMPANY) {
            ownerType = AccountOwnerType.COMPANY;
            this.companyOwner = companyOwner;
            companyOwner.setAccount(this);
        }
        else {
            throw new WrongAccountOwnerType("Account is already owned by " + (ownerType == AccountOwnerType.ADMINISTRATION_EMPLOYEE ? "AdministrationEmployee" : "Client"));
        }
    }

    public void resetOwner() throws WrongAccountOwnerType{
        ownerType = AccountOwnerType.NONE;
        if(clientOwner != null) {
            Client tmpClientOwner = clientOwner;
            clientOwner = null;
            tmpClientOwner.setAccount(null);
        }
        else if(administrationEmployeeOwner != null){
            AdministrationEmployee tmpAdministrationEmployeeOwner = administrationEmployeeOwner;
            administrationEmployeeOwner = null;
            tmpAdministrationEmployeeOwner.setAccount(null);
        }
        else if(companyOwner != null){
            PartnerCompany tmpCompanyOwner = companyOwner;
            companyOwner = null;
            tmpCompanyOwner.setAccount(null);
        }
    }
}
