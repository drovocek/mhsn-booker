package dro.volkov.booker.expense.views.dashboard;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dro.volkov.booker.MainLayout;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | Booker")
public class DashboardView extends VerticalLayout {

//    private final ExpenseService service;
//
//    public DashboardView(ExpenseService service) {
//        this.service = service;
//        addClassName("dashboard-view");
//        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
//        add(getContactStats(), getCompaniesChart());
//    }
//
//    private Component getContactStats() {
//        Span stats = new Span(service.countExpense() + " expense");
//        stats.addClassNames("text-xl", "mt-m");
//        return stats;
//    }
//
//    private Chart getCompaniesChart() {
//        Chart chart = new Chart(ChartType.PIE);
//
//        DataSeries dataSeries = new DataSeries();
//        service.findAll().forEach(expense ->
//                dataSeries.add(new DataSeriesItem(expense.getCategory(), expense.getPrice())));
//        chart.getConfiguration().setSeries(dataSeries);
//        return chart;
//    }
}