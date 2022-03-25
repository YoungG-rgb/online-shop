package com.amr.project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import springfox.documentation.annotations.ApiIgnore;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "advert")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiIgnore
@Builder
@ToString
public class Advert {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int price;

    @Column
    private String email;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<Category> categories;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> images;

    private boolean isPretendentToBeDeleted;
    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;
}
