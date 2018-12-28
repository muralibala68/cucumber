package org.bala.cucumber_internal;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterByTypeTransformer;
import io.cucumber.datatable.*;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.ObjectMapper;
import org.bala.md.Trade;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;
import static java.util.Locale.ENGLISH;

public class ParameterTypes implements TypeRegistryConfigurer {
    @Override
    public Locale locale() {
        return ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        Transformer transformer = new Transformer();
        typeRegistry.setDefaultDataTableCellTransformer(transformer);
        typeRegistry.setDefaultDataTableEntryTransformer(transformer);
        typeRegistry.setDefaultParameterTransformer(transformer);
        typeRegistry.defineDataTableType(new DataTableType( Trade.class, transformer));
    }

    private class Transformer implements ParameterByTypeTransformer,
                                         TableEntryByTypeTransformer,
                                         TableCellByTypeTransformer,
                                         TableEntryTransformer<Trade> {
        ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public Object transform(String s, Type type) {
            return objectMapper.convertValue(s, objectMapper.constructType(type));
        }

        @Override
        public <T> T transform(Map<String, String> map, Class<T> aClass, TableCellByTypeTransformer tableCellByTypeTransformer) {
            return objectMapper.convertValue(map, aClass);
        }

        @Override
        public <T> T transform(String s, Class<T> aClass) {
            return objectMapper.convertValue(s, aClass);
        }

        @Override
        public Trade transform(final Map<String, String> row) {
            return new Trade(row.get("productId"), parseDouble(row.get("size")), parseDouble(row.get("price")), parseLong(row.get("timestamp")));
        }
    }
}
