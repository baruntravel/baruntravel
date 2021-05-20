import { LikeOutlined, LikeTwoTone } from "@ant-design/icons";
import React from "react";
import styles from "./reviewCardBottom.module.css";

const ReviewCardBottom = ({ liked, onUnlike, likeCount, date }) => {
  return (
    <div className={styles.review__bottom}>
      <div>
        {liked ? (
          <LikeTwoTone
            style={{ padding: "0.5em" }}
            twoToneColor="#eb2f96"
            key="Like"
            onClick={onUnlike}
          />
        ) : (
          <LikeOutlined
            style={{ padding: "0.5em" }}
            key="Like"
            onClick={onUnlike}
          />
        )}
        <span className={styles.likeCount}>{likeCount}</span>
      </div>
      <span className={styles.date}>{date}</span>
    </div>
  );
};

export default ReviewCardBottom;
