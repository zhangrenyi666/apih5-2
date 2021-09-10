import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const configs = {
    antd: {
        rowKey: 'iecsCBSID',
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
            bsid: '',
            iecsCBSID: '',
            orgIDs: '',
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
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        let departmentId = ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId;
        this.setState({
            loading: true
        })
        this.props.myFetch('getSysProjectBySelect', { departmentId: departmentId }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        optionData: data,
                        orgID: data.length ? data[0].departmentId : '',
                        orgName: data.length ? data[0].departmentName : ''
                    }, () => {
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
        this.props.myFetch('getZxEqIecsCBSTree', { orgID: orgID, orgName: orgName }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        bsid: data.length ? data[0].bsid : '',
                        iecsCBSID: data.length ? data[0].iecsCBSID : '',
                        orgIDs: data.length ? data[0].orgID : '',
                        orgNames: data.length ? data[0].name : '',
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
                <div style={{ borderBottom: '2px solid rgba(30, 130, 223, 0.733)' }}>
                    {optionData.length ? <QnnForm
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload} //必须返回一个promise
                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
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
                                label: '工程项目',
                                field: 'departmentId',
                                type: 'select',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                },
                                optionData: optionData,
                                initialValue: orgID,
                                onChange: (val, obj) => {
                                    this.setState({
                                        treeData: null,
                                        orgID: val,
                                        orgName: obj.itemData.departmentName,
                                        iecsCBSID: '',
                                        formData: null
                                    }, () => {
                                        this.refreshs();
                                    })
                                },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '请选择',
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
                                        orgIDs: node.orgID,
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
                            //键值配置 默认{value:value,label:label,children:children}
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
                                        label: '编号',
                                        field: 'code',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '名称',
                                        field: 'name',
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
                                        label: '合同数量',
                                        field: 'contrQty',
                                        type: 'number',
                                        min: 0,
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '变更后数量',
                                        field: 'alterQty',
                                        type: 'number',
                                        min: 0,
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '备注',
                                        field: 'remark',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入',
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
                                        cbsType: '1',
                                        parentID: iecsCBSID
                                    }
                                }}
                                {...configs}
                                actionBtns={{
                                    apiName: "getSysMenuBtn",
                                    otherParams: (obj) => {
                                        let props = obj.props;
                                        let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                        return {
                                            menuParentId: curRouteData._value,
                                            tableField: "PartialItemization"
                                        }
                                    }
                                }}
                                method={{
                                    addSuccess:(args) => {
                                        if(args.response.success){
                                            this.setState({
                                                treeData: null
                                            }, () => {
                                                this.refreshs();
                                            })
                                        }
                                    },
                                    editSuccess:(args) => {
                                        if(args.response.success){
                                            this.setState({
                                                treeData: null
                                            }, () => {
                                                this.refreshs();
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
                                }}
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
                                            title: '编号',
                                            dataIndex: 'code',
                                            key: 'code',
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
                                            title: '名称',
                                            dataIndex: 'name',
                                            key: 'name',
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
                                            title: '单位',
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
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '合同数量',
                                            dataIndex: 'contrQty',
                                            key: 'contrQty',
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
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
                                            title: '变更后数量',
                                            dataIndex: 'alterQty',
                                            key: 'alterQty',
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
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