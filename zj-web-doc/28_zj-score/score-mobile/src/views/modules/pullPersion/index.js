import React,{ Component } from "react";
import {
    Row,
    Col,
    Popconfirm,
    Modal,
    Tree,
    Spin,
    Input,
    message,
    Tooltip
} from "antd";
import styles from "./style.less";
const TreeNode = Tree.TreeNode;
const Search = Input.Search;
const Msg = message;

const myFetch = () => {
    console.log("暂时未写fetch请求，需用props传入");
};

export default class PullPersion extends Component {
    constructor(props) {
        super(props);

        const { textObj = {} } = this.props;

        this.state = {
            //树结构总数据
            treeData: this.props.treeData || "",

            //默认值传[]会直接把数据清空,页面将要render时最好把选好的默认值存到state中这样能保证数据不丢，
            //[val, val] 也是所选的所有数据  defaultValue || defaultData 为了兼容1.0.1以下版本
            defaultValue:
                this.props.defaultValue || this.props.defaultData || [],

            //浮层里面的数据 点击确定后会和defaultValue合并取消则清空 只在弹出层里有效 弹出层关闭即清空
            ModalData: this.props.defaultValue || [],

            //编辑状态下可以删除和显示添加按钮
            edit: this.props.edit === false ? false : true,

            //0部门和人员都能选  1只能选部门  2只能选人员
            selectType: this.props.selectType || "0",

            //最多能选多少人
            maxNumber: this.props.maxNumber ? this.props.maxNumber : 99999,

            //最少选择多少人
            minNumber: this.props.minNumber ? this.props.minNumber : 0,

            //内置使用
            loading: this.props.loading, //是否在加载数据
            hide: true, //选择人面板影藏状态
            visible: this.props.visible || false, //选人的弹出层是否是出现状态
            searchValue: "",

            search: false,
            onSave: this.props.onSave || function () { },
            onCancel: this.props.onCancel || function () { },

            //收藏
            useCollect: this.props.useCollect, //开启收藏功能
            collectApi: this.props.collectApi, //查询收藏人员
            collectApiByAdd: this.props.collectApiByAdd, //新增收藏人员
            collectApiByDel: this.props.collectApiByDel, //删除收藏人员

            //搜索时的loading
            searchLoading: false
        };

        //渲染树的函数
        this.renderTree = this.renderTree.bind(this);
        //渲染树节点的函数
        this.renderTreeNodes = this.renderTreeNodes.bind(this);
        //节点点击函数
        this.nodeClick = this.nodeClick.bind(this);
        //搜索函数
        this.searchChange = this.searchChange.bind(this);
        //获取数据的方法
        this.getSelectData = this.getSelectData.bind(this);
        this.getData = this.getSelectData.bind(this);
        //设置数据的方法
        this.setData = this.setData.bind(this);

        //onChange函数
        this.onChange = this.props.onChange;

        //改变可以改变值得函数
        this.transform = this.props.transform;

        //数据被改变时将执行的方法
        this.valueChange = this.valueChange.bind(this);

        //更新插件里的所有可配置项  当传入apiName时使用
        this.upDateConfig = this.upDateConfig.bind(this);

        //label属性有就渲染没有就不渲染
        this.label = this.props.label || "";
        this.title = this.props.title || "请选择";


        this.textObj = {
            loading: "loading...",
            noData: "暂无数据",
            rightTitle: "已选择的部门或成员",
            maxNumber: "选择数量达到了上限",
            minNumber: `选择数量不足，至少需要选择${this.state.minNumber}`,
            ...textObj
        }
        this.k = {}; //k值
        if (this.props.k) {
            this.k = {
                label: this.props.k.label || "label",
                value: this.props.k.value || "value",
                type: this.props.k.type || "type",
                children: this.props.k.children || "children"
            };
        } else {
            this.k = {
                label: "label",
                value: "value",
                type: "type",
                children: "children"
            };
        }

        //myFetch方法
        this.myFetch = this.props.myFetch || myFetch;
        this.fetchConfig = this.props.fetchConfig || {};
        if (!this.props.myFetch) {
            console.error("myFetch属性必须设置");
        }
        //搜索时调用的接口
        this.search = this.props.search;
        this.searchPlaceholder = this.props.searchPlaceholder;
        this.searchApi = this.props.searchApi;
        this.searchParamsKey = this.props.searchParamsKey || "searchText"; //搜索文字的K
        this.searchOtherParams = this.props.searchOtherParams || {}; //搜索时的其他参数
    }


    //初始为被卸载状态
    isMounted = false;

    componentWillReceiveProps(newProps) {
        this.setState({
            ...newProps
        });
    }
    componentWillUpdate(nextProps,nextState) {
        if (!this.state.visible && nextState.visible) {
            //请求数据渲染树
            this.refresh();
        }
    }

    componentDidMount() {
        this.isMounted = true;
        if (this.props.fetchConfig && this.props.fetchConfig.apiName) {
            this.refresh();
        }
        if (this.props.didMount) {
            this.props.didMount({ ...this.props })
        }
    }

    componentWillUnmount() {
        this.isMounted = false;
    }

    //更新插件里的所有可配置项  当传入apiName时使用
    upDateConfig(newConfig = {}) {
        const { treeData,edit,selectType,maxNumber,minNumber } = this.state;
        let {
            label,
            title,
            textObj,
            search,
            searchApi,
            searchParamsKey,
            searchOtherParams,
            k
        } = this;

        //newConfig格式
        // let newConfig = {
        //   treeData: object || array<object>,//树结构 --必带
        //   edit: boolean, //是否可编辑
        //   selectType: string, //能选择的类型0 1 2
        //   maxNumber: number,//最大选择个数
        //   minNumber: number,//最小选择个数

        //   //搜索配置
        //   search: boolean,
        //   searchApi: string,
        //   searchParamsKey: string,//搜索文字的K 默认searchText
        //   searchOtherParams: object,//搜索时的其他参数

        //   key = {
        //     label: "label",
        //     value: "value",
        //     type: "type",
        //     children: "children"
        //   },

        //   //一些文字配置
        //   textObj: {
        //     loading: 'loading...',
        //     noData: '暂无数据',
        //     rightTitle: '已选择的部门或成员',
        //     maxNumber: '选择数量达到了上限',
        //     minNumber: `选择数量不足，至少需要选择${this.state.minNumber}`,
        //   }
        // }

        this.setState({
            treeData: newConfig["treeData"] || treeData, //树结构
            edit: newConfig["edit"] || edit, //是否可编辑
            search: newConfig["search"],
            selectType: newConfig["selectType"] || selectType, //选择类型
            maxNumber: newConfig["maxNumber"]
                ? newConfig["maxNumber"]
                : maxNumber, //最大选择个数
            minNumber: newConfig["minNumber"]
                ? newConfig["minNumber"]
                : minNumber //最小选择个数
        });
        this.label = newConfig.label || label;
        this.title = newConfig.title || title;

        //搜索配置
        this.search = newConfig.search || search;
        this.searchApi = newConfig.searchApi || searchApi;
        this.searchParamsKey = newConfig.searchParamsKey || searchParamsKey; //搜索文字的K
        this.searchOtherParams =
            newConfig.searchOtherParams || searchOtherParams; //搜索时的其他参数

        //k值配置
        if (newConfig["key"]) {
            this.k = {
                label: newConfig["key"].label || k["label"],
                value: newConfig["key"].value || k["value"],
                type: newConfig["key"].type || k["type"],
                children: newConfig["key"].children || k["children"]
            };
        }

        //一些文字配置
        if (newConfig["textObj"]) {
            this.textObj = {
                loading: newConfig["textObj"].loading || textObj.loading,
                noData: newConfig["textObj"].noData || textObj.noData,
                rightTitle:
                    newConfig["textObj"].rightTitle || textObj.rightTitle,
                maxNumber: newConfig["textObj"].maxNumber || textObj.maxNumber,
                minNumber: newConfig["textObj"].minNumber || textObj.minNumber
            };
        }
    }

    //获取树结构
    refresh = () => {
        if (this.fetchConfig.apiName) {
            //请求接口
            this.setState({
                loading: true
            });
            this.myFetch(
                this.fetchConfig.apiName,
                this.fetchConfig.params
            ).then(({ success,message,data }) => { 
                if (!this.isMounted) {
                    return;
                }
                if (success) {
                    if (this.props.collectApi && this.props.useCollect) {
                        this.getCollect(data);
                    } else {
                        this.setState({
                            treeData: data,
                            loading: false
                        });
                    }

                } else {
                    this.setState(
                        {
                            loading: false
                        },
                        () => {
                            Msg.error(message);
                        }
                    );
                }
            });
        }
    };

    //获取数据
    getSelectData() {
        return this.state.defaultValue;
    }

    //设置数据
    setData(data = []) {
        this.setState({
            defaultValue: data
        });
    }

    //数据被改变时将执行的方法
    //将执行两个方法  传入值 执行后会有个cb参数是被改变后的新值
    valueChange(val,cb) {
        let _val = val;
        if (this.onChange) {
            this.onChange(val);
        }
        if (this.transform) {
            _val = this.transform(val);
            if (!_val) {
                console.warn("请在transform里将值return出去");
            }
        }
        cb(this.transform ? _val : false);
    }

    //添加按钮点击后让浮层弹出来 并且设置 ModalData = defaultValue
    addBtn() {
        let { value } = this.k;
        let { defaultValue } = this.state;

        //设置树结构默认被展开的
        let defaultCheckedKeys = [];
        if (defaultValue) {
            for (let i = 0; i < defaultValue.length; i++) {
                let _value = defaultValue[i][value];
                defaultCheckedKeys.push(_value);
            }
        }

        this.setState({
            defaultCheckedKeys,
            ModalData: Object.assign([],defaultValue), //表单里的数据带到浮层中的选择的列表里
            visible: true
        });
    }

    //浮层ok按钮被点击时
    handleOk = e => {
        let { ModalData,minNumber,onSave } = this.state;
        this.valueChange(ModalData,val => {
            //小于最小值不让关闭
            if (ModalData.length < minNumber || val.length < minNumber) {
                message.error(this.textObj.minNumber);
                return;
            }
            //如果返回的val有值得话说明this.transform存在
            if (val) {
                this.setState({
                    defaultValue: val, //确定后将列表里的数据赋值给表单数据
                    visible: false
                });
                onSave(val);
            } else {
                this.setState({
                    defaultValue: ModalData, //确定后将列表里的数据赋值给表单数据
                    visible: false
                });
                onSave(ModalData);
            }
        });
    };

    //浮层按钮点击取消或者点击X
    handleCancel = e => {
        let { onCancel } = this.state;
        this.setState({
            visible: false
        });
        onCancel();
    };

    //节点被点击时
    nodeClick(select,nodeInfo) {
        let { label,value,type } = this.k; //, children
        let { ModalData,selectType,maxNumber } = this.state;
        let {
            node: {
                props: { eventKey,title,type: nodeType },
                props
            }
        } = nodeInfo;
        if (this.props.nodeClick) {
            let nodeClickInfo = this.props.nodeClick(nodeInfo);
            if (nodeClickInfo && nodeClickInfo.canSelect === false) {
                return;
            }
        }

        //当前是否被选中 如果ModalData里面有就是被选中状态 没有就是未选中状态
        let checked = false;

        //取label 因为children有两所以这么取 （一个label和一个对勾）
        let _child0 = "";
        if (title.props.children[0]) {
            if (title.props.children[0].props) {
                _child0 = title.props.children[0].props.children;
            }
        }
        let _o = props["data-item"] ? JSON.parse(props["data-item"]) : {};
        let obj = {
            ..._o,
            [label]: _child0,
            [value]: eventKey,
            [type]: nodeType
        };
        //如果有有匹配成功的话就是选中状态
        for (let i = 0; i < ModalData.length; i++) {
            if (ModalData[i][value] === obj[value]) {
                checked = true;
            }
        }
        let setDate = (checked,ModalData) => {
            if (checked) {
                this.selectedListDel(obj[value]);
            } else {
                //等于了最大值就不让选了
                if (ModalData.length >= maxNumber) {
                    message.error(this.textObj.maxNumber);
                } else {
                    ModalData.push(obj);
                    this.setState({
                        ModalData
                    });
                }
            }
        };

        //selectType  0部门和人员都能选 1只能选部门 2只能选人员
        if (selectType === "0") {
            //所有的都能选
            //选中状态应该被移除 不是选中状态则添加
            setDate(checked,ModalData);
        } else if (selectType === "1") {
            //只能选部门
            if (obj[type] === "1") {
                //点击的是部门才有反应
                setDate(checked,ModalData);
            }
        } else if (selectType === "2") {
            //只能选人员
            if (obj[type] === "2") {
                //点击的是部门才有反应
                setDate(checked,ModalData);
            }
        } else {
            return;
        }
    }

    //渲染子节点 传入的data一定是个数组
    renderTreeNodes(data = []) {
        let { label,value,type,children } = this.k;
        let { ModalData,selectType,collect = [],useCollect } = this.state;
        //selectType === '0' 不用管
        //selectType === '1' 加上pullperson-blueImg类名将部门节点变成蓝色
        //selectType === '2' 加上pullperson-blueImg类名将人员节点变成蓝色
        //搜索的列表数据每一项都带有search属性值为true  强制没有子节点 因为搜索结果是个列表
        if (data && data.length) {
            return data.map(item => {
                const _strItem = JSON.stringify(item);
                //判断这条数据是否被选中
                let _checked = false;
                for (let i = 0; i < ModalData.length; i++) {
                    if (item[value] === ModalData[i][value]) {
                        _checked = true;
                    }
                }

                //是否被收藏
                //是否是收藏人员
                let isCollect = false;
                collect.map((collectItem,index) => {
                    let _val = collectItem[value];
                    if (_val === item[value] && !isCollect) {
                        isCollect = true;
                    }
                    return item;
                });

                if (item.search) {
                    //说明只有一级数据是个搜索列表
                    return (
                        <TreeNode
                            className={`${
                                item[type] === "2" ? "pull-person-perNode" : ""
                                } myNode  ${
                                selectType === "0"
                                    ? "pullperson-blueImg"
                                    : selectType === "2"
                                        ? "pullperson-blueImg"
                                        : ""
                                }`}
                            title={
                                <span className={"pullperson-titleCon"}>
                                    <span
                                        className={"pullperson-titleCon-label"}
                                    >
                                        {item[label]}
                                    </span>
                                    {useCollect ? (
                                        <span
                                            className={`pullperson-titleCon-collect ${
                                                isCollect ? "blue" : ""
                                                }`}
                                            onClick={e => {
                                                e.stopPropagation();
                                                if (isCollect) {
                                                    this.collectDel(item);
                                                } else {
                                                    this.collectAdd(item);
                                                }
                                            }}
                                        >
                                            ♡
                                        </span>
                                    ) : null}
                                    <span
                                        // className={styles.checkedGou}
                                        className={"pullperson-checkedGou"}
                                        ref={item[value]}
                                    >
                                        {_checked ? "√" : null}
                                    </span>
                                </span>
                            }
                            key={item[value]}
                            type={item[type]}
                            data-item={_strItem}
                        />
                    );
                }

                if (item[type] === "2") {
                    //人员节点
                    //没有子节点
                    return (
                        <TreeNode
                            className={`pull-person-perNode myNode  pullperson-blueImg`}
                            title={
                                <span className={"pullperson-titleCon"}>
                                    <span
                                        className={"pullperson-titleCon-label"}
                                    >
                                        {item[label]}
                                    </span>
                                    {useCollect ? (
                                        <span
                                            className={`pullperson-titleCon-collect ${
                                                isCollect ? "blue" : ""
                                                }`}
                                            onClick={e => {
                                                e.stopPropagation();
                                                if (isCollect) {
                                                    this.collectDel(item);
                                                } else {
                                                    this.collectAdd(item);
                                                }
                                            }}
                                        >
                                            ♡
                                        </span>
                                    ) : null}
                                    <span
                                        className={"pullperson-checkedGou"}
                                        ref={item[value]}
                                    >
                                        {_checked ? "√" : null}
                                    </span>
                                </span>
                            }
                            key={item[value]}
                            type={item[type]}
                            data-item={_strItem}
                        />
                    );
                } else {
                    //部门节点或者根节点
                    //有子节点
                    return (
                        <TreeNode
                            className={`myNode  ${
                                selectType === "0"
                                    ? "pullperson-blueImg"
                                    : selectType === "1"
                                        ? "pullperson-blueImg"
                                        : ""
                                }`}
                            type={item[type]}
                            title={
                                <span className={"pullperson-titleCon"}>
                                    <span
                                        className={"pullperson-titleCon-label"}
                                    >
                                        {item[label]}
                                    </span>
                                    <span
                                        className={"pullperson-checkedGou"}
                                        ref={item[value]}
                                    >
                                        {_checked ? "√" : null}
                                    </span>
                                </span>
                            }
                            key={item[value]}
                            data-item={_strItem}
                        >
                            {this.renderTreeNodes(item[children])}
                        </TreeNode>
                    );
                }
            });
        }
    }

    //传入数据渲染出树
    renderTree(data) {
        if (data) {
            let { defaultCheckedKeys = [] } = this.state;
            let { label,value,children } = this.k;
            return (
                <Tree
                    showIcon={true}
                    defaultExpandedKeys={[...defaultCheckedKeys,"collect"]}
                    checkStrictly={true} //父子节点不在关联
                    onSelect={(selected,e) => {
                        this.nodeClick(selected,e);
                    }}
                >
                    {Array.isArray(data) ? (
                        //如果根节点是数组走这(根节点不只一个)
                        this.renderTreeNodes(data)
                    ) : (
                            //根节点只有一个走这
                            <TreeNode
                                title={data[label]}
                                key={data[value]}
                                disabled
                                disableCheckbox
                            >
                                {data[children]
                                    ? this.renderTreeNodes(data[children])
                                    : ""}
                            </TreeNode>
                        )}
                </Tree>
            );
        }
    }

    //删除确认框 并且会执行删除操作 (删除的是表单里的的数据)
    delConfirm(delValue) {
        let { value } = this.k;
        let { defaultValue } = this.state;
        for (let i = 0; i < defaultValue.length; i++) {
            if (defaultValue[i][value] === delValue) {
                //删除该人员或者部门
                defaultValue.splice(i,1);
                //
                this.valueChange(defaultValue,val => {
                    if (val) {
                        //val存在的话说明this.transform存在
                        this.setState({
                            defaultValue: val
                        });
                    } else {
                        this.setState({
                            defaultValue
                        });
                    }
                });
                return;
            }
        }
    }

    //删除选择列表里面的数据 （弹层里面的）
    selectedListDel(delValue) {
        //传入id
        let { value } = this.k;
        let { ModalData } = this.state; //, defaultCheckedKeys

        for (let i = 0; i < ModalData.length; i++) {
            if (ModalData[i][value] === delValue) {
                //删除该人员或者部门
                ModalData.splice(i,1);
                this.setState({
                    ModalData
                });
                return;
            }
        }
    }

    //获取收藏的数据
    getCollect = treeData => {
        if (this.props.useCollect) {
            this.myFetch(this.props.collectApi,{}).then(
                ({ success,message,data }) => {
                    if (success && data) {
                        let { label,value,type,children } = this.k;
                        if (treeData && !Array.isArray(treeData)) {
                            //确保根节点只有一个
                            this.setState({
                                loading: false,
                                collect: data,
                                treeData: [
                                    treeData,
                                    {
                                        [label]: "我的收藏",
                                        [value]: "collect",
                                        [type]: "0",
                                        [children]: data
                                    }
                                ]
                            });
                        } else {
                            let { treeData = [] } = this.state;
                            if (Array.isArray(treeData)) {
                                treeData = treeData.map(item => {
                                    if (item[value] === "collect") {
                                        item[children] = data;
                                    }
                                    return item;
                                });
                                this.setState({
                                    collect: data,
                                    treeData
                                });
                            } else {
                                this.setState({
                                    collect: data,
                                    treeData: [
                                        treeData,
                                        {
                                            [label]: "我的收藏",
                                            [value]: "collect",
                                            [type]: "0",
                                            [children]: data
                                        }
                                    ]
                                });
                            }
                        }
                    } else {
                        this.setState({
                            loading: false,
                            treeData
                        });
                        Msg.error(message);
                    }
                }
            );
        }
    };

    //添加收藏
    collectAdd = item => {
        if (this.props.collectApiByAdd) {
            this.myFetch(this.props.collectApiByAdd,[item]).then(
                ({ message,success }) => {
                    if (success) {
                        Msg.success(message);
                        this.getCollect();
                    } else {
                        Msg.error(message);
                    }
                }
            );
        }
    };

    //删除收藏
    collectDel = item => {
        if (this.props.collectApiByDel) {
            this.myFetch(this.props.collectApiByDel,[item]).then(
                ({ message,success }) => {
                    if (success) {
                        Msg.success(message);
                        this.getCollect();
                    } else {
                        Msg.error(message);
                    }
                }
            );
        }
    };

    //搜索时
    searchChange(e) {
        let val = e.target.value;
        let { treeData } = this.state;
        clearTimeout(window.searchTimer);
        window.searchTimer = setTimeout(() => {
            let _apiName = this.searchApi || this.fetchConfig.apiName;
            if (_apiName && this.myFetch) {
                if (treeData) {
                    //执行搜索
                    this.setState({ searchLoading: true });
                    this.myFetch(_apiName,{
                        [this.searchParamsKey]: val,
                        ...this.searchOtherParams
                    }).then(({ data,message,success }) => {
                        if (success && data) {
                            if (Array.isArray(data)) {
                                //没有搜索参数说明需要请求的跟节点（需要渲染子节点）
                                if (val) {
                                    data.map((item,index) => {
                                        return (item.search = true);
                                    });
                                }

                                //备份树结构数据
                                this.treeData = treeData;
                                this.setState({
                                    searchLoading: false,
                                    treeData: data
                                });
                            } else {
                                //备份树结构数据
                                this.treeData = treeData;
                                this.setState(
                                    {

                                        treeData: data ? [data] : treeData,
                                        searchLoading: false
                                    },
                                    () => {
                                        this.getCollect(data);
                                    }
                                );
                            }
                        }
                    });
                } else {
                    //将树结构数据还原
                    this.setState({
                        treeData: this.treeData
                    });
                }
            } else {
                console.error("请传入：searchApi属性和myFetch属性");
            }
        },300);
    }

    render() {
        let {
            edit,
            defaultValue,
            visible,
            ModalData,
            treeData,
            loading,
            search
        } = this.state;
        let { label,value,type } = this.k;
        return (
            <div className={styles.PullPersion}>
                <div className={styles.label}>{this.label}</div>
                <div className={styles.valueCon}>
                    <Row gutter={8}>
                        {!defaultValue || defaultValue.length === 0 ? (
                            //编辑状态要是没有数据显示【添加】按钮，非编辑状态下显示【-】
                            <span className={styles.addBtn}>
                                {edit ? (
                                    <span onClick={this.addBtn.bind(this)}>
                                        添加
                                    </span>
                                ) : (
                                        <span style={{ color: "#ccc" }}>-</span>
                                    )}
                            </span>
                        ) : (
                                defaultValue &&
                                defaultValue.map((item,i) => {
                                    return (
                                        // <Col
                                        //     key={i}
                                        //     span={6}
                                        //     md={6}
                                        //     sm={8}
                                        //     xs={12}
                                        //     xxl={6}
                                        // >
                                        <div
                                            key={i}
                                            className={`${styles.colCon} ${
                                                item[type] === "2"
                                                    ? styles.persion
                                                    : styles.folder
                                                }`}
                                        >
                                            <Tooltip title={item[label]}>
                                                <span>{item[label]}</span>
                                            </Tooltip>
                                            {/* 删除按钮 */}
                                            <span
                                                style={{
                                                    display: !edit ? "none" : ""
                                                }}
                                                className={styles.del}
                                            >
                                                <Popconfirm
                                                    title="确定删除吗?"
                                                    onConfirm={this.delConfirm.bind(
                                                        this,
                                                        item[value]
                                                    )}
                                                    okText="确定"
                                                    cancelText="取消"
                                                >
                                                    ×
                                            </Popconfirm>
                                            </span>
                                        </div>
                                        // </Col>
                                    );
                                })
                            )}

                        {//当选择的数据大于0 并且是编辑状态的时候在选择的数据的后面显示添加按钮
                            defaultValue && defaultValue.length > 0 && edit ? (
                                <div
                                    style={{ display: !edit ? "none" : "" }}
                                    className={styles.addBtn}
                                    onClick={this.addBtn.bind(this)}
                                >
                                    添加
                            </div>
                            ) : (
                                    // <Col span={6} md={6} sm={8} xs={12} xxl={4}>
                                    //     <span
                                    //         style={{ display: !edit ? "none" : "" }}
                                    //         className={styles.addBtn}
                                    //         onClick={this.addBtn.bind(this)}
                                    //     >
                                    //         添加
                                    //     </span>
                                    // </Col>
                                    <div />
                                )}
                    </Row>
                </div>

                {/* 选人的弹层 */}
                {visible ? (
                    <div>
                        <Modal
                            title={this.title}
                            visible={visible}
                            onOk={this.handleOk}
                            onCancel={this.handleCancel}
                            maskClosable={false} //点击蒙层不能退出
                            keyboard={false} //不能esc退出
                            width="700px"
                            destroyOnClose={true}
                            afterClose={() => {
                                this.setState({
                                    treeData: {}
                                });
                            }}
                        >
                            {loading ? (
                                <div className="example">
                                    <Spin />
                                    <center
                                        style={{
                                            color: "#999",
                                            fontSize: "12px"
                                        }}
                                    >
                                        {this.textObj.loading}
                                    </center>
                                </div>
                            ) : (
                                    <div className="pull-person-modalBody">
                                        <div className={styles.treeContainer}>
                                            {/* 执行renderTree进行树结构的渲染 */}
                                            {this.search || search ? (
                                                <div
                                                    className={
                                                        styles.searchContainer
                                                    }
                                                >
                                                    <Search
                                                        style={{ marginBottom: 8 }}
                                                        placeholder={
                                                            this
                                                                .searchPlaceholder ||
                                                            "请输入搜索条件..."
                                                        }
                                                        onChange={this.searchChange}
                                                    />
                                                </div>
                                            ) : null}

                                            <div className={styles.treeDom}>
                                                <Spin
                                                    spinning={
                                                        this.state.searchLoading
                                                    }
                                                >
                                                    {treeData
                                                        ? this.renderTree(treeData)
                                                        : this.textObj.noData}
                                                    {Array.isArray(treeData) &&
                                                        !treeData.length ? (
                                                            <center>
                                                                {this.textObj.noData}
                                                            </center>
                                                        ) : null}
                                                </Spin>
                                            </div>
                                        </div>
                                        <div className={styles.selectedContainer}>
                                            <span
                                                className={
                                                    styles.selectedContainerTitle
                                                }
                                            >
                                                {this.textObj.rightTitle}
                                            </span>
                                            {ModalData.map((item,i) => {
                                                return (
                                                    <div
                                                        key={i}
                                                        className={`${
                                                            item[type] === "2"
                                                                ? styles.persion
                                                                : styles.folder
                                                            }  ${styles.selectList}`}
                                                    >
                                                        <Row
                                                            type="flex"
                                                            align="middle"
                                                            justify="space-between"
                                                        >
                                                            <Col>{item[label]}</Col>
                                                            <Col>
                                                                <span
                                                                    onClick={this.selectedListDel.bind(
                                                                        this,
                                                                        item[value]
                                                                    )}
                                                                    className={
                                                                        styles.selectedListDel
                                                                    }
                                                                >
                                                                    ×
                                                            </span>
                                                            </Col>
                                                        </Row>
                                                    </div>
                                                );
                                            })}
                                        </div>
                                    </div>
                                )}
                        </Modal>
                    </div>
                ) : null}
            </div>
        );
    }
}
