package dro.volkov.booker.category.data.entity;

import dro.volkov.booker.expense.data.entity.AbstractEntity;
import dro.volkov.booker.expense_2.general.HasName;
import dro.volkov.booker.general.data.entity.HasFilterField;
import dro.volkov.booker.general.data.entity.HasNew;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Category extends AbstractEntity implements HasNew, HasName, HasFilterField,Serializable {

    @NotBlank
    @Length(min = 1, max = 15)
    @Column(name = "NAME", nullable = false, length = 15)
    private String name;

    @NotBlank
    @Length(min = 7, max = 7)
    @Column(name = "COLOR_HASH", nullable = false, length = 7)
    private String colorHash;

    @NotBlank
    @Length(min = 1, max = 100)
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;

    @Transient
    private String filterField;

    public Category(String name, String colorHash, String description) {
        this.name = name;
        this.colorHash = colorHash;
        this.description = description;
    }

    @Override
    public boolean isNew() {
        return getId() == null;
    }
}
