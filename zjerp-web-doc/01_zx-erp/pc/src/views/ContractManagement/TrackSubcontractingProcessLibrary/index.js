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
    pageSize: 99999999,
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
        this.props.myFetch('getZxCtSZProcessTree', { baseType: "gdgxk" }).then(
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
                        if(item.id === '1000gdgxk'){
                            isProcess = item.isProcess;
                        }
                        return item;
                    })
                    this.setState({
                        treeData: data,
                        parentID: '1000gdgxk',
                        treeNode: '1000gdgxk',
                        defaultExpandedKeys: ['1000gdgxk'],
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
                            //键值配置 默认{value:value,label:label,children:children}
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
                                    baseType: 'gdgxk'
                                }
                            }}
                            actionBtns={{
                                apiName: "getSysMenuBtn",
                                otherParams: (obj) => {
                                    let props = obj.props;
                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                    return {
                                        menuParentId: curRouteData._value,
                                        tableField: "TrackSubcontractingProcessLibrary"
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
                                        content: '确定删除此数据吗?',
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
                                        initialValue: 'gdgxk',
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
                                        title: '编码',
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
                                        title: '标准工序名称',
                                        dataIndex: 'processName',
                                        key: 'processName',
                                        width: 150,
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
                                        title: '是否工序',
                                        dataIndex: 'isProcess',
                                        key: 'isProcess',
                                        type: 'select',
                                        width: 100,
                                    },
                                    form: {
                                        type: 'radio',
                                        optionData: [
                                            {
                                                label: "否",
                                                value: '0'
                                            },
                                            {
                                                label: "是",
                                                value: '1'
                                            }
                                        ],
                                        addDisabled:true,
                                        initialValue: '1',
                                        placeholder: '请选择',
                                        onChange:(val,obj) => {
                                            if(val === '0' && obj?.qnnformData?.qnnFormProps?.clickCb?.rowData?.children?.length){
                                                Msg.error('当前工序存在下级数据，不可更改【是否工序】的值!');
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
                                        title: '单位',
                                        dataIndex: 'processUnit',
                                        key: 'processUnit',
                                        width: 100,
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
                                        title: '施工内容',
                                        dataIndex: 'content',
                                        key: 'content',
                                        width: 100,
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
                                        title: '计价规则',
                                        dataIndex: 'priceType',
                                        key: 'priceType',
                                        width: 100,
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
                                        title: '是否禁用',
                                        dataIndex: 'deleted',
                                        key: 'deleted',
                                        type: 'select',
                                        width: 100,
                                    },
                                    form: {
                                        type: 'radio',
                                        optionData: [
                                            {
                                                label: "否",
                                                value: '0'
                                            },
                                            {
                                                label: "是",
                                                value: '1'
                                            }
                                        ],
                                        initialValue: '0',
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
                                        title: '是否局数据',
                                        dataIndex: 'isGroup',
                                        key: 'isGroup',
                                        width: 100,
                                        render: (data) => {
                                            if (data === '0') {
                                                return '否';
                                            } else {
                                                return '是';
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