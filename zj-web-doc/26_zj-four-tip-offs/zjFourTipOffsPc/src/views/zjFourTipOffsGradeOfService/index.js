//典型问题曝光列表
import React, { Component } from "react";
import { basic } from "../modules/layouts";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
const config = {
	fetchConfig: {//表格的ajax配置
		apiName: 'getZjServiceLevelList',

	},
	antd: { //同步antd table组件配置 ***必传
		rowKey: function (row) {// ***必传
			return row.serverLevelId
		}
	},
	drawerConfig: {
		width: '800px'
	},
	limit: 10,   //每页显示条数 默认10
	curPage: 1,
	paginationConfig: {// 同步antd的分页组件配置   
		position: 'bottom'
	},

	isShowRowSelect: true,//是否显示选择框  默认true
	//每个表单项的布局 -- 搜索区域
	formItemLayoutSearch: {
		//默认数据
		labelCol: {
			xs: { span: 24 },
			sm: { span: 6 }
		},
		wrapperCol: {
			xs: { span: 24 },
			sm: { span: 18 }
		}
	},
	actionBtns: [
        {
            name: 'add',//内置add del
            icon: 'plus',//icon
            type: 'primary',//类型  默认 primary  [primary dashed danger]
            label: '新增',
            formBtns: [
                {
                    name: 'cancel', //关闭右边抽屉
                    type: 'dashed',//类型  默认 primary
                    label: '取消',
                },
                {
                    name: 'submit',//内置add del
                    type: 'primary',//类型  默认 primary
                    label: '提交',//提交数据并且关闭右边抽屉 
                    fetchConfig: {//ajax配置
                        apiName: 'addZjServiceLevel',
                    }
                }
            ]
        },
        {
            name: 'del',//内置add del
            icon: 'delete',//icon
            type: 'danger',//类型  默认 primary  [primary dashed danger]
            label: '删除',
            fetchConfig: {//ajax配置
                apiName: 'batchDeleteUpdateZjServiceLevel',
            },
        }
    ],
	formConfig: [
		{
			isInTable: false,
			form: {
				field: 'serverLevelId',
				type: 'string',
				placeholder: '请输入',
				hide:true,
			}
		},
		{
			table: {
				title: '服务等级名称', //表头标题
				dataIndex: 'levelName', //表格里面的字段
				key: 'levelName',//表格的唯一key  
				filter:true,
				onClick: "edit",
				btns:[
					{
						name: 'cancel', //关闭右边抽屉
						type: 'dashed',//类型  默认 primary
						label: '取消',
					},
					{
						name: 'submit',//内置add del
						type: 'primary',//类型  默认 primary
						label: '提交',//提交数据并且关闭右边抽屉 
						fetchConfig: {//ajax配置
							apiName: 'updateZjServiceLevel',
						}
					}
				]
			},
			form: {
				type: 'string',
				required: true,
				placeholder: '请输入',
			}
		},
		{
			table: {
				title: '备注', //表头标题
				dataIndex: 'remarks', //表格里面的字段
				key: 'remarks',//表格的唯一key  
			},
			form: {
				type: 'textarea',
				placeholder: '请输入',
			}
		},
		{
			table: {
				title: '创建者',
				dataIndex: 'createUserName',
				key: 'createUserName',
			},
			isInForm: false
		},
		{
			table: {
				title: '创建时间',
				dataIndex: 'createTime',
				key: 'createTime',
				format: 'YYYY-MM-DD HH:mm:ss',
			},
			isInForm: false
		},
	]
};

class index extends Component {
	render() {
		return (
			<div className={s.root}>
				<QnnTable
					{...this.props}
					fetch={this.props.myFetch} 		 upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					{...config}
				/>
			</div>
		);
	}
}
export default basic(index);