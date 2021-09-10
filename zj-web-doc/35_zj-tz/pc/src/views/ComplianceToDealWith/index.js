import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";
import { Avatar, message as Msg } from "antd";
const config = {
    antd: {
        rowKey: function (row) {
            return row.complianceDealId
        },
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.65
        }
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            establishDate:null
        }
    }
    componentDidMount(){
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        let establishDate;
        this.props.myFetch('getZjTzProManageList',{}).then(
            ({ data, success ,message }) => {
                if (success) {
                    data.map((item) => {
                        if(item.projectId === projectId){
                            establishDate = item.company2;
                        }
                        return item;
                    })
                    this.setState({
                        establishDate
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div className={s.root}>
                 
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch} 
		            upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={{
                        apiName: 'getZjTzComplianceDealList',
                        otherParams: {
                            projectId: projectId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'complianceDealId',
                                type: 'string',
                                hide:true,
                            }
                        },
                        {
                            table: {
                                title: '考核预警',
                                dataIndex: 'warnFlag',
                                key: 'warnFlag',
                                width: 80,
                                align:'center',
                                fixed: 'left',
                                render:(data) => {
                                    if(data === 'green'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjgreen.png')} />;
                                    }else if(data === 'yellow'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjyellow.png')} />;
                                    }else if(data === 'red'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjred.png')} />;
                                    }else{
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目名称',
                                filter: true,
                                width: 400,
                                fixed:'left',
                                dataIndex: 'projectId',
                                key: 'projectId',
                                
                                type:'select'
                            },
                            form: {
                                field: 'projectId',
                                type: 'select',
                                showSearch:true,
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: () => {
                                    return projectId
                                },
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '请选择',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 8 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 16 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                        },
                        {   
                            table: {
                                title: '子项目名称',
                                dataIndex: 'subprojectName',
                                key: 'subprojectName',
                                width: 250,
                                onClick: 'detail',
                                fixed:'left'
                                
                            },
                            form: {
                                field: 'subprojectInfoId',
                                type: 'select',
                                editDisabled:true,
                                placeholder: '请输入',
                                optionConfig: {
                                    label: 'subprojectName',
                                    value: 'subprojectInfoId'
                                },
                                fetchConfig: {
                                    apiName:"getZjTzProSubprojectInfoList",
                                    otherParams: {
                                        projectId: projectId
                                    }
                                },
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 8 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 16 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '管理单位',
                                dataIndex: 'companyName',
                                key: 'companyName',
                                width: 150,
                                filter:true,
                            },
                            isInForm:false,
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                            }
                        },
                        {
                            table: {
                                title: '项目公司成立日期',
                                dataIndex: 'establishDate',
                                key: 'establishDate',
                                format:'YYYY-MM-DD',
                                width:150,
                            },
                            form: {
                                type: 'date',
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true,
                                required:true,
                                diyRules: (obj) => {
                                    var required = obj.required;
                                    return [
                                        {
                                            required: required,
                                            message:'【请在项目信息中填写项目公司成立时间】'
                                        }
                                    ];
                                },
                                initialValue:(obj) => {
                                    if(this.state.establishDate){
                                        return this.state.establishDate;
                                    }
                                }
                            }
                        },
                        
                        {
                            table: {
                                title: '工程可行性研究报告',
                                dataIndex: 'base1',
                                key: 'base1',
                                width:150,
                                render:(data) => {
                                    if(data === 'green'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjgreen.png')} />;
                                    }else if(data === 'yellow'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjyellow.png')} />;
                                    }else if(data === 'red'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjred.png')} />;
                                    }
                                }
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '工可批复或项目核准',
                                dataIndex: 'base2',
                                key: 'base2',
                                width:150,
                                render:(data) => {
                                    if(data === 'green'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjgreen.png')} />;
                                    }else if(data === 'yellow'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjyellow.png')} />;
                                    }else if(data === 'red'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjred.png')} />;
                                    }
                                }
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '初设批复',
                                dataIndex: 'base3',
                                key: 'base3',
                                width:100,
                                render:(data) => {
                                    if(data === 'green'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjgreen.png')} />;
                                    }else if(data === 'yellow'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjyellow.png')} />;
                                    }else if(data === 'red'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjred.png')} />;
                                    }
                                }
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '施工图设计批复',
                                dataIndex: 'base4',
                                key: 'base4',
                                width:120,
                                render:(data) => {
                                    if(data === 'green'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjgreen.png')} />;
                                    }else if(data === 'yellow'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjyellow.png')} />;
                                    }else if(data === 'red'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjred.png')} />;
                                    }
                                }
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '用地批复',
                                dataIndex: 'base5',
                                key: 'base5',
                                width:100,
                                render:(data) => {
                                    if(data === 'green'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjgreen.png')} />;
                                    }else if(data === 'yellow'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjyellow.png')} />;
                                    }else if(data === 'red'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjred.png')} />;
                                    }
                                }
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '施工许可证',
                                dataIndex: 'base6',
                                key: 'base6',
                                width:100,
                                render:(data) => {
                                    if(data === 'green'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjgreen.png')} />;
                                    }else if(data === 'yellow'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjyellow.png')} />;
                                    }else if(data === 'red'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjred.png')} />;
                                    }
                                }
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '融资协议',
                                dataIndex: 'base7',
                                key: 'base7',
                                width:100,
                                render:(data) => {
                                    if(data === 'green'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjgreen.png')} />;
                                    }else if(data === 'yellow'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjyellow.png')} />;
                                    }else if(data === 'red'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjred.png')} />;
                                    }
                                }
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '环评批复',
                                dataIndex: 'base8',
                                key: 'base8',
                                width:100,
                                render:(data) => {
                                    if(data === 'green'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjgreen.png')} />;
                                    }else if(data === 'yellow'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjyellow.png')} />;
                                    }else if(data === 'red'){
                                        return <Avatar shape="square" size={20} src={require('../../imgs/yjred.png')} />;
                                    }
                                }
                            },
                            isInForm:false
                        },
                        {
                            isInTable:false,
                            form: {
                                label:'备注',
                                field:'remarks',
                                type: 'textarea',
                                autoSize:{
                                    minRows: 2,
                                },
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable:false,
                            form: {
                                label:'创建日期',
                                field:'createTime',
                                type: 'date',
                                initialValue:new Date(),
                                placeholder: '请选择',
                                addDisabled:true,
                                editDisabled:true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 8 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 16 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable:false,
                            form: {
                                label:'创建用户',
                                field:'createUserName',
                                type: 'string',
                                initialValue:realName,
                                placeholder: '请输入',
                                addDisabled:true,
                                editDisabled:true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 8 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 16 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
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
                                        name: 'complianceDetail',
                                        render: (rowData) => {
                                            return '<a>合规明细</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { complianceDealId, projectId } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}ComplianceToDealWithDetail/${complianceDealId}/${projectId}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        addClick: (obj) => {
                            if (projectId === 'all') {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('请选择右上角项目！')
                            }
                        },
                        editClick:(obj) => {
                            this.table.clearSelectedRows();
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
            </div>
        );
    }
}

export default index;