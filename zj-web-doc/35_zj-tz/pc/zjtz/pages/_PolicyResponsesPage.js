window.PolicyResponsesPage={
    // fetchConfig: {
    //     apiName: 'getZjTzPolicyCountryReplyList',
    // },
    antd: {
        rowKey: function (row) {
            return row.policyReplyId
        },
        size:'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {  
        position: 'bottom'
    },

    firstRowIsSearch: false,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 20 }
        }
    }
}