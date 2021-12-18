package dro.volkov.booker.dashboard.view;

import com.github.appreciated.apexcharts.config.chart.Type;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dro.volkov.booker.MainLayout;
import dro.volkov.booker.dashboard.ChartNode;
import org.vaadin.gatanaso.MultiselectComboBox;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//@RequiredArgsConstructor
@PermitAll
@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | Booker")
public class DashboardView extends VerticalLayout {

    private List<ChartNode> charts;

    @PostConstruct
    private void initView() {
        MultiselectComboBox<Type> typeSelector = new MultiselectComboBox<>("Type");
        typeSelector.setItems(Type.values());
        typeSelector.setItemLabelGenerator(Type::name);
        typeSelector.addSelectionListener(event -> {
            event.getAddedSelection()
                    .forEach(type ->
                            getChart(type)
                                    .ifPresentOrElse(this::add, () -> hasNoType(type)));

            Set<Type> removed = event.getRemovedSelection();
            getChildren()
                    .filter(component -> component instanceof ChartNode)
                    .map(component -> (ChartNode) component)
                    .filter(chartNode -> removed.contains(chartNode.getType()))
                    .forEach(this::remove);
        });
        add(typeSelector);
    }

    private void hasNoType(Type type) {
        throw new RuntimeException(String.format("Type %s does not exist", type));
    }

    private Optional<ChartNode> getChart(Type type) {
        return charts.stream()
                .filter(chartNode -> chartNode.getType().equals(type))
                .findFirst();
    }
}