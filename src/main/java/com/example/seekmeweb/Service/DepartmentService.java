package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DepartmentService extends JpaRepository<Department,Integer> {
    public Department findByName(String var1);
    public Department findById(int var1);
    public Department findByLevel(int var1);
    public Department findBySuperior(int var1);
    public List<Department> findAllById(int var1);
}
