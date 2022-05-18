package com.jarsoft.webapp.adverts.testtask.repositories;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.entity.LogEntity;
import org.springframework.data.repository.CrudRepository;

public interface LogRepository extends CrudRepository<LogEntity,Long> {
}
