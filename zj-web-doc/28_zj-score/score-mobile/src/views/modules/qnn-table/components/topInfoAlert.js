import React from "react";
import { Drawer,Alert,Modal,Button } from "antd";

const index = (props) => {

    const onClose = () => {
        props.setFormIsChangeedAlertText(null)
    }

    const onCloseAndcloseDrawer = () => {
        props.setFormIsChangeedAlertText(null);
        props.closeDrawer();
    }

    if (props.type === "0") {
        return (
            <Modal
                width="400px"
                title={props.formIsChangeedAlertTextContent.title}
                closable={false}
                destroyOnClose
                visible={true}
                onOk={onClose}
                onCancel={onClose}
                footer={[
                    <Button style={{width:"80px"}} key="back" onClick={onCloseAndcloseDrawer}>
                        否
                  </Button>,
                    <Button style={{width:"80px"}} key="submit" type="primary" onClick={onClose}>
                        是
                  </Button>
                ]}
            >
                {props.children}
            </Modal>
        );
    } else {
        return (
            <Drawer
                maskStyle={{
                    background: "transparent"
                }}
                placement={"top"}
                closable={false}
                onClose={() => {
                    onClose()
                }}
                visible={true}
                height={"auto"}
                bodyStyle={{ padding: "0px" }}
            >
                <Alert message={props.children} type="warning" showIcon closable afterClose={onClose} />
            </Drawer>
        );
    }
};

export default index;
