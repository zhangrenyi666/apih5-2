import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg } from 'antd';
const config = {
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
};
class index extends Component {
  isNumber(obj) {
    var t1 = /^\d+(\.\d+)?$/; //非负浮点数
    var t2 = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    if (t1.test(obj) || t2.test(obj)) {
      return true;
    } else {
      return false;
    }

  }
  render() {
    let params = this.props
    return (
      <div style={{ padding: '10px' }}>
        <QnnForm
          {...this.props}
          fetch={this.props.myFetch}
          {...config}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          formConfig={[
            {
              type: 'qnnTable',
              field: 'table1',
              qnnTableConfig: {
                fetchConfig: () => {
                  if (params.paramsData.tabIndex === '1') {
                    return {
                      apiName: 'getZxSfSpecialEmpItemList',
                      otherParams: { zxSfSpecialEmpId: params.paramsData.zxSfSpecialEmpId },
                    }
                  } else { return {} }
                },
                antd: {
                  rowKey: 'zxSfSpecialEmpItemId',
                  size: 'small'
                },
                actionBtnsContainerStyle: {
                  textAlign: 'right'
                },
                wrappedComponentRef: (me) => {
                  this.tableTwo = me;
                },
                actionBtns: [
                  {
                    name: 'addRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '新增行',
                    hide: () => { if (params.paramsData.clickName === 'detail') return true },
                    addRowDefaultData: {
                      addFlag: '1',
                      orgID: params.paramsData.orgID,
                      orgName: params.paramsData.orgName,
                    }
                  },
                  {
                    name: 'delRow',
                    icon: 'del',
                    type: 'danger',
                    label: '删除',
                    hide: () => { if (params.paramsData.clickName === 'detail') return true },
                  },
                  {
                    name: 'diyButton',
                    type: 'primary',
                    label: '保存',
                    field: "diyButton",
                    hide: () => { if (params.paramsData.clickName === 'detail') return true },
                    onClick: async (obj) => {
                      let emptyData = []
                      let meterList = await obj.qnnTableInstance.getTableData()
                      await meterList.map((item) => {
                        if (!item.name) emptyData.push('姓名')
                        if (!item.opProjName) emptyData.push('准操作项目')
                        item.seID = this.props.paramsData.zxSfSpecialEmpId
                        return item
                      })
                      if (emptyData.length > 0) {
                        Msg.warn(`请填写${emptyData.join()}`)
                        return
                      } else {
                        obj.btnCallbackFn.setBtnsLoading('add', 'diyButton');
                        let params = []
                        params = meterList
                        this.props.myFetch('batchSaveUpdateZxSfSpecialEmpItem', params)
                          .then(({ success, message }) => {
                            if (success) {
                              obj.btnCallbackFn.setBtnsLoading('remove', 'diyButton');
                              obj.btnCallbackFn.refresh()
                              Msg.success(message)
                            } else {
                              obj.btnCallbackFn.setBtnsLoading('remove', 'diyButton');
                              Msg.error(message)
                            }
                          })
                      }
                    }
                  }
                ],
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      label: '主键id',
                      field: 'zxSfSpecialEmpItemId',
                      hide: true
                    }
                  },
                  {
                    isInTable: false,
                    form: {
                      field: 'orgID',
                      hide: true,
                    }
                  },
                  {
                    table: {
                      title: '所在项目',
                      dataIndex: 'orgName',
                      key: 'orgName',
                      width: 200,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '姓名',
                      dataIndex: 'name',
                      key: 'name',
                      width: 120,
                      align: 'center',
                      tdEdit: params.paramsData.clickName !== 'detail',
                    },
                    form: {
                      required: true,
                      type: 'string',
                      placeholder: '请输入',
                    }
                  },
                  {
                    table: {
                      title: '性别',
                      width: 120,
                      tdEdit: params.paramsData.clickName !== 'detail',
                      dataIndex: 'sex',
                      key: 'sex',
                      align: 'center',
                      type: 'select',
                    },
                    form: {
                      type: 'select',
                      placeholder: '请选择',
                      optionData: [
                        { label: '男', value: '0' },
                        { label: "女", value: '1' }
                      ],
                      optionConfig: {
                        label: 'label',
                        value: 'value',
                      }
                    }
                  },
                  {
                    table: {
                      title: '准操作项目',
                      dataIndex: 'opProjName',
                      key: 'opProjName',
                      width: 250,
                      tdEdit: params.paramsData.clickName !== 'detail',
                      align: 'center',
                      type: 'select'
                    },
                    form: {
                      required: true,
                      type: 'select',
                      multiple: true,
                      placeholder: '请选择',
                      optionData: [
                        { label: '电工作业', value: '0' },
                        { label: "金属焊接切割作业", value: '1' },
                        { label: '起重机械含电梯作业', value: '2' },
                        { label: "企业内机动车辆驾驶", value: '3' },
                        { label: '登高架设作业', value: '4' },
                        { label: "锅炉作业", value: '5' },
                        { label: '压力容器操作', value: '6' },
                        { label: "爆破作业", value: '7' },
                        { label: '其他', value: '8' },
                      ],
                      optionConfig: {
                        label: 'label',
                        value: 'value',
                      },
                    }
                  },
                  {
                    table: {
                      title: '发证机关',
                      dataIndex: 'sendName',
                      key: 'sendName',
                      width: 150,
                      tdEdit: params.paramsData.clickName !== 'detail',
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      placeholder: '请输入',
                    }
                  },
                  {
                    table: {
                      title: '发证日期',
                      dataIndex: 'sendDate',
                      key: 'sendDate',
                      width: 150,
                      tdEdit: params.paramsData.clickName !== 'detail',
                      align: 'center',
                      format: 'YYYY-MM-DD',
                    },
                    form: {
                      type: 'date',
                      placeholder: '请选择',
                    }
                  },
                  {
                    table: {
                      title: '证件号码',
                      dataIndex: 'cardNo',
                      key: 'cardNo',
                      width: 200,
                      tdEdit: params.paramsData.clickName !== 'detail',
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      placeholder: '请输入',
                    }
                  },
                  {
                    table: {
                      title: '复审日期',
                      dataIndex: 'checkDate',
                      key: 'checkDate',
                      width: 150,
                      tdEdit: params.paramsData.clickName !== 'detail',
                      align: 'center',
                      format: 'YYYY-MM-DD',
                    },
                    form: {
                      type: 'date',
                      placeholder: '请选择',
                    }
                  },
                  {
                    table: {
                      title: '退场时间',
                      dataIndex: 'outDate',
                      key: 'outDate',
                      width: 150,
                      tdEdit: params.paramsData.clickName !== 'detail',
                      format: 'YYYY-MM-DD',
                      align: 'center',
                    },
                    form: {
                      type: 'date',
                      placeholder: '请选择',
                    }
                  }
                ]
              }
            }
          ]}
        />
      </div>
    );
  }
}
export default index;
