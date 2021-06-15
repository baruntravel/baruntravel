import styles from "./comunityPage.module.css";
import { useCallback, useState } from "react";
import { useHistory } from "react-router-dom";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import RouteContentBox from "../../components/comunityPage/routeContentBox/routeContentBox";
import PlaceContentBox from "../../components/comunityPage/placeContentBox/placeContentBox";
import areaList from "../../assets/areaList.json";

const ComunityPage = () => {
  const history = useHistory();
  const [area, setArea] = useState(window.location.pathname.split("/").pop());
  const [title, setTitle] = useState(Object.values(areaList).filter((i) => i.eng === area)[0].kor); // TODO : 예외처리 ex /community/aaaaa
  const [tabBox, setTabBox] = useState(false); // false : route, true : place
  const toggleTab = () => setTabBox(!tabBox);

  return (
    <div className={styles.container}>
      <Header title={title} />
      <div className={styles.body}>
        <div className={styles.tabBox}>
          <button className={tabBox ? `${styles.unclicked}` : `${styles.clicked}`} onClick={toggleTab}>
            루트
          </button>
          <button className={tabBox ? `${styles.clicked}` : `${styles.unclicked}`} onClick={toggleTab}>
            플레이스
          </button>
        </div>
        <div className={styles.contentBox}>
          {tabBox === false ? <RouteContentBox area={area} /> : <PlaceContentBox />}
        </div>
        <Navbar />
      </div>
    </div>
  );
};

export default ComunityPage;
