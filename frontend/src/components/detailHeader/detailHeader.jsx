import { HeartOutlined, HeartTwoTone, LeftOutlined } from "@ant-design/icons";
import React from "react";
import styles from "./detailHeader.module.css";

const DetailHeader = ({ category, onSaveHandler }) => {
  // onShareDetail은 만약 공유하기 기능이 추가된다면 공유
  const liked = true;
  return (
    <div className={styles.DetailHeader}>
      <div className={styles.navigation} role="뒤로 가기">
        <div className={styles.navigationBtn}>
          <div className={styles.navigationIcon}>
            <LeftOutlined />
          </div>
          <div className={styles.categoryBox}>
            <span className={styles.category}>{category || "카테고리"}</span>
          </div>
        </div>
      </div>
      <div className={styles.serviceIcons}>
        <div className={styles.heart}>
          {liked ? (
            <HeartTwoTone
              className={styles.heartBtn}
              twoToneColor="#eb2f96"
              // onClick={onClickDelete}
            />
          ) : (
            <HeartOutlined
              className={styles.heartBtn}
              style={{ color: "grey" }}
              // onClick={onHandleLike}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default DetailHeader;
