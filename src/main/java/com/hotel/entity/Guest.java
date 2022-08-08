package com.hotel.entity;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Guests")
public class Guest {
    @Id
    @Column(name = "id_number", nullable = false, length = 20)
    private String id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender", nullable = false, length = 1)
    private String gender;

    @Column(name = "citizenship", nullable = false, length = 50)
    private String citizenship;

    @Formula("Concat(first_name, ' ', middle_name, ' ', last_name)")
    private String fullName;

    @OneToMany(mappedBy = "guestIdNumber")
    private Set<Reservation> reservations = new LinkedHashSet<>();

    public Guest(String id, String firstName, String middleName, String lastName, LocalDate birthDate, String gender, String citizenship) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.citizenship = citizenship;
    }
}