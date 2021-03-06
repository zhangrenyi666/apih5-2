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
    paginationConfig: false,
    pageSize:99999999,
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
    rowSelection:{
        checkStrictly:true
    }
}
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            treeData: null,
            loading: false,
            parentID: '',
            treeNode: '',
            defaultExpandedKeys: [],
            isProcess:''
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true
        })
        this.props.myFetch('getZxCtSZProcessTree', { baseType: "szgxk" }).then(
            ({ data, success, message }) => {
                if (success) {
                    // let loopFn = (data) => {
                    //     for (var i = 0; i < data.length; i++) {
                    //         data[i].isLeaf = null;
                    //         if (data[i].childrenList) {
                    //             loopFn(data[i].childrenList)
                    //         }
                    //     }
                    //     return data;
                    // };
                    // data = loopFn(data);
                    let isProcess = '0';
                    data.map((item) => {
                        if(item.id === '1000szgxk'){
                            isProcess = item.isProcess;
                        }
                        return item;
                    })
                    this.setState({
                        treeData: data,
                        parentID: '1000szgxk',
                        treeNode: '1000szgxk',
                        defaultExpandedKeys: ['1000szgxk'],
                        isProcess,
                        loading: false
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
                                        treeNode: node.treeNode,
                                        isProcess: node?.isProcess
                                    })
                                })
                            }}
                            data={treeData}
                            setNodeProps={nodeInfo => {
                                if(nodeInfo?.childrenList?.length){
                                    return { isLeaf: false };
                                }
                                return { isLeaf: true };
                            }}
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
                                apiName: 'getZxCtSZProcessItemList',
                                otherParams: {
                                    treeNode: parentID,
                                    baseType: 'szgxk'
                                }
                            }}
                            actionBtns={{
                                apiName: "getSysMenuBtn",
                                otherParams: (obj) => {
                                    let props = obj.props;
                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                    return {
                                        menuParentId: curRouteData._value,
                                        tableField: "MunicipalStandardProcessLibrary"
                                    }
                                }
                            }}
                            method={{
                                addDisabled:() => {
                                    if (this.state.isProcess !== '1') {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                addSuccess:(args) => {
                                    if (args.response.success) {
                                        this.setState({
                                            treeData: null
                                        }, () => {
                                            this.refresh();
                                        })
                                    }
                                },
                                editSuccess:(args) => {
                                    if (args.response.success) {
                                        this.setState({
                                            treeData: null
                                        }, () => {
                                            this.refresh();
                                        })
                                    }
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
                                        fixed: "left",
                                        width: 200,
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
                                        width: 150,
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
                                        type: 'select',
                                        width: 100,
                                    },
                                    form: {
                                        type: 'radio',
                                        optionData: [
                                            {
                                                label: "???",
                                                value: '0'
                                            },
                                            {
                                                label: "???",
                                                value: '1'
                                            }
                                        ],
                                        addDisabled:true,
                                        initialValue: '1',
                                        placeholder: '?????????',
                                        onChange:(val,obj) => {
                                            if(val === '0' && obj?.qnnformData?.qnnFormProps?.clickCb?.rowData?.children?.length){
                                                Msg.error('?????????????????????????????????????????????????????????????????????!');
                                                obj.form.setFieldsValue({isProcess:'1'});
                                            }
                                        },
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
                                        width: 100,
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
                                        width: 100,
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
                                        width: 100,
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
                                        type: 'select',
                                        width: 100,
                                    },
                                    form: {
                                        type: 'radio',
                                        optionData: [
                                            {
                                                label: "???",
                                                value: '0'
                                            },
                                            {
                                                label: "???",
                                                value: '1'
                                            }
                                        ],
                                        initialValue: '0',
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
                                        width: 100,
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