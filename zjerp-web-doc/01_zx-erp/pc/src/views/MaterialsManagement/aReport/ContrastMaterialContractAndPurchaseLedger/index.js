import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import QnnForm from "../../../modules/qnn-table/qnn-form";
import { Button, Modal } from "antd";
const { confirm } = Modal;
const config = {
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
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            formData: {
                queryComID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
            }
        }
    }
    render () {
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
                            label: '??????',
                            field: 'queryComID',
                            hide: true
                        },
                        {
                            label: '????????????',
                            field: 'queryOrgID',
                            type: 'select',
                            showSearch: true,
                            allowClear: formData?.queryOrgID ? false : true,
                            required: true,
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
                            label: '????????????',
                            field: 'queryContractName',
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '????????????',
                            field: 'queryOutOrgName',
                            type: 'string',
                            span: 8
                        },
                        {
                            field: 'queryCatName',
                            type: 'string',
                            hide: true
                        },
                        {
                            label: '????????????',
                            field: 'queryCatID',
                            type: 'select',
                            showSearch: true,
                            optionConfig: {
                                label: 'catName', //?????? label
                                value: 'id',
                                linkageFields:{
                                    queryCatName:'catName'
                                }
                            },
                            fetchConfig: {
                                apiName: 'getZxSkResCategoryMaterialsList',
                                otherParams: {
                                    parentID: '0002'
                                }
                            },
                            span: 8
                        },
                        {
                            field: 'queryResID',
                            type: 'string',
                            hide: true
                        },
                        {
                            label: '????????????',
                            field: 'queryResCode',
                            type: 'selectByQnnTable',
                            optionConfig: {//??????????????????
                                label: 'resCode',
                                value: 'resCode',
                                linkageFields:{
                                    queryResID:'id'
                                }
                            },
                            parent: 'queryCatID',
                            dropdownMatchSelectWidth: 800,
                            qnnTableConfig: {
                                antd: {
                                    rowKey: "id"
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                                    params: {
                                        id: 'queryCatID'
                                    }
                                },
                                searchBtnsStyle: "inline",
                                formConfig: [
                                    {
                                        isInForm: false,
                                        isInSearch: true,
                                        table: {
                                            dataIndex: "resCode",
                                            title: "??????",
                                            width: 100
                                        },
                                        form: {
                                            type: 'string'
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        isInSearch: true,
                                        table: {
                                            dataIndex: "resName",
                                            title: "??????",
                                            width: 100
                                        },
                                        form: {
                                            type: 'string'
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        isInSearch: true,
                                        table: {
                                            dataIndex: "spec",
                                            title: "????????????",
                                        },
                                        form: {
                                            type: "string"
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        isInSearch: true,
                                        table: {
                                            dataIndex: "unit",
                                            title: "??????",
                                        },
                                        form: {
                                            type: "string"
                                        }
                                    }
                                ]
                            },
                            showSearch: true,
                            placeholder: '?????????',
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        this.form.form.validateFields().then((value) => {
                                            this.setState({
                                                formData: value
                                            })
                                        })
                                    }}>??????</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '??????????????????????',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    let stringData = '';
                                                    for (let key in value) {
                                                        if (value?.[key]) {
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:ZxSkContractContrastRptWen.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=?????????????????????????????????`;
                                                    window.open(URL);
                                                }
                                            })
                                        }}>??????</Button></div>
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
                    antd={{
                        rowKey: 'id',
                        size: 'small',
                        summary: (pageData) => {
                            let totalContractSum = 0;
                            let totalInAmt = 0;
                            pageData.forEach(({ contractSum, inAmt }) => {
                                totalContractSum += contractSum ? contractSum : 0;
                                totalInAmt += inAmt ? inAmt : 0;
                            });
                            return (
                                <>
                                    <QnnTable.Summary.Row>
                                        <QnnTable.Summary.Cell index={0}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={1}>??????</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={2}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={3}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={4}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={5}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={6}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={7}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={8}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={9}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={10}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={11}>{totalContractSum}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={12}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={13}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={14}>{totalInAmt}</QnnTable.Summary.Cell>
                                    </QnnTable.Summary.Row>
                                </>
                            )
                        }
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'exportZxSkContractContrastRptWen',
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
                                title: '??????',
                                dataIndex: 'index',
                                key: 'index',
                                width: 50,
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
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
                                dataIndex: 'firstName',
                                width: 150,
                                key: 'firstName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractNo',
                                width: 150,
                                key: 'contractNo',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'secondName',
                                width: 150,
                                key: 'secondName'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'workType',
                                width: 120,
                                key: 'workType'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'workNo',
                                width: 120,
                                key: 'workNo'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'workName',
                                width: 120,
                                key: 'workName'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'spec',
                                width: 100,
                                key: 'spec'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'unit',
                                width: 100,
                                key: 'unit'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'price',
                                width: 120,
                                key: 'price'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'qty',
                                width: 120,
                                key: 'qty'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'contractSum',
                                width: 120,
                                key: 'contractSum'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'inPrice',
                                width: 120,
                                key: 'inPrice'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'inQty',
                                width: 120,
                                key: 'inQty'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'inAmt',
                                width: 120,
                                key: 'inAmt'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;