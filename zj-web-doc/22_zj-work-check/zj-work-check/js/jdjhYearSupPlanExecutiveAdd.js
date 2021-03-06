var launchId = l.getUrlParam("id") || "";
var yearSupPlanId = l.getUrlParam("supPlanId") || "";
var supPlanExecutiveConditionId = "";

var $tabSystem = $("#tab-system"); //模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>'); //tab按钮控制条
var tabCons = []; //tab内容页面组
var pager;
var table, pagerDom, detailForm;
/**
 * 表单配置          行14
 * 表单提交          行76
 * 表格底部按钮      行130
 * 表格配置         行168
 * **/
var tabs = [
  {
    title: "基本信息",
    type: "form",
    conditions: [
      {
        type: "hidden", //五种形式：text,select,date,hidden,textarea,
        name: "supPlanExecutiveConditionId", //输入字段名
        immutableAdd: true
      },
      {
        type: "hidden", //五种形式：text,select,date,hidden,textarea,
        name: "yearSupPlanId", //输入字段名
        label: "监督责任部门",
        must: true,
        immutableAdd: true
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
            text: " "
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
        must: true
      }
    ]
  },
  {
    title: "完成情况填写",
    type: "table"
  }
];

$.each(tabs, function(i, tabItem) {
  //第一次遍历flowFormJson.tabs
  var $tabBtn = $("<span>" + tabItem.title + "</span>"); //创建tab按钮$对象
  $tabBar.append($tabBtn); //向tab按钮控制条插入tab按钮
  var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>'); //创建tab内容页面$对象
  var customBtnGroup; //tab内容页面中表单的底部按钮组配置
  if (tabItem.type === "form") {
    customBtnGroup = {
      btns: [
        {
          name: "add",
          label: '<i class="Hui-iconfont">&#xe600;</i> 确定'
        },
        {
          name: "cancel",
          label: "关闭"
        }
      ],
      callback: function(dataName, obj) {
        switch (dataName) {
          case "add":
            var body = {};
            for (var j = 0; j < tabs.length; j++) {
              //第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环

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
            l.ajax("addZjJdjhSupPlanExecutive", body, function(
              data,
              message,
              success
            ) {
              if (success) {
                yearSupPlanId = data.yearSupPlanId;
                supPlanExecutiveConditionId = data.supPlanExecutiveConditionId;
                pager.page("remote", {
                  yearSupPlanId: yearSupPlanId,
                  supPlanExecutiveConditionId: supPlanExecutiveConditionId
                });
                $("#tab-system").Huitab({
                  index: 1
                });
              } else {
                layer.alert(message, { icon: 0 }, function(index) {
                  layer.close(index);
                });
              }
            });
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
  if (tabItem.type === "table") {
    //表格
    var $con = $('<div class="page-container"></div>'); //
    var $table = $(
      '<table id="table" class="table table-border table-bordered table-bg table-hover"></table>'
    );
    var $cl = $('<div class="cl"></div>');
    pagerDom = $('<div id="pager" class="m-pagination f-r"></div>');
    var $btnCon = $('<div class="f-l mt-10"></div>');
    var $update = $(
      '<button data-name="edit" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe6df;</i> 计划外新增</button>'
    );
    var $submit = $(
      '<button data-name="submit" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe6df;</i> 提交</button>'
    );
    var $close = $(
      '<button data-name="close" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"> 关闭</button>'
    );

    $btnCon.append($update);
    $btnCon.append($submit);
    $btnCon.append($close);

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
          title: "监督责任部门", //表头名
          data: "supDutyDepName", //接口对应字段
          defaultContent: "-" //默认显示
        },
        {
          targets: [2], //第几列
          title: "填报人", //表头名
          data: "principal", //接口对应字段
          defaultContent: "-" //默认显示
        },
        {
          targets: [3], //第几列
          title: "监督事项", //表头名
          data: "supMatter", //接口对应字段
          defaultContent: "-" //默认显示
        },
        /*  {
			            	 targets: [3], //第几列
			            	 data: function(row) {
			            		 return row;
			            	 }, //接口对应字段
			            	 title: "监督事项", //表头名
			            	 defaultContent: "-", //默认显示
			            	 render: function(data, index, row) {
			            		 var a;
			            		 if (data) {
			            			 a = "<a style=\"color:blue;\"  onclick=\"myOpen1('详情','" + data.yearSupMatterId + '\',\'' + 'reply' + '\',\'' + row.rowIndex + '\')"  href="javascript:void(0);">' + data.supMatter + "</a>";
			            		 }
			            		 return a;
			            	 }
			             }, */
        {
          targets: [4], //第几列
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
                text = "-";
                break;
            }
            return text;
          }
        },
        {
          targets: [5], //第几列
          title: "配合部门", //表头名
          data: "coopDept", //接口对应字段
          defaultContent: "-" //默认显示
        },
        {
          targets: [6], //第几列
          title: "计划完成时间", //表头名
          data: "scheduleTime", //接口对应字段
          defaultContent: "-", //默认显示
          render: function(data) {
            return data ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
          }
        },
        {
          targets: [7], //第几列
          data: "principal", //接口对应字段
          title: "责任人", //表头名
          defaultContent: "-" //默认显示
        },
        {
          targets: [8], //第几列
          data: "remarks", //接口对应字段
          title: "备注", //表头名
          defaultContent: "-" //默认显示
        },
        /*  {
			            	 targets: [9], //第几列
			            	 data: "replyFlag", //接口对应字段
			            	 title: "是否回复", //表头名
			            	 defaultContent: "-", //默认显示
			            	 "render": function (data) {
			            		 var text = ""
			            			 switch (data) {
			            			 case "0": text = "-";
			            			 break;
			            			 case "1": text = "已回复";
			            			 break;
			            			 case "2": text = "已提交";
			            			 break;
			            			 default: text = "-";
			            			 break;
			            			 }
			            		 return text
			            	 }
			             }, */
        {
          targets: [9], //第几列
          data: "progress", //接口对应字段
          title: "进展情况", //表头名
          defaultContent: "-" //默认显示
        },
        {
          targets: [10], //第几列
          data: "fileList", //接口对应字段
          title: "附件", //表头名
          defaultContent: "-", //默认显示
          render: function(data) {
            var domain = window.lny.domain;
            var dom = "";
            for (var i = 0; i < data.length; i++) {
              var fileUrl = data[i].fileUrl,
                fileName = data[i].fileName;
              dom += '<li style="list-style:none;">';
              dom +=
                '<a style="width:80px;"  href="' +
                fileUrl +
                '">' +
                fileName +
                "</a>";
              dom += "</li>";
            }
            return dom;
          }
        },
        {
          targets: [11], //第几列
          width: 80,
          data: function(row) {
            return row;
          }, //接口对应字段
          title: "操作", //表头名
          render: function(data, index, row) {
            var html = "";
            html += '<span class="dropDown">';
            if (row.replyFlag == "2") {
              html = "-";
            } else {
              html +=
                "<a style=\"color:blue;\"  onclick=\"myOpen1('详情','" +
                data.yearSupMatterId +
                "','" +
                "reply" +
                "','" +
                row.rowIndex +
                "','" +
                data.yearSupPlanId +
                '\')"  href="javascript:void(0);">' +
                "完成情况填写" +
                "</a>";
            }
            html += "</span>";
            return html;
          }
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
var allData;
pager = pagerDom.page({
  remote: {
    //ajax请求配置
    url: l.getApiUrl("getMatterAndExecuteDetailList"), //getMatterAndExecuteDetailList  getZjJdjhYearSupMatterList
    params: {
      yearSupPlanId: yearSupPlanId,
      supPlanExecutiveConditionId: supPlanExecutiveConditionId
    },
    success: function(result) {
      if (result.success) {
        var data = result.data;
        allData = data;
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

//回复弹窗---新增
var replyAdd = $("#replyAdd").detailLayer({
  layerArea: ["100%", "100%"],
  conditions: [
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "executeDetailId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "supPlanExecutiveConditionId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "executeId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "quarter" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "yearSupPlanId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "yearSupMatterId" //输入字段名
    },
    {
      type: "textarea", //
      name: "supMatter", //
      label: "监督事项", //
      placeholder: "请输入监督事项",
      immutableAdd: true
    },
    {
      type: "text", //
      name: "coopDept", //
      label: "配合部门", //
      placeholder: "请输入",
      immutableAdd: true
    },
    {
      type: "date", //
      name: "scheduleTime", //
      label: "计划完成时间", //
      dateFmt: "yyyy-MM-dd",
      defaultValue: new Date(),
      id: "scheduleTime",
      immutableAdd: true
    },
    {
      type: "text", //
      name: "principal", //
      label: "责任人", //
      placeholder: "请输入",
      immutableAdd: true
    },
    {
      type: "textarea", //
      name: "remarks", //
      label: "备注", //
      placeholder: "请输入备注",
      immutableAdd: true
    },
    {
      type: "textarea", //
      name: "progress", //
      label: "进展情况", //
      placeholder: "请输入",
      must: true
    },
    {
      type: "upload", //
      name: "fileList", //
      label: "附件", //
      btnName: "添加监督工作结果",
      projectName: "zj-work-check",
      uploadUrl: "uploadFilesByFileName",
      maxLen: 1,
      fileType: [
        "jpg",
        "jpeg",
        "png",
        "gif",
        "docx",
        "xls",
        "doc",
        "ppt",
        "xlsx",
        "pptx",
        "xlsm",
        "pdf"
      ]
    }
  ],
  onSave: function(obj, _params) {
    l.ajax("addZjJdjhSupPlanExecuteDetail", _params, function(data) {
      if (data) {
        pager.page("remote");
        obj.close();
        $("#replyAdd").hide();
        $("#tab-system").show();
      }
    });
  }
});
//回复弹窗---编辑
var replyEdit = $("#replyEdit").detailLayer({
  layerArea: ["100%", "100%"],
  conditions: [
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "executeDetailId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "supPlanExecutiveConditionId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "executeId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "quarter" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "yearSupPlanId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "yearSupMatterId" //输入字段名
    },
    {
      type: "textarea", //
      name: "supMatter", //
      label: "监督事项", //
      placeholder: "请输入监督事项",
      immutableAdd: true
    },
    {
      type: "text", //
      name: "coopDept", //
      label: "配合部门", //
      placeholder: "请输入",
      immutableAdd: true
    },
    {
      type: "date", //
      name: "scheduleTime", //
      label: "计划完成时间", //
      dateFmt: "yyyy-MM-dd",
      defaultValue: new Date(),
      id: "scheduleTime",
      immutableAdd: true
    },
    {
      type: "text", //
      name: "principal", //
      label: "责任人", //
      placeholder: "请输入",
      immutableAdd: true
    },
    {
      type: "textarea", //
      name: "remarks", //
      label: "备注", //
      placeholder: "请输入备注",
      immutableAdd: true
    },
    {
      type: "textarea", //
      name: "progress", //
      label: "进展情况", //
      placeholder: "请输入",
      must: true
    },
    {
      type: "upload", //
      name: "fileList", //
      label: "附件", //
      btnName: "添加监督工作结果",
      projectName: "zj-work-check",
      uploadUrl: "uploadFilesByFileName",
      maxLen: 1,
      fileType: [
        "jpg",
        "jpeg",
        "png",
        "gif",
        "docx",
        "xls",
        "doc",
        "ppt",
        "xlsx",
        "pptx",
        "xlsm",
        "pdf"
      ]
    }
  ],
  onSave: function(obj, _params) {
    l.ajax("updateZjJdjhSupPlanExecuteDetail", _params, function(data) {
      if (data) {
        pager.page("remote");
        obj.close();
        $("#replyEdit").hide();
        $("#tab-system").show();
      }
    });
  }
});

//计划外事项新增
var addOutMatter = $("#addOutMatter").detailLayer({
  layerArea: ["100%", "100%"],
  conditions: [
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "executeDetailId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "supPlanExecutiveConditionId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "executeId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "quarter" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "yearSupPlanId" //输入字段名
    },
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "yearSupMatterId" //输入字段名
    },
    {
      type: "textarea", //
      name: "supMatter", //
      label: "监督事项", //
      placeholder: "请输入监督事项",
      must: true
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
      id: "scheduleTime",
      must: true
    },
    {
      type: "text", //
      name: "principal", //
      label: "责任人", //
      placeholder: "请输入",
      must: true
    },
    {
      type: "textarea", //
      name: "remarks", //
      label: "备注", //
      placeholder: "请输入备注"
    },
    {
      type: "textarea", //
      name: "progress", //
      label: "进展情况", //
      placeholder: "请输入",
      must: true
    },
    {
      type: "upload", //
      name: "fileList", //
      label: "附件", //
      btnName: "添加监督工作结果",
      projectName: "zj-work-check",
      uploadUrl: "uploadFilesByFileName",
      maxLen: 1,
      fileType: [
        "jpg",
        "jpeg",
        "png",
        "gif",
        "docx",
        "xls",
        "doc",
        "ppt",
        "xlsx",
        "pptx",
        "xlsm",
        "pdf"
      ]
    }
  ],
  onSave: function(obj, _params) {
    l.ajax("addPlanOutMatter", _params, function(data) {
      if (data) {
        pager.page("remote");
        obj.close();
        $("#replyAdd").hide();
      }
    });
  }
});

//提交
var submitLayer = $("#submitLayer").detailLayer({
  layerArea: ["80%", "40%"],
  conditions: [
    {
      type: "hidden", //五种形式：text,select,date,hidden,textarea,
      name: "supPlanExecutiveConditionId" //输入字段名
    },
    {
      type: "pickTree",
      name: "oaAuditor",
      label: "审核人员",
      pickType: {
        department: false, //部门列表对应接口字段名,false表示不开启该类
        member: "oaMemberList" //成员列表对应接口字段名,false表示不开启该类
      }
    }
  ],
  onSave: function(obj, _params) {
    l.ajax("updateZjJdjhSupPlanExecutive", _params, function(data) {
      if (data) {
        pager.page("remote");
        obj.close();
        $("#replyAdd").hide();
        parent.layer.close(parent.myOpenLayer);
      }
    });
  }
});

$("body").on("click", "button", function() {
  var checkedData = allData;
  var params = {};

  var name = $(this).attr("data-name");
  switch (name) {
    case "edit":
      params.yearSupPlanId = checkedData[0].yearSupPlanId;
      params.quarter = checkedData[0].quarter;
      params.supPlanExecutiveConditionId = supPlanExecutiveConditionId;

      if (checkedData[0].state == "2" || checkedData[0].state == "3") {
        layer.alert("在审批中不能新增计划外事项", { icon: 0 }, function(index) {
          layer.close(index);
        });
      } else {
        $("#addOutMatter").show();
        addOutMatter.open(params);
      }
      break;
    case "submit":
      if (checkedData[0].state == "2" || checkedData[0].state == "3") {
        layer.alert("已经提交审批了，不要重复提交", { icon: 0 }, function(
          index
        ) {
          layer.close(index);
        });
      } else {
        var breakFlag = 1;
        for (var i = 0; i < checkedData.length; i++) {
          if (!(checkedData[i].replyFlag == "1")) {
            breakFlag = 0;
            layer.alert("还没有回复", { icon: 0 }, function(index) {
              layer.close(index);
            });
            break;
          }
        }
        if (breakFlag == "1") {
          $("#submitLayer").show();
          submitLayer.open({
            supPlanExecutiveConditionId: supPlanExecutiveConditionId
          });
        } else {
          layer.alert("还没有回复", { icon: 0 }, function(index) {
            layer.close(index);
          });
        }
      }
      break;
    case "close":
      parent.layer.close(parent.myOpenLayer);
      break;
  }
});
var myOpenLayer;

function myOpen1(tit, id, type, index, yearSupPlanId) {
  var rowData = allData[Number(index)];
  rowData.supPlanExecutiveConditionId = supPlanExecutiveConditionId;

  var params;
  switch (type) {
    case "reply":
      if (rowData.progress != null) {
        $("#replyEdit").show();
        replyEdit.open(rowData);
      } else {
        $("#replyAdd").show();
        replyAdd.open(rowData);
      }
      //$("#tab-system").hide();
      break;
    /* case 'reply':
		params = {};
		params.ruleId = rowData.ruleId;
		l.ajax('getZjXmRulesDetail', params, function (res) {
			reply.open(res);
			$('#reply .btn').hide();
		})
		break; */
  }
}

if (launchId) {
  l.ajax(
    "getZjJdjhSupPlanExecutiveDetail",
    { supPlanExecutiveConditionId: launchId },
    function(data, message, success) {
      if (success) {
        detailForm
          .find("form")
          .prepend(
            $(
              '<div class="row cl"><label class="form-label col-2 f-l"><i style="color:red;">* </i>监督责任部门：</label><div class="col-4 f-l"><input type="text" class="input-text SearchSelect" onfocus="SearchSelect({ apiName: \'getZjJdjhYearSupPlanState2List\', otherKey: \'projectState\' })" value="' +
                data.supDutyDepName +
                '" name="supDutyDepName"></div> </div>'
            )
          );
        detailForm.setOpenData(data);
      } else {
        detailForm.setOpenData({ memberList: { oaMemberList: [] } });
      }
    }
  );
} else {
  detailForm
    .find("form")
    .prepend(
      $(
        '<div class="row cl"><label class="form-label col-2 f-l"><i style="color:red;">* </i>监督责任部门：</label><div class="col-4 f-l"><input type="text" class="input-text SearchSelect" onfocus="SearchSelect({ apiName: \'getZjJdjhYearSupPlanState2List\', otherKey: \'projectState\' })" value="" name="supDutyDepName"></div></div>'
      )
    );
  detailForm.setOpenData({ memberList: { oaMemberList: [] } });
}

var searchSelectDiv;
var curELe;
var searchSelectDatas = {};
var searchSelectErr = false;
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
var addListener = (function() {
  if (document.addEventListener) {
    /* ie9以上正常使用addEventListener */
    return function(element, type, fun, useCapture) {
      element.addEventListener(type, fun, useCapture ? useCapture : false);
    };
  } else {
    /* ie7、ie8使用attachEvent */
    return function(element, type, fun) {
      type = type !== "input" ? type : "propertychange";
      if (!fun.prototype["_" + type]) {
        /* 该事件第一次绑定 */
        fun.prototype["_" + type] = {
          _function: function(event) {
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
var removeListener = (function() {
  if (document.addEventListener) {
    /* ie9以上正常使用removeEventListener */
    return function(element, type, fun) {
      element.removeEventListener(type, fun);
    };
  } else {
    /* ie7、ie8使用detachEvent */
    return function(element, type, fun) {
      type = type !== "input" ? type : "propertychange";
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
    curELe.value = searchSelectData[curELe.name] || "";
    searchSelectDatas[curELe.name] = searchSelectData;
  } else {
    delete searchSelectDatas[curELe.name];
  }
  var selectData = searchSelectDatas[curELe.name] || {};
  var formData = detailForm.getDatas().data;
  var conditions = detailForm.conditions;
  if (curELe.name === "supDutyDepName") {
    $("input[name=schemeNumber]").val("");
  }
  function isImm(k, arrs) {
    var is = false;
    for (var index = 0; index < arrs.length; index++) {
      if (arrs[index].name === k) {
        is = arrs[index].immutableAdd || false;
        break;
      }
    }
    return is;
  }
  var newData = {};
  for (var key in formData) {
    if (isImm(key, conditions)) {
      newData[key] = selectData[key];
    } else {
      newData[key] = formData[key];
    }
  }

  detailForm.setOpenData(newData);
  closeSearchSelect();
}
function blurSearchSelect() {
  var event = this.event || window.event;
  var target = event.target || event.srcElement; //获取document 对象的引用
  if (target !== curELe) {
    if (curELe && curELe.value) {
      searchSelectErr = true;
      if (confirm("未从列表中选取项目！")) {
        curELe.value = searchSelectDatas[curELe.name][curELe.name] || "";
      }
    } else {
      setSearchSelect();
    }
  }
}
function closeSearchSelect() {
  searchSelectErr = false;
  searchSelectDiv.style.display = "none";
  curELe = null;
  removeListener(document, "mousedown", blurSearchSelect);
}
function SearchSelect(options) {
  options = options || { apiName: "api", otherKey: "other" };
  var apiName = options.apiName || "api";
  var otherKey = options.otherKey || "other";
  var otherValue = getOtherValue(otherKey);

  var event = this.event || window.event;
  var target = event.target || event.srcElement; //获取document 对象的引用
  if (curELe === target && !searchSelectErr) {
    return;
  }
  if (searchSelectErr) {
    if (curELe !== target) {
      target.blur();
    }
    return;
  }
  curELe = target;
  searchSelectDatas[curELe.name] = {};
  var tpos = getPos(curELe);
  function getPos(ele) {
    var pEle = ele.offsetParent;
    if (pEle.nodeName !== "BODY") {
      return {
        top: ele.offsetTop + getPos(pEle).top,
        left: ele.offsetLeft + getPos(pEle).left
      };
    } else {
      return {
        top: ele.offsetTop,
        left: ele.offsetLeft
      };
    }
  }
  if (!searchSelectDiv) {
    searchSelectDiv = document.createElement("div");
    var iframe = document.createElement("iframe");
    searchSelectDiv.appendChild(iframe);
    document.body.appendChild(searchSelectDiv);
  }
  searchSelectDiv.style.display = "block";
  searchSelectDiv.style.position = "absolute";
  searchSelectDiv.style.top = tpos.top + curELe.offsetHeight + "px";
  searchSelectDiv.style.left = tpos.left + "px";
  searchSelectDiv.style.zIndex = "100006";
  var searchSelectiframe = searchSelectDiv.children[0];
  searchSelectiframe.setAttribute("hideFocus", true, 0);
  searchSelectiframe.setAttribute("width", "240px", 0);
  searchSelectiframe.setAttribute("height", "200px", 0);
  searchSelectiframe.setAttribute("frameborder", "0", 0);
  searchSelectiframe.setAttribute("border", "0", 0);
  searchSelectiframe.setAttribute("scrolling", "no", 0);
  searchSelectiframe.setAttribute(
    "src",
    "searchSelect.html?otherKey=" +
      otherKey +
      "&otherValue=" +
      otherValue +
      "&keywordName=" +
      curELe.name +
      "&apiName=" +
      apiName +
      "&keyword=",
    0
  );
  function inputFun() {
    searchSelectErr = false;
    searchSelectiframe.setAttribute(
      "src",
      "searchSelect.html?otherKey=" +
        otherKey +
        "&otherValue=" +
        otherValue +
        "&keywordName=" +
        curELe.name +
        "&apiName=" +
        apiName +
        "&keyword=" +
        curELe.value,
      0
    );
  }
  addListener(curELe, "input", inputFun);
  addListener(document, "mousedown", blurSearchSelect);
}
function getOtherValue(otherKey) {
  switch (otherKey) {
    case "projectState":
      return "2";
      break;
    case "supPlanExecutiveConditionId":
      return detailForm.getDatas().data.supPlanExecutiveConditionId;
      break;
    default:
      return "";
      break;
  }
}
