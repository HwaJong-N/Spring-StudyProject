package com.ghkwhd.shop.repository.item;

import com.ghkwhd.shop.domain.item.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);
    Optional<Item> findById(Long id);
    List<Item> findAll();
    void update(Long id, Item updateItem);
    void delete(Long id);

    void updateAvgStar(Long id, Double avg);
}
