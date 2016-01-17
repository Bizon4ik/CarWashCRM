package biz.podoliako.carwash.models.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="name")
    @Length(max=50, message = "Максимальная длина 50 символов")
    private String name;

    @Column(name="surname")
    @Length(max=50, message = "Максимальная длина 50 символов")
    private String surname;

    @Column(name="phoneNumber")
    @Length(max=50, message = "Максимальная длина 50 символов")
    private String phoneNumber;

    @Column(name="role")
    private Role role;

    @Column(name="salary")
    private BigDecimal salary;

    @Column(name="day_commission")
    private Integer dayCommission;

    @Column(name="night_commission")
    private Integer nightCommission;

    @Column(name="date_of_creation")
    private Date dateOfCreation;

    @Column(name="date_of_delete")
    private Date dateOfDelete;

    @Column(name="owner_id")
    private Integer ownerId;

    @Column(name="created_by")
    private Integer createdBy;

    @OneToMany(mappedBy = "createdBy")
    private Set<Client> clients = new HashSet<>();

    public User() {
    }

    public User(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getDayCommission() {
        return dayCommission;
    }

    public void setDayCommission(Integer dayCommission) {
        this.dayCommission = dayCommission;
    }

    public Integer getNightCommission() {
        return nightCommission;
    }

    public void setNightCommission(Integer nightCommission) {
        this.nightCommission = nightCommission;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfDelete() {
        return dateOfDelete;
    }

    public void setDateOfDelete(Date dateOfDelete) {
        this.dateOfDelete = dateOfDelete;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
