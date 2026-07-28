package com.edu.seiryo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.seiryo.mapper.PurchaseListMapper;
import com.edu.seiryo.pojo.Goods;
import com.edu.seiryo.pojo.PurchaseList;
import com.edu.seiryo.pojo.PurchaseListGoods;
import com.edu.seiryo.query.PurchaseListQuery;
import com.edu.seiryo.service.GoodsService;
import com.edu.seiryo.service.PurchaseListGoodsService;
import com.edu.seiryo.service.PurchaseListService;
import com.edu.seiryo.utils.AssertUtil;
import com.edu.seiryo.utils.PageResultUtil;
import com.edu.seiryo.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 进货单 服务实现类
 * </p>
 *
 * @author 老李
 */
@Service
public class PurchaseListServiceImpl extends ServiceImpl<PurchaseListMapper, PurchaseList> implements PurchaseListService {

    @Resource
    private GoodsService goodsService;

    @Resource
    private PurchaseListGoodsService purchaseListGoodsService;

    // 1. 生成进货单号
    @Override
    public String createPurchaseNumber() {
        // 获取当前日期 yyyyMMdd
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 查询当天已有多少进货单
        QueryWrapper<PurchaseList> wrapper = new QueryWrapper<>();
        wrapper.likeRight("purchase_number", "JH" + date);
        int count = this.count(wrapper);
        // 生成三位流水号
        String serialNumber = String.format("%03d", count + 1);
        return "JH" + date + serialNumber;
    }

    // 2. 分页查询进货单列表
    @Override
    public Map<String, Object> purchaseList(PurchaseListQuery purchaseListQuery) {
        IPage<PurchaseList> page = new Page<>(purchaseListQuery.getPage(), purchaseListQuery.getLimit());
        QueryWrapper<PurchaseList> queryWrapper = new QueryWrapper<>();
        
        // 查询未删除的记录
        queryWrapper.eq("is_del", 0);
        
        // 单号模糊查询
        if (StringUtil.isNotEmpty(purchaseListQuery.getPurchaseNumber())) {
            queryWrapper.like("purchase_number", purchaseListQuery.getPurchaseNumber());
        }
        // 按供应商筛选
        if (purchaseListQuery.getSupplierId() != null) {
            queryWrapper.eq("supplier_id", purchaseListQuery.getSupplierId());
        }
        // 按日期范围筛选
        if (StringUtil.isNotEmpty(purchaseListQuery.getStartDate())) {
            queryWrapper.ge("purchase_date", purchaseListQuery.getStartDate());
        }
        if (StringUtil.isNotEmpty(purchaseListQuery.getEndDate())) {
            queryWrapper.le("purchase_date", purchaseListQuery.getEndDate());
        }

        // MyBatis-Plus 分页查询
        page = this.baseMapper.selectPage(page, queryWrapper);
        return PageResultUtil.setResult(page.getTotal(), page.getRecords());
    }

    // 3. 保存进货单
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePurchaseList(PurchaseList purchaseList, List<PurchaseListGoods> plgList) {
        AssertUtil.isTrue(purchaseList.getSupplierId() == null, "请选择供应商");
        AssertUtil.isTrue(purchaseList.getAmountPayable() == null, "应付金额为空");
        AssertUtil.isTrue(purchaseList.getAmountPaid() == null, "实付金额为空");
        AssertUtil.isTrue(purchaseList.getPurchaseDate() == null, "日期为空");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        AssertUtil.isTrue(0 != formatter.format(purchaseList.getPurchaseDate()).compareTo(formatter.format(date)), "请选择本日");

        AssertUtil.isTrue(!(this.save(purchaseList)), "进货单添加失败!");
        PurchaseList temp = this.getOne(new QueryWrapper<PurchaseList>().eq("purchase_number", purchaseList.getPurchaseNumber()));
        AssertUtil.isTrue(plgList == null, "请选择商品");

        plgList.forEach(plg -> {
            plg.setPurchaseListId(temp.getId());
            Goods goods = goodsService.getById(plg.getGoodsId());
            goods.setInventoryQuantity(goods.getInventoryQuantity() + plg.getNum());
            goods.setState(2);
            AssertUtil.isTrue(!(goodsService.updateById(goods)), "更新商品库存失败!");
            AssertUtil.isTrue(!(purchaseListGoodsService.save(plg)), "进货单商品记录添加失败!");
        });
    }

    // 4. 删除进货单
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePurchaseList(Integer id) {
        AssertUtil.isTrue(!(purchaseListGoodsService.remove(new QueryWrapper<PurchaseListGoods>().eq("purchase_list_id", id))),
                "关联商品记录删除失败!");
        AssertUtil.isTrue(!(this.removeById(id)), "进货单删除失败!");
    }
}