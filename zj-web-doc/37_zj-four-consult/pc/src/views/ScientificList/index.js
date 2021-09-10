import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { Tag, message as Msg, Tabs, Tooltip } from 'antd';
import s from './style.less';
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
        }
    }
    componentDidMount() {
        this.props.myFetch('getBaseCodeSelect', { itemId: 'wenTiLeiXing' }).then(({ success, message, data }) => {
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
        const { TabsData, key } = this.state;
        const { companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0];
        return (
            <div>
                {TabsData.length && TabsData.length > 1 ? <Tabs activeKey={key} onChange={this.callback}>
                    {
                        TabsData.map((item, index) => {
                            return (
                                <TabPane tab={item.itemName} key={index + 1}>
                                    <div style={{ height: window.innerHeight - 61 }}>
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
                                                    technologyIndustryFlag: '1',
                                                    industryScientificInfoId: item.itemId
                                                }
                                            }}
                                            actionBtns={{
                                                apiName: "getSysMenuBtn",
                                                otherParams: function (obj) {
                                                    var props = obj.Pprops;
                                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                                    return {
                                                        menuParentId: curRouteData._value,
                                                        tableField: "Scientific"
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
                                                        initialValue: '1',
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
                                                        onClick: 'detail',
                                                    },
                                                    form: {
                                                        type: "string",
                                                        addShow: false,
                                                        editDisabled: true,
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
                                                        title: '科技类型',
                                                        dataIndex: 'industryScientificInfoName',
                                                        key: 'industryScientificInfoName',
                                                        width: 100,
                                                        fieldsConfig: {
                                                            type: 'select',
                                                            field: 'industryScientificInfoId',
                                                            placeholder: '请选择',
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'wenTiLeiXing'
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
                                                        field: 'industryScientificInfoId',
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId',
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getBaseCodeSelect',
                                                            otherParams: {
                                                                itemId: 'wenTiLeiXing'
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
                                                        title: '科技标题',
                                                        dataIndex: 'title',
                                                        key: 'title',
                                                        width: 150,
                                                        tooltip: 200
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
                                                        label: '内容',
                                                        field: 'content',
                                                        type: 'richtext',
                                                        fetchConfig: {
                                                            //必须配置  上传图片地址
                                                            uploadUrl: window.configs.domain + 'upload' //***必传
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
                                                        }
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请选择',
                                                        addShow: false,
                                                        editDisabled: true,
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
                                                },
                                                // {
                                                //     isInForm: false,
                                                //     table: {
                                                //         title: "操作",
                                                //         dataIndex: 'action',
                                                //         key: 'action',
                                                //         align: "center",
                                                //         width: 80,
                                                //         showType: "tile",
                                                //         fixed:'right',
                                                //         btns: [
                                                //             {
                                                //                 name: 'edit',
                                                //                 render: function (rowData) {
                                                //                     return '<a>修改</a>';
                                                //                 },
                                                //                 formBtns: [
                                                //                     {
                                                //                         name: 'cancel',
                                                //                         type: 'dashed',
                                                //                         label: '取消',
                                                //                     },
                                                //                     {
                                                //                         name: 'submit',
                                                //                         type: 'primary',
                                                //                         label: '提交',
                                                //                         fetchConfig: {
                                                //                             apiName: 'updateZjSjConsultScientificInformation',
                                                //                         }
                                                //                     }
                                                //                 ]
                                                //             }
                                                //         ]
                                                //     }
                                                // }
                                            ]}
                                            method={{
                                                editOclick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            }}
                                        />
                                    </div>
                                </TabPane>
                            )
                        })
                    }
                </Tabs> : null}
            </div>
        );
    }
}

export default index;