const _addField = function (obj) {
    const {
        btnCallbackFn: { closeDrawer },
        formData
    } = obj;
    const { data = [],editData = {} } = this.state;
    const { _addFieldCb,_editFieldCb } = this.props;
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

    //适合表格使用
    let _title = title || label;
    let _dataIndex = dataIndex || field;

    
    //适合表单使用
    let _label = label || title;
    let _field = field || dataIndex;

    let table = {
        title: _title,
        dataIndex: _dataIndex,
        filter
    };
    let form = {
        label: _label,
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
        for (let i = 0; i < data.length; i++) {
            if (data[i]._id === _id) {
                _index = i;
            }
        }
        formConfig.splice(_index,1,formConfigItem);
        editData.formConfig = formConfig;
        //表格中的展示数据
        let _tableData = {
            ...formData,
            title: _title,
            dataIndex: _dataIndex,
        };
        data.splice(_index,1,_tableData);
        //回调
        _editFieldCb && _editFieldCb({ props: this.props,editData: _tableData,tableConfig: editData });
    } else {
        //新增字段
        //生成的数据
        formConfig.push(formConfigItem);
        editData.formConfig = formConfig;

        //表格中的展示数据
        let _tableData = {
            ...formData,
            title: _title,
            dataIndex: _dataIndex,
            _id: data.length + 1
        };
        data.push(_tableData);
        //回调
        _addFieldCb && _addFieldCb({ props: this.props,addData: _tableData,tableConfig: editData });
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
