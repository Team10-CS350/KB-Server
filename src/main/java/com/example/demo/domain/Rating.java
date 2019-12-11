package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ratings")
public class Rating {
    @EmbeddedId
    CompositeRatingKey id;

    @ManyToOne
    @MapsId("rater_id")
    @JoinColumn(name = "rater_id")
    User rater;

    @ManyToOne
    @MapsId("ratee_id")
    @JoinColumn(name = "ratee_id")
    User ratee;

    private class CompositeRatingKey implements Serializable {
        @Column(name = "rater_id")
        private Long rater;

        @Column(name = "ratee_id")
        private Long ratee;
    }
}