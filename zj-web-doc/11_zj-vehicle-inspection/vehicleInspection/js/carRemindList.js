var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var table = $('#table').DataTable({
    "info": false,// 是否显示数据信息
    "paging": false,// 是否开启自带分页
    "ordering": false, // 是否开启DataTables的高度自适应
    "processing": false,// 是否显示‘进度’提示
    "searching": false,// 是否开启自带搜索
    "autoWidth": false,// 是否监听宽度变化
    "columnDefs": [// 表格列配置
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
            "targets": [1],// 第几列
            "data": "financialNumber",// 接口对应字段
            "title": "财务编号",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [2],// 第几列
            "data": "carName",// 接口对应字段
            "title": "车辆名称",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [3],// 第几列
            "data": "modelNumber",// 接口对应字段
            "title": "型号",// 表头名
            "defaultContent": "-",// 默认显示
        },
		{
            "targets": [4],// 第几列
            "data": "useUnit",// 接口对应字段
            "title": "使用单位",// 表头名
            "defaultContent": "-",// 默认显示
        },
		        {
            "targets": [5],// 第几列
            "data": "subordinateUnit",// 接口对应字段
            "title": "所属单位",// 表头名
            "defaultContent": "-",// 默认显示
        },
		        {
            "targets": [6],// 第几列
            "data": "location",// 接口对应字段
            "title": "所在地点",// 表头名
            "defaultContent": "-",// 默认显示
        },
		        {
            "targets": [7],// 第几列
            "data": "accountedForDate",// 接口对应字段
            "title": "入账日期",// 表头名
            "defaultContent": "-",// 默认显示
			"render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [8],// 第几列
            "data": "isCarCheck",// 接口对应字段
            "title": "车检状态",// 表头名
            "defaultContent": "-",// 默认显示
            "render": function (data) {// 自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "未办理"
                        break
                    case "1":
                        r = "待办理"
                        break
	                case "2":
                        r = "审批中"
                        break
					case "3":
                        r = "通过"
                        break
					case "4":
                        r = "驳回"
                        break
                }
                return r
            }
        },
		{
            "targets": [9],// 第几列
            "data": "isCarInsurance",// 接口对应字段
            "title": "保险状态",// 表头名
            "defaultContent": "-",// 默认显示
            "render": function (data) {// 自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "未办理"
                        break
                    case "1":
                        r = "待办理"
                        break
	                case "2":
                        r = "审批中"
                        break
					case "3":
                        r = "通过"
                        break
					case "4":
                        r = "驳回"
                        break
                }
                return r
            }
        },
        {
            "targets": [10],// 第几列
            "data": "modifyUser",// 接口对应字段
            "title": "操作员",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [11],// 第几列
            "data": "modifyTime",// 接口对应字段
            "title": "操作时间",// 表头名
            "defaultContent": "-",// 默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
		        {
        	"targets": [12],// 第几列
            "width": 80,
            "data": function (row) {
                return row
            },// 接口对应字段
            "title": "操作",// 表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">'
				html += '<li><a href="javascript:void(0);" onclick="editTableRow('+data.rowIndex+')">详情</a></li>';
				// 申请者申请
                if((data.isCarInsurance =="0") || (data.isCarInsurance =="1") || (data.isCarInsurance =="4") 
					|| (data.isCarCheck =="0") || (data.isCarCheck =="1") || (data.isCarCheck =="4")){
				if(data.isLeader=="0"){
                    html += '<li><a href="javascript:void(0);" onclick="applyOpen('+data.rowIndex+')">车检/保险申请</a></li>';
				}
                }
                // 领导审批
				if(data.isLeader=="1"){
				   // 如果车险状态为审批中则显示
				if(data.isCarInsurance == "2"){
				    html += '<li><a href="javascript:void(0);" onclick="approvalOpen(\'' + data.id + '\',\'' + '1' + '\')">车辆保险审批</a></li>';
				}
                // 如果车检状态为审批中则显示
				if(data.isCarCheck == "2"){
					html += '<li><a href="javascript:void(0);" onclick="approvalOpen(\'' + data.id + '\',\'' + '0' + '\')">车辆年检审批</a></li>';
				}				
                }
                html += '</ul></span>'
                return html;
            }
        }
    ]
});
// 判断当前用户是否管理员
	l.ajax('getCjLoginUser',{}, function (res) {
	    if(res.isAdmin === "0"){
			$("#SetupButton").hide();
	}        
    })
var form = $('#form').filterfrom({
    conditions: [// 表单项配置
		{
            type: "text",// 三种形式：text,select,date,
            name: "carName",// 输入字段名
            label: "车辆名称",// 输入字段对杨的中文名称
            placeholder: "请输入车辆名称",
        },
        {
            type: "text",// 三种形式：text,select,date,
            name: "financialNumber",// 输入字段名
            label: "财务编号",// 输入字段对杨的中文名称
            placeholder: "请输入财务编号",
        },  
		{
            type: "text",// 三种形式：text,select,date,
            name: "useUnit",// 输入字段名
            label: "使用单位",// 输入字段对杨的中文名称
            placeholder: "请输入使用单位",
        },
		{
            type: "select",
            name: "isCarCheck",
            label: "车检状态",
            selectList: [// 当类型为select时，option配置
                {
                    value: "",// 输入字段的值
                    text: "全部",// 显示中文名
                    selected: true// 默认是否选中
                },
                {
                    value: 0,// 输入字段的值
                    text: "未办理",// 显示中文名
                    selected: false// 默认是否选中
                },
                {
                    value: 1,// 输入字段的值
                    text: "待办理",// 显示中文名
                    selected: false// 默认是否选中
                },
				{
                    value: 2,// 输入字段的值
                    text: "审批中",// 显示中文名
                    selected: false// 默认是否选中
                },
				{
                    value: 3,// 输入字段的值
                    text: "通过",// 显示中文名
                    selected: false// 默认是否选中
                },
				{
                    value: 4,// 输入字段的值
                    text: "驳回",// 显示中文名
                    selected: false// 默认是否选中
                }
            ],
        },
				{
            type: "select",
            name: "isCarInsurance",
            label: "保险状态",
            selectList: [// 当类型为select时，option配置
                {
                    value: "",// 输入字段的值
                    text: "全部",// 显示中文名
                    selected: true// 默认是否选中
                },
                {
                    value: 0,// 输入字段的值
                    text: "未办理",// 显示中文名
                    selected: false// 默认是否选中
                },
                {
                    value: 1,// 输入字段的值
                    text: "待办理",// 显示中文名
                    selected: false// 默认是否选中
                },
				{
                    value: 2,// 输入字段的值
                    text: "审批中",// 显示中文名
                    selected: false// 默认是否选中
                },
				{
                    value: 3,// 输入字段的值
                    text: "通过",// 显示中文名
                    selected: false// 默认是否选中
                },
				{
                    value: 4,// 输入字段的值
                    text: "驳回",// 显示中文名
                    selected: false// 默认是否选中
                }
            ],
        }
    ],
    onSearch: function (arr) {// 搜索按钮回调
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
         // console.log('搜索参数是：',_params)
        pager.page('remote',0, _params)
    },
    onReset: function (arr) {// 重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
})
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjCjCarRemindList"),
        params: {
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
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

// 相关设置
var setupLayer = $('#setupLayer').detailLayer({
    conditions: [
        {
            type: "hidden",// 五种形式：text,select,date,hidden,textarea,
            name: "recordid",// 输入字段名
        },
		{
            type: "pickTree",
            name: "glrymc",
            label: "车辆管理员",
            pickType: {
            department: false,// 部门列表对应接口字段名,false表示不开启该类
            member: "oaMemberList",// 成员列表对应接口字段名,false表示不开启该类
            },
        },
        {
            type: "text",//
            name: "insuranceYear",//
            label: "保险提醒（年）",//
            // placeholder: "请输入单位名称",
            // must: true
        },
        {
            type: "text",//
            name: "carRemindYear",//
            label: "车检提醒（年）",//
            // placeholder: "请输入备注",
        },
        {
            type: "text",//
            name: "timeRemind",//
            label: "时间提醒（天）",//
            // placeholder: "请输入备注",
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjCjCarRemind', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjCjCarRemind', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    }
})
// 详情
var detailLayer = $('#detailLayer').detailLayer({
	layerArea:['100%', '100%'],
       conditions: [
        {
            type: "hidden",// 五种形式：text,select,date,hidden,textarea,
            name: "recordid",// 输入字段名
        },
		{
            type: "text",//
            name: "carName",//
            label: "车辆名称",//
			immutableAdd:true
        },
        {
            type: "text",//
            name: "modelNumber",//
            label: "车辆型号",//
			immutableAdd:true
        },
		        {
            type: "text",//
            name: "useUnit",//
            label: "使用单位",//
			immutableAdd:true
        },
		{
            type: "text",//
            name: "subordinateUnit",//
            label: "所属单位",//
			immutableAdd:true
        },
		        {
            type: "text",//
            name: "location",//
            label: "所在地点",//
			immutableAdd:true
        },
		{
            type: "date",//
            name: "accountedForDate",//
            label: "入账日期",//
			dateFmt: "yyyy-MM-dd HH:mm:ss",
			id:"accountedForDate",
			immutableAdd:true
        },
		{
            type: "select",
            name: "isCarCheck",
            label: "车检状态",
            selectList: [// 当类型为select时，option配置
                {
                    value: "",// 输入字段的值
                    text: "全部",// 显示中文名
                    selected: true// 默认是否选中
                },
                {
                    value: 0,// 输入字段的值
                    text: "未办理",// 显示中文名
                    selected: false// 默认是否选中
                },
                {
                    value: 1,// 输入字段的值
                    text: "待办理",// 显示中文名
                    selected: false// 默认是否选中
                },
				{
                    value: 2,// 输入字段的值
                    text: "审批中",// 显示中文名
                    selected: false// 默认是否选中
                },
				{
                    value: 3,// 输入字段的值
                    text: "通过",// 显示中文名
                    selected: false// 默认是否选中
                },
				{
                    value: 4,// 输入字段的值
                    text: "驳回",// 显示中文名
                    selected: false// 默认是否选中
                }
            ],
			immutableAdd:true
        },
		//车辆年检申请人
		{
            type: "text",//
            name: "cjApplyUser",//
            label: "车辆年检申请人",//
			immutableAdd:true
        },
	    {
            type: "upload",//
            name: "carCheckImageList",//
            label: "车辆年检图片",//
            btnName: "添加图片",
			projectName:"zj-vehicle-inspection",
            fileType: ["jpg", "jpeg", "png", "gif"],
			immutableAdd:true
        },
        {
            type: "text",//
            name: "approvalUser",//
            label: "审核人",//
			immutableAdd:true
        },
		//审批结果
		{
            type: "select",
            name: "cjApprovalResult",
            label: "审批结果",
            selectList: [// 当类型为select时，option配置
			    {
                    value: "",// 输入字段的值
                    text: "未审批",// 显示中文名
                    selected: true// 默认是否选中
                },
                {
                    value: 0,// 输入字段的值
                    text: "同意",// 显示中文名
                    selected: false// 默认是否选中
                },
                {
                    value: 1,// 输入字段的值
                    text: "不同意",// 显示中文名
                    selected: false// 默认是否选中
                }
            ],
			immutableAdd:true
        },
		//审批意见
        {
            type: "textarea",//
            name: "cjApprovalOpinion",//
            label: "审批意见",//
			placeholder: "未填写",
			// must: true
			immutableAdd:true
        },
		{
            type: "select",
            name: "isCarInsurance",
            label: "保险状态",
            selectList: [// 当类型为select时，option配置
                {
                    value: "",// 输入字段的值
                    text: "全部",// 显示中文名
                    selected: true// 默认是否选中
                },
                {
                    value: 0,// 输入字段的值
                    text: "未办理",// 显示中文名
                    selected: false// 默认是否选中
                },
                {
                    value: 1,// 输入字段的值
                    text: "待办理",// 显示中文名
                    selected: false// 默认是否选中
                },
				{
                    value: 2,// 输入字段的值
                    text: "审批中",// 显示中文名
                    selected: false// 默认是否选中
                },
				{
                    value: 3,// 输入字段的值
                    text: "通过",// 显示中文名
                    selected: false// 默认是否选中
                },
				{
                    value: 4,// 输入字段的值
                    text: "驳回",// 显示中文名
                    selected: false// 默认是否选中
                }
            ],
			immutableAdd:true
        },
		//车辆保险申请人
		{
            type: "text",//
            name: "bxApplyUser",//
            label: "车辆保险申请人",//
			immutableAdd:true
        },
		{
            type: "upload",//
            name: "carInsuranceImageList",//
            label: "车辆保险图片",//
            btnName: "添加图片",
			projectName:"zj-vehicle-inspection",
            fileType: ["jpg", "jpeg", "png", "gif"],
			immutableAdd:true
        },
		{
            type: "text",//
            name: "approvalUser",//
            label: "审核人",//
			immutableAdd:true
        },
		//审批结果
		{
            type: "select",
            name: "bxApprovalResult",
            label: "审批结果",
            selectList: [// 当类型为select时，option配置
			    {
                    value: "",// 输入字段的值
                    text: "未审批",// 显示中文名
                    selected: true// 默认是否选中
                },
                {
                    value: 0,// 输入字段的值
                    text: "同意",// 显示中文名
                    selected: false// 默认是否选中
                },
                {
                    value: 1,// 输入字段的值
                    text: "不同意",// 显示中文名
                    selected: false// 默认是否选中
                }
            ],
			immutableAdd:true
        },
		//审批意见
        {
            type: "textarea",//
            name: "bxApprovalOpinion",//
            label: "审批意见",//
			placeholder: "未填写",
			immutableAdd:true
        }
    ]
})
// 保险/车检申请
var applyLayer = $('#applyLayer').detailLayer({
    conditions: [
        {
            type: "hidden",// 五种形式：text,select,date,hidden,textarea,
            name: "id",// 输入字段名
        },
		{
            type: "hidden",// 五种形式：text,select,date,hidden,textarea,
            name: "approvalId",// 输入字段名
        },
		{
            type: "hidden",// 五种形式：text,select,date,hidden,textarea,
            name: "isCarCheck",// 输入字段名
        },
		{
            type: "hidden",// 五种形式：text,select,date,hidden,textarea,
            name: "isCarInsurance",// 输入字段名
        },
		{
            type: "text",//
            name: "carName",//
            label: "车辆名称",//
			immutableAdd:true
        },
        {
            type: "text",//
            name: "modelNumber",//
            label: "车辆型号",//
			immutableAdd:true
        },
		        {
            type: "text",//
            name: "useUnit",//
            label: "使用单位",//
			immutableAdd:true
        },
		        {
            type: "text",//
            name: "subordinateUnit",//
            label: "所属单位",//
			immutableAdd:true
        },
		        {
            type: "text",//
            name: "location",//
            label: "所在地点",//
			immutableAdd:true	
        },
		{
            type: "date",//
            name: "accountedForDate",//
            label: "入账日期",//
			dateFmt: "yyyy-MM-dd HH:mm:ss",
			id:"accountedForDate",
			immutableAdd:true
			
        },
		{
            type: "text",//
            name: "approvalUser",//
            label: "审核人",//
			immutableAdd:true
        },
		{
            type: "select",
            name: "approvalType",
            label: "办理类型",
            selectList: [// 当类型为select时，option配置
                {
                    value: 0,// 输入字段的值
                    text: "车辆年检",// 显示中文名
                    selected: false// 默认是否选中
                },
                {
                    value: 1,// 输入字段的值
                    text: "车辆保险",// 显示中文名
                    selected: false// 默认是否选中
                }
            ],
        },
		{
            type: "upload",//
            name: "imageListForPc",//
            label: "申请人图片",//
            btnName: "添加图片",
			projectName:"cj_picture",
            fileType: ["jpg", "jpeg", "png", "gif"],
        }
    ],
    onSave: function (obj, _params) {
		var isCarCheck = _params.isCarCheck;
		var isCarInsurance = _params.isCarInsurance;
		var approvalType = _params.approvalType;
		if(isCarCheck == '2' && approvalType == '0'){
			 layer.alert("车辆年检的申请已经提交并审批中", { icon: 0, }, function (index) {
                layer.close(index);
				});
		}else if(isCarInsurance == '2' && approvalType == '1'){
				layer.alert("车辆保险的申请已经提交并审批中", { icon: 0, }, function (index) {
                layer.close(index);
				});
		}else{
		l.ajax('addZjCjApplicationForApproval', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
		}

    },
    onAdd: function (obj, _params) {
	    var isCarCheck = _params.isCarCheck;
		var isCarInsurance = _params.isCarInsurance;
		var approvalType = _params.approvalType;
		if(isCarCheck == '2' && approvalType == '0'){
			 layer.alert("车辆年检的申请已经提交并审批中", { icon: 0, }, function (index) {
                layer.close(index);
				});
		}else if(isCarInsurance == '2' && approvalType == '1'){
				layer.alert("车辆保险的申请已经提交并审批中", { icon: 0, }, function (index) {
                layer.close(index);
				});
		}else{
		l.ajax('addZjCjApplicationForApproval', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
		}
    }
})
// 领导审批
var approvalLayer = $('#approvalLayer').detailLayer({
	layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",// 五种形式：text,select,date,hidden,textarea,
            name: "approvalId",// 输入字段名
        },
		{
            type: "hidden",// 五种形式：text,select,date,hidden,textarea,
            name: "applyUserId",// 输入字段名
        },
		{
            type: "hidden",// 五种形式：text,select,date,hidden,textarea,
            name: "id",// 输入字段名
        },
		{
            type: "text",//
            name: "carName",//
            label: "车辆名称",//
			immutableAdd:true
        },
        {
            type: "text",//
            name: "modelNumber",//
            label: "车辆型号",//
			immutableAdd:true
        },
		        {
            type: "text",//
            name: "useUnit",//
            label: "使用单位",//
			immutableAdd:true
        },
		{
            type: "text",//
            name: "subordinateUnit",//
            label: "所属单位",//
			immutableAdd:true
        },
		        {
            type: "text",//
            name: "location",//
            label: "所在地点",//
			immutableAdd:true
        },
		{
            type: "date",//
            name: "accountedForDate",//
            label: "入账日期",//
			dateFmt: "yyyy-MM-dd HH:mm:ss",
			id:"accountedForDate",
			immutableAdd:true
        },
	    {
            type: "select",
            name: "approvalType",
            label: "办理类型",
            selectList: [// 当类型为select时，option配置
                {
                    value: 0,// 输入字段的值
                    text: "车辆年检",// 显示中文名
                    selected: false// 默认是否选中
                },
                {
                    value: 1,// 输入字段的值
                    text: "车辆保险",// 显示中文名
                    selected: false// 默认是否选中
                }
            ],
			immutableAdd:true
        },
        {
            type: "text",//
            name: "approvalUser",//
            label: "审核人",//
			immutableAdd:true
        },
		{
            type: "upload",//
            name: "imageListForPc",//
            label: "申请人图片",//
            btnName: "添加图片",
			projectName:"cj_picture",
            fileType: ["jpg", "jpeg", "png", "gif"],
			immutableAdd:true
        },
		{
            type: "select",
            name: "approvalResult",
            label: "是否同意",
            selectList: [// 当类型为select时，option配置
			    {
                    value: "",// 输入字段的值
                    text: "请选择",// 显示中文名
                    selected: true// 默认是否选中
                },
                {
                    value: 0,// 输入字段的值
                    text: "同意",// 显示中文名
                    selected: false// 默认是否选中
                },
                {
                    value: 1,// 输入字段的值
                    text: "不同意",// 显示中文名
                    selected: false// 默认是否选中
                }
            ],
			must: true
        },
        {
            type: "textarea",//
            name: "approvalOpinion",//
            label: "审批意见",//
            placeholder: "请输入备注",
			// must: true
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjCjApplicationForApproval', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('updateZjCjApplicationForApproval', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    }
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
	var params;
    switch (name) {
        case "add":
		   l.ajax('getDetailList',params,function(res){
			   // console.log(res)
			   
			   res.isAdd=true
                setupLayer.open(res);
            })
            break;
        case "edit":
            if (checkedData.length == 1) {
                setupLayer.open(checkedData[0]);
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
				layer.confirm("确定删除？", { icon: 0,}, function (index) {
                l.delTableRow("recordid", 'currentList', 'batchDeleteUnit', checkedData, function (data) {
                    pager.page('remote')
                })// 删除url地址
				layer.close(index);
            });
            }
            break;
    }
})
 function editTableRow(rowIndex){
	var checkedData=table.row(rowIndex).data() 
	 detailLayer.open(checkedData);
$('#detailLayer .btn').hide();	 
}
function applyOpen(rowIndex) {
var checkedData=table.row(rowIndex).data() 
	 applyLayer.open(checkedData);     
	 $(".layui-layer-title").html('车检/保险申请');
}
function approvalOpen(id,flag) {
	  var params = {
	       id:id,
		   approvalType:flag
        }
    l.ajax('getApplyDetail',params, function (res) {
           approvalLayer.open(res);
      $(".layui-layer-title").html('审批');
        })
}