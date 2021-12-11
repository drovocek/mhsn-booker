package dro.volkov.booker.category.data.entity;

import dro.volkov.booker.expense.data.entity.AbstractEntity;
import dro.volkov.booker.general.data.entity.HasFilterField;
import dro.volkov.booker.general.data.entity.HasNewCheck;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Category extends AbstractEntity implements HasFilterField, HasNewCheck, Serializable {

    @Length(min = 1, max = 15)
    private String name;

    @Length(min = 7, max = 7)
    private String colorHash;

    @Length(min = 1, max = 100)
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
