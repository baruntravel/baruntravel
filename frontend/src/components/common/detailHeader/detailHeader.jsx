import {
  HeartOutlined,
  HeartTwoTone,
  LeftOutlined,
  ShoppingTwoTone,
} from "@ant-design/icons";
// import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
// import { faShoppingBag } from "@fortawesome/free-solid-svg-icons";
import React from "react";
import styles from "./detailHeader.module.css";

const DetailHeader = ({
  liked,
  name,
  needLogin,
  onOpenPortalAuth,
  onHandleLike,
  onHandleUnlike,
}) => {
  // onShareDetail은 만약 공유하기 기능이 추가된다면 공유
  return (
    <div className={styles.DetailHeader}>
      <div className={styles.navigation} role="뒤로 가기">
        <div className={styles.navigationBtn}>
          <div className={styles.navigationIcon}>
            <LeftOutlined style={{ color: "#ED6AB8" }} />
          </div>
          <div className={styles.nameBox}>
            <span className={styles.name}>{name || "해당 이름"}</span>
          </div>
        </div>
      </div>
      <div className={styles.serviceIcons}>
        <div className={styles.bag}>
          <ShoppingTwoTone className={styles.icon} twoToneColor="#ED6AB8" />
        </div>
        <div className={styles.heart}>
          {liked ? (
            <HeartTwoTone
              className={styles.icon}
              twoToneColor="#eb2f96"
              onClick={onHandleUnlike}
            />
          ) : (
            <HeartOutlined
              className={styles.icon}
              style={{ color: "grey" }}
              onClick={onHandleLike}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default DetailHeader;
