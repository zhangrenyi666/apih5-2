import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Spin, Button, Row, Col, } from 'antd';
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
const pieInitialValue = [
    { value: 0, name: '土方机械' }, { value: 0, name: '压实机械' },
    { value: 0, name: '路面机械' }, { value: 0, name: '石方机械' },
    { value: 0, name: '桥涵机械' }, { value: 0, name: '运输机械' },
    { value: 0, name: '装卸起重机械' }, { value: 0, name: '动力设备' },
    { value: 0, name: '维修设备' }, { value: 0, name: '房建类设备' },
    { value: 0, name: '其他' },
]
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            loading: false,
            bsid: '',
            showReactEcharts: true,
            pieOneData: pieInitialValue,
            pieTwoData: pieInitialValue,
            pieThreeData: pieInitialValue,
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            ext1: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.ext1,
			isLocked: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? true : false) : false
        }
    }
    getDataOption = (name, data) => {
        var option = {
            title: {
                text: name,
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            color: [
                'rgb(26,170,240)', 'rgb(50,200,240)', 'rgb(160,230,180)',
                'rgb(250,80,150)', 'rgb(126,170,240)', 'rgb(150,200,240)',
                'rgb(60,230,180)', 'rgb(150,80,150)', 'rgb(200,80,50)',
                'rgb(226,170,140)', 'rgb(6,170,140)'
            ],
            series: [
                {
                    name,
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data,
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        return option;
    }

    setPieData(data, index) {
        if (index === 1) {
            return [
                { value: data.aaNum, name: '土方机械' }, { value: data.bbNum, name: '压实机械' },
                { value: data.ccNum, name: '路面机械' }, { value: data.ddNum, name: '石方机械' },
                { value: data.eeNum, name: '桥涵机械' }, { value: data.ffNum, name: '运输机械' },
                { value: data.ggNum, name: '装卸起重机械' }, { value: data.hhNum, name: '动力设备' },
                { value: data.iiNum, name: '维修设备' }, { value: data.jjNum, name: '房建类设备' },
                { value: data.kkNum, name: '其他' }
            ]
        } else if (index === 2) {
            return [
                { value: data.aaOrg, name: '土方机械' }, { value: data.bbOrg, name: '压实机械' },
                { value: data.ccOrg, name: '路面机械' }, { value: data.ddOrg, name: '石方机械' },
                { value: data.eeOrg, name: '桥涵机械' }, { value: data.ffOrg, name: '运输机械' },
                { value: data.ggOrg, name: '装卸起重机械' }, { value: data.hhOrg, name: '动力设备' },
                { value: data.iiOrg, name: '维修设备' }, { value: data.jjOrg, name: '房建类设备' },
                { value: data.kkOrg, name: '其他' }
            ]
        } else if (index === 3) {
            return [
                { value: data.aaLeft, name: '土方机械' }, { value: data.bbLeft, name: '压实机械' },
                { value: data.ccLeft, name: '路面机械' }, { value: data.ddLeft, name: '石方机械' },
                { value: data.eeLeft, name: '桥涵机械' }, { value: data.ffLeft, name: '运输机械' },
                { value: data.ggLeft, name: '装卸起重机械' }, { value: data.hhLeft, name: '动力设备' },
                { value: data.iiLeft, name: '维修设备' }, { value: data.jjLeft, name: '房建类设备' },
                { value: data.kkLeft, name: '其他' }
            ]
        }
    }
    render() {
        const { loading, showReactEcharts,departmentId, ext1, isLocked } = this.state
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
                        {showReactEcharts ? <Row justify="space-around">
                            <Col span={8}><ReactEcharts
                                option={this.getDataOption('数量比', this.state.pieOneData)}
                                notMerge={true}
                                lazyUpdate={true}
                                theme={"theme_name"}
                            /></Col>
                            <Col span={8}><ReactEcharts
                                option={this.getDataOption('原值比', this.state.pieTwoData)}
                                notMerge={true}
                                lazyUpdate={true}
                                theme={"theme_name"}
                            /></Col>
                            <Col span={8}><ReactEcharts
                                option={this.getDataOption('净值比', this.state.pieThreeData)}
                                notMerge={true}
                                lazyUpdate={true}
                                theme={"theme_name"}
                            /></Col>
                        </Row> : null}
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
                                    type: 'date',
                                    label: '截止时间',
                                    field: 'purDate',
                                    span: 6
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
                                apiName: 'getZxEqEquipListForEquipClassifyDistribute',
                                otherParams: () => {
                                    let purDate = this.formHasTicket?.form?.getFieldValue('purDate')
                                    return {
                                        departmentId: isLocked ? departmentId : ext1 === '3' || ext1 === '4' ? departmentId : null,
										departmentParentId2: isLocked ? null : ext1 === '2' ? departmentId : null,
										departmentPath: isLocked ? null : ext1 === '2' ? null : '9999999999', 
                                        purDate: moment(purDate).valueOf(), 
                                        numberFlag: isLocked ? 'sysProject' : ext1 === '2' || ext1 === '1' ? 'sysDepartment' : 'sysProject',
                                    }
                                },
                                success: ({ data, success, message }) => {
                                    if (success && data.length > 0) {
                                        let pieOneData = this.setPieData(data[0], 1)
                                        let pieTwoData = this.setPieData(data[0], 2)
                                        let pieThreeData = this.setPieData(data[0], 3)
                                        this.setState({ pieOneData, pieTwoData, pieThreeData })
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
                                            let pieOneData = this.setPieData(rowData, 1)
                                            let pieTwoData = this.setPieData(rowData, 2)
                                            let pieThreeData = this.setPieData(rowData, 3)
                                            this.setState({ pieOneData, pieTwoData, pieThreeData })
                                            // if (rowData.departmentParentId !== '9999999999' && rowData.departmentPath !== '9999999999') return
                                            if (expandedRowsKey.includes(parentID)) {
                                                expandNode(parentID, "close");
                                                return;
                                            }
                                            message.loading('loading', 1)
                                            let selectData = this.formHasTicket?.form?.getFieldsValue()
                                            this.props.myFetch('getZxEqEquipListForEquipClassifyDistribute', {
                                                departmentParentId: parentID,
                                                purDate: selectData?.purDate ? moment(selectData.purDate).valueOf() : null,
                                                numberFlag: rowData.departmentPath === '9999999999' ? 'sysDepartment' : 'sysProject'
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
                                        title: '土方、准备作业机械',
                                        dataIndex: 'title_1',
                                        key: 'title_1',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'aaNum',
                                                key: 'aaNum',
                                                width: 90,
                                            },
                                            {
                                                title: '原值',
                                                dataIndex: 'aaOrg',
                                                key: 'aaOrg',
                                                width: 90,
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'aaLeft',
                                                key: 'aaLeft',
                                                width: 90,

                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '压实机械',
                                        dataIndex: 'title_2',
                                        key: 'title_2',

                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'bbNum',
                                                key: 'bbNum',
                                                width: 90,
                                            },
                                            {
                                                title: '原值',
                                                dataIndex: 'bbOrg',
                                                key: 'bbOrg',
                                                width: 90,
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'bbLeft',
                                                key: 'bbLeft',
                                                width: 90,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '路面机械',
                                        dataIndex: 'title_3',
                                        key: 'title_3',

                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'ccNum',
                                                key: 'ccNum',
                                                width: 90,
                                            },
                                            {
                                                title: '原值',
                                                dataIndex: 'ccOrg',
                                                key: 'ccOrg',
                                                width: 90,
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'ccLeft',
                                                key: 'ccLeft',
                                                width: 90,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '石方开采及加工机械',
                                        dataIndex: 'title_4',
                                        key: 'title_4',

                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'ddNum',
                                                key: 'ddNum',
                                                width: 90,
                                            },
                                            {
                                                title: '原值',
                                                dataIndex: 'ddOrg',
                                                key: 'ddOrg',
                                                width: 90,
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'ddLeft',
                                                key: 'ddLeft',
                                                width: 90,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '桥涵机械',
                                        dataIndex: 'title_5',
                                        key: 'title_5',

                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'eeNum',
                                                key: 'eeNum',
                                                width: 90,
                                            },
                                            {
                                                title: '原值',
                                                dataIndex: 'eeOrg',
                                                key: 'eeOrg',
                                                width: 90,
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'eeLeft',
                                                key: 'eeLeft',
                                                width: 90,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '运输机械',
                                        dataIndex: 'title_6',
                                        key: 'title_6',

                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'ffNum',
                                                key: 'ffNum',
                                                width: 90,
                                            },
                                            {
                                                title: '原值',
                                                dataIndex: 'ffOrg',
                                                key: 'ffOrg',
                                                width: 90,
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'ffLeft',
                                                key: 'ffLeft',
                                                width: 90,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '装卸起重机械',
                                        dataIndex: 'title_7',
                                        key: 'title_7',

                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'ggNum',
                                                key: 'ggNum',
                                                width: 90,
                                            },
                                            {
                                                title: '原值',
                                                dataIndex: 'ggOrg',
                                                key: 'ggOrg',
                                                width: 90,
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'ggLeft',
                                                key: 'ggLeft',
                                                width: 90,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '动力设备',
                                        dataIndex: 'title_8',
                                        key: 'title_8',

                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'hhNum',
                                                key: 'hhNum',
                                                width: 90,
                                            },
                                            {
                                                title: '原值',
                                                dataIndex: 'hhOrg',
                                                key: 'hhOrg',
                                                width: 90,
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'hhLeft',
                                                key: 'hhLeft',
                                                width: 90,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '维修设备',
                                        dataIndex: 'title_9',
                                        key: 'title_9',

                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'iiNum',
                                                key: 'iiNum',
                                                width: 90,
                                            },
                                            {
                                                title: '原值',
                                                dataIndex: 'iiOrg',
                                                key: 'iiOrg',
                                                width: 90,
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'iiLeft',
                                                key: 'iiLeft',
                                                width: 90,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '房建类设备',
                                        dataIndex: 'title_10',
                                        key: 'title_10',

                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'jjNum',
                                                key: 'jjNum',
                                                width: 90,
                                            },
                                            {
                                                title: '原值',
                                                dataIndex: 'jjOrg',
                                                key: 'jjOrg',
                                                width: 90,
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'jjLeft',
                                                key: 'jjLeft',
                                                width: 90,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '其他',
                                        dataIndex: 'title_11',
                                        key: 'title_11',

                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'kkNum',
                                                key: 'kkNum',
                                                width: 90,
                                            },
                                            {
                                                title: '原值',
                                                dataIndex: 'kkOrg',
                                                key: 'kkOrg',
                                                width: 90,
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'kkLeft',
                                                key: 'kkLeft',
                                                width: 90,
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