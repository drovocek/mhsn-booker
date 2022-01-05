package dro.volkov.booker.general.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.general.data.FilterCrudService;
import dro.volkov.booker.general.data.entity.HasFilterField;
import dro.volkov.booker.general.event.*;

import static dro.volkov.booker.util.NotificationUtil.noticeSSS;

public class CustomGrid<T extends HasFilterField> extends Grid<T>
        implements SelectPublisher<T>, DeleteNotifier<T>,
        SaveNotifier<T>, FilterNotifier<T>, CloseNotifier {

    protected final FilterCrudService<T> service;

    protected Registration deleteRegistration;
    protected Registration saveRegistration;
    protected Registration closeRegistration;
    protected Registration filterRegistration;

    public CustomGrid(FilterCrudService<T> service, Class<T> beanType) {
        super(beanType);
        this.service = service;
        configGrid();
    }

    protected void configGrid() {
        addClassNames("filter-crud-grid");
        setSizeFull();
        getColumns().forEach(col -> col.setAutoWidth(true));
        asSingleSelect()
                .addValueChangeListener(event -> fireUISelectEvent(event.getValue()));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        deleteRegistration = addUIDeleteListener(deleteEvent -> deleteEntity(deleteEvent.getDeleted()));
        saveRegistration = addUISaveListener(saveEvent -> {
            T saved = saveEntity(saveEvent.getPersist());
            getDataProvider().refreshItem(saved);
            cancelSelect();
            noticeSSS("Save succeeded");
        });
        closeRegistration = addUICloseListener(cancelEvent -> cancelSelect());
        filterRegistration = addUIFilterListener(filterEvent -> updateList(filterEvent.getFilter()));
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        deleteRegistration.remove();
        saveRegistration.remove();
        closeRegistration.remove();
        filterRegistration.remove();
    }

    protected T saveEntity(T persist) {
        return service.save(persist);
    }

    protected void deleteEntity(T deleted) {
        service.delete(deleted);
        noticeSSS("Delete succeeded");
    }

    protected void cancelSelect() {
        asSingleSelect().clear();
    }

    protected void updateList(T filter) {
        setItems(service.findByFilter(filter.getFilterField()));
    }
}
