var code = l.getUrlParam("code") || "";
Lny.setCookie("code", code, 30);
var id = l.getUrlParam("assessmentId") || "";;
if(id){
    render(id);
}else{
    l.ajax("getZjScoreAnnualAssessmentListByAuditor", {}, function (
        data,
        message,
        success
    ) {
        if (success) {
            var selectDom = '';
            if(data &&  data.length){
                $.each(data, function (index, item) {
                    selectDom += "<li class='selectDomLi' data-id="+ item.assessmentId +" style='cursor:pointer; margin-bottom:10px;border-bottom:1px solid #ccc;padding-bottom:10px;text-align: center;'>"+ item.title +"</li>";
                });
                var idex = layer.open({
                    title: "请选择打分活动",
                    type: 1,
                    skin: "layui-layer-demo", //样式类名
                    closeBtn: 0, //不显示关闭按钮
                    anim: 2,
                    content:
                        "<ul class='selectDom' style='padding:26px'>" + selectDom + "</ul>"
                });
                $(".selectDomLi").click(function() {
                    id = $(this).attr("data-id");
                    render(id);
                    layer.close(idex);
                });
            }else{
                var idex = layer.open({
                    title: "请选择打分活动",
                    type: 1,
                    skin: "layui-layer-demo", //样式类名
                    closeBtn: 0, //不显示关闭按钮
                    anim: 2,
                    content:
                        "<ul class='selectDom' style='padding:26px'>暂无打分内容...</ul>"
                });
            }
        }
    });
}
function render(id) {
    l.ajax("getZjScoreCompanyDetailListByAuditor", { assessmentId: id }, function (
        data,
        message,
        success
    ) {
        if (success) {
            var data1 = [];
            var data2 = [];
            var data3 = [];
            $.each(data, function (index, item) {
                if (item.moduleType === '0') {
                    data1.push(item);
                } else if (item.moduleType === '1') {
                    data2.push(item);
                } else if (item.moduleType === '2') {
                    data3.push(item);
                }
            });
            $.each(data1, function (index, item) {
                item.rowIndex = index;
            });
            $.each(data2, function (index, item) {
                item.rowIndex = index;
            });
            $.each(data3, function (index, item) {
                item.rowIndex = index;
            });
            table.clear().rows.add(data1).draw();
            tables.clear().rows.add(data2).draw();
            tablet.clear().rows.add(data3).draw();
        }
    });
}
// render();
function myblur(rowData, oldValue, value) {
    if (oldValue !== value.val()) {
        if (parseInt(value.val()) || parseInt(value.val()) === 0) {
            rowData.companyScore = parseInt(value.val());
            l.ajax('batchUpdateZjScoreCompanyDetail', [rowData], function (data, message, success) {
                if (success) {
                    layer.alert(message, { icon: 1 });
                    render(id);
                } else {
                    layer.alert(message, { icon: 0 });
                    render(id);
                }
            })
        } else {
            layer.alert('请输入数字!', { icon: 0 });
            render(id);
        }
    }
}
var table = $("#table").DataTable({
    info: false, //是否显示数据信息
    paging: false, //是否开启自带分页
    ordering: false, //是否开启DataTables的高度自适应
    processing: false, //是否显示‘进度’提示
    searching: false, //是否开启自带搜索
    autoWidth: false, //是否监听宽度变化
    columnDefs: [
        {
            targets: [0],
            data: "rowIndex",
            title: "No.",
            width: 25,
            render: function (data) {
                return data + 1;
            }
        },
        {
            targets: [1], //第几列
            data: "companyFullName", //接口对应字段
            title: "单位全称", //表头名
            defaultContent: "-", //默认显示
        },
        {
            targets: [2], //第几列
            data: "companyShortName", //接口对应字段
            title: "单位简称", //表头名
            defaultContent: "-", //默认显示
        },
        {
            targets: [3], //第几列
            data: "content", //接口对应字段
            title: "打分项", //表头名
            defaultContent: "-", //默认显示
        },
        {
            targets: [4], //第几列
            data: "companyScore", //接口对应字段
            title: "打分", //表头名
            defaultContent: "-", //默认显示
            render: function (data, display, rowData) {
                if (data || data === 0) {
                    return "<input style='text-align: center;' onblur='myblur(" + JSON.stringify(rowData) + ",\"" + data + "\",$(this))' name='companyScore' value='" + data + "'/>";
                } else {
                    return "<input style='text-align: center;' onblur='myblur(" + JSON.stringify(rowData) + ",\"" + 0 + "\",$(this))' name='companyScore' value='0' placeholder='请输入'/>";
                }
            }
        }
    ]
});
var tables = $("#tables").DataTable({
    info: false, //是否显示数据信息
    paging: false, //是否开启自带分页
    ordering: false, //是否开启DataTables的高度自适应
    processing: false, //是否显示‘进度’提示
    searching: false, //是否开启自带搜索
    autoWidth: false, //是否监听宽度变化
    columnDefs: [
        {
            targets: [0],
            data: "rowIndex",
            title: "No.",
            width: 25,
            render: function (data) {
                return data + 1;
            }
        },
        {
            targets: [1], //第几列
            data: "companyFullName", //接口对应字段
            title: "单位全称", //表头名
            defaultContent: "-", //默认显示
        },
        {
            targets: [2], //第几列
            data: "companyShortName", //接口对应字段
            title: "单位简称", //表头名
            defaultContent: "-", //默认显示
        },
        {
            targets: [3], //第几列
            data: "content", //接口对应字段
            title: "打分项", //表头名
            defaultContent: "-", //默认显示
        },
        {
            targets: [4], //第几列
            data: "companyScore", //接口对应字段
            title: "打分", //表头名
            defaultContent: "-", //默认显示
            render: function (data, display, rowData) {
                if (data || data === 0) {
                    return "<input style='text-align: center;' onblur='myblur(" + JSON.stringify(rowData) + ",\"" + data + "\",$(this))' name='companyScore' value='" + data + "'/>";
                } else {
                    return "<input style='text-align: center;' onblur='myblur(" + JSON.stringify(rowData) + ",\"" + 0 + "\",$(this))' name='companyScore' value='0' placeholder='请输入'/>";
                }
            }
        }
    ]
});
var tablet = $("#tablet").DataTable({
    info: false, //是否显示数据信息
    paging: false, //是否开启自带分页
    ordering: false, //是否开启DataTables的高度自适应
    processing: false, //是否显示‘进度’提示
    searching: false, //是否开启自带搜索
    autoWidth: false, //是否监听宽度变化
    columnDefs: [
        {
            targets: [0],
            data: "rowIndex",
            title: "No.",
            width: 25,
            render: function (data) {
                return data + 1;
            }
        },
        {
            targets: [1], //第几列
            data: "companyFullName", //接口对应字段
            title: "单位全称", //表头名
            defaultContent: "-", //默认显示
        },
        {
            targets: [2], //第几列
            data: "companyShortName", //接口对应字段
            title: "单位简称", //表头名
            defaultContent: "-", //默认显示
        },
        {
            targets: [3], //第几列
            data: "content", //接口对应字段
            title: "打分项", //表头名
            defaultContent: "-", //默认显示
        },
        {
            targets: [4], //第几列
            data: "companyScore", //接口对应字段
            title: "打分", //表头名
            defaultContent: "-", //默认显示
            render: function (data, display, rowData) {
                if (data || data === 0) {
                    return "<input style='text-align: center;' onblur='myblur(" + JSON.stringify(rowData) + ",\"" + data + "\",$(this))' name='companyScore' value='" + data + "'/>";
                } else {
                    return "<input style='text-align: center;' onblur='myblur(" + JSON.stringify(rowData) + ",\"" + 0 + "\",$(this))' name='companyScore' value='0' placeholder='请输入'/>";
                }
            }
        }
    ]
});
$("#tab-system").Huitab({ index: 0 });

