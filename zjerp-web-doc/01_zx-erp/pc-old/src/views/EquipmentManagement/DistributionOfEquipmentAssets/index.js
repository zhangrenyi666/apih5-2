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
    paginationConfig: {
        position: 'bottom'
    },
    drawerConfig: {
        width: '900px'
    },
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
        this.state = {
            loading: false,
            bsid: '',
            showReactEcharts: true,
            pieOneData: pieInitialValue,
            pieTwoData: pieInitialValue,
            pieThreeData: pieInitialValue,
            purDate: null
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
        const { loading, showReactEcharts, purDate } = this.state;
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
                                    type: 'year',
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
                                                let value = this.formHasTicket.form.getFieldsValue();
                                                this.setState({
                                                    purDate: null,
                                                }, () => {
                                                    this.setState({
                                                        purDate: value.purDate ? moment(value.purDate).endOf('year').valueOf() : null,
                                                    }, () => {
                                                        this.table.refresh();
                                                    })
                                                })
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
                                otherParams: { departmentPath: '9999999999', purDate, numberFlag: 'sysDepartment' },
                                success: ({ data, success, message }) => {
                                    if (success) {
                                        let pieOneData = this.setPieData(data[0], 1)
                                        let pieTwoData = this.setPieData(data[0], 2)
                                        let pieThreeData = this.setPieData(data[0], 3)
                                        this.setState({ pieOneData, pieTwoData, pieThreeData })
                                    } else {
                                        Msg.error(message)
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
                                        onClick: (obj) => {
                                            var rowData = obj.rowData;
                                            var btnCallbackFn = obj.btnCallbackFn;
                                            var setState = btnCallbackFn.setState;
                                            var oldData = obj.state.data;
                                            var expandedRowKeys = obj.state.expandedRowKeys;
                                            var parentID = rowData.departmentId;
                                            let pieOneData = this.setPieData(rowData, 1)
                                            let pieTwoData = this.setPieData(rowData, 2)
                                            let pieThreeData = this.setPieData(rowData, 3)
                                            this.setState({ pieOneData, pieTwoData, pieThreeData })
                                            if (rowData.departmentParentId !== '9999999999' && rowData.departmentPath !== '9999999999') return
                                            if (rowData.children && rowData.children.length) {
                                                if (!expandedRowKeys.includes(parentID)) {
                                                    expandedRowKeys.push(parentID);
                                                    setState({
                                                        expandedRowKeys: [],
                                                    }, () => {
                                                        setState({
                                                            expandedRowKeys: expandedRowKeys,
                                                        })
                                                    })
                                                } else {
                                                    var index = expandedRowKeys.indexOf(parentID);
                                                    expandedRowKeys.splice(index, 1)
                                                    setState({
                                                        expandedRowKeys: [],
                                                    }, () => {
                                                        setState({
                                                            expandedRowKeys: expandedRowKeys,
                                                        })
                                                    })
                                                }
                                                return;
                                            }
                                            this.props.myFetch('getZxEqEquipListForEquipClassifyDistribute', {
                                                departmentParentId: parentID,
                                                purDate,
                                                numberFlag: rowData.departmentPath === '9999999999' ? 'sysDepartment' : 'sysProject'
                                            }).then((res) => {
                                                var success = res.success;
                                                var childrenData = res.data;
                                                var message = res.message;
                                                if (!childrenData.length) {
                                                    Msg.warn("该节点没有子集数据")
                                                    return;
                                                }
                                                if (success) {
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
                                                    oldData = loopFn(oldData);
                                                    if (!expandedRowKeys.includes(parentID)) {
                                                        expandedRowKeys.push(parentID);
                                                    }
                                                    setState({
                                                        expandedRowKeys: expandedRowKeys,
                                                        data: oldData,
                                                    }, () => {
                                                        setState({
                                                            expandedRowKeys: expandedRowKeys,
                                                        })
                                                    })
                                                } else {
                                                    Msg.error(message)
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
                                        width: 300,
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
                                                filter: true,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '压实机械',
                                        dataIndex: 'title_2',
                                        key: 'title_2',
                                        width: 300,
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
                                                filter: true,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '路面机械',
                                        dataIndex: 'title_3',
                                        key: 'title_3',
                                        width: 300,
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
                                                filter: true,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '石方开采及加工机械',
                                        dataIndex: 'title_4',
                                        key: 'title_4',
                                        width: 300,
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
                                                filter: true,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '桥涵机械',
                                        dataIndex: 'title_5',
                                        key: 'title_5',
                                        width: 300,
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
                                                filter: true,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '运输机械',
                                        dataIndex: 'title_6',
                                        key: 'title_6',
                                        width: 300,
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
                                                filter: true,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '装卸起重机械',
                                        dataIndex: 'title_7',
                                        key: 'title_7',
                                        width: 300,
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
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '动力设备',
                                        dataIndex: 'title_8',
                                        key: 'title_8',
                                        width: 300,
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
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '维修设备',
                                        dataIndex: 'title_9',
                                        key: 'title_9',
                                        width: 300,
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
                                                width: 120,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '房建类设备',
                                        dataIndex: 'title_10',
                                        key: 'title_10',
                                        width: 300,
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
                                                width: 120,
                                            }
                                        ]
                                    },
                                },
                                {
                                    table: {
                                        title: '其他',
                                        dataIndex: 'title_11',
                                        key: 'title_11',
                                        width: 300,
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
                                                width: 120,
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