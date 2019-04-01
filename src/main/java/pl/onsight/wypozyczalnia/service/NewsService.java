package pl.onsight.wypozyczalnia.service;

import pl.onsight.wypozyczalnia.model.Link;
import pl.onsight.wypozyczalnia.model.Pagination;
import pl.onsight.wypozyczalnia.model.entity.NewsEntity;

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

    void removeNews(Long id);
}
