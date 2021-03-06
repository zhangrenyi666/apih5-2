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
            return row.iecsCBSID
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
            bsid:'',
            iecsCBSID: '',
            orgIDs:'',
            formData: null,
            loading: false,
            optionData: [],
            orgID: '',
            orgName: ''
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        this.setState({
            loading: true
        })
        this.props.myFetch('getSysProjectBySelect', {departmentId:departmentId}).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        optionData: data,
                        orgID: data.length ? data[0].departmentId : '',
                        orgName: data.length ? data[0].departmentName : ''
                    },() => {
                        this.refreshs();
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    refreshs = () => {
        const { orgID, orgName } = this.state;
        this.props.myFetch('getZxEqIecsCBSTree', {orgID:orgID,orgName:orgName}).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        bsid:data.length ? data[0].bsid : '',
                        iecsCBSID:data.length ? data[0].iecsCBSID : '',
                        orgIDs:data.length ? data[0].orgID : '',
                        orgNames:data.length ? data[0].name : '',
                        treeData: data,
                        formData: data.length ? data[0] : {},
                        loading: false
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const { treeData, iecsCBSID, loading, formData, optionData, orgID, bsid, orgIDs, orgNames } = this.state;
        return (
            <Spin spinning={loading}>
                <div style={{borderBottom:'2px solid rgba(30, 130, 223, 0.733)'}}>
                    {optionData.length ? <QnnForm
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload} //??????????????????promise
                        //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
                        headers={{
                            token: this.props.loginAndLogoutInfo.loginInfo.token
                        }}
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
                                field: 'departmentId',
                                type: 'select',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                },
                                optionData: optionData,
                                initialValue: orgID,
                                onChange: (val,obj) => {
                                    this.setState({
                                        treeData: null,
                                        orgID: val,
                                        orgName:obj.itemData.departmentName,
                                        iecsCBSID:'',
                                        formData:null
                                    }, () => {
                                        this.refreshs();
                                    })
                                },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '?????????',
                                span: 8
                            }
                        ]}
                    /> : null}
                </div>
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
                                        {nodeData["name"]}
                                    </span>
                                );
                            }}
                            treeProps={{
                                showLine: true
                            }}
                            defaultExpandedKeys={[bsid]}
                            rightMenuOption={[]}
                            nodeClick={(node) => {
                                this.setState({
                                    iecsCBSID: '',
                                    formData: null
                                }, () => {
                                    this.setState({
                                        iecsCBSID: node.iecsCBSID,
                                        orgIDs:node.orgID,
                                        formData: {
                                            code: node.code,
                                            name: node.name,
                                            unit: node.unit,
                                            contrQty: node.contrQty,
                                            alterQty: node.alterQty,
                                            remark: node.remark
                                        }
                                    })
                                })
                            }}
                            data={treeData}
                            //???????????? ??????{value:value,label:label,children:children}
                            keys={{
                                label: "name",
                                value: "bsid",
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
                                        label: '??????',
                                        field: 'code',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????',
                                        field: 'name',
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
                                        label: '????????????',
                                        field: 'contrQty',
                                        type: 'number',
                                        min:0,
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '???????????????',
                                        field: 'alterQty',
                                        type: 'number',
                                        min:0,
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????',
                                        field: 'remark',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8
                                    }
                                ]}
                            /> : null}
                        </div>
                        <div>
                            {iecsCBSID ? <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.table = me;
                                }}
                                fetchConfig={{
                                    apiName: 'getZxEqIecsCBSList',
                                    otherParams: {
                                        cbsType:'1',
                                        parentID:iecsCBSID
                                    }
                                }}
                                {...configs}
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
                                                    apiName: 'addZxEqIecsCBS',
                                                },
                                                onClick: (obj) => {
                                                    if (obj.response.success) {
                                                        this.setState({
                                                            treeData: null
                                                        }, () => {
                                                            this.refreshs();
                                                        })
                                                    }
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'edit',//??????add del
                                        icon: 'edit',//icon
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
                                                    apiName: 'updateZxEqIecsCBS',
                                                },
                                                onClick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                    if (obj.response.success) {
                                                        this.setState({
                                                            treeData: null
                                                        }, () => {
                                                            this.refreshs();
                                                        })
                                                    }
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'diydel',//??????add del
                                        icon: 'delete',//icon
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
                                                    this.props.myFetch('batchDeleteUpdateZxEqIecsCBS', obj.selectedRows).then(({ success, message, data }) => {
                                                        if (success) {
                                                            this.setState({
                                                                treeData: null
                                                            }, () => {
                                                                this.refreshs();
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
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'iecsCBSID',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'parentID',
                                            type: 'string',
                                            initialValue: iecsCBSID,
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
                                            field: 'orgName',
                                            type: 'string',
                                            initialValue: orgNames,
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'orgID',
                                            type: 'string',
                                            initialValue: orgIDs,
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'cbsType',
                                            type: 'string',
                                            initialValue: '1',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'code',
                                            key: 'code',
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
                                            dataIndex: 'name',
                                            key: 'name',
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
                                            title: '????????????',
                                            dataIndex: 'contrQty',
                                            key: 'contrQty',
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
                                            title: '???????????????',
                                            dataIndex: 'alterQty',
                                            key: 'alterQty',
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