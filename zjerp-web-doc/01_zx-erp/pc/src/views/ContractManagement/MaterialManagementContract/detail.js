import React, { Component } from "react";
import { Form } from "../../modules/work-flow";
import QnnForm from "../../modules/qnn-form";
import { Modal, Button } from 'antd';
import s from './style.less';
let config = {
  //流程专属配置   
  editDocCdnAddress: window.configs.ntkoAddress,
  workFlowConfig: {
    //后台定的字段
    title: ["物资合同评审", "contractName", "contractNo"], //标题字段
    apiNameByAdd: "addZxCtSuppliesContrApplyFlow",
    apiNameByUpdate: "updateZxCtSuppliesContrApplyFlow",
    apiNameByGet: "getZxCtSuppliesContrApplyFlowDetail",
    flowId: 'ZxCtSuppliesContrApply',
    todo: "TodoList",
    hasTodo: "HasTodoList"
  },
  parameterName: 'orgID',
  isHaveDoc: true,
  docFieldLable: "公文正文",
  docFieldName: "applyFileList",
  docFieldIsRequired: true,
  docIsReadOnly: false,
  docFormFormItemLayout: {
    labelCol: {
      xs: { span: 4 },
      sm: { span: 4 }
    },
    wrapperCol: {
      xs: { span: 20 },
      sm: { span: 20 }
    }
  },
  //切换布局
  formLayout: "leftDoc",
  //是否使用描述式表单
  formType: "descriptions",
  descriptionsConfig: {
    "layout": "horizontal",
    "column": 12,
    size: "small"
  },
  // //左侧公文附件字段名字  
  filesFieldName: "contrApplyFileList",
  //请求左侧待办已办列表的ajax配置
  //@curList 当前处于什么列表 todo(待办)  hasTodo(已办)
  getTodoDataFetchConfig: window.qnnGetTodoDataFetchConfig,
};
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {
      visibleBjdh: false,
      list: null,
      flowButtons:[]
    }
  }
  handleCancelBjdh = () => {
    this.setState({ visibleBjdh: false });
  }
  componentWillMount() {
    const { flowData } = this.props;
    let paramsArry = {
      workId: flowData.workId,
      apiName: "getZxCtSuppliesContrApplyFlowDetail",
      apiType: "POST",
      flowId: "ZxCtSuppliesContrApply"
    };
    this.props.myFetch('openFlowByReport', paramsArry).then(({ success, message, data }) => {
      if (success) {
        let list = JSON.parse(data.apiData);
        this.setState({
          list: list,
          flowButtons:data?.flowButtons || []
        });
      } else {
      }
    });
  }
  showTable(val) {
    let contractCategory = this.props.flowData?.contractCategory
    if (contractCategory === val) {
      return false
    }
    return true
  }
  render() {
    const {
      isInQnnTable = true, match: { url }
    } = this.props;
    const { visibleBjdh, list, flowButtons } = this.state;
    this.props.match.path = url + '/HasTodoList';
    return (
      <div style={{ height: isInQnnTable ? "" : "100vh" }} className={s.root}>
        <div style={{ fontSize: 20, fontWeight: 700, textAlign: 'center' }} >合同会签单</div>
        {list ? <Form
          {...this.props}
          {...config}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
          wrappedComponentRef={(me) => {
            this.flowForm = me;
          }}
          todoListModuleShow={true}
          doctModuleShow={true}
          filesModuleShow={true}
          flowButtons={flowButtons}
          initialValueByDocs={list?.applyFileList}
          initialValueByFiles={list?.contrApplyFileList}
          formConfig={[
            {
              field: 'applyId',
              type: 'string',
              initialValue: list?.applyId,
              hide: true,
            },
            {
              field: 'workId',
              type: 'string',
              initialValue: list?.workId,
              hide: true,
            },
            {
              field: 'apih5FlowStatus',
              type: 'string',
              initialValue: list?.apih5FlowStatus,
              hide: true,
            },
            {
              field: 'beginPer',
              type: 'string',
              initialValue: list?.beginPer,
              hide: true,
            },
            {
              label: '合同编号',
              field: "contractNo",
              type: 'string',
              qnnDisabled: true,
              initialValue: list?.contractNo,
              placeholder: '请输入',
              span: 12,
              formItemLayout: {
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              }
            },
            {
                label: '甲方名称',
                field: 'firstName',
                type: 'string',
                initialValue: list?.firstName,
                required: true,
                qnnDisabled: true,
                span: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 8 },
                    sm: { span: 8 }
                  },
                  wrapperCol: {
                    xs: { span: 16 },
                    sm: { span: 16 }
                  }
                }
              },
              {
                label: '甲方名称',
                type: 'string',
                field: "firstID",
                initialValue: list?.firstID,
                hide: true,
              },
              {
                label: '乙方名称',
                field: 'secondName',
                type: 'string',
                initialValue: list?.secondName,
                required: true,
                qnnDisabled: true,
                span: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 8 },
                    sm: { span: 8 }
                  },
                  wrapperCol: {
                    xs: { span: 16 },
                    sm: { span: 16 }
                  }
                }
              },
              {
                label: '乙方名称',
                field: "secondID",
                type: 'string',
                initialValue: list?.secondID,
                hide: true
              },
            {
              label: '合同名称',
              field: "contractName",
              type: 'string',
              required: true,
              qnnDisabled: true,
              initialValue: list?.contractName,
              placeholder: '请输入',
              span: 12,
              formItemLayout: {
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              }
            },
            {
              label: '合同类型',
              field: "contractType",
              type: 'string',
              qnnDisabled: true,
              initialValue: list?.contractType,
              placeholder: '请输入',
              span: 12,
              formItemLayout: {
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              }
            },
            {
              field: 'comID',
              type: 'string',
              initialValue: list?.comID,
              hide: true,
            },
            {
              field: 'comName',
              type: 'string',
              initialValue: list?.comName,
              hide: true,
            },
            {
              field: 'orgID',
              type: 'string',
              initialValue: list?.orgID,
              hide: true,
            },
            {
              field: 'orgName',
              type: 'string',
              initialValue: list?.orgName,
              hide: true,
            },
            {
              label: '合同签订人',
              field: "agent",
              type: 'string',
              qnnDisabled: true,
              hide: true,
              initialValue: list?.agent,
              placeholder: '请输入',
              span: 12,
              formItemLayout: {
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              }
            },
            {
              label: '含税合同金额(万元)',
              field: "contractCost",
              type: 'number',
              qnnDisabled: true,
              hide: true,
              initialValue: list?.contractCost,
              placeholder: '请输入',
              span: 12,
              precision: 6,
              formItemLayout: {
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              }
            },
            {
              label: '不含税合同金额(万元)',
              field: "contractCostNoTax",
              type: 'number',
              qnnDisabled: true,
              hide: true,
              initialValue: list?.contractCostNoTax,
              placeholder: '请输入',
              span: 12,
              precision: 6,
              formItemLayout: {
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              }
            },
            {
              label: '合同税额(万元)',
              field: "contractCostTax",
              type: 'number',
              qnnDisabled: true,
              hide: true,
              initialValue: list?.contractCostTax,
              placeholder: '请输入',
              span: 12,
              precision: 6,
              formItemLayout: {
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              }
            },
            {
              label: '是否抵扣',
              field: "isDeduct",
              type: 'checkbox',
              optionData: [
                {
                  value: '1'
                }
              ],
              qnnDisabled: true,
              hide: true,
              span: 12,
              initialValue: list?.isDeduct,
              formItemLayout: {
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              }
            },
            {
              label: '合同工期',
              field: "csTimeLimit",
              type: 'string',
              qnnDisabled: true,
              hide: true,
              placeholder: '请输入',
              span: 12,
              initialValue: list?.csTimeLimit,
              formItemLayout: {
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              }
            },
            {
              label: '合同类别',
              field: "code7",
              type: 'select',
              optionConfig: {
                label: 'itemName', //默认 label
                value: 'itemId'
              },
              fetchConfig: {
                apiName: 'getBaseCodeSelect',
                otherParams: {
                  itemId: 'category_contr_type_CL'
                }
              },
              initialValue: list?.code7,
              placeholder: '请选择',
              span: 12,
              formItemLayout: {
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              },
              qnnDisabled: true,
              hide: true,
              required: true
            },
            {
              label: '物资来源',
              field: "materialSource",
              type: 'select',
              optionConfig: {
                label: 'itemName', //默认 label
                value: 'itemId'
              },
              fetchConfig: {
                apiName: 'getBaseCodeSelect',
                otherParams: {
                  itemId: 'category_contract_materialSource'
                }
              },
              condition: [
                {
                  regex: { 'apiBody.code7': 'WZ' },
                  action: ['required', 'unDisabled'],
                },
                {
                  regex: { 'apiBody.code7': ['!', 'WZ'] },
                  action: ['unRequired', 'disabled'],
                }
              ],
              initialValue: list?.materialSource,
              qnnDisabled: true,
              hide: true,
              placeholder: '请选择',
              span: 12,
              formItemLayout: {
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              }
            },
            // {
            //     label: '采购方式',
            //     field: "shopWay",
            //     type: 'select',
            //     optionConfig: {
            //         label: 'label', //默认 label
            //         value: 'value'
            //     },
            //     optionData: [
            //         {
            //             label: '招标采购',
            //             value: '0'
            //         },
            //         {
            //             label: '云电商采购',
            //             value: '1'
            //         },
            //         {
            //             label: '其他采购',
            //             value: '2'
            //         }
            //     ],
            //     initialValue: flowData?.shopWay,
            //     placeholder: '请选择',
            //     span: 12,
            //     qnnDisabled: true,
            //     required: true,
            //     formItemLayout: {
            //         labelCol: {
            //             xs: { span: 8 },
            //             sm: { span: 8 }
            //         },
            //         wrapperCol: {
            //             xs: { span: 16 },
            //             sm: { span: 16 }
            //         }
            //     }
            // },
            // {
            //     label: '采购编号',
            //     field: "shopNumber",
            //     type: 'string',
            //     condition: [
            //         {
            //             regex: { 'apiBody.shopWay': '2' },
            //             action: ['unRequired', 'disabled'],
            //         },
            //         {
            //             regex: { 'apiBody.shopWay': ['!', '2'] },
            //             action: ['required', 'unDisabled'],
            //         }
            //     ],
            //     qnnDisabled: true,
            //     initialValue: flowData?.shopNumber,
            //     placeholder: '请输入',
            //     span: 12,
            //     formItemLayout: {
            //         labelCol: {
            //             xs: { span: 8 },
            //             sm: { span: 8 }
            //         },
            //         wrapperCol: {
            //             xs: { span: 16 },
            //             sm: { span: 16 }
            //         }
            //     }
            // },
            {
              field: 'resCategoryName',
              type: 'string',
              initialValue: list?.resCategoryName,
              hide: true,
            },
            {
              label: '物资类别',
              field: "resCategoryID",
              type: 'select',
              optionConfig: {
                label: 'catName', //默认 label
                value: 'id'
              },
              fetchConfig: {
                apiName: 'getZxSkResCategoryMaterialsList',
                otherParams: {
                  parentID: '0002'
                }
              },
              qnnDisabled: true,
              hide: true,
              initialValue: list?.resCategoryID,
              placeholder: '请选择',
              span: 12,
              formItemLayout: {
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              }
            },
            {
              label: '合同内容',
              field: 'content',
              type: 'textarea',
              initialValue: list?.content,
              placeholder: '请输入',
              qnnDisabled: true,
              hide: true,
              span: 12,
              formItemLayout: {
                labelCol: {
                  xs: { span: 4 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 20 },
                  sm: { span: 20 }
                }
              }
            },
            {
              label: '备注',
              field: 'remarks',
              type: 'textarea',
              initialValue: list?.remarks,
              placeholder: '请输入',
              qnnDisabled: true,
              hide: true,
              span: 12,
              formItemLayout: {
                labelCol: {
                  xs: { span: 4 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 20 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "component",
              field: "componentQD",
              label: '清单查看',
              Component: obj => {
                return (
                  <Button type="primary" onClick={() => {
                    this.setState({
                      visibleBjdh: true
                    })
                  }}>清单查看</Button>
                );
              },
              span: 12,
            },
            {
              type: 'files',
              field: 'applyShopList',
              hide: true,
              initialValue: list?.applyShopList,
            },
            {
              type: 'files',
              field: 'applyLeaseList',
              hide: true,
              initialValue: list?.applyLeaseList,
            },
            {
              field: 'initiateFlowReview',
              type: 'textarea',
              label: '主要内容',
              opinionField: true,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.initiateFlowReview ? (list.initiateFlowReview).replace(/<br\/>/g, "\n") : '';
                return data
              },
              span: 12,
            },
            {
                type: "textarea",
                label: "项目各部门会签",
                field: "opinionField2",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField2 ? (list.opinionField2).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "项目各领导会签",
                field: "opinionField3",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField3 ? (list.opinionField3).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "公司主管部门审核",
                field: "opinionField4",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField4 ? (list.opinionField4).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "公司各部门(含法律部)会签",
                field: "opinionField5",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField6 ? (list.opinionField6).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "公司主管领导审核",
                field: "opinionField6",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField5 ? (list.opinionField5).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "公司总经理审批",
                field: "opinionField7",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField7 ? (list.opinionField7).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "总部主管部门审核",
                field: "opinionField8",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField8 ? (list.opinionField8).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "总部各部门会签",
                field: "opinionField9",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField9 ? (list.opinionField9).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "总部领导会签",
                field: "opinionField10",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField10 ? (list.opinionField10).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "托管公司主管部门审核",
                field: "opinionField11",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField11 ? (list.opinionField11).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "托管公司各部门会签",
                field: "opinionField12",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField12 ? (list.opinionField12).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "托管公司领导审批",
                field: "opinionField13",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField13 ? (list.opinionField13).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "事业部审核",
                field: "opinionField14",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField14 ? (list.opinionField14).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "事业部领导审批",
                field: "opinionField15",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField15 ? (list.opinionField15).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "相关事业部审核",
                field: "opinionField16",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField16 ? (list.opinionField16).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "相关事业部领导审批",
                field: "opinionField17",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField17 ? (list.opinionField17).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "法律顾问审核",
                field: "opinionField18",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField18 ? (list.opinionField18).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            },
            {
                type: "textarea",
                label: "合同校对",
                field: "opinionField19",
                opinionField: true,
                qnnDisabled: true,
                initialValue: () => {
                  let data = list && list.opinionField19 ? (list.opinionField19).replace(/<br\/>/g, "\n") : '';
                  return data
                },
                span: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                    },
                    wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                    }
                }
            }
          ]}
        /> : null}
        <Modal
          width='1200px'
          style={{ top: '0' }}
          title="清单信息"
          visible={visibleBjdh}
          footer={null}
          onCancel={this.handleCancelBjdh}
          bodyStyle={{ width: '1200px' }}
          centered={true}
          destroyOnClose={this.handleCancelBjdh}
          wrapClassName={'modals'}
        >
          <QnnForm
            {...this.props}
            match={this.props.match}
            fetch={this.props.myFetch}
            upload={this.props.myUpload}
            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
            formConfig={[
              {
                type: 'qnnTable',
                field: 'applyShopList',
                incToForm: true,
                hide: list?.code7 !== 'WL' ? false : true,
                initialValue:list?.applyShopList || [],
                qnnTableConfig: {
                  antd: {
                    rowKey: 'applyShopListId',
                    size: 'small'
                  },
                  paginationConfig: {
                    position: 'bottom'
                  },
                  isShowRowSelect: false,
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '主键id',
                        field: 'applyShopListId',
                        hide: true
                      }
                    },
                    {
                      isInTable: false,
                      form: {
                        field: 'addFlag',
                        type: 'string',
                        hide: true,
                      }
                    },
                    {
                      isInTable: false,
                      form: {
                        field: 'pp5',
                        type: 'string',
                        hide: true,
                      }
                    },
                    {
                      isInTable: false,
                      form: {
                        field: 'workNo',
                        type: 'string',
                        hide: true,
                      }
                    },
                    {
                      isInTable: false,
                      form: {
                        field: 'workType',
                        type: 'string',
                        hide: true,
                      }
                    },
                    {
                      table: {
                        title: `<div>物资类别<span style='color: #ff4d4f'>*</span></div>`,
                        dataIndex: 'workTypeID',
                        key: 'workTypeID',
                        width: 150,
                        fixed: 'left',
                        type: 'select'
                      },
                      form: {
                        type: 'select',
                        optionConfig: {
                          label: 'catName', //默认 label
                          value: 'id'
                        },
                        fetchConfig: {
                          apiName: 'getZxSkResCategoryMaterialsList',
                          otherParams: {
                            parentID: '0002'
                          }
                        },
                        allowClear: false,
                        showSearch: true,
                        required: true,
                        placeholder: '请选择'
                      }
                    },
                    {
                      table: {
                        title: `<div>物资编码<span style='color: #ff4d4f'>*</span></div>`,
                        dataIndex: 'workNo',
                        key: 'workNo',
                        width: 150,
                        fixed: 'left'
                      },
                      form: {
                        type: 'selectByQnnTable',
                        dependencies: ['workTypeID'],
                        dependenciesReRender: true,
                        optionConfig: {//下拉选项配置
                          label: 'resCode',
                          value: 'id'
                        },
                        dropdownMatchSelectWidth: 800,
                        qnnTableConfig: {
                          antd: {
                            rowKey: "id"
                          },
                          fetchConfig: {
                            apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                            otherParams: (obj) => {
                              let editRowData = obj.qnnFormProps?.qnnformData?.qnnFormProps?.qnnTableInstance?.getEditedRowDataSync?.();
                              return {
                                id: editRowData?.workTypeID,
                              }

                            }
                          },
                          searchBtnsStyle: "inline",
                          formConfig: [
                            {
                              isInForm: false,
                              isInSearch: true,
                              table: {
                                dataIndex: "resCode",
                                title: "编号",
                                width: 100
                              },
                              form: {
                                type: 'string'
                              }
                            },
                            {
                              isInForm: false,
                              isInSearch: true,
                              table: {
                                dataIndex: "resName",
                                title: "名称",
                                width: 100
                              },
                              form: {
                                type: 'string'
                              }
                            },
                            {
                              isInForm: false,
                              isInSearch: true,
                              table: {
                                dataIndex: "spec",
                                title: "规格型号",
                              },
                              form: {
                                type: "string"
                              }
                            },
                            {
                              isInForm: false,
                              isInSearch: true,
                              table: {
                                dataIndex: "unit",
                                title: "单位",
                              },
                              form: {
                                type: "string"
                              }
                            }
                          ]
                        },
                        allowClear: false,
                        showSearch: true,
                        required: true,
                        placeholder: '请选择'
                      }
                    },
                    {
                      table: {
                        title: '物资名称',
                        dataIndex: 'workName',
                        key: 'workName',
                        width: 150
                      },
                      form: {
                        type: 'string',
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '物资规格',
                        dataIndex: 'spec',
                        key: 'spec',
                        width: 100
                      },
                      form: {
                        type: 'string',
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '单位',
                        dataIndex: 'unit',
                        key: 'unit',
                        width: 100
                      },
                      form: {
                        type: 'string',
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '是否网价',
                        dataIndex: 'isNetPrice',
                        key: 'isNetPrice',
                        width: 100,
                        render: (data) => {
                          if (data === '1') {
                            return '是';
                          } else {
                            return '否';
                          }
                        }
                      },
                      form: {
                        type: 'checkbox',
                        optionData: [
                          {
                            value: "1"
                          }
                        ]
                      }
                    },
                    {
                      table: {
                        title: '数量',
                        dataIndex: 'qty',
                        key: 'qty',
                        width: 100,
                        render: (data, rowData) => {
                          if (rowData.qty || rowData.qty === 0) {
                            return rowData.qty.toFixed(3)
                          }
                        }
                      },
                      form: {
                        type: 'number',
                        precision: 3,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '含税合同单价',
                        dataIndex: 'price',
                        key: 'price',
                        width: 120,
                        render: (data, rowData) => {
                          if (rowData.price || rowData.price === 0) {
                            return rowData.price.toFixed(6)
                          }
                        }
                      },
                      form: {
                        type: 'number',
                        precision: 6,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '含税合同金额',
                        dataIndex: 'contractSum',
                        key: 'contractSum',
                        width: 120,
                        render: (data, rowData) => {
                          if (rowData.contractSum || rowData.contractSum === 0) {
                            return rowData.contractSum.toFixed(2)
                          }
                        }
                      },
                      form: {
                        type: 'number',
                        precision: 2,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '不含税合同单价',
                        dataIndex: 'priceNoTax',
                        key: 'priceNoTax',
                        width: 120,
                        render: (data, rowData) => {
                          if (rowData.priceNoTax || rowData.priceNoTax === 0) {
                            return rowData.priceNoTax.toFixed(6)
                          }
                        }
                      },
                      form: {
                        type: 'number',
                        precision: 6,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '不含税合同金额',
                        dataIndex: 'contractSumNoTax',
                        key: 'contractSumNoTax',
                        width: 120,
                        render: (data, rowData) => {
                          if (rowData.contractSumNoTax || rowData.contractSumNoTax === 0) {
                            return rowData.contractSumNoTax.toFixed(2)
                          }
                        }
                      },
                      form: {
                        type: 'number',
                        precision: 2,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '税额',
                        dataIndex: 'contractSumTax',
                        key: 'contractSumTax',
                        width: 100,
                        render: (data, rowData) => {
                          if (rowData.contractSumTax || rowData.contractSumTax === 0) {
                            return rowData.contractSumTax.toFixed(2)
                          }
                        }
                      },
                      form: {
                        type: 'number',
                        precision: 2,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: `<div>税率(%)<span style='color: #ff4d4f'>*</span></div>`,
                        dataIndex: 'taxRate',
                        key: 'taxRate',
                        width: 100,
                        type: 'select'
                      },
                      form: {
                        type: 'select',
                        optionConfig: {
                          label: 'itemName', //默认 label
                          value: 'itemId'
                        },
                        fetchConfig: {
                          apiName: 'getBaseCodeSelect',
                          otherParams: {
                            itemId: 'shuiLV'
                          }
                        },
                        allowClear: false,
                        showSearch: true,
                        required: true,
                        placeholder: '请选择'
                      }
                    },
                    {
                      table: {
                        title: '是否抵扣',
                        dataIndex: 'isDeduct',
                        key: 'isDeduct',
                        width: 100,
                        render: (data) => {
                          if (data === '1') {
                            return '是';
                          } else {
                            return '否';
                          }
                        }
                      },
                      form: {
                        type: 'string',
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '备注',
                        dataIndex: 'remarks',
                        key: 'remarks',
                        width: 100
                      },
                      form: {
                        type: 'string',
                        placeholder: '请输入'
                      }
                    }
                  ]
                }
              },
              {
                type: 'qnnTable',
                field: 'applyLeaseList',
                incToForm: true,
                hide: list?.code7 === 'WL' ? false : true,
                initialValue:list?.applyLeaseList || [],
                qnnTableConfig: {
                  antd: {
                    rowKey: 'applyLeaseListId',
                    size: 'small'
                  },
                  isShowRowSelect: false,
                  paginationConfig: {
                    position: 'bottom'
                  },
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '主键id',
                        field: 'applyLeaseListId',
                        hide: true
                      }
                    },
                    {
                      isInTable: false,
                      form: {
                        field: 'addFlag',
                        type: 'string',
                        hide: true,
                      }
                    },
                    {
                      isInTable: false,
                      form: {
                        field: 'pp5',
                        type: 'string',
                        hide: true,
                      }
                    },
                    {
                      isInTable: false,
                      form: {
                        field: 'workNo',
                        type: 'string',
                        hide: true,
                      }
                    },
                    {
                      isInTable: false,
                      form: {
                        field: 'workType',
                        type: 'string',
                        hide: true,
                      }
                    },
                    {
                      table: {
                        title: `<div>物资类别<span style='color: #ff4d4f'>*</span></div>`,
                        dataIndex: 'workTypeID',
                        key: 'workTypeID',
                        width: 150,
                        fixed: 'left',
                        type: 'select'
                      },
                      form: {
                        type: 'select',
                        optionConfig: {
                          label: 'catName', //默认 label
                          value: 'id'
                        },
                        fetchConfig: {
                          apiName: 'getZxSkResCategoryMaterialsList',
                          otherParams: {
                            parentID: '0002'
                          }
                        },
                        allowClear: false,
                        showSearch: true,
                        required: true,
                        placeholder: '请选择'
                      }
                    },
                    {
                      table: {
                        title: `<div>物资编码<span style='color: #ff4d4f'>*</span></div>`,
                        dataIndex: 'workNo',
                        key: 'workNo',
                        width: 150,
                        fixed: 'left'
                      },
                      form: {
                        type: 'selectByQnnTable',
                        dependencies: ['workTypeID'],
                        dependenciesReRender: true,
                        optionConfig: {//下拉选项配置
                          label: 'resCode',
                          value: 'id'
                        },
                        dropdownMatchSelectWidth: 800,
                        qnnTableConfig: {
                          antd: {
                            rowKey: "id"
                          },
                          fetchConfig: {
                            apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                            otherParams: (obj) => {
                              let editRowData = obj.qnnFormProps?.qnnformData?.qnnFormProps?.qnnTableInstance?.getEditedRowDataSync?.();
                              return {
                                id: editRowData?.workTypeID,
                              }

                            }
                          },
                          searchBtnsStyle: "inline",
                          formConfig: [
                            {
                              isInForm: false,
                              isInSearch: true,
                              table: {
                                dataIndex: "resCode",
                                title: "编号",
                                width: 100
                              },
                              form: {
                                type: 'string'
                              }
                            },
                            {
                              isInForm: false,
                              isInSearch: true,
                              table: {
                                dataIndex: "resName",
                                title: "名称",
                                width: 100
                              },
                              form: {
                                type: 'string'
                              }
                            },
                            {
                              isInForm: false,
                              isInSearch: true,
                              table: {
                                dataIndex: "spec",
                                title: "规格型号",
                              },
                              form: {
                                type: "string"
                              }
                            },
                            {
                              isInForm: false,
                              isInSearch: true,
                              table: {
                                dataIndex: "unit",
                                title: "单位",
                              },
                              form: {
                                type: "string"
                              }
                            }
                          ]
                        },
                        allowClear: false,
                        showSearch: true,
                        required: true,
                        placeholder: '请选择'
                      }
                    },
                    {
                      table: {
                        title: '物资名称',
                        dataIndex: 'workName',
                        key: 'workName',
                        width: 150
                      },
                      form: {
                        type: 'string',
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '物资规格',
                        dataIndex: 'spec',
                        key: 'spec',
                        width: 100
                      },
                      form: {
                        type: 'string',
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '单位',
                        dataIndex: 'unit',
                        key: 'unit',
                        width: 100
                      },
                      form: {
                        type: 'string',
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '是否网价',
                        dataIndex: 'isNetPrice',
                        key: 'isNetPrice',
                        width: 100,
                        render: (data) => {
                          if (data === '1') {
                            return '是';
                          } else {
                            return '否';
                          }
                        }
                      },
                      form: {
                        type: 'checkbox',
                        optionData: [
                          {
                            value: "1"
                          }
                        ]
                      }
                    },
                    {
                      table: {
                        title: '租期单位',
                        dataIndex: 'rentUnit',
                        key: 'rentUnit',
                        width: 150
                      },
                      form: {
                        type: 'string',
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '租期',
                        dataIndex: 'contrTrrm',
                        key: 'contrTrrm',
                        width: 100,
                        render: (data, rowData) => {
                            if (rowData.contrTrrm || rowData.contrTrrm === 0) {
                              return rowData.contrTrrm.toFixed(3)
                            }
                          }
                      },
                      form: {
                        type: 'number',
                        precision: 3,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '数量',
                        dataIndex: 'qty',
                        key: 'qty',
                        width: 100,
                        render: (data, rowData) => {
                          if (rowData.qty || rowData.qty === 0) {
                            return rowData.qty.toFixed(3)
                          }
                        }
                      },
                      form: {
                        type: 'number',
                        precision: 3,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '含税合同单价',
                        dataIndex: 'price',
                        key: 'price',
                        width: 120,
                        render: (data, rowData) => {
                          if (rowData.price || rowData.price === 0) {
                            return rowData.price.toFixed(6)
                          }
                        }
                      },
                      form: {
                        type: 'number',
                        precision: 6,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '含税合同金额',
                        dataIndex: 'contractSum',
                        key: 'contractSum',
                        width: 120,
                        render: (data, rowData) => {
                          if (rowData.contractSum || rowData.contractSum === 0) {
                            return rowData.contractSum.toFixed(2)
                          }
                        }
                      },
                      form: {
                        type: 'number',
                        precision: 2,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '不含税合同单价',
                        dataIndex: 'priceNoTax',
                        key: 'priceNoTax',
                        width: 120,
                        render: (data, rowData) => {
                          if (rowData.priceNoTax || rowData.priceNoTax === 0) {
                            return rowData.priceNoTax.toFixed(6)
                          }
                        }
                      },
                      form: {
                        type: 'number',
                        precision: 6,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '不含税合同金额',
                        dataIndex: 'contractSumNoTax',
                        key: 'contractSumNoTax',
                        width: 120,
                        render: (data, rowData) => {
                          if (rowData.contractSumNoTax || rowData.contractSumNoTax === 0) {
                            return rowData.contractSumNoTax.toFixed(2)
                          }
                        }
                      },
                      form: {
                        type: 'number',
                        precision: 2,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '税额',
                        dataIndex: 'contractSumTax',
                        key: 'contractSumTax',
                        width: 100,
                        render: (data, rowData) => {
                          if (rowData.contractSumTax || rowData.contractSumTax === 0) {
                            return rowData.contractSumTax.toFixed(2)
                          }
                        }
                      },
                      form: {
                        type: 'number',
                        precision: 2,
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: `<div>税率(%)<span style='color: #ff4d4f'>*</span></div>`,
                        dataIndex: 'taxRate',
                        key: 'taxRate',
                        width: 100,
                        type: 'select'
                      },
                      form: {
                        type: 'select',
                        optionConfig: {
                          label: 'itemName', //默认 label
                          value: 'itemId'
                        },
                        fetchConfig: {
                          apiName: 'getBaseCodeSelect',
                          otherParams: {
                            itemId: 'shuiLV'
                          }
                        },
                        allowClear: false,
                        showSearch: true,
                        required: true,
                        placeholder: '请选择'
                      }
                    },
                    {
                      table: {
                        title: '是否抵扣',
                        dataIndex: 'isDeduct',
                        key: 'isDeduct',
                        width: 100,
                        render: (data) => {
                          if (data === '1') {
                            return '是';
                          } else {
                            return '否';
                          }
                        }
                      },
                      form: {
                        type: 'string',
                        placeholder: '请输入'
                      }
                    },
                    {
                      table: {
                        title: '备注',
                        dataIndex: 'remarks',
                        key: 'remarks',
                        width: 100
                      },
                      form: {
                        type: 'string',
                        placeholder: '请输入'
                      }
                    }
                  ]
                }
              }
            ]}
          />
        </Modal>
      </div>
    );
  }
}
export default index;