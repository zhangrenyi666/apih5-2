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
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.mondayTrainContent?item.mondayTrainContent:'????????????'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.mondayMeetContent?item.mondayMeetContent:'????????????'}</div>
                      </div>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.tuesdayTrainContent?item.tuesdayTrainContent:'????????????'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.tuesdayMeetContent?item.tuesdayMeetContent:'????????????'}</div>
                      </div>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.wednesdayTrainContent?item.wednesdayTrainContent:'????????????'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.wednesdayMeetContent?item.wednesdayMeetContent:'????????????'}</div>
                      </div>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.thursdayTrainContent?item.thursdayTrainContent:'????????????'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.thursdayMeetContent?item.thursdayMeetContent:'????????????'}</div>
                      </div>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.fridayTrainContent?item.fridayTrainContent:'????????????'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.fridayMeetContent?item.fridayMeetContent:'????????????'}</div>
                      </div>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.saturdayTrainContent?item.saturdayTrainContent:'????????????'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.saturdayMeetContent?item.saturdayMeetContent:'????????????'}</div>
                      </div>
                      <div className={s.day}>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.sundayTrainContent?item.sundayTrainContent:'????????????'}</div>
                        <div className={s.day_span}><span style={{fontWeight:'bold'}}>???????????????</span>{item.sundayMeetContent?item.sundayMeetContent:'????????????'}</div>
                      </div>
                    </div>
                  )
                })
              }
            </div> : <div style={{textAlign:'center',height:'90vh',lineHeight:'90vh',fontWeight:'bold'}}>??????????????????...</div>}
            <div className={s.bottom}>
              <Button className={s.bottom_b} onClick={() => {
                this.setState({
                  button: true,
                  index: 0,
                  data:[]
                }, () => {
                  this.loadTherr();
                })
              }} type="primary" inline size="small">??????</Button>
              <Button onClick={() => {
                this.setState({
                  button: false,
                  index: 0,
                  data:[]
                }, () => {
                  this.loadTherrt();
                })
              }} type="primary" inline size="small">??????</Button>
            </div>
          </div> : <div className={s.message}>??????~????????????????????????...</div>
        }
      </div>
    )
  }
}

export default blank(Index);