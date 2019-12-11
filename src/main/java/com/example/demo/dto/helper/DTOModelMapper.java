package com.example.demo.dto.helper;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.core.annotation.AnnotationUtils;

public class DTOModelMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

   public static <T> T convertTo(Object entity, Class<T> aClass) {
        Assert.notNull(AnnotationUtils.getAnnotation(aClass, DTO.class),
                "Type should contain DTO annotation");

        return modelMapper.map(entity, aClass);
    }
}
