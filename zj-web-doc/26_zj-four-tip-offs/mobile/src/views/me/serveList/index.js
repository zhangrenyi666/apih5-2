import React, { Component } from "react";
import MyList from "../../modules/MListA";
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
                        {"合作、共享、服务、交流列表"}
                    </NavBar>
                </div>
                <div
                    style={{
                        height: window.innerHeight - 45
                    }}
                >
                    <MyList
                        myFetch={this.props.myFetch} //ajax方法必须返回一个promise
                        searchKey="feedbackTitle" //搜索时的key
                        fetchConfig={{
                            apiName: 'getZjFeedbackProblemList', //后台api
                        }}
                        Item={props => {
                            //列表模板 props里有所有数据
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                    style={{borderLeft:index % 2 === 0 ? '2px solid #ef0404' : '2px solid #efbc04'}}
                                    onClick={() => {
                                        dispatch(push(`${mainModule}serveListDetail/${item.feedbackId}`));
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>【合作、共享、服务、交流】</div>
                                        <div className={s.topr} style={{color:'#108ee9'}}>{item.typeName}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>{item.feedbackDescribe}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>{item.feedbackUserName}</div>
                                        <div className={s.topr}>{moment(item.feedbackTime).format('YYYY-MM-DD HH:mm:ss')}</div>
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
