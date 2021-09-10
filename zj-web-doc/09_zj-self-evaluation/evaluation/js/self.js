var evaluationType = l.getUrlParam("evaluationType") || "0";
var userId = l.getUrlParam("code") || "";
var bmId = l.getUrlParam("bmId") || "";
var assessmentId = l.getUrlParam("assessmentId") || "";
var localDB = new LocalDB();
Apih5.setCookie("code",userId,30);

var isAuditor; //1 是审核人    0 不是审核人
var opinion; //审核意见
var isAudited; //1 是已审核  0 是未审核

var detailLayer = $("#detailLayer").detailLayer({
    layerArea: ["100%","100%"],
    conditions: [
        {
            type: "hidden", //五种形式：text,select,date,hidden,textarea,
            name: "evaluationId" //输入字段名
        },
        {
            type: "upload", //
            name: "zjZpAccessoryList", //
            projectName: "zjSelfEvaluation",
            label: "支撑资料", //
            btnName: "添加资料",
            fileType: [
                "jpg",
                "jpeg",
                "png",
                "gif",
                "txt",
                "pdf",
                "doc",
                "docx",
                "xls",
                "xlsx",
                "ppt",
                "pptx",
                "rar",
                "zip"
            ]
        }
    ],
    onSave: function (obj,_params) {
        obj.close();
        var fileNames = renderSelfFileString(
            _params.evaluationId,
            _params.zjZpAccessoryList
        );
        $("tbody")
            .find(
                "tr[data-evaluationId=" +
                _params.evaluationId +
                "] td[data-col=6]"
            )
            .removeAttr("class")
            .html(fileNames);
    },
    onAdd: function (obj,_params) {
        obj.close();
        var fileNames = renderSelfFileString(
            _params.evaluationId,
            _params.zjZpAccessoryList
        );
        $("tbody")
            .find(
                "tr[data-evaluationId=" +
                _params.evaluationId +
                "] td[data-col=6]"
            )
            .html(fileNames);
    }
});
//选择审批人弹出
var detailLayerBySelectLeader = $("#detailLayerBySelectLeader").detailLayer({
    layerArea: ["50%","250px"],
    conditions: [
        {
            type: "select",
            name: "auditorId",
            label: "审核者",
            ajax: {
                api: "getZjZpEvaluationAuditorSelect",
                valueName: "auditorId",
                textName: "auditorName"
            },
            must: true
        }
    ],
    onSave: function (obj,_params) {
        obj.close();
        submitFn(_params);
    },
    onAdd: function (obj,_params) {
        obj.close();
        submitFn(_params);
    }
});
setTimeout(function () {
    changeEvaluationType(getUserType);
},100);
function getUserType() {
    l.ajax(
        "isOrNotZjZpEvaluationAuditor",
        { assessmentId: assessmentId },
        function (data,message,success) {
            if (success) {
                isAuditor = data.isAuditor;
                opinion = data.opinion;
                isAudited = data.isAudited;
                var apiName = "getEvaluationListByUserId";
                if (isAuditor == "1") {
                    apiName = "getZjZpEvaluationListByAuditor"; //审核者
                }
                refresh(apiName);
            } else {
                layer.alert(message);
            }
        }
    );
}
function refresh(apiName) {
    l.ajax(
        "IsOrNotEvaluationByUserId",
        {
            evaluationType: evaluationType,
            assessmentId: assessmentId
        },
        function (d,m,s) {
            if (s) {
                l.ajax(
                    apiName,
                    {
                        evaluationType: evaluationType,
                        assessmentId: assessmentId
                    },
                    function (data,message,success) {
                        if (success) {
                            for (var i = 0; i < data.length; i++) {
                                data[i].zjZpAccessoryList =
                                    data[i].zjZpAccessoryList || [];
                                for (
                                    var j = 0;
                                    j < data[i].zjZpAccessoryList.length;
                                    j++
                                ) {
                                    data[i].zjZpAccessoryList[j] = {
                                        fileName:
                                            data[i].zjZpAccessoryList[j]
                                                .accessoryName,
                                        fileUrl:
                                            data[i].zjZpAccessoryList[j]
                                                .accessoryAddress
                                    };
                                }
                            }
                            render(data,d.isFinish === "1");
                        } else {
                            changeEvaluationType(getUserType);
                        }
                    }
                );
            }
        }
    );
}
function render(data,disabled) {
    var ids = [],
        domDatas = [], //dom需要的数据
        domString = "", //dom字符串
        sumstandardDetail = 0, //标准合计
        sumselfDetail = 0, //自评合计
        sumreviewDetail = 0, //复评合计
        tableString = "",
        theadString = "",
        tbodyString = "",
        tfootString = "";
    $.each(data,function (index,item) {
        ids.push({
            index: index,
            evaluationId: item.evaluationId,
            detailId: item.detailId
        });
        item.index = index;
        // if (domDatas[item.moduleType]) {
        if (domDatas[index]) {
            domDatas[index].data.push(item);
            domDatas[index].substandardDetail =
                domDatas[index].substandardDetail +
                toNumber(item.standardDetail);
            domDatas[index].subselfDetail =
                domDatas[index].subselfDetail +
                toNumber(item.selfDetail);
            domDatas[index].subreviewDetail =
                domDatas[index].subreviewDetail +
                toNumber(item.reviewDetail);
        } else {
            // domDatas[item.moduleType] = {
            domDatas[index] = {
                moduleTypeName: item.moduleTypeName,
                substandardDetail: toNumber(item.standardDetail),
                subselfDetail: toNumber(item.selfDetail),
                subreviewDetail: toNumber(item.reviewDetail),
                data: [item]
            };
        }
    });
    // console.log(domDatas)
    localDB.setItem("ids",JSON.stringify(ids));
    //thead
    theadString = '<thead class="text-c">';
    theadString = theadString + "<tr>";
    theadString = theadString + '<th rowspan="2" width="24px">序号</th>';
    theadString = theadString + '<th rowspan="2" width="8%">工作模块</th>';
    theadString = theadString + '<th rowspan="2" width="10%">自评项</th>';
    theadString = theadString + '<th rowspan="2" width="10%">自评标准</th>';
    theadString = theadString + '<th colspan="2">标准分</th>';
    theadString = theadString + '<th rowspan="2" width="8%">自评支撑材料</th>';
    theadString = theadString + '<th rowspan="2">自评说明</th>';
    theadString = theadString + '<th colspan="2">自评分</th>';
    //复评部分start
    // theadString = theadString + '<th rowspan="2">复评说明</th>'
    // theadString = theadString + '<th colspan="2">复评分</th>'
    //复评部分end
    theadString = theadString + "</tr>";
    theadString = theadString + "<tr>";
    theadString = theadString + '<th width="32">明细</th>';
    theadString = theadString + '<th width="32">小计</th>';
    theadString = theadString + '<th width="32">明细</th>';
    theadString = theadString + '<th width="32">小计</th>';
    //复评部分start
    // theadString = theadString + '<th width="32">明细</th>'
    // theadString = theadString + '<th width="32">小计</th>'
    //复评部分end
    theadString = theadString + "</tr>";
    theadString = theadString + "</thead>";
    //tbody
    tbodyString = "<tbody>";
    $.each(domDatas,function (groupIndex,group) {
        if (!group) {
            return;
        }
        var groupString = "",
            moduleTypeName = group.moduleTypeName,
            rowspan = group.data.length,
            substandardDetail = group.substandardDetail, //标准小计
            subselfDetail = group.subselfDetail, //自评小计
            subreviewDetail = group.subreviewDetail; //复评小计
        sumstandardDetail = sumstandardDetail + substandardDetail; //标准合计
        sumselfDetail = sumselfDetail + subselfDetail; //自评合计
        sumreviewDetail = sumreviewDetail + subreviewDetail; //复评合计

        $.each(group.data,function (index,row) {
            var rowIndex = row.index;
            var evaluationId = row.evaluationId;
            var selfComment = row.selfComment;
            var selfDetail = toNumber(row.selfDetail);
            var reviewComment = row.reviewComment;
            var reviewDetail = toNumber(row.reviewDetail);
            var fileNames = renderSelfFileString(
                evaluationId,
                row.zjZpAccessoryList,
                disabled
            );
            localDB.setItem(evaluationId + "_selfComment",selfComment);
            localDB.setItem(evaluationId + "_selfDetail",selfDetail);
            localDB.setItem(evaluationId + "_reviewComment",reviewComment);
            localDB.setItem(evaluationId + "_reviewDetail",reviewDetail);
            // tdString0
            var tdString0 =
                '<td data-col="0" data-row="' +
                rowIndex +
                '">' +
                (rowIndex + 1) +
                "</td>";
            // tdString1
            var tdString1 =
                index !== 0
                    ? ""
                    : '<td data-col="1" data-row="' +
                    toRows(rowIndex,rowspan) +
                    '" rowspan="' +
                    rowspan +
                    '">' +
                    moduleTypeName +
                    "</td>";
            // tdString2
            var tdString2 =
                '<td data-col="2" data-row="' +
                rowIndex +
                '">' +
                row.content +
                "</td>";
            // tdString3
            var tdString3 =
                '<td data-col="3" data-row="' +
                rowIndex +
                '">' +
                row.standard +
                "</td>";
            // tdString4
            var tdString4 =
                '<td data-col="4" data-row="' +
                rowIndex +
                '" class="text-c">' +
                toNumber(row.standardDetail) +
                "</td>";
            // tdString5
            var tdString5 =
                index !== 0
                    ? ""
                    : '<td data-col="5" data-row="' +
                    toRows(rowIndex,rowspan) +
                    '" rowspan="' +
                    rowspan +
                    '" class="text-c">' +
                    substandardDetail +
                    "</td>";
            // tdString6
            var tdString6 =
                '<td data-col="6" data-evaluationId="' +
                evaluationId +
                '" data-row="' +
                rowIndex +
                '">' +
                fileNames +
                "</td>";
            // tdString7
            var tdString7_con = disabled
                ? '<textarea disabled class="textarea radius" data-localDB="' +
                evaluationId +
                '_selfComment">' +
                selfComment +
                "</textarea>"
                : '<textarea class="textarea radius" data-localDB="' +
                evaluationId +
                '_selfComment">' +
                selfComment +
                "</textarea>";
            var tdString7 =
                '<td data-col="7" data-row="' +
                rowIndex +
                '" class="text-c">' +
                tdString7_con +
                "</td>";
            // tdString8
            var tdString8_con = disabled
                ? '<input disabled data-to="' +
                (groupIndex + "_" + 9) +
                '" data-localDB="' +
                evaluationId +
                '_selfDetail" min="0" max="' +
                toNumber(row.standardDetail) +
                '" value="' +
                selfDetail +
                '"  onKeyUp="Apih5.onlyInt(this, event)" onBlur="Apih5.onlyInt(this, event)" onKeyPress="return Apih5.onlyInt(this, event)" onpaste="return false"  type="text" class="input-text">'
                : '<input data-to="' +
                (groupIndex + "_" + 9) +
                '" data-localDB="' +
                evaluationId +
                '_selfDetail" min="0" max="' +
                toNumber(row.standardDetail) +
                '" value="' +
                selfDetail +
                '"  onKeyUp="Apih5.onlyInt(this, event)" onBlur="Apih5.onlyInt(this, event)" onKeyPress="return Apih5.onlyInt(this, event)" onpaste="return false"  type="text" class="input-text">';
            var tdString8 =
                '<td data-col="8" data-row="' +
                rowIndex +
                '" class="text-c">' +
                tdString8_con +
                "</td>";
            // tdString9
            var tdString9 =
                index !== 0
                    ? ""
                    : '<td data-col="9" data-row="' +
                    toRows(rowIndex,rowspan) +
                    '" rowspan="' +
                    rowspan +
                    '"  data-source="' +
                    (groupIndex + "_" + 9) +
                    '" data-to="1" class="text-c">' +
                    subselfDetail +
                    "</td>";
            // tdString10
            var tdString10_con =
                '<textarea class="textarea radius" data-localDB="' +
                evaluationId +
                '_reviewComment">' +
                reviewComment +
                "</textarea>";
            var tdString10 =
                '<td data-col="10" data-row="' +
                rowIndex +
                '" class="text-c">' +
                tdString10_con +
                "</td>";
            // tdString8
            var tdString11_con =
                '<input data-to="' +
                (groupIndex + "_" + 12) +
                '" data-localDB="' +
                evaluationId +
                '_reviewDetail" min="0" max="' +
                toNumber(row.standardDetail) +
                '" value="' +
                reviewDetail +
                '"  onKeyUp="Apih5.onlyInt(this, event)" onBlur="Apih5.onlyInt(this, event)" onKeyPress="return Apih5.onlyInt(this, event)" onpaste="return false"  type="text" class="input-text">';
            var tdString11 =
                '<td data-col="11" data-row="' +
                rowIndex +
                '" class="text-c">' +
                tdString11_con +
                "</td>";
            // tdString9
            var tdString12 =
                index !== 0
                    ? ""
                    : '<td data-col="12" data-row="' +
                    toRows(rowIndex,rowspan) +
                    '" rowspan="' +
                    rowspan +
                    '"  data-source="' +
                    (groupIndex + "_" + 12) +
                    '" data-to="2" class="text-c">' +
                    subreviewDetail +
                    "</td>";
            var trString =
                '<tr data-evaluationId="' +
                evaluationId +
                '">' +
                tdString0 +
                tdString1 +
                tdString2 +
                tdString3 +
                tdString4 +
                tdString5 +
                tdString6 +
                tdString7 +
                tdString8 +
                tdString9 +
                //复评部分start
                // + tdString10
                // + tdString11
                // + tdString12
                //复评部分end
                "</tr>";
            groupString = groupString + trString;
        });
        tbodyString = tbodyString + groupString;
    });
    tbodyString = tbodyString + "</tbody >";
    //tfoot
    tfootString = '<tfoot style="font-weight:bold">';
    tfootString = tfootString + "<tr>";
    tfootString = tfootString + '<td colspan="4">合计</td>';
    tfootString =
        tfootString + '<td data-source="0">' + sumstandardDetail + "</td>";
    tfootString =
        tfootString + '<td data-source="0">' + sumstandardDetail + "</td>";
    tfootString = tfootString + "<td></td>";
    tfootString = tfootString + "<td></td>";
    tfootString =
        tfootString + '<td data-source="1">' + sumselfDetail + "</td>";
    tfootString =
        tfootString + '<td data-source="1">' + sumselfDetail + "</td>";
    //复评部分start
    // tfootString = tfootString + '<td></td>'
    // tfootString = tfootString + '<td data-source="2">' + sumreviewDetail + '</td>'
    // tfootString = tfootString + '<td data-source="2">' + sumreviewDetail + '</td>'
    //复评部分end
    tfootString = tfootString + "</tr>";
    tfootString = tfootString + "</tfoot>";
    //table
    tableString =
        '<table class="table table-border table-bg table-bordered radius">' +
        theadString +
        tbodyString +
        tfootString +
        "</table >";

    var otherString =
        '<h3 style="text-align:center">中交一公局信息化工作考核表（满分1000分）</h3>';
    otherString =
        otherString +
        '<p style="color:#333;margin-top:15px;text-indent: 32px;"> 考评系统将于2020年12月7日9:00准时开放，12月9日17:00准时关闭，请各单位、各直属项目及时登录系统，完成自评及材料提交。联系人：郭爽，电话：010-65168090 <span style="font-weight:bold"></span>。 </p>';

    var submitString =
        '<input style="margin-top:20px" class="btn btn-primary size-L radius" id="submit" type="button" value="提交">';
    var saveString =
        '<input style="margin-top:20px;margin-left:20px;" class="btn btn-primary size-L radius" id="save" type="button" value="保存">';
    var contralString = disabled
        ? ""
        : '<div style="text-align:right">' +
        submitString +
        saveString +
        "</div>";

    if (isAuditor == "1") {
        //是审核者  需要意见框 按钮为 同意驳回
        var passString =
            '<input style="margin-top:20px" class="btn btn-primary size-L radius" id="pass" type="button" value="同意">';
        var noString =
            '<input style="margin-top:20px;margin-left:20px;" class="btn btn-primary size-L radius" id="noPass" type="button" value="驳回">';
        var opttionContent =
            '<div style="text-align:right;"><div style="padding-right:50%;margin-top:16px;">审核者意见：</div>' +
            "<textarea  placeholder='请输入您的意见' " +
            (isAudited == "1" ? "disabled" : "") +
            " id='opttionContent' style='width:50%;margin:20px 0px;padding:10px;' rows='5'></textarea>" +
            "</div>";
        if (isAudited == "0") {
            contralString =
                opttionContent +
                '<div style="text-align:right">' +
                passString +
                noString +
                "</div>";
        } else {
            //已审核
            contralString = opttionContent;
        }
    }
    domString = otherString + domString + tableString + contralString;
    var $dom = $(domString);

    $("#root").html($dom);
    $("#opttionContent").val(opinion);
}

$("body").on("blur","#root tbody textarea",function () {
    var dataLoaclDB = $(this).attr("data-localDB");
    localDB.setItem(dataLoaclDB,$(this).val());
});
$("body").on("blur","#root tbody input",function () {
    var me = this;
    setTimeout(function () {
        var dataLoaclDB = $(me).attr("data-localDB");
        localDB.setItem(dataLoaclDB,toNumber($(me).val()));
        var dataTo = $(me).attr("data-to");
        var arr = $("body").find("#root tbody input[data-to=" + dataTo + "]");
        var newData = 0;
        for (var i = 0; i < arr.length; i++) {
            newData = newData + toNumber($(arr[i]).val());
        }
        $("body")
            .find("#root tbody td[data-source=" + dataTo + "]")
            .text(newData);
        var groupDataTo = $("body")
            .find("#root tbody td[data-source=" + dataTo + "]")
            .attr("data-to");
        if (groupDataTo) {
            var groupArr = $("body").find(
                "#root tbody td[data-to=" + groupDataTo + "]"
            );
            var newGroupData = 0;
            for (var i = 0; i < groupArr.length; i++) {
                newGroupData = newGroupData + toNumber($(groupArr[i]).text());
            }
            $("body")
                .find("#root tfoot td[data-source=" + groupDataTo + "]")
                .text(newGroupData);
        }
    },100);
});

function getzjZpUserDetailList() {
    var ids = JSON.parse(localDB.getItem("ids"));
    var zjZpUserDetailList = [];
    $.each(ids,function (i,v) {
        var evaluationId = v.evaluationId;
        var detailId = v.detailId;
        var _zjZpAccessoryList = JSON.parse(
            localDB.getItem(evaluationId + "_zjZpAccessoryList")
        );
        for (var j = 0; j < _zjZpAccessoryList.length; j++) {
            _zjZpAccessoryList[j] = {
                accessoryName: _zjZpAccessoryList[j].fileName,
                accessoryAddress: _zjZpAccessoryList[j].fileUrl
            };
        }
        zjZpUserDetailList.push({
            detailId: detailId,
            evaluationId: evaluationId,
            selfDetail: localDB.getItem(evaluationId + "_selfDetail"),
            selfComment: localDB.getItem(evaluationId + "_selfComment"),
            zjZpAccessoryList: _zjZpAccessoryList
        });
    });
    return zjZpUserDetailList;
}

function submitFn(params) {
    var auditorId = params.auditorId;

    layer.confirm(
        "提交后不可修改,只可提交一次，您确定要提交么？",
        {
            btn: ["确定","取消"] //按钮
        },
        function () {
            var body = {
                evaluationType: evaluationType,
                zjZpUserDetailList: getzjZpUserDetailList(),
                auditorId: auditorId,
                assessmentId: assessmentId
            };
            // console.log(body);
            // return;
            l.ajax("batchAddZjZpUserDetail",body,function (
                data,
                message,
                success
            ) {
                if (success) {
                    var successMsg = "提交成功！";
                    layer.alert(successMsg,{ icon: 0 },function (index) {
                        layer.close(index);
                        refresh("getEvaluationListByUserId");
                    });
                }
            });
        },
        function (index) {
            layer.close(index);
        }
    );
}

$("body").on("click","#submit",function () {
    var ids = JSON.parse(localDB.getItem("ids"));
    // var zjZpUserDetailList = [];
    var dangers = [];
    $.each(ids,function (i,v) {
        var evaluationId = v.evaluationId;
        // var detailId = v.detailId;
        var _zjZpAccessoryList = JSON.parse(
            localDB.getItem(evaluationId + "_zjZpAccessoryList")
        );
        if (_zjZpAccessoryList.length === 0) {
            dangers.push(evaluationId);
        }
        // for (var j = 0; j < _zjZpAccessoryList.length; j++) {
        //     _zjZpAccessoryList[j] = {
        //         accessoryName: _zjZpAccessoryList[j].fileName,
        //         accessoryAddress: _zjZpAccessoryList[j].fileUrl
        //     };
        // }
        // zjZpUserDetailList.push({
        //     detailId: detailId,
        //     evaluationId: evaluationId,
        //     selfDetail: localDB.getItem(evaluationId + "_selfDetail"),
        //     selfComment: localDB.getItem(evaluationId + "_selfComment"),
        //     zjZpAccessoryList: _zjZpAccessoryList
        // });
    });
    if (dangers.length > 0) {
        layer.alert("每一项都必须上传支撑材料",{ icon: 0 },function (index) {
            layer.close(index);
            $.each(dangers,function (i,v) {
                $("body")
                    .find("#root tbody td[data-evaluationId=" + v + "]")
                    .attr("class","danger");
            });
        });
    } else {
        //不进行提交 而是去选人
        var data = {
            // zjZpUserDetailList: zjZpUserDetailList
        };
        detailLayerBySelectLeader.open(data);
    }
});

$("body").on("click","#save",function () {
    var ids = JSON.parse(localDB.getItem("ids"));
    var zjZpUserDetailList = [];
    $.each(ids,function (i,v) {
        var detailId = v.detailId;
        var evaluationId = v.evaluationId;
        var _zjZpAccessoryList = JSON.parse(
            localDB.getItem(evaluationId + "_zjZpAccessoryList")
        );
        for (var j = 0; j < _zjZpAccessoryList.length; j++) {
            _zjZpAccessoryList[j] = {
                accessoryName: _zjZpAccessoryList[j].fileName,
                accessoryAddress:
                    l.domain +
                    _zjZpAccessoryList[j].fileUrl.replace(l.domain,"")
            };
        }
        zjZpUserDetailList.push({
            evaluationId: evaluationId,
            detailId: detailId,
            selfDetail: localDB.getItem(evaluationId + "_selfDetail"),
            selfComment: localDB.getItem(evaluationId + "_selfComment"),
            zjZpAccessoryList: _zjZpAccessoryList
        });
    });

    layer.confirm(
        "保存仅为存储数据不做提交，您确定要保存么？",
        {
            btn: ["确定","取消"] //按钮
        },
        function () {
            //模拟附件
            // zjZpUserDetailList = $.each(zjZpUserDetailList, function(
            //     index,
            //     item
            // ) {
            //     item.selfComment = Math.random();
            //     item.zjZpAccessoryList = [
            //         {
            //             accessoryName: "全部一样哟！！！",
            //             accessoryAddress:
            //                 "http://cx.apih5.com/web/upload/zjSelfEvaluation/1D0BQS4UR01P9B01A8C00000504BB0C7.png"
            //         }
            //     ];
            // });
            // console.log(zjZpUserDetailList);
            // return;

            var body = {
                evaluationType: evaluationType,
                zjZpUserDetailList: zjZpUserDetailList,
                assessmentId: assessmentId
            };

            l.ajax("batchAddUserDetailToDraft",body,function (
                data,
                message,
                success
            ) {
                if (success) {
                    var successMsg = "保存成功！";
                    layer.alert(successMsg,{ icon: 0 },function (index) {
                        layer.close(index);
                        refresh("getEvaluationListByUserId");
                    });
                }
            });
        },
        function (index) {
            layer.close(index);
        }
    );
});

//同意
$("body").on("click","#pass",function () {
    layer.confirm(
        "确定同意吗？",
        {
            btn: ["确定","取消"] //按钮
        },
        function () {
            var opttionContent = $("#opttionContent").val();
            var body = {
                opinion: opttionContent,
                zjZpUserDetailList: getzjZpUserDetailList(),
                assessmentId: assessmentId
            };
            l.ajax("auditorAgreeZjZpUserDetail",body,function (
                data,
                message,
                success
            ) {
                if (success) {
                    layer.alert(message,{ icon: 0 },function (index) {
                        layer.close(index);
                        // refresh("getZjZpEvaluationListByAuditor");
                        getUserType();
                    });
                }
            });
        },
        function (index) {
            layer.close(index);
        }
    );
});

//驳回
$("body").on("click","#noPass",function () {
    layer.confirm(
        "确定驳回吗？",
        {
            btn: ["确定","取消"] //按钮
        },
        function () {
            var opttionContent = $("#opttionContent").val();
            var body = {
                opinion: opttionContent,
                zjZpUserDetailList: getzjZpUserDetailList(),
                assessmentId: assessmentId
            };
            l.ajax("auditorRejectZjZpUserDetail",body,function (
                data,
                message,
                success
            ) {
                if (success) {
                    layer.alert(message,{ icon: 0 },function (index) {
                        layer.close(index);
                        getUserType();
                        // refresh("getZjZpEvaluationListByAuditor");
                    });
                }
            });
        },
        function (index) {
            layer.close(index);
        }
    );
});

function openImgUpload(evaluationId) {
    var data = {
        evaluationId: evaluationId,
        zjZpAccessoryList: JSON.parse(
            localDB.getItem(evaluationId + "_zjZpAccessoryList")
        )
    };
    detailLayer.open(data);
}
function renderSelfFileString(evaluationId,zjZpAccessoryList,disabled) {
    var fileNames = "",
        zjZpAccessoryList = zjZpAccessoryList || [];
    $.each(zjZpAccessoryList,function (i,d) {
        fileNames =
            fileNames +
            (i > 0 ? "<br/>" : "") +
            '<a target="_blank" href="' +
            l.domain +
            d.fileUrl.replace(l.domain,"") +
            '">' +
            d.fileName +
            "</a>";
    });
    localDB.setItem(
        evaluationId + "_zjZpAccessoryList",
        JSON.stringify(zjZpAccessoryList)
    );
    if (disabled) {
        if (zjZpAccessoryList.length === 0) {
            fileNames = "无上传材料";
        }
    } else {
        fileNames =
            fileNames +
            (zjZpAccessoryList.length > 0 ? "<br/>" : "") +
            '<a data-name="file" href="javascript:void(0);" style="text-decoration: underline;color:#06c" onClick="openImgUpload(\'' +
            evaluationId +
            "')\">点击上传/编辑</a>";
    }
    return fileNames;
}

function changeEvaluationType(cb) {
    var selectDom =
        "<li class='selectDomLi' data-id='0' style='cursor:pointer; margin-bottom:10px;border-bottom:1px solid #ccc;padding-bottom:10px;'>各单位</li>" +
        "<li class='selectDomLi' data-id='1' style='cursor:pointer; margin-bottom:10px;border-bottom:1px solid #ccc;padding-bottom:10px;'>总承包部、直属项目部等</li>" +
        "<li class='selectDomLi' data-id='2' style='cursor:pointer'>项目公司</li>";
    var idex = layer.open({
        title: "请选择公司类型",
        type: 1,
        skin: "layui-layer-demo", //样式类名
        closeBtn: 0, //不显示关闭按钮
        anim: 2,
        content:
            "<ul class='selectDom' style='padding:26px'>" + selectDom + "</ul>"
    });
    $(".selectDomLi").click(function () {
        evaluationType = $(this).attr("data-id");
        cb();
        layer.close(idex);
    });
}
