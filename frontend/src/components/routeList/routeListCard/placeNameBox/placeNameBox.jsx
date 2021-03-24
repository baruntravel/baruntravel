import React, { useCallback, useState } from "react";
import styles from "./placeNameBox.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";
import Portal from "../../../portal/portal";
import ReviewList from "../../../reviewComponents/reviewList/reviewList";
import PortalReview from "../../../../containers/portalReview/portalReview";

const PlaceNameBox = ({ item }) => {
  const [reviewVisible, setReviewVisible] = useState(false);
  const closeReviewList = useCallback(() => {
    setReviewVisible(false);
  }, []);
  const openReviewList = useCallback(() => {
    setReviewVisible(true);
  }, []);
  const handleClick = useCallback(() => {
    // 삭제 api호출
    console.log("삭제 api 호출");
  }, []);
  return (
    <div className={styles.PlaceNameBox}>
      <div className={styles.placeInfo}>
        <div className={styles.nameBox}>
          <FontAwesomeIcon icon={faMapMarkerAlt} className={styles.icon} />
          <span className={styles.placeName}>{item.placeName}</span>
        </div>
        <span className={styles.address}>{item.addressName}</span>
        <div className={styles.bottom}>
          <span className={styles.more} onClick={openReviewList}>
            리뷰
          </span>
          <a href={item.placeUrl} target="_blank" className={styles.review}>
            상세 보기
          </a>
        </div>
      </div>
      <div className={styles.deleteBox}>
        <button className={styles.deleteBtn} onClick={handleClick}>
          X
        </button>
      </div>
      {reviewVisible && (
        <Portal>
          <PortalReview item={item} closeReviewList={closeReviewList} />
        </Portal>
      )}
    </div>
  );
};

export default PlaceNameBox;
