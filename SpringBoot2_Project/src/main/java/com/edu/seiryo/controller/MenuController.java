package com.edu.seiryo.controller;

import com.edu.seiryo.model.RespBean;
import com.edu.seiryo.pojo.Menu;
import com.edu.seiryo.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单控制器
 * @author TianTian
 * @date 2022/1/14 15:40
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @RequestMapping("index")
    public String index() {
        return "menu/menu";
    }

    // 返回菜单树列表给前端 layui 表格
    @RequestMapping("list")
    @ResponseBody
    public List<Menu> list() {
        return menuService.queryMenuList();
    }

    // 添加子菜单
    @RequestMapping("add")
    @ResponseBody
    public RespBean add(Menu menu) {
        menuService.addMenu(menu);
        return RespBean.success("添加成功");
    }

    // 修改菜单
    @RequestMapping("update")
    @ResponseBody
    public RespBean update(Menu menu) {
        menuService.updateMenu(menu);
        return RespBean.success("修改成功");
    }

    // 删除菜单（含子节点校验）
    @RequestMapping("delete")
    @ResponseBody
    public RespBean delete(Integer id) {
        menuService.deleteMenu(id);
        return RespBean.success("删除成功");
    }
}