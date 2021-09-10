window.StatisticAnalysis = {//流程类型导入
    fetchConfig: {//表格的ajax配置
        apiName: 'getZjTjMedicaStatisticalList',
    },
    antd: { //同步antd table组件配置 ***必传
        rowKey: function (row) {// ***必传
            return row.statisticalId
        },
        size: "small"
    },
    paginationConfig: {// 同步antd的分页组件配置   
        position: 'bottom'
    },
    isShowRowSelect: false,//是否显示选择框  默认true
    actionBtns: [
        {
            label: '导出',
            icon: 'export',//icon
            type: 'primary',
            isValidate: false,//是否验证表单 默认true 
            onClick: function (obj) { 
                const deptName = obj.searchData.deptName;
                const hospitalId = obj.searchData.hospitalId;
                const domain = obj.props.myPublic.domain; 
                var URL = `http://weixin.fheb.cn:91/ureport/excel?_u=file:zjTjMedicaStatisticalList.xml&url=${domain}&deptName=${deptName || ''}&hospitalId=${hospitalId || ''}`;
                window.location.href = URL; 
                 
            }
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
    },
    formConfig: [
        {
            isInTable: false,
            form: {
                field: 'statisticalId',
                type: 'string',
                placeholder: '请输入',
                addShow: false,
                editShow: false,
            }
        },
        {
            isInSearch: true,
            table: {
                title: '部门', //表头标题
                dataIndex: 'deptName', //表格里面的字段
                key: 'deptName',//表格的唯一key  
            },
            form: {
                type: "string",
                placeholder:"请输入"
            }
        },
        {
            table: {
                title: '姓名', //表头标题
                dataIndex: 'createUserName', //表格里面的字段
                key: 'createUserName',//表格的唯一key  
            }
        },
        {
            isInSearch: true,
            table: {
                title: '医院', //表头标题
                dataIndex: 'hospitalName', //表格里面的字段
                key: 'hospitalName',//表格的唯一key  
            },
            form: { 
                field: 'hospitalId', //表格里面的字段
                type: "select",
                placeholder:"请选择",
                fetchConfig:{
                    apiName:"getZjTjMedicalList"
                },
                optionConfig:{
                    label:"medicalName",
                    value:"medicalId"
                }
            }
        },
        {
            table: {
                title: '套餐', //表头标题
                dataIndex: 'medicalName', //表格里面的字段
                key: 'medicalName',//表格的唯一key  
            }
        },
        {
            table: {
                title: '价格（元）', //表头标题
                dataIndex: 'price', //表格里面的字段
                key: 'price',//表格的唯一key  
            }
        }
    ]
}
