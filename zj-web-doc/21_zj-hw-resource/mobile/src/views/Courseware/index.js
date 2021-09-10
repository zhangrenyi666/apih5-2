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
                        {"知识库列表"}
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
                            apiName: 'getZjHwZyResourceCoursewareList', //后台api
                        }}
                        Item={props => {
                            //列表模板 props里有所有数据
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            if(item.coursewareClass === '0'){
                                item.coursewareClass = "培训课件";
                            }else if(item.coursewareClass === '1'){
                                item.coursewareClass = "安全教育课件";
                            }else if(item.coursewareClass === '2'){
                                item.coursewareClass = "交底课件";
                            }else{
                                item.coursewareClass = "未知";
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
                                                `${mainModule}CoursewareDetail/${
                                                item.coursewareId
                                                }`
                                            )
                                        );
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>{item.coursewareDepName}</div>
                                        <div className={s.topr}>{item.coursewareClass}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px",backgroundColor:'#1890ff' }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        名称:{item.coursewareName}
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        关键词:{item.coursewareKeyword}
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
