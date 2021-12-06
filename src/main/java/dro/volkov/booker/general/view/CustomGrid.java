package dro.volkov.booker.general.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.event.*;
import dro.volkov.booker.general.service.FilterCrudService;

import java.util.List;

import static dro.volkov.booker.util.NotificationUtil.noticeSSS;


public class CustomGrid<T> extends Grid<T> implements SelectPublisher<T>, DeleteNotifier<T>, SaveNotifier<T>, FilterNotifier<T>, CancelNotifier {

    protected final FilterCrudService<T> service;

    protected Registration deleteRegistration;
    protected Registration saveRegistration;
    protected Registration cancelRegistration;
    protected Registration filterRegistration;

    public CustomGrid(FilterCrudService<T> service) {
        super();
        this.service = service;
        configGrid();
    }

    protected void configGrid() {
        addClassNames("filter-crud-grid");
        setSizeFull();
        getColumns().forEach(col -> col.setAutoWidth(true));
        asSingleSelect().addValueChangeListener(event -> fireSelectEventToUI(event.getValue()));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        deleteRegistration = addDeleteListener(deleteEvent -> deleteEntity(deleteEvent.getDeleted()));
        saveRegistration = addSaveListener(saveEvent -> saveEntity(saveEvent.getPersist()));
        cancelRegistration = addCancelListener(cancelEvent -> cancelSelect());
        filterRegistration = addFilterListener(filterEvent -> updateList(filterEvent.getFiltered()));
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

    protected void updateList(List<T> filtered) {
        setItems(filtered);
    }
}
