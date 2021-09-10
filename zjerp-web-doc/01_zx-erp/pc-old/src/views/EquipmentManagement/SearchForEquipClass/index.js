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
                { value: data.aZ, name: '土方机械' }, { value: data.bZ, name: '压实机械' },
                { value: data.cZ, name: '路面机械' }, { value: data.dZ, name: '石方机械' },
                { value: data.eZ, name: '桥涵机械' }, { value: data.fZ, name: '运输机械' },
                { value: data.gZ, name: '装卸起重机械' }, { value: data.hZ, name: '动力设备' },
                { value: data.iZ, name: '维修设备' }, { value: data.jZ, name: '房建类设备' },
                { value: data.kZ, name: '其他' }
            ]
        } else if (index === 2) {
            return [
                { value: data.aW, name: '土方机械' }, { value: data.bW, name: '压实机械' },
                { value: data.cW, name: '路面机械' }, { value: data.dW, name: '石方机械' },
                { value: data.eW, name: '桥涵机械' }, { value: data.fW, name: '运输机械' },
                { value: data.gW, name: '装卸起重机械' }, { value: data.hW, name: '动力设备' },
                { value: data.iW, name: '维修设备' }, { value: data.jW, name: '房建类设备' },
                { value: data.kW, name: '其他' }
            ]
        } else if (index === 3) {
            return [
                { value: data.aX, name: '土方机械' }, { value: data.bX, name: '压实机械' },
                { value: data.cX, name: '路面机械' }, { value: data.dX, name: '石方机械' },
                { value: data.eX, name: '桥涵机械' }, { value: data.fX, name: '运输机械' },
                { value: data.gX, name: '装卸起重机械' }, { value: data.hX, name: '动力设备' },
                { value: data.iX, name: '维修设备' }, { value: data.jX, name: '房建类设备' },
                { value: data.kX, name: '其他' }
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
                                option={this.getDataOption('自有设备', this.state.pieOneData)}
                                notMerge={true}
                                lazyUpdate={true}
                                theme={"theme_name"}
                            /></Col>
                            <Col span={8}><ReactEcharts
                                option={this.getDataOption('外租设备', this.state.pieTwoData)}
                                notMerge={true}
                                lazyUpdate={true}
                                theme={"theme_name"}
                            /></Col>
                            <Col span={8}><ReactEcharts
                                option={this.getDataOption('协作单位自带设备', this.state.pieThreeData)}
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
                                apiName: 'getZxEqEquipListForEquipClassify',
                                otherParams: { departmentPath: '9999999999', purDate, numberFlag: 'sysDepartment' },
                                success: ({ data, success, message }) => {
                                    if (success && data.length > 0) {
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
                                            console.log(obj);
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
                                            this.props.myFetch('getZxEqEquipListForEquipClassify', {
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
                                                title: '自有',
                                                dataIndex: 'aZ',
                                                key: 'aZ',
                                                width: 90,
                                            },
                                            {
                                                title: '外租',
                                                dataIndex: 'aW',
                                                key: 'aW',
                                                width: 90,
                                            },
                                            {
                                                title: '协作单位自带',
                                                dataIndex: 'aX',
                                                key: 'aX',
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
                                                title: '自有',
                                                dataIndex: 'bZ',
                                                key: 'bZ',
                                                width: 90,
                                            },
                                            {
                                                title: '外租',
                                                dataIndex: 'bW',
                                                key: 'bW',
                                                width: 90,
                                            },
                                            {
                                                title: '协作单位自带',
                                                dataIndex: 'bX',
                                                key: 'bX',
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
                                                title: '自有',
                                                dataIndex: 'cZ',
                                                key: 'cZ',
                                                width: 90,
                                            },
                                            {
                                                title: '外租',
                                                dataIndex: 'cW',
                                                key: 'cW',
                                                width: 90,
                                            },
                                            {
                                                title: '协作单位自带',
                                                dataIndex: 'cX',
                                                key: 'cX',
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
                                                title: '自有',
                                                dataIndex: 'dZ',
                                                key: 'dZ',
                                                width: 90,
                                            },
                                            {
                                                title: '外租',
                                                dataIndex: 'dW',
                                                key: 'dW',
                                                width: 90,
                                            },
                                            {
                                                title: '协作单位自带',
                                                dataIndex: 'dX',
                                                key: 'dX',
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
                                                title: '自有',
                                                dataIndex: 'eZ',
                                                key: 'eZ',
                                                width: 90,
                                            },
                                            {
                                                title: '外租',
                                                dataIndex: 'eW',
                                                key: 'eW',
                                                width: 90,
                                            },
                                            {
                                                title: '协作单位自带',
                                                dataIndex: 'eX',
                                                key: 'eX',
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
                                                title: '自有',
                                                dataIndex: 'fZ',
                                                key: 'fZ',
                                                width: 90,
                                            },
                                            {
                                                title: '外租',
                                                dataIndex: 'fW',
                                                key: 'fW',
                                                width: 90,
                                            },
                                            {
                                                title: '协作单位自带',
                                                dataIndex: 'fX',
                                                key: 'fX',
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
                                                title: '自有',
                                                dataIndex: 'gZ',
                                                key: 'gZ',
                                                width: 90,
                                            },
                                            {
                                                title: '外租',
                                                dataIndex: 'gW',
                                                key: 'gW',
                                                width: 90,
                                            },
                                            {
                                                title: '协作单位自带',
                                                dataIndex: 'gX',
                                                key: 'gX',
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
                                                title: '自有',
                                                dataIndex: 'hZ',
                                                key: 'hZ',
                                                width: 90,
                                            },
                                            {
                                                title: '外租',
                                                dataIndex: 'hW',
                                                key: 'hW',
                                                width: 90,
                                            },
                                            {
                                                title: '协作单位自带',
                                                dataIndex: 'hX',
                                                key: 'hX',
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
                                                title: '自有',
                                                dataIndex: 'iZ',
                                                key: 'iZ',
                                                width: 90,
                                            },
                                            {
                                                title: '外租',
                                                dataIndex: 'iW',
                                                key: 'iW',
                                                width: 90,
                                            },
                                            {
                                                title: '协作单位自带',
                                                dataIndex: 'iX',
                                                key: 'iX',
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
                                                title: '自有',
                                                dataIndex: 'jZ',
                                                key: 'jZ',
                                                width: 90,
                                            },
                                            {
                                                title: '外租',
                                                dataIndex: 'jW',
                                                key: 'jW',
                                                width: 90,
                                            },
                                            {
                                                title: '协作单位自带',
                                                dataIndex: 'jX',
                                                key: 'jX',
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
                                                title: '自有',
                                                dataIndex: 'kZ',
                                                key: 'kZ',
                                                width: 90,
                                            },
                                            {
                                                title: '外租',
                                                dataIndex: 'kW',
                                                key: 'kW',
                                                width: 90,
                                            },
                                            {
                                                title: '协作单位自带',
                                                dataIndex: 'kX',
                                                key: 'kX',
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