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
            "data": "approvalName",//接口对应字段
            "title": "编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "applyUserName",//接口对应字段
            "title": "申请人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "subordinateUnit",//接口对应字段
            "title": "所属单位",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "applyUserTel",//接口对应字段
            "title": "联系方式",//表头名
            "defaultContent": "-",//默认显示
        },
		        {
            "targets": [5],//第几列
            "data": "equipmentInvolving",//接口对应字段
            "title": "涉及机房设备",//表头名
            "defaultContent": "-",//默认显示
        },
		        {
            "targets": [6],//第几列
            "data": "entryTime",//接口对应字段
            "title": "进入时间",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
		        {
            "targets": [7],//第几列
            "data": "estimateLeaveTime",//接口对应字段
            "title": "预计离开时间",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [8],//第几列
            "data": "approvalType",//接口对应字段
            "title": "审批状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "未审批"
                        break
                    case "1":
                        r = "审批中"
                        break
	                case "2":
                        r = "通过"
                        break
					case "3":
                        r = "被驳回"
                        break
                }
                return r
            }
        },
		        {
        	"targets": [9],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">'
				if(data.flag == "1"){
					
				}else{
					html += '<li><a href="javascript:void(0);" onclick="editTableRow('+data.rowIndex+')">审批</a></li>';
				}
				
				html += '<li><a href="javascript:void(0);" onclick="myOpen('+data.rowIndex+')">详情</a></li>';

                html += '</ul></span>'
                return html;
            }
        }
    ]
});
//判断当前用户是否管理员
		  l.ajax('getLoginUser',{}, function (res) {
                console.log(res)
				if(res.isAdmin == "0"){
					 $("#approvalButton").hide();
				}
               
        })
var form = $('#form').filterfrom({
    conditions: [//表单项配置
		{
            type: "text",//三种形式：text,select,date,
            name: "subordinateUnit",//输入字段名
            label: "所属单位",//输入字段对杨的中文名称
            placeholder: "请输入使用单位",
        },
		{
            type: "select",
            name: "approvalType",
            label: "审批状态",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "未审批",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "审批中",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 2,//输入字段的值
                    text: "通过",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 3,//输入字段的值
                    text: "被驳回",//显示中文名
                    selected: false//默认是否选中
                }
            ],
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
         // console.log('搜索参数是：',_params)
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
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjJfApplicationForApprovalList"),
        params: {
        },
        success: function (result) {
            if (result.success) {
				console.log(result.data);
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
//设置审核人员
var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
		{
            type: "pickTree",
            name: "oaimDepartmentUser",
            label: "局信息化管理部",
            pickType: {
            department: false,//部门列表对应接口字段名,false表示不开启该类
            member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            },
        },
		{
            type: "pickTree",
            name: "oaimDepartmentLeaderUser",
            label: "局信息化部门领导",
            pickType: {
            department: false,//部门列表对应接口字段名,false表示不开启该类
            member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            },
        }

    ],
    onSave: function (obj, _params) {
		if(_params.oaimDepartmentUser.oaMemberList.length>1){
            layer.alert("局信息化管理部只可选一个", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else if(_params.oaimDepartmentLeaderUser.oaMemberList.length>1){
            layer.alert("局信息化部门领导只可选一个", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else{
		  l.ajax('addApprovalMember', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
		}
			

    },
    onAdd: function (obj, _params) {
    if(_params.oaimDepartmentUser.oaMemberList.length>1){
            layer.alert("局信息化管理部只可选一个", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else if(_params.oaimDepartmentLeaderUser.oaMemberList.length>1){
            layer.alert("局信息化部门领导只可选一个", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else{
		  l.ajax('addApprovalMember', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
		}
    }
})

//申请出入机房
var applyLayer = $('#applyLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        }, 
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "applyUserId",//输入字段名
        }, 
        {
            type: 'date',//
            name: "entryTime",//
            label: "进入时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
			id:"entryTime",
			must: true
            //immutableAdd:true
        },
        {
            type: 'date',//
            name: "estimateLeaveTime",//
            label: "预计离开时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
			id:"estimateLeaveTime",
			must: true
            //immutableAdd:true
        },
		{
            type: 'text',//
            name: "approvalName",//
            label: "编号",//
            //placeholder: "请输入资产编号",
			//must: true,
            immutableAdd:true
        },
//		{
//            type: "pickTree",
//            name: "oaApplyUser",
//            label: "申请人",
//			must: true,
//            pickType: {
//                department: false,//部门列表对应接口字段名,false表示不开启该类
//                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
//            }
//        },
		{
            type: 'text',//
            name: "applyUserName",//
            label: "申请人",//
            //placeholder: "请输入资产编号",
			//must: true
            immutableAdd:true
			},
		{
            type: 'text',//
            name: "applyUserTel",//
            label: "联系方式	",//
            //placeholder: "请输入资产编号",
			//must: true
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "subordinateUnit",//
            label: "所属单位",//
            //placeholder: "请输入资产编号",
			//must: true
            immutableAdd:true
        },
	    {
            type: 'text',//
            name: "purpose",//
            label: "进入机房目的",//
            //placeholder: "请输入资产编号",
			must: true
            //immutableAdd:true
        },
	    {
            type: 'text',//
            name: "equipmentInvolving",//
            label: "涉及机房内设备",//
            //placeholder: "请输入资产编号",
			must: true
            //immutableAdd:true
        },
		{
            type: "upload",//
            name: "imageListForPc",//
            label: "申请人图片",//
            btnName: "添加图片",
			projectName:"hw_picture",
            fileType: ["jpg", "jpeg", "png", "gif"]
        },
		{
            type: "pickTree",
            name: "oaUnitApprovalUser",
            label: "申请单位审批人",
			must: true,
            pickType: {
            department: false,//部门列表对应接口字段名,false表示不开启该类
            member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
			
            },
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('addZjJfApproval', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjJfApproval', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    }
})
//审批出入机房（申请单位）
var approvalLayer = $('#approvalLayer').detailLayer({
	layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "approvalId",//输入字段名
        }, 
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "flag",//输入字段名
			defaultValue:"0" 
        }, 
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "applyUserId"//输入字段名
        },
        {
            type: 'date',//
            name: "entryTime",//
            label: "进入时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
			id:"entryTime",
            immutableAdd:true
        },
        {
            type: 'date',//
            name: "estimateLeaveTime",//
            label: "预计离开时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
			id:"estimateLeaveTime",
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "approvalName",//
            label: "编号",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "applyUserName",//
            label: "申请人",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "applyUserTel",//
            label: "联系方式	",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "subordinateUnit",//
            label: "所属单位",//
            immutableAdd:true
        },
	    {
            type: 'text',//
            name: "purpose",//
            label: "进入机房目的",//
            immutableAdd:true
        },
	    {
            type: 'text',//
            name: "equipmentInvolving",//
            label: "涉及机房内设备",//
            immutableAdd:true
        },
		{
            type: "upload",//
            name: "imageList",//
            label: "申请人图片",//
            btnName: "添加图片",
			projectName:"hw_picture",
            fileType: ["jpg", "jpeg", "png", "gif"],
			immutableAdd:true
        },
		{
            type: 'text',//
            name: "applyUnitUserName",//
            label: "申请单位人名称",//
            immutableAdd:true
        },
			    {
            type: 'text',//
            name: "imDepartmentUserName",//
            label: "局信息管理人员名称",//
            immutableAdd:true
        },
	    {
            type: 'text',//
            name: "imDepartmentLeaderName",//
            label: "局信息领导名称",//
            immutableAdd:true
        },
		
		{
            type: "date",//
            name: "approvalDate",//
            label: "审批日期",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id:"approvalDate",
			immutableAdd:true
        }, 
		{
            type: "select",
            name: "approvalResult",
            label: "是否同意",
            selectList: [//当类型为select时，option配置
			    {
                    value: "",//输入字段的值
                    text: "请选择",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "同意",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "不同意",//显示中文名
                    selected: false//默认是否选中
                }				
            ],
			must: true
        },
        {
            type: "textarea",//
            name: "approvalOpinion",//
            label: "审批意见",//
            placeholder: "请输入备注",
			//must: true
        },

    ],
    onSave: function (obj, _params) {
        l.ajax('addZjJfApply', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjJfApply', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    }
})

//审批出入机房（局信息人员）
var imDepartmentLayer = $('#imDepartmentLayer').detailLayer({
	layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "approvalId",//输入字段名
        }, 
	    {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "flag",//输入字段名
			defaultValue:"1"
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "applyUserId"//输入字段名
        },
        {
            type: 'date',//
            name: "entryTime",//
            label: "进入时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
			id:"entryTime",
            immutableAdd:true
        },
        {
            type: 'date',//
            name: "estimateLeaveTime",//
            label: "预计离开时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
			id:"estimateLeaveTime",
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "approvalName",//
            label: "编号",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "applyUserName",//
            label: "申请人",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "applyUserTel",//
            label: "联系方式",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "subordinateUnit",//
            label: "所属单位",//
            immutableAdd:true
        },
	    {
            type: 'text',//
            name: "purpose",//
            label: "进入机房目的",//
            immutableAdd:true
        },
	    {
            type: 'text',//
            name: "equipmentInvolving",//
            label: "涉及机房内设备",//
            immutableAdd:true
        },
		{
            type: "upload",//
            name: "imageList",//
            label: "申请人图片",//
            btnName: "添加图片",
			projectName:"hw_picture",
            fileType: ["jpg", "jpeg", "png", "gif"],
			immutableAdd:true
        },
		{
            type: "date",//
            name: "applyUnitDate",//
            label: "申请单位人员审批日期",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            id:"applyUnitDate",
			immutableAdd:true
        }, 
		{
            type: 'text',//
            name: "applyUnitUserName",//
            label: "申请单位人名称",//
            immutableAdd:true
        },
		{
            type: "select",
            name: "applyUnitResult",
            label: "申请单位审批结果",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "同意",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "不同意",//显示中文名
                    selected: false//默认是否选中
                }
            ],
			immutableAdd:true
        },
		{
            type: "textarea",//
            name: "applyUnitOpinion",//
            label: "申请单位审批意见",//
			immutableAdd:true
        },
		{
            type: 'text',//
            name: "imDepartmentUserName",//
            label: "局信息管理人员名称",//
            immutableAdd:true
        },
	    {
            type: 'text',//
            name: "imDepartmentLeaderName",//
            label: "局信息领导名称",//
            immutableAdd:true
        },
				
		{
            type: "date",//
            name: "approvalDate",//
            label: "审批日期",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id:"approvalDate",
			immutableAdd:true
        }, 
		{
            type: "select",
            name: "approvalResult",
            label: "是否同意",
            selectList: [//当类型为select时，option配置
			    {
                    value: "",//输入字段的值
                    text: "请选择",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "同意",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "不同意",//显示中文名
                    selected: false//默认是否选中
                }
            ],
			must: true
        },
        {
            type: "textarea",//
            name: "approvalOpinion",//
            label: "审批意见",//
            placeholder: "请输入备注",
			//must: true
        },

    ],
    onSave: function (obj, _params) {
        l.ajax('addZjJfApply', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjJfApply', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    }
})
//审批出入机房（局信息领导）
var imDepartmentLeaderLayer = $('#imDepartmentLeaderLayer').detailLayer({
	layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "approvalId",//输入字段名
        }, 
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "flag",//输入字段名
			defaultValue:"2"
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "applyUserId"//输入字段名
        },
        {
            type: 'date',//
            name: "entryTime",//
            label: "进入时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
			id:"entryTime",
            immutableAdd:true
        },
        {
            type: 'date',//
            name: "estimateLeaveTime",//
            label: "预计离开时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
			id:"estimateLeaveTime",
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "approvalName",//
            label: "编号",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "applyUserName",//
            label: "申请人",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "applyUserTel",//
            label: "联系方式",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "subordinateUnit",//
            label: "所属单位",//
            immutableAdd:true
        },
	    {
            type: 'text',//
            name: "purpose",//
            label: "进入机房目的",//
            immutableAdd:true
        },
	    {
            type: 'text',//
            name: "equipmentInvolving",//
            label: "涉及机房内设备",//
            immutableAdd:true
        },
		{
            type: "upload",//
            name: "imageList",//
            label: "申请人图片",//
            btnName: "添加图片",
			projectName:"hw_picture",
            fileType: ["jpg", "jpeg", "png", "gif"],
			immutableAdd:true
        },
		
		{
            type: "date",//
            name: "applyUnitDate",//
            label: "申请单位人员审批日期",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            //defaultValue: new Date(),
            id:"applyUnitDate",
			immutableAdd:true
        }, 
			{
            type: 'text',//
            name: "applyUnitUserName",//
            label: "申请单位人名称",//
            immutableAdd:true
        },
		{
            type: "select",
            name: "applyUnitResult",
            label: "申请单位审批结果",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "同意",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "不同意",//显示中文名
                    selected: false//默认是否选中
                }
            ],
			immutableAdd:true
        },
		{
            type: "textarea",//
            name: "applyUnitOpinion",//
            label: "申请单位审批意见",//
            placeholder: "请输入备注",
			immutableAdd:true
        },
		{
            type: "date",//
            name: "imDepartmentDate",//
            label: "局信息管理人员审批日期",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            immutableAdd:true,
            id:"imDepartmentDate",
        }, 
		{
            type: 'text',//
            name: "imDepartmentUserName",//
            label: "局信息管理人员名称",//
            immutableAdd:true
        },
		{
            type: "select",
            name: "imDepartmentResult",
            label: "局信息管理人员审批结果",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "同意",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "不同意",//显示中文名
                    selected: false//默认是否选中
                }
            ],
			immutableAdd:true
        },
		{
            type: "textarea",//
            name: "imDepartmentOpinion",//
            label: "局信息管理人员审批意见",//
            placeholder: "请输入备注",
			immutableAdd:true
        },
	    {
            type: 'text',//
            name: "imDepartmentLeaderName",//
            label: "局信息领导名称",//
            immutableAdd:true
        },
		{
            type: "date",//
            name: "approvalDate",//
            label: "局信息领导审批日期",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id:"approvalDate",
			immutableAdd:true
        }, 
		{
            type: "select",
            name: "approvalResult",
            label: "是否同意",
            selectList: [//当类型为select时，option配置
			    {
                    value: "",//输入字段的值
                    text: "请选择",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "同意",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "不同意",//显示中文名
                    selected: false//默认是否选中
                }
            ],
			must: true
        },
        {
            type: "textarea",//
            name: "approvalOpinion",//
            label: "审批意见",//
            placeholder: "请输入备注",
			//must: true
        },

    ],
    onSave: function (obj, _params) {
        l.ajax('addZjJfApply', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjJfApply', _params, function (data) {
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
		        l.ajax('getJfApprovalUserList',params, function (res) {
                detailLayer.open(res);
                $(".layui-layer-title").html('设置');
        })
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0]);
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
        case "apply":
		l.ajax('getApplyUserInfo',params, function (res) {
                applyLayer.open(res);
        })
            break;
	
			case "derive"://导出
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }else if (checkedData.length == 1) {
                layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                    l.ajax('applyExportExcel',checkedData[0],function(res){
                        layer.alert("导出成功！", { icon: 0 }, function (index) {
                            layer.close(index);
                            // console.log(res)
                            window.location.href=res;
                        })
                    })
                });

            }else{
                layer.alert("只能选择一个", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }
            break;
				
			
    }
})

//详情查看
var approvalDetailLayer = $('#approvalDetailLayer').detailLayer({
	layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "approvalId",//输入字段名
        }, 
		{
            type: "select",
            name: "approvalType",
            label: "审批状态",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "未审批",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "审批中",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 2,//输入字段的值
                    text: "通过",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 3,//输入字段的值
                    text: "被驳回",//显示中文名
                    selected: false//默认是否选中
                }
            ],
			immutableAdd:true
        },
        {
            type: 'date',//
            name: "entryTime",//
            label: "进入时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            //defaultValue: new Date(),
			id:"entryTime",
            immutableAdd:true
        },
        {
            type: 'date',//
            name: "estimateLeaveTime",//
            label: "预计离开时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            //defaultValue: new Date(),
			id:"estimateLeaveTime",
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "approvalName",//
            label: "编号",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "applyUserName",//
            label: "申请人",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "applyUserTel",//
            label: "联系方式	",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "subordinateUnit",//
            label: "所属单位",//
            immutableAdd:true
        },
	    {
            type: 'text',//
            name: "purpose",//
            label: "进入机房目的",//
            immutableAdd:true
        },
	    {
            type: 'text',//
            name: "equipmentInvolving",//
            label: "涉及机房内设备",//
            immutableAdd:true
        },
		{
            type: "upload",//
            name: "imageList",//
            label: "申请人图片",//
            btnName: "添加图片",
			projectName:"hw_picture",
            fileType: ["jpg", "jpeg", "png", "gif"],
			immutableAdd:true
        },
			{
            type: 'text',//
            name: "applyUnitUserName",//
            label: "申请单位人名称",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "applyUnitDateS",//
            label: "申请单位人审批日期",//
			placeholder: "未填写",
            immutableAdd:true
        },
		//{
        //    type: "date",//
        //    name: "applyUnitDateS",//
        //    label: "申请单位人审批日期",//
        //    dateFmt: "yyyy-MM-dd HH:mm:ss",
        //    immutableAdd:true,
        //    id:"applyUnitDate",
		//	defaultvalue:"",
        //}, 
		{
            type: "select",
            name: "applyUnitResult",
            label: "申请单位审批结果",
            selectList: [//当类型为select时，option配置
				{
                    value: "",//输入字段的值
                    text: "未选择",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "同意",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "不同意",//显示中文名
                    selected: false//默认是否选中
                }
            ],
			immutableAdd:true
        },
		{
            type: "textarea",//
            name: "applyUnitOpinion",//
            label: "申请单位审批意见",//
            placeholder: "未填写",
			immutableAdd:true
        },
		//{
        //    type: "date",//
        //    name: "imDepartmentDate",//
        //    label: "局信息管理人审批日期",//
        //    dateFmt: "yyyy-MM-dd HH:mm:ss",
        //    immutableAdd:true,
        //    id:"imDepartmentDate"
        //}, 
		{
            type: 'text',//
            name: "imDepartmentUserName",//
            label: "局信息管理人员名称",//
            immutableAdd:true
        },
		{
            type: 'text',//
            name: "imDepartmentDateS",//
            label: "局信息管理人审批日期",//
			placeholder: "未填写",
            immutableAdd:true
        },
		{
            type: "select",
            name: "imDepartmentResult",
            label: "局信息管理人员审批结果",
            selectList: [//当类型为select时，option配置
				{
                    value: "",//输入字段的值
                    text: "未选择",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "同意",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "不同意",//显示中文名
                    selected: false//默认是否选中
                }
            ],
			immutableAdd:true
        },
		{
            type: "textarea",//
            name: "imDepartmentOpinion",//
            label: "局信息管理人员审批意见",//
            placeholder: "未填写",
			immutableAdd:true
        },

		//{
        //    type: "date",//
        //    name: "imDepartmentLeaderDate",//
        //    label: "局信息领导审批日期",//
        //    dateFmt: "yyyy-MM-dd HH:mm:ss",
        //    immutableAdd:true,
        //    id:"imDepartmentLeaderDate"
        //}, 
	    {
            type: 'text',//
            name: "imDepartmentLeaderName",//
            label: "局信息领导名称",//
            immutableAdd:true
        },
		{
            type: "text",//
            name: "imDepartmentLeaderDateS",//
            label: "局信息领导审批日期",//
            placeholder: "未填写",
			immutableAdd:true
        },
		{
            type: "select",
            name: "imDepartmentLeaderResult",
            label: "局信息领导审批结果",
            selectList: [//当类型为select时，option配置
			    {
                    value: "",//输入字段的值
                    text: "未选择",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "同意",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "不同意",//显示中文名
                    selected: false//默认是否选中
                }
            ],
			immutableAdd:true
        },
        {
            type: "textarea",//
            name: "imDepartmentLeaderOpinion",//
            label: "审批意见",//
            placeholder: "未填写",
			immutableAdd:true
        },


    ],
})
function editTableRow(rowIndex){
	var checkedData=table.row(rowIndex).data()
	//console.log(checkedData);
	l.ajax('getJfApprovalDetailForPC',checkedData, function (res) {
		//console.log(res);
		if(res.applyUnitFlag == 1){
			approvalLayer.open(res);  
			$(".layui-layer-title").html('申请单位审批');
		}else
        if(res.imDepartmentFlag == 1){
			imDepartmentLayer.open(res);  
			$(".layui-layer-title").html('局信息审批');
		}else 
		if(res.imDepartmentLeaderFlag == 1){
			imDepartmentLeaderLayer.open(res);  
			$(".layui-layer-title").html('局领导审批');
		} 
        })
	       
}
function myOpen(rowIndex) {
   var checkedData=table.row(rowIndex).data()
	//console.log(checkedData);
	l.ajax('getJfApprovalDetailForPC',checkedData, function (res) {
		//console.log(res);
		if(res.applyUnitFlag == 1){
			approvalDetailLayer.open(res);  
			$('#approvalDetailLayer .btn').hide();
		}else
        if(res.imDepartmentFlag == 1){
			approvalDetailLayer.open(res);
            $('#approvalDetailLayer .btn').hide();			
			
		}else 
		if(res.imDepartmentLeaderFlag == 1){
			approvalDetailLayer.open(res);  
			$('#approvalDetailLayer .btn').hide();
			
		}else{
			approvalDetailLayer.open(res);  
			$('#approvalDetailLayer .btn').hide();
		}
        })
}