import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
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
    pageSize:99999999,
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
                            label: '公司',
                            field: 'queryComID',
                            type: 'string',
                            hide: true
                        },
                        {
                            label: '项目名称',
                            field: 'queryOrgID',
                            type: 'select',
                            showSearch: true,
                            allowClear: formData?.queryOrgID ? false : true,
                            required:true,
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
                            span: 8
                        },
                        {
                            type: 'month',
                            label: '期次',
                            field: 'queryPeriod',
                            required:true,
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        this.form.form.validateFields().then((value) => {
                                            if (value?.queryPeriod) {
                                                value.queryPeriod = moment(value.queryPeriod).format('YYYYMM');
                                            }
                                            this.setState({
                                                formData: value
                                            })
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
                                                    let URL = `${ureport}excel?_u=minio:ZxSaProCalculateInfo.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=工程结算台账(项目)报表`;
                                                    window.open(URL);
                                                }
                                            })
                                        }}>导出</Button></div>
                                );
                            },
                            span: 8
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
                        apiName: 'exportZxSaProCalculateInfo',
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
                                title: '业主合同与分包合同清单',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 2000,
                                children: [
                                    {
                                        title: '业主合同',
                                        dataIndex: 'title_11',
                                        key: 'title_11',
                                        width: 1000,
                                        children: [
                                            {
                                                title: '细目编号',
                                                dataIndex: 'workNo',
                                                key: 'workNo',
                                                width: 150
                                            },
                                            {
                                                title: '细目名称',
                                                dataIndex: 'workName',
                                                key: 'workName',
                                                width: 150
                                            },
                                            {
                                                title: '细目单位',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                width: 100
                                            },
                                            {
                                                title: '原合同清单',
                                                dataIndex: 'title_111',
                                                key: 'title_111',
                                                width: 300,
                                                children: [
                                                    {
                                                        title: '工程量',
                                                        dataIndex: 'contractQty',
                                                        key: 'contractQty',
                                                        width: 100
                                                    },
                                                    {
                                                        title: '单价',
                                                        dataIndex: 'contractPrice',
                                                        key: 'contractPrice',
                                                        width: 100
                                                    },
                                                    {
                                                        title: '工作量(元)',
                                                        dataIndex: 'contractAmt',
                                                        key: 'contractAmt',
                                                        width: 100
                                                    },
                                                ]
                                            },
                                            {
                                                title: '变更后清单',
                                                dataIndex: 'title_112',
                                                key: 'title_112',
                                                width: 300,
                                                children: [
                                                    {
                                                        title: '工程量',
                                                        dataIndex: 'quantity',
                                                        key: 'quantity',
                                                        width: 100
                                                    },
                                                    {
                                                        title: '单价',
                                                        dataIndex: 'price',
                                                        key: 'price',
                                                        width: 100
                                                    },
                                                    {
                                                        title: '工作量(元)',
                                                        dataIndex: 'amt',
                                                        key: 'amt',
                                                        width: 100
                                                    },
                                                ]
                                            }
                                        ]
                                    },
                                    {
                                        title: '分包合同',
                                        dataIndex: 'title_12',
                                        key: 'title_12',
                                        width: 1000,
                                        children: [
                                            {
                                                title: '细目编号',
                                                dataIndex: 'coWorkNo',
                                                key: 'coWorkNo',
                                                width: 150
                                            },
                                            {
                                                title: '细目名称',
                                                dataIndex: 'coWorkName',
                                                key: 'coWorkName',
                                                width: 150
                                            },
                                            {
                                                title: '细目单位',
                                                dataIndex: 'coUnit',
                                                key: 'coUnit',
                                                width: 100
                                            },
                                            {
                                                title: '原合同清单',
                                                dataIndex: 'title_121',
                                                key: 'title_121',
                                                width: 300,
                                                children: [
                                                    {
                                                        title: '工程量',
                                                        dataIndex: 'coContractQty',
                                                        key: 'coContractQty',
                                                        width: 100
                                                    },
                                                    {
                                                        title: '单价',
                                                        dataIndex: 'coContractPrice',
                                                        key: 'coContractPrice',
                                                        width: 100
                                                    },
                                                    {
                                                        title: '工作量(元)',
                                                        dataIndex: 'coContractAmt',
                                                        key: 'coContractAmt',
                                                        width: 100
                                                    },
                                                ]
                                            },
                                            {
                                                title: '变更后清单',
                                                dataIndex: 'title_122',
                                                key: 'title_122',
                                                width: 300,
                                                children: [
                                                    {
                                                        title: '工程量',
                                                        dataIndex: 'coQuantity',
                                                        key: 'coQuantity',
                                                        width: 100
                                                    },
                                                    {
                                                        title: '单价',
                                                        dataIndex: 'coPrice',
                                                        key: 'coPrice',
                                                        width: 100
                                                    },
                                                    {
                                                        title: '工作量(元)',
                                                        dataIndex: 'coAmt',
                                                        key: 'coAmt',
                                                        width: 100
                                                    },
                                                ]
                                            }
                                        ]
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '计量产值',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 480,
                                children: [
                                    {
                                        title: '工程量',
                                        dataIndex: 'title_21',
                                        key: 'title_21',
                                        width: 240,
                                        children: [
                                            {
                                                title: '本月',
                                                dataIndex: 'balQtyM',
                                                key: 'balQtyM',
                                                width: 120
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'balQtyK',
                                                key: 'balQtyK',
                                                width: 120
                                            }
                                        ]
                                    },
                                    {
                                        title: '工作量(元)',
                                        dataIndex: 'title_22',
                                        key: 'title_22',
                                        width: 240,
                                        children: [
                                            {
                                                title: '本月',
                                                dataIndex: 'balAmtM',
                                                key: 'balAmtM',
                                                width: 120
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'balAmtK',
                                                key: 'balAmtK',
                                                width: 120
                                            }
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '统计完成产值',
                                dataIndex: 'title_3',
                                key: 'title_3',
                                width: 480,
                                children: [
                                    {
                                        title: '工程量',
                                        dataIndex: 'title_31',
                                        key: 'title_31',
                                        width: 240,
                                        children: [
                                            {
                                                title: '本月',
                                                dataIndex: 'progressQtyM',
                                                key: 'progressQtyM',
                                                width: 120
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'progressQtyK',
                                                key: 'progressQtyK',
                                                width: 120
                                            }
                                        ]
                                    },
                                    {
                                        title: '工作量(元)',
                                        dataIndex: 'title_32',
                                        key: 'title_32',
                                        width: 240,
                                        children: [
                                            {
                                                title: '本月',
                                                dataIndex: 'progressAmtM',
                                                key: 'progressAmtM',
                                                width: 120
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'progressAmtK',
                                                key: 'progressAmtK',
                                                width: 120
                                            }
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '分包结算汇总',
                                dataIndex: 'title_4',
                                key: 'title_4',
                                width: 480,
                                children: [
                                    {
                                        title: '工程量',
                                        dataIndex: 'title_41',
                                        key: 'title_41',
                                        width: 240,
                                        children: [
                                            {
                                                title: '本月',
                                                dataIndex: 'coQtyM',
                                                key: 'coQtyM',
                                                width: 120
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'coQtyK',
                                                key: 'coQtyK',
                                                width: 120
                                            }
                                        ]
                                    },
                                    {
                                        title: '工作量(元)',
                                        dataIndex: 'title_42',
                                        key: 'title_42',
                                        width: 240,
                                        children: [
                                            {
                                                title: '本月',
                                                dataIndex: 'coThisTotalAmtM',
                                                key: 'coThisTotalAmtM',
                                                width: 120
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'coThisTotalAmtK',
                                                key: 'coThisTotalAmtK',
                                                width: 120
                                            }
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '数据对比分析',
                                dataIndex: 'title_6',
                                key: 'title_6',
                                width: 1680,
                                children: [
                                    {
                                        title: '统计与计量对比',
                                        dataIndex: 'title_61',
                                        key: 'title_61',
                                        width: 480,
                                        children: [
                                            {
                                                title: '统计-计量',
                                                dataIndex: 'title_611',
                                                key: 'title_611',
                                                width: 480,
                                                children: [
                                                    {
                                                        title: '工程量',
                                                        dataIndex: 'title_6111',
                                                        key: 'title_6111',
                                                        width: 240,
                                                        children: [
                                                            {
                                                                title: '本月',
                                                                dataIndex: 'tjlQtyM',
                                                                key: 'tjlQtyM',
                                                                width: 120
                                                            },
                                                            {
                                                                title: '累计',
                                                                dataIndex: 'tjlQtyK',
                                                                key: 'tjlQtyK',
                                                                width: 120
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        title: '工作量(元)',
                                                        dataIndex: 'title_6112',
                                                        key: 'title_6112',
                                                        width: 240,
                                                        children: [
                                                            {
                                                                title: '本月',
                                                                dataIndex: 'tjlAmtM',
                                                                key: 'tjlAmtM',
                                                                width: 120
                                                            },
                                                            {
                                                                title: '累计',
                                                                dataIndex: 'tjlAmtK',
                                                                key: 'tjlAmtK',
                                                                width: 120
                                                            }
                                                        ]
                                                    }
                                                ]
                                            }
                                        ]
                                    },
                                    {
                                        title: '统计与结算对比',
                                        dataIndex: 'title_62',
                                        key: 'title_62',
                                        width: 480,
                                        children: [
                                            {
                                                title: '统计-结算',
                                                dataIndex: 'title_621',
                                                key: 'title_621',
                                                width: 480,
                                                children: [
                                                    {
                                                        title: '工程量',
                                                        dataIndex: 'title_6211',
                                                        key: 'title_6211',
                                                        width: 240,
                                                        children: [
                                                            {
                                                                title: '本月',
                                                                dataIndex: 'tjsQtyM',
                                                                key: 'tjsQtyM',
                                                                width: 120
                                                            },
                                                            {
                                                                title: '累计',
                                                                dataIndex: 'tjsQtyK',
                                                                key: 'tjsQtyK',
                                                                width: 120
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        title: '工作量(元)',
                                                        dataIndex: 'title_6212',
                                                        key: 'title_6212',
                                                        width: 240,
                                                        children: [
                                                            {
                                                                title: '本月',
                                                                dataIndex: 'tjsAmtM',
                                                                key: 'tjsAmtM',
                                                                width: 120
                                                            },
                                                            {
                                                                title: '累计',
                                                                dataIndex: 'tjsAmtK',
                                                                key: 'tjsAmtK',
                                                                width: 120
                                                            }
                                                        ]
                                                    }
                                                ]
                                            }
                                        ]
                                    },
                                    {
                                        title: '结算与计量对比',
                                        dataIndex: 'title_63',
                                        key: 'title_63',
                                        width: 480,
                                        children: [
                                            {
                                                title: '结算-计量',
                                                dataIndex: 'title_631',
                                                key: 'title_631',
                                                width: 480,
                                                children: [
                                                    {
                                                        title: '工程量',
                                                        dataIndex: 'title_6311',
                                                        key: 'title_6311',
                                                        width: 240,
                                                        children: [
                                                            {
                                                                title: '本月',
                                                                dataIndex: 'jjQtyM',
                                                                key: 'jjQtyM',
                                                                width: 120
                                                            },
                                                            {
                                                                title: '累计',
                                                                dataIndex: 'jjQtyK',
                                                                key: 'jjQtyK',
                                                                width: 120
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        title: '工作量(元)',
                                                        dataIndex: 'title_6312',
                                                        key: 'title_6312',
                                                        width: 240,
                                                        children: [
                                                            {
                                                                title: '本月',
                                                                dataIndex: 'jjAmtM',
                                                                key: 'jjAmtM',
                                                                width: 120
                                                            },
                                                            {
                                                                title: '累计',
                                                                dataIndex: 'jjAmtK',
                                                                key: 'jjAmtK',
                                                                width: 120
                                                            }
                                                        ]
                                                    }
                                                ]
                                            }
                                        ]
                                    },
                                    {
                                        title: '结算与分包合同对比',
                                        dataIndex: 'title_64',
                                        key: 'title_64',
                                        width: 240,
                                        children: [
                                            {
                                                title: '分包合同-结算',
                                                dataIndex: 'title_641',
                                                key: 'title_641',
                                                width: 240,
                                                children: [
                                                    {
                                                        title: '工程量',
                                                        dataIndex: 'title_6311',
                                                        key: 'title_6311',
                                                        width: 120,
                                                        children: [
                                                            {
                                                                title: '累计',
                                                                dataIndex: 'fjQty',
                                                                key: 'fjQty',
                                                                width: 120
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        title: '工作量(元)',
                                                        dataIndex: 'title_6312',
                                                        key: 'title_6312',
                                                        width: 120,
                                                        children: [
                                                            {
                                                                title: '累计',
                                                                dataIndex: 'fjAmt',
                                                                key: 'fjAmt',
                                                                width: 120
                                                            }
                                                        ]
                                                    }
                                                ]
                                            }
                                        ]
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                width: 150,
                                key: 'remarks'
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;