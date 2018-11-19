package pl.sda.javapoz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.sda.javapoz.model.News;

import java.util.List;

/**
 * Created by RENT on 2017-03-22.
 */
public interface NewsRepository extends CrudRepository<News, Long> {

    List<News> findNewsByTag(String tag);


    @Query(value="SELECT * FROM News n WHERE n.id >= ?1*5-4 AND n.id <=?1*5", nativeQuery = true)
    List<News> findByNews(Integer newsPage);


}
