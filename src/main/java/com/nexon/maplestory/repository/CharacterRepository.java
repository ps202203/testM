package com.nexon.maplestory.repository;

import com.nexon.maplestory.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    Optional<Character> findByCharacterName(String characterName);
}