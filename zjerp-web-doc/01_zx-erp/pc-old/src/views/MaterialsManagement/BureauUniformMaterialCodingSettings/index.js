import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const configs = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
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
            formData: null,
            id: '',
            loading: false,
            flag: false
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true
        })
        this.props.myFetch('getZxSkResCategoryMaterialsTree', {}).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data,
                        formData: data.length ? data[0] : {},
                        id: data.length ? data[0].id : '',
                        loading: false
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const { treeData, loading, formData, id, flag } = this.state;
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
                            defaultExpandedKeys={['0002']}
                            rightMenuOption={[]}
                            nodeClick={(node) => {
                                this.setState({
                                    id: '',
                                    formData: null,
                                    flag: node.children.length ? true : false
                                }, () => {
                                    this.setState({
                                        id: node.id,
                                        formData: {
                                            catCode: node.catCode,
                                            catName: node.catName,
                                            spec: node.spec,
                                            unit: node.unit,
                                            isGroup: node.isGroup
                                        }
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
                        <div style={{ height: '131px' }}>
                            {formData ? <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload} //必须返回一个promise
                                //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                headers={{
                                    token: this.props.loginAndLogoutInfo.loginInfo.token
                                }}
                                wrappedComponentRef={(me) => this.qnnForm = me}
                                data={formData}
                                formItemLayout={{
                                    labelCol: {
                                        xs: { span: 7 },
                                        sm: { span: 7 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 17 },
                                        sm: { span: 17 }
                                    }
                                }}
                                formConfig={[
                                    {
                                        label: '资源代码',
                                        field: 'catCode',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '资源名称',
                                        field: 'catName',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '规格型号',
                                        field: 'spec',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '单位',
                                        field: 'unit',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '是否启用',
                                        field: 'isGroup',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "否",
                                                value: "0"
                                            },
                                            {
                                                label: "是",
                                                value: "1"
                                            }
                                        ],
                                        disabled: true,
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        span: 8
                                    }
                                ]}
                            /> : null}
                        </div>
                        <div>
                            {id ? <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.table = me;
                                }}
                                fetchConfig={{
                                    apiName: 'getZxSkResCategoryMaterialsAllResource',
                                    otherParams: {
                                        id: id
                                    }
                                }}
                                desc={flag ? '只能在叶子节点才能新增具体资源' : null}
                                {...configs}
                                actionBtns={flag ? [] : [
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
                                                    apiName: 'addZxSkResourceMaterials',
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
                                                    apiName: 'updateZxSkResourceMaterials',
                                                },
                                                onClick: (obj) => {
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
                                            if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        onClick: (obj) => {
                                            confirm({
                                                content: '确定启用此数据吗?',
                                                onOk: () => {
                                                    this.props.myFetch('batchStartUpdateZxSkResourceMaterials', obj.selectedRows).then(({ success, message, data }) => {
                                                        if (success) {
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
                                                    this.props.myFetch('batchStopUpdateZxSkResourceMaterials', obj.selectedRows).then(({ success, message, data }) => {
                                                        if (success) {
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
                                                    this.props.myFetch('batchDeleteUpdateZxSkResourceMaterials', obj.selectedRows).then(({ success, message, data }) => {
                                                        if (success) {
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
                                            field: 'categoryID',
                                            initialValue: id,
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'resStyle',
                                            type: 'string',
                                            initialValue: 'mt',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '物资编码',
                                            dataIndex: 'resCode',
                                            key: 'resCode',
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
                                            title: '物资名称',
                                            dataIndex: 'resName',
                                            key: 'resName',
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
                                            title: '规格型号',
                                            dataIndex: 'spec',
                                            key: 'spec',
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
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
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
                                            title: 'ABC分类',
                                            dataIndex: 'abcCategory',
                                            key: 'abcCategory',
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'globalCode',
                                                value: 'id',
                                            },
                                            fetchConfig: {
                                                apiName: "getZxEqGlobalCodeList",
                                                otherParams: {
                                                    categoryID: "category100203",
                                                    startFlag: '1'
                                                }
                                            },
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '请选择',
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
                </div>
            </Spin>
        );
    }
}

export default index;