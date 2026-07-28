package com.edu.seiryo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.edu.seiryo.model.RespBean;
import com.edu.seiryo.pojo.PurchaseList;
import com.edu.seiryo.pojo.PurchaseListGoods;
import com.edu.seiryo.query.PurchaseListQuery;
import com.edu.seiryo.service.PurchaseListGoodsService;
import com.edu.seiryo.service.PurchaseListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进货单控制器
 * @author TianTian
 * @date 2022/1/19 12:32
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseListController {

    @Resource
    private PurchaseListService purchaseListService;

    @Resource
    private PurchaseListGoodsService purchaseListGoodsService;

    /**
     * 进货入库主页跳转（生成单号并传给前端）
     * 对应文档第 11 页要求
     */
    @RequestMapping("index")
    public String index(Model model){
        // 生成进货单号方法
        String purchaseNumber = purchaseListService.createPurchaseNumber();
        model.addAttribute("purchaseNumber", purchaseNumber);
        return "purchase/purchase";
    }

    /**
     * 进货单列表查询（分页）
     * 对应 Layui 表格数据请求
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> list(PurchaseListQuery purchaseListQuery){
        return this.purchaseListService.purchaseList(purchaseListQuery);
    }

    /**
     * 添加进货单保存方法
     * @param purchaseList 进货单基础信息
     * @param goodsJson 前端传过来的商品列表数据
     */
    @RequestMapping("save")
    @ResponseBody
    public RespBean save(PurchaseList purchaseList, String goodsJson){
        Gson gson = new Gson();
        // 将 JSON 字符串转换为 List<PurchaseListGoods> 对象
        List<PurchaseListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<PurchaseListGoods>>(){}.getType());
        purchaseListService.savePurchaseList(purchaseList, plgList);
        return RespBean.success("进货成功");
    }
    
    @RequestMapping("goodsList")
    @ResponseBody
    public Map<String, Object> goodsList(Integer purchaseListId){
        // 1. 封装查询条件
        Map<String, Object> result = new HashMap<>();
        if (purchaseListId == null) {
            result.put("code", 0);
            result.put("msg", "");
            result.put("count", 0);
            result.put("data", new ArrayList<>());
            return result;
        }
        
        // 2. 使用 MyBatis-Plus 根据 ID 查询商品明细
        QueryWrapper<PurchaseListGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("purchase_list_id", purchaseListId);
        List<PurchaseListGoods> goodsList = purchaseListGoodsService.list(queryWrapper);
        
        // 3. 封装 Layui 表格需要的返回格式
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", goodsList.size());
        result.put("data", goodsList);
        
        return result;
    }
}