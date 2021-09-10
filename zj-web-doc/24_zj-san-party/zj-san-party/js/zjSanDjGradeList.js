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
            "data": "gradeTypeName",//接口对应字段
            "title": "奖励名称",//表头名
            "defaultContent": "-",//默认显示
        },        
        {
            "targets": [3],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名			
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
				if(data.gradeType =='QC10' || data.gradeType =='ZC14' || data.gradeType =='ZJ13' || data.gradeType =='YWPJ16' || data.gradeType =='ZHPJ15' || data.gradeType =='JT2' || data.gradeType =='GR1'){
				html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpen(\'查看\',\'' + data.recordId + '\',\'' + data.gradeType + '\',\'' + 'zjSanDjGradeListDetail1' + '\')">查看</a>';
				}else{
				html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpen(\'查看\',\'' + data.recordId + '\',\'' + data.gradeType + '\',\'' + 'zjSanDjGradeListDetail' + '\')">查看</a>';
				}
                
                html += '</ul></span>'
                return html;
            }
        }		
    ]
});

var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//五种形式：text,select,date,hidden,textarea,
            name: "gradeTypeName",//输入字段名
			label:"奖励名称",
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
        pager.page('remote', 0, _params)
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
        url: l.getApiUrl("getZjSanDjGradeGroupByGradeTypeList"),
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

function myOpen(title, id,gradeType, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&gradeType="+encodeURI(gradeType)+"&code="+code
    }
    layer.full(layer.open(options))
}