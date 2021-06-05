import { useRef } from "react";
import styles from "./cardContainer.module.css";

const CardContainer = ({ folderIn }) => {
  const titleRef = useRef();

  const passTitle = (e) => {
    // console.log(titleRef.current);
    folderIn();
  };

  return (
    <div>
      <div className={styles.cardsContainer}>
        <div className={styles.cardBox} onClick={passTitle}>
          {/* <div className={styles.imgBox}>
            <div className={styles.img1}>img1</div>
            <div className={styles.img2}>img2</div>
            <div className={styles.img3}>img3</div>
          </div> */}
          <span className={styles.wishListTitle} ref={titleRef}>
            제주도 갈만한 곳들
          </span>
        </div>

        <div className={styles.cardBox} onClick={passTitle}>
          <div className={styles.imgBox}></div>
          <span className={styles.wishListTitle} ref={titleRef}>
            서울 핫플레이스
          </span>
        </div>
      </div>
    </div>
  );
};

export default CardContainer;
