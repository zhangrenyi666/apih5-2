var table = $('#table').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "columnDefs": [//表格列配置
        {
            "targets": [0],
            "data": "rowIndex",
            "width": 13,
            "title": '<input type="checkbox">',
            "render": function (data) {
                return '<input type="checkbox" name="checkbox" data-rowIndex="' + data + '" >';
            }
        },
        {
            "targets": [1],
            "data": "rowIndex",
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [2],//第几列
            "data": "name",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "unit",//接口对应字段
            "title": "单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "grade",//接口对应字段
            "title": "成绩",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "phone",//接口对应字段
            "title": "电话",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "winningFlag",//接口对应字段
            "title": "中奖状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "未中奖"
                        break
                    case "1":
                        r = "已中奖"
                        break
                }
                return r
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "name",//输入字段名
            label: "姓名",//输入字段对杨的中文名称
            placeholder: "请输入姓名",
        },
        {
            type: "hidden",
            name: "awardType",
            label: "抽奖类型",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "80分以上",//显示中文名
                    selected: true//默认是否选中
                }
            ],
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "startDate" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = l.dateFormat(new Date(new Date(arr[i].value).getTime()), "yyyy-MM-dd");
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        pager.page('remote',0, _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote',0, _params)
    }
})
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getKscjCandidateList"),
        params: {
            lotteryId: l.getUrlParam("id"),
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
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

var leading = $('#leading').detailLayer({
    layerArea:['500px', '250px'],
    layerTitle:['导入'],
        conditions: [
        {
            type: "upload",
            maxLen:1,
            name: "importFileList",
            label: "导入文件",
            btnName: "选择文件",
            projectName:"zjkscj",
            fileType: ["xls", "xlsx"],
        }
    ],
    onAdd: function (obj, _params){
        if(_params.importFileList.length==0){
            layer.alert("请选择文件", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }
        var new_params={};
        new_params.realUrl=_params.importFileList[0].realUrl;
        new_params.lotteryId=l.getUrlParam("id")
        l.ajax('importCandidateList', new_params, function (_params) {
            pager.page('remote')
            obj.close()
        })
    }
});

var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "candidateId",//输入字段名
        },
        {
            type: "select",
            name: "awardType",
            label: "抽奖类型",
            selectList: [//当类型为select时，option配置
                {
                    value: "0",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "80分以上",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "text",//
            name: "name",//
            label: "姓名",//
            immutable:true,
            placeholder: "请输入姓名",
            must: true
        },
        {
            type: "text",//
            name: "unit",//
            label: "单位",//
            immutable:true,
            placeholder: "请输入单位",
            must: true
        },
        {
            type: "text",//
            name: "grade",//
            label: "成绩",//
            immutable:true,
            placeholder: "请输入成绩",
            must: true
        },
        {
            type: "text",//
            name: "phone",//
            label: "电话",//
            immutable:true,
            placeholder: "请输入电话",
            must: true
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateCandidate', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addKscjCandidate', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
// Lny.log(l.getUrlParam("id"))
// Lny.log(l.getUrlParam("title"))

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            break;
        case "leading"://导入
            leading.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0]);
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "draw":
            window.open('./draw/index.html?id='+l.getUrlParam("id")+"&title="+l.getUrlParam("title"))        
            // window.open('http://weixin.fheb.cn:89/zjtz/www/zj/ks/draw/index.html?id='+l.getUrlParam("id")+"&title="+l.getUrlParam("title"));
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }else if(checkedData.length == 1){
            //    Lny.log('选择的数据：',checkedData)
                l.delTableRowById("candidateId", "deleteCandidate", checkedData[0], function (data) {
                    pager.page('remote')
                })//  删除url地址
            }else {
          //      Lny.log('选择的数据：',checkedData)
                // l.delTableRow("candidateId", 'candidateList', 'batchDelCandidate', checkedData, function (data) {
                //     pager.page('remote')
                // })//  删除url地址
                l.ajax("batchDelCandidate", checkedData, function () {
                    pager.page('remote')
				})
            }
            break;
    }
})
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id
    }
    layer.full(layer.open(options))
}
function public(voteId) {//发布
    l.ajax('sendVoteInfo', { voteId: voteId }, function (data, message) {
        layer.alert(message, { icon: 0, }, function (index) {
            layer.close(index);
            pager.page('remote')
        });
    })
}
function preview(voteId) {//预览
    window.open(l.domainName + 'initMVotePreview.do?voteId=' + voteId + '&previewFlag=1', 'newwindow', 'height=500,width=350,top=100,left=500,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no')
}
