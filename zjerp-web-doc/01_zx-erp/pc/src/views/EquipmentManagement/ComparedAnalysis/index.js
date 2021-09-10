import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Spin, Button } from 'antd';
import QnnForm from "../../modules/qnn-table/qnn-form";
import ReactEcharts from 'echarts-for-react';
import { DownOutlined, LeftOutlined } from '@ant-design/icons';
import moment from 'moment';
const configs = {
    antd: {
        rowKey: 'departmentId',
        size: 'small'
    },
    paginationConfig: false,
    isShowRowSelect: false
}
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            loading: false,
            showReactEcharts: true,
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            ext1: curCompany.ext1,
            isLocked: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? true : false) : true,
            lockedOrgFirm: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
        }
    }


    getOptionTow = () => {
        const { allqty } = this.state;
        var option = {
            xAxis: {
                type: 'category',
                data: ['上期', '本期']
            },
            tooltip: {
                trigger: 'axis'
            },
            yAxis: [
                {
                    type: 'value',
                    name: '原值(万元)',
                },
            ],
            series: [{
                data: allqty,
                type: 'bar'
            }]
        };
        return option;
    }

    render() {
        const { loading, showReactEcharts, departmentId, ext1, isLocked,lockedOrgFirm } = this.state;
        return (
            <Spin spinning={loading}>
                <div>
                    <div style={{ height: '40%', marginBottom: 10 }}>
                        <div style={{ textAlign: 'right' }}>
                            <Button
                                type="primary"
                                icon={showReactEcharts ? <DownOutlined /> : <LeftOutlined />}
                                onClick={() => {
                                    this.setState({ showReactEcharts: !showReactEcharts })
                                }}
                                size='small'
                            />
                        </div>
                        {showReactEcharts ? <ReactEcharts
                            option={this.getOptionTow()}
                            notMerge={true}
                            lazyUpdate={true}
                            theme={"theme_name"}
                        ></ReactEcharts> : null}
                    </div>
                    <div>
                        <QnnForm
                            wrappedComponentRef={(me) => {
                                this.formHasTicket = me;
                            }}
                            fetch={this.props.myFetch}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 6 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 18 },
                                    sm: { span: 18 }
                                }
                            }}
                            formConfig={[
                                {
                                    type: 'rangeDate',
                                    label: '对比时间',
                                    field: 'Date',
                                    span: 8,
                                    required: true
                                },
                                {
                                    type: 'select',
                                    label: '设备分类',
                                    field: 'resCatalogID',
                                    span: 8,
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
                                },
                                {
                                    type: 'select',
                                    label: 'ABCD分类',
                                    field: 'abcType',
                                    span: 8,
                                    optionConfig: {
                                        label: 'globalCode',
                                        value: 'id'
                                    },
                                    fetchConfig: {
                                        apiName: "getZxEqGlobalCodeList",
                                        otherParams: {
                                            categoryID: "category100203",
                                            startFlag: '1'
                                        }
                                    }
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                if (!this.formHasTicket?.form?.getFieldValue('Date')) {
                                                    Msg.warn('请选择对比时间')
                                                    return
                                                }
                                                this.table.refresh();
                                            }}>查询</Button></div>
                                        );
                                    },
                                    span: 2
                                },
                                {
                                    type: "component",
                                    field: "component1",
                                    Component: obj => {
                                        return (
                                            <div style={{ background: '#f0f2f5', padding: '8px 12px', color: 'red' }}>
                                                请选择对比时间
                                            </div>
                                        );
                                    }
                                },
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
                            fetchConfig={() => {
                                const selectData = this.formHasTicket?.form?.getFieldsValue()
                                if (selectData?.Date) {
                                    return {
                                        apiName: 'getZxEqEquipListForComparedWith',
                                        otherParams: {
                                            departmentPath: isLocked ? null : '9999999999',
                                            departmentParentId2: isLocked ? lockedOrgFirm : ext1 === '2' ? departmentId : null,
                                            resCatalogID: selectData?.resCatalogID,
                                            abcType: selectData?.abcType,
                                            beginDate: selectData?.Date?.[0] ? moment(selectData.Date[0]).valueOf() : null,
                                            endDate: selectData?.Date?.[1] ? moment(selectData.Date[1]).valueOf() : null,
                                            lastEndDate: selectData?.Date?.[1] ? moment(selectData.Date[1]).valueOf() - 31536000000 : null,
                                            lastBeginDate: selectData?.Date?.[0] ? moment(selectData.Date[0]).valueOf() - 31536000000 : null,
                                        },
                                        success: ({ data, success }) => {
                                            if (success && data.length > 0) {
                                                const list = { allqty: [] }
                                                let { lastOrg, org } = data[0]
                                                list.allqty.push(
                                                    lastOrg ? lastOrg : 0,
                                                    org ? org : 0
                                                )
                                                this.setState({ allqty: [...list.allqty] })
                                            }
                                        }
                                    }
                                }
                                return {}
                            }}
                            {...configs}
                            formConfig={[
                                {
                                    table: {
                                        title: '机构',
                                        dataIndex: 'departmentName',
                                        key: 'departmentName',
                                        width: 360,
                                        fixed: 'left',
                                        onClick: ({
                                            rowData,
                                            qnnTableInstance: {
                                                getVTableData,
                                                setTableData,
                                                getExpandedRowsKey, expandNode,
                                                tool: { message }
                                            }
                                        }) => {
                                            let expandedRowsKey = getExpandedRowsKey();
                                            let parentID = rowData.departmentId;
                                            let tableData = getVTableData()
                                            let { lastOrg, org } = rowData
                                            const list = { allqty: [] }
                                            list.allqty.push(
                                                lastOrg ? lastOrg : 0,
                                                org ? org : 0
                                            )
                                            this.setState({ allqty: [...list.allqty] })
                                            if (rowData.departmentParentId === '9999999999') return
                                            if (expandedRowsKey.includes(parentID)) {
                                                expandNode(parentID, "close");
                                                return;
                                            }
                                            message.loading('loading', 1)
                                            let selectData = this.formHasTicket?.form.getFieldsValue()
                                            this.props.myFetch('getZxEqEquipListForComparedWith', {
                                                departmentParentId: parentID,
                                                resCatalogID: selectData.resCatalogID,
                                                abcType: selectData.abcType,
                                                beginDate: selectData.Date[0] ? moment(selectData.Date[0]).valueOf() : null,
                                                endDate: selectData.Date[1] ? moment(selectData.Date[1]).valueOf() : null,
                                                lastEndDate: selectData.Date[1] ? moment(selectData.Date[1]).valueOf() - 31536000000 : null,
                                                lastBeginDate: selectData.Date[0] ? moment(selectData.Date[0]).valueOf() - 31536000000 : null,
                                            }).then((res) => {
                                                message.destroy()
                                                if (res.success) {
                                                    var childrenData = res.data;
                                                    if (!childrenData.length) {
                                                        Msg.warn("该节点没有子集数据")
                                                        return;
                                                    }
                                                    var loopFn = function (data) {
                                                        for (var i = 0; i < data.length; i++) {
                                                            if (data[i].departmentId === parentID) {
                                                                data[i].children = childrenData;
                                                            } else if (data[i].children) {
                                                                data[i].children = loopFn(data[i].children)
                                                            }
                                                        }
                                                        return data;
                                                    }
                                                    tableData = loopFn([...tableData]);
                                                    setTableData([...tableData]);
                                                    expandNode(parentID, 'expand');
                                                } else {
                                                    Msg.error(res.message)
                                                }
                                            })
                                        }
                                    },
                                },
                                {
                                    table: {
                                        title: '上期',
                                        dataIndex: 'title_1',
                                        key: 'title_1',
                                        children: [
                                            {
                                                title: '数量(台)',
                                                dataIndex: 'lastNum',
                                                key: 'lastNum',
                                                width: 100
                                            },
                                            {
                                                title: '原值(万元)',
                                                dataIndex: 'lastOrg',
                                                key: 'lastOrg',
                                                width: 100
                                            },
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '本期',
                                        dataIndex: 'title_2',
                                        key: 'title_2',
                                        children: [
                                            {
                                                title: '数量(台)',
                                                dataIndex: 'num',
                                                key: 'num',
                                                width: 100
                                            },
                                            {
                                                title: '原值(万元)',
                                                dataIndex: 'org',
                                                key: 'org',
                                                width: 100
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '同比增长',
                                        dataIndex: 'rate',
                                        key: 'rate',
                                        width: 120,
                                    },
                                },
                            ]}
                        />
                    </div>
                </div>
            </Spin>
        );
    }
}

export default index;