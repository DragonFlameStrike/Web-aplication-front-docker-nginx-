package com.jarsoft.webapp.adverts.testtask.repositories;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface BannerRepository extends CrudRepository<BannerEntity,Long> {
}
