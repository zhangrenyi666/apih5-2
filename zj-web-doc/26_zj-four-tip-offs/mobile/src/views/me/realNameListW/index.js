import React, { Component } from "react";
import MyList from "../../modules/MList";
import { Divider } from "antd";
import s from "./style.less";
import { NavBar, Icon } from "antd-mobile";
import moment from 'moment';
import { push } from 'react-router-redux';
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
        };
    }
    componentDidMount(){
        const { wx } = window;
        wx.ready(() => {
            wx.hideOptionMenu();
        })
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}Me`));
                        }}
                    >
                        {"实名举报列表"}
                    </NavBar>
                </div>
                <div
                    style={{
                        height: window.innerHeight - 45
                    }}
                >
                    <MyList
                        myFetch={this.props.myFetch} //ajax方法必须返回一个promise
                        searchKey="title" //搜索时的key
                        fetchConfig={{
                            apiName: 'getZjViolationReportInfoList', //后台api
                            otherParams:{reportType: '1'}
                        }}
                        Item={props => {
                            //列表模板 props里有所有数据
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            if(item.processState === '0'){
                                item.processState = '未处理'
                            }else{
                                item.processState = '已处理'
                            }
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                    style={{borderLeft:index % 2 === 0 ? '2px solid #ef0404' : '2px solid #efbc04'}}
                                    onClick={() => {
                                        dispatch(push(`${mainModule}realNameListWDetail/${item.reportId}`));
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>【{item.title}】</div>
                                        <div className={s.topr} style={{color:'#108ee9'}}>{item.processState}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>{moment(item.reportTime).format('YYYY-MM-DD HH:mm:ss')}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div style={{paddingLeft:'3%'}}>
                                        {item.problemDescribe}
                                    </div>
                                </div>
                            );
                        }}
                    />
                </div>
            </div>
        );
    }
}
export default Index;
