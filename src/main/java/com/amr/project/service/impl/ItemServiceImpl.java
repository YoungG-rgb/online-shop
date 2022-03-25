package com.amr.project.service.impl;

import com.amr.project.converter.ItemMapper;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.inserttestdata.repository.ItemRepository;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.CartItem;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Mail;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.email.EmailSenderService;
import com.amr.project.util.TrackedEmailItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl extends ReadWriteServiceImpl<Item, Long> implements ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final ItemDao itemDao;
    private final TrackedEmailItem trackedEmailItem;
    private final EmailSenderService emailSenderService;

    @Autowired
    protected ItemServiceImpl(ItemMapper itemMapper, ItemRepository itemRepository, ItemDao itemDao, TrackedEmailItem trackedEmailItem, EmailSenderService emailSenderService) {
        super(itemDao);
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
        this.itemDao = itemDao;
        this.emailSenderService = emailSenderService;
        this.trackedEmailItem = trackedEmailItem;
    }

    @Override
    @Transactional
    public List<Item> getUnmoderatedItems() {
        return itemDao.getUnmoderatedItems();
    }

    @Override
    @Transactional
    public List<Item> getModeratedItems() {
        return itemDao.getModeratedItems();
    }

    @Override
    @Transactional
    public Item getItemName(String nameItem) {
        return itemDao.getItemName(nameItem);
    }

    @Override
    @Transactional
    public void update(Item item) {
        Mail mail = trackedEmailItem.trackedEmailItemUpdateDelete(item);
        if (mail.getMessage() != null)
            emailSenderService.sendSimpleEmail(mail);
        itemDao.update(item);
    }

    @Override
    @Transactional
    public void persist(Item item) {
        emailSenderService.sendSimpleEmail(trackedEmailItem.trackedEmailItemPersist(item));
        itemDao.persist(item);
    }

    @Override
    @Transactional
    public List<Item> getMostPopular(int quantity) {
        return itemDao.getMostPopular(quantity);
    }

    @Override
    @Transactional
    public Item getItemId(long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public ItemDto getItemDtoId(long id) {
        return itemMapper.itemToDto(itemRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public List<ItemDto> getAllItemsRatingSort() {
        List<Item> itemList = itemRepository.findAllByOrderByRatingDesc();
        return itemList.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Item> getAllItem(User user) {
        List<Long> list = user.getCartItems().stream().map(CartItem::getId).sorted().collect(Collectors.toList());
        List<Item> itemsList = new ArrayList<>();
        for (Long id : list) {
            itemsList.add(itemRepository.findById(id).orElse(null));
        }
        return itemsList;
    }

    @Override
    @Transactional
    public List<ItemDto> getItemsByCategoryId(Long id) {
        return itemDao.getItemsByCategoryId(id).stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }
}
