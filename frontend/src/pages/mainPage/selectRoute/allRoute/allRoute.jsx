import styles from "./allRoute.module.css";
import { useHistory } from "react-router-dom";
import { usersRouteItems } from "../../../../recoil/routeAtom";
import { useRecoilState } from "recoil";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch, faArrowLeft } from "@fortawesome/free-solid-svg-icons";

const AllRoute = () => {
  const [routeItems, setRouteItems] = useRecoilState(usersRouteItems);
  const routeList = Object.values(routeItems);
  let history = useHistory();

  return (
    <>
      <div className={styles.container}>
        <div className={styles.searchBox}>
          <FontAwesomeIcon
            icon={faArrowLeft}
            className={styles.backButton}
            size="lg"
            onClick={() => history.goBack()}
          />
          <input className={styles.inputBar} placeholder="어디로 가세요?" />
          <FontAwesomeIcon icon={faSearch} className={styles.searchButton} size="lg" />
        </div>
        <h3 className={styles.title}>전체 여행지</h3>
        <ul className={styles.routeList}>
          {routeList.map((route, index) => {
            return (
              <li
                onClick={() => history.push({ pathname: "/route", state: { id: index } })}
                className={styles.routeBox}
                key={index}
              >
                <div className={styles.box_left}>
                  <div className={styles.logo}></div>
                </div>
                <div className={styles.box_right}>
                  <h4 className={styles.areaTitle}>{route.routeName}</h4>
                  <h5 className={styles.areaSubtitle}>{route.creator}</h5>
                </div>
              </li>
            );
          })}
        </ul>
      </div>
    </>
  );
};

export default AllRoute;
