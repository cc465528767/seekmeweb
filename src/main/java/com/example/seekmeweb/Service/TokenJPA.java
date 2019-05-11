package com.example.seekmeweb.Service;


import com.example.seekmeweb.Bean.TokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface TokenJPA extends JpaRepository<TokenInfo,String>,
        JpaSpecificationExecutor<TokenInfo> {
}
