package pl.sda.javapoz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.sda.javapoz.model.Link;
import pl.sda.javapoz.model.News;
import pl.sda.javapoz.model.Pagination;
import pl.sda.javapoz.repository.NewsRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class NewsService {

    private NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> findAllNews() {
        List<News> newsList = new LinkedList<>();
        newsRepository.findAll().forEach(newsList::add);
        return newsList;
    }

    public News findNewsById(Long id) {
        return newsRepository.findOne(id);
    }

    public List<News> findFiveNews(Integer page) {
        return newsRepository.findByNews(page);
    }

    public Pagination getPaginationForPage(Integer page) {
        long pages = ((newsRepository.count() - 1) / 5) + 1;
        Pagination pagination = new Pagination();
        pagination.setNextPage(page < pages);
        pagination.setPreviousPage(page != 1);
        pagination.setPage(page);
        return pagination;
    }

    public Set<Link> findAllTag() {
        List<News> news = findAllNews();

        return news.stream()
                .map(News::getTag)
                .map(n -> new Link(StringUtils.capitalize(n), "/?tag=" + n))
                .collect(Collectors.toSet());
    }

    public List<News> findNewsByTag(String tag) {
        return newsRepository.findNewsByTag(tag);
    }
}
