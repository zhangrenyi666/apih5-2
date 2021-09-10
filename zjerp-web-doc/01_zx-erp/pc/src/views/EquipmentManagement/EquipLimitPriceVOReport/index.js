import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
const config = {
    antd: {
        rowKey: 'zxEqEquipLimitPriceId',
        size: 'small'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect: false
};
class index extends Component {
    render() {
        const { companyId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                    {...config}
                    fetchConfig={{
                        apiName: 'ureportZxEqEquipLimitPriceVOIdle',
                        otherParams: {
                            orgID: ext1 === '2' ? companyId : null,
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxEqEquipLimitPriceId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '公司名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 170,
                                tooltip: 17
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '数据期次',
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                width: 150,
                                render: (data) => {
                                    if (data) {
                                        return moment(data).month() === 0 ? (moment(data).format('YYYY') + '/上半年') : (moment(data).format('YYYY') + '/下半年');
                                    } else {
                                        return null;
                                    }
                                }
                            },
                            form: {
                                type: 'halfYear',
                                spanForm: 8
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '所在省份',
                                dataIndex: 'province',
                                width: 150,
                                key: 'province',
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
                                        itemId: 'shengfenjianchengdaima'
                                    }
                                },
                                allowClear: false,
                                showSearch: false,
                                placeholder: '请选择',
                            },
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '设备名称',
                                dataIndex: 'equipName',
                                width: 170,
                                tooltip: 17,
                                key: 'equipName'
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '厂家',
                                width: 170,
                                dataIndex: 'factory',
                                key: 'factory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格型号',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 150,
                            },
                            isInForm: false
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '工作时间',
                                width: 150,
                                dataIndex: 'workTime',
                                key: 'workTime',
                                render: (h) => {
                                    if (h === '0') {
                                        return '单班'
                                    } else if (h === '1') {
                                        return '双班'
                                    }
                                }
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                optionData: [
                                    { itemName: '单班', itemId: '0' },
                                    { itemName: '双班', itemId: '1' },
                                ],
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '租赁情况',
                                width: 150,
                                dataIndex: 'rentContent',
                                key: 'rentContent',
                                render: (h) => {
                                    if (h === '0') {
                                        return '六个月以下'
                                    } else if (h === '1') {
                                        return '六个月以上'
                                    }
                                }

                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                optionData: [
                                    { itemName: '六个月以下', itemId: '0' },
                                    { itemName: '六个月以上', itemId: '1' },
                                ],
                            }
                        },
                        {
                            table: {
                                title: '燃油情况',
                                width: 150,
                                dataIndex: 'ranyouQingkuang',
                                key: 'ranyouQingkuang',
                                render: (h) => {
                                    if (h === '0') {
                                        return '甲方承担'
                                    } else if (h === '1') {
                                        return '乙方承担'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '租赁限价（元/台.月）',
                                width: 170,
                                dataIndex: 'rentPrice',
                                key: 'rentPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '其他说明',
                                width: 200,
                                dataIndex: 'remarks',
                                key: 'remarks'
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