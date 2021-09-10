import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Spin, Row, Col, Modal, Button } from 'antd';
import Tree from "../../modules/tree";
import { push } from "react-router-redux";
import BatchEdit from '../com/BatchEditing'

const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxBuYgjLiveFeeId',
        size: 'small'
    },
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
    // paginationConfig: false
}
class index extends Component {
    constructor(props) {
        super(props);
        this.treeInstance = React.createRef();
        this.state = {
            loading: false,
            defaultExpandedKeys: [],
            dataLbist: [],
            treeCode: '',
            nodeType: 'wenming',
            projectName: '',
            orgID: props.router.location.query.orgID,
            zxBuBudgetBookId: props.router.location.query.zxBuBudgetBookId,
            status: props.router.location.query.status,
            projectTypeid: '',
            projectTypeName: '',
            batchEditStatus: false
        }
    }

    lastcountFunc = (obj) => {
        //   lastmoney          lastcount   lastprice
        // 后期不含税金额（元） = 后期数量  * 后期不含税单价

        //   lastjxtax          lastcount   lasttaxPrice
        // 后期进项税金（元） =   后期数量  *  后期税金单价
        let rowData = obj.form.getFieldsValue()
        rowData.lastmoney = (rowData.lastcount ? rowData.lastcount : 0) * (rowData.lastprice ? rowData.lastprice : 0)
        rowData.lastjxtax = (rowData.lastcount ? rowData.lastcount : 0) * (rowData.lasttaxPrice ? rowData.lasttaxPrice : 0)
        return rowData
    }

    initializationData = null
    componentDidMount() {
        this.props.hideLockProject();
        // props.showLockProject();
        const { orgID } = this.state
        //获取项目名称
        this.props.myFetch('getZxCtContractDetailByOrgID', { orgID, contrStatus: '1' }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        projectName: data.projectName
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
        //获取项目类型的baseCode
        this.props.myFetch('getBaseCodeSelect', { itemId: 'xiangMuLeiXing' }).then(res => {
            this.setState({
                projectTypeData: res.data
            })
            const projectTypeData = res.data
            //通过项目id获取预算项目信息
            this.props.myFetch('getZxBuProjectTypeList', { departmentId: orgID }).then(
                ({ data, success, message }) => {
                    if (success) {
                        let projectTypeId = ''
                        projectTypeData.forEach(element => {
                            if (element.itemName === data[0].projectTypeName) {
                                projectTypeId = element.itemId
                            }
                        })
                        this.inputForm.form.setFieldsValue({
                            projType: projectTypeId
                        })
                        this.setState({
                            projectTypeName: data[0].projectTypeName,
                            projectTypeid: projectTypeId
                        }, () => {
                            this.refresh('first');
                        })
                    } else {
                        Msg.error(message)
                    }
                }
            );
        })
    }

    refresh(type) {
        this.setState({
            loading: true
        })
        this.props.myFetch('getZxBuYgLiveFeeTree', {
            orgID: this.state.orgID,
            nodeType: this.state.nodeType,
            budgetBookID: this.state.zxBuBudgetBookId
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data ? data : '',
                        loading: false,
                        defaultExpandedKeys: data.length ? [data[0].value] : [],
                    }, () => {
                        if (type && data.length) {
                            this.initializationData = data[0]
                            this.click(data[0].value, data[0].valuePid)
                        }

                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }

    click(value, valuePid) {
        const { myFetch } = this.props;
        myFetch("getZxBuYgLiveFeeTreeList", {
            zxBuYgjLiveFeeId: value,
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeCode: '',
                        dataList: [],
                    }, () => {
                        this.setState({
                            treeCode: data.length ? data[0].treeCode.substring(0, 4) : '',
                            dataList: data
                        })
                    })
                } else {
                }
            }
        )
    }

    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }
    render() {
        const { projectName, status } = this.state;
        const { loading } = this.state;
        return (
            <Spin spinning={loading}>
                <div>
                    <Row >
                        <Col span={24}>
                            <h2>
                                施工预算编制-现场文明施工费-{projectName}
                            </h2>
                        </Col>
                    </Row>
                    <Row>
                        <Col span={6}>
                            <div
                                ref={(me) => {
                                    if (me) {
                                        this.leftDom = me;
                                    }
                                }}>
                                <div

                                    ref={(me) => {
                                        if (me) {
                                            let _this = this;
                                            function moveFn(e) {
                                                let conDomLeft = document.getElementsByClassName('ant-layout-content')[0].offsetLeft;
                                                _this.leftDom.style['flex-basis'] = e.pageX - conDomLeft + 'px'
                                            }
                                            me.addEventListener('mousedown', (e) => {
                                                this.onDragStartPos = e.pageX;
                                                document.addEventListener('mousemove', moveFn, false)
                                            }, false);
                                            document.addEventListener('mouseup', (e) => {
                                                document.removeEventListener('mousemove', moveFn, false)
                                            }, false)
                                        }
                                    }}
                                ></div>
                                {this.state.treeData ? <Tree
                                    selectText={false}
                                    modalType="common" //common  drawer  抽屉出现方式或者普通的
                                    visible
                                    ref={this.treeInstance}
                                    selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
                                    myFetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    btnShow={false} //是否显示底部按钮
                                    disabled={true}
                                    draggable={false}
                                    nodeRender={nodeData => {
                                        return (
                                            <span>
                                                {nodeData["label"]}
                                            </span>
                                        );
                                    }}
                                    treeProps={{
                                        showLine: true
                                    }}
                                    defaultExpandedKeys={this.state.defaultExpandedKeys}
                                    rightMenuOption={[]}
                                    nodeClick={(node) => {
                                        this.setState({
                                            valuePid: '',
                                        }, () => {
                                            this.setState({
                                                defaultExpandedKeys: [node.value],
                                                value: node.value,
                                                valuePid: node.valuePid,
                                            })
                                        })
                                        this.click(node.value, node.valuePid);
                                    }}
                                    data={this.state.treeData}
                                    //键值配置 默认{value:value,label:label,children:children}
                                    keys={{
                                        label: "label",
                                        value: "value",
                                        children: "children"
                                    }}
                                /> : null}
                            </div>
                        </Col>
                        <Col span={18}>
                            <div>

                                <div>
                                    <Row>
                                        <Col span={6}>
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
                                                        sm: { span: 12 }
                                                    },
                                                    wrapperCol: {
                                                        xs: { span: 24 },
                                                        sm: { span: 24 }
                                                    }
                                                }}
                                                fetchConfig={
                                                    {
                                                        apiName: 'getZxBuProjectTypeName',
                                                        otherParams: {
                                                            orgID: this.state.orgID
                                                        }
                                                    }
                                                }
                                                formConfig={[
                                                    {
                                                        label: '项目类型',
                                                        field: 'projectTypeName',
                                                        disabled: true,
                                                        type: 'string',
                                                        // optionConfig: {
                                                        //     label: 'itemName',
                                                        //     value: 'itemId'
                                                        // },
                                                        // optionData: this.state.projectTypeData
                                                    },
                                                    // {
                                                    //     name: 'goback',
                                                    //     type: 'dashed',
                                                    //     label: '返回',
                                                    //     isValidate: false,
                                                    //     onClick: (obj) => {
                                                    //         const { mainModule } = this.props.myPublic.appInfo;
                                                    //         const { orgID, zxBuBudgetBookId } = this.state;
                                                    //         this.props.dispatch(
                                                    //             push(`${mainModule}AfterBudgetingDetial/${orgID}/${zxBuBudgetBookId}`)
                                                    //         )   
                                                    //     }
                                                    // }
                                                ]}
                                            />
                                        </Col>
                                        <Col span={6}>
                                            {status !== '1' ? <div style={{ padding: '12px 11px 11px 0px' }}>
                                                <Button type="primary" onClick={async () => {
                                                    this.props.myFetch('initZxBuYgLiveFeeList', { ...await this.tableList.getTableData()[0] }).then(({ data, success, message }) => {
                                                        if (success) {
                                                            Msg.success('初始化成功!')
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    })
                                                }}>初始化</Button>
                                            </div> : null}
                                        </Col>
                                        <Col span={6}>
                                            {status !== '1' ? <div style={{ padding: '12px 11px 11px 0px' }}>
                                                <Button type="primary" onClick={() => {
                                                    this.setState({
                                                        batchEditStatus: true
                                                    })
                                                }}>批量修改</Button>
                                            </div> : null}
                                        </Col>
                                        <Col span={6}>
                                            <div style={{ padding: '12px 11px 11px 0px' }}>
                                                <Button type="primary" onClick={() => {
                                                    const { mainModule } = this.props.myPublic.appInfo;
                                                    const { orgID, zxBuBudgetBookId } = this.state;
                                                    this.props.dispatch(
                                                        push(`${mainModule}ConstructionBudgetingDetialList?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}&status=${status}`)
                                                    )
                                                }}>返回</Button>
                                            </div>
                                        </Col>
                                    </Row>
                                </div>
                                <div>
                                    {this.state.dataList && this.state.treeCode ? <QnnTable
                                        {...this.props}
                                        fetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                        wrappedComponentRef={(me) => {
                                            this.tableList = me;
                                        }}
                                        method={{
                                            saveOtherParamsFunc: async (obj) => {
                                                return {
                                                    budgetBookID: await obj.qnnTableInstance.getTableData()[0].budgetBookID,
                                                    orgID: await obj.qnnTableInstance.getTableData()[0].orgID,
                                                    price: await obj.qnnTableInstance.getTableData()[0].money
                                                }
                                            }
                                        }}
                                        // desc={<div><span style={{ color: "black" }}>提示：黑色字体表示未变更</span> <span style={{ color: "#ffd400" }}>黄色字体表示变更中</span> <span style={{ color: "red" }}>红色字体表示变更后</span></div>}
                                        actionBtns={this.state.dataList[0].nodeType === "root" || status === '1' ? null : [
                                            {
                                                name: 'add',//内置add del
                                                icon: 'plus',//icon
                                                type: 'primary',//类型  默认 primary  [primary dashed danger]
                                                label: '新增子节点',
                                                disabled: (obj) => {
                                                    if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                formBtns: [
                                                    {
                                                        name: 'cancel', //关闭右边抽屉
                                                        type: 'dashed',//类型  默认 primary
                                                        label: '取消',
                                                    },
                                                    {
                                                        name: 'submit',//内置add del
                                                        type: 'primary',//类型  默认 primary
                                                        label: '保存',//提交数据并且关闭右边抽屉 
                                                        fetchConfig: {//ajax配置
                                                            apiName: 'addZxBuYgjLiveFee',
                                                            otherParams: 'bind:saveOtherParamsFunc'
                                                        },
                                                        onClick: (obj) => {
                                                            if (obj.response.success) {
                                                                this.setState({
                                                                    treeData: null
                                                                }, () => {
                                                                    this.refresh('first')
                                                                    // this.click();
                                                                })
                                                            }
                                                        }
                                                    }
                                                ]
                                            },
                                            {
                                                name: 'edit',//内置add del
                                                icon: 'edit',
                                                type: 'primary',//类型  默认 primary  [primary dashed danger]
                                                label: '修改',
                                                formBtns: [
                                                    {
                                                        name: 'cancel', //关闭右边抽屉
                                                        type: 'dashed',//类型  默认 primary
                                                        label: '取消',
                                                    },
                                                    {
                                                        name: 'submit',//内置add del
                                                        type: 'primary',//类型  默认 primary
                                                        label: '保存',//提交数据并且关闭右边抽屉 
                                                        fetchConfig: {//ajax配置
                                                            apiName: 'updateZxBuYgjLiveFee',
                                                            otherParams: 'bind:saveOtherParamsFunc'
                                                        },
                                                        onClick: (obj) => {
                                                            obj.btnCallbackFn.clearSelectedRows();
                                                            if (obj.response.success) {
                                                                this.setState({
                                                                    treeData: null
                                                                }, () => {
                                                                    this.refresh('first')
                                                                    // this.click();
                                                                })
                                                            }
                                                        }
                                                    }
                                                ]
                                            },
                                            {
                                                name: 'diydel',//内置add del
                                                icon: 'delete',
                                                type: 'danger',//类型  默认 primary  [primary dashed danger]                                
                                                label: '删除',
                                                disabled: (obj) => {
                                                    if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                onClick: (obj) => {
                                                    confirm({
                                                        content: '确定删除此数据吗?',
                                                        onOk: () => {
                                                            this.props.myFetch('batchDeleteUpdateZxBuYgjLiveFee', obj.selectedRows).then(({ success, message, data }) => {
                                                                if (success) {
                                                                    this.setState({
                                                                        treeData: null
                                                                    }, () => {
                                                                        this.refresh('first')
                                                                        // this.click();
                                                                    })
                                                                    obj.btnCallbackFn.clearSelectedRows();
                                                                    obj.btnCallbackFn.refresh();
                                                                } else {
                                                                    Msg.error(message);
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            }
                                        ]}
                                        data={this.state.dataList}
                                        {...config}
                                        formConfig={[
                                            {
                                                isInTable: false,
                                                form: {
                                                    field: 'zxBuYgjLiveFeeId',
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb.rowInfo.name === 'add') {
                                                            const selectRowData = obj.parentTableInfo.qnnTableInstance.getSelectedRows()[0]
                                                            return selectRowData.zxBuYgjLiveFeeId
                                                        }
                                                    },
                                                    type: 'string',
                                                    hide: true,
                                                }
                                            },
                                            // budgetType
                                            {
                                                isInTable: false,
                                                form: {
                                                    field: 'budgetType',
                                                    type: 'number',
                                                    initialValue: () => {
                                                        return '4'
                                                    },
                                                    hide: true,
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    field: 'count',
                                                    type: 'number',
                                                    initialValue: () => {
                                                        return 1
                                                    },
                                                    hide: true,
                                                }
                                            },
                                            // {
                                            //     isInTable: false,
                                            //     form: {
                                            //         field: 'price',
                                            //         type: 'string',
                                            //         initialValue: (obj) => {
                                            //             console.log(obj._formData())
                                            //         },
                                            //         hide: true,
                                            //     }
                                            // },
                                            {
                                                isInTable: false,
                                                form: {
                                                    field: 'projType',
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb.rowInfo.name === 'add') {
                                                            const selectRowData = obj.parentTableInfo.qnnTableInstance.getSelectedRows()[0]
                                                            return selectRowData.projType
                                                        }
                                                    },
                                                    type: 'string',
                                                    hide: true,
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    field: 'parentID',
                                                    type: 'string',
                                                    hide: true,
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb.rowInfo.name === 'add') {
                                                            const selectRowData = obj.parentTableInfo.qnnTableInstance.getSelectedRows()[0]
                                                            return selectRowData.parentID
                                                        }
                                                    },
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    field: 'treeCode',
                                                    type: 'string',
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb.rowInfo.name === 'add') {
                                                            const selectRowData = obj.parentTableInfo.qnnTableInstance.getSelectedRows()[0]
                                                            return selectRowData.treeCode
                                                        }
                                                    },
                                                    hide: true,
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '编号',
                                                    dataIndex: 'pp1',
                                                    key: 'pp1',
                                                    align: 'center'
                                                    // render: (data,rowData)=>{
                                                    // return <div style={{ textIndent: (rowData.treeCode.length - 4) * 2 + 'px' }}>{data}</div>
                                                    // }
                                                },
                                                form: {
                                                    field: 'pp1',
                                                    type: 'string',
                                                    spanForm: 12,
                                                    formItemLayoutForm: {
                                                        labelCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 6 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 18 }
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '名称',
                                                    dataIndex: 'name',
                                                    key: 'name',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'name',
                                                    type: 'string',
                                                    spanForm: 12,
                                                    formItemLayoutForm: {
                                                        labelCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 6 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 18 }
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '节点类型',
                                                    dataIndex: 'nodeType',
                                                    key: 'nodeType',
                                                    align: 'center',
                                                    type: 'select',
                                                },
                                                form: {
                                                    field: 'nodeType',
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'itemName',
                                                        value: 'itemId'
                                                    },
                                                    placeholder: "请输入",
                                                    required: this.state.dataList[0].nodeType === "root" ? true : false,
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb) {
                                                            if (obj.clickCb.rowInfo.name === 'add') {
                                                                const selectRowData = obj.parentTableInfo.qnnTableInstance.getSelectedRows()[0]
                                                                return selectRowData.nodeType === "root" ? null : selectRowData.nodeType
                                                            }
                                                        }
                                                    },
                                                    addDisabled: this.state.dataList[0].nodeType === "root" ? false : true,
                                                    editDisabled: this.state.dataList[0].nodeType === "root" ? false : true,
                                                    fetchConfig: {
                                                        apiName: 'getBaseCodeSelect',
                                                        otherParams: {
                                                            itemId: 'jieDianLeiXing'
                                                        }
                                                    },
                                                    spanForm: 12,
                                                    formItemLayoutForm: {
                                                        labelCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 6 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 18 }
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '单位',
                                                    dataIndex: 'unit',
                                                    key: 'unit',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'unit',
                                                    type: 'string',
                                                    spanForm: 12,
                                                    formItemLayoutForm: {
                                                        labelCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 6 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 18 }
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '已发生不含税金额(元)',
                                                    dataIndex: 'money',
                                                    key: 'money',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'money',
                                                    type: 'number',
                                                    spanForm: 12,
                                                    formItemLayoutForm: {
                                                        labelCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 6 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 18 }
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '已发生进项税金(元)',
                                                    dataIndex: 'taxPrice',
                                                    key: 'taxPrice',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'taxPrice',
                                                    type: 'number',
                                                    spanForm: 12,
                                                    formItemLayoutForm: {
                                                        labelCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 6 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 18 }
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '后期数量',
                                                    dataIndex: 'lastcount',
                                                    key: 'lastcount',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'lastcount',
                                                    type: 'number',
                                                    spanForm: 12,
                                                    formItemLayoutForm: {
                                                        labelCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 6 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 18 }
                                                        }
                                                    },
                                                    onChange: (val, obj) => {
                                                        obj.form.setFieldsValue({
                                                            ...this.lastcountFunc(obj)
                                                        })
                                                    }
                                                },

                                            },
                                            {
                                                table: {
                                                    title: '后期不含税单价',
                                                    dataIndex: 'lastprice',
                                                    key: 'lastprice',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'lastprice',
                                                    type: 'number',
                                                    spanForm: 12,
                                                    formItemLayoutForm: {
                                                        labelCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 6 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 18 }
                                                        }
                                                    },
                                                    onChange: (val, obj) => {
                                                        obj.form.setFieldsValue({
                                                            ...this.lastcountFunc(obj)
                                                        })
                                                    }
                                                },
                                            },
                                            {
                                                table: {
                                                    title: '后期不含税金额(元)',
                                                    dataIndex: 'lastmoney',
                                                    key: 'lastmoney',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'lastmoney',
                                                    type: 'number',
                                                    spanForm: 12,
                                                    formItemLayoutForm: {
                                                        labelCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 6 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 18 }
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '后期税金单价',
                                                    dataIndex: 'lasttaxPrice',
                                                    key: 'lasttaxPrice',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'lasttaxPrice',
                                                    type: 'number',
                                                    spanForm: 12,
                                                    formItemLayoutForm: {
                                                        labelCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 6 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 18 }
                                                        }
                                                    },
                                                    onChange: (val, obj) => {
                                                        obj.form.setFieldsValue({
                                                            ...this.lastcountFunc(obj)
                                                        })
                                                    }
                                                },

                                            },
                                            {
                                                table: {
                                                    title: '后期进项税金(元)',
                                                    dataIndex: 'lastjxtax',
                                                    key: 'lastjxtax',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'lastjxtax',
                                                    type: 'number',
                                                    spanForm: 12,
                                                    formItemLayoutForm: {
                                                        labelCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 6 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 18 }
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '备注',
                                                    width: 200,
                                                    dataIndex: 'remarks',
                                                    key: 'remarks',
                                                    tooltip: 23,
                                                },
                                                isInForm: false
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    field: 'leaf',
                                                    type: 'number',
                                                    hide: true,
                                                }
                                            }
                                        ]}
                                    /> : null}
                                </div>
                                <BatchEdit
                                    {...this.props}
                                    show={this.state.batchEditStatus}
                                    dataList={this.state.dataList}
                                    type={'xcwm_sg'}
                                    updateComplete={() => {
                                        this.refresh('first')
                                    }}
                                    setShowData={() => {
                                        this.setState({
                                            batchEditStatus: false
                                        })
                                    }}
                                />
                            </div>
                        </Col>
                    </Row>
                </div>
            </Spin>
        );
    }
}

export default index;