var _groupName = l.getUrlParam("groupName");
var _groupId = l.getUrlParam("groupId");
var _userId = l.getUrlParam("userId");
var _zt = l.getUrlParam("zt");
var _delFlag = l.getUrlParam("delFlag");


var wxx = {
    recordidArr: [],
    selectedRecordid: {},
    groupName: _groupName,
    groupId: _groupId,
    userId: _userId,
    zt: _zt,
    delFlag: _delFlag,
    isLeader: '1',//是否领导 0是 1不是
    isFinanceStaff: '1'//是否财务人员 0是 1不是
}

var userData = {
    rows: [{
        name: 'oaGly',
        label: '选择用户'
    }]
}
myPickTree.initDataWidthName('oaGly');//初始化

var table2 = $('#table2').DataTable({
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
            "data": "assetsTypeBigName",//接口对应字段
            "title": "资产大类名称",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "assetsTypeSmallName",//接口对应字段
            "title": "资产小类名称",//表头名
            "defaultContent": "-"//默认显示
        }
    ]
});


var pager2 = $("#pager2").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getPermissionAssetsList"),
        params: {
            groupId: wxx.groupId,
            groupName: wxx.groupName
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table2.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }
        }
    }
});

$('.assetSelectS').change(function () {//联动下拉
    wxx.selectedRecordid.sszclx2 = $(this).val();
})

l.ajax('getAssetsTypeTreeAllList', {}, function (data, message, success) { //下拉菜单 联动
    if (success) {
        var select = {//大类下拉配置
            el: '.assetSelectB',
            data: data,
            key: {
                label: "assetTypeIdAndName",
                value: "recordid",
                children: 'currentList',
                chilrenName: 'currentList'
            }
        }
        var select2 = {//小类下拉配置
            el: '.assetSelectS',
            data: '',
            key: {
                label: "assetTypeIdAndName",
                value: "recordid"
            }
        }
        createSelectDom(select, function (currentList) {
            $(select.el).change(function () {
                var recordid = $(this).val();
                wxx.selectedRecordid.sszclx1 = recordid;
                $.each(wxx.recordidArr, function (i, v) {
                    if (v.recordid == recordid) {//创建子下拉
                        select2.data = v[select.key.chilrenName];
                        createSelectDom(select2, function () { });
                    }
                })
            })
        })
    }
})

//渲染用户
l.ajax('getOaGlyList', { userId: wxx.userId }, function (data, message, success) {
    if (success) {
		//console.log(data);
    wxx.isLeader = data.isLeader,//是否领导 0是 1不是
    wxx.isFinanceStaff = data.isFinanceStaff//是否财务人员 0是 1不是
	
    $("#isLD .switchImg").attr("data-id",wxx.isLeader);
    $("#isCW .switchImg").attr("data-id",wxx.isFinanceStaff);
        if (data.oaGly) { 
            if (data.oaGly.oaMemberList) {
                wxx.isAdd = true;
            }
            data.oaGly.member = data.oaGly.oaMemberList;
            delete data.oaGly.oaMemberList;
            delete data.oaGly.oaDepartmentList;
            myPickTree.drawCurData("oaGly", data.oaGly, true);
			if(data.isLeader == 0){
				
			 $("#isLD .switchImg").attr("src", "./img/no.png");
			}
			if(data.isFinanceStaff == 0){
		     $("#isCW .switchImg").attr("src", "./img/no.png");
			}
            // setSelect();
        } else {
            // setSelect();
        }
    }
})

//创建下拉
function createSelectDom(select, cb) {
    $(select.el).html('');
    if (select.data) {
        $.each(select.data, function (i, v) {
            var obj = {
                recordid: v[select.key.value],
                currentList: v[select.key.chilrenName]
            }
            wxx.recordidArr.push(obj);
            var currentList = v[select.key.chilrenName];
            var option = $('<option value="' + v[select.key.value] + '">' + v[select.key.label] + '</option>');
            $(select.el).append(option);
        })
        cb();
    }
}


function oAdd() {
    if (!wxx.isAdd) {
        layer.alert("请选择用户");
        return;
    }
    if (wxx.selectedRecordid.sszclx1 == '' || wxx.selectedRecordid.sszclx1 == undefined) {
        layer.alert("请选择资产大类");
        return;
    } else {
        var params = {
            groupId: wxx.groupId,
            groupName: wxx.groupName,
            userId: wxx.userId,
            zt: wxx.zt,
            delFlag: wxx.delFlag,
            assetsTypeBig: wxx.selectedRecordid.sszclx1,
            assetsTypeSmall: wxx.selectedRecordid.sszclx2
        }
        wxx.selectedRecordid.sszclx1 = '';
        wxx.selectedRecordid.sszclx2 = '';
        $('.assetSelectB').val('');
        $('.assetSelectS').val('');
        $('.assetSelectS').html('');

        l.ajax('addPermissionAssets', params, function (data, message, success) {
			console.log(message)
            if (success) {
                if (data.msg) {
                    layer.open({//确定后继续发送ajax 添加数据
                        type: '0',
                        btn: ["确定", "取消"],
                        content: data.msg,
                        yes: function (index) {
                            params.chooseFlag = '1';
                            l.ajax('addPermissionAssets', params, function (res) {
                                pager2.page('remote');
                            })
                            layer.close(index);
                        }
                    })
                } else {
                    pager2.page('remote');
                }
            }
        })
    }
}

function delfn() {
    var checkedData = l.getTableCheckedData(table2);
    if (checkedData.length == 0) {
        layer.alert("未选择任何项", { icon: 0 }, function (index) {
            layer.close(index);
        });
    } else {
        layer.confirm("确定删除？", { icon: 0 }, function (index) {
          /*   l.delTableRow("recordid", 'currentList', 'batchDeletePermissionAssets', checkedData, function (data) {
                pager2.page('remote')
            })//  删除url地址 */
				l.ajax("batchDeletePermissionAssets", checkedData, function () {
				   pager2.page('remote')
				})
            layer.close(index);
        });
    }
}



function oUserSave() {
    userSave(function (params) {
        var _params = {
            userId: wxx.userId,
            oaGly: params.oaGly,
            groupId: wxx.groupId,
            isLeader: wxx.isLeader,
            isFinanceStaff: wxx.isFinanceStaff
        }

        l.ajax('addOaGly', _params, function (data, message, success) {
            if (success) {
                if (data.oaGly.oaMemberList.length > 0) {
                    wxx.isAdd = true;
                } else {
                    wxx.isAdd = false;
                }
                myPickTree.drawCurData("oaGly", params.oaGly, true)
            }
        })
    })
}



$("body").on("click", "[data-id=pickTreeAdd]", function () {
    var pickType = { member: "oaMemberList" }
    myPickTree.open($(this).attr("data-name"), pickType)
})


// function pickTreeAddfn() {//加号被点击
//     var pickType = {}
//     pickType = {
//         department: false,//部门列表对应接口字段名,false表示不开启该类
//         member: "oaMemberList"//成员列表对应接口字段名,false表示不开启该类
//     };
//     myPickTree.open("oaGly", pickType);

// }



function userSave(cb) {
    var _params = {};
    $.each(userData.rows, function (i, v) {
        _params[v.name] = {
            oaMemberList: myPickTree.getDataByName(v.name).member
        };
    })
    cb(_params);
}

myPickTree.hasChange = false;
myPickTree.addUpdateCallback(function (name) {
    myPickTree.hasChange = true
})


//是否领导 是否财务人员  代码
$(".switchImg").on('click', function () {
    var id = $(this).attr("data-id");
    var _this = this;
    if (id === "0") {
        $(this).attr("data-id", "1");
        $(this).attr("src", "./img/off.png");
    } else {
        $(this).attr("data-id", "0");
        $(this).attr("src", "./img/no.png");
    }

    var isLeader = $("#isLD .switchImg").attr("data-id");
    var isFinanceStaff = $("#isCW .switchImg").attr("data-id");

    if (isLeader == "0" && isFinanceStaff == "0") {
        // console.log(this);

        $(this).attr("data-id", "1");
        $(this).attr("src", "./img/off.png");

        // $("#isCW .switchImg").attr("data-id", "1");
        // $("#isCW .switchImg").attr("src", "./img/off.png");

        // $("#isLD .switchImg").attr("data-id", "1");
        // $("#isLD .switchImg").attr("src", "./img/off.png");
        layer.alert("只能选择一项", { icon: 0 }, function (index) {
            layer.close(index);
        });
    }

    var params = {
        isLeader: $("#isLD .switchImg").attr("data-id"),
        isFinanceStaff: $("#isCW .switchImg").attr("data-id")
    }

    wxx.isLeader = params.isLeader;
    wxx.isFinanceStaff = params.isFinanceStaff;
})


