import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { Tag, message as Msg, Tabs, Modal, Tooltip } from 'antd';
import s from './style.less';
import QnnForm from '../modules/qnn-table/qnn-form';
const { confirm } = Modal;
const { TabPane } = Tabs;
const config = {
    antd: {
        rowKey: function (row) {
            return row.infoId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    searchBtnsStyle: 'inline'
}
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            TabsData: [
                {
                    itemId: '',
                    itemName: '全部'
                }
            ],
            key: '1',
            visible: false
        }
    }
    componentDidMount() {
        this.props.myFetch('getBaseCodeSelect', { itemId: 'keJiChengGuo' }).then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    TabsData: this.state.TabsData.concat(data)
                });
            } else {
                Msg.error(message);
            }
        });
    }
    callback = (key) => {
        this.setState({ key });
        if (key === '1') {
            if (this.table1) {
                this.table1.refresh();
            }
        } else if (key === '2') {
            if (this.table2) {
                this.table2.refresh();
            }
        } else if (key === '3') {
            if (this.table3) {
                this.table3.refresh();
            }
        } else if (key === '4') {
            if (this.table4) {
                this.table4.refresh();
            }
        } else if (key === '5') {
            if (this.table5) {
                this.table5.refresh();
            }
        } else if (key === '6') {
            if (this.table6) {
                this.table6.refresh();
            }
        } else if (key === '7') {
            if (this.table7) {
                this.table7.refresh();
            }
        } else if (key === '8') {
            if (this.table8) {
                this.table8.refresh();
            }
        } else if (key === '9') {
            if (this.table9) {
                this.table9.refresh();
            }
        } else if (key === '10') {
            if (this.table10) {
                this.table10.refresh();
            }
        }
    }
    render() {
        const { TabsData, key, visible } = this.state;
        const { companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0];
        return (
            <div>
                {TabsData.length && TabsData.length > 1 ? <Tabs activeKey={key} onChange={this.callback}>
                    {
                        TabsData.map((item, index) => {
                            return (
                                <TabPane tab={item.itemName} key={index + 1}>
                                    {item.itemName === '专利' ? <div style={{ height: window.innerHeight - 61 }}>
                                        <QnnTable
                                            {...this.props}
                                            fetch={this.props.myFetch}
                                            upload={this.props.myUpload}
                                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                            wrappedComponentRef={(me) => {
                                                if (index + 1 === 1) {
                                                    this.table1 = me;
                                                } else if (index + 1 === 2) {
                                                    this.table2 = me;
                                                } else if (index + 1 === 3) {
                                                    this.table3 = me;
                                                } else if (index + 1 === 4) {
                                                    this.table4 = me;
                                                } else if (index + 1 === 5) {
                                                    this.table5 = me;
                                                } else if (index + 1 === 6) {
                                                    this.table6 = me;
                                                } else if (index + 1 === 7) {
                                                    this.table7 = me;
                                                } else if (index + 1 === 8) {
                                                    this.table8 = me;
                                                } else if (index + 1 === 9) {
                                                    this.table9 = me;
                                                } else if (index + 1 === 10) {
                                                    this.table10 = me;
                                                }
                                            }}
                                            fetchConfig={{
                                                apiName: 'getZjSjConsultScientificInformationList',
                                                otherParams: {
                                                    technologyIndustryFlag: '0',
                                                    technologicalAchievementsId: item.itemId
                                                }
                                            }}
                                            actionBtns={{
                                                apiName: "getSysMenuBtn",
                                                otherParams: function (obj) {
                                                    var props = obj.Pprops;
                                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                                    return {
                                                        menuParentId: curRouteData._value,
                                                        tableField: "ScientificCG"
                                                    }
                                                }
                                            }}
                                            {...config}
                                            formConfig={[
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'deptId',
                                                        type: 'string',
                                                        initialValue: companyId,
                                                        placeholder: '请输入',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'infoId',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'technologyIndustryFlag',
                                                        type: 'string',
                                                        initialValue: '0',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '发布人',
                                                        field: 'userName',
                                                        type: "string",
                                                        hide: true,
                                                        placeholder: "请输入"
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '发布单位',
                                                        field: 'deptName',
                                                        type: "string",
                                                        initialValue: (obj) => {
                                                            if (obj.clickCb ?.rowInfo ?.name === 'add') {
                                                                return companyName;
                                                            }
                                                            return null;
                                                        },
                                                        hide: true,
                                                        placeholder: "请输入"
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '成果类型',
                                                        field: 'technologicalAchievementsId',
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId',
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getBaseCodeSelect',
                                                            otherParams: {
                                                                itemId: 'keJiChengGuo'
                                                            }
                                                        },
                                                        hide: true,
                                                        initialValue: item.itemId ? item.itemId : null,
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    isInSearch: true,
                                                    table: {
                                                        title: '专利号',
                                                        dataIndex: 'patentNo',
                                                        key: 'patentNo',
                                                        onClick: 'detail',
                                                        width: 150,
                                                        tooltip:200,
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        required: true,
                                                        placeholder: '请输入',
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 3 },
                                                                sm: { span: 3 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 21 },
                                                                sm: { span: 21 }
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    isInSearch: true,
                                                    table: {
                                                        title: '专利名称',
                                                        dataIndex: 'patentName',
                                                        key: 'patentName',
                                                        width: 150,
                                                        tooltip:200,
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        required: true,
                                                        placeholder: '请输入',
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 3 },
                                                                sm: { span: 3 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 21 },
                                                                sm: { span: 21 }
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    isInSearch: true,
                                                    table: {
                                                        title: '专利类型',
                                                        dataIndex: 'patentTypeName',
                                                        key: 'patentTypeName',
                                                        width: 100
                                                    },
                                                    form: {
                                                        field: 'patentTypeId',
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId',
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getBaseCodeSelect',
                                                            otherParams: {
                                                                itemId: 'zhuanLiLeiXing'
                                                            }
                                                        },
                                                        required: true,
                                                        placeholder: '请选择',
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 3 },
                                                                sm: { span: 3 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 21 },
                                                                sm: { span: 21 }
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    isInSearch: true,
                                                    table: {
                                                        title: '授权时间',
                                                        dataIndex: 'authorizationTime',
                                                        key: 'authorizationTime',
                                                        width: 80,
                                                        format: 'YYYY-MM-DD'
                                                    },
                                                    form: {
                                                        field: 'authorizationTime',
                                                        type: 'date',
                                                        required: true,
                                                        placeholder: '请选择',
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 3 },
                                                                sm: { span: 3 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 21 },
                                                                sm: { span: 21 }
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    isInSearch: true,
                                                    table: {
                                                        title: '申请单位',
                                                        dataIndex: 'applicant',
                                                        key: 'applicant',
                                                        width: 80,
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        required: true,
                                                        placeholder: '请输入',
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 3 },
                                                                sm: { span: 3 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 21 },
                                                                sm: { span: 21 }
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '专利权人',
                                                        dataIndex: 'patentee',
                                                        key: 'patentee',
                                                        width: 150,
                                                        tooltip:200,
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        required: true,
                                                        placeholder: '请输入',
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 3 },
                                                                sm: { span: 3 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 21 },
                                                                sm: { span: 21 }
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '附件',
                                                        field: 'attachmentList',
                                                        type: 'files',
                                                        fetchConfig: {
                                                            apiName: window.configs.domain + 'upload'
                                                        },
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 3 },
                                                                sm: { span: 3 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 21 },
                                                                sm: { span: 21 }
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '阅读量',
                                                        dataIndex: 'readNum',
                                                        key: 'readNum',
                                                        width: 80,
                                                        fieldsConfig: {
                                                            type: "number",
                                                            placeholder: "请输入"
                                                        }
                                                    },
                                                    isInForm: false
                                                }
                                            ]}
                                            method={{
                                                editOclick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                },
                                                importOclick: () => {
                                                    this.setState({
                                                        visible: true
                                                    })
                                                },
                                                importsOnClick: () => {
                                                    confirm({
                                                        content: '确定下载导入模板么?',
                                                        centered: true,
                                                        onOk: () => {
                                                            window.location.href = 'http://10.15.51.190/apiJsfw/upload/科技信息导入模板.xlsx';
                                                        }
                                                    });
                                                },
                                                buttonHide: () => {
                                                    return item.itemName === '专利' ? false : true;
                                                }
                                            }}
                                        />
                                    </div> : <div style={{ height: window.innerHeight - 61 }}>
                                            <QnnTable
                                                {...this.props}
                                                fetch={this.props.myFetch}
                                                upload={this.props.myUpload}
                                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                                wrappedComponentRef={(me) => {
                                                    if (index + 1 === 1) {
                                                        this.table1 = me;
                                                    } else if (index + 1 === 2) {
                                                        this.table2 = me;
                                                    } else if (index + 1 === 3) {
                                                        this.table3 = me;
                                                    } else if (index + 1 === 4) {
                                                        this.table4 = me;
                                                    } else if (index + 1 === 5) {
                                                        this.table5 = me;
                                                    } else if (index + 1 === 6) {
                                                        this.table6 = me;
                                                    } else if (index + 1 === 7) {
                                                        this.table7 = me;
                                                    } else if (index + 1 === 8) {
                                                        this.table8 = me;
                                                    } else if (index + 1 === 9) {
                                                        this.table9 = me;
                                                    } else if (index + 1 === 10) {
                                                        this.table10 = me;
                                                    }
                                                }}
                                                fetchConfig={{
                                                    apiName: 'getZjSjConsultScientificInformationList',
                                                    otherParams: {
                                                        technologyIndustryFlag: '0',
                                                        technologicalAchievementsId: item.itemId
                                                    }
                                                }}
                                                actionBtns={{
                                                    apiName: "getSysMenuBtn",
                                                    otherParams: function (obj) {
                                                        var props = obj.Pprops;
                                                        let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                                        return {
                                                            menuParentId: curRouteData._value,
                                                            tableField: "ScientificCG"
                                                        }
                                                    }
                                                }}
                                                {...config}
                                                formConfig={[
                                                    {
                                                        isInTable: false,
                                                        form: {
                                                            field: 'deptId',
                                                            type: 'string',
                                                            initialValue: companyId,
                                                            placeholder: '请输入',
                                                            hide: true,
                                                        }
                                                    },
                                                    {
                                                        isInTable: false,
                                                        form: {
                                                            field: 'infoId',
                                                            type: 'string',
                                                            hide: true,
                                                        }
                                                    },
                                                    {
                                                        isInTable: false,
                                                        form: {
                                                            field: 'technologyIndustryFlag',
                                                            type: 'string',
                                                            initialValue: '0',
                                                            hide: true,
                                                        }
                                                    },
                                                    {
                                                        isInSearch: true,
                                                        table: {
                                                            title: '发布人',
                                                            dataIndex: 'userName',
                                                            key: 'userName',
                                                            width: 80,
                                                            onClick: 'detail'
                                                        },
                                                        form: {
                                                            type: "string",
                                                            addShow: false,
                                                            editDisabled: true,
                                                            hide: (obj) => {
                                                                if (obj ?.initialValues ?.technologicalAchievementsName === '专利') {
                                                                    return true;
                                                                } else {
                                                                    return false;
                                                                }
                                                            },
                                                            placeholder: "请输入",
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInSearch: true,
                                                        table: {
                                                            title: '发布单位',
                                                            dataIndex: 'deptName',
                                                            key: 'deptName',
                                                            width: 150,
                                                            render: (data) => {
                                                                if (data && data.indexOf(',') !== -1) {
                                                                    data = data.split(',').join('→');
                                                                }
                                                                return (
                                                                    <Tooltip title={data}>
                                                                        <span>{data}</span>
                                                                    </Tooltip>
                                                                );
                                                            }
                                                        },
                                                        form: {
                                                            type: "string",
                                                            addShow: false,
                                                            editDisabled: true,
                                                            hide: (obj) => {
                                                                if (obj ?.initialValues ?.technologicalAchievementsName === '专利') {
                                                                    return true;
                                                                } else {
                                                                    return false;
                                                                }
                                                            },
                                                            formatter: function (data) {
                                                                if (data && data.indexOf(',') !== -1) {
                                                                    data = data.split(',').join('→');
                                                                }
                                                                return data;
                                                            },
                                                            initialValue: (obj) => {
                                                                if (obj.clickCb ?.rowInfo ?.name === 'add') {
                                                                    return companyName;
                                                                }
                                                                return null;
                                                            },
                                                            placeholder: "请输入",
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInSearch: item.itemId ? false : true,
                                                        table: {
                                                            title: '成果类型',
                                                            dataIndex: 'technologicalAchievementsName',
                                                            key: 'technologicalAchievementsName',
                                                            width: 100,
                                                            fieldsConfig: {
                                                                type: 'select',
                                                                field: 'technologicalAchievementsId',
                                                                placeholder: '请选择',
                                                                fetchConfig: {
                                                                    apiName: "getBaseCodeSelect",
                                                                    otherParams: {
                                                                        itemId: 'keJiChengGuo'
                                                                    }
                                                                },
                                                                optionConfig: {
                                                                    label: 'itemName',
                                                                    value: 'itemId',
                                                                }
                                                            },
                                                            render: (data) => {
                                                                return (
                                                                    <Tag style={{ width: '100%', textAlign: 'center' }} color={'red'}>
                                                                        {data}
                                                                    </Tag>
                                                                )
                                                            }
                                                        },
                                                        form: {
                                                            field: 'technologicalAchievementsId',
                                                            type: 'select',
                                                            optionConfig: {
                                                                label: 'itemName',
                                                                value: 'itemId',
                                                            },
                                                            fetchConfig: {
                                                                apiName: 'getBaseCodeSelect',
                                                                otherParams: {
                                                                    itemId: 'keJiChengGuo'
                                                                }
                                                            },
                                                            required: true,
                                                            addDisabled: item.itemId ? true : false,
                                                            initialValue: item.itemId ? item.itemId : null,
                                                            placeholder: '请选择',
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInSearch: true,
                                                        table: {
                                                            title: '成果标题',
                                                            dataIndex: 'title',
                                                            key: 'title',
                                                            width: 150,
                                                            render: (data, rowData) => {
                                                                if (rowData.technologicalAchievementsName === '专利') {
                                                                    return (
                                                                        <Tooltip title={rowData.patentName}>
                                                                            <span>{rowData.patentName}</span>
                                                                        </Tooltip>
                                                                    )
                                                                } else {
                                                                    return (
                                                                        <Tooltip title={data}>
                                                                            <span>{data}</span>
                                                                        </Tooltip>
                                                                    )
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            required: true,
                                                            hide: (obj) => {
                                                                if (obj ?.initialValues ?.technologicalAchievementsName === '专利') {
                                                                    return true;
                                                                } else {
                                                                    return false;
                                                                }
                                                            },
                                                            placeholder: '请输入',
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInTable: false,
                                                        form: {
                                                            label: '内容',
                                                            field: 'content',
                                                            type: 'richtext',
                                                            fetchConfig: {
                                                                //必须配置  上传图片地址
                                                                uploadUrl: window.configs.domain + 'upload' //***必传
                                                            },
                                                            hide: (obj) => {
                                                                if (obj ?.initialValues ?.technologicalAchievementsName === '专利') {
                                                                    return true;
                                                                } else {
                                                                    return false;
                                                                }
                                                            },
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '发布时间',
                                                            dataIndex: 'releaseTime',
                                                            key: 'releaseTime',
                                                            format: 'YYYY-MM-DD HH:mm:ss',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: "date",
                                                                placeholder: "请选择"
                                                            },
                                                        },
                                                        form: {
                                                            type: 'date',
                                                            placeholder: '请选择',
                                                            addShow: false,
                                                            editDisabled: true,
                                                            hide: (obj) => {
                                                                if (obj ?.initialValues ?.technologicalAchievementsName === '专利') {
                                                                    return true;
                                                                } else {
                                                                    return false;
                                                                }
                                                            },
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInSearch: true,
                                                        isInTable:false,
                                                        isInForm:false,
                                                        form: {
                                                            label:'发布时间',
                                                            field:'planTime',
                                                            type: 'rangeDate',
                                                            placeholder: '请选择',
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInTable: false,
                                                        table: {
                                                            title: '专利号',
                                                            dataIndex: 'patentNo',
                                                            key: 'patentNo',
                                                            onClick: 'detail',
                                                            width: 150
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            required: true,
                                                            hide: (obj) => {
                                                                if (obj ?.initialValues ?.technologicalAchievementsName === '专利') {
                                                                    return false;
                                                                } else {
                                                                    return true;
                                                                }
                                                            },
                                                            placeholder: '请输入',
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInTable: false,
                                                        table: {
                                                            title: '专利名称',
                                                            dataIndex: 'patentName',
                                                            key: 'patentName',
                                                            width: 150
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            required: true,
                                                            hide: (obj) => {
                                                                if (obj ?.initialValues ?.technologicalAchievementsName === '专利') {
                                                                    return false;
                                                                } else {
                                                                    return true;
                                                                }
                                                            },
                                                            placeholder: '请输入',
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInTable: false,
                                                        table: {
                                                            title: '专利类型',
                                                            dataIndex: 'patentTypeName',
                                                            key: 'patentTypeName',
                                                            width: 100
                                                        },
                                                        form: {
                                                            field: 'patentTypeId',
                                                            type: 'select',
                                                            optionConfig: {
                                                                label: 'itemName',
                                                                value: 'itemId',
                                                            },
                                                            fetchConfig: {
                                                                apiName: 'getBaseCodeSelect',
                                                                otherParams: {
                                                                    itemId: 'zhuanLiLeiXing'
                                                                }
                                                            },
                                                            required: true,
                                                            hide: (obj) => {
                                                                if (obj ?.initialValues ?.technologicalAchievementsName === '专利') {
                                                                    return false;
                                                                } else {
                                                                    return true;
                                                                }
                                                            },
                                                            placeholder: '请选择',
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInTable: false,
                                                        table: {
                                                            title: '授权时间',
                                                            dataIndex: 'authorizationTime',
                                                            key: 'authorizationTime',
                                                            width: 100,
                                                            format: 'YYYY-MM-DD'
                                                        },
                                                        form: {
                                                            field: 'authorizationTime',
                                                            type: 'date',
                                                            required: true,
                                                            hide: (obj) => {
                                                                if (obj ?.initialValues ?.technologicalAchievementsName === '专利') {
                                                                    return false;
                                                                } else {
                                                                    return true;
                                                                }
                                                            },
                                                            placeholder: '请选择',
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInTable: false,
                                                        table: {
                                                            title: '申请单位',
                                                            dataIndex: 'applicant',
                                                            key: 'applicant',
                                                            width: 100,
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            required: true,
                                                            hide: (obj) => {
                                                                if (obj ?.initialValues ?.technologicalAchievementsName === '专利') {
                                                                    return false;
                                                                } else {
                                                                    return true;
                                                                }
                                                            },
                                                            placeholder: '请输入',
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInTable: false,
                                                        table: {
                                                            title: '专利权人',
                                                            dataIndex: 'patentee',
                                                            key: 'patentee',
                                                            width: 100,
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            required: true,
                                                            hide: (obj) => {
                                                                if (obj ?.initialValues ?.technologicalAchievementsName === '专利') {
                                                                    return false;
                                                                } else {
                                                                    return true;
                                                                }
                                                            },
                                                            placeholder: '请输入',
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInTable: false,
                                                        form: {
                                                            label: '附件',
                                                            field: 'attachmentList',
                                                            type: 'files',
                                                            fetchConfig: {
                                                                apiName: window.configs.domain + 'upload'
                                                            },
                                                            formItemLayout: {
                                                                labelCol: {
                                                                    xs: { span: 3 },
                                                                    sm: { span: 3 }
                                                                },
                                                                wrapperCol: {
                                                                    xs: { span: 21 },
                                                                    sm: { span: 21 }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '阅读量',
                                                            dataIndex: 'readNum',
                                                            key: 'readNum',
                                                            width: 80,
                                                            fieldsConfig: {
                                                                type: "number",
                                                                placeholder: "请输入"
                                                            }
                                                        },
                                                        isInForm: false
                                                    }
                                                ]}
                                                method={{
                                                    editOclick: (obj) => {
                                                        obj.btnCallbackFn.clearSelectedRows();
                                                    },
                                                    importOclick: () => {
                                                        this.setState({
                                                            visible: true
                                                        })
                                                    },
                                                    importsOnClick: () => {
                                                        confirm({
                                                            content: '确定下载导入模板么?',
                                                            centered: true,
                                                            onOk: () => {
                                                                window.location.href = 'http://10.15.51.190/apiJsfw/upload/科技信息导入模板.xlsx';
                                                            }
                                                        });
                                                    },
                                                    buttonHide: () => {
                                                        return item.itemName === '专利' ? false : true;
                                                    }
                                                }}
                                            />
                                        </div>}
                                </TabPane>
                            )
                        })
                    }
                </Tabs> : null}
                {visible ? <div>
                    <Modal
                        width={'500px'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="导入"
                        visible={visible}
                        footer={null}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'replyData'}
                    >
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }}
                            formConfig={[
                                {
                                    field: 'deptId',
                                    type: 'string',
                                    initialValue: companyId,
                                    placeholder: '请输入',
                                    hide: true,
                                },
                                {
                                    field: 'deptName',
                                    type: 'string',
                                    initialValue: companyName,
                                    placeholder: '请输入',
                                    hide: true,
                                },
                                {
                                    label: '附件',
                                    field: 'attachmentList',
                                    type: 'files',
                                    required: true,
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload'
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                    field: 'cancel',
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
                                    field: 'submit',
                                    onClick: (obj) => {
                                        obj.values.searchType = '0';
                                        obj.btnfns.fetchByCb('importZjSjConsultScientificInformation', obj.values, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.table3.refresh();
                                                this.setState({ visible: false });
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
            </div>
        );
    }
}

export default index;