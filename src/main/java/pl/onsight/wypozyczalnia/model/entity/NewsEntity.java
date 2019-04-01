package pl.onsight.wypozyczalnia.model.entity;

import org.hibernate.validator.constraints.NotEmpty;
import pl.onsight.wypozyczalnia.model.listeners.InsertListener;
import pl.onsight.wypozyczalnia.model.listeners.UpdateListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "news")
@EntityListeners({ UpdateListener.class, InsertListener.class })
public class NewsEntity extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    private String link;
    private String tag;

    public NewsEntity() {
    }

    public NewsEntity(String title, String description, String link, String tag) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
