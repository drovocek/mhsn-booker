package dro.volkov.booker.expense.data.entity;

import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.general.data.entity.HasFilterField;
import dro.volkov.booker.general.data.entity.HasNewCheck;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

@Setter
@Getter
@ToString
@Entity
public class Expense extends AbstractEntity implements HasFilterField, HasNewCheck, Serializable {

    //    @NotNull
    @Min(1)
    @Column(name = "PRICE")
    private BigDecimal price;

    @Length(min = 1, max = 200)
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATE")
    private LocalDate date;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @NotEmpty
    @Column(name = "USERNAME" )
    private String username;

//    @ToString.Exclude
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User user;

//    @JsonIgnoreProperties({"employees"})
//    @Formula("(select count(c.id) from Contact c where c.company_id = id)")

    @Transient
    private String filterField;

    @PrePersist
    private void setDateIfNotSet() {
        if (date == null) {
            date = LocalDate.now(ZoneId.of("Europe/Moscow"));
        }
    }

    @Override
    public boolean isNew() {
        return getId() == null;
    }
}
