import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from '../modules/qnn-table/qnn-form';
import { message as Msg, Modal } from 'antd';
import moment from 'moment';
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: function (row) {
            return row.topicId
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
            visible: false
        }
    }
    render() {
        const { visible } = this.state;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={{
                        apiName: 'getZjSjConsultScientificResearchTopicList'
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: function (obj) {
                            var props = obj.Pprops;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "ScientificKT"
                            }
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'topicId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                                title: '课题编号',
                                dataIndex: 'topicNo',
                                key: 'topicNo',
                                width: 150,
                                tooltip: 200,
                                fixed: 'left',
                            },
                            form: {
                                type: "string",
                                placeholder: "请输入"
                            }
                        },
                        {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                                title: '课题名称',
                                dataIndex: 'topicName',
                                key: 'topicName',
                                width: 150,
                                tooltip: 200,
                                fixed: 'left',
                                onClick: 'detail'
                            },
                            form: {
                                type: "string",
                                placeholder: "请输入"
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '课题名称',
                                field: 'topicName',
                                type: "string",
                                required: true,
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
                            isInTable: false,
                            form: {
                                label: '课题编号',
                                field: 'topicNo',
                                type: "string",
                                required: true,
                                placeholder: "请输入",
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '课题类型',
                                field: 'topicTypeId',
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'keTiLeiXing'
                                    }
                                },
                                required: true,
                                placeholder: '请选择',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInSearch: true,
                            isInForm: false,
                            table: {
                                title: '承担单位',
                                dataIndex: 'undertakingUnitName',
                                key: 'undertakingUnitName',
                                width: 150,
                                tooltip: 200,
                                fieldsConfig: {
                                    type: 'string',
                                    placeholder: '请输入'
                                },
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '承担单位',
                                field: 'undertakUnitNameList',
                                type: 'select',
                                mode: "tags",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemName',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'chengDanDanWei'
                                    }
                                },
                                onChange: (vals) => {
                                    if (vals.length) {
                                        let endVals = [vals[vals.length - 1]];
                                        this.table.qnnForm.form.setFieldsValue({ undertakUnitNameList: endVals });
                                    } else {
                                        this.table.qnnForm.form.setFieldsValue({ undertakUnitNameList: [] });
                                    }
                                },
                                required: true,
                                placeholder: '请选择',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                                title: '参与单位',
                                dataIndex: 'joinUnitName',
                                key: 'joinUnitName',
                                width: 100,
                                tooltip: 200,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '参与单位',
                                field: 'joinUnitNameList',
                                type: 'select',
                                mode: "tags",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemName',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'canYuDanWei'
                                    }
                                },
                                onChange: (vals) => {
                                    if (vals.length) {
                                        this.table.qnnForm.form.setFieldsValue({ joinUnitNameList: vals });
                                    } else {
                                        this.table.qnnForm.form.setFieldsValue({ joinUnitNameList: [] });
                                    }
                                },
                                placeholder: '请选择',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                                title: '立项批准单位',
                                dataIndex: 'approvalUnitName',
                                key: 'approvalUnitName',
                                width: 150,
                                tooltip: 200,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '立项批准单位',
                                field: 'approvalUnitNameList',
                                type: 'select',
                                mode: "tags",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemName',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'liXiangPiZhunDanWei'
                                    }
                                },
                                initialValue: [],
                                placeholder: '请选择',
                                onChange: (vals) => {
                                    if (vals.length) {
                                        let endVals = [vals[vals.length - 1]];
                                        this.table.qnnForm.form.setFieldsValue({ approvalUnitNameList: endVals });
                                    } else {
                                        this.table.qnnForm.form.setFieldsValue({ approvalUnitNameList: [] });
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
                            table: {
                                title: '负责人',
                                dataIndex: 'responsiblePerson',
                                key: 'responsiblePerson',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '联系方式',
                                field: 'contactMode',
                                type: 'phone',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '参与人员',
                                field: 'joinPersonList',
                                type: 'select',
                                required: true,
                                mode: "tags",
                                optionData: [{ label: '请手动输入', value: '请手动输入' }],
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
                            isInForm: false,
                            table: {
                                title: '课题类型',
                                dataIndex: 'topicTypeName',
                                key: 'topicTypeName',
                                width: 100,
                                tooltip: 200,
                            },
                        },
                        {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                                title: '研发计划时间',
                                dataIndex: 'planTime',
                                key: 'planTime',
                                width: 200,
                                render: (data) => {
                                    if (data && data.length) {
                                        return moment(data[0]).format('YYYY-MM-DD') + '~' + moment(data[1]).format('YYYY-MM-DD')
                                    }
                                    return '';
                                }
                            },
                            form: {
                                type: 'rangeDate',
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '经费预算(万元)',
                                dataIndex: 'budget',
                                key: 'budget',
                                width: 130,
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                spanSearch: 6,
                                formItemLayoutSearch: {
                                    labelCol: {
                                        xs: { span: 8 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 16 },
                                        sm: { span: 16 }
                                    }
                                },
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInSearch: true,
                            isInForm: false,
                            isInTable: false,
                            form: {
                                field: 'symbol',
                                type: 'select',
                                placeholder: '请输入',
                                spanSearch: 2,
                                optionData: [//默认选项数据//可为function (props)=>array
                                    {
                                        label: '>',
                                        value: '0'
                                    },
                                    {
                                        label: '<',
                                        value: '1'
                                    },
                                    {
                                        label: '=',
                                        value: '2'
                                    },
                                    {
                                        label: '>=',
                                        value: '3'
                                    },
                                    {
                                        label: '<=',
                                        value: '4'
                                    }
                                ],
                                formItemLayoutSearch: {
                                    labelCol: {
                                        xs: { span: 0 },
                                        sm: { span: 0 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '局拨款金额(万元)',
                                field: 'bureauAppropriationAmount',
                                type: 'number',
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '研发计划时间',
                                field: 'planTime',
                                type: 'rangeDate',
                                required: true,
                                format: "YYYY-MM-DD",
                                placeholder: '请选择',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 3 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 9 },
                                        sm: { span: 9 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: "qnnForm",
                                field: "progressList",
                                label: "进度执行情况",
                                textObj: {
                                    add: "添加",
                                    del: "删除"
                                },
                                canAddForm: true,
                                qnnFormConfig: {
                                    formConfig: [
                                        {
                                            label: '报表期次',
                                            type: 'select',
                                            field: 'yearId',
                                            placeholder: '请选择',
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'year'
                                                }
                                            },
                                            optionConfig: {
                                                label: 'itemName',
                                                value: 'itemId',
                                            },
                                            span: 6,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 13 },
                                                    sm: { span: 13 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 11 },
                                                    sm: { span: 11 }
                                                }
                                            },
                                        },
                                        {
                                            type: 'component',
                                            field: 'yearS',
                                            Component: obj => {
                                                return (
                                                    <div style={{ height: '32px', lineHeight: '32px', margin: '12px 0' }}>
                                                        年
                                                    </div>
                                                );
                                            },
                                            span: 1,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 0 },
                                                    sm: { span: 0 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 24 }
                                                }
                                            },
                                        },
                                        {
                                            type: 'select',
                                            field: 'quarterId',
                                            placeholder: '请选择',
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'quarter'
                                                }
                                            },
                                            optionConfig: {
                                                label: 'itemName',
                                                value: 'itemId',
                                            },
                                            span: 3,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 0 },
                                                    sm: { span: 0 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                }
                                            },
                                        },
                                        {
                                            type: 'component',
                                            field: 'quarterS',
                                            Component: obj => {
                                                return (
                                                    <div style={{ height: '32px', lineHeight: '32px', margin: '12px 0' }}>
                                                        季度
                                                    </div>
                                                );
                                            },
                                            span: 14,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 0 },
                                                    sm: { span: 0 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 24 }
                                                }
                                            },
                                        },
                                        {
                                            label: '本季度计划',
                                            type: 'textarea',
                                            field: 'planForTheQuarter',
                                            placeholder: '请输入',
                                            span: 12,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                        },
                                        {
                                            label: '本季度完成',
                                            type: 'textarea',
                                            field: 'completedThisQuarter',
                                            placeholder: '请输入',
                                            span: 12,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                        },
                                        {
                                            label: '未完成原因',
                                            type: 'textarea',
                                            field: 'unfinishedReason',
                                            placeholder: '请输入',
                                            span: 12,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                        },
                                        {
                                            label: '下季度计划',
                                            type: 'textarea',
                                            field: 'nextQuarterPlan',
                                            placeholder: '请输入',
                                            span: 12,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                        }
                                    ]
                                }
                            }
                        },
                    ]}
                    method={{
                        editOclick:(obj) => {
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        importOclick:() => {
                            this.setState({
                                visible: true
                            })
                        },
                        importsOnClick: () => {
                            confirm({
                                content: '确定下载导入模板么?',
                                centered: true,
                                onOk: () => {
                                    window.location.href = 'http://10.15.51.190/apiJsfw/upload/科研课题导入模板，标题行勿动.xlsx';
                                }
                            });
                        },
                        exportOclick:(obj) => {
                            if (obj.selectedRows.length) {
                                obj.btnCallbackFn.fetchByCb('exportZjSjConsultScientificResearchTopic', obj.selectedRows, ({ data, success, message }) => {
                                    if (success) {
                                        window.location.href = data;
                                    } else {
                                        Msg.error(message);
                                    }
                                });
                            } else {
                                Msg.error('请选择导出数据！');
                            }
                        },
                    }}
                />
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
                                        obj.btnfns.fetchByCb('importZjSjConsultScientificResearchTopic', obj.values, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                if (this.table) {
                                                    this.table.refresh();
                                                }
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