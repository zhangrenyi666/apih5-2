import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import moment from 'moment';
const config = {
	fetchConfig: {
		apiName: 'getZjFlowUnionDuesList'
	},
	antd: {
		rowKey: function (row) {
			return row.workId;
		},
		size: 'small',
		summary: pageData => {
			if (pageData.length) {
				let total = 0;
				pageData.forEach(({ suggestAmountSmall }) => {
					if (suggestAmountSmall) {
						total += suggestAmountSmall;
					}
				});
				return (
					<tr>
						<td style={{ textAlign: "center", fontWeight: 'bold' }} colSpan={7}>总金额：{total}</td>
					</tr>
				);
			}else{
				return null;
			}
		}
	},
	drawerConfig: {
		width: "1000px",
		maskClosable: true
	},
	paginationConfig: {
		position: "bottom"
	},
	actionBtns: [
		{
			field: 'export',
			name: 'export',
			type: 'primary',
			label: '导出',
			onClick: (obj) => {
				let { myPublic: { domain, appInfo: { ureport } } } = obj.props;
				window.location.href = `${ureport}pdf?_u=file:zjFlowUnionDuesTotalList.xml&url=${domain}&delFlag=0&_n=工会经费流程申请单-${moment(new Date()).format('YYYY-MM-DD')}`;
			}
		},
	],
	formConfig: [
		{
			isInSearch: true,
			table: {
				title: "姓名",
				dataIndex: "handler",
				key: "handler",
			},
			form: {
				type: 'string',
				placeholder: '请输入'
			}
		},
		{
			isInSearch: true,
			table: {
				title: "单位",
				dataIndex: "appUnit",
				key: "appUnit",
			},
			form: {
				type: 'string',
				placeholder: '请输入'
			}
		},
		{
			isInForm: false,
			table: {
				title: "联系方式",
				dataIndex: "phone",
				key: "phone",
			}
		},
		{
			isInForm: false,
			table: {
				title: "金额(发放金额)",
				dataIndex: "suggestAmountSmall",
				key: "suggestAmountSmall",
			}
		},
		{
			isInForm: false,
			table: {
				title: "申请原因",
				dataIndex: "appReason",
				key: "appReason",
			}
		},
		{
			isInTable: false,
			isInSearch: true,
			form: {
				field: 'createTime',
				label: '申请时间段',
				type: 'rangeDate',
				placeholder: '请选择'
			}
		},
		{
			isInForm: false,
			table: {
				title: "申请时间",
				dataIndex: "createTime",
				key: "createTime",
				format: 'YYYY-MM-DD'
			}
		}
	]
};

class index extends Component {
	render() {
		return (
			<QnnTable
				{...this.props}
				fetch={this.props.myFetch}
				upload={this.props.myUpload}
				headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
				{...config}
			/>
		);
	}
}

export default index;
