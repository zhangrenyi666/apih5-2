import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Spin, Checkbox, Row, Col } from 'antd';
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
            optionData: [],
            qdData: [],
            orgID: ''
        }
    }
    componentDidMount() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        this.setState({
            loading: true,
        })
        this.props.myFetch('getZxBuProjectTypeCheckOver', {orgID: departmentId}).then(
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
    onChangeRadio(rowData, e) {
        //清空所有左边表的数据
        $('[name^=item]').parent().removeClass("ant-checkbox-checked")
        $('[name^=item]').prop('checked', false)
        //清除样式
        $(".addBack").css("background-color","")
        $(".addBack").css("color","")
        $(".addBack").removeClass("addBack")
        if (e.target.checked) {
            //解除禁用
            $('[name=itemR]').parent().parent().removeClass('ant-checkbox-wrapper-disabled');
            $('[name=itemR]').parent().removeClass('ant-checkbox-disabled');
            $('[name=itemR]').prop("disabled", false);
            let idNo = "#" + rowData.id + "No"
            let workIDJoin = !$(idNo).attr("name") ? [] : $(idNo).attr("name").split(",")
            //自己选中
            $('#' + rowData.id).parent().addClass("ant-checkbox-checked")
            $('#' + rowData.id).prop('checked', true)
            //选中添加样式
            $('[data-row-key='+rowData.id+']').css("background-color","#DBECE6")
            $('[data-row-key='+rowData.id+']').css("color","red")
            $('[data-row-key='+rowData.id+']').addClass("addBack")
            //放到form里
            this.inputForm.form.setFieldsValue({
                qdid: rowData.id,
            })
            if (workIDJoin.length !== 0) {
                workIDJoin.map((item) => {
                    //右表
                    $("#" + item).parent().addClass("ant-checkbox-checked")
                    $('#' + item).prop('checked', true)
                    //选中添加样式
                    $('[data-row-key='+item+']').css("background-color","#DBECE6")
                    $('[data-row-key='+item+']').css("color","red")
                    $('[data-row-key='+item+']').addClass("addBack")
                    return item;
                })
            }
        } else {
            this.inputForm.form.setFieldsValue({
                qdid: '',
            })
            //禁用
            $('[name=itemR]').parent().parent().addClass('ant-checkbox-wrapper-disabled');
            $('[name=itemR]').parent().addClass('ant-checkbox-disabled');
            $('[name=itemR]').prop("disabled", true);
        }
    }
    onChangeRadioALL(rowData, e) {
        const { myFetch } = this.props;
        let qdid = this.inputForm.form.getFieldsValue().qdid
        if (e.target.checked) {
            //右表选中自己
            $('#' + rowData.zxBuWorksId).parent().addClass("ant-checkbox-checked")
            $('#' + rowData.zxBuWorksId).prop('checked', true)
            //选中添加样式
            $('[data-row-key='+rowData.zxBuWorksId+']').css("background-color","#DBECE6")
            $('[data-row-key='+rowData.zxBuWorksId+']').css("color","red")
            $('[data-row-key='+rowData.zxBuWorksId+']').addClass("addBack")
            myFetch('relevanceZxBuYgjResTechnics', {
                //项目id
                orgID: this.inputForm.form.getFieldsValue().orgID,
                //项目清单id
                billID: qdid,
                //标准工序库id
                myBillID: rowData.zxBuWorksId,
            }).then(
                ({ data, success, message }) => {
                    if (success) {
                        //赋值 name id
                        let idNo = "#" + qdid + "No";
                        let idName = "#" + qdid + "Name";
                        let workIDJoin = !$(idNo).attr("name") ? [] : ($(idNo).attr("name")).split(",")
                        $(idNo).attr("name", this.addArrOne(rowData.zxBuWorksId, workIDJoin))
                        let workNoJoin = !$(idNo).text() ? [] : ($(idNo).text()).split(",")
                        $(idNo).html(this.addArrOne(rowData.workNo, workNoJoin))
                        let workNameJoin = !$(idName).text() ? [] : ($(idName).text()).split(",")
                        $(idName).html(this.addArrOne(rowData.workName, workNameJoin))
                    } else {
                    }
                }
            );
        } else {
            $('#' + rowData.zxBuWorksId).parent().removeClass("ant-checkbox-checked")
            $('#' + rowData.zxBuWorksId).prop('checked', false)
            //取消样式
            $('[data-row-key='+rowData.zxBuWorksId+']').css("background-color","")
            $('[data-row-key='+rowData.zxBuWorksId+']').css("color","")
            $('[data-row-key='+rowData.zxBuWorksId+']').removeClass("addBack")
            //这里删
            myFetch('removeRelevanceZxBuYgjResTechnics', {
                //项目id
                orgID: this.inputForm.form.getFieldsValue().orgID,
                //项目清单id
                billID: qdid,
                //标准工序库id
                myBillID: rowData.zxBuWorksId,
            }).then(
                ({ data, success, message }) => {
                    if (success) {
                        let idNo = "#" + qdid + "No";
                        let idName = "#" + qdid + "Name";
                        let workIDJoin = !$(idNo).attr("name") ? [] : $(idNo).attr("name").split(",")
                        $(idNo).attr("name", this.delArrOne(rowData.zxBuWorksId, workIDJoin))
                        let workNoJoin = !$(idNo).text() ? [] : ($(idNo).text()).split(",")
                        $(idNo).html(this.delArrOne(rowData.workNo, workNoJoin))
                        let workNameJoin = !$(idName).text() ? [] : ($(idName).text()).split(",")
                        $(idName).html(this.delArrOne(rowData.workName, workNameJoin))
                    } else {
                    }
                }
            );
        }
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
        const { optionData,  loading  } = this.state;
        return (
            <div className={s.root}>
                <Spin spinning={loading}>
                    <div className={s.head}>
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
                            <Col span={12}>
                                {<QnnTable
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
                                        rowKey: function (row) {
                                            return row.id
                                        },
                                        size: 'small',
                                        rowClassName: (rowData, index) => {
                                            return rowData.isLeaf === 0 ? s.addColor : '';
                                        },
                                        scroll: {
                                            y: document.documentElement.clientHeight * 0.6
                                        }
                                    }}
                                    actionBtns={[]}
                                    {...configL}
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
                                                        <Checkbox id={rowData.id} name='itemL'
                                                            disabled={rowData.contractQty === 0 ? true : false}
                                                            // defaultChecked={rowData.id === this.inputForm.form?.getFieldsValue()?.qdid ? true : false}
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
                                                render: (data, rowData, index) => {
                                                    return <div id={rowData.id + "No"} name={rowData.workIDJoin}>{data}</div>;
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '数据库清单名称',
                                                dataIndex: 'workNameJoin',
                                                key: 'workNameJoin',
                                                align: 'center',
                                                render: (data, rowData, index) => {
                                                    return <div id={rowData.id + "Name"}>{data}</div>;
                                                }
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
                                    antd={{
                                        rowKey: function (row) {
                                            return row.zxBuWorksId
                                        },
                                        size: 'small',
                                        rowClassName: (rowData, index) => {
                                            return rowData.isLeaf === 0 ? s.addColor : '';
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
                                        },
                                        success: (res) => {
                                            $('[name=itemR]').parent().parent().addClass('ant-checkbox-wrapper-disabled');
                                            $('[name=itemR]').parent().addClass('ant-checkbox-disabled');
                                            $('[name=itemR]').prop("disabled", true);
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
                                                        <Checkbox id={rowData.zxBuWorksId} name="itemR"
                                                            // defaultChecked={this.inputForm.form?.getFieldsValue()?.qdNo?.indexOf(rowData.workNo) != -1 && this.inputForm.form?.getFieldsValue()?.qdNo?.indexOf(rowData.workNo) != null ? true : false}
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