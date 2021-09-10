
new WOW().init();
var inventoryTitle = 'first';//判断盘点标题是否一样

//编辑页 配置
var editDetailData = [
    {
        type: "text",
        name: "inventoryTitle",
        label: "盘点标题",
        must: true
    },
    {
        type: "date",//
        name: "pdrq",//
        label: "盘点日期",//
        dateFmt: "yyyy-MM-dd",
        defaultValue: new Date(),
        id: "pdrq",
        immutable: true
    },
    {
        type: "select",
        name: "zcztdmafter",
        label: "资产状态(后)",
        selectList: [//当类型为select时，option配置
            // {
            //     valueName: "1111",
            //     textName: "资产状态1111"
            // },
            // {
            //     valueName: "222",
            //     textName: "资产状态222"
            // }
        ],
        ajax: {
            api: "getAssetsStateSelectList",
            valueName: "dmbh",
            textName: "dmnr"
        },
        must: true
    }, {
        type: "text",
        name: "cfddidafter",
        label: "存放地点(后)",
        placeholder: "请输入存放地点(后)",
        must: true
    }, {
        type: "select",//
        name: "sybmid",//
        label: "使用部门(后)",//
        placeholder: "请输入使用部门(后)",
        selectList: [//当类型为select时，option配置
        ],
        ajax: {
            api: "getDepartmentSelectAllList",
            valueName: "bmbh",
            textName: "bmmc"
        },
        must: true
    }, {
        type: "text",//
        name: "syrafter",//接口字段名
        label: "使用人",//
        placeholder: "请输入使用人",
    }, {
        type: "select",
        name: "pdjgdm",
        label: "盘点结果",
        selectList: [//当类型为select时，option配置
        ],
        ajax: {
            api: "getInventorySelectAllList",
            valueName: "dmbh",
            textName: "dmnr"
        },
        must: true
    }, {
        type: "textarea",//
        name: "clfa",//
        label: "处理方案",//
        placeholder: "请输入处理方案"
    }, {
        type: "textarea",//
        name: "remarks",//
        label: "备注",//
        placeholder: "请输入备注"
    },
    {
        type: "upload",//
        name: "imageList",//
        label: "资产图片",//
        btnName: "添加图片",
        fileType: ["jpg", "jpeg", "png", "gif"]
    }
]
var totalData = [];//默认数据



window.login(function () {


    var code = getUrl('token')
    // setCookie('code', code, 30)
    //设置cookie  
    function setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toUTCString();
        document.cookie = cname + "=" + cvalue + "; " + expires;
    }
    //获取cookie  
    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1);
            if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
        }
        return "";
    }
    function getUrl(k) {//获取地址栏参数，k为键名
        var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(m);
        if (r != null) return decodeURI(r[2]); return null;
    };



    //数据请求
    getAssetsList({
        pageSize: 99999
    });


    function getAssetsList(params) {
        l.ajax("getInventoryList", params, function (res) {
            totalData = res;

            $.each(totalData, function (i, v) {
                var zcztName = '';
                switch (v.zcztdm) {
                    case '1':
                        zcztName = '正常使用';
                        break;
                    case '2':
                        zcztName = '未验收';
                        break;
                    case '3':
                        zcztName = '不合格';
                        break;
                    case '4':
                        zcztName = '已丢失';
                        break;
                    case '5':
                        zcztName = '已报废';
                        break;
                }


                var $li = '';
                $li += '<a  class="DataList_a animated fadeIn ">';
                $li += '<li class="DataList_li" style="position:relative;;">';//模板li开始
                $li += '<div class=""DataList_li_con>';
                $li += '<div class="DataList_li_con_top">';
                $li += '<span class="zcbh">';
                $li += v.zcbh;
                $li += '</span>';
                $li += '<span class="accest_state">';
                $li += zcztName + ' / ' + (v.pdztName || '');
                $li += '</span>';
                $li += '</div>';
                $li += '<div class="DataList_li_con_con">';
                $li += '<h4 class="zcmc">';
                $li += v.zcmc;
                $li += '</h4>';
                $li += '<p class="zc-class">';
                $li += '<span class="big_class">';
                $li += v.sszclx1Name || '-';
                $li += '</span>';
                $li += '/';
                $li += '<span class="smart_class">';
                $li += v.sszclx2Name || '-';
                $li += '</span>';

                $li += '</p>'
                $li += '<div class="cfdd">';
                $li += v.cfdd1;
                $li += '</div>';

                $li += '</div>';
                $li += '</div>';
                $li += '</li>';//模板li over
                $li += '</a>';

                setTimeout(function () {//加载时动画效果
                    $(".DataList_ul").append($li);
                    var $inpSpan = $('<span class="checkbox-span"></span>');
                    var $inp = $('<input />', {
                        'class': 'checkbox',
                        'type': 'checkbox',
                        'sszcid': v.sszcid,
                        'recordid': v.recordid,
                        change: function () { //判断是否能被盘点  盘点日期 和 标题
                            if ($(this)[0].checked) {
                                var thisInventoryTitle = v.inventoryTitle;
                                if (inventoryTitle == 'first') {//首次
                                    inventoryTitle = thisInventoryTitle
                                } else {
                                    if (inventoryTitle != thisInventoryTitle) {
                                        layer.open({
                                            content: "<h2>该资产标题和和其他已选择资产标题不一致！</h2>",
                                            btn: ["确定"]
                                        });
                                        $(this)[0].checked = false;
                                    } else {
                                        inventoryTitle = thisInventoryTitle
                                    }
                                }

                                if (!v.pdrq) {
                                    return;
                                } else if (!(v.inventoryTitle == inventoryTitle)) {
                                    return;
                                    $(this)[0].checked = false;
                                } else {
                                    layer.open({
                                        content: "<h2>该资产已被盘点过！</h2>",
                                        btn: ["确定"]
                                    });
                                    $(this)[0].checked = false;
                                    var checkList = $('.checkbox:checked');
                                    if (checkList.length == 0) {
                                        inventoryTitle = 'first';
                                    }
                                }
                            } else {//取消选择的时候
                                var checkList = $('.checkbox:checked');
                                if (checkList.length == 0) {
                                    inventoryTitle = 'first';
                                }
                            }

                        }
                    }).appendTo($inpSpan)
                    $(".DataList_li").eq(i).append($inpSpan);

                    $(".DataList_li:odd").css({
                        "border-left": ".05rem solid rgb(255, 67, 0)",
                        "box-sizing": "border-box"
                    })
                    $(".DataList_li:even").css({
                        "border-left": ".05rem solid  green",
                        "box-sizing": "border-box"
                    })
                }, 0)
            })
        })
    }


    $('.btn span').click(function () {
        var type = $(this).attr('class');
        switch (type) {
            case 'close':
                window.location.href = './inventoryList.html'
                break;
            case 'inventory':
                var checkList = $('.checkbox:checked');
                if (checkList.length < 1) {
                    layer.open({
                        content: "<h2>请选择需要盘点的资产！</h2>",
                        btn: ["确定"]
                    });
                    return;
                }
                var checkListData = wxx.getSelectData(totalData, checkList, 'sszcid');//获取选择的数据  设置默认数据
                var isOk = true;
                $.each(checkListData, function (i, v) {
                    if (v.pdrq) {
                        layer.open({
                            content: '<h2><b>' + v.zcmc + ' </b>已盘点过，不能重复盘点！</h2>',
                            btn: ["确定"]
                        });
                        isOk = false;
                    }
                    if (v.inventoryTitle) {
                        var thisInventoryTitle = v.inventoryTitle;
                        if (inventoryTitle == 'first') {//首次
                            inventoryTitle = thisInventoryTitle
                        } else {
                            if (inventoryTitle != thisInventoryTitle) {
                                layer.open({
                                    content: "<h2>该资产标题和和其他已选择资产标题不一致！</h2>",
                                    btn: ["确定"]
                                });
                                isOk = false;
                            } else {
                                inventoryTitle = thisInventoryTitle
                            }
                        }
                    }
                })

                var immutableList = [];//禁止编辑的列表
                if (inventoryTitle) {
                    immutableList.push("inventoryTitle")
                }

                if (isOk) {
                    var editLayer = wxx.createFrom(editDetailData, checkListData, immutableList, function (_params) {//弹窗并 点击保存 并 获取数据
                        var params = _params;
                        var imgList = wxx.getImgList();//图片列表
                        var sszcidList = [];//选择资产的列表
                        var recordidList = [];//
                        $.each(checkList, function (i, v) { //获取选择的资产
                            sszcidList.push($(v).attr('sszcid'));
                            recordidList.push($(v).attr('recordid'));
                        })
                        params.sszcList = sszcidList.join();
                        params.recordList = recordidList.join();
                        params.imageList = imgList;
                        params.pdrq = new Date().getTime();

                        var batchAddInventory = 'batchAddZjInventory';//批量盘点接口
                        if (inventoryTitle) {//if 有盘点标题
                            batchAddInventory = 'batchAddZjInventoryHaveTitle';//批量盘点有标题接口
                        }

                        // console.log(batchAddZjInventory, params)
                        wxx.ajax(batchAddInventory, params, function (res) {
                            window.location.href = './assetsManageList.html'
                        })
                    })
                }
                break;
        }
        // 遍历图片列表
        $('.uploadList').on('click', 'li', function () {
            imgLayer($(this).attr('data-href'))
        })
    })


    //图片上传
    $("body").off('change', '#uploadBtn').on('change', '#uploadBtn', function () {
        wxx.upload();
    })
    function clearItem(dom) {
        dom.parent().remove();//删除可见范围中选中项
    }

    //点击搜索
    $('.search-btn').click(function () {
        var searchText = $('.search-input').val();
        var params = {
            pageSize: 99999,
            inventoryTitle: searchText
        }
        $(".DataList_ul").html('');
        getAssetsList(params);
    })

    $('.allcheck').find("input").change(function () {//全选按钮
        var isc = $(this)[0].checked;
        if (isc) {
            $.each($(".checkbox-span"), function (i, v) {
                $(".checkbox-span").find(".checkbox")[i].checked = true;
            })
        } else {
            $.each($(".checkbox-span"), function (i, v) {
                $(".checkbox-span").find(".checkbox")[i].checked = false;
            })
            inventoryTitle = 'first';
        }
    })




})


