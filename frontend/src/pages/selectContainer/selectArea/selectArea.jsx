import styles from "./selectArea.module.css";
import { useState } from "react";
import { useHistory, Link } from "react-router-dom";
import areaList from "../../../assets/areaList.json";
import AllArea from "./allArea/allArea";

const SelectArea = () => {
  const history = useHistory();
  const [isLoggedIn, setLoggedIn] = useState(true);
  const [area, setArea] = useState();
  const [showAll, setShowAll] = useState(false);

  //로그인 안 돼있을시 홈으로
  !isLoggedIn && history.push("/");

  function handleAreaClick(e) {
    setArea(e.target.id);
  }

  function showAllArea() {
    setShowAll(true);
  }

  const AreaList = () => {
    let areaArray = [];
    for (let i = 0; i < 10; i++) {
      areaArray.push(
        <Link to={`${areaList[i].eng}/places`}>
          <div className={styles.areaBox}>
            <li
              className={styles.area}
              id={areaList[i].eng}
              key={i}
              onClick={(e) => handleAreaClick(e)}
            >
              {areaList[i].kor}
            </li>
          </div>
        </Link>
      );
    }
    return areaArray;
  };

  return (
    <>
      {showAll ? (
        <AllArea />
      ) : (
        <div className={styles.container}>
          <div className={styles.title}>인기 여행지</div>
          <ul className={styles.areaList}>
            <div className={styles.areaBox}>
              <li className={styles.area} onClick={showAllArea}>
                전체 보기
              </li>
            </div>
            <AreaList />
            <div className={styles.areaBox}>
              <li className={styles.area} onClick={showAllArea}>
                전체 보기
              </li>
            </div>
          </ul>
        </div>
      )}
    </>
  );
};

export default SelectArea;
