import React from "react";
import {
    Input,
    Form,
} from "../../lib";
const FormItem = Form.Item;

const VarCharComponent = (props) => {
    const { field,commProps,rcFormParams,inputProps,form: { getFieldDecorator } } = props;
    return (<FormItem {...commProps}>
        {getFieldDecorator(field,{
            ...rcFormParams,  
        })(<Input {...inputProps} />)}
    </FormItem>)
}



export default VarCharComponent;