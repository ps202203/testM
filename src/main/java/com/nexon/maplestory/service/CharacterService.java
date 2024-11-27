package com.nexon.maplestory.service;

import com.nexon.maplestory.entity.Character;
import com.nexon.maplestory.entity.User;
import com.nexon.maplestory.repository.CharacterRepository;
import com.nexon.maplestory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NexonApiService nexonApiService;

    public Character getOrCreateCharacter(String apiKey, String characterName) {
        // 먼저 데이터베이스에서 캐릭터를 검색
        Optional<Character> optionalCharacter = characterRepository.findByCharacterName(characterName);
        if(optionalCharacter.isPresent()){
            return optionalCharacter.get();
        }

        // 데이터베이스에 캐릭터가 없으면 API 호출
        JSONObject characterInfo = nexonApiService.getCharacterInfo(apiKey, characterName);

        // 사용자 조회
        User user = userRepository.findByApiKey(apiKey);
        if(user == null){
            // 사용자가 없으면 새로 생성
            user = new User();
            user.setApiKey(apiKey);
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
        }

        // 캐릭터 엔티티 생성
        Character character = new Character();
        character.setCharacterName(characterInfo.getString("characterName"));
        character.setLevel(characterInfo.getInt("level"));
        character.setClassType(characterInfo.getString("classType"));
        character.setUser(user);
        character.setUpdatedAt(LocalDateTime.now());

        // 데이터베이스에 저장
        return characterRepository.save(character);
    }

    // 캐릭터 정보 업데이트 메서드 (선택 사항)
    public void updateCharacter(Character character) {
        // API 호출하여 최신 정보 가져오기
        JSONObject characterInfo = nexonApiService.getCharacterInfo(character.getUser().getApiKey(), character.getCharacterName());

        // 정보 업데이트
        character.setLevel(characterInfo.getInt("level"));
        character.setClassType(characterInfo.getString("classType"));
        character.setUpdatedAt(LocalDateTime.now());

        // 데이터베이스에 저장
        characterRepository.save(character);
    }
}