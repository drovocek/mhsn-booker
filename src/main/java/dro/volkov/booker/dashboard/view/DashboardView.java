package dro.volkov.booker.dashboard.view;

import com.github.appreciated.apexcharts.config.chart.Type;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dro.volkov.booker.MainLayout;
import dro.volkov.booker.dashboard.ChartDataAdapter;
import dro.volkov.booker.dashboard.ChartNode;
import dro.volkov.booker.dashboard.DateScale;
import dro.volkov.booker.expense.data.ExpenseCrudService;
import dro.volkov.booker.expense.data.entity.Expense;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static dro.volkov.booker.util.NotificationUtil.noticeERR;

@RequiredArgsConstructor
@PermitAll
@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | Booker")
public class DashboardView extends VerticalLayout {

    private final List<ChartNode> charts;

    private final ExpenseCrudService expenseService;

    private Div chartParent;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
    }

    private void fillCharts(DateScale dateScale) {
        List<Expense> expenses = expenseService.findByScale(dateScale);
        ChartDataAdapter cda = new ChartDataAdapter(expenses);
        charts.forEach(chartNode -> chartNode.refreshChart(dateScale, cda));
    }

    @PostConstruct
    private void initView() {
        this.chartParent = constructParent();
        add(new HorizontalLayout(constructScaleSelector(), constructCharTypeSelector()), chartParent);
    }

    private Div constructParent() {
        return new Div() {{
            addClassName("chart-parent");
        }};
    }

    private Accordion constructCharTypeSelector() {
        return new Accordion() {
            final List<Type> types = charts.stream().map(ChartNode::getType).collect(Collectors.toList());
            final MultiSelectListBox<Type> listBox = new MultiSelectListBox<>();

            {
                listBox.setItems(types);
                listBox.addSelectionListener(event -> {
                    event.getRemovedSelection().forEach(type -> getChart(type)
                            .ifPresentOrElse(chartParent::remove, () -> hasNoType(type)));
                    event.getAddedSelection().forEach(type -> getChart(type)
                            .ifPresentOrElse(chartParent::add, () -> hasNoType(type)));
                });
                close();
                add("Charts", listBox);
            }

            @Override
            protected void onAttach(AttachEvent attachEvent) {
                super.onAttach(attachEvent);
                listBox.select(types);
            }
        };
    }

    private ComboBox<DateScale> constructScaleSelector() {
        return new ComboBox<>() {
            {
                setLabel("Date scale");
                setItems(DateScale.values());
                setItemLabelGenerator(DateScale::name);
                setClearButtonVisible(false);
                addValueChangeListener(event -> fillCharts(event.getValue()));
            }

            @Override
            protected void onAttach(AttachEvent attachEvent) {
                super.onAttach(attachEvent);
                setValue(DateScale.WEEK);
            }
        };
    }

    private void hasNoType(Type type) {
        noticeERR(String.format("Type %s does not exist", type));
    }

    private Optional<ChartNode> getChart(Type type) {
        return charts.stream()
                .filter(chartNode -> chartNode.getType().equals(type))
                .findFirst();
    }
}