import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import moment from 'moment';
const config = {
  antd: {
    rowKey: 'zxSfFeeId',
    size: 'small'
  },
  drawerConfig: {
    width: '1200px'
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
      xs: { span: 16 },
      sm: { span: 16 }
    }
  },
  firstRowIsSearch: false,
  isShowRowSelect: true
};
class index extends Component {
  constructor(props) {
    super(props)
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
            this.tableOne = me;
          }}
          {...config}
          actionBtns={[
            {
              name: 'add',
              icon: 'plus',
              type: 'primary',
              label: '新增',
              formBtns: [
                {
                  name: 'cancel',
                  type: 'dashed',
                  label: '取消',
                },
                {
                  name: 'submit',
                  type: 'primary',
                  label: '保存',
                  fetchConfig: {
                    apiName: 'addZxSfFee'
                  }
                }
              ]
            },
            {
              name: "edit",
              label: "修改",
              field: "edit",
              type: "primary",
              onClick: (obj) => {
                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
              },
              formBtns: [
                {
                  name: 'cancel', //关闭右边抽屉
                  type: 'dashed',//类型  默认 primary
                  label: '取消',
                },
                {
                  name: 'diySubmit',
                  field: 'diySubmit',
                  type: 'primary',
                  label: '保存',
                  onClick: (obj) => {
                    obj.btnCallbackFn.clearSelectedRows();
                    obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                    let params = obj._formData
                    this.props.myFetch('updateZxSfFee', params).then(
                      ({ data, success, message }) => {
                        if (success) {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.success(message);
                          obj.btnCallbackFn.refresh();
                          obj.btnCallbackFn.closeDrawer();
                        } else {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.error(message);
                        }
                      }
                    );
                  }
                }
              ]
            },
            {
              name: "del",
              label: "删除",
              icon: "delete",
              field: "del",
              type: "danger",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length === 0) {
                  return true
                }
              },
              fetchConfig: {
                apiName: "batchDeleteUpdateZxSfFee",
              }
            },
          ]}
          fetchConfig={{
            apiName: 'getZxSfFeeList',
            otherParams: () => {
              return {
                orgID: departmentId,
              }

            }
          }}
          formConfig={[
            {
              isInTable: false,
              form: {
                field: 'zxSfFeeId',
                type: 'string',
                hide: true,
              }
            },
            {
              isInTable: false,
              form: {
                field: 'orgName',
                type: 'string',
                hide: true,
              }
            },
            {
              table: {
                title: '季度',
                dataIndex: 'season',
                key: 'season',
                align: "center",
                width: 150,
                fixed: 'left',
                format: 'YYYY-Q',
                filter: true,
                fieldsConfig: {
                  type: 'rangeDate',
                  field: 'seasonQuery'
                },
                render: (h) => {
                  let season = moment(h).format('yyyy-Q');
                  return season
                }
              },
              form: {
                field: 'season',
                type: 'quarter',
                spanForm: 12,
                required:true
              }
            },
            {
              isInTable: false,
              form: {
                label: '项目名称',
                field: "orgName",
                type: 'string',
                spanForm: 12,
                disabled: true,
                addShow: false,
                editShow: false,
                detailShow: true,
              }
            },
            {
              table: {
                title: '项目名称',
                dataIndex: 'orgName',
                key: 'orgName',
                align: "center",
                width: 160,
                filter: true,
                fieldsConfig: { 
                  type: 'string' ,
                  field: "orgName"
                },
                fixed: 'left',
                tooltip: 15,
                onClick: 'detail',
              },
              form: {
                addShow: true,
                editShow: true,
                detailShow: false,
                field: 'orgID',
                required:true,
                type: 'select',
                spanForm: 12,
                parent: 'season',
                fetchConfig: (obj) => {
                  let rowData = obj.btnCallbackFn.form.getFieldsValue()
                  if (rowData.season  && obj.clickCb.rowInfo.name !== 'detail') {
                    let season = '';
                    if (moment(rowData.season).quarter() === 1) {
                      season = `${moment(rowData.season).format('YYYY')}01`;
                    } else if (moment(rowData.season).quarter() === 2) {
                      season = `${moment(rowData.season).format('YYYY')}02`
                    } else if (moment(rowData.season).quarter() === 3) {
                      season = `${moment(rowData.season).format('YYYY')}03`
                    } else if (moment(rowData.season).quarter() === 4) {
                      season = `${moment(rowData.season).format('YYYY')}04`
                    }
                    return {
                      apiName: 'getSysCompanyProject',
                      otherParams: {
                        departmentId: departmentId,
                        season: season
                      }
                    }
                  }
                },
                optionConfig: {
                  label: 'departmentName',
                  value: 'departmentId',
                  linkageFields: {
                    orgName: 'departmentName',
                    produceAmt: 'totalProduceAmt'
                  }
                },
              }
            },
            {
              table: {
                title: '本季度产值',
                dataIndex: 'produceAmt',
                key: 'produceAmt',
                align: "center",
                width: 120,
              },
              form: {
                field: 'produceAmt',
                editDisabled:true,
                addDisabled:true,
                type: 'number',
                spanForm: 12,
                initialValue: 0
              }
            },
            {
              table: {
                title: '本期季度安全费用支出',
                dataIndex: 'feeAmt',
                key: 'feeAmt',
                align: "center",
                width: 150,
              },
              form: {
                field: 'feeAmt',
                type: 'number',
                spanForm: 12,
                required:true
              }
            },
            {
              isInTable: false,
              form: {
                label: '备注',
                field: 'remarks',
                type: 'textarea',
                spanForm: 12,
                autoSize: {
                  minRows: 1,
                  maxRows: 3
                },
              }
            },
            {
              isInTable: false,
              form: {
                label: '附件',
                spanForm: 12,
                field: 'fileList',
                type: 'files',
                fetchConfig: {
                  apiName: 'upload'
                },
                formItemLayout: {
                  labelCol: {
                    xs: { span: 8 },
                    sm: { span: 8 }
                  },
                  wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 12 }
                  }
                },
              }
            },
          ]}
        />
      </div>
    );
  }
}

export default index;