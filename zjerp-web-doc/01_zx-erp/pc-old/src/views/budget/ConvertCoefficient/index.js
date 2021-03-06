import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.zxBuProjectTypeCheckId
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
            visible: false,
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
        this.props.myFetch('getZxBuProjectTypeCheckTree', {
            "orgID": "0",
            "checkType": 2
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
                                apiName: 'getZxBuProjectTypeCheckTreeList',
                                otherParams: {
                                    parentID: this.state.value,
                                    // orgID: props.initialValues.orgID
                                    orgID: "0",
                                    checkType: 2
                                }
                            }}
                            // desc={<div><span style={{ color: "black" }}>????????????????????????????????????</span> <span style={{ color: "#ffd400" }}>???????????????????????????</span> <span style={{ color: "red" }}>???????????????????????????</span></div>}
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
                                                apiName: 'addZxBuProjectTypeCheck',
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
                                                apiName: 'updateZxBuProjectTypeCheck',
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
                                                this.props.myFetch('batchDeleteUpdateZxBuProjectTypeCheck', obj.selectedRows).then(({ success, message, data }) => {
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
                                        field: 'zxBuProjectTypeCheckId',
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
                                                    let qdxzRowData = this.tableQD.state.data[this.tableQD.state.data.length - 1];
                                                    return qdxzRowData != null ? qdxzRowData.treeNode : ''
                                                }
                                            }
                                        },
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'checkType',
                                        type: 'number',
                                        hide: true,
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
                                        title: '??????',
                                        dataIndex: 'checkNo',
                                        key: 'checkNo',
                                        align: "center"
                                        // render: (data, rowData) => {
                                        //     // return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>{data}</div>;
                                        // }
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
                                        title: '??????',
                                        dataIndex: 'checkName',
                                        key: 'checkName',
                                        align: "center",
                                        onClick: 'detail',
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
                                        title: '?????????',
                                        dataIndex: 'operate1',
                                        key: 'operate1',
                                        align: "center",
                                        type: "select"
                                    },
                                    form: {
                                        field: 'operate1',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'guanXiYi'
                                            },
                                        },
                                        spanForm: 12,
                                        formItemLayoutForm: {
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
                                        title: '?????????',
                                        dataIndex: 'rate1',
                                        key: 'rate1',   
                                        align: "center",
                                    },
                                    form: {
                                        field:'rate1',
                                        type: 'number',
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
                                        title: '?????????',
                                        dataIndex: 'operate2',
                                        key: 'operate2',
                                        align: "center",
                                        type: "select"
                                    },
                                    form: {
                                        field: 'operate2',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'guanXiEr'
                                            },
                                        },
                                        spanForm: 12,
                                        formItemLayoutForm: {
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
                                        title: '?????????',
                                        dataIndex: 'rate2',
                                        key: 'rate2',
                                        align: "center",
                                    },
                                    form: {
                                        type: 'number',
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
                                        dataIndex: 'dispRate',
                                        key: 'dispRate',
                                        align: "center",
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                    }
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'remarks',
                                        key: 'remarks',
                                        align: "center",
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????',
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