import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Row, Col, Modal } from "antd";
import moment from 'moment';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
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
                                    label: '所属单位',
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
                                    placeholder: '请选择',
                                    span: 8,
                                    hide: ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all' ? false : true
                                },
                                {
                                    label: '使用单位',
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
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: 'ABCD分类',
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
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '设备分类',
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
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '机械名称',
                                    field: 'equipName',
                                    placeholder: '请输入',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '所在地点',
                                    field: 'usePlace',
                                    placeholder: '请输入',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '验收截至时间',
                                    field: 'acceptanceDate',
                                    span: 8
                                },
                                {
                                    type: 'number',
                                    label: '原值',
                                    field: 'orginalValue',
                                    placeholder: '请输入',
                                    span: 8
                                },
                                {
                                    label: '国产进口',
                                    field: 'isMadeinChina',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value',
                                    },
                                    optionData: [
                                        { label: '国产', value: '0' },
                                        { label: '进口', value: '1' },
                                    ],
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                this.table.refresh();
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '确定导出数据吗?',
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
                                                            filter.push('&selectTypeForIsNotJiXieYunShuFlag=只查设备分类不等于运输机械类的数据')
                                                            value.acceptanceDate && filter.push('&acceptanceDate=' + moment(value.acceptanceDate).valueOf())
                                                            value.isMadeinChina && filter.push('&isMadeinChina=' + value.isMadeinChina)
                                                            value.orginalValue && filter.push('&orginalValue=' + value.orginalValue)
                                                            var URL = `${ureport}excel?_u=minio:zxEqEquipListIdle.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=公司机械设备报表`;
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
                                selectTypeForIsNotJiXieYunShuFlag: '只查设备分类不等于运输机械类的数据',
                                companyIdSearch: selectData?.companyIdSearch,
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
                                title: '所属单位',
                                dataIndex: 'ownerOrgName',
                                key: 'ownerOrgName',
                                width: 200,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '设备类型',
                                dataIndex: 'abcTypeName',
                                key: 'abcTypeName',
                                width: 100,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '机械名称',
                                dataIndex: 'equipName',
                                key: 'equipName',
                                width: 150,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '型号',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格',
                                width: 120,
                                dataIndex: 'model',
                                key: 'model'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '总功率(KW)',
                                width: 120,
                                dataIndex: 'power',
                                key: 'power'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '国产总价(元)',
                                width: 140,
                                dataIndex: 'orginalValue',
                                key: 'orginalValue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '国产余值(元)',
                                width: 140,
                                dataIndex: 'leftValue',
                                key: 'leftValue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '国家厂家',
                                width: 180,
                                dataIndex: 'factory',
                                key: 'factory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '来源',
                                width: 150,
                                dataIndex: 'source',
                                key: 'source',
                                render: (h) => {
                                    if (h === '0') {
                                        return '局外调拨'
                                    } else if (h === '1') {
                                        return '局批自购'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '验收时间',
                                width: 120,
                                dataIndex: 'acceptanceDateStr',
                                key: 'acceptanceDateStr',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '出厂日期',
                                width: 130,
                                dataIndex: 'outFactoryDateStr',
                                key: 'outFactoryDateStr',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '出厂序列号',
                                width: 150,
                                dataIndex: 'outFactorySerial',
                                key: 'outFactorySerial',
                            },
                        },
                        {
                            table: {
                                title: '主机厂牌',
                                width: 150,
                                dataIndex: 'mainFactory',
                                key: 'mainFactory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '主机型号',
                                width: 150,
                                dataIndex: 'mainspec',
                                key: 'mainspec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '主机功率(KW)',
                                width: 150,
                                dataIndex: 'mainpower',
                                key: 'mainpower',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '主机序列号',
                                width: 150,
                                dataIndex: 'mainserial',
                                key: 'mainserial'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '主机出厂日期',
                                width: 120,
                                dataIndex: 'mainoutfactory',
                                key: 'mainoutfactory',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '副机厂牌',
                                width: 150,
                                dataIndex: 'viceFactory',
                                key: 'viceFactory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '副机型号',
                                width: 150,
                                dataIndex: 'vicespec',
                                key: 'vicespec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '副机功率',
                                width: 150,
                                dataIndex: 'vicepower',
                                key: 'vicepower'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '副机出厂日期',
                                width: 120,
                                dataIndex: 'viceoutfactory',
                                key: 'viceoutfactory',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '底盘厂家',
                                width: 150,
                                dataIndex: 'downfactory',
                                key: 'downfactory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '底盘型式',
                                width: 150,
                                dataIndex: 'downspec',
                                key: 'downspec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '底盘系列号',
                                width: 150,
                                dataIndex: 'downserial',
                                key: 'downserial'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '底盘出厂日期',
                                width: 140,
                                dataIndex: 'downoutfactory',
                                key: 'downoutfactory',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '外形尺寸',
                                width: 150,
                                dataIndex: 'heightlong',
                                key: 'heightlong'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '自重(t)',
                                width: 150,
                                dataIndex: 'weight',
                                key: 'weight',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '使用单位',
                                width: 150,
                                dataIndex: 'useOrgName',
                                key: 'useOrgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '国产或进口',
                                dataIndex: 'isMadeinChina',
                                key: 'isMadeinChina',
                                width: 200,
                                render: (h) => {
                                    if (h === '0') {
                                        return '国产'
                                    } else if (h === '1') {
                                        return '进口'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '设备管理编号',
                                dataIndex: 'equipNo',
                                key: 'equipNo',
                                width: 200,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '财务固定资产编号',
                                dataIndex: 'financeNo',
                                key: 'financeNo',
                                width: 200,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '车辆牌照号',
                                width: 150,
                                dataIndex: 'passNo',
                                key: 'passNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                width: 180,
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            isInForm: false
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;