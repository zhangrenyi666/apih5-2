import React from "react";
import { Form } from "antd";
import getTableHeaderInput from "../func/getTableHeaderInput";
import moment from "moment";
const EditableRowByHeader = ({ form,index,...props }) => {
    return (
        <tr {...props} />
    );
};

const EditableFormRowByHeader = Form.create()(EditableRowByHeader);

class EditableCellByHeader extends React.Component {
    constructor(...props) {
        super(...props);
        this.getTableHeaderInput = getTableHeaderInput.bind(this);
        this.onSearch = this.onSearch.bind(this);
    }


    onSearch = (e,obj = {}) => {
        const { firstRowSearch } = this.props;
        this.props.Pprops.form.validateFields((error,values) => {
            if (error) {
                return;
            }
            values = values.searchParams;
            let _params = {
                ...values,
                ...obj
            };
            delete values.searchParams;
            for (const key in _params) {
                if (_params.hasOwnProperty(key)) {
                    const element = _params[key];
                    if (moment.isMoment(element)) {
                        _params[key] = moment(element).valueOf();
                    }
                }
            }
            window.clearTimeout(window.firstRowSearchTimer);
            window.firstRowSearchTimer = window.setTimeout(() => {
                firstRowSearch(_params);
            },400);
        });
    };

    render() {
        const {
            optionConfig,
            optionData,
            fetchCb,
            record = {},
            index,
            Pprops,
            setOptionData,
            Pstate,
            btns = [],
            createInput,
            firstRowSearch,
            firstRowIsSearch,
            form,
            col = {},
            ...restProps
        } = this.props;
        const { noHaveSearchInput,dataIndex,fieldsConfig = {} } = col;
        /* 排除掉多选框  操作列 */
        record.firstRowIsSearch = firstRowIsSearch;
        restProps.className = `${restProps.className} w-ant-th ${
            firstRowIsSearch ? " w-ant-th-firstRowIsSearch" : ""
            }`;

        //空白的表头单元格 相当于占位符
        const blankHeaderCol = () => {
            return (<div
                className={` ${
                    firstRowIsSearch ||
                        restProps.className.indexOf(
                            "ant-table-selection-column"
                        ) !== -1
                        ? "w-th-inp"
                        : ""
                    }`}
            />)
        };

        //表头输入框
        //位置在这里必须是func
        const tableHeaderInput = () => {
            return (<div className={firstRowIsSearch ? "w-th-inp" : ""}>
                {this.getTableHeaderInput(
                    {
                        type: "string",
                        field: `${dataIndex}`,
                        ...fieldsConfig
                    },
                    record
                )}

            </div>)
        };

        //强制不加搜索框是需要将类名去掉
        if(noHaveSearchInput){
            restProps.className = restProps.className.replace(/(w-ant-th)/ig, '')
        }

        return (
            <th ref={node => (this.cell = node)} {...restProps}>
                {restProps.children}

                {/* 排除掉多选框  操作列  firstRowIsSearch存在*/}
                {((restProps.className !== "ant-table-selection-column") &&
                    dataIndex &&
                    firstRowIsSearch &&
                    !noHaveSearchInput &&
                    !btns.length) ? (tableHeaderInput()) : (noHaveSearchInput ? null : blankHeaderCol())}
            </th>
        );
    }
}
export { EditableCellByHeader,EditableFormRowByHeader };
