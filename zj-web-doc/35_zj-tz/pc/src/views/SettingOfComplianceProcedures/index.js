import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from '../modules/qnn-table/qnn-form';
import { message as Msg, Modal } from 'antd';
import s from "./style.less";
const config = {
    antd: {
        rowKey: function (row) {
            return row.complianceBaseId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 21 },
            sm: { span: 3  }
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
            visible:false,
            proNameId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId : '',
        }
    }
    render() {
        const { visible, proNameId } = this.state;
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
                    fetchConfig = {{
                        apiName: 'getZjTzComplianceBaseList',
                        otherParams: {
                            projectId: proNameId
                        }
                    }}
                    getRowSelection={(obj) => {
                        return {
                            getCheckboxProps: record => ({
                                name: record.name,
                                disabled: record.num === "001" || record.num === "002" || record.num === "003" || record.num === "004" || record.num === "005" || record.num === "006" || record.num === "007" || record.num === "008"
                            })
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'complianceBaseId',
                                type: 'string',
                                hide:true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectId',
                                type: 'string',
                                initialValue:proNameId,
                                hide:true
                            }
                        },
                        {   
                            table: {
                                title: '??????',
                                dataIndex: 'index',
                                key: 'index',
                                render:(data,rowData,index) => {
                                    return index + 1;
                                }
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '????????????',
                                dataIndex: 'num',
                                key: 'num'
                            },
                            form: {
                                required: true,
                                type:'string',
                                placeholder:'?????????'
                            },
                        },
                        {   
                            table: {
                                title: '??????????????????',
                                dataIndex: 'complianceBanseName',
                                key: 'complianceBanseName'
                            },
                            form: {
                                required: true,
                                type:'string',
                                placeholder:'?????????'
                            },
                        },
                        {   
                            table: {
                                title: '?????????',
                                dataIndex: 'modifyUserName',
                                key: 'modifyUserName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '????????????',
                                dataIndex: 'modifyTime',
                                key: 'modifyTime',
                                format:'YYYY-MM-DD'
                            },
                            isInForm:false
                        }
                    ]}
                    method={{
                        editClick:(obj) => {
                            this.table.clearSelectedRows();
                        },
                        exportClick:() => {
                            // this.setState({
                            //     visible: true
                            // })
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
                                    label: '????????????',
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
                                        obj.btnfns.fetchByCb('', obj.values, ({ data, success, message }) => {
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