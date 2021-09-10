var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var personRegisterId = l.getUrlParam("id") || '';
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
            "data": "startTime",//接口对应字段
            "title": "开始时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
		{
            "targets": [3],//第几列
            "data": "endTime",//接口对应字段
            "title": "结束时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [4],//第几列
            "data": "content",//接口对应字段
            "title": "内容",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        }       
    ]
});
var mainDetailLayer = $('#mainDetailLayer').detailLayer({
    layerArea:['100%', '100%'],
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
            placeholder: "请输入公民身份证号"
        },	
		{
            type: "text",//
            name: "empNo",//
            label: "员工编号",//
            placeholder: "请输入员工编号",
            must: true
        },       
		{
            type: "text",//
            name: "nation",//
            label: "民族",//
            placeholder: "请输入民族"
        },
		{
            type: "date",//
            name: "birth",//
            label: "出生日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "birth",
            must: true
        },		
		{
            type: "select",//
            name: "sex",//
            label: "性别",//
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
            id: "joinDate"
        },
		{
            type: "date",//
            name: "regularizationDate",//
            label: "转为正式党员日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "regularizationDate"
        },		
        {
            type: "text",//
            name: "nativePlace",//
            label: "籍贯",//
            placeholder: "请输入籍贯"
        },
		{
            type: "date",//
            name: "workTime",//
            label: "参加工作时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "workTime",
            must: true
        },
		{
            type: "date",//
            name: "joinSysTime",//
            label: "进入本系统时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "joinSysTime"
        },
		{
            type: "select",//
            name: "politicsStatus",//
            label: "政治面貌",//
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
            id: "joinPartyTime"
        },		
		{
            type: "text",//
            name: "householdPlace",//
            label: "户口所在地",//
            placeholder: "请输入户口所在地",
            must: true
        },
		{
            type: "text",//
            name: "depName",//
            label: "部门名称",//
            placeholder: "请输入部门名称",
            must: true
        },	
		{
            type: "text",//
            name: "postRank",//
            label: "岗位级别",//
            placeholder: "请输入岗位级别"
        },
		{
            type: "text",//
            name: "duty",//
            label: "职务",//
            placeholder: "请输入职务"
        },
		{
            type: "text",//
            name: "education",//
            label: "学历",//
            placeholder: "请输入学历"
        },
		{
            type: "text",//
            name: "degree",//
            label: "学位",//
            placeholder: "请输入学位"
        },
		{
            type: "text",//
            name: "graduateSchool",//
            label: "毕业院校",//
            placeholder: "请输入毕业院校"
        },
		{
            type: "text",//
            name: "major",//
            label: "所学专业",//
            placeholder: "请输入所学专业"
        },
		{
            type: "date",//
            name: "graduateTime",//
            label: "毕业时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "graduateTime"
        },
		{
            type: "text",//
            name: "professAndTechQualification",//
            label: "专业技术资格",//
            placeholder: "请输入专业技术资格"
        },
		{
            type: "date",//
            name: "getProfessAndTechQualification",//
            label: "取得专业技术资格时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "getProfessAndTechQualification"
        },
		{
            type: "text",//
            name: "practiceQualification",//
            label: "执业资格",//
            placeholder: "请输入执业资格"
        },
		{
            type: "text",//
            name: "skillLevel",//
            label: "工人技术等级",//
            placeholder: "请输入工人技术等级"
        },
		{
            type: "text",//
            name: "workerTechType",//
            label: "工人技术工种",//
            placeholder: "请输入工人技术工种"
        },
		{
            type: "date",//
            name: "initiationTime",//
            label: "入会时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "initiationTime"
        },
		{
            type: "text",//
            name: "height",//
            label: "身高",//
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
            placeholder: "请输入性格爱好"
        },
		{
            type: "textarea",//
            name: "houseCondition",//
            label: "住房情况",//
            placeholder: "请输入住房情况"
        },
		{
            type: "textarea",//
            name: "criteria",//
            label: "择偶条件",//
            placeholder: "请输入择偶条件"
        },
		{
            type: "text",//
            name: "address",//
            label: "家庭住址",//
            placeholder: "请输入家庭住址"
        },
		{
            type: "text",//
            name: "phone",//
            label: "联系电话",//
            placeholder: "请输入联系电话"
        },		
		{
            type: "text",//
            name: "telephone",//
            label: "固定电话",//
            placeholder: "请输入固定电话"
        }		
    ],
    onSave: function (obj, _params) {           
            if (personRegisterId) {
                _params.personRegisterId = personRegisterId
                l.ajax('updateZjSanDjPersonRegister', _params, function (data, message, success) {
                    if (success) {
                        parent.pager.page('remote');
                        layer.confirm(message + "需要添加人员信息么？", { icon: 0, }, function (index) {
                            $("#tab-system").Huitab({ index: 1 })
                            layer.close(index);
                        }, function (index) {
                            obj.close()
                            layer.close(index);
                        });
                    }
                })
            } else {			
			l.ajax('addZjSanDjPersonRegister', _params, function (data, message, success) {
               if (success) {
                        parent.pager.page('remote')
                        personRegisterId = data.personRegisterId
                        layer.confirm(message + "需要添加人员信息么？", { icon: 0, }, function (index) {
                            $("#tab-system").Huitab({ index: 1 })
                            layer.close(index);
                        }, function (index) {
                            obj.close()
                            layer.close(index);
                        });
                    }
            })			
            }
       
    }
})

mainDetailLayer.close = function () {
    parent.layer.close(parent.myOpenLayer)
}


loadPage()
function loadPage() {
    if (personRegisterId) {
        l.ajax("getZjSanDjPersonRegisterDetail", { personRegisterId: personRegisterId }, function (data, message, success) {
            if (success) {
               // var projectName = data.projectName || ""
               // $projectInput.find("input").attr("disabled", "disabled").val(projectName)
                mainDetailLayer.setOpenData(data)
            }
        })
    } else {
        mainDetailLayer.setOpenData({ memberList: { oaMemberList: [] } })
    }
}
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjSanDjPersonInfoList"),
        params: {
            personId: personRegisterId
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



var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "personId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "personInfoId",//输入字段名
        },
		{
            type: "date",//
            name: "startTime",//
            label: "开始时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "startTime"
        },
		{
            type: "date",//
            name: "endTime",//
            label: "结束时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "endTime"
        },
        {
            type: "textarea",//
            name: "content",//
            label: "内容",//
            placeholder: "请输入"
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjSanDjPersonInfo', _params, function (data) {
            pager.page('remote', { personId: personRegisterId })
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjSanDjPersonInfo', _params, function (data) {
            pager.page('remote', { personId: personRegisterId })
            obj.close()
        })
    }
})

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            if (personRegisterId) {
                detailLayer.open({ isAdd: true, personId: personRegisterId });
            } else {
                layer.alert("请先保存基本信息！", { icon: 0, }, function (index) {
                    $("#tab-system").Huitab({ index: 0 })
                    layer.close(index);
                });
            }
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
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjSanDjPersonInfo", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
        case "close":
            parent.layer.close(parent.myOpenLayer)
            break;
    }
})
$("#tab-system").Huitab({index: 0});