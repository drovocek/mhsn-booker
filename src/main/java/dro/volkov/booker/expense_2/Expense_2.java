package dro.volkov.booker.expense_2;

import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.expense.data.entity.AbstractEntity;
import dro.volkov.booker.expense_2.general.GridField;
import dro.volkov.booker.general.data.entity.HasNew;
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
import java.time.LocalDate;
import java.time.ZoneId;

import static dro.volkov.booker.general.fabric.ComponentFabric.asLabel;

@Setter
@Getter
@ToString
@Entity
@Table(name = "EXPENSE")
public class Expense_2 extends AbstractEntity implements HasNew {

    @Min(1)
    @Column(name = "PRICE", nullable = false)
    @GridField(title = "Price")
    private Double price;

    @Length(max = 200)
    @Column(name = "DESCRIPTION", length = 200)
    @GridField(title = "Description")
    private String description;

    @NotNull
    @Column(name = "DATE", nullable = false)
    @GridField(title = "Date")
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

    @GridField(title = "Category")
    public Renderer<Expense_2> categoryRenderer() {
        return new ComponentRenderer<>(t -> asLabel(t.getCategory()));
    }

    @GridField(title = "User")
    public Renderer<Expense_2> userRenderer() {
        return new ComponentRenderer<>(t -> asLabel(t.getUser()));
    }
}
