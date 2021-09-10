import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
const config = {
  antd: {
    rowKey: 'zxCmGlobalCodeId',
    size: 'small'
  },
  isShowRowSelect: true,
  paginationConfig: {
    position: 'bottom'
  },
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
    const { categoryID } = this.props
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
          fetchConfig={{
            apiName: 'getZxCmGlobalCodeList',
            otherParams: { categoryID }
          }}
          tableTdEdit={true}
          tableTdEditFetchConfig={async (obj) => {
            let isAdd = this.isNumber(obj.rowId)
            let body = await obj.qnnTableInstance.getEditedRowData()
            if (body.percentage && (body.percentage > 100 || body.percentage < 0)) {
              Msg.warn('请输入正确的比率！')
              this.tableOne.setRowEditByRowId(obj.rowId)
              return
            }
            if (isAdd) body.zxCmGlobalCodeId = null
            body.categoryID = categoryID
            return {
              apiName: isAdd ? 'addZxCmGlobalCode' : 'updateZxCmGlobalCode',
              otherParams: { ...body },
              success: ({ success, message }) => {
                if (success) {
                  obj.btnCallbackFn.refresh()
                  Msg.success(message)
                } else {
                  Msg.error(message)
                }
              }
            }
          }}
          actionBtns={[
            {
              name: 'addRow',
              icon: 'plus',
              type: 'primary',
              label: '新增行',
            },
            {
              name: 'del',
              icon: 'del',
              type: 'danger',
              label: '删除',
              fetchConfig: { apiName: 'batchDeleteUpdateZxCmGlobalCode' }
            },
          ]}
          formConfig={[
            {
              isInTable: false,
              form: {
                field: 'zxCmGlobalCodeId',
                type: 'string',
                hide: true,
              }
            },
            {
              table: {
                title: '代码编号',
                dataIndex: 'globalID',
                key: 'globalID',
                width: 200,
                tooltip: 20,
                align: 'center',
                tdEdit: true,
              },
              form: {
                type: 'string',
                placeholder: '请输入',
              }
            },
            {
              table: {
                title: '代码描述',
                width: 180,
                tooltip: 18,
                tdEdit: true,
                dataIndex: 'globalDesc',
                key: 'globalDesc',
                align: 'center',
              },
              form: {
                type: 'string',
                placeholder: '请输入',
              }
            },
            {
              isInTable: () => {
                if (categoryID !== 'categorysafe001009') {
                  return false
                } else {
                  return true
                }
              },
              table: {
                title: '比率%',
                dataIndex: 'percentage',
                key: 'percentage',
                width: 100,
                tdEdit: true,
                align: 'center',
              },
              form: {
                type: 'number',
                placeholder: '请输入',
              }
            },
            {
              table: {
                title: '是否启用',
                dataIndex: 'enable',
                key: 'enable',
                width: 100,
                tdEdit: true,
                align: 'center',
                type: 'select',
              },
              form: {
                type: 'select',
                placeholder: '请选择',
                optionData: [{ label: '是', value: '1' }, { label: '否', value: '0' }],
                optionConfig: { label: 'label', value: 'value' },
                initialValue: '0'
              }
            },
            {
              table: {
                title: '是否默认选项',
                dataIndex: 'selected',
                key: 'selected',
                width: 100,
                tdEdit: true,
                align: 'center',
                type: 'select',
              },
              form: {
                type: 'select',
                placeholder: '请选择',
                optionData: [{ label: '是', value: '1' }, { label: '否', value: '0' }],
                optionConfig: { label: 'label', value: 'value' },
                initialValue: '0'
              }
            },
            {
              isInTable: () => {
                if (categoryID === 'categorysafe001009') {
                  return false
                } else {
                  return true
                }
              },
              table: {
                title: '地区',
                dataIndex: 'region',
                key: 'region',
                width: 120,
                align: 'center',
                tdEdit: true
              },
              form: {
                type: 'string',
                placeholder: '请输入',
              }
            },
            {
              table: {
                title: '备注',
                dataIndex: 'remarks',
                key: 'remarks',
                width: 150,
                tdEdit: true,
                align: 'center',
              },
              form: {
                type: 'string',
                placeholder: '请输入',
              }
            },
          ]}
        />
      </div>
    );
  }
}

export default index;