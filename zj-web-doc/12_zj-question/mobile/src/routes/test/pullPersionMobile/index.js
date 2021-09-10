import React, { Component } from 'react';
import { Button, WhiteSpace, Toast, WingBlank, Checkbox, List, Drawer, Flex, SearchBar, NavBar, Icon, ActivityIndicator } from 'antd-mobile';
import s from './style.less';
import { withRouter } from 'react-router-dom';
const CheckboxItem = Checkbox.CheckboxItem;
const version = '0.0.5';
const imgs = {
    //淡蓝色
    bluePersionImg: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgEAYAAAAj6qa3AAADJ0lEQVRo3u1YX0hTURyWNP+WIqYRqUsjGArmHwLzXz1ICUVIzYcgAhXCP4nk1YbsnjuwF4UwZ4rNl8T97sRpD4nMMK0HbZFtbufogpjonmoSStFDSehtw90FC1nidr3Gvj18cLmcc75v5/f9zrkhIUEEEUQQAoBOHXRgKi2NrmGPkY76eiYHjmJJZyfPqBfOk+S6Ov69Ay9YybmEJCXRq+wh/FKrRb/gMh7c2kIIAGOO25H590agBVMs2/pZN2wuSkw8QMJZlpD0dKQHFSmz230K9sWnQEoSVlZEvzMa9N1ltrsREdtbeXFxz8K9+RGo8CghSk43bJWFh4uvxjs0r/ByY6PfhXsx3Qp3cHhDg+gMQB3wBRdYLAE3wA4f8JzJJKKaV6uNxujoQAv3Dsl7Bp3u7cmoKDGkfZpVlpoqmAFuVvQOvViwpaTsuwHNt1zpHBMjtAH8vOIJwS0YIjes1oAb4O4G4usCb1z9Xy4PeAhOwAlypKVFhAcgdxiOQDymlpb8bkCCk4ptNtGE347t8LD2ueV2Zia6CCb8cXV1z8LnoBZrHQ4lBypzUUbGgTkSK5ZZ6XyTRII+sfHYOjGxa+FnnbXeo9fzXUb0gisqdE6EhtJGTe5Cbnm5knvt/IWFeXZG1VCWZSMnB62zUvKYpmkjGPGTgQEP26GenFMonHcJmZnLzv5TWtvj8OPy84hGuPy+btj4Pi4O/WAv4G/T055/8AFEkbD5+d0u3NtI2gEKUmw2e8a9BDN4bGrKVRLvTsfG7ptwPoxQLeRhymDwuaWvQhZ+uLaGJJrveGNsjCmF6/hKTw/P/HPPe77Gq3LdNmdnldxTboWLjBS+7Y06a5Tq7xf6APQXN0MTye/rEy7lF8FuKS0s/OcPHALdDRS9mmsLtoKCwBtQwkpx3uTkvgv3Zne3CfylRw7rmNrcFJ0B7nW5wpGUJSf73QBmnGUwVV0tOuFezDzTDFhQZaX/DSgBTGq6u0VvQD4rw4auLv+f7Nwhw3+SYiLYWjLe1sakaGYsX9vb0U3IJsdVKoQ0P/EZtTpg7J6Hn5dfB78uwcIwiCCC+C/wG7di2vTsqe0bAAAAAElFTkSuQmCC",
    blueFolderImg: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgEAYAAAAj6qa3AAABaElEQVRo3u3ZsUtCURTH8fsH1OBoQ6AgtLWJEAgRiG4FQjikD0GpIVIClXz3OTU0lQRha54jOAUJOTg0tYSQ95gNgeEQ4RBovCGcXks0tIb18J3f8p3vhzvdKwSPxxNCCJmEcmeSTMpXdKlesynfIaT2W63f1ljHOTqsVAoFRCKXy3YHL67iW+fM75cSQCnLmlqfIE8rjYbtAIwW3FAilZo6wFeLz7hEp8Ggfa6+RCRKp/8KQN7BjqoNh0YQFG1HIs4D+NkNMNV4NJILoKt6vz+t6h8o1Eu3Kx9gQHu5nH0A/qnFXShTOBBwLIAMVdPqKh53LIBRA42WEwkGYAAGYAAGYAAGYAAGYAAGYAAGYABHAkQxSnVNcxzAcdVUk1hMlKwLTy+6uCi3YFOBac48QB/WyDMeHwyqj+222/39Nqgf1ebvT3w+/Rw1usxm9VtEonx+ViqvoUzhTKZkIRJ5vfwVxuMJIYT4BGQM8pMyG9nLAAAAAElFTkSuQmCC",
}

class PullPersionMobile extends Component {
    state = {
        //树结构总数据
        treeData: this.props.treeData || '',

        //默认值传[]会直接把数据清空,页面将要render时最好把选好的默认值存到state中这样能保证数据不丢，
        //[val, val] 也是所选的所有数据
        defaultValue: this.props.defaultValue || [],

        //选中的数据 点击确定后会和defaultValue合并取消则清空 只在弹出层里有效 弹出层关闭即清空
        ModalData: [],

        //编辑状态下可以删除和显示添加按钮
        edit: this.props.edit === false ? false : true,

        //0部门和人员都能选  1只能选部门  2只能选人员
        selectType: this.props.selectType || "0",

        //是否在加载数据 
        loading: this.props.loading,

        //选人的弹出层是否是出现状态
        visible: false,

        //搜索的文字
        searchValue: '',

        //是否出现搜索框
        search: this.props.search || false,

        //一些其他设置
        //弹窗标题
        title: this.props.title ? this.props.title : '请选择',

        //表单label
        fLabel: this.props.label ? this.props.label : '',

        //导航条数据
        navData: [{
            label: this.props.firstNavLabel || '全部',
            value: 'all',
            type: '0'
        }],

        //当前渲染的节点
        listData: [],

        //最大值最小值
        minNumber: this.props.minNumber ? this.props.minNumber : 0,
        maxNumber: this.props.maxNumber ? this.props.maxNumber : 99999
    }

    //可配置项
    //k值
    k = {
        label: this.props.k ? (this.props.k.label ? this.props.k.label : "label") : "label",
        value: this.props.k ? (this.props.k.value ? this.props.k.value : "value") : "value",
        type: this.props.k ? (this.props.k.type ? this.props.k.type : "type") : "type",
        children: this.props.k ? (this.props.k.children ? this.props.k.children : "children") : "children",
    }

    //一些文字的配置
    textObj = {
        //搜索框的占位符
        searchPlaecholder: this.props.searchPlaecholder || 'search',
        //加载中的文字
        loading: this.props.textObj ? this.props.textObj.loading : 'loading...',
        //没有数据时显示的文字
        noData: this.props.textObj ? this.props.textObj.loading : '暂无数据',
        //底部选中的列表的标题
        subTit: this.props.textObj ? this.props.textObj.subTit : '已选择的部门或成员',
        //添加按钮的文字
        addBtn: this.props.textObj ? this.props.textObj.addBtn : '添加',
        //当selectType===1时弹出的提示
        noSelectPerson: this.props.textObj ? this.props.textObj.noSelectPerson : '只能选择部门！',
        //当selectType===2时弹出的提示
        noSelectDepartment: this.props.textObj ? this.props.textObj.noSelectDepartment : '只能选择人员！',
        //根节点不让选时弹出的提示
        rootNodeNoSelect: this.props.textObj ? this.props.textObj.rootNodeNoSelect : '根节点不可被选择！',
        //右上角保存按钮文字
        saveBtn: this.props.textObj ? this.props.textObj.saveBtn : '保存',
        //最大值最小值出限提醒 
        maxNumber: this.props.textObj ? this.props.textObj.maxNumber : '选择数量达到了上限',
        minNumber: this.props.textObj ? this.props.textObj.minNumber : `选择数量不足，至少需要选择${this.state.minNumber}个`
    }

    //默认值
    defaultValue = [];//默认值

    //antd的配置
    //导航条的配置
    NavBarAntd = this.props.NavBarAntd || {};
    //搜索框的配置
    searchAntd = this.props.searchAntd || {};

    //myFetch必须是一个promise对象
    myFetch = this.props.myFetch || false;

    //搜索时调用的接口
    searchApi = this.props.searchApi;
    //搜索文字的K
    searchParamsKey = this.props.searchParamsKey || 'searchText';
    //搜索时的其他参数 
    searchOtherParams = this.props.searchOtherParams || {};

    //根节点是否可选
    rootNodeSelect = this.props.rootNodeSelect || false;

    //本组件的url（路由）
    meUrl = this.props.meUrl || 'pullPersionMobile';

    //不可配置项
    //让选人组件出来/隐藏的函数
    hideFn = this.hideFn.bind(this);
    showFn = this.showFn.bind(this);
    //删除选中的函数
    delFn = this.delFn.bind(this);
    //删除选中的函数 --弹窗里用的
    modalConDelFn = this.modalConDelFn.bind(this);
    //弹窗出来隐藏函数
    onOpenChange = this.onOpenChange.bind(this);
    //搜索方法
    searchFn = this.searchFn.bind(this);
    //搜索值输入时
    searchChangeFn = this.searchChangeFn.bind(this);
    //保存
    saveFn = this.saveFn.bind(this);
    //取消
    cancelFn = this.cancelFn.bind(this);
    //双击节点
    CheckboxItemDoubleClick = this.CheckboxItemDoubleClick.bind(this);
    //获取数据
    // getValue = this.getValue.bind(this);
    //设置数据
    // setValue = this.setValue.bind(this);
    //导航条被点击
    navClick = this.navClick.bind(this);
    //插件内置使用的设置路由的插件
    setRouter = this.setRouter.bind(this);
    //是否使用路由控制区别就是按底部的返回键时用了路由控制不会直接退出当前页面
    routeControl = this.props.routeControl;
    //父级url
    parentUrl = '';
    //是否走路由监听 在返回和保存时不应该走路由监听
    routeListen = true;

    //判断是双击还是单击
    doubleClickCount = 0;

    componentWillReceiveProps(newProps) {
        this.setState({
            ...newProps
        });
    }

    componentDidMount() {
        let { children } = this.k;
        let url = "", params = {};
        if (this.routeControl) {
            url = this.props.match.url;
            params = this.props.params
        }
        // let { url, params } = this.props.match;
        this.defaultValue = [...this.state.defaultValue];

        if (!this.props.myFetch) {
            console.error('myFetch属性为必传')
        }
        if (this.props.help) {
            this.help()
        }

        // console.log(this)
        //如果路由是当前插件路由就打开弹层
        for (const key in params) {
            if (params[key] === this.meUrl) {
                let { ModalData, defaultValue } = this.state;
                this.setState({
                    ModalData: Object.assign([], defaultValue), //表单里的数据带到浮层中的选择的列表里
                    visible: true
                })
            }
        }

        if (this.routeControl) {
            //监听路由变化 如果navData有数据就不让回去
            this.props.history.listen((routeInfo) => {
                let { navData } = this.state; 
                if (this.routeListen && this.routeControl) {
                    if (routeInfo.pathname === this.parentUrl) {
                        if (navData.length > 1) {
                            //不能回去  并且删除一个导航条  重新设置listData
                            this.setRouter('in');
                            navData.pop();
                            this.setState({
                                navData,
                                listData: navData.length > 1 ? navData[navData.length - 1][children] : this.treeData
                            })
                        } else if (routeInfo.pathname === this.parentUrl) {
                            //关闭弹窗 
                            if (navData.length <= 1) {
                                this.setRouter('out', 'replace');
                                this.setState({
                                    //清空所有数据
                                    visible: false,
                                    ModalData: [],
                                })
                            }
                        }
                    } else if (routeInfo.pathname === this.meUrl) {
                        this.setState({
                            visible: false
                        })
                    }

                }

            })
        }

    }

    //帮助文档
    help() {
        console.log(`
        %c
        帮助文档：${version}

        <PullPersionMobile

            treeData            树结构数据 [array | object]  

            meUrl               路由名 [string]  eg  

            routeControl        用不用路由控制 主要作用是手机的back键点击后不会直接退出页面

            selectType="0"      [string]  默认0 选择类型  '0'人员部门都能选  '1'只能选择部门  '2'只能选择人员  

            help                帮助文档   [boolen]  默认false

            edit                是否可编辑 [boolen]  默认true

            label="人员"        左边label  [string]  默认null 

            title="人员选择"    弹出层的标题 [string] 默认“请选择”

            loading            loading状态  [boolen]  默认false

            myFetch={myFetch}  myFetch     [fn]  执行后后必须是return个promise对象    【必传】  

            search             是否开启搜索功能  [boolen]  默认false

            searchApi           搜索时调用的接口 [string]  默认null     【search功能存在时为必填】

            searchParamsKey     搜索时给后台的搜索值得k  [string] 默认searchText 

            searchOtherParams   搜索时的其他参数  [object] 默认空  eg {pageSize:'9999'} 

            rootNodeSelect      根节点是否可被选择 [boolen] 默认false

            minNumber           至少选择数量 [number] 默认 0
            
            maxNumber           最多选择数量 [number] 默认 9999
            
            可选的文字配置项
            textObj = { 
                searchPlaecholder  搜索框的占位符  [string]  默认'search',
                 
                loading 加载中的文字  [string] 默认'loading...',
                
                noData 没有数据时显示的文字 [string] 默认'暂无数据',
                
                subTit 底部选中的列表的标题 [string] 默认'已选择的部门或成员',
                
                addBtn   添加按钮的文字  [string] 默认'添加',

                saveBtn   右上角保存按钮文字  [string] 默认'保存',
                 
                noSelectPerson  当selectType===1时弹出的提示   默认'只能选择部门！',  

                noSelectDepartment 当selectType===2时弹出的提示 默认'只能选择人员！', 
                
                rootNodeNoSelect 根节点不让选时弹出的提示 默认'根节点不可被选择！',

            }
             
            
            可选的antd的配置
            导航条的配置
            NavBarAntd = {}  [object]  eg 同下
            搜索框的配置
            searchAntd = {}  [object]  eg {clear:true}

            可配置项  k值 
            k = {
                label: [string]  默认"label",
                value:[string]  默认"value",
                type: [string]  默认"type",
                children: [string]  默认"children",
            };

            firstNavLabel='全部'  面包屑导航条第一级名称  [string]   默认 '全部'
            
        />
        
        方法：
            getValue()  返回选中的数据
            setValue(array)  设置数据


        rc-form调用方式：
            const { getFieldDecorator } = form;
            {
                getFieldDecorator('MyFrom', {
                    valuePropName: 'defaultValue',
                    onChange: (v) => {
                        console.log('aaa', v)
                    }
                })(
                    <PullPersionMobile
                        help 
                        ref={(me) => {
                            if (me) {
                                this.pullPerson = me;
                            }
                        }}
                        treeData={[
                            {
                                label: '测试',
                                value: '0',
                                type: '2'
                            }
                        ]}
                    />
                )
            }


        `, 'color:green; font-size:20px; text-shadow:0 0 10px rgba(235, 235, 235, 0.5); padding:10px; box-sizing:border-box; border-radius:3px; border:1px solid green;background:black')
    }

    //获取数据
    getValue = () => {
        return this.state.defaultValue;
    }

    //设置数据
    setValue = (arr) => {
        this.setState({
            defaultValue: arr
        })
    }


    //双击节点
    CheckboxItemDoubleClick(e, data) {
        e.stopPropagation();
        let target = e.target;
        let { children, type, label, value } = this.k;
        this.doubleClickCount++;

        let _type = data[type];
        let _children = data[children];
        let _label = data[label];
        let _value = data[value];
        if (_type !== '2' && _children) {
            let { navData } = this.state;
            navData.push({
                value: _value,
                label: _label,
                type: _type,
                children: _children,
                _index: navData.length //这个是导航条的索引
            })
            //设置listData数据为children
            this.setState({
                listData: _children,
                navData
            })
        }

    }

    //保存函数
    saveFn() {
        let { ModalData, defaultValue, minNumber } = this.state;
        if (ModalData.length < minNumber) {
            Toast.info(this.textObj.minNumber)
            return;
        }
        this.defaultValue = [...ModalData];
        this.setState({
            defaultValue: Object.assign([], ModalData),
            ModalData: [],
            visible: false
        })
        if(this.props.onChange){//用于rc-form
            this.props.onChange(Object.assign([], ModalData));
        }
        this.setRouter('out', 'go');
    }

    valueChange = this.saveFn;

    //取消函数
    cancelFn() {
        //关闭弹窗并且清空ModalData
        this.setState({
            //清空所有数据
            visible: false,
            ModalData: [],
        })
        this.setRouter('out', 'go');
    }

    //人员或者部门展开
    PonChange = (e, val) => {
        e.stopPropagation();
        let checked = e.target.checked;
        let { ModalData, selectType, maxNumber } = this.state;
        let { label, value, type } = this.k;
        let _t = val[type]

        //提醒
        if (selectType === '1') {
            if (_t !== '1') {
                Toast.info(this.textObj.noSelectPerson, 2);
            }
        } else if (selectType === '2') {
            if (_t !== '2') {
                Toast.info(this.textObj.noSelectDepartment, 2);
            }
        }

        //根节点被选中时
        if (!this.rootNodeSelect && _t === '0') {
            Toast.info(this.textObj.rootNodeNoSelect, 2);
            return;
        }

        if (checked) {//被选中时
            //如果大于最大数量将不让选择
            if (ModalData.length >= maxNumber) {
                Toast.info(this.textObj.maxNumber)
                return;
            }


            //选的时候判断能不能被选中
            //人员部门都能选
            if (selectType === '0') {
                ModalData.push(val);
                this.setState({
                    ModalData
                })
            } else if (selectType === '1') {//只能选人员
                if (_t === '1') {
                    ModalData.push(val);
                    this.setState({
                        ModalData
                    })
                }
            } else if (selectType === '2') {//只能选部门
                if (_t === '2') {
                    ModalData.push(val);
                    this.setState({
                        ModalData
                    })
                }
            }

        } else {//取消选中时  
            ModalData.map((item, index) => {
                if (item[value] === val[value]) {
                    ModalData.splice(index, 1);
                    return ModalData;
                }
            })
            this.setState({
                ModalData
            })
        }

    }

    //搜索框输入值时调用
    searchFn(val) {
        let { treeData } = this.state;
        // console.log(val);
        if (!this.searchApi) {
            console.error('需要搜索必须传入searchApi属性');
        }
        if (val && this.props.myFetch) {
            this.setState({
                loading: true
            })
            this.myFetch(this.searchApi, { [this.searchParamsKey]: val, ...this.searchOtherParams }).then(({ data, message, success }) => {
                if (success) {
                    data.map((item, index) => {
                        return item.search = true;
                    })
                    //备份树结构数据
                    this.treeData = treeData;
                    this.setState({
                        listData: data,
                        loading: false
                    })
                } else {
                    this.setState({
                        loading: false
                    })
                }
            })

        } else {
            this.setState({
                listData: treeData
            })
        }

    }

    searchChangeFn(val) {
        clearTimeout(window.searchChangeTimer);

        window.searchChangeTimer = setTimeout(() => {
            this.searchFn(val);
        }, 200);
    }

    hideFn() {
        this.setState({
            visible: false
        })
    }

    //导航条被点击
    navClick(item) {
        let { navData, listData, treeData } = this.state;
        let { label, value, type, children } = this.k;
        let _type = item[type],
            _value = item[value],
            _label = item[label],
            _children = item[children],
            _index = item._index;

        //最后一个和第一个都不能被点击
        if (_index && _index !== navData.length - 1) {
            //处理listData和navData
            listData = _children;
            let _navData = navData.splice(0, _index + 1);

            this.setState({
                listData,
                navData: _navData
            })
        } else if (_value === 'all') {
            //点击了全部
            let _navData = navData.splice(0, 1);
            listData = treeData;
            this.setState({
                listData,
                navData: _navData
            })
        }

    }

    //设置路由
    setRouter(action, fn) {
        if (this.routeControl) {
            if (action === 'in') {
                //进入 
                //将父级url存起来
                //不能使用goback go等
                let { url } = this.props.match;
                this.parentUrl = url;
                //设置路由监听
                this.routeListen = true;
                //设置路由 
                if (fn) {
                    this.props.history[fn](this.meUrl);
                } else {
                    this.props.history.push(this.meUrl);
                }
            } else if (action === 'out') {
                //出去 
                //取消路由监听 
                this.routeListen = false;
                if (fn) {
                    if (fn === 'go') {
                        this.props.history.go(-1);
                        return;
                    }
                    this.props.history[fn](this.parentUrl);
                } else {
                    this.props.history.push(this.parentUrl);
                }

            }
        }
    }

    //弹出层被点出来的时候
    showFn() {
        //将插件路由push到history中
        this.setRouter('in');

        let { ModalData, defaultValue } = this.state;
        this.setState({
            ModalData: Object.assign([], defaultValue), //表单里的数据带到浮层中的选择的列表里
            visible: true
        })
    }

    delFn(delValue) {
        let { defaultValue } = this.state;
        let { value } = this.k;
        for (let i = 0; i < defaultValue.length; i++) {
            if (defaultValue[i][value] === delValue) {
                //删除该人员或者部门
                defaultValue.splice(i, 1);
                this.setState({
                    defaultValue
                });
                this.defaultValue = defaultValue;
                return;
            }
        }
    }

    //删除的只是弹窗里的数据
    modalConDelFn(delValue) {
        let { ModalData } = this.state;
        let { value } = this.k;
        for (let i = 0; i < ModalData.length; i++) {
            if (ModalData[i][value] === delValue) {
                //删除该人员或者部门
                ModalData.splice(i, 1);
                this.setState({
                    ModalData
                });
                return;
            }
        }
    }

    onOpenChange() {
        this.setState({ visible: !this.state.visible });
    }

    render() {
        let { label, value, type, children } = this.k;
        let {
            treeData,
            defaultValue,
            ModalData,
            title,
            edit,
            loading,
            visible,
            fLabel,
            search,
            navData,
            listData,
            selectType

        } = this.state;

        //设置树结构为数组
        if (treeData) {
            if (Array.isArray(treeData)) {
                treeData = treeData;
            } else {
                treeData = [treeData]
            }
        }

        //设置列表渲染数据
        //列表里面的不能用treeData
        if (navData.length === 1) {
            listData = listData && listData.length > 0 ? listData : treeData;
        }

        //form表单里的数据
        // console.log(defaultValue)
        const formCon = (
            this.defaultValue ?
                this.defaultValue.map((item, index) => {
                    let _type = item[type],
                        _value = item[value],
                        _label = item[label];
                    return <div key={index} style={{ paddingRight: edit ? '25px' : '5px' }} className={`${s.fItem}  ${_type === '2' ? s.per : s.folder}`}>
                        {_label}
                        <span style={{ display: !edit ? 'none' : '' }} onClick={() => { this.delFn(_value) }} className={s.fdel}>×</span>
                    </div>
                })
                :
                null
        )

        //弹窗里的数据
        const modalCon = (
            ModalData.length < 1 ?
                <WingBlank style={{ color: '#ccc', boxSizing: 'border-box' }}>
                    <center >{this.textObj.noData}</center>
                </WingBlank> :
                ModalData.map((item, index) => {
                    let _type = item[type],
                        _value = item[value],
                        _label = item[label];
                    return <div key={index} style={{ paddingRight: edit ? '25px' : '5px', boxSizing: 'border-box' }} className={`${s.fItem}  ${_type === '2' ? s.per : s.folder}`}>
                        {_label}
                        <span style={{ display: !edit ? 'none' : '' }} onClick={() => { this.modalConDelFn(_value) }} className={s.fdel}>×</span>
                    </div>
                })
        )

        //渲染导航条
        const renderNav = (<div className={s.navContainer}>
            {
                navData.map((item, index) => {
                    return <div key={index} className={s.navItem} onClick={() => { this.navClick(item) }}>
                        {item.label}
                        {
                            index === navData.length - 1 ? '' : <span style={{ paddingLeft: '5px' }}>/</span>
                        }
                    </div>
                })
            }
        </div>)


        //渲染列表
        const renderList = (<div>
            {
                <List
                    className={s.myList}
                    renderHeader={renderNav}
                    renderFooter={<div className={s.footer}>
                        <div className={s.tit}>
                            <WingBlank style={{ color: '#ccc' }}>
                                {this.textObj.subTit}
                            </WingBlank>
                        </div>
                        <div className={s.footerCon}>
                            <WingBlank>
                                {modalCon}
                            </WingBlank>
                        </div>

                    </div>}>
                    <div className={s.myListCon}>

                        {
                            listData && listData.length ?
                                listData.map((item, index) => {
                                    let _type = item[type],
                                        _value = item[value],
                                        _search = item.search,
                                        _label = item[label];
                                    let valO = {
                                        value: _value,
                                        label: _label,
                                        type: _type
                                    }

                                    //设置是否被选中
                                    let _checked = false;

                                    ModalData.map((v, i) => {
                                        let _val = v[value];
                                        if (_val === _value) {
                                            return _checked = true;
                                        }
                                    })

                                    let _isCheck = false;//是否可以选择
                                    let _img = '';//应该显示那张图片
                                    if (selectType === '0') {//人员部门都可以选
                                        if (_type === '2') {//人员
                                            _img = imgs.bluePersionImg;
                                            _isCheck = true;
                                        } else {
                                            _img = imgs.blueFolderImg;
                                            _isCheck = true;
                                        }
                                    } else if (selectType === '1') {//只能选择部门
                                        if (_type === '2') {//人员
                                            // _img = imgs.persionImg;
                                            _img = imgs.bluePersionImg;
                                        } else {
                                            _img = imgs.blueFolderImg;
                                            _isCheck = true;
                                        }
                                    } else if (selectType === '2') {//只能选择人员
                                        if (_type === '2') {//人员
                                            _img = imgs.bluePersionImg;
                                            _isCheck = true;
                                        } else {
                                            // _img = imgs.folderImg;
                                            _img = imgs.blueFolderImg;
                                        }
                                    } else {
                                        console.log('selectType类型不对')
                                    }
                                    //根节点不可选的时候
                                    if (!this.rootNodeSelect && _type === '0') {//根节点
                                        // _img = imgs.folderImg;
                                        _img = imgs.blueFolderImg;
                                        _isCheck = true;
                                        _isCheck = false;
                                    } else if (this.rootNodeSelect && _type === '0') {
                                        _img = imgs.blueFolderImg;
                                        _isCheck = true;
                                    }


                                    return <div key={index}
                                        className={s.lItem}
                                        onClick={(e) => { this.CheckboxItemDoubleClick(e, item) }}
                                    >
                                        {
                                            _isCheck ?
                                                <CheckboxItem
                                                    onClick={(e) => {
                                                        //点击是input框的话需要阻止冒泡
                                                        if (e.target.type === 'checkbox') {
                                                            e.stopPropagation();
                                                        }
                                                    }}
                                                    extra={<img src={_img} alt='img'/>}
                                                    checked={_checked}
                                                    arrow={(_type === '2' || _search) ? '' : "horizontal"}
                                                    onChange={(e) => this.PonChange(e, valO)}
                                                >
                                                    {_label}
                                                </CheckboxItem>
                                                :
                                                <List.Item
                                                    onClick={(e) => {
                                                        //点击是input框的话需要阻止冒泡
                                                        if (e.target.type === 'checkbox') {
                                                            e.stopPropagation();
                                                        }
                                                    }}
                                                    arrow={(_type === '2' || _search) ? '' : "horizontal"}
                                                    extra={<img src={_img} alt='img'/>}>
                                                    {_label}

                                                </List.Item>
                                        }

                                    </div>
                                })
                                :
                                <div className={s.treeNoData}>
                                    {this.textObj.noData}
                                </div>

                        }
                    </div>
                </List>

            }

        </div>)


        //主内容
        const sidebar = (<div className={s.pullPersonContainer}>
            <div>
                <NavBar
                    mode="dark"
                    icon={<div><Flex><Icon type="left" /><div>取消</div></Flex></div>}
                    onLeftClick={this.cancelFn}
                    rightContent={<div onClick={this.saveFn}>{this.textObj.saveBtn}</div>}
                    {...this.NavBarAntd}
                >{title}</NavBar>
            </div>
            <div style={{ display: search ? 'block' : 'none' }} className={s.searchContainer}>
                <SearchBar onSubmit={this.searchFn} onChange={this.searchChangeFn} placeholder="Search" {...this.searchAntd} />
            </div>

            {/* 节点渲染 */}
            <div className={s.treeContainer}>
                <WhiteSpace />
                {
                    loading ?
                        <ActivityIndicator className={s.myActivityIndicator} text={this.textObj.loading} />
                        :
                        treeData ? renderList : this.textObj.noData

                }

                <WhiteSpace />
            </div>

        </div>);

        return (
            <div className={s.PullPersionMobile}>
                {/* form表单 */}
                <div>
                    <Flex align="start">
                        <div className={s.label} style={{ display: !fLabel ? 'none' : '' }}>
                            <WhiteSpace />
                            {fLabel}
                            <WhiteSpace />
                        </div>
                        <div className={s.con}>
                            {formCon}
                            {
                                edit
                                    ?
                                    <div className={s.addBtn}><span onClick={this.showFn}>{this.textObj.addBtn}</span></div>
                                    : null
                            }

                        </div>
                    </Flex>
                </div>

                <Drawer
                    className={s.myDrawer}
                    style={{
                        minHeight: document.documentElement.clientHeight,
                        transform: `translate(${visible ? 0 : '100%'})`,
                        transition: `${visible ? '' : '0.3s'}`
                    }}
                    enableDragHandle
                    contentStyle={{ color: '#A6A6A6', textAlign: 'center', paddingTop: 42 }}
                    sidebar={sidebar}
                    open={this.state.visible}
                    onOpenChange={this.onOpenChange}
                >
                    loading...
                </Drawer>


            </div>
        )
    }
}
// export default withRouter(PullPersionMobile);
export default PullPersionMobile;