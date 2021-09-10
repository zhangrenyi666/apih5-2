import React, { Component } from "react";
import { goBack } from "connected-react-router";
import { Flex, NavBar, Icon, Toast } from "antd-mobile";
import { Form, Input, Tabs, Timeline, Icon as IconByAntd, Spin } from "antd";
import moment from "moment";
import {
  FlowSelectModal,
  actionFlow,
  clickFlowButton,
  FlowButtonGroup,
  FlowForm
} from "../flow";
import styles from "./style.less";
import QnnForm from "../../qnn-table/qnn-form";
import $ from "jquery";

// import { StickyContainer, Sticky } from "react-sticky";
const TabPane = Tabs.TabPane;

class Home extends Component {
  static getDerivedStateFromProps(props, state) {
    let { workId = "add", flowId, title = "title" } = props.match.params;
    let { isInQnnTable, rowData, workFlowConfig } = props;
    if (isInQnnTable && rowData) {
      //这是处理流程
      workId = rowData.workId;
      title = rowData.title;
      flowId = rowData.flowId;
    } else if (isInQnnTable) {
      //新增流程时候
      flowId = props.flowId;
      title = workFlowConfig.title;
    }

    //移动端新增时title不是处理时候的title
    if (workId === "add") {
      title = workFlowConfig.title;
    }

    let obj = {
      ...state,
      ...props,
      workId, //为add即是新增流程  为其他则是从列表点击进入的
      flowId,
      title: title //流程标题字段title
    };
    return obj;
  }

  constructor(props) {
    super(props);
    let { isInQnnTable, rowData } = props;
    let { workId = "add", flowId, title = "title" } = this.props.match.params;
    //isInQnnTable存在肯定是用的qnn-table rowData不存在是新增
    if (isInQnnTable && rowData) {
      //这是处理流程
      workId = rowData.workId;
      title = rowData.title;
      flowId = rowData.flowId;
    } else if (isInQnnTable) {
      //新增流程时候
      flowId = props.flowId;
      title = props.title;
    }
    this.state = {
      flowFormColumn: [],
      flowButtons: [],
      formConfig: this.props.formConfig, //表单项配置
      workId, //为add即是新增流程  为其他则是从列表点击进入的
      flowId,
      apiNameByAdd: this.props.apiNameByAdd,
      apiNameByUpdate: this.props.apiNameByUpdate,
      apiNameByGet: props.apiNameByGet,
      title: title, //流程标题字段title

      //以下四个属性只需会有一个参数 值为数据  需要返回新的数据
      //申请时提交的参数自定义格式化
      createParamsFormat:
        props.createParamsFormat ||
        function(obj) {
          return obj;
        },
      //打开流程时提交的参数自定义格式化
      openParamsFormat:
        props.openParamsFormat ||
        function(obj) {
          return obj;
        },
      //打开流程时获得的数据自定义格式化
      flowDataFormat:
        props.flowDataFormat ||
        function(obj) {
          return obj;
        },
      flowImgLoading: true,
      flowImgShow: true
      //处理时提交的参数自定义格式化
      // processParamsFormat:
      //   props.processParamsFormat ||
      //   function(obj) {
      //     return obj;
      //   }
    };
    this.rootBody = {
      apiTitle: props.apiTitle,
      selectAuthorNewFlag: "1"
    };
    this.actionFlow = actionFlow.bind(this);
    this.clickFlowButton = clickFlowButton.bind(this);
  }
  updateRootBody = newRootBody => {
    this.rootBody = {
      ...this.rootBody,
      ...newRootBody
    };
  };
  componentDidMount() {
    const { workId } = this.state;

    if (workId === "add") {
      this.openFlow();
    } else {
      //处理
      this.openFlowByPre();
    }
  }
  componentDidUpdate(prevProps, prevState) {
    const { isInQnnTable } = prevProps;
    const { workId } = this.state;
    if (isInQnnTable && workId !== prevState.workId) {
      if (workId === "add") {
        this.openFlow();
      } else {
        this.openFlowByPre();
      }
    }
  }
  openFlow() {
    //新增
    //需要将optionConent 删除
    let { formConfig } = this.state;
    const _column = formConfig
      .filter(item => {
        let { addShow = true } = item;
        return addShow;
      })
      .map(item => {
        if (item.disabled === true) {
          item.disabled = false;
        }
        return item;
      });
    this.setState({
      navTitle: "审批申请",
      flowFormColumn: _column,
      flowButtons: [{ buttonClass: "createOpenFlow" }]
    });
  }

  openFlowByPre() {
    //处理
    const {
      formConfig,
      apiNameByGet,
      apiNameByUpdate,
      workId,
      flowId,
      title,
      openParamsFormat,
      flowDataFormat
    } = this.state;
    // const { apiNameByGet } = this.props;
    const newFormConfig = [...formConfig];
    const _column = newFormConfig.map(item => {
      item.disabled = true;
      return item;
    });

    const { myFetch, form } = this.props;
    const { setFieldsValue } = form;
    const body = {
      flowId,
      workId,
      apiName: apiNameByGet,
      apiType: "POST",
      title: window.decodeURI(title)
    };
    const newBody = openParamsFormat(body, { ...this.props });

    Toast.loading("正在加载数据");
    myFetch("openFlow", newBody).then(({ success, data, message }) => {
      Toast.hide();
      if (success) {
        const {
          apiData: apiDataStr,
          nodeVars,
          flowVars,
          flowNode: { nodeId, trackId },
          flowButtons,
          flowHistoryList,
          flowWebUrl
        } = data;
        const apiData = apiDataStr ? JSON.parse(apiDataStr) : {};
        this.updateRootBody({
          workId,
          flowId,
          nodeId,
          trackId,
          nodeVars,
          flowVars,
          apiName: apiNameByUpdate,
          apiType: "POST",
          title: window.decodeURI(title)
        });
        const apiBody = {};
        const opinions =
          flowVars.opinionFieldName &&
          flowVars.opinionFieldName.split("_").map(item => {
            let itemArr = item.split("|");
            return {
              field: itemArr[0],
              type: "textarea",
              label: itemArr[1],
              disabled: true
            };
          });
        let flowFormColumn;
        if (opinions) {
          flowFormColumn = [].concat([], _column, opinions);
        } else {
          flowFormColumn = [].concat([], _column);
        }
        // let flowFormColumn = [].concat([], _column, opinions)
        flowFormColumn = flowFormColumn.map(item => {
          let { field, type, disabled } = item;
          field = field ? field.replace(/(apiBody.)/g, "") : "";
          if (type === "datetime") {
            //格式化为moent格式
            apiBody["apiBody." + field] = apiData[field]
              ? moment(apiData[field])
              : moment("1970");
          } else if (type === "textarea") {
            apiBody["apiBody." + field] = apiData[field]
              ? apiData[field].replace(/(<br\/>)/g, "\r\n")
              : null;
          } else if (type === "linkage") {
            //联动类型
            const forLinkage = _item => {
              let _dField = _item.form.field.replace(/(apiBody.)/g, "");
              if (_item.form.field.indexOf("apiBody.") === -1) {
                apiBody["apiBody." + _item.form.field] = apiData[_dField] || "";
              } else {
                apiBody[_item.form.field] = apiData[_dField] || "";
              }
              if (_item.children) {
                forLinkage(_item.children);
              }
            };
            forLinkage(item.children);
          } else {
            apiBody["apiBody." + field] = apiData[field];
          }

          disabled =
            type !== "upload"
              ? disabled
              : nodeVars && nodeVars.fileOperationFlag === "1"
              ? false
              : true;

          return {
            ...item,
            disabled
          };
        });

        if (nodeVars && nodeVars.opinionField) {
          let curOptionDomId = `apiBody.opinionContent`; //可输入的框
          //异步一下
          setTimeout(() => {
            let curOptionDom = document.getElementById(curOptionDomId);
            let opdOpt = apiBody[`apiBody.${nodeVars.opinionField}`];
            // console.log(apiBody)
            if (opdOpt) {
              opdOpt = opdOpt.replace(/\r\n/gi, "<br/>");
              $(curOptionDom).before(
                `<div style="line-height:1.3;color:#777;font-size:14px;margin-bottom:6px">${opdOpt}</div>`
              ); //以前输入的内容
            }
            $(curOptionDom).css({
              border: "1px dashed rgba(71, 160, 236, 0.8)",
              padding: "8px 3px"
            });
            // $(curOptionDom).click(function() {
            //   setTimeout(() => {
            //     curOptionDom.scrollIntoView(true);
            //   }, 500);
            // });
          }, 200);

          flowFormColumn = flowFormColumn.map(item => {
            const { field } = item;
            if (field === nodeVars.opinionField) {
              item.disabled = false;
              item.field = "opinionContent";
              item.placeholder = "请输入...";
            }
            return item;
          });

          // flowFormColumn.push({
          //   type: "textarea",
          //   label: "您的意见",
          //   field: "opinionContent",
          //   placeholder: "请输入"
          // });
        }

        this.setState(
          {
            flowFormColumn,
            flowButtons: flowButtons && flowButtons.reverse(),
            flowHistoryList,
            flowWebUrl,
            navTitle: "审批处理"
          },
          () => {
            let _fData = QnnForm.sFormatData(apiBody, flowFormColumn);
            let _newFData = flowDataFormat(_fData, { ...this.props }, data);
            setFieldsValue(_newFData);
          }
        );
      } else {
        Toast.fail(message);
      }
    });
  }

  isMobile = () => {
    if (
      navigator.userAgent.match(/Android/i) ||
      navigator.userAgent.match(/webOS/i) ||
      navigator.userAgent.match(/iPhone/i) ||
      navigator.userAgent.match(/iPad/i) ||
      navigator.userAgent.match(/iPod/i) ||
      navigator.userAgent.match(/BlackBerry/i) ||
      navigator.userAgent.match(/Windows Phone/i)
    ) {
      return true;
    } else {
      return false;
    }
  };
  createOpenFlow = () => {
    const {
      apiNameByUpdate,
      apiNameByAdd,
      title,
      flowId,
      createParamsFormat,
      flowFormColumn
    } = this.state;
    const {
      myFetch,
      form,
      apiTitle,
      formConfig,
      btnCallbackFn = {},
      isInQnnTable
    } = this.props;
    const { refresh } = btnCallbackFn;
    const { validateFields } = form;
    validateFields((err, values) => {
      //将moment时间格式处理为时间戳
      let _apiBody = values["apiBody"];
      let _title = "";
      if (Array.isArray(title)) {
        //是数组需要拼接
        for (let i = 0; i < title.length; i++) {
          let _tit = _apiBody[title[i]] || title[i];
          _title += `${i === 0 ? "" : "-"}${_tit}`;
        }
      } else {
        _title = _apiBody[title] || title;
      }
      for (let i = 0, j = formConfig.length; i < j; i++) {
        let _field = formConfig[i].field.replace(/(apiBody.)/g, "");
        if (formConfig[i].type === "datetime") {
          if (_apiBody[_field]) {
            _apiBody[_field] = moment(_apiBody[_field]).valueOf();
          }
        }
      }

      values["apiBody"] = _apiBody;
      //处理附件 移动端pc端的问题
      flowFormColumn && flowFormColumn.map(item => {
        let { field, type } = item;
        let _field = field.replace("apiBody.", "");
        if (type && type === "files") {
          let _files = values["apiBody"][_field];
          let newFiles = _files && _files.map(item => {
            item.url = item.fileUrl || item.url;
            return item;
          });
          values["apiBody"][_field] = newFiles;
        }
        return item;
      }); 

      if (!err) {
        const body = {
          title: _title,
          flowId,
          apiNameByCreate: apiNameByUpdate,
          apiName: apiNameByAdd,
          apiType: "POST",
          apiTitle: apiTitle,
          apiBody: JSON.stringify(values["apiBody"])
        };
        const newBody = createParamsFormat(body, { ...this.props });
        Toast.loading("正在创建流程", 0);
        myFetch("createOpenFlow", newBody).then(
          ({ success, message, data }) => {
            Toast.hide();
            if (success) {
              //将按钮全部删除
              this.setState({
                flowButtons: []
              });

              Toast.success("创建流程成功");
              if (isInQnnTable) {
                refresh();
              }

              const {
                workId,
                nodeVars,
                flowVars,
                flowNode: { nodeId, trackId },
                flowButtons,
                apiName,
                apiType,
                apiBody,
                title
              } = data;
              this.updateRootBody({
                nodeVars,
                flowId,
                flowVars,
                workId,
                nodeId,
                trackId,
                apiName,
                apiType,
                apiBody,
                title, //: _title
              });
              const { buttonId, buttonClass, buttonName } = flowButtons[0];
              const operateObj = {
                operate: buttonId,
                operateClazz: buttonClass,
                operateText: buttonName,
                operateFlag: 1
              };

              this.actionFlow({ operateObj });
            } else {
              Toast.fail(message, 2);
            }
          }
        );
      }
    });
  };
  callback(key) {
    // console.log(key);
  }
  setFlowImgShow() {
    this.setState({
      flowImgShow: !this.state.flowImgShow
    });
  }
  render() {
    const { dispatch, isInQnnTable } = this.props;
    const {
      flowButtons,
      flowFormColumn,
      flowHistoryList,
      navTitle,
      flowWebUrl,
      workId,
      flowImgLoading,
      // flowImgShow
    } = this.state;
    const { getFieldDecorator } = this.props.form;
    getFieldDecorator("visible", { initialValue: false })(<Input hidden />);
    getFieldDecorator("operateObj", { initialValue: {} });
    getFieldDecorator("curNextNodes", { initialValue: [] });
    const renderFlowHistory =
      flowHistoryList && flowHistoryList.length ? (
        <TabPane tab="操作历史" key="2">
          <div
            className={styles.flowHistory}
            style={
              !this.isMobile()
                ? {
                    height: "100%"
                  }
                : {
                    height: "90vh",
                    overflowY: "scroll",
                    paddingBottom: "10vh"
                  }
            }
          >
            <Timeline style={{ padding: "16px" }}>
              {flowHistoryList.map(
                (
                  { historyFlag, realName, nodeName, comments, actionTime },
                  idnex
                ) => {
                  return (
                    <Timeline.Item
                      dot={
                        historyFlag === "1" ? (
                          ""
                        ) : (
                          <IconByAntd type="clock-circle-o" />
                        )
                      }
                      key={idnex}
                    >
                      <div>
                        {nodeName}：{realName}
                      </div>
                      <div>{comments}</div>
                      {actionTime ? (
                        <div>
                          到达时间：
                          {moment(actionTime).format("YYYY-MM-DD HH:mm:ss")}
                        </div>
                      ) : (
                        ""
                      )}
                    </Timeline.Item>
                  );
                }
              )}
            </Timeline>

            {flowWebUrl ? (
              <div>
                {/* <div onClick={this.setFlowImgShow.bind(this)} className={styles.btn}>
                  {flowImgShow ? "关闭流程图" : "展开流程图"}
                </div> */}
                <Spin spinning={flowImgLoading}>
                  <iframe
                    onLoad={() => {
                      this.setState({
                        flowImgLoading: false
                      });
                    }}
                    src={flowWebUrl}
                    title="流程图"
                    frameBorder="0"
                    className={styles.flowIframe}
                  />
                </Spin>
              </div>
            ) : (
              ""
            )}
          </div>
        </TabPane>
      ) : null;

    const FlowCon = (
      <div>
        <FlowForm
          {...this.props}
          updateRootBody={this.updateRootBody}
          flowFormColumn={flowFormColumn}
          flowHistoryList={flowHistoryList}
        />
        <FlowButtonGroup
          {...this.props}
          clickFlowButton={this.clickFlowButton}
          createOpenFlow={this.createOpenFlow}
          flowButtons={flowButtons}
        />
        <FlowSelectModal {...this.props} actionFlow={this.actionFlow} />
      </div>
    );

    if (this.isMobile()) {
      return (
        <Flex
          className={`${styles.root} ${styles.rootByMobile}`}
          direction={"column"}
        >
          <NavBar
            style={{ width: "100%" }}
            mode="dark"
            icon={<Icon type="left" />}
            onLeftClick={() => {
              dispatch(goBack());
            }}
          >
            {navTitle}
          </NavBar>

          {workId === "add" ? (
            <div className={styles.FlowConByAdd}>{FlowCon}</div>
          ) : (
            <Tabs
              defaultActiveKey="1"
              onChange={this.callback}
              style={{
                width: "100%",
                background: "#fff"
              }}
            >
              <TabPane tab="详情" key="1">
                <div className={styles.FlowConByMobilePre}>{FlowCon}</div>
              </TabPane> 
              {renderFlowHistory}
            </Tabs>
          )}
        </Flex>
      );
    } else {
      //pc端
      return (
        <div
          className={`${styles.pcroot} ${
            isInQnnTable ? styles.pcrootByInQnnTable : null
          }`}
        >
          {workId === "add" ? (
            FlowCon
          ) : (
            <Tabs defaultActiveKey="1" onChange={this.callback}>
              <TabPane tab="详情" key="1" style={{ height: "100%" }}>
                {FlowCon}
              </TabPane>
              {renderFlowHistory}
            </Tabs>
          )}
        </div>
      );
    }
  }
}

const WrappedDynamicRule = Form.create()(Home);
export default WrappedDynamicRule;
