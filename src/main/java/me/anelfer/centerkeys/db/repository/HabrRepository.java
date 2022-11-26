package me.anelfer.centerkeys.db.repository;

import me.anelfer.centerkeys.db.model.HabrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabrRepository extends JpaRepository<HabrEntity, Long> {

    List<HabrEntity> findAllByTagIn(List<String> tag);

    List<HabrEntity> findAllByTagAndTitle(String tag, String title);

}
