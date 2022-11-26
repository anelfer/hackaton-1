package me.anelfer.centerkeys.service;

import lombok.AllArgsConstructor;
import me.anelfer.centerkeys.db.model.HabrEntity;
import me.anelfer.centerkeys.db.model.HeadHunterEntity;
import me.anelfer.centerkeys.db.model.StackoverflowEntity;
import me.anelfer.centerkeys.db.model.TagEntity;
import me.anelfer.centerkeys.db.repository.HabrRepository;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MainService {

    private final StackoverflowRepository repository;
    private final TagsRepository tagsRepository;
    private final HeadHunterRepository headHunterRepository;
    private final HabrRepository habrRepository;

    public Map<String, List<StackoverflowEntity>> getEntities(List<String> tag, LocalDateTime startTime, LocalDateTime endTime) {
        return startTime == null ? repository.findAllByTagIn(tag)
                .stream()
                .collect(Collectors.groupingBy(StackoverflowEntity::getTag)) :
                repository.findByTagInAndTimestampBetween(
                                tag,
                                Date.from(startTime.toInstant(ZoneOffset.UTC)),
                                Date.from(endTime.toInstant(ZoneOffset.UTC)))
                        .stream()
                        .collect(Collectors.groupingBy(StackoverflowEntity::getTag));
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

    public Map<String, List<HeadHunterEntity>> getVacancies(List<String> tag) {
        return headHunterRepository.findAllByTagIn(tag).stream().collect(Collectors.groupingBy(HeadHunterEntity::getTag));
    }

    public int getAvgPriceVacancy(String tag) {
        return headHunterRepository.getAveragePriceByTag(tag);
    }

    public int getMaxPriceVacancy(String tag) {
        return headHunterRepository.getMaxPriceByTag(tag);
    }

    public Map<String, List<HabrEntity>> getNews(List<String> tag) {
        return habrRepository.findAllByTagIn(tag).stream().collect(Collectors.groupingBy(HabrEntity::getTag));
    }

}
