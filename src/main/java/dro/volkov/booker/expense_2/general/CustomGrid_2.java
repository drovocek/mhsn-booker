package dro.volkov.booker.expense_2.general;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import dro.volkov.booker.expense_2.general.DataService;
import dro.volkov.booker.general.event.FilterNotifier;
import dro.volkov.booker.general.event.SelectPublisher;
import org.springframework.context.annotation.Scope;

import static dro.volkov.booker.util.NotificationUtil.noticeSSS;

@Scope("prototype")
@SpringComponent
public class CustomGrid_2<T> extends Grid<T>
        implements FilterNotifier<T>,
        SelectPublisher<T> {

//        SelectPublisher<T>, DeleteNotifier<T>,
//        SaveNotifier<T>, , CloseNotifier

    protected final DataService<T> dataService;

    protected Registration filterReg;

//    protected Registration deleteRegistration;
//    protected Registration saveRegistration;
//    protected Registration closeRegistration;


    public CustomGrid_2(DataService<T> dataService) {
        this.dataService = dataService;
        configGrid();
    }

    public void configGrid() {
        addClassNames("filter-crud-grid");
        setSizeFull();
        asSingleSelect()
                .addValueChangeListener(event -> fireUISelectEvent(event.getValue()));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        this.filterReg = addUIFilterListener(event -> updateList(event.getFilter()));
//        deleteRegistration = addUIDeleteListener(deleteEvent -> deleteEntity(deleteEvent.getDeleted()));
//        saveRegistration = addUISaveListener(saveEvent -> {
//            T saved = saveEntity(saveEvent.getPersist());
//            getDataProvider().refreshItem(saved);
//            cancelSelect();
//            noticeSSS("Save succeeded");
//        });
//        closeRegistration = addUICloseListener(cancelEvent -> cancelSelect());
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        filterReg.remove();
//        deleteRegistration.remove();
//        saveRegistration.remove();
//        closeRegistration.remove();
    }

    protected T saveEntity(T persist) {
        T save = dataService.save(persist);
        noticeSSS("Save succeeded");
        return save;
    }

    protected void deleteEntity(T deleted) {
        dataService.delete(deleted);
        noticeSSS("Delete succeeded");
    }

    protected void cancelSelect() {
        asSingleSelect().clear();
    }

    protected void updateList(T filter) {
        setItems(dataService.findByFilterFields(filter));
    }
}
