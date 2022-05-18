package com.jarsoft.webapp.adverts.testtask.repositories;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.entity.LogEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LogRepository extends CrudRepository<LogEntity,Long> {

    String SELECT_LOGS =
            "new com.jarsoft.webapp.adverts.testtask.entity.LogEntity(log.ipAddress,log.userAgent,log.time,log.idBanner,log.noContentReason)";

    String FIND_QUERY =
            "SELECT " + SELECT_LOGS + "FROM LogEntity log " + "WHERE log.ipAddress LIKE :remoteAddr AND log.userAgent LIKE :userAgent";
    @Query(value = FIND_QUERY)
    Iterable<LogEntity> findAllByIp(@Param("remoteAddr") String remoteAddr,@Param("userAgent") String userAgent);
}
