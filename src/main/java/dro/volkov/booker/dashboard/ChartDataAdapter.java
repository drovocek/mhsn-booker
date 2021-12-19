package dro.volkov.booker.dashboard;

import com.github.appreciated.apexcharts.helper.Series;
import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.expense.data.entity.Expense;
import lombok.Value;

import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

@Value
public class ChartDataAdapter {

    Series[] series;
    Double[] data;
    String[] labels;
    String[] colors;

    public ChartDataAdapter(Collection<Expense> chartData) {
        Map<Category, Double> spendByCategory = chartData.stream()
                .collect(
                        groupingBy((Expense::getCategory),
                                summingDouble(Expense::getPrice)));

        int categoryCount = spendByCategory.size();
        this.series = new Series[categoryCount];
        this.data = new Double[categoryCount];
        this.labels = new String[categoryCount];
        this.colors = new String[categoryCount];

        var ref = new Object() {
            int index = 0;
        };

        spendByCategory.entrySet().stream()
                .sorted((Map.Entry.comparingByValue()))
                .forEach(entry -> {
                    series[ref.index] = new Series<>(entry.getKey().getName(), entry.getValue());
                    data[ref.index] = entry.getValue();
                    labels[ref.index] = entry.getKey().getName();
                    colors[ref.index] = entry.getKey().getColorHash();
                    ref.index++;
                });
    }
}
