import React,{ useState,useEffect } from "react";
import {
    Form,
    Select,
    message as Msg,
    Divider,
    Pagination,
    Spin
} from "antd";
import s from "./selectByPaging.less"
const FormItem = Form.Item;
const { Option } = Select;

const SelectByPaging = (props) => {

    //定时器
    let SelectByPagingTimer;

    const { field,commProps,rcFormParams,inputProps = {},
        form: { getFieldDecorator,getFieldValue },
        fetchConfig = {},optionConfig = {},
        placeholder,myFetch,pageConfig = {},match,
        setState,selectKey,
        qnnFormIsMounted
    } = props;
    const keys = {
        value: "value",
        label: "label",
        ...optionConfig
    };
    const _pageConfig = {
        limit: 10,page: 1,
        ...pageConfig
    }

    const [loading,setLoading] = useState(false);
    const [optionData,setOptionData] = useState([]);
    const [limit] = useState(_pageConfig.limit);
    const [page,setPage] = useState(_pageConfig.page);
    const [totalNumber,setTotalNumber] = useState(0);
    const [searchText,setSearchText] = useState(null);
    const [open,setOpen] = useState(false);

    //是否第一次触礁  用于设置回显问题
    const [firstFouses,setFirstFouses] = useState(true);

    //组件实例化对象
    const [selectObj,setSelectObj] = useState(null);

    //分页改变后
    useEffect(() => {
        fetchData();
    },[page]);

    //打开获取关闭弹窗时候
    useEffect(() => {
        //关闭状态
        if (!open && selectObj) {
            //手动调用失去焦点 
            selectObj.rcSelect.onOuterBlur();
        }
    },[open]);

    //搜索文字改变后去搜索
    useEffect(() => {
        fetchData();
    },[searchText]);

    //回显值处理方法
    useEffect(() => {
        // isMonted = true;
        let fieldValue = getFieldValue(field);
        if (optionData.length && fieldValue && firstFouses) {
            let vObj = optionData.filter(item => item[keys.value] === fieldValue);
            if (!vObj.length) {
                //说明设置的值在下拉选中查询不到  需要去请求 
                fetchData({ [keys.value]: fieldValue });
                setFirstFouses(false);
            }
        }

        // return () => { 
        //     clearTimeout(SelectByPagingTimer);
        //     isMonted = false;
        // }
    });

    //输入后的回调
    function handleSearch(val) {
        if (SelectByPagingTimer) {
            clearTimeout(SelectByPagingTimer);
        }
        SelectByPagingTimer = setTimeout(() => {
            //搜索文字后需要重置分页
            setPage(1);
            setSearchText(val);
        },200)
    }

    //触礁后需要请求数据和打开弹窗
    function onFocus() {
        //这里有联动情况所以需要每次触礁都请求 
        if (!open) {
            setOpen(true);
            fetchData();
        }
    }

    function onBlur() {
        //点击分页时候实际上就失去了焦点但是弹窗还是打开状态的  所以要从新触礁
        if (open) {
            selectObj.rcSelect.onPopupFocus();
        }
    }

    function showTotal(total) {
        return `共查询到 ${total} 条数据`;
    }

    function clearSearchText() {
        setPage(1);
        setSearchText(null);
    }

    //分页切换
    function paginationChange(page) {
        setPage(page);
    }

    function onSelect(val) {
        //关闭弹窗
        setOpen(false);
    }

    //请求数据的方法
    //_oParams用于去请求特定的一条数据 例如：回显时候显示第二页的值 这时候在在下拉选项中是没有第二页的数据的
    function fetchData(_oParams) {
        const { apiName,params = {},otherParams,searchKey = "search" } = fetchConfig;
        let _params = {};
        if (params) {
            const _urlParams = match.params;
            for (const key in params) {
                if (params.hasOwnProperty(key)) {
                    const field = params[key];
                    _params[key] = getFieldValue(field) || _urlParams[field];
                }
            }
        }
        let _body = {
            [searchKey]: searchText,
            limit: limit,
            page: page,
            ..._params,
            ...otherParams
        }

        //请求特定值
        if (_oParams) {
            _body = {
                limit: limit,
                page: 0,
                ..._oParams
            }
        }
        if (!qnnFormIsMounted) {
            return;
        }
        setLoading(true);
        myFetch(apiName,_body).then(
            ({
                data,
                success,
                message,
                totalNumber
            }) => {
                setLoading(false);
                if (success) {

                    //将下拉选项设置到最外层的state中  因为有时候做双向绑定时候用到等    
                    if (qnnFormIsMounted) {
                        setOptionData(data);
                        setTotalNumber(totalNumber);
                        setState({
                            [selectKey]: data
                        },() => {
                            if (_oParams) {
                                //用于处理回显问题
                                rcFormParams.onChange(getFieldValue(field),data);
                            }
                        })
                    }

                } else {
                    Msg.error(message);
                }
            }
        );
    }

    const paginationDom = (<div
        onClick={(e) => e.stopPropagation()}
        className={s.paginationDom}>
        <div className={s.searchTextConn}>
            {searchText ? <span>关键词：<a className={s.searchText}>{searchText}</a> <a className={s.clearSearchTextBtn} onClick={clearSearchText}>清除</a></span> : null}
        </div>
        <div className={`${s.pagination}  qnn-form-pagination`}>
            <Pagination
                current={page}
                onChange={paginationChange}
                pageSize={limit}
                size="small"
                total={totalNumber}
                showTotal={showTotal} />
        </div>
    </div>);

    const options = optionData.map(d => <Option key={d[keys.value]}>{d[keys.label]}</Option>);

    if (loading && !open) {
        inputProps.disabled = true;
    }

    return (<FormItem {...commProps}>
        {getFieldDecorator(field,{
            ...rcFormParams
        })(
            <Select
                allowClear
                showSearch
                placeholder={placeholder}
                style={{
                    width: "100%"
                }}
                defaultActiveFirstOption={false}
                filterOption={false}
                searchText={123}
                onSearch={handleSearch}
                {...inputProps}
                loading={loading}
                onFocus={onFocus}
                onBlur={onBlur}
                getPopupContainer={_ => {
                    //寻找从当前节点出发第一个QnnForm的表单块
                    let meDom = document.getElementById(field);
                    const getFirstQnnFormDom = (dom) => {
                        let domParent = dom.parentNode;
                        if (domParent) {
                            let _class = domParent.className;
                            if (_class.indexOf('QnnForm') !== -1) {
                                return domParent;
                            } else {
                                return getFirstQnnFormDom(domParent);
                            }
                        }
                    }
                    return getFirstQnnFormDom(meDom)
                }}
                dropdownRender={menu => (
                    <div
                        //这里的事件和下面的时间共用的一个定时器必须这样不能注释
                        onMouseEnter={() => window.selectDrawTimer && clearTimeout(window.selectDrawTimer)}
                        onMouseLeave={() => { window.selectDrawTimer && clearTimeout(window.selectDrawTimer); window.selectDrawTimer = setTimeout(() => setOpen(false),300) }}
                    >
                        <Spin spinning={loading}>
                            <div className={s.selectContainer}>
                                <div className={s.optionItemsCon} onClick={(e) => e.stopPropagation()}>{menu}</div>
                                <div className={s.bottom}>
                                    <Divider style={{ margin: '4px 0' }} />
                                    <div className={`${s.bottomPageCon}`}>
                                        {paginationDom}
                                    </div>
                                </div>
                            </div>
                        </Spin>
                    </div>
                )}
                onSelect={onSelect}
                open={open}
                ref={(me) => {
                    if (me && !selectObj) {
                        setSelectObj(me)
                    }
                }}
                //这里的事件和上面的时间共用的一个定时器必须这样不能注释
                onMouseEnter={() => window.selectDrawTimer && clearTimeout(window.selectDrawTimer)}
                onMouseLeave={() => { window.selectDrawTimer && clearTimeout(window.selectDrawTimer); window.selectDrawTimer = setTimeout(() => setOpen(false),300) }}
            >
                {options}
            </Select>
        )
        }
    </FormItem >)
}

export default SelectByPaging;