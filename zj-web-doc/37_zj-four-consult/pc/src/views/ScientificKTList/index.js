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
                                title: '????????????',
                                dataIndex: 'topicNo',
                                key: 'topicNo',
                                width: 150,
                                tooltip: 200,
                                fixed: 'left',
                            },
                            form: {
                                type: "string",
                                placeholder: "?????????"
                            }
                        },
                        {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                                title: '????????????',
                                dataIndex: 'topicName',
                                key: 'topicName',
                                width: 150,
                                tooltip: 200,
                                fixed: 'left',
                                onClick: 'detail'
                            },
                            form: {
                                type: "string",
                                placeholder: "?????????"
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'topicName',
                                type: "string",
                                required: true,
                                placeholder: "?????????",
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
                                label: '????????????',
                                field: 'topicNo',
                                type: "string",
                                required: true,
                                placeholder: "?????????",
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
                                label: '????????????',
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
                                placeholder: '?????????',
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
                                title: '????????????',
                                dataIndex: 'undertakingUnitName',
                                key: 'undertakingUnitName',
                                width: 150,
                                tooltip: 200,
                                fieldsConfig: {
                                    type: 'string',
                                    placeholder: '?????????'
                                },
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
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
                                placeholder: '?????????',
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
                                title: '????????????',
                                dataIndex: 'joinUnitName',
                                key: 'joinUnitName',
                                width: 100,
                                tooltip: 200,
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
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
                                placeholder: '?????????',
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
                                title: '??????????????????',
                                dataIndex: 'approvalUnitName',
                                key: 'approvalUnitName',
                                width: 150,
                                tooltip: 200,
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????????????????',
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
                                placeholder: '?????????',
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
                                title: '?????????',
                                dataIndex: 'responsiblePerson',
                                key: 'responsiblePerson',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
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
                                label: '????????????',
                                field: 'contactMode',
                                type: 'phone',
                                required: true,
                                placeholder: '?????????',
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
                                label: '????????????',
                                field: 'joinPersonList',
                                type: 'select',
                                required: true,
                                mode: "tags",
                                optionData: [{ label: '???????????????', value: '???????????????' }],
                                placeholder: '?????????',
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
                                title: '????????????',
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
                                title: '??????????????????',
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
                                placeholder: '?????????'
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '????????????(??????)',
                                dataIndex: 'budget',
                                key: 'budget',
                                width: 130,
                            },
                            form: {
                                type: 'number',
                                placeholder: '?????????',
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
                                placeholder: '?????????',
                                spanSearch: 2,
                                optionData: [//??????????????????//??????function (props)=>array
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
                                label: '???????????????(??????)',
                                field: 'bureauAppropriationAmount',
                                type: 'number',
                                placeholder: '?????????',
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
                                label: '??????????????????',
                                field: 'planTime',
                                type: 'rangeDate',
                                required: true,
                                format: "YYYY-MM-DD",
                                placeholder: '?????????',
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
                                label: "??????????????????",
                                textObj: {
                                    add: "??????",
                                    del: "??????"
                                },
                                canAddForm: true,
                                qnnFormConfig: {
                                    formConfig: [
                                        {
                                            label: '????????????',
                                            type: 'select',
                                            field: 'yearId',
                                            placeholder: '?????????',
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
                                                        ???
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
                                            placeholder: '?????????',
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
                                                        ??????
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
                                            label: '???????????????',
                                            type: 'textarea',
                                            field: 'planForTheQuarter',
                                            placeholder: '?????????',
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
                                            label: '???????????????',
                                            type: 'textarea',
                                            field: 'completedThisQuarter',
                                            placeholder: '?????????',
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
                                            label: '???????????????',
                                            type: 'textarea',
                                            field: 'unfinishedReason',
                                            placeholder: '?????????',
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
                                            label: '???????????????',
                                            type: 'textarea',
                                            field: 'nextQuarterPlan',
                                            placeholder: '?????????',
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
                                content: '????????????????????????????',
                                centered: true,
                                onOk: () => {
                                    window.location.href = 'http://10.15.51.190/apiJsfw/upload/??????????????????????????????????????????.xlsx';
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
                                Msg.error('????????????????????????');
                            }
                        },
                    }}
                />
                {visible ? <div>
                    <Modal
                        width={'500px'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="??????"
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
                            upload={this.props.myUpload} //??????????????????promise
                            //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
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
                                    label: '??????',
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
                                    label: "??????",
                                    field: 'cancel',
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "??????",
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