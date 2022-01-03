package dro.volkov.booker.expense_2;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.general.event.FilterNotifier;
import dro.volkov.booker.general.event.SelectPublisher;

import static dro.volkov.booker.util.NotificationUtil.noticeSSS;

public class CustomGrid_2<T> extends Grid<T>
        implements FilterNotifier<T>,
        SelectPublisher<T> {

    protected final DataService<T> dataService;

    protected Registration filterReg;

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
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        filterReg.remove();
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

    protected void updateList(Object filter) {
        setItems(dataService.findByFilter(filter));
    }
}
