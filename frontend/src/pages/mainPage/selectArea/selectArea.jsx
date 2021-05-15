import styles from "./selectArea.module.css";
import { useHistory } from "react-router-dom";
import areaList from "../../../assets/areaList.json";

const SelectArea = () => {
  const { kakao } = window;
  let history = useHistory();
  const showAllArea = () => history.push("/place-all");

  const getAreaXY = (placeName) => {
    let ps = new kakao.maps.services.Places(),
      xy;

    ps.keywordSearch(placeName, async function (result, status) {
      if (status === kakao.maps.services.Status.OK) {
        xy = await result[0];
        return xy;
      }
    });
  };

  // 인기지역 10개만 보여주고, 전체보기 누르면 전체 지역
  const AreaList = () => {
    let areaArray = [];
    let xy;
    for (let i = 0; i < 10; i++) {
      areaArray.push(
        <div onClick={() => history.push({ pathname: "/place", state: { id: i } })} className={styles.areaBox} key={i}>
          <li className={styles.area} id={areaList[i].eng} key={i}>
            {areaList[i].kor}
          </li>
        </div>
      );
    }
    return areaArray;
  };

  return (
    <>
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
    </>
  );
};

export default SelectArea;
