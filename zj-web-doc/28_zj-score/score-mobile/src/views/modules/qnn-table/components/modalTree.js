import React from "react";
import PullPerson from "../../pullPersion";
const index = function () {
    const { getFieldDecorator } = this.props.form;
    const {
        openTree,
        pullpersonChangeFn,
        treeOption,
        pullpersonField
    } = this.state;
    if (openTree) {
        let initialValue = treeOption && treeOption.initialValue;
        let treeDidMount = treeOption && treeOption.treeDidMount;
        if (initialValue) {
            delete treeOption.initialValue;
        }
        if (treeDidMount) {
            delete treeOption.treeDidMount;
        }

        return (
            <div>
                {getFieldDecorator(pullpersonField,{
                    initialValue: initialValue,
                    valuePropName: "defaultValue",
                    onChange: pullpersonChangeFn
                })(
                    <PullPerson
                        visible
                        label={<a>{null}</a>}
                        myFetch={this.fetch}
                        {...treeOption}
                        didMount={treeDidMount}
                    />
                )}
            </div>
        );
    } else {
        return null;
    }
};

export default index;
