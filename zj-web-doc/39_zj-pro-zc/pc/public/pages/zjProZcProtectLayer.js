window.zjProZcProtectLayer = {
    fetchConfig: {
        apiName: "getZjProZcProtectLayerList"
    },
    antd: {
        rowKey: "safeScoreId",
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
                field:'safeScoreId',
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
                width: 180,
                title: '梁板编号',
                dataIndex: 'beamPlateNo'
            }
        },
        {
            isInForm:false,
            table: {
                title: '第一条测线合格率',
                dataIndex: 'rate1'
            }
        },
        {
            isInForm:false,
            table: {
                title: '第二条测线合格率',
                dataIndex: 'rate2'
            }
        },
        {
            isInForm:false,
            table: {
                title: '第三条测线合格率',
                dataIndex: 'rate3'
            }
        },
        {
            isInForm:false,
            table: {
                title: '第四条测线合格率',
                dataIndex: 'rate4'
            }
        },
        {
            isInForm:false,
            table: {
                title: '第五条测线合格率',
                dataIndex: 'rate5'
            }
        },
        {
            isInForm:false,
            table: {
                title: '平均合格率',
                dataIndex: 'averageRate'
            }
        },
       
    ]
}