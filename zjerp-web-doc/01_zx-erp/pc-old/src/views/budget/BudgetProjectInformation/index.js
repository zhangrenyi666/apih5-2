import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;
const config = {
    fetchConfig: {
        apiName: 'getZxBuProjectTypeList',
    },
    antd: {
        rowKey: 'zxBuProjectTypeId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
const configItem = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: false,
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    //checkbox选择框取消
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: []
        }
    }
    //业主合同信息
    getYZHTXXFun(val){
        const { myFetch } = this.props;
        myFetch('getZxCtContractList', {
            orgID: val,
            contrStatus: '1'
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.table.btnCallbackFn.qnnForm.setValues({
                        //合同中标价(亿元)
                        contractCost: data[0].alterContractSum/10000,
                        //总工期(月)
                        duration: this.getAllDate(data[0].planStartDate,data[0].planEndDate),
                    })
                } else {
                }
            }
        );
    }
    //获取工程类型明细
    getcheckLevelItem(val){
        const { myFetch } = this.props;
        myFetch('getZxBuProjectTypeCheckTreeList', {
            checkType: 2,
            limit: 9999,
            orgID: "0",
            page: 1,
            parentID: val
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        data: data.map((item)=>{
                            item.checkLevel2Name = item.checkName
                            item.checkLevel2ID = item.zxBuProjectTypeCheckId
                            item.checkLevel2ID = item.zxBuProjectTypeCheckId
                            item.zxBuProjectTypeItemId = item.zxBuProjectTypeCheckId
                            item.rate = 1
                            return item
                        })
                    })
                    //折算总合同金额(亿元)
                    let contractCost = this.table.qnnForm.form.getFieldsValue().contractCost
                    let duration = this.table.qnnForm.form.getFieldsValue().duration
                    this.table.btnCallbackFn.qnnForm.setValues({
                        //折算系数
                        rate: 1,
                        //折算总合同金额(亿元)
                        // codeID1:,
                        codeNum1: contractCost*1,
                        // 折算平均年产值(亿元)
                        // codeID2:,
                        codeNum2: contractCost/duration*12
                    })
                    myFetch('getZxBuProjectTypeCheckProjectType', {
                        rate1: contractCost*1,
                        rate2: contractCost/duration*12
                    }).then(
                        ({ data, success, message }) => {
                            if (success) {
                                this.table.btnCallbackFn.qnnForm.setValues({
                                    //赋值项目工程类型
                                    projectTypeName:data.checkName
                                })
                            } else {
                            }
                        }
                    );
                } else {
                }
            }
        );
    }
    //计算结束时间-开始时间 月差
    getAllDate(start, end){
        let startArr = moment(start).format('yyyy-MM-DD').split('-')
        let endArr = moment(end).format('yyyy-MM-DD').split('-')
        let me = (endArr[0]-startArr[0]) * 12 + (endArr[1]-startArr[1]);
        if(endArr[2]>startArr[2]){
            me = me + 1;
        }
        return me;
    }
    //计算 合同金额,平均产值,折算系数,项目工程类型
    //系数参考系数也会改变
    calculation(val){
        //折算系数
        let coe = 1;
        this.state.data.map((item) => {
            if(item.rate !== 0){
                coe = this.FloatMulTwo(coe,item.rate)
            }
            return item;
        })
        //合同金额
        let codeNum1 = this.FloatMulTwo(this.table.qnnForm.form.getFieldsValue().contractCost,coe)
        //平均年产值
        let codeNum2 = this.FloatMulTwo((codeNum1 / this.table.qnnForm.form.getFieldsValue().duration),12);
        const { myFetch } = this.props;
        myFetch('getZxBuProjectTypeCheckProjectType', {
            rate1: codeNum1,
            rate2: codeNum2
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.table.btnCallbackFn.qnnForm.setValues({
                        //赋值项目工程类型
                        projectTypeName:data.checkName,
                        //折算系数
                        rate:coe,
                        //合同金额
                        codeNum1:codeNum1,
                        //平均年产值
                        codeNum2:codeNum2
                    })
                } else {
                }
            }
        );
    }
    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }
    render() {
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.refresh();
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxBuProjectTypeId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgID',
                                key: 'orgID',
                                type: 'select',
                                width: 200,
                                align: 'center',
                                
                            },
                            form: {
                                field: 'orgID',
                                required: true,
                                type: 'select',
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'iecmOrgID'
                                },
                                fetchConfig: {
                                    apiName: 'getZxEqIecmOrgList'
                                },
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                },
                                onChange: (val, rowData) => {
                                    this.setState({
                                        data: []
                                    })
                                    this.table.qnnForm.form.setFieldsValue({
                                        orgName: rowData.itemData.orgName,
                                    })
                                    this.getYZHTXXFun(val);
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'orgName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '所属工程类别',
                                dataIndex: 'engiType',
                                key: 'engiType',
                                type: 'select',
                                align: 'center'
                            },
                            form: {
                                field: 'engiType',
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'suoShuGongChengLeiBie'
                                    }
                                },
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '合同中标价(亿元)',
                                dataIndex: 'contractCost',
                                key: 'contractCost',
                                width:100,
                                align: 'center'
                            },
                            form: {
                                field: 'contractCost',
                                type: 'number',
                                spanForm: 8,
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: 0,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '总工期(月)',
                                dataIndex: 'duration',
                                key: 'duration',
                                width: 100,
                                align: 'center'
                            },
                            form: {
                                field: 'duration',
                                type: 'number',
                                spanForm: 8,
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: 0,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '折算系数',
                                dataIndex: 'rate',
                                key: 'rate',
                                width: 100,
                                align: 'center'
                            },
                            form: {
                                field: 'rate',
                                type: 'number',
                                spanForm: 8,
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: 0,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 9 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '折算总合同金额(亿元)',
                                dataIndex: 'codeNum1',
                                key: 'codeNum1',
                                align: 'center'
                            },
                            form: {
                                field: 'codeNum1',
                                type: 'number',
                                spanForm: 8,
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: 0,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '折算平均年产值(亿元)',
                                dataIndex: 'codeNum2',
                                key: 'codeNum2',
                                align: 'center'
                            },
                            form: {
                                field: 'codeNum2',
                                type: 'number',
                                spanForm: 8,
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: 0,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '编制日期',
                                dataIndex: 'regTime',
                                format: 'YYYY-MM-DD',
                                key: 'regTime',
                                onClick: 'detail',
                                align: 'center',
                                render:(data)=>{
                                    return moment(data).format('YYYY-MM-DD')
                                },
                                willExecute: (obj)=>{
                                    // this.setState({
                                    //     data: []
                                    // })
                                    this.setState({
                                        data: obj.rowData.zxBuProjectTypeItemList
                                    })
                                }

                            },
                            form: {
                                type: 'date',
                                field: 'regTime',
                                initialValue: () => {
                                    return moment(new Date()).format('YYYY-MM-DD')
                                },
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 9 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '项目工程类别',
                                dataIndex: 'projectTypeName',
                                key: 'projectTypeName',
                                align: 'center'
                            },
                            form: {
                                field: 'projectTypeName',
                                type: 'string',
                                addDisabled:true,
                                editDisabled:true,
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks',
                            },
                            form: {
                                type: 'textarea',
                                field: 'remarks',
                            }
                        },
                        {
                            table: {
                                title: '工程类型',
                                dataIndex: 'checkLevel1ID',
                                key: 'checkLevel1ID',
                                type: 'select',
                                align: 'center'
                            },
                            form: {
                                field: 'checkLevel1ID',
                                required: true,
                                type: 'select',
                                allowClear:false,
                                optionConfig: {
                                    label: 'checkName',
                                    value: 'zxBuProjectTypeCheckId'
                                },
                                fetchConfig: {
                                    apiName: 'getZxBuProjectTypeCheckTreeList',
                                    //otherParams里面是定死的参数
                                    //params一般，二连那种
                                    otherParams: {
                                        //写死项目id
                                        checkType: 2,
                                        orgID: '0',
                                        parentID: 'check02'
                                    }
                                },
                                onChange: (val,obj) => {
                                    if(this.table.qnnForm.form.getFieldsValue().orgID==null){
                                        Msg.warn('请先选择项目名称')
                                        setTimeout(() => {
                                            this.table.qnnForm.form.setFieldsValue({
                                                checkLevel1ID:''
                                            })
                                        },200)
                                    }else{
                                        setTimeout(() => {
                                            this.table.qnnForm.form.setFieldsValue({
                                                checkLevel1Name:obj.itemdata.checkName
                                            })
                                        },200)
                                        this.getcheckLevelItem(val);
                                    }
                                }
                            }
                        },
                        {
                            isInTable:false,
                            form:{
                                type: 'string',
                                field: 'checkLevel1Name',
                                hide: true
                            },
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'status',
                                key: 'status',
                                align: 'center',
                                render: (data) => {
                                    if (data) {
                                        return data === '0' ? '未审核' : '已审核'
                                    } else {
                                        return '未审核'
                                    }
                                }
                            },
                            form: {
                                type: 'string',
                                field: 'billStatus',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'zxBuProjectTypeItemList',
                                Component: obj => {
                                    let drawerTitile = obj.Pstate.drawerDetailTitle;
                                    return (
                                        <div
                                            style={{ width: "100%", padding: '10px' }}
                                        >
                                            <QnnTable
                                                history={this.props.history}
                                                match={this.props.match}
                                                fetch={this.props.myFetch}
                                                myFetch={this.props.myFetch}
                                                upload={this.props.myUpload}
                                                headers={{
                                                    token: this.props.loginAndLogoutInfo.loginInfo.token
                                                }}
                                                antd={{
                                                    rowKey: 'zxBuProjectTypeItemId',
                                                    size: 'small'
                                                }}
                                                {...configItem}
                                                wrappedComponentRef={(me) => {
                                                    this.tableBu = me;
                                                }}
                                                data={this.state.data}
                                                formConfig={[
                                                    {
                                                        isInTable: false,
                                                        form: {
                                                            label: '主键id',
                                                            field: 'zxBuProjectTypeItemId',
                                                            hide: true
                                                        }
                                                    },
                                                    {
                                                        isInTable:false,
                                                        form: {
                                                            type: 'string',
                                                            field: 'checkLevel2ID',
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '类别',
                                                            dataIndex: 'checkLevel2Name',
                                                            key: 'checkLevel2Name',
                                                            align: 'center',
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            field: 'checkLevel2Name',
                                                        }
                                                    },
                                                    {
                                                        isInTable: drawerTitile === '详情' ? false : true,
                                                        table: {
                                                            title: '子类别',
                                                            dataIndex: 'checkLevel3ID',
                                                            key: 'checkLevel3ID',
                                                            width: 150,
                                                            type: 'select',
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        form: {
                                                            type: 'select',
                                                            field: 'checkLevel3ID',
                                                            optionConfig: {
                                                                label: 'checkName',
                                                                value: 'zxBuProjectTypeCheckId'
                                                            },
                                                            allowClear:false,
                                                            fetchConfig: (obj)=>{
                                                                return {
                                                                    apiName: 'getZxBuProjectTypeCheckTreeList',
                                                                    otherParams:{
                                                                        checkType: 2,
                                                                        orgID: '0',
                                                                        parentID: obj.rowData.checkLevel2ID
                                                                    }
                                                                }
                                                            },
                                                            onChange: (val,obj) =>{
                                                                let datas = this.state.data.map((item) => {
                                                                    if (item.checkLevel2ID === obj.itemdata.parentID) {
                                                                        //子类别id
                                                                        item.checkLevel3ID = obj.itemdata.zxBuProjectTypeCheckId
                                                                        //子类别名称
                                                                        item.checkLevel3Name = obj.itemdata.checkName
                                                                        //系数
                                                                        item.rate = obj.itemdata.rate1
                                                                        //参考系数
                                                                        item.dispRate = obj.itemdata.dispRate
                                                                        //备注
                                                                        item.remarks = obj.itemdata.remarks
                                                                        //最低系数1
                                                                        item.rate1 = obj.itemdata.rate1
                                                                        //最高系数2
                                                                        item.rate2 = obj.itemdata.rate2
                                                                    }
                                                                    return item;
                                                                });
                                                                this.setState({
                                                                    data: datas
                                                                })
                                                                //并且计算一下
                                                                this.calculation();
                                                            }
                                                        }
                                                    },
                                                    {
                                                        isInTable: drawerTitile === '详情' ? true : false,
                                                        table:{
                                                            title: '子类别',
                                                            dataIndex: 'checkLevel3Name',
                                                            key: 'checkLevel3Name',
                                                            width: 150
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            field: 'checkLevel3Name',
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '系数',
                                                            dataIndex: 'rate',
                                                            key: 'rate',
                                                            tdEdit: drawerTitile=== '详情' ? false : true,
                                                            tdEditCb: (obj)=>{
                                                                let datas
                                                                if(obj.newRowData.checkLevel3ID!=null){
                                                                    let rate = obj.newRowData.rate
                                                                    let rateLow = obj.newRowData.rate1
                                                                    let rateHigh = obj.newRowData.rate2
                                                                    datas = this.state.data.map((item) => {
                                                                        if (item.checkLevel2ID === obj.newRowData.checkLevel2ID) {
                                                                            if(rateLow <= rate && rate <= rateHigh){
                                                                                //系数
                                                                                item.rate = rate
                                                                            }else {
                                                                                Msg.warn('系数须在参考系数内')
                                                                            }
                                                                        }
                                                                        return item;
                                                                    });
                                                                    this.calculation();
                                                                }else{
                                                                    Msg.warn('请先选择子类别')
                                                                }
                                                                this.setState({
                                                                    data: datas == null ? this.state.data : datas
                                                                })
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            field: 'rate',
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '参考系数',
                                                            dataIndex: 'dispRate',
                                                            key: 'dispRate',
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            field: 'dispRate'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '备注',
                                                            dataIndex: 'remarks',
                                                            key: 'remarks',
                                                            tdEdit: drawerTitile=== '详情' ? false : true,
                                                            tdEditCb: (obj)=>{
                                                                let datas
                                                                if(obj.newRowData.checkLevel3ID!=null){
                                                                    datas = this.state.data.map((item) => {
                                                                        if (item.checkLevel2ID === obj.newRowData.checkLevel2ID) {
                                                                            //备注
                                                                            item.remarks = obj.newRowData.remarks
                                                                        }
                                                                        return item;
                                                                    });
                                                                }else{
                                                                    Msg.warn('请先选择子类别')
                                                                }
                                                                this.setState({
                                                                    data: datas == null ? this.state.data : datas
                                                                })
                                                            }
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            field: 'remarks'
                                                        }
                                                    }
                                                ]}
                                                actionBtns={[]}
                                            />
                                        </div>
                                    );
                                },
                            }
                        },
                    ]}
                    actionBtns={[
                        {
                            name: 'add',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '新增',
                            // onclick之前的方法
                            willExecute:(val) =>{
                                this.setState({
                                    data: [],
                                })
                                val.btnCallbackFn.setBtnsDisabled('remove', 'addsubmit');
                                val.btnCallbackFn.setBtnsLoading('remove', 'addsubmit');
                            },
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'diysubmit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    field: 'addsubmit',
                                    onClick: (val) => {
                                        val._formData.zxBuProjectTypeItemList = this.state.data;
                                        const { myFetch } = this.props;
                                        val.btnCallbackFn.setBtnsDisabled('add', 'addsubmit');
                                        val.btnCallbackFn.setBtnsLoading('add', 'addsubmit');
                                        myFetch('addZxBuProjectType', val._formData).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.setState({
                                                        data: []
                                                    })
                                                    this.table.closeDrawer();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            onClick: (obj)=> {
                                if (obj.selectedRows[0].status === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已审核的不能修改!');
                                } else {
                                    this.setState({
                                        data: obj.selectedRows[0].zxBuProjectTypeItemList
                                    })
                                   
                                }
                                this.table.clearSelectedRows();
                            },
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'diySubmit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    // fetchConfig: {//ajax配置
                                    //     apiName: 'updateZxBuProjectType',
                                    // },
                                    onClick: (val) => {
                                        val._formData.zxBuProjectTypeItemList = this.state.data;
                                        const { myFetch } = this.props;
                                        myFetch('updateZxBuProjectType', val._formData).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.setState({
                                                        data: []
                                                    })
                                                    this.table.closeDrawer();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
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
                                        this.props.myFetch('batchDeleteUpdateZxBuProjectType', obj.selectedRows).then(({ success, message, data }) => {
                                            if (success) {
                                                obj.btnCallbackFn.clearSelectedRows();
                                                obj.btnCallbackFn.refresh();
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                });
                            }
                        },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '审核',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].status === '1') {
                                        obj.btnCallbackFn.closeDrawer();
                                        this.table.clearSelectedRows();
                                        Msg.warn('已审核的不能审核!');
                                    } else {
                                        confirm({
                                            content: '确定审核选中的数据吗?',
                                            onOk: () => {
                                                myFetch('checkZxBuProjectType', obj.selectedRows[0]).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        } else {
                                                        }
                                                    }
                                                );
                                            }
                                        });
                                    }
                                } else {
                                    Msg.warn('只能审核一条数据！')
                                }
                            }   
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;