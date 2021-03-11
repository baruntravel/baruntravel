import styles from "./selectLocation.module.css"
import {useState} from "react";
import {useHistory} from "react-router-dom"
import districtList from "../../assets/districtList.json"

const SelectLocation = () => {
    const history = useHistory();
    const [isLoggedIn, setLoggedIn] = useState(true); 

    //로그인 안돼있을시 홈으로
    !isLoggedIn && history.push("/");

    return (
        <div className={styles.container}>
            <div className={styles.navbar}>
                <div className={styles.logoWrap}>
                    <div className={styles.logoTitle}>바른여행<br/>길잡이</div>
                </div>
                <h1 className={styles.title}>
                    지역을 선택해주세요!
                </h1>
                <div className={styles.profileWrap}></div>
            </div>
            <div className={styles.body}>
                <ul className={styles.districtList}>{districtList.map((item)=>{
                    return <li className={styles.district} id={item}>{item}</li>
                })}</ul>
            </div>
        </div>
    );
}

export default SelectLocation;
