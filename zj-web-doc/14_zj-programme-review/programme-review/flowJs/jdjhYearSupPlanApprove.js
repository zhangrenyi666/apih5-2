var flowId = l.getUrlParam("flowId") || "yearSupPlan"; //流程模版id
var code = l.getUrlParam("code");
var type = l.getUrlParam("type"); //详情需要将所有按钮隐藏 detail edit
var yearSupPlanId = l.getUrlParam("id") || "";
Apih5.setCookie("code", code, 30);

var $tabSystem = $("#tab-system"); //模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>'); //tab按钮控制条
var tabCons = []; //tab内容页面组
var table, pagerDom, detailForm;
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
  name: "年度监督计划",
  titleName: "sealApplyName",
  tabs: [
    {
      name: "基本信息",
      type: "1", //普通tab页1，附件tab页2，列表tab页面3
      isMain: "1", //是否为主表
      tbName: "zjJdjhYearSupPlan", //表名
      tbIdName: "yearSupPlanId", //表主键id
      conditions: [
        {
          type: "hidden", //
          name: "flowId", //
          label: "flowId", //
          placeholder: "请输入",
          defaultValue: "yearSupPlan"
        },
        {
          type: "hidden", //
          name: "yearSupPlanId", //
          label: "yearSupPlanId", //
          placeholder: "请输入",
          defaultValue: yearSupPlanId
        },
        {
          type: "pickTree", //
          name: "oaDutyDep", //接口字段名
          label: "监督责任部门", //
          must: true,
          immutable: yearSupPlanId ? true : false,
          pickType: {
            department: "oaDepartmentList", //部门列表对应接口字段名,false表示不开启该类
            member: false //成员列表对应接口字段名,false表示不开启该类
          }
        },
        {
          type: "text",
          name: "informant",
          label: "填报人",
          must: true
        },
        {
          type: "select",
          name: "year",
          label: "年度",
          immutable: yearSupPlanId ? true : false,
          selectList: [
            //当类型为select时，option配置  0:全公司  1：机关  2：项目
            {
              value: "",
              text: "请选择"
            },
            {
              value: "0",
              text: "2019"
            },
            {
              value: "1",
              text: "2020"
            },
            {
              value: "2",
              text: "2021"
            },
            {
              value: "3",
              text: "2022"
            },
            {
              value: "4",
              text: "2023"
            }
          ],
          must: true
        }
      ]
    },
    {
      //详情弹窗
      name: "检查项",
      type: "3", //table是表格
      tbName: "",
      tbIdName: "fileId"
    }
  ]
};

$.each(flowFormJson.tabs, function(i, tabItem) {
  //第一次遍历flowFormJson.tabs
  var $tabBtn = $("<span>" + tabItem.name + "</span>"); //创建tab按钮$对象
  $tabBar.append($tabBtn); //向tab按钮控制条插入tab按钮
  var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>'); //创建tab内容页面$对象
  var customBtnGroup; //tab内容页面中表单的底部按钮组配置
  if (tabItem.isMain) {
    //如果是主表单
    customBtnGroup = {
      btns:
        type === "detail"
          ? []
          : [
              {
                name: "add",
                label: '<i class="Hui-iconfont">&#xe600;</i> 确定'
              },
              {
                name: "launch",
                label: '<i class="Hui-iconfont">&#xe603;</i> 发起'
              },
              {
                name: "cancel",
                label: "取消"
              }
            ],
      callback: function(dataName, obj) {
        switch (dataName) {
          case "add":
            var body = {};
            for (var j = 0; j < flowFormJson.tabs.length; j++) {
              //第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环

              var tabItemj = flowFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象
              if (tabCons[j].getDatas) {
                var tabObjDatas = tabCons[j].getDatas(); //tab内容页面组的遍历对象获取数据对象
                if (tabObjDatas.err && tabObjDatas.err.length) {
                  //判断是否有错误（字段不能为空、超过个数限制等）
                  layer.alert(
                    tabObjDatas.err.join("<br/>"),
                    { icon: 0 },
                    function(index) {
                      $tabSystem.Huitab({
                        index: j
                      });
                      layer.close(index);
                    }
                  );
                  return; //直接停止for循环，且for循环之后的代码也不执行
                }
                body = tabObjDatas.data;
              }
            }
            var apiName = yearSupPlanId
              ? "updateZjJdjhYearSupPlan"
              : "addZjJdjhYearSupPlan";
            l.ajax(apiName, body, function(data, message, success) {
              if (success) {
                yearSupPlanId = data.yearSupPlanId;
                pager.page("remote", { yearSupPlanId: yearSupPlanId });
                layer.alert(message, { icon: 0 }, function(index) {
                  parent.pager.page("remote");
                  layer.close(index);
                  $("#tab-system").Huitab({
                    index: 1
                  });
                });
              }
            });
            break;
          case "launch":
            if (!yearSupPlanId) {
              layer.alert("请先提交基本信息！", { icon: 0 }, function(index) {
                layer.close(index);
                $("#tab-system").Huitab({
                  index: 0
                });
              });
              return;
            }
            var body = {
              flowId: flowId, //流程id
              yearSupPlanId: yearSupPlanId
            };
            for (var j = 0; j < flowFormJson.tabs.length; j++) {
              //第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
              var tabItemj = flowFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象
              if (tabCons[j].getDatas) {
                var tabObjDatas = tabCons[j].getDatas(); //tab内容页面组的遍历对象获取数据对象
                if (tabObjDatas.err.length) {
                  //判断是否有错误（字段不能为空、超过个数限制等）
                  layer.alert(
                    tabObjDatas.err.join("<br/>"),
                    { icon: 0 },
                    function(index) {
                      $tabSystem.Huitab({
                        index: j
                      });
                      layer.close(index);
                    }
                  );
                  return; //直接停止for循环，且for循环之后的代码也不执行
                }
                if (tabItemj.isMain) {
                  //如果是主表
                  tabObjDatas.data.yearSupPlanId = yearSupPlanId;
                  //给主表赋值
                  body["mainTableName"] = tabItemj.tbName;
                  body["mainTablePrimaryIdName"] = tabItemj.tbIdName;
                  body["mainTableDataObject"] = tabObjDatas.data;

                  body["apiBody"] = {};
                  //add by apih5 on 717
                  for (var key in tabObjDatas.data) {
                    body["apiBody"][key] = tabObjDatas.data[key];
                  }
                  //add by apih5 on 717

                 // body["title"] = tabObjDatas.data[flowFormJson.titleName];
				 body["title"] = flowFormJson.name;
                } else if (tabItemj.type === "2") {
                  //如果是附件类型子表，type==="2"
                  //给子表赋值-附件表
                  if (!body["subTableObject"]) {
                    body["subTableObject"] = {};
                  }
                  for (var key in tabObjDatas.data) {
                    var subTableDataObject = tabObjDatas.data[key];

                    //add by apih5 on 717
                    body["apiBody"][key] = tabObjDatas.data[key];
                    //add by apih5 on 717

                    body["subTableObject"][key] = {
                      subTablePrimaryIdName: tabItemj.tbIdName,
                      subTableType: tabItemj.type,
                      subTableDataObject: subTableDataObject
                    };
                  }
                } else {
                  //如果是普通类型子表，type==="1"，目前只有1和2
                  //给子表赋值-普通表
                  if (!body["subTableObject"]) {
                    body["subTableObject"] = {};
                  }

                  //add by apih5 on 717
                  for (var key in tabObjDatas.data) {
                    body["apiBody"][key] = tabObjDatas.data[key];
                  }
                  //add by apih5 on 717

                  body["subTableObject"][tabItemj.tbName] = {
                    subTablePrimaryIdName: tabItemj.tbIdName,
                    subTableType: tabItemj.type,
                    subTableDataObject: tabObjDatas.data
                  };
                }

                //add by apih5 on 717
                body["apiName"] = "submitZjJdjhYearSupPlan"; //购置申请发起时调用
                //add by apih5 on 717
              }
            }
            //流程发起特殊代码---开始
            layer.confirm("确定发起？", { icon: 0 }, function(index) {
              //流程发起请求
              l.ajax("createOpenFlow", body, function(_d, _m, _s, _r) {
                if (_s) {
                  //发起成功，返回workId
                  loadFlow(_d.flowButtons, {
                    otherBody: {
					  title: body["title"],
                      nodeId: _d.flowNode.nodeId,
                      trackId: _d.flowNode.trackId,
                      workId: _d.workId,
                      flowVars: _d.flowVars,
                      nodeVars: _d.nodeVars,
                      apih5FlowStatus: _d.apih5FlowStatus,
                      apiName: "submitZjJdjhYearSupPlan",
                      apiBody: {
                        workId: _d.workId,
                        apih5FlowStatus: _d.apih5FlowStatus,
                        yearSupPlanId: yearSupPlanId
                      }
                    },
                    endFn: function(buttonClass) {
                      obj.close();
                    },
                    callback: function(flowObj) {
                      flowObj.flowSelectOpen(0);
                    }
                  });
                }
              });
              layer.close(index);
			//  parent.layer.close(parent.myOpenLayer);
            });
            //流程发起特殊代码---结束
            break;
          case "cancel":
          default:
            obj.close();
            break;
        }
      }
    };
  } else {
    customBtnGroup = {
      btns: [],
      callback: function(dataName, obj) {}
    };
  }
  if (tabItem.type === "3") {
    //表格
    var $con = $('<div class="page-container"></div>'); //
    var $table = $(
      '<table id="table" class="table table-border table-bordered table-bg table-hover"></table>'
    );
    var $cl = $('<div class="cl"></div>');
    pagerDom = $('<div id="pager" class="m-pagination f-r"></div>');
    var $btnCon = $('<div class="f-l mt-10"></div>');
    var $add = $(
      '<button data-name="add" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe600;</i> 新增</button>'
    );
    var $update = $(
      '<button data-name="edit" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe6df;</i> 编辑</button>'
    );
    var $del = $(
      '<button data-name="del" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe6e2;</i> 删除</button>'
    );
    var $derive = $(
      '<button data-name="derive" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe644;</i> 导出</button>'
    );
    // var $launch = $('<button data-name="launch" data-type="TzLand" class="btn size-S  ml-10 btn-primary" type="button"><i class="Hui-iconfont">&#xe603;</i> 发起</button>');
    if (type !== "detail") {
      $btnCon.append($add);
      $btnCon.append($update);
      $btnCon.append($del);
    }
    $btnCon.append($derive);
    $cl.append($btnCon);
    $cl.append(pagerDom);
    $con.append($table);
    $con.append($cl);

    table = $table.DataTable({
      info: false, //是否显示数据信息
      paging: false, //是否开启自带分页
      ordering: false, //是否开启DataTables的高度自适应
      processing: false, //是否显示‘进度’提示
      searching: false, //是否开启自带搜索
      autoWidth: false, //是否监听宽度变化
      columnDefs: [
        //表格列配置
        {
          targets: [0],
          data: "rowIndex",
          width: 13,
          title: '<input type="checkbox">',
          render: function(data) {
            return (
              '<input type="checkbox" name="checkbox" data-rowIndex="' +
              data +
              '" >'
            );
          }
        },
        {
          targets: [1],
          data: "rowIndex",
          title: "No.",
          width: 25,
          render: function(data) {
            return data + 1;
          }
        },
        {
          targets: [2], //第几列
          data: "supMatter", //接口对应字段
          title: "监督事项", //表头名
          defaultContent: "-" //默认显示
        },
        {
          targets: [3], //第几列
          data: "planFlag", //接口对应字段
          title: "是否计划内", //表头名
          defaultContent: "-", //默认显示
          render: function(data) {
            var text = "";
            switch (data) {
              case "0":
                text = "是";
                break;
              case "1":
                text = "否";
                break;
              default:
                text = "未知";
                break;
            }
            return text;
          }
        },
        {
          targets: [4], //第几列
          data: "coopDept", //接口对应字段
          title: "配合部门", //表头名
          defaultContent: "-" //默认显示
        },
        {
          targets: [5], //第几列
          data: "scheduleTime", //接口对应字段
          title: "计划完成时间", //表头名
          defaultContent: "-", //默认显示
          render: function(data) {
            return data ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
          }
        },
        {
          targets: [6], //第几列
          data: "principal", //接口对应字段
          title: "责任人", //表头名
          defaultContent: "-" //默认显示
        },
        {
          targets: [7], //第几列
          data: "remarks", //接口对应字段
          title: "备注", //表头名
          defaultContent: "-" //默认显示
        }
      ]
    });
    $tabCon.append($con);
    tabCons[i] = $tabCon;
  } else {
    detailForm = tabCons[i] = $tabCon.detailLayer({
      customBtnGroup: customBtnGroup,
      conditions: tabItem.conditions
    });
  }
  tabCons[i].close = function() {
    parent.layer.close(parent.myOpenLayer);
  };
});

$tabSystem
  .append($tabBar)
  .append(tabCons)
  .Huitab({
    index: 0
  });

//检查项
var pager = pagerDom.page({
  remote: {
    //ajax请求配置
    url: l.getApiUrl("getZjJdjhYearSupMatterList"),
    params: {
      yearSupPlanId: yearSupPlanId
    },
    success: function(result) {
      if (result.success) {
        var data = result.data;
        $.each(data, function(index, item) {
          item.rowIndex = index;
        });
        table
          .clear()
          .rows.add(data)
          .draw();
      } else {
        layer.alert(result.message, { icon: 0 }, function(index) {
          layer.close(index);
        });
      }
    }
  }
});
loadPage();
function loadPage() {
  if (yearSupPlanId) {
    l.ajax(
      "getZjJdjhYearSupPlanDetails",
      { yearSupPlanId: yearSupPlanId },
      function(data, message, success) {
        if (success) {
          detailForm.setOpenData(data);
        }
      }
    );
  } else {
    detailForm.setOpenData({ memberList: { oaMemberList: [] } });
  }
}

var detailLayer = $("#detailLayer").detailLayer({
  layerArea: ["100%", "100%"],
  conditions: [
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "yearSupPlanId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "yearSupMatterId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "planFlag", //输入字段名
      defaultValue: "0"
    },
    {
      type: "textarea", //
      name: "supMatter", //
      label: "监督事项", //
      placeholder: "请输入监督事项"
    },
    {
      type: "text", //
      name: "coopDept", //
      label: "配合部门", //
      placeholder: "请输入"
    },
    {
      type: "date", //
      name: "scheduleTime", //
      label: "计划完成时间", //
      dateFmt: "yyyy-MM-dd",
      defaultValue: new Date(),
      id: "scheduleTime"
    },
    {
      type: "text", //
      name: "principal", //
      label: "责任人", //
      placeholder: "请输入"
    },
    {
      type: "textarea", //
      name: "remarks", //
      label: "备注", //
      placeholder: "请输入备注"
    }
  ],
  onSave: function(obj, _params) {
    l.ajax("updateZjJdjhYearSupMatter", _params, function(
      data,
      message,
      success
    ) {
      if (success) {
        pager.page("remote", { yearSupPlanId: yearSupPlanId });
        layer.alert(message, { icon: 0 }, function(index) {
          layer.close(index);
          obj.close();
        });
      }
    });
  },
  onAdd: function(obj, _params) {
    l.ajax("addZjJdjhYearSupMatter", _params, function(data, message, success) {
      if (success) {
        pager.page("remote", { yearSupPlanId: yearSupPlanId });
        layer.alert(message, { icon: 0 }, function(index) {
          layer.close(index);
          obj.close();
        });
      }
    });
  }
});
$("body").on("click", "button", function() {
  var checkedData = l.getTableCheckedData(table);
  var name = $(this).attr("data-name");
  switch (name) {
    case "add":
      if (yearSupPlanId) {
        detailLayer.open({ isAdd: true, yearSupPlanId: yearSupPlanId });
      } else {
        layer.alert(
          "请先切换到基本信息完善必填信息点击确认后再新增检查项！",
          { icon: 0 },
          function(index) {
            layer.close(index);
            $("#tab-system").Huitab({
              index: 0
            });
          }
        );
      }
      break;
    case "edit":
      if (checkedData.length == 1) {
        detailLayer.open(checkedData[0]);
      } else if (checkedData.length == 0) {
        layer.alert("未选择任何项", { icon: 0 }, function(index) {
          layer.close(index);
        });
      } else {
        layer.alert("只能选择一个", { icon: 0 }, function(index) {
          layer.close(index);
        });
      }
      break;
    case "del":
      if (checkedData.length == 0) {
        layer.alert("未选择任何项", { icon: 0 }, function(index) {
          layer.close(index);
        });
      } else {
        layer.confirm("确定删除？", { icon: 0 }, function(index) {
          l.ajax("batchDeleteUpdateZjJdjhYearSupMatter", checkedData, function(
            data,
            success,
            message
          ) {
            if (success) {
              pager.page("remote");
            }
          });
          layer.close(index);
        });
      }
      break;
    case "derive": //党费使用导出
      var params = { yearSupPlanId: yearSupPlanId };
      layer.alert("确定导出此数据？", { icon: 0 }, function(index) {
        l.ajax("exportExcelZjJdjhYearSupMatterList", params, function(res) {
          layer.alert("导出成功！", { icon: 0 }, function(index) {
            layer.close(index);
            window.location.href = res;
          });
        });
      });
      break;
    case "launch":
      var body = {
        flowId: flowId //流程id
      };
      for (var j = 0; j < flowFormJson.tabs.length; j++) {
        //第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
        var tabItemj = flowFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象
        if (tabCons[j].getDatas) {
          var tabObjDatas = tabCons[j].getDatas(); //tab内容页面组的遍历对象获取数据对象
          if (tabObjDatas.err.length) {
            //判断是否有错误（字段不能为空、超过个数限制等）
            layer.alert(tabObjDatas.err.join("<br/>"), { icon: 0 }, function(
              index
            ) {
              $tabSystem.Huitab({
                index: j
              });
              layer.close(index);
            });
            return; //直接停止for循环，且for循环之后的代码也不执行
          }
          if (tabItemj.isMain) {
            //如果是主表
            //给主表赋值
            body["mainTableName"] = tabItemj.tbName;
            body["mainTablePrimaryIdName"] = tabItemj.tbIdName;
            body["mainTableDataObject"] = tabObjDatas.data;

            body["apiBody"] = {};
            //add by apih5 on 717
            for (var key in tabObjDatas.data) {
              body["apiBody"][key] = tabObjDatas.data[key];
            }
            //add by apih5 on 717

            //body["title"] = tabObjDatas.data[flowFormJson.titleName];
			  body["title"] = flowFormJson.name;
          } else if (tabItemj.type === "2") {
            //如果是附件类型子表，type==="2"
            //给子表赋值-附件表
            if (!body["subTableObject"]) {
              body["subTableObject"] = {};
            }
            for (var key in tabObjDatas.data) {
              var subTableDataObject = tabObjDatas.data[key];

              //add by apih5 on 717
              body["apiBody"][key] = tabObjDatas.data[key];
              //add by apih5 on 717

              body["subTableObject"][key] = {
                subTablePrimaryIdName: tabItemj.tbIdName,
                subTableType: tabItemj.type,
                subTableDataObject: subTableDataObject
              };
            }
          } else {
            //如果是普通类型子表，type==="1"，目前只有1和2
            //给子表赋值-普通表
            if (!body["subTableObject"]) {
              body["subTableObject"] = {};
            }

            //add by apih5 on 717
            for (var key in tabObjDatas.data) {
              body["apiBody"][key] = tabObjDatas.data[key];
            }
            //add by apih5 on 717

            body["subTableObject"][tabItemj.tbName] = {
              subTablePrimaryIdName: tabItemj.tbIdName,
              subTableType: tabItemj.type,
              subTableDataObject: tabObjDatas.data
            };
          }

          //add by apih5 on 717
          body["apiName"] = "submitZjJdjhYearSupPlan"; //购置申请发起时调用
          //add by apih5 on 717
        }
      }
      //流程发起特殊代码---开始
      layer.confirm("确定发起？", { icon: 0 }, function(index) {
        //流程发起请求
        l.ajax("createOpenFlow", body, function(_d, _m, _s, _r) {
          if (_s) {
            //发起成功，返回workId
            loadFlow(_d.flowButtons, {
              otherBody: {
			   // title: _d.title,
			    title: body["title"],
                nodeId: _d.flowNode.nodeId,
                trackId: _d.flowNode.trackId,
                workId: _d.workId,
                flowVars: _d.flowVars,
                nodeVars: _d.nodeVars,
                apih5FlowStatus: _d.apih5FlowStatus,
                apiName: "updateFlowSealAfterSubmit",
                apiBody: {
                  workId: _d.workId,
                  apih5FlowStatus: _d.apih5FlowStatus
                }
              },
              endFn: function(buttonClass) {
                obj.close();
              },
              callback: function(flowObj) {
                flowObj.flowSelectOpen(0);
              }
            });
          }
        });
        layer.close(index);
      });
      //流程发起特殊代码---结束
      break;
    case "close":
      parent.layer.close(parent.myOpenLayer);
      break;
  }
});
