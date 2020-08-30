package id.ac.umn.LiviaJessica.models;

public class Recipe {
    private String publisher;
    private Double social_rank;
    private String f2f_url;
    private String publisher_url;
    private String title;
    private String source_url;
    private String image_url;
    private Integer page;

    public Recipe(String publisher, Double social_rank, String f2f_url, String publisher_url, String title, String source_url, Integer page, String image_url) {
        this.publisher = publisher;
        this.social_rank = social_rank;
        this.f2f_url = f2f_url;
        this.publisher_url = publisher_url;
        this.title = title;
        this.source_url = source_url;
        this.page = page;
        this.image_url = image_url;
    }

    public String getPublisher() {
        return publisher;
    }

    public Double getSocial_rank() {
        return social_rank;
    }

    public String getF2f_url() {
        return f2f_url;
    }

    public String getPublisher_url() {
        return publisher_url;
    }

    public String getTitle() {
        return title;
    }

    public String getSource_url() {
        return source_url;
    }

    public Integer getPage() {
        return page;
    }

    public String getImage_url() {
        return image_url;
    }
}
