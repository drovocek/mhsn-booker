package dro.volkov.booker.dashboard.component;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.dashboard.ChartNode;
import dro.volkov.booker.expense.data.entity.Expense;

import java.util.Collection;

@UIScope
@SpringComponent
public class BarApexChart extends ChartNode {

    @Override
    public ApexCharts createChart(Collection<Expense> data) {
        Series<Double>[] series = asSeries(data);
        return ApexChartsBuilder.get()
                .withChart(chart(getType()))
                .withTitle(title(this.title))
                .withLegend(legend(Position.right))
                .withSeries(series)
                .withXaxis(legend(series))
                .build();
    }

    @Override
    public Type getType() {
        return Type.bar;
    }
}
