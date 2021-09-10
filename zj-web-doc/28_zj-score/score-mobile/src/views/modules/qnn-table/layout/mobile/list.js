import React,{ Component } from 'react';
import MList from './MList/index'
import s from './list.less'
import { List,Flex,Icon,NavBar } from 'antd-mobile'; //,Toast
// import { Button } from 'antd'
import moment from "moment";
import QnnForm from "../../qnn-form";
// import { goBack,push } from 'connected-react-router';
import { actionBtnsByMobile } from '../../components'
const { Item } = List
const { Brief } = Item;
class list extends Component {

    static getDerivedStateFromProps(props,state) {
        let obj = {
            ...state,
            ...props
        };

        return obj;
    }


    constructor(...args) {
        super(...args);

        const _props = this.props;
        const { columns,title,layout } = _props;
        this.state = {
            //当前打开的数据
            openRowDate: {},
            //formBtns
            formBtns: [],

            columns,

            title,

            layout,
        }

        this.getLayout = this.getLayout.bind(this);
    }


    // componentDidMount() {
    // }
    componentDidUpdate() {
        // console.log('props',this.props)
        // this.props.form.setFieldsValue({
        //     ...this.state.openRowDate
        // })
        // console.log(this.state.openRowDate)

        //将按钮颜色变透明   
        document.ontouchmove = () => {
            if (this.refs.btnsContainer) {
                this.refs.btnsContainer.style.opacity = 0.3
            }
            
            // const listConDom = document.getElementsByClassName("am-list-view-scrollview")[0];
            // if(listConDom){
            //     console.log(listConDom)
            // }
        }
        document.ontouchend = () => {
            if (this.refs.btnsContainer) {
                this.refs.btnsContainer.style.opacity = 1
            }
        }
    }

    getText = (obj,item) => {
        if (obj === false || obj === null) {
            return ''
        }
        if ((typeof obj) === 'object') {
            if (obj.type === 'datetime') {
                return moment(item[obj.value]).format("YYYY-MM-DD HH:mm:ss")
            } else {
                console.error('未知类型， layout属性中有元素配置错误')
                return <div />
            }
        } else {
            return item[obj]
        }
    }

    getLayout(layout) {
        if (!layout) {

            //默认布局
            let _layout = [
                //頭部
                [null,null],
                //内容
                [null],
                //底部
                [null,null,null]
            ];

            const { columns } = this.state;

            //排除dataIndex:"action"的字段
            columns.filter((item) => {
                return item.dataIndex !== "action";
            }).forEach((element,index) => {
                let dataIndex = element.dataIndex;
                if (index < 2) {
                    _layout[0][index] = dataIndex;
                } else if (index < 3) {
                    _layout[1][index - 2] = dataIndex;
                } else if (index < 6) {
                    _layout[2][index - 3] = dataIndex;
                }
            });
            return _layout;
        } else {
            return layout;
        }
    }

    render() {
        const { openRowDate = {},formBtns = [] } = this.state;
        let _layout = this.state.layout;
        const {
            history,
            match: { params: { meta } },
            fetchConfig,
            mobileListItem = "style1",
            forms,
            formFetchConfig = {}
        } = this.props;
        const layout = this.getLayout(_layout); 

        //meta===detail || add || edit 为表单页面 
        if (!meta) {
            return <span>移动端使用qnn-table必须配置路由参数/:meta <br />eg:qnnTable/:meta</span>
        } 
        if (meta === 'detail' || meta === 'add') {
            //表单页面
            return <div className={s.formContainer}>
                <NavBar
                    mode="dark" 
                    icon={<Flex><Icon type="left" /><span style={{ fontSize: 15 }}>返回</span></Flex>}
                    onLeftClick={() => {
                        history.goBack();
                    }}
                ><span style={{ fontSize: 15 }}>{'详情'}</span></NavBar>
                <QnnForm
                    {...this.props}
                    fetchConfig={meta === 'add' ? {} : formFetchConfig}
                    data={meta === 'add' ? {} : openRowDate}
                    form={this.props.form}
                    fetch={this.props.fetch || this.props.myFetch}
                    headers={this.props.headers}
                    formConfig={[...forms.map(item => {
                        item.span = 24;
                        item.formItemLayout = {
                            labelCol: {
                                xs: {
                                    span: 6
                                }
                            },
                            wrapperCol: {
                                xs: {
                                    span: 18
                                }
                            }
                        }
                        return item;
                    })]}

                    style={{
                        height: window.innerHeight - 45,
                        overflow: "hidden",
                        // paddingBottom: meta === 'add' && formBtns.length ? 45 : 0,
                        background: "transparent"
                    }}

                    btns={meta === 'add' ? formBtns : []}
                />
            </div>
        } else {
            //列表页面 
            return (
                <div className={s.container}>
                    {/* 移动端列表 */}
                    <MList
                        {...this.props}
                        myFetch={this.props.myFetch} //ajax方法必须返回一个promise
                        searchKey="voteTitle"  //搜索时的key
                        fetchConfig={{ //ajax配置
                            ...fetchConfig
                        }}
                        Item={(props) => { //列表模板 props里有所有数据
                            //jsx 
                            let _Item = '';
                            if ((typeof mobileListItem) === 'function') {
                                _Item = mobileListItem(props)
                            } else { 
                                switch (mobileListItem) {
                                    case 'style1':
                                        const { rowData } = props;
                                        _Item = (<div className={s.item}>
                                            <Flex className={s.itemHeader}>
                                                <Flex.Item>{this.getText(layout[0][0],rowData)}</Flex.Item>
                                                <div style={{ textAlign: "right" }}>{this.getText(layout[0][1],rowData)}</div>
                                            </Flex>

                                            {
                                                layout[1][0] ? (<div className={s.itemBody}>
                                                    <Flex wrap="wrap" align="center" alignContent="center" style={{ color: "#666",fontSize: '13px' }}>
                                                        <Flex.Item>{this.getText(layout[1][0],rowData)}</Flex.Item>
                                                        <div className={s.itemBodyArr}>{
                                                            <Icon style={{ color: '#bbb',width: 25,height: 25 }} type={'right'} />
                                                        }</div>
                                                    </Flex>
                                                </div>) : null
                                            }


                                            {
                                                layout[2][0] ? (<Brief style={{ borderTop: '1px solid rgba(232, 232, 232, 0.575)' }}>
                                                    <Flex className={s.footer}>
                                                        <Flex.Item style={{ fontSize: '13px' }}>
                                                            <span style={{ marginRight: '10px' }}>{this.getText(layout[2][0],rowData)}</span>
                                                            {this.getText(layout[2][1],rowData)}
                                                        </Flex.Item>
                                                        <div style={{ textAlign: "right",fontSize: '13px' }}>{this.getText(layout[2][2],rowData)}</div>
                                                    </Flex>
                                                </Brief>) : null
                                            }

                                        </div>);
                                        break;
                                    default:
                                        _Item = (<div>没有该样式</div>);
                                        break;
                                }
                            }
                            return <div onClick={() => {
                                //打开详情页面 
                                this.setState({
                                    openRowDate: props.rowData
                                },() => {
                                    history.push('detail');
                                })
                            }}>{_Item}</div>
                        }}
                    />

                    {/* 移动端列表中的按钮 */}
                    {actionBtnsByMobile.bind(this,s)()}
                </div>
            );
        }

    }
}

export default list;