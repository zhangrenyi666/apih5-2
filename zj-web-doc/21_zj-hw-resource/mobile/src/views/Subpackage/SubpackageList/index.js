import React, { Component } from "react";
import { Divider } from "antd";
import { push } from "react-router-redux";
import MyList from "../../modules/MListByZj";
import s from "./style.less";
import { NavBar, Icon } from "antd-mobile";
import { goBack } from 'connected-react-router';
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
            subpackageMainId:this.props.match.params.subpackageMainId || ''
        };
    }
    render() {
        const { dispatch, myPublic: { androidApi } } = this.props;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            if (androidApi && this.state.falg == 0) {
                                androidApi.closeActivity()
                            } else {
                                dispatch(goBack())
                            }
                        }}
                    >
                        {"章节详情列表"}
                    </NavBar>
                </div>
                <div
                    style={{
                        height: window.innerHeight - 45
                    }}
                >
                    <MyList
                        myFetch={this.props.myFetch} //ajax方法必须返回一个promise
                        searchKey="standardProcessName" //搜索时的key
                        fetchConfig={{
                            apiName: 'getZjHwZyResourcePriceSubpackageDetailList', //后台api
                            otherParams:{subpackageMainId : this.state.subpackageMainId}
                        }}
                        Item={props => {
                            //列表模板 props里有所有数据
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    style={{
                                        borderLeft: `3px solid ${
                                            index % 2 === 0 ? "#ff4000" : "#ff9900"
                                            }`
                                    }}
                                    key={index}
                                    onClick={() => {
                                        const {
                                            mainModule
                                        } = this.props.myPublic.appInfo;
                                        this.props.dispatch(
                                            push(
                                                `${mainModule}SubpackageDetail/${item.subpackageDetailId}`
                                            )
                                        );
                                    }}
                                >
                                    <div className={s.top}>
                                          <div className={s.topl}>{item.code}</div>                                    
                                    </div>
                                    <Divider style={{ margin: "8px 0px",backgroundColor:'#1890ff' }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        标准工序名称:{item.standardProcessName}
                                    </div>                                  
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        单位:{item.unit}
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
