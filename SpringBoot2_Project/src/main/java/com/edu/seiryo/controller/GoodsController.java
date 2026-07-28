package com.edu.seiryo.controller;

import com.edu.seiryo.model.RespBean;
import com.edu.seiryo.pojo.Goods;
import com.edu.seiryo.query.GoodsQuery;
import com.edu.seiryo.service.GoodsService;
import com.edu.seiryo.service.GoodsTypeService;
import com.edu.seiryo.service.GoodsUnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商品控制器
 * @author TianTian
 * @date 2022/1/18 22:50
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private GoodsUnitService goodsUnitService;

    /**
     * 进入商品管理主页面
     */
    @RequestMapping("index")
    public String index() {
        return "goods/goods";
    }

    /**
     * 分页查询商品列表（对应文档第6页）
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> list(GoodsQuery goodsQuery) {
        return goodsService.goodsList(goodsQuery);
    }

    /**
     * 跳转到添加商品页面（对应文档第6页）
     */
    @RequestMapping("toAddGoodsPage")
    public String toAddGoodsPage(Model model) {
        // 加载所有商品类别和单位，供前端下拉框使用
        model.addAttribute("goodsTypes", goodsTypeService.list());
        model.addAttribute("goodsUnits", goodsUnitService.list());
        return "goods/goods_add";
    }

    /**
     * 执行添加商品操作
     */
    @RequestMapping("add")
    @ResponseBody
    public RespBean add(Goods goods) {
        // 默认新建商品库存为0
        goods.setInventoryQuantity(0);
        goodsService.save(goods);
        return RespBean.success("商品添加成功");
    }

    /**
     * 跳转到修改商品页面（对应文档第8页）
     */
    @RequestMapping("toUpdateGoodsPage")
    public String toUpdateGoodsPage(Integer id, Model model) {
        // 回显当前商品信息
        model.addAttribute("goods", goodsService.getById(id));
        // 加载所有商品类别和单位
        model.addAttribute("goodsTypes", goodsTypeService.list());
        model.addAttribute("goodsUnits", goodsUnitService.list());
        return "goods/goods_update";
    }

    /**
     * 执行修改商品操作
     */
    @RequestMapping("update")
    @ResponseBody
    public RespBean update(Goods goods) {
        goodsService.updateById(goods);
        return RespBean.success("商品修改成功");
    }

    /**
     * 执行删除商品操作（对应文档第8页）
     */
    @RequestMapping("delete")
    @ResponseBody
    public RespBean delete(Integer id) {
        // 逻辑删除：将 isDel 设为 1
        Goods goods = goodsService.getById(id);
        if (goods != null) {
            goods.setIsDel(1);
            goodsService.updateById(goods);
        }
        return RespBean.success("商品删除成功");
    }
}