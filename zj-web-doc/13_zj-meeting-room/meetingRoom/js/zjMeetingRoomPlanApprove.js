var flowId = l.getUrlParam('id') || "ZjMeetingRoom";//流程模版id
var nodeName = l.getUrlParam('nodeName') || "";
var code = l.getUrlParam('code');
Apih5.setCookie('code', code, 30);

var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
		name: "",
		tabs: []
}

var $tabTitle = $("#tab-title")//模版title
$tabTitle.html("会议室申请")

switch (flowId) {
case "ZjMeetingRoom":
	flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
		name: "会议室申请",
		titleName: "zcmc",
		tabs: [
		       {
		    	   name: "基本信息",
		    	   type: "1",//普通tab页1，附件tab页2，列表tab页面3
		    	   isMain: "1",//是否为主表
		    	   tbName: "zjMeetingReservationsFlow",//表名
		    	   tbIdName: "reservationsFlowId",//表主键id
		    	   conditions: [
		    	                {
		    	                	type: "hidden",//
		    	                	name: "reservationsFlowId",//
		    	                	label: "主键id",//
		    	                	placeholder: ""
		    	                },
		    	                {
		    	                	type: "hidden",//
		    	                	name: "meetingRoomTitle",//
		    	                	label: "会议室名称",//
		    	                	placeholder: ""
		    	                },
		    	                //1
		    	                {
		    	                	type: "hidden",//
		    	                	name: "isPlanMeeting",//
		    	                	label: "是否为计划会议",// 0是  1不是
		    	                	defaultValue: "0",
									placeholder: "",
									lineNum: 1									
		    	                },
		    	                //2
		    	                // {
		    	                // 	type: "text",//
		    	                // 	name: "meetingRoomTitle",//
		    	                // 	label: "会议名称",//
		    	                // 	placeholder: "请输入会议名称",
		    	                // 	must: true,
		    	                // 	lineNum: 2
								// },
								{
									type: "select",
									name: "fallInId",
									label: "会议名称",
									ajax: {//如果需要接口获取数据需要添加该属性
										api: "getZjMeetingPlanSelectAllListByDeptId",//api名称
										valueName: "fallInId",//设置value对应的接口字段名称
										textName: "meetingNameStr",//设置text对应的接口字段名称
									},
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
		//  {
        //     type: "pickTree",//
        //     name: "useUnit",//接口字段名
        //     label: "申请单位",//
        //     must: true,
		// 	lineNum: 2,
        //     pickType: {
        //         department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
        //         member: false,//成员列表对应接口字段名,false表示不开启该类
        //     }
		// },	
		{
			type: "text",//
			name: "applyUnit",//
			label: "申请单位",//
			placeholder: "请输入申请单位",
			must: true,
			lineNum: 2
		},										
		    	                {
		    	                	type: "select",
									name: "meetingDecoration",
									id:"meetingDecoration",
		    	                	label: "会议室摆设",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             {
		    	                	            	 value: "课桌型",//输入字段的值
		    	                	            	 text: "课桌型",//显示中文名
		    	                	            	 selected: true//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: "剧院型",//输入字段的值
		    	                	            	 text: "剧院型",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: "回型",//输入字段的值
		    	                	            	 text: "回型",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             }
												 ],
												 onclick:'',
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
		    	                // {
		    	                // 	type: "select",
		    	                // 	name: "meetingForm",
		    	                // 	label: "会议形式1111",
		    	                // 	selectList: [//当类型为select时，option配置
		    	                // 	             {
		    	                // 	            	 value: "常规会议",//输入字段的值
		    	                // 	            	 text: "常规会议",//显示中文名
		    	                // 	            	 selected: true//默认是否选中
		    	                // 	             },
		    	                // 	             {
		    	                // 	            	 value: "仅硬视频",//输入字段的值
		    	                // 	            	 text: "仅硬视频",//显示中文名
		    	                // 	            	 selected: false//默认是否选中
		    	                // 	             },
		    	                // 	             {
		    	                // 	            	 value: "仅软视频",//输入字段的值
		    	                // 	            	 text: "仅软视频",//显示中文名
		    	                // 	            	 selected: false//默认是否选中
		    	                // 	             },
		    	                // 	             {
		    	                // 	            	 value: "硬视频+软视频",//输入字段的值
		    	                // 	            	 text: "硬视频+软视频",//显示中文名
		    	                // 	            	 selected: false//默认是否选中
		    	                // 	             }
		    	                // 	             ],
		    	                // 	             must: true,
								// 				 lineNum: 7
		    	                // },		
		    	                //8
		    	                {
		    	                	type: "date",//
		    	                	name: "debuggingTime",//
		    	                	label: "调试时间",//
		    	                	dateFmt: "yyyy-MM-dd HH:mm:ss",
		    	                	defaultValue: new Date(),
		    	                	id:"debuggingTime",
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
		    	                	            	 api: "getMeetingRoomAllListForFlow",//api名称
		    	                	            	 valueName: "recordid",//设置value对应的接口字段名称
		    	                	            	 textName: "meetingRoomName",//设置text对应的接口字段名称
		    	                	             },
												 must: true,
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
		    	                // {
		    	                // 	type: "text",//
		    	                // 	name: "meetingPeopleNumber",//
		    	                // 	label: "是否用餐",//
		    	                // 	placeholder: "请输入是否用餐",
		    	                // 	must: true,
		    	                // 	lineNum: 11
								// },	
		    	                {
		    	                	type: "select",
		    	                	name: "meetingHaveMealsFlag",
		    	                	label: "是否用餐",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             {
		    	                	            	 value: 1,//输入字段的值
		    	                	            	 text: "否",//显示中文名
		    	                	            	 selected: true//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: 0,//输入字段的值
		    	                	            	 text: "是",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             }
		    	                	             ],
		    	                	             must: true,
		    	                	             lineNum: 11
		    	                },									
		    	                {
		    	                	type: "text",//
		    	                	name: "meetingHaveMealsNumber",//
		    	                	label: "用餐人数",//
		    	                	placeholder: "请输入用餐人数",
		    	                	// must: true,
		    	                	lineNum: 11
								},
		    	                {
		    	                	type: "text",//
		    	                	name: "breakfastlStandard",//
		    	                	label: "早餐标准",//
		    	                	placeholder: "请输入早餐标准",
		    	                	// must: true,
		    	                	lineNum: 12
		    	                },	
		    	                {
		    	                	type: "text",//
		    	                	name: "breakfastlNumber",//
		    	                	label: "早餐人数",//
		    	                	placeholder: "请输入早餐人数",
		    	                	// must: true,
		    	                	lineNum: 12
		    	                },	
		    	                {
		    	                	type: "text",//
		    	                	name: "lunchStandard",//
		    	                	label: "午餐标准",//
		    	                	placeholder: "请输入午餐标准",
		    	                	// must: true,
		    	                	lineNum: 13
		    	                },	
		    	                {
		    	                	type: "text",//
		    	                	name: "lunchNumber",//
		    	                	label: "午餐人数",//
		    	                	placeholder: "请输入午餐人数",
		    	                	// must: true,
		    	                	lineNum: 13
		    	                },	
		    	                {
		    	                	type: "text",//
		    	                	name: "dinnerStandard",//
		    	                	label: "晚餐标准",//
		    	                	placeholder: "请输入晚餐标准",
		    	                	// must: true,
		    	                	lineNum: 14
		    	                },	
		    	                {
		    	                	type: "text",//
		    	                	name: "dinnerNumber",//
		    	                	label: "晚餐人数",//
		    	                	placeholder: "请输入晚餐人数",
		    	                	// must: true,
		    	                	lineNum: 14
								},	
								
		    	                //11
		    	                {
		    	                	type: "text",//
		    	                	name: "joinUnitMainVenue",//
		    	                	label: "参会单位（主会场）",//
		    	                	placeholder: "单位与单位之间以”,“分隔",
		    	                	must: true,
		    	                	lineNum: 15
		    	                },	
		    	                //12
		    	                {
		    	                	type: "text",//
		    	                	name: "joinUnitHardVideo",//
		    	                	label: "参会单位（硬视频）",//
		    	                	placeholder: "单位与单位之间以”,“分隔",
		    	                	must: true,
		    	                	lineNum: 16
		    	                },	
		    	                //13
		    	                {
		    	                	type: "text",//
		    	                	name: "joinUnitSoftVideo",//
		    	                	label: "参会单位（软视频）",//
		    	                	placeholder: "单位与单位之间以”,“分隔",
		    	                	must: true,
		    	                	lineNum: 17
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
		    	                	type: "number",//
		    	                	name: "rostrumMike",//
		    	                	label: "主席台麦克（只）",//
		    	                	placeholder: "请输入主席台麦克数量（只）",
		    	                	must: true,
		    	                	lineNum: 18
		    	                },
		    	                //16
		    	                {
		    	                	type: "number",//
		    	                	name: "moveMike",//
		    	                	label: "移动麦克风（只）",//
		    	                	placeholder: "请输入移动麦克数量（只）",
		    	                	must: true,
		    	                	lineNum: 18
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
		    	                	             lineNum: 19
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
		    	                	             lineNum: 19
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
		    	                	             lineNum: 20
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
		    	                	             lineNum: 20
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
		    	                	             lineNum: 20
		    	                },
		    	                //22
		    	                {
		    	                	type: "select",
		    	                	name: "tableCard",
		    	                	label: "桌牌（请在特殊需求处备注名单）",
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
		    	                	type: "textarea",//
		    	                	name: "meetingSpecialNeeds",//
		    	                	label: "会议主要流程及特殊需要",//
		    	                	placeholder: "请输入会议主要流程及特殊需要",
		    	                	must: true
		    	                },
		    	                {
		    	                	type: "textarea",//
		    	                	name: "remakes",//
		    	                	label: "备注",//
									defaultValue: "1、本通知单用于1629、1号、2号会议室及多功能厅使用申请。\r"+
                                                  "2、仅申请1629常规会议或仅软视频（不需茶水服务）时，'确认/编号'发信息化管理部；其他类申请，'确认/编号'同时发信息化管理部和资产公司。调试时到信息化管理部1203拿钥匙。\r"+
                                                  "3、申请1号、2号、多功能厅不需要软视频时，'确认/编号'发资产公司；需要软视频时，'确认/编号'同时发资产公司和信息化管理部。\r\n"+
                                                  "4、所有会议调试时间一般安排在会前0.5天。\r"+
                                                  "5、预定会议室请提前2天预定。",		    	                
						            immutableAdd: true,
		    	                }								
		    	                ]
		       },
		       {
		    	   name: "附件信息",
		    	   type: "2",
		    	   tbName: "",
		    	   tbIdName: "fileId",//附件表主键id名
		    	   conditions: [
		    	                {
		    	                	type: "upload",//
		    	                	name: "sealFileList",//附件表名
		    	                	label: "附件1",//
		    	                	btnName: "添加附件",
		    	                	projectName: "zjSeal",
		    	                	// immutableAdd: true,
		    	                	uploadUrl: 'uploadFilesByFileName',
		    	                	fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
		    	                }
		    	                ]
		       }
		       ]
}
	break;
default:
	break;
}
//请求数据给第一个表单赋值
// myRefresh();
// function myRefresh() {
// l.ajax('getZjMeetingPlanIsLeaderJurisdiction', null, function (data) {

// })
// }
$(function(){
	$("select[name='fallInId']").change(function () {
		var fallInId = $(this).val();
		var checkedData={
			fallInId:fallInId
		}
		l.ajax("getZjMeetingPlanFallInDetail", checkedData, function (data) {
			if(data){
				$("input[name='meetingForm']").val(data.meetingForm);
				$("input[name='meetingPeopleNumber']").val(data.joinNumber);
				$("input[name='applyUnit']").val(data.fallInDeptName);
				$("input[name='meetingRoomTitle']").val(data.meetingNameStr);
			}else{
				$("input[name='meetingForm']").val("");
				$("input[name='meetingPeopleNumber']").val("");
				$("input[name='applyUnit']").val("");				
			}
		})		
	});
})
$.each(flowFormJson.tabs, function (i, tabItem) {//第一次遍历flowFormJson.tabs
	var $tabBtn = $('<span>' + tabItem.name + '</span>')//创建tab按钮$对象
	$tabBar.append($tabBtn)//向tab按钮控制条插入tab按钮
	var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>')//创建tab内容页面$对象
	var customBtnGroup;//tab内容页面中表单的底部按钮组配置
	if (tabItem.isMain) {//如果是主表单
		customBtnGroup = {
				btns: [
				       {
				    	   name: "launch",
				    	   label: '<i class="Hui-iconfont">&#xe603;</i> 发起'
				       },
				       {
				    	   name: "cancel",
				    	   label: "取消"
				       }
				       ],
				       callback: function (dataName, obj) {
				    	   switch (dataName) {
				    	   case "launch":
				    		   var body = {
				    			   flowId: flowId//流程id
				    	   }
				    		   for (var j = 0; j < flowFormJson.tabs.length; j++) {//第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
				    			   var tabItemj = flowFormJson.tabs[j]//模版中tabs数组的遍历元素数据对象
				    			   var tabObjDatas = tabCons[j].getDatas();//tab内容页面组的遍历对象获取数据对象
				    			   if (tabObjDatas.err.length) {//判断是否有错误（字段不能为空、超过个数限制等）
				    				   layer.alert(tabObjDatas.err.join("<br/>"), { icon: 0, }, function (index) {
				    					   $tabSystem.Huitab({
				    						   index: j
				    					   });
				    					   layer.close(index);
				    				   });
				    				   return//直接停止for循环，且for循环之后的代码也不执行
				    			   }
				    			   if (tabItemj.isMain) {//如果是主表
				    				   //给主表赋值
				    				   body["mainTableName"] = tabItemj.tbName;
				    				   body["mainTablePrimaryIdName"] = tabItemj.tbIdName;
				    				   body["mainTableDataObject"] = tabObjDatas.data;
				    				   body["apiBody"] = {}
				    				   //add by apih5 on 717
				    				   for (var key in tabObjDatas.data) {
				    					   body["apiBody"][key] = tabObjDatas.data[key]
				    				   }
				    				   //add by apih5 on 717 
									    // title: ['applyUserId', 'sendTime', '用印申请'], //标题字段
                                // body["title"] = tabObjDatas.data[flowFormJson.titleName];
                                var now = new Date();
                                var y = now.getFullYear();
                                var m = now.getMonth() + 1;
                                var d = now.getDate();

                                var h = now.getHours();
                                var mm = now.getMinutes();
                                var s = now.getSeconds();
                                var formatDate = y + '-' + m + '-' + d + ' ' + h + ':' + mm + ':' + s;
                                body["title"] = tabObjDatas.data['meetingRoomTitle'] ;
				    			   } else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
				    				   //给子表赋值-附件表
				    				   if (!body["subTableObject"]) { body["subTableObject"] = {} }
				    				   for (var key in tabObjDatas.data) {
				    					   var subTableDataObject = tabObjDatas.data[key];
				    					   //add by apih5 on 717
				    					   body["apiBody"][key] = tabObjDatas.data[key]
				    					   //add by apih5 on 717
				    					   body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: subTableDataObject }
				    				   }
				    			   } else {//如果是普通类型子表，type==="1"，目前只有1和2
				    				   //给子表赋值-普通表
				    				   if (!body["subTableObject"]) { body["subTableObject"] = {} }
				    				   //add by apih5 on 717
				    				   for (var key in tabObjDatas.data) {
				    					   body["apiBody"][key] = tabObjDatas.data[key]
				    				   }
				    				   //add by apih5 on 717
				    				   body["subTableObject"][tabItemj.tbName] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: tabObjDatas.data }
				    			   }
				    			   //add by apih5 on 717
				    			   body["apiName"] = "addZjMeetingReservationsFlow"//购置申请发起时调用
				    				   //add by apih5 on 717
				    		   }
							   var parm = body["apiBody"];
							   l.ajax("getZjMeetingApplyFlowConflictByReservationsTime", parm, function (_d, _m, _s, _r) {
								  if(_s){
								  //流程发起特殊代码---开始
								  layer.confirm("确定发起？", { icon: 0, }, function (index) {//流程发起请求
								   l.ajax("createOpenFlow", body, function (_d, _m, _s, _r) {
									   if (_s) {//发起成功，返回workId
										   loadFlow(_d.flowButtons, {
											   otherBody: {
												   title: body["title"],
												   flowId: flowId,//流程id
												   nodeId: _d.flowNode.nodeId,
												   trackId: _d.flowNode.trackId,
												   workId: _d.workId,
												   flowVars: _d.flowVars,
												   nodeVars: _d.nodeVars,
												   apih5FlowStatus: _d.apih5FlowStatus,
												   apiName: "updateZjMeetingReservationsFlow",
												   apiBody: { workId: _d.workId, apih5FlowStatus: _d.apih5FlowStatus }
											   },
											   endFn: function (buttonClass) { obj.close() },
											   callback: function (flowObj) {
												   flowObj.flowSelectOpen(0)
											   }
										   })
									   }
								   })
								   layer.close(index);
							   });
									  }else{
   
									  }
   
								  })
				    		   //流程发起特殊代码---结束
				    		   break;
				    	   case "cancel":
						    obj.close()
				    		   break;
				    	   default:
				    		   obj.close()
				    		   break;
				    	   }
				       }
		}
	} else {
		customBtnGroup = {
				btns: [],
				callback: function (dataName, obj) {
				}
		}
	}
	tabCons[i] = $tabCon.detailLayer({ customBtnGroup: customBtnGroup, conditions: tabItem.conditions })
	tabCons[i].setOpenData()
	tabCons[i].close = function () {
		//custom_close()
        parent.pager.page('remote')
		parent.layer.close(parent.myOpenLayer)		

	}
})


$tabSystem.append($tabBar).append(tabCons).Huitab({
	index: 0
});


function custom_close() {
	if
	(confirm("您确定要关闭本页吗？")) {
		window.opener = null;
		window.open('', '_self');
		window.close();
	}
	else { }
}