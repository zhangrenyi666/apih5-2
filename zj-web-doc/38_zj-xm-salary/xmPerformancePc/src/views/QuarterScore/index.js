import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { message as Msg, Modal, Tabs } from 'antd';
const { confirm } = Modal;
const { TabPane } = Tabs;
const config = {
    antd: {
        rowKey: 'detailsId',
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    incToForm: true,
    paginationConfig: false,
    pageSize: 9999,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            departmentId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId || '',
            formConfig: [],
            data: null,
            buttonFlag:false,
            activeKey: '1'
        }
    }
    componentDidMount() {
        this.refresh();
    }
    tabsCallback = (activeKey) => {
        this.setState({
            activeKey
        })
    }
    refresh = () => {
        const { departmentId } = this.state;
        this.props.myFetch('getZjXmJxQuarterlyAssessmentList', {}).then(({ success, message, data }) => {
            if (success) {
                console.log(data)
            } else {
                Msg.error(message);
            }
        });
        this.props.myFetch('checkZjXmJxQuarterlyProjectDetailsActualScore', { deptId: departmentId }).then((obj) => {
            if (obj.success) {
                this.setState({
                    buttonFlag:obj.data.buttonFlag === 'hide' ? true : false
                },() => {
                    this.props.myFetch('getZjXmJxQuarterlyProjectDetailsDeptColumn', { deptId: departmentId }).then(({ success, message, data }) => {
                        if (success) {
                            let formConfig = [
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'detailsId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '序号',
                                        dataIndex: 'index',
                                        key: 'index',
                                        width: 50,
                                        fixed: "left",
                                        render: (val, rowData, index) => {
                                            return index + 1;
                                        }
                                    },
                                    isInForm: false
                                },
                            ];
                            for (let i = 0; i < data.length; i++) {
                                if (Object.keys(data[i]).length === 1) {
                                    formConfig.push({
                                        table: {
                                            title: Object.keys(data[i])[0],
                                            dataIndex: data[i][Object.keys(data[i])[0]],
                                            key: data[i][Object.keys(data[i])[0]],
                                            fixed: 'left',
                                            width: (i === 1 || i === 2) ? 150 : (i === 3 ? 120 : 80)
                                        },
                                        isInForm: false
                                    })
                                } else {
                                    if (Object.keys(data[i])[1].split('~').length > 1) {
                                        formConfig.push({
                                            table: {
                                                title: `${Object.keys(data[i])[0]}(含加减分项)`,
                                                dataIndex: data[i][Object.keys(data[i])[0]],
                                                key: data[i][Object.keys(data[i])[0]],
                                                children: [
                                                    {
                                                        title: Object.keys(data[i])[1],
                                                        dataIndex: data[i][Object.keys(data[i])[1]],
                                                        key: data[i][Object.keys(data[i])[1]],
                                                        children: [
                                                            {
                                                                title: Object.keys(data[i])[2],
                                                                dataIndex: data[i][Object.keys(data[i])[2]],
                                                                key: data[i][Object.keys(data[i])[2]],
                                                                tdEdit: obj.data.buttonFlag === 'hide' ? false : true,
                                                                tdEditCb:(obj) => {
                                                                    this.state.data.map((item) => {
                                                                        if(item.detailsId === obj.newRowData.detailsId){
                                                                            item[obj.editField] = obj.newRowData[obj.editField]
                                                                        }
                                                                        return item;
                                                                    }); 
                                                                },
                                                                fieldsConfig: {
                                                                    type: "number",
                                                                    min: Number(Object.keys(data[i])[1].split('~')[0]),
                                                                    max: Number(Object.keys(data[i])[1].split('~')[1]),
                                                                    placeholder: "请输入..."
                                                                },
                                                            }
                                                        ]
                                                    }
                                                ]
                                            },
                                            isInForm: false
                                        })
                                    } else {
                                        formConfig.push({
                                            table: {
                                                title: Object.keys(data[i])[0],
                                                dataIndex: data[i][Object.keys(data[i])[0]],
                                                key: data[i][Object.keys(data[i])[0]],
                                                children: [
                                                    {
                                                        title: Object.keys(data[i])[1],
                                                        dataIndex: data[i][Object.keys(data[i])[1]],
                                                        key: data[i][Object.keys(data[i])[1]],
                                                        children: [
                                                            {
                                                                title: Object.keys(data[i])[2],
                                                                dataIndex: data[i][Object.keys(data[i])[2]],
                                                                key: data[i][Object.keys(data[i])[2]],
                                                                tdEdit: obj.data.buttonFlag === 'hide' ? false : true,
                                                                tdEditCb:(obj) => {
                                                                    this.state.data.map((item) => {
                                                                        if(item.detailsId === obj.newRowData.detailsId){
                                                                            item[obj.editField] = obj.newRowData[obj.editField]
                                                                        }
                                                                        return item;
                                                                    }); 
                                                                },
                                                                fieldsConfig: {
                                                                    type: "number",
                                                                    min: 0,
                                                                    max: Number(Object.keys(data[i])[1]),
                                                                    placeholder: "请输入..."
                                                                },
                                                            }
                                                        ]
                                                    }
                                                ]
                                            },
                                            isInForm: false
                                        })
                                    }
            
                                }
                            }
                            this.setState({
                                formConfig: formConfig
                            })
                        } else {
                            Msg.error(message);
                        }
                    });
                })
            } else {
                Msg.error(obj.message);
            }
        });
        this.props.myFetch('getZjXmJxQuarterlyProjectDetailsByPersonLiable', { deptId: departmentId }).then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    data
                })
            } else {
                Msg.error(message);
            }
        });
    }
    render() {
        const { formConfig, data, buttonFlag, activeKey } = this.state;
        return (
            <div>
                <Tabs activeKey={activeKey} onChange={this.tabsCallback}>

                </Tabs>
                {formConfig.length ? <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    actionBtns={[
                        {
                            name: 'save',
                            type: 'primary',
                            label: '保存',
                            hide:buttonFlag,
                            onClick:(obj) => {
                                confirm({
                                    title: `您确定要保存数据么?`,
                                    content: `数据保存后不可修改!`,
                                    okText: "确认",
                                    cancelText: "取消",
                                    onOk: () => {
                                        this.props.myFetch('submitZjXmJxQuarterlyProjectDetailsByPersonLiable', data).then(({ success, message, data }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({
                                                    formConfig:[]
                                                },() => {
                                                    this.refresh();
                                                })
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    },
                                });
                            }
                        }
                    ]}
                    data={data}
                    formConfig={formConfig}
                /> : null}
            </div>
        );
    }
}

export default index;