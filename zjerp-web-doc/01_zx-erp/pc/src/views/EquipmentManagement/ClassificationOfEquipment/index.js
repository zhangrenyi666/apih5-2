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
            defaultExpandedKeys: ['0003'],
            isGroup:'1'
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
        const { bsid, treeData, parentID, defaultExpandedKeys, loading, isGroup } = this.state;
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
                                        bsid: node.bsid,
                                        isGroup: node.id === '0003' ? '1' : node.isGroup
                                    })
                                })
                            }}
                            data={treeData}
                            //???????????? ??????{value:value,label:label,children:children}
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
                            actionBtns={{
                                apiName: "getSysMenuBtn",
                                otherParams: (obj) => {
                                    let props = obj.props;
                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                    return {
                                        menuParentId: curRouteData._value,
                                        tableField: "ClassificationOfEquipment"
                                    }
                                }
                            }}
                            method={{
                                addSuccess:(args) => {
                                    if(args.response.success){
                                        this.setState({
                                            treeData: null
                                        }, () => {
                                            this.refresh();
                                        })
                                    }
                                },
                                editSuccess:(args) => {
                                    if(args.response.success){
                                        this.setState({
                                            treeData: null
                                        }, () => {
                                            this.refresh();
                                        })
                                    }
                                },
                                qyDisabled:(obj) => {
                                    if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                qyOnClick:(obj) => {
                                    confirm({
                                        content: '?????????????????????????',
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
                                },
                                tyDisabled:(obj) => {
                                    if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                tyOnClick:(obj) => {
                                    confirm({
                                        content: '?????????????????????????',
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
                                },
                                diydelDisabled:(obj) => {
                                    if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                diydelOnClick:(obj) => {
                                    confirm({
                                        content: '?????????????????????????',
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
                            }}
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
                                        initialValue: isGroup,
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'catCode',
                                        key: 'catCode',
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
                                        title: '??????',
                                        dataIndex: 'catName',
                                        key: 'catName',
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
                                        title: '??????',
                                        dataIndex: 'unit',
                                        key: 'unit',
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
                                        dataIndex: 'price',
                                        key: 'price',
                                    },
                                    form: {
                                        type: 'number',
                                        min:0,
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
                                        dataIndex: 'spec',
                                        key: 'spec',
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????'
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'isGroup',
                                        key: 'isGroup',
                                        render: (data) => {
                                            if (data === '0') {
                                                return '???';
                                            } else {
                                                return '???';
                                            }
                                        }
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'remark',
                                        key: 'remark',
                                    },
                                    form: {
                                        type: 'textarea',
                                        placeholder: '?????????'
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