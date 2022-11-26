package me.anelfer.centerkeys.db.repository;

import me.anelfer.centerkeys.db.model.StackoverflowEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StackoverflowRepository extends JpaRepository<StackoverflowEntity, Long> {

    List<StackoverflowEntity> findAllByTag(String tag);

    List<StackoverflowEntity> findByTagAndTimestampBetween(String tag, Date timestamp, Date timestamp2);

    @Query(value = "select tag, max(today) as today, max(id) as id, max(week) as week, max(total) as total, " +
            "max(timestamp) as timestamp " +
            "from (select * from stackoverflow where extract(day from timestamp) = extract(day from now())" +
            " and now() - timestamp < interval '2 days' order by total desc) as abc group by tag order by total desc limit 10;",
            nativeQuery = true)
    List<StackoverflowEntity> findTop10ByTotal();

    @Query(value = "select tag, max(today) as today, max(id) as id, max(week) as week, max(total) as total, " +
            "max(timestamp) as timestamp " +
            "from (select * from stackoverflow where extract(day from timestamp) = extract(day from now())" +
            " and now() - timestamp < interval '2 days' order by week desc) as abc group by tag order by week desc limit 10;",
            nativeQuery = true)
    List<StackoverflowEntity> findTop10ByWeek();

    @Query(value = "select tag, max(today) as today, max(id) as id, max(week) as week, max(total) as total, " +
            "max(timestamp) as timestamp " +
            "from (select * from stackoverflow where extract(day from timestamp) = extract(day from now())" +
            " and now() - timestamp < interval '2 days' order by today desc) as abc group by tag order by today desc limit 10;",
            nativeQuery = true)
    List<StackoverflowEntity> findTop10ByToday();

}
