import React, { Component } from 'react';
import QnnForm from "../modules/qnn-table/qnn-form";
import { NavBar, Picker,List,Icon } from 'antd-mobile';
import { message as Msg, Input, Modal } from 'antd'
import { push } from 'react-router-redux';
import s from './style.less';
const config = {
    formConfig: [
        {
            type: "string",
            label: "主键ID",
            required: true,
            field: "nameId",
            hide: true
        },
        {
            label: '姓名',
            field: 'name',
            disabled: true,
            type: 'string'
        },
        {
            label: '部门',
            field: 'unit',
            disabled: true,
            type: 'string'
        },

        {
            label: '金额',
            field: 'money',
            disabled: true,
            type: 'string'
        },
        {
            label: '用户名',
            disabled: true,
            field: 'userName',
            type: 'string'
        },
        {
            label: '密码',
            field: 'password',
            disabled: true,
            type: 'string'
        }
    ],
};
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            changeActionText: '',
            yearId: props.match.params.yearId ? props.match.params.yearId : '',
            PickDate: [],
            showFlag:true
        };
    }
    componentDidMount() {
        
        const { myFetch } = this.props;
        myFetch('getZjGoodsYearList', { }).then(({ data, success, message }) => {
            if (success) {
                if (data.length) {
                    this.setState({
                        showFlag:false
                    })
                    let PickDateol = [];
                    for (var i = 0; i < data.length;i++){
                        PickDateol.push({
                            label:data[i].year,
                            value:data[i].yearId
                        })
                        if (this.state.yearId === data[i].yearId) {
                            this.setState({
                                changeActionText: data[i].year,
                                yearId:this.state.yearId
                            })
                        }
                    }
                    this.setState({
                        PickDate: PickDateol
                    }, () => {
                        this.setState({
                            showFlag:true
                        })
                    })
                }
            } else {
                Msg.error(message)
            }
        });
    }
    onOk = (val) => {
        if (this.state.PickDate.length>0) {
            this.setState({
                showFlag:false
            })
            for (var j = 0; j < this.state.PickDate.length; j++){
                if (val[0] === this.state.PickDate[j].value) {
                    this.setState({
                        changeActionText: this.state.PickDate[j].label,
                        yearId:this.state.PickDate[j].value
                    }, () => {
                            this.setState({
                                showFlag:true
                            })
                    })
                }
            }
        }
        
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div className={s.top}>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(push(`${mainModule}Add`));
                    }}
                >
                    {"个人信息"}
                </NavBar>
                    <Picker
                        data={this.state.PickDate}
                        cols={1}
                        value={''}
                        onOk={this.onOk}
                    >
                        <List.Item arrow="horizontal">{this.state.changeActionText+'年'}</List.Item>
                    </Picker>

                </div>
                <div style={{ height: '100%', paddingTop: "100px", overflow: 'hidden scroll' }}>
                    {this.state.showFlag ?　<QnnForm
                        {...this.props}
                        myPublic={this.props.myPublic}
                        form={this.props.form}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{
                            token: this.props.loginAndLogoutInfo.loginInfo.token
                        }}
                        {...config}
                        formItemLayout={{
                            labelCol: {
                                xs: { span: 24 },
                                sm: { span: 4 }
                            },
                            wrapperCol: {
                                xs: { span: 24 },
                                sm: { span: 20 }
                            }
                        }}
                        fetchConfig={{
                            apiName: "getZjGoodsDetails",
                            otherParams: {
                                yearId: this.state.yearId
                            }
                        }}
                        btns={[]}
                    /> :''}
                </div>
            </div>
        )
    }
}
export default Index;
