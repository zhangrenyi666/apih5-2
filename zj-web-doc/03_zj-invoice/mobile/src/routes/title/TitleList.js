import React, { Component } from 'react';
import { WingBlank, WhiteSpace, List, Flex, Button } from 'antd-mobile';
import { MyFetch } from '../../main';
import './TitleList.less';
class TitleList extends Component {
  constructor(props) {
    super(props);
    const { match: { params: { linkType, invoiceTitleListId } } } = this.props
    this.linkType = linkType//"0"为普通详情；"1"为外链详情
    this.invoiceTitleListId = invoiceTitleListId
    this.state = {
      data: []
    }
  }
  componentDidMount() {//首次加载，执行手动刷新方法
    MyFetch("getZjInvoiceList", { invoiceTitleListId: this.invoiceTitleListId }, ({ data, success }) => {
      if (success) {
        this.setState({ data })
      }
    })
  }
  render() {
    const { data } = this.state;
    const { history } = this.props;
    const Item = ({ data, color }) => {
      return (
        <div>
          <WhiteSpace />
          <WingBlank>
            <div style={{ lineHeight: 1.5 }} onClick={() => { history.push(`/Title/Detail/${this.invoiceTitleListId}/${data.invoiceId}`) }} className={`lny-TitleList-item lny-color-${color}-br`}>
              <div style={{ color: "#333", fontSize: ".36rem" }}>{data.companyName}</div>
              <div style={{ color: "#999", fontSize: ".28rem", marginTop: ".1rem" }}>{`税号：${data.taxNumber ? data.taxNumber : "无"}`}</div>
            </div>
          </WingBlank>
        </div>
      )
    }
    return (
      <div className="page lny-TitleList">
        <div className="lny-container flex">
          <List>
            {data && data.length > 0 ?
              data.map((data, i) => {
                return <Item key={i} data={data} color={i % 2 === 0 ? "red" : "orange"} />
              }) : <div className="lny-list-footer">{"暂无数据"}</div>
            }
          </List>
        </div>

        <Flex className="lny-footer">
          {this.linkType === "1" ?
            <Flex.Item>
              <Button type="ghost" onClick={() => { window.location.href = "http://weixin.fheb.cn/ZJOA/initIndex.do?flg=1" }} >{"返回首页"}</Button>
            </Flex.Item>
            :
            <Flex.Item>
              <Button type="ghost" onClick={() => { this.props.history.go(-1) }} >{"返回"}</Button>
            </Flex.Item>
          }
        </Flex>
      </div>
    );
  }
}

export default TitleList;