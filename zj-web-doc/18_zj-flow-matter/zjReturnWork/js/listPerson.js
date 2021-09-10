var code = Apih5.getUrlParam('code')
Apih5.setCookie('code',code,30)
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
            "data": "realName",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [3],//第几列
            "data": "mobile",//接口对应字段
            "title": "职务/岗位",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [4],//第几列
            "data": "festival",//接口对应字段
            "title": "春节所在地",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [5],//第几列
            "data": "isContactName",//接口对应字段
            "title": "有无赴湖北旅行史",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [6],//第几列
            "data": "contactInfo",//接口对应字段
            "title": "接触情况",//表头名
            "defaultContent": "-",//默认显示 
        },        {
            "targets": [7],//第几列
            "data": "isDiagnosisName",//接口对应字段
            "title": "确诊病人接触或同乘交通工具",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [8],//第几列
            "data": "isIllnessName",//接口对应字段
            "title": "有无发烧、咳嗽、乏力等症状",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [9],//第几列
            "data": "isQuarantineName",//接口对应字段
            "title": "是否被隔离",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [10],//第几列
            "data": "quarantineInfo",//接口对应字段
            "title": "隔离情况说明",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [11],//第几列
            "data": "overseasOpinion",//接口对应字段
            "title": "境外线下审批意见",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [12],//第几列
            "data": "zjHwSarsFileList",//接口对应字段
            "title": "承诺书附件",//表头名
            "defaultContent": "-",//默认显示 
            render: function (data,_,row) {
                var data = row.zjHwSarsFileList; 
                var htmlstr = '';
                if(data && data.length){
                    for (var i = 0; i < data.length; i++) {
                        htmlstr += '<div><a style="color:blue;" target="_block" href=' + encodeURI(data[i].url) + '>' + data[i].name + '</a></div>'
                    } 
                }
               
                return htmlstr
            }
        },
        {
            "targets": [13],//第几列
            "data": "zjHwSarsFileOtherList",//接口对应字段
            "title": "复工申请表附件",//表头名
            "defaultContent": "-",//默认显示 
            render: function (data,_,row) {
                var data = row.zjHwSarsFileOtherList;
                var htmlstr = '';
                if(data && data.length){
                    for (var i = 0; i < data.length; i++) { 
                        htmlstr += '<div><a style="color:blue;" target="_block" href=' + encodeURI(data[i].url) + '>' + data[i].name + '</a></div>'
                    } 
                }
             
                return htmlstr
            }
        }
    ]
});

var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjHwSarsUserList"),
        params: {
            sarsId: l.getUrlParam('sarsId')
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
				 if(result.data.length >0){	
				    if(result.data[0].apih5FlowStatus == 1 || result.data[0].apih5FlowStatus == 2){	
					    $("#save").hide();
					    $("#update").hide();
					    $("#delete").hide();
				    }
				} 
				
                $.each(data,function (index,item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message,{ icon: 0,},function (index) {
                    layer.close(index);
                });
            }
        },
    }
});
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ["100%","100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sarsId",//输入字段名
            defaultValue: l.getUrlParam('sarsId')
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sarsType",//输入字段名
			defaultValue:'0'
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sarsUserId",//输入字段名
        },
        {
            type: "text",//
            name: "realName",//
            label: "姓名",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "mobile",//
            label: "职务/岗位",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "festival",//
            label: "春节所在地",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "select",//
            name: "isContact",//
            label: "有无赴湖北旅行史",// 
            must: true,
            selectList: [
                {
                    text: "无",
                    value: "0"
                },
                {
                    text: "有",
                    value: "1"
                }
            ]
        },
        {
            type: "textarea",// 
            "name": "contactInfo",//接口对应字段
            "label": "有无与湖北人员接触情况",//表头名
            placeholder: "请输入",
            // must: true
        },
        {
            type: "select",//
            name: "isDiagnosis",//
            label: "有无与确诊病人接触和同乘交通工具",// 
            must: true,
            selectList: [
                {
                    text: "无",
                    value: "0"
                },
                {
                    text: "有",
                    value: "1"
                }
            ]
        },
        {
            type: "select",//
            name: "isIllness",//
            label: "有无发烧、咳嗽、乏力等症状",// 
            must: true,
            selectList: [
                {
                    text: "无",
                    value: "0"
                },
                {
                    text: "有",
                    value: "1"
                }
            ]
        },
        {
            type: "select",//
            name: "isQuarantine",//
            label: "是否被隔离",// 
            must: true,
            selectList: [
                {
                    text: "否",
                    value: "0"
                },
                {
                    text: "是",
                    value: "1"
                }
            ]
        },
        {
            type: "textarea",// 
            "name": "quarantineInfo",//接口对应字段
            "label": "隔离情况说明",//表头名
            placeholder: "请输入",
            // must: true
        },
        {
            type: "textarea",// 
            "name": "overseasOpinion",//接口对应字段
            "label": "境外线下审批意见",//表头名
            placeholder: "请输入",
            must: true
        },
        {
            type: "upload",//
            name: "zjHwSarsFileList",//
            label: "承诺书附件",//
            btnName: "添加附件",
            maxLen: 1,
            // projectName: "zj-san-party",
            // immutableAdd: true,
            uploadUrl: 'uploadFilesByFileName',
            // must:true,
            fileType: ["jpg","jpeg","png","gif","docx","xls","doc","ppt","xlsx","pptx","xlsm","pdf"]
        },
        {
            type: "upload",//
            name: "zjHwSarsFileOtherList",//
            label: "复工申请表附件",//
            btnName: "添加附件",
            maxLen: 1,
            // must:true,
            // projectName: "zj-san-party",
            // immutableAdd: true,
             uploadUrl: 'uploadFilesByFileName',
            fileType: ["jpg","jpeg","png","gif","docx","xls","doc","ppt","xlsx","pptx","xlsm","pdf"]
        }
    ],
    onSave: function (obj,_params) {
        l.ajax('updateZjHwSarsUser',_params,function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj,_params) {
        l.ajax('addZjHwSarsUser',_params,function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})

$("body").on("click","button",function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0]);
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            }
            break;
        case "goback":
            window.history.go(-1)
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？",{ icon: 0,},function (index) {
                    l.ajax("batchDeleteUpdateZjHwSarsUser",checkedData,function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
    }
})
