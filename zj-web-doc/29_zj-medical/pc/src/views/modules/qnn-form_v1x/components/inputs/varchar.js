//字符串 、数字
//清空组件值不可从props传入空进行更新 必须调用组件clearValue方法进行更新

import React,{ Component } from "react";
import {
    Input,
    Form,
} from "../../lib";
const FormItem = Form.Item;

class VarCharComponent extends Component {

    static getDerivedStateFromProps(nextProps,prevState) {
        let newState = {
            ...prevState,
            inpIng: false, //设置为不是正在输入的状态 
        }

        //不是输入的数据
        if (!prevState.inpIng && nextProps.rcFormParams.initialValue) {
            newState.value = nextProps.rcFormParams.initialValue;
        }
        return newState;
    }

    state = {
        value: this.props.rcFormParams.initialValue ? this.props.rcFormParams.initialValue : null,
    }

    componentDidMount() {
        this.setValue = this.setValue.bind(this);
        this.getValue = this.getValue.bind(this);
        this.clearValue = this.clearValue.bind(this);
    }

    setValue(newValue) {
        let { value } = this.state;

        //防止意外更新
        if (value !== newValue) {
            this.setState({
                value: newValue,
                inpIng: true
            })
        }
    }

    getValue() {
        let { value } = this.state;
        return value;
    }

    clearValue() {
        this.setState({
            value: null,
        })
    }

    onChange = (e) => {
        this.setValue(e.target.value);
    }

    render() {
        const { field,commProps,inputProps } = this.props;
        const { value } = this.state;

        return (<FormItem {...commProps}>
            <Input
                {...inputProps}
                id={field}
                value={value}
                onChange={this.onChange}
            />
        </FormItem>)
    }
}

export default VarCharComponent;