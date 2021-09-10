import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import s from "./style.less";
import { push } from "react-router-redux";
const config = {
    antd: {
        rowKey: 'compensationMaintainId',
        size: 'small'
    },
    isShowRowSelect: true,
    paginationConfig: {
        position: 'bottom'
    },
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tableData: []
        }
    }
    componentDidMount() {
        // 获取表格data
        this.getTableData();
    }

    getTableData() {
        const { clickNodeId, clickNodeName, tabId, tabName, zhujiandetail } = this.props;//父子组件
        let dataObj = {
            // zjXmSalaryEmployApprovalId: zhujiandetail,//主键
            compensationMaintainPlanId: tabId,//tab切换ID
            wageOfProjectId: clickNodeId//左侧树点击ID
        };
        this.setState({
            tableData: []
        })
        this.props.myFetch('getZjXmSalaryCompensationMaintainList', dataObj).then(({ success, message, data }) => {
            if (success) {
                if (data) {
                    this.setState({
                        tableData: data.map(item => {
                            var temp = [];
                            let list = item.itemList[0];
                            for (var key in list) {
                                temp.push({
                                    name: key,
                                    value: list[key]
                                });
                            }
                            for (let k = 0; k < temp.length; k++) {
                                item[temp[k].name] = temp[k].value;
                            }
                            item.flag = false;
                            return item
                        }),
                    });
                }
            } else {
                Msg.error(message);
            }
        });
    }
    render() {
        const { tableData } = this.state;
        const { titleData, tabId, tabName, clickNodeId } = this.props;
        return (
            <div>
                {(titleData.length > 0 && tableData.length > 0) ? <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.tableOne = me;
                    }}
                    {...config}
                    data={tableData}
                    method={{
                        saveOtherParamsFunc: (rowData, obj) => {
                            this.tableOne.clearSelectedRows();
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'goback',
                            type: 'dashed',
                            label: '返回',
                            isValidate: false,
                            onClick: (obj) => {
                                const { mainModule } = this.props.myPublic.appInfo;
                                this.props.dispatch(
                                    push(`${mainModule}SalaryDataMaintenance`)
                                )
                            }
                        },
                        {
                            name: 'diySave',
                            type: 'primary',
                            label: '保存',
                            field: 'addsubmit',
                            onClick: (obj) => {
                                let aa = obj.btnCallbackFn.getVTableData();
                                let arry = [];
                                for (let m = 0; m < aa.length; m++) {
                                    for (let x in aa[m]) {
                                        var keyName = x.substring(0, 11);
                                        if (keyName === 'pay-amount-') {
                                            arry.push({
                                                key: x
                                            })
                                        }
                                    }
                                    for (let j = 0; j < arry.length; j++) {
                                        let runName = arry[j].key;
                                        aa[m].itemList[0][runName] = aa[m][runName];
                                    }
                                }
                                this.props.myFetch('batchUpdateZjXmSalaryCompensationMaintain', aa).then(({ data, success, message }) => {
                                    if (success) {
                                        Msg.success(data)
                                        this.getTableData();
                                    } else {
                                        Msg.error(message)
                                    }
                                })
                            }
                        },
                        {
                            name: 'diySavea',
                            type: 'primary',
                            label: '计算',
                            field: 'try',
                            disabled: 'bind:_actionBtnNoSelected',
                            onClick: (obj) => {
                                let bb = obj.btnCallbackFn.getSelectedRows().map(item => {
                                    item.flag = true;
                                    return item
                                });
                                let aa = obj.btnCallbackFn.getVTableData().map(item => {
                                    for (let x = 0; x < bb.length; x++) {
                                        if (bb[x].compensationMaintainId === item.compensationMaintainId) {
                                            item = bb[x];
                                        }
                                    }
                                    return item
                                });
                                let arry = [];
                                for (let m = 0; m < aa.length; m++) {
                                    for (let x in aa[m]) {
                                        var keyName = x.substring(0, 11);
                                        if (keyName === 'pay-amount-') {
                                            arry.push({
                                                key: x
                                            })
                                        }
                                    }
                                    for (let j = 0; j < arry.length; j++) {
                                        let runName = arry[j].key;
                                        aa[m].itemList[0][runName] = aa[m][runName];
                                    }
                                }
                                this.props.myFetch('getZjXmSalaryCompensationMaintainFormula', aa).then(({ data, success, message }) => {
                                    if (success) {
                                        this.setState({
                                            tableData: []
                                        }, () => {
                                            this.setState({
                                                tableData: data.map(item => {
                                                    var temp = [];
                                                    let list = item.itemList[0];
                                                    for (var key in list) {
                                                        temp.push({
                                                            name: key,
                                                            value: list[key]
                                                        });
                                                    }
                                                    for (let k = 0; k < temp.length; k++) {
                                                        item[temp[k].name] = temp[k].value;
                                                    }
                                                    return item
                                                })
                                            })
                                        })
                                    } else {
                                        Msg.error(message)
                                    }
                                })
                            }
                        }
                    ]}
                    formConfig={titleData}
                /> : <div className={s.alert}>无数据</div>}
            </div>
        );
    }
}

export default index;