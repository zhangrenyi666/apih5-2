var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
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
	            	   "targets": [1],//第几列
	            	   "data": "meetingRoomTitle",//接口对应字段
	            	   "title": "会议名称",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render":function(data,index,row){
	            		   var a;
	            		   if(data){   
	            			   a = '<a style="color:blue;" onclick="myOpen1(\' '+row.rowIndex+ '\',\'' + 'xq' + '\')" href="javascript:void(0);">'+data+'</a>'
	            		   }
	            		   return a;
	            	   }			
	               },
                   {
	            	   "targets": [2],//第几列
	            	   "data": "meetingStartTime",//接口对应字段
	            	   "title": "会议开始日期",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   if(data == null){
	            			   return "-";
	            		   } else {
	            			   return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
	            		   }
	            	   }		
	               },				   
	               ]
});

var allData;
var pager = $("#pager").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjMeetingReservationsFlowList"),
		params: {  	
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

var detailLayer = $('#detailLayer').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "reservationsFlowId",//
	            	 label: "主键id",//
	            	 placeholder: ""
	             },
	             //1
	             {
	            	 type: "hidden",//
	            	 name: "typeAssets",//
	            	 label: "申请类型代码",// 1为公司  2为项目
	            	 defaultValue: "1",
	            	 placeholder: "",
	             },
	             //2
	             {
	            	 type: "text",//
	            	 name: "meetingRoomTitle",//
	            	 label: "会议名称",//
	            	 placeholder: "请输入会议名称",
	            	 must: true,
	            	 lineNum: 2

	             },
	             //3								
	             {
	            	 type: "date",//
	            	 name: "meetingStartTime",//
	            	 label: "会议开始日期",//
	            	 dateFmt: "yyyy-MM-dd HH:mm:ss",
	            	 defaultValue: new Date(),
	            	 id:"meetingStartTime",
	            	 must: true,
	            	 lineNum: 4
	             },	
	             //4
	             {
	            	 type: "date",//
	            	 name: "meetingEndTime",//
	            	 label: "会议结束日期",//
	            	 dateFmt: "yyyy-MM-dd HH:mm:ss",
	            	 defaultValue: new Date(),
	            	 id:"meetingEndTime",
	            	 must: true,
	            	 lineNum: 4
	             },
	             //5
	             {
	            	 type: "text",//
	            	 name: "applyUnit",//
	            	 label: "申请单位",//
	            	 placeholder: "请输入申请单位",
	            	 must: true,
	            	 lineNum: 2
	             },	
	             //6
	             {
	            	 type: "text",//
	            	 name: "meetingDecoration",//
	            	 label: "会议室摆设",//
	            	 placeholder: "请输入会议室摆设",
	            	 must: true,
	            	 lineNum: 6
	             },
	             //7
	             {
	            	 type: "text",//
	            	 name: "meetingForm",//
	            	 label: "会议形式",//
	            	 placeholder: "请输入会议形式",
	            	 must: true,
	            	 lineNum: 7
	             },	
	             //8
	             {
	            	 type: "date",//
	            	 name: "debuggingTime",//
	            	 label: "调试时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"debuggingTime",
	            	 must: true,
	            	 lineNum: 7
	             },	
	             //9
	             {
	            	 type: "select",//
	            	 name: "meetingRoomId",//
	            	 label: "会议室",
	            	 search: true,
	            	 placeholder: "请输入会议室",
	            	 selectList: [//当类型为select时，option配置
	            	              ],
	            	              ajax: {
	            	            	  api: "getMeetingRoomAllList",//api名称
	            	            	  valueName: "recordid",//设置value对应的接口字段名称
	            	            	  textName: "meetingRoomName",//设置text对应的接口字段名称
	            	              },
	            	              lineNum: 6
	             },	
	             //10
	             {
	            	 type: "text",//
	            	 name: "meetingPeopleNumber",//
	            	 label: "参会人数",//
	            	 placeholder: "请输入参会人数",
	            	 must: true,
	            	 lineNum: 10
	             },	
	             //11
	             {
	            	 type: "text",//
	            	 name: "joinUnitMainVenue",//
	            	 label: "参会单位（主会场）",//
	            	 placeholder: "单位与单位之间以”,“分隔",
	            	 must: true,
	            	 lineNum: 11
	             },	
	             //12
	             {
	            	 type: "text",//
	            	 name: "joinUnitHardVideo",//
	            	 label: "参会单位（硬视频）",//
	            	 placeholder: "单位与单位之间以”,“分隔",
	            	 must: true,
	            	 lineNum: 12
	             },	
	             //13
	             {
	            	 type: "text",//
	            	 name: "joinUnitSoftVideo",//
	            	 label: "参会单位（软视频）",//
	            	 placeholder: "单位与单位之间以”,“分隔",
	            	 must: true,
	            	 lineNum: 13
	             },		
	             //14
	             {
	            	 type: "text",//
	            	 name: "meetingLobbyScreen",//
	            	 label: "会议室及大堂屏幕",//
	            	 placeholder: "",
	            	 must: true,
	            	 lineNum: 10									

	             },
	             //15
	             {
	            	 type: "text",//
	            	 name: "rostrumMike",//
	            	 label: "主席台麦克（只）",//
	            	 placeholder: "请输入主席台麦克数量（只）",
	            	 must: true,
	            	 lineNum: 15
	             },
	             //16
	             {
	            	 type: "text",//
	            	 name: "moveMike",//
	            	 label: "移动麦克风（只）",//
	            	 placeholder: "请输入移动麦克数量（只）",
	            	 must: true,
	            	 lineNum: 15
	             },	
	             //17
	             {
	            	 type: "select",
	            	 name: "projector",
	            	 label: "投影仪",
	            	 selectList: [//当类型为select时，option配置
	            	              {
	            	            	  value: 0,//输入字段的值
	            	            	  text: "是",//显示中文名
	            	            	  selected: true//默认是否选中
	            	              },
	            	              {
	            	            	  value: 1,//输入字段的值
	            	            	  text: "否",//显示中文名
	            	            	  selected: false//默认是否选中
	            	              }
	            	              ],
	            	              must: true,
	            	              lineNum: 17
	             },
	             //18
	             {
	            	 type: "select",
	            	 name: "soundRecording",
	            	 label: "录音",
	            	 selectList: [//当类型为select时，option配置
	            	              {
	            	            	  value: 0,//输入字段的值
	            	            	  text: "是",//显示中文名
	            	            	  selected: true//默认是否选中
	            	              },
	            	              {
	            	            	  value: 1,//输入字段的值
	            	            	  text: "否",//显示中文名
	            	            	  selected: false//默认是否选中
	            	              }
	            	              ],
	            	              must: true,
	            	              lineNum: 17
	             },	
	             //19
	             {
	            	 type: "select",
	            	 name: "videotape",
	            	 label: "录像",
	            	 selectList: [//当类型为select时，option配置
	            	              {
	            	            	  value: 0,//输入字段的值
	            	            	  text: "是",//显示中文名
	            	            	  selected: true//默认是否选中
	            	              },
	            	              {
	            	            	  value: 1,//输入字段的值
	            	            	  text: "否",//显示中文名
	            	            	  selected: false//默认是否选中
	            	              }
	            	              ],
	            	              must: true,
	            	              lineNum: 18
	             },
	             //20
	             {
	            	 type: "select",
	            	 name: "music",
	            	 label: "音乐",
	            	 selectList: [//当类型为select时，option配置
	            	              {
	            	            	  value: 0,//输入字段的值
	            	            	  text: "是",//显示中文名
	            	            	  selected: true//默认是否选中
	            	              },
	            	              {
	            	            	  value: 1,//输入字段的值
	            	            	  text: "否",//显示中文名
	            	            	  selected: false//默认是否选中
	            	              }
	            	              ],
	            	              must: true,
	            	              lineNum: 18
	             },	
	             //21
	             {
	            	 type: "select",
	            	 name: "pPT",
	            	 label: "PPT",
	            	 selectList: [//当类型为select时，option配置
	            	              {
	            	            	  value: 0,//输入字段的值
	            	            	  text: "是",//显示中文名
	            	            	  selected: true//默认是否选中
	            	              },
	            	              {
	            	            	  value: 1,//输入字段的值
	            	            	  text: "否",//显示中文名
	            	            	  selected: false//默认是否选中
	            	              }
	            	              ],
	            	              must: true,
	            	              lineNum: 18
	             },
	             //22
	             {
	            	 type: "select",
	            	 name: "tableCard",
	            	 label: "桌牌",
	            	 selectList: [//当类型为select时，option配置
	            	              {
	            	            	  value: 0,//输入字段的值
	            	            	  text: "是",//显示中文名
	            	            	  selected: true//默认是否选中
	            	              },
	            	              {
	            	            	  value: 1,//输入字段的值
	            	            	  text: "否",//显示中文名
	            	            	  selected: false//默认是否选中
	            	              }
	            	              ],
	            	              must: true,
	            	              lineNum: 22
	             },	
	             //23
	             {
	            	 type: "select",
	            	 name: "teaWater",
	            	 label: "茶水",
	            	 selectList: [//当类型为select时，option配置
	            	              {
	            	            	  value: 0,//输入字段的值
	            	            	  text: "是",//显示中文名
	            	            	  selected: true//默认是否选中
	            	              },
	            	              {
	            	            	  value: 1,//输入字段的值
	            	            	  text: "否",//显示中文名
	            	            	  selected: false//默认是否选中
	            	              }
	            	              ],
	            	              must: true,
	            	              lineNum: 22
	             },
	             //24
	             {
	            	 type: "select",
	            	 name: "platform",
	            	 label: "讲台",
	            	 selectList: [//当类型为select时，option配置
	            	              {
	            	            	  value: 0,//输入字段的值
	            	            	  text: "是",//显示中文名
	            	            	  selected: true//默认是否选中
	            	              },
	            	              {
	            	            	  value: 1,//输入字段的值
	            	            	  text: "否",//显示中文名
	            	            	  selected: false//默认是否选中
	            	              }
	            	              ],
	            	              must: true,
	            	              lineNum: 22
	             },
	             //25
	             {
	            	 type: "select",
	            	 name: "vdeoconferencing",
	            	 label: "视频会议",
	            	 selectList: [//当类型为select时，option配置
	            	              {
	            	            	  value: 0,//输入字段的值
	            	            	  text: "是",//显示中文名
	            	            	  selected: true//默认是否选中
	            	              },
	            	              {
	            	            	  value: 1,//输入字段的值
	            	            	  text: "否",//显示中文名
	            	            	  selected: false//默认是否选中
	            	              }
	            	              ],
	            	              must: true,
	            	              lineNum: 25
	             },
	             //26
	             {
	            	 type: "select",
	            	 name: "artificialServices",
	            	 label: "人工服务",
	            	 selectList: [//当类型为select时，option配置
	            	              {
	            	            	  value: 0,//输入字段的值
	            	            	  text: "是",//显示中文名
	            	            	  selected: true//默认是否选中
	            	              },
	            	              {
	            	            	  value: 1,//输入字段的值
	            	            	  text: "否",//显示中文名
	            	            	  selected: false//默认是否选中
	            	              }
	            	              ],
	            	              must: true,
	            	              lineNum: 25
	             },
	             //27
	             {
	            	 type: "select",
	            	 name: "indicator",
	            	 label: "指示牌",
	            	 selectList: [//当类型为select时，option配置
	            	              {
	            	            	  value: 0,//输入字段的值
	            	            	  text: "是",//显示中文名
	            	            	  selected: true//默认是否选中
	            	              },
	            	              {
	            	            	  value: 1,//输入字段的值
	            	            	  text: "否",//显示中文名
	            	            	  selected: false//默认是否选中
	            	              }
	            	              ],
	            	              must: true,
	            	              lineNum: 25
	             },
	             //28
	             {
	            	 type: "text",//
	            	 name: "applyUnitContacts",//
	            	 label: "申请单位联系人",//
	            	 placeholder: "请输入申请单位联系人",
	            	 must: true,
	            	 lineNum: 28
	             },
	             //29
	             {
	            	 type: "text",//
	            	 name: "applyUnitContactsWay",//
	            	 label: "联系方式",//
	            	 placeholder: "请输入联系方式",
	            	 must: true,
	            	 lineNum: 28
	             },	
	             //30
	             {
	            	 type: "text",//
	            	 name: "meetingSpecialNeeds",//
	            	 label: "会议主要流程及特殊需要",//
	            	 placeholder: "请输入会议主要流程及特殊需要",
	            	 must: true
	             }	

	             ],
})


$("body").on("click", "button", function () {
	var checkedData = l.getTableCheckedData(table);
	var name = $(this).attr("data-name");
	switch (name) {
	case "add":
		myOpen('会议室申请发起','','zjMeetingRoomApprove')
		break;	
	}
})

function myOpen(title, id, url) {
	var options = {
			type: 2,
			title: title,
			content: url + ".html?id=" + id+"&code="+code

	}
	myOpenLayer = layer.open(options)
	layer.full(myOpenLayer)
}

function myOpen1(index, type) {
	var rowData = allData[Number(index)];
	var params;
	switch (type){
	case 'xq':
		params = {};
		params.reservationsFlowId = rowData.reservationsFlowId;
		l.ajax('getZjMeetingApplyFlowDetailByWorkId',params,function(res){
			detailLayer.open(res);
			$('#detailLayer .btn').hide();
		})
		break;	
	}
}