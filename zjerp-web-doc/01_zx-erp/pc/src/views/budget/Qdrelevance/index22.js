import React, { Component, version } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Spin, Checkbox, Row, Col, Radio } from 'antd';
import s from "./style.less";
import $ from 'jquery';
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
    pageSize: 9999,
    // isShowRowSelect: false
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
    // isShowRowSelect: false
}
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;

        this.state = {
            loading2: false,
            loading: false,
            optionData: [],
            qdData: [],
            orgID: '',
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId

        }
    }
    componentDidMount() {
        const { departmentId } = this.state
        this.setState({
            loading: true,
        })
        this.props.myFetch('getZxBuProjectTypeCheckOver', { orgID: departmentId }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        optionData: data,
                        // orgID: data.length ? data[0].iecmOrgID : '',
                        loading: false,
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    selectedR = []
    selectedL = []
    onChangeRadio(obj) {
        //放到form里
        if (obj.getSelectedRows().length > 0) {
            this.inputForm.form.setFieldsValue({
                qdid: obj.getSelectedRows()[0].id
            })
            this.selectedR.push(obj.getSelectedRows()[0].id)
            //取出右表的id值
            let workIDJoin = !obj.getSelectedRows()[0].workIDJoin ? [] : obj.getSelectedRows()[0].workIDJoin.split(",")
            let idArr = []
            //给id值加上键zxBuWorksId
            workIDJoin.forEach(element => {
                let workId = {};
                workId.zxBuWorksId = element
                idArr.push(workId)
            });
            //右表选中
            this.tableDateQD.setSelectedRows(idArr)
        } else {
            this.inputForm.form.setFieldsValue({
                qdid: ''
            })
            this.tableDateQD.clearSelectedRows()
        }
    }
    onChangeRadioALL(obj, arr, rowData) {
        console.log(arr)
        const { myFetch } = this.props;
        let qdid = this.inputForm.form.getFieldsValue().qdid
        if (!qdid) {
            Msg.error("没有选择项目叶子清单!")
            obj.clearSelectedRows();
        } else {
            //取原来的数量
            let radioData = this.tableQD.getSelectedRows()[0];
            let workIDJoin = !radioData.workIDJoin ? [] : radioData.workIDJoin.split(",")
            if (obj.getSelectedRows().length > workIDJoin.length) {
                // this.selectedL.push()
                this.setState({
                    loading2: true,
                }, () => {
                    console.log(this.inputForm.form.getFieldsValue().orgID, qdid, arr[arr.length - 1])
                    myFetch('relevanceZxBuYgjResTechnics', {
                        //项目id
                        orgID: this.inputForm.form.getFieldsValue().orgID,
                        //项目清单id
                        billID: qdid,
                        //标准工序库id
                        myBillID: arr[arr.length - 1]
                    }).then(
                        ({ data, success, message }) => {
                            if (success) {
                                rowData = rowData[rowData.length - 1]
                                let workIDJoin = !radioData.workIDJoin ? [] : radioData.workIDJoin.split(",")
                                radioData.workIDJoin = this.addArrOne(rowData.zxBuWorksId, workIDJoin)
                                let workNoJoin = !radioData.workNoJoin ? [] : radioData.workNoJoin.split(",")
                                radioData.workNoJoin = this.addArrOne(rowData.workNo, workNoJoin)
                                let workNameJoin = !radioData.workNameJoin ? [] : radioData.workNameJoin.split(",")
                                radioData.workNameJoin = this.addArrOne(rowData.workName, workNameJoin)
                                let arr = []
                                arr.push(radioData)
                                this.tableQD.setSelectedRows(arr)

                                this.tableQD.setTableData(data)
                            } else {
                            }
                            this.setState({
                                loading2: false
                            })
                        }
                    );
                })
            } else {
                this.differenceSet()
                this.setState({
                    loading2: true,
                }, () => {
                    myFetch('removeRelevanceZxBuYgjResTechnics', {
                        //项目id
                        orgID: this.inputForm.form.getFieldsValue().orgID,
                        //项目清单id
                        billID: qdid,
                        //标准工序库id
                        myBillID: this.differenceSet(workIDJoin, arr),
                    }).then(
                        ({ data, success, message }) => {
                            if (success) {
                                radioData.workIDJoin = data.workIDJoin
                                radioData.workNameJoin = data.workNameJoin
                                radioData.workNoJoin = data.workNoJoin
                                let arr = []
                                arr.push(radioData)
                                this.tableQD.setSelectedRows(arr)
                                this.tableQD.setTableData(this.tableQD.getTableData())
                            } else {
                            }
                            this.setState({
                                loading2: false
                            })
                        }
                    );
                })
            }
        }
    }
    //取唯一取消的选择id
    differenceSet(arr1, arr2) {
        var a = new Set(arr1); //多
        var b = new Set(arr2); //少
        var difference = new Set([...a].filter(x => !b.has(x)));
        let id;
        difference.forEach(item => {
            id = item
        })
        return id;
    }
    addArrOne(str, arr) {
        arr.push(str);
        return arr.toString();
    }
    delArrOne(str, arr) {
        //获取指定元素的索引
        var index = arr.indexOf(str);
        //使用splice()方法删除指定元素
        arr.splice(index, 1);
        return arr.toString();
    }
    render() {
        const { optionData, loading, loading2 } = this.state;
        return (
            <div >
                <div >
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
                                            this.tableQD.refresh();
                                        }
                                    },
                                    {
                                        label: 'qdid',
                                        field: 'qdid',
                                        type: 'string',
                                        hide: true,
                                    }
                                ]}
                            />
                        </Col>
                    </Row>
                </div>
                <div>
                    <Row>
                        <Col span={11}>
                            <Spin spinning={loading2}>
                                <QnnTable
                                    {...this.props}
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    wrappedComponentRef={(me) => {
                                        this.tableQD = me;
                                    }}
                                    fetchConfig={{
                                        apiName: "getZxBuWorksNoName",
                                        otherParams: () => {
                                            return {
                                                orgID: this.inputForm?.form?.getFieldValue('orgID'),
                                                parentID: '-1'
                                            }
                                        }
                                    }}
                                    antd={{
                                        rowKey: 'id',
                                        size: 'small',
                                        scroll: {
                                            y: document.documentElement.clientHeight * 0.6
                                        }
                                    }}
                                    getRowSelection={(dataArr) => {
                                        return {
                                            type: 'radio',
                                            onChange: this.onChangeRadio.bind(this, dataArr),
                                            getCheckboxProps: record => ({
                                                // name:record.name,
                                                disabled: record.isLeaf === 0, // Column configuration not to be checked
                                            }),
                                        }
                                    }}
                                    rowClassName={(record, index) => {
                                        return record.isLeaf === 0 ? s.backgrounde6 : ''
                                    }}
                                    actionBtns={[]}
                                    {...configL}
                                    // rowClassName={ (record, index) => {
                                    //     return record.no === '' ? s.addColor : ''
                                    // }}
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
                                                // align: 'left',
                                                render: (data, rowData) => {
                                                    return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>
                                                        {/* <Checkbox id={rowData.id} name='itemL'
                                                            disabled={rowData.contractQty === 0 ? true : false}
                                                            // defaultChecked={rowData.id === this.inputForm.form?.getFieldsValue()?.qdid ? true : false}
                                                            style={{ visibility: rowData.isLeaf === 0 ? 'hidden' : '', padding: '0px 10px 0px 0px' }}
                                                            onChange={this.onChangeRadio.bind(this, rowData)}
                                                        /> */}
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
                                            isInTable: false,
                                            table: {
                                                title: '关联id',
                                                dataIndex: 'workIDJoin',
                                                key: 'workIDJoin'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '数据库清单编号',
                                                dataIndex: 'workNoJoin',
                                                key: 'workNoJoin',
                                                align: 'center',
                                                textOverflow: "lineFeed",
                                                // render: (val, obj, rowData) => {
                                                //     return val && (val.split(',').length && val.split(',').sort().join(','))
                                                // }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '数据库清单名称',
                                                dataIndex: 'workNameJoin',
                                                key: 'workNameJoin',
                                                align: 'center',
                                                textOverflow: "lineFeed",
                                            },
                                            isInForm: false
                                        }
                                    ]}
                                />
                            </Spin>
                        </Col>
                        <Col span={1}></Col>
                        <Col span={12} style={{ paddingLeft: '10px' }}>
                            <Spin spinning={loading2}>
                                <QnnTable
                                    {...this.props}
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    wrappedComponentRef={(me) => {
                                        this.tableDateQD = me;
                                    }}
                                    antd={{
                                        rowKey: 'zxBuWorksId',
                                        size: 'small',
                                        scroll: {
                                            y: document.documentElement.clientHeight * 0.6
                                        }
                                    }}
                                    rowClassName={(record, index) => {
                                        return record.isLeaf === 0 ? s.backgrounde6 : ''
                                    }}
                                    fetchConfig={{
                                        apiName: 'getZxBuWorksTreeListAll',
                                        otherParams: {
                                            orgID: "0",
                                            // limit:10,
                                            // page:1
                                        },
                                    }}
                                    getRowSelection={(dataArr) => {
                                        return {
                                            hideSelectAll: true,
                                            onChange: this.onChangeRadioALL.bind(this, dataArr),
                                            getCheckboxProps: record => ({
                                                // name:record.name,
                                                disabled: record.isLeaf === 0, // Column configuration not to be checked
                                            }),
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
                                                render: (data, rowData) => {
                                                    return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>
                                                        {/* <Checkbox id={rowData.zxBuWorksId} name="itemR"
                                                            // defaultChecked={this.inputForm.form?.getFieldsValue()?.qdNo?.indexOf(rowData.workNo) != -1 && this.inputForm.form?.getFieldsValue()?.qdNo?.indexOf(rowData.workNo) != null ? true : false}
                                                            style={{ visibility: rowData.isLeaf === 0 ? 'hidden' : '', padding: '0px 10px 0px 0px' }}
                                                            onChange={this.onChangeRadioALL.bind(this, rowData)} /> */}
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
                                />
                            </Spin>
                        </Col>
                    </Row>
                </div>

            </div>
        );
    }
}

export default index;