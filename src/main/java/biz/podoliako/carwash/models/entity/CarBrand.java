package biz.podoliako.carwash.models.entity;

import biz.podoliako.carwash.services.validation.NotEmptyTrim;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="car_brands")
public class CarBrand {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmptyTrim
    @Column(length = 30)
    @Length(max = 30, message = "Не больше 30 символов")
    private String name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="created_by")
    private User createdBy;

    @Column(name="date_of_creation")
    private Date dateOfCreation;

    @Column(name="date_of_delete")
    private Date dateOfDelete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CarBrand{" +
                "name='" + name + '\'' +
                ", createdBy=" + createdBy +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfDelete=" + dateOfDelete +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarBrand carBrand = (CarBrand) o;

        if (createdBy != null ? !createdBy.equals(carBrand.createdBy) : carBrand.createdBy != null) return false;
        if (dateOfCreation != null ? !dateOfCreation.equals(carBrand.dateOfCreation) : carBrand.dateOfCreation != null)
            return false;
        if (dateOfDelete != null ? !dateOfDelete.equals(carBrand.dateOfDelete) : carBrand.dateOfDelete != null)
            return false;
        if (name != null ? !name.equals(carBrand.name) : carBrand.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (dateOfCreation != null ? dateOfCreation.hashCode() : 0);
        result = 31 * result + (dateOfDelete != null ? dateOfDelete.hashCode() : 0);
        return result;
    }
}
