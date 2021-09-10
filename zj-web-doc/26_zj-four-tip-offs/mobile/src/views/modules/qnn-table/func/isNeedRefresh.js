const isNeedRefresh = function() {
    const { isNeedRefresh, fetchConfig={} } = this.state; 
    //进行比较
    if (isNeedRefresh) { 
        this.refresh(fetchConfig.success);
    }
};

export default isNeedRefresh;
