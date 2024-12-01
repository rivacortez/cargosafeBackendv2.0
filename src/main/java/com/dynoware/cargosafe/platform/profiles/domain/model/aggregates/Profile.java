package com.dynoware.cargosafe.platform.profiles.domain.model.aggregates;

import com.dynoware.cargosafe.platform.iam.domain.model.aggregates.User;
import com.dynoware.cargosafe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.dynoware.cargosafe.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.dynoware.cargosafe.platform.profiles.domain.model.valueobjects.PersonName;
import com.dynoware.cargosafe.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Profile Aggregate Root
 */
@Entity
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @Embedded
    private PersonName name;

    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "email_address"))
    private EmailAddress emailAddress;

    @Getter
    private String street;
    @Getter
    private String phone;
    @Getter
    private String city;
    @Getter
    private String country;
    @Getter
    private String biography;
    @Getter
    private String profileImageUrl;

    @Setter
    @Getter
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Profile() {}

    public Profile(String firstName, String lastName, String email, String street, String phone, String city, String country, String biography, String profileImageUrl) {
        this.name = new PersonName(firstName, lastName);
        this.emailAddress = new EmailAddress(email);
        this.street = street;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.biography = biography;
        this.profileImageUrl = profileImageUrl;
    }

    public Profile(CreateProfileCommand command) {
        this(command.firstName(), command.lastName(), command.email(), command.street(), command.phone(), command.city(), command.country(), command.biography(), command.profileImageUrl());
    }

    public String getFullName() {
        return name.getFullName();
    }

    public String getEmailAddress() {
        return emailAddress.address();
    }

    public void updateName(String firstName, String lastName) {
        this.name = new PersonName(firstName, lastName);
    }

    public void updateEmailAddress(String email) {
        this.emailAddress = new EmailAddress(email);
    }

    public void updateProfile(String firstName, String lastName, String email, String street, String phone, String city, String country, String biography, String profileImageUrl) {
        this.name = new PersonName(firstName, lastName);
        this.emailAddress = new EmailAddress(email);
        this.street = street;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.biography = biography;
        this.profileImageUrl = profileImageUrl;
    }
}