package com.github.saphyra.dao;

import com.github.saphyra.converter.Converter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DaoTest {
    private static String ENTITY = "entity";
    private static String DOMAIN = "converted_entity";
    private static Long ID = 1L;

    @Mock
    private Converter<String, String> converter;

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private TestDaoImpl underTest;

    @Before
    public void init() {
        when(converter.convertDomain(DOMAIN)).thenReturn(ENTITY);
        when(converter.convertDomain(Arrays.asList(DOMAIN))).thenReturn(Arrays.asList(ENTITY));
        when(converter.convertEntity(ENTITY)).thenReturn(DOMAIN);
        given(converter.convertEntity(Arrays.asList(ENTITY))).willReturn(Arrays.asList(DOMAIN));
    }

    @Test
    public void testDelete() {
        //WHEN
        underTest.delete(DOMAIN);
        //THEN
        verify(testRepository).delete(ENTITY);
    }

    @Test
    public void deleteAll() {
        underTest.deleteAll();

        verify(testRepository).deleteAll();
    }

    @Test
    public void deleteAllEntitites() {
        //WHEN
        underTest.deleteAll(Arrays.asList(DOMAIN));
        //THEN
        verify(testRepository).deleteAll(Arrays.asList(ENTITY));
    }

    @Test
    public void testDeleteById() {
        //WHEN
        underTest.deleteById(ID);
        //THEN
        verify(testRepository).deleteById(ID);
    }

    @Test
    public void findAll() {
        given(testRepository.findAll()).willReturn(Arrays.asList(ENTITY));

        List<String> result = underTest.findAll();

        assertThat(result).containsExactly(DOMAIN);
    }

    @Test
    public void findAllByIds() {
        given(testRepository.findAllById(Arrays.asList(ID))).willReturn(Arrays.asList(ENTITY));

        List<String> result = underTest.findAllById(Arrays.asList(ID));

        assertThat(result).containsExactly(DOMAIN);
    }

    @Test
    public void testFindByIdShouldReturnEmptyWhenNotFound() {
        //GIVEN
        when(testRepository.findById(ID)).thenReturn(Optional.empty());
        //WHEN
        assertFalse(underTest.findById(ID).isPresent());
    }

    @Test
    public void testFindByIdShouldReturnDomain() {
        //GIVEN
        when(testRepository.findById(ID)).thenReturn(Optional.of(ENTITY));
        //WHEN
        Optional<String> result = underTest.findById(ID);
        //THEN
        assertTrue(result.isPresent());
        assertEquals(DOMAIN, result.get());
    }

    @Test
    public void testSave() {
        //WHEN
        underTest.save(DOMAIN);
        //THEN
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(testRepository).save(argumentCaptor.capture());
        assertEquals(ENTITY, argumentCaptor.getValue());
    }

    @Test
    public void testStaveAll() {
        //WHEN
        underTest.saveAll(Arrays.asList(DOMAIN));
        //THEN
        verify(testRepository).saveAll(Arrays.asList(ENTITY));
    }
}