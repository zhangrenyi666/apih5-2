import React from "react";
import { Table,message as Msg } from "antd";
import {
    EditableCellByHeader,
    EditableFormRowByHeader
} from "./tableHeaderCell";
import { EditableFormRow,EditableCell } from "./tableCell";

const index = function () {
    const {
        data,
        loading,
        paginationConfig,
        totalNumber,
        curPage,
        limit,
        antd,
        columns,
        isShowRowSelect,
        actionBtnsPosition,
        tableContentWidth,
        disabled,
        firstRowIsSearch,
        selectedRows = [],
        diyTableRow
    } = this.state;
    let clickCb = {
        selectedRows: selectedRows,
        btnCallbackFn: this.btnCallbackFn,
        _formData: this.props.form.getFieldsValue(),
        props: this.props,
        state: this.state
    };
    this.handleSave = (
        newRowData,
        oldRowData,
        { editField,tdEditCb,tdEditFetchConfig,fetchCb }
    ) => {
        const newData = [...data];
        const index = newData.findIndex(item => newRowData.key === item.key);
        const item = newData[index];
        newData.splice(index,1,{
            ...item,
            ...newRowData
        });
        const cbPrams = {
            btnCallbackFn: this.btnCallbackFn,
            state: this.state,
            props: this.props,
            newRowData: newRowData, //新的行数据
            newTableData: newData, //新的table数据
            oldRowData: oldRowData, //旧的行数据
            oldTableData: data, //新的table数据
            editField: editField //被修改的字段
        };
        //编辑后的回调
        if (tdEditCb && !tdEditFetchConfig) {
            tdEditCb(cbPrams);
        } else if (
            tdEditFetchConfig &&
            oldRowData[editField] !== newRowData[editField]
        ) {
            let { apiName,otherParams = {},params = {} } = tdEditFetchConfig;
            if (!apiName) {
                console.error("fetchConfig必须配置apiName属性");
                return;
            }
            let _params = {};
            const urlParams = this.props.match.params;
            for (const key in params) {
                _params[key] = urlParams[params[key]];
            }

            if (typeof otherParams === "function") {
                otherParams = otherParams({
                    ...this.props,
                    fetch: this.fetch,
                    btnCallbackFn: this.btnCallbackFn
                });
            }

            if (typeof apiName === "function") {
                apiName = apiName({
                    ...this.props,
                    fetch: this.fetch,
                    btnCallbackFn: this.btnCallbackFn
                });
            }
            //直接去请求更新接口
            Msg.loading("请稍等...");
            const body = {
                ...newRowData,
                ..._params,
                ...otherParams
            };
            this.fetch(apiName,body).then(({ data,success,message }) => {
                Msg.destroy();
                if (success) {
                    this.refresh();
                    Msg.success(message);
                    this.closeDrawer(false);
                    if (fetchCb) {
                        fetchCb({ data,success,message,...clickCb });
                    }
                } else {
                    Msg.error(message);
                }
            });
        }
    };

    this.firstRowSearch = values => {
        let { searchParams = {} } = this.state;
        searchParams = {
            ...searchParams,
            ...values
        };
        this.setState({ searchParams },this.refresh);
    };
    const _columns = columns.map((col,index) => {

        //获取头部单元格的一些数据方法
        //需要处理表头分组
        const getHeaderCell = (col) => {
            // console.log(col.title[0].props.children[0].props.dangerouslySetInnerHTML)
            // console.log(col)
            return (record) => {
                return {
                    record,
                    col:{
                        ...col
                    }, 
                    btns: record.btns,
                    Pprops: this.props,
                    firstRowSearch: this.firstRowSearch,
                    firstRowIsSearch,
                    setOptionData: obj => {
                        this.setState({
                            ...obj
                        });
                    },
                    Pstate: this.state
                }
            };
        }

        //获取单元格的一些数据方法 
        const getCell = record => {
            if ((typeof col.tdEdit) === 'function') {
                col.tdEdit = col.tdEdit(record,col,{
                    ...this.props,
                    fetch: this.fetch,
                    btnCallbackFn: this.btnCallbackFn
                })
            }
            return {
                record,
                tdEdit: col.tdEdit,
                fieldsConfig: {
                    field: col.dataIndex,
                    ...col.fieldsConfig
                },
                tdEditCb: col.tdEditCb,
                tdEditFetchConfig: col.tdEditFetchConfig,
                fetchCb: col.fetchCb,
                dataIndex: col.dataIndex,
                title: col.title,
                handleSave: this.handleSave,
                Pprops: this.props,
                setOptionData: obj => {
                    this.setState({
                        ...obj
                    });
                },
                Pstate: this.state,
            }
        }

        //需做表头分组
        const getCellWrapper = col => {
            return (record) => {
                if ((typeof col.tdEdit) === 'function') {
                    col.tdEdit = col.tdEdit(record,col,{
                        ...this.props,
                        fetch: this.fetch,
                        btnCallbackFn: this.btnCallbackFn
                    })
                }
                return {
                    record,
                    tdEdit: col.tdEdit,
                    fieldsConfig: {
                        field: col.dataIndex,
                        ...col.fieldsConfig
                    },
                    tdEditCb: col.tdEditCb,
                    tdEditFetchConfig: col.tdEditFetchConfig,
                    fetchCb: col.fetchCb,
                    dataIndex: col.dataIndex,
                    title: col.title,
                    handleSave: this.handleSave,
                    Pprops: this.props,
                    setOptionData: obj => {
                        this.setState({
                            ...obj
                        });
                    },
                    Pstate: this.state
                }
            };
        };

        //可编辑的单元格
        if (!col.fieldsConfig) {
            //避免报错 设置默认的fieldsConfig
            col.fieldsConfig = {
                type: "string",
                field: col.dataIndex
            };
        }
 
        if (firstRowIsSearch && !col.fieldsConfig) {
            console.error(
                "formConfig项的table属性的tdEdit属性为true时必须配置一个fieldsConfig  ---来着qnn-table的警告"
            );
        }

        //需做表头分组
        //这里处理子集后好处是无需在下面处理了
        if (col.children && col.children.length) {
            let loopChildren = (data) => {
                return data.map(item => {
                    if (item.children && item.children.length) {
                        item.children = loopChildren(item.children);
                    }
                    return {
                        ...item,
                        onCell: record => ({ ...getCellWrapper(item)(record) }),
                        onHeaderCell: record => ({ ...getHeaderCell(item)(record) })
                    };
                });
            }
            col.children = loopChildren(col.children);
        }

        if (!col.tdEdit || disabled) {
            //不可编辑的单元格
            //在这一步时候也是会返回表头单元格的
            return {
                ...col,
                onHeaderCell: record => ({ ...getHeaderCell(col)(record) })
            };
        } else {
            // 单元格可被编辑            
            return {
                ...col,
                onCell: record => ({ ...getCell(record) }),
                onHeaderCell: record => ({ ...getHeaderCell(col)(record) })
            };
        }
    });

    //自定义table内容 
    const components = {
        header: {
            row: EditableFormRowByHeader,
            cell: EditableCellByHeader
        },
        body: {
            row: EditableFormRow,
            cell: EditableCell,
            wrapper: (props) => {
                return <tbody className={props.className}>{props.children.map(item => {
                    return (diyTableRow ? diyTableRow(item,{
                        ...this.props,
                        fetch: this.fetch,
                        btnCallbackFn: this.btnCallbackFn
                    }) : item);
                })}</tbody>
            }
        }
    };

    //设置滚动条
    if (tableContentWidth !== "100%" && tableContentWidth) {
        antd.scroll = {
            x: tableContentWidth + 68 //这个尺寸瞎写的，后期慢慢检查
        };
    }

    //没有数据的时候不显示复选框  否则会丑丑的
    let _isShowRowSelect = isShowRowSelect;
    if (!_columns.length) {
        _isShowRowSelect = false;
    }
    if (antd.title) {
        let _tit = antd.title;
        if ((typeof antd.title) === 'string') {
            antd.title = () => { return <div style={antd.titleStyle ? { ...antd.titleStyle } : {}}>{_tit}</div> };
        }
    } 

    return (
        <Table
            components={components}
            loading={loading}
            dataSource={data}
            columns={_columns}
            bordered={true}
            rowSelection={_isShowRowSelect ? this.rowSelection() : null}
            {...antd}
            footer={
                actionBtnsPosition === "bottom"
                    ? curPageData => {
                        return this.actionBtnsModel();
                    }
                    : null
            }
            pagination={
                paginationConfig === false
                    ? false
                    : {
                        position: "bottom",
                        current: curPage,
                        defaultCurrent: 1,
                        defaultPageSize: 10,
                        pageSize: limit,
                        total: totalNumber,
                        showTotal: total => {
                            return `共查询到 ${total} 条数据`;
                        },
                        onChange: this.paginationChange,
                        showSizeChanger: true,
                        onShowSizeChange: (current,pageSize) => {
                            this.setState(
                                {
                                    limit: pageSize
                                },
                                () => {
                                    this.refresh();
                                }
                            );
                        },
                        ...paginationConfig
                    }
            }
        />
    );
};

export default index;
