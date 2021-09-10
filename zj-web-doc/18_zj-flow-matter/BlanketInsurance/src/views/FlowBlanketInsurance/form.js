import React, {Component} from "react";
import {Form} from "../modules/work-flow";
import { message as Msg, Divider } from 'antd';
let config = {
	workFlowConfig: {
		title: ["", "", "统保情况说明"],
		apiNameByAdd: "addZjFlowBlanketInsurance",
		apiNameByUpdate: "updateZjFlowBlanketInsurance",
		apiNameByGet: "getZjFlowBlanketInsuranceDetails",
		apiTitle: "setZjFlowBlanketInsurance",
		flowId: "tbqkExplain",
		// formLink: {
		// 	znrhApply: "FlowByxmFlowSeal"
		// },
		//
		todo: "todoByZjInHome",
		//已办
		hasTodo: "hasTodoByZjInHome",
	},
	formTailLayout: {
		wrapperCol: {
			sm: {
				// span: 12,
				offset: 12
			}
		}
	},
	//qnn-form配置
	formConfig: [
		{
			type: "string",
			label: "workId",
			field: "workId",
			hide: function () {
				return true
			},
			initialValue: function (obj) {
				return obj.match.params["workId"];
			}
		},
		{
			type: "string",
			label: "",
			field: "kidId",
			hide: function () {
				return true
			},
		},
		{
			type: "string",
			label: "",
			field: "titleFlag",
			hide: function () {
				return true
			},
			initialValue: '3'
		},
			//基本信息
		{
			type: 'component',
			field: 'diy1',
			Component: obj => {
				return (
					<Divider style={{ fontSize: '14px', color: '#545456', height: '38px', lineHeight: '38px', background: '#f0f2f5', textAlign: 'left' }} orientation="left">基本信息</Divider>
				);
			}
		},
		//公司名字
		{
			label: '公司名字',
			type: 'string',
			required: true,
			field: 'companyName',
			placeholder: '请输入...',
			span: 12,
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 6 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		//项目名字
		{
			label: '项目名字',
			type: 'string',
			required: true,
			field: 'proName',
			placeholder: '请输入...',
			span: 12,
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 6 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
			//项目概况
		{
			type: 'component',
			field: 'diy2',
			Component: obj => {
				return (
					<Divider style={{ fontSize: '14px', color: '#545456', height: '38px', lineHeight: '38px', background: '#f0f2f5', textAlign: 'left' }} orientation="left">项目概况</Divider>
				);
			}
		},
		//基本情况
		{
			label: '基本情况',
			type: 'textarea',
			required: true,
			field: 'ext1',
			placeholder: 'XX工程于XX年XX月XX日中标，业主单位为XX单位。项目于XX年XX月XX日签订工程施工合同，合同价为XX亿元，开工日期为XX年XX月XX日，合同工期为XX个月，预计XX年X月X日完工，项目质保期X年，项目保证期X年。若属投资项目，请明确项目公司名称、股东名称与股权比例、总承包单位名称、项目施工参建单位名称、或主建单位情况等情况。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		//工程项目情况
		{
			label: '工程项目情况',
			type: 'textarea',
			required: true,
			field: 'ext2',
			placeholder: '介绍主要的工程项目、工程量、工程造价与桥隧占比情况。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		//人员进场情况
		{
			label: '人员进场情况',
			type: 'textarea',
			required: true,
			field: 'ext3',
			placeholder: '介绍项目部主体员工、劳务派遣员工、临时工、协作单位人员与峰值情况。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		//设备情况
		{
			label: '设备情况',
			type: 'textarea',
			field: 'ext4',
			required: true,
			placeholder: '介绍大型设备来源、类别、数量、预计价值情况。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		//车辆情况
		{
			label: '车辆情况',
			type: 'textarea',
			required: true,
			field: 'ext5',
			placeholder: '介绍车辆来源、类别、数量情况。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
			//相关保险条款
		{
			type: 'component',
			field: 'diy5',
			Component: obj => {
				return (
					<Divider style={{ fontSize: '14px', color: '#545456', height: '38px', lineHeight: '38px', background: '#f0f2f5', textAlign: 'left' }} orientation="left">相关保险条款</Divider>
				);
			}
		},
		//招标文件
		{
			label: '招标文件',
			type: 'textarea',
			required: true,
			field: 'ext6',
			placeholder: '包括采购险种、核心条款及费率要求。',	
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		//PPP合同（若为投资项目）
		{
			label: 'PPP合同(若为投资项目)',
			type: 'textarea',
			required: true,
			field: 'ext7',
			placeholder: '包括采购险种、核心条款及费率要求。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		//施工合同
		{
			label: '施工合同',
			type: 'textarea',
			required: true,
			field: 'ext8',
			placeholder: '包括采购险种、核心条款及费率要求。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		//保费是否包干
		{
			label: '保费是否包干',
			type: 'textarea',
			required: true,
			field: 'ext9',
			placeholder: '是/否，情况说明。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
			//干预集采情况
		{
			type: 'component',
			field: 'diy4',
			Component: obj => {
				return (
					<Divider style={{ fontSize: '14px', color: '#545456', height: '38px', lineHeight: '38px', background: '#f0f2f5', textAlign: 'left' }} orientation="left">干预集采情况</Divider>
				);
			}
		},
		//相关方干预情况及沟通结果
		{
			label: '相关方干预情况及沟通结果',
			type: 'textarea',
			required: true,
			field: 'ext10',
			placeholder: 'XX单位口头/书面推荐保险险种或保险条件、XX经纪公司、XX保险公司的情况说明，有书面推荐的应将书面推荐文件作为附件，并将沟通情况与沟通结果说明。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		//相关方推荐经纪、保险公司履约能力
		{
			label: '相关方推荐经纪、保险公司履约能力',
			type: 'textarea',
			required: true,
			field: 'ext11',
			placeholder: '如遇相关方推荐保险公司、经纪公司等，请项目部对相关方推荐的保险公司、经纪公司的资质、承保能力、信用情况、承保经验、预计履约能力做说明。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		//相关方推荐保险条件
		{
			label: '相关方推荐保险条件',
			type: 'textarea',
			required: true,
			field: 'ext12',
			placeholder: '包括保费费率、保险条件、保险服务。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		//放弃常规险种采购情况分析（若有）
		{
			label: '放弃常规险种采购情况分析(若有)',
			type: 'textarea',
			required: false,
			field: 'ext13',
			placeholder: '常规险种包括工程险、三责险、人意险、等。如放弃常规险种的保险，请说明不投保的原因，对相关的风险程度、风险转移方式进行说明；并明确办理项目开工、工程计量、工程验收、工程结算等业务时，业主、政府是否需要该保险证明。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		//落款
		{
			type: 'component',
			field: 'diy3',
			Component: obj => {
				return (
					<Divider style={{ fontSize: '14px', color: '#545456', height: '38px', lineHeight: '38px', background: '#f0f2f5', textAlign: 'left' }} orientation="left">落款</Divider>
				);
			}
		},
		//制表人
		{
			label: '制表人',
			type: 'string',
			required: true,
			field: 'lister',
			placeholder: '请输入...',
			
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		{
			label: '制表日期',
			type: 'date',
			required: true,
			field: 'makeDate',
			placeholder: '请输入...',
			format: 'YYYY-MM-DD',
			
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		{
			label: '附件',
			field: 'blanketInsurancefileList',
			type: 'files',
			fetchConfig: {
				apiName: window.configs.domain + 'upload',
			},
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		{
			type: "textarea",
			label: "项目负责人意见",
			field: "opinionField1",
			opinionField: true,
			addShow: false,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		{
			type: "textarea",
			label: "公司总会计师意见",
			field: "opinionField3",
			opinionField: true,
			addShow: false,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		{
			type: "textarea",
			label: "公司负责人意见",
			field: "opinionField4",
			opinionField: true,
			addShow: false,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},	
		{
			type: "textarea",
			label: "财务管理部意见",
			field: "opinionField5",
			opinionField: true,
			addShow: false,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},	

		{
			type: "textarea",
			label: "财务管理部负责人意见",
			field: "opinionField6",
			opinionField: true,
			addShow: false,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		{
			type: "textarea",
			label: "集团公司总会计师意见",
			field: "opinionField7",
			opinionField: true,
			addShow: false,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
		{
			type: "textarea",
			label: "说明",
			field: "explain",
			qnnDisabled: true,
			// initialValue: `1、若为投资项目或一公局集团内多个单位参建的项目，由公司成立的项目公司/项目总承包经理部、或主建单位统一提出保险需求\n2、签批处需要领导亲笔手签，左上角盖公章，若非集团公司直属项目，盖各子分公司公章\n3、按照集团公司下发的保险集中采购管理制度，由于特殊原因未能参加统保项目需报送该说明。\n4、说明后需附包括且不限于合同中保险条款页复印件、相关方干预保险的书面文件。`
			initialValue: '1、若为投资项目或一公局集团内多个单位参建的项目，由公司成立的项目公司/项目总承包经理部、或主建单位统一提出保险需求\n2、签批处需要领导亲笔手签，左上角盖公章，若非集团公司直属项目，盖各子分公司公章\n3、按照集团公司下发的保险集中采购管理制度，由于特殊原因未能参加统保项目需报送该说明。\n4、说明后需附包括且不限于合同中保险条款页复印件、相关方干预保险的书面文件。',
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			}
		},
	]
};
class index extends Component {
	render() {
		const {
			isInQnnTable,
			myPublic: { domain,appInfo: { ureport } }
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
				btnsCURD={({ btns, flowData}) => {
					var url = this.props.match.url;
					var myPublic = this.props.myPublic.appInfo.mainModule;
					//已办2.4  已办待办7
					if (url === `${myPublic}hasTodoByZjInHome` && flowData && flowData.flowNode.nodeId === 'Node2' || url === `${myPublic}hasTodoByZjInHome` && flowData && flowData.flowNode.nodeId === 'Node4'|| flowData && flowData.flowNode.nodeId === 'Node7') {
						// var printUrl = `${ureport}excel?_u=file:zjFlowRhHouseholdIn.xml&url=${domain}&workId=${flowData.workId}`
						//从数据中获取Id ,转换成json
						let blanketInsuranceId = JSON.parse(flowData.apiData).blanketInsuranceId;
						let proName = JSON.parse(flowData.apiData).proName;
						btns.push({ buttonClass: "exprot",buttonFun: () => {
							this.props.myFetch('exportZjFlowBlanketInsurance',{blanketInsuranceId:blanketInsuranceId,proName:proName}).then(({ success, data, message }) => {
								if (success) {
									window.location.href = data;
								} else {
									Msg.error(message)
								}
							})
						},buttonId: "exprot",buttonName: "导出",icon: null});
					}
					return btns;
				}}
				fieldsCURD = {(obj, flowData, props) => {
					var url = props.match.url;
					var myPublic = props.myPublic.appInfo.mainModule;
					if(url === `${myPublic}todoByZjInHome` && flowData && flowData.flowNode.nodeId === 'Node4'|| url === `${myPublic}todoByZjInHome` && flowData && flowData.flowNode.nodeId === 'Node7'){
						obj = obj.map((item) => {
							if(item.field === 'blanketInsurancefileList'){
								item.disabled = false;
							}
							return item;
						})
					}
					return obj;
				}}
			/>  </div>
		);
	}
}
export default index;