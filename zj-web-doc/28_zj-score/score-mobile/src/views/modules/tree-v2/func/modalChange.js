function showDrawer() {
    this.setState({
        visible: true
    });
}

function onClose() {
    this.setState({
        visible: false
    });
}

function onCloseBySelectTip() {
    this.setState({
        visibleBySelectTip: false
    });
}
function showDrawerBySelectTip() {
    this.setState({
        visible: true
    });
}

export { showDrawer, onClose, onCloseBySelectTip, showDrawerBySelectTip };
