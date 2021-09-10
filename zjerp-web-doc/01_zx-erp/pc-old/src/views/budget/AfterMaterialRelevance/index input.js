import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Spin, Checkbox, Row, Col, InputNumber } from 'antd';
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
        this.props.myFetch('getZxBuProjectTypeCheckOver', {orgID:departmentId}).then(
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
        $("input[id$='gjLossCoefficient1']").removeAttr("value")
        $("input[id$='gjLossCoefficient1']").removeAttr("aria-valuenow")
        $("input[id$='gjConCoefficient1']").removeAttr("value")
        $("input[id$='gjConCoefficient1']").removeAttr("aria-valuenow")
        $('[name=scenePrice1]').find("span").html("")
        //清除样式
        $(".addBack").css("background-color","")
        $(".addBack").css("color","")
        $(".addBack").removeClass("addBack")
        if (e.target.checked) {
            //解除禁用 checkBox
            $('[name=itemR]').parent().parent().removeClass('ant-checkbox-wrapper-disabled');
            $('[name=itemR]').parent().removeClass('ant-checkbox-disabled');
            $('[name=itemR]').prop("disabled", false);
            //解除禁用 Input
            $("input[id$='gjLossCoefficient1']").parent().parent().removeClass("ant-input-number-disabled")
            $("input[id$='gjLossCoefficient1']").prop("disabled",false)
            $("input[id$='gjConCoefficient1']").parent().parent().removeClass("ant-input-number-disabled")
            $("input[id$='gjConCoefficient1']").prop("disabled",false)
            let idNo = "#" + rowData.id + "No"
            let voidJoin = !$(idNo).attr("name") ? [] : $(idNo).attr("name").split(",")
            let resgjloss = !$(idNo).attr("resgjloss") ? [] : $(idNo).attr("resgjloss").split(",")
            console.log(resgjloss)
            console.log(resgjcon)
            let resgjcon = !$(idNo).attr("resgjcon") ? [] : $(idNo).attr("resgjcon").split(",")
            let price = !$(idNo).attr("price") ? [] : $(idNo).attr("price").split(",")
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
            if (voidJoin.length !== 0) {
                voidJoin.map((item,index) => {
                    // 右表
                    $("#" + item).parent().addClass("ant-checkbox-checked")
                    $('#' + item).prop('checked', true)
                    //选中添加样式
                    $('[data-row-key='+item+']').css("background-color","#DBECE6")
                    $('[data-row-key='+item+']').css("color","red")
                    $('[data-row-key='+item+']').addClass("addBack")
                    // 赋值
                    $('[data-row-key='+item+']').find("input[id$='gjLossCoefficient1']").attr("value",resgjloss[index])
                    $('[data-row-key='+item+']').find("input[id$='gjLossCoefficient1']").attr("aria-valuenow",resgjloss[index])
                    $('[data-row-key='+item+']').find("input[id$='gjConCoefficient1']").attr("value",resgjcon[index])
                    $('[data-row-key='+item+']').find("input[id$='gjConCoefficient1']").attr("aria-valuenow",resgjcon[index])
                    $('[data-row-key='+item+']').children("td:last-child").find("span").html(price[index])
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
            //禁用输入框
            $("input[id$='gjLossCoefficient1']").parent().parent().addClass("ant-input-number-disabled")
            $("input[id$='gjLossCoefficient1']").prop("disabled",true)
            $("input[id$='gjConCoefficient1']").parent().parent().addClass("ant-input-number-disabled")
            $("input[id$='gjConCoefficient1']").prop("disabled",true)
        }
    }
    //差这了 串行赋值,没删干净,
    onChangeRadioALL(rowData, e) {
        const { myFetch } = this.props;
        let qdid = this.inputForm.form.getFieldsValue().qdid
        if (e.target.checked) {
            //右表选中自己
            $('#' + rowData.stockVOID).parent().addClass("ant-checkbox-checked")
            $('#' + rowData.stockVOID).prop('checked', true)
            //选中添加样式
            $('[data-row-key='+rowData.stockVOID+']').css("background-color","#DBECE6")
            $('[data-row-key='+rowData.stockVOID+']').css("color","red")
            $('[data-row-key='+rowData.stockVOID+']').addClass("addBack")
            //损耗系数
            const gjLossCoefficient1 = !rowData.gjLossCoefficient1 ? 1 : rowData.gjLossCoefficient1
            //折算系数
            const gjConCoefficient1 = !rowData.gjConCoefficient1 ? 1 : rowData.gjConCoefficient1
            const price = this.FloatMulTwo(!rowData.scenePrice ? 0 : rowData.scenePrice,this.FloatMulTwo(gjLossCoefficient1,gjConCoefficient1))
            myFetch('relevanceZxBuWorksToStockPrice', {
                //项目id
                orgID: this.inputForm.form.getFieldsValue().orgID,
                //项目清单id
                projWorkID: qdid,
                //材料所在主表id
                stockPriceID: rowData.stockPriceID,
                //材料名称
                resName: rowData.resName,
                //材料编号
                resCode: rowData.resCode,
                //材料单位
                spec: rowData.spec,
                //材料规格型号
                unit: rowData.unit,
                //分类
                businessType: rowData.businessType,
                //损耗系数
                gjLossCoefficient1: rowData.gjLossCoefficient1 == null ? 1 : rowData.gjLossCoefficient1,
                //耗损系数默认1
                gjLossCoefficient2: 1,
                //折算系数
                gjConCoefficient1: rowData.gjConCoefficient1 == null ? 1 : rowData.gjConCoefficient1,
                //折算系数默认1
                gjConCoefficient2: 1,
                //单价 
                scenePrice1: price
            }).then(
                ({ data, success, message }) => {
                    if (success) {
                        //赋值 name id
                        let idNo = "#" + qdid + "No";
                        let idName = "#" + qdid + "Name";
                        //id
                        let voidJoin = !$(idNo).attr("name") ? [] : $(idNo).attr("name").split(",")
                        $(idNo).attr("name", this.addArrOne(rowData.stockVOID, voidJoin))
                        //code
                        let resCodeJoin = !$(idNo).text() ? [] : $(idNo).text().split(",")
                        $(idNo).html(this.addArrOne(rowData.resCode, resCodeJoin))
                        //name
                        let resNameJoin = !$(idName).text() ? [] : $(idName).text().split(",")
                        $(idName).html(this.addArrOne(rowData.resName, resNameJoin))
                        //损耗
                        let resgjlossJoin = !$(idNo).attr("resgjloss") ? [] : $(idNo).attr("resgjloss").split(",")
                        $(idNo).attr("resgjloss", this.addArrOne(1, resgjlossJoin))
                        //折算
                        let resgjconJoin = !$(idNo).attr("resgjcon") ? [] : $(idNo).attr("resgjcon").split(",")
                        $(idNo).attr("resgjcon", this.addArrOne(1, resgjconJoin))
                        //单价
                        let priceJoin = !$(idNo).attr("price") ? [] : $(idNo).attr("price").split(",")
                        $(idNo).attr("resgjcon", this.addArrOne(price, priceJoin))
                        //损耗系数gjLossCoefficient1,折算系数gjConCoefficient1,单价
                        $('[data-row-key='+rowData.stockVOID+']').find("input[id$='gjLossCoefficient1']").attr("value",1)
                        $('[data-row-key='+rowData.stockVOID+']').find("input[id$='gjLossCoefficient1']").attr("aria-valuenow",1)
                        $('[data-row-key='+rowData.stockVOID+']').find("input[id$='gjConCoefficient1']").attr("value",1)
                        $('[data-row-key='+rowData.stockVOID+']').find("input[id$='gjConCoefficient1']").attr("aria-valuenow",1)
                        $('[data-row-key='+rowData.stockVOID+']').children("td:last-child").find("span").html(price)
                    } else {
                    }
                }
            );
        } else {
            $('#' + rowData.stockVOID).parent().removeClass("ant-checkbox-checked")
            $('#' + rowData.stockVOID).prop('checked', false)
            //取消样式
            $('[data-row-key='+rowData.stockVOID+']').css("background-color","")
            $('[data-row-key='+rowData.stockVOID+']').css("color","")
            $('[data-row-key='+rowData.stockVOID+']').removeClass("addBack")
            //这里删
            myFetch('removeRelevanceZxBuWorksToStockPrice', {
                //项目id
                orgID: this.inputForm.form.getFieldsValue().orgID,
                //项目清单id
                projWorkID: qdid,
                //材料所在主表id
                stockPriceID: rowData.stockPriceID, 
                //材料名称
                resName: rowData.resName,     
                //材料编号
                resCode: rowData.resCode,    
                //材料单位
                spec: rowData.spec,        
                //材料规格型号
                unit: rowData.unit,      
                //分类
                businessType: rowData.businessType
            }).then(
                ({ data, success, message }) => {
                    if (success) {
                        let idNo = "#" + qdid + "No";
                        let idName = "#" + qdid + "Name";
                        let voidJoin = !$(idNo).attr("name") ? [] : $(idNo).attr("name").split(",")
                        let index = this.getIndex(rowData.stockVOID,voidJoin)
                        $(idNo).attr("name", this.delArrOne(voidJoin, index))
                        let resCodeJoin = !$(idNo).text() ? [] : $(idNo).text().split(",")
                        $(idNo).html(this.delArrOne(resCodeJoin, index))
                        let resNameJoin = !$(idName).text() ? [] : $(idName).text().split(",")
                        $(idName).html(this.delArrOne(resNameJoin, index))
                        //损耗
                        let resgjlossJoin = !$(idNo).attr("resgjloss") ? [] : $(idNo).attr("resgjloss").split(",")
                        $(idNo).attr("resgjloss", this.delArrOne(resgjlossJoin, index))
                        //折算
                        let resgjconJoin = !$(idNo).attr("resgjcon") ? [] : $(idNo).attr("resgjcon").split(",")
                        $(idNo).attr("resgjcon", this.delArrOne(resgjconJoin, index))
                        //单价
                        let priceJoin = !$(idNo).attr("price") ? [] : $(idNo).attr("price").split(",")
                        $(idNo).attr("resgjcon", this.delArrOne(priceJoin, index))
                        $('[data-row-key='+rowData.stockVOID+']').find("input[id$='gjLossCoefficient1']").attr("value")
                        $('[data-row-key='+rowData.stockVOID+']').find("input[id$='gjLossCoefficient1']").attr("aria-valuenow")
                        $('[data-row-key='+rowData.stockVOID+']').find("input[id$='gjConCoefficient1']").attr("value")
                        $('[data-row-key='+rowData.stockVOID+']').find("input[id$='gjConCoefficient1']").attr("aria-valuenow")
                        $('[data-row-key='+rowData.stockVOID+']').children("td:last-child").find("span").html("")
                    } else {
                    }
                }
            );
        }
    }

    //赋值

    addArrOne(str, arr) {
        arr.push(str);
        return arr.toString();
    }
    delArrOne(arr, index) {
        if(index == null){
            //获取指定元素的索引
            // index = arr.indexOf(str);
        } 
        //使用splice()方法删除指定元素
        arr.splice(index, 1);
        return arr.toString();
    }
    getIndex(str,arr){
        return arr.indexOf(str);
    }
    //乘
    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }
    render() {
        const { optionData, loading } = this.state;
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
                                                this.inputForm.form.setFieldsValue({
                                                    qdid: '',
                                                })
                                                this.tableQD.refresh();
                                                this.tableDateWZ.refresh()
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
                                        apiName: "getZxBuWorksResNoName",
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
                                                render: (data, rowData) => {
                                                    return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>
                                                        <Checkbox id={rowData.id} name='itemL'
                                                            disabled={rowData.contractQty === 0 ? true : false}
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
                                                title: '材料编号',
                                                dataIndex: 'resCodeJoin',
                                                key: 'resCodeJoin',
                                                align: 'center',
                                                render: (data, rowData, index) => {
                                                    return <div id={rowData.id + "No"} 
                                                    name={rowData.voidJoin} 
                                                    resgjloss = {rowData.resGjLossCoefficientJoin}
                                                    resgjcon = {rowData.resGjConCoefficientJoin}
                                                    price = {rowData.priceJoin}
                                                    >{data}</div>;
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '材料名称',
                                                dataIndex: 'resNameJoin',
                                                key: 'resNameJoin',
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
                                {
                                    <QnnForm
                                    
                                    
                                    />

                                }
                                {<QnnTable
                                    {...this.props}
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    wrappedComponentRef={(me) => {
                                        this.tableDateWZ = me;
                                    }}
                                    antd={{
                                        rowKey: function (row) {
                                            return row.stockVOID
                                        },
                                        size: 'small',
                                        scroll: {
                                            y: document.documentElement.clientHeight * 0.6
                                        }
                                    }}
                                    fetchConfig={{
                                        apiName: 'getZxBuStockPriceItemResAll',
                                        otherParams:(obj)=> {
                                            return {
                                                orgID: this.inputForm?.form?.getFieldValue('orgID')
                                            }
                                        },
                                        success: (data) => {
                                            $('[name=itemR]').parent().parent().addClass('ant-checkbox-wrapper-disabled');
                                            $('[name=itemR]').parent().addClass('ant-checkbox-disabled');
                                            $('[name=itemR]').prop("disabled", true);
                                            //禁用输入框
                                            $("input[id$='gjLossCoefficient1']").parent().parent().addClass("ant-input-number-disabled")
                                            $("input[id$='gjLossCoefficient1']").prop("disabled",true)
                                            $("input[id$='gjConCoefficient1']").parent().parent().addClass("ant-input-number-disabled")
                                            $("input[id$='gjConCoefficient1']").prop("disabled",true)
                                        }
                                    }}
                                    actionBtns={[]}
                                    {...configR}
                                    formConfig={[
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'stockVOID',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'zxBuStockPriceItemId',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'stockPriceID',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '材料编号',
                                                dataIndex: 'resCode',
                                                key: 'resCode',
                                                fixed:'left',
                                                width: 100,
                                                render: (data, rowData,index) => {
                                                    return  <div>
                                                                <Checkbox id={rowData.stockVOID} name="itemR"
                                                                    style={{padding: '0px 10px 0px 0px' }}
                                                                    onChange={this.onChangeRadioALL.bind(this, rowData)} />
                                                                {data}
                                                            </div>;
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '材料名称',
                                                dataIndex: 'resName',
                                                key: 'resName',
                                                align: 'center',
                                                fixed:'left',
                                                width: 150,
                                                tooltip:23
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '单位',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                align: 'center',
                                                width: 80,
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '规格型号',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                align: 'center',
                                                width: 100,
                                                tooltip:23
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '损耗系数',
                                                dataIndex: 'gjLossCoefficient1',
                                                key: 'gjLossCoefficient1',
                                                width: 150,
                                                // type: "number",
                                                align:"center",
                                                tdEdit: true,
                                                fieldConfig: {
                                                    type: "number",
                                                },
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '折算系数',
                                                dataIndex: 'gjConCoefficient1',
                                                key: 'gjConCoefficient1',
                                                width: 150,
                                                // type: "number",
                                                align: "center",
                                                tdEdit: true,
                                                fieldConfig: {
                                                    type: "number",
                                                    // onBlur: () => {
                                                    //   this.Itemcompute();
                                                    // }
                                                }
                                                // render:(data)=>{
                                                //     return  <div>
                                                //                 <InputNumber name={'gjConCoefficient1'}></InputNumber>
                                                //             </div>
                                                // }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '材料基价',
                                                dataIndex: 'scenePrice',
                                                key: 'scenePrice',
                                                align: 'center',
                                                width: 100,
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '单价',
                                                dataIndex: 'scenePrice1',
                                                key: 'scenePrice1',
                                                align: 'center',
                                                width: 100,
                                                render:(data)=>{
                                                    return  <div name='scenePrice1'>
                                                                <span>{data}</span>
                                                            </div>
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            isInTable: false,
                                            table: {
                                                title: '分类',
                                                dataIndex: 'businessType',
                                                key: 'businessType',
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