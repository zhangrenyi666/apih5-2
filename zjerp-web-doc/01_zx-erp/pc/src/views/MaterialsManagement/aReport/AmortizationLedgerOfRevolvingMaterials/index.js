import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../../modules/qnn-table/qnn-form";
import { Button, Modal } from "antd";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    pageSize: 99999999,
    paginationConfig: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            formData: {
                queryComID:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
                queryPeriod: moment(new Date()).format('YYYYMM')
            }
        }
    }
    render() {
        const { departmentId, formData } = this.state;
        return (
            <div>
                <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    wrappedComponentRef={(me) => {
                        this.form = me;
                    }}
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
                            type: 'string',
                            label: '公司',
                            field: 'queryComID',
                            hide: true
                        },
                        {
                            label: '项目名称',
                            field: 'queryOrgID',
                            type: 'select',
                            showSearch: true,
                            allowClear: formData?.queryOrgID ? false : true,
                            optionConfig: {
                                label: 'departmentName',
                                value: 'departmentId'
                            },
                            fetchConfig: {
                                apiName: 'getSysProjectBySelect',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            span: 6
                        },
                        {
                            label: '物资编码',
                            field: 'queryResID',
                            type: 'selectByQnnTable',
                            optionConfig: {
                                value: 'id',
                                label: "resCode"
                            },
                            dropdownMatchSelectWidth: 850,
                            qnnTableConfig: {
                                antd: { rowKey: "id" },
                                fetchConfig: {
                                    apiName: "getZxSkTurnoverInResourceList"
                                },
                                searchBtnsStyle: "inline",
                                formConfig: [
                                    {
                                        isInForm: false,
                                        isInSearch: true,
                                        table: {
                                            dataIndex: "resCode",
                                            title: "编号",
                                            width: 150
                                        },
                                        form: { type: 'string' }
                                    },
                                    {
                                        isInForm: false,
                                        isInSearch: true,
                                        table: {
                                            dataIndex: "resName",
                                            title: "名称",
                                            width: 100
                                        },
                                        form: { type: 'string' }
                                    },
                                    {
                                        isInForm: false,
                                        isInSearch: true,
                                        table: {
                                            dataIndex: "spec",
                                            title: "规格型号",
                                            width: 100
                                        },
                                        form: { type: 'string' }
                                    },
                                    {
                                        isInForm: false,
                                        isInSearch: true,
                                        table: {
                                            dataIndex: "resUnit",
                                            title: "计量单位",
                                        },
                                        form: {
                                            type: "string"
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        // isInSearch:true,
                                        table: {
                                            dataIndex: "pricingManner",
                                            title: "计价方式",
                                        },
                                        form: {
                                            type: "string"
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        // isInSearch:true,
                                        table: {
                                            dataIndex: "planningAuthorities",
                                            title: "编制机构",
                                        },
                                        form: {
                                            type: "string"
                                        }
                                    }
                                ]
                            },
                            placeholder: '请选择',
                            span: 6
                        },
                        {
                            type: 'month',
                            label: '期次',
                            field: 'queryPeriod',
                            span: 6
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        let value = this.form.form.getFieldsValue();
                                        if (value?.queryPeriod) {
                                            value.queryPeriod = moment(value.queryPeriod).format('YYYYMM');
                                        }
                                        this.setState({
                                            formData: value
                                        })
                                    }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    if (value?.queryPeriod) {
                                                        value.queryPeriod = moment(value.queryPeriod).format('YYYYMM');
                                                    }
                                                    let stringData = '';
                                                    for (let key in value) {
                                                        if (value?.[key]) {
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:ZxSkTurnResAmoItemWen.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=周转材料摊销明细帐`;
                                                    window.open(URL);
                                                }
                                            })
                                        }}>导出</Button></div>
                                );
                            },
                            span: 6
                        }
                    ]}
                />
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'exportZxSkTurnResAmoItemWen',
                        otherParams: { ...formData }
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
                            table: {
                                title: '物资编码',
                                dataIndex: 'resCode',
                                width: 150,
                                key: 'resCode',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'resName',
                                width: 150,
                                key: 'resName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '规格',
                                dataIndex: 'spec',
                                width: 100,
                                key: 'spec'
                            }
                        },
                        {
                            table: {
                                title: '单位',
                                dataIndex: 'unit',
                                width: 100,
                                key: 'unit'
                            }
                        },
                        {
                            table: {
                                title: '数量',
                                dataIndex: 'buyQty',
                                width: 100,
                                key: 'buyQty'
                            }
                        },
                        {
                            table: {
                                title: '购入单价',
                                dataIndex: 'buyPrice',
                                width: 100,
                                key: 'buyPrice'
                            }
                        },
                        {
                            table: {
                                title: '原值',
                                dataIndex: 'buyAmt',
                                width: 100,
                                key: 'buyAmt'
                            }
                        },
                        {
                            table: {
                                title: '本期摊销金额',
                                dataIndex: 'shareAmt',
                                width: 120,
                                key: 'shareAmt'
                            }
                        },
                        {
                            table: {
                                title: '至上期末累计摊销金额',
                                dataIndex: 'allShareAmt',
                                width: 130,
                                key: 'allShareAmt'
                            }
                        },
                        {
                            table: {
                                title: '至上期末净值',
                                dataIndex: 'currentAmt',
                                width: 100,
                                key: 'currentAmt'
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                width: 150,
                                key: 'remarks'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;