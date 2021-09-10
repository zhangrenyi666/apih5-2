//解析配置 确定是否需要绑定函数

//传入的可能是方法或者字符串等数据
//传入函数直接返回
//传入bind:开头则取method中的方法
//传入其他格式直接返回
//传入的字符串不是 bind: 也直接返回
//(fn | fnName | otherData)=>fn
const bind = function (arg) {
    const { method = {} } = this.props;
    if ((typeof arg) === 'function') {
        return arg
    } else if ((typeof arg) === "string") {

        //如果没有 bind: 则直接返回即可 
        if (arg.indexOf("bind:") !== 0) {
            return arg;
        }
 
        //取对应方法返回
        let methodName = arg.replace("bind:",'');
        if (!method[methodName]) {
            console.error(`${methodName}未在method中定义。`);
        }
        if(typeof method[methodName] !== "function"){ 
            console.error(`${methodName}不是一个函数，请勿在method中定义非函数数据。`);
        }
        return method[methodName];
    } else {
        //有传空情况 所以不执行处理
        return arg
    }
}

export default bind;