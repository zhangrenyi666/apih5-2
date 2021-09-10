import React, { Component } from 'react';
import { List, WingBlank, ListView, Modal, Icon, PullToRefresh, WhiteSpace, Card } from 'antd-mobile';
import SvgIcon from '../common/svgIcon';
import './typeList.css';
const getTypeData = (e) => {
  var idkey = "", name = ""
  switch (e) {
    case 1:
      name = "TzLand";
      idkey = "landId";
      break;
    case 2:
      name = "TzDesignChart";
      idkey = "designChartId";
      break;
    case 3:
      name = "TzProjectFunds";
      idkey = "projectFundsId";
      break;
    case 4:
      name = "TzAssessment";
      idkey = "assessmentId";
      break;
    case 5:
      name = "TzGraphicProgress";
      idkey = "progressId";
      break;
    default:
      name = "";
      idkey = "";
      break;
  }
  return { name, idkey }
}


const dateFormat = (d, fmt) => {//格式化日期，d未new Date(),fmt为格式
  var o = {
    "M+": d.getMonth() + 1, //月份   
    "d+": d.getDate(), //日   
    "H+": d.getHours(), //小时   
    "m+": d.getMinutes(), //分   
    "s+": d.getSeconds(), //秒   
    "q+": Math.floor((d.getMonth() + 3) / 3), //季度   
    "S": d.getMilliseconds() //毫秒   
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}




class DailyDetail extends Component {
  constructor(props) {
    super(props);
    const dataSource = new ListView.DataSource({
      rowHasChanged: (row1, row2) => row1 !== row2,
    });
    this.initData = [];
    this.state = {
      accessoryList: [],
      refreshing: false,
      dataSource: dataSource.cloneWithRows(this.initData),
    }
  }
  manuallyRefreshFun = (otherObj) => {//手动刷新方法
    if (!otherObj) { otherObj = {} }
    this.manuallyRefresh = true;
    this.setState({ ...otherObj, refreshing: true });
  }
  componentWillUpdate(nextProps, nextState) {//判断是否为手动刷新   
    if (nextState.refreshing && this.manuallyRefresh) {
      this.manuallyRefresh = false
      this.onRefresh()
    }
  }
  // 第一次渲染后调用，只在客户端
  componentDidMount() {
    this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords })
  }
  componentWillReceiveProps(newProps) {
    if (this.props.type !== newProps.type) {
      this.manuallyRefresh = true;
      setTimeout(() => this.setState({ refreshing: true }), 10);
    }
  }
  onRefresh = () => {
    const { companyId, startDate, endDate, type, myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
    const { pathName } = routeData[curKey]
    const typeName = getTypeData(type).name;
    const body = { companyId, startDate, endDate }
    myFetch('get' + typeName + 'List', body).then(({ success, data, message, code }) => {
      if (success) {
        this.initData = [...data];
        if (this.initData.length === 0) {
          this.initData = [{ errMsg: "暂无数据" }]
        }
      } else if (code === "3003" || code === "3004") {
        dispatch(logout(pathName))
        return
      } else {
        this.initData = [{ errMsg: message }]
      }
      this.setState({
        dataSource: this.state.dataSource.cloneWithRows(this.initData),
        refreshing: false,
      });
    })
  }
  popupHide = () => {
    this.setState({ visible: false })
  }
  popupShow = (accessoryList) => {
    this.setState({ visible: true, accessoryList })
  }
  render() {
    const { visible, accessoryList, refreshing } = this.state
    const row = (rowData, sectionID, rowID) => {
      if (rowData.errMsg) {
        return <div style={{ textAlign: "center", lineHeight: "50px", color: "#999" }}>{rowData.errMsg}</div>
      } else {
        return (
          <WingBlank size="sm">
            <WhiteSpace size="sm" />
            <Card style={{ borderRadius: "0", borderLeft: (rowID % 2 === 0) ? "4px solid #FF3F01" : "4px solid #FFAF02" }}>
              <Card.Header
                style={{ fontSize: "14px", }}
                title={rowData["changes"] === "0" ? <span style={{ color: "#999999" }}>{"无变化"}</span> : <span style={{ color: "#0099ff" }}>{"有变化"}</span>}
                thumb={<SvgIcon style={{ marginRight: "5px" }} type={rowData["changes"] === "0" ? 'nochange' : 'change'} />}
                extra={<div>{dateFormat(new Date(rowData["fillDate"]), "yyyy-MM-dd")}</div>}
              />
              <Card.Body>
                <div>{rowData["progressDesc"]}</div>
              </Card.Body>
              <Card.Body>
                <div>{rowData["changeDesc"]}</div>
              </Card.Body>
              <Card.Footer content="" extra={rowData["accessoryList"].length > 0 ? (<span onClick={() => { this.popupShow(rowData["accessoryList"]) }} style={{ fontSize: "14px", whiteSpace: "nowrap", color: "#0099ff" }}>{"查看附件"}</span>) : ""} />
            </Card>
          </WingBlank>
        );
      }
    };
    return (
      <div className="page">
        <ListView
          className="flex"
          dataSource={this.state.dataSource}
          renderRow={row}
          scrollerOptions={{ scrollbars: true }}
          pullToRefresh={
            <PullToRefresh
              distanceToRefresh={window.devicePixelRatio * 10}
              refreshing={refreshing}
              onRefresh={this.onRefresh}
              indicator={{ deactivate: '下拉刷新' }}
            />
          }
        />
        <Modal
          popup
          visible={visible}
          animationType="slide-up"
        >
          <div className="typeList">
            <List
              renderHeader={() => (
                <div style={{ position: 'relative' }}>附件列表
                <span
                    style={{ position: 'absolute', right: 3, top: -5, }}
                    onClick={this.popupHide}>
                    <Icon type="cross" />
                  </span></div>)}
              className="popup-list"
            >
              {accessoryList.map((v, index) => (
                <List.Item key={index} >
                  <a onClick={() => {
                    window.location.href = v.url
                  }} style={{ fontSize: "14px" }}>{v.fileName}</a>
                </List.Item>
              ))}
            </List>
          </div>
        </Modal>
      </div>);
  }
}
export default DailyDetail;