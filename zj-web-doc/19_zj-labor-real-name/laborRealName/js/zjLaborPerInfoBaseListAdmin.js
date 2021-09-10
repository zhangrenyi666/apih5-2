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
            "data": "cropName",//接口对应字段
            "title": "公司名称",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [3],//第几列
            "data": "projectName",//接口对应字段
            "title": "项目名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "name",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示          
        },
        {
            "targets": [5],//第几列
            "data": "depName",//接口对应字段
            "title": "部门",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
				    case "1":
                        r = "经营部"
                        break
                    case "2":
                        r = "安全部"
                        break
                    case "3":
                        r = "工程部"
                        break
                    case "4":
                        r = "物设部"
                        break
                    case "5":
                        r = "财务部"
                        break
					case "6":
                        r = "试验部"
                        break
					case "7":
                        r = "人事部"
                        break
					case "8":
                        r = "办公室"
                        break
					case "9":
                        r = "测量组"
                        break
					case "10":
                        r = "后勤"
                        break
					case "11":
                        r = "未知"
                        break																
                }
                return r
            }
        },
        {
            "targets": [6],//第几列
            "data": "age",//接口对应字段
            "title": "年龄",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "nativePlace",//接口对应字段
            "title": "籍贯",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "education",//接口对应字段
            "title": "学历",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "1":
                        r = "高中"
                        break
                    case "2":
                        r = "本科"
                        break
                    case "3":
                        r = "硕士"
                        break
                    case "4":
                        r = "博士"
                        break
                    case "5":
                        r = "其他"
                        break
                }
                return r
            }
        },
        {
            "targets": [9],//第几列
            "data": "duty",//接口对应字段
            "title": "职务",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [10],//第几列
            "data": "professionalTitle",//接口对应字段
            "title": "职称",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "1":
                        r = "初级"
                        break
                    case "2":
                        r = "中级 "
                        break
                    case "3":
                        r = "高级 "
                        break
                    case "4":
                        r = "较高级"
                        break
                }
                return r
            }
        },
        {
            "targets": [11],//第几列
            "data": "workingYear",//接口对应字段
            "title": "工作年限",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [12],//第几列
            "data": "idCard",//接口对应字段
            "title": "身份证号",//表头名
            "defaultContent": "-",//默认显示
        },
		 {
            "targets": [13],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">'
                html += '<li><a href="javascript:void(0);" onclick="myOpen(\'添加证件\',\'' + data.recordid + '\',\'' + 'zjLaborSpecialOperationList' + '\')">添加证件</a></li>';
                html += '</ul></span>'
                return html;
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjLaborPerInfoBaseList"),
        params: {
		 perType: "1"
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
var $table = $('<table class="table table-border table-bordered radius"/>')
 var $tr = $("<tr/>")
        var $th = $("<th/>")
        var $td = $("<td/>")
        $table.append($tr.clone().append($th.clone().attr("colspan", "100%").addClass("text-c").html("管理人员信息")))
        var line0 = $tr.clone()
        var cell0_0 = $th.clone().attr("data-cellName", "name")
		var cell0_1 = $td.clone().attr("data-cellName", "name")
		var cell0_2 = $th.clone().attr("data-cellName", "projectName")
		var cell0_3 = $td.clone().attr("data-cellName", "projectName")
		var cell0_4 = $th.clone().attr("data-cellName", "sex")
		var cell0_5 = $td.clone().attr("data-cellName", "sex")
		var cell0_6 = $th.clone().attr("data-cellName", "depName")
		var cell0_7 = $td.clone().attr("data-cellName", "depName")
        line0.append(cell0_0).append(cell0_1).append(cell0_2).append(cell0_3).append(cell0_4).append(cell0_5).append(cell0_6).append(cell0_7)
        $table.append(line0)
        var line1 = $tr.clone()
        var cell1_0 = $th.clone().attr("data-cellName", "age")
        var cell1_1 = $td.clone().attr("data-cellName", "age")
        var cell1_2 = $th.clone().attr("data-cellName", "nativePlace")
        var cell1_3 = $td.clone().attr("data-cellName", "nativePlace")
        var cell1_4 = $th.clone().attr("data-cellName", "education")
        var cell1_5 = $td.clone().attr("data-cellName", "education")
        var cell1_6 = $th.clone().attr("data-cellName", "duty")
        var cell1_7 = $td.clone().attr("data-cellName", "duty")
        line1.append(cell1_0).append(cell1_1).append(cell1_2).append(cell1_3).append(cell1_4).append(cell1_5).append(cell1_6).append(cell1_7)
        $table.append(line1)
        var line2 = $tr.clone()
        var cell2_0 = $th.clone().attr("data-cellName", "professionalTitle")
        var cell2_1 = $td.clone().attr("data-cellName", "professionalTitle")
        var cell2_2 = $th.clone().attr("data-cellName", "workingYear")
        var cell2_3 = $td.clone().attr("data-cellName", "workingYear")
        var cell2_4 = $th.clone().attr("data-cellName", "safetyWorkFlag")
        var cell2_5 = $td.clone().attr("data-cellName", "safetyWorkFlag")
        var cell2_6 = $th.clone().attr("data-cellName", "safetyWorkYear")
        var cell2_7 = $td.clone().attr("data-cellName", "safetyWorkYear")
        line2.append(cell2_0).append(cell2_1).append(cell2_2).append(cell2_3).append(cell2_4).append(cell2_5).append(cell2_6).append(cell2_7)
        $table.append(line2)
        var line3 = $tr.clone()
        var cell3_0 = $th.clone().attr("data-cellName", "idCard")
		var cell3_1 = $td.clone().attr("data-cellName", "idCard")
        var cell3_2 = $th.clone().attr("data-cellName", "idCardPhotoFileList")
		var cell3_3 = $td.clone().attr("data-cellName", "idCardPhotoFileList")
		var cell3_4 = $th.clone().attr("data-cellName", "cropName")
		var cell3_5 = $td.clone().attr("data-cellName", "cropName")
        var cell3_6 = $th.clone().attr("data-cellName", "perPhotoFileList")
		var cell3_7 = $td.clone().attr("data-cellName", "perPhotoFileList")      
        line3.append(cell3_0).append(cell3_1).append(cell3_2).append(cell3_3).append(cell3_4).append(cell3_5).append(cell3_6).append(cell3_7)
        $table.append(line3)
        var line4 = $tr.clone()
		var cell4_0 = $th.clone().attr("data-cellName", "noteNumber")
		var cell4_1 = $td.clone().attr("data-cellName", "noteNumber")
        var cell4_2 = $th.clone().attr("data-cellName", "noteStartTime")
		var cell4_3 = $td.clone().attr("colspan", "2").attr("data-cellName", "noteStartTime")
		var cell4_4 = $th.clone().attr("data-cellName", "noteEndTime")
		var cell4_5 = $td.clone().attr("colspan", "2").attr("data-cellName", "noteEndTime")
        line4.append(cell4_0).append(cell4_1).append(cell4_2).append(cell4_3).append(cell4_4).append(cell4_5)
        $table.append(line4)
		var line5 = $tr.clone()
        var cell5_0 = $th.clone().attr("data-cellName", "transportNumber")		
		var cell5_1 = $td.clone().attr("data-cellName", "transportNumber")
		//var cell5_8 = $td.clone().attr("data-cellName", "transportNumber1")		
        var cell5_2 = $th.clone().attr("data-cellName", "transportStartTime")
		var cell5_3 = $td.clone().attr("data-cellName", "transportStartTime")
		var cell5_4 = $th.clone().attr("data-cellName", "transportEndTime")
		var cell5_5 = $td.clone().attr("data-cellName", "transportEndTime")
        var cell5_6 = $th.clone().attr("data-cellName", "transportFileList")
		var cell5_7 = $td.clone().attr("data-cellName", "transportFileList")   
        line5.append(cell5_0).append(cell5_1).append(cell5_2).append(cell5_3).append(cell5_4).append(cell5_5).append(cell5_6).append(cell5_7)
        $table.append(line5)
		var line6 = $tr.clone()
        var cell6_0 = $th.clone().attr("data-cellName", "constructionNumber")
		var cell6_1 = $td.clone().attr("data-cellName", "constructionNumber")
        var cell6_2 = $th.clone().attr("data-cellName", "constructionStartTime")
		var cell6_3 = $td.clone().attr("data-cellName", "constructionStartTime")
		var cell6_4 = $th.clone().attr("data-cellName", "constructionEndTime")
		var cell6_5 = $td.clone().attr("data-cellName", "constructionEndTime")
        var cell6_6 = $th.clone().attr("data-cellName", "constructionFileList")
		var cell6_7 = $td.clone().attr("data-cellName", "constructionFileList")   
        line6.append(cell6_0).append(cell6_1).append(cell6_2).append(cell6_3).append(cell6_4).append(cell6_5).append(cell6_6).append(cell6_7)
        $table.append(line6)
		var line7 = $tr.clone()
        var cell7_0 = $th.clone().attr("data-cellName", "specialOperationNumber")
		var cell7_1 = $td.clone().attr("data-cellName", "specialOperationNumber")
        var cell7_2 = $th.clone().attr("data-cellName", "specialOperationStartTime")
		var cell7_3 = $td.clone().attr("data-cellName", "specialOperationStartTime")
		var cell7_4 = $th.clone().attr("data-cellName", "specialOperationEndTime")
		var cell7_5 = $td.clone().attr("data-cellName", "specialOperationEndTime")
        var cell7_6 = $th.clone().attr("data-cellName", "specialOperationFileList")
		var cell7_7 = $td.clone().attr("data-cellName", "specialOperationFileList")   
        line7.append(cell7_0).append(cell7_1).append(cell7_2).append(cell7_3).append(cell7_4).append(cell7_5).append(cell7_6).append(cell7_7)
        $table.append(line7)
        var line8 = $tr.clone()
		var cell8_0 = $th.clone().attr("data-cellName", "rewardsAndPunish")
        var cell8_1 = $td.clone().attr("colspan", "5").attr("data-cellName", "rewardsAndPunish")
		var cell8_2 = $th.clone().attr("data-cellName", "rewardsAndPunishFileList")
        var cell8_3 = $td.clone().attr("data-cellName", "rewardsAndPunishFileList")
        line8.append(cell8_0).append(cell8_1).append(cell8_2).append(cell8_3)
        $table.append(line8)
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ['100%', '100%'],
	$formTable:$table,
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "perInfoBaseId",//输入字段名
        },
        {
            type: "hidden",//
            name: "perType",//
            label: "人员类型",//
			defaultValue:"1",
            placeholder: "请输入姓名"
        },  
        {
            type: "text",//
            name: "name",//
            label: "姓名",//
            placeholder: "请输入姓名",
            must: true
        },
        {
            type: "text",//
            name: "projectName",//
            label: "项目名称",//
            placeholder: "请输入项目名称",
            must: true
        },
        {
            type: "select",
            name: "sex",
            label: "性别",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "男",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "女",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "select",
            name: "depName",
            label: "部门",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "经营部",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "安全部",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "工程部",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 4,//输入字段的值
                    text: "物设部",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 5,//输入字段的值
                    text: "财务部",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 6,//输入字段的值
                    text: "试验部",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 7,//输入字段的值
                    text: "人事部",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 8,//输入字段的值
                    text: "办公室",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 9,//输入字段的值
                    text: "测量组",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 10,//输入字段的值
                    text: "后勤",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "number",//
            name: "age",//
            label: "年龄",//
            placeholder: "请输入年龄",
            must: true
        },
        {
            type: "text",//
            name: "nativePlace",//
            label: "籍贯",//
            placeholder: "请输入籍贯",
            must: true
        },
        {
            type: "select",
            name: "education",
            label: "学历",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "高中",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "本科",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "硕士",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 4,//输入字段的值
                    text: "博士",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 5,//输入字段的值
                    text: "其他",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "text",//
            name: "duty",//
            label: "职务",//
            placeholder: "请输入职务",
            must: true
        },
        {
            type: "select",
            name: "professionalTitle",
            label: "职称",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "初级",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "中级 ",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "高级 ",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 4,//输入字段的值
                    text: "较高级",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "number",//
            name: "workingYear",//
            label: "工作年限",//
            placeholder: "请输入工作年限",
            must: true
        },
        {
            type: "select",
            name: "safetyWorkFlag",
            label: "是否从事专职安全工作",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "是",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "否",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "number",//
            name: "safetyWorkYear",//
            label: "从事安全工作累计年数",//
            placeholder: "请输入从事安全工作累计年数",
        },
        {
            type: "text",//
            name: "idCard",//
            label: "身份证号",//
            placeholder: "请输入身份证号",
            must: true
        },
		{
            type: "upload",//
            name: "idCardPhotoFileList",//
            label: "身份证扫描件上传",//
            btnName: "添加身份证扫描件",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        },
        {
            type: "text",//
            name: "cropName",//
            label: "公司名称",//
            placeholder: "请输入公司名称",
            must: true
        },
		{
            type: "upload",//
            name: "perPhotoFileList",//
            label: "个人照片上传",//
            btnName: "添加个人照片",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        },
        {
            type: "text",//
            name: "noteNumber",//
            label: "注安师证号",//
            placeholder: "请输入注安师证号",
        },
        {
            type: "date",//
            name: "noteStartTime",//
            label: "发证日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"noteStartTime",
        },
        {
            type: "date",//
            name: "noteEndTime",//
            label: "到期日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"noteEndTime",
        },
        {
            type: "select",
            name: "transportNumber",
            label: "交通部安全证书编号",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "A",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "B ",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "C ",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
		{
            type: "text",//
            name: "transportNumber1",//
            label: "交通部安全证书编号1",//
            placeholder: "请输入交通部安全证书编号1",
        },
        {
            type: "date",//
            name: "transportStartTime",//
            label: "发证日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"transportStartTime",
        },
        {
            type: "date",//
            name: "transportEndTime",//
            label: "到期日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"transportEndTime",
        },
		{
            type: "upload",//
            name: "transportFileList",//
            label: "交通部安全证书上传",//
            btnName: "添加交通部安全证书",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        },
        {
            type: "select",
            name: "constructionNumber",
            label: "建设部安全证书编号",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "A",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "B ",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "C ",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "date",//
            name: "constructionStartTime",//
            label: "发证日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"constructionStartTime",
        },
        {
            type: "date",//
            name: "constructionEndTime",//
            label: "到期日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"constructionEndTime",
        },
		{
            type: "upload",//
            name: "constructionFileList",//
            label: "建设部安全证书上传",//
            btnName: "添加建设部安全证书",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        },
        {
            type: "select",
            name: "specialOperationNumber",
            label: "特种作业证件",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "电作作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "金属焊接、切割作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "起重机械（含电梯）作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 4,//输入字段的值
                    text: "企业内机动车辆驾驶",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 5,//输入字段的值
                    text: "登高架设作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 6,//输入字段的值
                    text: "锅炉作业（含水质化验）",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 7,//输入字段的值
                    text: "压力容器作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 8,//输入字段的值
                    text: "制冷作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 9,//输入字段的值
                    text: "爆破作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 10,//输入字段的值
                    text: "危险物品作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 11,//输入字段的值
                    text: "其它作业",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "date",//
            name: "specialOperationStartTime",//
            label: "发证日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"specialOperationStartTime",
        },
        {
            type: "date",//
            name: "specialOperationEndTime",//
            label: "到期日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"specialOperationEndTime",
        },
		{
            type: "upload",//
            name: "specialOperationFileList",//
            label: "特种作业证件上传",//
            btnName: "添加特种作业证件",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        },
        {
            type: "textarea",//
            name: "rewardsAndPunish",//
            label: "安全奖惩情况",//
            placeholder: "请输入安全奖惩情况",
        },
		{
            type: "upload",//
            name: "rewardsAndPunishFileList",//
            label: "安全奖惩情况上传",//
            btnName: "添加安全奖惩情况",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "rewardsAndPunishFileId",//输入字段名
        },
        {
            type: "select",
            name: "typeOfWork",
            label: "工种",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "普工",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "电工作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "金属焊接切割",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 4,//输入字段的值
                    text: "起重机械",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 5,//输入字段的值
                    text: "企业内机动车辆驾驶",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 6,//输入字段的值
                    text: "登高架设及高空悬挂作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 7,//输入字段的值
                    text: "制冷作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 8,//输入字段的值
                    text: "锅炉作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 9,//输入字段的值
                    text: "压力容器作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 10,//输入字段的值
                    text: "爆破作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 11,//输入字段的值
                    text: "其他",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "textarea",//
            name: "violateWarn",//
            label: "违章警示",//
            placeholder: "请输入违章警示",
        },
		{
            type: "upload",//
            name: "violateWarnFileList",//
            label: "违章警示上传",//
            btnName: "添加违章警示",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],	
    onSave: function (obj, _params) {
        l.ajax('updateZjLaborPerInfoBase', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjLaborPerInfoBase', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})






$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
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
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
				   layer.confirm("确定删除？", { icon: 0,}, function (index) {            
					   l.ajax("batchDeleteUpdateZjLaborPerInfoBase", checkedData, function () {
					   pager.page('remote')
				    })
				  layer.close(index);
               });
            }
            break;
    }
})
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id
    }
    layer.full(layer.open(options))
}
function public(voteId) {//发布
    l.ajax('sendVoteInfo', { voteId: voteId }, function (data, message) {
        layer.alert(message, { icon: 0, }, function (index) {
            layer.close(index);
            pager.page('remote')
        });
    })
}
function preview(voteId) {//预览
    window.open(l.domainName + 'initMVotePreview.do?voteId=' + voteId + '&previewFlag=1', 'newwindow', 'height=500,width=350,top=100,left=500,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no')
}
