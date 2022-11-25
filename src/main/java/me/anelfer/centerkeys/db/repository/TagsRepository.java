package me.anelfer.centerkeys.db.repository;

import me.anelfer.centerkeys.db.model.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<TagEntity, Long> {

    TagEntity findByTag(String tag);

    boolean existsByTag(String tag);

}
