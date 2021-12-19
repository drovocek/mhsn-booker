package dro.volkov.booker.dashboard;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.Chart;
import com.github.appreciated.apexcharts.config.Legend;
import com.github.appreciated.apexcharts.config.TitleSubtitle;
import com.github.appreciated.apexcharts.config.XAxis;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.TitleSubtitleBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.html.Div;
import dro.volkov.booker.expense.data.entity.Expense;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public abstract class ChartNode extends Div {

    protected String title = "Expense by category";

    public void refreshChart(Collection<Expense> data) {
        removeAll();
        ApexCharts chart = createChart(data);
        chart.addClassName("apex-chart");
        add(chart);
    }

    public ChartNode() {
        addClassName("chart-node");
    }

    public abstract Type getType();

    protected abstract ApexCharts createChart(Collection<Expense> data);

    protected Series<Double>[] asSeries(Collection<Expense> data) {
        TreeMap<String, Double> valByLabel =
                data.stream()
                        .sorted(Comparator.comparing(Expense::getPrice))
                        .collect(
                                groupingBy((expense -> expense.getCategory().getName()),
                                        TreeMap::new,
                                        summingDouble(Expense::getPrice)));

        return valByLabel.entrySet().stream()
                .map(entry -> new Series<>(entry.getKey(), entry.getValue()))
                .toArray(Series[]::new);
    }

    @SafeVarargs
    protected final XAxis legend(Series<Double>... series) {
        List<String> labels = Arrays.stream(series)
                .map(Series::getName)
                .collect(Collectors.toList());
        return XAxisBuilder.get().withCategories(labels).build();
    }

    @SafeVarargs
    protected final Double[] data(Series<Double>... series) {
        return Arrays.stream(series)
                .map(s->s.getData()[0])
                .toArray(Double[]::new);
    }

    protected Legend legend(Position position) {
        return LegendBuilder.get()
                .withPosition(position)
                .build();
    }

    protected Chart chart(Type type) {
        return ChartBuilder.get()
                .withType(type)
                .build();
    }

    protected TitleSubtitle title(String title) {
        return TitleSubtitleBuilder.get()
                .withText(title)
                .withAlign(Align.left)
                .build();
    }
}
