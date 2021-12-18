package dro.volkov.booker.expense.data.entity;

import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.general.data.entity.HasFilterField;
import dro.volkov.booker.general.data.entity.HasNewCheck;
import dro.volkov.booker.user.data.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

@Setter
@Getter
@ToString
@Entity
@Table(name = "EXPENSE")
public class Expense extends AbstractEntity implements HasFilterField, HasNewCheck, Serializable {

    @Min(1)
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Length(max = 200)
    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    @NotNull
    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @NotNull
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @ToString.Exclude
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private User user;

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
