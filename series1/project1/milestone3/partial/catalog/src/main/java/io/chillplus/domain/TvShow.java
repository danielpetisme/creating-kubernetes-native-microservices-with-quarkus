package io.chillplus.domain;


import javax.validation.constraints.NotBlank;

public class TvShow {

    public Long id;

    @NotBlank
    public String title;

    public String category;
}
