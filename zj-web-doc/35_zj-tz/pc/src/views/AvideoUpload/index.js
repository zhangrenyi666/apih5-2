import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { message as Msg, Modal, Row, Col } from "antd";
import { ImgPreview } from "qnn-form";
const confirm = Modal.confirm;
const play = require('./play.png');
const config = {
    antd: {
        rowKey: 'aerialVideo',
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
const baidu_zuobiao = () => {
    return <div
        style={{
            marginTop: '-8px', paddingLeft: '135px', color: '#bbbbbb'
        }}
    >
        请上传宽高比为16比9的图片
    </div>
}
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            videoUrl: '',
            visibleVideo: false
        }
    }
    componentDidMount() { }
    render() {
        const { videoUrl, visibleVideo } = this.state;
        const { projectId, projectName, companyId, companyName, projectShortName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjTzAerialVideoList',
                        otherParams: {
                            projectId: projectId
                        }
                    }}
                    componentsKey={{
                        baidu_zuobiao: baidu_zuobiao
                    }}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 6 },
                            sm: { span: 3 }
                        },
                        wrapperCol: {
                            xs: { span: 18 },
                            sm: { span: 21 }
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'aerialVideo',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectName',
                                type: 'string',
                                hide: true,
                                initialValue: projectName
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectShortName',
                                type: 'string',
                                hide: true,
                                initialValue: projectShortName
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyName',
                                type: 'string',
                                hide: true,
                                initialValue: companyName
                            }
                        },
                        {
                            table: {
                                title: '年月',
                                dataIndex: 'yearDate',
                                key: 'yearDate',
                                width: 60,
                                align:'center',
                                filter: true,
                                format: 'YYYY-MM',
                                fieldsConfig: {
                                    field: 'yearDate',
                                    type: 'month',
                                }
                            },
                            form: {
                                label: '项目名称',
                                field: 'projectId',
                                initialValue: () => {
                                    return projectId
                                },
                                type: 'select',
                                addDisabled: true,
                                editDisabled: true,
                                required: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '请选择',
                            },
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '排序',
                                dataIndex: 'sort',
                                key: 'sort',
                                width: 80,
                            },
                            form: {
                                field: 'sort',
                                required: true,
                                type: 'number',
                                placeholder: '请输入'
                            },
                        },
                        {
                            table: {
                                title: '管理单位',
                                dataIndex: 'companyName',
                                filter: true,
                                width: 100,
                                align:'center',
                                key: 'companyName',
                                fieldConfig: {
                                    field: 'companyName',
                                    initialValue: '',
                                    type: 'string',
                                }
                            },
                            form: {
                                field: 'companyId',
                                required: true,
                                type: 'string',
                                initialValue: companyId,
                                placeholder: '请输入',
                                hide: true,
                            },
                        },
                        {
                            table: {
                                title: '项目名称',
                                filter: true,
                                onClick: 'detail',
                                width: 200,
                                align: 'center',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                fieldsConfig: {
                                    label: '项目名称',
                                    field: 'projectId',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'projectName',
                                        value: 'projectId'
                                    },
                                    fetchConfig: {
                                        apiName: "getZjTzProManageList"
                                    },
                                },
                                render: (data) => {
                                    return data
                                }
                            },
                            form: {
                                label: '年月',
                                field: 'yearDate',
                                type: 'month',
                                required: true,
                                placeholder: '请输入'
                            },

                        },
                        {
                            table: {
                                title: '期次',
                                dataIndex: 'issue',
                                key: 'issue',
                                width: 80,
                                type: 'select',
                                filter: true
                            },
                            isInTable:false,
                            form: {
                                field: 'issue',
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'videoQiCi'
                                    }
                                },
                                required: true,
                                placeholder: '请选择...'
                            },
                        },
                        {
                            table: {
                                title: '视频名称',
                                dataIndex: 'videoName',
                                filter: true,
                                width: 200,
                                align: 'center',
                                key: 'videoName',
                                render: (data) => {
                                    return <div style={{width:'100%',whiteSpace:'pre-wrap'}}>{data}</div>;
                                }
                            },
                            form: {
                                type: 'string',
                                required: true,
                                field: 'videoName',
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: "component",
                                field: "component1",
                                Component: obj => {
                                    return (
                                        <Row style={{color:'red',fontSize:'16px'}}>
                                            <Col span={3}></Col>
                                            <Col span={21} style={{paddingLeft:'12px'}}>备注：上传的航拍视频请统一命名为“XX项目X年X月航拍视频”（项目名称为简称；若分段上传，原名称后需加第X段）</Col>
                                        </Row>
                                    );
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '封面上传',
                                field: 'imageList',
                                required: true,
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                },
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                "accept": "image/jpeg,image/png,image/jpg",
                                "max": 1,

                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '百度地图坐标拾取', //表头标题
                                field: 'baidu_zuobiao', //表格里面的字段
                                type: 'Component',
                                Component: "baidu_zuobiao",
                                colWrapperStyle: {
                                    paddingRight: 0
                                },
                            }
                        },
                        {
                            table: {
                                title: '视频播放',
                                width: 60,
                                dataIndex: 'aHh',
                                key: 'aHh',
                                align: 'center',
                                onClick: (obj) => {
                                    console.log(obj.rowData);
                                    if (obj.rowData && obj.rowData.videoList && obj.rowData.videoList.length > 0) {
                                        obj.rowData.videoList[0].name = obj.rowData.videoName;
                                        this.setState({
                                            videoUrl: obj.rowData.videoList,
                                            visibleVideo: true
                                        })
                                    } else {
                                        confirm({ title: '未加载到视频' })
                                    }
                                },
                                render: (data) => {
                                    return '<img style="height:30px" alt="" src=' + play + ' />'
                                }
                            },
                            form: {
                                label: "视频上传",
                                field: "videoList",
                                required: true,
                                type: "breakpointResume",
                                max: 1,
                                fetchConfig: {
                                    apiName: window.configs.apis.files
                                },
                                accept: 'audio/mp4,video/mp4'
                            }
                        },
                        {
                            table: {
                                title: '下载视频',
                                width: 200,
                                dataIndex: 'videoName',
                                key: 'videoName',
                                align: 'center',
                                onClick: (obj) => {
                                    if (obj.rowData && obj.rowData.videoList && obj.rowData.videoList.length > 0) {
                                        confirm({
                                            title: "确定下载该视频么?",
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                window.open(obj.rowData.videoList[0].downUrl);
                                            }
                                        });
                                    } else {
                                        confirm({ title: '未加载到视频' })
                                    }
                                },
                                render: (data) => {
                                    return data
                                }
                            },
                            isInForm: false
                        }
                    ]}
                    method={{
                        addClick: (obj) => {
                            if (projectId === 'all') {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('请选择右上角项目！')
                            }
                        },
                        editClick: (obj) => {
                            obj.btnCallbackFn.clearSelectedRows()
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
                // actionBtns={[
                //     {
                //         name: 'add',
                //         icon: 'plus',
                //         type: 'primary',
                //         label: '新增',
                //         field: 'addCancelBtn',
                //         formBtns: [
                //             {
                //                 name: 'cancel',
                //                 type: 'dashed',
                //                 label: '取消',
                //             },
                //             {
                //                 name: 'submit',
                //                 type: 'primary',
                //                 label: '保存',
                //                 fetchConfig: {
                //                     apiName: 'addZjTzAerialVideo',
                //                 }
                //             }
                //         ]
                //     },
                //     {
                //         name: 'edit',
                //         type: 'primary',
                //         label: '修改',
                //         editDisabled: false,
                //         onClick: "bind:editClick",
                //         formBtns: [
                //             {
                //                 name: 'cancel',
                //                 type: 'dashed',
                //                 label: '取消',
                //             },
                //             {
                //                 name: 'submit',
                //                 type: 'primary',
                //                 label: '保存',
                //                 fetchConfig: {
                //                     apiName: 'updateZjTzAerialVideo'
                //                 }
                //             }
                //         ]
                //     },
                //     {
                //         name: 'del',
                //         icon: 'delete',
                //         type: 'danger',
                //         label: '离职',
                //         fetchConfig: {
                //             apiName: 'batchDeleteUpdateZjTzAerialVideo',
                //         },
                //     }
                // ]}
                />
                {visibleVideo ? <ImgPreview
                    curIndex={0}
                    onClose={() => {
                        this.setState({
                            visibleVideo: false
                        })
                    }}
                    fileList={videoUrl}
                    allVideo
                /> : null}
            </div>
        );
    }
}

export default index;