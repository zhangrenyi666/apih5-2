import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Modal, Spin, Checkbox, Row, Col } from 'antd';
import s from "./style.less";
const confirm = Modal.confirm;
const configL = {
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
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
    paginationConfig: false,
    isShowRowSelect: false
}
const configR = {
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
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
    paginationConfig: false,
    isShowRowSelect: false
}
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            qdid: '',
            qdNo: [],
            qdName: [],
            optionData: [],
            qdData: [],
            orgID: ''
        }
    }
    componentDidMount() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        this.setState({
            loading: true
        })
        this.props.myFetch('getZxBuProjectTypeCheckOver', {orgID: departmentId}).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        optionData: data,
                        // orgID: data.length ? data[0].iecmOrgID : '',
                        loading: false
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    onChangeRadio = (rowData, e) => {
        console.log(this.formHasTicket);
        if(rowData.contractQty == 0){
            this.setState({
                qdid:'',
                qdNo: [],
                qdName: []
            })
            return Msg.error("合同数量为0,不允许挂接清单")
        }else{
            let qdNo = [];
            if (rowData.workNoJoin != null) {
                qdNo = rowData.workNoJoin.split(",")
            }
            const { myFetch } = this.props;
            if (e.target.checked) {
                console.log(e)
    
                this.setState({
                    qdid: rowData.id,
                    qdNo: qdNo
                })
            } else {
                this.setState({
                    qdid: '',
                    qdNo: []
                })
            }
        }
    }
    onChangeRadioALL = (rowData, e) => {
        const { myFetch } = this.props;
        if (this.state.qdid) {
            if (e.target.checked) {
                myFetch('relevanceZxBuYgjResTechnics', {
                    //项目id
                    orgID: this.state.orgID,
                    //项目清单id
                    billID: this.state.qdid,
                    //标准工序库id
                    myBillID: rowData.zxBuWorksId,
                }).then(
                    ({ data, success, message }) => {
                        if (success) {
                            this.setState({
                                qdData: data,
                                qdNo: this.addArrOne(rowData.workNo, this.state.qdNo),
                                qdName: this.addArrOne(rowData.workName, this.state.qdName)
                            })
                        } else {
                        }
                    }
                );
            } else {
                //这里删
                myFetch('removeRelevanceZxBuYgjResTechnics', {
                    //项目id
                    orgID: this.state.orgID,
                    //项目清单id
                    billID: this.state.qdid,
                    //标准工序库id
                    myBillID: rowData.zxBuWorksId,
                }).then(
                    ({ data, success, message }) => {
                        if (success) {
                            this.setState({
                                //重新赋值左面表格
                                qdData: data,
                                qdNo: this.delArrOne(rowData.workNo, this.state.qdNo),
                                qdName: this.delArrOne(rowData.workName, this.state.qdName)
                            })
                        } else {
                        }
                    }
                );

            }
        } else {
            Msg.error("所选项目叶子清单数量为0,不允许挂接!")
            this.setState({
                qdNo: [],
                qdName: []
            })
        }
    }
    addArrOne(str, arr) {
        arr.push(str);
        return arr;
    }
    delArrOne(str, arr) {
        //获取指定元素的索引
        var index = arr.indexOf(str);
        //使用splice()方法删除指定元素
        arr.splice(index, 1);

        return arr;
    }
    getProjectQd(val) {
        const { myFetch } = this.props;
        myFetch('getZxBuWorksNoName', {
            orgID: val,
            parentID: '-1'
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        qdData: data,
                        orgID: val,
                        qdid:'',
                        qdNo: [],
                        qdName: []
                    })
                } else {
                }
            }
        );
    }
    render() {
        const { qdData, optionData, loading } = this.state;
        return (
            <div className={s.root}>
                <Spin spinning={loading}>
                    <div className={s.head}>
                        {<Spin tip="Loading..."
                            spinning={this.state.loading}>
                            <Row>
                                <Col span={18}>
                                    <QnnForm
                                        fetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        headers={{
                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                        }}
                                        wrappedComponentRef={(me) => {
                                            this.inputForm = me;
                                        }}
                                        formItemLayout={{
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 16 }
                                            }
                                        }}
                                        formConfig={[
                                            {
                                                label: '工程项目',
                                                field: 'orgID',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'orgName',
                                                    value: 'orgID',
                                                },
                                                allowClear: false,
                                                optionData: optionData,
                                                placeholder: '请选择',
                                                onChange: (val, rowData) => {
                                                    this.getProjectQd(val);
                                                }
                                            }
                                        ]}
                                    />
                                </Col>
                            </Row>
                        </Spin>}
                    </div>
                    <div>
                        <Row>
                            <Col span={12} >
                                {<QnnTable
                                    {...this.props}
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    wrappedComponentRef={(me) => {
                                        this.tableQD = me;
                                    }}
                                    actionBtns={[]}
                                    antd={{
                                        rowKey: function (row) {
                                            return row.id
                                        },
                                        size: 'small',
                                        rowClassName: (rowData, index) => {
                                            return rowData.id === this.state.qdid ? s.addBack : rowData.isLeaf === 0 ? s.addColor : '';
                                        },
                                        scroll: {
                                            y: document.documentElement.clientHeight * 0.6
                                        }
                                    }}
                                    {...configL}
                                    data={qdData}
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
                                                title: '清单编号',
                                                dataIndex: 'workNo',
                                                key: 'workNo',
                                                render: (data, rowData) => {
                                                    console.log(data)
                                                    return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>
                                                        <Checkbox defaultChecked={rowData.id === this.state.qdid ? true : false}
                                                            style={{ visibility: rowData.isLeaf === 0 ? 'hidden' : '', padding: '0px 10px 0px 0px' }}
                                                            onChange={this.onChangeRadio.bind(this, rowData)}
                                                        />
                                                        {data}
                                                    </div>;
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '清单名称',
                                                dataIndex: 'workName',
                                                key: 'workName',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '数量',
                                                dataIndex: 'contractQty',
                                                key: 'contractQty',
                                                align: 'center',
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '单位',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                align: 'center',
                                                tooltip: 23,
                                                width: 50,
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '数据库清单编号',
                                                dataIndex: 'workNoJoin',
                                                key: 'workNoJoin',
                                                align: 'center',
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '数据库清单名称',
                                                dataIndex: 'workNameJoin',
                                                key: 'workNameJoin',
                                                align: 'center',
                                            },
                                            isInForm: false
                                        }

                                    ]}
                                />}
                            </Col>
                            <Col span={12} style={{ paddingLeft: '10px' }}>
                                {<QnnTable
                                    {...this.props}
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    wrappedComponentRef={(me) => {
                                        this.tableDateQD = me;
                                    }}
                                    antd= {{
                                        rowKey: function (row) {
                                            return row.zxBuWorksId
                                        },
                                        size: 'small',
                                        rowClassName: (rowData, index) => {
                                            return this.state.qdNo.indexOf(rowData.workNo) != -1 ? s.addBack : rowData.isLeaf === 0 ? s.addColor : '';
                                        },
                                        scroll: {
                                            y: document.documentElement.clientHeight * 0.6
                                        }
                                    }}
                                    fetchConfig={{
                                        apiName: 'getZxBuWorksTreeListAll',
                                        otherParams: {
                                            orgID: "0",
                                            // limit:10,
                                            // page:1
                                        }
                                    }}
                                    actionBtns={[]}
                                    {...configR}
                                    formConfig={[
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'zxBuWorksId',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '数据库清单编号',
                                                dataIndex: 'workNo',
                                                key: 'workNo',
                                                render: (data, rowData, index) => {
                                                    console.log(data)
                                                    let arr = this.state.qdNo;
                                                    return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>
                                                        <Checkbox defaultChecked={this.state.qdNo.indexOf(rowData.workNo) != -1 ? true : false}
                                                            style={{ visibility: rowData.isLeaf === 0 ? 'hidden' : '', padding: '0px 10px 0px 0px' }}
                                                            onChange={this.onChangeRadioALL.bind(this, rowData)} />
                                                        {data}
                                                    </div>;
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '数据库清单名称',
                                                dataIndex: 'workName',
                                                key: 'workName',
                                                align: 'center',
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '单位',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                align: 'center',
                                                tooltip: 23,
                                                width: 15,
                                            },
                                            isInForm: false
                                        },
                                    ]}
                                />}
                            </Col>
                        </Row>
                    </div>
                </Spin>
            </div>
        );
    }
}

export default index;