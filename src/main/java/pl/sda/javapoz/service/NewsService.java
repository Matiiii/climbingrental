package pl.sda.javapoz.service;

import pl.sda.javapoz.model.Link;
import pl.sda.javapoz.model.News;
import pl.sda.javapoz.model.Pagination;

import java.util.List;
import java.util.Set;

public interface NewsService {
    List<News> findAllNews();

    News findNewsById(Long id);

    List<News> findFiveNews(Integer page);

    Pagination getPaginationForPage(Integer page);

    Set<Link> findAllTag();

    List<News> findNewsByTag(String tag);

    void addNews(News news);
}
