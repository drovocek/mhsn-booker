package dro.volkov.booker.general.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import dro.volkov.booker.event.FilterPublisher;
import dro.volkov.booker.event.SelectPublisher;
import dro.volkov.booker.general.service.FilterCrudService;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
public abstract class FilterForm<T> extends HorizontalLayout implements FilterPublisher<T>, SelectPublisher<T> {

    protected final FilterCrudService<T> service;

    protected TextField filterText;
    protected Button addButton;

    @PostConstruct
    protected void initView() {
        addClassName("toolbar");
        filterText = construstFilterField();
        addButton = constructAddButton();
        add(filterText, addButton);
    }

    protected TextField construstFilterField() {
        return new TextField() {
            {
                setPlaceholder("Filter by ...");
                setClearButtonVisible(true);
                setValueChangeMode(ValueChangeMode.LAZY);
                addValueChangeListener(e -> {
                    List<T> filtered = service.findByFilter(this.getValue());
                    fireFilterEventToUI(filtered);
                });
            }
        };
    }

    protected Button constructAddButton() {
        return new Button() {{
            setText("Add");
            addClickListener(clickEvent -> fireSelectEventToUI(null));
        }};
    }
}
