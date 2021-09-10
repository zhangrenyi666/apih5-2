import React, { Component } from "react";
import { Button, Timeline } from 'antd';
import moment from 'moment';
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
const config = {
	antd: {
		rowKey: 'designFlowId',
		size: 'small'
	},
	paginationConfig: {
		position: 'bottom'
	},
	firstRowIsSearch: false,
	isShowRowSelect: true
};
class index extends Component {
	constructor(props) {
		super(props);
		this.state = {
			// flowData: props.flowData ? props.flowData : '',
			height: document.body.clientHeight - 55 - 53,
			dataBottomA: [
				{
					subprojectName: 'hhhh'
				}
			]
		}
	}
	componentDidMount() {

	}
	render() {
		const { height, dataBottomA } = this.state;
		return (
			<div style={{ height: "100%", position: 'relative' }}>
				<div style={{ background: '#fff', height: height, overflow: 'auto' }}>
					<QnnForm
						style={{ height: '120px' }}
						fetch={this.props.myFetch}
						upload={this.props.myUpload}
						headers={{
							token: this.props.loginAndLogoutInfo.loginInfo.token
						}}
						fetchConfig={{
							apiName: 'getZjTzInvXmyyqkMonthlyReportListBasicDataDetail',
						}}
						drawerConfig={{
							width: '600px'
						}}
						wrappedComponentRef={(me) => {
							this.formHasTicket = me;
						}}
						formConfig={[
							{
								type: 'select',
								label: '统计项名称',
								field: 'proNum',
								span: 8,
								formItemLayout: {
									labelCol: {
										xs: { span: 24 },
										sm: { span: 8 }
									},
									wrapperCol: {
										xs: { span: 24 },
										sm: { span: 16 }
									}
								}
							},
							{
								type: 'component',
								label: '',
								disabled: true,
								field: 'add',
								span: 4,
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
								Component: obj => {
									return (
										<div style={{ height: '60px', lineHeight: '56px',marginLeft:'20px' }}><Button type='primary'>新增统计项</Button></div>
									);
								}
							},
							{
								type: 'component',
								label: '',
								disabled: true,
								field: 'del',
								span: 3,
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
								Component: obj => {
									return (
										<div style={{ height: '60px', lineHeight: '56px' }}><Button type='danger' onClick={() => {
											this.setState({
												dataBottomA: []
											}, () => {
												this.setState({
													dataBottomA: [
														{
															subprojectName: 'hhhh'
														},
														{
															subprojectName: '657567'
														}
													]
												})
											})
										}}>删除</Button></div>
									);
								}
							},
							{
								type: 'component',
								label: '',
								disabled: true,
								field: 'fgf',
								Component: obj => {
									return (
										<div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', marginBottom: '10px', marginTop: '10px',paddingLeft: '20px' }}>
											统计项内容（按新增顺序排序，添加进来的筛选项均必填）
										</div>
									);
								}
							}
						]}
					/>
					{dataBottomA ? <QnnTable
						{...this.props}
						fetch={this.props.myFetch}
						upload={this.props.myUpload}
						headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
						wrappedComponentRef={(me) => {
							this.table = me;
						}}
						{...config}
						fetchConfig={{
							apiName: 'getZjTzInvXmyyqkMonthlyReportListBasicDataDetail',
							otherParams: {
								dataBottomA: dataBottomA
							}
						}}
						formConfig={[
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
									title: '序号',
									width: '14%',
									dataIndex: 'no',
									key: 'no',
									align: 'center',
									render: (data, rows, index) => {
										return index + 1
									}
								}
							},
							{
								table: {
									title: '统计项名称',
									width: '86%',
									dataIndex: 'subprojectName',
									key: 'subprojectName'
								}
							},
						]}
						actionBtns={[]}
					/> : null}
				</div>

				<div style={{ position: 'absolute', bottom: 0, background: 'white', textAlign: 'end', width: '100%', padding: '10px 16px', borderTop: '1px solid rgb(232, 232, 232)' }}>
					<Button type="dashed" style={{ marginRight: '8px' }} onClick={() => {
						this.props.btnCallbackFn.closeDrawer();
					}}>返回</Button>

				</div>
			</div>
		);
	}
}
export default index;
