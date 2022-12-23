package com.shebao.test.test.mapStruct;

import com.shebao.test.model.entity.Person;
import com.shebao.test.model.entity.Person1;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapStruct {
    PersonMapStruct INSTANT = Mappers.getMapper(PersonMapStruct.class);
//    @Mapping(target = "id", source = "id")
    Person1 pTop1(Person person);
}
