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
                        {"供应商列表"}
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
                            apiName: 'getZjHwZyResourceSupplierList', //后台api
                        }}
                        Item={props => {
                            //列表模板 props里有所有数据
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            if(item.supperClass === '0'){
                                item.supperClass = "分包供应商";
                            }else if(item.supperClass === '1'){
                                item.supperClass = "物资供应商";
                            }else if(item.supperClass === '2'){
                                item.supperClass = "机械租赁供应商";
                            }else if(item.supperClass === '3'){
                                item.supperClass = "广告宣传供应商";
                            }else if(item.supperClass === '4'){
                                item.supperClass = "信息供应商";
                            }else if(item.supperClass === '5'){
                                item.supperClass = "安全供应商";
                            }else if(item.supperClass === '6'){
                                item.supperClass = "其他供应商";
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
                                                `${mainModule}SupplierDetail/${
                                                item.supplierId
                                                }`
                                            )
                                        );
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>{item.supplierDepName}</div>
                                        <div className={s.topr}>{item.supperClass}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px",backgroundColor:'#1890ff' }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>名称:{item.unitName}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>经营范围:{item.businessScope}</div>
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
