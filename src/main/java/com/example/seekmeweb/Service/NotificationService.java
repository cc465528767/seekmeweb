package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationService extends JpaRepository<Notification, Integer> {

}
