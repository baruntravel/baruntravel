import styles from "./routeCard.module.css";
import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import { HeartOutlined, HeartFilled } from "@ant-design/icons";

const RouteCard = ({ routes }) => {
  const history = useHistory();

  // console.log(routes);

  return (
    <div className={styles.container}>
      {Object.keys(routes).map((value, index) => {
        return (
          <div onClick={() => history.push({ pathname: `/route/${index + 1}` })} key={index}>
            <div className={styles.routeCard}>
              <div className={styles.col1}>
                <div className={styles.routeThumbnail}></div>
              </div>
              <div className={styles.col2}>
                <div className={styles.row1}>
                  <div className={styles.routeName}>{routes[value].name}</div>
                  <HeartOutlined className={styles.heartIcon} />
                </div>
                <div className={styles.row2}>{routes[value].creator.name}</div>
                <div className={styles.row3}>
                  {routes[value].places.map((value, index) => {
                    return (
                      <div className={styles.places} key={index}>
                        {value.name}
                      </div>
                    );
                  })}
                </div>
              </div>
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default RouteCard;
