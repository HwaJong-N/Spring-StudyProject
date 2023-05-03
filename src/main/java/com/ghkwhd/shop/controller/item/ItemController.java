package com.ghkwhd.shop.controller.item;

import com.ghkwhd.shop.controller.itemDTO.ItemSaveDTO;
import com.ghkwhd.shop.controller.itemDTO.ItemUpdateDTO;
import com.ghkwhd.shop.domain.item.Item;
import com.ghkwhd.shop.repository.item.JpaItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/item/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final JpaItemRepository itemRepository;

    // 상품 목록 조회
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "item/items";
    }

    // 상품 상세 조회
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        itemRepository.findById(itemId).ifPresent(
                item -> model.addAttribute("item", item)
        );
        return "item/item";
    }

    // 상품 등록 폼 호출
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "item/addForm";
    }

    // 상품 등록
    @PostMapping("/add")
    public String addItem(@ModelAttribute("item") ItemSaveDTO dto,
                          RedirectAttributes redirectAttributes) {
        Item item = new Item();
        item.setItemName(dto.getItemName());
        item.setPrice(dto.getPrice());
        item.setSeller(dto.getSeller());
        item.setContent(dto.getContent());

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("saveStatus", true);

        return "redirect:/item/items/{itemId}";
    }


    // 상품 수정 폼 호출
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        itemRepository.findById(itemId).ifPresent(
                item -> model.addAttribute("item", item)
        );
        return "item/editForm";
    }


    // 상품 수정
    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId,
                           @ModelAttribute("item") ItemUpdateDTO dto,
                           RedirectAttributes redirectAttributes) {

        Item updateItem = new Item();
        updateItem.setItemName(dto.getItemName());
        updateItem.setPrice(dto.getPrice());
        updateItem.setSeller(dto.getSeller());
        updateItem.setContent(dto.getContent());

        itemRepository.update(itemId, updateItem);

        redirectAttributes.addAttribute("updateStatus", true);

        return "redirect:/item/items/{itemId}";
    }
}
