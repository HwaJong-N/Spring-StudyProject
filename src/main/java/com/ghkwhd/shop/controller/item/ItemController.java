package com.ghkwhd.shop.controller.item;

import com.ghkwhd.shop.controller.itemDTO.ItemSaveDTO;
import com.ghkwhd.shop.controller.itemDTO.ItemUpdateDTO;
import com.ghkwhd.shop.domain.item.Item;
import com.ghkwhd.shop.repository.item.JpaItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

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
        model.addAttribute("item", new ItemSaveDTO());
        return "item/addForm";
    }

    // 상품 등록
    @PostMapping("/add")
    public String addItem(@ModelAttribute("item") ItemSaveDTO dto,
                          RedirectAttributes redirectAttributes) throws IOException {

        // 요청 메세지에서 MultipartFile 추출
        MultipartFile multipartFile = dto.getThumbnailName();

        Item item = new Item();
        item.setItemName(dto.getItemName());
        item.setPrice(dto.getPrice());
        item.setSeller(dto.getSeller());
        item.setContent(dto.getContent());
        item.setThumbnailName(multipartFile.getOriginalFilename());
        item.setThumbnailUUID(checkEmpty(multipartFile));

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
                           RedirectAttributes redirectAttributes) throws IOException {

        Item updateItem = new Item();
        updateItem.setItemName(dto.getItemName());
        updateItem.setPrice(dto.getPrice());
        updateItem.setSeller(dto.getSeller());
        updateItem.setContent(dto.getContent());
        updateItem.setThumbnailName(dto.getThumbnailName());
        updateItem.setThumbnailUUID(dto.getThumbnailUUID());

        itemRepository.update(itemId, updateItem);

        redirectAttributes.addAttribute("updateStatus", true);

        return "redirect:/item/items/{itemId}";
    }


    // 썸네일 삭제
    @GetMapping("/{itemId}/edit/delete")
    public String deleteThumbnail(@PathVariable Long itemId) {

        Item findItem = itemRepository.findById(itemId).orElse(null);
        deleteImg(findItem.getThumbnailUUID());

        Item updateItem = new Item();
        updateItem.setItemName(findItem.getItemName());
        updateItem.setPrice(findItem.getPrice());
        updateItem.setSeller(findItem.getSeller());
        updateItem.setContent(findItem.getContent());
        updateItem.setThumbnailName("");
        updateItem.setThumbnailUUID("defaultImage.jpg");

        itemRepository.update(itemId, updateItem);

        return "redirect:/item/items/{itemId}";
    }


    // 썸네일 수정 폼 호출
    @GetMapping("/{itemId}/edit/edit")
    public String editThumbnailForm(@PathVariable Long itemId, Model model) {
        itemRepository.findById(itemId).ifPresent(
                item -> model.addAttribute("item", item)
        );
        return "item/editThumbnail";
    }


    // 썸네일 수정
    @PostMapping("/{itemId}/edit/edit")
    public String editThumbnail(@PathVariable Long itemId,
                                @ModelAttribute("item") ItemSaveDTO dto,
                                RedirectAttributes redirectAttributes) throws IOException {

        Item findItem = itemRepository.findById(itemId).orElse(null);
        deleteImg(findItem.getThumbnailUUID());

        // 기존 상품 삭제
        itemRepository.delete(itemId);

        // 요청 메세지에서 MultipartFile 추출
        MultipartFile multipartFile = dto.getThumbnailName();

        Item item = new Item();
        item.setItemName(dto.getItemName());
        item.setPrice(dto.getPrice());
        item.setSeller(dto.getSeller());
        item.setContent(dto.getContent());
        item.setThumbnailName(multipartFile.getOriginalFilename());
        item.setThumbnailUUID(checkEmpty(multipartFile));

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("editThumbnailStatus", true);

        return "redirect:/item/items/{itemId}";
    }


    // 상품 삭제하기
    @GetMapping("/{itemId}/delete")
    public String deleteItem(@PathVariable Long itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        deleteImg(item.getThumbnailUUID());
        itemRepository.delete(itemId);
        return "redirect:/item/items";
    }





    // 썸네일이 업로드 되었는지 확인
    private String checkEmpty(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return "defaultImage.jpg";
        } else {
            return saveImg(multipartFile);
        }
    }


    // 저장파일명 + 확장자
    private String createStoreName(String originalFilename) {
        String ext = originalFilename.substring(
                originalFilename.lastIndexOf(".") + 1);
        UUID uuid = UUID.randomUUID();
        return uuid + "." + ext;
    }


    // 파일 저장 경로 + 파일명
    private String getFullPath(String storeFileName) {
        String thumbnailDir = "/src/main/resources/static/thumbnail/";
        String path = System.getProperty("user.dir") + thumbnailDir;
        return path + storeFileName;
    }


    // 썸네일 저장 메서드
    private String saveImg(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));  // 파일 저장
        return storeFileName;
    }


    // 이미지 출력하기
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + getFullPath(filename));
    }


    // 프로젝트에서 사용하지 않는 이미지 제거하기
    private void deleteImg(String storeFileName) {
        if (!storeFileName.equals("defaultImage.jpg")) {
            File file = new File(getFullPath(storeFileName));
            if (file.exists()) file.delete();
        }
    }

}
