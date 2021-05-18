import React, { useCallback, useRef } from "react";
import styles from "./sortBox.module.css";

const SortBox = ({ onHandleRecommend, onHandleNewest }) => {
  const newRef = useRef();
  const recommendRef = useRef();

  const onSortByRecommend = useCallback(() => {
    onHandleRecommend();
    // recommendRef.current.style.~~
  }, [onHandleRecommend]);
  const onSortByDate = useCallback(() => {
    onHandleNewest();
    // newRef.current.style.
  }, [onHandleNewest]);
  return (
    <div className={styles.SortBox}>
      <span
        ref={newRef}
        className={styles.reviewList__new}
        onClick={onSortByDate}
      >
        최신순
      </span>
      <span
        ref={recommendRef}
        className={styles.reviewList__recommend}
        onClick={onSortByRecommend}
      >
        추천순
      </span>
    </div>
  );
};

export default SortBox;
