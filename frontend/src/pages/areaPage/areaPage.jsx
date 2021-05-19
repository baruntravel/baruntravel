import styles from "./areaPage.module.css";
import { useLocation } from "react-router-dom";
import { useCallback, useState } from "react";
import Navbar from "../../components/common/navbar/navbar";
import RouteContentBox from "./routeContentBox/routeContentBox";
import PlaceContentBox from "../../components/placeContentBox/placeContentBox";

const AreaPage = () => {
  const location = useLocation();
  const [area, setArea] = useState(location.state.areaKor);
  const [tabBox, setTabBox] = useState("route");

  const onHandleRouteTab = useCallback(() => {
    setTabBox("route");
  }, []);
  const onHandlePlaceTab = useCallback(() => {
    setTabBox("place");
  }, []);
  return (
    <div className={styles.container}>
      <div className={styles.navbar}>
        <Navbar />
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
          {tabBox === "route" ? <RouteContentBox /> : <PlaceContentBox />}
        </div>
        <div className={styles.floatingIcon}></div>
      </div>
    </div>
  );
};

export default AreaPage;
