// console.log(API)
var wxx = {
    // apiUrl:"http://qyh.apih5.com/",
    apiUrl: window.api,
    getUrlParam: function (k) {//获取地址栏参数，k未键名
        var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(m);
        if (r != null) return decodeURI(r[2]); return null;
    },
    ajax: function (apiName, params, success) {
        var loader = layer.open({
            type: 2
            , content: '加载中'
        });
        $.ajax({
            url: wxx.apiUrl + apiName,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(params),
            contentType: "application/json; charset=utf-8",
            beforeSend: function (xhr) {
                xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
                // 新增携带token请求
                var token = window.getUserInfo().token;
                xhr.setRequestHeader('token', token);//
            },
            complete: function (result) {
                layer.close(loader)
            },
            error: function (result, status, error) {//服务器响应失败处理函数
                console.log(apiName, error)
            },
            success: function (result) {
                if (result.success) {
                    // console.log(apiName, result.data)
                    success(result.data, result.message, result.success)
                } else {
                    if (result.code == '3003' || result.code == '3004') {
                        //重新获取code登录
                        window.reset();
                    } 

                    layer.open({
                        content: '<div style="font-size:30px">' + result.message + '</div>'
                        , btn: '<div style="font-size:26px;height:50px">我知道了</div>'
                    }); 
                }
            }
        })
    },
    imgLayer: function (url) {//直接把图片地址传入即可
        layer.open({
            type: 1,
            title: false,
            closeBtn: 1,
            skin: 'layui-layer-nobg', //没有背景色
            shadeClose: true,
            content: '<div"><img style="width:100%;vertical-align:middle;margin:0 auto;max-height:' + (window.innerHeight - 200) + 'px" src="' + url + '" /></div>'
        });
    },
    successMsg: function (desc, n, time) {//文字消息 返回步数  成功后延时多久后返回
        var desc = desc || '成功';
        var time = time || '1500';
        var n = n || null;
        layer.open({
            content: desc
            , skin: 'msg'
            , style: "font-size: 28px;padding: 5px"
            , success: function (index) {
                if (n && time) {
                    setTimeout(function () {
                        goBack(n)
                    }, time);
                } else {
                    layer.close(index)
                }
            }
        });
    },
    getValues: function (arr, cb) {
        var isOk = true;
        var params = {}
        $.each(arr, function (i, v) { //------------------ 获取页面数据
            var val = $('#' + v.name).val();
            params[v.name] = val;
            if (v.must && (val == '' || val == undefined) && isOk === true) {
                isOk = false;
                cb({
                    isOk: isOk,
                    label: v.label
                })
            }
        })

        if (isOk) {
            cb({
                params: params,
                isOk: isOk
            })
        }

    },
    getSelectData: function (totalData, checkList, value) {//获取选择的数据
        if (totalData) {
            var checkListArr = [];
            var data = []
            $.each(checkList, function (i, v) {
                checkListArr.push($(v).attr('sszcid'));
            })
            $.each(totalData, function (i, v) {
                $.each(checkListArr, function (ii, vv) {
                    if (v[value] == vv) {
                        data.push(v)
                    }
                })
            })
            return data;
        }
    },
    setSelectOption: function (arr) {
        $.each(arr, function (i, v) {
            wxx.ajax(v.api, {}, function (res) {
                var $str = '';
                $.each(res, function (ii, vv) {
                    $str += '<option value="' + vv[v.valueName] + '">' + vv[v.textName] + '</option>'
                })
                $('#' + v.name).html($str)
            })

        })
    },
    createFrom: function (data, defaultData, immutableList, cb) {//表单html
        var defaultData = defaultData || [];
        var selectApi = [];//选择框
        $.each(data, function (i, v) {
            if (v.type == 'select') {
                selectApi.push({
                    name: v.name,
                    api: v.ajax.api,
                    valueName: v.ajax.valueName,
                    textName: v.ajax.textName
                })
            }
        })

        if (typeof defaultData == "function") {//没传默认参数
            var cb = defaultData;
        }
        if (typeof immutableList == "function") {//没传默认参数
            var cb = immutableList;
        }
        if (typeof setSelect == "function") {//没传默认参数
            var cb = setSelect;
        } else {//编辑
            //some code...
        }

        var html = '<div class="layerFrom" style="padding: 10px 0px;box-sizing:border-box">';
        html += '<div class="listContainer  discountDetail">';
        html += '<div class="part2">';
        html += '<ul>';
        $.each(data, function (i, v) {
            html += '<li>';
            html += '<div class="left">';
            html += '<span class="text"><span style="color:red">' + (v.must ? '*' : '') + '</span>' + v.label + '</span>';
            html += '</div>';
            html += '<div class="right">';
            switch (v.type) {
                case 'text':
                    html += '<input data-disabled=' + v.immutable + ' type="' + v.type + '" id="' + v.name + '" class="input b" data-must="' + ((v.name == 'clfa' || v.name == 'remarks' || v.name == 'imageList') ? 'false' : 'true') + '" placeholder="" />';
                    break;
                case 'date':
                    html += '<input type="text" data-disabled=' + v.immutable + ' id="test1"  id="' + v.name + '" class="input b date" data-must="' + ((v.name == 'clfa' || v.name == 'remarks' || v.name == 'imageList') ? 'false' : 'true') + '" placeholder="" />';
                    // html += '<input type="date" id="'+ v.name +'" class="input b date" data-must="' + ( (v.name == 'clfa' || v.name == 'remarks' || v.name == 'imageList' ) ? 'false' : 'true' )+  '" placeholder="" />';
                    break;
                case 'select':
                    html += '<div>';
                    html += '<select  class="select" id="' + v.name + '">'
                    if (v.selectList.length > 0) {
                        $.each(v.selectList, function (i, v) {
                            html += '<option value="' + v.valueName + '">' + v.textName + '</option>'
                        })
                    } else {
                        // wxx.ajax(v.ajax.api, {}, function(res){
                        //     $.each(res, function(i, v){
                        //         html += '<option value="' + v.valueName + '">'+ v.textName +'</option>'
                        //     })
                        // })
                    }
                    html += '</select>'
                    html += '</div>';
                    break;
                case 'textarea':
                    html += '<textarea col="3" id="' + v.name + '" class="textarea b-b" data-must="' + ((v.name == 'clfa' || v.name == 'remarks' || v.name == 'imageList') ? 'false' : 'true') + '" ></textarea>'
                    break;
                case 'upload':
                    html += '<div class="right">'
                    html += '<div class="uploadFile">'
                    html += '<label for="uploadBtn" name="imageList" class="upload-btn">'
                    html += '<span style="padding-left: 10px;">' + v.btnName + '</span>'
                    html += '<input id="uploadBtn" class="inputFile" data-filetype="["jpg","jpeg","png","gif"]" name="filesName" size="2" type="file" />'
                    html += '</label>'
                    html += '<span class="require">上传格式要求：["jpg","jpeg","png","gif"]</span>'
                    html += '<ul style="height:auto;" class="uploadList" style="margin-top: 20px">'
                    html += '</ul>'
                    html += '</div>'
                    html += '</div>'
                    break;
            }
            html += '</div>';
            html += '</li>';
        })
        html += '</ul>';
        html += '</div>';
        html += '</div>';
        html += '</div>';
        var layerFrom = layer.open({
            type: 1
            , content: html
            , btn: ["<span style='font-size:30px; position:absolute; left:10%;  background:#0ebcfb; color:white; width:80%; border-raiuds:10px display:inline-block; margin-bottom:0px; '>确定<span>", "<span style='font-size:30px; position:absolute; left:10%; background:#0ebcfb; color:white; width:80%;border-raiuds:10px display:inline-block;'>取消</span>"]
            , anim: 'up'
            , style: 'position:fixed; left:0; top:0; padding-bottom:50px; overflow-y:scroll; width:100%; height:100%; border: 0px solid red; box-sizing:border-box; -webkit-animation-duration: .5s; animation-duration: .5s;'
            , yes: function (index) {
                wxx.getValues(editDetailData, function (data) {
                    if (data.isOk) {
                        cb(data.params);
                        return data.params;
                    } else {
                        layer.open({
                            type: '1',
                            content: "<h2 style='box-sizing:border-box;padding:.5rem;'>" + data.label + "为必填！</h2>",
                        })
                    }
                })
            }
        })
        wxx.setSelectOption(selectApi);//设置下拉

        laydate.render({
            elem: '#test1', //指定元素
            value: new Date()
        });

        //设置不可编辑
        $.each($(".input"), function (i, v) {
            if ($(v).attr("data-disabled") == "true") {
                $(v).attr("disabled", 'disabled')
            }
        })
        $.each(immutableList, function (i, v) {
            $("#" + v).attr("disabled", 'disabled')
        })


        if (defaultData.length > 0) {//设置默认值
            for (const key in defaultData[0]) {
                if (key == 'imageList') {
                    wxx.updateUploadList(defaultData[0][key]);
                }
                else {
                    $("#" + key).val(defaultData[0][key])
                }
            }
        }
        return layerFrom;
    },
    updateUploadList: function (uploadList) {
        var $str = "";
        $.each(uploadList, function (i, item) {
            $str += '<li data-href="' + item.fileUrl + '" data-name="' + item.fileName + '">';
            $str += '<div class="text">' + item.fileName + '</div>';
            $str += '<img class="img" onclick="clearItem($(this))" src="../img/close.png" />';
            $str += '</li>';
        });
        $(".uploadList").append($str)
    },
    getImgList: function () {
        var imageList = [];
        $(".uploadList li").each(function (i, item) {
            imageList[i] = {
                fileName: noNull(item.getAttribute("data-name")),
                fileUrl: noNull(item.getAttribute("data-href"))
            };
        });
        return imageList;
    },
    upload: function () {
        var accessoryList = [];
        if (layer.load) {
            var index = layer.load(1, {
                shade: [0.4, '#ccc'] //0.1透明度的白色背景
            });
        } else {
            var index = layer.open({ type: 2, content: '上传中' });
        }

        $.ajaxFileUpload({
            url: wxx.apiUrl + 'uploadFiles.do', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'uploadBtn', //文件上传域的ID
            dataType: 'text', //返回值类型 一般设置为json
            complete: function (result) {
                layer.close(index);
            },
            success: function (data, status) { //服务器成功响应处理函数
                data = JSON.parse(data);
                if (data.success == "true") {
                    accessoryList.push({
                        fileName: data.fileName,
                        fileUrl: data.fileUrl
                    })
                    wxx.updateUploadList(accessoryList);
                } else {
                    console.log(data.message)
                }
            },
            error: function (data, status, e) {//服务器响应失败处理函数
                console.log(e)
                // alert('失败：data:'+data+'\n----status:'+status+'\n-------e:'+e)
            }
        })

        return accessoryList;
    }

}




