package com.squad.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue filtering() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");

        return getMappingJacksonValueIgnoringFields(someBean, "SomeBeanFilter", "field1", "field2");
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList() {
        List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"),
                new SomeBean("value4", "value5", "value6"));

        return getMappingJacksonValueIgnoringFields(list, "SomeBeanFilter", "field2", "field3");
    }

    private MappingJacksonValue getMappingJacksonValueIgnoringFields(Object bean, String filterName, String... fields) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(bean);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept(fields);

        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter(filterName, filter);

        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

}
