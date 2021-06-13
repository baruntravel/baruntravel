import React from "react";
import styles from "./verticalWishList.module.css";

const VerticalWishList = (props) => {
  const wishListItems = [
    "https://i.pinimg.com/564x/d7/ec/75/d7ec75c9e68873ee75b734ac4ab09ced.jpg",
    "https://i.pinimg.com/474x/30/5a/21/305a216481dfaaec10fd59cf1f667652.jpg",
    "https://i.pinimg.com/474x/5f/a2/8e/5fa28eae2bdebd6ab2d30690304927b9.jpg",
    "https://i.pinimg.com/474x/a6/56/70/a65670944d4bf492a3a71c4a95bb3910.jpg",
  ];

  return (
    <div className={styles.VerticalWishList}>
      <header className={styles.header}>
        <span>찜 목록에 플레이스 추가하기</span>
        <button className={styles.closeButton}>X</button>
      </header>
      <section className={styles.body}>
        <ul className={styles.list}>
          <li className={styles.wishItem}>
            <div
              className={`${styles.imageContainer} ${styles.plusContainer}`}
            ></div>
            <span className={styles.name}>이름</span>
          </li>
          {wishListItems.map((item, index) => (
            <li key={index} className={styles.wishItem}>
              <div className={styles.imageContainer}>
                <img className={styles.image} src={item} alt="thumbnail" />
              </div>
              <span className={styles.name}>이름</span>
            </li>
          ))}
        </ul>
      </section>
    </div>
  );
};

export default VerticalWishList;
