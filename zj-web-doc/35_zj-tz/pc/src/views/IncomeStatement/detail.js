import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import s from "./style.less";
import { goBack } from "connected-react-router";
import { message as Msg, Modal, Spin } from "antd";
const config = {
    antd: {
        rowKey: 'profitExcelId',
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.65
        }
    },
    drawerConfig: {
        width: '1100px'
    },
    limit: 99999,
    curPage: 1,
    paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: false,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            profitId: props.match.params.profitId || '',
            visibleSend: false,
            loadingSend: false,
            apih5FlowStatus: props.match.params.apih5FlowStatus || '',
            isWorkId: props.match.params.isWorkId || ''
        }
    }

    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    render() {
        const { profitId, visibleSend, loadingSend, apih5FlowStatus } = this.state;
        let { myPublic: { domain } } = this.props;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig={{
                        apiName: 'getZjTzProfitExcelList',
                        otherParams: () => {
                            if (this.state.isWorkId === '1') {
                                return {
                                    workId: profitId
                                }
                            } else {
                                return {
                                    profitId: profitId
                                }
                            }
                        }
                    }}
                    wrappedComponentRef={(me) => {
                        this.tableGM = me;
                    }}
                    method={{
                        importClick: (obj) => {
                            this.setState({
                                visibleSend: true
                            });
                        },
                        goBack: (obj) => {
                            obj.props.dispatch(goBack())
                        },
                        hideClick: () => {
                            if (apih5FlowStatus === '1' || apih5FlowStatus === '2') {
                                return true
                            } else {
                                return false
                            }
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
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'profitExcelId',
                                type: 'string',
                                hide: true,
                            }
                        },

                        {
                            isInTable: false,
                            form: this.state.isWorkId === '1' ? {
                                label: '??????id',
                                field: 'workId',
                                initialValue: profitId,
                                hide: true
                            } : {
                                    label: '??????id',
                                    field: 'profitId',
                                    initialValue: profitId,
                                    hide: true
                                }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'ext11',
                                key: 'ext11'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 80,
                                dataIndex: 'ext12',
                                key: 'ext12',
                                align: 'center',
                                defaultSortOrder: 'descend'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                dataIndex: 'ext13',
                                key: 'ext13'
                            },
                            isInForm: false,
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'ext14',
                                key: 'ext14'
                            },
                            isInForm: false
                        },

                    ]}
                />
                <Modal
                    width='500px'
                    style={{ top: '0' }}
                    title="??????"
                    visible={visibleSend}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '500px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelSend}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSend}>
                        <div style={{ marginLeft: '15px' }} onClick={() => {
                            window.open(domain + 'upload/template/zjtz/profit.xlsx')
                        }}>???????????????<a>?????????</a></div>
                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            formConfig={[

                                {
                                    label: '??????',
                                    field: 'fileList',
                                    required: true,
                                    type: 'files',
                                    showDownloadIcon: true,//????????????????????????
                                    onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                    
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload',
                                        otherParams: {
                                            name: '?????????'
                                        }
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '??????',
                                    isValidate: false,
                                    onClick: () => {
                                        this.setState({
                                            visibleSend: false,
                                            loadingSend: false
                                        })
                                    }
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '??????',
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingSend: true
                                        })
                                        if (this.state.isWorkId === '1') {
                                            obj.values.workId = profitId;
                                        } else {
                                            obj.values.profitId = profitId;
                                        }
                                        this.props.myFetch('batchImportZjTzProfitExcel', obj.values).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.tableGM.refresh();
                                                    this.setState({
                                                        visibleSend: false,
                                                        loadingSend: false
                                                    })
                                                } else {
                                                    Msg.error(message);
                                                    this.tableGM.refresh();
                                                    this.setState({
                                                        visibleSend: false,
                                                        loadingSend: false
                                                    })
                                                }
                                            }
                                        );

                                    }
                                }
                            ]}
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