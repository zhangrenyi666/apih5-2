import React, { Component } from 'react';
import { WingBlank, WhiteSpace, ListView, PullToRefresh, Flex } from 'antd-mobile';
import moment from 'moment';
import 'moment/locale/zh-cn';
import './basicInfor.css';
import Chart from '../common/chart.js';
class DailyDetail extends Component {
  constructor(props) {
    super(props);
    const dataSource = new ListView.DataSource({
      rowHasChanged: (row1, row2) => row1 !== row2,
    });
    this.initData = [];
    this.state = {
      refreshing: false,
      dataSource: dataSource.cloneWithRows(this.initData)
    }

    this.stageThan = [
      { allProjectNum: 0, offProjectNum: 0 },
      { allProjectNum: 0, offProjectNum: 0 },
      { allProjectNum: 0, offProjectNum: 0 }
    ];//阶段总项目数 和 完成项目数 
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

  applyChart(e, than, i) {
    new Chart().init({
      ele: e,
      targetNum: parseInt(than.offProjectNum / than.allProjectNum * 100, 10), //初始化的目标
      sDeg: 40,//结束角度   从右往左
      eDeg: 140,//开始角度 从右往左
      r: 20, //半径

      txt: '完成进度',
      txtFontSize: '17px',
      txtFontFamily: 'Heiti SC',//

      unitValueBig: 26 * 2,//大格子单位
      unitValueSmart: 13 * 2,//小格子单位
      _panceBorderWidth: 6,//圆盘border宽
      paneBorderColor: '#0099ff',//仪表盘 和 刻度(小) 和 圆心 颜色
      pancePointerColor: '#0099ff',//指针颜色
      _pancePointerWidth: 2,//指针粗细  
      canvasBg: 'white',//canvas背景

      dotR: 2,//中间圆点的半径

      _panceKdColor: '#0099ff',//刻度数字颜色  （大）
      _panceKdFontFamily: 'Heiti SC',//刻度数字字体样式  （大）
      _panceKdFontSize: '10px',//刻度数字字体大小  （大）

      _panelMetreLineLenS: 5,//刻度长度（小）
      _panelMetreLineColorS: '#ccc',//刻度颜色（小）
      _panelMetreLineWidthS: 2.5,//刻度格子线的width(粗)（小）

      _panelMetreLineLenB: 7,//刻度长度（大）
      _panelMetreLineColorB: '#0099ff',//刻度颜色（大）
      _panelMetreLineWidthB: 3.5,//刻度格子线的width(粗)（大）

      panelNumcolor: '#0099ff',//仪盘表上的数字颜色  不是刻度
      panelNumBorderColor: 'transparent',//仪盘表上的数字框颜色  
      panelNumFontFamily: 'Heiti SC',//仪盘表上的数字字体样式
      panelNumFontSize: '17px',//仪盘表上的数字字体大小

      valueType: '%',//数值类型  % || angle
      // minNum:0,//最小值   数值类型为 % 可设置
      // maxNum:100,//最大值 数值类型为 % 可设置

      upDateFn: function (me) {//更新方法 参数是canvas所有参数
        // console.log('更新完后的数据：',me)
      }
    })
  }

  onRefresh = () => {
    const { myFetch, companyId, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
    const { pathName } = routeData[curKey]
    const body = { companyId }
    myFetch('getInvestCompanyInfo', body).then(({ success, data, message, code }) => {
      if (success) {
        this.initData = [{ ...data }];
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
  render() {
    const { refreshing } = this.state
    const row = (rowData, sectionID, rowID) => {
      const { projectName, projectState, contractAmount, holdingThan, winningTime } = rowData;
      var fakeData = [
        {
          stage: '第一阶段',
          data: rowData.firInvestCompanyList,
        },
        {
          stage: '第二阶段',
          data: rowData.secInvestCompanyList
        },
        {
          stage: '第三阶段',
          data: rowData.thiInvestCompanyList
        }
      ]


      if (rowData.errMsg) {
        return <div style={{ textAlign: "center", lineHeight: "50px", color: "#999" }}>{rowData.errMsg}</div>
      } else {
        return (
          <WingBlank size="sm">
            <WhiteSpace size="sm" />

            <div className="header">
              <div className="con">
                <div className="projectName"><span >{projectName}</span></div>
                <Flex>
                  <Flex.Item>
                    <span className="winDate">中标日期<br /> <span className="value">{winningTime ? moment(winningTime).format('l') : '未填报'}</span> </span>
                  </Flex.Item>
                  <Flex.Item>
                    状态<br />
                    <span className="value"> {projectState === "2" ? "已结束" : (projectState === "1" ? "执行中" : "未执行")}  </span>
                  </Flex.Item>
                </Flex>
                <Flex>
                  <Flex.Item>
                    投资金额<br />
                    <span className="value"> {contractAmount}<span className="unit">万元</span> </span>
                  </Flex.Item>
                  <Flex.Item>
                    控股比<br />
                    <span className="value"> {holdingThan}<span className="unit">%</span> </span>

                  </Flex.Item>
                </Flex>
              </div>


            </div>

            <div className="allStage">
              {
                fakeData.map((v, i) => {
                  let thisState = this.stageThan;
                  thisState[i].allProjectNum = v.data.length;//设置总项目数量
                  thisState[i].offProjectNum = 0;//重置已完成项目数量
                  let isStartText = "";
                  if (parseInt(rowData.stageFlag, 10) === i) {
                    isStartText = "（起始）"
                  }
                  return (
                    <div key={i} className="stage">
                      <div className="stage-top"> <span>{`${v.stage}${isStartText}`}</span> </div>
                      <div className="stage-con">
                        <div className="stage-con-left">
                          {
                            v.data.map((v, ii) => {//每个阶段的每条日期
                              let dateText = "";
                              let color = "#666"
                              if (v.finishFlag === "1") {//按时完成
                                dateText = v.finishDate ? moment(v.finishDate).format('l') : "-"
                                thisState[i].offProjectNum = thisState[i].offProjectNum + 1;//设置已完成项目数量
                                color = "#0099ff"
                              } else if (v.finishFlag === "2") {//超时完成
                                dateText = moment(v.finishDate).format('l') + "(过)"
                                color = "#ff0401"
                              } else {//未完成
                                dateText = "未完成";
                              }
                              return (
                                <div key={ii}>
                                  <span>{v.projectChineseName}</span>
                                  &nbsp;
                                  <span style={{ color }}>{dateText}</span>
                                </div>
                              )
                            })
                          }
                        </div>
                        <div className="stage-con-right">
                          {
                            thisState[i].allProjectNum || thisState[i].offProjectNum
                              ?
                              <div ref={(e) => {
                                this.applyChart(e, thisState[i], i);
                              }} className={"canvas-container" + i}></div>
                              :
                              ''
                          }
                        </div>
                      </div>
                    </div>
                  )
                })
              }
            </div>

            <WhiteSpace size="sm" />
          </WingBlank>
        )
      }
    }
    return (
      <div className="page basicInfor">
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
      </div>);
  }
}
export default DailyDetail;