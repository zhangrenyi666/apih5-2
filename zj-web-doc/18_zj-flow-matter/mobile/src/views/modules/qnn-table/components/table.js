import React from "react";
import { Table, Form, message as Msg } from "antd";
import createTableInput from "../func/getTableInput";
const EditableContext = React.createContext();

const EditableRow = ({ form, index, ...props }) => (
    <EditableContext.Provider value={form}>
        <tr {...props} />
    </EditableContext.Provider>
);

const EditableFormRow = Form.create()(EditableRow);

class EditableCell extends React.Component {
    constructor(...props) {
        super(...props);
        this.state = {
            editing: false
        }; 
        this.createInput = createTableInput.bind(this);
    }

    componentDidMount() {
        if (this.props.tdEdit) {
            document.addEventListener("click", this.handleClickOutside, true);
        }
    }

    componentWillUnmount() {
        if (this.props.tdEdit) {
            document.removeEventListener(
                "click",
                this.handleClickOutside,
                true
            );
        }
    }

    toggleEdit = (fieldsConfig = {}) => {
        const { type } = fieldsConfig;
        const editing = !this.state.editing;
        this.setState({ editing, type }, () => {
            if (editing) {
                this.input.focus();
            }
        });
    };

    handleClickOutside = e => {
        const { editing, type } = this.state;
        switch (type) {
            case "string":
            case "number":
                if (
                    editing &&
                    this.cell !== e.target &&
                    !this.cell.contains(e.target)
                ) {
                    this.save();
                }
                break;
            case "datetime":
            case "select":
                break;
            default:
                break;
        }
    };

    save = (e, obj = {}) => {
        const { record, handleSave, fieldsConfig = {} } = this.props;
        this.form.validateFields((error, values) => {
            if (error) {
                return;
            }
            this.toggleEdit(fieldsConfig);
            handleSave(
                //新行值，旧行值，其他信息或者方法
                { ...record, ...values, ...obj },
                { ...record },
                {
                    editField: this.props.dataIndex,
                    tdEditCb: this.props.tdEditCb,
                    tdEditFetchConfig: this.props.tdEditFetchConfig,
                    fetchCb: this.props.fetchCb
                }
            );
        });
    };

    render() {
        const { editing } = this.state;
        const {
            tdEdit,
            fieldsConfig,
            tdEditCb,
            tdEditFetchConfig,
            optionConfig,
            optionData,
            fetchCb,
            dataIndex,
            title,
            record,
            index,
            handleSave,
            Pprops,
            setOptionData,
            Pstate,
            ...restProps
        } = this.props; 
        return (
            <td
                ref={node => (this.cell = node)}
                {...restProps}
                className={`w-qnn-table-editableCell ${restProps.className}`}
            >
                {tdEdit ? (
                    <EditableContext.Consumer>
                        {form => {
                            this.form = form;
                            return editing ? (
                                this.createInput(fieldsConfig, record)
                            ) : (
                                <div
                                    className="tdEdit-cell-value-wrap"
                                    onClick={this.toggleEdit.bind(
                                        this,
                                        fieldsConfig
                                    )}
                                >
                                    {restProps.children}
                                </div>
                            );
                        }}
                    </EditableContext.Consumer>
                ) : (
                    restProps.children
                )}
            </td>
        );
    }
}

const index = function() {
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
    } = this.state; 
    let clickCb = {
        selectedRows: this.state.selectedRows,
        btnCallbackFn: this.btnCallbackFn,
        _formData: this.props.form.getFieldsValue(),
        props: this.props,
        state: this.state
    }; 
    this.handleSave = (
        newRowData,
        oldRowData,
        { editField, tdEditCb, tdEditFetchConfig, fetchCb }
    ) => {
        const newData = [...data];
        const index = newData.findIndex(item => newRowData.key === item.key);
        const item = newData[index];
        newData.splice(index, 1, {
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
            let { apiName, otherParams = {}, params = {} } = tdEditFetchConfig;
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
            this.fetch(apiName, body).then(({ data, success, message }) => {
                Msg.destroy();
                if (success) {
                    this.refresh();
                    Msg.success(message);
                    this.closeDrawer(false);
                    if (fetchCb) {
                        fetchCb({ data, success, message, ...clickCb });
                    }
                } else {
                    Msg.error(message);
                }
            });
        }
    };

    const _columns = columns.map(col => {
        //不可编辑的单元格 
        if (!col.tdEdit || disabled) {
            return col;
        }
        //可编辑的单元格
        if (!col.fieldsConfig) {
            console.error(
                "formConfig项的table属性的tdEdit属性为true时必须配置一个fieldsConfig  ---来着qnn-table的警告"
            );

            //避免报错 设置默认的fieldsConfig
            col.fieldsConfig = {
                type: "string",
                field: col.dataIndex
            };
        }

        return {
            ...col,
            onCell: record => ({
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
            })
        };
    });

    //自定义table内容
    const components = {
        body: {
            row: EditableFormRow,
            cell: EditableCell
        }
    };

    //设置滚动条
    if (tableContentWidth !== "100%" && tableContentWidth) {
        antd.scroll = {
            x: tableContentWidth
        };
    } 
    //没有数据的时候不显示复选框  否则会丑丑的
    let _isShowRowSelect = isShowRowSelect;
    if(!_columns.length){ _isShowRowSelect = false };
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
                actionBtnsPosition==="bottom"
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
                          onShowSizeChange: (current, pageSize) => {
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
