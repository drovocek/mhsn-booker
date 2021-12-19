package dro.volkov.booker.dashboard.component;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.FillBuilder;
import com.github.appreciated.apexcharts.config.builder.StrokeBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.dashboard.ChartDataAdapter;
import dro.volkov.booker.dashboard.ChartNode;
import dro.volkov.booker.dashboard.DateScale;

@UIScope
@SpringComponent
public class BarApexChart extends ChartNode {

    @Override
    public ApexCharts createChart(DateScale dateScale, ChartDataAdapter cda) {
        return ApexChartsBuilder.get()
                .withChart(chart(getType()))
                .withTitle(title(this.title))
                .withLegend(legend(Position.right))
                .withSeries(cda.getSeries())
                .withColors(cda.getColors())
                .withStroke(StrokeBuilder.get()
                        .withShow(true)
                        .withWidth(6.0)
                        .withColors("transparent")
                        .build())
                .withXaxis(XAxisBuilder.get().withCategories(dateScale.name()).build())
                .withFill(FillBuilder.get().withOpacity(1.0).build())
                .build();
    }

    @Override
    public Type getType() {
        return Type.bar;
    }
}
