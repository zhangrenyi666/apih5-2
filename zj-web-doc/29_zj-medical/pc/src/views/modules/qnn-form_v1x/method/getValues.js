
//获取全部表单值
//需要绑定this
//@是否需要验证表单  @回调函数(values)=>void
const getValues = function (isValidate = false,cb) {

    // 格式化参数方法
    const formatParams = (params) => {
        const { formConfig,tabs = [] } = this.state;
        let _formConfig = [...formConfig];
        let isMobile = this.isMobile();
        if (tabs.length) {
            //取所有为表单的tab页面的字段
            //赋值会给所有表单赋值
            _formConfig = [];
            tabs.map(item => {
                const { name,content } = item;
                if (name === "qnnForm") {
                    _formConfig = _formConfig.concat(content.formConfig);
                }
                return item;
            });
        } 
        return this.sFormatData(params,_formConfig,"get",isMobile) 
    }

    //获取值
    const getFieldsValue = (name) => {
        let values = {};

        //普通字段
        if (this.fieldsObj) {
            for (const key in this.fieldsObj) {
                if (this.fieldsObj.hasOwnProperty(key)) {
                    const element = this.fieldsObj[key];

                    //因为表单块的字段field是userInfo_Block.age这种样子的（前面一个一定是qnn-form加上去的也就是包含_Block  为了避免字段名就是xx.xx的形式所有需要做好判断）
                    //所以在取的时候需要去掉.号前面的部分
                    //因为表单块取值时候实际上也是调用了表单块的这个方法所以需要在这处理
                    const arrayKey = key.split('.'); 
                    if (arrayKey.length > 1 && arrayKey[0].indexOf('_Block') !== -1) {
                        values[`${arrayKey[arrayKey.length - 1]}`] = element.getValue();
                    } else {
                        values[`${key}`] = element.getValue();
                    }

                }
            }
        }

        //表单块
        //需要注意可增减的表单块是个对象{0:formBlocksItem,xxxx}
        if (this.formBlocks) {
            for (const key in this.formBlocks) {
                if (this.formBlocks.hasOwnProperty(key)) {

                    const formBlocksItem = this.formBlocks[key]; 
                    if(formBlocksItem.getValues){
                        //单纯的表单块
                        //这去调用每个表单块的getValues方法
                        formBlocksItem.getValues(true,(vals) => {
                            values[key] = vals; 
                        })
                    }else{
                        //可增减的表单块
                        //需要格式化为数组因为在格式化数据中用数组判断的
                        values[key] = [];
                        console.log(formBlocksItem)
                        for (const _key in formBlocksItem) {
                            if (formBlocksItem.hasOwnProperty(_key)) {
                                const _element = formBlocksItem[_key];
                                _element.getValues(true,(vals) => { 
                                    values[key].push(vals) 
                                })
                            }
                        } 
                    } 
                  
                }
            }
        }
 
        return values;
    }

    if (!isValidate) {
        //不需要验证
        // let values = formatParams(this.props.form.getFieldsValue());
        // if (cb) {
        //     cb({
        //         ...values
        //     })
        // }
        console.log('无需验证错误！！');

        let _vals = formatParams(getFieldsValue());  
        if (cb) {
            cb({
                ..._vals
            })
        }
        return _vals;
    } else { 
        //需要效验所有字段
        let _vals = formatParams(getFieldsValue());  
        if (cb) {
            cb({
                ..._vals
            })
        }
        return _vals;
    }
}

export default getValues;