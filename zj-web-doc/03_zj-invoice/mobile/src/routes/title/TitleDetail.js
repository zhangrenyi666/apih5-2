import React, { Component } from 'react';

import { Button } from 'antd-mobile';//Toast,  WingBlank, WhiteSpace,Icon
import { MyFetch, getDomain } from '../../main';
import './TitleDetail.less';
class TitleDetail extends Component {
  constructor(props) {
    super(props);
    const { match: { params: { invoiceTitleListId, invoiceTitleId, linkType } } } = this.props
    this.linkType = linkType//"0"为普通详情；"1"为外链详情
    this.invoiceTitleListId = invoiceTitleListId
    this.invoiceTitleId = invoiceTitleId
    this.state = {
      companyName: "",
      taxNumber: "",
      companyPhone: "",
      unitAddress: "",
      bank: "",
      bankAccount: "",
      qrcodeUrl: "",
      invoiceUrl: "",
      btnType: 0, //0是增值税 1是普通
    }
  }
  componentDidMount() {//首次加载，执行手动刷新方法
    MyFetch("getInvoiceDetails", { invoiceId: this.invoiceTitleId }, ({ data, success }) => {
      if (success && data) {
        // console.log(data)
        this.setState({ ...data })
      }
    })
  }

  btnClick(num) {
    let state = this.state;
    state.btnType = num;
    this.setState(state)
  }

  render() {
    const { qrcodeUrl, invoiceUrl, companyName, taxNumber, companyPhone, unitAddress, bank, bankAccount } = this.state
    var viewDom = [];

    if (this.state.btnType === 0) {
      viewDom = [{
        label: '公司名称',
        value: companyName
      }, {
        label: "单位税号",
        value: taxNumber
      }, {
        label: "公司电话",
        value: companyPhone
      }, {
        label: "公司地址",
        value: unitAddress
      }, {
        label: "银行账号",
        value: bank
      }, {
        label: "银行账户",
        value: bankAccount
      }];
    } else if (this.state.btnType === 1) {
      viewDom = [{
        label: '公司名称',
        value: companyName
      }, {
        label: "单位税号",
        value: taxNumber
      }]
    }


    let _qrcodeUrl, _invoiceUrl
    if (new RegExp("http://").test(qrcodeUrl) || new RegExp("https://").test(qrcodeUrl)) {
      _qrcodeUrl = qrcodeUrl
    } else {
      _qrcodeUrl = getDomain("file") + qrcodeUrl
    }
    if (new RegExp("http://").test(invoiceUrl) || new RegExp("https://").test(invoiceUrl)) {
      _invoiceUrl = invoiceUrl
    } else {
      _invoiceUrl = getDomain("file") + invoiceUrl
    }
    return (
      <div className="lny-TitleDetail">
        <div className="lny-TitleDetail-upBox">
          <ul>
            {
              viewDom.map((v, i) => {
                return (<li key={i}>
                  <div>{v.label}</div>
                  <div>{v.value}</div>
                </li>)
              })
            }
          </ul>
        </div>

        <div className="lny-TitleDetail-downBox">
          <div>开票时候出示</div>
          <img alt="发票二维码" src={this.state.btnType === 0 ? _qrcodeUrl : _invoiceUrl} />
          <div className="lny-TitleDetail-downBox-btn">
            <div>
              <Button onClick={this.btnClick.bind(this, 0)} size="small" type={this.state.btnType === 1 ? "ghost" : "primary"}>   {this.state.btnType === 1 ? "切换为增值税" : "增值税发票"} </Button>
            </div>
            <div>
              <Button onClick={this.btnClick.bind(this, 1)} size="small" type={this.state.btnType === 0 ? "ghost" : "primary"}>  {this.state.btnType === 0 ? "切换为普通" : "普通发票"}</Button>
            </div>
          </div>
        </div>

        <div className="lny-TitleDetail-bottom">
          {
            this.linkType === "1"
              ?
              <div>
                <div className="btn" onClick={() => {
                  window.location.href = "http://weixin.fheb.cn/ZJOA/initIndex.do?flg=1"
                }}>返回首页</div>

                <div className="btn" onClick={() => {
                  this.props.history.push(`/Title/List/${this.invoiceTitleListId}`)
                }}>其他发票列表</div>
              </div>
              :
              <div onClick={() => {
                this.props.history.go(-1)
              }}> <span className="btn"> 返回列表 </span></div>
          }

        </div>

      </div>
    );
  }
}

export default TitleDetail;