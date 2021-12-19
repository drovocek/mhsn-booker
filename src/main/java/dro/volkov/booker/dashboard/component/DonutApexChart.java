package dro.volkov.booker.dashboard.component;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ResponsiveBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.dashboard.ChartDataAdapter;
import dro.volkov.booker.dashboard.ChartNode;
import dro.volkov.booker.dashboard.DateScale;

@UIScope
@SpringComponent
public class DonutApexChart extends ChartNode {

    @Override
    protected ApexCharts createChart(DateScale dateScale, ChartDataAdapter cda) {
        return ApexChartsBuilder.get()
                .withChart(chart(getType()))
                .withTitle(title(this.title))
                .withLegend(legend(Position.right))
                .withSeries(cda.getData())
                .withLabels(cda.getLabels())
                .withColors(cda.getColors())
                .withResponsive(ResponsiveBuilder.get()
                        .withBreakpoint(480.0)
                        .withOptions(OptionsBuilder.get()
                                .withLegend(legend(Position.bottom))
                                .build())
                        .build())
                .build();

    }

    @Override
    public Type getType() {
        return Type.donut;
    }
}
