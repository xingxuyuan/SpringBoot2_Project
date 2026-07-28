layui.use(['table', 'layer', 'laydate'], function () {
    var table = layui.table,
        layer = layui.layer,
        laydate = layui.laydate,
        $ = layui.jquery;

    // 1. 初始化左侧进货单表格
    var tableIns = table.render({
        elem: '#purchaseList',
        url: ctx + '/purchase/list',
        page: true,
        limits: [10, 20, 50],
        limit: 10,
        cellMinWidth: 80,
        cols: [[
            {type: 'numbers', title: '编号', width: 60},
            {field: 'purchaseNumber', title: '进货单号', width: 150},
            {field: 'purchaseDate', title: '进货日期', width: 120},
            {field: 'supplierName', title: '供应商', width: 150},
            {field: 'amountPayable', title: '进货金额', width: 100},
            {field: 'remarks', title: '备注'},
            {fixed: 'right', title: '操作', toolbar: '#purchaseListBar', width: 180}
        ]]
    });

    // 2. 初始化右侧进货单商品表格
    var goodsTableIns = table.render({
        elem: '#purchaseListGoods',
        url: ctx + '/purchase/goodsList',
        page: false, // 右侧表格通常不分页
        cols: [[
            {field: 'code', title: '商品编码', width: 100},
            {field: 'name', title: '商品名称', width: 150},
            {field: 'model', title: '商品型号', width: 120},
            {field: 'unit', title: '单位', width: 80},
            {field: 'num', title: '数量', width: 80},
            {field: 'price', title: '单价', width: 100},
            {field: 'total', title: '总金额', width: 100}
        ]]
    });

    // 3. 左侧搜索按钮事件
    $('.search_btn').click(function () {
        var purchaseNumber = $('#purchaseNumber').val();
        var supplierId = $('#supplierId').val();
        var state = $('#state').val();
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();

        // 重新加载表格数据，带上查询条件
        tableIns.reload({
            where: {
                purchaseNumber: purchaseNumber,
                supplierId: supplierId,
                state: state,
                startDate: startDate,
                endDate: endDate
            },
            page: {curr: 1}
        });
    });

    // 4. 左侧行工具事件
    table.on('tool(purchaseList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'search') {
        
            // 1. 填充右侧顶部头信息
            $('#purchaseNumber_').val(data.purchaseNumber);
            $('#supplierName_').val(data.supplierName);
            $('#amountPayable_').val(data.amountPayable);
            $('#state_').val(data.state === 1 ? '已付' : '未付');
            $('#userName_').val(data.userName);

            // 2. 刷新右侧商品表格
            goodsTableIns.reload({
                where: {purchaseListId: data.id},
                page: {curr: 1}
            });
        } else if (obj.event === 'del') {
            layer.confirm('确定要删除货单【' + data.purchaseNumber + '】吗？', {icon: 3, title: '提示'}, function (index) {
                $.post(ctx + "/purchase/delete", {id: data.id}, function (res) {
                    if (res.code == 200) {
                        layer.msg("删除成功");
                        tableIns.reload();
                        // 同时清空右侧内容
                        $('#purchaseNumber_').val('');
                        $('#supplierName_').val('');
                        $('#amountPayable_').val('');
                        $('#state_').val('');
                        $('#userName_').val('');
                        goodsTableIns.reload({where: {purchaseListId: -1}});
                    } else {
                        layer.msg(res.message);
                    }
                });
                layer.close(index);
            });
        }
    });
    
    // 5. 右侧“重置”按钮
    $('.search_btn02').click(function(){
        $('#purchaseNumber_').val('');
        $('#supplierName_').val('');
        $('#amountPayable_').val('');
        $('#state_').val('');
        $('#userName_').val('');
        goodsTableIns.reload({where: {purchaseListId: -1}});
    });
});