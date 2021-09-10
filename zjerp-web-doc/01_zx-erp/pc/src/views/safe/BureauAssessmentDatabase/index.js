import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {
  antd: {
    rowKey: 'zxSfJuAccessRoomId',
    size: 'small'
  },
  drawerConfig: {
    width: '1200px'
  },
  paginationConfig: {
    position: 'bottom'
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
            apiName: 'getZxSfJuAccessRoomList'
          }}
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
                  field: 'addsubmit',
                  fetchConfig: {
                    apiName: 'addZxSfJuAccessRoom'
                  }
                }
              ]
            },
            {
              name: 'edit',
              icon: 'edit',
              type: 'primary',
              label: '修改',
              onClick: (obj) => {
                this.table.clearSelectedRows();
              },
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
                    apiName: 'updateZxSfJuAccessRoom'
                  }
                }
              ]
            },
            {
              name: 'del',
              icon: 'delete',
              type: 'danger',
              label: '删除',
              field: "del",
              fetchConfig: {
                apiName: 'batchDeleteUpdateZxSfJuAccessRoom',
              },
            },
          ]}
          formConfig={[
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'zxSfJuAccessRoomId',
                hide: true
              }
            },
          
            {
              table: {
                title: '考核项目',
                dataIndex: 'examName',
                key: 'examName',
                filter: true,
                width: 150,
                onClick: 'detail',
                tooltip: 15
              },
              form: {
                type: 'string',
                field: 'examName',
                required: true
              }
            },
            {
              table: {
                title: '考核内容',
                dataIndex: 'examContext',
                key: 'examContext',
                width: 300,
                tooltip: 30,
              },
              form: {
                type: 'textarea',
                field: 'examContext',
                autoSize: {
                  minRows: 1,
                  maxRows: 3
                },
              }
            },
            {
              table: {
                title: '满分',
                dataIndex: 'allScore',
                key: 'allScore',
              },
              form: {
                type: 'number',
                field: 'allScore'
              }
            },
            {
              isInTable:false,
              form: {
                label:'备注',
                type: 'textarea',
                field: 'notes',
                autoSize: {
                  minRows: 1,
                  maxRows: 3
                },
              }
            },
            {
              table: {
                title: '排序',
                dataIndex: 'orderNo',
                key: 'orderNo',
                width: 100
              },
              form: {
                type: 'number',
                field: 'orderNo'
              }
            }
          ]}
        />
      </div>
    );
  }
}

export default index;