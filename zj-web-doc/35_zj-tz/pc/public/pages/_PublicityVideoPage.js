window.PublicityVideoPage={
    fetchConfig: {
        apiName: 'getZjTzVideoList',
    },
    antd: {
        rowKey: function (row) {
            return row.videoId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {  
        position: 'bottom'
    },

    firstRowIsSearch: false,
    isShowRowSelect: true,
    // formItemLayout: {
    //     labelCol: {
    //         xs: { span: 24 },
    //         sm: { span: 2 }
    //     },
    //     wrapperCol: {
    //         xs: { span: 24 },
    //         sm: { span: 22 }
    //     }
    // }
}