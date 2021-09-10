const _addField = function(obj) {
    const {
        btnCallbackFn: { closeDrawer },
        formData
    } = obj;
    const { data = [], editData = {} } = this.state;
    let { formConfig = [] } = editData;
    let {
        _id,
        title,
        label,
        dataIndex,
        field,
        type,
        isInSearch,
        isInForm,
        isInTable,
        filter,
        disabled,
        addDisabled,
        editDisabled
    } = formData;
    let _title = title || label;
    let _field = field || dataIndex;
    let table = {
        title: _title,
        dataIndex: _field,
        filter
    };
    let form = {
        label: _title,
        field: _field,
        type,
        disabled,
        addDisabled,
        editDisabled
    };
    let formConfigItem = {
        table,
        form,
        isInSearch,
        isInForm,
        isInTable
    };
    if (_id) {
        //编辑字段
        let _index = 0;
        for(let i=0;i<data.length;i++){
            if(data[i]._id === _id){
                _index = i;
            }
        }
        formConfig.splice(_index, 1, formConfigItem);
        editData.formConfig = formConfig;
        //表格中的展示数据
        let _tableData = {
            ...formData,
            title: _title,
            dataIndex: _field, 
        };
        data.splice(_index, 1,_tableData); 
    } else {
        //新增字段
        //生成的数据
        formConfig.push(formConfigItem);
        editData.formConfig = formConfig;

        //表格中的展示数据
        let _tableData = {
            ...formData,
            title: _title,
            dataIndex: _field,
            _id: data.length + 1
        };
        data.push(_tableData);
    }

    this.setState(
        {
            editData,
            data
        },
        () => {
            closeDrawer(false);
        }
    );
};

export default _addField;
