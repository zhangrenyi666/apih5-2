import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal } from 'antd';
import s from "./style.less";
import Tree from "../modules/tree-v2";
const config = {
    antd: {
        rowKey: function (row) {
            return row.expertId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    searchBtnsStyle: 'inline'
}
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            departmentId: ''
        }
    }
    render() {
        const { visible, departmentId } = this.state;
        return (
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
                    <Tree
                        selectText={false}
                        modalType="common" //common  drawer  抽屉出现方式或者普通的
                        visible
                        selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        btnShow={false} //是否显示底部按钮
                        disabled={true}
                        draggable={false}
                        canExpand={({ nodeData, props }) => {
                            return false;
                        }}
                        nodeRender={nodeData => {
                            return (
                                <span>
                                    {nodeData["itemName"]}
                                </span>
                            );
                        }}
                        rightMenuOption={[]}
                        nodeClick={(node, selectedKeys) => {
                            this.setState({
                                departmentId: ''
                            }, () => {
                                this.setState({
                                    departmentId: node.props.dataRef.itemId
                                })
                            });
                        }}
                        //ajax请求配置
                        fetchConfig={{
                            apiName: "getBaseCodeSelect",
                            params: {
                                itemId: 'wenTiLeiXing'
                            }
                        }}
                        //键值配置 默认{value:value,label:label,children:children}
                        keys={{
                            label: "itemName",
                            value: "codeId",
                            children: "children"
                        }}
                    />
                </div>
                <div className={s.rootr}>
                    {departmentId ? <QnnTable
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        fetchConfig={{
                            apiName: 'getZjSjConsultExpertList',
                            otherParams: {
                                areasOfExpertiseId: departmentId
                            }
                        }}
                        actionBtns={{
                            apiName: "getSysMenuBtn",
                            otherParams: function (obj) {
                                var props = obj.Pprops;
                                let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                return {
                                    menuParentId: curRouteData._value,
                                    tableField: "ExpertIntroduction"
                                }
                            }
                        }}
                        {...config}
                        formConfig={[
                            {
                                isInTable: false,
                                form: {
                                    field: 'expertId',
                                    type: 'string',
                                    hide: true,
                                }
                            },
                            {
                                isInTable: false,
                                form: {
                                    field: 'pidAll',
                                    type: 'string',
                                    initialValue: departmentId,
                                    hide: true,
                                }
                            },
                            {
                                isInTable: false,
                                form: {
                                    label: '专家',
                                    field: 'deptList',
                                    type: 'treeSelect',
                                    treeSelectOption: {
                                        selectType: "2",
                                        maxNumber: 1,
                                        fetchConfig: {//配置后将会去请求下拉选项数据
                                            apiName: 'getSysDepartmentUserAllTree'
                                        },
                                        search: true,
                                        useCollect: true,
                                        collectApi: "appGetSysFrequentContactsList",  //查询收藏人员     接受后台参数[{xx:xxx,...}]
                                        collectApiByAdd: "appAddSysFrequentContacts", //新增收藏人员   传给后台的参数[{xx:xxx,...}]
                                        collectApiByDel: "appRemoveSysFrequentContacts", //删除收藏人员
                                        searchPlaceholder: '姓名、账号、电话',
                                        // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                        searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                        searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                    },
                                    onChange: (vals) => {
                                        let { value } = vals[0];
                                        this.props.myFetch('getSysUserDetails', { userKey: value }).then(
                                            ({ success, message, data }) => {
                                                if (success) {
                                                    this.table.qnnForm.form.setFieldsValue({ phone: data.mobile, postionsName: data.postionsName, sex: data.gender, age: data.age, email: data.email })
                                                } else {
                                                    Msg.error(message);
                                                }
                                            }
                                        );
                                    },
                                    required: true,
                                    editDisabled: true,
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
                            },
                            {
                                isInSearch: true,
                                table: {
                                    title: '姓名',
                                    dataIndex: 'userName',
                                    key: 'userName',
                                    width: 80,
                                    fixed: 'left',
                                    onClick: 'detail'
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '请输入',
                                    addShow: false,
                                    editShow: false,
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
                                }
                            },
                            {
                                table: {
                                    title: '手机号',
                                    dataIndex: 'phone',
                                    key: 'phone',
                                    width: 120,
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '请输入',
                                    // required: true,
                                    editDisabled: true,
                                    addDisabled: true,
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
                                    },
                                }
                            },
                            {
                                table: {
                                    title: '职务',
                                    dataIndex: 'postionsName',
                                    key: 'postionsName',
                                    width: 100,
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '请输入',
                                    // required: true,
                                    editDisabled: true,
                                    addDisabled: true,
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
                                    },
                                }
                            },
                            {
                                table: {
                                    title: '性别',
                                    dataIndex: 'sex',
                                    key: 'sex',
                                    width: 80,
                                    render: (data) => {
                                        if (data === '0') {
                                            return '未知';
                                        } else if (data === '1') {
                                            return '男';
                                        } else if (data === '2') {
                                            return '女';
                                        } else {
                                            return '';
                                        }
                                    }
                                },
                                form: {
                                    type: 'radio',
                                    placeholder: '请输入',
                                    optionData: [
                                        {
                                            label: "未知", value: '0'
                                        },
                                        {
                                            label: "男", value: '1'
                                        },
                                        {
                                            label: "女", value: '2'
                                        }
                                    ],
                                    // required: true,
                                    editDisabled: true,
                                    addDisabled: true,
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
                                    },
                                }
                            },
                            {
                                table: {
                                    title: '年龄',
                                    dataIndex: 'age',
                                    key: 'age',
                                    width: 80,
                                    render: (data) => {
                                        if (data) {
                                            return data
                                        }
                                        return '';
                                    }
                                },
                                form: {
                                    type: 'number',
                                    placeholder: '请输入',
                                    // required: true,
                                    editDisabled: true,
                                    addDisabled: true,
                                    spanForm: 12,
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 6 },
                                            sm: { span: 6 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 16 },
                                            sm: { span: 16 }
                                        }
                                    },
                                }
                            },
                            {
                                table: {
                                    title: '邮箱',
                                    dataIndex: 'email',
                                    key: 'email',
                                    width: 150,
                                    tooltip:50,
                                },
                                form: {
                                    type: 'email',
                                    placeholder: '请输入',
                                    // required: true,
                                    editDisabled: true,
                                    addDisabled: true,
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
                                    },
                                }
                            },
                            {
                                table: {
                                    title: '职称',
                                    dataIndex: 'position',
                                    key: 'position',
                                    width: 100,
                                },
                                form: {

                                    field: 'positionId',
                                    type: 'select',
                                    placeholder: '请选择',
                                    // required: true,
                                    showSearch: true,
                                    fetchConfig: {
                                        apiName: 'getBaseCodeTree',
                                        otherParams: {
                                            itemId: 'postions'
                                        }
                                    },
                                    optionConfig: {
                                        label: 'itemName',
                                        value: 'itemId',
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
                                    },
                                }
                            },
                            {
                                table: {
                                    title: '擅长领域',
                                    dataIndex: 'areasOfExpertiseName',
                                    key: 'areasOfExpertiseName',
                                    width: 150,
                                    tooltip:50,
                                },
                                form: {
                                    type: 'select',
                                    field: 'areasOfExpertiseId',
                                    placeholder: '请选择',
                                    optionConfig: {
                                        label: 'itemName',
                                        value: 'itemId',
                                    },
                                    multiple: true,
                                    showSearch: true,
                                    optionDataGroup: true,
                                    fetchConfig: {
                                        apiName: 'getBaseCodeTree',
                                        otherParams: {
                                            itemId: 'wenTiLeiXing'
                                        }
                                    }, required: true,
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
                                }
                            },
                            {
                                table: {
                                    title: '简介',
                                    dataIndex: 'introduction',
                                    key: 'introduction',
                                    width: 150,
                                    tooltip:50,
                                },
                                form: {
                                    type: 'textarea',
                                    placeholder: '请输入',
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
                                }
                            },
                            // {
                            //     isInForm: false,
                            //     table: {
                            //         title: "操作",
                            //         dataIndex: 'action',
                            //         key: 'action',
                            //         align: "center",
                            //         width: 80,
                            //         fixed: 'right',
                            //         showType: "tile",
                            //         btns: [
                            //             {
                            //                 name: 'edit',
                            //                 render: function (rowData) {
                            //                     return '<a>修改</a>';
                            //                 },
                            //                 formBtns: [
                            //                     {
                            //                         name: 'cancel',
                            //                         type: 'dashed',
                            //                         label: '取消',
                            //                     },
                            //                     {
                            //                         name: 'submit',
                            //                         type: 'primary',
                            //                         label: '提交',
                            //                         fetchConfig: {
                            //                             apiName: 'updateZjSjConsultExpert',
                            //                         }
                            //                     }
                            //                 ]
                            //             }
                            //         ]
                            //     }
                            // }
                        ]}
                        method={{
                            editOclick: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                            }
                        }}
                    /> : <div className={s.alert}>点击左侧节点即可查看详细信息</div>}
                </div>
                {visible ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="批量导入"
                        visible={visible}
                        footer={null}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'replyData'}
                    >
                        <QnnForm
                            form={this.props.form}
                            history={this.props.history}
                            match={this.props.match}
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
                                    label: '部门',
                                    field: 'deptList',
                                    type: 'treeSelect',
                                    treeSelectOption: {
                                        selectType: "1",
                                        maxNumber: 1,
                                        fetchConfig: {//配置后将会去请求下拉选项数据
                                            apiName: 'getSysDepartmentAllTree'
                                        }
                                    },
                                    required: true
                                },
                                {
                                    label: '附件',
                                    field: 'excelList',
                                    type: 'files',
                                    required: true,
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload'
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
                                    onClick: (obj) => {
                                        obj.btnfns.fetchByCb('importZjSjConsultExpert', obj.values, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({ visible: false });
                                                this.table.refresh();
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
            </div>
        );
    }
}

export default index;