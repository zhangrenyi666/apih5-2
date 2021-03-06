import React from "react";
import Apih5 from "qnn-apih5"
import QnnTable from '../modules/qnn-table';
import { message as Msg } from 'antd';
import FormRedraw from "./FormRedraw";
class Index extends Apih5 {
    constructor(props) {
        super(props)
        this.state = {
            changeBmValue: '',
            treeData: [],
            value: '',
            changeType: '1',
            oneItemLayout: {
                labelCol: {
                    sm: { span: 2 }
                },
                wrapperCol: {
                    sm: { span: 22 }
                }
            },
            oneFormItemWrapperStyle: -5,
            fourItemSpan: 6,
            fourItemLayout: {
                labelCol: {
                    sm: { span: 8 }
                },
                wrapperCol: {
                    sm: { span: 16 }
                }
            },
            fourItemLayout2: {
                labelCol: {
                    sm: { span: 12 }
                },
                wrapperCol: {
                    sm: { span: 12 }
                }
            },
        }
    }
    componentDidMount() {
        this.refresh('0');
    }
    refresh(departmentParentId, key) {
        let tableData = null
        this.props.myFetch('getSysDepartmentCurrentTree', { departmentParentId }).then(
            ({ data, success, message }) => {
                if (departmentParentId !== '0') {
                    var loopFn = function (dataList) {
                        for (var i = 0; i < dataList.length; i++) {
                            if (dataList[i].value === key) {
                                dataList[i].children = data;
                            } else if (dataList[i].children) {
                                dataList[i].children = loopFn(dataList[i].children)
                            }
                        }
                        return dataList;
                    }

                    tableData = loopFn(this.state.treeData);
                } else {
                    tableData = data
                }

                if (success) {
                    this.setState({
                        treeData: tableData
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const orgId = this.apih5.getOrgId()
        return (
            // <div className={s.page}>
            //     <div className={s.rootl}
            //         ref={(me) => {
            //             if (me) {
            //                 this.leftDom = me;
            //             }
            //         }}>
            //         <div
            //             className={s.hr}
            //             ref={(me) => {
            //                 if (me) {
            //                     let _this = this;
            //                     function moveFn(e) {
            //                         let conDomLeft = document.getElementsByClassName('ant-layout-content')[0].offsetLeft;
            //                         _this.leftDom.style['flex-basis'] = e.pageX - conDomLeft + 'px'
            //                     }
            //                     me.addEventListener('mousedown', (e) => {
            //                         this.onDragStartPos = e.pageX;
            //                         document.addEventListener('mousemove', moveFn, false)
            //                     }, false);
            //                     document.addEventListener('mouseup', (e) => {
            //                         document.removeEventListener('mousemove', moveFn, false)
            //                     }, false)
            //                 }
            //             }}
            //         ></div>
            //         {this.state.treeData.length ? <Tree
            //             selectText={false}
            //             modalType="common" //common  drawer  ?????????????????????????????????
            //             visible
            //             selectModal="0" //0?????????  1??????(??????)  2????????????????????????
            //             myFetch={this.props.myFetch}
            //             upload={this.props.myUpload}
            //             btnShow={false} //????????????????????????
            //             disabled={true}
            //             draggable={false}
            //             nodeRender={nodeData => {
            //                 return (
            //                     <span>
            //                         {nodeData["label"]}
            //                     </span>
            //                 );
            //             }}
            //             treeProps={{
            //                 showLine: true
            //             }}
            //             rightMenuOption={[]}
            //             nodeClick={(node, selectedKeys) => {
            //                 this.refresh(node.value, selectedKeys[0])
            //                 this.setState({
            //                     value: ''
            //                 }, () => {
            //                     this.setState({
            //                         value: node
            //                     })
            //                 })
            //             }}
            //             data={this.state.treeData}
            //             //???????????? ??????{value:value,label:label,children:children}
            //             keys={{
            //                 label: "label",
            //                 value: "value",
            //                 children: "children"
            //             }}
            //         /> : null}
            //     </div>
            //     <div className={s.rootr}>
            //         {
            //             Object.keys(this.state.value).length ? <QnnTable
            //                 fetch={this.props.myFetch}
            //                 upload={this.props.myUpload}
            //                 wrappedComponentRef={(me) => { this.myQnnTable = me }}
            //                 method={{}}
            //                 antd={{
            //                     rowKey: 'zjXmSalaryUserReshuffleId'
            //                 }}
            //                 componentsKey={{}}
            //                 fetchConfig={
            //                     {
            //                         apiName: 'getZjXmSalaryUserReshuffleList',
            //                         otherParams: {
            //                             topId: this.state.value.value
            //                         }
            //                     }
            //                 }
            //                 actionBtns={[
            //                     {
            //                         name: "add", //??????add del
            //                         icon: "plus", //icon
            //                         type: "primary", //??????  ?????? primary  [primary dashed danger]
            //                         label: "??????",
            //                         formBtns: [
            //                             {
            //                                 name: "cancel", //??????????????????
            //                                 type: "dashed", //??????  ?????? primary
            //                                 label: "??????"
            //                             },

            //                             {
            //                                 field: "submit",
            //                                 name: "submit",
            //                                 type: "primary",
            //                                 label: "??????",
            //                                 fetchConfig: {
            //                                     apiName: 'addZjXmSalaryUserExtension',
            //                                     params: {},
            //                                     otherParams: async (obj) => {
            //                                     },
            //                                 },
            //                             }
            //                         ]
            //                     },
            //                     {
            //                         field: 'submitsp',
            //                         name: 'del',
            //                         fetchConfig: {//ajax??????
            //                             apiName: '????????????',
            //                         },
            //                         type: 'primary',
            //                         label: '????????????',
            //                     },
            //                     {
            //                         field: 'submitsczh',
            //                         name: 'del',
            //                         fetchConfig: {//ajax??????
            //                             apiName: '????????????',
            //                         },
            //                         type: 'primary',
            //                         label: '????????????',
            //                     },
            //                     {
            //                         field: 'delBtn',
            //                         name: 'del',
            //                         icon: 'delete',
            //                         fetchConfig: {//ajax??????
            //                             apiName: 'login',
            //                         },
            //                         type: 'danger',
            //                         label: '??????',
            //                     },
            //                 ]}
            //                 formConfig={[
            //                     {
            //                         isInTable: false,
            //                         form: {
            //                             field: 'zjXmSalaryUserReshuffleId',
            //                             type: 'string',
            //                             hide: true
            //                         }
            //                     },
            //                     {
            //                         table: {
            //                             title: '??????',
            //                             dataIndex: 'name',
            //                             fixed: 'left'
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '??????',
            //                             dataIndex: 'position',
            //                             fixed: 'left'
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '????????????',
            //                             dataIndex: 'ssxm',
            //                             key: 'ssxm',
            //                             fixed: 'left'
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '????????????',
            //                             dataIndex: 'ssks',
            //                             key: 'ssks',
            //                             fixed: 'left'
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '????????????',
            //                             dataIndex: 'ssbm',
            //                             key: 'ssbm',
            //                             fixed: 'left'
            //                         },
            //                         isInForm: false
            //                     },
            //                     // ??????????????????
            //                     {
            //                         table: {
            //                             title: '????????????',
            //                             dataIndex: 'ydgw',
            //                             key: 'ydgw',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '????????????',
            //                             dataIndex: 'ydxm',
            //                             key: 'ydxm',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '????????????',
            //                             dataIndex: 'ydks',
            //                             key: 'ydks',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '????????????',
            //                             dataIndex: 'ydbm',
            //                             key: 'ydbm',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '????????????',
            //                             dataIndex: 'ydlx',
            //                             key: 'ydlx',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '????????????',
            //                             dataIndex: 'dgzt',
            //                             key: 'dgzt',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '??????????????????',
            //                             dataIndex: 'approvalLocation',
            //                             key: 'approvalLocation',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '????????????',
            //                             dataIndex: 'apih5FlowStatus',
            //                             key: 'apih5FlowStatus',
            //                             width: 100,
            //                             type: 'select'
            //                         },
            //                         form: {
            //                             field: "apih5FlowStatus",
            //                             type: "select",
            //                             placeholder: "?????????...",
            //                             fetchConfig: {
            //                                 apiName: "getBaseCodeSelect",
            //                                 otherParams: {
            //                                     itemId: 'liuChengZhuangTai'
            //                                 }
            //                             },
            //                             optionConfig: {
            //                                 label: 'itemName',
            //                                 value: 'itemId',
            //                             },
            //                         }
            //                     },
            //                     {
            //                         table: {
            //                             title: '????????????',
            //                             dataIndex: 'approvalTime',
            //                             key: 'approvalTime',
            //                             width: 100,
            //                             format: 'YYYY-MM-DD'
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         isInForm: false,
            //                         table: {
            //                             showType: "tile", //??????????????? bubble????????????  tile???????????? ??????bubble  ???0.6.15??????????????????????????????table????????????????????????table?????????
            //                             width: 110,
            //                             title: "??????",
            //                             key: "action", //???????????????
            //                             fixed: "right", //???????????????
            //                             align: "center",
            //                             btns: [
            //                                 {
            //                                     name: "edit", // ??????name??????add,  del, edit, detail, Component, form???
            //                                     label: "??????",
            //                                     onClick: (obj) => {
            //                                         // zjXmSalaryUserReshuffleId
            //                                     }
            //                                 },
            //                                 {
            //                                     name: 'diy',//??????add del
            //                                     label: "??????",
            //                                     btns: [
            //                                         {
            //                                             name: "jlsc", //??????????????????
            //                                             label: "????????????",
            //                                             onClick: "bind:jlsc"
            //                                         },
            //                                         // {
            //                                         //     name: "pyjl", //??????????????????
            //                                         //     label: "????????????",
            //                                         //     onClick: "bind:pyjl"
            //                                         // }
            //                                     ]
            //                                 },
            //                             ]
            //                         }
            //                     }
            //                 ]}
            //                 tabs={[
            //                     {
            //                         field: "basic",
            //                         name: "qnnFormDiy",
            //                         title: "????????????",
            //                         content: (obj) => {
            //                             return (
            //                                 <div>
            //                                     <FormRedraw {...this.props} refInstance={this.myQnnTable} clickInfo={obj.clickCb.rowInfo} />
            //                                 </div>
            //                             )
            //                         }
            //                     }
            //                 ]}
            //             /> : <div className={s.alert}>????????????????????????????????????</div>
            //         }
            //     </div>
            // </div>

            <div>
                <QnnTable
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    wrappedComponentRef={(me) => { this.myQnnTable = me }}
                    antd={{
                        rowKey: 'zjXmSalaryUserReshuffleId'
                    }}
                    componentsKey={{}}
                    fetchConfig={
                        {
                            apiName: 'getZjXmSalaryUserReshuffleList',
                            otherParams: {
                                orgId
                            }
                        }
                    }
                    method={{
                        changeConfirmTypeFunc: (obj) => {
                            const rowData = obj.qnnTableInstance.getSelectedRows()
                            if (rowData.length === 1 && rowData[0].changeConfirmType === '0') {
                                return false
                            } else {
                                return true
                            }
                        }
                    }}
                    actionBtns={[
                        {
                            name: "add", //??????add del
                            icon: "plus", //icon
                            type: "primary", //??????  ?????? primary  [primary dashed danger]
                            label: "??????",
                            formBtns: [
                                {
                                    name: "cancel", //??????????????????
                                    type: "dashed", //??????  ?????? primary
                                    label: "??????"
                                },

                                {
                                    field: "submit",
                                    name: "submit",
                                    type: "primary",
                                    label: "??????",
                                    fetchConfig: {
                                        apiName: 'addZjXmSalaryUserExtension',
                                        params: {},
                                        otherParams: async (obj) => {
                                        },
                                    },
                                }
                            ]
                        },
                        // {
                        //     field: 'submitsp',
                        //     name: 'del',
                        //     fetchConfig: {//ajax??????
                        //         apiName: '????????????',
                        //     },
                        //     type: 'primary',
                        //     label: '????????????',
                        // },
                        // {
                        //     field: 'submitsczh',
                        //     name: 'del',
                        //     fetchConfig: {//ajax??????
                        //         apiName: '????????????',
                        //     },
                        //     type: 'primary',
                        //     label: '????????????',
                        // },
                        {
                            field: 'confirm',
                            name: 'confirm',
                            type: 'primary',
                            label: '??????',
                            disabled: 'bind:changeConfirmTypeFunc',
                            onClick: async (obj) => {
                                const {  success, message } = await this.props.myFetch('changeConfirmZjXmSalaryUserReshuffle', { ...obj.selectedRows[0] })
                                if (success) {
                                    Msg.success('????????????')
                                    this.myQnnTable.clearSelectedRows()
                                    this.myQnnTable.refresh()
                                } else {
                                    Msg.error(message)
                                }
                            }
                        },
                        {
                            field: 'delBtn',
                            name: 'del',
                            icon: 'delete',
                            fetchConfig: {//ajax??????
                                apiName: 'batchDeleteZjXmSalaryUserReshuffle',
                            },
                            type: 'danger',
                            label: '??????',
                        },
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zjXmSalaryUserReshuffleId',
                                type: 'string',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'realName',
                                fixed: 'left',
                                onClick: 'detail'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'positionName',
                                fixed: 'left'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '????????????/??????',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                fixed: 'left'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'officeName',
                                key: 'officeName',
                                fixed: 'left'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'departmentName',
                                key: 'departmentName',
                                fixed: 'left'
                            },
                            isInForm: false
                        },
                        // ??????????????????
                        // {
                        //     table: {
                        //         title: '????????????',
                        //         dataIndex: 'positionNameAfter',
                        //         key: 'positionNameAfter',
                        //     },
                        //     isInForm: false
                        // },
                        {
                            table: {
                                title: '????????????/??????',
                                dataIndex: 'orgAfterName',
                                key: 'orgAfterName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'officeAfterName',
                                key: 'officeAfterName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'departmentAfterName',
                                key: 'departmentAfterName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'changeType',
                                key: 'changeType',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                label: '????????????',
                                field: 'changeType',
                                placeholder: '?????????',
                                optionData: [
                                    {
                                        label: "????????????",
                                        value: "1"
                                    },
                                    {
                                        label: "????????????",
                                        value: "2"
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'changeConfirmType',
                                key: 'changeConfirmType',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                label: '???????????????',
                                field: 'changeConfirmType',
                                placeholder: '?????????',
                                optionData: [
                                    {
                                        label: "?????????",
                                        value: "0"
                                    },
                                    {
                                        label: "?????????",
                                        value: "1"
                                    }
                                ],
                            }
                        },
                        // {
                        //     table: {
                        //         title: '????????????',
                        //         dataIndex: 'dgzt',
                        //         key: 'dgzt',
                        //     },
                        //     isInForm: false
                        // },
                        // {
                        //     table: {
                        //         title: '??????????????????',
                        //         dataIndex: 'approvalLocation',
                        //         key: 'approvalLocation',
                        //     },
                        //     isInForm: false
                        // },
                        // {
                        //     table: {
                        //         title: '????????????',
                        //         dataIndex: 'apih5FlowStatus',
                        //         key: 'apih5FlowStatus',
                        //         width: 100,
                        //         type: 'select'
                        //     },
                        //     form: {
                        //         field: "apih5FlowStatus",
                        //         type: "select",
                        //         placeholder: "?????????...",
                        //         fetchConfig: {
                        //             apiName: "getBaseCodeSelect",
                        //             otherParams: {
                        //                 itemId: 'liuChengZhuangTai'
                        //             }
                        //         },
                        //         optionConfig: {
                        //             label: 'itemName',
                        //             value: 'itemId',
                        //         },
                        //     }
                        // },
                        // {
                        //     table: {
                        //         title: '????????????',
                        //         dataIndex: 'approvalTime',
                        //         key: 'approvalTime',
                        //         width: 100,
                        //         format: 'YYYY-MM-DD'
                        //     },
                        //     isInForm: false
                        // },
                        {
                            isInForm: false,
                            table: {
                                showType: "tile", //??????????????? bubble????????????  tile???????????? ??????bubble  ???0.6.15??????????????????????????????table????????????????????????table?????????
                                width: 110,
                                title: "??????",
                                key: "action", //???????????????
                                fixed: "right", //???????????????
                                align: "center",
                                btns: [
                                    {
                                        name: "edit", // ??????name??????add,  del, edit, detail, Component, form???
                                        label: "??????",
                                        disabled: (obj) => {
                                            return obj.rowData.changeConfirmType !== '0'
                                        },
                                        onClick: (obj) => {
                                            // zjXmSalaryUserReshuffleId
                                        }
                                    },
                                    // {
                                    //     name: 'diy',//??????add del
                                    //     label: "??????",
                                    //     btns: [
                                    //         {
                                    //             name: "jlsc", //??????????????????
                                    //             label: "????????????",
                                    //             onClick: "bind:jlsc"
                                    //         },
                                    //         // {
                                    //         //     name: "pyjl", //??????????????????
                                    //         //     label: "????????????",
                                    //         //     onClick: "bind:pyjl"
                                    //         // }
                                    //     ]
                                    // },
                                ]
                            }
                        }
                    ]}
                    tabs={[
                        {
                            field: "basic",
                            name: "qnnFormDiy",
                            title: "????????????",
                            content: (obj) => {
                                return (
                                    <div>
                                        <FormRedraw {...this.props} refInstance={this.myQnnTable} clickInfo={obj.clickCb} />
                                    </div>
                                )
                            }
                        }
                    ]}
                />
            </div>
        )
    }
}

export default Index