import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const configs = {
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
                            //???????????? ??????{value:value,label:label,children:children}
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
                                upload={this.props.myUpload} //??????????????????promise
                                //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
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
                                        label: '????????????',
                                        field: 'catCode',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'catName',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'spec',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????',
                                        field: 'unit',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '???????????????',
                                        field: 'isGroup',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "???",
                                                value: "0"
                                            },
                                            {
                                                label: "???",
                                                value: "1"
                                            }
                                        ],
                                        disabled: true,
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
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
                                desc={flag ? '?????????????????????????????????????????????' : null}
                                {...configs}
                                actionBtns={flag ? [] : {
                                    apiName: "getSysMenuBtn",
                                    otherParams: (obj) => {
                                        let props = obj.props;
                                        let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                        return {
                                            menuParentId: curRouteData._value,
                                            tableField: "BureauUniformMaterialCodingSettings"
                                        }
                                    }
                                }}
                                method={{
                                    qyDisabled:(obj) => {
                                        if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    qyOnClick:(obj) => {
                                        confirm({
                                            content: '?????????????????????????',
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
                                }}
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
                                            title: '????????????',
                                            dataIndex: 'resCode',
                                            key: 'resCode',
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
                                            title: '????????????',
                                            dataIndex: 'resName',
                                            key: 'resName',
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
                                            placeholder: '?????????'
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
                                            title: 'ABCD??????',
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
                                    // {
                                    //     table: {
                                    //         title: '???????????????',
                                    //         dataIndex: 'isGroup',
                                    //         key: 'isGroup',
                                    //         render: (data) => {
                                    //             if (data === '0') {
                                    //                 return '???';
                                    //             } else {
                                    //                 return '???';
                                    //             }
                                    //         }
                                    //     },
                                    //     isInForm: false
                                    // },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'enableFlag',
                                            key: 'enableFlag',
                                            render: (data) => {
                                                if (data === '1') {
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
                </div>
            </Spin>
        );
    }
}

export default index;