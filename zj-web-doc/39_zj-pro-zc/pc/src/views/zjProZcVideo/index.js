import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { Modal } from "antd";
const confirm = Modal.confirm;
const config = {
    fetchConfig: {
        apiName: 'getZjProZcVideoList'
    },
    antd: {
        rowKey: function (row) {
            return row.videoId
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
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            videoUrl:false,
            visibleVideo:false
        }
    }
    componentDidMount() { }
    handleCancelVideo = () => {
        this.setState({ visibleVideo: false,loadingVideo:false });
    }
    render() {
        const { videoUrl, visibleVideo } = this.state;
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
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                label: '主键id',
                                field: 'videoId',
                                hide: true,
                            },
                        },
                        {
                            table: {
                                title: '序号',
                                dataIndex: 'orderFlag',
                                key: 'orderFlag',
                                align: 'center',
                                width:100,
                            },
                            form: {
                                label: '序号',
                                field: 'orderFlag',
                                type:'number'
                            },
                        },
                        {
                            table: {
                                title: '视频文件',
                                dataIndex: 'name',
                                key: 'name',
                                render: (data, rowData) => {
                                    if (rowData && rowData.fileList && rowData.fileList.length > 0) {
                                        return rowData.fileList[0].name
                                    } else {
                                        return ''
                                    }
                                    
                                }
                            },
                            form: {
                                type: 'files',
                                label: '视频',
                                field: 'fileList',
                                required: true,
                                desc: '点击上传', 
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload'
                                },
                                max: 1
                            }
                        },
                        {
                            table: {
                                title: '上传用户',
                                dataIndex: 'createUserName',
                                key: 'createUserName'
                            },
                            form: {
                                type: 'string',
                                label: '上传用户',
                                addDisabled: true,
                                editDisabled: true,
                                field: 'createUserName',
                                initialValue: (obj) => {
                                    return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
                                }
                            }
                        },
                        {
                            table: {
                                title: '上传时间',
                                dataIndex: 'createTime',
                                key: 'createTime',
                                format:'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                addDisabled: true,
                                editDisabled: true,
                                label: '上传时间',
                                field: 'createTime',
                                initialValue: () => {
                                    return new Date()
                                }
                            }
                        },
                        {
                            table: {
                                title: '附件',
                                dataIndex: 'fileList',
                                key: 'fileList',
                                align:'center',
                                onClick: (obj) => {
                                    if (obj.rowData && obj.rowData.fileList &&　obj.rowData.fileList.length > 0) {
                                        this.setState({
                                            videoUrl: obj.rowData.fileList[0].url,
                                            visibleVideo: true,
                                        })
                                    }else {
                                        confirm({title:'未加载到视频'})
                                    }
                                },
                                render: (data) => {
                                    return <div><a>查看</a></div>
                                }
                            },
                            isInForm:false
                        }
                    ]}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            field: 'addOutBtn',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    fetchConfig: {
                                        apiName: 'addZjProZcVideo'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            type: 'primary',
                            label: '修改',
                            editDisabled:false,
                            onClick: (obj) => {
                                this.table.clearSelectedRows();
                            },
                            formBtns: [
                                {
                                    name: 'cancel', 
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    fetchConfig: {
                                        apiName: 'updateZjProZcVideo'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'del',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            fetchConfig: {
                                apiName: 'batchDeleteUpdateZjProZcVideo'
                            },
                        }
                    ]}
                />
                <Modal
                        width='800px'
                        style={{ top: '0' }}
                        title="视频"
                        visible={visibleVideo}
                        footer={null}
                        onCancel={this.handleCancelVideo}
                        bodyStyle={{ width:'800px',textAlign:'center'}}
                        centered={true}
                        destroyOnClose={this.handleCancelVideo}
                        wrapClassName={'modals'}
                >
                        <video src={videoUrl} controls="controls" width="700" ></video>
                </Modal>
            </div>
        );
    }
}

export default index;