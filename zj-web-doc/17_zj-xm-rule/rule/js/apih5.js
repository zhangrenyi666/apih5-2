/*
    此版本日志：
        lny -> 改为apih5
        新增 selectSearch linkage 组件
        修复 搜索时只有一个input框回车即提交导致token丢失的问题
*/

//全局Lny
var Apih5 = window.Apih5 = function (apiNames, domain) {
    this.domain = domain ? domain : "http://qyh.weidingplus.com/";
    //this.domain = "http://wyongan.xicp.net/wdplus-web/";
    this.apiNames = apiNames || {};
}
Apih5.cookie = {}
Apih5.dev = false;//是不是开发环境，是开发环境时有Lny.log
!function ($, L) {
    //AJAX跨域配置
    $.support.cors = true;
    //jq-page插件默认配置
    if ($.fn.page) {
        $.fn.page.defaults = {
            pageSize: 10,
            pageBtnCount: 9,
            firstBtnText: '首页',
            lastBtnText: '尾页',
            prevBtnText: '上一页',
            nextBtnText: '下一页',
            showJump: true,
            jumpBtnText: 'GO',
            showPageSizes: true,
            pageSizeItems: [10, 20, 40],
            remote: {
                pageIndexName: 'currentPage',     //请求参数，当前页数，索引从1开始
                pageSizeName: 'pageSize',       //请求参数，每页数量
                totalName: 'totalNumber'              //指定返回数据的总数据量的字段名
            }
        };
    }

    //Lny的静态方法start
    L.log = (L.dev && window.console) ? (console.log ? console.log : function () { }) : function () { }
    L.onlyInt = function (me, event) {
        var val = $(me).val();
        var max = $(me).attr("max") ? Number($(me).attr("max")) : Number.MAX_VALUE;
        var min = Number($(me).attr("min"))
        event = event || window.event || arguments.callee.caller.arguments[0];
        var charCode2;
        if ('charCode' in event) {//IE7 and IE8 no charCode
            charCode2 = event.charCode;
        } else {
            charCode2 = event.keyCode;
        }
        if (event.type === "keyup") {
            var returnValue = "";
            if (val !== "") {
                var _arr = val.split(".");
                if (_arr && _arr.length === 2 && _arr[1] === "") {
                    returnValue = val
                } else {
                    var num = Number(val)
                    if (!isNaN(num)) {
                        if (val < min) {
                            returnValue = min
                        } else if (val > max) {
                            returnValue = max
                        } else {
                            returnValue = num
                        }
                    }
                }
            }
            event.returnValue = returnValue
            $(me).val(returnValue);
            event.returnValue = false
            return false;
        } else {
            if (charCode2) {
                if (charCode2 === 8/*back*/ || charCode2 === 13/*Enter*/ || charCode2 === 9/*Tab*/ || charCode2 === 37/*<- */ || charCode2 === 39/* ->*/) {
                    return true;
                } else if (charCode2 === 46) {
                    var _arr = val.split(".");
                    if (_arr.length === 1) {
                        $(me).val(val + ".");
                    }
                    event.returnValue = false
                    return false;

                } else if (charCode2 < 48 || charCode2 > 57) {/*0-9*/
                    event.returnValue = false
                    return false;
                } else {
                    if (val === "0") {
                        $(me).val(String.fromCharCode(charCode2));
                        event.returnValue = false
                        return false;
                    } else {
                        return true;
                    }
                }
            } else {
                var returnValue = "";
                var num = Number(val)
                if (!isNaN(num)) {
                    if (val < min) {
                        returnValue = min
                    } else if (val > max) {
                        returnValue = max
                    } else {
                        returnValue = num
                    }
                }
                event.returnValue = returnValue
                $(me).val(returnValue);
                event.returnValue = false
                return false;
            }
        }
    };
    L.getUrlParam = function (k) {//获取地址栏参数，k为键名
        var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(m);
        if (r != null) return decodeURI(r[2]); return null;
    };
    //设置cookie  
    L.setCookie = function (cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toUTCString();
        document.cookie = cname + "=" + cvalue + "; " + expires;
        L.cookie[cname] = cvalue
    }
    //获取cookie  
    L.getCookie = function (cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1);
            if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
        }
        return L.cookie[cname];
    }
    //清除cookie    
    L.clearCookie = function (name) {
        L.setCookie(name, "", -1);
        L.cookie[name] = ""
    }
    L.dateFormat = function (d, fmt) {//格式化日期，d未new Date(),fmt为格式
        var o = {
            "M+": d.getMonth() + 1, //月份   
            "d+": d.getDate(), //日   
            "H+": d.getHours(), //小时   
            "m+": d.getMinutes(), //分   
            "s+": d.getSeconds(), //秒   
            "q+": Math.floor((d.getMonth() + 3) / 3), //季度   
            "S": d.getMilliseconds() //毫秒   
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
    L.dateToUnix = function (str) {//转unix时间戳，str是带有“-”格式化日期字符串
        return new Date(Date.parse(str.toString().replace(/-/g, "/"))).getTime()
    }
    //Lny的静态方法end

    //Lny的动态方法start
    L.prototype = {
        getTableCheckedData: function (table) {
            var checkBoxs = table.$("[name=checkbox]:checked")
            var checkedData = [];
            if (checkBoxs.length > 0) {
                for (var i = 0; i < checkBoxs.length; i++) {
                    checkedData.push(table.row(checkBoxs.eq(i).attr("data-rowIndex")).data())
                }
            }
            return checkedData
        },
        delTableRow: function (idKey, listKey, urlname, data, callback) {
            var params = {};
            var arr = []
            for (var i = 0; i < data.length; i++) {
                var param = {};
                param[idKey] = data[i][idKey]
                arr.push(param)
            }
            params[listKey] = arr;
            l.ajax(urlname, params, function (data) {
                callback(data)
            })
        },
        delTableRowById: function (idKey, urlname, data, callback) {
            var params = {};
            params[idKey] = data[idKey];
            l.ajax(urlname, params, function (data) {
                callback(data)
            })
        },
        addApi: function (apis) {
            apis = apis || {}
            for (var key in apis) {
                this.apiNames[key] = apis[key]
            }
            return this.apiNames
        },
        getApiUrl: function (apiName) {
            apiName = apiName || ""
            if (!this.apiNames[apiName]) {
                L.log(apiName, "尚未在mainjs中定义")
            }
            return this.domain + this.apiNames[apiName]
        },
        ajax: function (apiName, params, success, noLoading) {
            var loader;
            if (!noLoading) {
                loader = layer.load(1, {
                    shade: [0.3, '#000'] //0.1透明度的白色背景
                });
            }

            $.ajax({
                url: this.getApiUrl(apiName),
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(params),
                contentType: "application/json; charset=utf-8",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
                    // 新增携带token请求
                    var token = L.getCookie('code')
                    xhr.setRequestHeader('token', token);//
                },
                complete: function (result) {
                    if (!noLoading) {
                        layer.close(loader)
                    }
                },
                error: function (result, status, error) {//服务器响应失败处理函数
                    L.log(apiName, error)
                },
                success: function (result) {
                    L.log(apiName, result)

                    if (result.code === "3007") {

                        if (!Apih5 || !l) {
                            return;
                        }
                        //设置刷新token的接口 
                        l.addApi({
                            refreshAccessToken: "user/refreshAccessToken"
                        });

                        l.ajax("refreshAccessToken", { token: L.getCookie('code') }, function (getTokendata, getTokenmessage, getTokensuccess) {

                            var result = {
                                success: getTokensuccess,
                                message: getTokenmessage,
                                data: getTokendata
                            }
                            if (result.success) {
                                //重新设置token
                                L.setCookie('code', result.data, 30);

                                l.ajax(apiName, params, function (_data, _message, _success) {

                                    var result = {
                                        success: _success,
                                        message: _message,
                                        data: _data
                                    }
                                    if (window.parent && window.parent.postMessage) {
                                        window.parent.postMessage(result, '*')
                                    }
                                    success(result.data, result.message, result.success, result)
                                    if (!result.success) {
                                        layer.alert(result.message, { icon: 0, }, function (index) {
                                            layer.close(index);
                                        });
                                    }
                                })
                            } else {
                                layer.alert(result.message, { icon: 0, }, function (index) {
                                    layer.close(index);
                                });
                            }
                        })

                    } else {
                        if (window.parent && window.parent.postMessage) {
                            window.parent.postMessage(result, '*')
                        }
                        success(result.data, result.message, result.success, result)
                        if (!result.success) {
                            layer.alert(result.message, { icon: 0, }, function (index) {
                                layer.close(index);
                            });
                        }
                    }


                    // if (window.parent && window.parent.postMessage) {
                    //     window.parent.postMessage(result,'*')
                    // }
                    // success(result.data,result.message,result.success,result)
                    // if (!result.success) {
                    //     layer.alert(result.message,{ icon: 0,},function (index) {
                    //         layer.close(index);
                    //     });
                    // }
                }
            })
        },
        log: L.log,
        getUrlParam: L.getUrlParam,//获取地址栏参数，k未键名
        dateFormat: L.dateFormat,//格式化日期，d未new Date(),fmt为格式
        dateToUnix: L.dateToUnix//带有“-”格式化日期字符串转unix时间戳
    }
    //Lny的动态方法end

    //筛选表单jq插件
    $.fn.filterfrom = function (options) {
        var $me = this;
        var defaults = {
            maxLength: 5,
            conditions: [],
            onSearch: function () { },
            onReset: function () { }
        }
        options = $.extend({}, defaults, options)

        var tableHtml = '<table class="from-table" onkeydown="if(event.keyCode==13){return false;}"><tr>'
        var cArr = options.conditions;
        $me.selectListDatas = {};
        $me.resetSelectListDatas = {};
        $me.ajaxing = {};
        var createHtml = function (e) {
            var domHtml = '';
            switch (e.type) {
                case "text":
                    domHtml = '<td><label class="form-label">' + e.label + '：</label></td><td><input type="text" name="' + e.name + '" placeholder="' + (e.placeholder ? e.placeholder : '') + '" value="' + (e.defaultValue ? e.defaultValue : '') + '" class="input-text"></td>'
                    break
                case "number":
                    domHtml = '<td><label class="form-label">' + e.label + '：</label></td><td><input min="0" style="width:100%" type="number" name="' + e.name + '" placeholder="' + (e.placeholder ? e.placeholder : '') + '" value="' + (e.defaultValue ? e.defaultValue : 0) + '" class="input-text"></td>'
                    break
                case "checkbox":
                    var realValue = newValue || that.defaultValue || [];
                    //设置值前清空所有
                    $.each(that.$dom.find("input"), function (i, item) {
                        $(item).attr("checked", false)
                        item.checked = false;
                    });

                    //设置值
                    $.each(that.$dom.find("input"), function (i, item) {
                        for (var i = 0; i < realValue.length; i++) {
                            if ($(item).attr("value") === realValue[i]) {
                                $(item).attr("checked", true)
                                item.checked = true;
                            }
                        }
                    });
                    break
                case "selectAll":
                    var optionHtml = "", sl = [], isAjax = false;
                    if (e.ajax) {
                        if (e.ajax.api) {
                            $me.ajaxing[e.name] = true;
                            l.ajax(e.ajax.api, { selectFlag: 1 }, function (selectList) {
                                $me.selectListDatas[e.name] = [];
                                $.each(selectList, function (index, v) {
                                    $me.selectListDatas[e.name].push({
                                        value: v[e.ajax.valueName || "value"] || "",
                                        text: v[e.ajax.textName || "text"] || "",
                                        children: v[e.ajax.childrenName || "children"] || []
                                    })
                                })
                                $me.resetSelect(e.name, $me.selectListDatas[e.name])
                                if (e.ajax.child) {
                                    var childAjax = {}
                                    $.each(cArr, function (index, v) {
                                        if (v.name == e.ajax.child) {
                                            for (var key in v.ajax) {
                                                childAjax[key] = v.ajax[key]
                                            }
                                        }
                                    })
                                    $me.find("select[name=" + e.name + "]").on("change", function (event) {
                                        $.each($me.selectListDatas[e.name], function (index, v) {
                                            if (v.value == event.target.value) {
                                                $me.selectListDatas[e.ajax.child] = [];
                                                $.each(v.children || [], function (index2, v2) {
                                                    $me.selectListDatas[e.ajax.child].push({
                                                        value: v2[childAjax.valueName || "value"] || "",
                                                        text: v2[childAjax.textName || "text"] || "",
                                                    })
                                                })
                                                $me.resetSelect(e.ajax.child, $me.selectListDatas[e.ajax.child])
                                            }
                                        })
                                    })
                                    $me.selectListDatas[e.ajax.child] = [];
                                    $me.resetSelectListDatas[e.ajax.child] = [];
                                    if ($me.selectListDatas[e.name].length) {
                                        $.each($me.selectListDatas[e.name][0].children || [], function (index, v) {
                                            $me.resetSelectListDatas[e.ajax.child].push({
                                                value: v[childAjax.valueName || "value"] || "",
                                                text: v[childAjax.textName || "text"] || "",
                                            })
                                            $me.selectListDatas[e.ajax.child].push({
                                                value: v[childAjax.valueName || "value"] || "",
                                                text: v[childAjax.textName || "text"] || "",
                                            })
                                        })
                                    }
                                    $me.resetSelect(e.ajax.child, $me.selectListDatas[e.ajax.child])
                                }
                                $me.ajaxing[e.name] = false;
                            })
                        } else if (e.ajax.parent && $me.selectListDatas[e.name]) {
                            isAjax = true
                            sl = $me.selectListDatas[e.name]
                        }
                    } else {
                        $me.selectListDatas[e.name] = e.selectList
                        sl = $me.selectListDatas[e.name]
                    }
                    for (var i = 0; i < sl.length; i++) {
                        optionHtml += '<option ' + ((sl[i].disabled) ? 'disabled' : '') + ' value="' + sl[i].value + '" ' + ((sl[i].selected) ? 'selected' : '') + '>' + sl[i].text + '</option>'
                    }
                    domHtml = '<td><label class="form-label">' + e.label + '：</label></td><td><select id="organId" multiple size="1" name="' + e.name + '">' + optionHtml + '</select></td>'

                    break
                case "select":
                    var optionHtml = "", sl = [], isAjax = false;
                    if (e.ajax) {
                        if (e.ajax.api) {
                            $me.ajaxing[e.name] = true;
                            l.ajax(e.ajax.api, { selectFlag: 1 }, function (selectList) {
                                $me.selectListDatas[e.name] = [];
                                $.each(selectList, function (index, v) {
                                    $me.selectListDatas[e.name].push({
                                        value: v[e.ajax.valueName || "value"] || "",
                                        text: v[e.ajax.textName || "text"] || "",
                                        children: v[e.ajax.childrenName || "children"] || []
                                    })
                                })
                                $me.resetSelect(e.name, $me.selectListDatas[e.name])
                                if (e.ajax.child) {
                                    var childAjax = {}
                                    $.each(cArr, function (index, v) {
                                        if (v.name == e.ajax.child) {
                                            for (var key in v.ajax) {
                                                childAjax[key] = v.ajax[key]
                                            }
                                        }
                                    })
                                    $me.find("select[name=" + e.name + "]").on("change", function (event) {
                                        $.each($me.selectListDatas[e.name], function (index, v) {
                                            if (v.value == event.target.value) {
                                                $me.selectListDatas[e.ajax.child] = [];
                                                $.each(v.children || [], function (index2, v2) {
                                                    $me.selectListDatas[e.ajax.child].push({
                                                        value: v2[childAjax.valueName || "value"] || "",
                                                        text: v2[childAjax.textName || "text"] || "",
                                                    })
                                                })
                                                $me.resetSelect(e.ajax.child, $me.selectListDatas[e.ajax.child])
                                            }
                                        })
                                    })
                                    $me.selectListDatas[e.ajax.child] = [];
                                    $me.resetSelectListDatas[e.ajax.child] = [];
                                    if ($me.selectListDatas[e.name].length) {
                                        $.each($me.selectListDatas[e.name][0].children || [], function (index, v) {
                                            $me.resetSelectListDatas[e.ajax.child].push({
                                                value: v[childAjax.valueName || "value"] || "",
                                                text: v[childAjax.textName || "text"] || "",
                                            })
                                            $me.selectListDatas[e.ajax.child].push({
                                                value: v[childAjax.valueName || "value"] || "",
                                                text: v[childAjax.textName || "text"] || "",
                                            })
                                        })
                                    }
                                    $me.resetSelect(e.ajax.child, $me.selectListDatas[e.ajax.child])
                                }
                                $me.ajaxing[e.name] = false;
                            })
                        } else if (e.ajax.parent && $me.selectListDatas[e.name]) {
                            isAjax = true
                            sl = $me.selectListDatas[e.name]
                        }
                    } else {
                        $me.selectListDatas[e.name] = e.selectList
                        sl = $me.selectListDatas[e.name]
                    }
                    for (var i = 0; i < sl.length; i++) {
                        optionHtml += '<option ' + ((sl[i].disabled) ? 'disabled' : '') + ' value="' + sl[i].value + '" ' + ((sl[i].selected) ? 'selected' : '') + '>' + sl[i].text + '</option>'
                    }
                    domHtml = '<td><label class="form-label">' + e.label + '：</label></td><td><div class="select-box"><select class="select" size="1" name="' + e.name + '">' + optionHtml + '</select></div></td>'

                    break
                case "date":
                    var datePickerOption = (e.minDate ? 'minDate:' + e.minDate + ',' : '') + (e.maxDate ? 'maxDate:' + e.maxDate + ',' : '') + (e.dateFmt ? 'dateFmt:\'' + e.dateFmt + '\',' : '')
                    domHtml = '<td><label class="form-label">' + e.label + '：</label></td><td><input type="text" name="' + e.name + '" placeholder="" value="' + (e.defaultValue ? e.defaultValue : '') + '" onfocus="WdatePicker({' + datePickerOption + '})" id="' + e.id + '" class="input-text Wdate"></td>'
                    break

                case "address"://地址联动
                    var $con = $('<div style="position:relative"></div>');
                    $con.attr("class", "row").attr("style", "margin-top: 0;padding-left:0;padding-right:0;margin-left:0")
                    var $col = $('<div class="f-l col-4"></div>')
                    var $addressSelect1 = $('<select name="' + that.name + '" data-addressName="address1" class="select"></select>')
                    var $addressSelect2 = $('<select name="' + that.name + '" data-addressName="address2" class="select"></select>')
                    var $addressSelect3 = $('<select name="' + that.name + '" data-addressName="address3" class="select"></select>')
                    var selectData1 = [];
                    var selectData2 = [];
                    var selectData3 = [];
                    $addressSelect1.on("change", function () {
                        var val = $(this).val()
                        $.each(selectData1, function (i, v) {
                            if (v.value == val) {
                                selectData2 = $me.updateSelect(v.children || [], $addressSelect2, that.ajax)
                                selectData3 = $me.updateSelect(selectData2[0].children || [], $addressSelect3, that.ajax)
                            }
                        })
                    })
                    $addressSelect2.on("change", function () {
                        var val = $(this).val()
                        $.each(selectData2, function (i, v) {
                            if (v.value == val) {
                                selectData3 = $me.updateSelect(v.children || [], $addressSelect3, that.ajax)
                            }
                        })
                    })
                    if ($me.addressData.length > 0) {
                        selectData1 = $me.updateSelect($me.addressData, $addressSelect1, that.ajax)
                        selectData2 = $me.updateSelect(selectData1[0].children || [], $addressSelect2, that.ajax)
                        selectData3 = $me.updateSelect(selectData2[0].children || [], $addressSelect3, that.ajax)
                    } else {
                        if (that.selectList) {
                            $me.addressData = that.selectList;
                            selectData1 = $me.updateSelect($me.addressData, $addressSelect1, that.ajax)
                            selectData2 = $me.updateSelect(selectData1[0].children || [], $addressSelect2, that.ajax)
                            selectData3 = $me.updateSelect(selectData2[0].children || [], $addressSelect3, that.ajax)
                        } else {
                            $me.ajaxing[that.name] = true;
                            l.ajax(that.ajax.api, {}, function (data) {
                                $me.addressData = data;
                                selectData1 = $me.updateSelect($me.addressData, $addressSelect1, that.ajax)
                                selectData2 = $me.updateSelect(selectData1[0].children || [], $addressSelect2, that.ajax)
                                selectData3 = $me.updateSelect(selectData2[0].children || [], $addressSelect3, that.ajax)
                                $me.ajaxing[that.name] = false;
                            })
                        }
                    }
                    $con.append($col.clone().append($addressSelect1));
                    $con.append($col.clone().append($addressSelect2));
                    $con.append($col.clone().append($addressSelect3));
                    domHtml = $con;
                    break;
            }
            return domHtml
        }
        for (var i = 0; i < cArr.length; i++) {
            if (i % options.maxLength == 0 && i != 0) {
                tableHtml += '</tr><tr>'
            }
            tableHtml += createHtml(cArr[i]);
        }
        if (cArr.length % options.maxLength == 0) {
            tableHtml += '</tr><tr>'
        }
        tableHtml += '<td width="20"></td><td><button data-name="search" class="btn btn-primary" type="button"><i class="Hui-iconfont">&#xe709;</i> 查询</button><button data-name="reset" class="btn ml-20  btn-primary" type="button"><i class="Hui-iconfont">&#xe66c;</i> 重置</button></td>'
        tableHtml += '</tr></table>'
        var $table = $(tableHtml)
        $me.append($table)
        $me.on("click", "button", function () {
            var name = $(this).attr("data-name");
            switch (name) {
                case "search":
                    options.onSearch($me.serializeArray())
                    break;
                case "reset":
                    for (var key in $me.resetSelectListDatas) {
                        $me.resetSelect(key, $me.resetSelectListDatas[key])
                    }
                    $me[0].reset()
                    options.onReset($me.serializeArray())
                    $('.fs-label').text('请选择');
                    $('.fs-label').attr('title', '请选择');
                    $("#organId").val([]);
                    $(".fs-option").removeClass('selected');
                    break;
            }
        })

        $me.resetSelect = function (name, sl) {
            var $select = $me.find("select[name=" + name + "]");
            $select.html("");

            for (var i = 0; i < sl.length; i++) {
                $select.append('<option ' + ((sl[i].disabled) ? 'disabled' : '') + ' value="' + sl[i].value + '" ' + ((sl[i].selected) ? 'selected' : '') + '>' + sl[i].text + '</option>');
            }

        };
        $("#organId").fSelect();
        return $me
    }

    //详情弹窗jq插件
    $.fn.detailLayer = function (options) {
        var $me = this;
        // 参数配置start
        var defaults = {
            controlKey: null,
            conditions: [],
            onSave: function () { L.log("保存") },
            onAdd: function () { L.log("新增") },
            customBtnGroup: null
        }
        options = $.extend({}, defaults, options);
        // 参数配置end

        //属性声明start
        $me.idName = $me.attr("id");

        $me.conditions = options.conditions;//环境参数集合
        $me.items = [];//表单子元素集合
        $me.$formTable = options.$formTable || null;
        $me.mylayer = {};//弹出层对象
        $me.isAdd = true;//弹出层是否是为"新增"态
        $me.colOffset = options.colOffset || 2;
        $me.layerTitle = options.layerTitle;//弹出层标题
        $me.layerArea = options.layerArea;//弹出层尺寸
        $me.addCallback = options.onAdd;//弹出层“新增”态，保存按钮回调函数
        $me.saveCallback = options.onSave;//弹出层“编辑”态，保存按钮回调函数

        $me.selectListDatas = {};//下拉列表数据集合
        $me.addressData = [];//地址数据

        $me.ajaxing = {};//ajaxing请求状态集合

        // 特殊控制声明
        $me.controlKey = options.controlKey || null//根据哪个key来执行特殊控制
        $me.controlValue = null//哪些controlKey字段的value属性值执行特殊控制 

        //自定义按钮组
        $me.customBtnGroup = options.customBtnGroup || null
        //属性声明end

        //结构构建start
        var $page = $('<div class="page-container" ></div>')//页面容器
        var $detailForm = $('<form class="form form-horizontal"></form>')//详情表单容器
        var $detailFormControlLine = $('<div class="row cl"></div>')//详情表单控制行
        var $detailFormControlBtnBox = $('<div class="col-' + (12 - $me.colOffset) + ' f-l col-offset-' + $me.colOffset + '"></div>');//详情表单控制按钮组
        if ($me.customBtnGroup) {
            var $btnArr = []
            var btns = $me.customBtnGroup.btns || []
            $.each(btns, function (i, item) {
                var $btnItem = $('<button data-index="' + i + '" class="btn btn-primary mr-10" type="button">' + item.label + '</button>')
                $btnItem.attr("data-name", $me.idName + item.name)
                $.each($btnItem.find("*"), function (i2, item2) {
                    $(item2).attr("data-name", $me.idName + item.name)
                })
                $btnItem.on("click", function (event) {
                    if (typeof $me.customBtnGroup.callback === "function") {
                        $me.customBtnGroup.callback($(event.target).attr("data-name").replace($me.idName, ''), $me)
                    }
                })
                $btnArr.push($btnItem)
            })
            $detailFormControlBtnBox.append($btnArr)
        } else {
            var $btnSave = $('<button data-name="' + $me.idName + 'save" class="btn btn-primary" type="button"><i class="Hui-iconfont">&#xe632;</i> 确定</button>')//保存按钮
            var $btnClose = $('<button data-name="' + $me.idName + 'close" class="btn btn-primary ml-10" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>')//取消按钮
            $btnSave.on("click", function () {
                var dataObj = $me.getDatas()
                if (!dataObj.err.length) {
                    if ($me.isAdd) {
                        $me.addCallback($me, dataObj.data)
                    } else {
                        $me.saveCallback($me, dataObj.data)
                    }
                } else {
                    layer.alert(dataObj.err.join("<br/>"), { icon: 0, }, function (index) {
                        layer.close(index);
                    });
                }
            })
            $btnClose.on("click", function () {
                $me.close()
            })
            $detailFormControlBtnBox.append([$btnSave, $btnClose])
        }
        $detailFormControlLine.append($detailFormControlBtnBox)
        // 重置下拉列表内容start
        $me.resetSelectHtml = function (data, $s) {
            $s.html("");
            if (data) {
                $.each(data, function (i, v) {
                    $s.append('<option ' + ((v.disabled) ? 'disabled' : '') + ' value="' + v.value + '" ' + ((v.selected) ? 'selected' : '') + '>' + v.text + '</option>');
                })
            }
            return $s
        };
        // 重置下拉列表内容end

        // 刷新联动下拉列表数据start
        $me.updateSelect = function (data, $s, ajax) {
            if (!ajax) {
                ajax = {
                    valueName: "value",
                    textName: "text",
                    childrenName: "children",
                }
            }
            var d = [];
            if (data && data.length > 0) {
                $.each(data, function (i, v) {
                    d.push({
                        value: v[ajax.valueName],
                        text: v[ajax.textName],
                        children: v[ajax.childrenName] || [],
                    })
                })
            }
            if (d.length > 0) {
                $me.resetSelectHtml(d, $s).show()
            } else {
                $me.resetSelectHtml(d, $s).hide()
            }
            return d
        }
        // 刷新联动下拉列表数据start
        //表单子元素构造函数start
        function FormItem(condition, key) {
            // L.log(key)
            if (!condition.type) {
                L.log("FormItem:key为" + key + "的type不存在")
            }
            if (!condition.name) {
                L.log("FormItem:key为" + key + "的name不存在")
            }
            var that = this;
            this.condition = condition;
            this.key = key;
            this.type = condition.type;//类型
            this.name = condition.name;//名字
            this.remind = condition.remind;//提示

            this.label = condition.label || "";//标题
            this.must = condition.must || false;//是否必填

            this.immutable = condition.immutable//仅编辑禁用
            this.immutableAdd = condition.immutableAdd//新增也禁用         

            this.defaultValue = condition.defaultValue//text,number,textarea,date,hidden类型特有
            this.placeholder = condition.placeholder//text,number,textarea,date类型特有
            this.ajax = condition.ajax//select,address,pickTree类型特有
            this.maxLen = condition.maxLen || 99//pickTree,upload类型特有

            this.minDate = condition.minDate//date类型特有
            this.maxDate = condition.maxDate//date类型特有
            this.dateFmt = condition.dateFmt//date类型特有
            this.id = condition.id || condition.name//date类型特有



            this.pickType = condition.pickType//pickTree类型特有
            this.noMultiple = condition.noMultiple//pickTree类型特有
            this.otherMemberData = condition.otherMemberData || []//pickTree类型特有

            this.min = condition.min//number类型特有
            this.max = condition.max//number类型特有

            this.btnName = condition.btnName//upload类型特有
            this.fileType = condition.fileType//upload类型特有
            this.projectName = condition.projectName//upload类型特有
            this.uploadUrl = condition.uploadUrl//upload类型特有


            this.search = condition.search || false
            this.selectList = condition.selectList//selectList类型特有

            this.minOptionNum = condition.minOptionNum || 1;//option类型特有

            this.controlHide = condition.controlHide//类型特有
            this.controlLabel = condition.controlLabel//类型特有

            this.ueOptions = condition.ueOptions || {};//Ueditor特有
            this.ueOptions.zIndex = 99999999;
            this.ue = null;//Ueditor特有

            // test新增
            this.onChange = condition.onChange;
            this.help = condition.help;

            //联动特有 ---test
            this.children = condition.children;

            this.$label = $('<label title="' + this.label + '" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;"><i style="color:red;' + (this.must ? '' : 'display:none') + '">* </i><span>' + this.label + '</span>：</label>')
            this.$dom = this.createDom()
            return this
        }
        FormItem.prototype.setValue = function (newValue, data) {
            var that = this;
            var immutable = false;
            if ($me.isAdd) {
                immutable = that.immutableAdd || false
            } else {
                immutable = that.immutableAdd || that.immutable || false
            }

            switch (that.type) {
                case "address":
                    var val = newValue || that.defaultValue || "1_2_6";
                    var valArr = val.split("_")
                    var $addressSelect = [that.$dom.find("[name=" + that.name + "][data-addressName=address1]"), that.$dom.find("[name=" + that.name + "][data-addressName=address2]"), that.$dom.find("[name=" + that.name + "][data-addressName=address3]")]
                    var selectData = [];
                    $.each($addressSelect, function (ii, item) {
                        var val = valArr[ii]
                        if (val && val != "") {
                            if (ii == 0) {
                                selectData[ii] = $me.updateSelect($me.addressData, item, that.ajax);
                            }
                            item.val(val);
                            if (ii != $addressSelect.length - 1) {
                                $.each(selectData[ii], function (j, m) {
                                    if (m.value == val) {
                                        selectData[ii + 1] = $me.updateSelect(m.children || [], $addressSelect[ii + 1], that.ajax)
                                    }
                                })
                            }
                        } else {
                            $me.updateSelect([], item, that.ajax)
                            item.val('');
                        }
                    })
                    break
                case "upload":
                    resetUploadList(($me.idName + that.name), newValue || [], immutable)
                    break;
                case "pickMember":
                    drawPickMember(newValue[0] || [], newValue[1] || []);
                    break;
                case "pickTree":
                    var d = {};
                    for (var key in that.pickType) {
                        if (that.pickType[key]) {
                            if (newValue) {
                                d[key] = newValue[that.pickType[key]]
                            } else {
                                d[key] = []
                            }
                        }
                    }
                    if (immutable) {
                        that.$dom.find(".pickTreeAdd").hide();
                    } else {
                        that.$dom.find(".pickTreeAdd").show();
                    }
                    myPickTree.drawCurData(($me.idName + that.name), d, immutable)
                    break;
                case "date":
                    var dateValue = ""
                    if (newValue) {
                        dateValue = l.dateFormat(new Date(Number(newValue)), that.dateFmt || "yyyy-MM-dd")
                    } else if (that.defaultValue) {
                        dateValue = l.dateFormat(new Date(Number(that.defaultValue)), that.dateFmt || "yyyy-MM-dd")
                    }
                    that.$dom.find("[name=" + that.name + "]").val(dateValue)
                    break;
                case "option":
                    var uploadList = {
                        uploadUrl: 'uploadFilesByFileName',
                        projectName: 'zj-xm-rule',
                        btnName: '添加流程图',
                        fileType: ["docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"],
                        maxLen: 1,
                        name: 'ruleFileFlowList'
                    }
                    that.$dom.find('.optionAdd').remove();
                    that.$dom.find('[name=flowName]').val("")
                    that.$dom.find('[name=flowName]').eq(0).attr("disabled", immutable);
                    resetUploadList(($me.idName + 'ruleFileFlowList' + '0'), [], immutable)
                    var dataArr = newValue || that.defaultValue || []
                    for (var j = 0; j < dataArr.length; j++) {
                        if (j < that.minOptionNum) {
                            that.$dom.find('[name=flowName]').eq(j).val(dataArr[j].flowName || "")
                            resetUploadList(($me.idName + 'ruleFileFlowList' + j), dataArr[j].ruleFileFlowList || [], immutable)
                        } else {
                            var $optionAdd = $('<div class="mb-15 optionAdd" style="position:relative"><input type="text" disabled='+ immutable +' name="flowName" value="' + dataArr[j].flowName + '" class="input-text"><div class="mt-15 uploadFile" data-uploadUrl="' + uploadList.uploadUrl + '" data-projectName="' + uploadList.projectName + '" data-name="' + ($me.idName + uploadList.name + j) + '" data-maxLen="' + uploadList.maxLen + '"><ul class="uploadList"></ul><label style="position: relative;" for="uploadBtn_' + ($me.idName + uploadList.name + j) + '" name="' + uploadList.name + j + '" class="btn btn-primary size-S">' + uploadList.btnName + '<input  id="uploadBtn_' + ($me.idName + uploadList.name + j) + '" data-fileType=\'' + JSON.stringify(uploadList.fileType) + '\' name="filesName" size="2" class="inputFile"  type="file" /></label><span class="ml-10">' + (uploadList.remind ? uploadList.remind : (uploadList.fileType ? ('上传文件类型：' + JSON.stringify(uploadList.fileType)) : '')) + '</span></div></div>')
                            var $removeBtn = $('<a title="删除" class="removeBtn" href="javascript:;" class="ml-5 delete" style="text-decoration: none;font-size: 20px;position: absolute;right: -25px;top: -1px;"><i class="icon Hui-iconfont"></i></a>')
                            $removeBtn.click(function () {
                                $(this).parents(".optionAdd").remove()
                            })
                            if (!immutable) {
                                $optionAdd.append($removeBtn)
                            }
                            that.$dom.find('.optionAddBox').append($optionAdd)
                        }
                        that.$dom.find('[name=flowName]').eq(j).attr("disabled", immutable);
                        resetUploadList(($me.idName + 'ruleFileFlowList' + j), dataArr[j].ruleFileFlowList || [], immutable)
                    }
                    if (immutable) {
                        that.$dom.find(".addBtn").hide();
                    } else {
                        that.$dom.find(".addBtn").show();
                    }
                    break;
                case "editor":
                    var ueReadySetConent = function () {
                        that.ue.setContent(newValue || that.defaultValue || "");
                        that.ue.off('ready', ueReadySetConent)
                    };
                    that.ue = UE.getEditor($me.idName + that.name, that.ueOptions);
                    that.ue.on('ready', ueReadySetConent);
                    break;
                case "number":
                    that.$dom.find("[name=" + that.name + "]").val(newValue || that.defaultValue || that.min || 0)
                    break;
                case "textarea":
                    var realValue = (newValue || that.defaultValue || "").replace(/<br\/>/g, '\r\n')
                    that.$dom.find("[name=" + that.name + "]").val(realValue)
                    break
                case "select":
                    var realValue = newValue || that.defaultValue || ""
                    that.$dom.find("[name=" + that.name + "]").val(realValue)
                    if (that.search) {
                        $.each($me.selectListDatas[that.name], function (index, v) {
                            if (v.value == realValue) {
                                that.$dom.find(".selectSearch").html(v.text)
                            }
                        })
                    }
                    break
                case "checkbox":
                    var realValue = newValue || that.defaultValue || [];
                    //设置值前清空所有
                    $.each(that.$dom.find("input"), function (i, item) {
                        $(item).attr("checked", false)
                        item.checked = false;
                    });

                    //设置值
                    $.each(that.$dom.find("input"), function (i, item) {
                        for (var i = 0; i < realValue.length; i++) {
                            if ($(item).attr("value") === realValue[i]) {
                                $(item).attr("checked", true)
                                item.checked = true;
                            }
                        }
                    });
                    break
                case "selectSearch":
                    var realValue = newValue || that.defaultValue || "";
                    var textName = that.ajax.textName;
                    that.$dom.find("[name=" + that.name + "_searchInput]").val(data[textName]);
                    that.$dom.find("[name=" + that.name + "]").val(realValue);
                    break;
                default://hidden,text,select
                    var realValue = newValue || that.defaultValue || ""
                    that.$dom.find("[name=" + that.name + "]").val(realValue)
                    break;
            }
            that.$dom.find("[name=" + that.name + "]").attr("disabled", immutable);
            return that
        }
        FormItem.prototype.getValue = function () {
            var that = this;
            var d = "";
            switch (that.type) {
                case "upload":
                case "pickMember":
                    L.log(that.type + "类型暂不可使用getValue")
                    break;
                case "address":
                    d = "";
                    d += (that.$dom.find("[data-addressName=address1]").val() ? that.$dom.find("[data-addressName=address1]").val() : "");
                    d += (that.$dom.find("[data-addressName=address2]").val() ? "_" + that.$dom.find("[data-addressName=address2]").val() : "");
                    d += (that.$dom.find("[data-addressName=address3]").val() ? "_" + that.$dom.find("[data-addressName=address3]").val() : "");
                    break
                case "pickTree":
                    d = myPickTree.getDataByName($me.idName + that.name)
                    break
                case "option":
                    d = [];
                    var con = that.$dom.find('[name=flowName]')
                    for (var j = 0; j < con.length; j++) {
                        d.push({ "flowName": $.trim(con.eq(j).val()), "ruleFileFlowList":getUploadList(that.$dom.find('.uploadFile').eq(j).attr('data-name')) })
                    }
                    break;
                case "editor":
                    d = that.ue.getContent()
                    break;
                case "textarea":
                    var oldd = that.$dom.find("[name=" + that.name + "]").val()
                    d = oldd.replace(/\r\n/g, '<br/>').replace(/\n/g, '<br/>').replace(/\s/g, ' ');
                    break;
                case "linkage":
                    d = that.$dom.find("[name=" + that.name + "]").val()
                    break;
                case "checkbox":
                    var checkBoxs = that.$dom.find("[type=checkbox]:checked");
                    var d = [];
                    if (checkBoxs.length > 0) {
                        for (var i = 0; i < checkBoxs.length; i++) {
                            d.push(checkBoxs.eq(i).attr("data-value"))
                        }
                    }
                    break;
                default://hidden,text,number,select,date
                    d = that.$dom.find("[name=" + that.name + "]").val()
                    break;
            }
            return d
        }
        FormItem.prototype.createDom = function () {
            var that = this;
            var $con = $('<div style="position:relative"></div>');

            //字段你切换后调用该方法
            //用来设置一些字段连动什么的
            var inputChangeCbFn = function (value, lists) {
                lists = lists || $me.selectListDatas[that.name];
                $.each(lists, function (index, v) {
                    if (v.value == value) {
                        var linkage = that.condition.linkage;
                        var otherInfo = v.item || v;
                        //需要联动别的字段
                        if (linkage) {
                            for (var key in linkage) {
                                var ele = linkage[key];
                                var val = otherInfo[ele];
                                var eleType = null;
                                var dateFmt = null;

                                $.each($me.conditions, function (index, v) {
                                    if (v.name == key) {
                                        eleType = v.type;
                                        dateFmt = v.dateFmt;
                                    }
                                });

                                var _val = '';
                                if (eleType === "date" || eleType === "datetime") {
                                    //日期
                                    if (val) {
                                        _val = l.dateFormat(new Date(Number(val)), dateFmt || "yyyy-MM-dd");
                                    }
                                } else {
                                    if (val) {
                                        //防止后台返回html的换行
                                        _val = val.replace(/<br\/>/g, '\r\n');
                                    }
                                }
                                $me.find("[name=" + key + "]").val(_val)
                            }
                        }
                    }
                })
            }


            switch (that.type) {
                case "hidden"://隐藏域
                    var $hidden = $('<input type="hidden" name="' + that.name + '" value="' + (that.defaultValue ? that.defaultValue : '') + '">')
                    $con.append($hidden)
                    break
                case "text"://单行文本
                    var $text = $('<input  type="text" name="' + that.name + '" placeholder="' + (that.placeholder ? that.placeholder : '') + '" value="' + (that.defaultValue ? that.defaultValue : '') + '" class="input-text">')
                    $con.append($text)
                    break
                case "number"://数字文本
                    var $num = $('<input min="' + (that.min ? that.min : 0) + '" max="' + (that.max ? that.max : "") + '" style="width:100%" onKeyUp="Apih5.onlyInt(this,event)" onBlur="Apih5.onlyInt(this,event)" onKeyPress="return Apih5.onlyInt(this,event)" onpaste="return false"  type="text" name="' + that.name + '" placeholder="' + (that.placeholder ? that.placeholder : '') + '" value="' + (that.defaultValue ? that.defaultValue : 0) + '" class="input-text">')
                    $con.append($num)
                    break
                case "textarea"://多行文本
                    var $textarea = $('<textarea class="textarea" name="' + that.name + '" placeholder="' + (that.placeholder ? that.placeholder : '') + '">' + (that.defaultValue ? that.defaultValue : '') + '</textarea>')
                    $con.append($textarea)
                    break
                case "date"://日期插件
                    var datePickerOption = (that.minDate ? 'minDate:' + that.minDate + ',' : '') + (that.maxDate ? 'maxDate:' + that.maxDate + ',' : '') + (that.dateFmt ? 'dateFmt:\'' + that.dateFmt + '\',' : '')
                    var dateValue = ""
                    if (that.defaultValue) {
                        dateValue = l.dateFormat(new Date(that.defaultValue), that.dateFmt || "yyyy-MM-dd")
                    }
                    var $date = $('<input type="text" name="' + that.name + '" placeholder="" value="' + dateValue + '" onfocus="WdatePicker({' + datePickerOption + '})" id="' + that.id + '" class="input-text Wdate"/>')
                    $con.append($date)
                    break
                case "select"://下拉列表
                    var optionHtml = "", sl = [];
                    if (that.ajax) {
                        if (that.ajax.api) {
                            $me.ajaxing[that.name] = true;
                            l.ajax(that.ajax.api, {}, function (selectList) {
                                $me.selectListDatas[that.name] = [];
                                $.each(selectList, function (index, v) {
                                    $me.selectListDatas[that.name].push({
                                        value: v[that.ajax.valueName || "value"] || "",
                                        text: v[that.ajax.textName || "text"] || "",
                                        children: v[that.ajax.childrenName || "children"] || [],
                                        item: v
                                    })
                                })
                                $me.resetSelectHtml($me.selectListDatas[that.name], $me.find("select[name=" + that.name + "]"))
                                if (that.ajax.child) {
                                    var childAjax = {}
                                    $.each($me.conditions, function (index, v) {
                                        if (v.name == that.ajax.child) {
                                            for (var key in v.ajax) {
                                                childAjax[key] = v.ajax[key]
                                            }
                                        }
                                    })
                                    $me.find("select[name=" + that.name + "]").on("change", function (event) {
                                        $.each($me.selectListDatas[that.name], function (index, v) {
                                            if (v.value == event.target.value) {
                                                $me.selectListDatas[that.ajax.child] = [];
                                                $.each(v.children || [], function (index2, v2) {
                                                    $me.selectListDatas[that.ajax.child].push({
                                                        value: v2[childAjax.valueName || "value"] || "",
                                                        text: v2[childAjax.textName || "text"] || "",
                                                    })
                                                })
                                                $me.resetSelectHtml($me.selectListDatas[that.ajax.child], $me.find("select[name=" + that.ajax.child + "]"))
                                            }
                                        })
                                    })
                                    $me.selectListDatas[that.ajax.child] = [];
                                    $.each($me.selectListDatas[that.name][0].children || [], function (index, v) {
                                        $me.selectListDatas[that.ajax.child].push({
                                            value: v[that.ajax.valueName || "value"] || "",
                                            text: v[that.ajax.textName || "text"] || "",
                                            children: v[that.ajax.childrenName || "children"] || []
                                        })
                                    })
                                    $me.resetSelectHtml($me.selectListDatas[that.ajax.child], $me.find("select[name=" + that.ajax.child + "]"))
                                }
                                $me.ajaxing[that.name] = false;
                            })
                        } else if (that.ajax.parent && $me.selectListDatas[that.name]) {
                            sl = $me.selectListDatas[that.name]
                        }
                    } else {
                        if (that.selectList) {
                            $me.selectListDatas[that.name] = that.selectList
                            sl = $me.selectListDatas[that.name];
                        } else {
                            L.log(that.name + "，selectList和ajax没有设置")
                        }
                    }
                    for (var i = 0; i < sl.length; i++) {
                        optionHtml += '<option ' + ((sl[i].disabled) ? 'disabled' : '') + ' value="' + sl[i].value + '" ' + ((sl[i].selected) ? 'selected' : '') + '>' + sl[i].text + '</option>'
                    }

                    var $sel = $('<select name="' + that.name + '" class="select"></select>')
                    $sel.val(that.defaultValue || "")
                    $sel.append(optionHtml)
                    $con.append($sel);
                    if (that.search) {
                        $sel.hide()
                        var $search = $('<div class="select selectSearch" style="height:31px;"></div>')
                        var $searchBox = $('<div style="display:none;z-index:1001;position:absolute;left:0;right:0;top:31px;padding:0 15px"></div>')
                        var $searchInput = $('<input class="input-text" style="margin-bottom:4px"/>')
                        var $searchListBox = $('<div class="select" style="background:#fff;border-top:none;"></div>')
                        var $searchList = $('<div style="max-height:200px;overflow-y:auto;overflow-x:hidden;"></div>')


                        $searchInput.on("input propertychange", function () {
                            if ($(this).val()) {
                                $searchList.children().hide()
                                $searchList.find(':contains(' + $(this).val().toString() + ')').show()
                            } else {
                                $searchList.children().show()
                            }
                        })

                        var setTime;
                        $searchInput.on("blur", function () {
                            setTime = setTimeout(function () {
                                $searchBox.hide();
                            }, 240)
                        })
                        $searchList.on("click", "div", function () {
                            if (setTime) { clearTimeout(setTime); $searchBox.hide(); }
                            var text = $(this).text()
                            var searchValue = $(this).attr("data-searchValue")
                            $sel.val(searchValue)
                            inputChangeCbFn(searchValue);

                            $search.html(text)
                            $searchInput.val("")
                            if (that.ajax.child) {
                                var childAjax = {}
                                $.each($me.conditions, function (index, v) {
                                    if (v.name == that.ajax.child) {
                                        for (var key in v.ajax) {
                                            childAjax[key] = v.ajax[key]
                                        }
                                    }
                                })
                                $.each($me.selectListDatas[that.name], function (index, v) {
                                    if (v.value == searchValue) {
                                        $me.selectListDatas[that.ajax.child] = [];
                                        $.each(v.children || [], function (index2, v2) {
                                            $me.selectListDatas[that.ajax.child].push({
                                                value: v2[childAjax.valueName || "value"] || "",
                                                text: v2[childAjax.textName || "text"] || "",
                                            })
                                        })
                                        $me.resetSelectHtml($me.selectListDatas[that.ajax.child], $me.find("select[name=" + that.ajax.child + "]"))
                                    }
                                })
                            }
                        })
                        $searchListBox.append($searchInput);
                        $searchListBox.append($searchList);
                        $searchBox.append($searchListBox);
                        $search.on("click", function () {
                            if ($searchBox.is(":hidden")) {
                                $searchList.html("")
                                $.each($sel.children(), function (index, optionItem) {
                                    $searchList.append('<div style="display:block;white-space:pre;min-height: 1.2em;padding: 0px 2px 1px;" data-searchValue="' + optionItem.value + '">' + optionItem.text + '</div>')
                                })

                                $searchBox.show();    //如果元素为隐藏,则将它显现
                                $searchInput.focus()
                            } else {
                                $searchBox.hide();     //如果元素为显现,则将其隐藏
                            }
                        })
                        $con.append($search);
                        $con.append($searchBox);
                    }

                    //值变动监听 
                    $sel.on("change", function (event) {
                        inputChangeCbFn(event.target.value);
                    })

                    break
                case "address"://地址联动
                    $con.attr("class", "row").attr("style", "margin-top: 0;padding-left:0;padding-right:0;margin-left:0")
                    var $col = $('<div class="f-l col-4"></div>')
                    var $addressSelect1 = $('<select name="' + that.name + '" data-addressName="address1" class="select"></select>')
                    var $addressSelect2 = $('<select name="' + that.name + '" data-addressName="address2" class="select"></select>')
                    var $addressSelect3 = $('<select name="' + that.name + '" data-addressName="address3" class="select"></select>')
                    var selectData1 = [];
                    var selectData2 = [];
                    var selectData3 = [];
                    $addressSelect1.on("change", function () {
                        var val = $(this).val()
                        $.each(selectData1, function (i, v) {
                            if (v.value == val) {
                                selectData2 = $me.updateSelect(v.children || [], $addressSelect2, that.ajax)
                                selectData3 = $me.updateSelect(selectData2[0].children || [], $addressSelect3, that.ajax)
                            }
                        })
                    })
                    $addressSelect2.on("change", function () {
                        var val = $(this).val()
                        $.each(selectData2, function (i, v) {
                            if (v.value == val) {
                                selectData3 = $me.updateSelect(v.children || [], $addressSelect3, that.ajax)
                            }
                        })
                    })
                    if ($me.addressData.length > 0) {
                        selectData1 = $me.updateSelect($me.addressData, $addressSelect1, that.ajax)
                        selectData2 = $me.updateSelect(selectData1[0].children || [], $addressSelect2, that.ajax)
                        selectData3 = $me.updateSelect(selectData2[0].children || [], $addressSelect3, that.ajax)
                    } else {
                        if (that.selectList) {
                            $me.addressData = that.selectList;
                            selectData1 = $me.updateSelect($me.addressData, $addressSelect1, that.ajax)
                            selectData2 = $me.updateSelect(selectData1[0].children || [], $addressSelect2, that.ajax)
                            selectData3 = $me.updateSelect(selectData2[0].children || [], $addressSelect3, that.ajax)
                        } else {
                            $me.ajaxing[that.name] = true;
                            l.ajax(that.ajax.api, {}, function (data) {
                                $me.addressData = data;
                                selectData1 = $me.updateSelect($me.addressData, $addressSelect1, that.ajax)
                                selectData2 = $me.updateSelect(selectData1[0].children || [], $addressSelect2, that.ajax)
                                selectData3 = $me.updateSelect(selectData2[0].children || [], $addressSelect3, that.ajax)
                                $me.ajaxing[that.name] = false;
                            })
                        }
                    }
                    $con.append($col.clone().append($addressSelect1));
                    $con.append($col.clone().append($addressSelect2));
                    $con.append($col.clone().append($addressSelect3));
                    break;
                case "pickTree"://树形选择
                    var $pickTree = $('<div data-id="pickTree" data-name="' + $me.idName + that.name + '" class="pickTree"></div>')
                    var $pickTreeList = $('<ul data-id="pickTreeList" class="cl"></ul>')
                    var $pickTreeAdd = $('<button data-id="pickTreeAdd" class="pickTreeAdd" type="button"></button>')
                    myPickTree.initDataWidthName(($me.idName + that.name), that.otherMemberData)
                    $pickTreeAdd.on("click", function () {
                        myPickTree.open(($me.idName + that.name), that.pickType, that.maxLen)
                    })
                    $pickTree.append($pickTreeList);
                    $pickTree.append($pickTreeAdd);
                    $con.append($pickTree);
                    break
                case "pickMember"://旧的成员选择
                    var $pickMember = $('<div id="pickMember" class="pickMember"><ul id="pickMemberList" class="cl"></ul><button data-name="pickMemberAdd" class="pickMemberAdd" type="button"></button></div>')
                    $con.append($pickMember);
                    break
                case "upload"://上传组件唯一
                    accessoryList[$me.idName + that.name] = []
                    var $upload = $('<div class="uploadFile" data-uploadUrl="' + (that.uploadUrl ? that.uploadUrl : "") + '" data-projectName="' + (that.projectName ? that.projectName : "") + '" data-name="' + ($me.idName + that.name) + '" data-maxLen="' + that.maxLen + '"><ul class="uploadList"></ul><label style="position: relative;" for="uploadBtn_' + ($me.idName + that.name) + '" name="' + that.name + '" class="btn btn-primary size-S">' + (that.btnName ? that.btnName : '添加') + '<input  id="uploadBtn_' + ($me.idName + that.name) + '" data-fileType=\'' + JSON.stringify(that.fileType ? that.fileType : []) + '\' name="filesName" size="2" class="inputFile"  type="file" /></label><span class="ml-10">' + (that.remind ? that.remind : (that.fileType ? ('上传文件类型：' + JSON.stringify(that.fileType)) : '')) + '</span></div>')
                    $con.append($upload);
                    break
                case "option"://option
                    var uploadList = {
                        uploadUrl: 'uploadFilesByFileName',
                        projectName: 'zj-xm-rule',
                        btnName: '添加流程图',
                        fileType: ["docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"],
                        maxLen: 1,
                        name: 'ruleFileFlowList'
                    }
                    var j = $('.optionAddBox').context.childNodes.length - 1;
                    for(var i = 0; i < $('.optionAddBox').context.childNodes.length; i++){
                        accessoryList[($me.idName + uploadList.name + i)] = []
                    }
                    var $option = $('<div class="mb-15 option" style="position:relative"><input type="text" name="flowName" placeholder="' + (that.placeholder ? that.placeholder : '') + '" class="input-text"><div class="mt-15 uploadFile" data-uploadUrl="' + uploadList.uploadUrl + '" data-projectName="' + uploadList.projectName + '" data-name="' + ($me.idName + uploadList.name + '0') + '" data-maxLen="' + uploadList.maxLen + '"><ul class="uploadList"></ul><label style="position: relative;" for="uploadBtn_' + ($me.idName + uploadList.name + '0') + '" name="' + uploadList.name + '0' + '" class="btn btn-primary size-S">' + uploadList.btnName + '<input  id="uploadBtn_' + ($me.idName + uploadList.name + '0') + '" data-fileType=\'' + JSON.stringify(uploadList.fileType) + '\' name="filesName" size="2" class="inputFile"  type="file" /></label><span class="ml-10">' + (uploadList.remind ? uploadList.remind : (uploadList.fileType ? ('上传文件类型：' + JSON.stringify(uploadList.fileType)) : '')) + '</span></div></div>')
                    var $optionAddBox = $('<div class="optionAddBox"></div>')
                    var $optionAdd = $('<div class="mb-15 optionAdd" style="position:relative"><input type="text" name="flowName" placeholder="' + (that.placeholder ? that.placeholder : '') + '" class="input-text"><div class="mt-15 uploadFile" data-uploadUrl="' + uploadList.uploadUrl + '" data-projectName="' + uploadList.projectName + '" data-name="' + ($me.idName + uploadList.name + '1') + '" data-maxLen="' + uploadList.maxLen + '"><ul class="uploadList"></ul><label style="position: relative;" for="uploadBtn_' + ($me.idName + uploadList.name + '1') + '" name="' + uploadList.name + '1' + '" class="btn btn-primary size-S">' + uploadList.btnName + '<input  id="uploadBtn_' + ($me.idName + uploadList.name + '1') + '" data-fileType=\'' + JSON.stringify(uploadList.fileType) + '\' name="filesName" size="2" class="inputFile"  type="file" /></label><span class="ml-10">' + (uploadList.remind ? uploadList.remind : (uploadList.fileType ? ('上传文件类型：' + JSON.stringify(uploadList.fileType)) : '')) + '</span></div></div>')
                    var $removeBtn = $('<a title="删除" class="removeBtn" href="javascript:;" class="ml-5 delete" style="text-decoration: none;font-size: 20px;position: absolute;right: -25px;top: -1px;"><i class="icon Hui-iconfont"></i></a>')
                    $removeBtn.click(function () {
                        $(this).parents(".optionAdd").remove()
                    })
                    $optionAdd.append($removeBtn.clone(true))
                    var $addBtn = $('<span class="addBtn btn btn-primary size-S"><i class="Hui-iconfont">&#xe600;</i> 添加选项</span>')
                    $addBtn.click(function () {
                        ++j;
                        accessoryList[($me.idName + uploadList.name + j)] = []
                        $optionAdd.find('.uploadFile').attr('data-name',($me.idName + uploadList.name + j))
                        $optionAdd.find('.uploadFile label').attr('for',("uploadBtn_" + ($me.idName + uploadList.name + j)))
                        $optionAdd.find('.uploadFile label').attr('name',(uploadList.name + j))
                        $optionAdd.find('.uploadFile .inputFile').attr('id',("uploadBtn_" + ($me.idName + uploadList.name + j)))
                        $optionAddBox.append($optionAdd.clone(true))
                    })
                    for (var i = 0; i < that.minOptionNum; i++) {
                        $optionAddBox.append($option.clone())
                    }
                    $con.append($optionAddBox)
                    $con.append($addBtn)
                    break
                case "editor":
                    var $editor = $('<script id="' + $me.idName + that.name + '" type="text/plain" style="width:100%;height:200px;"></script>')
                    $con.append($editor)
                    break;
                //可筛选的下拉插件
                case "selectSearch":
                    var $col = $('<div class="col-12 f-l" style="padding:0;"></div>');
                    //搜索框
                    var $searchInputValue = $('<input type="hidden" name="' + that.name + '" value="' + (that.defaultValue ? that.defaultValue : '') + '"/>')
                    var $searchInput = $('<input type="text" ' + (this.immutableAdd ? 'disabled' : '') + '  name="' + that.name + '_searchInput"  initialValue="" placeholder="' + (that.placeholder ? that.placeholder : '') + '" style="width:100%" class="input-text"/>')

                    //选择区域容器
                    var $optionWrapper = $('<div class="col-12 f-l" style="padding:0;text-align:left;position:relative"></div>');//这个容器实际没啥用就是为了样式
                    var $optionContainer = $('<div class="col-12 f-l" style="display:none;padding:0;text-align:left;height:150px;overflow-y:scroll;position:absolute;top:0px;left:0;background:white;z-index:99;border:1px solid #e0dfdf;"></div>');
                    //需要去请求数据然后放进去
                    if (this.help) {
                        var _h = '<div>';
                        _h += '<div style="padding:10px;">和select类型使用方法一样,唯一不同是ajax属性里多一个searchParamsField属性，<br/>用于搜索时传给后台的字段,新增onChange属性</div>';
                        _h += '</div>';
                        //文档
                        layer.open({
                            type: 1,
                            title: 'selectSearch帮助文本档',
                            content: _h //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                        });
                    }
                    if (that.ajax && that.ajax.api) {
                        //刷新下拉方法 
                        var _refresh = function (apiName, params) {
                            //请求下拉内容
                            if (that.ajax.parentParamsField) {
                                var parentValue = $searchInput.attr('parentValue');
                                params[that.ajax.parentParamsField] = parentValue;
                            }
                            $optionContainer.html($('<div style="text-align:center;margin-top:10px">请稍等...<div>'));
                            l.ajax(apiName, params, function (data, message, success) {
                                $optionContainer.html('');
                                if (success) {
                                    if (!data || data.length === 0) {
                                        $optionContainer.html('<div style="text-align:center;color:#ddd;margin-top:10px;">暂无数据</div>');
                                    }

                                    var _label = that.ajax.textName || 'label';
                                    var _value = that.ajax.valueName || 'value';
                                    var _inpValue = $searchInputValue.val();//真实的value

                                    for (var i = 0; i < data.length; i++) {
                                        data[i].value = data[i][_value];
                                        data[i].item = data[i];
                                    }

                                    for (var w = 0; w < data.length; w++) {
                                        var item = data[w];
                                        var $opt = $('<div value="' + item[_value] + '" style="background:' + (_inpValue === item[_value] ? "#0099FF" : "") + ';color:' + (_inpValue === item[_value] ? "white" : "") + ';cursor:pointer;padding:3px 5px;border-bottom:1px solid #faf6f6;">' + item[_label] + '</div>');
                                        //设置默认值 首次渲染才会设置默认值 
                                        if (_inpValue === item[_value] && !$searchInput.attr('initialValue')) {
                                            $searchInput.attr('initialValue', item[_label])
                                            $searchInput.val(item[_label]);
                                        }
                                        //设置点击
                                        $opt.click(function () {
                                            var _val = $(this).attr('value')
                                            var _lab = $(this).text();
                                            $searchInput.val(_lab);
                                            $searchInputValue.val(_val);

                                            //联动 
                                            inputChangeCbFn(_val, data);

                                            if (that.onChange) {
                                                that.onChange(_val, that, $me)
                                            }
                                            //选中后改变条的颜色
                                            $(this).siblings().css({
                                                color: '#333',
                                                background: 'white'
                                            })
                                            $(this).css({
                                                color: 'white',
                                                background: '#0099FF'
                                            })

                                            $optionContainer.css({
                                                display: 'none'
                                            })

                                        });
                                        $optionContainer.append($opt);
                                    }
                                } else {
                                    layer.alert(message)
                                }
                            }, true);
                        }
                        //请求数据
                        var params = that.ajax.params || {};
                        var searchParamsField = that.ajax.searchParamsField || that.ajax.textName;

                        //用户搜索时  搜索时清除选择的数据  搜索时还需带上父级值
                        $($searchInput).keyup(function () {
                            var _val = $(this).val();
                            clearTimeout(window._searchT);
                            window._searchT = setTimeout(function () {
                                var _params = params;
                                //搜索参数 搜索时需要加上父id
                                _params[searchParamsField] = _val;
                                _refresh(that.ajax.api, _params);
                                $searchInputValue.val('')
                            }, 200)
                        })
                        //触焦 
                        $($searchInput).click(function () {
                            if (this.immutableAdd) { //禁用时不让点击
                                return;
                            }
                            $optionContainer.css({
                                display: 'block'
                            })
                            //搜索时需要加上父id
                            _refresh(that.ajax.api, params);
                        })
                        //从选项里将鼠标移出去
                        $optionContainer.mouseleave(function () {
                            $(this).css({
                                display: 'none'
                            })
                            //强制让输入框失去焦点
                            $($searchInput).blur()
                        })
                        $optionContainer.mouseenter(function () {
                            clearTimeout(window.searchInputMouseleaveTime)
                        })
                        $searchInput.mouseenter(function () {
                            clearTimeout(window.searchInputMouseleaveTime)
                        })
                        //鼠标从搜索框中移出
                        $searchInput.mouseleave(function () {
                            // clearTimeout(window.searchInputMouseleaveTime)
                            window.searchInputMouseleaveTime = setTimeout(function () {
                                if ($optionContainer.css('display') === 'block') {
                                    $optionContainer.css('display', 'none');
                                    //强制让输入框失去焦点
                                    $($searchInput).blur()
                                }
                                $optionContainer.mouseenter(function () {
                                    $optionContainer.css('display', 'block')
                                })
                            }, 600);
                        })
                    } else {
                        layer.open({
                            title: '配置错误'
                            , content: that.name + '的ajax属性下的api属性为必填'
                        });
                    }
                    $optionWrapper.append($optionContainer);
                    $col.append($searchInputValue);
                    $col.append($searchInput);
                    $col.append($optionWrapper);
                    $con.append($col)
                    break;
                //四级联动
                case "linkage":

                    break;

                case "qnnTable":

                    var $con = $('<div style="position:relative"></div>');
                    var idName = 'table_' + that.name;
                    var idNameByPager = 'pager_' + that.name;
                    //插入表格需要的dom元素
                    var $tableDom = $('<table id="' + idName + '" class="table table-border table-bordered table-bg table-hover"></table>');
                    $con.append($tableDom);
                    var $pagerDom = $('<div id="' + idNameByPager + '" class="m-pagination f-r"></div>')
                    $con.append($pagerDom);

                    //异步执行回调 
                    setTimeout(function () {
                        that.condition.createFormCb(idName, idNameByPager);
                    }, 20);
                    break
                case "checkbox":
                    var $checkbox = $('<div class="col-12 f-l" style="padding:0;"></div>');
                    var optionData = that.condition.optionData;
                    var name = that.name;
                    $.each(optionData, function (index, item) {
                        var label = item.label;
                        var value = item.value;
                        var checked = item.checked;
                        var $checkboxItem = $('<span style="margin-right:26px;margin-bottom:8px;display:inline-block;">'
                            + '<input type="checkbox" ' + (checked ? "checked" : "") + '  data-value="' + value + '" value="' + value + '" name="' + name + '" style="width:18px;height:18px"/> '
                            + label
                            + '</span>');
                        $checkbox.append($checkboxItem);
                    });

                    $con.append($checkbox)
                    break;
                default:
                    break
            }
            return $con
        }
        //表单子元素构造函数end
        if ($me.$formTable) {
            $.each($me.conditions, function (index, condition) {
                if (condition) {
                    var item = new FormItem(condition, index)
                    var $con = item.$dom
                    var cellName = item.name
                    var $label = item.$label
                    if (item.type !== "hidden") {
                        $me.$formTable.find("td[data-cellName=" + cellName + "]").html($con)
                        $me.$formTable.find("th[data-cellName=" + cellName + "]").html($label)
                    } else {
                        $me.$formTable.append($con)
                    }
                    $me.items.push(item);
                }
            })
            $detailForm.append($me.$formTable);
        } else {
            var $detailFormList = []
            $.each($me.conditions, function (index, condition) {
                $detailFormList.push($('<div data-lineNum="' + index + '" data-children=0></div>'))
            })
            $.each($me.conditions, function (index, condition) {
                if (condition) {
                    var item = new FormItem(condition, index)
                    var $con = item.$dom
                    var $label = item.$label
                    var lineNum = condition.lineNum === 0 ? 0 : condition.lineNum || index
                    var conCol = condition.conCol
                    var labelCol = condition.labelCol || 2;

                    //联动选中不需要label
                    if (item.type === "linkage") {
                        labelCol = 0;
                        $label = $('<i></i>');

                        //需要为每一项子集设置this方法
                        var setThisFn = function (obj) {
                            if (obj) {
                                obj.rootType = 'linkage';//暂时没用 
                                //设置chang事件
                                obj.onChange = function (val, that) {
                                    //$con切换时会影响子级 

                                    var childName = obj.children ? obj.children.name : false;
                                    if (val && childName) {
                                        var childrenDom = '';
                                        var _searchChildren = '';//带搜索框的下拉特有的
                                        if (obj.children.type === 'selectSearch') {
                                            childrenDom = that.$dom.siblings().find('[name=' + childName + '_searchInput]');
                                            _searchChildren = that.$dom.siblings().find('[name=' + childName + ']');
                                        } else {
                                            childrenDom = that.$dom.siblings().find('[name=' + childName + ']');
                                        }
                                        //除了需要更改disabled还需要赋值
                                        childrenDom.attr('disabled', false);
                                        childrenDom.attr('parentValue', val); //给子集设置父级的id
                                        childrenDom.val('');
                                        if (_searchChildren) {
                                            _searchChildren.val('');
                                            _searchChildren.attr('disabled', false);
                                            _searchChildren.attr('parentValue', val); //给子集设置父级的id
                                        }

                                        //给子集设置值
                                        if (obj.ajax && obj.ajax.setChildrenValue) {
                                            var _api = obj.ajax.setChildrenValue.api;
                                            var _params = obj.ajax.setChildrenValue.params || {};
                                            var parentK = obj.ajax.setChildrenValue.parentParamsField;
                                            var _textName = obj.ajax.setChildrenValue.textName;
                                            if (parentK) {
                                                _params[parentK] = childrenDom.attr('parentValue');
                                            }
                                            l.ajax(_api, _params, function (data, messags, success) {
                                                if (success) {
                                                    childrenDom.val(data[_textName]);
                                                } else {
                                                    childrenDom.val(messags);
                                                }
                                            }, true)
                                        }

                                        //判断父级要是值为空的话讲设置disabled 
                                        var setCDisabled = function (obj) {
                                            if (obj) {
                                                var name = obj.name;
                                                var dom = '';
                                                var _domsearchChildren = '';//带搜索框的下拉特有的 

                                                if (obj.type === 'selectSearch') {
                                                    _domsearchChildren = that.$dom.siblings().find('[name=' + name + '_searchInput]');
                                                    dom = that.$dom.siblings().find('[name=' + name + ']');
                                                } else {
                                                    dom = that.$dom.siblings().find('[name=' + name + ']')
                                                }
                                                if (!dom.val() && that.name !== name) {
                                                    if (obj.children) {
                                                        //需要将children disabled
                                                        var childName = obj.children.name;
                                                        var childrenDom = '';
                                                        var _searchChildren = '';//带搜索框的下拉特有的
                                                        if (obj.type === 'selectSearch') {
                                                            _searchChildren = that.$dom.siblings().find('[name=' + childName + '_searchInput]');
                                                            childrenDom = that.$dom.siblings().find('[name=' + childName + ']');
                                                        } else {
                                                            childrenDom = that.$dom.siblings().find('[name=' + childName + ']')
                                                        }

                                                        //除了需要更改disabled还需要赋值
                                                        childrenDom.attr('disabled', true);
                                                        _searchChildren.attr('disabled', true);
                                                        _searchChildren.val('');
                                                        childrenDom.val('');
                                                    }
                                                }

                                                setCDisabled(obj.children);
                                            }
                                        }
                                        setCDisabled(obj);
                                    }
                                }
                                var item = new FormItem(obj, index);
                                var $con = item.$dom;
                                var $label = item.$label;
                                var lineNum = condition.lineNum === 0 ? 0 : condition.lineNum || index;
                                var conCol = condition.conCol;
                                var labelCol = condition.labelCol || 2;
                                $detailFormList[lineNum].attr("class", "row cl");
                                var len = parseInt($detailFormList[lineNum].attr("data-children"));
                                len = len ? len + 1 : 1
                                $detailFormList[lineNum].attr("data-children", len)
                                conCol = conCol === 0 ? 0 : conCol || parseInt((12 - len * labelCol) / len);
                                $label.attr("class", "form-label col-" + labelCol + " col-offset-" + (12 / len - (conCol + labelCol)) + " f-l")
                                $con.attr("class", "col-10 f-l mb-10");
                                $detailFormList[lineNum].append($label);
                                $detailFormList[lineNum].append($con);
                                $me.items.push(item);
                                setThisFn(obj.children)
                            }
                        }
                        setThisFn(item.children)
                    }


                    if (item.type !== "hidden") {
                        $detailFormList[lineNum].attr("class", "row cl")
                        var len = parseInt($detailFormList[lineNum].attr("data-children"))
                        len = len ? len + 1 : 1
                        $detailFormList[lineNum].attr("data-children", len)
                        conCol = conCol === 0 ? 0 : conCol || parseInt((12 - len * labelCol) / len)
                        $label.attr("class", "form-label col-" + labelCol + " col-offset-" + (12 / len - (conCol + labelCol)) + " f-l")
                        $detailFormList[lineNum].append($label)
                        $detailFormList[lineNum].append($con)
                        if (item.type !== 'linkage') {
                            $detailFormList[lineNum].children("div").attr("class", "col-" + conCol + " f-l");
                        }
                    } else {
                        $detailFormList[lineNum].append($con)

                    }

                    $me.items.push(item);
                }
            })
            $detailForm.append($detailFormList);
        }
        $detailForm.append($detailFormControlLine)
        $page.append($detailForm)
        $me.html($page)
        //结构构建send

        // 打开弹窗start
        $me.open = function (data) {
            $me.setOpenData(data, $me._open)
        }
        // 打开弹窗start

        // 判断数据是否初始化成功start
        $me.isAjaxInit = function () {
            for (var key in $me.ajaxing) {
                if ($me.ajaxing[key]) {
                    return false
                }
            }
            return true
        }
        // 判断数据是否初始化成功start

        // 设置弹窗打开形式start
        $me.setOpenData = function (data, callback) {
            var setTimer = setInterval(function () {
                if ($me.isAjaxInit()) {
                    clearInterval(setTimer)
                    if (!data) {
                        data = { isAdd: true };
                        $me.isAdd = true;
                    } else if (data.isAdd) {
                        $me.isAdd = true
                    } else {
                        $me.isAdd = false;
                    }
                    if ($me.controlKey) {
                        $me.controlValue = data[$me.controlKey]
                    }
                    for (var i = 0; i < $me.items.length; i++) {
                        if ($me.isHide($me.items[i])) {
                            $me.items[i].$dom.parent().hide()
                        } else {
                            $me.items[i].$dom.parent().show()
                            if ($me.isLabel($me.items[i])) {//特殊
                                $me.items[i].$label.find('span').html($me.items[i].label + "(分)")
                            } else {
                                $me.items[i].$label.find('span').html($me.items[i].label)
                            }
                            switch ($me.items[i].type) {
                                case "select":
                                    if (!$me.isAdd) {
                                        if ($me.items[i].ajax && $me.items[i].ajax.parent) {
                                            $.each($me.selectListDatas[$me.items[i].ajax.parent], function (index, v) {
                                                if (v.value == data[$me.items[i].ajax.parent]) {
                                                    $me.selectListDatas[$me.items[i].name] = [];
                                                    $.each(v.children || [], function (index2, v2) {
                                                        $me.selectListDatas[$me.items[i].name].push({
                                                            value: v2[$me.items[i].ajax.valueName || "value"] || "",
                                                            text: v2[$me.items[i].ajax.textName || "text"] || "",
                                                            children: v2[$me.items[i].ajax.childrenName || "children"] || [],
                                                            selected: (v2[$me.items[i].ajax.valueName || "value"] == data[$me.items[i].name]) ? true : false,
                                                        })
                                                    })
                                                    $me.find("select[name=" + $me.items[i].name + "]").attr("disabled", $me.items[i].immutableAdd || $me.items[i].immutable || false);
                                                    $me.resetSelectHtml($me.selectListDatas[$me.items[i].name], $me.find("select[name=" + $me.items[i].name + "]"))
                                                }
                                            });
                                            $me.items[i].setValue(data[$me.items[i].name]);
                                        } else {
                                            $me.items[i].setValue(data[$me.items[i].name]);
                                        }
                                    } else {
                                        if ($me.items[i].ajax && $me.items[i].ajax.parent) {
                                            $me.selectListDatas[$me.items[i].name] = []
                                            $.each($me.selectListDatas[$me.items[i].ajax.parent][0].children || [], function (index, v) {
                                                $me.selectListDatas[$me.items[i].name].push({
                                                    value: v[$me.items[i].ajax.valueName || "value"] || "",
                                                    text: v[$me.items[i].ajax.textName || "text"] || "",
                                                    children: v[$me.items[i].ajax.childrenName || "children"] || [],
                                                })
                                            })
                                            $me.find("select[name=" + $me.items[i].name + "]").attr("disabled", $me.items[i].immutableAdd || false);
                                            $me.resetSelectHtml($me.selectListDatas[$me.items[i].name], $me.find("select[name=" + $me.items[i].name + "]"))
                                        } else {
                                            $me.items[i].setValue("")
                                        }
                                    }
                                    break;
                                case "pickMember":
                                    $me.items[i].setValue([data["oaDepartmentList"], data["oaMemberList"]])
                                    break;
                                case "selectSearch":
                                    $me.items[i].setValue(data[$me.items[i].name], data)
                                    break;
                                case "checkbox":
                                    //新增时候先清空然后赋值   
                                    $me.items[i].setValue(data[$me.items[i].name]);
                                    break;
                                default://hidden,text,number,textarea,date,address,option,pickTree,upload 
                                    $me.items[i].setValue(data[$me.items[i].name]);
                                    break
                            }
                        }
                    }
                    if (callback) {
                        callback(data.layerTitle, data.layerArea)
                    }
                }
            }, 10)
        };
        // 设置弹窗打开形式end

        // 打开弹窗start
        $me._open = function (layerTitle, layerArea) {
            $me.mylayer = layer.open({
                type: 1,
                title: layerTitle || $me.layerTitle || ($me.isAdd ? '新增' : "详情"),
                area: layerArea || $me.layerArea || ['720px', '420px'],
                content: $me
            });
        };
        // 打开弹窗end

        // 获取数据start
        $me.getDatas = function () {
            var data = {};
            var err = [];
            for (var i = 0; i < $me.items.length; i++) {
                if (!$me.isHide($me.items[i])) {
                    switch ($me.items[i].type) {
                        case "option":
                            var d = $me.items[i].getValue()
                            if ($me.items[i].must && d.length < $me.items[i].minOptionNum) {
                                err.push("至少添加" + $me.items[i].minOptionNum + "个选项！")
                            }
                            data[$me.items[i].name] = d;
                            break
                        case "pickMember":
                            if ($me.items[i].must && getPickMember("department").length == 0 && getPickMember("member").length == 0) {
                                err.push($me.items[i].label + "不能为空！")
                            }
                            data["oaDepartmentList"] = getPickMember("department")
                            data["oaMemberList"] = getPickMember("member")
                            break;

                        case "upload":
                            data[$me.items[i].name] = getUploadList($me.idName + $me.items[i].name)
                            if ($me.items[i].must && !data[$me.items[i].name].length) {
                                err.push($me.items[i].label + "未上传！")
                            }
                            break;
                        case "pickTree":
                            var d = $me.items[i].getValue()
                            var len = 0;
                            data[$me.items[i].name] = {};
                            for (var key in $me.items[i].pickType) {
                                if ($me.items[i].pickType[key]) {
                                    len = len + d[key].length;
                                    data[$me.items[i].name][$me.items[i].pickType[key]] = d[key];
                                }
                            }
                            if ($me.items[i].must && len == 0) {
                                err.push($me.items[i].label + "不能为空！")
                            }
                            if ($me.items[i].noMultiple && len > 1) {
                                err.push($me.items[i].label + "只能选择一个!")
                            }
                            break;
                        case "editor":
                            var d = $me.items[i].getValue()
                            if ($me.items[i].must && $me.items[i].ue.hasContents()) {
                                err.push($me.items[i].label + "不能为空！")
                            }
                            data[$me.items[i].name] = d
                            break
                        case "linkage":
                            var d = $me.items[i].getValue();
                            break
                        default://hidden,text,number,textarea,date,select,address
                            var d = $me.items[i].getValue()
                            if ($me.items[i].must && $.trim(d) == "") {
                                err.push($me.items[i].label + "不能为空！")
                            }
                            data[$me.items[i].name] = $me.items[i].type == "date" ? l.dateToUnix(d) : d
                            break
                    }
                }
            }
            return {
                err: err,
                data: data
            }
        }
        // 获取数据end

        // 关闭弹窗start
        $me.close = function () {
            layer.close($me.mylayer)
        };
        // 关闭弹窗end
        // 重置默认值start
        $me.resetDefaultValue = function (name, newDefaultValue) {
            for (var i = 0; i < $me.items.length; i++) {
                if ($me.items[i].name == name) {
                    $me.items[i].defaultValue = newDefaultValue;
                    break;
                }
            }
        }
        // 重置默认值end

        // 根据controlValue字段判断是否隐藏start
        $me.isHide = function (item) {
            var hideState = false;
            if (item.controlHide) {
                var hideArr = item.controlHide.split(",");
                for (var j = 0; j < hideArr.length; j++) {
                    if ($me.controlValue == hideArr[j]) {
                        hideState = true; break
                    }
                }
            }
            return hideState
        }
        // 根据controlValue字段判断是否隐藏end

        // 根据controlValue字段判断是否改变Label start
        $me.isLabel = function (item) {
            var labelState = false;
            if (item.controlLabel) {
                var labelArr = item.controlLabel.split(",");
                for (var j = 0; j < labelArr.length; j++) {
                    if ($me.controlValue == labelArr[j]) {
                        labelState = true; break
                    }
                }
            }
            return labelState
        }
        // 根据controlValue字段判断是否改变Label end
        return $me
    }
}(jQuery, Apih5)
var wConfirm = window.confirm;
window.confirm = function (message) {
    try {
        var iframe = document.createElement("IFRAME");
        iframe.style.display = "none";
        iframe.setAttribute("src", 'data:text/plain,');
        document.documentElement.appendChild(iframe);
        var alertFrame = window.frames[0];
        var iwindow = alertFrame.window;
        if (iwindow == undefined) {
            iwindow = alertFrame.contentWindow;
        }
        var result = iwindow.confirm(message);
        iframe.parentNode.removeChild(iframe);
        return result;
    }
    catch (exc) {
        return wConfirm(message);
    }
}