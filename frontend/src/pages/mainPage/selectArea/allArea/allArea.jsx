import styles from "./allArea.module.css";
import { Link, useHistory } from "react-router-dom";
import areaList from "../../../../assets/areaList.json";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch, faArrowLeft } from "@fortawesome/free-solid-svg-icons";

const AllArea = () => {
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
          <FontAwesomeIcon
            icon={faSearch}
            className={styles.searchButton}
            size="lg"
          />
        </div>
        <h3 className={styles.title}>전체 여행지</h3>
        <ul className={styles.areaList}>
          {areaList.map((area, index) => {
            return (
              <Link to={`${area.eng}/places`} key={index}>
                <li className={styles.areaBox}>
                  <div className={styles.box_left}>
                    <div className={styles.logo}></div>
                  </div>
                  <div className={styles.box_right}>
                    <h4 className={styles.areaTitle}>{area.kor}</h4>
                    <h5 className={styles.areaSubtitle}>{area.kor}</h5>
                  </div>
                </li>
              </Link>
            );
          })}
        </ul>
      </div>
    </>
  );
};

export default AllArea;
