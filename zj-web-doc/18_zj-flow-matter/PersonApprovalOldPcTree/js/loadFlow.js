(function(w) {
    w.loadFlow = function loadFlow(flowButtons, options) {
        //0、域常量及变量
        var defaultOptions = {
                otherBody: {},
                btnContainer: null,
                endFn: function() {
                    // console.log("end")
                },
                submitFn: function() {
                    // console.log("submit")
                },
                callback: function() {
                    // console.log("callback")
                }
            },
            flowButtons = flowButtons || [],
            options = $.extend({}, defaultOptions, options),
            actionData = {},
            loadedNodes = {},
            flowLayer,
            $div = $("<div></div>"),
            $btns = [],
            btns = [],
            submitFn = options.submitFn,
            callback = options.callback,
            btnContainer = options.btnContainer,
            endFn = options.endFn,
            formData = {},
            otherBody = options.otherBody;
        //1、创建总容器
        var $container = $div
            .clone()
            .attr("style", "display:none;padding:20px 40px;"); //操作弹窗的容器
        //2、创建并添加流程操作按钮
        // console.log("生成流程控制按钮组：", flowButtons)
        for (var i = 0; i < flowButtons.length; i++) {
            var btnObject = createFlowActionBtn(i, flowButtons);
            $btns.push(btnObject.$btn);
            btns.push(btnObject.btn);
        }

        //3、创建并添加流程选择容器
        var $flowSelectContainer = $div.clone();
        $container.append($flowSelectContainer);
        //4、创建并添加信息框
        var $msgbox = $('<div style="color:red"></div>');
        $container.append($msgbox);
        //5、创建并添加流程选择提交关闭按钮
        var $submitBtn = $(
            '<button data-name="flowSelectConfirm" data-type="submit" class="btn size-S ml-10 btn-primary" type="button"><i class="Hui-iconfont" >&#xe644;</i> 提交</button>'
        );
        var $closeBtn = $(
            '<button data-name="flowSelectConfirm" data-type="close"  class="btn size-S ml-10 btn-primary" type="button"><i class="Hui-iconfont" >&#xe644;</i> 关闭</button>'
        );
        var $flowSelectConfirmContainer = $div.clone();
        $flowSelectConfirmContainer.append($submitBtn).append($closeBtn);
        $container.append($flowSelectConfirmContainer);
        //6、添加总容器
        $("body").append($container);
        //7、绑定按钮事件
        $flowSelectContainer.on(
            "click",
            '[data-name="flowSelect"]',
            flowSelect
        );
        $submitBtn.on("click", flowSelectSubmit);
        $closeBtn.on("click", flowSelectClose);

        function createFlowActionBtn(buttonIndex, flowButtons) {
            var buttonName = flowButtons[buttonIndex]["buttonName"];
            var $btn = $(
                '<button class="btn size-S ml-10 btn-primary" type="button"></button >'
            );
            $btn.attr({ "data-buttonIndex": buttonIndex });
            $btn.html(buttonName);
            $btn.on("click", function(event) {
                var ele = event.target || event.srcElement;
                var $ele = $(ele);
                var _buttonIndex = $ele.attr("data-buttonIndex");
                flowSelectOpen(_buttonIndex);
            });
            return {
                $btn: $btn,
                btn: {
                    name: buttonIndex,
                    label: buttonName
                }
            };
        }
        function flowSelectOpen(index, newformData) {
            actionData = {};
            loadedNodes = {};
            formData = newformData || {};
            var buttonId = flowButtons[index]["buttonId"];
            var buttonName = flowButtons[index]["buttonName"];
            var buttonClass = flowButtons[index]["buttonClass"];
            // console.log("点击流程控制按钮：", buttonClass)
            actionData = {
                operate: buttonId,
                operateClazz: buttonClass,
                operateText: buttonName,
                operateFlag: 1,
                reOpen: false
            };
            var body = {
                actionData: JSON.stringify(actionData),
                title: newformData ? newformData["title"] : ""
            };

            for (var key in otherBody) {
                if (otherBody.hasOwnProperty(key)) {
                    body[key] = otherBody[key];
                }
            }
            for (var key in formData) {
                if (formData.hasOwnProperty(key)) {
                    body[key] = formData[key];
                }
            }
            switch (buttonClass) {
                case "com.horizon.wf.action.ActionSubmit":
                    // console.log("打开弹窗请求：", body)
                    l.ajax("actionFlow", body, function(_d, _m, _s, _r) {
                        if (_s) {
                            // console.log("打开弹窗请求结果：", _d)
                            actionData["nextNodes"] = _d.nextNodes;
                            actionData["operate"] = _d.operate;
                            actionData["operateClazz"] = _d.operateClazz;
                            actionData["operateText"] = _d.operateText;
                            actionData["operateFlag"] = _d.operateFlag;
                            $flowSelectContainer.html("");
                            var $radioBox = $(
                                '<input data-name="flowSelect" type="radio"/>'
                            );
                            var $checkBox = $(
                                '<input data-name="flowSelect" type="checkbox"/>'
                            );
                            var $label = $("<label></label>");
                            var $tabSystem = $("<div></div>");
                            $.each(_d.nextNodes, function(index, item) {
                                var $line = $div.clone();
                                var selectName = item.selectName;
                                var nodeGroup =
                                    item.nodeGroup || "nodeCheckBox";
                                var value = item.selectId;
                                var nodeId = item.nodeId;
                                var isDone = item.isDone;
                                var $tabSystemC = $tabSystem
                                    .clone()
                                    .attr("data-selectValue", value)
                                    .attr("data-selectName", nodeGroup);
                                var $lineC = $label.clone().text(selectName);

                                if (_d.nextNodes.length > 1) {
                                    var $selectBox =
                                        nodeGroup !== "nodeCheckBox"
                                            ? $radioBox.clone()
                                            : $checkBox.clone();
                                    // $selectBox.attr("name", nodeGroup).attr("disabled", isDone == "true").val(value)
                                    $selectBox
                                        .attr("name", nodeGroup)
                                        .attr("disabled", false)
                                        .val(value);
                                    if (isDone == "true") {
                                        $lineC.css({
                                            color: "#999"
                                        });
                                    }
                                    $lineC.prepend($selectBox);
                                } else if (_d.nextNodes.length) {
                                    loadedNodes[value] = {};
                                    var $tabBar = $(
                                        '<div class="tabBar cl"></div>'
                                    );
                                    var $tabBtn = $("<span></span>"); //创建tab按钮$对象
                                    var authors = item.authors;
                                    if (authors) {
                                        var iMax = 0;
                                        for (var i = 0; i < 3; i++) {
                                            var key = "";
                                            switch (i) {
                                                case 0:
                                                    key = "Author";
                                                    break;
                                                case 1:
                                                    key = "SecondAuthor";
                                                    break;
                                                case 2:
                                                    key = "Reader";
                                                    break;
                                            }
                                            var author = authors[key];
                                            if (
                                                !author ||
                                                (author &&
                                                    author.isFree != "true")
                                            ) {
                                                iMax++;
                                                continue;
                                            }
                                            $tabBar.append(
                                                $tabBtn
                                                    .clone()
                                                    .text(author.authorName)
                                            );
                                            var $tabCon = $(
                                                '<div class="tabCon"></div>'
                                            ).attr("id", key);
                                            var conditions = [
                                                {
                                                    type: "pickTree",
                                                    name: "selectAuthorApih5",
                                                    label: author.authorName,
                                                    must:
                                                        author.required ==
                                                        "true",
                                                    immutableAdd:
                                                        author.isSelect !=
                                                        "true",
                                                    maxLen:
                                                        author.selectType ==
                                                        "radio"
                                                            ? 1
                                                            : false,
                                                    otherMemberData:
                                                        author.initAuthorApih5Old ||
                                                        [],
                                                    pickType: {
                                                        member: "oaMemberList", //成员列表对应接口字段名,false表示不开启该类
                                                        department:
                                                            author.isUser ==
                                                            "true"
                                                                ? false
                                                                : "oaDepartment" //成员列表对应接口字段名,false表示不开启该类
                                                    }
                                                }
                                            ];
                                            loadedNodes[value][
                                                key
                                            ] = $tabCon.detailLayer({
                                                conditions: conditions,
                                                customBtnGroup: {
                                                    btns: [],
                                                    callback: function(
                                                        dataName,
                                                        obj
                                                    ) {}
                                                }
                                            });

                                            loadedNodes[value][key].setOpenData(
                                                {
                                                    selectAuthorApih5:
                                                        author.selectAuthorApih5 ||
                                                        {}
                                                }
                                            );
                                            $tabSystemC.append(
                                                loadedNodes[value][key]
                                            );
                                        }
                                        if (iMax !== 3) {
                                            $tabSystemC.prepend($tabBar);
                                            $tabSystemC.Huitab({
                                                index: 0
                                            });
                                        }
                                    } else {
                                        $tabSystemC.html(_d.operateMsg);
                                    }
                                }
                                $line.append($lineC);
                                $line.append($tabSystemC);
                                $flowSelectContainer.append($line);
                            });

                            flowLayer = layer.open({
                                type: 1,
                                title: _d.operateMsg,
                                area: ["800px", "80%"],
                                content: $container,
                                end: function() {
                                    endFn(buttonClass);
                                }
                            });
                        }
                    });
                    break;
                default:
                    l.ajax("actionFlow", body, function(_d, _m, _s, _r) {
                        if (_s) {
                            endFn(buttonClass);
                        }
                    });
                    break;
            }
        }
        function flowSelect(event) {
            var ele = event.target || event.srcElement;
            var dataIndex;
            var name = $(ele).attr("name");
            var value = $(ele).val();
            var checked = $(ele).is(":checked");
            var type = $(ele).attr("type");
            var $tabSystemC = $(
                '[data-selectName="' +
                    name +
                    '"][data-selectValue="' +
                    value +
                    '"]'
            );

            function callback() {
                if (type === "radio") {
                    $('[data-selectName="' + name + '"]').hide();
                }
                if (checked) {
                    $tabSystemC.show();
                } else {
                    $tabSystemC.hide();
                }
            }
            var nextNodes = actionData.nextNodes;
            for (var index = 0; index < nextNodes.length; index++) {
                if (nextNodes[index].selectId === value) {
                    dataIndex = index;
                    nextNodes[index].isSelect = checked ? "true" : "false";
                } else if (type === "radio") {
                    nextNodes[index].isSelect = "false";
                }
            }
            // console.log("点击选择流程路径：", value, "；是否已加载：", !!loadedNodes[value])
            if (!loadedNodes[value]) {
                loadedNodes[value] = {};
                $tabSystemC.html("loading...").show();
                var body = { actionData: JSON.stringify(actionData) };
                for (var key in otherBody) {
                    if (otherBody.hasOwnProperty(key)) {
                        body[key] = otherBody[key];
                    }
                }
                for (var key in formData) {
                    if (formData.hasOwnProperty(key)) {
                        body[key] = formData[key];
                    }
                }
                // console.log("选择流程请求数据：", body);
                l.ajax("actionFlow", body, function(_d, _m, _s, _r) {
                    if (_s) {
                        // console.log("选择流程返回数据：", _d);
                        var authors = {};
                        for (var i = 0; i < _d.nextNodes.length; i++) {
                            if (_d.nextNodes[i].selectId == value) {
                                authors = _d.nextNodes[i].authors;
                                break;
                            }
                        }
                        actionData["nextNodes"][dataIndex]["authors"] =
                            _d.nextNodes[i].authors;
                        actionData["operate"] = _d.operate;
                        actionData["operateClazz"] = _d.operateClazz;
                        actionData["operateText"] = _d.operateText;
                        actionData["operateFlag"] = _d.operateFlag;
                        $tabSystemC.html("").hide();
                        var $tabBar = $('<div class="tabBar cl"></div>');
                        var $tabBtn = $("<span></span>"); //创建tab按钮$对象
                        if (authors) {
                            var iMax = 0;
                            for (var i = 0; i < 3; i++) {
                                var key = "";
                                switch (i) {
                                    case 0:
                                        key = "Author";
                                        break;
                                    case 1:
                                        key = "SecondAuthor";
                                        break;
                                    case 2:
                                        key = "Reader";
                                        break;
                                }
                                var author = authors[key];
                                if (
                                    !author ||
                                    (author && author.isFree != "true")
                                ) {
                                    iMax++;
                                    continue;
                                }
                                $tabBar.append(
                                    $tabBtn.clone().text(author.authorName)
                                );
                                var $tabCon = $(
                                    '<div class="tabCon"></div>'
                                ).attr(
                                    "id",
                                    value.replace("~", "_") + key + dataIndex
                                );
                                var conditions = [
                                    {
                                        type: "pickTree",
                                        name: "selectAuthorApih5",
                                        label: author.authorName,
                                        must: author.required == "true",
                                        immutableAdd: author.isSelect != "true",
                                        maxLen:
                                            author.selectType == "radio"
                                                ? 1
                                                : false,
                                        otherMemberData:
                                            author.initAuthorApih5Old || [],
                                        pickType: {
                                            member: "oaMemberList", //成员列表对应接口字段名,false表示不开启该类
                                            department:
                                                author.isUser == "true"
                                                    ? false
                                                    : "oaDepartment" //成员列表对应接口字段名,false表示不开启该类
                                        }
                                    }
                                ];
                                loadedNodes[value][key] = $tabCon.detailLayer({
                                    conditions: conditions,
                                    customBtnGroup: {
                                        btns: [],
                                        callback: function(dataName, obj) {}
                                    }
                                });
                                loadedNodes[value][key].setOpenData({
                                    selectAuthorApih5:
                                        author.selectAuthorApih5 || {}
                                });
                                $tabSystemC.append(loadedNodes[value][key]);
                            }
                            if (iMax !== 3) {
                                $tabSystemC.prepend($tabBar);
                                $tabSystemC.Huitab({
                                    index: 0
                                });
                            }
                        } else {
                            $tabSystemC.html(_d.operateMsg);
                        }

                        callback();
                    }
                });
            } else {
                callback();
            }
        }
        function flowSelectSubmit() {
            var nextNodes = actionData.nextNodes;
            var hasSelect = false;
            for (var index = 0; index < nextNodes.length; index++) {
                if (nextNodes[index].isSelect == "true") {
                    hasSelect = true;
                    break;
                }
            }
            if (hasSelect) {
                for (
                    var index = 0;
                    index < actionData.nextNodes.length;
                    index++
                ) {
                    var nextNode = actionData.nextNodes[index];
                    var authors = nextNode.authors;
                    var selectId = nextNode.selectId;
                    for (var key in authors) {
                        if (authors.hasOwnProperty(key)) {
                            var selectAuthorApih5 = {};
                            if (
                                loadedNodes[selectId][key] &&
                                !loadedNodes[selectId][key].getDatas().err
                                    .length
                            ) {
                                selectAuthorApih5 = loadedNodes[selectId][
                                    key
                                ].getDatas().data["selectAuthorApih5"];
                            }
                            actionData.nextNodes[index].authors[key][
                                "selectAuthorApih5"
                            ] = selectAuthorApih5;
                        }
                    }
                }
                var body = { actionData: JSON.stringify(actionData) };
                for (var key in otherBody) {
                    if (otherBody.hasOwnProperty(key)) {
                        body[key] = otherBody[key];
                    }
                }
                for (var key in formData) {
                    if (formData.hasOwnProperty(key)) {
                        body[key] = formData[key];
                    }
                }
                // console.log("提交数据：", body)
                l.ajax("actionFlow", body, function(_d, _m, _s, _r) {
                    if (_s) {
                        // console.log("返回数据：", _d)
                        flowSelectSubmitCallback();
                    }
                });
            } else {
                $msgbox.html("请选择流程！");
            }
        }
        function flowSelectSubmitCallback() {
            layer.close(flowLayer);
            submitFn();
        }
        function flowSelectClose() {
            layer.close(flowLayer);
        }
        if (btnContainer) {
            // console.log("插入流程控制按钮组：", flowButtons)
            btnContainer.append($btns);
        }
        callback({
            flowSelectOpen: flowSelectOpen,
            btns: btns
        });
    };
})(window);
