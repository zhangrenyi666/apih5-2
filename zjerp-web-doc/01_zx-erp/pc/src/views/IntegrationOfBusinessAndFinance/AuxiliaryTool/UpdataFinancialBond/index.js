import React, { Component } from "react";
import QnnForm from "../../../modules/qnn-form";
import { message as Msg, Button } from 'antd';

class index extends Component {
    render() {
        return (
            <div style={{ marginTop: '10%' }}>
                <div style={{ textAlign: 'center', marginBottom: 10, color: '#888' }}>
                    手动更新业财债务债权
                </div>
                <QnnForm
                    {...this.props}
                    fetch={this.props.myFetch}
                    wrappedComponentRef={(me) => { this.form = me }}
                    formConfig={[
                        {
                            label: '输入债务债权编号',
                            field: "contractNo",
                            type: 'string',
                            placeholder: '请输入',
                            span: 24,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 10 },
                                    sm: { span: 10 }
                                }
                            }
                        },
                        {
                            field: "diyButton",
                            type: 'Component',
                            Component: obj => {
                                return <Button
                                    style={{ background: 'rgb(24,144,255)', borderRadius: 3, color: '#fff', marginLeft: '49%', marginTop: 10 }}
                                    onClick={() => {
                                        this.form.form.setFieldsValue({ contractNo: null })
                                        Msg.success('债权债务信息不会立即更新，请20分钟后查看',3)
                                        // this.props.myFetch('', {})
                                        //     .then(({ success, message }) => {
                                        //         if (success) {
                                        //             this.form.form.setFieldsValue({ contractNo: null })
                                        //         } else {
                                        //             Msg.error(message)
                                        //         }
                                        //     })
                                    }}
                                >提交</Button>;
                            },
                            span: 24,
                        },
                    ]}
                />
            </div>

        )
    }
}

export default index;