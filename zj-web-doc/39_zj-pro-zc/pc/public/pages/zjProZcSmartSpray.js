window.zjProZcSmartSpray = {
    fetchConfig: {
        apiName: "getZjProZcSmartSprayList"
    },
    antd: {
        rowKey: "smartSprayId",
        size: "small"
    },
    drawerConfig: {
        width:1000
    },
    firstRowIsSearch: false,
    formConfig: [
        {
            isInTable: false,
            form: {
                type: "string",
                field:'smartSprayId',
                hide: true
            }
        },    
        {
            isInForm: false,
            table: {
                width: 80,
                align: 'center',
                title: '序号', //表头标题
                dataIndex: 'no', //表格里面的字段
                key: 'no',//表格的唯一key    
                render: (data, rows, index) => {
                    return index + 1;
                }
            },
        },
        {
            isInForm:false,
            table: {
                title: '台座编号',
                dataIndex: 'pedestalNo'
            }
        },
        {
            isInForm:false,
            table: {
                title: '日期',
                format:'YYYY-MM-DD',
                dataIndex: 'dateDate'
            }
        },
        {
            isInForm:false,
            table: {
                title: '时间',
                // format:'HH:MM',
                dataIndex: 'timeStr'
            }
        },
        {
            isInForm:false,
            table: {
                title: '养护时长(分)',
                dataIndex: 'curingTime'
            }
        }
    ]
}