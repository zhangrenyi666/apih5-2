import React, { Component } from "react";
import { DownloadOutlined } from '@ant-design/icons';
import QnnForm from "../modules/qnn-table/qnn-form";
import { Button, Spin, Modal } from "antd";
class SelectFilesDownLoad extends Component {
    constructor(props) {
        super(props);
        this.state = {
            changeUrl: this.props.dataList[0].url,
            visibleSendData: false,
            loadingSendData: false
        }
    }
    handleCancelSend = () => {
        this.setState({ visibleSendData: false, loadingSendData: false });
    }
    handleCancel = () => {
        this.setState({ visible: false, loading: false });
    }
    render() {
        const { visibleSendData, loadingSendData } = this.state
        return (
            <div style={{ textAlign: 'center' }}>
                <Button type="primary" icon={<DownloadOutlined />} onClick={() => {
                    this.setState({
                        visibleSendData: true
                    })
                }} size={'middle'} />
                <Modal
                    width='500px'
                    style={{ top: '0' }}
                    title="附件下载"
                    visible={visibleSendData}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '500px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelSend}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSendData}>
                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            wrappedComponentRef={(me) => {
                                this.formEdit = me;
                            }}
                            formConfig={[
                                {
                                    field: 'zxErpFileList',
                                    initialValue: this.props.dataList,
                                    type: 'files',
                                    label: "附件",
                                    disabled: true,
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 5 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 19 }
                                        }
                                    },
                                }
                            ]}
                        />
                    </Spin>
                </Modal>
            </div>
        )
    }
}
export default SelectFilesDownLoad;