package com.website.demo.API.user;

import com.website.demo.API.address.Address;
import com.website.demo.API.person.Person;
import com.website.demo.authorities.AppUserRole;
import com.website.demo.API.schedule.Schedule;
import com.website.demo.API.specialization.Specialization;
import com.website.demo.API.user.doctor.DoctorDto;
import com.website.demo.API.visit.Visit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "AppUser")
@Table(name = "app_user")



public class AppUser implements UserDetails  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String password;
    private String img;
    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    @ManyToMany
    @JoinTable(name = "doctor_specialization", joinColumns = {@JoinColumn(name = "app_user_id")}, inverseJoinColumns = {@JoinColumn(name = "specialization_id")})
    private Set<Specialization> specializations;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Visit> visitSet;
    @ManyToMany
    @JoinTable(name = "doctor_schedule", joinColumns = {@JoinColumn(name = "app_user_id")}, inverseJoinColumns = {@JoinColumn(name = "schedule_id")})
    private Set<Schedule> schedules;
    @Enumerated(value = EnumType.STRING)
    private AppUserRole role;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    public AppUser(Person person,
                   String password,
                   AppUserRole role){
        this.person = person;
        this.password = password;
        this.role = role;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = false;
    }

    public AppUser(String firstName,
                   String secondName,
                   String lastName,
                   String email,
                   String password,
                   String phone,
                   Address address,
                   LocalDate birthdate,
                   AppUserRole role) {

        person = new Person(
                firstName,
                secondName,
                lastName,
                email,
                phone,
                address,
                birthdate
        );

        this.password = password;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = false;
        this.role = role;
    }

    public String getEmail(){
        return person.getEmail();
    }

    public void setAddress(Address address){
        person.setAddress(address);
    }

    public String getFirstName(){
        return person.getFirstName();
    }
    public String getSecondName(){return person.getSecondName();}
    public String getLastName(){return person.getLastName();}
    public String getPhone(){return person.getPhone();}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        Set<SimpleGrantedAuthority> roleGrantedAuthorities = role.getGrantedAuthorities();
        authorities.addAll(roleGrantedAuthorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Address getAddress(){
        return person.getAddress();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

}
