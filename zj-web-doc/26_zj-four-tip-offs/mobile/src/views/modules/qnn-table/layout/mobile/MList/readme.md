### 上拉刷新下拉加载简单组件

    使用 import MyList from XXX

    <MyList 
        myFetch={this.props.myFetch} //ajax方法必须返回一个promise
        searchKey="voteTitle"  //搜索时的key
        fetchConfig={{ //ajax配置
            apiName:'getVoteListByVoterMobile', //后台api
            otherParams:{} //参数
        }}
        Item={(props)=>{ //列表模板 props里有所有数据
            //jsx
            console.log(props)
            return <div>123</div>
        }}
    />