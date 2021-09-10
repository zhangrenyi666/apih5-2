import React from "react";
import QnnForm from "../qnn-form";
import { Modal, Button } from "antd";
const index = function() {
    const {
        modalVisible,
        modalTitle,
        modalBtns = [],
        modalOption = {},
        qnnFormOption
    } = this.state;
    const qnnFormModalConfig = this.getQnnFormModalConfig({
        ...qnnFormOption
    });
    return (
        <Modal
            destroyOnClose
            title={modalTitle}
            visible={modalVisible}
            onOk={this.handleOk}
            onCancel={this.handleCancel}
            footer={
                modalBtns.length
                    ? modalBtns.map((item, index) => {
                          let { label, icon, type = "primary" } = item;
                          return (
                              <Button
                                  key={index}
                                  icon={icon}
                                  onClick={() => {
                                      this.modalBtnClick(item);
                                  }}
                                  type={type}
                              >
                                  {label}
                              </Button>
                          );
                      })
                    : null
            }
            {...modalOption}
        >
            <QnnForm {...qnnFormModalConfig} />
        </Modal>
    );
};

export default index;
