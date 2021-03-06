import React from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Row, Col, Modal } from "antd";
import moment from 'moment';
import Apih5 from 'qnn-apih5'
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    paginationConfig: false,
    isShowRowSelect: false
};
class index extends Apih5 {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            ext1: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.ext1,
            lockID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId && lockProject?.projectId !== 'all') ? lockProject.projectId : null,
            useOrgID: (curCompany?.ext1 === '1' || curCompany?.ext1 === '2') ? curCompany?.companyId : curCompany?.projectId,
            lockProject
        }
    }
    render() {
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        const { departmentId, ext1, lockID, useOrgID, lockProject } = this.state;
        return (
            <div>
                <Row>
                    <Col span={24}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            wrappedComponentRef={(me) => {
                                this.formHasTicket = me;
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
                                    field: 'companyIdSearch',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyBySelect',
                                        otherParams: { departmentId }
                                    },
                                    placeholder: '?????????',
                                    span: 8,
                                    hide: ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all' ? false : true
                                },
                                {
                                    label: '????????????',
                                    field: 'useOrgIDSearch',
                                    type: 'select',
                                    showSearch: true,
                                    hide: () => {
                                        if ((ext1 === '1' || ext1 === '2') && lockProject.projectId && lockProject.projectId === 'all') {
                                            return false
                                        }
                                        return true
                                    },
                                    dependencies: ['companyIdSearch'],
                                    dependenciesReRender: true,
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProjectList',
                                        otherParams: (obj) => {
                                            if (ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all') {
                                                return {
                                                    companyId: obj?.form?.getFieldValue('companyIdSearch')
                                                }
                                            }
                                            return {
                                                companyId: departmentId
                                            }
                                        }
                                    },
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    label: 'ABCD??????',
                                    field: 'abcType',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'globalCode',
                                        value: 'id',
                                    },
                                    fetchConfig: () => {
                                        return {
                                            apiName: "getZxEqGlobalCodeList",
                                            otherParams: {
                                                categoryID: "category100203",
                                                startFlag: '1'
                                            }
                                        }
                                    },
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'resCatalogID',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'catName',
                                        value: 'id',
                                    },
                                    fetchConfig: {
                                        apiName: "getZxEqResCategoryList",
                                        otherParams: {
                                            parentID: '0003',
                                            isGroup: '1'
                                        }
                                    },
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '????????????',
                                    field: 'equipName',
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '????????????',
                                    field: 'usePlace',
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '??????????????????',
                                    field: 'acceptanceDate',
                                    span: 8
                                },
                                {
                                    type: 'number',
                                    label: '??????',
                                    field: 'orginalValue',
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'isMadeinChina',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value',
                                    },
                                    optionData: [
                                        { label: '??????', value: '0' },
                                        { label: '??????', value: '1' },
                                    ],
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                this.table.refresh();
                                            }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '??????????????????????',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            !lockID && !value.useOrgIDSearch && filter.push('&useOrgID=' + useOrgID)
                                                            !value.useOrgIDSearch && lockID && filter.push('&useOrgIDLock=' + lockID)
                                                            value.useOrgIDSearch && filter.push('&useOrgIDSearch=' + value.useOrgIDSearch)
                                                            value.companyIdSearch && filter.push('&companyIdSearch=' + value.companyIdSearch)
                                                            value.resCatalogID && filter.push('&resCatalogID=' + value.resCatalogID)
                                                            value.equipName && filter.push('&equipName=' + value.equipName)
                                                            value.abcType && filter.push('&abcType=' + value.abcType)
                                                            value.usePlace && filter.push('&usePlace=' + value.usePlace)
                                                            filter.push('&selectTypeForJiXieYunShuFlag=??????????????????=????????????????????????')
                                                            value.acceptanceDate && filter.push('&acceptanceDate=' + moment(value.acceptanceDate).valueOf())
                                                            value.isMadeinChina && filter.push('&isMadeinChina=' + value.isMadeinChina)
                                                            value.orginalValue && filter.push('&orginalValue=' + value.orginalValue)
                                                            var URL = `${ureport}excel?_u=minio:zxEqEquipListIdle.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=??????????????????`;
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
                    </Col>
                </Row>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    pageSize={9999}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxEqEquipListForReport',
                        otherParams: () => {
                            let selectData = this.formHasTicket?.form?.getFieldsValue()
                            return {
                                useOrgID: lockID ? null : selectData?.useOrgIDSearch ? null : useOrgID,
                                useOrgIDLock: selectData?.useOrgIDSearch ? null : lockID,
                                useOrgIDSearch: selectData?.useOrgIDSearch,
                                companyIdSearch: selectData?.companyIdSearch,
                                selectTypeForJiXieYunShuFlag: '??????????????????=????????????????????????',
                                resCatalogID: selectData?.resCatalogID,
                                equipName: selectData?.equipName,
                                abcType: selectData?.abcType,
                                usePlace: selectData?.usePlace,
                                acceptanceDate: selectData?.acceptanceDate ? moment(selectData.acceptanceDate).valueOf() : null,
                                orginalValue: selectData?.orginalValue,
                                isMadeinChina: selectData?.isMadeinChina,
                            }
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
                                title: '????????????',
                                dataIndex: 'ownerOrgName',
                                key: 'ownerOrgName',
                                width: 200
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'abcTypeName',
                                key: 'abcTypeName',
                                width: 100,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'equipName',
                                key: 'equipName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 120,
                                dataIndex: 'model',
                                key: 'model'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????(KW)',
                                width: 120,
                                dataIndex: 'power',
                                key: 'power'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????(???)',
                                width: 140,
                                dataIndex: 'orginalValue',
                                key: 'orginalValue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????(???)',
                                width: 140,
                                dataIndex: 'leftValue',
                                key: 'leftValue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 180,
                                dataIndex: 'factory',
                                key: 'factory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 150,
                                dataIndex: 'source',
                                key: 'source',
                                render: (h) => {
                                    if (h === '0') {
                                        return '????????????'
                                    } else if (h === '1') {
                                        return '????????????'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'acceptanceDateStr',
                                key: 'acceptanceDateStr',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 130,
                                dataIndex: 'outFactoryDateStr',
                                key: 'outFactoryDateStr',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 150,
                                dataIndex: 'outFactorySerial',
                                key: 'outFactorySerial',
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'mainFactory',
                                key: 'mainFactory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'mainspec',
                                key: 'mainspec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????(KW)',
                                width: 150,
                                dataIndex: 'mainpower',
                                key: 'mainpower',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 150,
                                dataIndex: 'mainserial',
                                key: 'mainserial'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                width: 120,
                                dataIndex: 'mainoutfactory',
                                key: 'mainoutfactory',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'viceFactory',
                                key: 'viceFactory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'vicespec',
                                key: 'vicespec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'vicepower',
                                key: 'vicepower'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                width: 120,
                                dataIndex: 'viceoutfactory',
                                key: 'viceoutfactory',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'downfactory',
                                key: 'downfactory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'downspec',
                                key: 'downspec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 150,
                                dataIndex: 'downserial',
                                key: 'downserial'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                width: 140,
                                dataIndex: 'downoutfactory',
                                key: 'downoutfactory',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'heightlong',
                                key: 'heightlong'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????(t)',
                                width: 150,
                                dataIndex: 'weight',
                                key: 'weight',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'useOrgName',
                                key: 'useOrgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'isMadeinChina',
                                key: 'isMadeinChina',
                                width: 200,
                                render: (h) => {
                                    if (h === '0') {
                                        return '??????'
                                    } else if (h === '1') {
                                        return '??????'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'equipNo',
                                key: 'equipNo',
                                width: 200,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'financeNo',
                                key: 'financeNo',
                                width: 200,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 150,
                                dataIndex: 'passNo',
                                key: 'passNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 150,
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            isInForm: false
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;