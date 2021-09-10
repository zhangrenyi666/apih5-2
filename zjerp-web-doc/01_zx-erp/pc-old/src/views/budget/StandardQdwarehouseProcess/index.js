import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.zxBuYgjResTechnicsId
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
            defaultExpandedKeys: [],
            descFlag:false
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
        const { valuePid, value, treeData, defaultExpandedKeys, loading, descFlag } = this.state;
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
                                this.props.myFetch("getZxBuWorksDetail",{
                                    zxBuWorksId:node.value
                                }).then(
                                    ({ data, success, message }) => {
                                        if (success) {
                                            if(data.isLeaf===1){
                                                //是叶子节点
                                                this.setState({
                                                    descFlag: false
                                                })
                                            }else{
                                                this.setState({
                                                    descFlag: true
                                                })
                                            }
                                        } else {
                                            Msg.error(message)
                                        }
                                    }
                                );
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
                                apiName: 'getZxBuYgjResTechnicsList',
                                otherParams: {
                                    billID: this.state.value,
                                    orgID: "0",
                                }
                            }}
                            desc={ descFlag ? <div style={{textAlign:"center"}}><span style={{ color: "red"}}>提示：只有叶子节点可以挂接工序</span></div> : null}
                            actionBtns={ !descFlag ? [
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
                                                apiName: 'addZxBuYgjResTechnics',
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
                                                apiName: 'updateZxBuYgjResTechnics',
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
                                                this.props.myFetch('batchDeleteUpdateZxBuYgjResTechnics', obj.selectedRows).then(({ success, message, data }) => {
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
                            ] : null}
                            {...config}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'zxBuYgjResTechnicsId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'billID',
                                        initialValue: value,
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'money',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'price',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'qty',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'technicAmt',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'downAmt',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'upAmt',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'taxRate',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'taxPrice',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'finPrice',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'finTaxRate',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'finTaxPrice',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'remPrice',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'remTaxRate',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'remTaxPrice',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'finQty',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'remQty',
                                        initialValue: 0,
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'orgID',
                                        //默认传0  应该传机构id
                                        initialValue: '0',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '序号',
                                        dataIndex: 'techOrder',
                                        key: 'techOrder',
                                        align: "center",
                                    },
                                    form: {
                                        type: 'string',
                                        dataIndex:'techOrder',
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
                                        title: '工序编号',
                                        dataIndex: 'techNon',
                                        key: 'techNon',
                                        align: "center",
                                    },
                                    form: {
                                        type: 'string',
                                        dataIndex:'techNon',
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
                                        title: '工序名称',
                                        dataIndex: 'name',
                                        key: 'name',
                                        align: "center",
                                    },
                                    form: {
                                        type: 'string',
                                        dataIndex:'techNon',
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
                                        title: '工序单位',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        align: "center",
                                    },
                                    form: {
                                        type: 'string',
                                        dataIndex: 'unit',
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
                            ]}
                        /> : null}
                    </div>
                </div>
            </Spin>
        );
    }
}

export default index;