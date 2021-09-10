const wran = function(){
    console.assert(this.props.form, 'form对象必传  来自：qnn-form警告');
    console.assert(this.props.match, '路由信息match属性为必传  来自：qnn-form警告');
    console.assert(this.props.history, '路由信息history属性为必传  来自：qnn-form警告'); 
    console.assert(this.props.fetch, 'fetch属性为必传  来自：qnn-form警告'); 

}
export default wran;