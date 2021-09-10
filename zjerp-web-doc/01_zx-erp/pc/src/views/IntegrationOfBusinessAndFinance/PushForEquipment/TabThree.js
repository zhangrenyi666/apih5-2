import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button } from 'antd';
import { message as Msg } from 'antd';
class index extends Component {
  componentDidMount() {
    if (this.props.onRef) {
      this.props.onRef(this);
    }
  }
  render() {
    let params = this.props
    return (
      <div style={{ padding: '10px' }}>
        <QnnForm
          fetch={this.props.myFetch}
          fetchConfig={{
            apiName: 'getZxSaSettleAuditDatacheckByMainId',
            otherParams: { jsxId: params.baseData.zxSaEquipSettleAuditId }
          }}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          formItemLayout={{
            labelCol: {
              xs: { span: 12 },
              sm: { span: 12 }
            },
            wrapperCol: {
              xs: { span: 12 },
              sm: { span: 12 }
            }
          }}
          formConfig={[
            {
              type: "component",
              field: "component1",
              Component: obj => {
                return (
                  <div style={{ background: '#f0f2f5', padding: '8px 12px', color: 'red' }}>
                    说明：当“本期调差金额”、“本期税额调差”不超过1时，才能保存“结算项”、“负计量”、“成本科目”！
                  </div>
                );
              }
            },
            {
              type: 'component',
              field: 'aa',
              hide: params.clickName === 'detail',
              Component: obj => {
                return (
                  <div style={{ textAlign: 'right', padding: '12px' }}>
                    <Button type="primary" onClick={() => {
                      let params = obj.form.getFieldsValue()
                      if (Math.abs(params.tcse) > 1) {
                        Msg.warn('本期税额调差不能超过1')
                        return
                      }
                      if (Math.abs(params.tcje) > 1) {
                        Msg.warn('本期调差金额不能超过1')
                        return
                      }
                      params.jsxId = this.props.baseData.zxSaEquipSettleAuditId
                      this.props.myFetch('updateEqZxSaSettleAuditDatacheck', params)
                        .then(({ success, message }) => {
                          if (success) {
                            obj.btnCallbackFn.refresh()
                            Msg.success(message)
                            //保存后刷新基础信息
                            this.props.myFetch('getZxSaEquipSettleAuditDetail', { zxSaEquipSettleAuditId: this.props.baseData.zxSaEquipSettleAuditId })
                              .then(({ data }) => {
                                this.props.TabOneRef.setDeawerValues(data)
                              })
                          } else {
                            Msg.error(message)
                          }
                        })
                    }}>保存</Button>
                  </div>
                );
              },
            },
            {
              field: "zxSaSettleAuditDatacheckId",
              type: 'string',
              hide: true,
            },
            {
              label: '本期发票价税合计',
              field: "fpjshj",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 6,
              precision:2
            },
            {
              label: '本期结算金额含税',
              field: "settleAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 6,
              precision:2
            },
            {
              label: '本期调差后金额',
              field: "adjustSettleAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 6,
              precision:2
            },
            {
              label: '本期调差金额(1元以内)',
              field: "tcje",
              type: 'number',
              disabled: true,
              initialValue: 0,
              precision:2,
              span: 6,
            },
            {
              label: '本期发票税额',
              field: "fpse",
              type: 'number',
              initialValue: 0,
              span: 6,
              disabled: params.clickName === 'detail',
              precision:2,
              onBlur: (val, obj) => {
                let value = val.target.value;
                let formData = obj.form.getFieldsValue()
                if (formData.fpse || formData.fpse === 0) {
                  obj.form.setFieldsValue({
                    tcse: value - formData?.adjustSettleAmtTax,
                    adjustSettleAmtTax: formData.fpse
                  })
                }
              }
            },
            {
              label: '本期结算税额',
              field: "settleAmtTax",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 6,
              precision:2
            },
            {
              label: '本期调差后税额',
              field: "adjustSettleAmtTax",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 6,
              precision:2
            },
            {
              label: '本期税额调差',
              field: "tcse",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 6,
              precision:2
            }
          ]}
        />
      </div>
    );
  }
}
export default index;
