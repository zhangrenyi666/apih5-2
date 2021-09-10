var code = Lny.getUrlParam('code');
Lny.setCookie('code', code, 30);
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
	            	   "data": "title",//接口对应字段
	            	   "width": 300,
	            	   "title": "标题",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [3],//第几列
	            	   "data": "sendUserName",//接口对应字段
	            	   "title": "递交者",//表头名
	            	   "defaultContent": "-",//默认显示
	               },

	               {
	            	   "targets": [4],//第几列
	            	   "data": "nodeName",//接口对应字段
	            	   "title": "当前活动名称",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [5],//第几列
	            	   "data": "flowStatus",//接口对应字段
	            	   "title": "流程状态",//表头名
	            	   "defaultContent": "-",//默认显示 
	               },
	               {
	            	   "targets": [6],//第几列
	            	   "width": 150,
	            	   "data": "createTime",//接口对应字段
	            	   "title": "发起时间",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
	            	   }
	               },
				   {
	            	   "targets": [7],//第几列
	            	   "width": 150,
	            	   "data": "endTime",//接口对应字段
	            	   "title": "结束时间",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   if(data == null){
	            			   return "-";
	            		   } else {
	            			   return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
	            		   }
	            	   }
	               },
	               {
	            	   "targets": [8],//第几列
	            	   "width": 100,
	            	   "data": "flowName",//接口对应字段
	            	   "title": "所属于流程",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
				    {
	            	   "targets": [9],//第几列
	            	   "width": 100,
	            	   "data": "createUserName",//接口对应字段
	            	   "title": "申请人",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
				   {
	            	   "targets": [10],//第几列
	            	   "width": 100,
	            	   "data": "limitTime",//接口对应字段
	            	   "title": "超时时限",//表头名
	            	   "defaultContent": "-",//默认显示
	               }
	               ]
});
var pager = $("#pager").page({
	remote: {// ajax请求配置
		url: l.getApiUrl("getHasTodoList"),
		params: {
		flowId:"sanTran,sanDjBusiness,sanDjFourNew,sanDjStyleGame,sanDjSkill,sanDjScientific,sanDjMonograp,sanDjSuggest,sanDjPaper,sanDjProfessionalTitle,sanDjQc,sanDjHonor,sanDjCredential,sanDjCollege,sanDjEducation,sanDjQcComprehensive,sanDjQcBusinessElv"
		},
		success: function (result) {
			if (result.success) {
				var data = result.data;							
                $.each(data, function (index, item) {						
				   l.ajax("getZjSanDjFlowTotalList", data[index], function (params) {
					    item.rowIndex = index;
						item.createUserName = params.perName;
						item.limitTime = params.limitTime;
						item.redFlag = params.redFlag;
						table.clear().rows.add(data).draw();	
				    })				    				  
                })																
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
	case "process":
		if (checkedData.length == 1) {
			myOpen("流程处理", checkedData[0], " ")
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
	case "del":
		if (checkedData.length == 0) {
			layer.alert("未选择任何项", { icon: 0, }, function (index) {
				layer.close(index);
			});
		} else {
			 layer.confirm("删除的数据无法恢复，请谨慎操作！！！确定删除？", { icon: 0, }, function (index) {
                            l.ajax("batchDeleteZjSanDjFlow", checkedData, function () {
                             pager.page('remote')
                            })
                          layer.close(index);
                        });
			 /* for(var i=0;i<checkedData.length;i++){
				    if(checkedData[i].flowStatus != '退回'){
				        layer.alert("包含未退回的审批，不能删除，请重新选择！", { icon: 0, }, function (index) {
                        layer.close(index);
                       });	
                      break;					
				    }else{
				        layer.confirm("确定删除？", { icon: 0, }, function (index) {
                            l.ajax("batchDeleteZjSanDjFlow", checkedData, function () {
                             pager.page('remote')
                            })
                          layer.close(index);
                        });
				    }
				}	 */ 			
		}
		break;	
	}
})


var myOpenLayer;
function myOpen(title, data, url) {

	
	if(data.flowId == 'sanDjBusiness'){//----三公司测试---流程-----个人奖励评分流程---------------------------------
		if(data.title =='工龄申请'){
		url = 'flowPersonageAgeProcess'
		}else{
		url = 'flowPersonageProcess'
		}	
	}else if(data.flowId == 'sanDjFourNew'){//----三公司测试---流程-----集体-----------
		url = 'flowCollectiveProcess'
	}else if(data.flowId == 'sanDjStyleGame'){//----三公司测试---流程-----一公局集团奖项------------------
		url = 'flowGroupAwardProcess'
	}else if(data.flowId == 'sanDjSkill'){//----三公司测试---流程-----技术创新成果奖-----------------
		url = 'flowSkillInnovationProcess'
	}else if(data.flowId == 'sanDjScientific'){//----三公司测试---流程-----专有科技成果奖-----------
		url = 'flowProprietaryTechnologyProcess'
	}else if(data.flowId == 'sanDjMonograp'){//----三公司测试---流程-----专利---------------------
		url = 'flowPatentProcess'
	}else if(data.flowId == 'sanDjSuggest'){//----三公司测试---流程-----合理化建议及技术革新成果、五小---------------
		url = 'flowSuggestProcess'
	}else if(data.flowId == 'sanDjPaper'){//----三公司测试---流程-----学术著作、论文-----------------------------------
		url = 'flowPaperProcess'
	}else if(data.flowId == 'sanDjProfessionalTitle'){//----三公司测试---流程-----施工工法、技术标准--------------------
		url = 'flowSkillStandardProcess'
	}else if(data.flowId == 'sanDjQc'){//----三公司测试---流程-----QC成果-------------------------------------------
		url = 'flowQcProcess'
	}else if(data.flowId == 'sanDjHonor'){//----三公司测试---流程-----科技研发项目---------------------------------------
		url = 'flowRdProjectsProcess'
	}else if(data.flowId == 'sanDjCredential'){//----三公司测试---流程-----施工工艺标准-----------------------------------
		url = 'flowConstructStandardProcess'
	}else if(data.flowId == 'sanDjCollege'){//----三公司测试---流程-----证件-----------------------------------
		url = 'flowCertificateProcess'
	}else if(data.flowId == 'sanDjEducation'){//----三公司测试---流程-----职称-----------------------------------
		url = 'flowProfessionalProcess'
	}else if(data.flowId == 'sanDjQcComprehensive'){//----三公司测试---流程-----综合评价-----------------------------------
		url = 'flowComprehensiveProcess'
	}else if(data.flowId == 'sanDjQcBusinessElv'){//----三公司测试---流程-----业务评价-----------------------------------
		url = 'flowBusinessProcess'
	}else if(data.flowId == 'sanDjPartyApply'){//-------三公司-----流程----发展对象申请
        url = 'partyApplyProcess'
	}else if(data.flowId == 'sanDjPartyMemberTran'){//-------三公司-----流程----党支部委员变更申请
        url = 'partyMemberTransferProcess'
	}else if(data.flowId == 'sanDjPartySelect'){//-------三公司-----流程----优秀共产党员推荐
        url = 'partySelectionListProcess'
	}else if(data.flowId == 'sanDjPartyReview'){//-------三公司-----流程----先进党支部推荐
        url = 'partyReviewListProcess'
	}else if(data.flowId == 'sanDjPartyIntroduce'){//-------三公司-----流程----党员党组织关系介绍信推荐
        url = 'partyIntroduceListProcess'
	}else if(data.flowId == 'sanDjUnionApply'){//-------三公司-----流程----工会会员申请
        url = 'unionApplyProcess'
	}else if(data.flowId == 'sanDjReunionMemberTran'){//-------三公司-----流程----变更团支部委员的申请
        url = 'reunionMemberTransferProcess'
	}else if(data.flowId == 'sanDjReunionMemberOrg'){//-------三公司-----流程----团支部申请
        url = 'reunionMemberOrgProcess'
	}else if(data.flowId == 'sanDjPartyMemberOrg'){//-------三公司-----流程----党支部委员分工请示
        url = 'partyMemberOrgProcess'
	}else if(data.flowId == 'sanDjUnionMemberOrg'){//-------三公司-----流程----工会委员会申请
        url = 'unionMemberOrgProcess'
	}else if(data.flowId == 'sanDjUnionMemberTransfer'){//-------三公司-----流程----变更工会委员的申请
        url = 'unionMemberTransferProcess'
	}else if(data.flowId == 'sanDjUnionIntroduce'){//-------三公司-----流程----工会会员组织关系介绍信
        url = 'unionIntroduceListProcess'
	}else if(data.flowId == 'sanDjUnionMarriageBroker'){//-------三公司-----流程----工会红娘奖励登记
        url = 'unionMarriageBrokerTwoProcess'
	}else if(data.flowId == 'sanTran'){//-------三公司-----流程----人员调动
        url = 'flowTranProcess'
	}
	
	






	var options = {
			type: 2,
			title: title,
			content: url + ".html?workId=" + data.workId + "&trackId=" + data.trackId + "&nodeId=" + data.nodeId+"&code="+code
	}
	myOpenLayer = layer.open(options)
	layer.full(myOpenLayer)
}