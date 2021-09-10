import React, { Component } from 'react';
import { List, Switch, Picker, TextareaItem, ImagePicker, InputItem, WhiteSpace, WingBlank, Button, Toast } from 'antd-mobile';
import { createForm } from 'rc-form';
import { TurnoverExamineDetailData } from '../tableConfig'
// import Image from '../common/image'
import { getWxJsSdk, MyFetch, getUserInfo } from '../../main'; //, getUserInfo
import './style.less';
const Item = List.Item;

class TurnoverExamineDetail extends Component {
    constructor(props) {
        super(props);
        document.getElementsByTagName('title')[0].innerHTML = '出入机房审批详情'
        this.approvalId = this.props.match.params.approvalId;
        this.flag = this.props.match.params.flag;
        let flag;
        if (this.flag === '0' || this.flag === '1' || this.flag === '2') {//判断浏览器网址上的flag符不符合标准
            flag = this.flag
        } else {
            flag = false;
        }
        this.state = {
            flag: flag,//标识 如果浏览器网址传过来的不是no就取浏览器上的 反之取后台传过来的
            files: [],
            person: [],
            selectPerson: [],//指定人
            initValue: {},//初始化表单数据值
            ...TurnoverExamineDetailData
        };
    }

    componentDidMount() {
        //getJfApprovalDetail 获取详情接口 
        getUserInfo(({ userInfo }) => {
            let userId = userInfo.userId;
            // let userId = 'chengxu_test';
            console.log('params:',{ approvalId: this.approvalId, userId: userId }) 
            MyFetch('getJfApprovalDetail', { approvalId: this.approvalId, userId: userId }, ({ success, data, message }) => {
                console.log('getJfApprovalDetail:', data)
                if (!success) { //如果没有数据直接退出
                    return;
                }
                // data.applyUnitDate = 1234569873254;//fakeData;
                let flag = data.flag ? data.flag : false;//
                // console.log(flag);
                this.setState({
                    applyUserId: data.applyUserId,
                    approvalType: data.approvalType,
                    flag: flag ? flag : this.state.flag,
                    initValue: data || {},
                    detailFlag: data.detailFlag,// 0:只能查看，1：可以修改
                    files: data ? (data.imageList || []) : [],

                    //三个 审批 时间
                    applyUnitDate: data.applyUnitDate ? data.applyUnitDate : new Date().getTime(),//申请单位审批时间
                    imDepartmentDate: data.imDepartmentDate ? data.imDepartmentDate : new Date().getTime(),//局信息话部门审批时间
                    imDepartmentLeaderDate: data.imDepartmentLeaderDate ? data.imDepartmentLeaderDate : new Date().getTime()//局信息化领导审批时间
                })
                let tableData = this.state.tableData;

                // ------------下面三个用于三个意见的显隐  1隐藏  0显示
                if (data.applyUnitFlag !== '0') {//审批单位未审批  
                    for (let j = 0; j < tableData.length; j++) {
                        if (tableData[j].name === 'applyUnitResult' || tableData[j].name === 'applyUnitOpinion') {//隐藏审批单位结果和意见
                            tableData[j].isHide = true
                        }
                    }
                } else {
                    for (let j = 0; j < tableData.length; j++) {
                        if (tableData[j].name === 'applyUnitResult' || tableData[j].name === 'applyUnitOpinion') {//隐藏审批单位结果和意见
                            tableData[j].isHide = false
                        }
                    }
                }

                if (data.imDepartmentLeaderFlag !== '0') {// 
                    for (let j = 0; j < tableData.length; j++) {
                        if (tableData[j].name === 'imDepartmentLeaderResult' || tableData[j].name === 'imDepartmentLeaderOpinion') { 
                            tableData[j].isHide = true
                        }
                    }
                } else {
                    for (let j = 0; j < tableData.length; j++) {
                        if (tableData[j].name === 'imDepartmentLeaderResult' || tableData[j].name === 'imDepartmentLeaderOpinion') { 
                            tableData[j].isHide = false
                        }
                    }
                }

                if (data.imDepartmentFlag !== '0') {
                    for (let j = 0; j < tableData.length; j++) {
                        if (tableData[j].name === 'imDepartmentResult' || tableData[j].name === 'imDepartmentOpinion') { 
                            tableData[j].isHide = true
                        }
                    }
                }else {
                    for (let j = 0; j < tableData.length; j++) {
                        if (tableData[j].name === 'imDepartmentResult' || tableData[j].name === 'imDepartmentOpinion') { 
                            tableData[j].isHide = false
                        }
                    }
                } 

                this.setState({
                    tableData
                })

            })

        })

    }

    onChangePerson = (person) => {//改变问询对象
        this.setState({ person });
    }

    formatData(date) {//传入时间戳自动格式化时间 返回格式2018/03/01 12:30
        if (date) { 
            //24小时制
            let myDate = new Date(date);
            let _date = myDate.getFullYear() + "-" + sup(myDate.getMonth()+1) + "-" + sup(myDate.getDate());
            let _time = sup(myDate.getHours()) + ":" + sup(myDate.getMinutes()) + ":" + sup(myDate.getSeconds());
            function sup(n) { return (n < 10) ? '0' + n : n; }
            return _date + ' ' + _time; 
        } else {
            return date;
        }
    }

    sendBtn() {
        let { flag, applyUserId } = this.state;
        this.props.form.validateFields((error, value) => {//获取表单数据 
            let params = Object.assign(value);//提交的参数
            if (params.approvalResult) {
                params.approvalResult = '0'
            } else {
                params.approvalResult = '1'
            }

            let newParams = {
                approvalId: this.approvalId,
                flag: flag,
                applyUserId,
                approvalResult: params.approvalResult,
                approvalOpinion: params.approvalOpinion,
                approvalDate: new Date().getTime(),//审批日期
            }

            console.log('newParams', newParams);
            MyFetch('addZjJfApply', newParams, (res) => {
                if (res.success) {
                    this.props.history.push('/page/TurnoverExamineList')
                } else {
                    Toast.fail(res.message)
                }
            })
        });
    }

    onChangeImg = (files, type, index) => {
        // console.log(files, type, index);
        const me = this;
        getWxJsSdk(({ wx, ready, error }) => {
            if (ready && !error) {
                wx.chooseImage({
                    count: 1, // 默认9
                    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                    success: function (res) {
                        const localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                        wx.uploadImage({
                            localId: localIds[0], // 需要上传的图片的本地ID，由chooseImage接口获得
                            isShowProgressTips: 1, // 默认为1，显示进度提示
                            success: function (res2) {
                                const serverId = res2.serverId; // 返回图片的服务器端ID
                                var files = [];
                                for (const item of me.state.files) {
                                    files.push(item)
                                }
                                if (window.__wxjs_is_wkwebview) {
                                    wx.getLocalImgData({
                                        localId: localIds[0], // 图片的localID
                                        success: function (res3) {
                                            const localData = res3.localData;// localData是图片的base64数据，可以用img标签显示
                                            files.push({
                                                url: localData,
                                                serverId
                                            })
                                            me.setState({ files });
                                        }
                                    });
                                } else {
                                    files.push({
                                        url: localIds[0],
                                        serverId
                                    })
                                    me.setState({ files });
                                }
                            }
                        });
                    }
                });
            }
        })
    }
    onImageClick = (index, files) => { //查看图片
        var fileImg = [];
        for (const item of files) {
            fileImg.push(item.url);
        }
        getWxJsSdk(({ wx, ready, error }) => {
            if (ready && !error) {
                wx.previewImage({
                    current: fileImg[index], // 
                    urls: fileImg // 
                });
            }
        })
    }

    render() {
        const { getFieldProps, getFieldError } = this.props.form;
        let { title, tableData, initValue, detailFlag, approvalType } = this.state;
        let approvalText = "";
        switch (approvalType) {
            case '0':
                approvalText = '未审批'
                break;
            case '1':
                approvalText = '审批中'
                break;
            case '2':
                approvalText = '通过'
                break;
            case '3':
                approvalText = '驳回'
                break;
            default:
                approvalText = ''
                break;
        }
        return <div className="TurnoverExamineDetailContainer">
            <div className="header colorList">
                <h3>{title}</h3>
                <h4 style={{ display: detailFlag === '0' ? 'block' : 'none', paddingTop: '.2rem' }}>【{approvalText}】</h4>
            </div>

            <div className="content colorList">
                <form>
                    <List>
                        {
                            tableData.map((v, i) => {
                                let { type, name, label, isSelect, select, isHide, onChange, defaultValue, required, errorMessage, disabled } = v;
                                let nisHide = isHide ? 'none' : 'block';

                                let nRequired = isHide === true ? false : (required ? true : false);
                                switch (type) {
                                    case 'text'://普通文本  
                                        return <div key={i} className="list" style={{ display: nisHide }}>
                                            <InputItem
                                                className={disabled ? 'noOrangeBorder' : ''}
                                                labelNumber={10}
                                                type={type}
                                                {...getFieldProps(name, {
                                                    initialValue: isSelect ? (initValue[name] === '0' ? select[0].label : select[1].label) : defaultValue || initValue[name] || '',
                                                    rules: [
                                                        { required: nRequired || false, message: errorMessage || '' },
                                                    ],
                                                    onChange: onChange
                                                })}
                                                clear
                                                error={!!getFieldError(name)}
                                                onErrorClick={() => {
                                                    Toast.info(getFieldError(name).join('、'));
                                                }}
                                                editable={!disabled}
                                            >{label}：</InputItem>
                                        </div>
                                    case 'date':// 日期 传入时间戳 
                                        let _initValue = initValue[name] ? initValue[name] : new Date().getTime();
                                        let dataW = this.formatData(_initValue);
                                        return <div key={i} className="list" style={{ display: nisHide }}>
                                            <InputItem
                                                className={disabled ? 'noOrangeBorder' : ''}
                                                labelNumber={12}
                                                type={'text'}
                                                {...getFieldProps(name, {
                                                    initialValue: defaultValue || dataW || '',
                                                    rules: [
                                                        { required: nRequired || false, message: errorMessage || '' },
                                                    ],
                                                    onChange: () => { return onChange || '' }
                                                })}
                                                clear
                                                error={!!getFieldError(name)}
                                                onErrorClick={() => {
                                                    alert(getFieldError(name).join('、'));
                                                }}
                                                editable={!disabled}
                                            >{label}：</InputItem>
                                        </div>
                                    case 'textarea':// 多行文本  
                                        let _d = '';//时间
                                        //匹配对应的时间
                                        switch (name) {
                                            case 'applyUnitOpinion'://审批单位
                                                _d = this.state.applyUnitDate;
                                                break;
                                            case 'imDepartmentOpinion'://局信息化部门
                                                _d = this.state.imDepartmentDate;
                                                break;
                                            case 'imDepartmentLeaderOpinion'://局信息化领导
                                                _d = this.state.imDepartmentLeaderDate;
                                                break;
                                            default:
                                                _d = new Date().getTime();
                                                break;
                                        }
                                        let _dataW = this.formatData(_d);

                                        return <div key={i} className="list" style={{ display: nisHide === 'none' ? 'none' : (this.state.detailFlag === '0' && v.explicitImplicit ? 'none' : '') }}>

                                            <div className="textareaTime textareaHeader"
                                                style={{
                                                    color: 'black',
                                                    textAlign: 'left',
                                                    borderBottom: '1px solid #ddd',
                                                    padding: '.1rem 0',
                                                    lineHeight: '.9'
                                                }}>审批时间：<span>{_dataW}</span></div>

                                            <div className="textareaHeader" style={{ color: 'black' }}>{label + '：'}</div>
                                            <TextareaItem
                                                className={disabled ? 'noOrangeBorder' : ''}
                                                type={type}
                                                rows={3}
                                                placeholder=""
                                                labelNumber={10}
                                                {...getFieldProps(name, {
                                                    initialValue: initValue[name] || defaultValue || '',
                                                    rules: [
                                                        { required: nRequired || false, message: errorMessage || '' },
                                                    ],
                                                    onChange: onChange
                                                })}
                                                error={!!getFieldError(name)}
                                                onErrorClick={() => {
                                                    alert(getFieldError(name).join('、'));
                                                }}
                                                editable={!disabled}
                                            ></TextareaItem>
                                        </div>
                                    case 'image':// 图片
                                        const { files } = this.state;
                                        return <div key={i} className="list" style={{ display: nisHide }}>
                                            <div className="textareaHeader" style={{ color: 'black' }}>{label + '：'}</div>
                                            <Item>
                                                <ImagePicker className='w-disabled'
                                                    files={files}
                                                    selectable={!disabled}
                                                    onImageClick={this.onImageClick}
                                                />
                                            </Item>
                                        </div>
                                    case 'radio':// 单选  
                                        return <div key={i} className="list" style={{ display: nisHide === 'none' ? 'none' : (this.state.detailFlag === '0' ? 'none' : '') }}>
                                            <Item
                                                className="radio"
                                                disabled={disabled}
                                                extra={<Switch
                                                    disabled={disabled}
                                                    {...getFieldProps(name, {
                                                        initialValue: false,
                                                        valuePropName: 'checked',
                                                    })}
                                                    platform="android"
                                                />}
                                            >{label}：</Item>
                                        </div>
                                    case 'oa':// oa拉人
                                        return <div key={i} className="list" style={{ display: nisHide }}>
                                            <Picker data={this.state.selectPerson} cols={2} value={this.state.person} onChange={this.onChangePerson}>
                                                <List.Item arrow="horizontal">{label}：</List.Item>
                                            </Picker>
                                        </div>
                                    default:
                                        return <div key={i}><WhiteSpace /><WingBlank>不存在的字段类型：{type}</WingBlank></div>
                                }
                            })
                        }
                    </List>
                </form>
            </div>
            <div style={{ display: this.state.detailFlag === '0' ? 'none' : '' }}>
                <WingBlank>
                    <WhiteSpace />
                        <Button type="primary" onClick={this.sendBtn.bind(this)}>提交</Button>
                    <WhiteSpace size='lg' />
                </WingBlank>
            </div>
        </div>
    }
}

export default createForm()(TurnoverExamineDetail);
