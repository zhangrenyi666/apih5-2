import React, { Component } from "react";
import QnnForm from "../modules/qnn-table/qnn-form";
import { Row, Col , Spin,message as Msg,Tabs,Collapse } from 'antd';
const TabPane = Tabs.TabPane;

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loadingSend: false,
            loadingFormmm: false,
            key:'1'
        }
    }
    componentDidMount() {
       
    }
    tabCallback = (key) => {
        this.setState({ key });
        if (key === '1') {
            if (this.table1) {
                this.table1.refresh();
            }
        } else if (key === '2') {
            if (this.table2) {
                this.table2.refresh();
            }
        }
    }
    render() {
        const { key } = this.state;
        return (
            <div>
                <Tabs activeKey={key} onChange={this.tabCallback}>
                    <TabPane tab={'梁场预制台座使用情况列表'} key={1}>
                        <QnnForm
                                {...this.props} 
                                fetchConfig={{
                                    apiName: 'getZjProZcTbeamPedestalUseConditionList',
                                    otherParams: {
                                        typeFlag:'0'
                                    }
                                }}
                                fetch={this.props.myFetch} 
                                wrappedComponentRef={(me) => {
                                    this.formmmA = me;
                                }}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                formConfig={[ 
                                    {
                                        label: '主键id',
                                        field: 'tbeamPedestalUseConditionId',
                                        hide: true
                                    },
                                    {
                                        label: '主键id',
                                        field: 'state0',
                                        type:'string',
                                        hide: true
                                    },
                                    {
                                        label: '主键id',
                                        field: 'state1',
                                        type:'string',
                                        hide: true
                                    },
                                    {
                                        label: '主键id',
                                        field: 'state2',
                                        type:'string',
                                        hide: true
                                    },
                                    {
                                        type: 'Component',
                                        field: "workerUploadNum",
                                        Component: (obj, rowData) => {
                                            let value = obj.form.getFieldsValue();
                                            let stateZero = 0;
                                            if (value.state0) {
                                                stateZero = value.state0;
                                            }
                                            let stateOne = 0;
                                            if (value.state1) {
                                                stateOne = value.state1;
                                            }
                                            let stateTwo = 0;
                                            if (value.state2) {
                                                stateTwo = value.state2;
                                            }
                                            return <div style={{ background: '#f1f1f1', padding: '7px', marginRight:'10px' }}>
                                                <span style={{ width: '50%',textAlign: 'right' }}>使用中 <big style={{color:'green'}}>{stateZero}</big> 个，占用中 <big style={{color:'red'}}>{stateOne}</big> 个，闲置中 <big style={{color:'blue'}}>{stateTwo}</big> 个</span>
                                            </div>
                                        }
                                    },
                                    {
                                        type: 'qnnTable',
                                        field: 'zjProZcTbeamPedestalUseConditionList',
                                        incToForm: true,
                                        qnnTableConfig: {
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'tbeamPedestalUseConditionId',
                                                size: 'small',
                                                scroll:{
                                                    y:document.documentElement.clientHeight*0.55
                                                }
                                            },
                                            drawerConfig: {
                                                width: '1000px'
                                            },
                                            limit: 999,
                                            curPage:1,
                                            paginationConfig: false,
                                            firstRowIsSearch: false,
                                            isShowRowSelect: false,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 4 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 20 }
                                                }
                                            },
                                            formConfig:[
                                                {
                                                    table: {
                                                        title: '序号',
                                                        width: 70,
                                                        align:'center',
                                                        dataIndex: 'tbeamPedestalUseConditionId',
                                                        key: 'tbeamPedestalUseConditionId',
                                                        tdEdit:false
                                                    },
                                                    isInForm:false
                                                },
                                                {
                                                    table: {
                                                        title: '预制台座编号',
                                                        width:250,
                                                        dataIndex: 'prefabPedestalNo',
                                                        tdEdit:false,
                                                        key: 'prefabPedestalNo'
                                                    },
                                                    isInForm:false
                                                },
                                                {
                                                    table: {
                                                        title: '台座状态',
                                                        width:200,
                                                        dataIndex: 'pedestalState',
                                                        key: 'pedestalState',
                                                        tdEdit: true,
                                                        fieldsConfig: {
                                                            type:'select',
                                                            optionData:[
                                                                {
                                                                    label:"使用中",
                                                                    value:"0",
                                                                },
                                                                {
                                                                    label:"占用中",
                                                                    value:"1",
                                                                },
                                                                {
                                                                    label:"闲置中",
                                                                    value:"2",
                                                                }
                                                            ],
                                                            style: (val, obj) => {
                                                                let color = '';
                                                                if (val === '0') {
                                                                    color = 'green';
                                                                } else if (val === '1') {
                                                                    color = 'red';
                                                                } else if (val === '2') {
                                                                    color = 'blue';
                                                                } else {

                                                                }
                                                                return {
                                                                    color:color
                                                                };
                                                            }
                                                        }
                                                    },
                                                    isInForm:false
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks',
                                                        tdEdit: true,
                                                        fieldsConfig: {
                                                            type: "textarea",
                                                            field:'remarks',
                                                            placeholder: "请输入...",
                                                            autoSize: {
                                                                minRows: 1,
                                                                maxRows: 3
                                                            }
                                                        }
                                                    },
                                                    isInForm:false
                                                }
                                            ],
                                            actionBtns:[
                                            {
                                                    name: "save",
                                                    type: "primary",
                                                    label: "保存",
                                                    onClick: () => {
                                                        let value = this.formmmA.form.getFieldsValue();
                                                        value.typeFlag = '0';
                                                        this.props.myFetch("saveAllZjProZcTbeamPedestalUseCondition", value).then(({ success, data, message }) => {
                                                            if (success) {
                                                                this.setState({
                                                                    loadingSend:false
                                                                })
                                                                this.formmmA.refresh();
                                                            } else {
                                                                Msg.error(message)
                                                            }
                                                        })
                                                    }
                                                },
                                            ]
                                        }
                                    }
                                ]}
                                btns={[

                                ]}
                            />
                    </TabPane>
                    <TabPane tab={'梁场存放台座使用情况列表'} key={2}>
                    <QnnForm
                                {...this.props} 
                                fetchConfig={{
                                    apiName: 'getZjProZcTbeamPedestalUseConditionList',
                                    otherParams: {
                                        typeFlag:'1'
                                    }
                                }}
                                fetch={this.props.myFetch} 
                                wrappedComponentRef={(me) => {
                                    this.formmm = me;
                                }}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                formConfig={[ 
                                    {
                                        label: '主键id',
                                        type:'string',
                                        field: 'tbeamPedestalUseConditionId',
                                        hide: true
                                    },
                                    {
                                        label: '主键id',
                                        field: 'state0',
                                        type:'string',
                                        hide: true
                                    },
                                    {
                                        label: '主键id',
                                        field: 'state1',
                                        type:'string',
                                        hide: true
                                    },
                                    {
                                        label: '主键id',
                                        field: 'state2',
                                        type:'string',
                                        hide: true
                                    },
                                    {
                                        type: 'Component',
                                        field: "state",
                                        Component: (obj, rowData) => {
                                            let value = obj.form.getFieldsValue();
                                            let stateOne = 0;
                                            if (value.state0) {
                                                stateOne = value.state0;
                                            }
                                            let stateTwo = 0;
                                            if (value.state1) {
                                                stateTwo = value.state1;
                                            }
                                            return <div style={{ background: '#f1f1f1', padding: '7px', marginRight:'10px' }}>
                                                <span style={{ width: '50%',textAlign: 'right' }}>占用中 <big style={{color:'red'}}>{stateOne}</big> 个，闲置中 <big style={{color:'blue'}}>{stateTwo}</big> 个</span>
                                            </div>
                                        }
                                    },
                                    {
                                        type: 'qnnTable',
                                        field: 'zjProZcTbeamPedestalUseConditionList',
                                        incToForm: true,
                                        qnnTableConfig: {
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'tbeamPedestalUseConditionId',
                                                size: 'small',
                                                scroll:{
                                                    y:document.documentElement.clientHeight*0.55
                                                }
                                            },
                                            drawerConfig: {
                                                width: '1000px'
                                            },
                                            limit: 999,
                                            curPage:1,
                                            paginationConfig: false,
                                            firstRowIsSearch: false,
                                            isShowRowSelect: false,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 4 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 20 }
                                                }
                                            },
                                            formConfig:[
                                                {
                                                    table: {
                                                        title: '序号',
                                                        width: 70,
                                                        align:'center',
                                                        dataIndex: 'tbeamPedestalUseConditionId',
                                                        key: 'tbeamPedestalUseConditionId',
                                                        tdEdit:false
                                                    },
                                                    isInForm:false
                                                },
                                                {
                                                    table: {
                                                        title: '存梁台座编号',
                                                        width:250,
                                                        dataIndex: 'prefabPedestalNo',
                                                        tdEdit:false,
                                                        key: 'prefabPedestalNo'
                                                    },
                                                    isInForm:false
                                                },
                                                {
                                                    table: {
                                                        title: '台座状态',
                                                        width:200,
                                                        dataIndex: 'pedestalState',
                                                        key: 'pedestalState',
                                                        tdEdit: true,
                                                        fieldsConfig: {
                                                            type:'select',
                                                            optionData:[
                                                                {
                                                                    label:"占用中",
                                                                    value:"0",
                                                                },
                                                                {
                                                                    label:"闲置中",
                                                                    value:"1",
                                                                }
                                                            ],
                                                            style: (val, obj) => {
                                                                let color = '';
                                                                if (val === '0') {
                                                                    color = 'green';
                                                                } else if (val === '1') {
                                                                    color = 'red';
                                                                } else if (val === '2') {
                                                                    color = 'blue';
                                                                } else {

                                                                }
                                                                return {
                                                                    color:color
                                                                };
                                                            }
                                                        }
                                                    },
                                                    isInForm:false
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks',
                                                        tdEdit: true,
                                                        fieldsConfig: {
                                                            type: "textarea",
                                                            field:'remarks',
                                                            placeholder: "请输入...",
                                                            autoSize: {
                                                                minRows: 1,
                                                                maxRows: 3
                                                            }
                                                        }
                                                    },
                                                    isInForm:false
                                                }
                                            ],
                                            actionBtns:[
                                            {
                                                    name: "save",
                                                    type: "primary",
                                                    label: "保存",
                                                    onClick: () => {
                                                        let value = this.formmm.form.getFieldsValue();
                                                        value.typeFlag = '1';
                                                        this.props.myFetch("saveAllZjProZcTbeamPedestalUseCondition", value).then(({ success, data, message }) => {
                                                            if (success) {
                                                                this.setState({
                                                                    loadingFormmm:false
                                                                })
                                                                this.formmm.refresh();
                                                            } else {
                                                                Msg.error(message)
                                                            }
                                                        })
                                                    }
                                                },
                                            ]
                                        }
                                    }
                                ]}
                                btns={[

                                ]}
                            />
                    </TabPane>
                </Tabs>
            </div>
        );
    }
}

export default index;