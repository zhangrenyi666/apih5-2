import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxCrProjectEvaluationId',
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.6
        }
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
const configItem = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: false,
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    FloatMulTwo(arg1, arg2) {
        var r1, r2, m, n;
        try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
        try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
        m = Math.pow(10, Math.max(r1, r2));
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    }
    getTotalFunc(rowData) {//计算表格内的总值，显示在表单上
        let ruzhangAll = 0;
        let tableData = this.table.qnnForm.form.getFieldsValue();
        tableData.projectEvaluationScoreList.map((item) => {
            if (item.id === rowData.id) {
                ruzhangAll += Number(rowData.getScore ? rowData.getScore * 10 : 0);
            } else {
                ruzhangAll += Number(item.getScore ? item.getScore * 10 : 0);
            }
            return item;
        })
        this.table.qnnForm.form.setFieldsValue({
            totalScore: Number(ruzhangAll / 10)
        });

    }
    render() {
        const { projectId, projectName, companyId,departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        console.log(this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany);
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={{
                        apiName: 'getZxCrProjectEvaluationList',
                        otherParams: {
                            orgID: departmentId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxCrProjectEvaluationId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectId',
                                type: 'string',
                                initialValue: projectId,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgID',
                                type: 'string',
                                initialValue: projectId,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyId',
                                type: 'string',
                                initialValue: companyId,
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                width: 180,
                                tooltip: 23,
                                fixed: 'left',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                onClick: 'detail',
                                willExecute: (obj) => {
                                    this.table.btnCallbackFn.refresh();
                                }
                            },
                            form: {
                                field: 'orgName',
                                type: 'string',
                                initialValue: projectName,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '协作单位名称',
                                dataIndex: 'customerName',
                                key: 'customerName',
                                width: 200,
                                tooltip: 23
                            },
                            form: {
                                field: 'customerId',
                                required: true,
                                type: 'selectByPaging',
                                showSearch: true,
                                optionConfig: {
                                    label: 'customerName',
                                    value: 'orgCertificate',
                                    linkageFields: {
                                        customerName: "customerName",
                                        orgCertificate: 'orgCertificate'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZxCrCustomerInfoListAll'
                                },
                                placeholder: '请选择',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                hide: true,
                                type: 'string',
                                field: 'orgCertificate'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                hide: true,
                                type: 'string',
                                field: 'customerName'
                            }
                        },
                        {
                            table: {
                                title: '协作单位负责人',
                                dataIndex: 'chargeMan',
                                width: 160,
                                key: 'chargeMan'
                            },
                            form: {
                                field: 'chargeMan',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '负责人联系电话',
                                width: 160,
                                dataIndex: 'chargeManPhone',
                                key: 'chargeManPhone'
                            },
                            form: {
                                field: 'chargeManPhone',
                                type: 'phone',
                                placeholder: '请输入',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '专业类别代码',
                                width: 200,
                                dataIndex: 'catCode',
                                key: 'catCode'
                            },
                            form: {
                                field: 'catCode',
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '专业类别',
                                dataIndex: 'catID',
                                key: 'catID',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                field: 'catID',
                                required: true,
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id',
                                    linkageFields: {
                                        catCode: 'catCode',
                                        parentID: 'parentID'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZxCrProjectEvaluationListCatName',
                                },
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                                onChange: (value) => {
                                    this.table.btnCallbackFn.refresh();
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                hide: true,
                                field: 'parentID'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                hide: true,
                                field: 'resName'
                            }
                        },
                        {
                            table: {
                                title: '分类代码',
                                dataIndex: 'resCode',
                                key: 'resCode'
                            },
                            form: {
                                field: 'resCode',
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '分类名称',
                                width: 180,
                                dataIndex: 'resName',
                                key: 'resName',
                                // type: 'select',
                            },
                            form: {
                                type: 'select',
                                required: true,
                                field: 'resID',
                                optionConfig: {
                                    label: 'resName',
                                    value: 'id',
                                    linkageFields: {
                                        resCode: "resCode",
                                        resName: 'resName'
                                    }
                                },
                                dependenciesReRender: true,//多个依赖-配置
                                dependencies: ['parentID', 'catID'],
                                fetchConfig: {//parentID应该存在列表接口里面--待验证
                                    apiName: 'getZxCrProjectEvaluationListResName',
                                    otherParams: (val) => {
                                        let parentIDVal = '';
                                        let catIDVal = '';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            parentIDVal = aa.parentID;
                                            catIDVal = aa.catID;
                                        } else {
                                            parentIDVal = '';
                                            catIDVal = '';
                                        }
                                        return {
                                            parentID: parentIDVal,
                                            catID: catIDVal
                                        }
                                    }
                                },
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '承建工程合同额(万元)',
                                width: 200,
                                dataIndex: 'contractAmt',
                                key: 'contractAmt'
                            },
                            form: {
                                field: 'contractAmt',
                                type: 'number',
                                // type: (rowData, props)=> {
                                //     // if (rowData.itemId === "0") {
                                //         return 'number'
                                //     // } else {
                                //     //     return 'string'
                                //     // }
                                // },
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            }
                        },
                        // {
                        //     isInTable: false,
                        //     form:{
                        //         field: 'radio',
                        //         label: '测试',
                        //         type: 'radio',
                        //         optionData: [
                        //             { label: "字符串", value: "string" },
                        //             { label: "数字", value: "number" },
                        //             { label: "下拉", value: "select" },
                        //             { label: "文件上传", value: "files" },
                        //         ]
                        //     }
                        // },
                        // {
                        //     isInTable: false,
                        //     form:{
                        //         dependencies: ["radio"],
                        //         type: function (args) {
                        //             return args._formData().radio || "string"
                        //         },
                        //         label: '动态type',
                        //         field: 'dtType',
                        //         optionData: [
                        //             { label: "张三", value: "0" },
                        //             { label: "李四", value: "1" }
                        //         ]
                        //     }
                        // },
                        {
                            table: {
                                title: '考核总得分',
                                dataIndex: 'totalScore',
                                key: 'totalScore'
                            },
                            form: {
                                field: 'totalScore',
                                type: 'number',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '考核期次',
                                dataIndex: 'period',
                                key: 'period',
                                type: 'select'
                            },
                            form: {
                                field: 'period',
                                type: 'select',
                                required: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'qiCi'//问张启明
                                    },
                                },
                                placeholder: '请输入',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '考核日期',
                                dataIndex: 'checkDate',
                                key: 'checkDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                field: 'checkDate',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '进场日期',
                                dataIndex: 'inDate',
                                key: 'inDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'inDate',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '退场日期',
                                dataIndex: 'outDate',
                                key: 'outDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                field: 'outDate',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '考核标准',
                                width: 180,
                                dataIndex: 'checkStandard',
                                key: 'checkStandard',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                required: true,
                                field: 'checkStandard',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [//问张启明
                                    {
                                        label: '严重不良行为考核表',
                                        value: '0'
                                    },
                                    {
                                        label: '打分考核表',
                                        value: '1'
                                    }
                                ],
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                                onChange: (val) => {
                                    const { myFetch } = this.props;
                                    if (val === '0') {
                                        myFetch('getZxCrProjectEvaluationBadDetailOne', {}).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        projectEvaluationBadList: data
                                                    })
                                                    this.table.btnCallbackFn.refresh();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                        this.table.qnnForm.form.setFieldsValue({
                                            totalScore: 0
                                        })
                                    } else if (val === '1') {
                                        myFetch('getZxCrProjectEvaluationScoreDetailOne', {}).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    let totalScoreVal = 0;
                                                    for (let k = 0; k < data.length; k++) {
                                                        if (data[k].getScore) {
                                                            totalScoreVal += Number(data[k].getScore);
                                                        }
                                                    }
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        totalScore: totalScoreVal,
                                                        projectEvaluationScoreList: data
                                                    })
                                                    this.table.btnCallbackFn.refresh();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    } else {

                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '填报人',
                                width: 120,
                                dataIndex: 'preparer',
                                key: 'preparer'
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                field: 'preparer',
                                initialValue: () => {
                                    return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                },
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '审核人',
                                width: 120,
                                dataIndex: 'auditor',
                                key: 'auditor'
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                field: 'auditor',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '评审状态',
                                width: 100,
                                fixed: 'right',
                                dataIndex: 'auditStatus',
                                key: 'auditStatus',
                                render: (data) => {
                                    if (data) {
                                        return data === '0' ? '未审核' : '已审核'
                                    } else {
                                        return '未审核'
                                    }
                                }
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                field: 'auditStatus',
                                hide: true,
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [//问张启明
                                    {
                                        label: "未审核",
                                        value: "0"
                                    },
                                    {
                                        label: "已审核",
                                        value: "1"
                                    }
                                ],
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'fileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: 'upload'
                                },
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diySK1',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                            严重不良行为考核表
                                        </div>
                                    );
                                },
                                dependencies: ['checkStandard'],
                                hide: (obj) => {
                                    let checkStandard = obj.form.getFieldsValue()?.checkStandard || obj.clickCb?.rowData?.checkStandard || obj.clickCb?.selectedRows[0]?.checkStandard;
                                    if (checkStandard) {
                                        if (checkStandard === '0') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    } else {
                                        return true
                                    }
                                }
                            },
                        },
                        // 表格修改
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'projectEvaluationBadList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    tableTdEdit: true,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '评价内容',
                                                width: 220,
                                                tooltip: 23,
                                                dataIndex: 'evalContent',
                                                key: 'evalContent'
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'evalContent'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '评价细目',
                                                width: 220,
                                                tooltip: 23,
                                                dataIndex: 'scoreItem',
                                                key: 'scoreItem'
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'scoreItem'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '是否存在严重不良行为',
                                                width: 100,
                                                dataIndex: 'isBad',
                                                key: 'isBad',
                                                type: 'select',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'isBad',
                                                optionConfig: {
                                                    label: 'label', //默认 label
                                                    value: 'value'
                                                },
                                                optionData: [//问张启明
                                                    {
                                                        label: '否',
                                                        value: '0'
                                                    },
                                                    {
                                                        label: '是',
                                                        value: '1'
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remarks',
                                                width: 120,
                                                key: 'remarks',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'textarea',
                                                field: 'remarks',
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 3
                                                }
                                            }
                                        }
                                    ],
                                    actionBtns: []
                                },
                                dependencies: ['checkStandard'],
                                hide: (obj) => {
                                    let checkStandard = obj.form.getFieldsValue()?.checkStandard || obj.clickCb?.rowData?.checkStandard || obj.clickCb?.selectedRows[0]?.checkStandard;
                                    if (checkStandard) {
                                        if (checkStandard === '0') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    } else {
                                        return true
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diySK2',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                            打分考核表
                                        </div>
                                    );
                                },
                                dependencies: ['checkStandard'],
                                hide: (obj) => {
                                    let checkStandard = obj.form.getFieldsValue()?.checkStandard || obj.clickCb?.rowData?.checkStandard || obj.clickCb?.selectedRows[0]?.checkStandard;
                                    if (checkStandard) {
                                        if (checkStandard === '1') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    } else {
                                        return true
                                    }
                                }
                            },
                        },
                        // 表格修改
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'projectEvaluationScoreList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    tableTdEdit: true,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '评价内容',
                                                width: 220,
                                                tooltip: 23,
                                                dataIndex: 'evalContent',
                                                key: 'evalContent'
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'evalContent'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '评价细目',
                                                width: 220,
                                                tooltip: 23,
                                                dataIndex: 'scoreItem',
                                                key: 'scoreItem'
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'scoreItem'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '标准分值',
                                                width: 100,
                                                dataIndex: 'standardScore',
                                                key: 'standardScore'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'standardScore'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '项目减分',
                                                width: 100,
                                                dataIndex: 'deductScore',
                                                key: 'deductScore',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'deductScore',
                                                precision: 1,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (typeof (colVal) === 'number' && colVal >= 0) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            if (colVal > rowData.standardScore) {
                                                                Msg.warn('不能大于标准分值！');
                                                                newRowData.deductScore = 0;
                                                            } else {
                                                                if (colVal >= 0 && typeof (colVal) === 'number') {
                                                                    newRowData.getScore = Number(this.FloatMulTwo(rowData.standardScore, colVal));
                                                                    // 计算总值
                                                                    this.getTotalFunc(newRowData);
                                                                }
                                                            }
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                        }, 600)
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '项目得分',
                                                width: 100,
                                                dataIndex: 'getScore',
                                                key: 'getScore'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'getScore',
                                                precision: 1
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remarks',
                                                width: 120,
                                                key: 'remarks',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'textarea',
                                                field: 'remarks',
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 3
                                                }
                                            }
                                        }
                                    ],
                                    actionBtns: []
                                },
                                dependencies: ['checkStandard'],
                                hide: (obj) => {
                                    let checkStandard = obj.form.getFieldsValue()?.checkStandard || obj.clickCb?.rowData?.checkStandard || obj.clickCb?.selectedRows[0]?.checkStandard;
                                    if (checkStandard) {
                                        if (checkStandard === '1') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    } else {
                                        return true
                                    }
                                },
                            }
                        }
                    ]}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'diysubmit',
                                    type: 'primary',
                                    label: '保存',
                                    field: 'addsubmit',
                                    onClick: (val) => {
                                        const { myFetch } = this.props;
                                        myFetch('addZxCrProjectEvaluation', val._formData).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.table.closeDrawer();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            icon: 'edit',
                            type: 'primary',
                            label: '修改',
                            onClick: (obj) => {
                                if (obj.selectedRows[0].auditStatus === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已审核的不能修改!');
                                } else {
                                    this.table.btnCallbackFn.refresh();
                                }
                                this.table.clearSelectedRows();
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'diySubmit',
                                    type: 'primary',
                                    label: '保存',
                                    onClick: (val) => {
                                        const { myFetch } = this.props;
                                        myFetch('updateZxCrProjectEvaluation', val._formData).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.table.closeDrawer();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                }
                            ]
                        },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '审核',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].auditStatus === '1') {
                                        obj.btnCallbackFn.closeDrawer();
                                        this.table.clearSelectedRows();
                                        Msg.warn('已审核的不能审核!');
                                    } else {
                                        obj.selectedRows[0].companyID = '1';
                                        confirm({
                                            content: '确定审核选中的数据吗?',
                                            onOk: () => {
                                                myFetch('updateAuditStatus', obj.selectedRows[0]).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            // Msg.warn(message);
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        } else {
                                                            Msg.warn(message);
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        }
                                                    }
                                                );
                                            }
                                        });

                                    }
                                } else {
                                    Msg.warn('只能审核一条数据！')
                                }
                            }
                        },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '反审核',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].auditStatus === '0') {
                                        obj.btnCallbackFn.closeDrawer();
                                        this.table.clearSelectedRows();
                                        Msg.warn('未审核的不能反审核!');
                                    } else {
                                        obj.selectedRows[0].companyID = '1';
                                        confirm({
                                            content: '确定反审核选中的数据吗?',
                                            onOk: () => {
                                                myFetch('updateAuditStatusOut', obj.selectedRows[0]).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            // Msg.warn(message)
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        } else {
                                                            Msg.warn(message);
                                                            this.table.clearSelectedRows();
                                                        }
                                                    }
                                                );
                                            }
                                        });

                                    }
                                } else {
                                    Msg.warn('只能反审核一条数据！')
                                }
                            }
                        },
                        {
                            name: 'diyDel',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                let arry = [];
                                for (let m = 0; m < obj.selectedRows.length; m++) {
                                    if (obj.selectedRows[m].auditStatus === '1') {
                                        //存在已审核的数据
                                        arry.push(obj.selectedRows[m].auditStatus);
                                    }
                                }
                                if (arry.length > 0) {
                                    Msg.warn('请选择未审核的数据！')
                                } else {
                                    confirm({
                                        content: '确定删除选中的数据吗?',
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZxCrProjectEvaluation', obj.selectedRows).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        Msg.success(message)
                                                        this.table.refresh();
                                                        this.table.clearSelectedRows();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }

                            }
                        }
                    ]}

                />
            </div>
        );
    }
}

export default index;