import styles from "./comunityPage.module.css";
import { useLocation } from "react-router-dom";
import { useCallback, useState } from "react";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import RouteContentBox from "../../components/comunityPage/routeContentBox/routeContentBox";
import PlaceContentBox from "../../components/comunityPage/placeContentBox/placeContentBox";
const ComunityPage = () => {
  const location = useLocation();
  const [area, setArea] = useState(location.state.areaKor);
  const [tabBox, setTabBox] = useState(false); // false : route, true : place
  const toggleTab = () => setTabBox(!tabBox);

  return (
    <div className={styles.container}>
      <Header title={area} />
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
