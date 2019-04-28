package pl.onsight.wypozyczalnia.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.onsight.wypozyczalnia.model.Link;
import pl.onsight.wypozyczalnia.model.Pagination;
import pl.onsight.wypozyczalnia.model.entity.NewsEntity;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class NewsTest {

    @Autowired
    private NewsService newsService;


    @Test
    @Transactional
    public void shouldFindNewsById() {
        //given
        List<NewsEntity> allNews = newsService.findAllNews();
        //when
        Long id1 = 1L;
        Long id2 = 2L;
        NewsEntity news1 = newsService.findNewsById(id1);
        NewsEntity news2 = newsService.findNewsById(id2);
        //then
        assertThat(news1).isNotNull();
        assertThat(news2).isNotNull();
        assertThat(allNews.get(0).getId()).isEqualTo(news2.getId());
        assertThat(allNews.get(1).getId()).isEqualTo(news1.getId());
        assertThat(news1).isNotEqualTo(news2);
    }

    @Test
    @Transactional
    public void shouldRemoveNews() {
        //given
        List<NewsEntity> newsBeforeRemoving = newsService.findAllNews();

        //when
        newsService.removeNews(newsService.findNewsById(1L).getId());
        List<NewsEntity> newsAfterRemoving = newsService.findAllNews();

        NewsEntity news = new NewsEntity();
        news.setTitle("3");
        news.setDescription("3");
        news.setLink("3");
        news.setTag("3");
        newsService.addNews(news);

        List<NewsEntity> newsAfterAdding = newsService.findAllNews();
        //then
        assertThat(newsBeforeRemoving).isNotEmpty();
        assertThat(newsAfterRemoving.size()).isNotEqualTo(newsBeforeRemoving.size());
        assertThat(newsAfterAdding).isNotEmpty();

    }

    @Test
    @Transactional
    public void shouldReverseListOfNews() {
        //given
        // data are pull here from MySQL database
        //when
        List<NewsEntity> newsBeforeReverse = newsService.findAllNews();
        Collections.reverse(newsBeforeReverse);

        //then
        assertThat(newsBeforeReverse.size()).isEqualTo(2);
        assertThat(newsBeforeReverse.get(newsBeforeReverse.size() - 1).getTitle()).isEqualTo("2");
    }


    @Test
    @Transactional
    public void shouldFindAllNews() {
        //given
        // data are pull here from MySQL database
        //when
        List<NewsEntity> allNews = newsService.findAllNews();
        //then
        assertThat(allNews.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void shouldFiveNewsPerPage() {
        //given

        NewsEntity news3 = new NewsEntity();
        news3.setTitle("3");
        news3.setDescription("3");
        news3.setLink("3");
        news3.setTag("3");

        NewsEntity news4 = new NewsEntity();
        news4.setTitle("4");
        news4.setDescription("4");
        news4.setLink("4");
        news4.setTag("4");

        NewsEntity news5 = new NewsEntity();
        news5.setTitle("5");
        news5.setDescription("5");
        news5.setLink("5");
        news5.setTag("5");

        NewsEntity news6 = new NewsEntity();
        news6.setTitle("6");
        news6.setDescription("6");
        news6.setLink("6");
        news6.setTag("6");


        newsService.addNews(news3);
        newsService.addNews(news4);
        newsService.addNews(news5);
        newsService.addNews(news6);
        Integer page = 1;
        List<NewsEntity> allNews = newsService.findAllNews();

        //when

        List<NewsEntity> newsPerPage = newsService.findFiveNews(page);
        //then
        assertThat(allNews.size()).isEqualTo(6);
        assertThat(newsPerPage.size()).isNotEqualTo(allNews.size());
        assertThat(newsPerPage.size()).isEqualTo(5);
    }


    @Test
    @Transactional
    public void shouldGetPaginationForPage() {
        //given
        Pagination pagination = new Pagination();
        Integer page = 2;
        //when
        Pagination pagination1 = newsService.getPaginationForPage(page);
        //then
        assertThat(pagination1).isNotNull();
        assertThat(pagination1).isNotEqualTo(pagination);
    }

    @Test
    @Transactional
    public void shouldGetAllTags() {
        //given
        //when
        Set<Link> setOfTags = newsService.findAllTag();
        Long id1 = 1L;
        String news1tag = "/?tag=" + newsService.findNewsById(id1).getTag();

        //then
        assertThat(setOfTags).isNotNull();
        assertThat(setOfTags.size()).isEqualTo(2);
        assertThat(setOfTags.contains(news1tag));
    }

    @Test
    @Transactional
    public void shouldFindNewsByTag() {
        //given
        List<NewsEntity> allNews = newsService.findAllNews();
        String tag = "1";
        //when
        List<NewsEntity> newsByTag = newsService.findNewsByTag(tag);
        //then
        assertThat(newsByTag).isNotEmpty();
        assertThat(allNews).isNotEqualTo(newsByTag);

    }
}
