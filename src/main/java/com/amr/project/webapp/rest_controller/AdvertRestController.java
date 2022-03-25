package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.AdvertMapper;

import com.amr.project.model.dto.AdvertDto;
import com.amr.project.model.entity.Advert;

import com.amr.project.model.entity.CartItem;
import com.amr.project.service.abstracts.AdvertService;
import com.amr.project.service.abstracts.CategoryService;
import com.amr.project.service.abstracts.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AdvertRestController {

    private final AdvertService advertService;
    private final AdvertMapper advertMapper;
    private final CategoryService categoryService;
    private final UserService userService;


    @ApiOperation(value = "Добавление объявления пользователя")
    @PostMapping("/advert/new")
    public ResponseEntity<?> addAdvert(@RequestBody AdvertDto advertDto, Principal principal ){
        Advert advert = advertMapper.dtoToAdvert(advertDto);
        if (advertDto.getName() == null)
            return ResponseEntity.badRequest().body("empty advert");
            advert.setUser(userService.findByUsername(principal.getName()));

        advertService.persist(advert);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Обновляет объявления пользователя")
    @PutMapping("/advert/update")
    public ResponseEntity<AdvertDto> updateItem(@RequestBody @NonNull AdvertDto advertDto) {
        Advert advert = advertMapper.dtoToAdvert(advertDto);
        if (advertDto.getName() == null || advertDto.getCategories() == null || advertDto.getCategoriesName() == null)
            return ResponseEntity.badRequest().build();
        advert.setId(advertDto.getId());
        advert.setUser(userService.findByUsername(advertDto.getUser().getUsername()));
        advert.setCategories(Arrays.stream(advertDto.getCategoriesName())
                .map(categoryService::getCategory)
                .collect(Collectors.toList()));
        advertService.update(advert);
        return ResponseEntity.ok().body(advertMapper.advertToDto(advert));

    }
    @GetMapping("advert/{id}")
    public ResponseEntity<AdvertDto> getAdvert(@PathVariable @NonNull Long id) {
        Advert advert = advertService.getByKey(id);
        return ResponseEntity.ok().body(advertMapper.advertToDto(advert));
    }

    @GetMapping("/adverts")
    public List<AdvertDto> getAllAdverts(){
        return advertService.getAllAdverts();
    }

    @ApiOperation(value = "Удаляет объект Advert по id")
    @DeleteMapping("advert/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable @NonNull Long id) {
        Advert advert = advertService.getByKey(id);
        if (advert == null)
            return ResponseEntity.badRequest().body("empty advert");
        advert.setPretendentToBeDeleted(true);
        advertService.update(advert);
        return ResponseEntity.ok().build();
    }

}
