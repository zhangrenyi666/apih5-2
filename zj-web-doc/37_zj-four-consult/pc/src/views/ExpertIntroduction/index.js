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
                        modalType="common" //common  drawer  ?????????????????????????????????
                        visible
                        selectModal="0" //0?????????  1??????(??????)  2????????????????????????
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        btnShow={false} //????????????????????????
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
                        //ajax????????????
                        fetchConfig={{
                            apiName: "getBaseCodeSelect",
                            params: {
                                itemId: 'wenTiLeiXing'
                            }
                        }}
                        //???????????? ??????{value:value,label:label,children:children}
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
                                    label: '??????',
                                    field: 'deptList',
                                    type: 'treeSelect',
                                    treeSelectOption: {
                                        selectType: "2",
                                        maxNumber: 1,
                                        fetchConfig: {//??????????????????????????????????????????
                                            apiName: 'getSysDepartmentUserAllTree'
                                        },
                                        search: true,
                                        useCollect: true,
                                        collectApi: "appGetSysFrequentContactsList",  //??????????????????     ??????????????????[{xx:xxx,...}]
                                        collectApiByAdd: "appAddSysFrequentContacts", //??????????????????   ?????????????????????[{xx:xxx,...}]
                                        collectApiByDel: "appRemoveSysFrequentContacts", //??????????????????
                                        searchPlaceholder: '????????????????????????',
                                        // searchApi:'getSysDepartmentUserAllTree',  //??????????????????api  [string]
                                        searchParamsKey: 'search',//???????????????K ?????????'searchText'   [string]
                                        searchOtherParams: { pageSize: 999 }//????????????????????????  [object]
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
                                    title: '??????',
                                    dataIndex: 'userName',
                                    key: 'userName',
                                    width: 80,
                                    fixed: 'left',
                                    onClick: 'detail'
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '?????????',
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
                                    title: '?????????',
                                    dataIndex: 'phone',
                                    key: 'phone',
                                    width: 120,
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '?????????',
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
                                    title: '??????',
                                    dataIndex: 'postionsName',
                                    key: 'postionsName',
                                    width: 100,
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '?????????',
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
                                    title: '??????',
                                    dataIndex: 'sex',
                                    key: 'sex',
                                    width: 80,
                                    render: (data) => {
                                        if (data === '0') {
                                            return '??????';
                                        } else if (data === '1') {
                                            return '???';
                                        } else if (data === '2') {
                                            return '???';
                                        } else {
                                            return '';
                                        }
                                    }
                                },
                                form: {
                                    type: 'radio',
                                    placeholder: '?????????',
                                    optionData: [
                                        {
                                            label: "??????", value: '0'
                                        },
                                        {
                                            label: "???", value: '1'
                                        },
                                        {
                                            label: "???", value: '2'
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
                                    title: '??????',
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
                                    placeholder: '?????????',
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
                                    title: '??????',
                                    dataIndex: 'email',
                                    key: 'email',
                                    width: 150,
                                    tooltip:50,
                                },
                                form: {
                                    type: 'email',
                                    placeholder: '?????????',
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
                                    title: '??????',
                                    dataIndex: 'position',
                                    key: 'position',
                                    width: 100,
                                },
                                form: {

                                    field: 'positionId',
                                    type: 'select',
                                    placeholder: '?????????',
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
                                    title: '????????????',
                                    dataIndex: 'areasOfExpertiseName',
                                    key: 'areasOfExpertiseName',
                                    width: 150,
                                    tooltip:50,
                                },
                                form: {
                                    type: 'select',
                                    field: 'areasOfExpertiseId',
                                    placeholder: '?????????',
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
                                    title: '??????',
                                    dataIndex: 'introduction',
                                    key: 'introduction',
                                    width: 150,
                                    tooltip:50,
                                },
                                form: {
                                    type: 'textarea',
                                    placeholder: '?????????',
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
                            //         title: "??????",
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
                            //                     return '<a>??????</a>';
                            //                 },
                            //                 formBtns: [
                            //                     {
                            //                         name: 'cancel',
                            //                         type: 'dashed',
                            //                         label: '??????',
                            //                     },
                            //                     {
                            //                         name: 'submit',
                            //                         type: 'primary',
                            //                         label: '??????',
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
                    /> : <div className={s.alert}>??????????????????????????????????????????</div>}
                </div>
                {visible ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="????????????"
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
                                    label: '??????',
                                    field: 'deptList',
                                    type: 'treeSelect',
                                    treeSelectOption: {
                                        selectType: "1",
                                        maxNumber: 1,
                                        fetchConfig: {//??????????????????????????????????????????
                                            apiName: 'getSysDepartmentAllTree'
                                        }
                                    },
                                    required: true
                                },
                                {
                                    label: '??????',
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
                                    label: "??????",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "??????",
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