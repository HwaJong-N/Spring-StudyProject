package com.ghkwhd.shop.controller.item;

import com.ghkwhd.shop.service.item.ItemService;
import com.ghkwhd.shop.service.itemDTO.ItemSaveDTO;
import com.ghkwhd.shop.service.itemDTO.ItemUpdateDTO;
import com.ghkwhd.shop.domain.item.Item;
import com.ghkwhd.shop.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequestMapping("/item/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ReviewService reviewService;

    // 상품 목록 조회
    @GetMapping
    public String loadItems(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "item/items";
    }

    // 상품 상세 조회
    @GetMapping("/{itemId}")
    public String loadItem(@PathVariable Long itemId, Model model) {
        Item findItem = itemService.findById(itemId);
        model.addAttribute("item", findItem);
        model.addAttribute("reviews", reviewService.findAll(itemId));
        return "item/item";
    }


    // 상품 등록 폼 호출
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemSaveDTO());
        return "item/addForm";
    }

    // 상품 등록
    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveDTO dto, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "item/addForm";
        }

        Item savedItem = itemService.save(dto);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("addSuccess", true);
        return "redirect:/item/items/{itemId}";
    }


    // 상품 수정 폼 호출
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item findItem = itemService.findById(itemId);
        model.addAttribute("item", findItem);
        return "item/editForm";
    }


    // 상품 수정
    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId,
                           @Validated @ModelAttribute("item") ItemUpdateDTO dto,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "item/editForm";
        }

        itemService.editItem(itemId, dto);
        redirectAttributes.addAttribute("editSuccess", true);
        return "redirect:/item/items/{itemId}";
    }


    // 상품 삭제하기
    @GetMapping("/{itemId}/delete")
    public String deleteItem(@PathVariable Long itemId) {
        reviewService.deleteByItemId(itemId);
        itemService.deleteItem(itemId);
        return "redirect:/item/items";
    }


    // 썸네일 삭제
    @GetMapping("/{itemId}/edit/delete")
    public String deleteThumbnail(@PathVariable Long itemId) {

        itemService.deleteThumbnail(itemId);
        return "redirect:/item/items/{itemId}";
    }


    // 썸네일 수정 폼 호출
    @GetMapping("/{itemId}/edit/edit")
    public String editThumbnailForm(@PathVariable Long itemId, Model model) {
        Item findItem = itemService.findById(itemId);
        model.addAttribute("item", findItem);
        return "item/editThumbnail";
    }


    // 썸네일 수정
    @PostMapping("/{itemId}/edit/edit")
    public String editThumbnail(@PathVariable Long itemId,
                                @Validated @ModelAttribute("item") ItemSaveDTO dto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "item/editThumbnail";
        }

        itemService.editThumbnail(itemId, dto);
        return "redirect:/item/items/{itemId}";
    }


    // 이미지 출력하기
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource displayImage(@PathVariable String filename) throws MalformedURLException {
        return itemService.display(filename);
    }

}
