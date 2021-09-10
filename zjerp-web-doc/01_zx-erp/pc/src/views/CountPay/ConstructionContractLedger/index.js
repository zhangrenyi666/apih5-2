import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import ReactEcharts from 'echarts-for-react';
import { Button, Modal, message as Msg } from "antd";
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
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
            formData: {
                queryComID:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
            },
            visible: false,
            ModalData: null,
            ZXData: []
        }
    }
    handleCancel = () => {
        this.setState({ visible: false });
    }
    getOption = () => {
        const { ZXData } = this.state;
        const option = {
            tooltip: {
                trigger: 'item',
                confine: true
            },
            xAxis: {
                type: 'category',
                data: ZXData.map((item) => {
                    return item.periodStr;
                })
            },
            yAxis: {
                name:'毛利率(%)',
                type: 'value'
            },
            color:'#06a0ce',
            series: [{
                data: ZXData.map((item) => {
                    return item.mll;
                }),
                type: 'line',
                smooth: true
            }]
        }
        return option
    }
    render() {
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { departmentId, formData, visible, ModalData, queryOrgID } = this.state;
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
                            label: '所属事业部',
                            field: 'queryDep',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'suoShuShiYeBu'
                                }
                            },
                            showSearch: true,
                            span: 8
                        },
                        {
                            label: '所属单位',
                            field: 'queryComID',
                            type: 'select',
                            optionConfig: {
                                label: 'companyName',
                                value: 'companyId'
                            },
                            fetchConfig: {
                                apiName: 'getSysCompanyBySelect',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            disabled:curCompany?.ext1 === '3' || curCompany?.ext1 === '4' || queryOrgID ? true : false,
                            showSearch: true,
                            allowClear: false,
                            span: 8
                        },
                        {
                            label: '项目名称',
                            field: 'queryOrgID',
                            type: 'select',
                            optionConfig: {
                                label: 'departmentName',
                                value: 'departmentId'
                            },
                            parent: 'queryComID',
                            fetchConfig: {
                                apiName: 'getSysProjectList',
                                params: {
                                    departmentId: 'queryComID'
                                }
                            },
                            disabled:curCompany?.ext1 === '3' || curCompany?.ext1 === '4' || queryOrgID ? true : false,
                            showSearch: true,
                            allowClear: formData?.queryOrgID ? false : true,
                            span: 8
                        },
                        {
                            label: '查询时间',
                            field: 'queryDate',
                            type: 'rangeDate',
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}>
                                        <Button type="primary" onClick={() => {
                                            let value = this.form.form.getFieldsValue();
                                            if (value?.queryDate?.length) {
                                                value.queryBeginDate = moment(value.queryDate[0]).startOf('date').valueOf();
                                                value.queryEndDate = moment(value.queryDate[1]).endOf('date').valueOf();
                                            }
                                            delete value.queryDate;
                                            this.setState({
                                                formData: value
                                            })
                                        }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    if (value?.queryDate?.length) {
                                                        value.queryBeginDate = moment(value.queryDate[0]).startOf('date').valueOf();
                                                        value.queryEndDate = moment(value.queryDate[1]).endOf('date').valueOf();
                                                    }
                                                    delete value.queryDate;
                                                    let stringData = '';
                                                    for (let key in value) {
                                                        if (value?.[key]) {
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:ZxCsDqRpt.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=建造合同台账报表`;
                                                    window.open(URL);
                                                }
                                            })
                                        }}>导出</Button>
                                    </div>
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
                        apiName: 'exportZxCsDqRpt',
                        otherParams: {
                            ...formData
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
                            table: {
                                title: '所属单位',
                                dataIndex: 'comName',
                                key: 'comName',
                                width: 150,
                                fixed: 'left'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 150,
                                fixed: 'left'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '工程类别',
                                dataIndex: 'projType',
                                key: 'projType',
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'gongChengLeiBie'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '所属事业部',
                                dataIndex: 'bizDep',
                                key: 'bizDep',
                                width: 120,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //默认 label
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'suoShuShiYeBu'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '期次',
                                dataIndex: 'periodStr',
                                key: 'periodStr',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '上报时间',
                                dataIndex: 'reportTime',
                                key: 'reportTime',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同开工日期',
                                dataIndex: 'planStartDate',
                                key: 'planStartDate',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同竣工日期',
                                dataIndex: 'planEndDate',
                                key: 'planEndDate',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同金额',
                                dataIndex: 'contractCost',
                                key: 'contractCost',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '初始建造合同',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 400,
                                children: [
                                    {
                                        title: '总收入',
                                        dataIndex: 'num1',
                                        key: 'num1',
                                        width: 100
                                    },
                                    {
                                        title: '总成本',
                                        dataIndex: 'num2',
                                        key: 'num2',
                                        width: 100
                                    },
                                    {
                                        title: '毛利',
                                        dataIndex: 'num3',
                                        key: 'num3',
                                        width: 100,
                                    },
                                    {
                                        title: '毛利率(%)',
                                        dataIndex: 'num4',
                                        key: 'num4',
                                        width: 100
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '当前建造合同',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 400,
                                children: [
                                    {
                                        title: '总收入',
                                        dataIndex: 'num5',
                                        key: 'num1',
                                        width: 100
                                    },
                                    {
                                        title: '总成本',
                                        dataIndex: 'num6',
                                        key: 'num2',
                                        width: 100
                                    },
                                    {
                                        title: '毛利',
                                        dataIndex: 'num7',
                                        key: 'num3',
                                        width: 100,
                                    },
                                    {
                                        title: '毛利率(%)',
                                        dataIndex: 'num8',
                                        key: 'num4',
                                        width: 100
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '审批进度',
                                dataIndex: 'auditStatus',
                                key: 'auditStatus',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '是否有总部',
                                dataIndex: 'isHaveZb',
                                key: 'isHaveZb',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目所在地',
                                dataIndex: 'location',
                                key: 'location',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目总经、项目经理',
                                dataIndex: 'xmas',
                                key: 'xmas',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '毛利率走势图',
                                dataIndex: 'mlv',
                                key: 'mlv',
                                width: 120,
                                fixed: 'right',
                                render: (data, rowData) => {
                                    if (rowData.periodOrderStr === '0') {
                                        return <a onClick={() => {
                                            this.props.myFetch('exportZxctCsdqLineChart', { queryOrgID: rowData.orgId }).then(({ success, message, data }) => {
                                                if (success) {
                                                    this.setState({
                                                        visible: true,
                                                        ModalData: rowData,
                                                        ZXData: data
                                                    })
                                                } else {
                                                    Msg.error(message);
                                                }
                                            });
                                        }}>折线图</a>;
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                    ]}
                />
                <Modal
                    width='1200px'
                    title="毛利率走势图"
                    visible={visible}
                    footer={null}
                    onCancel={this.handleCancel}
                    bodyStyle={{ width: '1200px' }}
                    centered={true}
                    wrapClassName={'modals'}
                >
                    <div>
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
                                formItemLayout={{
                                    labelCol: {
                                        xs: { span: 10 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 7 },
                                        sm: { span: 7 }
                                    }
                                }}
                                formConfig={[
                                    {
                                        label: '项目名称',
                                        field: 'queryOrgID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'departmentName',
                                            value: 'departmentId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysProjectBySelect'
                                        },
                                        initialValue: ModalData?.orgId,
                                        onChange:(val) => {
                                            this.props.myFetch('exportZxctCsdqLineChart', { queryOrgID: val }).then(({ success, message, data }) => {
                                                if (success) {
                                                    this.setState({
                                                        ZXData: data
                                                    })
                                                } else {
                                                    Msg.error(message);
                                                }
                                            });
                                        }
                                    }
                                ]}
                            />
                        </div>
                        <div style={{height:'30vh'}}>
                            <ReactEcharts
                                style={{ height: "100%" }}
                                option={this.getOption()}
                                notMerge={true}
                                lazyUpdate={true}
                                theme={"theme_name"}
                            />
                        </div>
                    </div>
                </Modal>
            </div>
        );
    }
}

export default index;