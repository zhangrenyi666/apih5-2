import React from "react";
import createTableInput from "../func/getTableInput";
import { Form } from "antd";
const EditableContext = React.createContext();
const EditableRow = ({ form,index,...props }) => (
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
            document.addEventListener("click",this.handleClickOutside,true);
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
        this.setState({ editing,type },() => {
            if (editing) {
                this.input.focus();
            }
        });
    };

    handleClickOutside = e => {
        const { editing,type } = this.state;
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

    save = (e,obj = {}) => {
        const { record,handleSave,fieldsConfig = {} } = this.props;
        this.form.validateFields((error,values) => {
            if (error) {
                return;
            }
            this.toggleEdit(fieldsConfig);
            handleSave(
                //新行值，旧行值，其他信息或者方法
                { ...record,...values,...obj },
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
            isRenderCheckBox,
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
                                this.createInput(fieldsConfig,record)
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

export { EditableFormRow,EditableCell };
