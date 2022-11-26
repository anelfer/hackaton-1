package me.anelfer.centerkeys.db.repository;

import me.anelfer.centerkeys.db.model.HeadHunterEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HeadHunterRepository extends CrudRepository<HeadHunterEntity, Long> {

    @Query(value = "select avg(price) from headhunter where tag=?", nativeQuery = true)
    int getAveragePriceByTag(String tag);

    @Query(value = "select max(price) from headhunter where tag=?", nativeQuery = true)
    int getMaxPriceByTag(String tag);

    List<HeadHunterEntity> findAllByTag(String tag);

    List<HeadHunterEntity> findAllByTagIn(List<String> tag);

}
