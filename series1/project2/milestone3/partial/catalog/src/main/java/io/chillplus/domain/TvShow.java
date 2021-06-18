package io.chillplus.domain;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tv_show")
public class TvShow extends PanacheEntity {

    @NotBlank
    @Column(name = "title", nullable = false)
    public String title;

    @Column(name = "category")
    public String category;

    public static List<TvShow> findAllOrderByTitle() {
        return new ArrayList<>();
    }
    public static TvShow findByTitle() {
        return null;
    }

    public static List<TvShow> findByCategoryIgnoreCase() {
        return new ArrayList<>();
    }
}
