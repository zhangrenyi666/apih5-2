
import styles from "./ListSubcontracts.less"
import $ from "jquery"

//拖动类 
//需要传入主键id
class DragFns {
    constructor(rowKey) {
        this.rowKey = rowKey;
        this.dragDomId = undefined;
    }

    addClass(dom, className) {
        let oldClass = dom.getAttribute("class");
        oldClass = `${oldClass?.replace?.(className, '')} ${className} `;
        dom.setAttribute("class", oldClass);
        dom.className = oldClass;
    }

    removeClass(dom, className) {
        let oldClass = dom.getAttribute("class");
        oldClass = oldClass?.replace?.(new RegExp(className, 'g'), '');
        dom.setAttribute("class", oldClass);
        dom.className = oldClass;
    }

    //通用事件
    //@target 被拖拽元素触摸的dom
    endEdSetStyle(targetDom) {
        //恢复被拖拽的dom样式
        const dragDom = document.getElementById(`${this.dragDomId}`);
        this.removeClass(dragDom, styles.onDragStart);
        //恢复被触摸dom的样式
        if (targetDom) {
            this.removeClass(targetDom, styles.beforeInsert);
            this.removeClass(targetDom, styles.afterInsert);
        }
        //为了确保无问题 将所有输入控件的拖拽中的样式全部删除
        const formItemCols = document.querySelectorAll(`.${styles.onDragEnter}`);
        formItemCols.forEach(element => this.removeClass(element, styles.onDragEnter));
    }


    onDragStart(event, rowId) {
        event.stopPropagation();
        // 设置拖动节点信息 在放置时候可被放置节点获取到
        event.dataTransfer.setData("rowId", rowId)
        this.addClass(event.target, styles.onDragStart);
        this.dragDomId = rowId;
    }
    onDragOver(event) {
        //这样就将该元素变为了可放置元素 
        event.preventDefault();
        event.stopPropagation();

        if (event.target.id && event.target.id !== this.dragDomId) {
            this.addClass(event.target, styles.onDragEnter);
            //目标元素高度
            let targetH = event.target.offsetHeight;
            //目标元素距离窗口顶部的距离
            let targetTop = $(event.target).offset().top;

            //获取目标元素正中位置 用于 判断是前插入dom 还是后插入dom 
            if (!this.startY) {
                this.startY = (targetH / 2) + targetTop;
            }

            if ((this.startY - event.clientY) > 0) {
                //向前插入 
                this.removeClass(event.target, styles.afterInsert);
                this.addClass(event.target, styles.beforeInsert);
                this.targetDir = 'before'
            } else {
                //向后插入 
                this.removeClass(event.target, styles.beforeInsert);
                this.addClass(event.target, styles.afterInsert);
                this.targetDir = 'after'
            }

        }
    }
    onDragEnter(event) {
        event.stopPropagation();
        if (event.target.id && event.target.id !== this.dragDomId) {
            this.addClass(event.target, styles.onDragEnter);
        }
    }
    onDragLeave(event) {
        event.stopPropagation();
        this.removeClass(event.target, styles.onDragEnter);
        this.startY = undefined;
        this.removeClass(event.target, styles.beforeInsert);
        this.removeClass(event.target, styles.afterInsert);
    }
    async onDrop(event, tableData = [], cb) {
        event.preventDefault();
        event.stopPropagation();

        //直接返回新的排序表格数据接口
        const newtableData = [...tableData];

        // 样式恢复 
        this.endEdSetStyle(event.target);
        //拖动逻辑操作
        if (event.target.id !== this.dragDomId) {
            const tableDataLen = tableData?.length;

            const dragId = this.dragDomId;
            const targetId = event.target.id;
            const insetDir = this.targetDir;

            //新位置的索引
            let targetIndex = 0;
            //被拖动id的索引
            let dragIndex = 0;

            tableData.forEach((item, index) => {
                if (item[this.rowKey] === targetId) {
                    targetIndex = index;
                }
                if (item[this.rowKey] === dragId) {
                    dragIndex = index;
                }
            });

            if (targetIndex === dragIndex) return;

            //目标数据
            const targetData = tableData[targetIndex] || {};
            //截取出被拖动的数据
            const dragData = newtableData.splice(dragIndex, 1);

            if (insetDir === "before") {
                // 排序到新位置前面的节点
                newtableData.splice(targetIndex, 0, dragData[0]);
            } else if (insetDir === "after") {
                // 排序到新位置后面的节点
                newtableData.splice(targetIndex + 1, 0, dragData[0]);
            }  

            const params = {
                // 要移动的节点
                moveKey: dragId,
                // 新位置后面的节点
                afterKey: insetDir === "before" ? targetData[this.rowKey] : tableData?.[targetIndex + 1]?.[this.rowKey],
                // 新位置前面的节点
                beforeKey: insetDir === "before" ? (targetIndex - 1 >= 0 ? tableData?.[targetIndex - 1]?.[this.rowKey] : null) : targetData[this.rowKey],
                //  当前页面的第一个节点
                firstKey: tableData[0]?.[this.rowKey],
                // 当前页面的最后一个节点
                lastKey: tableData[tableDataLen - 1]?.[this.rowKey],
                // departmentId: this.props.endKey,
            }
            cb && cb({
                params,
                newtableData
            });
 
        }
    }

    onDropEnd(event) {
        // 样式恢复 
        this.endEdSetStyle(event.target);
    }
}

export default DragFns;