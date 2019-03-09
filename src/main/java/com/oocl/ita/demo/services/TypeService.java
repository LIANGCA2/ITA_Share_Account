package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.Type;
import com.oocl.ita.demo.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> getAllTypes(String accountKind) {
        return typeRepository.findAllByAccountKind(accountKind);
    }
}
