package me.anelfer.centerkeys.controller;

import lombok.AllArgsConstructor;
import me.anelfer.centerkeys.db.model.HabrEntity;
import me.anelfer.centerkeys.db.model.HeadHunterEntity;
import me.anelfer.centerkeys.db.model.StackoverflowEntity;
import me.anelfer.centerkeys.db.model.TagEntity;
import me.anelfer.centerkeys.service.MainService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api")
public class MainController {

    private final MainService service;

    @GetMapping("/info")
    public Map<String, List<StackoverflowEntity>> getInfo(
            @RequestParam List<String> tag,
            @RequestParam(required = false) String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return service.getEntities(tag,
                date != null ? LocalDateTime.parse(date, dateTimeFormatter) : null,
                LocalDateTime.now());
    }

    @GetMapping("/tags")
    public List<TagEntity> getTags(@RequestParam(required = false) Integer limit) {
        return service.getTags(limit == null ? 10 : limit);
    }

    @GetMapping("/top")
    public List<StackoverflowEntity> getTop(@RequestParam String by) {
        return service.getTop(by);
    }

    @GetMapping("/vacancy")
    public Map<String, List<HeadHunterEntity>> getVacancy(@RequestParam List<String> tag) {
        return service.getVacancies(tag);
    }

    @GetMapping("/vacancy/average")
    public int getAvgPriceVacancy(@RequestParam String tag) {
        return service.getAvgPriceVacancy(tag);
    }

    @GetMapping("/vacancy/max")
    public int getMaxPriceVacancy(@RequestParam String tag) {
        return service.getMaxPriceVacancy(tag);
    }

    @GetMapping("/news")
    public Map<String, List<HabrEntity>> getNews(@RequestParam List<String> tag) {
        return service.getNews(tag);
    }

}
