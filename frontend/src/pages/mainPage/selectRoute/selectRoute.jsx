import styles from "./selectRoute.module.css";
import { useHistory } from "react-router-dom";
import { usersRouteItems } from "../../../recoil/routeAtom";
import { useRecoilState } from "recoil";

const SelectRoute = ({ routes }) => {
  let history = useHistory();
  const showAllRoute = () => history.push("/route-all");
  const RouteListElem = () => {
    let routeArray = [];
    // 인기 루트 10개 이하만
    for (let i = 0; i < (routes.length < 10 ? routes.length : 10); i++) {
      routeArray.push(
        <div onClick={() => history.push({ pathname: "/route", state: { id: i } })} className={styles.routeBox} key={i}>
          <li className={styles.route}>{routes[i].name}</li>
        </div>
      );
    }
    return routeArray;
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.title}>인기 루트</div>
        <ul className={styles.routeList}>
          <div className={styles.routeBox}>
            <li className={styles.route} onClick={showAllRoute}>
              전체 보기
            </li>
          </div>
          <RouteListElem />
          <div className={styles.routeBox}>
            <li className={styles.route} onClick={showAllRoute}>
              전체 보기
            </li>
          </div>
        </ul>
      </div>
    </>
  );
};

export default SelectRoute;
