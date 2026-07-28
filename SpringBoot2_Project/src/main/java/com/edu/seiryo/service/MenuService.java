package com.edu.seiryo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.seiryo.pojo.Menu;

import java.util.List;

/**
 * 菜单表服务类
 * @author TianTian
 * @date 2022/1/19 13:57
 */
public interface MenuService extends IService<Menu> {
    List<Menu> queryMenuList();
    void addMenu(Menu menu);
    void updateMenu(Menu menu);
    void deleteMenu(Integer id);
}