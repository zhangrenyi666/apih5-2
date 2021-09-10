window.FormTest = {
    fetchConfig: {
        apiName: "getSysDepartmentAllTree",
        params: {
            field2: 'field2'
        }
    },
    formConfig: [
        {
            type: "string",
            field: "label",
            label: "字段",
        },
        {
            field: "aaa",
            type: "date",
            label: "字段二",
            initialValue: new Date().getTime(),
        },
        {
            type: "string",
            field: "value",   
            label: "流程字段1",
            initialValue: "aaa",
        },
        {
            type: 'string',
            field: "type",
            label: "type", 
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
