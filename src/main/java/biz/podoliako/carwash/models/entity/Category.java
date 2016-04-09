package biz.podoliako.carwash.models.entity;


import biz.podoliako.carwash.services.validation.CategoryNameUnique;
import biz.podoliako.carwash.services.validation.NotEmptyTrim;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Categories")
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="name", length = 100)
    @Length(max=100, message = "Максимальная длина 100 символов")
    @NotEmptyTrim
    private String name;

    @Column(name="description", length = 200)
    @Length(max=200, message = "Максимальная длина 200 символов")
    @NotEmptyTrim
    private String description;

    @Column(name = "date_of_creation")
    private Date dateOfCreation;

    @Column(name = "date_of_delete")
    private  Date dateOfDelete;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="created_by")
    private User createdBy;

    public Category() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        Category category = (Category) o;

        if (createdBy != null ? !createdBy.equals(category.createdBy) : category.createdBy != null) return false;
        if (dateOfCreation != null ? !dateOfCreation.equals(category.dateOfCreation) : category.dateOfCreation != null)
            return false;
        if (dateOfDelete != null ? !dateOfDelete.equals(category.dateOfDelete) : category.dateOfDelete != null)
            return false;
        if (description != null ? !description.equals(category.description) : category.description != null)
            return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dateOfCreation != null ? dateOfCreation.hashCode() : 0);
        result = 31 * result + (dateOfDelete != null ? dateOfDelete.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfDelete=" + dateOfDelete +
                ", createdBy=" + createdBy +
                '}';
    }
}
