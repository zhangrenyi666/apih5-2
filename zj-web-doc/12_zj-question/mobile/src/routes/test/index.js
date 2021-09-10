import React, { Component } from 'react';
import PullPersionMobile from './pullPersionMobile'
import { createForm } from 'rc-form';


class Test extends Component {
    constructor(props) {
        super(props);

        this.state = {
            num: 0
        }
    }

    onSubmit = (e) => {
        e.preventDefault();
        this.setState({
            num: this.state.num += 1
        })

        this.props.form.validateFields((error, value) => {
            console.log(error, value);
        });
    };


    render() {
        const { form } = this.props;
        const { getFieldDecorator, getFieldProps } = form;

        return (
            <div>
                <div onSubmit={this.onSubmit}>
                    {
                        getFieldDecorator('MyFrom', {
                            valuePropName: 'defaultValue',
                            onChange: (v) => {
                                console.log('aaa', v)
                            }
                        })(
                            <PullPersionMobile
                                // help
                                // routeControl
                                // meUrl = 'test'
                                ref={(me) => {
                                    if (me) {
                                        this.pullPerson = me;
                                    }
                                }}
                                treeData={[
                                    {
                                        label: '测试',
                                        value: '0',
                                        type: '2'
                                    }
                                ]}
                            />
                        )
                    }

                    <button onClick={this.onSubmit}>获取数据 {this.state.num}</button>
                </div>
            </div>
        )
    }
}

export default createForm()(Test);

