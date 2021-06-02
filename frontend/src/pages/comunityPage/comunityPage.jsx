import styles from "./comunityPage.module.css";
import { useLocation } from "react-router-dom";
import { useCallback, useState } from "react";
import Header from "../../components/common/header/header";
import RouteContentBox from "../../components/comunityPage/routeContentBox/routeContentBox";
import PlaceContentBox from "../../components/comunityPage/placeContentBox/placeContentBox";
const ComunityPage = () => {
  const location = useLocation();
  const [area, setArea] = useState(location.state.areaKor);
  const [tabBox, setTabBox] = useState("route");
  const onHandleRouteTab = useCallback(() => setTabBox("route"), []);
  const onHandlePlaceTab = useCallback(() => setTabBox("place"), []);

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <Header />
      </div>
      <div className={styles.areaTitle}>{area}</div>
      <div className={styles.body}>
        <div className={styles.tabBox}>
          <button className={styles.routeTab} onClick={onHandleRouteTab}>
            루트
          </button>
          <button className={styles.placeTab} onClick={onHandlePlaceTab}>
            플레이스
          </button>
        </div>
        <div className={styles.contentBox}>
          {tabBox === "route" ? <RouteContentBox area={area} /> : <PlaceContentBox />}
        </div>
        <div className={styles.floatingIcon}></div>
      </div>
    </div>
  );
};

export default ComunityPage;
