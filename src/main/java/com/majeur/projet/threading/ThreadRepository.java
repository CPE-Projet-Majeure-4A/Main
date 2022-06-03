package com.majeur.projet.threading;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ThreadRepository extends CrudRepository<ThreadEntity, Integer> {

    public List<ThreadEntity> findByName(String name);
}

