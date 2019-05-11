package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.ClientCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ClientIdService extends JpaRepository<ClientCard, Integer> {
//    public List<ClientCard> findByUserId(String userid);
    public ClientCard findByUserId(String userid);

    @Query(value = "select * from tb_client  where user_id <> ?1 and length(clientid)>5 "
            , nativeQuery = true)
    public ArrayList<ClientCard> getList(String myid);
}
