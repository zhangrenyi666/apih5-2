import React, { Component } from "react";
import MyList from "../modules/MList";
import { Divider } from "antd";
import { push } from 'react-router-redux';
import s from "./style.less";

class XmdahzPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
            lisiApiname:props.sdata.lisiApiname,
            // updateApiname:props.sdata.updateApiname
        };
    }
    componentDidMount(){
        
    }
    render() {
        const { dispatch,myPublic: { appInfo: { mainModule } } } = this.props;
        const { domain } = this.props.myPublic;
        const {itemIdVal,loading, lisiApiname} = this.state;
        return (
                <div style={{
                    height: document.documentElement.clientHeight - 45
                }}>
                    <MyList
                            myFetch={this.props.myFetch} 
                            searchKey="timeNodeName"
                            fetchConfig={{
                                apiName:lisiApiname, 
                                otherParams: {
                                    // enginnerId:itemIdVal
                                }
                            }}
                            Item={props => {
                                const { rowData, rowID } = props;
                                const item = rowData;
                                const index = rowID;
                                let informationalizationFlag = '';
                                if(item.informationalizationFlag === '0'){
                                    informationalizationFlag = '否';
                                }else if(item.informationalizationFlag === '1'){
                                    informationalizationFlag = '是';
                                }else {
                                    informationalizationFlag = '';
                                }
                                return (
                                    <div
                                        className={s.center}
                                        key={index}
                                        onClick={() => {
                                            dispatch(this.props.actions.saveNodes(item)); 
                                            dispatch(push(`${mainModule}serviceInformationDetail/${this.props.sdata.updateApiname}/${this.props.keyData}`));
                                        }}
                                    >
                                        <div className={s.top}>
                                            <div className={s.topl}>时间节点：{item.timeNodeName ? item.timeNodeName: '无'}</div>
                                            <div className={s.topr}>流程是否已信息化：{informationalizationFlag}</div>
                                        </div>
                                        <Divider style={{ margin: "8px 0px" }} />
                                        <div className={s.top}>
                                            <div className={s.topl}>事项名称：{item.itemNameName ? item.itemNameName : '无'}</div>
                                            <div className={s.topr}>使用范围：{item.scopeOfUseName ? item.scopeOfUseName : '无'}</div>
                                        </div>
                                        <Divider style={{ margin: "8px 0px" }} />
                                        <div className={s.top}>
                                            <div className={s.topl}>流程：{item.process ? item.process: '无'}</div>
                                            <div className={s.topr}>办理时限：{item.handleTimeLimit ? item.handleTimeLimit : '无'}</div>
                                        </div>
                                    </div>
                                );
                            }}
                        />
                </div>
        );
    }
}
export default XmdahzPage;
