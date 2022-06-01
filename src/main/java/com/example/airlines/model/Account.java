package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter protected Integer id;

    @Getter @Setter private String email;

    @Getter @Setter private String password;

    @Getter @Setter private AccountOwnerType ownerType = AccountOwnerType.NONE;

    @Getter @Setter private AccountRole role = AccountRole.CLIENT;

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
            if(role != AccountRole.ADMIN)
                role = AccountRole.CLIENT;
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
            if(role != AccountRole.ADMIN)
                role = AccountRole.ADMINISTRATIVE;
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
            if(role != AccountRole.ADMIN)
                role = AccountRole.COMPANY;
            this.companyOwner = companyOwner;
            companyOwner.setAccount(this);
        }
        else {
            throw new WrongAccountOwnerType("Account is already owned by " + (ownerType == AccountOwnerType.ADMINISTRATION_EMPLOYEE ? "AdministrationEmployee" : "Client"));
        }
    }

    public void setAsAdmin() {
        role = AccountRole.ADMIN;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
