import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
let config = {
  antd: {
    rowKey: function (row) {
      return row.id
    },
    size: 'small'
  },
  firstRowIsSearch: false,
  isShowRowSelect: false,
  formItemLayout: {
    labelCol: {
      xs: { span: 8 },
      sm: { span: 8 }
    },
    wrapperCol: {
      xs: { span: 18 },
      sm: { span: 18 }
    }
  }
};
class index extends Component {
  render() {
    
  console.log(this.props);
    return (
      <div style={{ padding: '10px', overflow: 'hidden' }}>
        <QnnTable
          fetch={this.props.myFetch}
          upload={this.props.myUpload}
          headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          {...config}
          fetchConfig={{
            apiName: 'getZxCtEqContrItemForLimitPriceList',
            otherParams: {
              zxEqResCategoryId: this.props.rowData.zxEqResCategoryId,
              comID: this.props.tableFns.form.getFieldValue('comID')
            }
          }}
          formConfig={[
            {
              table: {
                title: '公司名称',
                dataIndex: 'orgName',
                key: 'orgName',
                width: 150,
                filter: true,
                fieldsConfig: { type: 'string' },
                align: 'center',
              },
              isInForm: false
            },
            {
              table: {
                title: '数据期次',
                dataIndex: 'period',
                key: 'period',
                width: 150,
                align: 'center',
                render: (data) => {
                  return moment(data).month() === 0 ? (moment(data).format('YYYY') + '/上半年') : (moment(data).format('YYYY') + '/下半年');
                }
              },
            },
            {
              table: {
                title: '所在省份',
                dataIndex: 'province',
                key: 'province',
                width: 150,
                align: 'center',
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
                    itemId: 'xingzhengquhuadaima'
                  }
                },
              },
            },
            {
              table: {
                title: '设备名称',
                dataIndex: 'equipName',
                key: 'equipName',
                width: 150,
                align: 'center',
                filter: true,
                fieldsConfig: { type: 'string' },
              },
              isInForm: false
            },
            {
              table: {
                title: '厂家',
                dataIndex: 'factory',
                key: 'factory',
                width: 150,
                align: 'center',
              },
              isInForm: false
            },
            {
              table: {
                title: '规格型号',
                dataIndex: 'spec',
                key: 'spec',
                width: 150,
                align: 'center',
              },
              isInForm: false
            },
            {
              table: {
                title: '工作时间',
                dataIndex: 'workTime',
                key: 'workTime',
                width: 150,
                align: 'center',
                render: (h) => {
                  if (h === '0') {
                    return '单班'
                  } else if (h === '1') {
                    return '双班'
                  }
                }
              },
              isInForm: false
            },
            {
              table: {
                title: '租赁情况',
                dataIndex: 'rentContent',
                key: 'rentContent',
                width: 100,
                align: 'center',
                render: (h) => {
                  if (h === '0') {
                    return '六个月以下'
                  } else if (h === '1') {
                    return '六个月及以上'
                  }
                }
              },
              isInForm: false
            },
            {
              table: {
                title: '燃油情况',
                dataIndex: 'ranyouQingkuang',
                key: 'ranyouQingkuang',
                width: 150,
                align: 'center',
                render: (h) => {
                  if (h === '0') {
                    return '甲方承担'
                  } else if (h === '1') {
                    return '乙方承担'
                  }
                }
              },
              isInForm: false
            },
            {
              table: {
                title: '租赁限价（元/台.月）',
                dataIndex: 'rentPrice',
                key: 'rentPrice',
                width: 150,
                align: 'center',
              },
              isInForm: false
            },
            {
              table: {
                title: '其他说明',
                dataIndex: 'remark',
                key: 'remark',
                width: 150,
                align: 'center',
              },
              isInForm: false
            }
          ]}
        />
      </div>
    );
  }
}
export default index;
