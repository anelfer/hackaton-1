package me.anelfer.centerkeys.db.repository;

import me.anelfer.centerkeys.db.model.StackoverflowEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface StackoverflowRepository extends CrudRepository<StackoverflowEntity, Long> {

    List<StackoverflowEntity> findAllByTag(String tag);

    List<StackoverflowEntity> findByTagAndTimestampBetween(String tag, Date timestamp, Date timestamp2);

}
