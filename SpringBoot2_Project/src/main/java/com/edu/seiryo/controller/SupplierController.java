package com.edu.seiryo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.seiryo.model.RespBean;
import com.edu.seiryo.pojo.Supplier;
import com.edu.seiryo.query.SupplierQuery;
import com.edu.seiryo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.rmi.MarshalledObject;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

    @RequestMapping("index" )
    public String index(){
        return "/supplier/supplier";
    }
}
