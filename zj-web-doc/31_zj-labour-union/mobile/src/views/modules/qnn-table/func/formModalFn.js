const handleCancel = function() {
    this.setState({
        modalVisible: false
    });
};

const handleOk = function() {
    this.setState({
        modalVisible: false
    });
};

const modalBtnClick = function(obj) {
    //未写提交
    let { name } = obj;
    if (name === "close") {
        this.handleCancel();
    }
};

export { handleCancel, handleOk, modalBtnClick };
