import React, { useCallback, useEffect, useRef } from "react";

import styles from "./sortBox.module.css";

const SortBox = ({ onHandleRecommend, onHandleNewest }) => {
  const newRef = useRef();
  const recommendRef = useRef();

  const onSortByRecommend = useCallback(() => {
    onHandleRecommend();
    recommendRef.current.classList.add(styles["pink__color"]);
    newRef.current.classList.remove(styles["pink__color"]);
  }, [onHandleRecommend]);
  const onSortByDate = useCallback(() => {
    onHandleNewest();
    newRef.current.classList.add(styles["pink__color"]);
    recommendRef.current.classList.remove(styles["pink__color"]);
  }, [onHandleNewest]);

  useEffect(() => {
    newRef.current.classList.add(styles["pink__color"]);
  }, []);
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
