﻿var code = Lny.getUrlParam("code");
Lny.setCookie("code", code, 30);
var table = $("#table").DataTable({
	info: false, //是否显示数据信息
	paging: false, //是否开启自带分页
	ordering: false, //是否开启DataTables的高度自适应
	processing: false, //是否显示‘进度’提示
	searching: false, //是否开启自带搜索
	autoWidth: false, //是否监听宽度变化
	columnDefs: [
	             //表格列配置
	             {
	            	 targets: [0],
	            	 data: "rowIndex",
	            	 title: "No.",
	            	 width: 25,
	            	 render: function(data) {
	            		 return data + 1;
	            	 }
	             },
	             {
	            	 targets: [1], //第几列
	            	 data: function(row) {
	            		 return row;
	            	 }, //接口对应字段
	            	 title: "监督责任部门", //表头名
	            	 defaultContent: "-", //默认显示
	            	 render: function(data, index, row) {
	            		 var a;
	            		 if (data) {                                        
	            			 a = "<a style=\"color:blue;\"  onclick=\"myOpen1('完成情况填写','" + data.supPlanExecutiveConditionId + "','" + data.supPlanId + "','" + data.apih5FlowStatus + "','" + "jdjhYearSupPlanExecutiveApproveEdit" + '\')" href="javascript:void(0);">' + data.supDutyDepName + "</a>";
	            		 }
	            		 return a;
	            	 }
	             },
	             {
	            	 targets: [2], //第几列
	            	 title: "填报人", //表头名
	            	 data: "informant", //接口对应字段
	            	 defaultContent: "-" //默认显示
	             },  
	             {
	            	 targets: [3], //第几列
	            	 title: "年度", //表头名
	            	 data: "year", //接口对应字段
	            	 defaultContent: "-", //默认显示
	            	 "render": function (data) {
	            		 var text = ""
	            			 switch (data) {
	            			 case "0": text = "2019";
	            			 break;
	            			 case "1": text = "2020";
	            			 break;
	            			 case "2": text = "2021";
	            			 break;
	            			 case "3": text = "2022";
	            			 break;
	            			 case "4": text = "2023";
	            			 break;
	            			 default: text = "未知";
	            			 break;
	            			 }
	            		 return text
	            	 }
	             },
	             {
	            	 targets: [4], //第几列
	            	 title: "季度", //表头名
	            	 data: "quarter", //接口对应字段
	            	 defaultContent: "-", //默认显示
	            	 "render": function (data) {
	            		 var text = ""
	            			 switch (data) {
	            			 case "0": text = "第一季度";
	            			 break;
	            			 case "1": text = "第二季度";
	            			 break;
	            			 case "2": text = "第三季度";
	            			 break;
	            			 case "3": text = "第四季度";
	            			 break;
	            			 default: text = "未知";
	            			 break;
	            			 }
	            		 return text
	            	 }
	             },
	             {
	            	 targets: [5], //第几列
	            	 title: "状态", //表头名
	            	 data: "apih5FlowStatus", //接口对应字段
	            	 defaultContent: "-", //默认显示
	            	 "render": function (data) {
	            		 var text = ""
	            			 switch (data) {
	            			 case "0": text = "待提交";
	            			 break;
	            			 case "1": text = "审批中";
	            			 break;
	            			 case "2": text = "已审批";
	            			 break;
							 case "3": text = "驳回";
	            			 break;
	            			 default: text = "待填写";
	            			 break;
	            			 }
	            		 return text
	            	 }
	             }

	             ]
});
var form = $("#form").filterfrom({
	conditions: [
	             {
	            	 type: "text", //三种形式：text,select,date,
	            	 name: "supDutyDep", //输入字段名
	            	 label: "监督责任部门", //输入字段对杨的中文名称
	            	 placeholder: "请输入监督责任部门"
	             },
				 {
	            	 type: "text", //三种形式：text,select,date,
	            	 name: "informant", //输入字段名
	            	 label: "填报人", //输入字段对杨的中文名称
	            	 placeholder: "请输入填报人"
	             },
				 {
            type: "select",
            name: "year",
            label: "年度",
            selectList: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
                {
                    value: "",
                    text: "全部"
                },
                {
                    value: "0",
                    text: "2019"
                },
				{
                    value: "1",
                    text: "2020"
                },
                {
                    value: "2",
                    text: "2021"
                },
				{
                    value: "3",
                    text: "2022"
                },
                {
                    value: "4",
                    text: "2023"
                }
            ],
        },
		 {
          type: "select",
          name: "quarter",
          label: "季度",
          selectList: [
            //当类型为select时，option配置  0:全公司  1：机关  2：项目
            {
              value: "",
              text: "请选择 "
            },
            {
              value: "0",
              text: "第一季度"
            },
            {
              value: "1",
              text: "第二季度"
            },
            {
              value: "2",
              text: "第三季度"
            },
            {
              value: "3",
              text: "第四季度"
            }
          ]
        }		
	             ],
	             onSearch: function(arr) {
	            	 //搜索按钮回调
	            	 var _params = {};
	            	 for (var i = 0; i < arr.length; i++) {
	            		 _params[arr[i].name] = arr[i].value;
	            	 }
	            	 pager.page("remote", 0, _params);
	             },
	             onReset: function(arr) {
	            	 //重置按钮回调
	            	 var _params = {};
	            	 for (var i = 0; i < arr.length; i++) {
	            		 _params[arr[i].name] = arr[i].value;
	            	 }
	            	 pager.page("remote", _params);
	             }
});

var allData;
var pager = $("#pager").page({
	remote: {
		//ajax请求配置
		url: l.getApiUrl("getZjJdjhSupPlanExecutiveList"),
		params: {},
		success: function(result) {
			if (result.success) {
				var data = result.data;
				allData = data;			
				$.each(data, function(index, item) {
					item.rowIndex = index;
				});
				table.clear().rows.add(data).draw();
			} else {
				layer.alert(result.message, { icon: 0 }, function(index) {
					layer.close(index);
				});
			}
		}
	}
});

$("body").on("click", "button", function() {
	var checkedData = l.getTableCheckedData(table);
	var name = $(this).attr("data-name");
	switch (name) {
	case "add":	
		 myOpen("完成情况填写", "","", "jdjhYearSupPlanExecutiveApprove","");		
		break;
	}
});

var myOpenLayer;
function myOpen(title, id,supPlanId, url,supPlanExecutiveConditionId) {
	var options = {
			type: 2,
			title: title,
			content: url + ".html?id=" + id+"&supPlanId="+supPlanId+"&code="+code+"&supPlanExecutiveConditionId="+supPlanExecutiveConditionId
	};
	myOpenLayer = layer.open(options);
	layer.full(myOpenLayer);
};
function myOpen1(title, id,supPlanId, apih5FlowStatus,url) {
	var options = {
			type: 2,
			title: title,
			content: url + ".html?id=" + id+"&supPlanId="+supPlanId+"&code="+code+"&apih5FlowStatus="+apih5FlowStatus 
	};
	myOpenLayer = layer.open(options);
	layer.full(myOpenLayer);
}


