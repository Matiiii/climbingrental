package pl.sda.javapoz.model;

public class Pagination {

    private Integer page;
    private boolean previousPage;
    private boolean nextPage;

    public Pagination() {
    }

    public Pagination(Integer page, boolean previousPage, boolean nextPage) {
        this.page = page;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public boolean isPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(boolean previousPage) {
        this.previousPage = previousPage;
    }

    public boolean isNextPage() {
        return nextPage;
    }

    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }
}
