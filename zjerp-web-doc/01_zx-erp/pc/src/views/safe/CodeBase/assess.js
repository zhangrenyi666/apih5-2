import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
const config = {
  antd: {
    rowKey: 'zxSfAccessRoomId',
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
            apiName: 'getZxSfAccessRoomList',
            otherParams: { categoryID: 'categorysafe001003' }
          }}
          tableTdEdit={true}
          tableTdEditFetchConfig={async (obj) => {
            let isAdd = this.isNumber(obj.rowId)
            let body = await obj.qnnTableInstance.getEditedRowData()
            if (body.allScore > 100 || body.allScore < 0) {
              Msg.warn('请输入正确的满分！')
              this.tableOne.setRowEditByRowId(obj.rowId)
              return
            }
            if (body.orderNo < 0) {
              Msg.warn('请输入正确的排序！')
              return
            }
            if (isAdd) body.zxSfAccessRoomId = null
            body.categoryID = 'categorysafe001003'
            return {
              apiName: isAdd ? 'addZxSfAccessRoom' : 'updateZxSfAccessRoom',
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
              fetchConfig: { apiName: 'batchDeleteUpdateZxSfAccessRoom' }
            },
          ]}
          formConfig={[
            {
              isInTable: false,
              form: {
                field: 'zxSfAccessRoomId',
                type: 'string',
                hide: true,
              }
            },
            {
              table: {
                title: '考核项目',
                dataIndex: 'examName',
                key: 'examName',
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
                title: '考核内容',
                width: 180,
                tooltip: 18,
                tdEdit: true,
                dataIndex: 'examContext',
                key: 'examContext',
                align: 'center',
              },
              form: {
                type: 'string',
                placeholder: '请输入',
              }
            },
            {
              table: {
                title: '满分',
                dataIndex: 'allScore',
                key: 'allScore',
                width: 120,
                align: 'center',
                tdEdit: true
              },
              form: {
                type: 'number',
                placeholder: '请输入',
              }
            },
            {
              table: {
                title: '排序',
                dataIndex: 'orderNo',
                key: 'orderNo',
                width: 150,
                tdEdit: true,
                align: 'center',
              },
              form: {
                type: 'number',
                placeholder: '请输入',
              }
            }
          ]}
        />
      </div>
    );
  }
}

export default index;