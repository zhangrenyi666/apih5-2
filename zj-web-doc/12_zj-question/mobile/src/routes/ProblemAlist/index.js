import React, { Component } from "react";
import MyList from "../modules/MList";
import { Divider } from "antd";
import { myFetch } from '../../tools';
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
        const { dispatch } = this.props;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(goBack());
                        }}
                    >
                        {"定制清单列表"}
                    </NavBar>
                </div>
                <div
                    style={{
                        height: window.innerHeight - 45
                    }}
                >
                    <MyList
                        myFetch={myFetch} //ajax方法必须返回一个promise
                        searchKey="departmentName" //搜索时的key
                        fetchConfig={{
                            apiName: 'getZjXmHasTitleQuestionList', //后台api
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
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>部门：{item.departmentName}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px",backgroundColor:'#1890ff' }} />
                                    <div style={{paddingLeft:'3%'}}>
                                        类别：{item.questionClassName}
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div style={{paddingLeft:'3%'}}>
                                        检查项：{item.questionCheckItemName}
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
