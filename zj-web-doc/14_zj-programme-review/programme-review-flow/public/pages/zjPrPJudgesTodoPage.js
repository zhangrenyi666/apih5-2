window.zjPrPJudgesTodoPage = {//评审人统计
	fetchConfig: {//表格的ajax配置
		apiName: 'getZjPrReviewerTodoStatisticsList',
	},
	antd: { //同步antd table组件配置 ***必传
		rowKey: function (row) {// ***必传
			return row.anonymousId
		},
		size:'small'
	},
	drawerConfig: {
		width: '1000px'
	},
	paginationConfig: {// 同步antd的分页组件配置   
		position: 'bottom'
    },
    isShowRowSelect: false,  
	actionBtns: [
        {
            name: 'export',//内置add del
            icon: 'export',//icon
            type: 'primary',//类型  默认 primary  [primary dashed danger]
            label: '导出',
            onClick: (obj) => {
                window.location.href = "http://weixin.fheb.cn:91/ureport/excel?_u=file:评审人待办统计.ureport.xml&url=http://weixin.fheb.cn:98/apifangan/&_n=评审人待办统计";//线上
                // window.location.href = "http://weixin.fheb.cn:91/ureport/excel?_u=file:评审人待办统计.ureport.xml&url=http://test.apih5.com:9091/web/";//本地
            }
        },
	],
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
	formConfig: [
        {
			table: {
				title: '专家名称', //表头标题
				dataIndex: 'userName', //表格里面的字段
                key: 'userName',//表格的唯一key 
                align:'center' 
			},
            isInForm: false,
			isInSearch: true,
			form: {
				type: 'string',
				placeholder: "请输入"
			}			
        },  
        {
			table: {
				title: '待审批方案（个）', //表头标题
				dataIndex: 'totalReview', //表格里面的字段
                key: 'totalReview',//表格的唯一key  
                align:'center'
			},
            isInForm: false,
            isInTable:true,
			isInSearch: false,		
        }
	]
}