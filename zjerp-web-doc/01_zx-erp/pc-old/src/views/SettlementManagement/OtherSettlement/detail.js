import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import moment from 'moment';
import { Drawer, Modal, Button } from "antd";
import ManageContract from './manageContract';
const confirm = Modal.confirm;
const config = {
  formItemLayout: {
    labelCol: {
      xs: { span: 5 },
      sm: { span: 5 }
    },
    wrapperCol: {
      xs: { span: 19 },
      sm: { span: 19 }
    }
  }
};
class index extends Component {
  constructor(props) {
    super(props)
    this.state = {
      lineName: '',
      visible: false,
    }
  }
  onClose = () => {
    this.setState({
      visible: false,
    });
  };
  render() {
    let { myPublic: { domain, appInfo: { ureport } }, flowData = {} } = this.props;
    const { visible } = this.state;
    return (
      <div style={{ padding: '10px' }}>
        <QnnForm
          {...this.props}
          fetch={this.props.myFetch}
          upload={this.props.myUpload}
          headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
          {...config}
          fetchConfig={{
            apiName: 'getZxSaOtherEquipSettleDetail',
            otherParams: { zxSaOtherEquipSettleId: flowData?.zxSaOtherEquipSettleId }
          }}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          formConfig={[
            {
              label: '结算单编号',
              field: "billNo",
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              label: '结算类型',
              field: "billType",
              type: 'select',
              span: 12,
              optionConfig: {
                label: 'label',
                value: 'value',
              },
              disabled: true,
              optionData: [
                { label: '返还保证金', value: '2' },
                { label: '中期', value: '0' },
                { label: '最终', value: '1' },
              ]
            },
            {
              label: '合同甲方',
              field: "orgName",
              type: 'string',
              disabled: true,
              span: 12
            },
            {
              label: '合同乙方',
              field: "secondName",
              type: 'string',
              disabled: true,
              span: 12
            },
            {
              label: '结算时限',
              field: "secondName_1",
              type: 'string',
              disabled: true,
              span: 12,
              initialValue: () => {
                let beginDate = new Date(moment(flowData?.beginDate).format());
                let endDate = new Date(moment(flowData?.endDate).format());
                let BeginDate = beginDate.getFullYear() + '-' + (beginDate.getMonth() + 1) + '-' + beginDate.getDate()
                let EndDate = endDate.getFullYear() + '-' + (endDate.getMonth() + 1) + '-' + endDate.getDate()
                let value = BeginDate + '至' + EndDate
                return value
              }
            },
            {
              label: '是否抵扣',
              field: "isDeduct",
              type: 'radio',
              disabled: true,
              optionData: [
                { label: '是', value: '1' },
                { label: '否', value: '0' }
              ],
              initialValue: '0',
              span: 12,
            },
            {
              label: '结算内容及说明',
              field: 'content',
              type: 'textarea',
              span: 12,
              disabled: true,
            },
            {
              field: "diyButton2",
              type: 'Component',
              Component: obj => {
                return <Button
                  style={{
                    margin: 20, background: 'rgb(24,144,255)',
                    marginLeft: 0
                  }}
                  onClick={() => {
                    this.setState({ visible: true })
                  }}
                >
                  <p style={{ color: '#fff' }}>合同查询</p>
                </Button>
              },
              span: 24,
            },
            {
              field: "diyText",
              type: 'Component',
              Component: obj => {
                return <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>审批内容</div>
              },
              span: 24,
            },
            {
              field: "diyButton",
              type: 'Component',
              Component: obj => {
                return <div
                  style={{
                    marginLeft: 60, marginTop: 20,
                    overflow: 'hidden',
                  }}
                >
                  <p style={{ float: 'left' }}>结算单明细：</p>
                  <p
                    style={{ float: 'left', color: 'rgb(24,144,255)' }}
                    onClick={() => {
                      confirm({
                        content: '确定下载该结算单吗?',
                        onOk: () => {
                          window.open(`${ureport}excel?_u=file:zxSaOtherEquipSettleList.xml&url=${domain}&zxSaOtherEquipSettleId=${flowData?.zxSaOtherEquipSettleId}&zxCtOtherManageId=${flowData?.zxCtOtherManageId}`);
                        }
                      })
                    }}
                  >{flowData?.billNo} - 其他类结算单</p>
                </div>
              },
              span: 24,
            },
            {
              field: "diyText1",
              type: 'Component',
              Component: obj => {
                return <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>附件</div>
              },
              span: 24,
            },
            {
              label: '附件',
              field: 'zxErpFileList',
              type: 'files',
              disabled: true,
              fetchConfig: {
                apiName: 'upload'
              },
              span: 12
            },
            {
              type: 'qnnTable',
              field: 'table1',
              qnnTableConfig: {
                fetchConfig: () => {
                  return {
                    apiName: 'getZxSaOtherEquipSettleItemList',
                    otherParams: { zxSaOtherEquipSettleId: flowData?.zxSaOtherEquipSettleId }
                  }
                },
                antd: {
                  rowKey: 'zxSaOtherEquipSettleItemId',
                  size: 'small'
                },
                paginationConfig: false,
                isShowRowSelect: false,
                pageSize: 999,
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                formConfig: [
                  {
                    table: {
                      title: '统计项',
                      dataIndex: 'statisticsName',
                      key: 'statisticsName',
                      width: 100,
                      type: 'string',
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '本期（元）',
                      dataIndex: 'thisAmt',
                      key: 'thisAmt',
                      width: 150,
                      align: 'center',
                      render: (val) => {
                        if (val && !isNaN(val)) {
                          return Number(val).toFixed(2)
                        } else {
                          return val
                        }
                      },
                    },
                  },
                  {
                    table: {
                      title: '开累（元）',
                      dataIndex: 'totalAmt',
                      key: 'totalAmt',
                      width: 100,
                      align: 'center',
                      render: (val) => {
                        if (val && !isNaN(val)) {
                          return Number(val).toFixed(2)
                        } else {
                          return val
                        }
                      },
                    },
                  },
                ],
              }
            }
          ]}
        />
        {
          visible ? <Drawer
            title={'附属类合同管理'}
            placement="right"
            onClose={this.onClose}
            visible={visible}
            width={window.screen.width * 0.75}
            className={'drawerClass'}
          >
            <ManageContract {...this.props} flowData={flowData} />
          </Drawer> : null
        }
      </div>
    );
  }
}
export default index;
