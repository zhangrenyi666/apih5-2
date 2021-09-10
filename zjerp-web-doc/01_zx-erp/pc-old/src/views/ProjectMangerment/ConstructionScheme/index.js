import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import ConstructionSchemeForm from './form';
import { message as Msg } from 'antd';
import toPY from './pinyin'
const config = {
  antd: {
    rowKey: function (row) {
      return row.zxScGroupSchemeId
    },
    size: 'small'
  },
  drawerConfig: {
    width: '1000px'
  },
  paginationConfig: {
    position: 'bottom'
  },
  formItemLayout: {
    labelCol: {
      xs: { span: 8 },
      sm: { span: 8 }
    },
    wrapperCol: {
      xs: { span: 18 },
      sm: { span: 18 }
    }
  },
};
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {

    }
  }

  render() {
    const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany
    return (
      <div>
        <QnnTable
          {...this.props}
          fetch={this.props.myFetch}
          upload={this.props.myUpload}
          headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          {...config}
          fetchConfig={{
            apiName: 'getZxScGroupSchemeList',
            otherParams: {
              orgID: departmentId,
            }
          }}
          componentsKey={{
            ConstructionSchemeForm: (obj) => {
              this.table.clearSelectedRows();
              return <ConstructionSchemeForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
            }
          }}
          actionBtns={[
            {
              name: 'add',//内置add del
              icon: 'plus',//icon
              type: 'primary',//类型  默认 primary  [primary dashed danger]
              label: '新增',
              formBtns: [
                {
                  name: 'cancel', //关闭右边抽屉
                  type: 'dashed',//类型  默认 primary
                  label: '取消',
                },
                {
                  name: 'submit',//内置add del
                  type: 'primary',//类型  默认 primary
                  label: '保存',//提交数据并且关闭右边抽屉 
                  fetchConfig: {//ajax配置
                    apiName: 'addZxScGroupScheme',
                    otherParams: { apih5FlowStatus: '-1' }
                  }
                }
              ]
            },
            {
              name: 'edit',//内置add del
              icon: 'edit',//icon
              type: 'primary',//类型  默认 primary  [primary dashed danger]
              label: '修改',
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1 || SelectedRowsData[0].apih5FlowStatus === '1' || SelectedRowsData[0].apih5FlowStatus === '2') {
                  return true;
                } else {
                  return false;
                }
              },
              formBtns: [
                {
                  name: 'cancel', //关闭右边抽屉
                  type: 'dashed',//类型  默认 primary
                  label: '取消',
                },
                {
                  name: 'submit',//内置add del
                  type: 'primary',//类型  默认 primary
                  label: '保存',//提交数据并且关闭右边抽屉 
                  fetchConfig: {//ajax配置
                    apiName: 'updateZxScGroupScheme',
                  },
                  onClick: (obj) => {
                    obj.btnCallbackFn.clearSelectedRows();
                  }
                }
              ]
            },
            {
              Component: "ConstructionSchemeForm",
              disabled: "bind:_actionBtnNoSelected",
              drawerTitle: "发起",
              icon: "plus",
              label: "审核",
              name: "Component",
              tableField: "projectInfo",
              tableName: "施组评审发起",
              type: "primary",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1 || SelectedRowsData[0].apih5FlowStatus !== '-1') {
                  return true;
                } else {
                  return false;
                }
              },
            },
            {
              name: 'del',//内置add del
              icon: 'delete',//icon
              type: 'danger',//类型  默认 primary  [primary dashed danger]
              label: '删除',
              fetchConfig: {//ajax配置
                apiName: 'batchDeleteUpdateZxScGroupScheme',
              },
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length > 0) {
                  for (var i = 0; i < SelectedRowsData.length; i++) {
                    if (SelectedRowsData[i].apih5FlowStatus === '1' || SelectedRowsData[i].apih5FlowStatus === '2') {
                      return true
                    }
                  }
                }
              },
            }
          ]}
          formConfig={[
            {
              isInTable: false,
              form: {
                field: 'zxScGroupSchemeId',
                type: 'string',
                hide: true,
              }
            },
            {
              isInTable: false,
              form: {
                field: 'workId',
                type: 'string',
                hide: true,
              }
            },
            {
              isInTable: false,
              form: {
                field: 'apih5FlowStatus',
                type: 'string',
                hide: true,
              }
            },
            {
              isInTable: false,
              form: {
                field: 'comId',
                type: 'string',
                hide: true,
              }
            },
            {
              table: {
                title: '单位名称',
                dataIndex: 'comName',
                key: 'comName',
                fixed: 'left',
                width: 150,
                tooltip: 15,
                filter: true,
              },
              form: {
                field: 'comName',
                type: 'string',
                spanForm: 12,
                editDisabled: true,
                addDisabled: true,
              }
            },
            {
              isInTable: false,
              form: {
                field: 'projectName',
                type: 'string',
                hide: true,
              }
            },
            {
              table: {
                title: '项目名称',
                dataIndex: 'projectName',
                key: 'projectName',
                width: 170,
                tooltip: 17,
                filter: true,
                onClick: 'detail',
              },
              form: {
                type: 'select',
                showSearch: true,
                field: 'projectId',
                spanForm: 12,
                required: true,
                editDisabled: true,
                optionConfig: {
                  label: 'orgName', //默认 label
                  value: 'orgID',
                  linkageFields: {
                    comName: 'companyName',
                    projectName: 'orgName',
                    province: 'provinceAbbreviation',
                    comId: 'companyId'
                  }
                },
                fetchConfig: () => {
                  return {
                    apiName: 'getZxCtContractListByStatus',
                    otherParams: { orgID: departmentId }
                  }
                },
                onChange: (val, obj) => {
                  if (val) {
                    let pageData = obj.form.getFieldsValue()
                    let params = {}
                    params.projectId = pageData.projectId
                    this.props.myFetch('getZxScGroupSchemeSequence', params).then(
                      ({ data, success, message }) => {
                        if (success) {
                          let provincePY = ''
                          this.props.myFetch('getBaseCodeSelect', { itemId: 'xingzhengquhuadaima' })
                            .then(({ data, success }) => {
                              if (success && data && data.length > 0) {
                                for (var i = 0; i < data.length; i++) {
                                  if (obj.itemData.provinceAbbreviation === data[i].itemAsName) {
                                    provincePY = data[i].ext1
                                  }
                                }
                              }
                            })
                            .then(() => {
                              obj.form.setFieldsValue({
                                schemeNo: `${obj.itemData.contractor}-${obj.itemData.biddingQualification}-${provincePY}-${obj.itemData.shortName}-SZ-${data}`
                              })
                            })

                        } else {
                          Msg.error(message);
                        }
                      }
                    );
                  } else {
                    obj.form.setFieldsValue({
                      schemeNo: '',
                    })
                  }
                },
              }
            },
            {
              table: {
                title: '施组编号',
                dataIndex: 'schemeNo',
                key: 'schemeNo',
                width: 180,
                tooltip: 20,
                filter: true,
              },
              form: {
                field: 'schemeNo',
                editDisabled: true,
                addDisabled: true,
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
              }
            },
            {
              table: {
                title: '施组名称',
                dataIndex: 'schemeName',
                key: 'schemeName',
                width: 150,
                filter: true,
              },
              form: {
                field: 'schemeName',
                required: true,
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
              }
            },
            {
              table: {
                title: '工程类别',
                dataIndex: 'projectType',
                key: 'projectType',
                width: 150,
                filter: true,
                render: (item) => {
                  if (item === '0') {
                    return '公路工程'
                  } else if (item === '1') {
                    return '铁路工程'
                  } else if (item === '2') {
                    return '市政工程'
                  } else if (item === '3') {
                    return '轨道交通'
                  } else if (item === '4') {
                    return '交安设施'
                  } else if (item === '5') {
                    return '养护工程'
                  } else if (item === '6') {
                    return '汽车试验场'
                  } else if (item === '7') {
                    return '港口工程'
                  } else if (item === '8') {
                    return '房建工程'
                  } else if (item === '9') {
                    return '机场'
                  } else if (item === '10') {
                    return '其他工程'
                  }
                }
              },
              form: {
                field: 'projectType',
                type: 'select',
                spanForm: 12,
                optionConfig: {
                  label: 'label', //默认 label
                  value: 'value'
                },
                optionData: [
                  { label: '公路工程', value: '0' },
                  { label: '铁路工程', value: '1' },
                  { label: '市政工程', value: '2' },
                  { label: '轨道交通', value: '3' },
                  { label: '交安设施', value: '4' },
                  { label: '养护工程', value: '5' },
                  { label: '汽车试验场', value: '6' },
                  { label: '港口工程', value: '7' },
                  { label: '房建工程', value: '8' },
                  { label: '机场', value: '9' },
                  { label: '其他工程', value: '10' },

                ],
              }
            },
            {
              isInTable: false,
              form: {
                label: '所在省份',
                field: 'province',
                type: 'string',
                editDisabled: true,
                addDisabled: true,
                spanForm: 12,
              }
            },
            {
              table: {
                title: '合同工期',
                dataIndex: 'contrDuration',
                key: 'contrDuration',
                width: 150,
              },
              form: {
                field: 'contrDuration',
                required: true,
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
              }
            },
            {
              isInTable: false,
              form: {
                required: true,
                label: '计划开工日期',
                field: 'planDate',
                width: 100,
                format: 'YYYY-MM-DD',
                type: 'date',
                placeholder: '请选择',
                spanForm: 12,
              }
            },
            {
              table: {
                title: '合同金额(万元)',
                dataIndex: 'contractAmt',
                key: 'contractAmt',
                width: 150,
              },
              form: {
                field: 'contractAmt',
                required: true,
                type: 'number',
                placeholder: '请输入',
                spanForm: 12,
              }
            },
            {
              isInTable: false,
              form: {
                label: '项目经理',
                field: 'projManager',
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
              }
            },
            {
              isInTable: false,
              form: {
                required: true,
                label: '项目总工',
                field: 'projEngineer',
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
              }
            },
            {
              table: {
                title: '项目联系人',
                dataIndex: 'schemeAppro',
                key: 'schemeAppro',
                width: 150,
              },
              form: {
                label: '项目联系人',
                field: 'schemeAppro',
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
                initialValue:this.props.loginAndLogoutInfo.loginInfo.userInfo.userId
              }
            },
            {
              isInTable: false,
              form: {
                label: '项目总工联系方式',
                field: 'engineerTel',
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
              }
            },

            {
              isInTable: false,
              form: {
                required: true,
                label: '项目联系人联系方式',
                field: 'approTel',
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
              }
            },
            {
              isInTable: false,
              form: {
                editDisabled: true,
                addDisabled: true,
                label: '上报日期',
                field: 'bizDate',
                type: 'date',
                spanForm: 12,
                initialValue: new Date(),
              }
            },
            {
              isInTable: false,
              form: {
                label: '备注',
                field: 'remark',
                type: 'textarea',
                placeholder: '请输入',
                spanForm: 12,
              }
            },
            {
              table: {
                title: '评审结束时间',
                dataIndex: 'passTime',
                key: 'passTime',
                width: 150,
                format: 'YYYY-MM-DD',
              },
              isInForm: false,
            },
            {
              table: {
                title: '评审状态',
                dataIndex: 'apih5FlowStatus',
                key: 'apih5FlowStatus',
                width: 100,
                fixed: 'right',
                render: (data) => {
                  if (data === '0') {
                    return '待提交';
                  } else if (data === '1') {
                    return '审核中';
                  } else if (data === '2') {
                    return '审核完成';
                  } else if (data === '3') {
                    return '退回';
                  } else if (data === '-1') {
                    return '未审核';
                  }
                }
              },
              isInForm: false,
            }
          ]}
        />
      </div>
    );
  }
}

export default index;