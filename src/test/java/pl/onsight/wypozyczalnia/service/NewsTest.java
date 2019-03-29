package pl.onsight.wypozyczalnia.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.onsight.wypozyczalnia.model.entity.NewsEntity;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class NewsTest {

  @Autowired
  private NewsService newsService;


  @Test
  @Transactional
  public void shouldReverseListOfNews() {
    //given
  /*  NewsEntity news1 = new NewsEntity();
    news1.setTitle("Artur");
    news1.setDescription("asdasd");
    news1.setLink("sadasdasda");
    news1.setTag("artur");

    NewsEntity news2 = new NewsEntity();
    news2.setTitle("Adam");
    news2.setDescription("asdasd");
    news2.setLink("sadasdasda");
    news2.setTag("adam");

    newsService.addNews(news1);
    newsService.addNews(news2);*/
    //when
    List<NewsEntity> newsBeforeReverse = newsService.findAllNews();
    Collections.reverse(newsBeforeReverse);

    //then
    assertThat(newsBeforeReverse.size()).isEqualTo(2);
    assertThat(newsBeforeReverse.get(newsBeforeReverse.size() - 1).getTitle()).isEqualTo("2");
  }


}
