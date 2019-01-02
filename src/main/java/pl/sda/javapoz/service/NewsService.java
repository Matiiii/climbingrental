package pl.sda.javapoz.service;

import pl.sda.javapoz.model.Link;
import pl.sda.javapoz.model.NewsEntity;
import pl.sda.javapoz.model.Pagination;

import java.util.List;
import java.util.Set;

public interface NewsService {
    List<NewsEntity> findAllNews();

    NewsEntity findNewsById(Long id);

    List<NewsEntity> findFiveNews(Integer page);

    Pagination getPaginationForPage(Integer page);

    Set<Link> findAllTag();

    List<NewsEntity> findNewsByTag(String tag);

    void addNews(NewsEntity news);
}
