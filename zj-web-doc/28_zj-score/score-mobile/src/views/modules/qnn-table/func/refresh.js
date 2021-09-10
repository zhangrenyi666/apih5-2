import { message as Msg } from "antd";

// 刷新表格数据
const refresh = function (cb) {
    let { fetchConfig,apis } = this.state;

    let listConfig = {};
    //没有配置fetchConfig
    if (!fetchConfig && !apis) {
        this.setState({ loading: false });
        return;
    }
    if (apis) {
        listConfig = apis.list || {};
    }

    //从两个地方取值fetchConfig优先
    fetchConfig = fetchConfig || listConfig;
    const { success } = fetchConfig;
    //回调 优先传入的
    cb = cb || success;


    //刷新数据时候可能因为该表格是被嵌入qnn-form中的
    //在后台配置params后获取规则改为从网址获取 如果没有获取到则在表单中取
    //异步一下是因为在获取到表单值之后还未设置进表单在获取参数的方法中就无法获取到form项数据
    //造成原因：因为开始没有吧表格嵌入到表单的操作，取值只会到网址上取值  ***需要优化 
    this.setState({ loading: true });
    setTimeout(() => { //async 
        this.getTableFetchParams({ // await 
            ...fetchConfig
        }).then(({ params,apiName }) => {
            if (!apiName) return;
            this.fetch(apiName,params).then(res => {
                let { data,totalNumber,success,message } = res; 
                if (!this.isMounted) {
                    return;
                }
                if (success) {
                    this.setState({
                        totalNumber,
                        data,
                        loading: false,
                        isNeedRefresh: false
                    });
                    if (cb) {
                        cb(res,{ btnCallbackFn: this.btnCallbackFn });
                    }
                } else {
                    Msg.error(message,1,() => {
                        this.setState({
                            loading: false,
                            isNeedRefresh: false
                        });
                    });
                    if (cb) {
                        cb(res,{ btnCallbackFn: this.btnCallbackFn });
                    }
                }
            });
        });
    },1)

};
export default refresh;
