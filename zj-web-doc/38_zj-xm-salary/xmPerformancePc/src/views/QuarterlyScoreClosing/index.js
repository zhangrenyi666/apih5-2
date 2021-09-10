import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Tabs, Spin } from 'antd';
import moment from 'moment';
import s from './style.less';
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
            buttonFlag: false,
            loading: false,
            activeKey: '1',
            tableData:[],
            departmentData: [],
            quarterlyData: [],
            yearMonth: moment(new Date()).valueOf()
        }
    }
    componentDidMount() {
        this.refresh();
    }
    tabsCallback = (activeKey) => {
        const { departmentData, quarterlyData } = this.state;
        let index = Number(activeKey) - 1;
        this.setState({
            activeKey,
            formConfig:[]
        },() => {
            if(departmentData.length){
                this.props.myFetch('checkZjXmJxQuarterlyProjectDetailsActualScore', { deptId: departmentData[0].deptId, assessmentId:quarterlyData[index].assessmentId }).then((obj) => {
                    if (obj.success) {
                        this.setState({
                            buttonFlag: obj.data.buttonFlag === 'hide' ? true : false
                        }, () => {
                            this.props.myFetch('getZjXmJxQuarterlyProjectDetailsDeptColumn', { deptId: departmentData[0].deptId, assessmentId:quarterlyData[index].assessmentId }).then(({ success, message, data }) => {
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
                                                    width: (i === 0 || i === 1 || i === 2) ? 150 : 120
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
                                                                        tdEditCb: (obj) => {
                                                                            this.state.tableData.map((item) => {
                                                                                if (item.detailsId === obj.newRowData.detailsId) {
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
                                                                        tdEditCb: (obj) => {
                                                                            this.state.tableData.map((item) => {
                                                                                if (item.detailsId === obj.newRowData.detailsId) {
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
                                        formConfig: formConfig,
                                        loading:false
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
                this.props.myFetch('getZjXmJxQuarterlyProjectDetailsByPersonLiable', { deptId: departmentData[0].deptId, assessmentId:quarterlyData[index].assessmentId }).then((obj) => {
                    if (obj.success) {
                        this.setState({
                            tableData: obj.data
                        })
                    } else {
                        Msg.error(obj.message);
                    }
                });
            }
        })
    }
    refresh = () => {
        const { departmentId } = this.state;
        this.setState({
            loading: true
        })
        this.props.myFetch('getZjXmJxQuarterlyAssessmentList', { yearMonth: this.state.yearMonth }).then((quarterlyObj) => {
            if (quarterlyObj.success) {
                this.setState({
                    quarterlyData: quarterlyObj.data,
                    loading: quarterlyObj.data.length ? true : false
                }, () => {
                    if(quarterlyObj.data.length){
                        this.props.myFetch('getZjXmJxQuarterlyAssessmentDeptList', { isClosed: '1', departmentId: departmentId }).then((departmentObj) => {
                            if (departmentObj.success) {
                                this.setState({
                                    departmentData: departmentObj.data,
                                    loading: departmentObj.data.length ? true : false
                                }, () => {
                                    if (departmentObj.data.length) {
                                        this.props.myFetch('checkZjXmJxQuarterlyProjectDetailsActualScore', { deptId: departmentObj.data[0].deptId, assessmentId:quarterlyObj.data[0].assessmentId }).then((obj) => {
                                            if (obj.success) {
                                                this.setState({
                                                    buttonFlag: obj.data.buttonFlag === 'hide' ? true : false
                                                }, () => {
                                                    this.props.myFetch('getZjXmJxQuarterlyProjectDetailsDeptColumn', { deptId: departmentObj.data[0].deptId, assessmentId:quarterlyObj.data[0].assessmentId }).then(({ success, message, data }) => {
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
                                                                            width: (i === 0 || i === 1 || i === 2) ? 150 : 120
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
                                                                                                tdEditCb: (obj) => {
                                                                                                    this.state.tableData.map((item) => {
                                                                                                        if (item.detailsId === obj.newRowData.detailsId) {
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
                                                                                                tdEditCb: (obj) => {
                                                                                                    this.state.tableData.map((item) => {
                                                                                                        if (item.detailsId === obj.newRowData.detailsId) {
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
                                                                formConfig: formConfig,
                                                                loading:false
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
                                        this.props.myFetch('getZjXmJxQuarterlyProjectDetailsByPersonLiable', { deptId: departmentObj.data[0].deptId, assessmentId:quarterlyObj.data[0].assessmentId }).then((obj) => {
                                            if (obj.success) {
                                                this.setState({
                                                    tableData: obj.data
                                                })
                                            } else {
                                                Msg.error(obj.message);
                                            }
                                        });
                                    }
                                })
                            } else {
                                Msg.error(departmentObj.message);
                            }
                        });
                    }
                })
            } else {
                Msg.error(quarterlyObj.message);
            }
        });
    }
    render() {
        const { formConfig, tableData, buttonFlag, activeKey, loading, quarterlyData, departmentData, yearMonth } = this.state;
        return (
            <div className={s.root}>
                <Spin spinning={loading}>
                    <QnnForm
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload} //必须返回一个promise
                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                        headers={{
                            token: this.props.loginAndLogoutInfo.loginInfo.token
                        }}
                        formItemLayout={{
                            labelCol: {
                                xs: { span: 7 },
                                sm: { span: 7 }
                            },
                            wrapperCol: {
                                xs: { span: 17 },
                                sm: { span: 17 }
                            }
                        }}
                        formConfig={[
                            {
                                label: '考核年份',
                                field: 'yearMonth',
                                type: 'year',
                                initialValue: yearMonth,
                                onChange: (val) => {
                                    this.setState({
                                        yearMonth: moment(val).valueOf()
                                    }, () => {
                                        this.refresh();
                                    })
                                },
                                allowClear: false,
                                placeholder: '请选择',
                                span: 8
                            }
                        ]}
                    />
                    {
                        quarterlyData.length ? <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
                            {
                                quarterlyData.map((item, index) => {
                                    let quarterName;
                                    if (item.quarter === '1') {
                                        quarterName = '一季度';
                                    } else if (item.quarter === '2') {
                                        quarterName = '二季度';
                                    } else if (item.quarter === '3') {
                                        quarterName = '三季度';
                                    } else if (item.quarter === '4') {
                                        quarterName = '四季度';
                                    }
                                    let yearMonthName = moment(item.yearMonth).format('YYYY');
                                    let assessmentTitle = item.assessmentTitle;
                                    return (
                                        <TabPane tab={quarterName} key={`${index + 1}`}>
                                            {
                                                departmentData.length ? formConfig.length && activeKey === `${index + 1}` ? <div><div style={{textAlign:'center',fontSize:'18px',fontWeight:'bold'}}>{yearMonthName}{quarterName}{assessmentTitle}评分表</div><QnnTable
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
                                                            hide: buttonFlag,
                                                            onClick: (obj) => {
                                                                confirm({
                                                                    title: `您确定要保存数据么?`,
                                                                    content: `数据保存后不可修改!`,
                                                                    okText: "确认",
                                                                    cancelText: "取消",
                                                                    onOk: () => {
                                                                        this.props.myFetch('submitZjXmJxQuarterlyProjectDetailsByPersonLiable', tableData).then(({ success, message, data }) => {
                                                                            if (success) {
                                                                                Msg.success(message);
                                                                                this.setState({
                                                                                    formConfig: []
                                                                                }, () => {
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
                                                    data={tableData}
                                                    formConfig={formConfig}
                                                /></div> : null : <div style={{ textAlign: "center", paddingTop: "10vh", fontSize: "20px", fontWeight: 'bold' }}>您所在部门暂无本季度考核数据...</div>
                                            }
                                        </TabPane>
                                    )
                                })
                            }
                        </Tabs> : <div style={{ textAlign: "center", paddingTop: "20vh", fontSize: "20px", fontWeight: 'bold' }}>暂无季度考核数据...</div>
                    }
                </Spin>
            </div>
        );
    }
}

export default index;