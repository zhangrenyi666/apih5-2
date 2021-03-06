import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: "id",
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
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
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            treeData: null,
            loading: false,
            parentID: '1000szgxk',
            treeNode: '1000szgxk',
            defaultExpandedKeys: ['1000szgxk']
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true
        })
        this.props.myFetch('getZxCtSZProcessTree', {baseType:"szgxk"}).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data,
                        loading:false
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const { treeNode, treeData, parentID, defaultExpandedKeys, loading } = this.state;
        // const { projectid } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <Spin spinning={loading}>
                <div className={s.root}>
                    <div className={s.rootl}
                        ref={(me) => {
                            if (me) {
                                this.leftDom = me;
                            }
                        }}>
                        <div
                            className={s.hr}
                            ref={(me) => {
                                if (me) {
                                    let _this = this;
                                    function moveFn(e) {
                                        let conDomLeft = document.getElementsByClassName('ant-layout-content')[0].offsetLeft;
                                        _this.leftDom.style['flex-basis'] = e.pageX - conDomLeft + 'px'
                                    }
                                    me.addEventListener('mousedown', (e) => {
                                        this.onDragStartPos = e.pageX;
                                        document.addEventListener('mousemove', moveFn, false)
                                    }, false);
                                    document.addEventListener('mouseup', (e) => {
                                        document.removeEventListener('mousemove', moveFn, false)
                                    }, false)
                                }
                            }}
                        ></div>
                        {treeData ? <Tree
                            selectText={false}
                            modalType="common" //common  drawer  ?????????????????????????????????
                            visible
                            selectModal="0" //0?????????  1??????(??????)  2????????????????????????
                            myFetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            btnShow={false} //????????????????????????
                            disabled={true}
                            draggable={false}
                            nodeRender={nodeData => {
                                return (
                                    <span>
                                        {nodeData["processName"]}
                                    </span>
                                );
                            }}
                            treeProps={{
                                showLine: true
                            }}
                            defaultExpandedKeys={defaultExpandedKeys}
                            rightMenuOption={[]}
                            nodeClick={(node) => {
                                this.setState({
                                    parentID: ''
                                }, () => {
                                    this.setState({
                                        defaultExpandedKeys: node.treeNode.split(','),
                                        parentID: node.id,
                                        treeNode: node.treeNode
                                    })
                                })
                            }}
                            data={treeData}
                            //???????????? ??????{value:value,label:label,children:children}
                            keys={{
                                label: "processName",
                                value: "id",
                                children: "childrenList"
                            }}
                        /> : null}
                    </div>
                    <div className={s.rootr}>
                        {parentID ? <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            fetchConfig={{
                                apiName:'getZxCtSZProcessItemList',
                                otherParams:{
                                    treeNode:parentID,
                                    baseType:'szgxk'
                                }
                            }}
                            actionBtns={[
                                {
                                    name: 'add',//??????add del
                                    icon: 'plus',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'addZxCtSZProcess',
                                            },
                                            onClick: (obj) => {
                                                if (obj.response.success) {
                                                    this.setState({
                                                        treeData: null
                                                    }, () => {
                                                        this.refresh();
                                                    })
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//??????add del
                                    icon: 'edit',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'updateZxCtSZProcess',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                                if (obj.response.success) {
                                                    this.setState({
                                                        treeData: null
                                                    }, () => {
                                                        this.refresh();
                                                    })
                                                }
                                            }
                                        }
                                    ]
                                },
                                // {
                                //     name: 'qy',//??????add del
                                //     icon: 'edit',//icon
                                //     type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                //     label: '??????',
                                //     disabled: (obj) => {
                                //         if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                //             return false;
                                //         } else {
                                //             return true;
                                //         }
                                //     },
                                //     onClick: (obj) => {
                                //         confirm({
                                //             content: '?????????????????????????',
                                //             onOk: () => {
                                //                 this.props.myFetch('batchStartUpdateZxEqResCategory', obj.selectedRows).then(({ success, message, data }) => {
                                //                     if (success) {
                                //                         this.setState({
                                //                             treeData: null
                                //                         }, () => {
                                //                             this.refresh();
                                //                         })
                                //                         obj.btnCallbackFn.clearSelectedRows();
                                //                         obj.btnCallbackFn.refresh();
                                //                     } else {
                                //                         Msg.error(message);
                                //                     }
                                //                 });
                                //             }
                                //         });
                                //     }
                                // },
                                {
                                    name: 'diydel',//??????add del
                                    icon: 'delete',//icon
                                    type: 'danger',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '?????????????????????????',
                                            onOk: () => {
                                                this.props.myFetch('batchDeleteUpdateZxCtSZProcess', obj.selectedRows).then(({ success, message, data }) => {
                                                    if (success) {
                                                        this.setState({
                                                            treeData: null
                                                        }, () => {
                                                            this.refresh();
                                                        })
                                                        obj.btnCallbackFn.clearSelectedRows();
                                                        obj.btnCallbackFn.refresh();
                                                    } else {
                                                        Msg.error(message);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            ]}
                            {...config}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'id',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'parentID',
                                        type: 'string',
                                        initialValue: parentID,
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'baseType',
                                        type: 'string',
                                        initialValue: 'szgxk',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'treeNode',
                                        type: 'string',
                                        initialValue: treeNode,
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'isGroup',
                                        type: 'string',
                                        initialValue: '0',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'processNo',
                                        key: 'processNo',
                                        fixed:"left",
                                        width:200,
                                        filter: true,
                                        onClick: 'detail'
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
                                    table: {
                                        title: '??????????????????',
                                        dataIndex: 'processName',
                                        key: 'processName',
                                        width:150,
                                        filter: true,
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
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'isProcess',
                                        key: 'isProcess',
                                        type:'select',
                                        width:100,
                                    },
                                    form: {
                                        type: 'radio',
                                        optionData:[
                                            {
                                                label:"???",
                                                value:'0'
                                            },
                                            {
                                                label:"???",
                                                value:'1'
                                            }
                                        ],
                                        initialValue:'1',
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
                                    table: {
                                        title: '??????',
                                        dataIndex: 'processUnit',
                                        key: 'processUnit',
                                        width:100,
                                    },
                                    form: {
                                        type: 'string',
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
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'content',
                                        key: 'content',
                                        width:100,
                                    },
                                    form: {
                                        type: 'string',
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
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'priceType',
                                        key: 'priceType',
                                        width:100,
                                    },
                                    form: {
                                        type: 'string',
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
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'deleted',
                                        key: 'deleted',
                                        type:'select',
                                        width:100,
                                    },
                                    form: {
                                        type: 'radio',
                                        optionData:[
                                            {
                                                label:"???",
                                                value:'0'
                                            },
                                            {
                                                label:"???",
                                                value:'1'
                                            }
                                        ],
                                        initialValue:'0',
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
                                    table: {
                                        title: '???????????????',
                                        dataIndex: 'isGroup',
                                        key: 'isGroup',
                                        width:100,
                                        render: (data) => {
                                            if (data === '0') {
                                                return '???';
                                            } else {
                                                return '???';
                                            }
                                        }
                                    },
                                    isInForm: false
                                }
                            ]}
                        /> : null}
                    </div>
                </div>
            </Spin>
        );
    }
}

export default index;