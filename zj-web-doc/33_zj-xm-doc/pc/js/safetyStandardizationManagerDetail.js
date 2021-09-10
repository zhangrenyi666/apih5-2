var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30);
//这个和wxx.photoType必须是同步的
var initType = l.getUrlParam('photoType') ? l.getUrlParam('photoType') : '';
//修改zTree的ajax带上token  然后把参数也变了
$.fn.zTree._z.view.asyncNode = function (setting, node, isSilent, callback) {
    var i, l
    var tools = $.fn.zTree._z.tools;
    var $$ = tools.$;
    var consts = $.fn.zTree.consts;
    var data = $.fn.zTree._z.data;
    var view = $.fn.zTree._z.view;
    if (node && !node.isParent) {
        tools.apply(callback);
        return false;
    } else if (node && node.isAjaxing) {
        return false;
    } else if (tools.apply(setting.callback.beforeAsync, [setting.treeId, node], true) == false) {
        tools.apply(callback);
        return false;
    }
    if (node) {
        node.isAjaxing = true;
        var icoObj = $$(node, consts.id.ICON, setting);
        icoObj.attr({
            "style": "",
            "class": consts.className.BUTTON + " " + consts.className.ICO_LOADING
        });
    }
    var tmpParam = {};
    for (i = 0, l = setting.async.autoParam.length; node && i < l; i++) {
        var pKey = setting.async.autoParam[i].split("="),
            spKey = pKey;
        if (pKey.length > 1) {
            spKey = pKey[1];
            pKey = pKey[0];
        }
        tmpParam[spKey] = node[pKey];
    }
    if (tools.isArray(setting.async.otherParam)) {
        for (i = 0, l = setting.async.otherParam.length; i < l; i += 2) {
            tmpParam[setting.async.otherParam[i]] = setting.async.otherParam[i + 1];
        }
    } else {
        for (var p in setting.async.otherParam) {
            tmpParam[p] = setting.async.otherParam[p];
        }
    }

    //更改zTree的参数为自定义的的 原本使用的是
    var _params = {
        parentId: tmpParam.levelId,
    }
    var _tmpV = data.getRoot(setting)._ver;
    $.ajax({
        contentType: setting.async.contentType,
        dataType: setting.async.dataType,
        cache: false,
        type: setting.async.type,
        url: tools.apply(setting.async.url, [setting.treeId, node], setting.async.url),
        data: JSON.stringify(_params), //tmpParam  
        beforeSend: function (xhr) {
            xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
            var token = Lny.getCookie('code')
            xhr.setRequestHeader('token', token); //
        },
        success: function (msg) {
            if (_tmpV != data.getRoot(setting)._ver) {
                return;
            }
            var newNodes = [];
            try {
                if (!msg || msg.length == 0) {
                    newNodes = [];
                } else if (typeof msg == "string") {
                    newNodes = eval("(" + msg + ")");
                } else {
                    newNodes = msg;
                }
            } catch (err) {
                newNodes = msg;
            }

            if (node) {
                node.isAjaxing = null;
                node.zAsync = true;
            }
            view.setNodeLineIcos(setting, node);
            if (newNodes && newNodes !== "") {
                newNodes = tools.apply(setting.async.dataFilter, [setting.treeId, node, newNodes], newNodes);
                view.addNodes(setting, node, !!newNodes ? tools.clone(newNodes) : [], !!isSilent);
            } else {
                view.addNodes(setting, node, [], !!isSilent);
            }
            setting.treeObj.trigger(consts.event.ASYNC_SUCCESS, [setting.treeId, node, msg]);
            tools.apply(callback);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (_tmpV != data.getRoot(setting)._ver) {
                return;
            }
            if (node) node.isAjaxing = null;
            view.setNodeLineIcos(setting, node);
            setting.treeObj.trigger(consts.event.ASYNC_ERROR, [setting.treeId, node, XMLHttpRequest, textStatus, errorThrown]);
        }
    });
    return true;
}

//配置工序表格数据
var table = $('#table').DataTable({
    "info": false, //是否显示数据信息
    "paging": false, //是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false, //是否显示‘进度’提示
    "searching": false, //是否开启自带搜索
    "autoWidth": false, //是否监听宽度变化
    "columnDefs": [ //表格列配置
        {
            "targets": [0],
            "data": "rowIndex",
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [1], //第几列
            "data": "title", //接口对应字段
            "title": "资料名称", //表头名
            "defaultContent": "-", //默认显示
            "width": 150,
            "render":function(data,index,row){
                return '<a style="color:blue;" onclick="myOpen(\' '+encodeURI(JSON.stringify(row))+ '\')" href="javascript:void(0);">'+data+'</a>'
            }
        },
        {
            "targets": [2], //第几列
            "data": "remarks", //接口对应字段
            "title": "备注", //表头名
            "defaultContent": "-", //默认显示
            "width": 200,
        },
        {
            "targets": [3], //第几列
            "data": "attachment", //接口对应字段
            "title": "附件", //表头名
            "defaultContent": "-", //默认显示
            "width": 200,
            render: function (data) {
                var dom = "";
                for (var i = 0; i < data.length; i++) {
                    var fileUrl = data[i].fileUrl,
                        fileName = data[i].fileName;
                    dom += '<li style="list-style:none;">';
                    dom +=
                        '<a style="width:80px;"  href="' +
                        encodeURI(fileUrl) +
                        '" target="_blank">' +
                        fileName +
                        "</a>";
                    dom += "</li>";
                }
                return dom;
            }
        },
        {
            "targets": [4], //第几列
            "data": "modifyUserName", //接口对应字段
            "title": "更新者", //表头名
            "defaultContent": "-", //默认显示
            "width": 80
        },
        {
            "targets": [5], //第几列
            "data": "modifyTime", //接口对应字段
            "title": "更新时间", //表头名
            "defaultContent": "-", //默认显示
            "width": 120,
            "render": function (data) {
                if (data) {
                    return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
                } else {
                    return "-";
                }
            }
        }
    ]
});

//更新工序表格的方法
var pager = $("#pager").page({
    remote: { //ajax请求配置
        url: l.getApiUrl("getXmZlDatabaseList"),
        params: {},
        success: function (result) {
            if (result.success) {
                var data = result.data;
                //console.dir(data)
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
                // Lny.log(result)
            } else {
                layer.alert(result.message, {
                    icon: 0,
                }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ['70%', '70%'],
    customBtnGroup:{
        btns: []
    },
    conditions: [
        {
            type: "hidden", //五种形式：text,select,date,hidden,textarea,
            name: "levelId", //输入字段名        
        },
        {
            type: "hidden",
            name: "levelIdAll", //输入字段名
        },
        {
            type: "hidden",
            name: "levelNameAll", //输入字段名
        },
        {
            type: "hidden",
            name: "databaseId", //输入字段名
        },
        {
            type: "text", //
            name: "title", //
            label: "资料名称", //
            placeholder: "请输入资料名称",
            must: true,
            immutableAdd: true
        },
        {
            type: "textarea", //
            name: "remarks", //
            label: "备注", //
            placeholder: "请输入资料备注",
            immutableAdd: true
        },
        {
            type: "upload",//
            name: "attachment",//
            label: "附件",//
            btnName: "添加附件",
            projectName: "zj-xm-doc",
            uploadUrl: 'uploadFilesByFileName',
            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"],
            immutableAdd: true
        }
    ]
})
function myOpen(row){
    var rowData = JSON.parse(decodeURI(row));
    detailLayer.open(rowData);
}
$(document).ready(function () {
    ! function () { //改变样式方法
        $(window).resize(function () {
            $('.left').css({
                height: $(window).height(),
            })
        })

        $('.left').css({
            height: $(window).height(),
        })
        $('.right').css({
            'minHeight': $(window).height()
        })
    }();

    l.ajax('getXmZlDatabaseLevelList', {
        parentId: '0'
    }, function (res) { //请求所有数据 
        var node; //右键时选择的节点 
        var ztreeFn = {
            childrenName: 'projectLevelList', //子节点字段
            nodeName: 'levelName', //节点字段
            onClick: function (event, treeId, treeNode, clickFlag) {
                // 设置字体颜色
                $('.curSelectedNode').removeClass('curSelectedNode');
                var _cur = event.target;
                $(_cur).parent().addClass('curSelectedNode');
                node = treeNode

                wxx.clickNodeId = treeNode.levelId;
                var pidAllArr;
                if (treeNode.parentIdAll) {
                    pidAllArr = treeNode.parentIdAll.split(',');
                    wxx.clickNodeId = treeNode.levelId;
                }
                var params = {
                    levelId: treeNode.levelId,
                };
                wxx.applyProcessTable(params);
            },
            addDiyDom: function (treeId, treeNode) { //渲染后面的节点后边的数字
                var aObj = $("#" + treeNode.tId + "_a");
                var num = $("<span>", {
                    'class': 'projectNum',
                    'parentId': treeNode.levelId,
                    'html': '<span>' + '(编码：<span class="projectNum-num-span" id="' + treeNode.levelId + 'sigle' + '">' + treeNode.levelCode + '</span>' + ')</span>',
                    click: function () {
                        wxx.clickNodeId = '';
                    }

                }).appendTo(aObj);

            },
            zTreeOnRename: function (e, treeId, treeNode) { //改名回调
                var folderFlag, isParent;
                if (wxx.folderFlag && wxx.isParent) {
                    folderFlag = wxx.folderFlag;
                    isParent = wxx.isParent;
                } else {
                    folderFlag = treeNode.folderFlag;
                    isParent = treeNode.isParent;
                }

                if (isParent) { //为了文件在treeNode里面没有数据 folderFlag
                    folderFlag = '1'
                } else {
                    folderFlag = '0'
                }
                var params = {
                    levelId: node.levelId,
                    levelName: node.levelName,
                    isParent: isParent,
                    levelCode: node.levelCode
                }
                wxx.folderFlag = '';
                wxx.isParent = '';
                l.ajax('updateXmZlDatabaseLevel', params, function (res) {
                    layer.msg('修改成功');
                    wxx.isAdd = false; //不是新增改名
                })
            },
            OnRightClick: function (event, treeId, treeNode) { //右键菜单方法
                event.stopPropagation();
                if (treeNode) {
                    wxx.rightNode = treeNode;
                    wxx.rightClickNodeId = treeNode.levelId;
                    node = treeNode; //选中的节点
                    wxx.rootNodeId = treeNode.tId.slice(0, (treeNode.tId.indexOf('_'))); //截取需要的id
                    var dom;
                    var isfile;
                    if (node.isParent) {
                        isfile = 'fileds';
                    } else {
                        isfile = 'file';
                    }
                    //获取全部pid
                    var allpath = [treeNode.levelId];
                    getAllPath(treeNode);

                    function getAllPath(data) {
                        var dataParentpath = data.getParentNode();
                        if (dataParentpath) {
                            allpath.push(dataParentpath.levelId);
                            getAllPath(dataParentpath)
                        } else {
                            var newAllpath = allpath.reverse();
                            var strAllpath = newAllpath.join();
                            wxx.allParPath = strAllpath;
                        }
                    }
                    wxx.showMenu(event, wxx.rMenu, true, isfile);
                }
            },
            // beforeDrag: function (treeId, treeNodes) { //拖动之前    
            //     var par = treeNodes ? treeNodes[0].getParentNode() : null;
            //     if (treeNodes[0].isFirstNode && treeNodes[0].isParent && !par) { //根节点被拖动
            //         layer.open({
            //             type: '0',
            //             title: '拖动排序',
            //             content: '<div class="sortLayer">' +
            //                 '<ul class="sortLayer-ul" id="sortLayer-ul"></ul>' +
            //                 '</div>',
            //             btn: ['确定', '取消'],
            //             yes: function (index) {
            //                 var oAllDatas = wxx.allDatas;
            //                 var newAllDatas = []; //排序好的数据
            //                 var sortLayerLis = $('.sortLayer-ul').children();
            //                 $.each(sortLayerLis, function (i, v) {
            //                     var sortLayerLiNodeId = $(v).attr('levelId'); //排序好的 li 的 id
            //                     $.each(oAllDatas, function (i, v) {
            //                         if (v.levelId == sortLayerLiNodeId) {
            //                             delete v.projectLevelList;
            //                             newAllDatas.push(v);
            //                         }
            //                     })
            //                 })

            //                 var params = {
            //                     gxProjectLevelDictList: newAllDatas
            //                 }
            //                 l.ajax('pcExchangeGxProjectLevelDict', params, function (res) {
            //                     layer.msg('移动成功！');
            //                     l.ajax('getXmZlDatabaseLevelList', {
            //                         parentId: '0'
            //                     }, function (res) { //请求所有数据
            //                         wxx.allDatas = res; //使数据同步
            //                         wxx.applyNode(res); //渲染节点函数
            //                     })

            //                     layer.close(index);
            //                 })
            //             }
            //         })

            //         $.each(wxx.allDatas, function (i, v) {
            //             var newAllDatasDelChildren = [];
            //             var $li = $('<li>', {
            //                 'class': 'sortLayer-li',
            //                 'levelId': v.levelId,
            //                 'html': v.levelName,
            //             }).appendTo('.sortLayer-ul');
            //         })

            //         var el = document.getElementById('sortLayer-ul');
            //         var sortable = new Sortable(el, {
            //             dragClass: "sortable-drag",
            //             chosenClass: "sortable-chosen", // Class name for the chosen item
            //         })
            //         return false;
            //     }
            // },
            // beforeDrop: function (treeId, treeNodes, targetNode, moveType) {
            //     var b = false;
            //     //限制不让托处当前文件夹 目标节点id 只能是兄弟节点 
            //     var parentNode = treeNodes ? treeNodes[0].getParentNode() : null;
            //     if (parentNode) {
            //         var childrenArr = parentNode.projectLevelList;
            //         $.each(childrenArr, function (i, v) {
            //             if (v.levelId != treeNodes[0].levelId) { //节点本身不能算进去
            //                 if (targetNode.levelId == v.levelId) {
            //                     b = true;
            //                 }
            //             }
            //         })
            //     }
            //     if (b) {
            //         return b;
            //     }
            // },
            // zTreeOnDrop: function (event, treeId, treeNodes, targetNode, moveType) {
            //     var parent;
            //     if (targetNode) {
            //         parent = targetNode.getParentNode(); //获取父节点
            //         var nodeList = [];
            //         $.each(parent.projectLevelList, function (i, v) {
            //             delete v.projectLevelList;
            //             nodeList.push(v);
            //         })
            //         var nodeIdArr = [];
            //         var params = {
            //             gxProjectLevelDictList: nodeList
            //         }
            //         l.ajax('pcExchangeGxProjectLevelDict', params, function (res) {
            //             layer.msg('移动成功！');
            //         })
            //     }
            // },
            // zTreeOnMouseDown: function (event, treeId, treeNode) { //鼠标按下时
            //     if (treeNode) {
            //         var allpath = wxx.getAllPathId(treeNode, 'nodes');
            //         wxx.allParPathClick = allpath;
            //     }
            // },
            filter: function (treeId, parentNode, childNodes) { //请求到的节点的子节点数据
                var childNodes = childNodes.data;
                if (!childNodes) return null;
                for (var i = 0, l = childNodes.length; i < l; i++) {
                    childNodes[i].isParent = true;
                    if (childNodes[i].levelName) {
                        childNodes[i].levelName = childNodes[i].levelName.replace(/\.n/g, '.');
                    }
                }
                return childNodes;
            }
        };
        var wxx = {
            photoType: initType, //0,
            urlType: l.getUrlParam('type'),
            allDatas: res ? res : [], //所有的数据  在增加根节点 删除根节点 用到  
            rMenu: $("#rMenu"), //右键的菜单
            rootNodeId: '', //根节点Dom - id
            responseId: '', //新增返回来的id 新增节点后改名使用
            clickNodeId: '', //点击节点时的节点 上传图片 修改图片
            rightClickNodeId: '', //右键菜单是去该节点id  用于删除节点判断删除的是不是当前查看的节点
            rightNode: '', //右键菜单时的节点 
            allParPath: '', //右键时 当前节点到根节点 的全部路径 就是id数组
            allParPathClick: '', //鼠标按下时 当前节点到根节点 的全部路径 就是id数组            
            folderFlag: '', //新增节点时给后台判断是否是文件夹 
            isParent: '', //新增时给前台判断是否是文件夹
            isAdd: false, //用于改名是判断是不是新增时的改名
            addNodeAllId: '', //用于更新工序数字
            delFiledPidAll: '', //被删除的文件夹 的pid
            isMove: false, //左边是否可以拉伸
            miseOpenNodes: [], //需要被展开的数据
            curPnum: '', //当前右键工序的数量--只在导入工序成功后更新这个数字  其他情况一律为空
            setting: { //默认配置项
                view: {
                    showIcon: true,
                    dblClickExpand: true,
                    selectedMulti: false,
                    showLine: false,
                    addDiyDom: ztreeFn.addDiyDom
                },
                data: {
                    key: {
                        name: ztreeFn.nodeName,
                        children: ztreeFn.childrenName
                    }
                },
                callback: {
                    onRightClick: ztreeFn.OnRightClick,
                    onClick: ztreeFn.onClick,
                    onRename: ztreeFn.zTreeOnRename,
                    // beforeDrag: ztreeFn.beforeDrag,
                    // beforeDrop: ztreeFn.beforeDrop,
                    // onDrop: ztreeFn.zTreeOnDrop,
                    // onMouseDown: ztreeFn.zTreeOnMouseDown
                },
                edit: {
                    enable: true,
                    showRemoveBtn: false,
                    showRenameBtn: false,
                    drag: {
                        isMove: false,
                        dropRoot: false,
                        inner: false,
                    }
                },
                async: {
                    enable: true,
                    url: l.domain + "getXmZlDatabaseLevelList",
                    autoParam: ["levelId"],
                    dataType: "json",
                    type: "post",
                    contentType: "application/json; charset=utf-8",
                    dataFilter: ztreeFn.filter,
                }
            },
            getAllPathId: function (data, type) { //获取目标id的全部父id  相当于ztree的getPath();  传入node  type[可不传 默认返回id]
                var type = type || 'id';
                if (type == 'id') {
                    var allpath = [data.levelId];
                    getAllPath(data)

                    function getAllPath(data) {
                        var dataParentpath = data.getParentNode();
                        if (dataParentpath) {
                            allpath.push(dataParentpath.levelId);
                            getAllPath(dataParentpath)
                        }
                    }
                    return allpath;
                } else if (type == 'nodes') {
                    var allpath = [data];
                    getAllPath(data)

                    function getAllPath(data) {
                        var dataParentpath = data.getParentNode();
                        if (dataParentpath) {
                            allpath.push(dataParentpath);
                            getAllPath(dataParentpath)
                        }
                    }
                    return allpath;
                }
            },
            delFileAndFiled: function (delNodePid, rootNodeId) { //传入被删除节点的pid  根节点id  纠正删除文件夹下文件后isParent属性问题
                var treeObj = $.fn.zTree.getZTreeObj(wxx.rootNodeId);
                var oNode = treeObj.getNodeByParam("levelId", delNodePid, null);
                oNode.isParent = true;
                treeObj.updateNode(oNode);
            },
            addNode: function (event, type, folderFlag) { //增加节点 
                _add()

                function _add() {
                    var folderFlag = folderFlag; //是否是文件夹
                    var isParent = true;

                    if (type == 'node') { //增加子节点

                        //不能新增文件的的情况
                        //该文件夹下有一个文件或者文件夹存在了
                        //文件夹不限
                        var canAdd = true; //是否能做新增操作

                        //首先对其做展开操作
                        if (!node.zAsync) { //节点未展开
                            layer.alert('请先将节点展开在做新增操作')
                            return;
                        }
                        if (canAdd) {
                            var zTree = $.fn.zTree.getZTreeObj(wxx.rootNodeId);
                            var parentId = node.levelId;

                            layer.open({ // 自定义弹出窗口，输入名称的编码
                                type: 1,
                                shadeClose: false,
                                area: ['350px', '250px'],
                                move: false,
                                title: "输入节点名称和编码",
                                btn: ['确定', '取消'],
                                content: "<input type=\"text\" id= \"inputLevelName\" class=\"newNodeInput\" placeholder=\"输入名称\"/></br><input type=\"text\" id= \"inputLevelCode\" class=\"newNodeInput\" placeholder=\"输入编码\"/>",
                                yes: function (index) {
                                    if ($("#inputLevelName").val() === "") {
                                        layer.msg('名称不能为空', {
                                            time: 1000
                                        })
                                    } else if ($("#inputLevelCode").val() === "") {
                                        layer.msg('编码不能为空', {
                                            time: 1000
                                        })
                                    } else {
                                        var params = { // 接口参数
                                            levelName: $("#inputLevelName").val(),
                                            levelCode: $("#inputLevelCode").val(),
                                            isParent: isParent,
                                            parentId: parentId,
                                            parentIdAll: node.parentIdAll,
                                            parentNameAll: node.parentNameAll
                                        }
                                        l.ajax('addXmZlDatabaseLevel', params, function (res) {
                                            layer.msg('新增成功')
                                            // 关闭弹窗
                                            layer.close(index)
                                            // 更新ztree dom数据，将新添加的层级添加显示到树形结构中
                                            if (node) {
                                                newNode = zTree.addNodes(node, {
                                                    levelId: res.levelId,
                                                    parentId: res.parentId,
                                                    isParent: isParent,
                                                    levelName: res.levelName,
                                                    levelCode: res.levelCode
                                                });
                                            }
                                            node = newNode[0];
                                            wxx.responseId = res.levelId; //手动修改id  新增成功后修改名称需要
                                            newNode[0].levelId = res.levelId;
                                            newNode[0].parentId = res.parentId;
                                            newNode[0].parentIdAll = res.parentIdAll;
                                            newNode[0].parentNameAll = res.parentNameAll;
                                            newNode[0].levelCode = res.levelCode;
                                            newNode[0].levelName = res.levelName;

                                            wxx.folderFlag = folderFlag;
                                            wxx.isParent = isParent;
                                            wxx.addNodeAllId = params.parentIdAll;

                                        })
                                    }
                                }
                            })

                        }
                    } else if (type == 'rootNode') { //新增根节点                      
                        layer.open({
                            type: 1,
                            shadeClose: false,
                            area: ['350px', '250px'],
                            move: false,
                            title: "输入节点名称和编码",
                            btn: ['确定', '取消'],
                            content: "<input type=\"text\" id= \"inputLevelName\" class=\"newNodeInput\" placeholder=\"输入名称\"/></br><input type=\"text\" id= \"inputLevelCode\" class=\"newNodeInput\" placeholder=\"输入编码\"/>",
                            yes: function (index) {
                                if ($("#inputLevelName").val() === "") {
                                    layer.msg('名称不能为空', {
                                        time: 1000
                                    })
                                } else if ($("#inputLevelCode").val() === "") {
                                    layer.msg('编码不能为空', {
                                        time: 1000
                                    })
                                } else {
                                    var params = {
                                        levelName: $("#inputLevelName").val(),
                                        levelCode: $("#inputLevelCode").val(),
                                        isParent: isParent,
                                        parentId: '0',
                                        parentIdAll: '',
                                        parentNameAll: ''
                                    }
                                    l.ajax('addXmZlDatabaseLevel', params, function (res) {
                                        layer.msg('新增成功')
                                        layer.close(index)
                                        l.ajax('getXmZlDatabaseLevelList', {
                                            parentId: '0'
                                        }, function (res) { //请求所有数据
                                            res.isParent = true; //本地添加之后立马就是文件夹
                                            wxx.allDatas = res; //使数据同步
                                            wxx.applyNode(res); //渲染节点函数
                                        })
                                    })
                                }
                            }
                        });
                    }
                }
            },
            addNodeFn: function (e) { //增加节点 点击函数
                var eAdd = /^(add)$/i;
                var eAddRootNode = /^(addRootNode)$/i;
                if (eAdd.test(($(e.target).attr('id')))) {
                    //只能新增文件夹
                    wxx.addNode(e, 'node', '1');

                } else if (eAddRootNode.test(($(e.target).attr('id')))) { //新增根节点被点击
                    wxx.addNode(e, 'rootNode', '1');
                }
            },
            delNodeFn: function (params) { //删除节点具体函数
                var zTree = params.zTree;
                var delNodeId = params.delNodeId; //被删除的节点的id
                var delNodePid = params.delNodePid; //parentId 
                var delNum = params.delNum;
                layer.msg('删除成功');
                if (wxx.clickNodeId == wxx.rightClickNodeId) {
                    //清除表格
                    wxx.applyProcessTable('')
                }
                if (delNodePid != '0') { //普通删除
                    zTree.removeNode(node);
                    wxx.delFileAndFiled(delNodePid, wxx.rootNodeId);

                } else if (delNodePid == '0') { //根节点删除
                    //跟节点删除后需要刷新节点
                    l.ajax('getXmZlDatabaseLevelList', {
                        // levelType: initType,
                        parentId: '0'
                    }, function (res) { //请求所有数据

                        res.isParent = true; //本地添加之后立马就是文件夹
                        wxx.allDatas = res; //使数据同步
                        wxx.applyNode(res); //渲染节点函数
                    })
                }
            },
            delNode: function (event, type) { //删除 fn 根据pid判断是不是根节点
                var zTree = $.fn.zTree.getZTreeObj(wxx.rootNodeId);
                var delNodeId = node.levelId; //被删除的节点的id
                var delNodePid = node.parentId; //parentId 
                wxx.delFiledPidAll = node.parentIdAll; //被删除后要更新的所有pid
                var delData = {
                    zTree: zTree,
                    delNodePid: delNodePid,
                    delNodeId: delNodeId
                }

                layer.confirm('确定删除吗？', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    var params = [{
                        levelId: delNodeId
                    }];
                    l.ajax('batchDeleteUpdateXmZlDatabaseLevel', params, function (res) {
                        wxx.delNodeFn(delData);
                        var $tableContainer = $('.page-container');
                        $tableContainer.hide();
                    })
                });

            },
            //改为了渲染表格的方法只需要传入一个参数即可
            applyProcessTable: function (params) {
                var $tableContainer = $('.page-container'); //表格容器
                if (params) {
                    //显示工序列表 
                    $tableContainer.show();
                    pager.page('remote', params);
                } else {
                    //影藏表格
                    $tableContainer.hide()
                }
            },
            showMenu: function (event, objDom, isShow, nodeType) { //右键菜单显示 或者 隐藏 
                if (isShow) {
                    var x = event.pageX;
                    var y = event.pageY;
                    $(objDom).find('li').show(0); //所有li立马显示
                    $(objDom).find('li').click(function () { //li被点后立马收缩菜单
                        $(objDom).slideUp(150);
                    })
                    if (nodeType == 'root') { //根节点
                        $('#add, #del, #rename, #edit').hide(0);
                    } else if (nodeType == 'file') { //文件
                        $('#addRootNode').hide(0); //#add, 
                    } else if (nodeType == 'fileds') { //文件夹
                        $('#addRootNode').hide(0);
                    }
                    //设置完该隐藏选项后出来
                    $(objDom).slideDown(30, function () {
                        //显示后执行判断移动位置  
                        if (y + $(objDom).height() > $('.main').height()) {
                            $(objDom).css({
                                'left': x - 5 + 'px',
                                'top': y - $(objDom).height() + 'px',
                                'border-radius': '5px',
                                'border-bottom-left-radius': '0'
                            })
                        }
                    });

                    //这应该立马设置菜单的位置
                    $(objDom).css({
                        'left': x - 5 + 'px',
                        'top': y - 5 + 'px',
                        'border-radius': '5px',
                        'border-top-left-radius': '0'
                    })
                    //此时应该判断下菜单是不是在最底部在最底部的会菜单应该往上移不然会被遮挡
                } else if (!isShow) {
                    $(objDom).slideUp(150);
                }
            },
            applyNode: function (res) { //渲染全部节点 fn
                $('.tree-container').html('');
                if (res && res.length >= 1) {
                    $.each(res, function (i, v) {
                        if (v.folderFlag == '0') {
                            v.isParent = false;
                        } else if (v.folderFlag == '1') {
                            v.isParent = true;
                        }

                        var $ul = $('<ul id="tree' + i + '" class="ztree"></ul>');
                        $('.tree-container').append($ul);
                        $.fn.zTree.init($("#tree" + i + ""), wxx.setting, res[i]);
                    }); //数据遍历 over
                } else {
                    $('.tree-container').append('<h4>点击鼠标右键，<br/>新增一个吧！</h4>');
                }
            },
            getCheckedNodes: function () { //获取选择的所有节点
                var checkedArr = [];
                $.each(wxx.allDatas, function (i, v) {
                    var treeObj = $.fn.zTree.getZTreeObj("tree" + i);
                    var nodes = treeObj.getCheckedNodes(true);
                    checkedArr.push(nodes)
                }); //数据遍历 over

                var checkedNodes = [];
                $.each(checkedArr, function (i, v) { //过滤节点
                    $.each(v, function (ii, vv) {
                        checkedNodes.push(vv);
                    })
                })
                return checkedNodes;
            }
        } //wxx over
        wxx.applyNode(wxx.allDatas); //进行第一次渲染

        $('.main').on('mousedown mouseup mousemove', function (event) { //左边伸缩

            var $target = $(event.target);
            var eType = event.type;
            var x = '';

            if (eType == 'mousedown') {
                if ($target.attr('class') == 'puling-down') {
                    wxx.isMove = true;
                    //防止意外复制
                    event.stopPropagation();
                    event.preventDefault();
                }
            }

            if (eType == 'mouseup') {
                wxx.isMove = false;
            }

            if (eType == 'mousemove') {
                if (wxx.isMove) {
                    x = event.clientX;
                    if (x < 200) {
                        return;
                    } else if (x > 800) {
                        return;
                    }
                    $('.left').css({
                        width: x
                    })
                    $('.right').css({
                        "padding-left": x
                    })
                }
            }
        })

        $('.left').contextmenu(function (event) { //右键去除默认弹窗
            event.stopPropagation();
            if ($(event.target).attr('class')) { //右键 非节点 的位置
                if (event.type == 'contextmenu' || event.button == '2') {
                    if (($(event.target).attr('class')).indexOf('left') != '-1') {
                        wxx.showMenu(event, wxx.rMenu, true, 'root');
                    }
                }
            }
            return false;
        })
        $('.left').on('click mousedown mouseleave scroll', function (event) {
            if (event.type == 'click') { //鼠标在点击左边 隐藏菜单
                event.stopPropagation();
                wxx.showMenu(event, wxx.rMenu, false)
            }
            if (event.type == 'scroll') { //鼠标在点击左边 隐藏菜单
                event.stopPropagation();
                var top = $(this).scrollTop();
                $('.puling-down').css({
                    top: top
                })
            }
        })
        $('#rMenu').on('mouseleave click', function (e) { //菜单
            var eventType = e.type;
            var eventTarget = e.target;
            switch (eventType) { //菜单事件
                case 'mouseleave':
                    $(this).slideUp(150);
                    break;
                case 'click':
                    switch (eventTarget.id) {
                        case 'add': //新增节点
                            wxx.addNodeFn(e);
                            break;
                        case 'addRootNode': //新增根节点
                            wxx.addNodeFn(e);
                            break;
                        case 'del': //删除节点
                            wxx.delNode(e);
                            break;
                        case 'rename': //改节点名
                            layer.open({
                                type: 1,
                                shadeClose: false,
                                area: ['350px', '250px'],
                                move: false,
                                title: "输入节点名称和编码",
                                btn: ['确定', '取消'],
                                content: "<input type=\"text\" id= \"inputLevelName\" class=\"newNodeInput\" placeholder=\"输入名称\"/></br><input type=\"text\" id= \"inputLevelCode\" class=\"newNodeInput\" placeholder=\"输入编码\"/>",
                                yes: function (index) {
                                    if ($("#inputLevelName").val() === "") {
                                        layer.msg('名称不能为空', {
                                            time: 1000
                                        })
                                    } else if ($("#inputLevelCode").val() === "") {
                                        layer.msg('编码不能为空', {
                                            time: 1000
                                        })
                                    } else {
                                        var params = {
                                            levelName: $("#inputLevelName").val(),
                                            levelCode: $("#inputLevelCode").val(),
                                            isParent: node.isParent,
                                            levelId: node.levelId
                                        }
                                        l.ajax('updateXmZlDatabaseLevel', params, function (res) {
                                            layer.msg('更新成功')
                                            layer.close(index)
                                            // 修改后更新parentNameAll
                                            node.parentNameAll = res.parentNameAll

                                            // 更新成功后，更新ztree所改该条dom数据
                                            var treeObj = $.fn.zTree.getZTreeObj(wxx.rootNodeId);
                                            var nodes = treeObj.getNodeByParam("levelId", res.levelId, null);
                                            nodes.levelName = res.levelName;
                                            nodes.levelCode = res.levelCode;
                                            treeObj.updateNode(nodes);
                                            // 手动更改编码数据
                                            $("#" + res.levelId + "sigle").html(res.levelCode)

                                        })
                                    }
                                }
                            });
                            break;
                    }
                    break;
            }
        })


        if (wxx.urlType == 'portion_aq') { //判断是不是安全按钮被点击
            $('.btn[data-name="btnTwo"]').click();
            wxx.applyProcessTable('');
        }
    })
});