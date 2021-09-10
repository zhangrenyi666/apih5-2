var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
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
            "data": "empNo",//接口对应字段
            "title": "员工编号",//表头名
            "defaultContent": "-",//默认显示	
        },	 
		{
            "targets": [2],//第几列
           "data": function (row) {
                return row
            },//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示
			"render":function(data,index,row){
                var a;
                if(data){   
                   a = '<a style="color:blue;"  onclick="myOpen(\'人员信息详情\',\'' + data.personRegisterId + '\',\'' + 'personRegisterInfoDetail' + '\')" href="javascript:void(0);">' + data.perName + '</a>'
                }
                return a;
            }				
        },	
        {
            "targets": [3],//第几列
            "data": "proName",//接口对应字段
            "title": "所在项目",//表头名
            "defaultContent": "-",//默认显示
        },       
		{
            "targets": [4],//第几列
            "data": "nation",//接口对应字段
            "title": "民族",//表头名
            "defaultContent": "-",//默认显示
        },		               
		{
            "targets": [5],//第几列
            "data": "sex",//接口对应字段
            "title": "性别",//表头名
            "defaultContent": "-",//默认显示
				"render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "男";
                        break;
                    case "1": text = "女";
                        break;                 
                    default: text = "未知";
                        break;
                }
                return text
            }
        },
		{
            "targets": [6],//第几列
            "data": "nativePlace",//接口对应字段
            "title": "籍贯",//表头名
            "defaultContent": "-",//默认显示
        },	
        {
            "targets": [7],//第几列
            "data": "politicsStatus",//接口对应字段
            "title": "政治面貌",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "正式党员";
                        break;
                    case "1": text = "团员";
                        break;                  
					case "2": text = "群众";
                        break;
					case "3": text = "预备党员";
                        break;
					case "4": text = "中共党员";
                        break;
                    default: text = "未知";
                        break;
                }
                return text
            }
        },
		{
            "targets": [8],//第几列
            "data": "depName",//接口对应字段
            "title": "部门",//表头名
            "defaultContent": "-",//默认显示
        },	
		{
            "targets": [9],//第几列
            "data": "postRank",//接口对应字段
            "title": "岗位级别",//表头名
            "defaultContent": "-",//默认显示
        },	
        {
            "targets": [10],//第几列
            "data": "duty",//接口对应字段
            "title": "职务",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [11],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "empNo",//输入字段名
            label: "员工编号",//输入字段对杨的中文名称
            placeholder: "请输入员工编号",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "projectId",//输入字段名
            label: "所在项目",//输入字段对杨的中文名称
            placeholder: "请输入所在项目",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "perName",//输入字段名
            label: "姓名",//输入字段对杨的中文名称
            placeholder: "请输入名称",
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
        pager.page('remote', _params)
    }
})
var allData;
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjSanDjPersonRegisterForLeaveList"),
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


var transferLayer = $('#transferLayer').detailLayer({
    layerArea:['70%', '90%'],
    conditions: [
          {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "personRegisterId",//输入字段名
        },	
        {
            type: "text",//
            name: "perName",//
            label: "姓名",//
            placeholder: "请输入姓名",
			immutableAdd: true,
            must: true
        }, 
		{
            type: "pickTree",
            name: "oaProject",
            label: "所在项目",
			must: true,
			//immutableAdd: true,
			//immutable: true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },
		{
            type: "text",//
            name: "idCard",//
            label: "公民身份证号",//
			immutableAdd: true,
            placeholder: "请输入公民身份证号"
        },	
		{
            type: "text",//
            name: "empNo",//
            label: "员工编号",//
            placeholder: "请输入员工编号",
			immutableAdd: true,
            must: true
        },       
		{
            type: "text",//
            name: "nation",//
            label: "民族",//
			immutableAdd: true,
            placeholder: "请输入民族"
        },
		{
            type: "date",//
            name: "birth",//
            label: "出生日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "birth",
			immutableAdd: true,
            must: true
        },		
		{
            type: "select",//
            name: "sex",//
            label: "性别",//
			immutableAdd: true,
            selectList: [//当类型为select时，option配置         
                {
                    value: 0,//输入字段的值
                    text: "男",//显示中文名
                    selected: false//默认是否选中
                },               
				{
                    value: 1,//输入字段的值
                    text: "女",//显示中文名
                    selected: false//默认是否选中
                }				
            ],
            must: true
        },
		{
            type: "select",//
            name: "category",//
            label: "人员类别",//
			immutableAdd: true,
            selectList: [//当类型为select时，option配置         
                {
                    value: 0,//输入字段的值
                    text: "正式党员",//显示中文名
                    selected: false//默认是否选中
                },               
				{
                    value: 1,//输入字段的值
                    text: "预备党员",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 2,//输入字段的值
                    text: "团员",//显示中文名
                    selected: false//默认是否选中
                },               
				{
                    value: 3,//输入字段的值
                    text: "群众",//显示中文名
                    selected: false//默认是否选中
                }		
            ],
            must: true
        },		
		{
            type: "date",//
            name: "joinDate",//
            label: "加入党组织日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
			immutableAdd: true,
            id: "joinDate"
        },
		{
            type: "date",//
            name: "regularizationDate",//
            label: "转为正式党员日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
			immutableAdd: true,
            id: "regularizationDate"
        },		
        {
            type: "text",//
            name: "nativePlace",//
            label: "籍贯",//
			immutableAdd: true,
            placeholder: "请输入籍贯"
        },
		{
            type: "date",//
            name: "workTime",//
            label: "参加工作时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "workTime",
			immutableAdd: true,
            must: true
        },
		{
            type: "date",//
            name: "joinSysTime",//
            label: "进入本系统时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
			immutableAdd: true,
            id: "joinSysTime"
        },
		{
            type: "select",//
            name: "politicsStatus",//
            label: "政治面貌",//
			immutableAdd: true,
             selectList: [//当类型为select时，option配置         
                {
                    value: 0,//输入字段的值
                    text: "正式党员",//显示中文名
                    selected: false//默认是否选中
                },               
				{
                    value: 1,//输入字段的值
                    text: "团员",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "群众",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "预备党员",//显示中文名
                    selected: false//默认是否选中
                },	
               {
                    value: 4,//输入字段的值
                    text: "中共党员",//显示中文名
                    selected: false//默认是否选中
                }					
            ],
			 must: true
        },		
		{
            type: "date",//
            name: "joinPartyTime",//
            label: "参加党派时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
			immutableAdd: true,
            id: "joinPartyTime"
        },		
		{
            type: "text",//
            name: "householdPlace",//
            label: "户口所在地",//
            placeholder: "请输入户口所在地",
			immutableAdd: true,
            must: true
        },
		{
            type: "text",//
            name: "depName",//
            label: "部门名称",//
            placeholder: "请输入部门名称",
			immutableAdd: true,
            must: true
        },	
		{
            type: "text",//
            name: "postRank",//
            label: "岗位级别",//
			immutableAdd: true,
            placeholder: "请输入岗位级别"
        },
		{
            type: "text",//
            name: "duty",//
            label: "职务",//
			immutableAdd: true,
            placeholder: "请输入职务"
        },
		{
            type: "text",//
            name: "education",//
            label: "学历",//
			immutableAdd: true,
            placeholder: "请输入学历"
        },
		{
            type: "text",//
            name: "degree",//
            label: "学位",//
			immutableAdd: true,
            placeholder: "请输入学位"
        },
		{
            type: "text",//
            name: "graduateSchool",//
            label: "毕业院校",//
			immutableAdd: true,
            placeholder: "请输入毕业院校"
        },
		{
            type: "text",//
            name: "major",//
            label: "所学专业",//
			immutableAdd: true,
            placeholder: "请输入所学专业"
        },
		{
            type: "date",//
            name: "graduateTime",//
            label: "毕业时间",//
            dateFmt: "yyyy-MM-dd",
			immutableAdd: true,
            defaultValue: new Date(),
            id: "graduateTime"
        },
		{
            type: "text",//
            name: "professAndTechQualification",//
            label: "专业技术资格",//
			immutableAdd: true,
            placeholder: "请输入专业技术资格"
        },
		{
            type: "date",//
            name: "getProfessAndTechQualification",//
            label: "取得专业技术资格时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
			immutableAdd: true,
            id: "getProfessAndTechQualification"
        },
		{
            type: "text",//
            name: "practiceQualification",//
            label: "执业资格",//
			immutableAdd: true,
            placeholder: "请输入执业资格"
        },
		{
            type: "text",//
            name: "skillLevel",//
            label: "工人技术等级",//
			immutableAdd: true,
            placeholder: "请输入工人技术等级"
        },
		{
            type: "text",//
            name: "workerTechType",//
            label: "工人技术工种",//
			immutableAdd: true,
            placeholder: "请输入工人技术工种"
        },
		{
            type: "date",//
            name: "initiationTime",//
            label: "入会时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
			immutableAdd: true,
            id: "initiationTime"
        },
		{
            type: "text",//
            name: "height",//
            label: "身高",//
			immutableAdd: true,
            placeholder: "请输入身高"
        },
		{
            type: "text",//
            name: "weight",//
            label: "体重",//
            placeholder: "请输入体重"
        },
		{
            type: "textarea",//
            name: "hobby",//
            label: "性格爱好",//
			immutableAdd: true,
            placeholder: "请输入性格爱好"
        },
		{
            type: "textarea",//
            name: "houseCondition",//
            label: "住房情况",//
			immutableAdd: true,
            placeholder: "请输入住房情况"
        },
		{
            type: "textarea",//
            name: "criteria",//
			immutableAdd: true,
            label: "择偶条件",//
            placeholder: "请输入择偶条件"
        },
		{
            type: "text",//
            name: "address",//
            label: "家庭住址",//
			immutableAdd: true,
            placeholder: "请输入家庭住址"
        },
		{
            type: "text",//
            name: "phone",//
            label: "联系电话",//
			immutableAdd: true,
            placeholder: "请输入联系电话"
        },		
		{
            type: "text",//
            name: "telephone",//
            label: "固定电话",//
			immutableAdd: true,
            placeholder: "请输入固定电话"
        }         
    ],
    onSave: function (obj, _params) {
        l.ajax('returnZjSanDjPersonRegister', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    }
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
             case "edit":
            if (checkedData.length == 1) {
                transferLayer.open(checkedData[0]);
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
    }
})

var myOpenLayer
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}

function myOpenFlow(title, data, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?workId=" + data.workId + "&nodeId=" + data.nodeId + "&code=" + code + "&personRegisterId=" + data.personRegisterId
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}