package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.RealLoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RealLocService extends JpaRepository<RealLoc, Integer> {
    @Query(value = "select * from tb_loc  where jobnum <> ?1 and belong =  ?2 "
            , nativeQuery = true)
    public List<RealLoc> findBelongList(String jobnum,int belong);
    public RealLoc findByUserId(String userid);

}
