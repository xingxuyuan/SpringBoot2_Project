package com.edu.seiryo.controller;

import com.edu.seiryo.dto.TreeDto;
import com.edu.seiryo.model.RespBean;
import com.edu.seiryo.pojo.GoodsType;
import com.edu.seiryo.service.GoodsTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品分类控制器
 * @author TianTian
 * @date 2022/1/20 14:49
 */
@Controller
@RequestMapping("/goodsType")
public class GoodsTypeController {

    @Resource
    private GoodsTypeService goodsTypeService;

    /**
     * 进入商品分类管理主页
     */
    @RequestMapping("index")
    public String index() {
        return "goodsType/goodsType";
    }

    /**
     * 获取左侧分类树的数据
     */
    @RequestMapping("queryAllGoodsTypes")
    @ResponseBody
    public List<TreeDto> queryAllGoodsTypes(Integer typeId) {
        return goodsTypeService.queryAllGoodsTypes(typeId);
    }

    /**
     * 添加商品子类
     */
    @RequestMapping("add")
    @ResponseBody
    public RespBean add(GoodsType goodsType) {
        goodsTypeService.addGoodsType(goodsType);
        return RespBean.success("添加分类成功");
    }

    /**
     * 更新分类名称或图标
     */
    @RequestMapping("update")
    @ResponseBody
    public RespBean update(GoodsType goodsType) {
        goodsTypeService.updateGoodsType(goodsType);
        return RespBean.success("更新分类成功");
    }

    /**
     * 删除分类
     */
    @RequestMapping("delete")
    @ResponseBody
    public RespBean delete(Integer id) {
        goodsTypeService.deleteGoodsType(id);
        return RespBean.success("删除分类成功");
    }
}