package com.edu.seiryo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.seiryo.mapper.GoodsMapper;
import com.edu.seiryo.pojo.Goods;
import com.edu.seiryo.pojo.GoodsType;
import com.edu.seiryo.query.GoodsQuery;
import com.edu.seiryo.service.GoodsService;
import com.edu.seiryo.service.GoodsTypeService;
import com.edu.seiryo.utils.PageResultUtil;
import com.edu.seiryo.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商品表实现类
 * @author TianTian
 * @date 2022/1/19 14:51
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Resource
    private GoodsTypeService goodsTypeService;

    @Override
    public Map<String, Object> goodsList(GoodsQuery goodsQuery) {
        // 1. 创建商品分页对象
        IPage<Goods> page = new Page<>(goodsQuery.getPage(), goodsQuery.getLimit());
        
        // 2. 创建商品查询条件
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        // 查询未删除商品
        queryWrapper.eq("is_del", 0);
        
        // 商品名称模糊查询
        if(StringUtil.isNotEmpty(goodsQuery.getGoodsName())){
            queryWrapper.like("name", goodsQuery.getGoodsName());
        }
        // 商品类别查询（左侧树状图点击时触发）
        if(null != goodsQuery.getTypeId()){
            queryWrapper.eq("type_id", goodsQuery.getTypeId());
        }
        
        // 3. MyBatis-Plus 分页查询
        page = this.baseMapper.selectPage(page, queryWrapper);
        
        for(Goods goods : page.getRecords()){
            // 补充单位
            goods.setUnitName(goods.getUnit());
            // 补充商品类别
            if(goods.getTypeId() != null){
                GoodsType goodsType = goodsTypeService.getById(goods.getTypeId());
                if(goodsType != null){
                    goods.setTypeName(goodsType.getName());
                }
            }
        }
        
        // 5. 返回 Layui 需要的数据格式
        return PageResultUtil.setResult(page.getTotal(), page.getRecords());
    }
}