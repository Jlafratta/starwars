package com.conexia.starwars.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FilmDTO extends BaseEntity {
    private List<String> characters;
    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;
    private Date created;
    private Date edited;
    private String producer;
    private String title;
    private String episodeId;
    private String director;
    private String releaseDate;
    private String openingCrawl;

    @JsonProperty("episode_id")
    public void setEpisodeid(String episodeId) {
        this.episodeId = episodeId;
    }

    @JsonProperty("release_date")
    public void setReleasedate(String releasedate) {
        this.releaseDate = releasedate;
    }

    @JsonProperty("opening_crawl")
    public void setOpeningcrawl(String openingCrawl) {
        this.openingCrawl = openingCrawl;
    }
}
