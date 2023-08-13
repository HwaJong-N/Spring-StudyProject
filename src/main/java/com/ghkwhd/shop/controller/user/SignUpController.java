package com.ghkwhd.shop.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghkwhd.shop.domain.user.User;
import com.ghkwhd.shop.dto.ResponseDTO;
import com.ghkwhd.shop.service.user.UserService;
import com.ghkwhd.shop.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final UserService userService;

    @GetMapping("/signUp")
    public String loadSignUpPage() {
        return "home/signUpHome";
    }

    @PostMapping("/signUp")
    @ResponseBody   // 화면도 같이 사용하기 때문에 @RestController 대신 @ResponseBody 사용
    public ResponseDTO<?> signUp(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMap.put("error-" + fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseDTO<>(errorMap, HttpStatus.BAD_REQUEST);
        }

        boolean isExist = userService.isExistId(userDTO.getId());
        if (isExist) {
            return new ResponseDTO<>("동일한 id 가 존재합니다", HttpStatus.CONFLICT); // 409 상태코드
        }
        User savedUser = userService.save(userDTO);
        return new ResponseDTO<>(savedUser, HttpStatus.OK);
    }
}
