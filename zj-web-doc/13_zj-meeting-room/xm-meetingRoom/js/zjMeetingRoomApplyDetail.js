var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var id = Lny.getUrlParam('id')
loadPage()
function loadPage() {
        l.ajax("getZjMeetingApplyFlowDetailByWorkId", { reservationsFlowId: id }, function (data, message, success) {
            if (success) {
                //detailLayer.open(data);
                detailLayer.setOpenData(data)	
				$('#detailLayer .btn').hide();
            }
        });	
}
/*
detailLayer = function () {
    parent.layer.close(myOpenLayer)
}
*/
var detailLayer = $('#detailLayer').detailLayer({
		layerArea:['100%', '100%'],
		    	   conditions: [
		    	                //2
		    	                {
		    	                	type: "text",//
		    	                	name: "meetingRoomTitle",//
		    	                	label: "会议名称",//
		    	                	placeholder: "请输入会议名称",
		    	                	immutableAdd: true,
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
		    	                	immutableAdd: true,
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
		    	                	immutableAdd: true,
		    	                	lineNum: 4
		    	                },
		    	                //5
		    	                {
		    	                	type: "text",//
		    	                	name: "applyUnit",//
		    	                	label: "申请单位",//
		    	                	placeholder: "请输入申请单位",
		    	                	immutableAdd: true,
		    	                	lineNum: 2
		    	                },	
		    	                //6
		    	                {
		    	                	type: "text",//
		    	                	name: "meetingDecoration",//
		    	                	label: "会议室摆设",//
		    	                	placeholder: "请输入会议室摆设",
		    	                	immutableAdd: true,
		    	                	lineNum: 6
		    	                },
		    	                //7
		    	                {
		    	                	type: "text",//
		    	                	name: "meetingForm",//
		    	                	label: "会议形式",//
		    	                	placeholder: "请输入会议形式",
		    	                	immutableAdd: true,
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
		    	                	immutableAdd: true,
		    	                	lineNum: 7
		    	                },	
		    	                //9
		    	                {
		    	                	type: "select",//
		    	                	name: "meetingRoomId",//
		    	                	label: "会议室",
		    	                	immutableAdd: true,										
		    	                	placeholder: "请输入会议室",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             ],
		    	                	             ajax: {
		    	                	            	 api: "getMeetingRoomAllListForFlow",//api名称
		    	                	            	 valueName: "recordid",//设置value对应的接口字段名称
		    	                	            	 textName: "meetingRoomName",//设置text对应的接口字段名称
		    	                	             },
		    	                	             lineNum: 6,											 
		    	                },	
		    	                //10
		    	                {
		    	                	type: "text",//
		    	                	name: "meetingPeopleNumber",//
		    	                	label: "参会人数",//
		    	                	placeholder: "请输入参会人数",
		    	                	immutableAdd: true,
		    	                	lineNum: 10
		    	                },	
		    	                //11
		    	                {
		    	                	type: "text",//
		    	                	name: "joinUnitMainVenue",//
		    	                	label: "参会单位（主会场）",//
		    	                	placeholder: "单位与单位之间以”,“分隔",
		    	                	immutableAdd: true,
		    	                	lineNum: 11
		    	                },	
		    	                //12
		    	                {
		    	                	type: "text",//
		    	                	name: "joinUnitHardVideo",//
		    	                	label: "参会单位（硬视频）",//
		    	                	placeholder: "单位与单位之间以”,“分隔",
		    	                	immutableAdd: true,
		    	                	lineNum: 12
		    	                },	
		    	                //13
		    	                {
		    	                	type: "text",//
		    	                	name: "joinUnitSoftVideo",//
		    	                	label: "参会单位（软视频）",//
		    	                	placeholder: "单位与单位之间以”,“分隔",
		    	                	immutableAdd: true,
		    	                	lineNum: 13
		    	                },		
		    	                //14
		    	                {
		    	                	type: "text",//
		    	                	name: "meetingLobbyScreen",//
		    	                	label: "会议室及大堂屏幕",//
		    	                	placeholder: "",
		    	                	immutableAdd: true,
		    	                	lineNum: 10									

		    	                },
		    	                //15
		    	                {
		    	                	type: "text",//
		    	                	name: "rostrumMike",//
		    	                	label: "主席台麦克（只）",//
		    	                	placeholder: "请输入主席台麦克数量（只）",
		    	                	immutableAdd: true,
		    	                	lineNum: 15
		    	                },
		    	                //16
		    	                {
		    	                	type: "text",//
		    	                	name: "moveMike",//
		    	                	label: "移动麦克风（只）",//
		    	                	placeholder: "请输入移动麦克数量（只）",
		    	                	immutableAdd: true,
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
		    	                	             immutableAdd: true,
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
		    	                	             immutableAdd: true,
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
		    	                	             immutableAdd: true,
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
		    	                	             immutableAdd: true,
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
		    	                	             immutableAdd: true,
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
		    	                	             immutableAdd: true,
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
		    	                	             immutableAdd: true,
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
		    	                	             immutableAdd: true,
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
		    	                	             immutableAdd: true,
		    	                	             lineNum: 25
		    	                },
		    	                {
		    	                	type: "text",//
		    	                	name: "applyUnitContacts",//
		    	                	label: "申请单位联系人",//
		    	                	placeholder: "请输入申请单位联系人",
		    	                	immutableAdd: true,
		    	                	lineNum: 25
		    	                },
		    	                //26
		    	                {
		    	                	type: "text",//
		    	                	name: "applyUnitContactsWay",//
		    	                	label: "联系方式",//
		    	                	placeholder: "请输入联系方式",
		    	                	immutableAdd: true,
		    	                	lineNum: 25
		    	                },	
		    	                //27
		    	                {
		    	                	type: "text",//
		    	                	name: "meetingSpecialNeeds",//
		    	                	label: "会议主要流程及特殊需要",//
		    	                	placeholder: "请输入会议主要流程及特殊需要",
		    	                	immutableAdd: true
		    	                },									
		    	                {
		    	                	type: "textarea",//
		    	                	name: "opinionField1",//
		    	                	label: "领导",//
		    	                	placeholder: "请输入领导",
		    	                	immutableAdd: true
		    	                },
							    {
		    	                	type: "textarea",//
		    	                	name: "remakes",//
		    	                	label: "备注",//
									defaultValue: "1、本通知单用于1629、1号、2号会议室及多功能厅使用申请。\r"+
                                                  "2、仅申请1629常规会议或仅软视频（不需茶水服务）时，'确认/编号'发信息化管理部；其他类申请，'确认/编号'同时发信息化管理部和资产公司。调试时到信息化管理部1203拿钥匙。\r"+
                                                  "3、申请1号、2号、多功能厅不需要软视频时，'确认/编号'发资产公司；需要软视频时，'确认/编号'同时发资产公司和信息化管理部。"+
                                                  "4、所有会议调试时间一般安排在会前0.5天。\r"+
                                                  "5、预定会议室请提前2天预定。",		    	                
						            immutableAdd: true,
		    	                }	
		    	                ]
})