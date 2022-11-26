package me.anelfer.centerkeys.service;

import lombok.AllArgsConstructor;
import me.anelfer.centerkeys.db.model.HeadHunterEntity;
import me.anelfer.centerkeys.db.model.StackoverflowEntity;
import me.anelfer.centerkeys.db.model.TagEntity;
import me.anelfer.centerkeys.db.repository.HeadHunterRepository;
import me.anelfer.centerkeys.db.repository.StackoverflowRepository;
import me.anelfer.centerkeys.db.repository.TagsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class MainService {

    private final StackoverflowRepository repository;
    private final TagsRepository tagsRepository;
    private final HeadHunterRepository headHunterRepository;

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

    public List<StackoverflowEntity> getTop(String by) {
        return switch (by) {
            case "total" -> repository.findTop10ByTotal();
            case "week" -> repository.findTop10ByWeek();
            case "today" -> repository.findTop10ByToday();
            default -> new ArrayList<>();
        };
    }

    public List<HeadHunterEntity> getVacancies(String tag) {
        return headHunterRepository.findAllByTag(tag);
    }

    public int getAvgPriceVacancy(String tag) {
        return headHunterRepository.getAveragePriceByTag(tag);
    }

}
