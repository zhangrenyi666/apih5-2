import React, { isValidElement } from "react";
import { Menu, Dropdown, Icon, Divider, Tooltip, Button, message as Msg } from "antd";
import moment from "moment";
import s from "../style.less";
import getSearchInput from "./getSearchInput";
const formConfigClass = function() {
    const {
        formConfig = [],
        columns = [],
        forms,
        searchFroms,
        labelConfig = {},
        searchFormColNum
    } = this.state; 
    let _tableContentWidth = 0;
    for (let i = 0; i < formConfig.length; i++) {
        let _item = { ...formConfig[i] };
        let {
            isInSearch,
            isInTable = true,
            isInForm = true,
            table = {},
            form = {},
            showType = "bubble"
        } = _item;
        let {
            tooltip = false,
            dataIndex,
            defaultValue = "",
            type = "",
            imgStyle = {},
            render,
            onClick,
            fetchConfig,
            optionData = [],
            optionConfig,
            drawerTitle,
            filter,
            willExecute
        } = table;
        form.field = form.field ? form.field : table.dataIndex;
        form.label = form.label ? form.label : table.title;
        //主要给table使用的变量
        let _type = type;
        let _fetchConfig = fetchConfig || form.fetchConfig || {};
        let _field = dataIndex || form.field;
        let _optionData = form.optionData || optionData;
        let _optionConfig = optionConfig ||
            form.optionConfig || {
                value: "value",
                label: "label",
                children: "children"
            };

        //矫正showType位置
        showType = table.showType || showType;

        //需要将render里面的字符串html取出来然后用html渲染
        if (render) {
            table.render = (data, rows) => {
                data = data ? data : defaultValue;
                const _realData = render(data, rows);
                if (isValidElement(_realData)) {
                    return _realData;
                }
                if (_realData) {
                    if (typeof _realData !== "object") {
                        return (
                            <span
                                dangerouslySetInnerHTML={{ __html: _realData }}
                            />
                        );
                    } else {
                        let _str =
                            _realData.props.dangerouslySetInnerHTML.__html;
                        return (
                            <span dangerouslySetInnerHTML={{ __html: _str }} />
                        );
                    }
                }
            };
        }
        if (isInTable && typeof isInTable === "function") {
            isInTable = isInTable({ ...this.props });
        }
        //所有在table中显示的字段
        if (isInTable) {
            if (table.btns) {
                //按钮
                //btns类型需要特殊处理
                table.render = (data, rows) => {
                    let row = { ...rows };
                    let domArr = [];
                    if (showType === "tile") {
                        for (let i = 0; i < table.btns.length; i++) {
                            let { render, label } = table.btns[i];
                            domArr.push(
                                <span
                                    onClick={this.action.bind(
                                        this,
                                        {
                                            ...this.props,
                                            btnCallbackFn: this.btnCallbackFn,
                                            ...table.btns[i]
                                        },
                                        row
                                    )}
                                    key={i}
                                >
                                    {render ? (
                                        <a
                                            dangerouslySetInnerHTML={{
                                                __html: render({
                                                    rowData: row,
                                                    btnCallbackFn: this
                                                        .btnCallbackFn,
                                                    ...table.btns[i],
                                                    ...this.props
                                                })
                                            }}
                                        />
                                    ) : (
                                        label
                                    )}
                                    {i === table.btns.length - 1 ? null : (
                                        <Divider type="vertical" />
                                    )}
                                </span>
                            );
                        }
                        return (
                            <div style={{ textAlign: "center" }}>{domArr}</div>
                        );
                    } else if (showType === "bubble") {
                        for (let i = 0; i < table.btns.length; i++) {
                            let { render, label } = table.btns[i];
                            domArr.push(
                                <Menu.Item
                                    style={{
                                        minWidth: "80px",
                                        textAlign: "center"
                                    }}
                                    onClick={this.action.bind(
                                        this,
                                        {
                                            ...this.props,
                                            btnCallbackFn: this.btnCallbackFn,
                                            ...table.btns[i]
                                        },
                                        row
                                    )}
                                    key={i}
                                >
                                    {render ? (
                                        <a
                                            dangerouslySetInnerHTML={{
                                                __html: render({
                                                    rowData: row,
                                                    btnCallbackFn: this
                                                        .btnCallbackFn,
                                                    ...table.btns[i],
                                                    ...this.props
                                                })
                                            }}
                                        />
                                    ) : (
                                        label
                                    )}
                                </Menu.Item>
                            );
                        }
                        return (
                            <Dropdown overlay={<Menu>{domArr}</Menu>}>
                                <center>
                                    <a>
                                        {labelConfig.actionBtn || "操作"}
                                        <Icon type="down" />
                                    </a>
                                </center>
                            </Dropdown>
                        );
                    }
                };
            }
            //处理别的render方法
            if (tooltip) {
                table.render = data => {
                    return (
                        <Tooltip title={data}>
                            <span>
                                {data && data.substr(0, tooltip)}
                                {data && data.length > tooltip ? "..." : null}
                            </span>
                        </Tooltip>
                    );
                };
            }

            //特殊类型
            if (type === "images") {
                table.render = data => {
                    let url = "";
                    if (Array.isArray(data) && data[0]) {
                        url = data[0].url;
                    } else {
                        url = data;
                    }
                    return <img width="50px" {...imgStyle} src={url} alt="" />;
                };
            } else if (_type === "select") {
                //类型为下拉时自动去匹配下拉选项里的值  暂时select类型的table不支持render属性
                const _sk = this.selectKey(_field);
                if (render) {
                    console.error(
                        "暂不支持select类型的table单元格设置render属性  ---来自qnn-table的警告"
                    );
                }
                if (onClick) {
                    console.error(
                        "暂不支持select类型的table单元格设置onClock属性  ---来自qnn-table的警告"
                    );
                }
                if (_fetchConfig.apiName) {
                    _fetchConfig.otherParams = _fetchConfig.otherParams || {};
                    _fetchConfig.params = _fetchConfig.params || {};
                    //需要去请求下拉选项
                    let _params = {};
                    const urlParams = this.props.match.params;
                    for (const key in _fetchConfig.params) {
                        _params[key] = urlParams[_fetchConfig.params[key]];
                    }
                    table.render = tdData => {
                        if (!this.state[_sk]) {
                            this.fetch(_fetchConfig.apiName, {
                                ..._fetchConfig.otherParams,
                                ..._params
                            }).then(({ success, data }) => {
                                if (success) {
                                    this.setState({
                                        [_sk]: data
                                    });
                                }
                            });
                        }

                        let _arr = this.state[_sk];
                        let _val =
                            _arr &&
                            _arr.filter(item => {
                                return item[_optionConfig.value] === tdData;
                            });
                        let _v =
                            _val && _val[0]
                                ? _val[0][_optionConfig.label]
                                : defaultValue;
                        return _v;
                    };
                } else {
                    table.render = data => {
                        let _val = _optionData.filter(item => {
                            return item[_optionConfig.value] === data;
                        });
                        let _v = _val[0]
                            ? _val[0][_optionConfig.label]
                            : defaultValue;
                        return _v;
                    };
                }
            }

            //只格式化时间
            if (table.format) {
                table.render = data => {
                    if (data) {
                        return moment(data).format(table.format);
                    } else {
                        return defaultValue;
                    }
                };
            }

            //点击事件
            if (onClick) {
                table.render = (data, rows) => {
                    data = data ? data : defaultValue;
                    const _realData = render ? render(data, rows) : data;
                    const _click = () => {
                        if (typeof onClick === "function") {
                            onClick({
                                props: this.props,
                                btnCallbackFn: this.btnCallbackFn,
                                data,
                                rowData: rows,
                                formConfig,
                                drawerTitle
                            });
                        } else {
                            this.colClick({
                                onClick,
                                fetchConfig,
                                formConfig,
                                table,
                                rows,
                                rowData: rows,
                                drawerTitle,
                                data,
                                willExecute
                            });
                        }
                    };
                    let _dom = null;
                    if (_realData) {
                        if (isValidElement(_realData)) {
                            let _dom = React.cloneElement(_realData, {
                                onClick: () => {
                                    _click();
                                }
                            });

                            return _dom;
                        } else {
                            if (typeof _realData !== "object") {
                                _dom = (
                                    <span
                                        dangerouslySetInnerHTML={{
                                            __html: _realData
                                        }}
                                    />
                                );
                            } else {
                                let _str =
                                    _realData.props.dangerouslySetInnerHTML
                                        .__html;
                                _dom = (
                                    <span
                                        dangerouslySetInnerHTML={{
                                            __html: _str
                                        }}
                                    />
                                );
                            }
                        }
                    }
                    return (
                        <a
                            onClick={() => {
                                _click();
                            }}
                        >
                            {_dom}
                        </a>
                    );
                };
            }

            //需要将表格内容的宽算出来
            _tableContentWidth += table.width ? table.width : 100;
            //将表头使用html渲染
            if (table.title) {
                if (typeof table.title === "string") {
                    table.title = (
                        <span
                            dangerouslySetInnerHTML={{ __html: table.title }}
                        />
                    );
                } else {
                    let _str = table.title.props.dangerouslySetInnerHTML.__html;
                    table.title = (
                        <span dangerouslySetInnerHTML={{ __html: _str }} />
                    );
                }
            }

            const colSearch = (dataIndex, selectedKeys, confirm) => {
                const { searchParams } = this.state;
                if (selectedKeys && selectedKeys.length !== 0) {
                    searchParams[dataIndex] = selectedKeys;
                    this.setState(
                        {
                            searchParams
                        },
                        () => {
                            this.refresh(() => {
                                confirm();
                            });
                        }
                    );
                }else{
                    Msg.error("请输入关键词")
                } 
            };

            const colSearchClear = (dataIndex, clearFilters) => {
                const { searchParams } = this.state; 
                searchParams[dataIndex] = "";
                this.setState(
                    {
                        searchParams
                    },
                    () => {
                        this.refresh(() => {
                            clearFilters();
                        });
                    }
                );
            };

            const getColumnSearchProps = _item => {
                const { table = {}, form = {} } = _item;
                let dataIndex = form["field"] || table["dataIndex"];
                let type = table["type"] || form["type"];
                let title = form["label"];
                let optionData = form["optionData"];
                let fetchConfig = form["fetchConfig"];
                let optionConfig = form["optionConfig"];
                return {
                    filterDropdown: ({
                        setSelectedKeys,
                        selectedKeys,
                        confirm,
                        clearFilters
                    }) => {
                        return (
                            <div className={s["custom-filter-dropdown"]}>
                                {getSearchInput.bind(
                                    this,
                                    type,
                                    title,
                                    dataIndex,
                                    optionData,
                                    fetchConfig,
                                    optionConfig,
                                    colSearch,
                                    setSelectedKeys,
                                    selectedKeys,
                                    confirm
                                )()}
                                <Button
                                    onClick={() => {
                                        colSearchClear(dataIndex, clearFilters);
                                    }}
                                    size="small"
                                    style={{ width: 90, marginRight: 8 }}
                                >
                                    重置
                                </Button>
                                <Button
                                    type="primary"
                                    onClick={() => {
                                        colSearch(
                                            dataIndex,
                                            selectedKeys,
                                            confirm
                                        );
                                    }}
                                    icon="search"
                                    size="small"
                                    style={{ width: 90 }}
                                >
                                    搜索
                                </Button>
                            </div>
                        );
                    },
                    filterIcon: filtered => (
                        <Icon
                            type="search"
                            style={{ color: filtered ? "#1890ff" : undefined }}
                        />
                    ),
                    // onFilter: (value, record) => {
                    //   console.log(value)
                    // },
                    onFilterDropdownVisibleChange: visible => {
                        if (visible) {
                            if (type === "string") {
                                setTimeout(() => this.searchInput.select());
                            }
                        }
                    }
                };
            };

            //设置单列过滤  -----loading
            if (filter) {
                table = {
                    ...table,
                    ...getColumnSearchProps(_item)
                };
            }

            columns.push(table);
        }

        if (isInForm && typeof isInForm === "function") {
            isInForm = isInForm({ ...this.props });
        }

        if (isInForm) {
            let _form = { ...form };
            _form.formItemLayout = form.formItemLayoutForm
                ? form.formItemLayoutForm
                : null;
            _form.span = form.spanForm ? form.spanForm : 24;
            _form.offset = form.offsetForm ? form.offsetForm : 0;
            if (form.span || form.offset) {
                console.error(
                    "qnn-form中才可配置span|offset，在qnn—table只能配置spanForm | offsetForm"
                );
            }
            forms.push(_form);
        }

        if (isInSearch && typeof isInSearch === "function") {
            isInSearch = isInSearch({ ...this.props });
        }

        if (isInSearch) {
            let _span = searchFormColNum ? 24 / searchFormColNum : "";
            let _f = { ...form };
            _f.formItemLayout = form.formItemLayoutSearch
                ? form.formItemLayoutSearch
                : null;
            _f.span = form.spanSearch ? form.spanSearch : _span ? _span : 8;
            _f.offset = form.offsetSearch ? form.offsetSearch : 0;
            _f.required = form.searchRequied ? form.searchRequied : false;
            _f.field = `search__${form.field}`;
            _f.help = ""; //删除出掉帮助信息
            if (_f.type === "string" || _f.type === "number") {
                //非下拉回车直接搜索
                _f.onPressEnter = e => {
                    const { searchParams } = this.state;
                    searchParams[form.field] = e.target.value;
                    this.setState(
                        {
                            searchParams
                        },
                        () => {
                            this.refresh();
                        }
                    );
                };
            }
            searchFroms.push(_f);
        }
    }

    this.setState({
        columns, //这是isInTable
        forms,
        searchFroms,
        tableContentWidth: columns.length > 7 ? _tableContentWidth : "100%",
        isNeedClassifyData:false
    });
};

export default formConfigClass;
