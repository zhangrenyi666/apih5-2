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
            "data": "sszclx1Name",//接口对应字段
            "title": "资产大类",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "sszclx2Name",//接口对应字段
            "title": "资产小类",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "zcbh",//接口对应字段
            "title": "资产编号",//表头名
            "defaultContent": "-",//默认显示
			"render":function(data,index,row){
                var a;
                if(data){   
                    a = '<a style="color:blue;" onclick="myOpen1(\' '+row.rowIndex+ '\',\'' + 'bf' + '\')" href="javascript:void(0);">'+data+'</a>'
                }
                return a;
            }
        },
        {
            "targets": [5],//第几列
            "data": "zcmc",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [6],//第几列
            "data": "sydwName",//接口对应字段
            "title": "使用单位",//表头名
            "defaultContent": "-",//默认显示
        },		
        {
            "targets": [7],//第几列
            "data": "grrq",//接口对应字段
            "title": "购入日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [8],//第几列
            "data": "zcztName",//接口对应字段
            "title": "资产状态",//表头名
            "defaultContent": "-",//默认显示
        },
        {
        	"targets": [9],//第几列
            "data": "wxztdmName",//接口对应字段
            "title": "维修状态",//表头名
            "defaultContent": "-",//默认显示
        },
        		
		{
            "targets": [10],//第几列
            "data": "glryName",//接口对应字段
            "title": "管理员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
        	 "targets": [11],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
				return (data) ? l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss") : "-";
                //return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
        	"targets": [12],//第几列
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
				html += '<li><a href="javascript:void(0);" onclick="editTableRow('+data.rowIndex+')">编辑</a></li>';
                if(data.zcztdm=="1"){//正常使用                 
                    if(data.wxztdm=="2"||data.wxztdm==null || data.wxztdm==""){//维修状态 完成
                        html += '<li><a href="javascript:void(0);" onclick="myOpen(\'资产维修新增\',\'' + data.recordid + '\',\'' + 'assetsRepairList' + '\')">资产维修</a></li>';
                    }
                }else  if(data.zcztdm=="3"){//已报废
                    html = '-';
                }else if(data.zcztdm=="4" || data.zcztdm=="5"){//闲置
                    //html = '<li><a href="javascript:void(0);" onclick="myOpen(\'资产调拨\',\'' + data.recordid + '\',\'' + 'assetsXZList' + '\')">资产恢复</a></li>';
					 html = '-';
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
            type: "select",
            name: "sszclx1",
            label: "资产大类",
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getAssetsTypeTreeAllList",//api名称
                child: "sszclx2",//如果有联动下拉，设置被他控制的下拉name
                valueName: "recordid",//设置value对应的接口字段名称
                textName: "assetTypeIdAndName",//设置text对应的接口字段名称
                childrenName: "currentList",//设置children对应的接口字段名称
            },
        },
        {
            type: "select",
            name: "sszclx2",
            label: "资产小类",
            ajax: {
                parent: "sszclx1",//如果有联动下拉，设置他被控制的下拉name
                valueName: "recordid",
                textName: "assetTypeIdAndName",
            },
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "zcbh",//输入字段名
            label: "资产编号",//输入字段对杨的中文名称
            placeholder: "请输入资产编号",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "zcmc",//输入字段名
            label: "资产名称",//输入字段对杨的中文名称
            placeholder: "请输入资产名称",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "glryid",//输入字段名
            label: "管理人员",//输入字段对杨的中文名称
            placeholder: "请输入管理人员",
        },
       {
           type: "text",//三种形式：text,select,date,
           name: "sybmid",//输入字段名
           label: "使用部门",//输入字段对杨的中文名称
           placeholder: "请输入使用部门",
       },    
		{
            type: "text",//三种形式：text,select,date,
            name: "sydwid",//输入字段名
            label: "使用单位",//输入字段对杨的中文名称
            placeholder: "请输入使用单位",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "syr",//输入字段名
            label: "使用人",//输入字段对杨的中文名称
            placeholder: "请输入使用人",
        },	
		{
            type: "select",
            name: "zcztdm",
            label: "资产状态",
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getAssetsStateSelectList",//api名称
                valueName: "dmbh",//设置value对应的接口字段名称
                textName: "dmnr",//设置text对应的接口字段名称
            },
        },
		{
            type: "date",//text,select,date,
            name: "startTime",
            label: "购入日期开始",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin"
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "购入日期结束",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            //maxDate: "\'#F{$dp.$D(\\\'"+"logmin"+"\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax"
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

var allData;
var userId = l.getUrlParam("userAccount");
var orgID = l.getUrlParam("orgID");
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getAssetsList"),
        params: {
            sszclx1: "",
            sszclx2: "",
            cfdd1: "",
            cfdd2: "",
            zcmc: "",
            sybm: "",
            syr: "",
            cwbh: "",
            zcztdm: "",
			sydw:"",
			sessionId:userId,
			departmentOrgId:orgID
        },
        success: function (result) {
            if (result.success) {			
                var data = result.data;
				//console.log('params:',data)
				allData = data;
				 if(result.data.length >0){	
				    if(result.data[0].isCompany == 0){	
					    $("#delete").hide();
				    }
				} 
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

var addDetailLayer = $('#addDetailLayer').detailLayer({
    layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "glryid",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "syr",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sybmid",//输入字段名
        },
		 {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sydwid",//输入字段名
        },

        {
            type: "select",
            name: "sszclx1",
            label: "资产大类",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsTypeTreeAllList",
                child: "sszclx2",
                valueName: "recordid",
                textName: "assetTypeIdAndName",
                childrenName: "currentList",
            },
            must: true
        },
        {
            type: "select",
            name: "sszclx2",
            label: "资产小类",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                parent: "sszclx1",
                valueName: "recordid",
                textName: "assetTypeIdAndName",
            },
            must: true
        },
        {
            type: 'text',//
            name: "zcbh",//
            label: "资产编号",//
            placeholder: "请输入资产编号"
			//must: true
            //immutableAdd:true
        },
	
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            must: true
        },
		{
            type: "text",//
            name: "zcyz",//
            label: "资产原值（元）",//
            placeholder: "请输入资产原值（元）",
            must: true
        },
			{
            type: "select",
            name: "xzl",
            label: "残值率（%）",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getCzlSelectAllList",
                valueName: "dmbh",
                textName: "dmnr"
            },
			must: true
        },
		{
            type: "hidden",//
            name: "synx",//
            label: "使用年限（年）",//
            placeholder: "请输入使用年限（年）",
			//must: true,
            max: 99
        },
		{
            type: "number",//
            name: "bxts",//
            label: "保修天数（月）",//
            placeholder: "请输入保修天数（月）",
			must: true,
            max: 9999999999
        },
        
		{
            type: "text",//
            name: "ggxh",//
            label: "规格型号",//
            placeholder: "请输入规格型号"
        },
		{
            type: "date",//
            name: "grrq",//
            label: "购入日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"grrq",
            must: true
        },
		{
            type: "select",
            name: "grfsdm",
            label: "购入方式",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getBuyMannerSelectAllList",
                valueName: "dmbh",
                textName: "dmnr"
            },
        },
		{
            type: "pickTree",
            name: "glrymc",
            label: "管理员",
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "select",
            name: "cqdwid",
            label: "产权单位",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getUnitSelectAllList",
                valueName: "dwbh",
                textName: "dwmc"
            },
            must: true
        },
		 {
            type: "pickTree",//
            name: "oaSydw",//接口字段名
            label: "使用单位",//
            //must: true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "select",//
            name: "sybmid",//接口字段名
            label: "使用部门",//
            selectList: [//当类型为select时，option配置
            ], 
            ajax: {
                api: "getDepartmentSelectAllList",
                valueName: "bmbh",
                textName: "bmmc"
            }
        },
        {
            type: "text",
            name: "cfdd1",
            label: "存放地点",
            placeholder: "请输入存放地点",
            must: true
        },		
        {
            type: "text",//
            name: "syr",//接口字段名
            label: "使用人",//
            placeholder: "请输入使用人",
        },
        {
            type: "date",//
            name: "scrq",//
            label: "出厂日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"scrq"
        },  
        {
            type: "text",//
            name: "serialNumber",//
            label: "出厂编号",//
            placeholder: "请输入出厂编号"
        },			
	    {
            type: "select",//
            name: "manufacturerId",//
            label: "生产厂家",
            placeholder: "请输入资产品牌",
			selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getManufacturerSelectAllList",
                valueName: "recordid",
                textName: "manufacturerName",
            },
        },
	    {
            type: "select",//
            name: "brandId",//
            label: "资产品牌",
            placeholder: "请输入资产品牌",
			selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsBrandSelectAllList",
                valueName: "recordid",
                textName: "brandName",
            },
        },
        {
            type: "select",
            name: "gys",
            label: "供应商",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getSupplierSelectAllList",
                valueName: "recordid",
                textName: "gysmc",
            },            
        },
        {
            type: "textarea",//
            name: "bz",//
            label: "备注",//
            placeholder: "请输入备注",
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
			projectName:"zj-assets-haiwei",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
       // var zcbh= _params.zcbh;//资产编号
        var cgbh= _params.cgbh;//采购编码
        var fpbh= _params.fpbh;//发票编号
        var zctm= _params.zctm;//资产条码
        var djph= _params.djph;//登记批号
		var zcyz = _params.zcyz;//资产原值（元）
		var zcyzGs = /^[Z0-9 \d+(\.\d+)?]{1,20}$/.test(zcyz);//资产原值格式限制
      //  var zcbhGs = /^[0-9]{1,20}$/.test(zcbh);//资产编号格式限制
        var cgbhGs = /^[a-zA-Z0-9]{0,20}$/.test(cgbh);//采购编码格式限制
        var fpbhGs = /^[a-zA-Z0-9]{0,20}$/.test(fpbh);//发票编号格式限制
        var zctmGs = /^[a-zA-Z0-9]{0,20}$/.test(zctm);//资产条码格式限制
        var djphGs = /^[a-zA-Z0-9]{0,20}$/.test(djph);//登记批号格式限制
	/* 	if(zcbhGs==false){
            layer.alert("资产编号只允许为数字最大长度为20", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else */
		if(cgbhGs==false){
            layer.alert("采购编码只允许为英文或数字最大长度为20", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else if(fpbhGs==false){
            layer.alert("发票编号只允许为英文或数字最大长度为20", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else if(zctmGs==false){
            layer.alert("资产条码只允许为英文或数字最大长度为20", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else if(djphGs==false){
            layer.alert("登记批号只允许为英文或数字最大长度为20", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else if(_params.glrymc.oaMemberList.length>1){
            layer.alert("管理员只可选一个", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else  if(zcyzGs==false){
            layer.alert("资产原值只允许数字最大长度为30", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else {
            l.ajax('updateAssets', _params, function (_params) {
                pager.page('remote');
                obj.close()
            })
        }
    },
    onAdd: function (obj, _params) {
       // var zcbh= _params.zcbh;//资产编号
        var cgbh= _params.cgbh;//采购编码
        var cwbh= _params.cwbh;//财务编号
        var fpbh= _params.fpbh;//发票编号
        var zctm= _params.zctm;//资产条码
        var djph= _params.djph;//登记批号
        var zcyz = _params.zcyz;//资产原值（元）
		var zcyzGs = /^[Z0-9 \d+(\.\d+)?]{1,20}$/.test(zcyz);//资产原值格式限制
       // var zcbhGs = /^[0-9]{1,20}$/.test(zcbh);//资产编号格式限制
        //var zcbhGs;
        var cgbhGs = /^[a-zA-Z0-9]{0,20}$/.test(cgbh);//采购编码格式限制
        var cwbhGs = /^[a-zA-Z0-9]{0,20}$/.test(cwbh);//财务编号格式限制
        var fpbhGs = /^[a-zA-Z0-9]{0,20}$/.test(fpbh);//发票编号格式限制
        var zctmGs = /^[a-zA-Z0-9]{0,20}$/.test(zctm);//资产条码格式限制
        var djphGs = /^[a-zA-Z0-9]{0,20}$/.test(djph);//登记批号格式限制
	/* 	if(zcbhGs==false){
            layer.alert("资产编号只允许为数字最大长度为20", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else  */
		if(cgbhGs==false){
            layer.alert("采购编码只允许为英文或数字最大长度为20", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else if(cwbhGs==false){
            layer.alert("财务编号只允许为英文或数字最大长度为20", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else if(fpbhGs==false){
            layer.alert("发票编号只允许为英文或数字最大长度为20", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else if(zctmGs==false){
            layer.alert("资产条码只允许为英文或数字最大长度为20", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else if(djphGs==false){
            layer.alert("登记批号只允许为英文或数字最大长度为20", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else if(_params.glrymc.oaMemberList.length>1){ //----------------改改
            layer.alert("管理员只可选一个", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else if(zcyzGs==false){
            layer.alert("资产原值只允许数字最大长度为30", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else {
            l.ajax('addAssets', _params, function (_params) {
                pager.page('remote')
                obj.close()
            })
            }
        }

})
var editDetailLayer = $('#editDetailLayer').detailLayer({
    layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "glryid",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "syr",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sybmid",//输入字段名
        },
		 {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sydwid",//输入字段名
        },

        {
            type: "select",
            name: "sszclx1",
            label: "资产大类",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsTypeTreeAllList",
                child: "sszclx2",
                valueName: "recordid",
                textName: "assetTypeIdAndName",
                childrenName: "currentList",
            },
            must: true
        },
        {
            type: "select",
            name: "sszclx2",
            label: "资产小类",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                parent: "sszclx1",
                valueName: "recordid",
                textName: "assetTypeIdAndName",
            },
            must: true
        },
        {
            type: 'text',//
            name: "zcbh",//
            label: "资产编号",//
            placeholder: "请输入资产编号",
          //  must: true,
           immutableAdd:true
        },
		
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            must: true
        },
		{
            type: "text",//
            name: "zcyz",//
            label: "资产原值（元）",//
            placeholder: "请输入资产原值（元）",
            must: true,
			immutableAdd:true
        },
			{
            type: "select",
            name: "xzl",
            label: "残值率（%）",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getCzlSelectAllList",
                valueName: "dmbh",
                textName: "dmnr"
            },
			must: true,
			immutableAdd:true
        },
		{
            type: "number",//
            name: "synx",//
            label: "使用年限（年）",//
            placeholder: "请输入使用年限（年）",
			must: true,
            max: 99
        },
		{
            type: "number",//
            name: "bxts",//
            label: "保修天数（月）",//
            placeholder: "请输入保修天数（月）",
			must: true,
            max: 9999999999,
			immutableAdd:true
        },        
		{
            type: "text",//
            name: "ggxh",//
            label: "规格型号",//
            placeholder: "请输入规格型号"
        },
		{
            type: "date",//
            name: "grrq",//
            label: "购入日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"grrq",
			//immutableAdd:true,
            must: true
			
        },
		{
            type: "select",
            name: "grfsdm",
            label: "购入方式",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getBuyMannerSelectAllList",
                valueName: "dmbh",
                textName: "dmnr"
            },
        },
        {
            type: "hidden",//
            name: "cgbh",//
            label: "采购编号",//
            placeholder: "请输入采购编号",
        },
		{
            type: "pickTree",
            name: "glrymc",
            label: "管理员",
            pickType: {
            department: false,//部门列表对应接口字段名,false表示不开启该类
            member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "select",
            name: "cqdwid",
            label: "产权单位",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getUnitSelectAllList",
                valueName: "dwbh",
                textName: "dwmc"
            },
            must: true
        },
		 {
            type: "pickTree",//
            name: "oaSydw",//接口字段名
            label: "使用单位",//
            //must: true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "select",//
            name: "sybmid",//接口字段名
            label: "使用部门",//
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getDepartmentSelectAllList",
                valueName: "bmbh",
                textName: "bmmc"
            }
        },
        {
            type: "text",
            name: "cfdd1",
            label: "存放地点",
            placeholder: "请输入存放地点",
            must: true
        },
        {
            type: "text",//
            name: "syr",//接口字段名
            label: "使用人",//
            placeholder: "请输入使用人"
        },


        {
            type: "hidden",//
            name: "zcyt",//
            label: "资产用途",//
            placeholder: "请输入资产用途",
        },
        
       	
        {
            type: "hidden",//
            name: "zctm",//
            label: "资产条码",//
            placeholder: "请输入资产条码",
        },
       
        {
            type: "hidden",//
            name: "djph",//
            label: "登记批号",//
            placeholder: "请输入登记批号",
        },
        {
            type: "date",//
            name: "scrq",//
            label: "出厂日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"scrq"
        },    
        {
            type: "text",//
            name: "serialNumber",//
            label: "出厂编号",//
            placeholder: "请输入出厂编号"
        },		
        {
            type: "hidden",//
            name: "nynx",//
            label: "耐用年限（年）",//
            placeholder: "请输入耐用年限（年）",
            max: 9999999999
        },
	    {
            type: "select",//
            name: "manufacturerId",//
            label: "生产厂家",
            placeholder: "请输入资产品牌",
			selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getManufacturerSelectAllList",
                valueName: "recordid",
                textName: "manufacturerName",
            },
        },
		{
            type: "select",//
            name: "brandId",//
            label: "资产品牌",
            placeholder: "请输入资产品牌",
			selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsBrandSelectAllList",
                valueName: "recordid",
                textName: "brandName",
            },
        },
        {
            type: "select",
            name: "gys",
            label: "供应商",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getSupplierSelectAllList",
                valueName: "recordid",
                textName: "gysmc",
            },
            
        },
        {
            type: "textarea",//
            name: "bz",//
            label: "备注",//
            placeholder: "请输入备注",
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
			projectName:"zj-assets-haiwei",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
        var zcbh= _params.zcbh;//资产编号
        var cgbh= _params.cgbh;//采购编码
        var fpbh= _params.fpbh;//发票编号
        var zctm= _params.zctm;//资产条码
        var djph= _params.djph;//登记批号
		var zcyz = _params.zcyz;//资产原值（元）
		var zcyzGs = /^[Z0-9 \d+(\.\d+)?]{1,20}$/.test(zcyz);//资产原值格式限制
        var zcbhGs = /^[0-9]{1,20}$/.test(zcbh);//资产编号格式限制
        var cgbhGs = /^[a-zA-Z0-9]{0,20}$/.test(cgbh);//采购编码格式限制
        var fpbhGs = /^[a-zA-Z0-9]{0,20}$/.test(fpbh);//发票编号格式限制
        var zctmGs = /^[a-zA-Z0-9]{0,20}$/.test(zctm);//资产条码格式限制
        var djphGs = /^[a-zA-Z0-9]{0,20}$/.test(djph);//登记批号格式限制
		 if(cgbhGs==false){
            layer.alert("采购编码只允许为英文或数字最大长度为20", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else if(fpbhGs==false){
            layer.alert("发票编号只允许为英文或数字最大长度为20", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else if(zctmGs==false){
            layer.alert("资产条码只允许为英文或数字最大长度为20", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else if(djphGs==false){
            layer.alert("登记批号只允许为英文或数字最大长度为20", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else if(_params.glrymc.oaMemberList.length>1){
            layer.alert("管理员只可选一个", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else  if(zcyzGs==false){
            layer.alert("资产原值只允许数字最大长度为30", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else {
            // console.log(_params)
            l.ajax('updateAssets', _params, function (_params) {
                pager.page('remote');
                obj.close()
            })
        }
    },
    onAdd: function (obj, _params) {
        var zcbh= _params.zcbh;//资产编号
        var cgbh= _params.cgbh;//采购编码
        var cwbh= _params.cwbh;//财务编号
        var fpbh= _params.fpbh;//发票编号
        var zctm= _params.zctm;//资产条码
        var djph= _params.djph;//登记批号
		var zcyz = _params.zcyz;//资产原值（元）
		var zcyzGs = /^[Z0-9 \d+(\.\d+)?]{1,20}$/.test(zcyz);//资产原值格式限制
        var zcbhGs = /^[0-9]{1,20}$/.test(zcbh);//资产编号格式限制
        var cgbhGs = /^[a-zA-Z0-9]{0,20}$/.test(cgbh);//采购编码格式限制
        var cwbhGs = /^[a-zA-Z0-9]{0,20}$/.test(cwbh);//财务编号格式限制
        var fpbhGs = /^[a-zA-Z0-9]{0,20}$/.test(fpbh);//发票编号格式限制
        var zctmGs = /^[a-zA-Z0-9]{0,20}$/.test(zctm);//资产条码格式限制
        var djphGs = /^[a-zA-Z0-9]{0,20}$/.test(djph);//登记批号格式限制
		 if(cgbhGs==false){
            layer.alert("采购编码只允许为英文或数字最大长度为20", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else if(cwbhGs==false){
            layer.alert("财务编号只允许为英文或数字最大长度为20", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else if(fpbhGs==false){
            layer.alert("发票编号只允许为英文或数字最大长度为20", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else if(zctmGs==false){
            layer.alert("资产条码只允许为英文或数字最大长度为20", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else if(djphGs==false){
            layer.alert("登记批号只允许为英文或数字最大长度为20", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else if(_params.glrymc.oaMemberList.length>1){ //----------------改改
            layer.alert("管理员只可选一个", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else if(zcyzGs==false){
            layer.alert("资产原值只允许数字最大长度为30", { icon: 0 }, function (index) {
                layer.close(index);
            });
        }else {
			// console.log(_params)
            l.ajax('addAssets', _params, function (_params) {
				
                pager.page('remote')
                obj.close()
            })
            }
        }

});

var detailLayer = $('#detailLayer').detailLayer({
     layerArea:['100%', '100%'],
    conditions: [
        {
            type: "select",
            name: "sszclx1",
            label: "资产大类",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsTypeTreeAllList",
                child: "sszclx2",
                valueName: "recordid",
                textName: "assetTypeIdAndName",
                childrenName: "currentList",
            },
            immutableAdd:true
        },
        {
            type: "select",
            name: "sszclx2",
            label: "资产小类",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                parent: "sszclx1",
                valueName: "recordid",
                textName: "assetTypeIdAndName",
            },
            immutableAdd:true
        },
        {
            type: 'text',//
            name: "zcbh",//
            label: "资产编号",//
            placeholder: "请输入资产编号",
            immutableAdd:true
        },
		
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutableAdd:true
        },
		{
            type: "text",//
            name: "zcyz",//
            label: "资产原值（元）",//
            placeholder: "请输入资产原值（元）",
            immutableAdd:true
        },
			{
            type: "select",
            name: "xzl",
            label: "残值率（%）",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getCzlSelectAllList",
                valueName: "dmbh",
                textName: "dmnr"
            },
			immutableAdd:true
        },
		{
            type: "number",//
            name: "synx",//
            label: "使用年限（年）",//
            placeholder: "请输入使用年限（年）",
			 immutableAdd:true
        },
		{
            type: "number",//
            name: "bxts",//
            label: "保修天数（月）",//
            placeholder: "请输入保修天数（月）",
			immutableAdd:true
        },        
		{
            type: "text",//
            name: "ggxh",//
            label: "规格型号",//
            placeholder: "请输入规格型号",
			immutableAdd:true
        },
		{
            type: "date",//
            name: "grrq",//
            label: "购入日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"grrq",
			immutableAdd:true
        },
		{
            type: "select",
            name: "grfsdm",
            label: "购入方式",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getBuyMannerSelectAllList",
                valueName: "dmbh",
                textName: "dmnr"
            },
			 immutableAdd:true
        },
        {
            type: "hidden",//
            name: "cgbh",//
            label: "采购编号",//
            placeholder: "请输入采购编号",
			 immutableAdd:true
        },
		{
            type: "pickTree",
            name: "glrymc",
            label: "管理员",
            pickType: {
            department: false,//部门列表对应接口字段名,false表示不开启该类
            member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            },
			 immutableAdd:true
        },
        {
            type: "select",
            name: "cqdwid",
            label: "产权单位",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getUnitSelectAllList",
                valueName: "dwbh",
                textName: "dwmc"
            },
             immutableAdd:true
        },
		 {
            type: "pickTree",//
            name: "oaSydw",//接口字段名
            label: "使用单位",//
             immutableAdd:true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },

        {
            type: "select",//
            name: "sybmid",//接口字段名
            label: "使用部门",//
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getDepartmentSelectAllList",
                valueName: "bmbh",
                textName: "bmmc"
            },
			 immutableAdd:true
        },
        {
            type: "text",
            name: "cfdd1",
            label: "存放地点",
            placeholder: "请输入存放地点",
             immutableAdd:true
        },
        {
            type: "text",//
            name: "syr",//接口字段名
            label: "使用人",//
            placeholder: "请输入使用人",
			 immutableAdd:true
        },
        {
            type: "date",//
            name: "scrq",//
            label: "出厂日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"scrq",
			 immutableAdd:true
        },    
        {
            type: "text",//
            name: "serialNumber",//
            label: "出厂编号",//
            placeholder: "请输入出厂编号",
			 immutableAdd:true
        },		
        {
            type: "hidden",//
            name: "nynx",//
            label: "耐用年限（年）",//
            placeholder: "请输入耐用年限（年）",
			 immutableAdd:true
        },
		{
            type: "select",//
            name: "brandId",//
            label: "资产品牌",
            placeholder: "请输入资产品牌",
			selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsBrandSelectAllList",
                valueName: "recordid",
                textName: "brandName",
            },
			 immutableAdd:true
        },
        {
            type: "select",
            name: "gys",
            label: "供应商",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getSupplierSelectAllList",
                valueName: "recordid",
                textName: "gysmc",
            },
             immutableAdd:true
        },
        {
            type: "textarea",//
            name: "bz",//
            label: "备注",//
            placeholder: "请输入备注",
			 immutableAdd:true
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
			projectName:"zj-assets-haiwei",
            fileType: ["jpg", "jpeg", "png", "gif"],
			 immutableAdd:true
        }
    ],
})

var leading = $('#leading').detailLayer({
    layerArea:['410px', '310px'],
    layerTitle:['导入'],
        conditions: [
        {
            type: "upload",
            maxLen:1,
            name: "importFileList",
            label: "导入文件",
            btnName: "选择文件",
			projectName:"zj-assets-haiwei",
            fileType: ["xls", "xlsx"],
        }
    ],
    onAdd: function (obj, _params){
        if(_params.importFileList.length==0){
            layer.alert("请选择文件", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }

        l.ajax('batchInputAssets', {"importFileList":_params.importFileList}, function (_params) {
            pager.page('remote')
            obj.close()
        })
    }
});


$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            // detailLayer.open();
            addDetailLayer.open();
            break;
        /* case "edit":
            if (checkedData.length == 1) {
                // console.log('数据是:',checkedData[0])
                editDetailLayer.open(checkedData[0]);                
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }
            break; */
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
		    layer.confirm("确定删除？", { icon: 0,}, function (index) {
           /*  l.delTableRow("recordid", 'currentList', 'batchDeleteAssets', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址 */
				l.ajax("batchDeleteAssets", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });               
            }
            break;
        case "leading"://导入
            leading.open();
            break;
		case "downExcel"://导入
             window.location.href = "http://wechat.zjyjhw.com/apihaiwei/template/zj-assets-haiwei/导入模板里面有详细字段备注.xlsx";
            break;
        case "derive"://导出
            var params={};
            params.sydwName = $('[name = "sydwid"]').val();
            params.startTime =  $('[name = "startTime"]').val();
            params.endTime =  $('[name = "endTime"]').val();
			
			//window.location.href = 'http://localhost:8080/web/' + 'exportAssetsList'

			//window.location.href = 'http://localhost:8080/web/' + 'exportAssetsList'+"?code="+code
              //console.log(params);
             layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                 l.ajax('assetsExportExcel',params,function(res){
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href=res;
                    }) 
                })  
            }); 
            break;	
    }
})

function editTableRow(rowIndex){
	var checkedData=table.row(rowIndex).data()
	 editDetailLayer.open(checkedData);         
}

function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&code="+code
    }
    layer.full(layer.open(options))
}

function myOpen1(index, type) {
	 var rowData = allData[Number(index)];
    var params;
    switch (type){
        case 'bf':
            params = {};
            params.recordid = rowData.recordid;
            // console.log('params:',params)
            l.ajax('getAssetsDetails',params,function(res){
                detailLayer.open(res);
                $('#detailLayer .btn').hide();
            })
            break;	
    }
}