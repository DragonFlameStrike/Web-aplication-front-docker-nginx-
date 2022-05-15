package com.jarsoft.webapp.adverts.testtask.repositories;


import com.jarsoft.webapp.adverts.testtask.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface CategoryRepository extends CrudRepository<CategoryEntity,Long> {
    String SELECT_CATEGORIES =
            "new com.jarsoft.webapp.adverts.testtask.entity.CategoryEntity(category.name,category.IdRequest,category.IdCategory)";

    String FIND_QUERY_FOR_VIEW_NOT_DELETED =
            "SELECT " + SELECT_CATEGORIES + "FROM CategoryEntity category " + "WHERE category.deleted = 0";
    @Query(value = FIND_QUERY_FOR_VIEW_NOT_DELETED)
    Iterable<CategoryEntity> findAllNotDeleted();


    String FIND_QUERY_FOR_VIEW_NOT_DELETED_BY_SEARCH =
            "SELECT " + SELECT_CATEGORIES + "FROM CategoryEntity category " + "WHERE category.deleted = 0 AND LOWER(category.name) LIKE Concat('%',:searchCategoryPattern,'%')";
    @Query(value = FIND_QUERY_FOR_VIEW_NOT_DELETED_BY_SEARCH)
    Iterable<CategoryEntity> findAllNotDeletedBySearch(@Param("searchCategoryPattern") String searchCategoryNameLowerCase);

    String FIND_QUERY_FOR_VIEW_NOT_DELETED_BY_NAME =
            "SELECT " + SELECT_CATEGORIES + "FROM CategoryEntity category " + "WHERE category.deleted = 0 AND category.name LIKE :searchCategoryName";
    @Query(value = FIND_QUERY_FOR_VIEW_NOT_DELETED_BY_NAME)
    Iterable<CategoryEntity> findAllNotDeletedByName(@Param("searchCategoryName") String name);

    String FIND_QUERY_FOR_VIEW_NOT_DELETED_BY_ID_REQUEST =
            "SELECT " + SELECT_CATEGORIES + "FROM CategoryEntity category " + "WHERE category.deleted = 0 AND category.IdRequest LIKE :searchCategoryIdRequest";
    @Query(value = FIND_QUERY_FOR_VIEW_NOT_DELETED_BY_ID_REQUEST)
    Iterable<CategoryEntity> findAllNotDeletedByIdRequest(@Param("searchCategoryIdRequest") String IdRequest);
}
