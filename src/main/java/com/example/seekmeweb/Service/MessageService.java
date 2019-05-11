package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageService extends JpaRepository<Message,Integer> {
    public Message findById(String id);

    @Query(value = "select * from tb_message where sentid = :sentid and receiveid = :receiveid and type= :type", nativeQuery = true)
    public List<Message> getRecord(@Param("sentid") String val1,
                                   @Param("receiveid") String val2,@Param("type") int a);
}
