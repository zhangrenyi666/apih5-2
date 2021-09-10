const _delField = function (obj) {
    const {
        selectedRows
    } = obj;
    let { data = [],editData = {} } = this.state;
    const { _delFieldCb } = this.props;
    let { formConfig = [] } = editData;

    data = data.filter(item=>{
        return !selectedRows.map(item=>item._id).includes(item._id);
    });
    editData.formConfig = formConfig.filter(item=>{
        return !selectedRows.map(item=>item.field).includes(item.form.field);
    });
     
    //回调
    _delFieldCb && _delFieldCb({ props: this.props,delData: selectedRows,tableConfig: editData });

    this.setState(
        {
            editData,
            data
        } 
    );
};

export default _delField;
