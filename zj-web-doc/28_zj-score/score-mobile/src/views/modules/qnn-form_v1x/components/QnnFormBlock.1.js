import React from "react";
import s from "../styleByZJ.less";
import { Icon,Button,Row,Col,Modal } from "antd"; //, Form ,Divider
import { QnnFormCom } from "../components";
const { confirm } = Modal;
class QnnFormBlock extends React.Component {

    //删除可增删的表单块
    delFormBlock = (field,_initialValueKey,_index) => {
        const _setThisAttr = this.props.PsetThisAttr;
        const _getThisAttr = this.props.PgetThisAttr;

        //删除之前应该把数组中其他值存起来  
        // let oldValues = _getThisAttr(_initialValueKey);
 
        /*
         因为可增减的表单块对象都是{0:obj,1:obj}
         所以需要获取每个表单块的具体值才对
        */
        let formBlockObj = this.props.getFormBlockObj(field);
        console.log(formBlockObj)
        let oldValues = [];
        for (const key in formBlockObj) {
            if (formBlockObj.hasOwnProperty(key)) {
                const element = formBlockObj[key];
                console.log(element)
                oldValues.push(element.getValues())
            }
        }
        console.log(oldValues)

        if (
            oldValues &&
            oldValues.length
        ) {
            oldValues.splice(
                _index,
                1
            );

            console.log('oldValues：', oldValues)
            _setThisAttr(
                _initialValueKey,
                oldValues
            );

            //需要删除掉this.formBlocks[field]上对应的数据
            //否则取值时候依然会取到
            this.props.delFormBlockObjByCanAdd(field,_index)
        }

    }

    //弹出删除提醒
    showDeleteFormBlockConfirm(tit,cb) {
        confirm({
            title: `确定要删除【${tit}】吗?`,
            okText: '确定',
            okType: 'danger',
            cancelText: '取消',
            onOk() {
                cb()
            },
            onCancel() {
                console.log('Cancel');
            }
        });
    }


    render() {
        const _props = this.props.Pprops;
        const _setThisAttr = this.props.PsetThisAttr;
        const _getThisAttr = this.props.PgetThisAttr;
        let { label,field,initialValue } = _props; //,form 

        //测试删除
        //开发中...
        delete _props.form;
        _props.setFormBlockObj = this.props.setFormBlockObj;

        if (!_props.canAddForm) {
            let _initVal = this.props.value || initialValue || [];
            // if (this.props.isMobile()) {
            return (
                <div>
                    {/* label */}
                    <div className={s.QnnFormBlockLabel}>
                        <Row type="flex" justify="space-between">
                            <Col>
                                <span id={field}>{label}</span>
                            </Col>
                        </Row>
                    </div>
                    {/* /label */}
                    <QnnFormCom
                        {..._props}
                        initialValue={_initVal}
                        style={{ paddingBottom: "0px" }}
                    />
                </div>
            );
            // } else {
            //     return (
            //         <div>
            //             {/* label */}
            //             <div>
            //                 <Divider orientation="left">
            //                     <div id={field}>{label}</div>
            //                 </Divider>
            //             </div>
            //             {/* /label */}
            //             <QnnFormCom
            //                 {..._props}
            //                 initialValue={_initVal}
            //                 style={{ paddingBottom: "0px" }}
            //             />
            //         </div>
            //     );
            // }
        } else {
            let { textObj = {} } = _props;
            textObj = {
                add: '添加',
                del: '删除',
                ...textObj
            }
            let { add,del } = textObj;

            //将初期値当做表单项，并且给每个表单项设置id
            let _initialValueKey = `${field}InitialValue`;
            let _initVal = this.props.value || initialValue; // || [{id:1}]
            if (
                !_getThisAttr(_initialValueKey) &&
                _initVal &&
                _initVal.length
            ) {
                _initVal = _initVal.map(item => {
                    item.id = `${new Date().getTime().toString()}${(Math.random() * 10000 + 100)}`;
                    return item;
                });
                _setThisAttr(_initialValueKey,_initVal);
            }


            const formBlocksNum = _getThisAttr(_initialValueKey) || [{ id: 1 }];

            return (
                <div style={{ marginBottom: 12 }}>
                    <>
                        {formBlocksNum.map((_,_index) => {
                            let __props = { ..._props };
                            //label是动态的
                            let realLabel = formBlocksNum.length > 1
                                ? `${label}${_index + 1}`
                                : label;
                            return (
                                <div key={_index}>
                                    {/* label */}
                                    <div className={s.QnnFormBlockLabel}>
                                        <Row
                                            type="flex"
                                            justify="space-between"
                                            id={field}
                                        >
                                            <Col>
                                                {realLabel}
                                            </Col>
                                            <Col>
                                                {/* 刪除按鈕 */}
                                                {(this.props.disabled || formBlocksNum.length <= 1) ? null : (
                                                    <span
                                                        className={s.formBlockDelBtn}
                                                        // 点击先提示下
                                                        onClick={this.showDeleteFormBlockConfirm.bind(this,realLabel,this.delFormBlock.bind(this,field,_initialValueKey,_index))}>
                                                        <Icon type="delete" />{del}
                                                    </span>
                                                )}
                                            </Col>
                                        </Row>
                                    </div>
                                    {/* /label */}
                                    {/* formBlocks */}
                                    <div className={s.QnnFormBlockForm}>
                                        <QnnFormCom
                                            {...__props}
                                            state={this.state}
                                            field={field}
                                            index={_index}
                                            formBlockIndex={_index}
                                            style={{ paddingBottom: "0px" }}
                                            initialValue={_getThisAttr(
                                                _initialValueKey
                                            )}
                                            id={_.id}
                                        />
                                    </div>
                                    {/* /formBlocks */}
                                </div>
                            );
                        })}
                    </>
                    {this.props.disabled ? null : (
                        <div style={{ padding: "12px" }}>
                            <Button
                                icon="plus"
                                size="small"
                                onClick={() => {
                                    let _obj = {
                                        id: `${new Date().getTime().toString()}${(Math.random() * 10000 + 100)}`
                                    };
                                    let _formBlocksNum = formBlocksNum.concat([
                                        _obj
                                    ]);

                                    //新增时候直接在尾部插入一条空数据即可
                                    _setThisAttr(_initialValueKey,_formBlocksNum);
                                }}
                            >
                                {add}
                            </Button>
                        </div>
                    )}
                </div>
            );
        }
    }
}
export default QnnFormBlock;
