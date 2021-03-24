import React from "react";
import ReviewList from "../../components/reviewComponents/reviewList/reviewList";
import styles from "./portalReview.module.css";

const PortalReview = ({ item, closeReviewList }) => {
  return (
    <div className={styles.PortalReview}>
      <div className={styles.reviewList}>
        <ReviewList item={item} closeList={closeReviewList} />
      </div>
    </div>
  );
};

export default PortalReview;
