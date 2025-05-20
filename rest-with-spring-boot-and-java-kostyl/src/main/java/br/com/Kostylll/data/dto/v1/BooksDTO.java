package br.com.Kostylll.data.dto.v1;

import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.Objects;

public class BooksDTO extends RepresentationModel<BooksDTO> {

    private long id;
    private String title;
    private String author;
    private Date launch_date;
    private long price;

    public BooksDTO() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(Date launch_date) {
        this.launch_date = launch_date;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BooksDTO booksDTO = (BooksDTO) o;
        return getId() == booksDTO.getId() && getPrice() == booksDTO.getPrice() && Objects.equals(getTitle(), booksDTO.getTitle()) && Objects.equals(getAuthor(), booksDTO.getAuthor()) && Objects.equals(getLaunch_date(), booksDTO.getLaunch_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getTitle(), getAuthor(), getLaunch_date(), getPrice());
    }
}
