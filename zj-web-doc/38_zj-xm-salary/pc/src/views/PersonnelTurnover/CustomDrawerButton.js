import React from 'react'
import {  Button } from 'antd'


const CustomDrawerButton = (props) => {

    return (
        <div style={{
            width: '100%',
            position: 'absolute',
            bottom: 0,
            display: 'flex',
            justifyContent: 'flex-end',
            padding: '8px 12px',
            borderTop: '1px solid #e8e8e8',
            background: ' #fff'
        }}
        >
            <Button
                onClick={() => {
                    props.refInstance.closeDrawer()
                    props.cancelFunc()
                }}
            >取消</Button>
            <Button
                type="primary"
                style={{
                    marginLeft: '8px'
                }}
                onClick={() => {
                    props.okFunc()
                }} >
                保存
            </Button>
        </div>
    )

}
export default CustomDrawerButton