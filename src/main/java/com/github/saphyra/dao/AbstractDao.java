package com.github.saphyra.dao;

import com.github.saphyra.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public abstract class AbstractDao<E, D, ID, R extends JpaRepository<E, ID>> {
    protected final Converter<E, D> converter;
    protected final R repository;

    public void delete(D domain){
        repository.delete(converter.convertDomain(domain));
    }

    public void deleteById(ID id){
        repository.deleteById(id);
    }

    public D findById(ID id) {
        return repository.findById(id).map(converter::convertEntity).orElse(null);
    }

    public void save(D domain){
        repository.save(converter.convertDomain(domain));
    }
}
