package XMLStorage.model;

import java.io.Serializable;

/**
 * Class to represent model of CD entity
 */
public class CDModel implements Serializable {

    private String title;

    private String artist;

    private String country;

    private String compony;

    private double price;

    private Integer year;

    public CDModel(String title, String artist, String country, String compony, double price, Integer year) {
        this.title = title;
        this.artist = artist;
        this.country = country;
        this.compony = compony;
        this.price = price;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompony() {
        return compony;
    }

    public void setCompony(String compony) {
        this.compony = compony;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "CDModel{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", country='" + country + '\'' +
                ", compony='" + compony + '\'' +
                ", price=" + price +
                ", year=" + year +
                '}';
    }
}
