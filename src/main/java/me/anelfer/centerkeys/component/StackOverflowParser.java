package me.anelfer.centerkeys.component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.anelfer.centerkeys.db.model.StackoverflowEntity;
import me.anelfer.centerkeys.db.model.TagEntity;
import me.anelfer.centerkeys.db.repository.StackoverflowRepository;
import me.anelfer.centerkeys.db.repository.TagsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
@AllArgsConstructor
public class StackOverflowParser implements Parser  {

    private final StackoverflowRepository repository;
    private final TagsRepository tagsRepository;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.HOURS)
    @Override
    public void parse() {
        try {
            Document doc = Jsoup.connect("https://stackoverflow.com/tags").get();
            log.info(doc.title());
            Elements newsHeadlines = doc.select(".grid--item");
            for (Element headline : newsHeadlines) {
                Optional<Element> tag = headline.getElementsByClass("post-tag").stream().findFirst();
                if (tag.isEmpty()) continue;
                Element tagElement = tag.get();
                String tagText = tagElement.text();
                System.out.println("tag = " + tagText);

                Elements statsBlock = headline.getElementsByClass("fs-caption");
                for (Element stats : statsBlock) {
                    Elements elementsByIndexEquals = stats.getElementsByIndexEquals(0);
                    Element allQuestions = elementsByIndexEquals.get(0);
                    Element questionsToday = elementsByIndexEquals.get(1);
                    Elements elementsByIndexEquals1 = stats.getElementsByIndexEquals(1);
                    Element questionsWeek = elementsByIndexEquals1.get(elementsByIndexEquals1.size() - 1);

                    int all = Integer.parseInt(allQuestions.text().split(" ")[0]);
                    int today = Integer.parseInt(questionsToday.text().split(" ")[0]);
                    int week = Integer.parseInt(questionsWeek.text().split(" ")[0]);

                    System.out.println("all questions = " + all);
                    System.out.println("questions today = " + today);
                    System.out.println("questionsWeek = " + week);

                    StackoverflowEntity entity = new StackoverflowEntity(tagText, all, week, today);
                    repository.save(entity);
                    boolean exists = tagsRepository.existsByTag(tagText);
                    if (!exists) tagsRepository.save(new TagEntity(tagText));
                }
                System.out.println("==========");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
