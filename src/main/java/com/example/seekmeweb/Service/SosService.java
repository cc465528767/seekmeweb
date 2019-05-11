package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.Sos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SosService extends JpaRepository<Sos, Integer> {
    public List<Sos> findBySeekerId(String seekerId);
    public List<Sos> findByHelperId(String helperId);

    @Query(value = "select * from tb_sos sos where seeker_id=?1 and helper_id is not null "
            , nativeQuery = true)

    public List<Sos> findSeekerIdList(String seekerId);

    @Query(value = "select * from tb_sos user where sos_id=?1 and helper_id is null"
            , nativeQuery = true)
    public Sos findBySosid(String sosid1);
}
