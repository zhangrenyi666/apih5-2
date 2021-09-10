import React, { Component } from 'react';
import { blank } from '../modules/layouts';
import { Toast, Button } from 'antd-mobile';
import TopNav from '../topNav/topNav';
import s from './style.less';
class Index extends Component {
  constructor() {
    super();
    this.state = {
      data: [],
      inex: 0,
      oaMemberId: "",
      datat: [],
      flag: true,
      button: true
    }
  }
  componentDidMount() {
    this.loadTherr();
  }
  loadTherr() {
    const { myFetch } = this.props
    myFetch("getZjPxjhWeekPlanWeekList").then(({ success, message, data }) => {
      if (success) {
        if (data && data.length) {
          this.setState({
            data: data,
            oaMemberId: data[0].oaMemberId
          }, () => {
            const { myFetch } = this.props
            let body = {
              oaMemberId: this.state.oaMemberId
            }
            myFetch("getZjPxjhWeekPlanWeekList", body).then(({ success, message, data }) => {
              if (success) {
                this.setState({
                  datat: data,
                })
              } else {
                Toast.fail(message)
              }
            })
          })
        }
      } else {
        this.setState({
          flag: false
        })
      }
    })
  }
  loadTherrt() {
    const { myFetch } = this.props
    myFetch("getZjPxjhWeekPlanNextWeekList").then(({ success, message, data }) => {
      if (success) {
        if(data && data.length){
          this.setState({
            data: data,
            oaMemberId: data[0].oaMemberId
          }, () => {
            const { myFetch } = this.props
            let body = {
              oaMemberId: this.state.oaMemberId
            }
            myFetch("getZjPxjhWeekPlanNextWeekList", body).then(({ success, message, data }) => {
              if (success) {
                this.setState({
                  datat: data,
                })
              } else {
                Toast.fail(message)
              }
            })
          })
        }
      } else {
        this.setState({
          flag: false
        })
      }
    })
  }
  tagClick = (index) => {
    this.setState({
      oaMemberId: this.state.data[index].oaMemberId,
      index
    }, () => {
      if (this.state.button) {
        const { myFetch } = this.props
        let body = {
          oaMemberId: this.state.oaMemberId
        }
        myFetch("getZjPxjhWeekPlanWeekList", body).then(({ success, message, data }) => {
          if (success) {
            this.setState({
              datat: data,
            })
          } else {
            Toast.fail(message)
          }
        })
      } else {
        const { myFetch } = this.props
        let body = {
          oaMemberId: this.state.oaMemberId
        }
        myFetch("getZjPxjhWeekPlanNextWeekList", body).then(({ success, message, data }) => {
          if (success) {
            this.setState({
              datat: data,
            })
          } else {
            Toast.fail(message)
          }
        })
      }
    });
  }
  render() {
    return (
      <div>
        {
          this.state.flag ? <div className={s.root}>
            {this.state.data && this.state.data.length ? <div className={s.top} >
              <TopNav index={this.state.index || 0} arr={this.state.data} valueName={"oaUserName"} onChange={(index) => {
                this.tagClick(index)
              }} />
            </div> : null}
            {this.state.data && this.state.data.length ? <div className={s.centert}>
              {
                this.state.datat.map((item, index) => {
                  return (
                    <div key={index}>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周一培训：</span>{item.mondayTrainContent?item.mondayTrainContent:'暂无安排'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周一会议：</span>{item.mondayMeetContent?item.mondayMeetContent:'暂无安排'}</div>
                      </div>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周二培训：</span>{item.tuesdayTrainContent?item.tuesdayTrainContent:'暂无安排'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周二会议：</span>{item.tuesdayMeetContent?item.tuesdayMeetContent:'暂无安排'}</div>
                      </div>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周三培训：</span>{item.wednesdayTrainContent?item.wednesdayTrainContent:'暂无安排'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周三会议：</span>{item.wednesdayMeetContent?item.wednesdayMeetContent:'暂无安排'}</div>
                      </div>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周四培训：</span>{item.thursdayTrainContent?item.thursdayTrainContent:'暂无安排'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周四会议：</span>{item.thursdayMeetContent?item.thursdayMeetContent:'暂无安排'}</div>
                      </div>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周五培训：</span>{item.fridayTrainContent?item.fridayTrainContent:'暂无安排'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周五会议：</span>{item.fridayMeetContent?item.fridayMeetContent:'暂无安排'}</div>
                      </div>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周六培训：</span>{item.saturdayTrainContent?item.saturdayTrainContent:'暂无安排'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周六会议：</span>{item.saturdayMeetContent?item.saturdayMeetContent:'暂无安排'}</div>
                      </div>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周日培训：</span>{item.sundayTrainContent?item.sundayTrainContent:'暂无安排'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>周日会议：</span>{item.sundayMeetContent?item.sundayMeetContent:'暂无安排'}</div>
                      </div>
                    </div>
                  )
                })
              }
            </div> : <div style={{textAlign:'center',height:'90vh',lineHeight:'90vh',fontWeight:'bold'}}>本周暂无数据...</div>}
            <div className={s.bottom}>
              <Button className={s.bottom_b} onClick={() => {
                this.setState({
                  button: true,
                  index: 0,
                  data:[]
                }, () => {
                  this.loadTherr();
                })
              }} type="primary" inline size="small">本周</Button>
              <Button onClick={() => {
                this.setState({
                  button: false,
                  index: 0,
                  data:[]
                }, () => {
                  this.loadTherrt();
                })
              }} type="primary" inline size="small">下周</Button>
            </div>
          </div> : <div className={s.message}>抱歉~无权限访问该页面...</div>
        }
      </div>
    )
  }
}

export default blank(Index);