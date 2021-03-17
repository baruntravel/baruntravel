import styles from "./usersRouteMap.module.css";
import RouteMap from "../../components/routeMap/routeMap";

const UsersRouteMap = ({ markers }) => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.mapContainer}>
        <RouteMap markers={markers} width={"768px"} height={"512px"} />
      </div>
    </div>
  );
};

export default UsersRouteMap;
