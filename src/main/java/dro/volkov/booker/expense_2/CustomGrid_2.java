package dro.volkov.booker.expense_2;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.general.event.*;

import java.util.ArrayList;
import java.util.List;

import static dro.volkov.booker.util.NotificationUtil.noticeSSS;

public class CustomGrid_2<T> extends Grid<T>
        implements FilterNotifier<T>, DeleteNotifier<T>, SaveNotifier<T>, FilterSwitchNotifier,
        SelectPublisher<T> {

    protected final DataService<T> dataService;

    protected Registration filterReg;
    protected Registration deleteReg;
    protected Registration saveReg;
    protected Registration filterSwitchReg;

    protected final List<T> dataStore = new ArrayList<>();

    public CustomGrid_2(DataService<T> dataService) {
        this.dataService = dataService;
        configGrid();
    }

    public void configGrid() {
        addClassNames("filter-crud-grid");
        setSizeFull();
        asSingleSelect()
                .addValueChangeListener(event -> fireUISelectEvent(event.getValue()));
        configDataProvider();
    }

    protected void configDataProvider() {
        setItems(new ListDataProvider<>(this.dataStore));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        this.filterReg = addUIFilterListener(event -> updateList(event.getFilter()));
        this.deleteReg = addUIDeleteListener(event -> deleteEntity(event.getDeleted()));
        this.saveReg = addUISaveListener(event -> {
            T saved = saveEntity(event.getPersist());
            getDataProvider().refreshItem(saved);
            clearSelect();
            noticeSSS("Save succeeded");
        });
        this.filterSwitchReg = addUIFilterSwitchListener(event -> {
            if (event.isOpened()) {
                clearSelect();
            }
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        this.filterReg.remove();
        this.deleteReg.remove();
        this.saveReg.remove();
        this.filterSwitchReg.remove();
    }

    protected T saveEntity(T persist) {
        T save = this.dataService.save(persist);
        noticeSSS("Save succeeded");
        return save;
    }

    protected void deleteEntity(T deleted) {
        clearSelect();
        if (deleted != null) {
            this.dataService.delete(deleted);
            this.dataStore.remove(deleted);
            getDataProvider().refreshAll();
            noticeSSS("Delete succeeded");
        }
    }

    protected void clearSelect() {
        asSingleSelect().clear();
    }

    protected void updateList(Object filter) {
        this.dataStore.clear();
        this.dataStore.addAll(this.dataService.findByFilter(filter));
        getDataProvider().refreshAll();
    }
}
