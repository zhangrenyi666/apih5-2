import React, { Component } from "react";
import { basic } from "../modules/layouts";
import s from './style.less';
class index extends Component {
    constructor(){
        super();
        this.state = {
            url:''
        }
    }
    componentDidMount() {
        const {
            myFetch
        } = this.props;
        myFetch("initZjPrProgrammeConfirmList",{}).then(({
            success,
            data
        }) => {
            if (success) {
                this.setState({
                    url:data
                })
            }
        })
    }
    render() {
        return (
            <div className={s.root}>
                <iframe width='100%' height='100%' frameBorder={0} src={this.state.url}></iframe>
            </div>
        );
    }
}

export default basic(index);