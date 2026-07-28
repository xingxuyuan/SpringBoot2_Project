package com.edu.seiryo.controller;

import com.edu.seiryo.model.GoodsModel;
import com.edu.seiryo.pojo.Goods;
import com.edu.seiryo.pojo.GoodsType;
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
 * @author TianTian
 * @date 2022/1/19 14:06
 */
@Controller
@RequestMapping("common")
public class CommonController {
    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private GoodsUnitService goodsUnitService;


    /**
     * 添加商品-选择商品页
     * @return
     */
    @RequestMapping("toSelectGoodsPage")
    public String toSelectGoodsPage(){
        return "common/goods";
    }

    @RequestMapping("toAddGoodsInfoPage")
    public String toGoodsInfoPage(Integer gid, Model model){
        // 1. 根据商品id查询商品
        Goods goods = goodsService.getById(gid);
        // 2. 补充商品单位
        goods.setUnitName(goods.getUnit());
        // 3. 补充商品类别
        if(goods.getTypeId() != null){
            GoodsType goodsType = goodsTypeService.getById(goods.getTypeId());
            if(goodsType != null){
                goods.setTypeName(goodsType.getName());
            }
        }
        // 4. 保存到页面
        model.addAttribute("goods", goods);
        return "common/goods_add_update";
    }


    /**
     修改商品-商品信息修改页(单价、进货数量
     * @param goodsModel
     * @param model
     * @return
     */
    @RequestMapping("toUpdateGoodsInfoPage")
    public String toUpdateGoodsInfoPage(GoodsModel goodsModel, Model model){
        // 1. 根据商品id查询商品基本信息
        Goods goods = goodsService.getById(goodsModel.getId());
        // 2. 补充商品单位
        goods.setUnitName(goods.getUnit());
        // 3. 补充商品类别
        if(goods.getTypeId() != null){
            GoodsType goodsType = goodsTypeService.getById(goods.getTypeId());
            if(goodsType != null){
                goods.setTypeName(goodsType.getName());
            }
        }
        // 4. 将本次进货信息传递给前端页面
        model.addAttribute("goods", goods);
        model.addAttribute("goodsModel", goodsModel);
        // 5. flag设置为1则调用更新，否则调用新增
        model.addAttribute("flag",1);
        return "common/goods_add_update";
    }


    /**
     * 当前库存页
     * @return
     */
    @RequestMapping("toGoodsStockPage")
    public String toGoodsStockPage() {
        return "common/stock_search";
    }



    @RequestMapping("stockList")
    @ResponseBody
    public Map<String,Object> stockLick(GoodsQuery goodsQuery){
        return null;
    }


    /**
     * 商品报损|报溢查询页
     * @return
     */
    @RequestMapping("toDamageOverflowSearchPage")
    public String toDamageOverflowSearchPage(){
        return "common/damage_overflow_search";
    }


    /**
     * 库存报警页
     * @return
     */
    @RequestMapping("alarmPage")
    public String alarmPage(){
        return "common/alarm";
    }


    /**
     * 库存报警查询接口
     * @param goodsQuery
     * @return
     */
    @RequestMapping("listAlarm")
    @ResponseBody
    public Map<String,Object> listAlarm(GoodsQuery goodsQuery){
        goodsQuery.setType(3);
        return null;
    }
}