package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.Type;
import com.oocl.ita.demo.repositories.TypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TypeServiceTest {
    @Autowired
    private TypeService typeService;

    @MockBean
    private TypeRepository typeRepository;
    @Test
    public void shoud_get_null_when_call_getAllTypes_with_blank() {
        //given
        String accountKind = "";
        List<Type> types = new ArrayList<>();
        when(typeRepository.findAll()).thenReturn(types);
        //when
        List<Type> result = typeService.getAllTypes(accountKind);
        //then
        assertEquals(types, result);
    }

    @Test
    public void shoud_get_all_type_when_call_getAllTypes_with_blank() {
        //given
        String accountKind = "支出";
        List<Type> types = new ArrayList<>();
        Type type =mock(Type.class);
        when(type.getId()).thenReturn(1);
        types.add(type);
        when(typeRepository.findAllByAccountKind(accountKind)).thenReturn(types);
        //when
        List<Type> result = typeService.getAllTypes(accountKind);
        //then
        assertEquals((Integer) 1, result.get(0).getId());
    }

    @Test
    public void should_return_null_when_call_findTypeByTypeName_with_null() {
        //given
        String type = "";
        List<Type> types = new ArrayList<>();
        when(typeRepository.findTypeByType(type)).thenReturn(types);
        //when
        Type result = typeService.findTypeByTypeName(type);
        //then
        assertNull(result);
    }

    @Test
    public void should_return_type_when_call_findTypeByTypeName_with_typename() {
        //given
        String typename="typename";
        List<Type> types = new ArrayList<>();
        Type type=mock(Type.class);
        when(type.getId()).thenReturn(1);
        types.add(type);
        when(typeRepository.findTypeByType(typename)).thenReturn(types);
        //when
        Type result = typeService.findTypeByTypeName(typename);
        //then
        assertEquals((Integer) 1, result.getId());
    }

    @Test
    public void should_return_when_type_is_null() {
        //given
        Type type=new Type();

        //when
        typeService.save(type);

        //then
        verify(typeRepository, times(0)).save(type);
    }

    @Test
    public void should_save_when_type_is_not_null() {
        //given
        //when
        Type type=mock(Type.class);
        when(type.getType()).thenReturn("保存好了");

        typeService.save(type);

        //then
        verify(typeRepository, times(1)).save(type);
    }
}