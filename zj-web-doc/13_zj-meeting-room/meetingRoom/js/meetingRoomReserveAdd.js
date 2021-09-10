var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var id = Lny.getUrlParam('id')
loadPage()
function loadPage() {
	l.ajax("getZjMeetingApplyFlowDetailByWorkId", { reservationsFlowId: id }, function (data, message, success) {
		if (success) {
			detailLayer.setOpenData(data);
		}
	});
}
var detailLayer = $('#detailLayer').detailLayer({
	layerArea: ['100%', '100%'],
	conditions: [						
		{
			type: "date",//
			name: "meetingStartTime",//
			label: "开始时间",//
			dateFmt: "yyyy-MM-dd HH:mm:ss",
			defaultValue: new Date(),
			lineNum: 1
		},
		{
			type: "date",//
			name: "meetingEndTime",//
			label: "结束时间",//
			dateFmt: "yyyy-MM-dd HH:mm:ss",
			defaultValue: new Date(),
			lineNum: 1
		},
		{
			type: "text",//
			name: "meetingRoomTitle",//
			label: "会议名称",//
			placeholder: "请输入会议名称",
			must:true

		},
		{
			type: "select",//
			name: "meetingRoomId",//
			label: "会议地点",
			placeholder: "请输入会议地点",
			ajax: {
				api: "getMeetingRoomAllListForFlow",//api名称
				valueName: "recordid",//设置value对应的接口字段名称
				textName: "meetingRoomName",//设置text对应的接口字段名称
			},
			must:true
		},
		{
			type: "textarea",//
			name: "meetingRoomContent",//
			label: "会议内容",//
			placeholder: "请输入会议内容"
		},
		{
            type: "pickTree",
            name: "meetingPersonList",
            label: "参会人员",
            pickType: {
            department: false,//部门列表对应接口字段名,false表示不开启该类
            member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
			}
		},
		{
            type: "checkbox",//
            name: "checkboxTest",//
            label: "会议提醒",//   
            optionData:[
                {
                    label:"1小时",
                    value:"1小时",
                },
                {
                    label:"3小时",
                    value:"3小时"
                },
                {
                    label:"24小时",
                    value:"24小时"
                } 
            ]
        }
	],
	onSave: function (obj, _params) {
		l.ajax('updateZjMeetingReservations', _params, function (data,message,success) {
		 if(success = "true"){
			pager.page('remote')
			obj.close()		
			}else{
			pager.page('remote')	
			}                
		})    
	},
	onAdd: function (obj, _params) {     
			l.ajax('addZjMeetingReservations', _params, function (data,message,success) {
			if(success = "true"){
				pager.page('remote')
				obj.close()		
				}else{
				pager.page('remote')	
				}
			})
	}
})