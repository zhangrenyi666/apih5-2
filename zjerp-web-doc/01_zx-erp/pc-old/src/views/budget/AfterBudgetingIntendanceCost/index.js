import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Spin, Row, Col, Modal, Button } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
import { push } from "react-router-redux";

const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.zxBuYgjLiveStandardId
        },
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
        this.state = {
            loading: false,
            defaultExpandedKeys: [],
            dataLbist: [],
            treeCode: '',
            nodeType: 'guanli',
            projectName: '',
            orgID: props.match.params.orgID,
            zxBuBudgetBookId: props.match.params.zxBuBudgetBookId,
            projectTypeid: '',
            projectTypeName: '',
        }
    }
    componentDidMount() {
        const {orgID} = this.state
        //获取项目名称
        this.props.myFetch('getSysProjectDetail', {departmentId: orgID}).then(
            ({ data, success, message }) => {
                if(success){
                    this.setState({
                        projectName : data.projectName
                    })
                }else {
                    Msg.error(message)
                }
            }
        );
        //获取项目类型的baseCode
        this.props.myFetch('getBaseCodeSelect',{itemId: 'xiangMuLeiXing'}).then(res=>{
            this.setState({
                projectTypeData: res.data
            })
            const projectTypeData = res.data
            //通过项目id获取预算项目信息
            this.props.myFetch('getZxBuProjectTypeList', {departmentId: orgID}).then(
                ({ data, success, message }) => {
                    if(success){
                        let projectTypeId = ''
                        projectTypeData.forEach(element => {
                            if(element.itemName === data[0].projectTypeName){
                                projectTypeId = element.itemId
                            }
                        })
                        this.inputForm.form.setFieldsValue({
                            projType: projectTypeId
                        })
                        this.setState({
                            projectTypeName: data[0].projectTypeName,
                            projectTypeid : projectTypeId
                        },()=>{
                            this.refresh();
                        })
                    }else {
                        Msg.error(message)
                    }
                }
            );
        })
    }
    refresh(){
        this.setState({
            loading: true
        })
        this.props.myFetch('getZxBuYgjLiveStandardTree', {
            projType: this.state.projectTypeid,
            nodeType: this.state.nodeType
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data ? data : '',
                        loading: false,
                        defaultExpandedKeys: data.length ? [data[0].value] : [],
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    click(value, valuePid) {
        const { myFetch } = this.props;
        myFetch("getZxBuYgjLiveStandardTreeList", {
            zxBuYgjLiveStandardId: value,
            projType: this.inputForm.form.getFieldsValue().projType,
            parentID: valuePid,
            nodeType: this.state.nodeType
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
        const { orgID, projectName} = this.state;
        const { loading } = this.state;
        return (
            <Spin spinning={loading}>
                <div>
                    <Row className={s.bor}>
                        <Col span={24}>
                            <h2>
                                标后预算编制-经理部管理费-{projectName}
                            </h2>
                        </Col>
                    </Row>
                    <Row>
                        <div className={s.root}>
                            <div className={s.rootl}
                                ref={(me) => {
                                    if (me) {
                                        this.leftDom = me;
                                    }
                                }}>
                                <div
                                    className={s.hr}
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
                            <div className={s.rootr}>
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
                                                formConfig={[
                                                    {
                                                        label: '项目类型',
                                                        field: 'projType',
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId'
                                                        },
                                                        optionData: this.state.projectTypeData
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
                                            <div style={{ padding: '12px 11px 11px 0px' }}>
                                                <Button type="dashed" onClick={()=>{
                                                    const { mainModule } = this.props.myPublic.appInfo;
                                                    const { orgID, zxBuBudgetBookId } = this.state;
                                                    this.props.dispatch(
                                                        push(`${mainModule}AfterBudgetingDetial/${orgID}/${zxBuBudgetBookId}`)
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
                                        // desc={<div><span style={{ color: "black" }}>提示：黑色字体表示未变更</span> <span style={{ color: "#ffd400" }}>黄色字体表示变更中</span> <span style={{ color: "red" }}>红色字体表示变更后</span></div>}
                                        actionBtns={this.state.dataList[0].nodeType === "root" ? null : [
                                            {
                                                name: 'add',//内置add del
                                                icon: 'plus',//icon
                                                type: 'primary',//类型  默认 primary  [primary dashed danger]
                                                label: '新增子节点',
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
                                                            apiName: 'addZxBuYgjLiveStandard',
                                                        },
                                                        onClick: (obj) => {
                                                            if (obj.response.success) {
                                                                this.setState({
                                                                    treeData: null
                                                                }, () => {
                                                                    this.refresh();
                                                                    this.click();
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
                                                            apiName: 'updateZxBuYgjLiveStandard',
                                                        },
                                                        onClick: (obj) => {
                                                            obj.btnCallbackFn.clearSelectedRows();
                                                            if (obj.response.success) {
                                                                this.setState({
                                                                    treeData: null
                                                                }, () => {
                                                                    this.refresh();
                                                                    this.click();
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
                                                            this.props.myFetch('batchDeleteUpdateZxBuYgjLiveStandard', obj.selectedRows).then(({ success, message, data }) => {
                                                                if (success) {
                                                                    this.setState({
                                                                        treeData: null
                                                                    }, () => {
                                                                        this.refresh();
                                                                        this.click();
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
                                                    field: 'zxBuYgjLiveStandardId',
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb.rowInfo.name === 'add') {
                                                            if (this.tableList.state.data.length) {
                                                                let qdxzRowData = this.tableList.state.data[0];
                                                                return qdxzRowData.zxBuYgjLiveStandardId
                                                            }
                                                        }
                                                    },
                                                    type: 'string',
                                                    hide: true,
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    field: 'projType',
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb.rowInfo.name === 'add') {
                                                            if (this.tableList.state.data.length) {
                                                                let qdxzRowData = this.tableList.state.data[0];
                                                                return qdxzRowData.projType
                                                            }
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
                                                            if (this.tableList.state.data.length) {
                                                                let qdxzRowData = this.tableList.state.data[0];
                                                                return qdxzRowData.parentID
                                                            }
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
                                                            if (this.tableList.state.data.length) {
                                                                let qdxzRowData = this.tableList.state.data[0];
                                                                return qdxzRowData.treeCode
                                                            }
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
                                                    align:'center'
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
                                                    initialValue: (val) => {
                                                        return this.state.dataList[0].nodeType === "root" ? null : this.state.dataList[0].nodeType
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
                                            //从这开始
                                            {
                                                table: {
                                                    title: this.state.treeCode === '0003' ? '数量(人)' : '数量',
                                                    dataIndex: 'count',
                                                    key: 'count',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'count',
                                                    type: 'number',
                                                    spanForm: 12,
                                                    // addDisabled: this.state.dataList[1] ? this.state.dataList[1].leaf === 1 ? false : true : false,
                                                    editDisabled: this.state.dataList[1] ? this.state.dataList[1].leaf === 1 ? false : true : false,
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
                                                    onChange:()=>{
                                                        if (this.tableList.qnnForm.form.getFieldsValue().count && this.tableList.qnnForm.form.getFieldsValue().price) {
                                                            this.tableList.qnnForm.form.setFieldsValue({
                                                                money: this.FloatMulTwo(this.tableList.qnnForm.form.getFieldsValue().count, this.tableList.qnnForm.form.getFieldsValue().price)
                                                            })
                                                        }
                                                        if (this.tableList.qnnForm.form.getFieldsValue().count && this.tableList.qnnForm.form.getFieldsValue().priceTax) {
                                                            this.tableList.qnnForm.form.setFieldsValue({
                                                                moneyTax: this.FloatMulTwo(this.tableList.qnnForm.form.getFieldsValue().count, this.tableList.qnnForm.form.getFieldsValue().priceTax)
                                                            })
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                table: {
                                                    title: this.state.treeCode === '0003' ? '工期(月)' : '不含税单价',
                                                    dataIndex: 'price',
                                                    key: 'price',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'price',
                                                    type: 'number',
                                                    spanForm: 12,
                                                    // addDisabled: this.state.dataList[1] ? this.state.dataList[1].leaf === 1 ? false : true : false,
                                                    editDisabled: this.state.dataList[1] ? this.state.dataList[1].leaf === 1 ? false : true : false,
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
                                                    onChange:()=>{
                                                        if (this.tableList.qnnForm.form.getFieldsValue().count && this.tableList.qnnForm.form.getFieldsValue().price) {
                                                            this.tableList.qnnForm.form.setFieldsValue({
                                                                money: this.FloatMulTwo(this.tableList.qnnForm.form.getFieldsValue().count, this.tableList.qnnForm.form.getFieldsValue().price)
                                                            })
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                table: {
                                                    title: this.state.treeCode === '0003' ? '金额(元)' : '不含税金额(元)',
                                                    dataIndex: 'money',
                                                    key: 'money',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'money',
                                                    type: 'number',
                                                    spanForm: 12,
                                                    addDisabled: true,
                                                    editDisabled: true,
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
                                                }
                                            },
                                            {
                                                table: {
                                                    title: this.state.treeCode === '0003' ? '标准' : '税金单价',
                                                    dataIndex: 'priceTax',
                                                    key: 'priceTax',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'priceTax',
                                                    type: 'number',
                                                    spanForm: 12,
                                                    // addDisabled: this.state.dataList[1] ? this.state.dataList[1].leaf === 1 ? false : true : false,
                                                    editDisabled: this.state.dataList[1] ? this.state.dataList[1].leaf === 1 ? false : true : false,
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
                                                    onChange:()=>{
                                                        if (this.tableList.qnnForm.form.getFieldsValue().count && this.tableList.qnnForm.form.getFieldsValue().priceTax) {
                                                            this.tableList.qnnForm.form.setFieldsValue({
                                                                moneyTax: this.FloatMulTwo(this.tableList.qnnForm.form.getFieldsValue().count, this.tableList.qnnForm.form.getFieldsValue().priceTax)
                                                            })
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                isInTable: this.state.treeCode === '0003' ? false : true,
                                                table: {
                                                    title: '进项税金(元)',
                                                    dataIndex: 'moneyTax',
                                                    key: 'moneyTax',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'moneyTax',
                                                    type: 'number',
                                                    spanForm: 12,
                                                    addDisabled: true,
                                                    editDisabled: true,
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
                                                    hide: (obj) => {
                                                        if (this.state.treeCode === '0003') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '是否向上汇总',
                                                    field: 'activity',
                                                    type: "radio",
                                                    optionData: [  //可为function (props)=>array
                                                        {
                                                            label: "是",
                                                            value: "1"
                                                        },
                                                        {
                                                            label: "否",
                                                            value: "0"
                                                        }
                                                    ],
                                                    initialValue: "1",
                                                    hide: this.state.dataList[1] ? this.state.dataList[1].leaf === 1 ? false : true : false
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '备注',
                                                    dataIndex: 'remarks',
                                                    key: 'remarks',
                                                    align: 'center'
                                                },
                                                form: {
                                                    field: 'remarks',
                                                    type: 'string'
                                                }
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
                            </div>
                        </div>
                    </Row>
                </div>
            </Spin>
        );
    }
}

export default index;