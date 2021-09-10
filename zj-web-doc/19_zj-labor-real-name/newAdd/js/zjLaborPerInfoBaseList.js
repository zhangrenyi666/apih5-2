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
            "data": "perType",//接口对应字段
            "title": "人员类型",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "name",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "projectName",//接口对应字段
            "title": "项目名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "sex",//接口对应字段
            "title": "性别",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "1":
                        r = "男"
                        break
                    case "2":
                        r = "女"
                        break
                }
                return r
            }
        },
        {
            "targets": [6],//第几列
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
                }
                return r
            }
        },
        {
            "targets": [7],//第几列
            "data": "age",//接口对应字段
            "title": "年龄",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "nativePlace",//接口对应字段
            "title": "籍贯",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
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
            "targets": [10],//第几列
            "data": "duty",//接口对应字段
            "title": "职务",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [11],//第几列
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
            "targets": [12],//第几列
            "data": "workingYear",//接口对应字段
            "title": "工作年限",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [13],//第几列
            "data": "safetyWorkFlag",//接口对应字段
            "title": "是否从事专职安全工作",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "1":
                        r = "是"
                        break
                    case "2":
                        r = "否"
                        break
                }
                return r
            }
        },
        {
            "targets": [14],//第几列
            "data": "safetyWorkYear",//接口对应字段
            "title": "从事安全工作累计年数",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [15],//第几列
            "data": "idCard",//接口对应字段
            "title": "身份证号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [16],//第几列
            "data": "cropName",//接口对应字段
            "title": "公司名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [17],//第几列
            "data": "noteNumber",//接口对应字段
            "title": "注安师证号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [18],//第几列
            "data": "noteStartTime",//接口对应字段
            "title": "发证日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [19],//第几列
            "data": "noteEndTime",//接口对应字段
            "title": "到期日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [20],//第几列
            "data": "transportNumber",//接口对应字段
            "title": "交通部安全证书编号",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "1":
                        r = "A"
                        break
                    case "2":
                        r = "B "
                        break
                    case "3":
                        r = "C "
                        break
                }
                return r
            }
        },
        {
            "targets": [21],//第几列
            "data": "transportStartTime",//接口对应字段
            "title": "发证日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [22],//第几列
            "data": "transportEndTime",//接口对应字段
            "title": "到期日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [23],//第几列
            "data": "constructionNumber",//接口对应字段
            "title": "建设部安全证书编号",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "1":
                        r = "A"
                        break
                    case "2":
                        r = "B "
                        break
                    case "3":
                        r = "C "
                        break
                }
                return r
            }
        },
        {
            "targets": [24],//第几列
            "data": "constructionStartTime",//接口对应字段
            "title": "发证日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [25],//第几列
            "data": "constructionEndTime",//接口对应字段
            "title": "到期日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [26],//第几列
            "data": "specialOperationNumber",//接口对应字段
            "title": "特种作业证件",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "1":
                        r = "电作作业"
                        break
                    case "2":
                        r = "金属焊接、切割作业"
                        break
                    case "3":
                        r = "起重机械（含电梯）作业"
                        break
                    case "4":
                        r = "企业内机动车辆驾驶"
                        break
                    case "5":
                        r = "登高架设作业"
                        break
                    case "6":
                        r = "锅炉作业（含水质化验）"
                        break
                    case "7":
                        r = "压力容器作业"
                        break
                    case "8":
                        r = "制冷作业"
                        break
                    case "9":
                        r = "爆破作业"
                        break
                    case "10":
                        r = "危险物品作业"
                        break
                    case "11":
                        r = "其它作业"
                        break
                }
                return r
            }
        },
        {
            "targets": [27],//第几列
            "data": "specialOperationStartTime",//接口对应字段
            "title": "发证日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [28],//第几列
            "data": "specialOperationEndTime",//接口对应字段
            "title": "到期日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [29],//第几列
            "data": "rewardsAndPunish",//接口对应字段
            "title": "安全奖惩情况",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [30],//第几列
            "data": "typeOfWork",//接口对应字段
            "title": "工种",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "1":
                        r = "普工"
                        break
                    case "2":
                        r = "电工作业"
                        break
                    case "3":
                        r = "金属焊接切割"
                        break
                    case "4":
                        r = "起重机械"
                        break
                    case "5":
                        r = "企业内机动车辆驾驶"
                        break
                    case "6":
                        r = "登高架设及高空悬挂作业"
                        break
                    case "7":
                        r = "制冷作业"
                        break
                    case "8":
                        r = "锅炉作业"
                        break
                    case "9":
                        r = "压力容器作业"
                        break
                    case "10":
                        r = "爆破作业"
                        break
                    case "11":
                        r = "其他"
                        break
                }
                return r
            }
        },
        {
            "targets": [31],//第几列
            "data": "violateWarn",//接口对应字段
            "title": "违章警示",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjLaborPerInfoBaseList"),
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
var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "perInfoBaseId",//输入字段名
        },
        {
            type: "text",//
            name: "perType",//
            label: "人员类型",//
            placeholder: "请输入人员类型",
            must: true
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
            type: "text",//
            name: "cropName",//
            label: "公司名称",//
            placeholder: "请输入公司名称",
            must: true
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
            type: "textarea",//
            name: "rewardsAndPunish",//
            label: "安全奖惩情况",//
            placeholder: "请输入安全奖惩情况",
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
                l.delTableRow("violateWarnFileId", 'sxAqJsonNodeList', 'batchDeleteZjLaborPerInfoBase', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址
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
