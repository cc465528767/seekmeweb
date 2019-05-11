package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.Web;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebService extends JpaRepository<Web,Integer> {
    Web findById(int var1);
    Web findByUsername(String var1);
}
