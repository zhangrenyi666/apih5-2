var nodeId = l.getUrlParam("nodeId");
var workId = l.getUrlParam("workId");
var trackId = l.getUrlParam("trackId");
var yearSupPlanId = l.getUrlParam("yearSupPlanId") || "";
var title = l.getUrlParam("title") || "";

var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);

var table, pagerDom, detailForm;
var workFormJson = {
  //流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
  name: "事项待办",
  titleName: "projectTitle",
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
          defaultValue: "depSupPlah"
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
          immutableAdd: true,
          pickType: {
            department: "oaDepartmentList", //部门列表对应接口字段名,false表示不开启该类
            member: false //成员列表对应接口字段名,false表示不开启该类
          }
        },
        {
          type: "text",
          name: "informant",
          label: "填报人",
          immutableAdd: true
        },
        {
          type: "select",
          name: "year",
          label: "年度",
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
          immutableAdd: true
        },
		{
          type: "select",
          name: "quarter",
          label: "季度",
          selectList: [
            //当类型为select时，option配置  0:全公司  1：机关  2：项目
            {
              value: "",
              text: "请选择 "
            },
            {
              value: "0",
              text: "第一季度"
            },
            {
              value: "1",
              text: "第二季度"
            },
            {
              value: "2",
              text: "第三季度"
            },
            {
              value: "3",
              text: "第四季度"
            }
          ],
          immutableAdd: true
        },
        {
          type: "textarea", //
          name: "opinionField1", //
          label: "部门总经理审批", //
          placeholder: "请输入部门总经理审批",
          immutableAdd: true
        }
      ]
    },
    {
      name: "检查项",
      type: "3", //3列表tab
      tbName: "",
      tbIdName: "fileId"
    },
    {
      name: "流程进度图",
      type: "4"
    }
  ]
};

//add by lny on 717
var _body = {
 title: title,
  nodeId: nodeId,
  trackId: trackId,
  workId: workId,
  apiName: "getZjJdjhYearSupPlanDetails",
  apiBody: { workId: workId }
};

l.ajax("openFlow", _body, function(_d, _m, _s, _r) {
  if (_s) {
    loadFlow(_d.flowButtons, {
      otherBody: {
        nodeId: _d.flowNode.nodeId,
        trackId: _d.flowNode.trackId,
        workId: _d.workId,
        flowVars: _d.flowVars,
        nodeVars: _d.nodeVars,
        apiName: "submitZjJdjhYearSupPlan"
      },
      submitFn: function() {
        parent.pager.page("remote");
        parent.layer.close(parent.myOpenLayer);
      },
      callback: function(flowObj) {
        var $tabSystem = $("#tab-system"); //模版顶级jq对象
        var $tabBar = $('<div class="tabBar cl"></div>'); //tab按钮控制条
        var tabCons = []; //tab内容页面组
        var mainTableDataObject = _d.mainTableDataObject; //主表数据对象
        var subTableObject = _d.subTableObject; //子表数据对象数组
        var flowWebUrl = _d.flowWebUrl || ""; //子表数据对象数组
        var apiDataObj = JSON.parse(_d.apiData);
        yearSupPlanId = apiDataObj.yearSupPlanId;
        $.each(workFormJson.tabs, function(i, tabItem) {
          //第一次遍历workFormJson.tabs
          var $tabBtn = $("<span>" + tabItem.name + "</span>"); //创建tab按钮$对象
          $tabBar.append($tabBtn); //向tab按钮控制条插入tab按钮
          var $tabCon = $(
            '<div class="tabCon" id="tab' + i.toString() + '"></div>'
          ); //创建tab内容页面$对象
          if (tabItem.type === "4") {
            //流程图tab
            if (flowWebUrl) {
              var $iframe = $(
                '<iframe width="100%" height="600" src="' + flowWebUrl + '"/>'
              );
              tabCons[i] = $tabCon.append($iframe);
            } else {
              tabCons[i] = $tabCon.append(
                $(
                  '<div style="color:#666;text-align:center;line-height:50px">未发现流程图</div>'
                )
              );
            }
          } else if (tabItem.type === "3") {
            //列表tab
            var $con = $('<div class="page-container"></div>'); //
            var $table = $(
              '<table id="table" class="table table-border table-bordered table-bg table-hover"></table>'
            );
            var $cl = $('<div class="cl"></div>');
            pagerDom = $('<div id="pager" class="m-pagination f-r"></div>');
            var $btnCon = $('<div class="f-l mt-10"></div>');

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
                  title: "No.",
                  width: 25,
                  render: function(data) {
                    return data + 1;
                  }
                },
                {
                  targets: [1], //第几列
                  data: "supMatter", //接口对应字段
                  title: "监督事项", //表头名
                  defaultContent: "-" //默认显示
                },
                {
                  targets: [2], //第几列
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
                  targets: [3], //第几列
                  data: "coopDept", //接口对应字段
                  title: "配合部门", //表头名
                  defaultContent: "-" //默认显示
                },
                {
                  targets: [4], //第几列
                  data: "scheduleTime", //接口对应字段
                  title: "计划完成时间", //表头名
                  defaultContent: "-", //默认显示
                  render: function(data) {
                    return data
                      ? l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss")
                      : "-";
                  }
                },
                {
                  targets: [5], //第几列
                  data: "principal", //接口对应字段
                  title: "责任人", //表头名
                  defaultContent: "-" //默认显示
                },
                {
                  targets: [6], //第几列
                  data: "remarks", //接口对应字段
                  title: "备注", //表头名
                  defaultContent: "-" //默认显示
                }
              ]
            });

            $tabCon.append($con);
            tabCons[i] = $tabCon;

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
          } else {
            var customBtnGroup; //tab内容页面中表单的底部按钮组配置
            if (tabItem.isMain) {
              //如果是主表单
              var btns = flowObj.btns;
              btns.push({
                name: "cancel",
                label: "取消"
              });
              customBtnGroup = {
                btns: btns,
                callback: function(dataName, obj) {
                  switch (dataName) {
                    case "cancel":
                      obj.close();
                      break;
                    default:
                      var body = {};
                      for (var j = 0; j < workFormJson.tabs.length; j++) {
                        //第二次遍历workFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
                        var tabItemj = workFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象
                        if (tabItemj.type === "4") {
                          //流程图tab
                        } else if (tabItemj.type === "3") {
                          //列表tab
                        } else {
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
                            //给主表赋值
                            body["mainTableName"] = tabItemj.tbName;
                            body["mainTablePrimaryIdName"] = tabItemj.tbIdName;
                            body["mainTableDataObject"] = tabObjDatas.data;

                            //add by lny on 717
                            body["apiBody"] = {};
                            for (var key in tabObjDatas.data) {
                              body["apiBody"][key] = tabObjDatas.data[key];
                            }
                            //add by lny on 717

                            //body["title"] =tabObjDatas.data[workFormJson.titleName];
							body["title"] = title;
                            body["opinionContent"] =
                              tabObjDatas.data["opinionContent"];
                          } else if (tabItemj.type === "2") {
                            //如果是附件类型子表，type==="2"
                            //给子表赋值-附件表
                            if (!body["subTableObject"]) {
                              body["subTableObject"] = {};
                            }
                            for (var key in tabObjDatas.data) {
                              var subTableDataObject = tabObjDatas.data[key];

                              //add by lny on 717
                              body["apiBody"][key] = tabObjDatas.data[key];
                              //add by lny on 717

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

                            //add by lny on 717
                            for (var key in tabObjDatas.data) {
                              body["apiBody"][key] = tabObjDatas.data[key];
                            }
                            //add by lny on 717

                            body["subTableObject"][tabItemj.tbName] = {
                              subTablePrimaryIdName: tabItemj.tbIdName,
                              subTableType: tabItemj.type,
                              subTableDataObject: tabObjDatas.data
                            };
                          }
                        }
                      }
                      //流程操作特殊代码---开始
                      if (false) {
                        layer.confirm("确定打印？", { icon: 0 }, function(
                          index
                        ) {
                          //流程发起请求
                          l.ajax(buttonUrl, body, function(
                            data,
                            message,
                            success
                          ) {
                            if (success) {
                              window.location.href = data;
                              // window.open(data)
                            }
                          });
                          layer.close(index);
                        });
                      } else {
                        flowObj.flowSelectOpen(dataName, body);
                      }
                    //流程操作特殊代码---结束
                  }
                }
              };

              if (_d.nodeVars != null) {
                //如果需要显示意见框
                if (_d.nodeVars.opinionShowFlag === "1") {
                  tabItem.conditions.push({
                    type: "textarea", //
                    name: "opinionContent", //
                    label: "您的意见", //
                    defaultValue: "同意",
                    must: true,
                    placeholder: "您的意见"
                  });
                }
              }
            } else {
              customBtnGroup = {
                btns: [],
                callback: function(dataName, obj) {}
              };
            }
            tabCons[i] = $tabCon.detailLayer({
              customBtnGroup: customBtnGroup,
              conditions: tabItem.conditions
            });
            //流程操作特殊代码（向各个表单中赋值）---开始

            //add by lny on 717
            var apiData = _d.apiData;
            var apiName = _d.apiName;
            if (apiName) {
              tabCons[i].setOpenData(JSON.parse(apiData));
            } else {
              //add by lny on 717
              if (tabItem.isMain) {
                tabCons[i].setOpenData(mainTableDataObject);
              } else if (tabItem.type === "2") {
                var _subTableDataObject = {};
                for (var key in subTableObject) {
                  _subTableDataObject[key] =
                    subTableObject[key].subTableDataObject;
                }
                tabCons[i].setOpenData(_subTableDataObject);
              } else {
                tabCons[i].setOpenData(
                  subTableObject[tabItem.tbName].subTableDataObject
                );
              }
            }
            //流程操作特殊代码（向各个表单中赋值）---结束
            tabCons[i].close = function() {
              parent.layer.close(parent.myOpenLayer);
            };
          }
        });
        $tabSystem
          .append($tabBar)
          .append(tabCons)
          .Huitab({
            index: 0
          });
      }
    });
  }
});
