import { Rate } from "antd";
import React from "react";
import styles from "./reviewScoreText.module.css";

const ReviewScoreText = ({ score, content }) => {
  return (
    <div className={styles.ReviewScoreText}>
      <div className={styles.rate}>
        <div className={styles.rateBox}>
          <Rate
            className={styles.rate}
            value={parseInt(score)}
            disabled={true}
            style={{
              fontSize: "0.9em",
              marginBottom: "12px",
            }}
          />
          <span className={styles.score}>{parseInt(score)}</span>
        </div>
        <section>
          <span className={styles.context}>{content}</span>
        </section>
      </div>
    </div>
  );
};

export default ReviewScoreText;
