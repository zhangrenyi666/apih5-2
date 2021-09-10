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
        this.state = {
            loading: false,
            showReactEcharts: true,
        }
    }


    getOptionTow = () => {
        const { allqty } = this.state;
        var option = {
            xAxis: {
                type: 'category',
                data: [
                    moment(new Date()).format('YYYY') - 4, moment(new Date()).format('YYYY') - 3,
                    moment(new Date()).format('YYYY') - 2, moment(new Date()).format('YYYY') - 1, moment(new Date()).format('YYYY')
                ]
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
                type: 'line'
            }]
        };
        return option;
    }

    render() {
        const { loading, showReactEcharts } = this.state;
        const { companyId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                                            isGroup:'1'
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
                                                this.table.refresh();
                                            }}>查询</Button></div>
                                        );
                                    },
                                    span: 2
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
                            fetchConfig={{
                                apiName: 'getZxEqEquipListForFiveYearTrend',
                                otherParams:  () => {
                                    const selectData =  this.formHasTicket?.form?.getFieldsValue()
                                    return {
                                        departmentPath: '9999999999',
                                        resCatalogID: selectData?.resCatalogID,
                                        abcType: selectData?.abcType,
                                        departmentParentId2: ext1 === '2' ? companyId : null
                                    }
                                },
                                success: ({ data, success, message }) => {
                                    if (success && data.length > 0) {
                                        const list = { allqty: [] }
                                        if (data.length) {
                                            let { oneAmount, twoAmount, threeAmount, fourAmount, fiveAmount } = data[0]
                                            list.allqty.push(
                                                oneAmount ? oneAmount : 0,
                                                twoAmount ? twoAmount : 0,
                                                threeAmount ? threeAmount : 0,
                                                fourAmount ? fourAmount : 0,
                                                fiveAmount ? fiveAmount : 0
                                            )
                                            this.setState({ allqty: [...list.allqty] })
                                        } else {
                                            Msg.warning('暂无数据')
                                        }
                                    }
                                }
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
                                            let { oneAmount, twoAmount, threeAmount, fourAmount, fiveAmount } = rowData
                                            const list = { allqty: [] }
                                            list.allqty.push(
                                                oneAmount ? oneAmount : 0,
                                                twoAmount ? twoAmount : 0,
                                                threeAmount ? threeAmount : 0,
                                                fourAmount ? fourAmount : 0,
                                                fiveAmount ? fiveAmount : 0
                                            )
                                            this.setState({ allqty: [...list.allqty] })
                                            if (rowData.departmentParentId === '9999999999') return
                                            if (expandedRowsKey.includes(parentID)) {
                                                expandNode(parentID, "close");
                                                return;
                                            }
                                            message.loading('loading', 1)
                                            let selectData = this.formHasTicket?.form.getFieldsValue()
                                            this.props.myFetch('getZxEqEquipListForFiveYearTrend', {
                                                departmentParentId: parentID,
                                                resCatalogID: selectData.resCatalogID,
                                                abcType: selectData.abcType
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
                                        title: moment(new Date()).format('YYYY') - 4,
                                        dataIndex: 'title_1',
                                        key: 'title_1',
                                        children: [
                                            {
                                                title: '数量(台)',
                                                dataIndex: 'oneNum',
                                                key: 'oneNum',
                                                width: 100
                                            },
                                            {
                                                title: '原值(万元)',
                                                dataIndex: 'oneAmount',
                                                key: 'oneAmount',
                                                width: 100
                                            },
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: moment(new Date()).format('YYYY') - 3,
                                        dataIndex: 'title_2',
                                        key: 'title_2',
                                        children: [
                                            {
                                                title: '数量(台)',
                                                dataIndex: 'twoNum',
                                                key: 'twoNum',
                                                width: 100
                                            },
                                            {
                                                title: '原值(万元)',
                                                dataIndex: 'twoAmount',
                                                key: 'twoAmount',
                                                width: 100
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: moment(new Date()).format('YYYY') - 2,
                                        dataIndex: 'title_3',
                                        key: 'title_3',
                                        children: [
                                            {
                                                title: '数量(台)',
                                                dataIndex: 'threeNum',
                                                key: 'threeNum',
                                                width: 100
                                            },
                                            {
                                                title: '原值(万元)',
                                                dataIndex: 'threeAmount',
                                                key: 'threeAmount',
                                                width: 100
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: moment(new Date()).format('YYYY') - 1,
                                        dataIndex: 'title_4',
                                        key: 'title_4',
                                        children: [
                                            {
                                                title: '数量(台)',
                                                dataIndex: 'fourNum',
                                                key: 'fourNum',
                                                width: 100
                                            },
                                            {
                                                title: '原值(万元)',
                                                dataIndex: 'fourAmount',
                                                key: 'fourAmount',
                                                width: 100
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: moment(new Date()).format('YYYY'),
                                        dataIndex: 'title_5',
                                        key: 'title_5',
                                        children: [
                                            {
                                                title: '数量(台)',
                                                dataIndex: 'fiveNum',
                                                key: 'fiveNum',
                                                width: 100
                                            },
                                            {
                                                title: '原值(万元)',
                                                dataIndex: 'fiveAmount',
                                                key: 'fiveAmount',
                                                width: 100
                                            }
                                        ]
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