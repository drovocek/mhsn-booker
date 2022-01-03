package dro.volkov.booker.expense_2.util;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.Renderer;
import dro.volkov.booker.expense_2.general.GridField;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GridConfigurator {

    private static final Map<Class<?>, List<GridFieldDefinition>> gridFieldDefinitionsByClass = new ConcurrentHashMap<>();

    public static <T> void configureColumns(Grid<T> grid, Class<T> beanType) {
        getGridFieldDefinitions(beanType).forEach(definition ->
                addColumnByMethodReturnType(grid, definition.getter(), beanType)
                        .setKey(definition.fieldName())
                        .setHeader(definition.title())
        );
    }

    @SneakyThrows
    private static <T> Grid.Column<T> addColumnByMethodReturnType(Grid<T> grid, Method getter, Class<T> beanType) {
        if (getter.getReturnType().equals(Renderer.class)) {
            @SuppressWarnings("unchecked")
            Grid.Column<T> column = grid.addColumn((Renderer<T>) getFieldValue(getter, beanType.getConstructor().newInstance()));
            return column;
        } else {
            return grid.addColumn(t -> getFieldValue(getter, t));
        }
    }

    @SneakyThrows
    private static <T> Object getFieldValue(Method method, T target) {
        return method.invoke(target);
    }

    public static <T> List<GridFieldDefinition> getGridFieldDefinitions(Class<T> beanType) {
        return gridFieldDefinitionsByClass.computeIfAbsent(beanType, (key) -> {
            Map<String, GridFieldDefinition> fieldDefinitions = new LinkedHashMap<>();
            Arrays.stream(beanType.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(GridField.class))
                    .forEach(field -> {
                        GridField annotation = field.getAnnotation(GridField.class);
                        Method getter = Arrays.stream(beanType.getDeclaredMethods())
                                .filter(method -> method.getName().equals("get".concat(StringUtils.capitalize(field.getName()))))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException(String.format("Add getter to field %s", field.getName())));
                        fieldDefinitions.put(field.getName(), new GridFieldDefinition(annotation.title(), field.getName(), getter));
                    });

            Arrays.stream(beanType.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(GridField.class))
                    .filter(method -> !fieldDefinitions.containsKey(method.getName().replaceFirst("get", "")))
                    .forEach(method -> {
                        GridField annotation = method.getAnnotation(GridField.class);
                        String methodName = method.getName();
                        String fieldName = methodName.replaceFirst("get", "");
                        fieldDefinitions.put(fieldName, new GridFieldDefinition(annotation.title(), fieldName, method));
                    });

            return new ArrayList<>(fieldDefinitions.values());
        });
    }

    record GridFieldDefinition(String title, String fieldName, Method getter) {
    }

    public static <T> void configureGrid(Grid<T> grid) {
        grid.getColumns().forEach(col -> {
            col.setAutoWidth(true);
            col.setSortable(true);
            col.setResizable(true);
        });
        grid.setColumnReorderingAllowed(true);
    }
}
