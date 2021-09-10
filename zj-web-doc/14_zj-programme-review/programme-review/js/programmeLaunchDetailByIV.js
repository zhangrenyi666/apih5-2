var launchId = l.getUrlParam('id') || "";
var detailForm = $('#detailForm').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "launchId",//输入字段名
            label: "方案审批id",
            immutableAdd: true
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
            immutableAdd: true
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "projectName",//输入字段名
            label: "项目名称",
            must: true,
            immutableAdd: true
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "schemeNumber",//输入字段名
            label: "方案编号",
            must: true,
            immutableAdd: true
        },
        {
            type: "text",
            name: "schemeName",
            label: "方案名称",
            immutableAdd: true,
            lineNum: 4
        },
        {
            type: "text",
            name: "engineeringType",
            label: "工程类别",
            immutableAdd: true,
            lineNum: 4
        },
        {
            type: "text",
            name: "province",
            label: "所属省份",
            immutableAdd: true,
            lineNum: 5
        },
        {
            type: "date",//text,select,date,
            name: "implementationTime",
            id: "implementationTime",
            label: "方案计划实施时间",
            immutableAdd: true,
            lineNum: 5
        },
        {
            type: "date",//text,select,date,
            name: "projectStartDate",
            id: "projectStartDate",
            label: "项目开工日期",
            immutableAdd: true,
            lineNum: 6
        },
        {
            type: "date",//text,select,date,
            name: "projectEndDate",
            id: "projectEndDate",
            label: "项目竣工日期",
            immutableAdd: true,
            lineNum: 6
        },
        {
            type: "text",
            name: "projectGeneralUser",
            label: "项目总工",
            immutableAdd: true,
            lineNum: 7
        },
        {
            type: "text",
            name: "projectGeneralUserTel",
            label: "总工联系方式",
            must: true,
            lineNum: 7
        },
        {
            type: "text",
            name: "programmingPerson",
            label: "方案编制人",
            lineNum: 8,
            immutableAdd: true			
        },
        {
            type: "text",
            name: "programmingPersonTel",
            label: "编制人联系方式",
            lineNum: 8,
            immutableAdd: true			
        },
        {
            type: "text",//
            name: "projectContractAmount",//
            label: "项目合同金额",//
            immutableAdd: true,
        },
        {
            type: "textarea",//
            name: "hierarchyDescription",//
            label: "技术安全等级划分说明",//
            immutableAdd: true,
        },
		{
            type: "upload",//
            name: "documentText",//
            label: "公文正文",//
            btnName: "选择",
			projectName:"zj-programme-review",
            immutableAdd: true			
        },
		{
            type: "upload",//
            name: "documentAccessory",//
            label: "公文附件",//
            btnName: "选择",
			projectName:"zj-programme-review",
            immutableAdd: true			
        },		
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
			immutableAdd: true
        },
        {
            type: "textarea",//
            name: "programmingPreliminaryTrial",//
            label: "方案初评情况",//
            immutableAdd: true
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "unitName",//输入字段名
            label: "实施单位",
            immutableAdd: true			
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "detailedListId",//输入字段名
            label: "方案id",
            must: true,
            immutableAdd: true
        }
    ],
    onSave: function (obj, _params) {
        if (launchId) {
            _params.launchId = launchId
            l.ajax("updateZjPrProgrammeLaunchByIV", _params, function (data, message, success) {
                if (success) {
                    layer.alert(message, { icon: 0, }, function (index) {
                        parent.pager.page('remote')
                        layer.close(index);
                        obj.close()
                    });
                }
            })
        } else {
            l.ajax("addZjPrProgrammeLaunchByIV", _params, function (data, message, success) {
                if (success) {
                    launchId = data.launchId
                    layer.alert(message, { icon: 0, }, function (index) {
                        parent.pager.page('remote')
                        layer.close(index);
                        obj.close()
                    });
                }
            })
        }
    }
})

detailForm.close = function () {
    parent.layer.close(parent.myOpenLayer)
}



if (launchId) {
    l.ajax("getzjPrProgrammeLaunchDetailByIV", { launchId: launchId }, function (data, message, success) {
        if (success) {
            detailForm.find("form").prepend($('<div class="row cl"><label class="form-label col-2 f-l"><i style="color:red;">* </i>项目名称：</label><div class="col-4 f-l"><input type="text" class="input-text SearchSelect" onfocus="SearchSelect({ apiName: \'getZjSchemeConfirmationListBySelect\', otherKey: \'projectState\' })" value="' + data.projectName + '" name="projectName" disabled="disabled"></div><label class="form-label col-2 f-l"><i style="color:red;">* </i>方案编号：</label><div class="col-4 f-l"><input type="text" class="input-text SearchSelect" onfocus="SearchSelect({ apiName: \'getZjSchemeDetailedListSelectAllList\', otherKey: \'recordid\' })" value="' + data.schemeNumber + '" name="schemeNumber" disabled="disabled"></div></div>'))
            detailForm.setOpenData(data)
        } else {
            detailForm.setOpenData({ memberList: { oaMemberList: [] } })
        }
    })
} else {
    detailForm.find("form").prepend($('<div class="row cl"><label class="form-label col-2 f-l"><i style="color:red;">* </i>项目名称：</label><div class="col-4 f-l"><input type="text" class="input-text SearchSelect" onfocus="SearchSelect({ apiName: \'getZjSchemeConfirmationListBySelect\', otherKey: \'projectState\' })" value="" name="projectName"></div><label class="form-label col-2 f-l"><i style="color:red;">* </i>方案编号：</label><div class="col-4 f-l"><input type="text" class="input-text SearchSelect" onfocus="SearchSelect({ apiName: \'getZjSchemeDetailedListSelectAllList\', otherKey: \'recordid\' })" value="" name="schemeNumber"></div></div>'))
    detailForm.setOpenData({ memberList: { oaMemberList: [] } })
}



var searchSelectDiv;
var curELe;
var searchSelectDatas = {}
var searchSelectErr = false
/**
 * addEventlistener兼容函数
 * ie9以上正常使用addEventlistener函数
 * ie7、ie8用传递的function的function.prototype储存经过处理的事件
 * function.prototype["_" + type]：记录处理后的信息
 * function.prototype["_" + type]._function <function>：经过处理的事件
 * function.prototype["_" + type]._element  <array>   ：已经绑定的dom
 */
var ua = navigator.userAgent.toLowerCase();
var isIE = /msie/.test(ua);
/*** addEventlistener ***/
var addListener = (function () {
    if (document.addEventListener) {
        /* ie9以上正常使用addEventListener */
        return function (element, type, fun, useCapture) {
            element.addEventListener(type, fun, useCapture ? useCapture : false);
        };
    } else {
        /* ie7、ie8使用attachEvent */
        return function (element, type, fun) {
            type = type !== "input" ? type : "propertychange"
            if (!fun.prototype["_" + type]) {
                /* 该事件第一次绑定 */
                fun.prototype["_" + type] = {
                    _function: function (event) {
                        fun.call(element, event);
                    },
                    _element: [element]
                };
                element.attachEvent("on" + type, fun.prototype["_" + type]._function);
            } else {
                /* 该事件被绑定过 */
                var s = true;
                // 判断当前的element是否已经绑定过该事件
                for (var i in fun.prototype["_" + type]._element) {
                    if (fun.prototype["_" + type]._element[i] === element) {
                        s = false;
                        break;
                    }
                }
                // 当前的element没有绑定过该事件
                if (s === true) {
                    element.attachEvent("on" + type, fun.prototype["_" + type]._function);
                    fun.prototype["_" + type]._element.push(element);
                }
            }
        };
    }
})();
/*** removeEventlistener ***/
var removeListener = (function () {
    if (document.addEventListener) {
        /* ie9以上正常使用removeEventListener */
        return function (element, type, fun) {
            element.removeEventListener(type, fun);
        };
    } else {
        /* ie7、ie8使用detachEvent */
        return function (element, type, fun) {
            type = type !== "input" ? type : "propertychange"
            element.detachEvent("on" + type, fun.prototype["_" + type]._function);
            if (fun.prototype["_" + type]._element.length === 1) {
                // 该事件只有一个element监听，删除function.prototype["_" + type]
                delete fun.prototype["_" + type];
            } else {
                // 该事件只有多个element监听，从function.prototype["_" + type]._element数组中删除该element
                for (var i in fun.prototype["_" + type]._element) {
                    if (fun.prototype["_" + type]._element[i] === element) {
                        fun.prototype["_" + type]._element.splice(i, 1);
                        break;
                    }
                }
            }
        };
    }
})();
function setSearchSelect(searchSelectData) {
    if (searchSelectData) {
        curELe.value = searchSelectData[curELe.name] || ""
        searchSelectDatas[curELe.name] = searchSelectData
    } else {
        delete searchSelectDatas[curELe.name];
    }
    var selectData = searchSelectDatas[curELe.name] || {}
    var formData = detailForm.getDatas().data
    var conditions = detailForm.conditions
    if (curELe.name === "projectName") {
        $("input[name=schemeNumber]").val("")
    }
    function isImm(k, arrs) {
        var is = false
        for (var index = 0; index < arrs.length; index++) {
            if (arrs[index].name === k) {
                is = arrs[index].immutableAdd || false
                break
            }
        }
        return is
    }
    var newData = {}
    for (var key in formData) {
        if (isImm(key, conditions)) {
            newData[key] = selectData[key]
        } else {
            newData[key] = formData[key]
        }
    }

    detailForm.setOpenData(newData)
    closeSearchSelect()
}
function blurSearchSelect() {
    var event = this.event || window.event;
    var target = event.target || event.srcElement; //获取document 对象的引用 
    if (target !== curELe) {
        if (curELe && curELe.value) {
            searchSelectErr = true
            if (confirm("未从列表中选取项目！")) {
                curELe.value = searchSelectDatas[curELe.name][curELe.name] || ""
            }
        } else {
            setSearchSelect()
        }
    }
}
function closeSearchSelect() {
    searchSelectErr = false
    searchSelectDiv.style.display = "none"
    curELe = null
    removeListener(document, "mousedown", blurSearchSelect)
}
function SearchSelect(options) {
    options = options || { apiName: "api", otherKey: "other" }
    var apiName = options.apiName || "api"
    var otherKey = options.otherKey || "other"
    var otherValue = getOtherValue(otherKey)

    var event = this.event || window.event;
    var target = event.target || event.srcElement; //获取document 对象的引用 
    if (curELe === target && !searchSelectErr) {
        return
    }
    if (searchSelectErr) {
        if (curELe !== target) {
            target.blur()
        }
        return
    }
    curELe = target
    searchSelectDatas[curELe.name] = {}
    var tpos = getPos(curELe)
    function getPos(ele) {
        var pEle = ele.offsetParent
        if (pEle.nodeName !== "BODY") {
            return {
                top: ele.offsetTop + getPos(pEle).top,
                left: ele.offsetLeft + getPos(pEle).left
            }
        } else {
            return {
                top: ele.offsetTop,
                left: ele.offsetLeft
            }
        }
    }
    if (!searchSelectDiv) {
        searchSelectDiv = document.createElement("div")
        var iframe = document.createElement("iframe")
        searchSelectDiv.appendChild(iframe)
        document.body.appendChild(searchSelectDiv)
    }
    searchSelectDiv.style.display = "block"
    searchSelectDiv.style.position = "absolute"
    searchSelectDiv.style.top = tpos.top + curELe.offsetHeight + "px"
    searchSelectDiv.style.left = tpos.left + "px"
    searchSelectDiv.style.zIndex = "100006"
    var searchSelectiframe = searchSelectDiv.children[0]
    searchSelectiframe.setAttribute('hideFocus', true, 0);
    searchSelectiframe.setAttribute('width', "100%", 0);
    searchSelectiframe.setAttribute('height', "200px", 0);
    searchSelectiframe.setAttribute('frameborder', "0", 0);
    searchSelectiframe.setAttribute('border', "0", 0);
    searchSelectiframe.setAttribute('scrolling', "no", 0);
    searchSelectiframe.setAttribute('src', "searchSelect.html?otherKey=" + otherKey + "&otherValue=" + otherValue + "&keywordName=" + curELe.name + "&apiName=" + apiName + "&keyword=", 0);
    function inputFun() {
        searchSelectErr = false
        searchSelectiframe.setAttribute('src', "searchSelect.html?otherKey=" + otherKey + "&otherValue=" + otherValue + "&keywordName=" + curELe.name + "&apiName=" + apiName + "&keyword=" + curELe.value, 0);
    }
    addListener(curELe, "input", inputFun)
    addListener(document, "mousedown", blurSearchSelect)
}
function getOtherValue(otherKey) {
    switch (otherKey) {
        case "projectState":
            return "2"
            break;
        case "recordid":
            return detailForm.getDatas().data.recordid
            break;
        default:
            return ""
            break;
    }
}










