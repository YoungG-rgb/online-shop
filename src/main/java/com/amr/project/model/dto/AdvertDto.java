package com.amr.project.model.dto;


import com.amr.project.model.entity.User;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class AdvertDto {
    private Long id;
    private String name;
    private String description;
    private int price;
    private String email;
    private List<CategoryDto>  categories;
    private User user;
    private String[] categoriesName;
    private boolean isPretendentToBeDeleted;
    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;
    private List<ImageDto> images;
}
