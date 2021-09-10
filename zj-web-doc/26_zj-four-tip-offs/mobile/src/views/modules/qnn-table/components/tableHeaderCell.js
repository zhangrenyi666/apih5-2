import React from "react";
import { Form } from "antd";
import getTableHeaderInput from "../func/getTableHeaderInput";
import moment from "moment";
// const EditableContext = React.createContext();
const EditableRowByHeader = ({ form,index,...props }) => {  
    return (
        // <EditableContext.Provider value={form}>
        <tr {...props}/>
        // </EditableContext.Provider>
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
            fieldsConfig = {},
            optionConfig,
            optionData,
            fetchCb,
            dataIndex,
            title,
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
            ...restProps
        } = this.props;
        // console.log(form)
        /* 排除掉多选框  操作列 */
        record.firstRowIsSearch = firstRowIsSearch;
        restProps.className = `${restProps.className} w-ant-th ${
            firstRowIsSearch ? " w-ant-th-firstRowIsSearch" : ""
            }`;
        return (
            <th ref={node => (this.cell = node)} {...restProps}>
                {restProps.children}

                {/* 排除掉多选框  操作列  firstRowIsSearch存在*/}
                {restProps.className !== "ant-table-selection-column" &&
                    dataIndex &&
                    firstRowIsSearch &&
                    !btns.length ? (
                        <div className={firstRowIsSearch ? "w-th-inp" : ""}>
                            {this.getTableHeaderInput(
                                {
                                    type: "string",
                                    field: `${dataIndex}`,
                                    ...fieldsConfig
                                },
                                record
                            )}
                             
                        </div>
                    ) : (
                        <div
                            className={` ${
                                firstRowIsSearch ||
                                    restProps.className.indexOf(
                                        "ant-table-selection-column"
                                    ) !== -1
                                    ? "w-th-inp"
                                    : ""
                                }`}
                        />
                    )}
            </th>
        );
    }
}
export { EditableCellByHeader,EditableFormRowByHeader };
