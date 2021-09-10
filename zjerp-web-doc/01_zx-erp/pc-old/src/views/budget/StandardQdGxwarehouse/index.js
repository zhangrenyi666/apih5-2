import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.zxBuWorksId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
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
            valuePid:'',
            value:'',
            treeData: null,
            loading: false,
            defaultExpandedKeys: []
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true
        })
        this.props.myFetch('getZxBuWorksTree', {
            "orgID": "0"
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data,
                        loading: false
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const { valuePid, value, treeData, defaultExpandedKeys, loading } = this.state;
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
                                        {nodeData["label"]}
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
                                    valuePid: ''
                                }, () => {
                                    this.setState({
                                        defaultExpandedKeys: [node.value],
                                        value: node.value,
                                        valuePid: node.valuePid
                                    })
                                })
                            }}
                            data={this.state.treeData}
                            //键值配置 默认{value:value,label:label,children:children}
                            keys={{
                                label: "label",
                                value: "value",
                                children: "children"
                            }}
                        /> : null}
                    </div>
                    <div className={s.rootr}>
                        {valuePid ? <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.tableQD = me;
                            }}
                            fetchConfig={{
                                apiName: 'getZxBuWorksTreeList',
                                otherParams: {
                                    parentID: this.state.value,
                                    // orgID: props.initialValues.orgID
                                    orgID: "0",
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
                                                apiName: 'addZxBuWorks',
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
                                    name: 'edit',//内置add del
                                    icon: 'edit',
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
                                                apiName: 'updateZxBuWorks',
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
                                {
                                    name: 'diydel',//内置add del
                                    icon: 'delete',
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
                                                this.props.myFetch('batchDeleteUpdateZxBuWorks', obj.selectedRows).then(({ success, message, data }) => {
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
                                        field: 'zxBuWorksId',
                                        initialValue: value,
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'parentID',
                                        type: 'string',
                                        initialValue: (obj) => {
                                            if (obj.clickCb.rowInfo.name === 'add') {
                                                if (this.tableQD.state.data.length) {
                                                    let qdxzRowData = this.tableQD.state.data[0];
                                                    return qdxzRowData.parentID
                                                }
                                            }
                                        },
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'orgID',
                                        type: 'string',
                                        initialValue: '0',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'treeNode',
                                        type: 'string',
                                        hide: true,
                                        initialValue: (obj) => {
                                            if (obj.clickCb.rowInfo.name === 'add') {
                                                if (this.tableQD.state.data.length) {
                                                    let qdxzRowData = this.tableQD.state.data[0];
                                                    return qdxzRowData.treeNode
                                                }
                                            }
                                        },
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'isLeaf',
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '清单编号',
                                        dataIndex: 'workNo',
                                        key: 'workNo',
                                        align: "center",
                                        render: (data, rowData) => {
                                            return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>{data}</div>;
                                        }
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
                                        title: '清单名称',
                                        dataIndex: 'workName',
                                        key: 'workName',
                                        align: "center",
                                        // onClick: 'detail',
                                    }
                                },
                                {
                                    table: {
                                        title: '计量单位',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        align: "center"
                                    }
                                },
                                {
                                    table: {
                                        title: '是否局下达',
                                        dataIndex: 'isGroup',
                                        key: 'isGroup',
                                        align: "center",
                                    },
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