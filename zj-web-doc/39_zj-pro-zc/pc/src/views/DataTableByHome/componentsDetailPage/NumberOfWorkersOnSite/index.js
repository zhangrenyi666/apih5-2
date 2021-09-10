import React from 'react';
import Apih5 from 'qnn-apih5';
// import QnnTable from 'qnn-table';
import style from "./style.less"

class Page extends Apih5 {
    state = {
        data: [
            // {
            //     label: "钢筋班",
            //     number: "30/35",
            //     person: ["雷军","老干妈","马糊涂"]
            // },
            // {
            //     label: "混泥土班",
            //     number: "30/35",
            //     person: ["雷军"]
            // }, 
        ]
    };

    componentDidMount() {
        this.refresh();

        this.refreshTimer = setInterval(this.refresh,1000 * 60)
    }

    componentWillUnmount() {
        clearInterval(this.refreshTimer)
    }

    refresh = async () => {
        return new Promise(async (resolve) => {
            const { myFetch,errMsg } = this.props;
            const { data = [],success,message,code } = await myFetch("getTeamPresentNumberDetails",{
                limit: 999
            });
            resolve();
            if (success) {
                this.setState({
                    data: data.map(({ groupName,presentNumber,personNumber,persons = [] }) => {
                        return {
                            label: groupName,
                            number: `${presentNumber}/${personNumber}`,
                            person: persons.map(({ names }) => names)
                        }
                    }),
                });
            } else {
                errMsg(message,code);
            }
        })
    };
    render() {
        const { data } = this.state;

        return (
            <div className={style.page}>
                <div className={style.title}>
                    在场作业人员信息
                </div>
                <div className={style.con}>
                    {
                        data.map((item,index) => {
                            return <div key={index} className={style.li}>
                                <div className={style.top}>
                                    <div className={style.label}>{item.label}</div>
                                    <div className={style.number}>{item.number}</div>
                                </div>
                                <div className={style.person}>
                                    {
                                        item.person.map((personItem,i) => {
                                            return <div key={i} className={style.personItem}>{personItem}</div>
                                        })
                                    }
                                </div>
                            </div>
                        })
                    }
                </div>
            </div >)
    }
}
export default Page;