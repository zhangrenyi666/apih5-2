var recordid = l.getUrlParam('id') || "";
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
	            	   "title": 'No.',
	            	   "width": 25,
	            	   "render": function (data) {
	            		   return data + 1
	            	   }
	               },			
	               {
	            	   targets: [1], //第几列
	            	   title: "监督事项", //表头名
	            	   data: "supMatter", //接口对应字段
	            	   defaultContent: "-" //默认显示
	               },
	               {
	            	   targets: [2], //第几列
	            	   data: "planFlag", //接口对应字段
	            	   title: "是否计划内", //表头名
	            	   defaultContent: "-", //默认显示
	            	   "render": function (data) {
	            		   var text = ""
	            			   switch (data) {
	            			   case "0": text = "是";
	            			   break;
	            			   case "1": text = "否";
	            			   break;
	            			   default: text = "-";
	            			   break;
	            			   }
	            		   return text
	            	   }
	               },
	               {
	            	   targets: [3], //第几列
	            	   title: "配合部门", //表头名
	            	   data: "coopDept", //接口对应字段
	            	   defaultContent: "-" //默认显示
	               },			             
	               {
	            	   targets: [4], //第几列
	            	   title: "计划完成时间", //表头名
	            	   data: "scheduleTime", //接口对应字段
	            	   defaultContent: "-", //默认显示
	            	   "render": function (data) {
	            		   return data ? l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss") : "-";
	            	   }
	               },
	               {
	            	   targets: [5], //第几列
	            	   data: "principal", //接口对应字段
	            	   title: "责任人", //表头名
	            	   defaultContent: "-" //默认显示
	               },
	               {
	            	   targets: [6], //第几列
	            	   data: "remarks", //接口对应字段
	            	   title: "备注", //表头名
	            	   defaultContent: "-" //默认显示
	               }
	               ]
});

var detailForm = $('#detailForm').detailLayer({
	conditions: [		
	             {
	            	 type: "pickTree",//
	            	 name: "oaDutyDep",//接口字段名
	            	 label: "监督责任部门",//
	            	 immutableAdd: true,
	            	 pickType: {
	            		 department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
	            		 member: false,//成员列表对应接口字段名,false表示不开启该类
	            	 }
	             },
	             {
	            	 type: "text",
	            	 name: "informant",
	            	 label: "填报人",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "select",
	            	 name: "year",
	            	 label: "年度",
	            	 selectList: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
	            	              {
	            	            	  value: "",
	            	            	  text: " "
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
	            	              immutableAdd: true
	             }
	             ],
})

detailForm.close = function () {
	parent.layer.close(parent.myOpenLayer)
}
loadPage()
function loadPage() {
	if (recordid) {
		l.ajax("getZjJdjhYearSupPlanDetails", { yearSupPlanId: recordid }, function (data, message, success) {
			if (success) {
				detailForm.setOpenData(data)
			}
		})
	} else {
		detailForm.setOpenData({ memberList: { oaMemberList: [] } })
	}
}

var allData;
//检查项
var pager = $("#pager").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjJdjhYearSupMatterList"),
		params: {
			yearSupPlanId: recordid,
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

$("body").on("click", "button", function () {
	var checkedData = l.getTableCheckedData(table);
	var name = $(this).attr("data-name");
	switch (name) {
	case "close":
		parent.layer.close(parent.myOpenLayer)
		break; 
	 case "derive"://党费使用导出
		 var params = {yearSupPlanId: recordid}	  
             layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('exportExcelZjJdjhYearSupMatterList', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            }); 			
    break	
	}
})

$("#tab-system").Huitab({
	index: 0
});
