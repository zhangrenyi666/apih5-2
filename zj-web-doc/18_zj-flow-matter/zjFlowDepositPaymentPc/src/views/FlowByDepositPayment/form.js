import { Form } from "../modules/work-flow";
import React,{ Component } from "react";
import moment from "moment";
import { message as Msg } from "antd";
const config = {
    //流程专属配置  --qnn-table中配了这也得配置
    workFlowConfig: {
        //后台定的字段
        title: ["title_sendTime","保证金支付","申请"], //标题字段
        apiNameByAdd: "addZjFlowDepositPayment",
        apiNameByUpdate: "updateZjFlowDepositPayment",
        apiNameByGet: "getZjFlowDepositPaymentDetail",
        flowId: "DepositPayment",
        formLink: {
            DepositPayment: "FlowByDepositPayment",
            jgDepositPayment: "FlowByjgDepositPayment"
        },
        //待办已办切换路由
        todo: "todoByDepositPayment",
        hasTodo: "hasTodoByDepositPayment"
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 6 },
            sm: { span: 6 }
        },
        wrapperCol: {
            xs: { span: 18 },
            sm: { span: 18 }
        }
    },
    formConfig: [
        {
            type: "string",
            label: "workId",
            field: "workId",
            hide: true,
            initialValue: function (obj) {
                return obj.match.params["workId"];
            }
        },
        {
            type: "string",
            label: "主键ID",
            field: "depositPaymentId",
            hide: true
        },
        {
            type: "string",
            label: "申请时间format",
            field: "title_sendTime",
            initialValue: moment().format("YYYY-MM-DD HH:mm:ss"),
            placeholder: "请输入",
            hide: true
        },
        {
            type: "select",
            label: "公文类型",
            initialValue: "0",
            field: "documentType",
            required: true,
            optionConfig: {
                label: "label",
                value: "value"
            },
            optionData: [
                {
                    value: "0",
                    label: "请示批复"
                },
                {
                    value: "1",
                    label: "通知通报"
                },
                {
                    value: "2",
                    label: "报告函"
                }
            ]
        },
        {
            hide: true,
            type: "string",
            label: "审批类别",
            field: "type",
            initialValue: "保证金",
            required: true
        },
        {
            type: "date",
            label: "申请日期",
            field: "appDate",
            initialValue: moment().format("YYYY-MM-DD"),
            required: true
        },
        {
            type: "string",
            label: "工程名称",
            field: "projectName",
            placeholder: "请输入",
            required: true
        },
        {
            type: "string",
            label: "中标单位名称",
            field: "winUnitName",
            placeholder: "请输入",
            required: true
        },
        {
            type: "string",
            label: "业主名称",
            field: "ownerName",
            placeholder: "请输入",
            required: true
        },
        {
            type: "string",
            label: "业主地址",
            field: "ownerAddress",
            placeholder: "请输入"
        },
        {
            type: "money",
            label: "工程造价(元)",
            field: "buildCost",
            placeholder: "请输入",
            precision: 2, //数值精度
            required: true
        },
        {
            type: "date",
            label: "资审日",
            field: "informDate",
            placeholder: "请选择"
        },
        {
            type: "date",
            label: "开标日",
            field: "bidOpenDate",
            placeholder: "请选择",
            required: true
        },
        {
            type: "files",
            label: "附件",
            field: "depositFileList",
            desc: "点击选择上传", //默认 点击或者拖动上传
            fetchConfig: {
                //配置后将会去请求下拉选项数据
                apiName: window.configs.domain + "upload"
            }
        },
        {
            type: "qnnForm",
            field: "depositDetailList",
            label: "保证金",
            //文字配置 默认数据如下 [object]
            textObj: {
                add: "添加保证金",
                del: "删除"
            },
            //是否能新增form表单(true可动态增删) 默认false [bool]
            //注意：开启后表单项值的类型为array  关闭为object
            canAddForm: true,
            //canAddForm===true 的初期値设置格式（提交数据格式&后台返回字段格式 同）
            //canAddForm===false 的初期値设置格式（提交数据格式&后台返回字段格式 同）
            //qnn-form配置  
            qnnFormConfig: {
                formItemLayout: {
                    labelCol: {
                        xs: { span: 6 },
                        sm: { span: 6 }
                    },
                    wrapperCol: {
                        xs: { span: 18 },
                        sm: { span: 18 }
                    }
                },
                formConfig: [
                    {
                        type: "select",
                        label: "保证金名称",
                        placeholder: "请选择",
                        field: "depositTypeId",
                        required: true,
                        optionConfig: {
                            label: "label",
                            value: "value"
                        },
                        optionData: [
                            {
                                value: "0",
                                label: "投标保证金"
                            },
                            {
                                value: "1",
                                label: "履约保证金"
                            },
                            {
                                value: "2",
                                label: "农民工工资保证金"
                            },
                            {
                                value: "3",
                                label: "其他保证金"
                            }
                        ]
                    },
                    {
                        type: "money",
                        label: "金额",
                        field: "depositMoney",
                        precision: 2, //数值精度
                        placeholder: "请输入",
                        required: true,
                        // onChange:"bind:_blocksAddends::表单块字段名::表单块中的字段名::总数字段名",
                        onChange: (v,props) => {
                            const arr = props.funcCallBackParams.form.getFieldValue("apiBody.depositDetailList");
                            const totalMoney = props.funcCallBackParams.form.getFieldValue('apiBody.totalMoney');
                            const sum = arr.reduce((prve,curBlockVal) => prve += curBlockVal.depositMoney,0);
                            if (sum !== totalMoney) {
                                props.fns.setValues({
                                    apiBody: {
                                        totalMoney: sum,
                                        upperTotalMoney: props.qnnformData.qnnFormProps.changeNumMoneyToChinese(sum)
                                    }
                                })
                            }
                        },
                    },
                    {
                        type: "date",
                        label: "开始时间",
                        field: "startTime",
                        placeholder: "请选择",
                        required: true
                    },
                    {
                        type: "date",
                        label: "结束时间",
                        field: "endTime",
                        placeholder: "请选择",
                        required: true
                    }
                ]
            }
        },
        {
            type: "money",
            label: "合计金额",
            field: "totalMoney",
            precision: 2, //数值精度
            placeholder: "请输入",
            qnnDisabled: true,
        },
        {
            type: "string",
            label: "合计金额大写",
            field: "upperTotalMoney",
            placeholder: "请输入",
            qnnDisabled: true,
        },
        {
            type: "textarea",
            label: "申请人意见",
            field: "initialOpinion",
            initialValue: "情况属实",
            qnnDisabled: true,
        },
        {
            type: "textarea",
            label: "单位总会计师",
            field: "opinionField2",
            addShow: false
        },
        {
            type: "textarea",
            label: "单位总经理/执行董事",
            field: "opinionField3",
            addShow: false
        },
        {
            type: "textarea",
            label: "主责事业部经办人",
            field: "opinionField1",
            addShow: false
        },
        {
            type: "textarea",
            label: "主责事业部负责人",
            field: "opinionField4",
            addShow: false
        },
        {
            type: "textarea",
            label: "财务管理部总经理",
            field: "opinionField5",
            addShow: false
        },
        {
            type: "textarea",
            label: "公司分管领导",
            field: "opinionField9",
            addShow: false
        },
        {
            type: "textarea",
            label: "集团公司总会计师",
            field: "opinionField6",
            addShow: false
        },
        {
            type: "textarea",
            label: "集团公司总经理",
            field: "opinionField7",
            addShow: false
        },
        {
            type: "textarea",
            label: "集团公司董事长",
            field: "opinionField8",
            addShow: false
        },
        {
            type: "textarea",
            label: "备注",
            field: "remark",
            qnnDisabled: true,
            initialValue: `1、需附保证金支付文件（招标文件、合同等）\n2、审批权限区间：
           （1）0-200万元（含），主责事业部负责人和财务管理部总经理审批；
           （2）200-1200万元（含），审批至公司分管领导和总会计师；
           （3）1200-3000万元（含），审批至公司总经理；
           （4）3000-5000万元（含），审批至公司董事长；
           （5）大于5000万元（不含），公司党委会研究并报中国交建批准。`
        }
    ]
};
class index extends Component {
    changeNumMoneyToChinese = (money) => {
        var cnNums = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖"); //汉字的数字
        var cnIntRadice = new Array("","拾","佰","仟"); //基本单位
        var cnIntUnits = new Array("","万","亿","兆"); //对应整数部分扩展单位
        var cnDecUnits = new Array("角","分","毫","厘"); //对应小数部分单位
        var cnInteger = "整"; //整数金额时后面跟的字符
        var cnIntLast = "元"; //整型完以后的单位
        var maxNum = 999999999999999.9999; //最大处理的数字
        var IntegerNum; //金额整数部分
        var DecimalNum; //金额小数部分
        var ChineseStr = ""; //输出的中文金额字符串
        var parts; //分离金额后用的数组，预定义    
        var Symbol = "";//正负值标记
        if (money == "") {
            return "";
        }
        money = parseFloat(money);
        if (money >= maxNum) {
            Msg.error('超出最大处理数字');
            return "";
        }
        if (money == 0) {
            ChineseStr = cnNums[0] + cnIntLast + cnInteger;
            return ChineseStr;
        }
        if (money < 0) {
            money = -money;
            Symbol = "负 ";
        }
        money = money.toString(); //转换为字符串
        if (money.indexOf(".") == -1) {
            IntegerNum = money;
            DecimalNum = '';
        } else {
            parts = money.split(".");
            IntegerNum = parts[0];
            DecimalNum = parts[1].substr(0,4);
        }
        if (parseInt(IntegerNum,10) > 0) { //获取整型部分转换
            var zeroCount = 0;
            var IntLen = IntegerNum.length;
            for (var i = 0; i < IntLen; i++) {
                var n = IntegerNum.substr(i,1);
                var p = IntLen - i - 1;
                var q = p / 4;
                var m = p % 4;
                if (n == "0") {
                    zeroCount++;
                }
                else {
                    if (zeroCount > 0) {
                        ChineseStr += cnNums[0];
                    }
                    zeroCount = 0; //归零
                    ChineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
                }
                if (m == 0 && zeroCount < 4) {
                    ChineseStr += cnIntUnits[q];
                }
            }
            ChineseStr += cnIntLast;
            //整型部分处理完毕
        }
        if (DecimalNum != '') { //小数部分
            var decLen = DecimalNum.length;
            for (var i = 0; i < decLen; i++) {
                var n = DecimalNum.substr(i,1);
                if (n != '0') {
                    ChineseStr += cnNums[Number(n)] + cnDecUnits[i];
                }
            }
        }
        if (ChineseStr == '') {
            ChineseStr += cnNums[0] + cnIntLast + cnInteger;
        } else if (DecimalNum == '') {
            ChineseStr += cnInteger;
        }
        ChineseStr = Symbol + ChineseStr;

        return ChineseStr;
    }
    render() {
        return (
            <div>
                <Form
                    {...this.props}
                    {...config}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                    // data={{}}
                    btnsCURD={({ btns,flowData }) => {
                        if (flowData?.nodeVars?.showPrintFlag === '1' && flowData?.flowId === 'DepositPayment') {
                            let { myPublic: { domain,appInfo: { ureport } } } = this.props;
                            var printUrl = `${ureport}excel?_u=file:zjFlowDepositPayment.xml&url=${domain}&workId=${flowData?.workId}`
                            btns.push({ buttonClass: "exprot",buttonFun: null,buttonId: "exprot",buttonName: "导出",icon: null,printUrl: printUrl });
                        }
                        return btns;
                    }}
                    changeNumMoneyToChinese={this.changeNumMoneyToChinese}
                    actionParamsFormat={(body) => {
                        let apiBody = JSON.parse(body.apiBody);
                        body.flowVars.totalMoney = apiBody.totalMoney;
                        body.apiBody = JSON.stringify(apiBody);
                        return body
                    }}
                />
            </div>
        );
    }
}
export default index;
