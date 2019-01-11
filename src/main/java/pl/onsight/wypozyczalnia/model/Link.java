package pl.onsight.wypozyczalnia.model;

public class Link {

    private String name;
    private String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Link(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public Link() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link1 = (Link) o;

        if (name != null ? !name.equals(link1.name) : link1.name != null) return false;
        return link != null ? link.equals(link1.link) : link1.link == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }
}
