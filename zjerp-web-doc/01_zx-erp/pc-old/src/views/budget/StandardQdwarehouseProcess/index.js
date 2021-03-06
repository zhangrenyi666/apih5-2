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
                                                //???????????????
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
                            //???????????? ??????{value:value,label:label,children:children}
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
                            desc={ descFlag ? <div style={{textAlign:"center"}}><span style={{ color: "red"}}>?????????????????????????????????????????????</span></div> : null}
                            actionBtns={ !descFlag ? [
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
                                    name: 'edit',//??????add del
                                    icon: 'edit',
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
                                    name: 'diydel',//??????add del
                                    icon: 'delete',
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
                                        //?????????0  ???????????????id
                                        initialValue: '0',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'techOrder',
                                        key: 'techOrder',
                                        align: "center",
                                    },
                                    form: {
                                        type: 'string',
                                        dataIndex:'techOrder',
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
                                        dataIndex: 'techNon',
                                        key: 'techNon',
                                        align: "center",
                                    },
                                    form: {
                                        type: 'string',
                                        dataIndex:'techNon',
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
                                        dataIndex: 'name',
                                        key: 'name',
                                        align: "center",
                                    },
                                    form: {
                                        type: 'string',
                                        dataIndex:'techNon',
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
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        align: "center",
                                    },
                                    form: {
                                        type: 'string',
                                        dataIndex: 'unit',
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
                            ]}
                        /> : null}
                    </div>
                </div>
            </Spin>
        );
    }
}

export default index;