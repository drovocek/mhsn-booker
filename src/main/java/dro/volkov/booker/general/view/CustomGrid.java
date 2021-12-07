package dro.volkov.booker.general.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.event.*;
import dro.volkov.booker.expense.data.entity.HasFilterField;
import dro.volkov.booker.general.service.FilterCrudService;

import static dro.volkov.booker.util.NotificationUtil.noticeSSS;

public class CustomGrid<T extends HasFilterField> extends Grid<T> implements SelectPublisher<T>, DeleteNotifier<T>, SaveNotifier<T>, FilterNotifier<T>, CloseNotifier {

    protected final FilterCrudService<T> service;

    protected Registration deleteRegistration;
    protected Registration saveRegistration;
    protected Registration closeRegistration;
    protected Registration filterRegistration;

    public CustomGrid(FilterCrudService<T> service,Class<T> beanType) {
        super(beanType);
        this.service = service;
        configGrid();
    }

    protected void configGrid() {
        addClassNames("filter-crud-grid");
        setSizeFull();
        getColumns().forEach(col -> col.setAutoWidth(true));
        asSingleSelect().addValueChangeListener(event -> fireUISelectEvent(event.getValue()));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        deleteRegistration = addUIDeleteListener(deleteEvent -> deleteEntity(deleteEvent.getDeleted()));
        saveRegistration = addUISaveListener(saveEvent -> saveEntity(saveEvent.getPersist()));
        closeRegistration = addUICloseListener(cancelEvent -> cancelSelect());
        filterRegistration = addUIFilterListener(filterEvent -> {
            System.out.println("CATCH");
            updateList(filterEvent.getFilter());
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        deleteRegistration.remove();
        saveRegistration.remove();
    }

    protected void saveEntity(T persist) {
        service.save(persist);
        //updateList();
        cancelSelect();
        noticeSSS("Save succeeded");
    }

    protected void deleteEntity(T deleted) {
        service.delete(deleted);
        //  updateList();
        //  form.close();
        noticeSSS("Delete succeeded");
    }

    protected void cancelSelect() {
        asSingleSelect().clear();
    }

    protected void updateList(T filter) {
        setItems(service.findByFilter(filter.getFilterField()));
    }
}
