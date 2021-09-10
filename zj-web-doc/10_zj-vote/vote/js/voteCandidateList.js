var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var table, detailLayer, detailLayer0;
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getVoteCandidateList"),
        params: {
            voteId: l.getUrlParam("id"),
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                if (data[0]) {
                    $("button[data-name=edit]").show()
                    $("button[data-name=del]").show()
                    var candidateDefaultOrder = 1;//模式给1
                    $.each(data, function (index, item) {
                        item.rowIndex = index
                        candidateDefaultOrder = item.candidateDefaultOrder;
                    })
                    var conditions = [
                        {
                            type: "hidden",//五种形式：text,select,date,hidden,textarea,
                            name: "voteId",//输入字段名
                            defaultValue: l.getUrlParam("id")
                        },
                        {
                            type: "hidden",//五种形式：text,select,date,hidden,textarea,
                            name: "candidateId"//输入字段名
                        },
                        {
                            type: "number",//
                            name: "candidateOrder",//
                            label: "排序",//
                            defaultValue: candidateDefaultOrder,
                            placeholder: "请输入排序",
                            must: true
                        }
                    ];
                    var columnDefs = [//表格列配置
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
                            "data": "candidateOrder",//接口对应字段
                            "title": "排序",//表头名
                            "defaultContent": "-"//默认显示
                        }
                    ]
                    var columnDefsLen = 1;
                    if (data[0].titleOne) {
                        columnDefsLen = columnDefsLen + 1
                        columnDefs.push({
                            "targets": [columnDefsLen],//第几列
                            "data": "contentOne",
                            "title": data[0].titleOne,//表头名
                            "defaultContent": "-"//默认显示
                        })
                        conditions.push({
                            type: "text",//
                            name: "contentOne",//    
                            label: data[0].titleOne
                        })
                    }
                    if (data[0].titleTwo) {
                        columnDefsLen = columnDefsLen + 1
                        columnDefs.push({
                            "targets": [columnDefsLen],//第几列
                            "data": "contentTwo",
                            "title": data[0].titleTwo,//表头名
                            "defaultContent": "-"//默认显示
                        })
                        conditions.push({
                            type: "text",//
                            name: "contentTwo",//    
                            label: data[0].titleTwo
                        })
                    }
                    if (data[0].titleThree) {
                        columnDefsLen = columnDefsLen + 1
                        columnDefs.push({
                            "targets": [columnDefsLen],//第几列
                            "data": "contentThree",
                            "title": data[0].titleThree,//表头名
                            "defaultContent": "-"//默认显示
                        })
                        conditions.push({
                            type: "text",//
                            name: "contentThree",//    
                            label: data[0].titleThree
                        })
                    }
                    if (data[0].titleFour) {
                        columnDefsLen = columnDefsLen + 1
                        columnDefs.push({
                            "targets": [columnDefsLen],//第几列
                            "data": "contentFour",
                            "title": data[0].titleFour,//表头名
                            "defaultContent": "-"//默认显示
                        })
                        conditions.push({
                            type: "text",//
                            name: "contentFour",//    
                            label: data[0].titleFour
                        })
                    }
                    if (data[0].titleFive) {
                        columnDefsLen = columnDefsLen + 1
                        columnDefs.push({
                            "targets": [columnDefsLen],//第几列
                            // "data": "name",//接口对应字段
                            "data": "contentFive",
                            "title": data[0].titleFive,//表头名
                            "defaultContent": "-"//默认显示
                        })
                        conditions.push({
                            type: "text",//
                            name: "contentFive",//    
                            label: data[0].titleFive
                        })
                    }
                    conditions.push({
                        type: "upload",//
                        name: "imageList",//
						projectName: "zj-vote",
                        label: "图片",//
                        btnName: "添加图片",
                        fileType: ["jpg", "jpeg", "png", "gif"]
                    })
                    if (!detailLayer) {
                        detailLayer = $('#detailLayer').detailLayer({
                            layerArea: ["100%", "100%"],
                            conditions: conditions,
                            onSave: function (obj, _params) {
                                if (_params.imageList.length > 1) {
                                    layer.alert("只能上传一张图片", { icon: 0 }, function (index) {
                                        layer.close(index);
                                    });
                                } else {
                                    var newParams = {
                                        voteId: _params.voteId,
                                        candidateOrder: _params.candidateOrder,
                                        candidateId: _params.candidateId,
                                        imageList: _params.imageList || [],
                                        contentOne: _params.contentOne || "",
                                        contentTwo: _params.contentTwo || "",
                                        contentThree: _params.contentThree || "",
                                        contentFour: _params.contentFour || "",
                                        contentFive: _params.contentFive || ""
                                    }
                                    l.ajax('updateVoteCandidate', newParams, function (data) {
                                        pager.page('remote');
                                        obj.close()
                                    })
                                }
                            },
                            onAdd: function (obj, _params) {
                                if (_params.imageList.length > 1) {
                                    layer.alert("只能上传一张图片", { icon: 0 }, function (index) {
                                        layer.close(index);
                                    });
                                } else {
                                    var newParams = {
                                        voteId: _params.voteId,
                                        candidateOrder: _params.candidateOrder,
                                        candidateId: _params.candidateId,
                                        imageList: _params.imageList || [],

                                        titleOne: data[0].titleOne || "",
                                        titleTwo: data[0].titleTwo || "",
                                        titleThree: data[0].titleThree || "",
                                        titleFour: data[0].titleFour || "",
                                        titleFive: data[0].titleFive || "",

                                        contentOne: _params.contentOne || "",
                                        contentTwo: _params.contentTwo || "",
                                        contentThree: _params.contentThree || "",
                                        contentFour: _params.contentFour || "",
                                        contentFive: _params.contentFive || ""
                                    }
                                    l.ajax('addVoteCandidate', newParams, function (data) {
                                        pager.page('remote');
                                        obj.close()
                                    })
                                }
                            }
                        });
                    } else {
                        detailLayer.resetDefaultValue("candidateOrder", candidateDefaultOrder)
                    }
                    if (!table) {
                        table = $('#table').html("").DataTable({
                            "info": false,//是否显示数据信息
                            "paging": false,//是否开启自带分页
                            "ordering": false, //是否开启DataTables的高度自适应
                            "processing": false,//是否显示‘进度’提示
                            "searching": false,//是否开启自带搜索
                            "autoWidth": false,//是否监听宽度变化
                            "columnDefs": columnDefs                           
                        })
                        table.clear().rows.add(data).draw();
                    } else {
                        table.clear().rows.add(data).draw();
                    }
                } else {

                    // 重置
                    if (table) { table.destroy() }
                    $("button[data-name=edit]").hide()
                    $("button[data-name=del]").hide()
                    $('#detailLayer0').html("")
                    $('#detailLayer').html("")
                    $("#table").html('<tbody><tr role="row" class="odd"><td>暂无数据</td></tr></tbody>');
                    detailLayer0 = null;
                    detailLayer = null;
                    table = null
                    // 新建设置弹窗
                    detailLayer0 = $('#detailLayer0').detailLayer({
                        layerTitle: "首次设置",
                        layerArea: ["100%", "100%"],
                        conditions: [
                            {
                                type: "option",//
                                name: "option",//
                                label: "属性选项设置",//
                                minOptionNum: 1,
                                must: true,
                            }
                        ],
                        onAdd: function (obj, _params) {
                            if (_params) {
                                var initConditions = []
                                $.each(_params.option, function (i, v) {
                                    if (v.content) {
                                        initConditions.push(v.content)
                                    }
                                })
                                if (initConditions.length > 5) {
                                    layer.alert("最多输入5个选项", { icon: 0, }, function (index) {
                                        layer.close(index);
                                    });
                                } else {
                                    var conditions = [
                                        {
                                            type: "hidden",//五种形式：text,select,date,hidden,textarea,
                                            name: "voteId",//输入字段名
                                            defaultValue: l.getUrlParam("id")
                                        },
                                        {
                                            type: "hidden",//五种形式：text,select,date,hidden,textarea,
                                            name: "candidateId",//输入字段名
                                        },
                                        {
                                            type: "number",//
                                            name: "candidateOrder",//
                                            label: "排序",//
                                            defaultValue: 1,
                                            placeholder: "请输入排序",
                                            must: true
                                        },
                                    ];
                                    var labelObj = {};
                                    $.each(initConditions, function (i, label) {
                                        var name = ""
                                        switch (i) {
                                            case 0:
                                                labelObj["titleOne"] = label;
                                                name = "contentOne";
                                                break;
                                            case 1:
                                                labelObj["titleTwo"] = label;
                                                name = "contentTwo"
                                                break;
                                            case 2:
                                                labelObj["titleThree"] = label;
                                                name = "contentThree"
                                                break;
                                            case 3:
                                                labelObj["titleFour"] = label;
                                                name = "contentFour"
                                                break;
                                            case 4:
                                                labelObj["titleFive"] = label;
                                                name = "contentFive"
                                                break;
                                        }
                                        conditions.push({
                                            type: "text",//
                                            name: name,//    
                                            label: label,
                                        })
                                    })

                                    conditions.push({
                                        type: "upload",//
                                        name: "imageList",//
                                        label: "图片",//
                                        btnName: "添加图片",
										projectName: "zj-vote",
                                        fileType: ["jpg", "jpeg", "png", "gif"],
                                    })
                                    detailLayer = $('#detailLayer').detailLayer({
                                        layerArea: ["100%", "100%"],
                                        conditions: conditions,
                                        onSave: function (obj, _params) {
                                            if (_params.imageList.length > 1) {
                                                layer.alert("只能上传一张图片", { icon: 0 }, function (index) {
                                                    layer.close(index);
                                                });
                                            } else {
                                                var newParams = {
                                                    voteId: _params.voteId,
                                                    candidateOrder: _params.candidateOrder,
                                                    candidateId: _params.candidateId,
                                                    imageList: _params.imageList || [],
                                                    contentOne: _params.contentOne || "",
                                                    contentTwo: _params.contentTwo || "",
                                                    contentThree: _params.contentThree || "",
                                                    contentFour: _params.contentFour || "",
                                                    contentFive: _params.contentFive || "",
                                                }
                                                l.ajax('updateVoteCandidate', newParams, function (data) {
                                                    pager.page('remote');
                                                    obj.close()
                                                })
                                            }
                                        },
                                        onAdd: function (obj, _params) {
                                            if (_params.imageList.length > 1) {
                                                layer.alert("只能上传一张图片", { icon: 0 }, function (index) {
                                                    layer.close(index);
                                                });
                                            } else {
                                                var newParams = {
                                                    voteId: _params.voteId,
                                                    candidateOrder: _params.candidateOrder,
                                                    candidateId: _params.candidateId,
                                                    imageList: _params.imageList || [],

                                                    titleOne: labelObj.titleOne || "",
                                                    titleTwo: labelObj.titleTwo || "",
                                                    titleThree: labelObj.titleThree || "",
                                                    titleFour: labelObj.titleFour || "",
                                                    titleFive: labelObj.titleFive || "",

                                                    contentOne: _params.contentOne || "",
                                                    contentTwo: _params.contentTwo || "",
                                                    contentThree: _params.contentThree || "",
                                                    contentFour: _params.contentFour || "",
                                                    contentFive: _params.contentFive || "",
                                                }
                                                l.ajax('addVoteCandidate', newParams, function (data) {
                                                    pager.page('remote');
                                                    obj.close()
                                                })
                                            }
                                        }
                                    });
                                    detailLayer0.close()
                                    detailLayer.open()
                                }
                            }
                            // pager.page('remote');
                            // obj.close()
                        }
                    });
                }
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});
$('#form .form-label').css({
    color: '#0099ff'
})
$('#form .form-label').click(function () {
    $this = $(this)
    layer.prompt({ title: '输入新标题', formType: 0 }, function (text, index) {
        $this.text(text + ":");
        formBable.title = text;
        layer.close(index);
    });
})



$("body").on("click", "button", function () {

    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            if (detailLayer) {
                detailLayer.open()
            } else {
                detailLayer0.open();
            }
            break;
        case "edit":
            var checkedData = l.getTableCheckedData(table);
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
            var checkedData = l.getTableCheckedData(table);
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                l.delTableRow("candidateId", 'candidateList', 'batchDeleteVoteCandidate', checkedData, function (data) {
                    pager.page('remote');
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
