package com.edu.seiryo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.seiryo.dto.TreeDto;
import com.edu.seiryo.mapper.GoodsTypeMapper;
import com.edu.seiryo.pojo.GoodsType;
import com.edu.seiryo.service.GoodsTypeService;
import com.edu.seiryo.utils.AssertUtil;
import com.edu.seiryo.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {

    @Override
    public List<TreeDto> queryAllGoodsTypes(Integer typeId) {
        List<GoodsType> goodsTypeList = this.list(new QueryWrapper<>());
        List<TreeDto> treeDtoList = new ArrayList<>();
        for (GoodsType goodsType : goodsTypeList) {
            TreeDto treeDto = new TreeDto();
            treeDto.setId(goodsType.getId());
            treeDto.setPid(goodsType.getPId());
            treeDto.setName(goodsType.getName());
            treeDtoList.add(treeDto);
        }
        return treeDtoList;
    }

    @Override
    @Transactional
    public void addGoodsType(GoodsType goodsType) {
        AssertUtil.isTrue(StringUtil.isEmpty(goodsType.getName()), "分类名称为空");
        if (goodsType.getPId() == null) {
            goodsType.setPId(-1);
        }
        goodsType.setState(0);
        AssertUtil.isTrue(!this.save(goodsType), "添加分类失败");
    }

    @Override
    @Transactional
    public void updateGoodsType(GoodsType goodsType) {
        AssertUtil.isTrue(goodsType.getId() == null, "请选择要修改的分类");
        AssertUtil.isTrue(StringUtil.isEmpty(goodsType.getName()), "分类名称为空");
        AssertUtil.isTrue(!this.updateById(goodsType), "修改分类失败");
    }

    @Override
    @Transactional
    public void deleteGoodsType(Integer id) {
        AssertUtil.isTrue(id == null, "请选择要删除的分类");
        QueryWrapper<GoodsType> childWrapper = new QueryWrapper<>();
        childWrapper.eq("p_id", id);
        long childCount = this.count(childWrapper);
        AssertUtil.isTrue(childCount > 0, "该分类下存在子分类，无法删除");
        AssertUtil.isTrue(!this.removeById(id), "删除分类失败");
    }
}