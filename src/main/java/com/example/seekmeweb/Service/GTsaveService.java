package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.SaveMe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GTsaveService extends JpaRepository<SaveMe, Integer> {
}
