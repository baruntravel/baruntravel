import React, { useState } from "react";
import ReviewForm from "../../components/reviewComponents/reviewForm/reviewForm";
import ReviewList from "../../components/reviewComponents/reviewList/reviewList";
import styles from "./portalReview.module.css";

const PortalReview = ({ item, closeReviewList }) => {
  const [writeReview, setWriteReview] = useState(true);
  const writeReviewOpen = () => {
    setWriteReview(true);
  };
  const writeReviewClose = () => {
    setWriteReview(false);
  };
  return (
    <div className={styles.PortalReview}>
      <div className={styles.reviewBody}>
        {writeReview ? (
          <ReviewForm
            item={item}
            onCloseReviewForm={writeReviewClose}
            onClose={closeReviewList}
          />
        ) : (
          <ReviewList
            item={item}
            closeList={closeReviewList}
            onOpenWriteReview={writeReviewOpen}
          />
        )}
      </div>
    </div>
  );
};

export default PortalReview;
