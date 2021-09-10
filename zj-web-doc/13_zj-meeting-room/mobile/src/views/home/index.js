import React, { Component } from "react";
// import "intro.js/introjs.css";
// import { Steps, Hints } from "intro.js-react";

class componentName extends Component {
  render() {
    // const steps = [
    //   {
    //     element: ".selector1",
    //     intro: "test 1",
    //     position: "right",
    //     tooltipClass: "myTooltipClass",
    //     highlightClass: "myHighlightClass"
    //   },
    //   {
    //     element: ".selector2",
    //     intro: "test 2"
    //   },
    //   {
    //     element: ".selector3",
    //     intro: "test 3"
    //   }
    // ];

    // const hints = [
    //   {
    //     element: ".selector1",
    //     hint: "test 1",
    //     hintPosition: "middle-middle"
    //   },
    //   {
    //     element: ".selector2",
    //     hint: "test 2"
    //   }
    // ];

    return (
      <div>
        首页
        {/* <Steps
          initialStep={1}
          enabled={false}
          steps={steps}
          ref={steps => (this.steps = steps)}
          onExit={e => {
            console.log(e);
          }}
        />
        <Hints enabled={true} hints={hints} />

        <h1 className="selector1">步骤一</h1>
        <h1 className="selector1" style={{ textAlign: "center" }}>
          步骤二
        </h1>
        <h1 className="selector1" style={{ textAlign: "right" }}>
          步骤三
        </h1> */}
      </div>
    );
  }
}
export default componentName;
