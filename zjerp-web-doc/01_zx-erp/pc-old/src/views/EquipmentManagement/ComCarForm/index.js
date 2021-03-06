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
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            useOrgID: '',
            ownerOrgId: '',
            resCatalogID: null,
            equipName: '',
            abcType: '',
            usePlace: '',
            acceptanceDate: null,
            isMadeinChina: null,
            orginalValue: '',
        }
    }
    componentDidMount() {

    }
    render() {
        const { useOrgID, ownerOrgId, resCatalogID, equipName, abcType, usePlace, acceptanceDate, isMadeinChina, orginalValue } = this.state;
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
        return (
            <div>
                <Row>
                    <Col span={24}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
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
                                    field: 'ownerOrgId',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProject'
                                    },
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'useOrgID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProject'
                                    },
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    label: 'ABC??????',
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
                                            parentID: '0003'
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
                                    type: 'string',
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
                                                let value = this.formHasTicket.form.getFieldsValue();
                                                this.setState({
                                                    useOrgID: '',
                                                    ownerOrgId: '',
                                                    resCatalogID,
                                                    equipName: '',
                                                    abcType: '',
                                                    usePlace: '',
                                                    acceptanceDate: null,
                                                    isMadeinChina: null,
                                                    orginalValue: ''
                                                }, () => {
                                                    this.setState({
                                                        useOrgID: value.useOrgID ? value.useOrgID : null,
                                                        ownerOrgId: value.ownerOrgId ? value.ownerOrgId : null,
                                                        resCatalogID: value.resCatalogID ? value.resCatalogID : null,
                                                        equipName: value.equipName ? value.equipName : null,
                                                        abcType: value.abcType ? value.abcType : null,
                                                        usePlace: value.usePlace ? value.usePlace : null,
                                                        acceptanceDate: value.acceptanceDate ? moment(value.acceptanceDate).valueOf() : null,
                                                        isMadeinChina: value.isMadeinChina ? value.isMadeinChina : null,
                                                        orginalValue: value.orginalValue ? value.orginalValue : null,
                                                    }, () => {
                                                        this.table.refresh();
                                                    })
                                                })
                                            }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '??????????????????????',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            value.useOrgID && filter.push('&useOrgID=' + value.useOrgID)
                                                            value.ownerOrgId && filter.push('&ownerOrgId=' + value.ownerOrgId)
                                                            value.resCatalogID && filter.push('&resCatalogID=' + value.resCatalogID)
                                                            value.equipName && filter.push('&equipName=' + value.equipName)
                                                            value.abcType && filter.push('&abcType=' + value.abcType)
                                                            value.usePlace && filter.push('&usePlace=' + value.usePlace)
                                                            value.acceptanceDate && filter.push('&acceptanceDate=' + moment(value.acceptanceDate).valueOf())
                                                            value.isMadeinChina && filter.push('&isMadeinChina=' + value.isMadeinChina)
                                                            value.orginalValue && filter.push('&orginalValue=' + value.orginalValue)
                                                            var URL = `${ureport}excel?_u=file:zxEqEquipListIdle.xml&url=${domain}&delFlag=0&isXianzhi=1${filter.join('')}`;
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
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxEqEquipListForReport',
                        otherParams: { isXianzhi: '1',useOrgID, ownerOrgId, resCatalogID, equipName, abcType, usePlace, acceptanceDate, isMadeinChina, orginalValue }
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
                                width: 170,
                                tooltip:17
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
                                dataIndex: 'equipName',
                                key: 'equipName',
                                width: 150,
                                tooltip:15
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
                                tooltip: 23,
                                dataIndex: 'factory',
                                key: 'factory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 150,
                                tooltip: 15,
                                dataIndex: 'source',
                                key: 'source'
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
                                type: 'number'
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
                                tooltip:15,
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
                                type: 'number'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                tooltip: 15,
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
                                tooltip: 15,
                                dataIndex: 'passNo',
                                key: 'passNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 180,
                                tooltip:18,
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