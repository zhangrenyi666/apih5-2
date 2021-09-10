var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var table = $('#table').DataTable({
    "info": false,// 是否显示数据信息
    "paging": false,// 是否开启自带分页
    "ordering": false, // 是否开启DataTables的高度自适应
    "processing": false,// 是否显示‘进度’提示
    "searching": false,// 是否开启自带搜索
    "autoWidth": false,// 是否监听宽度变化
    "columnDefs": [// 表格列配置
        {
            "targets": [0],
            "data": "rowIndex",
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [1],// 第几列
            "data": "project",// 接口对应字段
            "title": "项目",// 表头名
            "defaultContent": "-",// 默认显示 
        },
        {
            "targets": [2],// 第几列
            "data": "questionTitleName",// 接口对应字段
            "title": "检查项",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [3],// 第几列 
            "data": "questionDescribe",// 接口对应字段
            "title": "问题描述",// 表头名
            "defaultContent": "-",// 默认显示  
        },
        {
            "targets": [4],// 第几列
            "data": "questionState",// 接口对应字段
            "title": "解决程度",// 表头名
            "defaultContent": "-",// 默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "未审批"
                        break
                    case "1":
                        r = "审批中"
                        break
                    case "2":
                        r = "通过"
                        break
                    case "3":
                        r = "未通过"
                        break
                    case "4":
                        r = "问题关闭"
                        break
                    case "5":
                        r = "追加待回答"
                        break
                    case "6":
                        r = "追加待审批"
                        break
                }
                return r
            }
        },
        {
            "targets": [5],// 第几列
            "data": "modifyUserName",// 接口对应字段
            "title": "创建者",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [6],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "创建时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});

var allData;
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjQuestionApprovalListByquestionId"),
        params: {
            projectName: decodeURI(window.location.search.substr(1).split("=")[2].split("&")[0]),
            modifyFlag: window.location.search.substr(1).split("=")[3]
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                allData = data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});

