import React from "react";
import {
    Input,
    Form,
} from "../../lib";
const FormItem = Form.Item; 
class VarCharComponent extends React.Component { 

    render() {
        const { field,commProps,rcFormParams,inputProps,form: { getFieldDecorator } } = this.props;
  
        return (<FormItem {...commProps}>
            {getFieldDecorator(field,{
                ...rcFormParams,
            })(<Input  {...inputProps} />)}
        </FormItem>)
    }
}


export default VarCharComponent;