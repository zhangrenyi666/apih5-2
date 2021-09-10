import React, { Component } from "react";
import { NavBar, Icon } from "antd-mobile";
import { push } from 'react-router-redux';
import MyList from "../modules/MList";
import s from "./style.less";
import moment from 'moment';
class NewsPage extends Component {
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}App`));
                        }}
                    >
                        系统公告
                    </NavBar>
                    <div style={{height: window.innerHeight - 45}}>
                        <MyList
                            myFetch={this.props.myFetch}
                            upload={this.props.myUpload} //ajax方法必须返回一个promise
                            searchKey="title" //搜索时的key
                            fetchConfig={{//表格的ajax配置
                                apiName: 'getZjSjConsultNewsList'
                            }}
                            Item={props => {
                                //列表模板 props里有所有数据
                                const { rowData, rowID } = props;
                                const item = rowData;
                                const index = rowID;
                                return (
                                    <div
                                        className={s.center}
                                        key={index}
                                        onClick={() => {
                                            dispatch(push(`${mainModule}NewsDetail/${item.newsId}`));
                                        }}
                                    >
                                        <div className={s.top}>
                                            <div className={s.topl}>
                                                {item.title}
                                            </div>
                                            <div className={s.topr}>
                                                {moment(item.releaseTime).format('YYYY-MM-DD')}
                                            </div>
                                        </div>
                                    </div>
                                );
                            }}
                        />
                    </div>
                </div>
            </div>
        );
    }
}

export default NewsPage;
