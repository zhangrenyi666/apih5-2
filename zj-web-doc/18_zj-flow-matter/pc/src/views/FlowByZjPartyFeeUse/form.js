import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["","","党费使用申请"],
		apiNameByAdd: "addZjFlowPartyFeeUse",
		apiNameByUpdate: "updateZjFlowPartyFeeUse",
		apiNameByGet: "getZjFlowPartyFeeUseDetail",
		apiTitle: "getZjFlowPartyFeeUseTitle",
		flowId: "zjPartyFeeUse",
		formLink: {
			zjPartyFeeUse: "FlowByzjPartyFeeUse"
		},
		todo: "todoByzjYongYin",
		hasTodo: "hasTodoByzjYongYin"
	},
	formTailLayout: {
        wrapperCol: {
            sm: {
                span: 12,
                offset: 12
            }
        }
	}
};
class index extends Component {
	constructor(props) {
		super(props);
		this.state = {
			isNeedClassifyConfig: false,
			optationDate: []
		}
	}
	componentDidMount() {
		if (this.props.rowData && this.props.rowData.workId) {
			this.props.myFetch("getZjFlowPartyFeeUseDetail", { workId: this.props.rowData.workId }).then(({ success, data, message }) => {
				if (success) {
					this.refreshSAelect(data.feeDetailId1);
                } else {
                }
            })
           
        }
    }
	// 刷新阶段选择下拉
	refreshSAelect = (val) => {
		this.setState({
			optationDate: []
		})
		this.props.myFetch("getZjFlowPartyFeeDetailAllList", { detailId: val }).then(({ success, data, message }) => {
			if (success) {
				for (var i = 0; i < data.length; i++){
					if (val === data[i].detailId) {
						this.setState({
							optationDate: data[i].currentList,
							isNeedClassifyConfig: true
						}, () => {
							
						})
					}
				}
			
			} else {
			}
		})
	}
	render() {
		const {
			isInQnnTable
		} = this.props;
		return (<div style={
			{
				height: isInQnnTable ? "" : "100vh"
			}
		} >

			<Form 
			{...this.props} 
			{...config}
			{...this.props.workFlowConfig}
			{...config.workFlowConfig}
			isNeedClassifyConfig={this.state.isNeedClassifyConfig}
			changeIsNeedClassifyConfig={() => {
				this.setState({
					isNeedClassifyConfig: false
				})
			}}
			formConfig={[ 
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
					label: "",
					field: "feeUseId",
					hide: true
				}, 	
				{
					type: "select",
					label: "公文类型",
					required: true,
					field: "documentType",
					placeholder: "请输入",
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 4 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 20 }
						}
					},
					optionConfig: {
						label: 'text', 
						value: 'value',
					},
					optionData: [
						{
							value: "0",
							text: "请示批复"
						},
						{
							value: "1",
							text: "通知通报"
						},
						{
							value: "2",
							text: "报告函"
						}
					]
				},
				{
					type: "select",
					label: "申请单位",
					required: true,
					field: "applyUnitId",
					placeholder: '请选择',
					fetchConfig: {
						apiName: "getZjFlowPartyFeeUnitList"
					},
					optionConfig: {
						label: 'unitName',
						value: 'feeUnitId'
					},
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 4 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 20 }
						}
					},
				},
				{
					type: "date",
					label: "申请时间",
					field: "applyTime",
					required: true,
					initialValue: moment().format('YYYY-MM-DD'),
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 4 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 20 }
						}
					},
				},
				{
					type: "select",
					label: "党费明细分类",
					required: true,
					field: "feeDetailId1",
					placeholder: "请输入",
					fetchConfig: {
						apiName: "getZjFlowPartyFeeDetailAllList"
					},
					optionConfig: {
						label: 'detailName',
						value: 'detailId'
					},
					onSelect: (val) => {
						this.refreshSAelect(val);
					},
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 4 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 20 }
						}
					},
				},
				{
					type: "select",
					label: "党费明细",
					required: true,
					field: "feeDetailId2",
					placeholder: "请输入",
					optionData: this.state.optationDate,
					optionConfig: {
						label: 'detailName',
						value: 'detailId'
					},
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 4 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 20 }
						}
					},
				},
				{
					type: "phone",
					label: "联系电话",
					required: true,
					field: "phone",
					placeholder: "请输入",
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 4 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 20 }
						}
					},
				},
				
				{
					type: "number",
					label: "申请金额（元）",
					initialValue:0,
					required: true,
					span:12,
					field: "applyMoneySmall",
					placeholder: "请输入",
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 8 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 16 }
						}
					},
				},
				{
					type: "string",
					label: "申请人",
					required: true,
					field: "applyUserName",
					placeholder: "请输入",
					span: 12,
					colStyle: {
						paddingLeft: '10px'
					},
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 8 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 16 }
						}
					},
				},
				{
					type: "textarea",
					label: "申请事由",
					field: "applyReason",
					required: true,
					placeholder: "请输入",
					// span:12,
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 4 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 20 }
						}
					},
				},
				{
					label: '附件',
					field: 'fileList',
					type: 'files',
					fetchConfig: {
						apiName: window.configs.domain + 'upload',
					},
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 4 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 20 }
						}
					},
				},
				{
					type: "textarea",
					label: "支部书记意见",
					field: "opinionField1",
					addShow: false,
					placeholder: "请输入",
					opinionField: true,
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 4 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 20 }
						}
					},
				},
				{
					type: "textarea",
					label: "党委工作部意见",
					field: "opinionField2",
					opinionField: true,
					addShow: false,
					placeholder: "请输入",
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 4 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 20 }
						}
					},
				},
				{
					type: "textarea",
					label: "党委副书记意见",
					field: "opinionField3",
					opinionField: true,
					addShow: false,
					placeholder: "请输入",
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 4 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 20 }
						}
					},
				},
				{
					type: "textarea",
					label: "党委书记意见",
					field: "opinionField4",
					opinionField: true,
					addShow: false,
					placeholder: "请输入",
					formItemLayout: {
						labelCol: {
							xs: { span: 24 },
							sm: { span: 4 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 20 }
						}
					},
				},
			]}
			/>  </div>
		);
	}
}
export default index;