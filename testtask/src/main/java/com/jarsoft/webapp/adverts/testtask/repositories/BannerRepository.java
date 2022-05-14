package com.jarsoft.webapp.adverts.testtask.repositories;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface BannerRepository extends CrudRepository<BannerEntity,Long> {
    String SELECT_BANNERS =
            "new com.jarsoft.webapp.adverts.testtask.entity.BannerEntity(banner.name,banner.price,banner.text,banner.IdBanner)";


    String FIND_QUERY_FOR_VIEW_NOT_DELETED =
            "SELECT " + SELECT_BANNERS + "FROM BannerEntity banner " + "WHERE banner.deleted = 0";
    @Query(value = FIND_QUERY_FOR_VIEW_NOT_DELETED)
    Iterable<BannerEntity> findAllNotDeleted();


    String FIND_QUERY_FOR_VIEW_NOT_DELETED_BY_NAME =
            "SELECT " + SELECT_BANNERS + "FROM BannerEntity banner " + "WHERE banner.deleted = 0 AND LOWER(banner.name) LIKE Concat('%',:searchBannerName,'%')";
    @Query(value = FIND_QUERY_FOR_VIEW_NOT_DELETED_BY_NAME)
    Iterable<BannerEntity> findAllNotDeletedByName(@Param("searchBannerName") String searchBannerNameLowerCase);

}
