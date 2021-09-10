
window.FormDrag = {

    fieldCanDrag: true,
    fieldDragCbs: {
        onDragEnd: function (obj) {
            console.log('onDragEnd',obj)
        },
        onDragStart: function (obj) {
            console.log('onDragStart:',obj) 
        }
    },

    formConfig: [
        {
            type: 'string',
            label: '姓名0',
            field: 'name',
            placeholder: '请输入',
            required: false,
            span: 12
        },
        {
            type: 'string',
            label: '年纪1',
            field: 'age',
            placeholder: '请输入',
            required: false,
            span: 12
        },
        {
            type: 'string',
            label: '爱好2',
            field: 'hobby',
            placeholder: '请输入',
            required: false,
            span: 12
        },
        {
            type: 'select',
            label: '其他3',
            field: 'other3',
            placeholder: '请输入',
            required: false,
            span: 12,
            optionData: [
                {
                    label: "test",
                    value: "0"
                }
            ]
        },
        {
            type: 'date',
            label: '其他4',
            field: 'other4',
            placeholder: '请输入',
            required: false,
            span: 12
        },
        {
            type: 'string',
            label: '其他5',
            field: 'other5',
            placeholder: '请输入',
            required: false,
            span: 12
        },
        // {
        //     type: 'string',
        //     label: '其他6',
        //     field: 'other6',
        //     placeholder: '请输入',
        //     required: false,
        //     span:12
        // },
    ],
    btns: [
        {
            field: "diy",
            label: "设置值",
            type: 'primary',
            onClick: function (obj) {
                obj.btnCallbackFn.setValues({
                    label: "值被更改",
                    value: null
                })
            }
        },
        {
            field: "diy",
            label: "获取值",
            type: 'primary',
            onClick: function (obj) { //此时里面会多一个 response
                obj.btnCallbackFn.getValues(true,function (vals) {
                    console.log(vals)
                })
            }
        },

    ]
};


