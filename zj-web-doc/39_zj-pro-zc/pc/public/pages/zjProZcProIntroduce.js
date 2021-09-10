

window.zjProZcProIntroduce = {
    fetchConfig: {
        apiName: "getZjProZcProIntroduceList"
    },
    formConfig: [
        {
            label: '主键id',
            field: 'proIntroduceId',
            hide:true
        },
        
        {
            type: 'slider',
            label: '播放速度',
            field: 'seconds', 
            required: true, 
            span:12,
            min:60,
            max:150,
            marks: { 60: '1档', 70: '2档', 80: '3档', 90: '4档', 100: '5档', 110: '6档', 120: '7档', 130: '8档', 140: '9档', 150: '10档'},
            onChange: (value,obj) => {
                var fetch = obj.fns.fetch;
                fetch('updateZjProZcProIntroduceSeconds',{"seconds":value,"proIntroduceId":obj.form.getFieldValue('proIntroduceId')}).then();
            },
        },
        {
            type: 'richtext',
            label: '',
            field: 'introduce',
            placeholder: '请输入',
            fetchConfig: { uploadUrl: window.configs.domain + 'upload' },
            formItemLayout: {
                labelCol: {
                    sm: { span: 2 }
                },
                wrapperCol: {
                    sm: { span: 22 }
                }
            }
        }
    ],
    tailFormItemLayout:{
        wrapperCol: {
            xs: {
                    span: 24,
                    offset: 0
            },
            sm: {
                    span: 24,
                    offset: 12
            }
        }
    },
    btns: [
        {
            field: "submit",
            label: "提交",
            type: 'primary',
            fetchConfig: {
                apiName: 'addZjProZcProIntroduce',
            },
            onClick:function(obj){
                //qnnForm
                obj.btnCallbackFn.refresh()
            }

        }
    ],
    
};


