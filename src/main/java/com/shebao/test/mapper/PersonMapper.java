package com.shebao.test.mapper;

import com.shebao.test.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonMapper extends JpaRepository<Person,Long> {

}
