const isNeedRefresh = function() {
    const { isNeedRefresh } = this.state; 
    //进行比较
    if (isNeedRefresh) { 
        this.refresh();
    }
};

export default isNeedRefresh;
