import React,{ Component } from "react"; 
import TrainList from "apih5/pages/TrainList"
class TrainListPage extends Component {
    render() {
        return (
            <TrainList 
                {...this.props}
            />
        );
    }
}
 
export default TrainListPage;
