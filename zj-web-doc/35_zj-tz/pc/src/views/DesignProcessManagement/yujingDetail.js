import React, { Component } from "react";
import { push } from "react-router-redux";
import s from "./style.less";
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Checkbox, Tag, Spin } from "antd";
import HasTicketMoneyForm from './hasTicketMoneyForm';

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            designFlowId: props.match.params.designFlowId || '',
            partManageId: '',
            fujianLook: [],
            rowDataList: [],
            visibleSend: false,
            loadingSend: false,
        }
    }
    componentDidMount() { }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    render() {
        const { designFlowId, visibleSend, loadingSend } = this.state;
        let me = this;
        return (
            <div className={s.root}>
                <QnnForm
                    {...this.props}
                    fetchConfig={{
                        apiName: 'getZjTzPartManageList',
                        otherParams: {
                            designFlowId: designFlowId
                        }
                    }}
                    upload={this.props.myUpload}
                    fetch={this.props.myFetch}
                    wrappedComponentRef={(me) => {
                        this.tableGM = me;
                    }}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    formConfig={[
                        {
                            label: '主键id',
                            field: 'codePid',
                            hide: true
                        },
                        {
                            label: '外层主键id',
                            field: 'designFlowId',
                            hide: true
                        },
                        {
                            type: 'qnnTable',
                            field: 'zjTzPartManageList',
                            incToForm: true,
                            qnnTableConfig: {
                                actionBtnsPosition: "top",
                                antd: {
                                    rowKey: 'partManageId',
                                    size: 'small',
                                    scroll: {
                                        y: document.documentElement.clientHeight * 0.65
                                    }
                                },
                                drawerConfig: {
                                    width: '1000px'
                                },
                                limit: 999,
                                curPage: 1,
                                paginationConfig: false,
                                firstRowIsSearch: false,
                                isShowRowSelect: true,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 20 }
                                    }
                                },
                                componentsKey: {
                                    HasTicketMoneyForm: (obj) => {
                                        return <HasTicketMoneyForm refreshOut={this.tableGM.refresh} {...obj} {...this.props} designFlowId={designFlowId} />
                                    }
                                },
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'partManageId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'designFlowId',
                                            type: 'string',
                                            initialValue: designFlowId,
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '序号',
                                            dataIndex: 'orderNum',
                                            key: 'orderNum',
                                            width: 60,
                                            align: 'center',
                                            defaultSortOrder: 'descend',
                                            fixed: 'left',
                                            render: (data, rowData) => {
                                                if (rowData.colorFlag === 'white') {
                                                    return data
                                                } else {
                                                    return (
                                                        <Tag style={{ width: '100%', textAlign: 'center' }} color={rowData.colorFlag} key={data}>
                                                            {data}
                                                        </Tag>
                                                    )
                                                }

                                            }
                                        },
                                        isInForm: false,
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            required: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '设计环节名称',
                                            onClick: 'detail',
                                            dataIndex: 'designPartName',
                                            key: 'designPartName',
                                            tdEdit: false
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            required: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '中标时环节',
                                            dataIndex: 'bidPartName',
                                            align: 'center',
                                            width: 120,
                                            key: 'bidPartName',
                                            tdEdit: false,
                                            render: (data, rowData) => {
                                                if (rowData.bidPartId === '0') {
                                                    return <Checkbox disabled checked={false}></Checkbox>
                                                } else {
                                                    return <Checkbox disabled checked={true}></Checkbox>
                                                }
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '重点环节',
                                            dataIndex: 'buName',
                                            key: 'buName',
                                            width: 120,
                                            align: 'center',
                                            tdEdit: false,
                                            render: (data, rowData) => {
                                                if (rowData.buId === '0') {
                                                    return <Checkbox disabled checked={false}></Checkbox>
                                                } else {
                                                    return <Checkbox disabled checked={true}></Checkbox>
                                                }
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '计划完成日期',
                                            dataIndex: 'planDate',
                                            key: 'planDate',
                                            tdEdit: false,
                                            width: 140,
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            addDisabled: true,
                                            editDisabled: true,
                                            field: 'planDate',
                                            placeholder: '请选择'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '实际完成日期',
                                            format: 'YYYY-MM-DD',
                                            dataIndex: 'actualDate',
                                            width: 140,
                                            key: 'actualDate',
                                            tdEdit: false
                                        },
                                        form: {
                                            type: 'date',
                                            field: 'actualDate',
                                            placeholder: '请选择'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '锁定状态',
                                            dataIndex: 'lockFlag',
                                            width: 100,
                                            key: 'lockFlag',
                                            render: (data) => {
                                                if (data === '0') {
                                                    return '未锁定'
                                                } else {
                                                    return '锁定'
                                                }
                                            }
                                        },
                                        isInForm: false,
                                    },
                                    {
                                        table: {
                                            title: '证明材料附件',
                                            dataIndex: 'fileFlag',
                                            width: 140,
                                            key: 'fileFlag',
                                            // tdEdit:true,
                                            render: (data, rowData) => {
                                                if (data) {
                                                    if (data === '0') {
                                                        return '未上传'
                                                    } else {
                                                        return '已上传'
                                                    }
                                                } else {
                                                    return ''
                                                }
                                            },
                                            onClick: (obj) => {
                                                this.setState({
                                                    partManageId: obj.rowData.partManageId,
                                                    fujianLook: obj.rowData.zjTzFileList,
                                                    rowDataList: obj.rowData
                                                }, () => {
                                                    if (obj.rowData.zjTzFileList.length > 0) {
                                                        this.setState({
                                                            visibleSend: true,
                                                        })
                                                    } else {
                                                        Msg.warn('当前数据没有上传附件！')
                                                    }
                                                })
                                            },
                                        },
                                        form: {
                                            field: 'zjTzFileList',
                                            type: 'files',
                                            fetchConfig: {
                                                apiName: window.configs.domain + 'upload'
                                            },
                                            showDownloadIcon: true,//是否显示下载按钮
                                            onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                            
                                            onChange: (val, rowData) => {
                                                if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                    Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                                    setTimeout(() => {
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            zjTzFileList: []
                                                        })
                                                    }, 200)
                                                }
                                            }
                                        }
                                    }
                                ],
                                actionBtns: [
                                    {
                                        name: 'goback',
                                        type: 'dashed',
                                        label: '返回',
                                        isValidate: false,
                                        onClick: (obj) => {
                                            const { mainModule } = this.props.myPublic.appInfo;
                                            this.props.dispatch(
                                                push(`${mainModule}DesignProcessManagement`)
                                            )
                                        }
                                    }
                                ]
                            }
                        }
                    ]}
                    btns={[

                    ]}
                />
                <Modal
                    width='800px'
                    style={{ top: '0' }}
                    title="证明材料"
                    visible={visibleSend}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '800px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelSend}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSend}>
                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            formConfig={[
                                {
                                    type: "qnnTable",
                                    field: "qnnTableKP",
                                    qnnTableConfig: {
                                        data: me.state.fujianLook,
                                        drawerConfig: {
                                            width: "800px"
                                        },
                                        actionBtnsPosition: "top",
                                        antd: {
                                            rowKey: function (row) {
                                                return row.partManageId;
                                            },
                                            size: 'small'
                                        },
                                        paginationConfig: {
                                            position: 'bottom'
                                        },
                                        firstRowIsSearch: false,
                                        isShowRowSelect: false,
                                        wrappedComponentRef: (me) => {
                                            this.tableFJ = me;
                                        },
                                        actionBtns: [],
                                        formConfig: [
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: "partManageId",
                                                    initialValue: me.state.partManageId,
                                                    hide: true
                                                },
                                            },
                                            {
                                                table: {
                                                    title: '证明材料文件名',
                                                    dataIndex: 'name',
                                                    key: 'name'
                                                },
                                                isInForm: false,
                                            },
                                            {
                                                table: {
                                                    title: '附件',
                                                    dataIndex: 'url',
                                                    key: 'url',
                                                    render: (data) => {
                                                        return <div onClick={() => {
                                                            // window.location.href = data
                                                        }}><a target={'_blank'} href={data}>查看附件</a></div>
                                                    }
                                                },
                                                isInForm: false,
                                            }
                                        ]
                                    }
                                }
                            ]}
                            btns={[]}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 4 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            }}
                        />
                    </Spin>
                </Modal>

            </div>
        );
    }
}

export default index;