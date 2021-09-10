import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Button } from 'antd';
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.tableQD = props.tableQD;
        this.refresh = props.refreshs;
        this.state = {
            QDFlagData:props.QDFlagData || {}
        }
    }
    render() {
        let { myPublic: { domain } } = this.props;
        return (
            <div>
                {this.tableQD?.rowInfo?.name === 'export' ? <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    wrappedComponentRef={(me) => {
                        this.form = me;
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
                            field: 'workBookID',
                            type: 'string',
                            initialValue: this.state.QDFlagData?.id,
                            placeholder: '请输入',
                            hide: true,
                        },
                        {
                            label: '清单类型',
                            field: 'inputWorkType',
                            type: 'select',
                            optionConfig: {
                                label: 'label',
                                value: 'value'
                            },
                            allowClear: false,
                            initialValue: '0',
                            optionData: [
                                {
                                    label: '公路清单',
                                    value: '0'
                                },
                                {
                                    label: '铁路清单',
                                    value: '1'
                                },
                                {
                                    label: '市政清单',
                                    value: '2'
                                },
                                {
                                    label: '房建清单',
                                    value: '3'
                                }
                            ],
                            placeholder: '请选择',
                            required: true
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ paddingLeft: '16.66666667%' }}><Button type="primary" onClick={() => {
                                        let formData = obj.form.getFieldsValue();
                                        if (formData.inputWorkType === '0') {
                                            confirm({
                                                content: '确定下载公路清单模板吗?',
                                                onOk: () => {
                                                    window.open(`${domain}system-server/downloadFile?filePath=import/【业主合同台账】模块清单导入模板-公路清单.xls&downName=【业主合同台账】模块清单导入模板-公路清单.xls`);
                                                }
                                            });
                                        } else if (formData.inputWorkType === '1') {
                                            confirm({
                                                content: '确定下载铁路清单模板吗?',
                                                onOk: () => {
                                                    window.open(`${domain}system-server/downloadFile?filePath=import/【业主合同台账】模块清单导入模板-铁路清单.xls&downName=【业主合同台账】模块清单导入模板-铁路清单.xls`);
                                                }
                                            });
                                        } else if (formData.inputWorkType === '2') {
                                            confirm({
                                                content: '确定下载市政清单模板吗?',
                                                onOk: () => {
                                                    window.open(`${domain}system-server/downloadFile?filePath=import/【业主合同台账】模块清单导入模板-市政清单.xls&downName=【业主合同台账】模块清单导入模板-市政清单.xls`);
                                                }
                                            });
                                        } else if (formData.inputWorkType === '3') {
                                            confirm({
                                                content: '确定下载房建清单模板吗?',
                                                onOk: () => {
                                                    window.open(`${domain}system-server/downloadFile?filePath=import/【业主合同台账】模块清单导入模板-房建清单.xls&downName=【业主合同台账】模块清单导入模板-房建清单.xls`);
                                                }
                                            });
                                        }
                                    }}>导入模板下载</Button></div>
                                );
                            }
                        },
                        {
                            label: '附件',
                            field: 'attachment',
                            type: 'files',
                            required: true,
                            max: 1,
                            fetchConfig: {
                                apiName: 'upload'
                            }
                        }
                    ]}
                    btns={[
                        {
                            name: "submit",
                            type: "primary",
                            label: "导入",
                            field: 'submit',
                            onClick: (obj) => {
                                confirm({
                                    content: '确定导入数据吗?',
                                    onOk: () => {
                                        obj.btnfns.fetchByCb('importZxCtWorks', obj.values, (exportObj) => {
                                            if (exportObj.success) {
                                                Msg.success(exportObj.message);
                                                this.tableQD.rowInfo = null;
                                                this.tableQD.closeDrawer(false);
                                                this.refresh();
                                            } else {
                                                Msg.error(exportObj.message);
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    ]}
                /> : null}
            </div>
        );
    }
}

export default index;