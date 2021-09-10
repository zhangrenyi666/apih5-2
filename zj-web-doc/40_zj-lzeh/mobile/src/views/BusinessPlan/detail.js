import React, { Component } from "react";
import s from "./index.less"
import { NavBar, Icon, Toast, List, Button, Modal, Flex } from "antd-mobile"
import moment from 'moment';
import { Table, Input } from 'antd';
class index extends Component {
    constructor(props) {
        super(props)
        this.state = {
            dataArr: [],
            modal: false,
            modalTop: false,
            seachText: ''
        }
    }
    componentDidMount() {
        document.title = "经营目标任务计划明细";
        this.getData()
    }
    getData(params = null) {
        this.props.myFetch('getZjLzehManageTaskPlanItemList', {
            zjLzehManageTaskPlanId: this.props.match.params.zjLzehManageTaskPlanId,
            dutyDepartment: params
        }).then(({ data, success, message }) => {
            if (success) {
                this.setState({ dataArr: data, modal: false })
            } else {
                this.setState({ modal: false })
                Toast.fail(message, 1);
            }
        })
    }
    render() {
        const columns = [
            {
                title: '事项描述',
                dataIndex: 'matterDescription',
                width: 150,
                key: 'planMakespan',
            },
            {
                title: '控制要求',
                dataIndex: 'controlDemand',
                width: 150,
                key: 'planMakespan',
            },
            {
                title: '计划完成时间',
                dataIndex: 'planMakespan',
                width: 120,
                key: 'planMakespan',
                render: (data) => {
                    return <div >{moment(data).format('YYYY-MM-DD')}</div>
                }
            },
            {
                title: '实际完成时间',
                dataIndex: 'realMakespan',
                width: 120,
                key: 'realMakespan',
                render: (data) => {
                    return <div >{moment(data).format('YYYY-MM-DD')}</div>
                }

            },
            {
                title: '责任部门',
                dataIndex: 'dutyDepartment',
                width: 120,
                key: 'dutyDepartment',
            },
            {
                title: '责任人',
                dataIndex: 'dutyPerson',
                width: 100,
                key: 'dutyPerson',
            },
            {
                title: '分管领导',
                dataIndex: 'managgeLead',
                width: 100,
                key: 'managgeLead',
            },
            {
                title: '配合人员',
                dataIndex: 'coordPerson',
                width: 130,
                key: 'coordPerson',
            },
            {
                title: '配合部门',
                dataIndex: 'coordDepartment',
                width: 100,
                key: 'coordDepartment',
            },
            {
                title: '完成情况',
                dataIndex: 'completion',
                width: 100,
                key: 'completion',
            },
        ];
        return (
            <div style={{ width: '100%', height: "100vh" }}>
                <div className={s.top}>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            this.props.history.goBack()
                        }}
                        rightContent={[
                            <Icon key="0" type="search" style={{ margin: '5px 5px 6px 5px', color: '#fff' }}
                                onClick={() => { this.setState({ modal: true }) }}
                            />

                        ]}
                    >
                        {"经营目标任务计划明细"}
                    </NavBar>
                </div>

                <div style={{
                    height: window.innerHeight - 45,
                }}>
                    <Flex direction={'row'} className={s.modalTop} justify={'between'} style={{ height: this.state.modalTop ? '40px' : 0 }}>
                        <div>责任部门：{this.state.seachText}</div>
                        <Button
                            style={{ marginLeft: '20px' }} type="primary" inline size={'small'}
                            onClick={() => { this.setState({ modalTop: false, seachText: null }, () => { this.getData() }) }}
                        >清除</Button>
                    </Flex>
                    <Table bordered={true} size={'small'} columns={columns} dataSource={this.state.dataArr} pagination={false} scroll={{ x: 1170, y: this.state.modalTop ? '73vh' : '83vh' }} />
                </div>
                <Modal
                    visible={this.state.modal}
                    transparent
                    maskClosable={false}
                >
                    <List style={{ backgroundColor: 'white' }}>
                        <Input addonBefore="责任部门" defaultValue={this.state.seachText}
                            onChange={(e) => { this.setState({ seachText: e.target.value }) }}
                        />
                    </List>
                    <div className={s.seachButtonView}>
                        <Button style={{ marginRight: '20px', background: '#eee' }} inline onClick={() => { this.setState({ modal: false }) }}>取消</Button>
                        <Button
                            style={{ marginLeft: '20px' }} type="primary" inline
                            onClick={() => {
                                this.setState({ modalTop: true }, () => {
                                    this.getData(this.state.seachText)
                                })
                            }}
                        >确定</Button>
                    </div>
                </Modal>
            </div>
        );
    }
}
export default index;
