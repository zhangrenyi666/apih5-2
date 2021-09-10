window.FormTest = {
    fetchConfig: {
        apiName: "getSysDepartmentAllTree",
        params: {
            field2: 'field2'
        }
    },
    formConfig: [ 
        {
            field: "aaa",
            type: "date",
            label: "字段二",
            initialValue: new Date().getTime(),  
        },
        {
            type: "string",
            field: "value",   
            label: "流程字段",
            initialValue: "aaa", 
            span:12,
        },
        {
            type: "string",
            field: "value2",   
            label: "流程字段1",
            initialValue: "aaa",
            span:12,
        },
        {
            type: 'string',
            field: "type",
            label: "type", 
        },
        {
            type: "richtext",
            label: "富文本",
            field: "richtext1", //唯一的字段名 ***必传
            fetchConfig: {
                //必须配置  上传图片地址
                uploadUrl: window.configs.domain + 'upload' //***必传
            },
            initialValue: "<b>这是初始值</b>"
        },
           
    ],

    btns: [
        {
            field: "diy",
            label: "设置值",
            type: 'primary',
            icon: "testIcon",
            onClick: function (obj) {
                obj.btnCallbackFn.setValues({
                    label: "值被更改", 
                    value:null
                })
            }
        },
        {
            field: "diy",
            label: "获取值",
            type: 'primary',
            icon: "testIcon",
            onClick: function (obj) { //此时里面会多一个 response
                obj.btnCallbackFn.getValues(true,function (vals) {
                    console.log(vals)
                })
            }
        },

    ]
};
