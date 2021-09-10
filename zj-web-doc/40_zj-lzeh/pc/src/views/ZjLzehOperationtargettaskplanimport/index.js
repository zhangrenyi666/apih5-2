//安全检查管理
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import { Modal, Button, Upload, message, } from 'antd';
import moment from "moment";
import { UploadOutlined } from '@ant-design/icons';
import download from "./download.js";
import styles from './index.min.css'
import { push } from "react-router-redux";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.zjLzehManageTaskPlanItemId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },

    firstRowIsSearch: false,
    isShowRowSelect: true,
    // paginationConfig: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
        }
    }
    componentDidMount() { }
    confirm = () => {
        const fileList = this.form.form.getFieldValue('fileList')
        if (fileList) {
            const parms = {
                fileList: fileList,
                zjLzehManageTaskPlanId: this.props.parent.primaryKey
            }
            this.props.parent.props.myFetch('importManageTaskPlanItem', parms).then(
                (success) => {
                    if (success) {
                        this.setState({ visible: false })
                        this.table.refresh()

                    } else {
                        message.warning('请求失败！');
                    }
                }
            );
        } else {
            message.warning('至少上传一个目标任务计划！');
        }

    };

    downloadTemplate = () => {
        var link = document.createElement("a");
        link.href = this.props.parent.props.myPublic.domain + "template/zj-lzeh/目标任务计划导入模版.xlsx";
        link.download = "目标任务计划导入模版.xlsx";
        link.click();
    }

    exportFilesFunc = async () => {
        const {
            loginAndLogoutInfo: {
                loginInfo: { token }
            },
            myPublic: { domain }
        } = this.props.parent.props;
        let params
        const fileName = `经营目标任务计划${moment().format('YYYYMMDD')}`
        const searchParams = this.table.getSearchParams()
        if (Object.keys(searchParams).length) {
            params = {
                ...searchParams,
                fileName
            }
        } else {
            params = {
                fileName
            }
        }

        confirm({
            content: '确定导出数据吗?',
            centered: true,
            onOk: () => {
                download(`${domain}exportManageTaskPlanItem`, params, { token })
            }
        });
    }

    render() {
        const {
            mainModule
        } = this.props.parent.props.myPublic.appInfo;
        return (
            <div>
                <div style={{ marginBottom: '12px' }}>
                    <Button type="primary" onClick={() => this.setState({ visible: true })} >
                        导入目标任务计划
                    </Button>
                    <Button type="primary" style={{ marginLeft: '8px' }} onClick={this.downloadTemplate} >
                        下载-导入目标任务计划模板
                    </Button>
                    <Button type="primary" style={{ marginLeft: '8px' }} onClick={this.exportFilesFunc} >
                        导出
                    </Button>
                    <Button type="primary" style={{ float: 'right' }} onClick={() => {
                        this.props.parent.getChildrenComShowStatus(this, true)
                    }} >
                        返回
                    </Button>
                </div>
                <Modal
                    title=" 导入目标任务计划"
                    centered
                    className={styles.rangePick}
                    visible={this.state.visible}
                    onOk={this.confirm}
                    onCancel={() => this.setState({ visible: false })}
                    width={500}
                >
                    <QnnForm
                        {...this.props.parent.props}
                        fetch={this.props.parent.props.myFetch}
                        upload={this.props.parent.props.myUpload}
                        headers={{ token: this.props.parent.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.form = me;
                        }}
                        formItemLayout={{
                            labelCol: {
                                xs: { span: 24 },
                                sm: { span: 8 }
                            },
                            wrapperCol: {
                                xs: { span: 24 },
                                sm: { span: 16 }
                            }
                        }}
                        formConfig={[
                            {
                                label: '导入目标任务计划',
                                field: "fileList",
                                type: 'files',
                                accept: '.xlsx,.xls',
                                max: 1
                            },
                        ]}
                    ></QnnForm>
                </Modal>
                <QnnTable
                    {...this.props.parent.props}
                    fetch={this.props.parent.props.myFetch}
                    headers={{ token: this.props.parent.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    method={{
                        getPrimaryKey: (obj) => {
                            return {
                                zjLzehManageTaskPlanId: this.props.parent.primaryKey
                            }
                        }
                    }}
                    fetchConfig={
                        {
                            apiName: 'getZjLzehManageTaskPlanItemList',
                            otherParams: 'bind:getPrimaryKey'
                        }
                    }
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zjLzehManageTaskPlanId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '任务事项',
                                dataIndex: 'matterDescription',
                                key: 'matterDescription',
                                align: 'center',
                                // width: 100,
                                // tooltip: 5,
                            },
                            form: {
                                label: '任务事项',
                                field: 'matterDescription',
                                type: 'number',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '事项说明',
                                dataIndex: 'controlDemand',
                                key: 'controlDemand',
                                align: 'center',
                                // tooltip: 5,
                            },
                            form: {
                                label: '事项说明',
                                field: 'controlDemand',
                                type: 'number',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '计划完成时间',
                                dataIndex: 'planMakespan',
                                key: 'planMakespan',
                                align: 'center',
                                format: 'YYYY-MM-DD',
                                // width: 150,
                            },
                            form: {
                                label: '计划完成时间',
                                field: 'planMakespan',
                                type: 'number',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '责任部门',
                                dataIndex: 'dutyDepartment',
                                key: 'dutyDepartment',
                                align: 'center',
                                // width: 200,
                                filter: true,
                                type: 'string',
                                // tooltip: 5,
                            },
                            form: {
                                label: '责任部门',
                                field: 'dutyDepartment',
                                type: 'string',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '责任人',
                                dataIndex: 'dutyPerson',
                                key: 'dutyPerson',
                                align: 'center',
                                // width: 120,
                                // tooltip: 5,
                            },
                            form: {
                                label: '责任人',
                                field: 'dutyPerson',
                                type: 'number',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '分管领导',
                                dataIndex: 'managgeLead',
                                key: 'managgeLead',
                                align: 'center',
                                // width: 120,
                                // tooltip: 5,
                            },
                            form: {
                                label: '分管领导',
                                field: 'managgeLead',
                                type: 'number',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '配合人员',
                                dataIndex: 'coordPerson',
                                key: 'coordPerson',
                                align: 'center',
                                // width: 200,
                                // tooltip: 5,
                            },
                            form: {
                                label: '配合人员',
                                field: 'coordPerson',
                                type: 'number',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '配合部门',
                                dataIndex: 'coordDepartment',
                                key: 'coordDepartment',
                                align: 'center',
                                // width: 200,
                                // tooltip: 5,
                            },
                            form: {
                                label: '配合部门',
                                field: 'coordDepartment',
                                type: 'number',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '实际完成时间',
                                dataIndex: 'realMakespan',
                                key: 'realMakespan',
                                format: 'YYYY-MM-DD',
                                align: 'center',
                                // width: 150,
                            },
                            form: {
                                label: '实际完成时间',
                                field: 'realMakespan',
                                type: 'number',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '完成情况说明',
                                dataIndex: 'completeDes',
                                key: 'completeDes'
                            },
                            form: {
                                label: '完成情况说明',
                                field: 'completeDes',
                                type: 'string',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '完成情况',
                                dataIndex: 'completion',
                                key: 'completion',
                                align: 'center',
                                // width: 300,
                                // tooltip: 5,
                            },
                            form: {
                                label: '完成情况',
                                field: 'completion',
                                type: 'number',
                                required: true,
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}
export default index;