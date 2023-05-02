package com.ghkwhd.shop.controller.item;

import com.ghkwhd.shop.domain.item.Item;
import com.ghkwhd.shop.repository.item.JpaItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/item/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final JpaItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "item/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        itemRepository.findById(itemId).ifPresent(
                item -> model.addAttribute("item", item)
        );
        return "item/item";
    }
}
