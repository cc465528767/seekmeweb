package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.Trace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TraceService extends JpaRepository<Trace, Integer> {
     public List<Trace> findByUserId(String userid);

}
