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
            activeIndex: -1,
            QDid: '',
            projectName: '',
            QDFlagData: null,
        }
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
                            return true
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
        const { orgID, projectName } = this.state;
        return (
            <div>
                <Row>
                    <Col span={24}>
                        <h2>
                            标后预算编制-工程量清单综合基价费用-{projectName}
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
                                            scroll: {
                                                y: document.documentElement.clientHeight * 0.2
                                            },
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
                                                        push(`${mainModule}AfterBudgetingDetial?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}`)
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
                                                    dataIndex: 'budgetQuantity',
                                                    key: 'budgetQuantity',
                                                    align: 'center',
                                                    render: (data, rowData) => {
                                                        return data ? data : 0
                                                    }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: "不含税",
                                                    children: [
                                                        {
                                                            title: '综合基价',
                                                            dataIndex: 'comPrice',
                                                            key: 'comPrice',
                                                            align: 'center',
                                                            render: (data, rowData) => {
                                                                return data ? data : 0
                                                            }
                                                        },
                                                        {
                                                            title: '金额',
                                                            dataIndex: 'taxFrePrice',
                                                            key: 'taxFrePrice',
                                                            align: 'center',
                                                            render: (data, rowData) => {
                                                                return data ? data : 0
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: "不含税",
                                                    children: [
                                                        {
                                                            title: '工序单价',
                                                            dataIndex: 'techPrice',
                                                            key: 'techPrice',
                                                            align: 'center',
                                                            render: (data, rowData) => {
                                                                return data ? data : 0
                                                            }
                                                        },
                                                        {
                                                            title: '主材单价',
                                                            dataIndex: 'resPrice',
                                                            key: 'resPrice',
                                                            align: 'center',
                                                            render: (data, rowData) => {
                                                                return data ? data : 0
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: "施工进项税",
                                                    children: [
                                                        {
                                                            title: '单价',
                                                            dataIndex: 'conTaxPrice',
                                                            key: 'conTaxPrice',
                                                            align: 'center',
                                                            render: (data, rowData) => {
                                                                return data ? data : 0
                                                            }
                                                        },
                                                        {
                                                            title: '金额',
                                                            dataIndex: 'conTaxAmount',
                                                            key: 'conTaxAmount',
                                                            align: 'center',
                                                            render: (data, rowData) => {
                                                                return data ? data : 0
                                                            }
                                                        }
                                                    ]
                                                }
                                            }
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
                                                if (!this.tableQD) {
                                                    return true
                                                } else {
                                                    if (this.tableQD.getSelectedRowsKey().length === 0) {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }

                                            }
                                        }}
                                        unilineEdit={true}
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
                                                isInTable: false,
                                                form: {
                                                    field: 'standardTechID',
                                                    type: 'string',
                                                    hide: true,
                                                }
                                            },
                                            // standardTechID
                                            {
                                                table: {
                                                    title: '序号',
                                                    align: 'center',
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
                                                    tdEdit: true,
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
                                                    tdEdit: true,
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
                                                    title: '计算公式',
                                                    dataIndex: 'formulaStr',
                                                    key: 'formulaStr',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "string",
                                                        field: 'formulaStr',
                                                        onBlur: async (e, obj) => {
                                                            const value = e.target.value
                                                            obj.qnnTableInstance.setEditedRowData({
                                                                qty: this.calculator(value)
                                                            })
                                                        }
                                                    },
                                                    align: 'center'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '数量',
                                                    dataIndex: 'qty',
                                                    key: 'qty',
                                                    align: 'center'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '不含税单价',
                                                    dataIndex: 'price',
                                                    key: 'price',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "string",
                                                        field: 'price',
                                                        onBlur: async (e, obj) => {
                                                            const val = e.target.value
                                                            const rowData = await obj.qnnTableInstance.getEditedRowData()
                                                            await obj.qnnTableInstance.setEditedRowData({
                                                                ...rowData,
                                                                price: +val,
                                                                taxPrice: +val * rowData.taxRate / 100
                                                            })
                                                        }
                                                    },
                                                    align: 'center'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '税率',
                                                    dataIndex: 'taxRate',
                                                    key: 'taxRate',
                                                    align: 'center',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        field: 'taxRate',
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
                                                                taxPrice: rowData.price * +val / 100
                                                            })
                                                        }
                                                    },
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '税金单价',
                                                    dataIndex: 'taxPrice',
                                                    key: 'taxPrice',
                                                    align: 'center'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '参考区域',
                                                    dataIndex: 'areaName',
                                                    key: 'areaName',
                                                    align: 'center',
                                                    // render: (val) => {
                                                    //     return <span>华北区</span>
                                                    // }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '标准值',
                                                    dataIndex: 'technicAmt',
                                                    key: 'technicAmt',
                                                    align: 'center'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '上限值',
                                                    dataIndex: 'upAmt',
                                                    key: 'upAmt',
                                                    align: 'center'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '下限值',
                                                    dataIndex: 'downAmt',
                                                    key: 'downAmt',
                                                    align: 'center'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '参考值查询',
                                                    dataIndex: 'pp1',
                                                    key: 'pp1',
                                                    align: 'center'
                                                },
                                                isInForm: false
                                            },
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

                                                    this.props.myFetch('updateZxBuYgjResTechnicsList', {
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