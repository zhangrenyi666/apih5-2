import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Row, Col } from 'antd';
import { push } from "react-router-redux";
import s from "./style.less";
import Tree from "../../modules/tree";
const configTop = {
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 10 },
            sm: { span: 10 }
        },
        wrapperCol: {
            xs: { span: 14 },
            sm: { span: 14 }
        }
    },
    isShowRowSelect: true,

};
const configBottom = {
    // paginationConfig: {
    //     position: 'bottom'
    // },
    formItemLayout: {
        labelCol: {
            xs: { span: 10 },
            sm: { span: 10 }
        },
        wrapperCol: {
            xs: { span: 14 },
            sm: { span: 14 }
        }
    },
    // isShowRowSelect: false,
    paginationConfig: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.props = this.props.props
        this.state = {
            defaultExpandedKeys: [],
            value: '',
            valuePid: '',
            treeData: null,
            orgID: props.router.location.query.orgID,
            zxBuBudgetBookId: props.router.location.query.zxBuBudgetBookId,
            status: props.router.location.query.status,
            activeIndex: -1,
            QDid: '',
            projectName: '',
            QDFlagData: null,
        }
    }
    // 加法
    floatAccuracyPlusFunc = (arg1, arg2) => {
        let m = 0
        let r1 = arg1.toString().split(".")[1] ? arg1.toString().split(".")[1].length : 0
        let r2 = arg1.toString().split(".")[1] ? arg1.toString().split(".")[1].length : 0
        m = Math.pow(10, Math.max(r1, r2))
        return (arg1 * m + arg2 * m) / m
    }
    // 乘法
    floatAccuracyMultiplicationFunc = (arg1, arg2) => {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length;
        }
        catch (e) {
        }
        try {
            m += s2.split(".")[1].length;
        }
        catch (e) {
        }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    }

    //  减法
    floatAccuracySubtractionFunc(arg1, arg2) {
        var r1, r2, m, n;
        try {
            r1 = arg1.toString().split(".")[1].length;
        }
        catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        }
        catch (e) {
            r2 = 0;
        }
        m = Math.pow(10, Math.max(r1, r2));
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    }

    checkRowData = null
    // 计算器方法
    calculator = (string) => {
        const reBrackets = /\([^()]+\)/
        const reMultiplicationDivision = /\d+(\.\d+)?[*/][+-]?\d+(\.\d+)?/
        let removeBracketFunc = (val) => {
            if (reBrackets.test(val)) {
                let str = val.replace(reBrackets.exec(val)[0], calcExpFunc(reBrackets.exec(val)[0]))
                if (reBrackets.test(str)) {
                    return removeBracketFunc(str)
                } else {
                    return calcExpFunc(str)
                }
            } else {
                if (reMultiplicationDivision.test(val)) {
                    return calcResFunc(val)
                } else {
                    return plusFunc(val)
                }
            }
        }

        let calcExpFunc = (val) => {
            if (reMultiplicationDivision.test(val)) {
                let str = val.replace(reMultiplicationDivision.exec(val)[0], calcResFunc(reMultiplicationDivision.exec(val)[0]))
                if (reMultiplicationDivision.test(str)) {
                    return calcExpFunc(str)
                } else {
                    return plusFunc(str)
                }
            } else {
                return plusFunc(val)
            }
        }

        let calcResFunc = (val) => {
            if (val.indexOf('/') > 0) {
                let n = val.split('/')
                return floatAccDivFunc(n[0], n[1])
            } else if (val.indexOf('*') > 0) {
                let n = val.split('*')
                return floatAccMulFunc(n[0], n[1])
            } else {
                return plusFunc(val)
            }
        }

        let plusFunc = (val) => {
            if (!reBrackets.test(val) && !reMultiplicationDivision.test(val) && val.indexOf('+') < 0 && val.indexOf('-') < 0) {
                return val
            } else {
                const numberValue = val.indexOf('(') < 0 || val.indexOf(')') < 0 ? val : val.split('(')[1].split(')')[0]
                let finalNumber = 0
                if (val.indexOf('-') > 0) {
                    let toNum = numberValue.split('-').map(item => {
                        if (item.indexOf('+') > 0) {
                            return item
                        } else {
                            return +item
                        }
                    })
                    let negative = toNum.map((item2, index) => {
                        if (index !== 0) {
                            return '-' + item2
                        } else {
                            return item2
                        }
                    })
                    negative.map(item3 => {
                        if (typeof item3 === 'string' && item3.indexOf('+') > 0) {
                            let item3Num = 0
                            item3.split('+').map(itemm => {
                                item3Num += +itemm
                                return true
                            })
                            item3 = +item3Num
                        }
                        finalNumber = floatAccuracyFunc(finalNumber, +item3)
                        return true
                    })
                } else {
                    numberValue.split('+').map(item => {
                        finalNumber = floatAccuracyFunc(finalNumber, +item)
                        return true
                    })
                }
                return finalNumber
            }
        }

        let floatAccuracyFunc = (arg1, arg2) => {
            let m = 0
            let r1 = arg1.toString().split(".")[1] ? arg1.toString().split(".")[1].length : 0
            let r2 = arg1.toString().split(".")[1] ? arg1.toString().split(".")[1].length : 0
            m = Math.pow(10, Math.max(r1, r2))
            return (arg1 * m + arg2 * m) / m
        }

        let floatAccDivFunc = (num1, num2) => {
            var t1, t2, r1, r2;
            try { t1 = num1.toString().split('.')[1].length } catch (e) { t1 = 0 }
            try { t2 = num2.toString().split(".")[1].length } catch (e) { t2 = 0 }
            r1 = Number(num1.toString().replace(".", ""));
            r2 = Number(num2.toString().replace(".", ""));
            return (r1 / r2) * Math.pow(10, t2 - t1);
        }

        let floatAccMulFunc = (num1, num2) => {
            var m = 0, s1 = num1.toString(), s2 = num2.toString();
            try { m += s1.split(".")[1].length } catch (e) { };
            try { m += s2.split(".")[1].length } catch (e) { };
            return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
        }

        return +removeBracketFunc(string)
    }

    componentDidMount() {
        this.props.hideLockProject();
        const { orgID } = this.state
        this.props.myFetch('getZxCtWorksWorkNameTree', { orgID: orgID }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data,
                        valuePid: data.length ? data[0].valuePid : '',
                        value: data.length ? data[0].value : '',
                        defaultExpandedKeys: data.length ? [data[0].value] : [],
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
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
    }
    render() {
        const { orgID, projectName, status } = this.state;
        return (
            <div>
                <Row>
                    <Col span={24}>
                        <h2>
                            施工预算编制-工程量清单内工程-{projectName}
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
                            {this.state.treeData ? <Tree
                                selectText={false}
                                modalType="common" //common  drawer  抽屉出现方式或者普通的
                                //树结构是否处于出现状态
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
                                        valuePid: ''
                                    }, () => {
                                        this.setState({
                                            defaultExpandedKeys: [node.value],
                                            value: node.value,
                                            valuePid: node.valuePid,
                                            activeIndex: -1,
                                            QDid: ''
                                        })
                                    })
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
                                <div>
                                    {this.state.valuePid ? <QnnTable
                                        {...this.props}
                                        fetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                        antd={{
                                            rowKey: 'zxBuControlId',
                                            size: 'small',
                                            onRow: (record, index) => {
                                                return {
                                                    onClick: () => {
                                                        this.setState({
                                                            activeIndex: index,
                                                            QDid: record.id
                                                        })
                                                    }
                                                }
                                            },
                                            // rowClassName: (record, index) => {
                                            //     return index === this.state.activeIndex ? s.highlight : '';
                                            // }
                                        }}
                                        wrappedComponentRef={(me) => {
                                            this.tableQD = me;
                                        }}
                                        fetchConfig={{
                                            apiName: 'getZxBuControlTreeList',
                                            otherParams: {
                                                parentID: this.state.valuePid === '-1' ? this.state.valuePid : this.state.value,
                                                orgID: orgID,
                                                budgetBookID: this.state.zxBuBudgetBookId
                                            }
                                        }}
                                        actionBtns={[
                                            {
                                                name: 'goback',
                                                type: 'dashed',
                                                label: '返回',
                                                isValidate: false,
                                                onClick: (obj) => {
                                                    const { mainModule } = this.props.myPublic.appInfo;
                                                    const { orgID, zxBuBudgetBookId } = this.state;
                                                    this.props.dispatch(
                                                        push(`${mainModule}ConstructionBudgetingDetialList?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}&status=${status}`)
                                                    )
                                                }
                                            }
                                        ]}
                                        {...configTop}
                                        rowSelection={{
                                            type: 'radio',
                                            onChange: (selectedRowKey, selectedData, delKey) => {
                                                this.checkRowData = selectedData.length ? selectedData[0] : null
                                                this.setState({
                                                    QDid: selectedData.length ? selectedData[0].workID : null
                                                })
                                            },
                                            getCheckboxProps: record => ({
                                                // name:record.name,
                                                disabled: record.isLeaf === '0',
                                            }),
                                        }}
                                        rowClassName={(record, index) => {
                                            return record.isLeaf === '0' ? s.backgrounde6 : ''
                                        }}
                                        formConfig={[
                                            {
                                                isInTable: false,
                                                form: {
                                                    field: 'zxBuControlId',
                                                    type: 'string',
                                                    hide: true,
                                                }
                                            },
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
                                                    dataIndex: 'workNoForTree',
                                                    key: 'workNoForTree',
                                                    align: 'center'
                                                    // render: (data, rowData) => {
                                                    //     return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>{data}</div>;
                                                    // }
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
                                                    title: '计量单位',
                                                    dataIndex: 'unit',
                                                    key: 'unit',
                                                    align: 'center'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '单价',
                                                    dataIndex: 'budgetPrice',
                                                    key: 'budgetPrice',
                                                    align: 'center',
                                                    render: (data, rowData) => {
                                                        return data ? data : 0
                                                    }

                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '数量',
                                                    children: [
                                                        {
                                                            title: '合同内',
                                                            dataIndex: 'qtyContract',
                                                            key: 'qtyContract',
                                                            align: 'center',
                                                            render: (data, rowData) => {
                                                                return data ? data : 0
                                                            }
                                                        },
                                                        {
                                                            title: '变更',
                                                            dataIndex: 'qtyChange',
                                                            key: 'qtyChange',
                                                            align: 'center',
                                                            render: (data, rowData) => {
                                                                return data ? data : 0
                                                            }
                                                        }
                                                    ]

                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: "已完工程",
                                                    children: [
                                                        {
                                                            title: "工程量",
                                                            children: [
                                                                {
                                                                    title: '合同内',
                                                                    dataIndex: 'finEngContract',
                                                                    key: 'finEngContract',
                                                                    align: 'center',
                                                                    tdEdit: () => {
                                                                        return status !== '1'
                                                                    },
                                                                    fieldConfig: {
                                                                        disabled: (args) => {
                                                                            return args.rowData.isLeaf === '0'
                                                                        },
                                                                        type: "number",
                                                                        field: 'finEngContract',
                                                                        onBlur: async (e, obj) => {
                                                                            let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                                            let newData = {}
                                                                            const value = e.target.value
                                                                            // 变更都需要有值 且当前为叶子节点
                                                                            if (value) {
                                                                                if (rowData.isLeaf !== '0') {
                                                                                    //已完工程-工程量-小计finEngSubtotal = 已完工程工程量合同内finEngContract + 已完工程工程量变更finEngChange
                                                                                    newData.finEngSubtotal = this.floatAccuracyPlusFunc(rowData.finEngContract || 0, rowData.finEngChange || 0)
                                                                                    //已完工程-不含税-金额(元)finTFAmount  = 已完工程工程量小计finEngSubtotal * 已完工程不含税综合基价finTFPrice
                                                                                    newData.finTFAmount = this.floatAccuracyMultiplicationFunc(rowData.finEngSubtotal || 0, rowData.finTFPrice || 0)
                                                                                    //已完工程-施工进项税-金额(元)finCIAmount = 已完工程工程量小计finEngSubtotal * 已完工程施工进项税单价finCIUPrice
                                                                                    newData.finCIAmount = this.floatAccuracyMultiplicationFunc(rowData.finEngSubtotal || 0, rowData.finCIUPrice || 0)
                                                                                    //剩余工程-工程量-合同内remEngContract = 数量合同内qtyContract - 已完工程程量合同内finEngContract
                                                                                    rowData.remEngContract = this.floatAccuracySubtractionFunc(rowData.qtyContract || 0, rowData.finEngContract || 0)
                                                                                    //剩余工程-工程量-变更remEngChange = 数量变更qtyChange - 已完工程工程量变更finEngChange
                                                                                    rowData.remEngChange = this.floatAccuracySubtractionFunc(rowData.qtyChange || 0, rowData.finEngChange || 0)
                                                                                    //剩余工程-工程量-小计remEngSubtotal = 已完成工程量合同内remEngContract + 已完成工程量变更remEngChange
                                                                                    rowData.remEngSubtotal = this.floatAccuracyPlusFunc(rowData.remEngContract || 0, rowData.remEngChange || 0)
                                                                                    //剩余工程-不含税-金额(元)remTFAmount = 剩余工程 工程量 小计remEngSubtotal * 剩余工程 不含税 综合基价 remTFPrice
                                                                                    newData.remTFAmount = this.floatAccuracyMultiplicationFunc(rowData.remEngSubtotal || 0, rowData.remTFPrice || 0)
                                                                                    //剩余工程-施工进项税-金额(元)remCIAmount = 剩余工程施工进项税单价remCIUPrice * 剩余工程工程量小计remEngSubtotal
                                                                                    newData.remCIAmount = this.floatAccuracyMultiplicationFunc(rowData.remCIUPrice || 0, rowData.remEngSubtotal || 0)

                                                                                    await obj.qnnTableInstance.setEditedRowData({ ...rowData, ...newData })

                                                                                    const {  success, message } = await this.props.myFetch('updateZxBuControl', { ...rowData, ...newData })
                                                                                    if (success) {
                                                                                        // const { zxBuControlId } = await obj.qnnTableInstance.getEditedRowData()
                                                                                        if (await obj.qnnTableInstance.getSelectedRows().length && (rowData.zxBuControlId === await obj.qnnTableInstance.getSelectedRows()[0].zxBuControlId)) {
                                                                                            // zxBuControlId
                                                                                            this.checkRowData = await obj.qnnTableInstance.getEditedRowData()
                                                                                        }
                                                                                        obj.qnnTableInstance.refresh()
                                                                                        Msg.success('保存成功')
                                                                                    } else {
                                                                                        // obj.qnnTableInstance.refresh()
                                                                                        Msg.error(message)
                                                                                    }
                                                                                } else {
                                                                                    Msg.error('当前非叶子节点')
                                                                                }
                                                                            }
                                                                        }
                                                                    },
                                                                },
                                                                {
                                                                    title: '变更',
                                                                    dataIndex: 'finEngChange',
                                                                    key: 'finEngChange',
                                                                    align: 'center',
                                                                    tdEdit: () => {
                                                                        return status !== '1'
                                                                    },
                                                                    fieldConfig: {
                                                                        disabled: (args) => {
                                                                            return args.rowData.isLeaf === '0'
                                                                        },
                                                                        type: "number",
                                                                        field: 'finEngChange',
                                                                        onBlur: async (e, obj) => {
                                                                            let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                                            let newData = {}
                                                                            const value = e.target.value
                                                                            // 合同内需要有值 且当前为叶子节点
                                                                            if (value) {
                                                                                if (rowData.isLeaf !== '0') {
                                                                                    //已完工程-工程量-小计finEngSubtotal = 已完工程工程量合同内finEngContract + 已完工程工程量变更finEngChange
                                                                                    newData.finEngSubtotal = this.floatAccuracyPlusFunc(rowData.finEngContract || 0, rowData.finEngChange || 0)
                                                                                    //已完工程-不含税-金额(元)finTFAmount  = 已完工程工程量小计finEngSubtotal * 已完工程不含税综合基价finTFPrice
                                                                                    newData.finTFAmount = this.floatAccuracyMultiplicationFunc(rowData.finEngSubtotal || 0, rowData.finTFPrice || 0)
                                                                                    //已完工程-施工进项税-金额(元)finCIAmount = 已完工程工程量小计finEngSubtotal * 已完工程施工进项税单价finCIUPrice
                                                                                    newData.finCIAmount = this.floatAccuracyMultiplicationFunc(rowData.finEngSubtotal || 0, rowData.finCIUPrice || 0)
                                                                                    //剩余工程-工程量-合同内remEngContract = 数量合同内qtyContract - 已完工程程量合同内finEngContract
                                                                                    rowData.remEngContract = this.floatAccuracySubtractionFunc(rowData.qtyContract || 0, rowData.finEngContract || 0)
                                                                                    //剩余工程-工程量-变更remEngChange = 数量变更qtyChange - 已完工程工程量变更finEngChange
                                                                                    rowData.remEngChange = this.floatAccuracySubtractionFunc(rowData.qtyChange || 0, rowData.finEngChange || 0)
                                                                                    //剩余工程-工程量-小计remEngSubtotal = 已完成工程量合同内remEngContract + 已完成工程量变更remEngChange
                                                                                    rowData.remEngSubtotal = this.floatAccuracyPlusFunc(rowData.remEngContract || 0, rowData.remEngChange || 0)
                                                                                    //剩余工程-不含税-金额(元)remTFAmount = 剩余工程 工程量 小计remEngSubtotal * 剩余工程 不含税 综合基价 remTFPrice
                                                                                    newData.remTFAmount = this.floatAccuracyMultiplicationFunc(rowData.remEngSubtotal || 0, rowData.remTFPrice || 0)
                                                                                    //剩余工程-施工进项税-金额(元)remCIAmount = 剩余工程施工进项税单价remCIUPrice * 剩余工程工程量小计remEngSubtotal
                                                                                    newData.remCIAmount = this.floatAccuracyMultiplicationFunc(rowData.remCIUPrice || 0, rowData.remEngSubtotal || 0)

                                                                                    await obj.qnnTableInstance.setEditedRowData({ ...rowData, ...newData })

                                                                                    const {  success, message } = await this.props.myFetch('updateZxBuControl', { ...rowData, ...newData })
                                                                                    if (success) {
                                                                                        // const { zxBuControlId } = await obj.qnnTableInstance.getEditedRowData()
                                                                                        if (await obj.qnnTableInstance.getSelectedRows().length && (rowData.zxBuControlId === await obj.qnnTableInstance.getSelectedRows()[0].zxBuControlId)) {
                                                                                            // zxBuControlId
                                                                                            this.checkRowData = await obj.qnnTableInstance.getEditedRowData()
                                                                                        }
                                                                                        obj.qnnTableInstance.refresh()
                                                                                        Msg.success('保存成功')
                                                                                    } else {
                                                                                        // obj.qnnTableInstance.refresh()
                                                                                        Msg.error(message)
                                                                                    }
                                                                                } else {
                                                                                    Msg.error('当前非叶子节点')
                                                                                }
                                                                            }
                                                                        }
                                                                    },
                                                                },
                                                                {
                                                                    title: '小计',
                                                                    dataIndex: 'finEngSubtotal',
                                                                    key: 'finEngSubtotal',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                }
                                                            ]
                                                        },

                                                        {
                                                            title: "不含税",
                                                            children: [
                                                                {
                                                                    title: '综合基价',
                                                                    dataIndex: 'finTFPrice',
                                                                    key: 'finTFPrice',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                },
                                                                {
                                                                    title: '金额',
                                                                    dataIndex: 'finTFAmount',
                                                                    key: 'finTFAmount',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                }
                                                            ]
                                                        },
                                                        {
                                                            title: "不含税",
                                                            children: [
                                                                {
                                                                    title: '工序单价',
                                                                    dataIndex: 'finTechPrice',
                                                                    key: 'finTechPrice',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                },
                                                                {
                                                                    title: '主材单价',
                                                                    dataIndex: 'finResPrice',
                                                                    key: 'finResPrice',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                }
                                                            ]
                                                        },
                                                        {
                                                            title: "施工进项税",
                                                            children: [
                                                                {
                                                                    title: '单价',
                                                                    dataIndex: 'finCIUPrice',
                                                                    key: 'finCIUPrice',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                },
                                                                {
                                                                    title: '金额',
                                                                    dataIndex: 'finCIAmount',
                                                                    key: 'finCIAmount',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                }
                                                            ]
                                                        }

                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: "剩余工程",
                                                    children: [
                                                        {
                                                            title: "工程量",
                                                            children: [
                                                                {
                                                                    title: '合同内',
                                                                    dataIndex: 'remEngContract',
                                                                    key: 'remEngContract',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                },
                                                                {
                                                                    title: '变更',
                                                                    dataIndex: 'remEngChange',
                                                                    key: 'remEngChange',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                },
                                                                {
                                                                    title: '小计',
                                                                    dataIndex: 'remEngSubtotal',
                                                                    key: 'remEngSubtotal',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                }
                                                            ]

                                                        },
                                                        {
                                                            title: "不含税",
                                                            children: [
                                                                {
                                                                    title: '综合基价',
                                                                    dataIndex: 'remTFPrice',
                                                                    key: 'remTFPrice',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                },
                                                                {
                                                                    title: '金额',
                                                                    dataIndex: 'remTFAmount',
                                                                    key: 'remTFAmount',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                }
                                                            ]

                                                        },
                                                        {
                                                            title: "不含税",
                                                            children: [
                                                                {
                                                                    title: '工序单价',
                                                                    dataIndex: 'remTechPrice',
                                                                    key: 'remTechPrice',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                },
                                                                {
                                                                    title: '主材单价',
                                                                    dataIndex: 'remResPrice',
                                                                    key: 'remResPrice',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                }
                                                            ]
                                                        },
                                                        {
                                                            title: "施工进项税",
                                                            children: [
                                                                {
                                                                    title: '单价',
                                                                    dataIndex: 'remCIUPrice',
                                                                    key: 'remCIUPrice',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                },
                                                                {
                                                                    title: '金额',
                                                                    dataIndex: 'remCIAmount',
                                                                    key: 'remCIAmount',
                                                                    align: 'center',
                                                                    render: (data, rowData) => {
                                                                        return data ? data : 0
                                                                    }
                                                                }
                                                            ]
                                                        }
                                                    ]
                                                }
                                            },
                                        ]}
                                    /> : null}
                                </div>
                                <div>
                                    {<QnnTable
                                        //通过清单id去查询工序
                                        {...this.props}
                                        fetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                        antd={{
                                            rowKey: 'zxBuYgjResTechnicsId',
                                            size: 'small',
                                            scroll: {
                                                y: document.documentElement.clientHeight * 0.2
                                            }
                                        }}
                                        wrappedComponentRef={(me) => {
                                            this.tableGX = me;
                                        }}
                                        {...configBottom}
                                        fetchConfig={{
                                            apiName: 'getZxBuYgjResTechnicsAndQDList',
                                            otherParams: {
                                                billID: this.state.QDid,
                                                orgID: orgID,
                                                budgetBookID: this.state.zxBuBudgetBookId
                                            }
                                        }}
                                        method={{
                                            saveIsOkFunc: (obj) => {
                                                if (status === '1') {
                                                    return true
                                                } else {
                                                    if (!this.tableQD) {
                                                        return true
                                                    } else {
                                                        if (this.tableQD.getSelectedRowsKey().length === 0) {
                                                            return true
                                                        } else {

                                                        }
                                                    }
                                                }
                                            }
                                        }}
                                        unilineEdit={()=>{
                                            return  status !== '1'
                                        }}
                                        formConfig={[
                                            {
                                                isInTable: false,
                                                form: {
                                                    field: 'zxBuYgjResTechnicsId',
                                                    type: 'string',
                                                    hide: true,
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '序号',
                                                    align: 'center',
                                                    // dataIndex: 'order',
                                                    key: 'order',
                                                    render: (val, rowData, index) => {
                                                        return <span>{index + 1}</span>
                                                    }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '清单关联名称',
                                                    dataIndex: 'workName',
                                                    key: 'workName',
                                                    align: 'center'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '工序编号',
                                                    dataIndex: 'techNon',
                                                    key: 'techNon',
                                                    align: 'center',
                                                    fieldConfig: {
                                                        type: "string",
                                                        field: 'techNon',
                                                    },
                                                    tdEdit: (obj, val, row) => {
                                                        return row.rowData.standardTechID === undefined
                                                    }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '工序名称',
                                                    dataIndex: 'name',
                                                    key: 'name',
                                                    tdEdit: () => {
                                                        return status !== '1'
                                                    },
                                                    fieldConfig: {
                                                        type: "string",
                                                        field: 'name',
                                                    },
                                                    align: 'center'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '工序单位',
                                                    dataIndex: 'unit',
                                                    key: 'unit',
                                                    tdEdit: () => {
                                                        return status !== '1'
                                                    },
                                                    fieldConfig: {
                                                        type: "string",
                                                        field: 'unit',
                                                    },
                                                    align: 'center'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: "已完工程",
                                                    children: [
                                                        {
                                                            title: '计算公式',
                                                            dataIndex: 'formulaStr',
                                                            key: 'formulaStr',
                                                            tdEdit: () => {
                                                                return status !== '1'
                                                            },
                                                            fieldConfig: {
                                                                type: "string",
                                                                field: 'formulaStr',
                                                                onBlur: async (e, obj) => {
                                                                    const value = e.target.value
                                                                    obj.qnnTableInstance.setEditedRowData({
                                                                        finQty: this.calculator(value)
                                                                    })
                                                                }
                                                            },
                                                            align: 'center'
                                                        },
                                                        {
                                                            title: '数量',
                                                            dataIndex: 'finQty',
                                                            key: 'finQty',
                                                            align: 'center'
                                                        },
                                                        {
                                                            title: '不含税单价',
                                                            dataIndex: 'finPrice',
                                                            key: 'finPrice',
                                                            tdEdit: () => {
                                                                return status !== '1'
                                                            },
                                                            fieldConfig: {
                                                                type: "string",
                                                                field: 'finPrice',
                                                                onBlur: async (e, obj) => {
                                                                    const val = e.target.value
                                                                    const rowData = await obj.qnnTableInstance.getEditedRowData()
                                                                    await obj.qnnTableInstance.setEditedRowData({
                                                                        ...rowData,
                                                                        finPrice: +val,
                                                                        finTaxPrice: +val * rowData.taxRate / 100
                                                                    })
                                                                }
                                                            },
                                                            align: 'center'
                                                        },
                                                        {
                                                            title: '税率',
                                                            dataIndex: 'finTaxRate',
                                                            key: 'finTaxRate',
                                                            align: 'center',
                                                            tdEdit: () => {
                                                                return status !== '1'
                                                            },
                                                            fieldConfig: {
                                                                type: "select",
                                                                field: 'finTaxRate',
                                                                optionConfig: {
                                                                    label: 'itemName', //默认 label
                                                                    value: 'itemId'
                                                                },
                                                                fetchConfig: {
                                                                    apiName: 'getBaseCodeSelect',
                                                                    otherParams: {
                                                                        itemId: 'shuiLv'
                                                                    }
                                                                },
                                                                onChange: async (val, obj) => {
                                                                    const rowData = await obj.qnnTableInstance.getEditedRowData()
                                                                    await obj.qnnTableInstance.setEditedRowData({
                                                                        ...rowData,
                                                                        finTaxPrice: rowData.finPrice * +val / 100
                                                                    })
                                                                }
                                                            },
                                                        },
                                                        {
                                                            title: '税金单价',
                                                            dataIndex: 'finTaxPrice',
                                                            key: 'finTaxPrice',
                                                            align: 'center'
                                                        },
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: "剩余工程",
                                                    children: [
                                                        {
                                                            title: '计算公式',
                                                            dataIndex: 'formulaStr2',
                                                            key: 'formulaStr2',
                                                            tdEdit: () => {
                                                                return status !== '1'
                                                            },
                                                            fieldConfig: {
                                                                type: "string",
                                                                field: 'formulaStr2',
                                                                onBlur: async (e, obj) => {
                                                                    const value = e.target.value
                                                                    obj.qnnTableInstance.setEditedRowData({
                                                                        remQty: this.calculator(value)
                                                                    })
                                                                }
                                                            },
                                                            align: 'center'
                                                        },
                                                        {
                                                            title: '数量',
                                                            dataIndex: 'remQty',
                                                            key: 'remQty',
                                                            align: 'center'
                                                        },
                                                        {
                                                            title: '不含税单价',
                                                            dataIndex: 'remPrice',
                                                            key: 'remPrice',
                                                            tdEdit: () => {
                                                                return status !== '1'
                                                            },
                                                            fieldConfig: {
                                                                type: "string",
                                                                field: 'remPrice',
                                                                onBlur: async (e, obj) => {
                                                                    const val = e.target.value
                                                                    const rowData = await obj.qnnTableInstance.getEditedRowData()
                                                                    await obj.qnnTableInstance.setEditedRowData({
                                                                        ...rowData,
                                                                        remPrice: +val,
                                                                        remTaxPrice: +val * rowData.remTaxRate / 100
                                                                    })
                                                                }
                                                            },
                                                            align: 'center'
                                                        },
                                                        {
                                                            title: '税率',
                                                            dataIndex: 'remTaxRate',
                                                            key: 'remTaxRate',
                                                            align: 'center',
                                                            tdEdit: () => {
                                                                return status !== '1'
                                                            },
                                                            fieldConfig: {
                                                                type: "select",
                                                                field: 'remTaxRate',
                                                                optionConfig: {
                                                                    label: 'itemName', //默认 label
                                                                    value: 'itemId'
                                                                },
                                                                fetchConfig: {
                                                                    apiName: 'getBaseCodeSelect',
                                                                    otherParams: {
                                                                        itemId: 'shuiLv'
                                                                    }
                                                                },
                                                                onChange: async (val, obj) => {
                                                                    const rowData = await obj.qnnTableInstance.getEditedRowData()
                                                                    await obj.qnnTableInstance.setEditedRowData({
                                                                        ...rowData,
                                                                        remTaxPrice: rowData.remPrice * +val / 100
                                                                    })
                                                                }
                                                            },
                                                        },
                                                        {
                                                            title: '税金单价',
                                                            dataIndex: 'remTaxPrice',
                                                            key: 'remTaxPrice',
                                                            align: 'center'
                                                        },
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '参考区域',
                                                    dataIndex: 'areaName',
                                                    key: 'areaName',
                                                    align: 'center',
                                                    // render: () => {
                                                    //     return <span>华北区</span>
                                                    // }
                                                },
                                                isInForm: false
                                            },
                                            // {
                                            //     table: {
                                            //         title: '标准值',
                                            //         dataIndex: 'technicAmt',
                                            //         key: 'technicAmt',
                                            //         align: 'center'
                                            //     },
                                            //     isInForm: false
                                            // },
                                            // {
                                            //     table: {
                                            //         title: '上限值',
                                            //         dataIndex: 'upAmt',
                                            //         key: 'upAmt',
                                            //         align: 'center'
                                            //     },
                                            //     isInForm: false
                                            // },
                                            // {
                                            //     table: {
                                            //         title: '下限值',
                                            //         dataIndex: 'downAmt',
                                            //         key: 'downAmt',
                                            //         align: 'center'
                                            //     },
                                            //     isInForm: false
                                            // },
                                            // {
                                            //     table: {
                                            //         title: '参考值查询',
                                            //         dataIndex: 'pp1',
                                            //         key: 'pp1',
                                            //         align: 'center'
                                            //     },
                                            //     isInForm: false
                                            // },
                                        ]}
                                        actionBtns={[
                                            // {
                                            //     name: 'diydel',//内置add del
                                            //     icon: 'delete',
                                            //     type: 'danger',//类型  默认 primary  [primary dashed danger]                                
                                            //     label: '删除',
                                            //     disabled: true,
                                            //     // disabled: (obj) => {
                                            //     //     if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                            //     //         return false;
                                            //     //     } else {
                                            //     //         return true;
                                            //     //     }
                                            //     // },
                                            //     onClick: (obj) => {
                                            //         confirm({
                                            //             content: '确定删除此数据吗?',
                                            //             onOk: () => {
                                            //                 this.props.myFetch('batchDeleteUpdateZxBuYgjResTechnics', obj.selectedRows).then(({ success, message, data }) => {
                                            //                     if (success) {
                                            //                         obj.btnCallbackFn.clearSelectedRows();
                                            //                         obj.btnCallbackFn.refresh();
                                            //                     } else {
                                            //                         Msg.error(message);
                                            //                     }
                                            //                 });
                                            //             }
                                            //         });
                                            //     }
                                            // },
                                            {
                                                name: 'diySubmit',
                                                field: 'diySubmit',
                                                type: 'primary',
                                                label: '保存',
                                                disabled: 'bind:saveIsOkFunc',
                                       
                                                onClick: async (obj) => {
                                                    const data = await obj.qnnTableInstance.getTableData()

                                                    this.props.myFetch('updateZxBuYgjResTechnicsListConstruction', {
                                                        ...this.checkRowData,
                                                        zxBuYgjResTechnicsList: data
                                                    }).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                Msg.success(message);
                                                                this.tableQD.refresh();
                                                                // obj.btnCallbackFn.refresh();
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        }
                                                    );
                                                }
                                            },
                                            {
                                                name: 'addRow',
                                                icon: 'plus',
                                                type: 'primary',
                                                label: '新增',
                                                disabled: 'bind:saveIsOkFunc',
                                                field: "addRow",
                                            },
                                            {
                                                name: 'delRow',
                                                icon: 'delete',
                                                type: 'danger',
                                                label: '删除',
                                                field: "del",
                                            }
                                        ]}
                                    />}
                                </div>
                            </div>
                        </div>
                    </Col>
                </Row>



            </div>
        )
    }
}
export default index;