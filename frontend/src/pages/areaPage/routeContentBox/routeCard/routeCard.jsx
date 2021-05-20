import styles from "./routeCard.module.css";
import { useState, useEffect } from "react";
import { useRecoilState } from "recoil";
import { usersRouteItems } from "../../../../recoil/routeAtom";
import { Link } from "react-router-dom";

const RouteCard = () => {
  const [routes, setRoutes] = useRecoilState(usersRouteItems); // Todo : routeAPI로 불러오기
  //   useEffect(() =>  []);
  return (
    <div className={styles.container}>
      {Object.keys(routes).map((value, index) => {
        return (
          <Link to={`route/${value}`} key={index}>
            <div className={styles.routeCard}>
              <div className={styles.col1}>
                <div className={styles.routeThumbnail}></div>
              </div>
              <div className={styles.col2}>
                <div className={styles.row1}>
                  <div className={styles.routeName}>{routes[value].routeName}</div>
                  <button className={styles.heartButton}>❤️</button>
                </div>
                <div className={styles.row2}>
                  {routes[value].places.map((value, index) => {
                    return (
                      <div className={styles.places} key={index}>
                        {value.placeName}
                      </div>
                    );
                  })}
                </div>
              </div>
            </div>
          </Link>
        );
      })}
    </div>
  );
};

// routeName, heartButton, p

export default RouteCard;
