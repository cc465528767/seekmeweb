package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserService extends JpaRepository<User, Integer> {
    //通过手机1来查询
    public User findByPhone1(String phone1);
    public User findByToken(String token);
    public User findByUserId(String userid);
    public List<User> findByBelongAndLeaf(int belong,int leaf);


//    select * from tb_user user where belong in (select id from tb_department  where superior  =  131)
    @Query(value = "select * from tb_user user where belong in (select id from tb_department  where superior  =  ?1)"
            , nativeQuery = true)
    public List<User> getBelongMember(int val1);

    @Query(value = "select * from tb_user where belong = ?1 and leaf =1", nativeQuery = true)
    public List<User> getBelongAndLeaf1(int val2);
}
