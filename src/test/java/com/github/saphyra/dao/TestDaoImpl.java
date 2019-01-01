package com.github.saphyra.dao;

import com.github.saphyra.converter.Converter;

public class TestDaoImpl extends AbstractDao<String, String, Long, TestRepository> {
    public TestDaoImpl(Converter<String, String> converter, TestRepository repository) {
        super(converter, repository);
    }
}
