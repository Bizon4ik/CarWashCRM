package biz.podoliako.carwash.models.entity;


import biz.podoliako.carwash.services.validation.ClientNameUnique;
import biz.podoliako.carwash.services.validation.NotEmptyTrim;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="clients")
public class Client {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column(name="name", length = 50)
    @Length(max=50, message = "Максимальная длина 50 символов")
    @NotEmptyTrim
    @ClientNameUnique
    private String name;

    @Column(name = "phone_number", length = 20)
    @Length(max=20, message = "Максимальная длина 20 символов")
    private String phoneNumber;

    @Column(name = "pay_by_cash")
    private Boolean isPayByCash;

    @Column(name="price_multiplicator")
    private Integer priceMultiplicator = 100;

    @Column(name = "date_of_creation")
    private Date dateOfCreation;

    @Column(name = "date_of_delete")
    private  Date dateOfDelete;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="created_by")
    private User createdBy;



    public Client() {
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public  Boolean getIsPayByCash() {
        return isPayByCash;
    }

    public void setIsPayByCash(Boolean isPayByCash) {
        this.isPayByCash = isPayByCash;
    }

    public Integer getPriceMultiplicator() {
        return priceMultiplicator;
    }

    public void setPriceMultiplicator(Integer priceMultiplicator) {
        this.priceMultiplicator = priceMultiplicator;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (isPayByCash != null ? !isPayByCash.equals(client.isPayByCash) : client.isPayByCash != null) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(client.phoneNumber) : client.phoneNumber != null) return false;
        if (priceMultiplicator != null ? !priceMultiplicator.equals(client.priceMultiplicator) : client.priceMultiplicator != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (isPayByCash != null ? isPayByCash.hashCode() : 0);
        result = 31 * result + (priceMultiplicator != null ? priceMultiplicator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isPayByCash=" + isPayByCash +
                ", priceMultiplicator=" + priceMultiplicator +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfDelete=" + dateOfDelete +
                ", createdBy=" + createdBy +
                '}';
    }
}
