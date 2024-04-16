package com.victor.kochnev.dal.repository.jpa;

import com.victor.kochnev.dal.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagEntityRepository extends JpaRepository<TagEntity, UUID> {

    @Query("""
            select t from TagEntity t
            where t.data in :dataList
            """)
    List<TagEntity> findAllByData(@Param("dataList") List<String> dataList);
}
