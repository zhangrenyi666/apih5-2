import React, { isValidElement } from "react";
const index = function() {
    const { desc } = this.state;
    if (isValidElement(desc)) {
        return (
            <div
                style={{
                    marginBottom: "8px",
                    color: "#bbb",
                    fontSize: "13px"
                }}
            >
                {desc}
            </div>
        );
    }
    return (
        <div
            style={{
                marginBottom: "8px",
                color: "#bbb",
                fontSize: "13px"
            }}
            dangerouslySetInnerHTML={{ __html: desc }}
        />
    );
};

export default index;
