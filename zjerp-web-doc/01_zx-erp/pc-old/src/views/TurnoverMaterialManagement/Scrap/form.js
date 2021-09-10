import React,{
    Component
} from "react";
import {
    Form
} from "../../modules/work-flow";
import $ from 'jquery';
let config = {
    workFlowConfig: {
        title: ["周转材报废","billNo",""],
        // apiNameByAdd: "addZxSkMonthPur",
        apiNameByAdd: "updateZxSkTurnOverScrap",
        apiNameByUpdate: "updateZxSkTurnOverScrap",
        apiNameByGet: "getZxSkTurnOverScrapDetail",
        // apiTitle: "setZjkOaFlowTitle",//???
        flowId: "zxSkTurnOverScrapWorkId",
        todo: "TodoHasTo",
        hasTodo: "TodoHasToq",
	
	},
	parameterName:'orgID',
	formTailLayout: {
        wrapperCol: {
            sm: {
                span: 12,
                offset: 12
            }
        }
    },
};
class index extends Component {
	constructor(props) {
        super(props);
        this.state = {}
    }
	render() {
        const {
            isInQnnTable,flowData
		} = this.props;
		const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
						field: "id",
						initialValue:flowData && flowData.id ? flowData.id:'',
						hide: true
					},
					
					{
						label:'报废单位',
						field: 'orgID',
						type: 'select',
						qnnDisabled: true,
						initialValue:flowData && flowData.orgID ? flowData.orgID:'',
						optionConfig: {
							label: 'orgName',
							value: 'orgID'
						},
						fetchConfig: {
							apiName: 'getZxCtContractList',
							otherParams: {
								contrStatus: "1",
								orgID: departmentId
							}
						},
						span:12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 6 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 18 }
							}
						},
					},
					{
						label: "收购单位名称",
						required: true,
						qnnDisabled: true,
						initialValue:flowData && flowData.acceptOrgName ? flowData.acceptOrgName:'',
						field: 'acceptOrgName',
						type: 'string',
						span:12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 6 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 18 }
							}
						},
					},
					{
						label: "单据编号",
						qnnDisabled: true,
						initialValue:flowData && flowData.billNo ? flowData.billNo:'',
						field: 'billNo',
						type: 'string',
						span:12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 6 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 18 }
							}
						},
					},
					{
						type: "date",
						label: "日期",
						required: true,
						qnnDisabled:true,
						field: "busDate",
						initialValue:flowData && flowData.busDate?flowData.busDate:'',
						placeholder: "请输入",
						span:12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 6 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 18 }
							}
						},
					},
					{
						type: "string",
						label: "经办人",
						qnnDisabled: true,
						initialValue:flowData && flowData.consignee?flowData.consignee:'',
						field: "consignee",
						placeholder: "请输入",
						span:12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 6 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 18 }
							}
						},
					},
					{
						type: "textarea",
						label: "备注",
						qnnDisabled: true,
						initialValue:flowData && flowData.remarks?flowData.remarks:'',
						field: "remarks",
						placeholder: "请输入",
						span:12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 6 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 18 }
							}
						},
					},
					{
						type: "component",
						field: "component5",
						Component: obj => {
							return (
								<div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
									详细信息
								</div>
							);
						}
					},
					{
						"type": "qnnTable",
						"field": "zxSkTurnOverScrapItemList",
						incToForm: true,
						initialValue:flowData && flowData.zxSkTurnOverScrapItemList ? flowData.zxSkTurnOverScrapItemList:'',
						"qnnTableConfig": {
							"antd": {
								"rowKey": "id",
								size: 'small',
								rowClassName: (record, val) => {
									$('.ant-table-footer').css('display','none')
									return '';
								}
							},
							paginationConfig: false,
							isShowRowSelect: false,
							"formConfig": [
								{
									isInTable: false,
									form: {
										label: '主键id',
										field: 'id',
										hide: true
									}
								},
								{
									table: {
										title: '物资编码',
										width: 200,
										// tooltip:80,
										dataIndex: 'resCode',
										key: 'resCode'
									},
									form: {
										type: 'selectByPaging',
										field: 'resCode',
										allowClear:false,
										optionConfig: {
											label: 'joinName',
											value: 'resCode'
										}
									}
								},
								{
									isInTable: false,
									form: {
										field: 'resID',
										hide:true
									}
								},
								{
									table: {
										title: '物资名称',
										// width: 150,
										dataIndex: 'resName',
										key: 'resName',
										// tooltip:10,
									},
									form: {
										type: 'string',
										field: 'resName'
									}
								},
								{
									table: {
										title: '规格型号',
										dataIndex: 'spec',
										key: 'spec',
										// width: 80,
										// tooltip:10,
									},
									form: {
										type: 'string',
										field: 'spec'
									}
								},
								{
									table: {
										title: '计量单位',
										dataIndex: 'resUnit',
										key: 'resUnit',
										// width: 80,
									},
									form: {
										type: 'string',
										field: 'resUnit'
									}
								},
								{
									table: {
										title: '入库日期',
										dataIndex: 'inBusDate',
										key: 'inBusDate',
										// width: 80,
										format: 'YYYY-MM-DD'
									},
									form: {
										type: 'date',
										field: 'inBusDate'
									}
								},
								{
									table: {
										title: '库存数量',
										dataIndex: 'stockQty',
										key: 'stockQty',
										// width: 80,
									},
									form: {
										type: 'number',
										field: 'stockQty'
									}
								},
								{
									table: {
										title: '原值',
										dataIndex: 'originalAmt',
										key: 'originalAmt',
										// width: 80,
									},
									form: {
										type: 'number',
										field: 'originalAmt'
									}
								},
								{
									table: {
										title: '净值',
										dataIndex: 'currentAmt',
										key: 'currentAmt',
										// width: 80,
									},
									form: {
										type: 'number',
										field: 'currentAmt'
									}
								},
								{
									table: {
										title: '处理数量',
										// width:100,
										dataIndex: 'discardQty',
										key: 'discardQty'
									},
									form: {
										type: 'number',
										field: 'discardQty'
									}
								},
								{
									table: {
										title: '处理单价',
										// width:100,
										dataIndex: 'discardPrice',
										key: 'discardPrice'
									},
									form: {
										type: 'number',
										field: 'discardPrice'
									}
								},
								{
									table: {
										title: '处理金额',
										// width:120,
										dataIndex: 'discardAmt',
										key: 'discardAmt'
									},
									form: {
										type: 'number',
										field: 'discardAmt'
									}
								}
							],
							"actionBtns": []
						}
					},
					
					{
						label: '公文附件',
						field: 'fileList',//???
						type: 'files',
						required:true,
						// qnnDisabled: true,
						initialValue:flowData && flowData.fileList ? flowData.fileList:'',
						onPreview:"bind:_docPre",
						fetchConfig: {
							apiName: 'upload',
						},
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 3 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 21 }
							}
						},
					},
					{
						type: "textarea",
						label: "公司主管部门",
						field: "opinionField1",
						opinionField: true,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 3 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 21 }
							}
						},
						addShow: false
					},
					{
						type: "textarea",
						label: "材料主管领导",
						field: "opinionField2",
						opinionField: true,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 3 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 21 }
							}
						},
						addShow: false
					},
					{
						type: "textarea",
						label: "部门会签",
						field: "opinionField3",
						opinionField: true,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 3 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 21 }
							}
						},
						addShow: false
					},
					{
						type: "textarea",
						label: "项目归档",
						field: "opinionField4",
						opinionField: true,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 3 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 21 }
							}
						},
						addShow: false
					}
				]}
			/>
		</div>
        );
    }
}
export default index;