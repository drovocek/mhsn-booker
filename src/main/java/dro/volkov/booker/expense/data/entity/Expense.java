package dro.volkov.booker.expense.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
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
    private BigDecimal price;

    //    @Length(min = 3, max = 50)
    private String description;

    private LocalDate date;

//    @NotNull
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "category_id")
//    private Category category;

    private String category;

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
