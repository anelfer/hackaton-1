package me.anelfer.centerkeys.component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.anelfer.centerkeys.db.model.HeadHunterEntity;
import me.anelfer.centerkeys.db.model.StackoverflowEntity;
import me.anelfer.centerkeys.db.model.TagEntity;
import me.anelfer.centerkeys.db.repository.HeadHunterRepository;
import me.anelfer.centerkeys.db.repository.TagsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@AllArgsConstructor
public class HeadHunterParser implements Parser {

    private final TagsRepository tagsRepository;
    private final HeadHunterRepository repository;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.HOURS)
    @Override
    public void parse() {
        try {
            //very opasny moment)
            List<TagEntity> allTags = tagsRepository.findAll();
            for (TagEntity allTag : allTags) {
                String tag = allTag.getTag();
                Document doc = Jsoup.connect("https://hh.ru/search/vacancy?only_with_salary=true&text=" + tag).get();
                log.info(doc.title());
                Elements items = doc.getElementsByClass("serp-item");
                for (Element item : items) {
                    Elements elementsByClass = item.getElementsByClass("serp-item__title");
                    String name = elementsByClass.text();
                    Elements elementsByAttributeValue = item.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation");
                    Element element = elementsByAttributeValue.get(0);
                    String text = element.text();
                    String[] splitPrice = text.split(" ");
                    String price = "";
                    if (text.startsWith("от")) {
                        price = splitPrice[1];
                    }
                    if (text.contains("–")) {
                        price = splitPrice[0];
                    }
                    if (price.isEmpty()) continue;
                    int priceInt = Integer.parseInt(price.replaceAll(" ",""));
                    String currency = splitPrice[splitPrice.length - 1];
                    HeadHunterEntity headHunterEntity = new HeadHunterEntity(tag, name, priceInt, currency);
                    repository.save(headHunterEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
