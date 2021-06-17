import { useState, useEffect, useCallback } from "react";
import styles from "./routeContentBox.module.css";
import SortBox from "../../../components/common/sortBox/sortBox";
import RouteCard from "../../common/routeCard/routeCard";
import MapButton from "../mapButton/mapButton";
import { useHistory } from "react-router-dom";
import { onReceivePlace } from "../../../api/placeAPI";
import { getRoutesByRegion } from "../../../api/routeAPI";

const RouteContentBox = ({ areaEng, areaKor }) => {
  const history = useHistory();
  const mapHandler = () => history.push("/route-map");
  const [routes, setRoutes] = useState([]);

  useEffect(() => {
    onGetRoutes(1, 10, "best", areaKor);
  }, []);

  async function onGetRoutes(page, size, sortType, region) {
    const { content } = await getRoutesByRegion(page, size, sortType, region);
    setRoutes(content);
  }

  return (
    <div className={styles.container}>
      <div className={styles.functionBox}>
        <MapButton mapHandler={mapHandler} />
        <SortBox />
      </div>
      <div className={styles.routeCards}>
        <RouteCard routes={routes} />
      </div>
    </div>
  );
};

export default RouteContentBox;
