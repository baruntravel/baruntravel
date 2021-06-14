import { useState, useEffect, useCallback } from "react";
import styles from "./routeContentBox.module.css";
import SortBox from "../../../components/common/sortBox/sortBox";
import RouteCard from "../../common/routeCard/routeCard";
import RouteMapPage from "../../../pages/routeMapPage/routeMapPage";
import MapButton from "../mapButton/mapButton";
// import UsersRoutePage from "../../usersRoutePage/usersRoutePage";

const RouteContentBox = ({ area }) => {
  const [mapOpen, setMapOpen] = useState(false);
  const mapHandler = useCallback(() => setMapOpen(!mapOpen), []);

  return (
    <div className={styles.container}>
      <div className={styles.functionBox}>
        <MapButton mapHandler={mapHandler} />
        <SortBox />
      </div>
      <div className={styles.routeCards}>
        <RouteCard />
      </div>

      {mapOpen && <RouteMapPage />}
    </div>
  );
};

export default RouteContentBox;
