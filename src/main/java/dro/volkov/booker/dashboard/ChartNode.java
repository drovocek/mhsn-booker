package dro.volkov.booker.dashboard;

import com.github.appreciated.apexcharts.config.chart.Type;
import com.vaadin.flow.component.html.Div;

public abstract class ChartNode extends Div {

    public abstract Type getType();
}
