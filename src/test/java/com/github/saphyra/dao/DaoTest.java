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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
    }

    @Test
    public void testDelete() {
        //WHEN
        underTest.delete(DOMAIN);
        //THEN
        verify(testRepository).delete(ENTITY);
    }

    @Test
    public void testDeleteById() {
        //WHEN
        underTest.deleteById(ID);
        //THEN
        verify(testRepository).deleteById(ID);
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