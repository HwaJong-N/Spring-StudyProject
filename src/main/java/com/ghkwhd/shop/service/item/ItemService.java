package com.ghkwhd.shop.service.item;

import com.ghkwhd.shop.domain.item.Item;
import com.ghkwhd.shop.repository.item.JpaItemRepository;
import com.ghkwhd.shop.dto.ItemSaveDTO;
import com.ghkwhd.shop.dto.ItemUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final JpaItemRepository itemRepository;

    // 등록
    public Item save(ItemSaveDTO dto) throws IOException {
        Item item = savedtoToItem(dto);
        return itemRepository.save(item);
    }


    // 조회
    public Item findById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }


    // 상품 수정 및 삭제
    public void editItem(Long id, ItemUpdateDTO dto) {
        Item updateItem = stringToItem(dto.getItemName(), dto.getPrice(), dto.getSeller(),
                dto.getContent(), dto.getThumbnailName(), dto.getThumbnailUUID(), dto.getAvgStar());
        itemRepository.update(id, updateItem);
    }

    public void deleteItem(Long id) {
        deleteImg(id);
        itemRepository.delete(id);
    }


    // 썸네일 수정 및 삭제
    public void editThumbnail(Long id, ItemSaveDTO dto) throws IOException {
        Item item = savedtoToItem(dto);
        itemRepository.update(id, item);
    }

    public void deleteThumbnail(Long id) {
        Item item = deleteImg(id);
        Item updateItem = stringToItem(item.getItemName(), item.getPrice(), item.getSeller(),
                item.getContent(), "", "defaultImage.jpg", item.getAvgStar());
        itemRepository.update(id, updateItem);
    }


    // 상품 썸네일 출력
    public Resource display(String filename) throws MalformedURLException {
        return new UrlResource("file:" + getFullPath(filename));
    }
    

    // ItemSaveDTO 의 데이터를 이용해 Item 객체 생성 및 반환
    private Item savedtoToItem(ItemSaveDTO dto) throws IOException {

        // 요청 메세지에서 MultipartFile 추출
        MultipartFile multipartFile = dto.getThumbnailName();

        Item item = new Item();
        item.setItemName(dto.getItemName());
        item.setPrice(dto.getPrice());
        item.setSeller(dto.getSeller());
        item.setContent(dto.getContent());
        item.setThumbnailName(multipartFile.getOriginalFilename());
        item.setThumbnailUUID(checkEmpty(multipartFile));
        item.setAvgStar(dto.getAvgStar());

        return item;
    }


    // 전달받은 String 을 이용해 Item 객체 생성 및 반환
    private Item stringToItem(String name, int price, String seller, String content, String thName, String thUUID, Double avgStar) {

        Item item = new Item();
        item.setItemName(name);
        item.setPrice(price);
        item.setSeller(seller);
        item.setContent(content);
        item.setThumbnailName(thName);
        item.setThumbnailUUID(thUUID);
        item.setAvgStar(avgStar);

        return item;
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


    // 프로젝트에서 사용하지 않는 이미지 제거하기
    private Item deleteImg(Long id) {
        // 상품의 id 를 전달받고 저장된 썸네일 이름을 추출
        Item item = itemRepository.findById(id).orElse(null);
        String storeFileName = item.getThumbnailUUID();

        // 썸네일 삭제 로직
        if (!storeFileName.equals("defaultImage.jpg")) {
            File file = new File(getFullPath(storeFileName));
            if (file.exists()) file.delete();
        }

        return item;
    }

}
