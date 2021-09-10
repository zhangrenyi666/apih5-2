import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, Row, Col } from "antd";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
        size: 'small'
    },
    drawerConfig: {
        width: window.screen.width * 0.7
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
            orgID: '',//项目名称
            materialType: '',//单位名称
            eduLevel: '', //文化程度
            title: null,//职称
            bzDateStart: null,//开始时间
            bzDateEnd: null,//结束时间
            name: null,//辛明
            pos: null//岗位
        }
    }
    componentDidMount() {

    }
    render() {
        const { orgID, materialType, title, bzDateStart, bzDateEnd, name, pos, eduLevel } = this.state;
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
                                    label: '项目名称',
                                    field: 'orgID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect'
                                    },
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '单位名称',
                                    field: 'materialType',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'catName',
                                        value: 'id',
                                        children: 'zxSkResourceMaterialsList'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxSkResCategoryMaterialsAll'
                                    },
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '文化程度',
                                    type: 'select',
                                    field: 'eduLevel',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'resCode'
                                    },
                                    optionData: [
                                        {
                                            label: "博士",
                                            value: "0"
                                        },
                                        {
                                            label: "硕士",
                                            value: "1"
                                        },
                                        {
                                            label: "本科",
                                            value: "2"
                                        },
                                        {
                                            label: "大专",
                                            value: "3"
                                        },
                                        {
                                            label: "中专",
                                            value: "4"
                                        },
                                        {
                                            label: "高中",
                                            value: "5"
                                        },
                                        {
                                            label: "初中",
                                            value: "6"
                                        },
                                        {
                                            label: "小学",
                                            value: "7"
                                        }
                                    ],
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '职称',
                                    type: 'select',
                                    field: 'title',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'resCode'
                                    },
                                    optionData: [
                                        {
                                            label: '无',
                                            resCode: '0'
                                        },
                                        {
                                            label: '初级',
                                            resCode: '1'
                                        },
                                        {
                                            label: '中级',
                                            resCode: '2'
                                        },
                                        {
                                            label: '高级',
                                            resCode: '3'
                                        }
                                    ],
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '姓名',
                                    field: 'name',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '岗位',
                                    field: 'pos',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '填报起始时间',
                                    field: 'bzDateStart',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '填报终止时间',
                                    field: 'bzDateEnd',
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
                                                    orgID: '',
                                                    materialType: '',
                                                    title: '',
                                                    bzDateStart: null,
                                                    bzDateEnd: null,
                                                    name: '',
                                                    pos: '',
                                                    eduLevel: null
                                                }, () => {
                                                    this.setState({
                                                        orgID: value.orgID ? value.orgID : null,
                                                        materialType: value.materialType ? value.materialType : null,
                                                        title: value.title ? value.title : null,
                                                        bzDateStart: value.bzDateStart ? new Date(value.bzDateStart._d).getTime() : null,
                                                        bzDateEnd: value.bzDateEnd ? new Date(value.bzDateEnd._d).getTime() : null,
                                                        name: value.name ? value.name : null,
                                                        pos: value.pos ? value.pos : null,
                                                        eduLevel: value.eduLevel ? value.eduLevel : null,
                                                    }, () => {
                                                        this.table.refresh();
                                                    })
                                                })
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '确定导出数据吗?',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            value.orgID && filter.push('&orgID=' + value.orgID)
                                                            value.materialType && filter.push('&materialType=' + value.materialType)
                                                            value.title && filter.push('&title=' + value.title)
                                                            value.bzDateStart && filter.push('&bzDateStart=' + new Date(value.bzDateStart._d).getTime())
                                                            value.bzDateEnd && filter.push('&bzDateEnd=' + new Date(value.bzDateEnd._d).getTime())
                                                            value.name && filter.push('&name=' + value.name)
                                                            value.pos && filter.push('&pos=' + value.pos)
                                                            value.eduLevel && filter.push('&eduLevel=' + value.eduLevel)
                                                            var URL = `${ureport}excel?_u=file:zxEqEemployeeList.xml&url=${domain}&delFlag=0${filter.join('')}`;
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
                    {...config}
                    fetchConfig={{
                        apiName: 'ureportZxEqEemployeeListIdle',
                        otherParams: { orgID, materialType, title, bzDateStart, bzDateEnd, name, pos, eduLevel }
                    }}
                    actionBtns={[]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        }, {
                            table: {
                                title: '序号',
                                dataIndex: 'index',
                                key: 'index',
                                width: 50,
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName ',
                                key: 'orgName '
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '姓名',
                                dataIndex: 'name',
                                width: 200,
                                tooltip: 23,
                                key: 'name'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '年龄',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'age',
                                key: 'age'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '性别',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '文化程度',
                                width: 120,
                                dataIndex: 'eduLevel',
                                key: 'eduLevel',
                                render: (h) => {
                                    if (h === '0') {
                                        return '博士'
                                    } else if (h === '1') {
                                        return '硕士'
                                    } else if (h === '2') {
                                        return '本科'
                                    } else if (h === '3') {
                                        return '大专'
                                    } else if (h === '4') {
                                        return '中专'
                                    } else if (h === '5') {
                                        return '高中'
                                    } else if (h === '6') {
                                        return '初中'
                                    } else if (h === '7') {
                                        return '小学'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '职称',
                                width: 120,
                                dataIndex: 'title',
                                key: 'title',
                                render: (h) => {
                                    if (h === '0') {
                                        return '无'
                                    } else if (h === '1') {
                                        return '初级'
                                    } else if (h === '2') {
                                        return '中级'
                                    } else if (h === '3') {
                                        return '高级'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '所在岗位',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'pos',
                                key: 'pos'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '本岗工龄',
                                width: 120,
                                dataIndex: 'posAge',
                                key: 'posAge'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '备注',
                                width: 120,
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