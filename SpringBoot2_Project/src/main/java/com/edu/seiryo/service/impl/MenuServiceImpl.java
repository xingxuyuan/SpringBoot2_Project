package com.edu.seiryo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.seiryo.mapper.MenuMapper;
import com.edu.seiryo.pojo.Menu;
import com.edu.seiryo.service.MenuService;
import com.edu.seiryo.utils.AssertUtil;
import com.edu.seiryo.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> queryMenuList() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        return this.list(queryWrapper);
    }

    @Override
    @Transactional
    public void addMenu(Menu menu) {
        AssertUtil.isTrue(StringUtil.isEmpty(menu.getName()), "菜单名称为空");
        if (menu.getPId() == null) {
            menu.setPId(1); // 默认为根菜单，假设根菜单ID为1
        }
        // 自动判定层级：父级层级 + 1
        Menu parent = this.getById(menu.getPId());
        if (parent != null) {
            // 最多三级菜单限制
            AssertUtil.isTrue(parent.getGrade() < 2, "最多只能添加三级菜单");
            menu.setGrade(parent.getGrade() + 1);
        } else {
            menu.setGrade(0); // 自己作为一级
        }
        AssertUtil.isTrue(!this.save(menu), "添加菜单失败");
    }

    @Override
    @Transactional
    public void updateMenu(Menu menu) {
        AssertUtil.isTrue(menu.getId() == null, "请选择要修改的菜单");
        AssertUtil.isTrue(StringUtil.isEmpty(menu.getName()), "菜单名称为空");
        
        Menu oldMenu = this.getById(menu.getId());
        // 如果更改了父级，需要重新校验层级
        if (menu.getPId() != null && !menu.getPId().equals(oldMenu.getPId())) {
            Menu parent = this.getById(menu.getPId());
            if (parent != null) {
                menu.setGrade(parent.getGrade() + 1);
                // 递归更新子菜单层级，避免违反树形结构
                updateChildMenuGrade(menu.getId(), menu.getGrade());
            }
        }
        AssertUtil.isTrue(!this.updateById(menu), "修改菜单失败");
    }

    // 递归更新子菜单层级
    private void updateChildMenuGrade(Integer parentId, Integer parentGrade) {
        List<Menu> childMenus = this.list(new QueryWrapper<Menu>().eq("p_id", parentId));
        if (childMenus != null && !childMenus.isEmpty()) {
            for (Menu child : childMenus) {
                child.setGrade(parentGrade + 1);
                this.updateById(child);
                // 递归处理子级的子级
                updateChildMenuGrade(child.getId(), child.getGrade());
            }
        }
    }

    @Override
    @Transactional
    public void deleteMenu(Integer id) {
        AssertUtil.isTrue(id == null, "请选择要删除的菜单");
        
        long childCount = this.count(new QueryWrapper<Menu>().eq("p_id", id).eq("is_del", 0));
        AssertUtil.isTrue(childCount > 0, "该菜单下有子菜单，无法删除");
        // 逻辑删除
        Menu menu = this.getById(id);
        menu.setIsDel(1);
        AssertUtil.isTrue(!this.updateById(menu), "菜单删除失败");
    }
}