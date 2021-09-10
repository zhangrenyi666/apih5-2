window.zjPrAnonymousPage = {//珠海大屏项目桥梁进度
	fetchConfig: {//表格的ajax配置
		apiName: 'getZjPrExpertAnonymousList',
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
	// limit: 9999999,   //每页显示条数 默认10
    // curPage: 1, //当前页  默认1、
	actionBtns: [
		{
			name: 'add',//内置add del
			icon: 'plus',//icon
			type: 'primary',//类型  默认 primary  [primary dashed danger]
			label: '新增',
			// Component: MerchantsAdd,
			formBtns: [
				{
					name: 'cancel', //关闭右边抽屉
					type: 'dashed',//类型  默认 primary
					label: '取消',
					hide: function (obj) {
						var index = obj.btnCallbackFn.getActiveKey();
						if (index === "2" || index === "3") {
							return true;
						} else {
							return false;
						}
					},
				},
				{
					name: 'diyJB',//内置add del
					type: 'primary',//类型  默认 primary
					label: '提交',//提交数据并且关闭右边抽屉
					hide: function (obj) {
						var index = obj.btnCallbackFn.getActiveKey();
						if (index === "2" || index === "3") {
							return true;
						} else {
							return false;
						}
					},
					onClick: function (obj) { //此时里面会多一个response
						const { fetch, msg } = obj.btnCallbackFn;
						fetch('addZjPrProgrammeExpertAnonymous', { ...obj._formData }, function ({ data, success, message }) {
							if (success) {
								var anonymousId = {
									anonymousId: data.anonymousId
								};
								obj.props.form.setFieldsValue(anonymousId);
								msg.success(message)
								obj.btnCallbackFn.refresh();
								obj.btnCallbackFn.closeDrawer(false);										
							} else {
								msg.error(message);
							}
						})
					}
				},
			]
		},
		{
			name: 'del',//内置add del
			icon: 'delete',//icon
			type: 'danger',//类型  默认 primary  [primary dashed danger]
			label: '删除',
			fetchConfig: {//ajax配置
				apiName: 'batchDeleteUpdateZjPrProgrammeExpertAnonymous',
			},
		}
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
	}
}