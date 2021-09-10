var flowId = l.getUrlParam('id') || "xmMeetingRoom";//流程模版id
var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);

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
case "xmMeetingRoom":
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
            type: "pickTree",//
            name: "useUnit",//接口字段名
            label: "申请单位",//
            must: true,
			lineNum: 2,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },						
/*		
								{
                                    type: "select",
                                    name: "applyUnit",
                                    label: "申请单位",
			                        search: true,
                                    selectList: [//当类型为select时，option配置
                                     ],
                                    ajax: {
                                    api: "getZjMeetingSysDepartmentListAllList",
                                    valueName: "departmentName",
                                    textName: "departmentName"
                                },
			                        must: true,
									lineNum: 2
                                },
								*/
		    	                //6
		    	              		    	                {
		    	                	type: "select",
		    	                	name: "meetingDecoration",
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
		    	                	             must: true,
		    	                	             lineNum: 6
		    	                },									
		    	                //7
		    	                {
		    	                	type: "select",
		    	                	name: "meetingForm",
		    	                	label: "会议形式",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             {
		    	                	            	 value: "常规会议",//输入字段的值
		    	                	            	 text: "常规会议",//显示中文名
		    	                	            	 selected: true//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: "仅硬视频",//输入字段的值
		    	                	            	 text: "仅硬视频",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: "仅软视频",//输入字段的值
		    	                	            	 text: "仅软视频",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: "硬视频+软视频",//输入字段的值
		    	                	            	 text: "硬视频+软视频",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             }
		    	                	             ],
		    	                	             must: true,
		    	                	             lineNum: 7
		    	                },	
		    	                //8
		    	                {
		    	                	type: "date",//
		    	                	name: "debuggingTime",//
		    	                	label: "调试时间",//
		    	                	dateFmt: "yyyy-MM-dd HH:mm:ss",
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
		    	                	            	 api: "getMeetingRoomAllListForFlow",//api名称
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
		    	                	type: "number",//
		    	                	name: "rostrumMike",//
		    	                	label: "主席台麦克（只）",//
		    	                	placeholder: "请输入主席台麦克数量（只）",
		    	                	must: true,
		    	                	lineNum: 15
		    	                },
		    	                //16
		    	                {
		    	                	type: "number",//
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
		    	                //28
		    	                {
		    	                	type: "text",//
		    	                	name: "applyUnitContacts",//
		    	                	label: "申请单位联系人",//
		    	                	placeholder: "请输入申请单位联系人",
		    	                	must: true,
		    	                	lineNum: 25
		    	                },
		    	                //29
		    	                {
		    	                	type: "text",//
		    	                	name: "applyUnitContactsWay",//
		    	                	label: "联系方式",//
		    	                	placeholder: "请输入联系方式",
		    	                	must: true,
		    	                	lineNum: 25
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
									defaultValue: "1、所有会议调试时间一般安排在会前0.5天。\r"+
                                                  "2、预定会议室请提前2天预定。",		    	                
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
				    				   //add by lny on 717
				    				   for (var key in tabObjDatas.data) {
				    					   body["apiBody"][key] = tabObjDatas.data[key]
				    				   }
				    				   //add by lny on 717 
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


				    					   //add by lny on 717
				    					   body["apiBody"][key] = tabObjDatas.data[key]
				    					   //add by lny on 717


										  
										   
										   
										   
				    					   body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: subTableDataObject }
				    				   }
				    			   } else {//如果是普通类型子表，type==="1"，目前只有1和2
				    				   //给子表赋值-普通表
				    				   if (!body["subTableObject"]) { body["subTableObject"] = {} }


				    				   //add by lny on 717
				    				   for (var key in tabObjDatas.data) {
				    					   body["apiBody"][key] = tabObjDatas.data[key]
				    				   }
				    				   //add by lny on 717



				    				   body["subTableObject"][tabItemj.tbName] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: tabObjDatas.data }
				    			   }



				    			   //add by lny on 717
				    			   body["apiName"] = "addZjMeetingReservationsFlow"//购置申请发起时调用
				    				   //add by lny on 717



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
						   window.location.href = 'zjMeetingRoomArrangement.html?code=' + code;
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
   layer.alert("提交成功！", { icon: 1, }, function (index) {
     window.location.href = 'zjMeetingRoomArrangement.html?code=' + code;
    });
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