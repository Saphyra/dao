package com.github.saphyra.dao;

import com.github.saphyra.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractDao<ENTITY, DOMAIN, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> {
    protected final Converter<ENTITY, DOMAIN> converter;
    protected final REPOSITORY repository;

    public void delete(DOMAIN domain) {
        repository.delete(converter.convertDomain(domain));
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public Optional<DOMAIN> findById(ID id) {
        return repository.findById(id).map(converter::convertEntity);
    }

    public void save(DOMAIN domain) {
        repository.save(converter.convertDomain(domain));
    }

    public void saveAll(List<DOMAIN> iterable) {
        repository.saveAll(converter.convertDomain(iterable));
    }
}
