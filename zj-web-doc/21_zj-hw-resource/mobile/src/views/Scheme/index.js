import React, { Component } from "react";
import { Divider } from "antd";
import { push } from "react-router-redux";
import MyList from "../modules/MListByZj";
import s from "./style.less";
import { NavBar, Icon } from "antd-mobile";
import { goBack } from 'connected-react-router';
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
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
                        {"方案列表"}
                    </NavBar>
                </div>
                <div
                    style={{
                        height: window.innerHeight - 45
                    }}
                >
                    <MyList
                        myFetch={this.props.myFetch} //ajax方法必须返回一个promise
                        searchKey="key" //搜索时的key
                        fetchConfig={{
                            apiName: 'getZjHwZyResourceSchemeList', //后台api
                        }}
                        Item={props => {
                            //列表模板 props里有所有数据
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            if(item.schemeClass === '0'){
                                item.schemeClass = "优秀QC成果库";
                            }else if(item.schemeClass === '1'){
                                item.schemeClass = "工序工艺标准化库";
                            }else if(item.schemeClass === '2'){
                                item.schemeClass = "培训库";
                            }else if(item.schemeClass === '3'){
                                item.schemeClass = "论文库";
                            }else if(item.schemeClass === '4'){
                                item.schemeClass = "方案库";
                            }else if(item.schemeClass === '5'){
                                item.schemeClass = "专家库";
                            }else{
                                item.organizationClass = "未知";
                            }
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
                                                `${mainModule}SchemeDetail/${
                                                item.schemeId
                                                }`
                                            )
                                        );
                                    }}
                                >
                                    <div className={s.top}>
                                       <div className={s.topl}>{item.schemeDepName}</div>
                                        <div className={s.topr}>{item.schemeClass}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px",backgroundColor:'#1890ff' }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        名称:{item.schemeName}
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        关键词:{item.pointExplain}
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
