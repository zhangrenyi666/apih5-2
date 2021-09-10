import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { goBack } from "connected-react-router";
import { message as Msg, Spin, Avatar, Modal } from "antd";
import moment from 'moment';
import downLoad from "../modules/download";
const confirm = Modal.confirm;
const config = {
    getRowSelection: function (obj) {
        return {
            //设置某行为禁止选中  
            getCheckboxProps: record => ({
                name: record.name,
                disabled: record.complianceBanseName === "工程可行性研究报告" || record.complianceBanseName === "工可批复或项目核准" || record.complianceBanseName === "初设批复" || record.complianceBanseName === "施工图设计批复" || record.complianceBanseName === "用地批复" || record.complianceBanseName === "施工许可证" || record.complianceBanseName === "融资协议" || record.complianceBanseName === "环评批复", // Column configuration not to be checked
            })
        }
    },
    antd: {
        rowKey: 'complianceDetailId',
        size: 'small',
        scroll:{
            y:window.innerHeight - 380
        }
    },
    drawerConfig: {
        width: '1000px'
    },
    curPage: 1,
    limit: 999999,
    paginationConfig: false,
    formItemLayout: {
        labelCol: {
            xs: { span: 21 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            complianceDealId: props.match.params.complianceDealId || '',
            projectId: props.match.params.projectId || '',
            data: [],
            loading: false,
            rowData: {},
            index: 0
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true
        }, () => {
            const { complianceDealId, projectId } = this.state;
            this.props.myFetch('getZjTzComplianceDetailList', { complianceDealId: complianceDealId }).then(
                ({ data, success, message }) => {
                    if (success) {
                        if (data.length) {
                            data[0].projectId = projectId;
                            this.setState({
                                rowData: data[0],
                                data: data[0].zjTzComplianceDetailList,
                                loading: false
                            })
                        } else {
                            this.setState({
                                data: [],
                                loading: false
                            })
                        }
                    } else {
                        Msg.error(message)
                    }
                }
            );
        })
    }
    render() {
        const { data, loading, rowData } = this.state;
        return (
            <div className={s.root}>
                <Spin spinning={loading}>
                    <div className={s.top}>
                        <div className={s.topl}>
                            <h4 style={{ color: 'red' }}>应办理环节指的是项目公司成立时，项目应办理的合法合规文件，从工可、初设、施设、用地、施工许可、融资协议中选择</h4>
                        </div>
                        <div className={s.topr}>
                            {rowData?.companyName ? <div><span style={{ fontWeight: 'bold' }}>管理单位：</span>{rowData.companyName}</div> : null}
                            {rowData?.projectShortName ? <div><span style={{ fontWeight: 'bold' }}>当前项目：</span>{rowData.projectShortName}</div> : null}
                            {rowData?.subprojectName ? <div><span style={{ fontWeight: 'bold' }}>子项目名称：</span>{rowData.subprojectName}</div> : null}
                        </div>
                    </div>
                    <QnnTable
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        data={data}
                        {...config}
                        formConfig={[
                            {
                                isInTable: false,
                                form: {
                                    field: 'complianceDetailId',
                                    type: 'string',
                                    hide: true,
                                }
                            },
                            {
                                table: {
                                    title: '制度时限预警',
                                    dataIndex: 'colourFlag',
                                    key: 'colourFlag',
                                    width: 100,
                                    fixed: 'left',
                                    render: (data, rowData, index) => {
                                        if (index < 8) {
                                            if (data === 'green') {
                                                return <div><Avatar shape="square" size={20} src={require('../../imgs/yjgreen.png')} /></div>;
                                            } else if (data === 'yellow') {
                                                return <div><Avatar shape="square" size={20} src={require('../../imgs/yjyellow.png')} /></div>;
                                            } else if (data === 'red') {
                                                return <div><Avatar shape="square" size={20} src={require('../../imgs/yjred.png')} /></div>;
                                            } else {
                                                return '';
                                            }
                                        }else{
                                            return '';
                                        }
                                    }
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '请输入'
                                },
                            },
                            {
                                table: {
                                    title: '策划时限预警',
                                    dataIndex: 'colourFlag1',
                                    key: 'colourFlag1',
                                    width: 100,
                                    fixed: 'left',
                                    render: (data, rowData, index) => {
                                        if (index < 8) {
                                            if (data === 'green') {
                                                return <div><Avatar shape="square" size={20} src={require('../../imgs/yjgreen.png')} /></div>;
                                            } else if (data === 'yellow') {
                                                return <div><Avatar shape="square" size={20} src={require('../../imgs/yjyellow.png')} /></div>;
                                            } else if (data === 'red') {
                                                return <div><Avatar shape="square" size={20} src={require('../../imgs/yjred.png')} /></div>;
                                            } else {
                                                return '';
                                            }
                                        }else{
                                            return '';
                                        }
                                    }
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '请输入'
                                },
                            },
                            {
                                table: {
                                    title: '序号',
                                    dataIndex: 'index',
                                    key: 'index',
                                    width: 80,
                                    fixed: 'left',
                                    render: (data, rowData, index) => {
                                        // if (index < 8) {
                                        //     if (rowData.colourFlag === 'green') {
                                        //         return <div><Avatar shape="square" size={20} src={require('../../imgs/yjgreen.png')} />{index + 1}</div>;
                                        //     } else if (rowData.colourFlag === 'yellow') {
                                        //         return <div><Avatar shape="square" size={20} src={require('../../imgs/yjyellow.png')} />{index + 1}</div>;
                                        //     } else if (rowData.colourFlag === 'red') {
                                        //         return <div><Avatar shape="square" size={20} src={require('../../imgs/yjred.png')} />{index + 1}</div>;
                                        //     }
                                        // } else {
                                        //     return index + 1;
                                        // }
                                        return index + 1;
                                    }
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '环节编号',
                                    dataIndex: 'num',
                                    key: 'num',
                                    width: 80,
                                    fixed: 'left',
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '请输入'
                                },
                            },
                            {
                                table: {
                                    title: '合规环节名称',
                                    dataIndex: 'complianceBanseName',
                                    key: 'complianceBanseName',
                                    width: 130,
                                    fixed: 'left',
                                    tdEdit: true,
                                    tdEditCb: (obj) => {
                                        this.state.data.map((item) => {
                                            if (item.complianceDetailId === obj.newRowData.complianceDetailId) {
                                                item.complianceBanseName = obj.newRowData.complianceBanseName;
                                            }
                                            return item;
                                        });
                                    }
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '请输入',
                                    disabled: (obj, index) => {
                                        if (obj.rowIndex < 8) {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    }
                                },
                            },
                            {
                                table: {
                                    title: '<center>项目公司成立时<br/>应办理环节</center>',
                                    dataIndex: 'dealFlag',
                                    key: 'dealFlag',
                                    width: 110,
                                    tdEdit: true,
                                    tdEditCb: (obj) => {
                                        this.state.data.map((item) => {
                                            if (item.complianceDetailId === obj.newRowData.complianceDetailId) {
                                                item.dealFlag = '1';
                                            } else {
                                                item.dealFlag = '0';
                                            }
                                            return item;
                                        });
                                        this.table.refresh();
                                    },
                                    render: () => {
                                        return '';
                                    }
                                },
                                form: {
                                    type: 'radio',
                                    optionData: [
                                        {
                                            label: "",
                                            value: "1"
                                        }
                                    ],
                                    initialValue: '0',
                                    disabled: (obj, index) => {
                                        if (obj.rowIndex > 7) {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    }
                                },
                            },
                            {
                                table: {
                                    title: '<center>制度要求应办理<br>完结日期<center>',
                                    dataIndex: 'shouldFinishDate',
                                    key: 'shouldFinishDate',
                                    format: "YYYY-MM-DD",
                                    tdEdit: true,
                                    width: 110,
                                    tdEditCb: (obj) => {
                                        this.state.data.map((item) => {
                                            if (item.complianceDetailId === obj.newRowData.complianceDetailId) {
                                                item.shouldFinishDate = obj.newRowData.shouldFinishDate;
                                            }
                                            return item;
                                        });
                                    }
                                },
                                form: {
                                    type: 'date',
                                    placeholder: '请选择',
                                    disabled: (obj, index) => {
                                        if (obj.rowIndex < 8) {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    }
                                },
                            },
                            {
                                table: {
                                    title: '<center>策划批复应办理<br>完结日期<center>',
                                    dataIndex: 'approvalShouldFinishDate',
                                    key: 'approvalShouldFinishDate',
                                    format: "YYYY-MM-DD",
                                    tdEdit: true,
                                    width: 130,
                                    tdEditCb: (obj) => {
                                        this.state.data.map((item) => {
                                            if (item.complianceDetailId === obj.newRowData.complianceDetailId) {
                                                item.approvalShouldFinishDate = obj.newRowData.approvalShouldFinishDate;
                                            }
                                            return item;
                                        });
                                    }
                                },
                                form: {
                                    type: 'date',
                                    placeholder: '请选择'
                                },
                            },
                            {
                                table: {
                                    title: '文件批复日期',
                                    dataIndex: 'approvalDate',
                                    key: 'approvalDate',
                                    format: "YYYY-MM-DD",
                                    tdEdit: true,
                                    width: 130,
                                    tdEditCb: (obj) => {
                                        if (obj.newRowData.approvalDate > moment().endOf('day').valueOf()) {
                                            Msg.error('文件批复日期不可选择晚于今天的日期！');
                                            this.state.data.map((item) => {
                                                if (item.complianceDetailId === obj.newRowData.complianceDetailId) {
                                                    item.approvalDate = undefined;
                                                }
                                                return item;
                                            });
                                            this.table.refresh();
                                        } else {
                                            this.state.data.map((item) => {
                                                if (item.complianceDetailId === obj.newRowData.complianceDetailId) {
                                                    item.approvalDate = obj.newRowData.approvalDate;
                                                }
                                                return item;
                                            });
                                        }
                                    }
                                },
                                form: {
                                    type: 'date',
                                    placeholder: '请选择'
                                },
                            },
                            {
                                table: {
                                    title: '办理情况',
                                    dataIndex: 'dealSituation',
                                    key: 'dealSituation',
                                    tdEdit: true,
                                    width: 100,
                                    tdEditCb: (obj) => {
                                        this.state.data.map((item) => {
                                            if (item.complianceDetailId === obj.newRowData.complianceDetailId) {
                                                item.dealSituation = obj.newRowData.dealSituation;
                                            }
                                            return item;
                                        });
                                    }
                                },
                                form: {
                                    type: 'select',
                                    optionConfig: {
                                        label: "label",
                                        value: "value"
                                    },
                                    optionData: [
                                        {
                                            label: '未启动',
                                            value: '0'
                                        },
                                        {
                                            label: '办理中',
                                            value: '1'
                                        },
                                        {
                                            label: '已完成',
                                            value: '2'
                                        },
                                        {
                                            label: '无需办理',
                                            value: '3'
                                        }
                                    ],
                                    placeholder: '请选择'
                                },
                            },
                            {
                                table: {
                                    title: '附件',
                                    dataIndex: 'zjTzFileList',
                                    key: 'zjTzFileList',
                                    tdEdit: true,
                                    width: 220,
                                    tdEditCb: (obj) => {
                                        this.state.data.map((item) => {
                                            if (item.complianceDetailId === obj.newRowData.complianceDetailId) {
                                                item.zjTzFileList = obj.newRowData.zjTzFileList;
                                            }
                                            return item;
                                        });
                                    }
                                },
                                form: {
                                    type: 'files',
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload'
                                    },
                                    showDownloadIcon: true,//是否显示下载按钮
                                    onPreview: "bind:_docFilesByOfficeUrl",//365显示

                                },
                            },
                            {
                                table: {
                                    title: '备注',
                                    dataIndex: 'bz',
                                    key: 'bz',
                                    width: 150,
                                    tdEdit: true,
                                    tdEditCb: (obj) => {
                                        this.state.data.map((item) => {
                                            if (item.complianceDetailId === obj.newRowData.complianceDetailId) {
                                                item.bz = obj.newRowData.bz;
                                            }
                                            return item;
                                        });
                                    }
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '请输入'
                                },
                            },
                            {
                                isInForm: false,
                                table: {
                                    title: "操作",
                                    fixed: 'right',
                                    dataIndex: 'action',
                                    key: 'action',
                                    align: "center",
                                    noHaveSearchInput: true,
                                    showType: "tile",
                                    width: 80,
                                    btns: [
                                        {
                                            name: 'ComplianceToDealWithDetail',
                                            render: (obj, rowData, index) => {
                                                if (index < 8) {
                                                    return '';
                                                } else {
                                                    return '<a>插入</a>';
                                                }
                                            },
                                            onClick: (obj) => {
                                                this.setState({
                                                    loading: true,
                                                    index: this.state.index + 1
                                                }, () => {
                                                    let datas = this.state.data;
                                                    let number = 0;
                                                    for (let i = 0; i < datas.length; i++) {
                                                        if (datas[i].complianceDetailId === obj.rowData.complianceDetailId) {
                                                            number = i;
                                                        }
                                                    }
                                                    datas.splice(number, 0, { complianceDealId: this.state.rowData.complianceDealId, complianceDetailId: String(this.state.index) });
                                                    let datat = [];
                                                    for (let i = 0; i < datas.length; i++) {
                                                        if (i <= 8) {
                                                            datas[i].num = `00${i + 1}`;
                                                        } else {
                                                            datas[i].num = `0${i + 1}`;
                                                        }
                                                        datat.push(datas[i]);
                                                    }
                                                    this.setState({
                                                        data: datat,
                                                        loading: false
                                                    })
                                                })
                                            },
                                        }
                                    ]
                                }
                            }
                        ]}
                        method={{
                            goBack: (obj) => {
                                this.props.dispatch(
                                    goBack()
                                )
                            },
                            saveClick: (obj) => {
                                this.setState({
                                    loading: true
                                }, () => {
                                    for (var i = 0; i < this.state.data.length; i++) {
                                        if (this.state.data[i].dealFlag === '1') {
                                            for (var j = 0; j < this.state.data.length; j++) {
                                                if (this.state.data[j].dealSituation === '2' && !this.state.data[j].approvalDate) {
                                                    Msg.error('办理情况为【已完成】的数据文件批复日期必填！', 5);
                                                    this.setState({
                                                        loading: false
                                                    })
                                                    break;
                                                } else if (this.state.data[j].dealSituation === '3' && !this.state.data[j].bz) {
                                                    Msg.error('办理情况为【无需办理】的数据备注必填！', 5);
                                                    this.setState({
                                                        loading: false
                                                    })
                                                    break;
                                                } else if (j === this.state.data.length - 1) {
                                                    confirm({
                                                        content: '确定保存数据吗?',
                                                        onOk: () => {
                                                            rowData.zjTzComplianceDetailList = this.state.data;
                                                            obj.btnCallbackFn.fetchByCb('saveZjTzComplianceDealAllDetail', rowData, ({ data, success, message }) => {
                                                                if (success) {
                                                                    Msg.success(message);
                                                                    this.setState({
                                                                        loading: false
                                                                    })
                                                                    this.refresh();
                                                                } else {
                                                                    Msg.error(message);
                                                                    this.setState({
                                                                        loading: false
                                                                    })
                                                                    this.refresh();
                                                                }
                                                            });
                                                        },
                                                        onCancel:() => {
                                                            this.setState({
                                                                loading: false
                                                            })
                                                        }
                                                    });
                                                }
                                            }
                                            break;
                                        } else if (i === this.state.data.length - 1) {
                                            Msg.error('项目公司成立时应办理环节必须勾选一条！', 5);
                                            this.setState({
                                                loading: false
                                            })
                                        }
                                    }
                                })
                            },
                            diydelClick: (obj) => {
                                this.setState({
                                    loading: true
                                }, () => {
                                    let selectedRows = obj.selectedRows;
                                    let datas = this.state.data;
                                    datas = datas.filter(item => {
                                        let idList = selectedRows.map(v => v.complianceDetailId)
                                        return !idList.includes(item.complianceDetailId)
                                    })
                                    let datat = [];
                                    for (let i = 0; i < datas.length; i++) {
                                        if (i <= 8) {
                                            datas[i].num = `00${i + 1}`;
                                        } else {
                                            datas[i].num = `0${i + 1}`;
                                        }
                                        datat.push(datas[i]);
                                    }
                                    this.setState({
                                        data: datat,
                                        loading: false
                                    }, () => {
                                        this.table.clearSelectedRows();
                                    })
                                })
                            },
                            exportClick: () => {
                                const {
                                    loginAndLogoutInfo: {
                                        loginInfo: { token }
                                    },
                                    myPublic: { domain }
                                } = this.props;
                                let body = {
                                    fileName: `${rowData?.projectShortName}合法合规办理情况`,
                                    complianceDealId: rowData?.complianceDealId
                                }
                                let URL = `${domain + "exportZjTzComplianceDetailList"}`;
                                confirm({
                                    content: '确定导出数据吗?',
                                    onOk: () => {
                                        downLoad(URL, body, { token });
                                    }
                                });
                            }
                        }}
                        actionBtns={{
                            apiName: "getSysMenuBtn",
                            otherParams: function (obj) {
                                var props = obj.Pprops;
                                let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                return {
                                    menuParentId: curRouteData._value,
                                    tableField: "projectInfo"
                                }
                            }
                        }}
                    />
                </Spin>
            </div>
        );
    }
}

export default index;