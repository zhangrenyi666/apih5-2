import React from 'react';
import styles from './index.less';
const TextJustify = ({ value }) => {
    return (
        <div className={styles.box}>
            {value.toString().split("").map((item, index) => {
                return <div key={index}>{item}</div>
            })}
        </div>
    )

}
export default TextJustify