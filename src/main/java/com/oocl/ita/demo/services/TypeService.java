package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.Type;
import com.oocl.ita.demo.repositories.TypeRepository;
import org.apache.commons.lang.StringUtils;
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
        if(StringUtils.isEmpty(accountKind)){
            return typeRepository.findAll();
        }
        return typeRepository.findAllByAccountKind(accountKind);
    }

    public Type findTypeByTypeName(String type){
        List<Type> types = typeRepository.findTypeByType(type);
        if(types.isEmpty()) return null;
        return types.get(0);
    }

    public void save(Type type) {
        if(type.getType() == null) return ;
        typeRepository.save(type);
    }
}
