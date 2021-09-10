//打开选人的方法
const openTree = function (obj = {}) {
    this.setState({
        pullpersonField: obj.field || "pullPerson",
        pullpersonChangeFn: obj.pullpersonChangeFn || function () { },
        openTree: obj.openTree === false ? obj.openTree : true,
        treeOption: obj
    });
};

//关闭右边抽屉
const closeDrawer = function (show = false,cb = () => { }) {
    // console.log(show) 
    const { MyComponent } = this.state;
    this.setState(
        {
            DrawerShow: show,
            MyComponent: MyComponent,
            //关闭抽屉后必须去掉form的是否更改过
            formIsChangeedAlertText:null,
            formIsChangeed:false,
        },
        cb
    );
    //延时清空  不然在关闭瞬间将会看到表单
    if (!show) {
        setTimeout(() => {
            this.setState({
                MyComponent: null,
                maskClosable: true,
                formGlobalDisabledStatus:false, //关闭抽屉一定要将抽屉全局禁用去掉 
            });
        },0);
    }
};

//设置选中数据
const setSelectedRows = function (selectedRows) {
    if (!Array.isArray(selectedRows)) {
        console.error('setSelectedRows方法必须传入一个数组')
        return;
    }
    this.setState({
        selectedRows
    });
}

const selectKey = field => `${field}_optionData`;
const isMobile = function () {
    return this.getDeviceType() === "mobile";
};

//设置按钮loading或者禁用方法
//(string 增加或者删除, string 要操作的数据, 设置loading状态或者禁用状态)
const setBtnsLoading = function (action,field,type = "loading") {

    let { loadingBtnsByField = [],disabledBtnsByField = [] } = this.state;

    if (type === "loading") {
        let loadingBtnsByFieldLen = loadingBtnsByField.length;
        if (action === 'add') {
            //新增直接添加即可
            loadingBtnsByField.push(field);
        } else if (action === 'remove') {
            if (loadingBtnsByFieldLen) {
                //删除时只有一个按钮确认是那个按钮直接删除即可
                if (loadingBtnsByFieldLen === 1) {
                    if (field === loadingBtnsByField[0]) {
                        loadingBtnsByField = [];
                    }
                } else {
                    //多个时需要确认好是哪个按钮
                    let _index = null;
                    loadingBtnsByField.forEach((element,index) => {
                        if (element === field) {
                            _index = index;
                        }
                    });
                    loadingBtnsByField.splice(_index,1);
                }
            }
        }
  
        this.setState({
            loadingBtnsByField
        })
    } else if (type === 'disabled') {
        let disabledBtnsByFieldLen = disabledBtnsByField.length;
        if (action === 'add') {
            //新增直接添加即可
            disabledBtnsByField.push(field);
        } else if (action === 'remove') {
            if (disabledBtnsByFieldLen) {
                //删除时只有一个按钮确认是那个按钮直接删除即可
                if (disabledBtnsByFieldLen === 1) {
                    if (field === disabledBtnsByField[0]) {
                        disabledBtnsByField = [];
                    }
                } else {
                    //多个时需要确认好是哪个按钮
                    let _index = null;
                    disabledBtnsByField.forEach((element,index) => {
                        if (element === field) {
                            _index = index;
                        }
                    });
                    disabledBtnsByField.splice(_index,1);
                }
            }
        }
        this.setState({
            disabledBtnsByField
        })
    }

};


export { openTree,closeDrawer,selectKey,isMobile,setSelectedRows,setBtnsLoading };
