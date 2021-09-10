window.ImportantDocumentsPage={
    fetchConfig: {
        apiName: 'getZjTzImportDocList',
    },
    antd: {
        rowKey: function (row) {
            return row.importDocId
        },
        size:'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {  
        position: 'bottom'
    },

    firstRowIsSearch: false,
    isShowRowSelect: true,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    }
}