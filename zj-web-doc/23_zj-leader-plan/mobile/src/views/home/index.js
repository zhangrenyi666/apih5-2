import React, { Component } from 'react';
import { blank } from '../modules/layouts'
import { push } from 'connected-react-router'
import { Toast, Button } from 'antd-mobile';
import TopNav from '../topNav/topNav';
import s from './style.less';
class Index extends Component{ 
    constructor(){
        super();
        this.state = {
            data:[],
            inex:0,
            planId:"",
            datat:[],
            flag:true,
            button:true
        }
    }
    componentDidMount(){
        this.loadTherr();
    }
    loadTherr(){
        const { myFetch } = this.props
        myFetch("getZjLdzbLeaderPlanWeekList").then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    data:data,
                    planId:data[0].planId
                },() => {
                    const { myFetch } = this.props
                    let body = {
                        planId:this.state.planId
                    }
                    myFetch("getZjLdzbLeaderPlanWeekList",body).then(({ success, message, data }) => {
                        if (success) {
                            this.setState({
                                datat:data,
                            })
                        }else{
                            Toast.fail(message)
                        }
                    })
                })
            }else{
                Toast.fail(message)
                this.setState({
                    flag:false
                })
            }
        })
    }
    loadTherrt(){
        const { myFetch } = this.props
        myFetch("getZjLdzbLeaderPlanNextWeekList").then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    data:data,
                    planId:data[0].planId
                },() => {
                    const { myFetch } = this.props
                    let body = {
                        planId:this.state.planId
                    }
                    myFetch("getZjLdzbLeaderPlanNextWeekList",body).then(({ success, message, data }) => {
                        if (success) {
                            this.setState({
                                datat:data,
                            })
                        }else{
                            Toast.fail(message)
                        }
                    })
                })
            }else{
                Toast.fail(message)
                this.setState({
                    flag:false
                })
            }
        })
    }
    tagClick = (index) => {
        this.setState({
            planId:this.state.data[index].planId,
            index
        },() => {
            if(this.state.button){
                const { myFetch } = this.props
                let body = {
                    planId:this.state.planId
                }
                myFetch("getZjLdzbLeaderPlanWeekList",body).then(({ success, message, data }) => {
                    if (success) {
                        this.setState({
                            datat:data,
                        })
                    }else{
                        Toast.fail(message)
                    }
                })
            }else{
                const { myFetch } = this.props
                let body = {
                    planId:this.state.planId
                }
                myFetch("getZjLdzbLeaderPlanNextWeekList",body).then(({ success, message, data }) => {
                    if (success) {
                        this.setState({
                            datat:data,
                        })
                    }else{
                        Toast.fail(message)
                    }
                })
            }
        });
    }   
    render(){
        return (
            <div>
                {
                    this.state.flag ? <div>
                    <div className={s.top} >
                        <TopNav index={this.state.index || 0} arr={this.state.data} valueName={"oaMemberName"} onChange={(index) => {
                                 this.tagClick(index)
                        }} />
                     </div>
                     <div className={s.centert}>
                        {
                            this.state.datat.map((item,index) => {
                                if(item.mondayContent == ""){
                                    item.mondayContent = "无日程安排";
                                }
                                if(item.tuesdayContent == ""){
                                    item.tuesdayContent = "无日程安排";
                                }
                                if(item.wednesdayContent == ""){
                                    item.wednesdayContent = "无日程安排";
                                }
                                if(item.thursdayContent == ""){
                                    item.thursdayContent = "无日程安排";
                                }
                                if(item.fridayContent == ""){
                                    item.fridayContent = "无日程安排";
                                }
                                if(item.saturdayContent == ""){
                                    item.saturdayContent = "无日程安排";
                                }
                                if(item.sundayContent == ""){
                                    item.sundayContent = "无日程安排";
                                }
                                return (
                                    <div key={index}>
                                        <div className={s.day}>
                                            <div className={s.day_span}>周一&nbsp;:</div><div className={s.day_spant}>{item.mondayContent}</div>
                                        </div>
                                        <div className={s.day}>
                                            <div className={s.day_span}>周二&nbsp;:</div><div className={s.day_spant}>{item.tuesdayContent}</div>
                                        </div>
                                        <div className={s.day}>
                                            <div className={s.day_span}>周三&nbsp;:</div><div className={s.day_spant}>{item.wednesdayContent}</div>
                                        </div>
                                        <div className={s.day}>
                                            <div className={s.day_span}>周四&nbsp;:</div><div className={s.day_spant}>{item.thursdayContent}</div>
                                        </div>
                                        <div className={s.day}>
                                            <div className={s.day_span}>周五&nbsp;:</div><div className={s.day_spant}>{item.fridayContent}</div>
                                        </div>
                                        <div className={s.day}>
                                            <div className={s.day_span}>周六&nbsp;:</div><div className={s.day_spant}>{item.saturdayContent}</div>
                                        </div>
                                        <div className={s.day}>
                                            <div className={s.day_span}>周日&nbsp;:</div><div className={s.day_spant}>{item.sundayContent}</div>
                                        </div>
                                    </div>
                                )
                            })
                        }
                     </div>
                     <div className={s.bottom}>
                        <Button className={s.bottom_b} onClick={() => {
                            this.setState({
                                button:true,
                                index:0
                            },() => {
                               this.loadTherr(); 
                            })                          
                        }} type="primary" inline size="small">本周</Button>
                        <Button onClick={() => {
                            this.setState({
                                button:false,
                                index:0
                            },() => {
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