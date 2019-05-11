package com.example.seekmeweb.Controller;

import com.example.seekmeweb.Bean.Department;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.security.PrivateKey;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping({"/department"})
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    private DepartmentController() {
    }

    @RequestMapping({"/test"})
    @ResponseBody
    public String test() {
        return "hello test";
    }

    @RequestMapping({"/departmentFindByName"})
    @ResponseBody
    public String test1(HttpServletRequest httpServletRequest){
        String name=httpServletRequest.getParameter("name").trim();
        Department exam=this.departmentService.findByName(name);
        if(exam!=null)
            return Result.create(1,"yes",exam);
        else return Result.create(0,name,null);
    }

    @RequestMapping({"/departmentAll"})
    @ResponseBody
    public List<Department> departmentList() {
        return departmentService.findAll();
    }

    @RequestMapping({"/departmentInsert"})
    @ResponseBody
    public String departmentinsert(HttpServletRequest httpServletRequest) {
        String name = httpServletRequest.getParameter("name");
        String superior = httpServletRequest.getParameter("superior");
        Department Superior = this.departmentService.findByName(superior);
        Department same = this.departmentService.findByName(name);
        if (Superior == null) return Result.create(0, "1", null);
        else if (same != null) return Result.create(0, "2", null);
        else {
            try {
                Department department = new Department();
                department.setName(name);
                department.setSuperior(Superior.getId());
                department.setLevel(Superior.getLevel() + 1);
                department.setId(1000);
                departmentService.save(department);
//                return "!";
                return Result.create(1, "3", null);
            } catch (Exception e) {
                return Result.create(0, "4", null);
            }
        }
    }

    @RequestMapping({"/departmentInsertFirst"})
    @ResponseBody
    public String departmentinsertfirst(HttpServletRequest httpServletRequest) {
        String name = httpServletRequest.getParameter("name");
//        String superior = httpServletRequest.getParameter("superior");
//        Department Superior = this.departmentService.findByName(superior);
//        Department same = this.departmentService.findByName(name);
//        if (Superior == null) return Result.create(0, "1", null);
//        else if (same != null) return Result.create(0, "2", null);
        try {
            Department department = new Department();
            department.setName(name);
            department.setSuperior(1);
            department.setLevel(1);
            department.setId(1);
            departmentService.save(department);
//                return "!";
            return Result.create(1, "3", null);
        } catch (Exception e) {
            return Result.create(0, "4", null);
        }
    }


    @RequestMapping({"/update/department"})
    @ResponseBody
    public String updatedepartment(HttpServletRequest httpServletRequest) {
        String name = httpServletRequest.getParameter("name");
        int superior = Integer.parseInt(httpServletRequest.getParameter("superior"));
        int id=Integer.parseInt(httpServletRequest.getParameter("id"));

        Department exam=this.departmentService.findById(id);
        if(exam!=null){
            Department S=this.departmentService.findById(superior);
            if(S==null)
            {
                return Result.create(0,String.valueOf(superior) , null);
            }
            exam.setName(name);
            exam.setSuperior(superior);
            int i=S.getLevel();
            i++;
            exam.setLevel(i);
            this.departmentService.save(exam);
            return Result.create(1, "修改成功", null);
        }
        return Result.create(0, "修改失败", null);

    }
}
