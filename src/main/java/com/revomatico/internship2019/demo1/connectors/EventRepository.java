package com.revomatico.internship2019.demo1.connectors;

import com.revomatico.internship2019.demo1.readers.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, String> {
}