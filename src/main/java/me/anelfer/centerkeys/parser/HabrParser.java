package me.anelfer.centerkeys.parser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.anelfer.centerkeys.db.model.HabrEntity;
import me.anelfer.centerkeys.db.model.TagEntity;
import me.anelfer.centerkeys.db.repository.HabrRepository;
import me.anelfer.centerkeys.db.repository.TagsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@AllArgsConstructor
public class HabrParser implements Parser {

    private final HabrRepository habrRepository;
    private final TagsRepository tagsRepository;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.HOURS)
    @Override
    public void parse() {
        log.info("Attempt to parse Habr");
        try {
            //very opasny moment)
            List<TagEntity> allTags = tagsRepository.findAll();
            for (TagEntity allTag : allTags) {
                String tag = allTag.getTag();
                Document doc = Jsoup.connect("https://habr.com/ru/search/?q=" + tag + "&target_type=posts&order=date").get();
                Elements items = doc.getElementsByClass("tm-articles-list__item");
                for (Element item : items) {
                    Elements titleWithUrl = item.getElementsByClass("tm-article-snippet__title-link");
                    Elements timePosted = item.getElementsByClass("tm-article-snippet__datetime-published");
                    Elements textElement = item.getElementsByClass("article-formatted-body");

                    String href = titleWithUrl.attr("href");
                    href = "habr.com" + href;
                    String title = titleWithUrl.text();
                    String text = textElement.text();
                    String time = timePosted.text();

                    HabrEntity habrEntity = new HabrEntity(tag, title, text, href, time);
                    List<HabrEntity> allByTagAndTitle = habrRepository.findAllByTagAndTitle(tag, title);
                    if (!allByTagAndTitle.isEmpty()) continue;
                    habrRepository.save(habrEntity);
                }
            }
            log.info("Habr parsed successfully");
        } catch (IOException e) {
            log.error("Error while parsing Habr", e);
        }
    }

}
