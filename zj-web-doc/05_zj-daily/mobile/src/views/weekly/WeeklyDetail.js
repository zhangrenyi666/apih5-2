import React, { Component } from 'react';
import { Toast } from 'antd-mobile';
import BasicInfor from './basicInfor'
import TypeList from './typeList'
import './WeeklyDetail.css';
class WeeklyDetail extends Component {
  constructor(props) {
    super(props);
    const { match: { params: { companyId, startDate, endDate } } } = this.props
    this.companyId = companyId
    this.startDate = startDate
    this.endDate = endDate
    this.state = {
      index: 0,
      landNumber: 0,
      chartNumber: 0,
      fundsNumber: 0,
      assessNumber: 0,
      progressNumber: 0,
    }
  }
  onChange = (v, index) => {
    this.setState({ index })
  }
  _onChange = (index) => {
    this.setState({ index })
  }
  componentDidMount() {
    const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
    const { pathName } = routeData[curKey]
    const { companyId, startDate, endDate } = this;
    const body = { companyId, startDate, endDate }
    Toast.loading('Loading...', 0);
    myFetch('getDailyFiveNumbers', body).then(({ success, data, message, code }) => {
      Toast.hide()
      if (success) {
        const { landNumber, chartNumber, fundsNumber, assessNumber, progressNumber } = data;
        this.setState({ landNumber, chartNumber, fundsNumber, assessNumber, progressNumber })
      } else if (code === "3003" || code === "3004") {
        dispatch(logout(pathName))
      } else {
        Toast.offline(message, 2)
      }
    })
  }
  render() {
    const { companyId, startDate, endDate } = this;
    const { index, landNumber, chartNumber, fundsNumber, assessNumber, progressNumber } = this.state
    return (
      <div className="page WeeklyDetail">
        <div className="newNav">
          <div className="newNavLine">
            <NavItem index={0} curIndex={index} count={0} onClick={this._onChange} name={"基本信息"} />
            <NavItem index={1} curIndex={index} count={landNumber} onClick={this._onChange} name={"土地征迁"} />
            <NavItem index={2} curIndex={index} count={chartNumber} onClick={this._onChange} name={"设计图表"} />
          </div>
          <div className="newNavLine">
            <NavItem index={3} curIndex={index} count={fundsNumber} onClick={this._onChange} name={"项目资金"} />
            <NavItem index={4} curIndex={index} count={assessNumber} onClick={this._onChange} name={"评估许可证"} />
            <NavItem index={5} curIndex={index} count={progressNumber} onClick={this._onChange} name={"工程形象"} />
          </div>
        </div>
        <div className={"flex"} style={{ position: "relative" }}>
          {index === 0 ?
            <BasicInfor {...this.props} companyId={companyId} startDate={startDate} endDate={endDate} />
            :
            <TypeList  {...this.props} type={index} companyId={companyId} startDate={startDate} endDate={endDate} />
          }
        </div>
      </div>);
  }
}
class NavItem extends Component {
  render() {
    return (
      <div onClick={() => { this.props.onClick(this.props.index) }} className={this.props.curIndex === this.props.index ? "newNavItem cur" : "newNavItem"}>
        {(() => {
          if (this.props.count > 0) {
            return <span>{this.props.name}<i className="noRead"></i></span>
          } else {
            return <span>{this.props.name}</span>
          }
        })()
        }
      </div>
    )
  }
}

export default WeeklyDetail;