import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Spin, Button } from 'antd';
import QnnForm from "../../modules/qnn-table/qnn-form";
import ReactEcharts from 'echarts-for-react';
import { DownOutlined, LeftOutlined } from '@ant-design/icons';
const configs = {
	antd: {
		rowKey: 'departmentId',
		size: 'small'
	},
	paginationConfig: false,
	isShowRowSelect: false
}
const pieInitialValue = [{ value: 0, name: '租赁' }, { value: 0, name: '协作队伍自带' }, { value: 0, name: '自有' }]
class index extends Component {
	constructor(props) {
		super(props);
		const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
		const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
		this.state = {
			loading: false,
			bsid: '',
			showReactEcharts: true,
			pieOneData: pieInitialValue,
			departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
			ext1: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.ext1,
			isLocked: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? true : false) : false
		}
	}
	getDataOption = (name, data) => {
		var option = {
			title: {
				text: name,
				left: 'center'
			},
			tooltip: {
				trigger: 'item',
				formatter: '{a} <br/>{b} : {c} ({d}%)'
			},
			legend: {
				orient: 'vertical',
				left: 'left',
				data: ['租赁', '协作队伍自带', '自有']
			},
			color: ['rgb(26,170,240)', 'rgb(50,200,240)', 'rgb(160,230,180)'],
			series: [
				{
					name: '数量',
					type: 'pie',
					radius: '55%',
					center: ['50%', '60%'],
					data,
					emphasis: {
						itemStyle: {
							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					}
				}
			]
		};
		return option;
	}

	render() {
		const { loading, showReactEcharts,departmentId, ext1, isLocked } = this.state
		return (
			<Spin spinning={loading}>
				<div>
					<div style={{ height: '40%', marginBottom: 10 }}>
						<div style={{ textAlign: 'right' }}>
							<Button
								type="primary"
								icon={showReactEcharts ? <DownOutlined /> : <LeftOutlined />}
								onClick={() => {
									this.setState({ showReactEcharts: !showReactEcharts })
								}}
								size='small'
							/>
						</div>
						{showReactEcharts ? <ReactEcharts
							option={this.getDataOption('台数', this.state.pieOneData)}
							notMerge={true}
							lazyUpdate={true}
							theme={"theme_name"}
						/> : null}
					</div>
					<div>
						<QnnForm
							wrappedComponentRef={(me) => {
								this.formHasTicket = me;
							}}
							fetch={this.props.myFetch}
							formItemLayout={{
								labelCol: {
									xs: { span: 6 },
									sm: { span: 6 }
								},
								wrapperCol: {
									xs: { span: 18 },
									sm: { span: 18 }
								}
							}}
							formConfig={[
								{
									type: 'select',
									label: '设备分类',
									field: 'resCatalogID',
									span: 8,
									optionConfig: {
										label: 'catName',
										value: 'id',
									},
									fetchConfig: {
										apiName: "getZxEqResCategoryList",
										otherParams: {
											parentID: '0003'
										}
									},
								},
								// {
								// 	type: 'rangeDate',
								// 	label: '时间段',
								// 	field: 'Date',
								// 	span: 8,
								// },
								{
									type: 'component',
									field: 'aa',
									Component: obj => {
										return (
											<div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
												this.table.refresh();
											}}>查询</Button></div>
										);
									},
									span: 2
								}
							]}
						/>
						<QnnTable
							{...this.props}
							fetch={this.props.myFetch}
							upload={this.props.myUpload}
							headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
							wrappedComponentRef={(me) => {
								this.table = me;
							}}
							fetchConfig={{
								apiName: 'getZxEqEquipListForEquipSource',
								otherParams: () => {
									const selectData = this.formHasTicket?.form?.getFieldsValue()
									return {
										departmentId: isLocked ? departmentId : ext1 === '3' || ext1 === '4' ? departmentId : null,
										departmentParentId2: isLocked ? null : ext1 === '2' ? departmentId : null,
										departmentPath: isLocked ? null : ext1 === '2' ? null : '9999999999',
										resCatalogID: selectData?.resCatalogID,
										numberFlag: isLocked ? 'sysProject' : ext1 === '2' || ext1 === '1' ? 'sysDepartment' : 'sysProject',
									}
								},
								success: ({ data, success }) => {
									if (success && data.length > 0) {
										let pieOneData = [{ value: data[0].zlCount, name: '租赁' }, { value: data[0].xzCount, name: '协作队伍自带' }, { value: data[0].zyCount, name: '自有' }]
										this.setState({ pieOneData })
									}
								}
							}}
							{...configs}
							formConfig={[
								{
									table: {
										title: '机构',
										dataIndex: 'departmentName',
										key: 'departmentName',
										width: 360,
										fixed: 'left',
										onClick: ({
											rowData,
											qnnTableInstance: {
												getVTableData,
												setTableData,
												getExpandedRowsKey, expandNode,
												tool: { message }
											}
										}) => {
											let expandedRowsKey = getExpandedRowsKey();
											let parentID = rowData.departmentId;
											let tableData = getVTableData()
											let pieOneData = [{ value: rowData.zlCount, name: '租赁' }, { value: rowData.xzCount, name: '协作队伍自带' }, { value: rowData.zyCount, name: '自有' }]
											this.setState({ pieOneData })
											// if (rowData.departmentParentId !== '9999999999' && rowData.departmentPath !== '9999999999') return
											if (expandedRowsKey.includes(parentID)) {
												expandNode(parentID, "close");
												return;
											}
											message.loading('loading', 1)
											let selectData = this.formHasTicket?.form?.getFieldsValue()
											this.props.myFetch('getZxEqEquipListForEquipSource', {
												departmentParentId: parentID,
												resCatalogID: selectData?.resCatalogID,
												numberFlag: rowData.departmentPath === '9999999999' ? 'sysDepartment' : 'sysProject'
											}).then((res) => {
												message.destroy()
												if (res.success) {
													var childrenData = res.data;
													if (!childrenData.length) {
														Msg.warn("该节点没有子集数据")
														return;
													}
													var loopFn = function (data) {
														for (var i = 0; i < data.length; i++) {
															if (data[i].departmentId === parentID) {
																data[i].children = childrenData;
															} else if (data[i].children) {
																data[i].children = loopFn(data[i].children)
															}
														}
														return data;
													}
													tableData = loopFn([...tableData]);
													setTableData([...tableData]);
													expandNode(parentID, 'expand');
												} else {
													Msg.error(res.message)
												}
											})
										}
									},
								},
								{
									table: {
										title: '租赁',
										dataIndex: 'title_1',
										key: 'title_1',
										children: [
											{
												title: '数量',
												dataIndex: 'zlCount',
												key: 'zlCount',
												width: 100,
											},
											{
												title: '金额',
												dataIndex: 'zlAmount',
												key: 'zlAmount',
												width: 100,
											},
										]
									},
								},
								{
									table: {
										title: '协作队伍自带',
										dataIndex: 'title_2',
										key: 'title_2',
										children: [
											{
												title: '数量',
												dataIndex: 'xzCount',
												key: 'xzCount',
												width: 100,
											},
											{
												title: '金额',
												dataIndex: 'xzAmount',
												key: 'xzAmount',
												width: 100,
											}
										]
									},
								},
								{
									table: {
										title: '自有',
										dataIndex: 'title_3',
										key: 'title_3',
										children: [
											{
												title: '数量',
												dataIndex: 'zyCount',
												key: 'zyCount',
												width: 100,
											},
											{
												title: '金额',
												dataIndex: 'zyAmount',
												key: 'zyAmount',
												width: 100,
											}
										]
									},
								},
								{
									table: {
										title: '总计',
										dataIndex: 'title_4',
										key: 'title_4',
										children: [
											{
												title: '数量',
												dataIndex: 'totalCount',
												key: 'totalCount',
												width: 100,
											},
											{
												title: '金额',
												dataIndex: 'totalAmount',
												key: 'totalAmount',
												width: 100,
											}
										]
									},
								},
							]}
						/>
					</div>
				</div>
			</Spin>
		);
	}
}

export default index;