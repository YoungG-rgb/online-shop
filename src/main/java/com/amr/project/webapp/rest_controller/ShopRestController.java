package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.inserttestdata.repository.UserRepository;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shop_api")
@AllArgsConstructor
@Validated
public class ShopRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final ShopMapper shopMapper;
    private final ShopService shopService;
    private final ShopMapper shopConverter;

    @GetMapping("/{name}")
    public ResponseEntity<ShopDto> getShop(@PathVariable @NonNull String name) {
        Shop shop = shopService.getShop(name);
        return ResponseEntity.ok().body(shopConverter.shopToDto(shop));
    }

    @PostMapping("/")
    @ApiOperation(value = "Добавляет новый магазин")
    public ResponseEntity<ShopDto> addShop(Principal principal, @Valid @RequestBody ShopDto shopDto) {
        Shop shop = shopMapper.dtoToShop(shopDto);
        User user = userService.findByUsername(principal.getName());
        shop.setUser(user);
        List<Shop> shops = new ArrayList<>(user.getShops());
        shops.add(shop);
        user.setShops(shops);
        shopService.persist(shop);
        userService.update(user);
        LOGGER.info("Магазин с id: " + shop.getId() + " успешно добавлен");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/shops")
    public List<ShopDto> getAllShop() {
        return shopService.getAllShopsRatingSort();
    }

    @GetMapping("/shop/{id}")
    public ShopDto shop(@PathVariable("id") long id) {
        return shopService.getShopId(id);
    }
}
