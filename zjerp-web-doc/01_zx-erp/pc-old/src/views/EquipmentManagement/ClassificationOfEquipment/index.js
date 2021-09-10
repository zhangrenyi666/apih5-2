import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'id',
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
    },
    isShowRowSelect:true
}
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            treeData: null,
            loading: false,
            parentID: '0003',
            bsid: '0003',
            defaultExpandedKeys: ['0003']
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true
        })
        this.props.myFetch('getZxEqResCategoryTree', {}).then(
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
        const { bsid, treeData, parentID, defaultExpandedKeys, loading } = this.state;
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
                            modalType="common" //common  drawer  抽屉出现方式或者普通的
                            visible
                            selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
                            myFetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            btnShow={false} //是否显示底部按钮
                            disabled={true}
                            draggable={false}
                            nodeRender={nodeData => {
                                return (
                                    <span>
                                        {nodeData["catName"]}
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
                                        defaultExpandedKeys: node.bsid.split(','),
                                        parentID: node.id,
                                        bsid: node.bsid
                                    })
                                })
                            }}
                            data={treeData}
                            //键值配置 默认{value:value,label:label,children:children}
                            keys={{
                                label: "catName",
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
                                apiName:'getZxEqResCategoryList',
                                otherParams:{
                                    parentID:parentID
                                }
                            }}
                            actionBtns={[
                                {
                                    name: 'add',//内置add del
                                    icon: 'plus',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '新增',
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'addZxEqResCategory',
                                                success:(args) => {
                                                    if(args.response.success){
                                                        this.setState({
                                                            treeData: null
                                                        }, () => {
                                                            this.refresh();
                                                        })
                                                    }
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//内置add del
                                    icon: 'edit',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '修改',
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'updateZxEqResCategory',
                                                success:(args) => {
                                                    if(args.response.success){
                                                        this.setState({
                                                            treeData: null
                                                        }, () => {
                                                            this.refresh();
                                                        })
                                                    }
                                                }
                                            },
                                            onClick:(obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'qy',//内置add del
                                    icon: 'edit',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '启用',
                                    disabled: (obj) => {
                                        if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '确定启用此数据吗?',
                                            onOk: () => {
                                                this.props.myFetch('batchStartUpdateZxEqResCategory', obj.selectedRows).then(({ success, message, data }) => {
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
                                },
                                {
                                    name: 'ty',//内置add del
                                    icon: 'edit',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '停用',
                                    disabled: (obj) => {
                                        if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '确定停用此数据吗?',
                                            onOk: () => {
                                                this.props.myFetch('batchStopUpdateZxEqResCategory', obj.selectedRows).then(({ success, message, data }) => {
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
                                },
                                {
                                    name: 'diydel',//内置add del
                                    icon: 'delete',//icon
                                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                                    label: '删除',
                                    disabled: (obj) => {
                                        if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '确定删除此数据吗?',
                                            onOk: () => {
                                                this.props.myFetch('batchDeleteUpdateZxEqResCategory', obj.selectedRows).then(({ success, message, data }) => {
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
                                        field: 'bsid',
                                        type: 'string',
                                        initialValue: bsid,
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
                                        title: '编码',
                                        dataIndex: 'catCode',
                                        key: 'catCode',
                                        filter: true,
                                        onClick: 'detail'
                                    },
                                    form: {
                                        type: 'string',
                                        required: true,
                                        placeholder: '请输入',
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
                                        title: '名称',
                                        dataIndex: 'catName',
                                        key: 'catName',
                                        filter: true,
                                    },
                                    form: {
                                        type: 'string',
                                        required: true,
                                        placeholder: '请输入',
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
                                        title: '单位',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '请输入',
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
                                        title: '单价',
                                        dataIndex: 'price',
                                        key: 'price',
                                    },
                                    form: {
                                        type: 'number',
                                        min:0,
                                        placeholder: '请输入',
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
                                        title: '规格型号',
                                        dataIndex: 'spec',
                                        key: 'spec',
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '是否启用',
                                        dataIndex: 'isGroup',
                                        key: 'isGroup',
                                        render: (data) => {
                                            if (data === '0') {
                                                return '否';
                                            } else {
                                                return '是';
                                            }
                                        }
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '备注',
                                        dataIndex: 'remark',
                                        key: 'remark',
                                    },
                                    form: {
                                        type: 'textarea',
                                        placeholder: '请输入'
                                    }
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