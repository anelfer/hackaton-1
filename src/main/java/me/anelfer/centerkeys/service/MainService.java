package me.anelfer.centerkeys.service;

import lombok.AllArgsConstructor;
import me.anelfer.centerkeys.db.model.StackoverflowEntity;
import me.anelfer.centerkeys.db.model.TagEntity;
import me.anelfer.centerkeys.db.repository.StackoverflowRepository;
import me.anelfer.centerkeys.db.repository.TagsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class MainService {

    private final StackoverflowRepository repository;
    private final TagsRepository tagsRepository;

    public List<StackoverflowEntity> getEntities(String tag, LocalDateTime startTime, LocalDateTime endTime) {
        return startTime == null ? repository.findAllByTag(tag) :
                repository.findByTagAndTimestampBetween(
                        tag,
                        Date.from(startTime.toInstant(ZoneOffset.UTC)),
                        Date.from(endTime.toInstant(ZoneOffset.UTC)));
    }

    public List<TagEntity> getTags(int limit) {
        Iterable<TagEntity> all = tagsRepository.findAll(PageRequest.of(0, limit));
        ArrayList<TagEntity> tagList = new ArrayList<>();
        all.forEach(tagList::add);
        return tagList;
    }

}
