package br.com.Kostylll.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "author", nullable = false, length = 30)
    private String author;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "launch_date", nullable = false, length = 30)
    private Date launch_date;

    @Column(name = "price", nullable = false, length = 30)
    private Long price;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    public Books() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return getId() == books.getId() && Objects.equals(getAuthor(), books.getAuthor()) && Objects.equals(getLaunch_date(), books.getLaunch_date()) && Objects.equals(getPrice(), books.getPrice()) && Objects.equals(getTitle(), books.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getLaunch_date(), getPrice(), getTitle());
    }
}
