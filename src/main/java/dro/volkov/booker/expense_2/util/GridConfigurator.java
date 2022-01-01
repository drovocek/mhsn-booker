package dro.volkov.booker.expense_2.util;

import com.vaadin.flow.component.grid.Grid;
import dro.volkov.booker.expense_2.general.GridField;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GridConfigurator {

    private static final Map<Class<?>, List<GridFieldDefinition>> gridFieldDefinitionsByClass = new ConcurrentHashMap<>();

    public static <T> void configureColumns(Grid<T> grid, Class<T> beanType) {
        getGridFieldDefinitions(beanType).forEach(definition -> {
                    System.out.println("add key: " + definition.getFieldName());
                    grid.addColumn(t -> getFieldValue(definition.getGetter(), t))
                            .setKey(definition.getFieldName())
                            .setHeader(definition.getTitle());
                }
        );
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
                        fieldDefinitions.put(field.getName(), new GridFieldDefinition(field.getName(), annotation.title()));
                    });

            Arrays.stream(beanType.getDeclaredMethods())
                    .peek(method -> {
                        String methodName = method.getName();
                        if (methodName.startsWith("get")) {
                            String fieldName = methodName.replaceFirst("get", "");
                            fieldDefinitions.computeIfPresent(fieldName, (fn, val) -> {
                                val.setGetter(method);
                                return val;
                            });
                        }
                    })
                    .filter(method -> method.isAnnotationPresent(GridField.class))
                    .forEach(method -> {
                        GridField annotation = method.getAnnotation(GridField.class);
                        String methodName = method.getName();
                        String fieldName = methodName.replaceFirst("get", "");
                        if (fieldDefinitions.containsKey(fieldName)) {
                            throw new RuntimeException(String.format("Method name \"%s\" duplicate field name", fieldName));
                        }
                        fieldDefinitions.put(fieldName, new GridFieldDefinition(fieldName, annotation.title(), method));
                    });

            return new ArrayList<>(fieldDefinitions.values());
        });
    }

    @Getter
    @Setter
    static class GridFieldDefinition {

        private String fieldName;
        private String title;
        private Method getter;
        private boolean hasField = true;

        public GridFieldDefinition(String fieldName, String title) {
            this.fieldName = fieldName;
            this.title = title;
        }

        public GridFieldDefinition(String fieldName, String title, Method getter) {
            this.fieldName = fieldName;
            this.title = title;
            this.getter = getter;
            this.hasField = false;
        }
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
