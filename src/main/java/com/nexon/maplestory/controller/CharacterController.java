package com.nexon.maplestory.controller;

import com.nexon.maplestory.entity.Character;
import com.nexon.maplestory.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping("/")
    public String home() {
        return "home"; // home.html 템플릿
    }

    @PostMapping("/search")
    public String searchCharacter(@RequestParam("characterName") String characterName,
                                  @RequestParam("apiKey") String apiKey, Model model) {
        // 입력 검증
        if(apiKey == null || apiKey.trim().isEmpty() || characterName == null || characterName.trim().isEmpty()){
            model.addAttribute("error", "API 키와 캐릭터 이름을 모두 입력해주세요.");
            return "home";
        }

        // 캐릭터 이름 형식 검증 (예: 허용된 문자만 포함)
        if(!characterName.matches("^[a-zA-Z0-9가-힣]{1,20}$")){
            model.addAttribute("error", "캐릭터 이름 형식이 올바르지 않습니다.");
            return "home";
        }

        try {
            Character character = characterService.getOrCreateCharacter(apiKey, characterName);
            model.addAttribute("character", character);
            return "result"; // result.html 템플릿
        } catch (Exception e) {
            model.addAttribute("error", "캐릭터 정보를 불러오는 데 실패했습니다: " + e.getMessage());
            return "home";
        }
    }
}