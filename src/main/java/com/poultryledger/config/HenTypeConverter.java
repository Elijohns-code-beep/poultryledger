package com.poultryledger.config;

import com.poultryledger.entity.HenType;
import com.poultryledger.repository.HenTypeRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HenTypeConverter implements Converter<String, HenType> {
    private final HenTypeRepository repository;
    public HenTypeConverter(HenTypeRepository repository) { this.repository = repository; }
    @Override public HenType convert(String source) { if (source == null || source.isBlank()) return null; return repository.findById(Long.valueOf(source)).orElseThrow(); }
}
