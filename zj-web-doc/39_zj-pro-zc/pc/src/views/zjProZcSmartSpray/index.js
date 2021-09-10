import React,{ Component } from 'react';
import QnnTable from '../modules/qnn-table'; 
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal,Spin } from "antd";
class index extends Component { 
    constructor(props){
        super(props);
        this.state = {
            visibleSend:false,
            loadingSend: false
        }
    }
   
    handleCancelSend = () => {
        this.setState({ visibleSend: false,loadingSend:false });
    }
    render() {
        const { visibleSend, loadingSend } = this.state;
        return (
            <div>
                <QnnTable
                history={this.props.history}
                match={this.props.match}
                    fetch={this.props.myFetch}
                    wrappedComponentRef={(me) => {
                        this.tableGM = me;
                    }}
                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                {...window.zjProZcSmartSpray}
                actionBtns={[
                    {
                        name: 'import',
                        type: 'primary',
                        label: '覆盖式导入',
                        field: 'addOutBtn',
                        onClick: (obj) => {
                            this.setState({
                                visibleSend: true
                            });
                        }
                    },
                    {
                        name: "del",
                        icon: "delete",
                        type: "danger",
                        label: "删除",
                        fetchConfig: {
                            apiName: "batchDeleteUpdateZjProZcSmartSpray"
                        }
                    }
                ]}
            />
                <Modal
                    width='500px'
                    style={{ top: '0' }}
                    title="追加式导入"
                    visible={visibleSend}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '500px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelSend}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSend}>
                        {/* <div style={{marginLeft:'15px'}} onClick={() => {
                        }}>模板下载：<a>模板</a></div> */}
                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            formConfig={[
                                {
                                    label: '附件',
                                    field: 'fileList',
                                    required:true,
                                    type: 'files',
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload'
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
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
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingSend: true
                                        })
                                        this.props.myFetch('batchImportZjProZcSmartSprayList', obj.values).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.tableGM.refresh();
                                                    this.setState({
                                                        visibleSend: false,
                                                        loadingSend: false
                                                    })
                                                } else {
                                                    Msg.error(message)
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
            
        )
    }
}

export default index